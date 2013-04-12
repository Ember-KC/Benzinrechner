package net.kami.ourfirstproject.activities;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.utils.NumberUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMessageActivity extends OptionMenuActivity {
	public static final String EXTRA_KILOMETERS = "net.kami.ourfirstproject.KILOMETERS_MESSAGE";
	public static final String EXTRA_LITERS = "net.kami.ourfirstproject.LITERS_MESSAGE";
	private int defaultUsage = 0;

	@Override
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		double usageRounded = intent.getDoubleExtra(MainActivity.EXTRA_MESSAGE,
				defaultUsage);
		double oldUsage = intent.getDoubleExtra(MainActivity.EXTRA_OLDUSAGE,
				defaultUsage);
		double averageUsage = intent.getDoubleExtra(
				MainActivity.EXTRA_AVERAGEUSAGE, defaultUsage);

		// Message dem bereits bestehenden Textview zuweisen

		// Set the text view as the activity layout
		setContentView(R.layout.activity_display_message);
		TextView showMessage = (TextView) findViewById(R.id.usageText);
		showMessage.setText(new StringBuilder(this
				.getString(R.string.current_usage_text)
				+ NumberUtil.formatDecimalNumber(usageRounded, this)));
		TextView showOldUsage = (TextView) findViewById(R.id.oldUsageText);
		showOldUsage.setText(this.getString(R.string.old_usage_text)
				+ NumberUtil.formatDecimalNumber(oldUsage, this));
		TextView showAverageUsage = (TextView) findViewById(R.id.averageUsageText);

		if (averageUsage != 0) {
			showAverageUsage.setText(this
					.getString(R.string.average_usage_text)
					+ NumberUtil.formatDecimalNumber(averageUsage, this));

		} else {
			showAverageUsage.setText(this
					.getString(R.string.average_usage_not_displayed));

		}
		// Button calculateNew = (Button) findViewById(R.id.button_calcNew);
		ImageView iv = (ImageView) findViewById(R.id.smiley);
		this.findSmiley(usageRounded, averageUsage, iv);

	}

	public final void calculateNew(final View view) {
		finish();
	}

	public final void findSmiley(final double usageRounded,
			final double averageUsage, final ImageView iv) {
		if (averageUsage > 0) {

			if (usageRounded <= averageUsage) {
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.smiley_gut));
				iv.setTag("smiley_gut");
			} else {
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.smiley_schlecht));
				iv.setTag("smiley_schlecht");

			}
		}

	}
}
