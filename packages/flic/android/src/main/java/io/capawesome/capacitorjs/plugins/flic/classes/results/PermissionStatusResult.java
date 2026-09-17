package io.capawesome.capacitorjs.plugins.flic.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;

public class PermissionStatusResult implements Result {

    @Nullable
    private final PermissionState bluetooth;

    @Nullable
    private final PermissionState bluetoothConnect;

    @Nullable
    private final PermissionState bluetoothScan;

    @Nullable
    private final PermissionState location;

    public PermissionStatusResult(
        @Nullable PermissionState bluetooth,
        @Nullable PermissionState bluetoothConnect,
        @Nullable PermissionState bluetoothScan,
        @Nullable PermissionState location
    ) {
        this.bluetooth = bluetooth;
        this.bluetoothConnect = bluetoothConnect;
        this.bluetoothScan = bluetoothScan;
        this.location = location;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bluetooth", createPermissionStateString(bluetooth));
        result.put("bluetoothConnect", createPermissionStateString(bluetoothConnect));
        result.put("bluetoothScan", createPermissionStateString(bluetoothScan));
        result.put("location", createPermissionStateString(location));
        return result;
    }

    @NonNull
    private String createPermissionStateString(@Nullable PermissionState state) {
        return state == null ? PermissionState.PROMPT.toString() : state.toString();
    }
}
