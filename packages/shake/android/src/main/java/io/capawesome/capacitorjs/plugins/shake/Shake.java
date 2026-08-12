package io.capawesome.capacitorjs.plugins.shake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.shake.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.shake.classes.options.StartWatchingOptions;

public class Shake implements SensorEventListener {

    @NonNull
    private final ShakePlugin plugin;

    @Nullable
    private final Sensor accelerometerSensor;

    private long lastShakeTimestamp = 0;

    @NonNull
    private final SensorManager sensorManager;

    private static final long SHAKE_DEBOUNCE_IN_MILLISECONDS = 500;

    private float shakeThreshold = 0;

    private boolean watching = false;

    public Shake(@NonNull ShakePlugin plugin) {
        this.plugin = plugin;
        this.sensorManager = (SensorManager) plugin.getContext().getSystemService(Context.SENSOR_SERVICE);
        this.accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public boolean isAvailable() {
        return accelerometerSensor != null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        double gX = event.values[0] / SensorManager.GRAVITY_EARTH;
        double gY = event.values[1] / SensorManager.GRAVITY_EARTH;
        double gZ = event.values[2] / SensorManager.GRAVITY_EARTH;
        double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        if (gForce < shakeThreshold) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastShakeTimestamp < SHAKE_DEBOUNCE_IN_MILLISECONDS) {
            return;
        }
        lastShakeTimestamp = now;
        plugin.notifyShakeListeners();
    }

    public void startWatching(@NonNull StartWatchingOptions options, @NonNull EmptyCallback callback) {
        shakeThreshold = options.getShakeThreshold();
        if (!watching) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
            watching = true;
        }
        callback.success();
    }

    public void stopWatching(@Nullable EmptyCallback callback) {
        if (watching) {
            sensorManager.unregisterListener(this);
            watching = false;
        }
        if (callback != null) {
            callback.success();
        }
    }
}
