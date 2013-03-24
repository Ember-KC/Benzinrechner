package net.kami.ourfirstproject.activities;

import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.datahandling.UsageListArrayAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UsageListDeleteActivity extends ListActivity {

	private ArrayAdapter<FuelEntry> listAdapter;
	private List<FuelEntry> fuelEntries;
	protected int selectedItem;

	private static final String TAG = "UsageListDeleteActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_list_view);
		// Tippen und Halten öffnet Menü
		registerForContextMenu(getListView());

		fuelEntries = FuelEntryDAO.getInstance().getEntryForListView(this);

		listAdapter = new UsageListArrayAdapter(this,
				R.layout.delete_list_view, fuelEntries, this);

		ListView meineListView = (ListView) findViewById(android.R.id.list);
		meineListView.setAdapter(listAdapter);

	}
}
