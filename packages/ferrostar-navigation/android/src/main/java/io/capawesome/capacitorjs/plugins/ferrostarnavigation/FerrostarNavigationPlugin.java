package io.capawesome.capacitorjs.plugins.ferrostarnavigation;

import android.Manifest;
import android.os.Build;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.CustomException;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.events.NavigationStateChangedEvent;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.events.RouteDeviationDetectedEvent;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options.ReplaceRouteOptions;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options.StartNavigationOptions;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.interfaces.EmptyCallback;

@CapacitorPlugin(
    name = "FerrostarNavigation",
    permissions = {
        @Permission(
            alias = FerrostarNavigationPlugin.PERMISSION_LOCATION,
            strings = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION }
        ),
        @Permission(alias = FerrostarNavigationPlugin.PERMISSION_NOTIFICATIONS, strings = { Manifest.permission.POST_NOTIFICATIONS })
    }
)
public class FerrostarNavigationPlugin extends Plugin {

    public static final String EVENT_NAVIGATION_COMPLETED = "navigationCompleted";
    public static final String EVENT_NAVIGATION_STATE_CHANGED = "navigationStateChanged";
    public static final String EVENT_ROUTE_DEVIATION_DETECTED = "routeDeviationDetected";
    public static final String PERMISSION_LOCATION = "location";
    public static final String PERMISSION_NOTIFICATIONS = "notifications";
    public static final String TAG = "FerrostarNavigation";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private FerrostarNavigation implementation;

    @Override
    public void load() {
        implementation = new FerrostarNavigation(this);
    }

    @PluginMethod
    public void muteVoice(PluginCall call) {
        try {
            implementation.muteVoice(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyNavigationCompletedListeners() {
        notifyListeners(EVENT_NAVIGATION_COMPLETED, new JSObject());
    }

    public void notifyNavigationStateChangedListeners(@NonNull NavigationStateChangedEvent event) {
        notifyListeners(EVENT_NAVIGATION_STATE_CHANGED, event.toJSObject());
    }

    public void notifyRouteDeviationDetectedListeners(@NonNull RouteDeviationDetectedEvent event) {
        notifyListeners(EVENT_ROUTE_DEVIATION_DETECTED, event.toJSObject());
    }

    @PluginMethod
    public void replaceRoute(PluginCall call) {
        try {
            ReplaceRouteOptions options = new ReplaceRouteOptions(call);
            implementation.replaceRoute(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startNavigation(PluginCall call) {
        try {
            StartNavigationOptions options = new StartNavigationOptions(call);
            if (isLocationPermissionMissing(options)) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, "handleLocationPermissionCallback");
                return;
            }
            if (isNotificationsPermissionMissing(options)) {
                requestPermissionForAlias(PERMISSION_NOTIFICATIONS, call, "handleNotificationsPermissionCallback");
                return;
            }
            implementation.startNavigation(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopNavigation(PluginCall call) {
        try {
            implementation.stopNavigation(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unmuteVoice(PluginCall call) {
        try {
            implementation.unmuteVoice(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(@NonNull Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    @PermissionCallback
    private void handleLocationPermissionCallback(PluginCall call) {
        if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
            rejectCall(call, CustomExceptions.LOCATION_PERMISSION_DENIED);
            return;
        }
        startNavigation(call);
    }

    /**
     * The navigation also runs without the notifications permission, so a denial does not reject the call.
     */
    @PermissionCallback
    private void handleNotificationsPermissionCallback(PluginCall call) {
        try {
            StartNavigationOptions options = new StartNavigationOptions(call);
            implementation.startNavigation(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    /**
     * The foreground service has the location type, so it also needs the location permission when the location is simulated.
     */
    private boolean isLocationPermissionMissing(@NonNull StartNavigationOptions options) {
        if (options.getSimulateLocation() && !options.getContinueInBackground()) {
            return false;
        }
        return getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED;
    }

    private boolean isNotificationsPermissionMissing(@NonNull StartNavigationOptions options) {
        if (!options.getContinueInBackground() || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return false;
        }
        return getPermissionState(PERMISSION_NOTIFICATIONS) != PermissionState.GRANTED;
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException ? ((CustomException) exception).getCode() : null;
        if (code == null) {
            call.reject(message);
        } else {
            call.reject(message, code);
        }
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }
}
