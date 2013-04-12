package net.kami.ourfirstproject.utils;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import net.kami.ourfirstproject.R;
import android.content.Context;

public abstract class NumberUtil {

	// formats Double according to given locale and attaches the String "liters"
	// to it
	public static StringBuffer formatDecimalNumber(final Double number,
			final Context context) {
		NumberFormat df = NumberFormat.getInstance(Locale.getDefault());
		StringBuffer usageString = df.format(number, new StringBuffer(),
				new FieldPosition(new ArrayList<Double>().indexOf(number)));
		usageString.append(" " + context.getString(R.string.string_liters));
		return usageString;
	}

}
