package net.kami.ourfirstproject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class UsageListCursorAdapter extends CursorAdapter {
	private static final String TAG = "UsageListCursorAdapter";

	private LayoutInflater inflator;
	private int ciLiters, ciDate;

	public UsageListCursorAdapter(Context context, Cursor c) {
		// CursorAdapter nutzt weder FLAG_AUTO_REQUERY noch
		// FLAG_REGISTER_CONTENT_OBSERVER , deshalb wird im Konstruktor der Wert
		// 0 eingetragen
		super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		inflator = LayoutInflater.from(context);
		ciLiters = c.getColumnIndex(DBHelper.COL_USAGE);
		ciDate = c.getColumnIndex(DBHelper.COL_DATE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		TextView textview1 = (TextView) view.findViewById(R.id.litersText);
		TextView textview2 = (TextView) view.findViewById(R.id.dateText);
		String liters = cursor.getString(ciLiters);
		String dateString = cursor.getString(ciDate);
		Log.d(TAG, "Liter: " + liters);
		textview1.setText(liters);
		textview2.setText(dateString);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflator.inflate(R.layout.list_view, null);
	}

}
