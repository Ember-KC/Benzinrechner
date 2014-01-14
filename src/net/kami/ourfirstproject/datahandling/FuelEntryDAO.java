package net.kami.ourfirstproject.datahandling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.kami.ourfirstproject.utils.DateUtil;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class FuelEntryDAO {

	private static FuelEntryDAO instance = null;

	/**
	 * Default-Konstruktor, der nicht au�erhalb dieser Klasse aufgerufen werden
	 * kann
	 */
	private FuelEntryDAO() {
	}

	/**
	 * Statische Methode, liefert die einzige Instanz dieser Klasse zur�ck
	 */
	public static FuelEntryDAO getInstance() {
		if (instance == null) {
			instance = new FuelEntryDAO();
		}
		return instance;
	}

	// minDate: Tagesdatum minus ein Jahr
	// maxDate: Tagesdatum
	public List<FuelEntry> getEntryForListView(final Context context) {
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

    public int getUsageEntryCount(final Context context) {
        int count = 0;
        List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();
        DBHelper dbh = new DBHelper(context);
        String selectQuery = "SELECT COUNT(*) FROM "
                + DBHelper.getTableNameUsage()+";";

        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count;
    }

	public void deleteSelectedEntries(
			final Collection<FuelEntry> fuelEntryList, final Context context) {
        DBHelper dbh = new DBHelper(context);
        for (FuelEntry fe : fuelEntryList) {
            String date = fe.getDate().toString();
            String kilometer = Double.toString(fe.getKilometers());
            String liter = Double.toString(fe.getLiters());
            String usage = Double.toString(fe.getUsage());
			dbh.delete(date, kilometer, liter, usage);

		}

	}

	public void saveEntry(final List<FuelEntry> fuelEntryList,
			final Context context) {
        DBHelper dbh = new DBHelper(context);
        for (FuelEntry fe : fuelEntryList) {
            String date = fe.getDate().toString();
            String kilometer = Double.toString(fe.getKilometers());
            String liter = Double.toString(fe.getLiters());
            String usage = Double.toString(fe.getUsage());
			dbh.insert(date, kilometer, liter, usage);
		}

	}
}
