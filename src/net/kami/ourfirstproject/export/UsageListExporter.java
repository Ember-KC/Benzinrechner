package net.kami.ourfirstproject.export;

import java.io.File;
import java.util.List;

import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

public class UsageListExporter {

	private static final String TAG = UsageListExporter.class.getSimpleName();

	public static void exportUsageList(Context context) {

		Serializer serializer = new Persister();
		List<FuelEntry> fuelEntries = FuelEntryDAO.getInstance()
				.getEntryForListView(context);
		File result = new File("sdcard/download/example.xml");

		try {
			serializer.write(fuelEntries, result);
		} catch (Exception e) {
			Log.e(TAG, "Error writing XML file");
		}
	}
}
