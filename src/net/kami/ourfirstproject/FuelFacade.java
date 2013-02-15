package net.kami.ourfirstproject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class FuelFacade {

	// der Verbrauch wird ausgerechnet, auf zwei Nachkommastellen gerundet
	// und in einen String zur �bergabe an die n�chste Activity eingef�gt
	public static BigDecimal calculateFuel(double liters, double kilometers) {
		double usage = (liters / kilometers) * 100;
		BigDecimal usageRounded = new BigDecimal(usage);
		usageRounded = usageRounded.setScale(2, BigDecimal.ROUND_HALF_UP);
		return usageRounded;
	}

	// TODO: Verbrauchsverlauf der letzten 12 Monate grafisch darstellen (�ber
	// Kontextmen� zug�nglich)
	// die Methode calculateAverageUsage berechnet den Durchschnittsverbrauch
	// aus den vorhergegangenen Tankvorg�ngen. Dabei werden die letzten 12
	// Monate ber�cksichtigt.
	public static double calculateAverageUsage(Context context) {
		// vorhergehende Verbr�uche werden aus der DB abgerufen und in einer
		// ArrayList gespeichert
		List<Double> usageList = new ArrayList<Double>();
		DBHelper dbh = new DBHelper(context);
		usageList = dbh.getUsageList();
		double averageUsage = 0.00;
		double usageSum = 0.00;
		double averageUsageDouble = 0.00;

		// um den Teiler zu ermitteln, wird die L�nge der ArrayList ermittelt
		int size = usageList.size();
		// Size darf nicht 0 sein, da sonst sp�ter durch 0 geteilt w�rde und es
		// dadurch zu einem Fehler kommen w�rde
		if (size != 0) {

			// iterieren durch die ArrayList und Aufsummieren der
			// Verbrauchsdaten
			for (double usageEntry : usageList) {
				usageSum = usageSum + usageEntry;
			}

			// Berechnen des Durchschnittverbrauchs, Runden auf zwei
			// Nachkommastellen und parsen in einen String, um es an die n�chste
			// Activity als Message �bergeben zu k�nnen
			averageUsage = usageSum / size;
			BigDecimal averageUsageRounded = new BigDecimal(averageUsage);
			averageUsageRounded = averageUsageRounded.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			averageUsageDouble = averageUsageRounded.doubleValue();
		}

		return averageUsageDouble;
	}

	// Methode gibt true zur�ck, wenn der aktuelle Verbrauch niedriger ist als
	// der durchschnittliche Verbrauch
	public static boolean isCurrentUsageSmaller(double currentUsage,
			double averageUsage) {
		return currentUsage <= averageUsage;

	}
}
