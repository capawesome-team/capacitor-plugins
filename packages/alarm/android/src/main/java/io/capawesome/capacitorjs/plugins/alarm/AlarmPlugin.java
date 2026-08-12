package io.capawesome.capacitorjs.plugins.alarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import io.capawesome.capacitorjs.plugins.alarm.classes.CustomException;
import io.capawesome.capacitorjs.plugins.alarm.classes.options.CreateAlarmOptions;
import io.capawesome.capacitorjs.plugins.alarm.classes.options.CreateTimerOptions;
import io.capawesome.capacitorjs.plugins.alarm.classes.results.CreateAlarmResult;
import io.capawesome.capacitorjs.plugins.alarm.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.alarm.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.alarm.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.alarm.interfaces.Result;

@CapacitorPlugin(name = "Alarm", permissions = @Permission(strings = {}, alias = "alarms"))
public class AlarmPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "AlarmPlugin";

    private Alarm implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Alarm(this);
    }

    @PluginMethod
    public void cancelAlarm(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void createAlarm(PluginCall call) {
        try {
            CreateAlarmOptions options = new CreateAlarmOptions(call);
            NonEmptyResultCallback<CreateAlarmResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CreateAlarmResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.createAlarm(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void createTimer(PluginCall call) {
        try {
            CreateTimerOptions options = new CreateTimerOptions(call);
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
            implementation.createTimer(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getAlarms(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            NonEmptyResultCallback<IsAvailableResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAvailableResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isAvailable(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openAlarms(PluginCall call) {
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
            implementation.openAlarms(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
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
