package net.kami.ourfirstproject.datahandling;

import java.util.Date;
import java.util.List;

import android.content.Context;

public class FuelEntryDAO {
	private DBHelper dbhelper;

	private static FuelEntryDAO instance = null;

	/**
	 * Default-Konstruktor, der nicht außerhalb dieser Klasse aufgerufen werden
	 * kann
	 */
	private FuelEntryDAO() {
	}

	/**
	 * Statische Methode, liefert die einzige Instanz dieser Klasse zurück
	 */
	public static FuelEntryDAO getInstance() {
		if (instance == null) {
			instance = new FuelEntryDAO();
		}
		return instance;
	}

	public List<FuelEntry> getEntryByDate(Date date) {
		return null;
	}

	public void deleteSelectedEntries(List<FuelEntry> fuelEntryList) {

	}

	public void saveEntry(List<FuelEntry> fuelEntryList, Context context) {
		for (FuelEntry fe : fuelEntryList) {
			String date = fe.getDate().toString();
			String kilometer = Double.toString(fe.getKilometers());
			String liter = Double.toString(fe.getLiters());
			String usage = Double.toString(fe.getUsage());
			DBHelper dbh = new DBHelper(context);
			dbh.insert(date, kilometer, liter, usage);
		}

	}
}
