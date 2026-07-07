package io.capawesome.capacitorjs.plugins.pdfgenerator;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.CustomException;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options.GenerateFromHtmlOptions;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options.GenerateFromUrlOptions;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.results.GenerateResult;
import io.capawesome.capacitorjs.plugins.pdfgenerator.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.pdfgenerator.interfaces.Result;

@CapacitorPlugin(name = "PdfGenerator")
public class PdfGeneratorPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PdfGeneratorPlugin";

    private PdfGenerator implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new PdfGenerator(this);
    }

    @PluginMethod
    public void generateFromHtml(PluginCall call) {
        try {
            GenerateFromHtmlOptions options = new GenerateFromHtmlOptions(call);
            NonEmptyResultCallback<GenerateResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GenerateResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.generateFromHtml(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void generateFromUrl(PluginCall call) {
        try {
            GenerateFromUrlOptions options = new GenerateFromUrlOptions(call);
            NonEmptyResultCallback<GenerateResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GenerateResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.generateFromUrl(options, callback);
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
