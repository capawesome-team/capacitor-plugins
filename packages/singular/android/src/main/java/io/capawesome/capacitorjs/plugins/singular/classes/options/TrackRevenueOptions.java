package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.SingularHelper;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;
import java.util.Map;

public class TrackRevenueOptions {

    private final double amount;

    @Nullable
    private final Map<String, Object> attributes;

    @NonNull
    private final String currency;

    @Nullable
    private final String eventName;

    public TrackRevenueOptions(@NonNull PluginCall call) throws Exception {
        Double amount = call.getDouble("amount");
        if (amount == null) {
            throw CustomExceptions.AMOUNT_MISSING;
        }
        String currency = call.getString("currency");
        if (currency == null) {
            throw CustomExceptions.CURRENCY_MISSING;
        }
        this.amount = amount;
        this.attributes = SingularHelper.createHashMapFromJSONObject(call.getObject("attributes"));
        this.currency = currency;
        this.eventName = call.getString("eventName");
    }

    public double getAmount() {
        return amount;
    }

    @Nullable
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    @Nullable
    public String getEventName() {
        return eventName;
    }
}
