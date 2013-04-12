package net.kami.ourfirstproject.activities;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.DBHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ResetDatabaseActivity extends Activity {

	private static final String TAG = "ResetDatabaseActivity";

	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reset_database_activity);
	}

	public final void abortDatabaseReset(final View view) {
		finish();
	}

	public final void confirmDatabaseReset(final View view) {
		DBHelper dbh = new DBHelper(this);
		dbh.onDelete(this);
		Log.i(TAG,
				"Datenbank zurückgesetzt, Tabelle"
						+ DBHelper.getTableNameUsage() + " geleert");
		Toast.makeText(this, R.string.database_reset, Toast.LENGTH_SHORT)
				.show();
		finish();
	}
}
