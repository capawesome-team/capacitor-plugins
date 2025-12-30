package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment;

public class PaymentDidFinishEvent {

    @NonNull
    private final Payment payment;

    public PaymentDidFinishEvent(@NonNull Payment payment) {
        this.payment = payment;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("payment", payment.toJSObject());
        return result;
    }
}
