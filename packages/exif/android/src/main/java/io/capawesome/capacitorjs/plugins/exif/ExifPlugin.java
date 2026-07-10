package io.capawesome.capacitorjs.plugins.exif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.exif.classes.CustomException;
import io.capawesome.capacitorjs.plugins.exif.classes.options.ReadExifOptions;
import io.capawesome.capacitorjs.plugins.exif.classes.options.RemoveExifOptions;
import io.capawesome.capacitorjs.plugins.exif.classes.options.WriteExifOptions;
import io.capawesome.capacitorjs.plugins.exif.classes.results.ReadExifResult;
import io.capawesome.capacitorjs.plugins.exif.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.exif.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.exif.interfaces.Result;

@CapacitorPlugin(name = "Exif")
public class ExifPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "ExifPlugin";

    private Exif implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Exif(this);
    }

    @PluginMethod
    public void readExif(PluginCall call) {
        try {
            ReadExifOptions options = new ReadExifOptions(call);
            NonEmptyResultCallback<ReadExifResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ReadExifResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.readExif(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removeExif(PluginCall call) {
        try {
            RemoveExifOptions options = new RemoveExifOptions(call);
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
            implementation.removeExif(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void writeExif(PluginCall call) {
        try {
            WriteExifOptions options = new WriteExifOptions(call);
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
            implementation.writeExif(options, callback);
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
