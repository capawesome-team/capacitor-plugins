package io.capawesome.capacitorjs.plugins.localization;

import android.content.Context;
import android.os.LocaleList;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.localization.classes.results.GetLocalesResult;
import io.capawesome.capacitorjs.plugins.localization.classes.results.GetSettingsResult;
import io.capawesome.capacitorjs.plugins.localization.classes.results.LocaleResult;
import io.capawesome.capacitorjs.plugins.localization.interfaces.NonEmptyResultCallback;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Localization {

    @NonNull
    private final Context context;

    public Localization(@NonNull Context context) {
        this.context = context;
    }

    public void getLocales(@NonNull NonEmptyResultCallback<GetLocalesResult> callback) {
        LocaleList localeList = LocaleList.getDefault();
        List<LocaleResult> locales = new ArrayList<>();
        for (int i = 0; i < localeList.size(); i++) {
            locales.add(createLocaleResult(localeList.get(i)));
        }
        callback.success(new GetLocalesResult(locales));
    }

    public void getSettings(@NonNull NonEmptyResultCallback<GetSettingsResult> callback) {
        String timeZone = TimeZone.getDefault().getID();
        boolean uses24HourClock = DateFormat.is24HourFormat(context);
        int firstDayOfWeek = convertToIsoDayOfWeek(Calendar.getInstance().getFirstDayOfWeek());
        callback.success(new GetSettingsResult(timeZone, uses24HourClock, firstDayOfWeek));
    }

    private int convertToIsoDayOfWeek(int calendarDayOfWeek) {
        return ((calendarDayOfWeek + 5) % 7) + 1;
    }

    @NonNull
    private LocaleResult createLocaleResult(@NonNull Locale locale) {
        String languageTag = locale.toLanguageTag();
        String languageCode = locale.getLanguage();
        String regionCode = locale.getCountry().isEmpty() ? null : locale.getCountry();
        Currency currency = resolveCurrency(locale);
        String currencyCode = currency == null ? null : currency.getCurrencyCode();
        String currencySymbol = currency == null ? null : currency.getSymbol(locale);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        String decimalSeparator = String.valueOf(symbols.getDecimalSeparator());
        String groupingSeparator = String.valueOf(symbols.getGroupingSeparator());
        String textDirection = resolveTextDirection(locale);
        String measurementSystem = resolveMeasurementSystem(regionCode);
        return new LocaleResult(
            languageTag,
            languageCode,
            regionCode,
            currencyCode,
            currencySymbol,
            decimalSeparator,
            groupingSeparator,
            textDirection,
            measurementSystem
        );
    }

    @Nullable
    private Currency resolveCurrency(@NonNull Locale locale) {
        try {
            return Currency.getInstance(locale);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    @Nullable
    private String resolveMeasurementSystem(@Nullable String regionCode) {
        if (regionCode == null) {
            return null;
        }
        switch (regionCode.toUpperCase(Locale.ROOT)) {
            case "US":
            case "LR":
            case "MM":
                return "us";
            case "GB":
                return "uk";
            default:
                return "metric";
        }
    }

    @NonNull
    private String resolveTextDirection(@NonNull Locale locale) {
        int layoutDirection = TextUtils.getLayoutDirectionFromLocale(locale);
        return layoutDirection == View.LAYOUT_DIRECTION_RTL ? "rtl" : "ltr";
    }
}
