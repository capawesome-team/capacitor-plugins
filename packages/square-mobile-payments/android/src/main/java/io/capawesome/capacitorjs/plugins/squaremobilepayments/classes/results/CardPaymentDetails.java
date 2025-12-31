package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import org.json.JSONObject;

public class CardPaymentDetails implements Result {

    @NonNull
    private final Card card;

    @NonNull
    private final String entryMethod;

    public CardPaymentDetails(
        @NonNull Card card,
        @NonNull String entryMethod
    ) {
        this.card = card;
        this.entryMethod = entryMethod;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("card", card.toJSObject());
        result.put("entryMethod", entryMethod);
        return result;
    }
}
