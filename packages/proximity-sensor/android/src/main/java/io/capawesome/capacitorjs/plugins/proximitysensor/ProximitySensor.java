package io.capawesome.capacitorjs.plugins.proximitysensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.Measurement;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.results.GetMeasurementResult;

public class ProximitySensor {

    @NonNull
    private final ProximitySensorPlugin plugin;

    private boolean isRunning = false;

    @Nullable
    private SensorEventListener sensorEventListener;

    public ProximitySensor(@NonNull ProximitySensorPlugin plugin) {
        this.plugin = plugin;
    }

    public void getMeasurement(@NonNull NonEmptyResultCallback<GetMeasurementResult> callback) {
        SensorManager sensorManager = getSensorManager();
        Sensor proximitySensor = getProximitySensor();
        final float maximumRange = proximitySensor.getMaximumRange();

        final SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                sensorManager.unregisterListener(this);
                Measurement measurement = new Measurement(event.values[0], maximumRange);
                callback.success(new GetMeasurementResult(measurement));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(listener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public boolean isAvailable() {
        return getProximitySensor() != null;
    }

    public void startMeasurementUpdates(@NonNull EmptyCallback callback) {
        if (isRunning) {
            callback.success();
            return;
        }

        SensorManager sensorManager = getSensorManager();
        Sensor proximitySensor = getProximitySensor();
        final float maximumRange = proximitySensor.getMaximumRange();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Measurement measurement = new Measurement(event.values[0], maximumRange);
                plugin.notifyMeasurementEventListeners(measurement);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        isRunning = true;
        callback.success();
    }

    public void stopMeasurementUpdates(@Nullable EmptyCallback callback) {
        getSensorManager().unregisterListener(sensorEventListener);
        isRunning = false;
        if (callback != null) {
            callback.success();
        }
    }

    @Nullable
    private Sensor getProximitySensor() {
        return getSensorManager().getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @NonNull
    private SensorManager getSensorManager() {
        return (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
    }
}
