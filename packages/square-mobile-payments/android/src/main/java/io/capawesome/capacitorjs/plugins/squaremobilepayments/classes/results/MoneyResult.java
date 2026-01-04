package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;

public class MoneyResult implements Result {

    private final int amount;

    @NonNull
    private final String currency;

    public MoneyResult(int amount, @NonNull String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("amount", amount);
        result.put("currency", currency);
        return result;
    }
}
