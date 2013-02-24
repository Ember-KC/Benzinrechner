package net.kami.ourfirstproject;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;

public class UsageList extends ListActivity {

	// Schnittstelle zur Datenbank
	private DBHelper dbh;
	// wird f�r die Listenansicht ben�tigt
	private Cursor dbCursor;
	// bildet den Cursor auf die ListView ab
	private UsageListCursorAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Tippen und Halten �ffnet Men�
		registerForContextMenu(getListView());

		dbh = new DBHelper(this);
		dbCursor = dbh.query();
		// Activity �bernimmt Verwaltung des Cursors
		startManagingCursor(dbCursor);

		listAdapter = new UsageListCursorAdapter(this, dbCursor);
		setListAdapter(listAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbh.close();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// Kontextmen� entfalten
		/*
		 * MenuInflater inflater = getMenuInflater();
		 * inflater.inflate(R.menu.context_menu, menu);
		 */
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return false;

	}

	private void updateList() {
		// zun�chst Cursor, dann Liste aktualisieren
		dbCursor.requery();
		listAdapter.notifyDataSetChanged();
	}
}