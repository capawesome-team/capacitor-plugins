package io.capawesome.capacitorjs.plugins.settingslauncher;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.settingslauncher.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.settingslauncher.classes.options.OpenAndroidSettingsOptions;
import io.capawesome.capacitorjs.plugins.settingslauncher.interfaces.EmptyCallback;

public class SettingsLauncher {

    @NonNull
    private final SettingsLauncherPlugin plugin;

    public SettingsLauncher(@NonNull SettingsLauncherPlugin plugin) {
        this.plugin = plugin;
    }

    public void openAndroidSettings(@NonNull OpenAndroidSettingsOptions options, @NonNull EmptyCallback callback) throws Exception {
        String action = getActionForPage(options.getPage());
        if (action == null) {
            throw CustomExceptions.PAGE_INVALID;
        }
        Intent intent = new Intent(action);
        try {
            startIntent(intent);
        } catch (ActivityNotFoundException exception) {
            throw CustomExceptions.PAGE_NOT_SUPPORTED;
        } catch (Exception exception) {
            throw CustomExceptions.OPEN_FAILED;
        }
        callback.success();
    }

    public void openAppSettings(@NonNull EmptyCallback callback) throws Exception {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
        try {
            startIntent(intent);
        } catch (Exception exception) {
            throw CustomExceptions.OPEN_FAILED;
        }
        callback.success();
    }

    public void openNotificationSettings(@NonNull EmptyCallback callback) throws Exception {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        try {
            startIntent(intent);
        } catch (Exception exception) {
            throw CustomExceptions.OPEN_FAILED;
        }
        callback.success();
    }

    @Nullable
    private String getActionForPage(@NonNull String page) {
        switch (page) {
            case "ACCESSIBILITY":
                return Settings.ACTION_ACCESSIBILITY_SETTINGS;
            case "AIRPLANE_MODE":
                return Settings.ACTION_AIRPLANE_MODE_SETTINGS;
            case "APN":
                return Settings.ACTION_APN_SETTINGS;
            case "BATTERY_SAVER":
                return Settings.ACTION_BATTERY_SAVER_SETTINGS;
            case "BLUETOOTH":
                return Settings.ACTION_BLUETOOTH_SETTINGS;
            case "CAPTIONING":
                return Settings.ACTION_CAPTIONING_SETTINGS;
            case "CAST_SETTINGS":
                return Settings.ACTION_CAST_SETTINGS;
            case "DATA_ROAMING":
                return Settings.ACTION_DATA_ROAMING_SETTINGS;
            case "DATE":
                return Settings.ACTION_DATE_SETTINGS;
            case "DEVELOPMENT":
                return Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS;
            case "DEVICE_INFO":
                return Settings.ACTION_DEVICE_INFO_SETTINGS;
            case "DISPLAY":
                return Settings.ACTION_DISPLAY_SETTINGS;
            case "DREAM":
                return Settings.ACTION_DREAM_SETTINGS;
            case "HOME":
                return Settings.ACTION_HOME_SETTINGS;
            case "IGNORE_BATTERY_OPTIMIZATION":
                return Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;
            case "INPUT_METHOD":
                return Settings.ACTION_INPUT_METHOD_SETTINGS;
            case "INTERNAL_STORAGE":
                return Settings.ACTION_INTERNAL_STORAGE_SETTINGS;
            case "LOCALE":
                return Settings.ACTION_LOCALE_SETTINGS;
            case "LOCATION":
                return Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            case "MANAGE_ALL_APPLICATIONS":
                return Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS;
            case "MANAGE_APPLICATIONS":
                return Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS;
            case "MEMORY_CARD":
                return Settings.ACTION_MEMORY_CARD_SETTINGS;
            case "NETWORK":
                return Settings.ACTION_NETWORK_OPERATOR_SETTINGS;
            case "NFC":
                return Settings.ACTION_NFC_SETTINGS;
            case "NFC_PAYMENT":
                return Settings.ACTION_NFC_PAYMENT_SETTINGS;
            case "NOTIFICATION_LISTENER":
                return Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;
            case "PRINTING":
                return Settings.ACTION_PRINT_SETTINGS;
            case "PRIVACY":
                return Settings.ACTION_PRIVACY_SETTINGS;
            case "SEARCH":
                return Settings.ACTION_SEARCH_SETTINGS;
            case "SECURITY":
                return Settings.ACTION_SECURITY_SETTINGS;
            case "SOUND":
                return Settings.ACTION_SOUND_SETTINGS;
            case "SYNC":
                return Settings.ACTION_SYNC_SETTINGS;
            case "USAGE":
                return Settings.ACTION_USAGE_ACCESS_SETTINGS;
            case "USER_DICTIONARY":
                return Settings.ACTION_USER_DICTIONARY_SETTINGS;
            case "VOICE_INPUT":
                return Settings.ACTION_VOICE_INPUT_SETTINGS;
            case "VPN":
                return Settings.ACTION_VPN_SETTINGS;
            case "WIFI":
                return Settings.ACTION_WIFI_SETTINGS;
            case "WIRELESS":
                return Settings.ACTION_WIRELESS_SETTINGS;
            default:
                return null;
        }
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }

    private void startIntent(@NonNull Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}
