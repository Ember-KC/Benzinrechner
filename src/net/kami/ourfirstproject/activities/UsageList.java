package net.kami.ourfirstproject.activities;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.DBHelper;
import net.kami.ourfirstproject.datahandling.UsageListCursorAdapter;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

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
		MenuInflater inflater = getMenuInflater();
		menu.setHeaderTitle(this.getString(R.string.contextmenu_title));
		inflater.inflate(R.menu.usage_list_contextmenu, menu);
	}

	// TODO Vor dem L�schen eines Eintrags muss eine Abfrage kommen, mit der der
	// User das L�schen best�tigen oder Abbrechen kann
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.menu_delete_entry:
			dbh.delete(info.id);
			updateList();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void updateList() {
		// zun�chst Cursor, dann Liste aktualisieren
		dbCursor.requery();
		listAdapter.notifyDataSetChanged();
	}
}