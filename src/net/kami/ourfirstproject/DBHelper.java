package net.kami.ourfirstproject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.kami.ourfirstproject.utils.DateUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = DBHelper.class.getSimpleName();

	// Define the version and database file name
	private static final String DB_NAME = "benzin1.db";
	private static final int DB_VERSION = 1;
	private static final String JOURNAL_NAME = DB_NAME + ".db-journal";

	// Name und Attribute der Tabelle "usageTable"
	private static final String TABLE_NAME_USAGE = "usage_table";
	private static final String COL_ID = "id";
	private static final String COL_DATE = "date";
	private static final String COL_KILOMETER = "kilometer";
	private static final String COL_LITER = "liter";
	private static final String COL_USAGE = "usage";

	// Tabelle erstellen

	private static final String TABLE_USAGE_CREATE = "CREATE TABLE "
			+ TABLE_NAME_USAGE + " ( " + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + COL_DATE + " STRING, "
			+ COL_KILOMETER + " STRING , " + COL_LITER + " STRING , "
			+ COL_USAGE + " STRING);";

	// Tabelle löschen
	private static final String TABLE_USAGE_DROP = "DROP TABLE IF EXISTS "
			+ TABLE_NAME_USAGE;

	// Constructor to simplify Business logic access to the repository
	public DBHelper(Context context) {

		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_USAGE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrade der Datenbank von Version " + oldVersion + " zu "
				+ newVersion + "; alle Daten werden gelöscht");
		db.execSQL(TABLE_USAGE_DROP);
		onCreate(db);

	}

	public void onDelete(Context context) {
		File file = context.getDatabasePath(DB_NAME);
		SQLiteDatabase.deleteDatabase(file);
		Log.i(TAG, "Datenbank gelöscht");

	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void insert(String date, String kilometer, String liter, String usage) {
		long rowId = -1;
		try {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(COL_DATE, date);
			values.put(COL_KILOMETER, kilometer);
			values.put(COL_LITER, liter);
			values.put(COL_USAGE, usage);
			rowId = db.insert(TABLE_NAME_USAGE, null, values);
			db.close();

		} catch (SQLiteException e) {
			Log.e(TAG, "insert()", e);

		} finally {
			Log.i(TAG, "Insert() : rowId" + rowId);

		}
	}

	// minDate: Tagesdatum minus ein Jahr
	// maxDate: Tagesdatum
	public List<Double> getUsageList() {
		List<Double> usageList = new ArrayList<Double>();
		// Select All Query
		String selectQuery = "SELECT usage FROM "
				+ TABLE_NAME_USAGE
				+ " WHERE "
				+ COL_DATE
				+ " BETWEEN "
				+ "\""
				+ DateUtil.getDateAsString(DateUtil.calculateDateByYear(
						new Date(), -1)) + "\"" + " AND " + "\""
				+ DateUtil.getDateAsString(new Date()) + "\"";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				double usage = Double.parseDouble(cursor.getString(0));
				// Adding usage to list
				usageList.add(usage);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// return contact list
		return usageList;
	}

}
