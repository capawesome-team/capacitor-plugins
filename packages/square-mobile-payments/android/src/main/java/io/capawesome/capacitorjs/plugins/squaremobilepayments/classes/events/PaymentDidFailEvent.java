package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment;
import org.json.JSONObject;

public class PaymentDidFailEvent {

    @Nullable
    private final Payment payment;

    @Nullable
    private final String code;

    @NonNull
    private final String message;

    public PaymentDidFailEvent(@Nullable Payment payment, @Nullable String code, @NonNull String message) {
        this.payment = payment;
        this.code = code;
        this.message = message;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("payment", payment == null ? JSONObject.NULL : payment.toJSObject());
        result.put("code", code == null ? JSONObject.NULL : code);
        result.put("message", message);
        return result;
    }
}
