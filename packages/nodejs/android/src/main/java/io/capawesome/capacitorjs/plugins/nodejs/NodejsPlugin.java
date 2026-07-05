package io.capawesome.capacitorjs.plugins.nodejs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.nodejs.classes.CustomException;
import io.capawesome.capacitorjs.plugins.nodejs.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.nodejs.classes.events.MessageEvent;
import io.capawesome.capacitorjs.plugins.nodejs.classes.options.SendOptions;
import io.capawesome.capacitorjs.plugins.nodejs.classes.options.StartOptions;
import io.capawesome.capacitorjs.plugins.nodejs.classes.results.IsReadyResult;
import io.capawesome.capacitorjs.plugins.nodejs.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.nodejs.interfaces.Result;

@CapacitorPlugin(name = "Nodejs")
public class NodejsPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_MESSAGE = "message";
    public static final String EVENT_READY = "ready";
    public static final String START_MODE_AUTO = "auto";
    public static final String START_MODE_MANUAL = "manual";
    public static final String TAG = "NodejsPlugin";

    private NodejsConfig config;
    private Nodejs implementation;

    @Override
    public void load() {
        config = getNodejsConfig();
        implementation = new Nodejs(this, getContext(), config);
        if (START_MODE_AUTO.equals(config.getStartMode())) {
            implementation.start(
                new StartOptions(),
                new EmptyCallback() {
                    @Override
                    public void success() {}

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, "Failed to start the Node.js runtime.", exception);
                    }
                }
            );
        }
    }

    @PluginMethod
    public void isReady(PluginCall call) {
        try {
            IsReadyResult result = new IsReadyResult(implementation.isReady());
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void send(PluginCall call) {
        try {
            SendOptions options = new SendOptions(call);

            implementation.send(options);
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void start(PluginCall call) {
        try {
            if (!START_MODE_MANUAL.equals(config.getStartMode())) {
                rejectCall(call, CustomExceptions.START_MODE_NOT_MANUAL);
                return;
            }
            StartOptions options = new StartOptions(call);
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

            implementation.start(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyMessageListeners(@NonNull MessageEvent event) {
        notifyListeners(EVENT_MESSAGE, event.toJSObject(), true);
    }

    public void notifyReadyListeners() {
        notifyListeners(EVENT_READY, new JSObject(), true);
    }

    @Override
    protected void handleOnPause() {
        super.handleOnPause();
        implementation.handleOnPause();
    }

    @Override
    protected void handleOnResume() {
        super.handleOnResume();
        implementation.handleOnResume();
    }

    @NonNull
    private NodejsConfig getNodejsConfig() {
        NodejsConfig config = new NodejsConfig();
        String nodeDir = getConfig().getString("nodeDir", null);
        if (nodeDir != null) {
            config.setNodeDir(nodeDir);
        }
        String startMode = getConfig().getString("startMode", null);
        if (startMode != null) {
            config.setStartMode(startMode);
        }
        return config;
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
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
