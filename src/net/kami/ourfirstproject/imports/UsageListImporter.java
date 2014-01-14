package net.kami.ourfirstproject.imports;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import net.kami.ourfirstproject.R;
import net.kami.ourfirstproject.activities.UsageList;
import net.kami.ourfirstproject.datahandling.DBHelper;
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

        Serializer deserializer = new Persister();

        File file = new File(filename);
        FuelEntryList read = null;
        try {
            read = deserializer.read(FuelEntryList.class, file);
            if (FuelEntryDAO.getInstance().getUsageEntryCount(context)> 0) {
                showAlertDialog(context, read);
            } else {
                importEntries(context, read , false);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error reading XML file\n" + e);
            Toast.makeText(context, context.getString(R.string.import_failure),
                    Toast.LENGTH_LONG).show();
        }
    }

    private static void importEntries(Context context, FuelEntryList read, boolean deleteBeforeImport) {
        if (read != null) {
            List<FuelEntry> fuelEntries = read.getFuelEntries();

            for (FuelEntry fuelEntry : fuelEntries) {
                fuelEntry.setDate(DateUtil.parseToDbDate(fuelEntry.getDate(), context));
            }

            if (deleteBeforeImport) {
                new DBHelper(context).onDelete(context);
                Log.v(TAG, "Usage entries deleted before import.");
            }

            Log.v(TAG, "Importing " + read.getFuelEntries().size() + " entries from file.");

            FuelEntryDAO.getInstance().saveEntry(fuelEntries, context);

            ((UsageList) context).updateList();

            Resources res = context.getResources();
            Toast.makeText(context, "" + String.format(res.getString(R.string.import_success), fuelEntries.size()),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Keine Daten eingelesen.", Toast.LENGTH_LONG).show();
        }
    }

    private static void showAlertDialog(final Context context, final FuelEntryList read) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.title_import);

        // Setting Dialog Message
        alertDialog.setMessage(R.string.delete_existing_entries);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(R.string.button_yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        importEntries(context, read, true);
                    }
                });

        // Setting Negative "Abort" Button
        alertDialog.setNegativeButton(R.string.button_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        Toast.makeText(context,"Import abgebrochen.", Toast.LENGTH_LONG);
                    }
                });

        // Setting Neutral "No" Button
        alertDialog.setNeutralButton(R.string.button_no,
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        importEntries(context, read, false);
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

}
