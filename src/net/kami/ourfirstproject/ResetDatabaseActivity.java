package net.kami.ourfirstproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResetDatabaseActivity extends Activity {

	private static final String TAG = "ResetDatabaseActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reset_database_activity);
		TextView text = (TextView) findViewById(R.id.reset_database_text);
		Button buttonOk = (Button) findViewById(R.id.button_confirmDatasbaseReset);
		Button buttonAbort = (Button) findViewById(R.id.button_abortDatabaseReset);

	}

	public void abortDatabaseReset(View view) {
		finish();
	}

	public void confirmDatabaseReset(View view) {
		DBHelper dbh = new DBHelper(this);
		dbh.onDelete(this);
		Toast.makeText(this, R.string.database_reset, Toast.LENGTH_SHORT)
				.show();
		finish();
	}
}
