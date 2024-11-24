package io.capawesome.capacitorjs.plugins.foregroundservice;

import android.Manifest;
import android.app.NotificationChannel;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import java.util.ArrayList;
import org.json.JSONObject;

@CapacitorPlugin(
    name = "ForegroundService",
    permissions = @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = ForegroundServicePlugin.LOCAL_NOTIFICATIONS)
)
public class ForegroundServicePlugin extends Plugin {

    public static final String LOCAL_NOTIFICATIONS = "display";

    public static final String TAG = "ForegroundService";
    public static final String BUTTON_CLICKED_EVENT = "buttonClicked";
    public static Bridge staticBridge = null;

    private static final String MOVE_TO_FOREGROUND_CALLBACK_NAME = "moveToForegroundResult";
    private static final String REQUEST_MANAGE_OVERLAY_PERMISSION_CALLBACK_NAME = "requestManageOverlayPermissionResult";
    public static final String ERROR_ID_MISSING = "id must be provided.";
    public static final String ERROR_ID_OR_NAME_MISSING = "id and name must be provided.";
    private ForegroundService implementation;

    @Override
    public void load() {
        try {
            staticBridge = this.bridge;
            implementation = new ForegroundService(this);
        } catch (Exception exception) {
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void moveToForeground(PluginCall call) {
        try {
            String packageName = getContext().getPackageName();
            Intent intent = getContext().getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
            startActivityForResult(call, intent, MOVE_TO_FOREGROUND_CALLBACK_NAME);
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void startForegroundService(PluginCall call) {
        try {
            implementation.stopForegroundService();
        } catch (Exception exception) {
            // Ignore exception
        }
        startOrUpdateForegroundService(call, false);
    }

    @PluginMethod
    public void updateForegroundService(PluginCall call) {
        startOrUpdateForegroundService(call, true);
    }

    private void startOrUpdateForegroundService(PluginCall call, boolean isUpdate) {
        try {
            String channelId = call.getString("notificationChannelId");
            String body = call.getString("body");
            String icon = call.getString("smallIcon");
            int id = call.getInt("id");
            String title = call.getString("title");
            boolean silent = call.getBoolean("silent", false);
            JSArray buttons = call.getArray("buttons", new JSArray());

            ArrayList<Bundle> buttonBundles = new ArrayList<>();
            for (int i = 0; i < buttons.length(); i++) {
                JSONObject button = buttons.getJSONObject(i);
                Bundle buttonBundle = new Bundle();
                buttonBundle.putString("title", button.getString("title"));
                buttonBundle.putInt("id", button.getInt("id"));
                buttonBundles.add(buttonBundle);
            }

            if (isUpdate) {
                implementation.updateForegroundService(channelId, body, icon, id, title, buttonBundles, silent);
            } else {
                implementation.startForegroundService(channelId, body, icon, id, title, buttonBundles, silent);
            }
            call.resolve();
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void stopForegroundService(PluginCall call) {
        try {
            implementation.stopForegroundService();
            call.resolve();
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            JSObject result = new JSObject();
            result.put(LOCAL_NOTIFICATIONS, "granted");
            call.resolve(result);
        } else {
            super.checkPermissions(call);
        }
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            JSObject result = new JSObject();
            result.put(LOCAL_NOTIFICATIONS, "granted");
            call.resolve(result);
        } else {
            if (getPermissionState(LOCAL_NOTIFICATIONS) == PermissionState.GRANTED) {
                this.checkPermissions(call);
            } else {
                requestPermissionForAlias(LOCAL_NOTIFICATIONS, call, "permissionsCallback");
            }
        }
    }

    @PluginMethod
    public void requestManageOverlayPermission(PluginCall call) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(getContext().getApplicationContext())) {
                    return;
                }
                String packageName = getContext().getPackageName();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName));
                startActivityForResult(call, intent, REQUEST_MANAGE_OVERLAY_PERMISSION_CALLBACK_NAME);
            } else {
                call.resolve();
            }
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void checkManageOverlayPermission(PluginCall call) {
        try {
            boolean granted = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                granted = Settings.canDrawOverlays(getContext().getApplicationContext());
            }

            JSObject result = new JSObject();
            result.put("granted", granted);
            call.resolve(result);
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void createNotificationChannel(PluginCall call) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                call.unavailable();
                return;
            }
            NotificationChannel notificationChannel = ForegroundServiceHelper.createNotificationChannelFromPluginCall(
                call,
                getContext().getPackageName()
            );
            if (notificationChannel == null) {
                call.reject(ERROR_ID_OR_NAME_MISSING);
                return;
            }
            implementation.createNotificationChannel(notificationChannel);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void deleteNotificationChannel(PluginCall call) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                call.unavailable();
                return;
            }
            String id = call.getString("id");
            if (id == null) {
                call.reject(ERROR_ID_MISSING);
                return;
            }

            implementation.deleteNotificationChannelById(id);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    public static void onButtonClicked(int buttonId) {
        try {
            ForegroundServicePlugin plugin = ForegroundServicePlugin.getForegroundServicePluginInstance();
            if (plugin == null) {
                return;
            }
            plugin.handleButtonClicked(buttonId);
        } catch (Exception exception) {
            Logger.error(ForegroundServicePlugin.TAG, exception.getMessage(), exception);
        }
    }

    @ActivityCallback
    private void moveToForegroundResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        call.resolve();
    }

    @ActivityCallback
    private void requestManageOverlayPermissionResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        checkManageOverlayPermission(call);
    }

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }

    private void handleButtonClicked(int buttonId) {
        JSObject result = new JSObject();
        result.put("buttonId", buttonId);
        notifyListeners(BUTTON_CLICKED_EVENT, result);
    }

    private static ForegroundServicePlugin getForegroundServicePluginInstance() {
        if (staticBridge == null || staticBridge.getWebView() == null) {
            return null;
        }
        PluginHandle handle = staticBridge.getPlugin("ForegroundService");
        if (handle == null) {
            return null;
        }
        return (ForegroundServicePlugin) handle.getInstance();
    }
}
