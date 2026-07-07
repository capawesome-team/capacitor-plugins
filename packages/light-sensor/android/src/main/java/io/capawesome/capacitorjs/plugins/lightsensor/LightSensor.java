package io.capawesome.capacitorjs.plugins.lightsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.lightsensor.classes.Measurement;
import io.capawesome.capacitorjs.plugins.lightsensor.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.lightsensor.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.lightsensor.classes.results.GetMeasurementResult;

public class LightSensor {

    @NonNull
    private final LightSensorPlugin plugin;

    private boolean isRunning = false;

    @Nullable
    private SensorEventListener sensorEventListener;

    public LightSensor(@NonNull LightSensorPlugin plugin) {
        this.plugin = plugin;
    }

    public void getMeasurement(@NonNull NonEmptyResultCallback<GetMeasurementResult> callback) {
        SensorManager sensorManager = getSensorManager();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                sensorManager.unregisterListener(this);
                Measurement measurement = new Measurement(event);
                callback.success(new GetMeasurementResult(measurement));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public boolean isAvailable() {
        SensorManager sensorManager = getSensorManager();
        return sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null;
    }

    public void startMeasurementUpdates(@NonNull EmptyCallback callback) {
        if (isRunning) {
            callback.success();
            return;
        }

        SensorManager sensorManager = getSensorManager();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Measurement measurement = new Measurement(event);
                plugin.notifyMeasurementEventListeners(measurement);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
        isRunning = true;
        callback.success();
    }

    public void stopMeasurementUpdates(@Nullable EmptyCallback callback) {
        if (sensorEventListener != null) {
            getSensorManager().unregisterListener(sensorEventListener);
            sensorEventListener = null;
        }
        isRunning = false;
        if (callback != null) {
            callback.success();
        }
    }

    @NonNull
    private SensorManager getSensorManager() {
        return (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
    }
}
