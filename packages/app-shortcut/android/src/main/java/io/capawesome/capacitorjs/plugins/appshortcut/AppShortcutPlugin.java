package io.capawesome.capacitorjs.plugins.appshortcut;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.appshortcut.classes.events.OnAppShortcutEvent;
import io.capawesome.capacitorjs.plugins.appshortcut.classes.options.SetOptions;
import io.capawesome.capacitorjs.plugins.appshortcut.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcut.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcut.interfaces.Result;
import java.util.Objects;

@CapacitorPlugin(name = "AppShortcut")
public class AppShortcutPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String ERROR_SHORTCUTS_MISSING = "shortcuts must be provided.";
    public static final String ERROR_TITLE_MISSING = "title must be provided.";
    public static final String ERROR_ID_MISSING = "id must be provided.";
    public static final String EVENT_ON_APP_SHORTCUT = "onAppShortcut";
    public static final String INTENT_EXTRA_ITEM_NAME = "shortcutId"; // DO NOT CHANGE THIS VALUE!
    public static final String TAG = "AppShortcutPlugin";

    private AppShortcut implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new AppShortcut(getContext());
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
            implementation.set(options.getShortcuts(), callback);
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

    private void notifyDeviceScannedListener(@NonNull OnAppShortcutEvent event) {
        notifyListeners(EVENT_ON_APP_SHORTCUT, event.toJSObject());
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
            JSObject result = new JSObject();
            String shortcutId = intent.getStringExtra(AppShortcutPlugin.INTENT_EXTRA_ITEM_NAME);
            if (shortcutId != null) {
                result.put("id", shortcutId);
                this.notifyListeners(AppShortcutPlugin.EVENT_ON_APP_SHORTCUT, result, true);
            }
        }
    }
}
