package net.kami.ourfirstproject.activities;

import java.util.List;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ChartActivity extends Activity {

	private XYMultipleSeriesRenderer multipleRenderer = new XYMultipleSeriesRenderer();
	private GraphicalView mChartView;
	private XYMultipleSeriesDataset multipleSeries;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
		createChart();
	}

	private void createChart() {

		// set some properties on the main renderer
		multipleRenderer.setApplyBackgroundColor(false);
		multipleRenderer.setBackgroundColor(getResources().getColor(
				R.color.white));
		multipleRenderer.setAxisTitleTextSize(16);
		multipleRenderer.setLabelsTextSize(15);
		multipleRenderer.setLegendTextSize(15);
		multipleRenderer.setMargins(new int[] { 10, 20, 10, 10 });
		multipleRenderer.setPointSize(5);
		multipleRenderer.setShowLegend(false);
		multipleRenderer.setZoomRate(-10);
		multipleRenderer.setYAxisMin(3);
		multipleRenderer.setYAxisMax(15);
		multipleRenderer.setYLabelsPadding(15);
		multipleRenderer.setXLabelsPadding(15);
		multipleRenderer
				.setMarginsColor(getResources().getColor(R.color.white));
		multipleRenderer.setAxesColor(Color.DKGRAY);
		multipleRenderer.setShowAxes(true);
		List<FuelEntry> fuelEntries = FuelEntryDAO.getInstance()
				.getEntryForListView(this);
		XYSeries fuelSeries = new XYSeries(this.getString(R.string.date));
		int i = 1;
		for (FuelEntry fe : fuelEntries) {
			// TODO Datum in double umrechnen und statt i der Series hinzufügen
			fuelSeries.add(i, fe.getUsage());
			i++;
		}
		multipleSeries = new XYMultipleSeriesDataset();
		multipleSeries.addSeries(fuelSeries);

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);
		renderer.setDisplayChartValuesDistance(10);
		renderer.setColor(Color.RED);

		multipleRenderer.addSeriesRenderer(renderer);

		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getLineChartView(this, multipleSeries,
					multipleRenderer);
			multipleRenderer.setSelectableBuffer(100);
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else
			mChartView.repaint();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}

}
