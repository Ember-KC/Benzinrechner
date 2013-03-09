package net.kami.ourfirstproject.export;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.datahandling.FuelEntryList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class UsageListExporter {

	private static final String TAG = UsageListExporter.class.getSimpleName();

	public static void exportUsageList(Context context) {

		Serializer serializer = new Persister();
		FuelEntryList fel = new FuelEntryList();
		fel.fuelEntries = FuelEntryDAO.getInstance().getEntryForListView(
				context);
		DateFormat formatterLocale = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String dateString = formatterLocale.format(new Date());
		File file = new File("sdcard/download/fuellist-" + dateString + ".xml");

		try {
			serializer.write(fel, file);
			Toast.makeText(context,
					context.getString(R.string.list_exported) + file,
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e(TAG, "Error writing XML file");
		}
	}
}
