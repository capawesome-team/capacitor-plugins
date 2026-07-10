package io.capawesome.capacitorjs.plugins.localization.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.localization.interfaces.Result;
import org.json.JSONObject;

public class LocaleResult implements Result {

    @Nullable
    private final String currencyCode;

    @Nullable
    private final String currencySymbol;

    @Nullable
    private final String decimalSeparator;

    @Nullable
    private final String groupingSeparator;

    @NonNull
    private final String languageCode;

    @NonNull
    private final String languageTag;

    @Nullable
    private final String measurementSystem;

    @Nullable
    private final String regionCode;

    @NonNull
    private final String textDirection;

    public LocaleResult(
        @NonNull String languageTag,
        @NonNull String languageCode,
        @Nullable String regionCode,
        @Nullable String currencyCode,
        @Nullable String currencySymbol,
        @Nullable String decimalSeparator,
        @Nullable String groupingSeparator,
        @NonNull String textDirection,
        @Nullable String measurementSystem
    ) {
        this.languageTag = languageTag;
        this.languageCode = languageCode;
        this.regionCode = regionCode;
        this.currencyCode = currencyCode;
        this.currencySymbol = currencySymbol;
        this.decimalSeparator = decimalSeparator;
        this.groupingSeparator = groupingSeparator;
        this.textDirection = textDirection;
        this.measurementSystem = measurementSystem;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("currencyCode", currencyCode == null ? JSONObject.NULL : currencyCode);
        result.put("currencySymbol", currencySymbol == null ? JSONObject.NULL : currencySymbol);
        result.put("decimalSeparator", decimalSeparator == null ? JSONObject.NULL : decimalSeparator);
        result.put("groupingSeparator", groupingSeparator == null ? JSONObject.NULL : groupingSeparator);
        result.put("languageCode", languageCode);
        result.put("languageTag", languageTag);
        result.put("measurementSystem", measurementSystem == null ? JSONObject.NULL : measurementSystem);
        result.put("regionCode", regionCode == null ? JSONObject.NULL : regionCode);
        result.put("textDirection", textDirection);
        return result;
    }
}
