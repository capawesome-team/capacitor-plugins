package io.capawesome.capacitorjs.plugins.singular;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomException;
import io.capawesome.capacitorjs.plugins.singular.classes.events.DeviceAttributionInfoReceivedEvent;
import io.capawesome.capacitorjs.plugins.singular.classes.events.SingularLinkResolvedEvent;
import io.capawesome.capacitorjs.plugins.singular.classes.options.CreateReferrerShortLinkOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetCustomUserIdOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetDeviceTokenOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetGlobalPropertyOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetLimitAdvertisingIdentifiersOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetLimitDataSharingOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.TrackAdRevenueOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.TrackEventOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.TrackRevenueOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.UnsetGlobalPropertyOptions;
import io.capawesome.capacitorjs.plugins.singular.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.singular.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;

@CapacitorPlugin(name = "Singular")
public class SingularPlugin extends Plugin {

    public static final String EVENT_DEVICE_ATTRIBUTION_INFO_RECEIVED = "deviceAttributionInfoReceived";
    public static final String EVENT_SINGULAR_LINK_RESOLVED = "singularLinkResolved";
    public static final String TAG = "SingularPlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private Singular implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Singular(this);
    }

    @PluginMethod
    public void clearGlobalProperties(PluginCall call) {
        try {
            implementation.clearGlobalProperties(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void createReferrerShortLink(PluginCall call) {
        try {
            CreateReferrerShortLinkOptions options = new CreateReferrerShortLinkOptions(call);
            implementation.createReferrerShortLink(options, createNonEmptyResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getGlobalProperties(PluginCall call) {
        try {
            implementation.getGlobalProperties(createNonEmptyResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getLimitDataSharing(PluginCall call) {
        try {
            implementation.getLimitDataSharing(createNonEmptyResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
            implementation.initialize(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAllTrackingStopped(PluginCall call) {
        try {
            implementation.isAllTrackingStopped(createNonEmptyResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyDeviceAttributionInfoReceivedListeners(@NonNull DeviceAttributionInfoReceivedEvent event) {
        notifyListeners(EVENT_DEVICE_ATTRIBUTION_INFO_RECEIVED, event.toJSObject());
    }

    public void notifySingularLinkResolvedListeners(@NonNull SingularLinkResolvedEvent event) {
        notifyListeners(EVENT_SINGULAR_LINK_RESOLVED, event.toJSObject());
    }

    @PluginMethod
    public void resumeAllTracking(PluginCall call) {
        try {
            implementation.resumeAllTracking(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setCustomUserId(PluginCall call) {
        try {
            SetCustomUserIdOptions options = new SetCustomUserIdOptions(call);
            implementation.setCustomUserId(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setDeviceToken(PluginCall call) {
        try {
            SetDeviceTokenOptions options = new SetDeviceTokenOptions(call);
            implementation.setDeviceToken(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setGlobalProperty(PluginCall call) {
        try {
            SetGlobalPropertyOptions options = new SetGlobalPropertyOptions(call);
            implementation.setGlobalProperty(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setLimitAdvertisingIdentifiers(PluginCall call) {
        try {
            SetLimitAdvertisingIdentifiersOptions options = new SetLimitAdvertisingIdentifiersOptions(call);
            implementation.setLimitAdvertisingIdentifiers(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setLimitDataSharing(PluginCall call) {
        try {
            SetLimitDataSharingOptions options = new SetLimitDataSharingOptions(call);
            implementation.setLimitDataSharing(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void skanGetConversionValue(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void skanRegisterAppForAdNetworkAttribution(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void skanUpdateConversionValue(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void stopAllTracking(PluginCall call) {
        try {
            implementation.stopAllTracking(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void trackAdRevenue(PluginCall call) {
        try {
            TrackAdRevenueOptions options = new TrackAdRevenueOptions(call);
            implementation.trackAdRevenue(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void trackEvent(PluginCall call) {
        try {
            TrackEventOptions options = new TrackEventOptions(call);
            implementation.trackEvent(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void trackRevenue(PluginCall call) {
        try {
            TrackRevenueOptions options = new TrackRevenueOptions(call);
            implementation.trackRevenue(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void trackingOptIn(PluginCall call) {
        try {
            implementation.trackingOptIn(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void trackingUnder13(PluginCall call) {
        try {
            implementation.trackingUnder13(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unsetCustomUserId(PluginCall call) {
        try {
            implementation.unsetCustomUserId(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unsetGlobalProperty(PluginCall call) {
        try {
            UnsetGlobalPropertyOptions options = new UnsetGlobalPropertyOptions(call);
            implementation.unsetGlobalProperty(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnNewIntent(Intent intent) {
        super.handleOnNewIntent(intent);
        implementation.handleNewIntent(intent);
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    @NonNull
    private <T extends Result> NonEmptyResultCallback<T> createNonEmptyResultCallback(@NonNull PluginCall call) {
        return new NonEmptyResultCallback<T>() {
            @Override
            public void success(@NonNull T result) {
                resolveCall(call, result);
            }

            @Override
            public void error(Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
