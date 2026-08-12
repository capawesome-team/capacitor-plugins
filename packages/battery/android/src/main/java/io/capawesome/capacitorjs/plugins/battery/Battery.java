package io.capawesome.capacitorjs.plugins.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import io.capawesome.capacitorjs.plugins.battery.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.battery.classes.events.BatteryLevelChangeEvent;
import io.capawesome.capacitorjs.plugins.battery.classes.events.BatteryStateChangeEvent;
import io.capawesome.capacitorjs.plugins.battery.classes.events.LowPowerModeChangeEvent;
import io.capawesome.capacitorjs.plugins.battery.classes.results.GetBatteryLevelResult;
import io.capawesome.capacitorjs.plugins.battery.classes.results.GetBatteryStateResult;
import io.capawesome.capacitorjs.plugins.battery.classes.results.IsLowPowerModeEnabledResult;
import io.capawesome.capacitorjs.plugins.battery.interfaces.NonEmptyResultCallback;

public class Battery {

    @NonNull
    private final BatteryPlugin plugin;

    @NonNull
    private final PowerManager powerManager;

    private boolean isObserving = false;

    private float lastLevel = -1;

    @NonNull
    private String lastState = "unknown";

    private boolean lastLowPowerModeEnabled = false;

    private final BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleBatteryChanged(intent);
        }
    };

    private final BroadcastReceiver powerSaveModeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handlePowerSaveModeChanged();
        }
    };

    public Battery(@NonNull BatteryPlugin plugin) {
        this.plugin = plugin;
        this.powerManager = (PowerManager) plugin.getContext().getSystemService(Context.POWER_SERVICE);
    }

    public void getBatteryLevel(@NonNull NonEmptyResultCallback<GetBatteryLevelResult> callback) {
        float level = computeBatteryLevel(getBatteryStatusIntent());
        if (level < 0) {
            callback.error(CustomExceptions.BATTERY_LEVEL_UNAVAILABLE);
            return;
        }
        callback.success(new GetBatteryLevelResult(level));
    }

    public void getBatteryState(@NonNull NonEmptyResultCallback<GetBatteryStateResult> callback) {
        callback.success(new GetBatteryStateResult(computeBatteryState(getBatteryStatusIntent())));
    }

    public void isLowPowerModeEnabled(@NonNull NonEmptyResultCallback<IsLowPowerModeEnabledResult> callback) {
        callback.success(new IsLowPowerModeEnabledResult(powerManager.isPowerSaveMode()));
    }

    public void startObserving() {
        if (isObserving) {
            return;
        }
        Intent intent = getBatteryStatusIntent();
        lastLevel = computeBatteryLevel(intent);
        lastState = computeBatteryState(intent);
        lastLowPowerModeEnabled = powerManager.isPowerSaveMode();
        ContextCompat.registerReceiver(
            plugin.getContext(),
            batteryChangedReceiver,
            new IntentFilter(Intent.ACTION_BATTERY_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        );
        ContextCompat.registerReceiver(
            plugin.getContext(),
            powerSaveModeChangedReceiver,
            new IntentFilter(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        );
        isObserving = true;
    }

    public void stopObserving() {
        if (!isObserving) {
            return;
        }
        plugin.getContext().unregisterReceiver(batteryChangedReceiver);
        plugin.getContext().unregisterReceiver(powerSaveModeChangedReceiver);
        isObserving = false;
    }

    private float computeBatteryLevel(@Nullable Intent intent) {
        if (intent == null) {
            return -1;
        }
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level < 0 || scale <= 0) {
            return -1;
        }
        return level / (float) scale;
    }

    @NonNull
    private String computeBatteryState(@Nullable Intent intent) {
        if (intent == null) {
            return "unknown";
        }
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            return "charging";
        }
        if (status == BatteryManager.BATTERY_STATUS_FULL) {
            return "full";
        }
        if (plugged == 0) {
            return "unplugged";
        }
        return "unknown";
    }

    @Nullable
    private Intent getBatteryStatusIntent() {
        return plugin.getContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void handleBatteryChanged(@Nullable Intent intent) {
        float level = computeBatteryLevel(intent);
        if (level >= 0 && level != lastLevel) {
            lastLevel = level;
            plugin.notifyBatteryLevelChangeListeners(new BatteryLevelChangeEvent(level));
        }
        String state = computeBatteryState(intent);
        if (!state.equals(lastState)) {
            lastState = state;
            plugin.notifyBatteryStateChangeListeners(new BatteryStateChangeEvent(state));
        }
    }

    private void handlePowerSaveModeChanged() {
        boolean enabled = powerManager.isPowerSaveMode();
        if (enabled == lastLowPowerModeEnabled) {
            return;
        }
        lastLowPowerModeEnabled = enabled;
        plugin.notifyLowPowerModeChangeListeners(new LowPowerModeChangeEvent(enabled));
    }
}
