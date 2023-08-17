package io.capawesome.capacitorjs.plugins.datetimepicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatetimePickerHelper {

    public static Date convertStringToDate(String format, String value) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(value);
    }

    public static String convertDateToString(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    @Nullable
    public static Theme convertStringToTheme(@Nullable String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "light":
                return Theme.LIGHT;
            case "dark":
                return Theme.DARK;
            case "auto":
                return Theme.AUTO;
            default:
                return null;
        }
    }

     @Nullable
    public static AndroidTimePickerMode convertStringToTimePickerMode(@Nullable String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "clock":
                return AndroidTimePickerMode.CLOCK;
            case "spinner":
                return AndroidTimePickerMode.SPINNER;
            default:
                return null;
        }
    }

    @Nullable
    public static AndroidDatePickerMode convertStringToDatePickerMode(@Nullable String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "calendar":
                return AndroidDatePickerMode.CALENDAR;
            case "spinner":
                return AndroidDatePickerMode.SPINNER;
            default:
                return null;
        }
    }

    public static Locale convertStringToLocale(String value) {
        return Locale.forLanguageTag(value);
    }

    public static boolean is24HourLocale(@NonNull Locale locale) {
        String timeString = DateFormat.getTimeInstance(DateFormat.LONG, locale).format(new Date()).toUpperCase();
        if (timeString.contains(" AM") || timeString.contains(" PM")) {
            return false;
        } else {
            return true;
        }
    }
}
