package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import org.json.JSONObject;

public class Card implements Result {

    @NonNull
    private final String brand;

    @NonNull
    private final String lastFourDigits;

    @Nullable
    private final String cardholderName;

    @Nullable
    private final Integer expirationMonth;

    @Nullable
    private final Integer expirationYear;

    public Card(
        @NonNull String brand,
        @NonNull String lastFourDigits,
        @Nullable String cardholderName,
        @Nullable Integer expirationMonth,
        @Nullable Integer expirationYear
    ) {
        this.brand = brand;
        this.lastFourDigits = lastFourDigits;
        this.cardholderName = cardholderName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("brand", brand);
        result.put("lastFourDigits", lastFourDigits);
        result.put("cardholderName", cardholderName == null ? JSONObject.NULL : cardholderName);
        result.put("expirationMonth", expirationMonth == null ? JSONObject.NULL : expirationMonth);
        result.put("expirationYear", expirationYear == null ? JSONObject.NULL : expirationYear);
        return result;
    }
}
