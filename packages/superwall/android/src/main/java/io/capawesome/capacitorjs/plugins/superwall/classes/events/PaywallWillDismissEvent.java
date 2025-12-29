package io.capawesome.capacitorjs.plugins.superwall.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.superwall.sdk.models.triggers.Experiment;
import com.superwall.sdk.paywall.presentation.PaywallInfo;
import org.json.JSONObject;

public class PaywallWillDismissEvent {

    @NonNull
    private final PaywallInfo paywallInfo;

    public PaywallWillDismissEvent(@NonNull PaywallInfo paywallInfo) {
        this.paywallInfo = paywallInfo;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject event = new JSObject();
        event.put("paywallInfo", convertPaywallInfoToJSObject(paywallInfo));
        return event;
    }

    @NonNull
    private JSObject convertPaywallInfoToJSObject(@NonNull PaywallInfo paywallInfo) {
        JSObject jsObject = new JSObject();
        jsObject.put("id", paywallInfo.getDatabaseId());
        jsObject.put("placement", paywallInfo.getName());

        Experiment experiment = paywallInfo.getExperiment();
        if (experiment != null) {
            jsObject.put("experiment", convertExperimentToJSObject(experiment));
        } else {
            jsObject.put("experiment", JSONObject.NULL);
        }

        JSObject data = new JSObject();
        jsObject.put("data", data);

        return jsObject;
    }

    @NonNull
    private JSObject convertExperimentToJSObject(@NonNull Experiment experiment) {
        JSObject jsObject = new JSObject();
        jsObject.put("id", experiment.getId());
        jsObject.put("variant", experiment.getVariant().getType().toString());
        return jsObject;
    }
}
