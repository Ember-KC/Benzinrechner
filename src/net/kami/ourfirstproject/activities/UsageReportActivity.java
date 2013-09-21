package net.kami.ourfirstproject.activities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.utils.DateUtil;
import net.kami.ourfirstproject.utils.NumberUtil;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

public class UsageReportActivity extends Activity {

	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage_report);

		webView = (WebView) findViewById(R.id.usageReportTable);

		String customHtml = "<html><body><table border =1><tr><td> "
				+ getResources().getString(R.string.date) + "</td><td>"
				+ getResources().getString(R.string.kilometers) + "</td><td>"
				+ getResources().getString(R.string.liters) + "</td><td> "
				+ getResources().getString(R.string.usage) + " </td></tr>"
				+ generateTable(this) + " </table></body></html>";

		webView.loadData(customHtml, "text/html", "UTF-8");

	}

	private String generateTable(Context context) {
		List<FuelEntry> fuelEntries = FuelEntryDAO.getInstance()
				.getEntryForListView(this);
		Comparator<FuelEntry> descendingOrder = Collections.reverseOrder();
		Collections.sort(fuelEntries, descendingOrder);
		String date = null;
		double liters = 0;
		double kilometers = 0;
		double usage = 0;
		String usageHtml = "";
		for (FuelEntry fe : fuelEntries) {
			date = DateUtil.parseDateStringForLocale(fe.getDate(), this);
			liters = fe.getLiters();
			kilometers = fe.getKilometers();
			usage = fe.getUsage();
			usageHtml = usageHtml
					+ "<tr><td>"
					+ date
					+ "</td><td align = \"right\">"
					+ NumberUtil.formatDecimalNumberForLocale(kilometers,
							context) + "</td><td align = \"right\">"
					+ NumberUtil.formatDecimalNumberForLocale(liters, context)
					+ "</td><td align = \"right\">"
					+ NumberUtil.formatDecimalNumberForLocale(usage, context)
					+ "</td></tr>";
		}
		return usageHtml;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usage_report, menu);
		return true;
	}

}
