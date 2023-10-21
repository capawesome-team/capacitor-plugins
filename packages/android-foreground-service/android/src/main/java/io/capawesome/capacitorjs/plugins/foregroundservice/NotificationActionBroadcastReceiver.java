package io.capawesome.capacitorjs.plugins.foregroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.getcapacitor.Logger;

public class NotificationActionBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            int buttonId = intent.getExtras().getInt("buttonId");
            ForegroundServicePlugin.onButtonClicked(buttonId);
        } catch (Exception exception) {
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }
}
