package io.capawesome.capacitorjs.plugins.pdfannotator;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.CustomException;
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.options.OpenOptions;
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.results.OpenResult;
import io.capawesome.capacitorjs.plugins.pdfannotator.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.pdfannotator.interfaces.Result;

@CapacitorPlugin(name = "PdfAnnotator")
public class PdfAnnotatorPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PdfAnnotatorPlugin";

    private PdfAnnotator implementation;

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

    @Override
    public void load() {
        super.load();
        this.implementation = new PdfAnnotator(this);
    }

    @PluginMethod
    public void open(PluginCall call) {
        try {
            OpenOptions options = new OpenOptions(call);
            Intent intent = implementation.createOpenIntent(options);
            startActivityForResult(call, intent, "handleOpenResult");
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @ActivityCallback
    private void handleOpenResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        try {
            NonEmptyResultCallback<OpenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull OpenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.handleOpenResult(result, callback);
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
