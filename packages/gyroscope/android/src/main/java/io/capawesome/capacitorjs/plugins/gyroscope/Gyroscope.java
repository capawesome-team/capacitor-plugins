package io.capawesome.capacitorjs.plugins.gyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.Measurement;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.results.GetMeasurementResult;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.results.IsAvailableResult;

public class Gyroscope {

    @NonNull
    private final GyroscopePlugin plugin;

    private boolean isRunning = false;

    @Nullable
    private SensorEventListener sensorEventListener;

    public Gyroscope(@NonNull GyroscopePlugin plugin) {
        this.plugin = plugin;
    }

    public void getMeasurement(@NonNull NonEmptyResultCallback<GetMeasurementResult> callback) throws Exception {
        SensorManager sensorManager = (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor == null) {
            throw new Exception(GyroscopePlugin.ERROR_GYROSCOPE_UNAVAILABLE);
        }

        final SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                sensorManager.unregisterListener(this);
                Measurement measurement = new Measurement(event);
                callback.success(new GetMeasurementResult(measurement));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(listener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        SensorManager sensorManager = (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        boolean isAvailable = gyroscopeSensor != null;
        callback.success(new IsAvailableResult(isAvailable));
    }

    public void startMeasurementUpdates(@NonNull EmptyCallback callback) {
        if (isRunning) {
            callback.success();
            return;
        }

        SensorManager sensorManager = (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor == null) {
            callback.error(new Exception(GyroscopePlugin.ERROR_GYROSCOPE_UNAVAILABLE));
            return;
        }

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Measurement measurement = new Measurement(event);
                plugin.notifyMeasurementEventListeners(measurement);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        isRunning = true;
        callback.success();
    }

    public void stopMeasurementUpdates(@Nullable EmptyCallback callback) {
        SensorManager sensorManager = (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(sensorEventListener);
        isRunning = false;
        if (callback != null) {
            callback.success();
        }
    }
}
