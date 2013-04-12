package net.kami.ourfirstproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.kami.ourfirstproject.R;
import android.content.Context;

public abstract class DateUtil {

	/**
	 * Diese Methode wandelt ein übergebenes Datumsobjekt in einen String um
	 * 
	 * @param date
	 *            beliebiges Datumsobjekt
	 * @return Datum als String im Format yyyy-MM-dd
	 */
	public static String getDateAsString(final Date date) {
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
	public static Date calculateDateByYear(final Date baseDate,
			final int deltaYears) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.YEAR, deltaYears);
		return calendar.getTime();

	}

	public static String parseDateForLocale(final String dateString,
			final Context context) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		DateFormat formatterLocale = new SimpleDateFormat(
				context.getString(R.string.simple_date_format_pattern),
				Locale.getDefault());
		Date date = formatter.parse(dateString);
		return formatterLocale.format(date);
	}

	public static String convert(final int number, final int digit) {
		String buffer = String.valueOf(number);
		while (buffer.length() != digit) {
			buffer = "0" + buffer;
		}
		return buffer;
	}

}
