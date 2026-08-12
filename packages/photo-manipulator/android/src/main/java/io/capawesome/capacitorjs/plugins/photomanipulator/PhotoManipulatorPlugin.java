package io.capawesome.capacitorjs.plugins.photomanipulator;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.CustomException;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.options.GetInfoOptions;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.options.TransformOptions;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.results.TransformResult;
import io.capawesome.capacitorjs.plugins.photomanipulator.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.photomanipulator.interfaces.Result;

@CapacitorPlugin(name = "PhotoManipulator")
public class PhotoManipulatorPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PhotoManipulatorPlugin";

    private PhotoManipulator implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new PhotoManipulator(this);
    }

    @PluginMethod
    public void getInfo(PluginCall call) {
        try {
            GetInfoOptions options = new GetInfoOptions(call);
            NonEmptyResultCallback<GetInfoResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetInfoResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getInfo(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void transform(PluginCall call) {
        try {
            TransformOptions options = new TransformOptions(call);
            NonEmptyResultCallback<TransformResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull TransformResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.transform(options, callback);
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

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
