package net.kami.ourfirstproject.datahandling;

import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.activities.UsageList;
import net.kami.ourfirstproject.utils.DateUtil;
import net.kami.ourfirstproject.utils.NumberUtil;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class UsageListArrayAdapter extends ArrayAdapter<FuelEntry> {

	private final Context context;
	private final List<FuelEntry> fuelEntries;
	private View rowView = null;
	private ActionMode mActionMode;
	private ActionMode.Callback mActionModeCallback;
	private Activity activity;

	private static final String TAG = "UsageListArrayAdapter";

	public UsageListArrayAdapter(Context context, int textViewResourceId,
			List<FuelEntry> fuelEntries, Activity activity) {
		super(context, textViewResourceId, fuelEntries);
		this.context = context;
		this.fuelEntries = fuelEntries;
		this.activity = activity;
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
		if (context instanceof UsageList) {
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
			CheckBox checkBox = (CheckBox) rowView
					.findViewById(R.id.deleteCheckbox);
			TextView textview1 = (TextView) rowView
					.findViewById(R.id.litersTextDeleteList);
			TextView textview2 = (TextView) rowView
					.findViewById(R.id.dateTextDeleteList);
			textview1.setText(NumberUtil.formatDecimalNumber(liters, context));
			textview2.setText(dateString);
			checkBox.setChecked(false);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					/*
					 * String tag = (String) buttonView.getTag(); String[] pos =
					 * tag.split(",");
					 */
					if (isChecked) {
						if (mActionMode == null)
							mActionMode = activity
									.startActionMode(new ActionMode.Callback() {

										// Called when the action mode is
										// created;
										// startActionMode() was
										// called
										@Override
										public boolean onCreateActionMode(
												ActionMode mode, Menu menu) {
											// Inflate a menu resource providing
											// context
											// menu items
											MenuInflater inflater = mode
													.getMenuInflater();
											inflater.inflate(
													R.menu.delete_list_context_menu,
													menu);
											return true;
										}

										// Called each time the action mode is
										// shown. Always
										// called after
										// onCreateActionMode, but
										// may be called multiple times if the
										// mode
										// is
										// invalidated.
										@Override
										public boolean onPrepareActionMode(
												ActionMode mode, Menu menu) {
											return false; // Return false if
															// nothing
															// is done
										}

										// Called when the user selects a
										// contextual
										// menu
										// item
										@Override
										public boolean onActionItemClicked(
												ActionMode mode, MenuItem item) {
											switch (item.getItemId()) {
											case R.id.delete_list_cancel:
												mode.finish(); // Action picked,
																// so
																// close
																// the CAB
												return true;
											case R.id.delete_list_confirm:
												// deleteUsageEntries();
												mode.finish();
												return true;
											default:
												return false;
											}
										}

										// Called when the user exits the action
										// mode
										@Override
										public void onDestroyActionMode(
												ActionMode mode) {
											mActionMode = null;
										}
									});
					} else {
						if (mActionMode != null) { // Only operate when
													// mActionMode is available

							mActionMode.finish();
							mActionMode = null;
						}
					}
				}
			});

		}
		return rowView;
	}
}
