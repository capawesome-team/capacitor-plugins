package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class Money {

    private final int amount;

    @NonNull
    private final String currency;

    public Money(@NonNull JSObject obj) {
        Integer amountValue = obj.getInteger("amount");
        this.amount = amountValue != null ? amountValue : 0;

        String currencyValue = obj.getString("currency");
        this.currency = currencyValue != null ? currencyValue : "USD";
    }

    public int getAmount() {
        return amount;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }
}
