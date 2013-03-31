package net.kami.ourfirstproject.datahandling;

import java.util.List;
import java.util.Set;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.activities.UsageList;
import net.kami.ourfirstproject.utils.DateUtil;
import net.kami.ourfirstproject.utils.NumberUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
		// fuelEntrySet is implemented as Singleton as otherwise a new set would
		// be opened for each entry
		final Set<FuelEntry> fuelEntrySet = FuelEntryDeleteSet.getInstance();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final FuelEntry fe = fuelEntries.get(position);
		double liters = fe.getLiters();
		String dateString = fe.getDate();
		try {
			dateString = DateUtil.parseDateForLocale(dateString, context);

		} catch (java.text.ParseException p) {
			Log.d(TAG, "Error when parsing date" + dateString);

		}
		// falls der Adapter für das Layout list_view aufgerufen wird, wird
		// folgendes ausgeführt
		if (fe != null) {
			if (context instanceof UsageList) {
				rowView = inflater.inflate(R.layout.list_view, parent, false);
				TextView textview1 = (TextView) rowView
						.findViewById(R.id.litersText);
				TextView textview2 = (TextView) rowView
						.findViewById(R.id.dateText);
				textview1.setText(NumberUtil.formatDecimalNumber(liters,
						context));
				textview2.setText(dateString);
			} else {
				// falls der Adapter für ein anderes Layout aufgerufen wird,
				// wird
				// folgendes ausgeführt
				rowView = inflater.inflate(R.layout.delete_list_view, parent,
						false);
				CheckBox checkBox = (CheckBox) rowView
						.findViewById(R.id.deleteCheckbox);
				TextView textview1 = (TextView) rowView
						.findViewById(R.id.litersTextDeleteList);
				TextView textview2 = (TextView) rowView
						.findViewById(R.id.dateTextDeleteList);
				textview1.setText(NumberUtil.formatDecimalNumber(liters,
						context));
				textview2.setText(dateString);
				checkBox.setChecked(false);
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							// if an entry is checked, it is added to the list
							// of
							// entries to be deleted
							fuelEntrySet.add(fe);

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
												// Inflate a menu resource
												// providing
												// context
												// menu items
												MenuInflater inflater = mode
														.getMenuInflater();
												inflater.inflate(
														R.menu.delete_list_context_menu,
														menu);
												return true;
											}

											// Called each time the action mode
											// is
											// shown. Always
											// called after
											// onCreateActionMode, but
											// may be called multiple times if
											// the
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
													ActionMode mode,
													MenuItem item) {
												switch (item.getItemId()) {
												case R.id.delete_list_cancel:
													mode.finish();
													activity.finish();
													return true;
												case R.id.delete_list_confirm:
													showAlertDialog(context,
															mode, fuelEntrySet);
													return true;
												default:
													return false;
												}
											}

											// Called when the user exits the
											// action
											// mode
											@Override
											public void onDestroyActionMode(
													ActionMode mode) {
												mActionMode = null;
											}
										});
						} else {
							// checked entries that are unchecked again, are
							// removed
							// from the list of entries to be deleted
							fuelEntrySet.remove(fe);
							Log.d(TAG, "Entry " + fe.toString()
									+ " removed from selection.");
							Log.d(TAG, "Set-Name " + fuelEntrySet.toString());
							Log.d(TAG, "Set-Größe " + fuelEntrySet.size());
							// If the list of entries to be deleted is empty,
							// the
							// action bar can be closed
							if (mActionMode != null && fuelEntrySet.size() < 1) {
								mActionMode.finish();
								mActionMode = null;
							}
						}
					}
				});

			}
		}
		return rowView;

	}

	private void showAlertDialog(final Context context, final ActionMode mode,
			final Set<FuelEntry> fuelEntrySet) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

		// Setting Dialog Title
		alertDialog.setTitle(R.string.confirm_delete);

		// Setting Dialog Message
		alertDialog.setMessage(R.string.delete_entries_notification);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton(R.string.button_confirmAction,
				new DialogInterface.OnClickListener() {
					// if the confirm button is clicked, all entries in the set
					// of entries to be deleted are passed to the DAO to be
					// deleted
					public void onClick(DialogInterface dialog, int which) {
						FuelEntryDAO.getInstance().deleteSelectedEntries(
								fuelEntrySet, context);
						mode.finish();
						activity.finish();
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
}
