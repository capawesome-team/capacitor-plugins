package io.capawesome.capacitorjs.plugins.foregroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.getcapacitor.Logger;

public class NotificationTapBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            int notificationId = intent.getIntExtra("notificationId", -1);

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(launchIntent);
            }

            ForegroundServicePlugin.onNotificationTapped(notificationId);
        } catch (Exception exception) {
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }
}
