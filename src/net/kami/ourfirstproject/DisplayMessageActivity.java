package net.kami.ourfirstproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
	public final static String EXTRA_KILOMETERS = "net.kami.ourfirstproject.KILOMETERS_MESSAGE";
	public final static String EXTRA_LITERS = "net.kami.ourfirstproject.LITERS_MESSAGE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		String oldUsage = intent.getStringExtra(MainActivity.EXTRA_OLDUSAGE);
		String averageUsage = intent
				.getStringExtra(MainActivity.EXTRA_AVERAGEUSAGE);

		// Message dem bereits bestehenden Textview zuweisen

		// Set the text view as the activity layout
		setContentView(R.layout.activity_display_message);
		TextView showMessage = (TextView) findViewById(R.id.usageText);
		showMessage.setText(message);
		TextView showOldUsage = (TextView) findViewById(R.id.oldUsageText);
		showOldUsage.setText("Beim letzten Mal haben Sie " + oldUsage
				+ " Liter auf 100 Kilometer verbraucht.");
		TextView showAverageUsage = (TextView) findViewById(R.id.averageUsageText);
		showAverageUsage.setText("Durchschnittlich haben Sie  " + averageUsage
				+ " Liter auf 100 Kilometer verbraucht.");
		Button calculateNew = (Button) findViewById(R.id.button_calcNew);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // This ID represents the
			// Home or Up button. In the case of this // activity, the Up button
			// is
			// shown. Use NavUtils to allow users // to navigate up one level in
			// the
			// application structure. For // more details, see the Navigation
			// pattern on
			// Android Design: // //
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void calculateNew(View view) {
		// Intent resetValues = new Intent(this, MainActivity.class);
		//
		// resetValues.putExtra(EXTRA_KILOMETERS, "1");
		// resetValues.putExtra(EXTRA_LITERS, "1");
		// startActivity(resetValues);
		finish();
	}
}
