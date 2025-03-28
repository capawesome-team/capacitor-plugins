package io.capawesome.capacitorjs.plugins.appshortcuts;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.appshortcuts.classes.events.ClickEvent;
import io.capawesome.capacitorjs.plugins.appshortcuts.classes.options.SetOptions;
import io.capawesome.capacitorjs.plugins.appshortcuts.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcuts.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcuts.interfaces.Result;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

@CapacitorPlugin(name = "AppShortcuts")
public class AppShortcutsPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String ERROR_SHORTCUTS_MISSING = "shortcuts must be provided.";
    public static final String ERROR_TITLE_MISSING = "title must be provided.";
    public static final String ERROR_ID_MISSING = "id must be provided.";
    public static final String EVENT_CLICK = "click";
    public static final String INTENT_EXTRA_ITEM_NAME = "shortcutId"; // DO NOT CHANGE THIS VALUE!
    public static final String TAG = "AppShortcutsPlugin";

    private AppShortcuts implementation;

    @Override
    public void load() {
        this.implementation = new AppShortcuts(getContext(), getAppShortcutsConfig());
    }

    @PluginMethod
    public void get(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(@NonNull Result result) {
                    resolveCall(call, result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.get(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void set(PluginCall call) {
        try {
            SetOptions options = new SetOptions(call, getContext(), getBridge());
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call, null);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.set(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void clear(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call, null);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.clear(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void notifyClickListener(@NonNull ClickEvent event) {
        notifyListeners(EVENT_CLICK, event.toJSObject(), true);
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable JSObject result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    @Override
    protected void handleOnNewIntent(Intent intent) {
        super.handleOnNewIntent(intent);
        if (Objects.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            String shortcutId = intent.getStringExtra(AppShortcutsPlugin.INTENT_EXTRA_ITEM_NAME);
            if (shortcutId != null) {
                ClickEvent event = new ClickEvent(shortcutId);
                this.notifyClickListener(event);
            }
        }
    }

    private AppShortcutsConfig getAppShortcutsConfig() {
        AppShortcutsConfig config = new AppShortcutsConfig();
        JSONObject configJSON = getConfig().getConfigJSON();
        try {
            JSONArray shortcutsJSON = configJSON.getJSONArray("shortcuts");
            JSArray shortcuts = new JSArray();
            for (int i = 0; i < shortcutsJSON.length(); i++) {
                shortcuts.put(shortcutsJSON.get(i));
            }
            config.setShortcuts(AppShortcutsHelper.createShortcutInfoCompatList(shortcuts, getContext(), getBridge()));
        } catch (Exception e) {
            return config;
        }
        return config;
    }
}
