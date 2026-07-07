package io.capawesome.capacitorjs.plugins.permissions;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.permissions.classes.CustomException;
import io.capawesome.capacitorjs.plugins.permissions.classes.options.CheckOptions;
import io.capawesome.capacitorjs.plugins.permissions.classes.options.RequestOptions;
import io.capawesome.capacitorjs.plugins.permissions.classes.results.CheckResult;
import io.capawesome.capacitorjs.plugins.permissions.classes.results.RequestResult;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.Result;

@CapacitorPlugin(
    name = "Permissions",
    permissions = {
        @Permission(
            strings = { Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN },
            alias = PermissionsPlugin.ALIAS_BLUETOOTH
        ),
        @Permission(
            strings = { Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR },
            alias = PermissionsPlugin.ALIAS_CALENDAR
        ),
        @Permission(strings = { Manifest.permission.CAMERA }, alias = PermissionsPlugin.ALIAS_CAMERA),
        @Permission(
            strings = { Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS },
            alias = PermissionsPlugin.ALIAS_CONTACTS
        ),
        @Permission(strings = { Manifest.permission.ACCESS_BACKGROUND_LOCATION }, alias = PermissionsPlugin.ALIAS_LOCATION_BACKGROUND),
        @Permission(strings = { Manifest.permission.ACCESS_COARSE_LOCATION }, alias = PermissionsPlugin.ALIAS_LOCATION_COARSE),
        @Permission(strings = { Manifest.permission.ACCESS_FINE_LOCATION }, alias = PermissionsPlugin.ALIAS_LOCATION_FINE),
        @Permission(strings = { Manifest.permission.RECORD_AUDIO }, alias = PermissionsPlugin.ALIAS_MICROPHONE),
        @Permission(strings = { Manifest.permission.ACTIVITY_RECOGNITION }, alias = PermissionsPlugin.ALIAS_MOTION),
        @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = PermissionsPlugin.ALIAS_NOTIFICATIONS),
        @Permission(strings = { Manifest.permission.READ_MEDIA_IMAGES }, alias = PermissionsPlugin.ALIAS_PHOTOS),
        @Permission(strings = { Manifest.permission.READ_EXTERNAL_STORAGE }, alias = PermissionsPlugin.ALIAS_PHOTOS_LEGACY),
        @Permission(strings = { Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED }, alias = PermissionsPlugin.ALIAS_PHOTOS_USER_SELECTED)
    }
)
public class PermissionsPlugin extends Plugin {

    public static final String ALIAS_BLUETOOTH = "bluetooth";
    public static final String ALIAS_CALENDAR = "calendar";
    public static final String ALIAS_CAMERA = "camera";
    public static final String ALIAS_CONTACTS = "contacts";
    public static final String ALIAS_LOCATION_BACKGROUND = "locationBackground";
    public static final String ALIAS_LOCATION_COARSE = "locationCoarse";
    public static final String ALIAS_LOCATION_FINE = "locationFine";
    public static final String ALIAS_MICROPHONE = "microphone";
    public static final String ALIAS_MOTION = "motion";
    public static final String ALIAS_NOTIFICATIONS = "notifications";
    public static final String ALIAS_PHOTOS = "photos";
    public static final String ALIAS_PHOTOS_LEGACY = "photosLegacy";
    public static final String ALIAS_PHOTOS_USER_SELECTED = "photosUserSelected";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PermissionsPlugin";

    private Permissions implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Permissions(this);
    }

    @PluginMethod
    public void check(PluginCall call) {
        try {
            CheckOptions options = new CheckOptions(call);
            NonEmptyResultCallback<CheckResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CheckResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.check(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void request(PluginCall call) {
        try {
            RequestOptions options = new RequestOptions(call);
            implementation.validateManifestDeclarations(options.getPermissions());
            String[] aliases = implementation.getAliasesToRequest(options.getPermissions());
            if (aliases.length > 0) {
                requestPermissionForAliases(aliases, call, "handleRequestPermissionsCallback");
            } else {
                handleRequestPermissionsCallback(call);
            }
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PermissionCallback
    private void handleBackgroundLocationPermissionCallback(PluginCall call) {
        try {
            RequestOptions options = new RequestOptions(call);
            resolveRequestCall(call, options);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PermissionCallback
    private void handleRequestPermissionsCallback(PluginCall call) {
        try {
            RequestOptions options = new RequestOptions(call);
            String[] aliases = implementation.getBackgroundLocationAliasesToRequest(options.getPermissions());
            if (aliases.length > 0) {
                requestPermissionForAliases(aliases, call, "handleBackgroundLocationPermissionCallback");
            } else {
                resolveRequestCall(call, options);
            }
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }

    private void resolveRequestCall(@NonNull PluginCall call, @NonNull RequestOptions options) {
        NonEmptyResultCallback<RequestResult> callback = new NonEmptyResultCallback<>() {
            @Override
            public void success(@NonNull RequestResult result) {
                resolveCall(call, result);
            }

            @Override
            public void error(Exception exception) {
                rejectCall(call, exception);
            }
        };
        implementation.request(options, callback);
    }
}
