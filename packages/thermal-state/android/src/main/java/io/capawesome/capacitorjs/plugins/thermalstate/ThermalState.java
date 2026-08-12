package io.capawesome.capacitorjs.plugins.thermalstate;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import io.capawesome.capacitorjs.plugins.thermalstate.classes.events.ThermalStateChangeEvent;
import io.capawesome.capacitorjs.plugins.thermalstate.classes.results.GetThermalStateResult;
import io.capawesome.capacitorjs.plugins.thermalstate.interfaces.NonEmptyResultCallback;
import java.util.concurrent.Executor;

public class ThermalState {

    @NonNull
    private final ThermalStatePlugin plugin;

    private boolean isObserving = false;

    @NonNull
    private final PowerManager powerManager;

    private PowerManager.OnThermalStatusChangedListener thermalStatusChangedListener;

    public ThermalState(@NonNull ThermalStatePlugin plugin) {
        this.plugin = plugin;
        this.powerManager = (PowerManager) plugin.getContext().getSystemService(Context.POWER_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getThermalState(@NonNull NonEmptyResultCallback<GetThermalStateResult> callback) {
        String state = mapThermalStatus(powerManager.getCurrentThermalStatus());
        callback.success(new GetThermalStateResult(state));
    }

    public void startObserving() {
        if (isObserving || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return;
        }
        Executor executor = ContextCompat.getMainExecutor(plugin.getContext());
        thermalStatusChangedListener = this::handleThermalStatusChanged;
        powerManager.addThermalStatusListener(executor, thermalStatusChangedListener);
        isObserving = true;
    }

    public void stopObserving() {
        if (!isObserving) {
            return;
        }
        if (thermalStatusChangedListener != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            powerManager.removeThermalStatusListener(thermalStatusChangedListener);
            thermalStatusChangedListener = null;
        }
        isObserving = false;
    }

    private void handleThermalStatusChanged(int status) {
        plugin.notifyThermalStateChangeListeners(new ThermalStateChangeEvent(mapThermalStatus(status)));
    }

    @NonNull
    private String mapThermalStatus(int status) {
        switch (status) {
            case PowerManager.THERMAL_STATUS_SEVERE:
            case PowerManager.THERMAL_STATUS_CRITICAL:
            case PowerManager.THERMAL_STATUS_EMERGENCY:
            case PowerManager.THERMAL_STATUS_SHUTDOWN:
                return "critical";
            case PowerManager.THERMAL_STATUS_LIGHT:
                return "fair";
            case PowerManager.THERMAL_STATUS_MODERATE:
                return "serious";
            default:
                return "nominal";
        }
    }
}
