package io.capawesome.capacitorjs.plugins.foregroundservice;

import static android.content.Context.POWER_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import com.getcapacitor.plugin.util.AssetUtil;
import java.util.ArrayList;

public class ForegroundService {

    public static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "default";
    public static final String ACTION_UPDATE = "UPDATE_NOTIFICATION";

    private final ForegroundServicePlugin plugin;

    @Nullable
    private PowerManager.WakeLock activeWakeLock;

    public ForegroundService(ForegroundServicePlugin plugin) {
        this.plugin = plugin;
        createNotificationChannel();
    }

    public void startForegroundService(String body, String icon, int id, String title, ArrayList<Bundle> buttons) {
        startOrUpdateForegroundService(body, icon, id, title, buttons, false);
    }

    public void updateForegroundService(String body, String icon, int id, String title, ArrayList<Bundle> buttons) {
        startOrUpdateForegroundService(body, icon, id, title, buttons, true);
    }

    private void startOrUpdateForegroundService(String body, String icon, int id, String title,
                                                ArrayList<Bundle> buttons, boolean isUpdate) {
        if (!isUpdate) {
            acquireWakeLock();
        }

        int iconResourceId = AssetUtil.getResourceID(plugin.getContext(), AssetUtil.getResourceBaseName(icon), "drawable");
        Bundle notificationBundle = new Bundle();
        notificationBundle.putString("body", body);
        notificationBundle.putInt("icon", iconResourceId);
        notificationBundle.putInt("id", id);
        notificationBundle.putString("title", title);
        notificationBundle.putParcelableArrayList("buttons", new ArrayList<Bundle>(buttons));

        Context context = plugin.getContext();
        Intent intent = new Intent(context, AndroidForegroundService.class);
        intent.putExtra("notification", notificationBundle);

        if (isUpdate) {
            intent.setAction(ACTION_UPDATE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void stopForegroundService() {
        releaseWakeLock();
        Context context = plugin.getContext();
        Intent intent = new Intent(context, AndroidForegroundService.class);
        context.stopService(intent);
    }

    private void acquireWakeLock() {
        if (activeWakeLock != null) {
            return;
        }
        PowerManager powerManager = (PowerManager) plugin.getContext().getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "CapacitorAndroidForegroundService::Wakelock"
        );
        wakeLock.acquire();
        activeWakeLock = wakeLock;
    }

    private void releaseWakeLock() {
        if (activeWakeLock == null) {
            return;
        }
        activeWakeLock.release();
        activeWakeLock = null;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default";
            String description = "Default";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = plugin.getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
