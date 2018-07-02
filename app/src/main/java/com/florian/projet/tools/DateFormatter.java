package com.florian.projet.tools;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static Date parseDate(String date, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.FRANCE);
            return formatter.parse(date);
        } catch (ParseException e) {
            Log.d("Parse Date Error", e.getMessage() + "");
            return null;
        }
    }
}
