package net.kami.ourfirstproject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class FuelFacade {

	// der Verbrauch wird ausgerechnet, auf zwei Nachkommastellen gerundet
	// und in einen String zur Übergabe an die nächste Activity eingefügt
	public static BigDecimal calculateFuel(double liters, double kilometers) {
		double usage = (liters / kilometers) * 100;
		BigDecimal usageRounded = new BigDecimal(usage);
		usageRounded = usageRounded.setScale(2, BigDecimal.ROUND_HALF_UP);
		return usageRounded;
	}

	// TODO: Verbrauchsverlauf der letzten 12 Monate grafisch darstellen (über
	// Kontextmenü zugänglich)
	// die Methode calculateAverageUsage berechnet den Durchschnittsverbrauch
	// aus den vorhergegangenen Tankvorgängen. Dabei werden die letzten 12
	// Monate berücksichtigt.
	public static double calculateAverageUsage(Context context) {
		// vorhergehende Verbräuche werden aus der DB abgerufen und in einer
		// ArrayList gespeichert
		List<Double> usageList = new ArrayList<Double>();
		DBHelper dbh = new DBHelper(context);
		usageList = dbh.getUsageList();
		double averageUsage = 0.00;
		double usageSum = 0.00;
		double averageUsageDouble = 0.00;

		// um den Teiler zu ermitteln, wird die Länge der ArrayList ermittelt
		int size = usageList.size();
		// Size darf nicht 0 sein, da sonst später durch 0 geteilt würde und es
		// dadurch zu einem Fehler kommen würde
		if (size != 0) {

			// iterieren durch die ArrayList und Aufsummieren der
			// Verbrauchsdaten
			for (double usageEntry : usageList) {
				usageSum = usageSum + usageEntry;
			}

			// Berechnen des Durchschnittverbrauchs, Runden auf zwei
			// Nachkommastellen und parsen in einen String, um es an die nächste
			// Activity als Message übergeben zu können
			averageUsage = usageSum / size;
			BigDecimal averageUsageRounded = new BigDecimal(averageUsage);
			averageUsageRounded = averageUsageRounded.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			averageUsageDouble = averageUsageRounded.doubleValue();
		}

		return averageUsageDouble;
	}

	// Methode gibt true zurück, wenn der aktuelle Verbrauch niedriger ist als
	// der durchschnittliche Verbrauch
	public static boolean isCurrentUsageSmaller(double currentUsage,
			double averageUsage) {
		return currentUsage <= averageUsage;

	}
}
