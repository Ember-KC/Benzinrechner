package net.kami.ourfirstproject.activities;

import java.util.Collections;
import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.utils.DateUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

public class UsageReportActivity extends Activity {

	private WebView webView;
	private List<FuelEntry> fuelEntries;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage_report);

		webView = (WebView) findViewById(R.id.usageReportTable);

		String customHtml = "<html><body><table border =1><tr><td> "
				+ "Datum </td><td>Kilometer</td><td> Liter </td><td> Verbrauch </td>"
				+ generateTable() + " </table></body></html>";

		webView.loadData(customHtml, "text/html", "UTF-8");

	}

	private String generateTable() {
		fuelEntries = FuelEntryDAO.getInstance().getEntryForListView(this);
		Collections.sort(fuelEntries);
		String date = null;
		double liters = 0;
		double kilometers = 0;
		double usage = 0;
		String usageHtml = null;
		for (FuelEntry fe : fuelEntries) {
			date = DateUtil.parseDateStringForLocale(fe.getDate(), this);
			liters = fe.getLiters();
			kilometers = fe.getKilometers();
			usage = fe.getUsage();
			usageHtml = usageHtml + "<tr><td>" + date + "</td><td>"
					+ kilometers + "</td><td>" + liters + "</td><td>" + usage
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
