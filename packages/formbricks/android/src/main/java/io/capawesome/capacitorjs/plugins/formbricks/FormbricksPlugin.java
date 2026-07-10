package io.capawesome.capacitorjs.plugins.formbricks;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetAttributeOptions;
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetAttributesOptions;
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetLanguageOptions;
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetUserIdOptions;
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetupOptions;
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.TrackOptions;

@CapacitorPlugin(name = "Formbricks")
public class FormbricksPlugin extends Plugin {

    public static final String TAG = "Formbricks";

    public static final String ERROR_ACTION_MISSING = "action must be provided.";
    public static final String ERROR_APP_URL_MISSING = "appUrl must be provided.";
    public static final String ERROR_ATTRIBUTES_MISSING = "attributes must be provided.";
    public static final String ERROR_ENVIRONMENT_ID_MISSING = "environmentId must be provided.";
    public static final String ERROR_KEY_MISSING = "key must be provided.";
    public static final String ERROR_LANGUAGE_MISSING = "language must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String ERROR_USER_ID_MISSING = "userId must be provided.";
    public static final String ERROR_VALUE_MISSING = "value must be provided.";

    private Formbricks implementation;

    @Override
    public void load() {
        implementation = new Formbricks(this);
    }

    @PluginMethod
    public void logout(PluginCall call) {
        try {
            implementation.logout();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setAttribute(PluginCall call) {
        try {
            SetAttributeOptions options = new SetAttributeOptions(call);
            implementation.setAttribute(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setAttributes(PluginCall call) {
        try {
            SetAttributesOptions options = new SetAttributesOptions(call);
            implementation.setAttributes(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setLanguage(PluginCall call) {
        try {
            SetLanguageOptions options = new SetLanguageOptions(call);
            implementation.setLanguage(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUserId(PluginCall call) {
        try {
            SetUserIdOptions options = new SetUserIdOptions(call);
            implementation.setUserId(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setup(PluginCall call) {
        try {
            SetupOptions options = new SetupOptions(call);
            implementation.setup(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void track(PluginCall call) {
        try {
            TrackOptions options = new TrackOptions(call);
            implementation.track(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        message = (message != null) ? message : ERROR_UNKNOWN_ERROR;
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
