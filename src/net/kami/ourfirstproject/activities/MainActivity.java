package net.kami.ourfirstproject.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.datahandling.FuelFacade;
import net.kami.ourfirstproject.utils.DateUtil;

import org.apache.commons.lang3.StringUtils;

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

public class MainActivity extends OptionMenuActivity {

	public static final String EXTRA_MESSAGE = "net.kami.ourfirstproject.MESSAGE";
	public static final String EXTRA_OLDUSAGE = "net.kami.ourfirstproject.OLDUSAGE";
	public static final String EXTRA_AVERAGEUSAGE = "net.kami.ourfirstproject.AVERAGEUSAGE";

	private static final int DIALOG_WRONG_FORMAT = 1;

	private EditText editKilometers;
	private EditText editLiters;
	private DatePicker editDate;

	private List<FuelEntry> fuelEntryList = new ArrayList<FuelEntry>();

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
		// beim Monat muss 1 hinzugef�gt werden, da Januar der Monat 0 ist
		String month = DateUtil.convert(editDate.getMonth() + 1, 2);
		String year = String.valueOf(editDate.getYear());
		String dateString = year + "-" + month + "-" + day;
		return dateString;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	// TODO: XML-Export erg�nzen, dazu sollten die Eintr�ge pro Tankvorgang auf
	// ein
	// eigenes Objekt gemappt werden
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		return true;
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
		editKilometers = (EditText) findViewById(R.id.edit_kilometers);
		editKilometers.setText(null);
		editLiters = (EditText) findViewById(R.id.edit_liters);
		editLiters.setText(null);
		Button button = (Button) findViewById(R.id.button);
		button.setEnabled(false);

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

		// die eingegebenen Werte f�r die Navigationselemente werden abgerufen
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
				saveUsageInDb(dateString, kilometers, liters,
						usageRoundedDouble);

				// die Nachrichten zur �bergabe an die n�chste Activity werden
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

	// TODO: �ber Kontextmen� zug�ngliche Liste der letzten Verbr�uche (mit
	// Datum)
	// zug�nglich machen, in der auch falsch erfasste Werte gel�scht werden
	// k�nnen

	// die Methode saveUsageInDB speichert die aktuellen Verbrauchsdaten in der
	// Datenbank
	public void saveUsageInDb(String date, double kilometer, double liter,
			double usage) {
		fuelEntryList.add(new FuelEntry(liter, kilometer, date, usage));
		FuelEntryDAO.getInstance().saveEntry(fuelEntryList, this);
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
