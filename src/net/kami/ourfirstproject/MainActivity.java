package net.kami.ourfirstproject;

import java.math.BigDecimal;

import net.kami.ourfirstproject.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

//TODO: auf Anzeigeseite soll Smiley zeigen wie gut Verbrauch im Vergleich zu Durchschnitt ist --> Unit-Test fehlt noch!!!

public class MainActivity extends Activity {

	public static final String EXTRA_MESSAGE = "net.kami.ourfirstproject.MESSAGE";
	public static final String EXTRA_OLDUSAGE = "net.kami.ourfirstproject.OLDUSAGE";
	public static final String EXTRA_AVERAGEUSAGE = "net.kami.ourfirstproject.AVERAGEUSAGE";

	private static final int DIALOG_WRONG_FORMAT = 1;

	private EditText editKilometers;
	private EditText editLiters;
	private DatePicker editDate;

	private DBHelper dbhelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button);
		button.setEnabled(false);

		editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		editLiters = (EditText) findViewById(R.id.edit_liters);
		editDate = (DatePicker) findViewById(R.id.edit_date);

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
		case R.id.menu_deleteDatabase:
			loadDatabaseReset();
		case R.id.menu_showUsageList:
			loadUsageList();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadSettings() {
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	private void loadDatabaseReset() {
		Intent resetDatabaseIntent = new Intent(this,
				ResetDatabaseActivity.class);
		startActivity(resetDatabaseIntent);
	}

	private void loadUsageList() {
		Intent usageListIntent = new Intent(this, UsageList.class);
		startActivity(usageListIntent);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	// TODO: beim Restart ist Button ab Beginn aktiviert, sollte nach dem ersten
	// Drücken deaktiviert sein
	@Override
	protected void onRestart() {
		super.onRestart();
		// Set the text view as the activity layout
		setContentView(R.layout.activity_main);
		editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		editKilometers.setText(null);
		editLiters = (EditText) findViewById(R.id.edit_liters);
		editLiters.setText(null);
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

		// Activity being restarted from stopped state
	}

	public void getFuelValues(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		// die Navigationselemente werden abgerufen und Variablen zugewiesen
		EditText editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		EditText editLiters = (EditText) findViewById(R.id.edit_liters);
		DatePicker editDate = (DatePicker) findViewById(R.id.edit_date);

		// die eingegebenen Werte für die Navigationselemente werden abgerufen
		// und Variablen zugewiesen
		String kilometersString = editKilometers.getText().toString();
		String litersString = editLiters.getText().toString();
		double kilometers = 0.00;
		double liters = 0.00;
		if (StringUtils.isNotBlank(kilometersString)
				&& StringUtils.isNotBlank(litersString)) {
			kilometers = Double.parseDouble(kilometersString);
			liters = Double.parseDouble(litersString);

			if (kilometers > 0 && liters > 0) {

				BigDecimal usageRounded = FuelFacade.calculateFuel(liters,
						kilometers);
				double usageRoundedDouble = usageRounded.doubleValue();

				// der Verbrauch des vorhergehenden Tankvorgangs und der
				// Durchschnittsverbrauch werden abgerufen
				Double oldUsage = getOldUsage();
				double averageUsage = FuelFacade.calculateAverageUsage(this);

				// die aktuell eingegebenen Verbrauchsdaten werden in der
				// Preferences-Datei gespeichert
				saveUsage(usageRounded);

				// die aktuell eingegebenen Verbrauchsdaten werden in der DB
				// gespeichert
				String dateString = getDate(editDate);
				dbhelper = new DBHelper(this);
				saveUsageInDb(dateString, kilometersString, litersString,
						usageRounded.toString());

				// die Nachrichten zur Übergabe an die nächste Activity werden
				// zusammengestellt
				intent.putExtra(EXTRA_AVERAGEUSAGE, averageUsage);
				intent.putExtra(EXTRA_OLDUSAGE, oldUsage);
				intent.putExtra(EXTRA_MESSAGE, usageRoundedDouble);
				startActivity(intent);
			}

		} else {
			showDialog(1);
		}

	}

	private Double getOldUsage() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String defaultUsage = getResources().getString(
				R.string.old_usage_default);
		String oldUsage = sharedPref.getString(getString(R.string.save_usage),
				defaultUsage);
		Double oldUsageDouble = Double.parseDouble(oldUsage);
		return oldUsageDouble;
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

	protected Dialog onCreateDialog(int id, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		if (id == DIALOG_WRONG_FORMAT) {
			builder.setTitle(R.string.warning_dialog_title);
			builder.setMessage(R.string.invalid_input_message);
			builder.setCancelable(false);
			builder.setPositiveButton(R.string.close,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Beim KLick auf den Button wird der Dialog
							// automatisch geschlossen, dazu ist kein
							// gesonderter Methodenaufruf notwendig
						}
					});
		}
		return builder.create();

	}

}
