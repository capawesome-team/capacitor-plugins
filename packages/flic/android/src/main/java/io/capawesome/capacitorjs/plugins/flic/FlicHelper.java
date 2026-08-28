package io.capawesome.capacitorjs.plugins.flic;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.enums.ButtonConnectionState;
import io.flic.flic2libandroid.BatteryLevel;
import io.flic.flic2libandroid.Flic2Button;

public class FlicHelper {

    @NonNull
    public static ButtonConnectionState createButtonConnectionState(@NonNull Flic2Button button) {
        switch (button.getConnectionState()) {
            case Flic2Button.CONNECTION_STATE_CONNECTING:
                return ButtonConnectionState.CONNECTING;
            case Flic2Button.CONNECTION_STATE_CONNECTED_STARTING:
            case Flic2Button.CONNECTION_STATE_CONNECTED_READY:
                return ButtonConnectionState.CONNECTED;
            default:
                return ButtonConnectionState.DISCONNECTED;
        }
    }

    @NonNull
    public static JSObject createButtonJSObject(@NonNull Flic2Button button) {
        JSObject result = new JSObject();
        BatteryLevel batteryLevel = button.getLastKnownBatteryLevel();
        if (batteryLevel != null) {
            result.put("batteryVoltage", (double) batteryLevel.getVoltage());
        }
        result.put("connectionState", createButtonConnectionState(button).getValue());
        result.put("firmwareVersion", button.getFirmwareVersion());
        result.put("id", button.getBdAddr());
        result.put("isReady", button.getConnectionState() == Flic2Button.CONNECTION_STATE_CONNECTED_READY);
        result.put("isUnpaired", button.isUnpaired());
        String name = button.getName();
        if (name != null) {
            result.put("name", name);
        }
        result.put("pressCount", button.getPressCount());
        result.put("serialNumber", button.getSerialNumber());
        result.put("uuid", button.getUuid());
        return result;
    }
}
