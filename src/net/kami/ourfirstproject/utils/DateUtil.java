package net.kami.ourfirstproject.utils;

import android.content.Context;
import net.kami.ourfirstproject.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class DateUtil {

    /**
     * Diese Methode wandelt ein �bergebenes Datumsobjekt in einen String um
     *
     * @param date beliebiges Datumsobjekt
     * @return Datum als String im Format yyyy-MM-dd
     */
    public static String getDateAsString(final Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

    /**
     * Diese Methode addiert oder substrahiert eine Anzahl von Jahren von einem
     * �bergebenen Datumsobjekt.
     *
     * @param baseDate   beliebiges Datumsobjekt
     * @param deltaYears Anzahl der Jahre, die addiert oder substrahiert werden sollen
     * @return Datumsobjekt
     */
    public static Date calculateDateByYear(final Date baseDate,
                                           final int deltaYears) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.YEAR, deltaYears);
        return calendar.getTime();

    }

    // To make the date human readable
    public static String parseDateStringForLocale(final String dateString,
                                                  final Context context) {
        Date date = null;
        String dateForLocale = null;
        try {
            date = convertStringToDate(dateString, "yyyy-MM-dd");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            dateForLocale = parseDateForLocale(date, context.getString(R.string.simple_date_format_pattern), context);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateForLocale;
    }

    // To parse human readable String to DB String
    public static String parseToDbDate(final String dateString,
                                       final Context context) {
        Date date = null;
        String dateForLocale = null;
        try {
            date = convertStringToDate(dateString, context.getResources().getString(R.string.simple_date_format_pattern));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            try {
                dateForLocale = parseDateForLocale(date, "yyyy-MM-dd", context);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateForLocale;
    }

    public static String parseDateForLocale(final Date date, String pattern,
                                            final Context context) throws ParseException {
        DateFormat formatterLocale = new SimpleDateFormat(
                pattern,
                Locale.getDefault());
        return formatterLocale.format(date);
    }

    public static Date convertStringToDate(final String dateString, final String pattern) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(pattern,
                Locale.getDefault());
        Date date = formatter.parse(dateString);
        return date;
    }

    // Diese Methode f�gt bei einer einstelligen Nummer eine f�hrende Null hinzu
    // Dies dient dazu, einstellige Monatszahlen (z. B. Monat 1 f�r Januar) mit
    // f�hrender Null zu speichern
    public static String convert(final int number, final int digit) {
        String buffer = String.valueOf(number);
        while (buffer.length() != digit) {
            buffer = "0" + buffer;
        }
        return buffer;
    }

    public static Date parseTimeString(final String time) {
        return null;
    }

}
