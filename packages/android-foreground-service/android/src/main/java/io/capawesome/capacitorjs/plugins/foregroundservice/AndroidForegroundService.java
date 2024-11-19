package io.capawesome.capacitorjs.plugins.foregroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import java.util.ArrayList;

public class AndroidForegroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            Bundle notificationBundle = extras.getBundle("notification");
            String body = notificationBundle.getString("body");
            int id = notificationBundle.getInt("id");
            int icon = notificationBundle.getInt("icon");
            String title = notificationBundle.getString("title");
            ArrayList<Bundle> buttonsBundle = notificationBundle.getParcelableArrayList("buttons");

            PendingIntent contentIntent = buildContentIntent(id);
            Notification.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new Notification.Builder(getApplicationContext(), ForegroundService.DEFAULT_NOTIFICATION_CHANNEL_ID);
            } else {
                builder = new Notification.Builder(getApplicationContext());
            }
            builder
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .setSmallIcon(icon)
                .setPriority(Notification.PRIORITY_HIGH);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Notification.Action[] actions = convertBundlesToNotificationActions(
                    buttonsBundle.toArray(new Bundle[buttonsBundle.size()])
                );
                builder.setActions(actions);
            }

            Notification notification = builder.build();

            if (ForegroundService.ACTION_UPDATE.equals(action)) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notification);
            } else {
                startForeground(id, notification);
            }
        } catch (Exception exception) {
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
        return START_STICKY;
    }

    private PendingIntent buildContentIntent(int id) {
        String packageName = getApplicationContext().getPackageName();
        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        int pendingIntentFlags;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntentFlags = PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE;
        } else {
            pendingIntentFlags = PendingIntent.FLAG_CANCEL_CURRENT;
        }
        return PendingIntent.getActivity(getApplicationContext(), id, intent, pendingIntentFlags);
    }

    private Notification.Action[] convertBundlesToNotificationActions(Bundle[] bundles) {
        Notification.Action[] actions = new Notification.Action[bundles.length];
        for (int i = 0; i < bundles.length; i++) {
            String title = bundles[i].getString("title");
            int id = bundles[i].getInt("id");

            Intent intent = new Intent(this, NotificationActionBroadcastReceiver.class);
            intent.putExtra("buttonId", id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                id,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT
            );

            Notification.Action action = new Notification.Action(0, title, pendingIntent);
            actions[i] = action;
        }
        return actions;
    }
}
