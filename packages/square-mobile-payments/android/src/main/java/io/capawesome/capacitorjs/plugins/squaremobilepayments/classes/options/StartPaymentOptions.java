package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;

public class StartPaymentOptions {

    @NonNull
    private final PaymentParameters paymentParameters;

    @NonNull
    private final PromptParameters promptParameters;

    public StartPaymentOptions(@NonNull PluginCall call) throws Exception {
        JSObject paymentParametersObj = call.getObject("paymentParameters");
        if (paymentParametersObj == null) {
            throw CustomExceptions.PAYMENT_PARAMETERS_MISSING;
        }
        this.paymentParameters = new PaymentParameters(paymentParametersObj);

        JSObject promptParametersObj = call.getObject("promptParameters");
        if (promptParametersObj == null) {
            throw CustomExceptions.PROMPT_PARAMETERS_MISSING;
        }
        this.promptParameters = new PromptParameters(promptParametersObj);
    }

    @NonNull
    public PaymentParameters getPaymentParameters() {
        return paymentParameters;
    }

    @NonNull
    public PromptParameters getPromptParameters() {
        return promptParameters;
    }
}
