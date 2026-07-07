package io.capawesome.capacitorjs.plugins.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.compass.classes.Heading;
import io.capawesome.capacitorjs.plugins.compass.classes.events.HeadingChangeEvent;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.compass.classes.results.GetHeadingResult;

public class Compass {

    @NonNull
    private final CompassPlugin plugin;

    private boolean isRunning = false;

    @Nullable
    private SensorEventListener sensorEventListener;

    public Compass(@NonNull CompassPlugin plugin) {
        this.plugin = plugin;
    }

    public void getHeading(@NonNull NonEmptyResultCallback<GetHeadingResult> callback) {
        SensorManager sensorManager = getSensorManager();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                sensorManager.unregisterListener(this);
                Heading heading = createHeadingFromSensorEvent(event);
                callback.success(new GetHeadingResult(heading));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public boolean isAvailable() {
        SensorManager sensorManager = getSensorManager();
        return sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null;
    }

    public void startHeadingUpdates(@NonNull EmptyCallback callback) {
        if (isRunning) {
            callback.success();
            return;
        }

        SensorManager sensorManager = getSensorManager();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Heading heading = createHeadingFromSensorEvent(event);
                plugin.notifyHeadingChangeListeners(new HeadingChangeEvent(heading));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
        isRunning = true;
        callback.success();
    }

    public void stopHeadingUpdates(@Nullable EmptyCallback callback) {
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
    private Heading createHeadingFromSensorEvent(@NonNull SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        double azimuth = Math.toDegrees(orientation[0]);
        double magneticHeading = (azimuth + 360.0) % 360.0;
        magneticHeading = Math.round(magneticHeading * 100.0) / 100.0;

        Double accuracy = null;
        if (event.values.length >= 5 && event.values[4] >= 0) {
            accuracy = Math.round(Math.toDegrees(event.values[4]) * 100.0) / 100.0;
        }

        return new Heading(magneticHeading, null, accuracy);
    }

    @NonNull
    private SensorManager getSensorManager() {
        return (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
    }
}
