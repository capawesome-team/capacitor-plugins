package io.capawesome.capacitorjs.plugins.flic;

import android.Manifest;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.flic.classes.CustomException;
import io.capawesome.capacitorjs.plugins.flic.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonConnectedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonConnectionFailedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonDisconnectedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonReadyEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonUnpairedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ScanStatusChangedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.options.ConnectButtonByIdOptions;
import io.capawesome.capacitorjs.plugins.flic.classes.options.DisconnectButtonByIdOptions;
import io.capawesome.capacitorjs.plugins.flic.classes.options.ForgetButtonByIdOptions;
import io.capawesome.capacitorjs.plugins.flic.classes.results.GetButtonsResult;
import io.capawesome.capacitorjs.plugins.flic.classes.results.PermissionStatusResult;
import io.capawesome.capacitorjs.plugins.flic.classes.results.StartScanResult;
import io.capawesome.capacitorjs.plugins.flic.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.flic.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;

@CapacitorPlugin(
    name = "Flic",
    permissions = {
        @Permission(strings = { Manifest.permission.ACCESS_FINE_LOCATION }, alias = FlicPlugin.PERMISSION_LOCATION),
        @Permission(strings = { Manifest.permission.BLUETOOTH_CONNECT }, alias = FlicPlugin.PERMISSION_BLUETOOTH_CONNECT),
        @Permission(strings = { Manifest.permission.BLUETOOTH_SCAN }, alias = FlicPlugin.PERMISSION_BLUETOOTH_SCAN)
    }
)
public class FlicPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_BUTTON_CONNECTED = "buttonConnected";
    public static final String EVENT_BUTTON_CONNECTION_FAILED = "buttonConnectionFailed";
    public static final String EVENT_BUTTON_DISCONNECTED = "buttonDisconnected";
    public static final String EVENT_BUTTON_DOUBLE_CLICK = "buttonDoubleClick";
    public static final String EVENT_BUTTON_DOWN = "buttonDown";
    public static final String EVENT_BUTTON_HOLD = "buttonHold";
    public static final String EVENT_BUTTON_READY = "buttonReady";
    public static final String EVENT_BUTTON_SINGLE_CLICK = "buttonSingleClick";
    public static final String EVENT_BUTTON_UNPAIRED = "buttonUnpaired";
    public static final String EVENT_BUTTON_UP = "buttonUp";
    public static final String EVENT_SCAN_STATUS_CHANGED = "scanStatusChanged";
    public static final String PERMISSION_BLUETOOTH_CONNECT = "bluetoothConnect";
    public static final String PERMISSION_BLUETOOTH_SCAN = "bluetoothScan";
    public static final String PERMISSION_LOCATION = "location";
    public static final String TAG = "FlicPlugin";

    private Flic implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Flic(this);
    }

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        resolveCall(call, createPermissionStatusResult());
    }

    @PluginMethod
    public void connectButtonById(PluginCall call) {
        try {
            ConnectButtonByIdOptions options = new ConnectButtonByIdOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.connectButtonById(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disconnectButtonById(PluginCall call) {
        try {
            DisconnectButtonByIdOptions options = new DisconnectButtonByIdOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.disconnectButtonById(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void forgetButtonById(PluginCall call) {
        try {
            ForgetButtonByIdOptions options = new ForgetButtonByIdOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.forgetButtonById(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getButtons(PluginCall call) {
        try {
            NonEmptyResultCallback<GetButtonsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetButtonsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getButtons(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.initialize(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyButtonConnectedListeners(@NonNull ButtonConnectedEvent event) {
        notifyListeners(EVENT_BUTTON_CONNECTED, event.toJSObject());
    }

    public void notifyButtonConnectionFailedListeners(@NonNull ButtonConnectionFailedEvent event) {
        notifyListeners(EVENT_BUTTON_CONNECTION_FAILED, event.toJSObject());
    }

    public void notifyButtonDisconnectedListeners(@NonNull ButtonDisconnectedEvent event) {
        notifyListeners(EVENT_BUTTON_DISCONNECTED, event.toJSObject());
    }

    public void notifyButtonDoubleClickListeners(@NonNull ButtonEvent event) {
        notifyListeners(EVENT_BUTTON_DOUBLE_CLICK, event.toJSObject());
    }

    public void notifyButtonDownListeners(@NonNull ButtonEvent event) {
        notifyListeners(EVENT_BUTTON_DOWN, event.toJSObject());
    }

    public void notifyButtonHoldListeners(@NonNull ButtonEvent event) {
        notifyListeners(EVENT_BUTTON_HOLD, event.toJSObject());
    }

    public void notifyButtonReadyListeners(@NonNull ButtonReadyEvent event) {
        notifyListeners(EVENT_BUTTON_READY, event.toJSObject());
    }

    public void notifyButtonSingleClickListeners(@NonNull ButtonEvent event) {
        notifyListeners(EVENT_BUTTON_SINGLE_CLICK, event.toJSObject());
    }

    public void notifyButtonUnpairedListeners(@NonNull ButtonUnpairedEvent event) {
        notifyListeners(EVENT_BUTTON_UNPAIRED, event.toJSObject());
    }

    public void notifyButtonUpListeners(@NonNull ButtonEvent event) {
        notifyListeners(EVENT_BUTTON_UP, event.toJSObject());
    }

    public void notifyScanStatusChangedListeners(@NonNull ScanStatusChangedEvent event) {
        notifyListeners(EVENT_SCAN_STATUS_CHANGED, event.toJSObject());
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        requestPermissionForAliases(getRequiredPermissionAliases(), call, "handlePermissionsCallback");
    }

    @PluginMethod
    public void startScan(PluginCall call) {
        try {
            if (!areRequiredPermissionsGranted()) {
                requestPermissionForAliases(getRequiredPermissionAliases(), call, "handlePermissionsCallback");
                return;
            }
            NonEmptyResultCallback<StartScanResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull StartScanResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.startScan(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopScan(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.stopScan(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private boolean areRequiredPermissionsGranted() {
        for (String alias : getRequiredPermissionAliases()) {
            if (getPermissionState(alias) != PermissionState.GRANTED) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    private PermissionStatusResult createPermissionStatusResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return new PermissionStatusResult(
                PermissionState.GRANTED,
                getPermissionState(PERMISSION_BLUETOOTH_CONNECT),
                getPermissionState(PERMISSION_BLUETOOTH_SCAN),
                PermissionState.GRANTED
            );
        }
        return new PermissionStatusResult(
            PermissionState.GRANTED,
            PermissionState.GRANTED,
            PermissionState.GRANTED,
            getPermissionState(PERMISSION_LOCATION)
        );
    }

    @NonNull
    private String[] getRequiredPermissionAliases() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return new String[] { PERMISSION_BLUETOOTH_CONNECT, PERMISSION_BLUETOOTH_SCAN };
        }
        return new String[] { PERMISSION_LOCATION };
    }

    @PermissionCallback
    private void handlePermissionsCallback(PluginCall call) {
        switch (call.getMethodName()) {
            case "requestPermissions":
                resolveCall(call, createPermissionStatusResult());
                break;
            case "startScan":
                if (areRequiredPermissionsGranted()) {
                    startScan(call);
                } else {
                    rejectCall(call, CustomExceptions.PERMISSIONS_DENIED);
                }
                break;
            default:
                break;
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
