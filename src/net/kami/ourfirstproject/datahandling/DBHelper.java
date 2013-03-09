package net.kami.ourfirstproject.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = DBHelper.class.getSimpleName();

	// Define the version and database file name
	private static final String DB_NAME = "benzin1.db";
	private static final int DB_VERSION = 1;

	// Name und Attribute der Tabelle "usageTable"
	private static final String TABLE_NAME_USAGE = "usage_table";
	private static final String COL_ID = "_id";
	public static final String COL_DATE = "date";
	public static final String COL_KILOMETER = "kilometer";
	public static final String COL_LITER = "liter";
	public static final String COL_USAGE = "usage";

	// Tabelle erstellen

	private static final String TABLE_USAGE_CREATE = "CREATE TABLE "
			+ getTableNameUsage() + " ( " + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + COL_DATE + " STRING, "
			+ COL_KILOMETER + " STRING , " + COL_LITER + " STRING , "
			+ COL_USAGE + " STRING);";

	// Tabelle löschen
	private static final String TABLE_USAGE_DROP = "DROP TABLE IF EXISTS "
			+ getTableNameUsage();

	// Tabelle leeren
	private static final String TABLE_USAGE_DELETE_ENTRIES = "DELETE FROM "
			+ getTableNameUsage() + ";";

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
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(TABLE_USAGE_DELETE_ENTRIES);
		db.close();

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
			rowId = db.insert(getTableNameUsage(), null, values);
			db.close();

		} catch (SQLiteException e) {
			Log.e(TAG, "insert()", e);

		} finally {
			Log.i(TAG, "Insert() : rowId" + rowId);

		}
	}

	public static String getTableNameUsage() {
		return TABLE_NAME_USAGE;
	}

	public void delete(String date, String kilometer, String liter, String usage) {
		// ggf. Datenbank öffnen
		SQLiteDatabase db = getWritableDatabase();
		int numDeleted = db.delete(TABLE_NAME_USAGE, COL_KILOMETER + " = ?"
				+ " AND " + COL_LITER + " =?" + " AND " + COL_DATE + " =?"
				+ " AND " + COL_USAGE + " =?", new String[] { kilometer, liter,
				date, usage });
		db.close();
		Log.i(TAG, "delete(): row =" + numDeleted);
	}
}
