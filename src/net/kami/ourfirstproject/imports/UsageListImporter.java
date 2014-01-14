package net.kami.ourfirstproject.imports;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.datahandling.FuelEntry;
import net.kami.ourfirstproject.datahandling.FuelEntryDAO;
import net.kami.ourfirstproject.datahandling.FuelEntryList;
import net.kami.ourfirstproject.utils.DateUtil;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.List;

public abstract class UsageListImporter {

    private static final String TAG = UsageListImporter.class.getSimpleName();

    public static void importUsageList(final Context context, final String filename) {
        //1. Abfrage, ob importiert werden soll; Hinweis, dass DB-Einträge gelöscht werden
        //2. Filechooser
        //3. Validierung der Datei; falls okay und Inhalte vorhanden sind -> DB zurücksetzen und Daten importieren

        //TODO Abfrage, ob importiert werden soll; Hinweis, dass DB-Einträge gelöscht werden
        //TODO Validierung der Datei; falls okay und Inhalte vorhanden sind -> DB zurücksetzen und Daten importieren

        Serializer deserializer = new Persister();

        File file = new File(filename);
        FuelEntryList read = null;
        try {
            read = deserializer.read(FuelEntryList.class, file);
            Log.v(TAG, "" + read.getFuelEntries().size() + " entries read from xml-file.");

        } catch (Exception e) {
            Log.e(TAG, "Error reading XML file\n" + e);
            Toast.makeText(context, context.getString(R.string.import_failure),
                    Toast.LENGTH_LONG).show();
        }

        if (read != null) {
            List<FuelEntry> fuelEntries = read.getFuelEntries();

            for (FuelEntry fuelEntry : fuelEntries) {
                fuelEntry.setDate(DateUtil.parseToDbDate(fuelEntry.getDate(), context));
            }

            FuelEntryDAO.getInstance().saveEntry(fuelEntries, context);

            Resources res = context.getResources();
            Toast.makeText(context, "" + String.format(res.getString(R.string.import_success), fuelEntries.size()),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Tja...", Toast.LENGTH_LONG).show();
        }
    }

}
