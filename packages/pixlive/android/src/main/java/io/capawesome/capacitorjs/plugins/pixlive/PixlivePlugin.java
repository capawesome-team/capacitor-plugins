package io.capawesome.capacitorjs.plugins.pixlive;

import android.Manifest;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.pixlive.classes.options.*;
import io.capawesome.capacitorjs.plugins.pixlive.classes.results.*;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.*;
import java.util.ArrayList;
import java.util.List;

@CapacitorPlugin(
    name = "Pixlive",
    permissions = {
        @Permission(strings = { Manifest.permission.BLUETOOTH }, alias = PixlivePlugin.PERMISSION_BLUETOOTH),
        @Permission(strings = { Manifest.permission.BLUETOOTH_CONNECT }, alias = PixlivePlugin.PERMISSION_BLUETOOTH_CONNECT),
        @Permission(strings = { Manifest.permission.BLUETOOTH_SCAN }, alias = PixlivePlugin.PERMISSION_BLUETOOTH_SCAN),
        @Permission(strings = { Manifest.permission.CAMERA }, alias = PixlivePlugin.PERMISSION_CAMERA),
        @Permission(strings = { Manifest.permission.ACCESS_FINE_LOCATION }, alias = PixlivePlugin.PERMISSION_LOCATION),
        @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = PixlivePlugin.PERMISSION_NOTIFICATIONS)
    }
)
public class PixlivePlugin extends Plugin {

    public static final String TAG = "Pixlive";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    static final String PERMISSION_BLUETOOTH = "bluetooth";
    static final String PERMISSION_BLUETOOTH_CONNECT = "bluetoothConnect";
    static final String PERMISSION_BLUETOOTH_SCAN = "bluetoothScan";
    static final String PERMISSION_CAMERA = "camera";
    static final String PERMISSION_LOCATION = "location";
    static final String PERMISSION_NOTIFICATIONS = "notifications";

    private static final String CALLBACK_PERMISSION = "handlePermissionCallback";

    private Pixlive implementation;

    @Override
    public void load() {
        implementation = new Pixlive(this);
    }

    @Override
    protected void handleOnPause() {
        super.handleOnPause();
        if (implementation != null) {
            implementation.handleOnPause();
        }
    }

    @Override
    protected void handleOnResume() {
        super.handleOnResume();
        if (implementation != null) {
            implementation.handleOnResume();
        }
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.handleOnDestroy();
        }
        super.handleOnDestroy();
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.initialize(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    @Override
    public void checkPermissions(PluginCall call) {
        super.checkPermissions(call);
    }

    @PluginMethod
    @Override
    public void requestPermissions(PluginCall call) {
        try {
            List<String> aliases = getPermissionAliasesFromCall(call);
            String[] aliasArray = aliases.toArray(new String[0]);
            requestPermissionForAliases(aliasArray, call, CALLBACK_PERMISSION);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PermissionCallback
    private void handlePermissionCallback(PluginCall call) {
        checkPermissions(call);
    }

    @PluginMethod
    public void synchronize(PluginCall call) {
        try {
            SynchronizeOptions options = new SynchronizeOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.synchronize(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void synchronizeWithToursAndContexts(PluginCall call) {
        try {
            SynchronizeWithToursAndContextsOptions options = new SynchronizeWithToursAndContextsOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.synchronizeWithToursAndContexts(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateTagMapping(PluginCall call) {
        try {
            UpdateTagMappingOptions options = new UpdateTagMappingOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.updateTagMapping(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void enableContextsWithTags(PluginCall call) {
        try {
            EnableContextsWithTagsOptions options = new EnableContextsWithTagsOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.enableContextsWithTags(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getContexts(PluginCall call) {
        try {
            NonEmptyResultCallback<GetContextsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetContextsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getContexts(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getContext(PluginCall call) {
        try {
            GetContextOptions options = new GetContextOptions(call);
            NonEmptyResultCallback<GetContextResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetContextResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getContext(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void activateContext(PluginCall call) {
        try {
            ActivateContextOptions options = new ActivateContextOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.activateContext(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopContext(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.stopContext(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getNearbyGPSPoints(PluginCall call) {
        try {
            GetNearbyGPSPointsOptions options = new GetNearbyGPSPointsOptions(call);
            NonEmptyResultCallback<GetNearbyGPSPointsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetNearbyGPSPointsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getNearbyGPSPoints(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getGPSPointsInBoundingBox(PluginCall call) {
        try {
            GetGPSPointsInBoundingBoxOptions options = new GetGPSPointsInBoundingBoxOptions(call);
            NonEmptyResultCallback<GetGPSPointsInBoundingBoxResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetGPSPointsInBoundingBoxResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getGPSPointsInBoundingBox(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getNearbyBeacons(PluginCall call) {
        try {
            NonEmptyResultCallback<GetNearbyBeaconsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetNearbyBeaconsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getNearbyBeacons(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startNearbyGPSDetection(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.startNearbyGPSDetection(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopNearbyGPSDetection(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.stopNearbyGPSDetection(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startGPSNotifications(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.startGPSNotifications(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopGPSNotifications(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.stopGPSNotifications(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNotificationsSupport(PluginCall call) {
        try {
            SetNotificationsSupportOptions options = new SetNotificationsSupportOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.setNotificationsSupport(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setInterfaceLanguage(PluginCall call) {
        try {
            SetInterfaceLanguageOptions options = new SetInterfaceLanguageOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.setInterfaceLanguage(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void createARView(PluginCall call) {
        try {
            CreateARViewOptions options = new CreateARViewOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.createARView(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void destroyARView(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.destroyARView(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void resizeARView(PluginCall call) {
        try {
            ResizeARViewOptions options = new ResizeARViewOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.resizeARView(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setARViewTouchEnabled(PluginCall call) {
        try {
            SetARViewTouchEnabledOptions options = new SetARViewTouchEnabledOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.setARViewTouchEnabled(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setARViewTouchHole(PluginCall call) {
        try {
            SetARViewTouchHoleOptions options = new SetARViewTouchHoleOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.setARViewTouchHole(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyListenersFromImplementation(@NonNull String eventName, @NonNull JSObject data) {
        notifyListeners(eventName, data);
    }

    @NonNull
    private List<String> getPermissionAliasesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray permissionsArray = call.getArray("permissions");
        List<String> aliases = new ArrayList<>();
        if (permissionsArray != null) {
            List<String> permissionsList = permissionsArray.toList();
            for (String permission : permissionsList) {
                aliases.add(permission);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                aliases.add(PERMISSION_BLUETOOTH_CONNECT);
                aliases.add(PERMISSION_BLUETOOTH_SCAN);
            } else {
                aliases.add(PERMISSION_BLUETOOTH);
            }
            aliases.add(PERMISSION_CAMERA);
            aliases.add(PERMISSION_LOCATION);
            aliases.add(PERMISSION_NOTIFICATIONS);
        }
        return aliases;
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
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
