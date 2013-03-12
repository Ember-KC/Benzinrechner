package net.kami.ourfirstproject.datahandling;

import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.utils.DateUtil;
import net.kami.ourfirstproject.utils.NumberUtil;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class UsageListArrayAdapter extends ArrayAdapter<FuelEntry> {

	private final Context context;
	private final List<FuelEntry> fuelEntries;
	private View rowView = null;

	private static final String TAG = "UsageListArrayAdapter";

	public UsageListArrayAdapter(Context context, int textViewResourceId,
			List<FuelEntry> fuelEntries) {
		super(context, textViewResourceId, fuelEntries);
		this.context = context;
		this.fuelEntries = fuelEntries;
	}

	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FuelEntry fe = fuelEntries.get(position);
		double liters = fe.getLiters();
		String dateString = fe.getDate();
		try {
			dateString = DateUtil.parseDateForLocale(dateString, context);

		} catch (java.text.ParseException p) {
			Log.d(TAG, "Error when parsing date" + dateString);

		}
		// falls der Adapter für das Layout list_view aufgerufen wird, wird
		// folgendes ausgeführt
		if (view.getId() == R.layout.list_view) {
			rowView = inflater.inflate(R.layout.list_view, parent, false);
			TextView textview1 = (TextView) rowView
					.findViewById(R.id.litersText);
			TextView textview2 = (TextView) rowView.findViewById(R.id.dateText);
			textview1.setText(NumberUtil.formatDecimalNumber(liters, context));
			textview2.setText(dateString);
		} else {
			// falls der Adapter für ein anderes Layout aufgerufen wird, wird
			// folgendes ausgeführt
			rowView = inflater
					.inflate(R.layout.delete_list_view, parent, false);
			CheckedTextView checkBox = (CheckedTextView) rowView
					.findViewById(R.id.deleteCheckbox);
			TextView textview1 = (TextView) rowView
					.findViewById(R.id.litersText);
			TextView textview2 = (TextView) rowView.findViewById(R.id.dateText);
			textview1.setText(NumberUtil.formatDecimalNumber(liters, context));
			textview2.setText(dateString);
			checkBox.setChecked(false);

		}

		return rowView;

	}
}
