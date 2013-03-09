package net.kami.ourfirstproject.activities;

import java.util.ArrayList;
import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.DBHelper;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.datahandling.UsageListArrayAdapter;
import net.kami.ourfirstproject.export.UsageListExporter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UsageList extends ListActivity {

	// Schnittstelle zur Datenbank
	private DBHelper dbh;
	// wird für die Listenansicht benötigt
	private Cursor dbCursor;

	// bildet den Cursor auf die ListView ab
	private ArrayAdapter<FuelEntry> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		// Tippen und Halten öffnet Menü
		registerForContextMenu(getListView());

		List<FuelEntry> fuelEntries = FuelEntryDAO.getInstance()
				.getEntryForListView(this);

		listAdapter = new UsageListArrayAdapter(this, R.layout.list_view,
				fuelEntries);

		ListView meineListView = (ListView) findViewById(android.R.id.list);
		meineListView.setAdapter(listAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// Kontextmenü entfalten
		MenuInflater inflater = getMenuInflater();
		menu.setHeaderTitle(this.getString(R.string.contextmenu_title));
		inflater.inflate(R.menu.usage_list_contextmenu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {

		case R.id.menu_delete_entry:
			// Showing alert dialog if user choses to delete an entry
			showAlertDialog(this, info);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void showAlertDialog(Context context,
			final AdapterContextMenuInfo info) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle(R.string.confirm_delete);

		// Setting Dialog Message
		alertDialog.setMessage(R.string.delete_entry_question);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton(R.string.button_confirmAction,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						int position = (int) info.id;
						FuelEntry fe = listAdapter.getItem(position);
						List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();
						fuelEntries.add(fe);
						FuelEntryDAO.getInstance().deleteSelectedEntries(
								fuelEntries, UsageList.this);
						updateList();
					}
				});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton(R.string.button_abortAction,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	private void updateList() {
		// zunächst Cursor, dann Liste aktualisieren
		listAdapter.clear();
		List<FuelEntry> fuelEntries = FuelEntryDAO.getInstance()
				.getEntryForListView(this);
		listAdapter.addAll(fuelEntries);
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.usage_list_option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_exportXML:
			UsageListExporter.exportUsageList(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}