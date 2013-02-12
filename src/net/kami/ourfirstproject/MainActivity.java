package net.kami.ourfirstproject;

import java.math.BigDecimal;

import net.kami.ourfirstproject.utils.DateUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

//TODO: Ober- und Untergrenzen für guten Verbrauch einstellen --> auf Anzeigeseite soll Smiley zeigen wie gut Verbrauch ist

public class MainActivity extends Activity {

	public static final String EXTRA_MESSAGE = "net.kami.ourfirstproject.MESSAGE";
	public static final String EXTRA_OLDUSAGE = "net.kami.ourfirstproject.OLDUSAGE";
	public static final String EXTRA_AVERAGEUSAGE = "net.kami.ourfirstproject.AVERAGEUSAGE";

	private DBHelper dbhelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button);
		button.setEnabled(false);

		final EditText editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		final EditText editLiters = (EditText) findViewById(R.id.edit_liters);
		final DatePicker editDate = (DatePicker) findViewById(R.id.edit_date);

		TextWatcher textwatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String kilometer = editKilometers.getText().toString();
				String liter = editLiters.getText().toString();

				Button button = (Button) findViewById(R.id.button);
				button.setEnabled(kilometer.length() > 0 && liter.length() > 0);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};

		editKilometers.addTextChangedListener(textwatcher);
		editLiters.addTextChangedListener(textwatcher);

	}

	public String getDate(DatePicker editDate) {
		String day = DateUtil.convert(editDate.getDayOfMonth(), 2);
		// beim Monat muss 1 hinzugefügt werden, da Januar der Monat 0 ist
		String month = DateUtil.convert(editDate.getMonth() + 1, 2);
		String year = String.valueOf(editDate.getYear());
		String dateString = year + "-" + month + "-" + day;
		return dateString;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			loadSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadSettings() {
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// Set the text view as the activity layout
		setContentView(R.layout.activity_main);
		EditText editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		editKilometers.setText(null);
		EditText editLiters = (EditText) findViewById(R.id.edit_liters);
		editLiters.setText(null);

		// Activity being restarted from stopped state
	}

	// TODO: Businesslogik in separate Klasse auslagern

	public void getFuelValues(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		// die Navigationselemente werden abgerufen und Variablen zugewiesen
		EditText editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		EditText editLiters = (EditText) findViewById(R.id.edit_liters);
		DatePicker editDate = (DatePicker) findViewById(R.id.edit_date);

		// die eingegebenen Werte für die Navigationselemente werden abgerufen
		// und Variablen zugewiesen
		String kilometersString = editKilometers.getText().toString();
		double kilometers = Double.parseDouble(kilometersString);
		String litersString = editLiters.getText().toString();
		double liters = Double.parseDouble(litersString);

		BigDecimal usageRounded = FuelFacade.calculateFuel(liters, kilometers);
		StringBuilder message = new StringBuilder("Sie haben " + usageRounded
				+ " Liter auf 100 Kilometer verbraucht.");

		// der Verbrauch des vorhergehenden Tankvorgangs und der
		// Durchschnittsverbrauch werden abgerufen
		String oldUsage = getOldUsage();
		String averageUsage = FuelFacade.calculateAverageUsage(this);

		// die aktuell eingegebenen Verbrauchsdaten werden in der
		// Preferences-Datei gespeichert
		saveUsage(usageRounded);

		// die aktuell eingegebenen Verbrauchsdaten werden in der DB gespeichert
		String dateString = getDate(editDate);
		dbhelper = new DBHelper(this);
		saveUsageInDb(dateString, kilometersString, litersString,
				usageRounded.toString());

		// die Nachrichten zur Übergabe an die nächste Activity werden
		// zusammengestellt
		intent.putExtra(EXTRA_AVERAGEUSAGE, averageUsage);
		intent.putExtra(EXTRA_OLDUSAGE, oldUsage);
		intent.putExtra(EXTRA_MESSAGE, message.toString());
		startActivity(intent);
	}

	private String getOldUsage() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String defaultUsage = getResources().getString(
				R.string.old_usage_default);
		String oldUsage = sharedPref.getString(getString(R.string.save_usage),
				defaultUsage);
		return oldUsage;
	}

	// TODO: Speicherung in Datei entfernen und letzten Verbrauch aus DB lesen
	// die Methode sageUsage(BigDecimal usage) speichert den aktuellen Verbrauch
	// in einer Preferences-Date
	public void saveUsage(BigDecimal usage) {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.save_usage), usage.toString());
		editor.commit();
	}

	// TODO: über Kontextmenü zugängliche Liste der letzten Verbräuche
	// zugänglich machen, in der auch ggf. falsch erfasste Werte geändert werden
	// können

	// die Methode saveUsageInDB speichert die aktuellen Verbrauchsdaten in der
	// Datenbank
	public void saveUsageInDb(String date, String kilometer, String liter,
			String usage) {
		dbhelper.insert(date, kilometer, liter, usage);
		Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
	}

}
