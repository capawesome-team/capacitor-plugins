package io.capawesome.capacitorjs.plugins.superwall.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.superwall.sdk.config.options.PaywallOptions;
import com.superwall.sdk.config.options.SuperwallOptions;
import com.superwall.sdk.logger.LogLevel;
import com.superwall.sdk.logger.LogScope;
import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions;
import java.util.EnumSet;
import org.json.JSONArray;

public class ConfigureOptions {

    @NonNull
    private final String apiKey;

    @Nullable
    private final SuperwallOptions options;

    public ConfigureOptions(@NonNull PluginCall call) throws Exception {
        this.apiKey = getApiKeyFromCall(call);
        this.options = buildSuperwallOptions(getOptionsFromCall(call));
    }

    @NonNull
    public String getApiKey() {
        return apiKey;
    }

    @Nullable
    public SuperwallOptions getSuperwallOptions() {
        return options;
    }

    @Nullable
    private static SuperwallOptions buildSuperwallOptions(@Nullable JSObject optionsObject) {
        if (optionsObject == null) {
            return null;
        }

        SuperwallOptions superwallOptions = new SuperwallOptions();

        // Paywall options
        JSObject paywallsObj = optionsObject.getJSObject("paywalls");
        if (paywallsObj != null) {
            PaywallOptions paywallOptions = new PaywallOptions();

            Boolean isHapticFeedbackEnabled = paywallsObj.getBool("isHapticFeedbackEnabled");
            if (isHapticFeedbackEnabled != null) {
                paywallOptions.setHapticFeedbackEnabled(isHapticFeedbackEnabled);
            }

            Boolean shouldShowPurchaseFailureAlert = paywallsObj.getBool("shouldShowPurchaseFailureAlert");
            if (shouldShowPurchaseFailureAlert != null) {
                paywallOptions.setShouldShowPurchaseFailureAlert(shouldShowPurchaseFailureAlert);
            }

            Boolean shouldPreload = paywallsObj.getBool("shouldPreload");
            if (shouldPreload != null) {
                paywallOptions.setShouldPreload(shouldPreload);
            }

            Boolean automaticallyDismiss = paywallsObj.getBool("automaticallyDismiss");
            if (automaticallyDismiss != null) {
                paywallOptions.setAutomaticallyDismiss(automaticallyDismiss);
            }

            superwallOptions.setPaywalls(paywallOptions);
        }

        // Logging options
        JSObject loggingObj = optionsObject.getJSObject("logging");
        if (loggingObj != null) {
            SuperwallOptions.Logging logging = new SuperwallOptions.Logging();

            String levelStr = loggingObj.getString("level");
            if (levelStr != null) {
                switch (levelStr) {
                    case "DEBUG":
                        logging.setLevel(LogLevel.debug);
                        break;
                    case "INFO":
                        logging.setLevel(LogLevel.info);
                        break;
                    case "WARN":
                        logging.setLevel(LogLevel.warn);
                        break;
                    case "ERROR":
                        logging.setLevel(LogLevel.error);
                        break;
                    default:
                        logging.setLevel(LogLevel.warn);
                        break;
                }
            }

            try {
                JSONArray scopesArray = loggingObj.getJSONArray("scopes");
                if (scopesArray != null && scopesArray.length() > 0) {
                    EnumSet<LogScope> scopes = EnumSet.noneOf(LogScope.class);
                    for (int i = 0; i < scopesArray.length(); i++) {
                        String scopeStr = scopesArray.getString(i);
                        LogScope scope = null;
                        switch (scopeStr) {
                            case "ALL":
                                scope = LogScope.all;
                                break;
                            case "CONFIG":
                                scope = LogScope.configManager;
                                break;
                            case "EVENTS":
                                scope = LogScope.paywallEvents;
                                break;
                            case "PAYWALLS":
                                scope = LogScope.paywallPresentation;
                                break;
                            case "PURCHASES":
                                scope = LogScope.productsManager;
                                break;
                        }
                        if (scope != null) {
                            scopes.add(scope);
                        }
                    }
                    if (!scopes.isEmpty()) {
                        logging.setScopes(scopes);
                    }
                }
            } catch (Exception e) {
                // Skip scopes if array is invalid
            }

            superwallOptions.setLogging(logging);
        }

        // Network environment
        String networkEnvStr = optionsObject.getString("networkEnvironment");
        if (networkEnvStr != null) {
            switch (networkEnvStr) {
                case "RELEASE":
                    superwallOptions.setNetworkEnvironment(new SuperwallOptions.NetworkEnvironment.Release());
                    break;
                case "DEVELOPER":
                    superwallOptions.setNetworkEnvironment(new SuperwallOptions.NetworkEnvironment.Developer());
                    break;
                default:
                    superwallOptions.setNetworkEnvironment(new SuperwallOptions.NetworkEnvironment.Release());
                    break;
            }
        }

        // Locale identifier
        String localeIdentifier = optionsObject.getString("localeIdentifier");
        if (localeIdentifier != null) {
            superwallOptions.setLocaleIdentifier(localeIdentifier);
        }

        // Android-specific options
        Boolean shouldObservePurchases = optionsObject.getBool("shouldObservePurchases");
        if (shouldObservePurchases != null) {
            superwallOptions.setShouldObservePurchases(shouldObservePurchases);
        }

        Boolean isExternalDataCollectionEnabled = optionsObject.getBool("isExternalDataCollectionEnabled");
        if (isExternalDataCollectionEnabled != null) {
            superwallOptions.setExternalDataCollectionEnabled(isExternalDataCollectionEnabled);
        }

        return superwallOptions;
    }

    @NonNull
    private static String getApiKeyFromCall(@NonNull PluginCall call) throws Exception {
        String apiKey = call.getString("apiKey");
        if (apiKey == null) {
            throw CustomExceptions.API_KEY_MISSING;
        }
        return apiKey;
    }

    @Nullable
    private static JSObject getOptionsFromCall(@NonNull PluginCall call) {
        return call.getObject("options");
    }
}
