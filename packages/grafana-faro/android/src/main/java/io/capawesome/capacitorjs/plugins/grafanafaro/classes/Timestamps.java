package io.capawesome.capacitorjs.plugins.grafanafaro.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class Timestamps {

    private static final ThreadLocal<SimpleDateFormat> FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            return format;
        }
    };

    public static String nowIso8601() {
        return FORMAT.get().format(new Date());
    }

    public static String iso8601(long epochMillis) {
        return FORMAT.get().format(new Date(epochMillis));
    }

    private Timestamps() {}
}
