package net.kami.ourfirstproject.datahandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.kami.ourfirstproject.utils.DateUtil;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FuelEntryDAO {
	private DBHelper dbhelper;

	private static FuelEntryDAO instance = null;

	private List<String> fuelEntryStrings = new ArrayList<String>();

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

	public List<FuelEntry> getEntryForListView(Context context) {
		List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();
		DBHelper dbh = new DBHelper(context);
		String selectQuery = "SELECT * FROM "
				+ DBHelper.getTableNameUsage()
				+ " WHERE "
				+ DBHelper.COL_DATE
				+ " BETWEEN "
				+ "\""
				+ DateUtil.getDateAsString(DateUtil.calculateDateByYear(
						new Date(), -1)) + "\"" + " AND " + "\""
				+ DateUtil.getDateAsString(new Date()) + "\"";

		SQLiteDatabase db = dbh.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int ciLiters = cursor.getColumnIndex(DBHelper.COL_LITER);
		int ciKilometers = cursor.getColumnIndex(DBHelper.COL_KILOMETER);
		int ciDate = cursor.getColumnIndex(DBHelper.COL_DATE);
		int ciUsage = cursor.getColumnIndex(DBHelper.COL_USAGE);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				double liters = Double.parseDouble(cursor.getString(ciLiters));
				double kilometers = Double.parseDouble(cursor
						.getString(ciKilometers));
				String date = cursor.getString(ciDate);
				double usage = Double.parseDouble(cursor.getString(ciUsage));
				FuelEntry fuelEntry = new FuelEntry(liters, kilometers, date,
						usage);
				// Adding usage to list
				fuelEntries.add(fuelEntry);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return fuelEntries;
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
