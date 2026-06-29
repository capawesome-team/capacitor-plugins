package io.capawesome.capacitorjs.plugins.badge;

import static me.leolin.shortcutbadger.ShortcutBadger.isBadgeCounterSupported;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.getcapacitor.Logger;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Badge {

    private static final String TAG = "Badge";
    private static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "capacitor_badge";
    private static final String DEFAULT_NOTIFICATION_CHANNEL_NAME = "Badge";
    private static final String STORAGE_KEY = "capacitor.badge";
    private Context context;
    private BadgeConfig config;

    Badge(Context context, BadgeConfig config) {
        this.config = config;
        this.context = context.getApplicationContext();
        createNotificationChannel();
        if (isBadgeCounterSupported(context)) {
            this.context = context;
        }
        boolean restoreCount = this.config.getPersist();
        if (restoreCount) {
            restore();
        }
    }

    public void handleOnResume() {
        try {
            boolean resetCount = this.config.getAutoClear();
            if (resetCount) {
                set(0);
            }
        } catch (Exception ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        }
    }

    public int get() {
        return getPrefs().getInt(STORAGE_KEY, 0);
    }

    public void set(int count) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putInt(STORAGE_KEY, count);
        editor.apply();
        applyCount(count);
    }

    public void increase() {
        int count = get();
        set(count + 1);
    }

    public void decrease() {
        int count = get();
        if (count < 1) {
            return;
        }
        set(count - 1);
    }

    public void clear() {
        set(0);
    }

    public boolean isSupported() {
        int count = get();
        // Doing this check causes the side effect of resetting the counter if it's supported.
        boolean isSupported = ShortcutBadger.isBadgeCounterSupported(context);
        if (isSupported) {
            set(count);
        }
        return isSupported;
    }

    private void restore() {
        try {
            int count = get();
            applyCount(count);
        } catch (Exception ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        }
    }

    private void applyCount(int count) {
        createNotificationChannel();
        boolean success = ShortcutBadger.applyCount(context, count);
        if (!success) {
            Logger.warn(TAG, "Unable to apply badge count.");
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (
            notificationManager == null ||
            notificationManager.getNotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID) != null
        ) {
            return;
        }
        NotificationChannel notificationChannel = new NotificationChannel(
            DEFAULT_NOTIFICATION_CHANNEL_ID,
            DEFAULT_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        );
        notificationChannel.setShowBadge(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE);
    }
}
