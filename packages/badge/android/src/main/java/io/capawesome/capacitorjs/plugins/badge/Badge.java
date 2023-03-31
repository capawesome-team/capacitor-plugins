package io.capawesome.capacitorjs.plugins.badge;

import static me.leolin.shortcutbadger.ShortcutBadger.isBadgeCounterSupported;

import android.content.Context;
import android.content.SharedPreferences;
import com.getcapacitor.Logger;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Badge {

    private static final String STORAGE_KEY = "capacitor.badge";
    private Context context;
    private BadgeConfig config;

    Badge(Context context, BadgeConfig config) {
        this.config = config;
        if (isBadgeCounterSupported(context)) {
            this.context = context;
        } else {
            this.context = context.getApplicationContext();
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
        ShortcutBadger.applyCount(context, count);
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
            ShortcutBadger.applyCount(context, count);
        } catch (Exception ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        }
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE);
    }
}
