package io.capawesome.capacitorjs.plugins.batteryoptimization;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.RequiresApi;

public class BatteryOptimization {

    private final BatteryOptimizationPlugin plugin;

    public BatteryOptimization(BatteryOptimizationPlugin plugin) {
        this.plugin = plugin;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isBatteryOptimizationEnabled() {
        PowerManager powerManager = (PowerManager) plugin.getContext().getSystemService(Context.POWER_SERVICE);
        return !powerManager.isIgnoringBatteryOptimizations(plugin.getContext().getPackageName());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openBatteryOptimizationSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        plugin.getContext().startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimization() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + plugin.getContext().getPackageName()));
        plugin.getContext().startActivity(intent);
    }
}
