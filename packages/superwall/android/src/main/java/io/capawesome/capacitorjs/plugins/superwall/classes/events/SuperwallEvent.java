package io.capawesome.capacitorjs.plugins.superwall.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.superwall.sdk.analytics.superwall.SuperwallEventInfo;
import java.util.Map;

public class SuperwallEvent {

    @NonNull
    private final SuperwallEventInfo eventInfo;

    public SuperwallEvent(@NonNull SuperwallEventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject event = new JSObject();
        event.put("type", convertEventType(eventInfo.getEvent()));
        event.put("data", convertEventData(eventInfo));
        return event;
    }

    @NonNull
    private String convertEventType(@NonNull Object event) {
        String simpleName = event.getClass().getSimpleName();
        switch (simpleName) {
            case "FirstSeen":
                return "FIRST_SEEN";
            case "AppOpen":
                return "APP_OPEN";
            case "AppLaunch":
                return "APP_LAUNCH";
            case "AppClose":
                return "APP_CLOSE";
            case "SessionStart":
                return "SESSION_START";
            case "DeepLink":
                return "DEEP_LINK";
            case "TriggerFire":
                return "TRIGGER_FIRE";
            case "PaywallOpen":
                return "PAYWALL_OPEN";
            case "PaywallClose":
                return "PAYWALL_CLOSE";
            case "PaywallDecline":
                return "PAYWALL_DECLINE";
            case "TransactionStart":
                return "TRANSACTION_START";
            case "TransactionComplete":
                return "TRANSACTION_COMPLETE";
            case "TransactionFail":
                return "TRANSACTION_FAIL";
            case "TransactionAbandon":
                return "TRANSACTION_ABANDON";
            case "TransactionRestore":
                return "TRANSACTION_RESTORE";
            case "TransactionTimeout":
                return "TRANSACTION_TIMEOUT";
            case "SubscriptionStart":
                return "SUBSCRIPTION_START";
            case "FreeTrialStart":
                return "FREE_TRIAL_START";
            case "SubscriptionStatusDidChange":
                return "SUBSCRIPTION_STATUS_DID_CHANGE";
            default:
                return "UNKNOWN";
        }
    }

    @NonNull
    private JSObject convertEventData(@NonNull SuperwallEventInfo eventInfo) {
        JSObject data = new JSObject();
        for (Map.Entry<String, Object> entry : eventInfo.getParams().entrySet()) {
            data.put(entry.getKey(), entry.getValue());
        }
        return data;
    }
}
