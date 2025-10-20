package io.capawesome.capacitorjs.plugins.foregroundservice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class ForegroundServiceHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public static NotificationChannel createNotificationChannelFromPluginCall(PluginCall call, String packageName) {
        String id = call.getString("id");
        if (id == null) {
            return null;
        }
        String name = call.getString("name");
        if (name == null) {
            return null;
        }
        int importance = call.getInt("importance", NotificationManager.IMPORTANCE_DEFAULT);
        String description = call.getString("description", "");

        NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
        notificationChannel.setDescription(description);
        return notificationChannel;
    }
}
