package net.kami.ourfirstproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	/**
	 * Diese Methode wandelt ein übergebenes Datumsobjekt in einen String um
	 * 
	 * @param date
	 *            beliebiges Datumsobjekt
	 * @return Datum als String im Format yyyy-MM-dd
	 */
	public static String getDateAsString(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		String dateFormatted = formatter.format(date);
		return dateFormatted;
	}

	/**
	 * Diese Methode addiert oder substrahiert eine Anzahl von Jahren von einem
	 * übergebenen Datumsobjekt.
	 * 
	 * @param baseDate
	 *            beliebiges Datumsobjekt
	 * @param deltaYears
	 *            Anzahl der Jahre, die addiert oder substrahiert werden sollen
	 * @return Datumsobjekt
	 */
	public static Date calculateDateByYear(Date baseDate, int deltaYears) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.YEAR, deltaYears);
		return calendar.getTime();

	}

	// TODO: formatiert aktuell bei Locale DE nach dem Pattern dd.mm.yy hh:mm,
	// muss geändert werden in dd.mm.yyyy
	public static String parseDateForLocale(String dateString)
			throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatterLocale = new SimpleDateFormat();
		Date date = formatter.parse(dateString);
		return formatterLocale.format(date);
	}

	public static String convert(int number, int digit) {
		String buffer = String.valueOf(number);
		while (buffer.length() != digit)
			buffer = "0" + buffer;
		return buffer;
	}

}
