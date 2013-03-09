package net.kami.ourfirstproject.activities;

import net.kami.ourfirstproject.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionMenuActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	// TODO: XML-Export ergänzen, dazu sollten die Einträge pro Tankvorgang auf
	// ein
	// eigenes Objekt gemappt werden
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
