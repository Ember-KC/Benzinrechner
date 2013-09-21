package net.kami.ourfirstproject.activities;

import net.kami.ourfirstproject.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionMenuActivity extends Activity {

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	// TODO csv Export und Import hinzufügen
	@Override
	public final boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.menu_deleteDatabase:
			startActivity(new Intent(this, ResetDatabaseActivity.class));
			return true;
		case R.id.menu_showUsageList:
			startActivity(new Intent(this, UsageList.class));
			return true;
		case R.id.menu_showUsageReport:
			startActivity(new Intent(this, UsageReportActivity.class));
			return true;
		case R.id.menu_showUsageChart:
			startActivity(new Intent(this, ChartActivity.class));
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
