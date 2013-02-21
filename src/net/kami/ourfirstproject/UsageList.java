package net.kami.ourfirstproject;

import java.util.ArrayList;

import net.kami.ourfirstproject.utils.NumberUtil;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class UsageList extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBHelper dbh = new DBHelper(this);
		ArrayList<Double> usage = new ArrayList<Double>(dbh.getUsageList());
		ArrayList<StringBuffer> usageStrings = new ArrayList<StringBuffer>();
		for (Double liters : usage) {
			StringBuffer usageString = NumberUtil.formatDecimalNumber(liters,
					this);
			usageStrings.add(usageString);
		}

		ArrayAdapter<StringBuffer> adapter = new ArrayAdapter<StringBuffer>(
				this, android.R.layout.simple_list_item_1, android.R.id.text1,
				usageStrings);

		// Assign adapter to ListView
		setListAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}