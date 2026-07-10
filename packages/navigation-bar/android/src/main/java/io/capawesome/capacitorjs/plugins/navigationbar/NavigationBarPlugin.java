package io.capawesome.capacitorjs.plugins.navigationbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.options.SetColorOptions;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.options.SetStyleOptions;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.results.GetColorResult;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.results.GetStyleResult;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.Result;

@CapacitorPlugin(name = "NavigationBar")
public class NavigationBarPlugin extends Plugin {

    public static final String ERROR_COLOR_MISSING = "color must be provided.";
    public static final String ERROR_STYLE_MISSING = "style must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "NavigationBar";

    @Nullable
    private NavigationBar implementation;

    @Override
    public void load() {
        try {
            implementation = new NavigationBar(this);
            implementation.applyConfig(getNavigationBarConfig());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void getColor(PluginCall call) {
        try {
            NonEmptyCallback<GetColorResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(@NonNull GetColorResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.getColor(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getStyle(PluginCall call) {
        try {
            NonEmptyCallback<GetStyleResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(@NonNull GetStyleResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.getStyle(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void hide(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.hide(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setColor(PluginCall call) {
        try {
            SetColorOptions options = new SetColorOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.setColor(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setStyle(PluginCall call) {
        try {
            SetStyleOptions options = new SetStyleOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.setStyle(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void show(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.show(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private NavigationBarConfig getNavigationBarConfig() {
        NavigationBarConfig config = new NavigationBarConfig();
        config.setColor(getConfig().getString("color"));
        config.setDividerColor(getConfig().getString("dividerColor"));
        config.setStyle(getConfig().getString("style"));
        return config;
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
