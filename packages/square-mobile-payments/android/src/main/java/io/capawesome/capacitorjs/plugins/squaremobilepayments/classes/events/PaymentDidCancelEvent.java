package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment;
import org.json.JSONObject;

public class PaymentDidCancelEvent {

    @Nullable
    private final Payment payment;

    public PaymentDidCancelEvent(@Nullable Payment payment) {
        this.payment = payment;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("payment", payment == null ? JSONObject.NULL : payment.toJSObject());
        return result;
    }
}
