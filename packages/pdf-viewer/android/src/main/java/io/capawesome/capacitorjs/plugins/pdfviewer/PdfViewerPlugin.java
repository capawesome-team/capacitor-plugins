package io.capawesome.capacitorjs.plugins.pdfviewer;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.CustomException;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.events.PageChangeEvent;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.options.OpenOptions;
import io.capawesome.capacitorjs.plugins.pdfviewer.interfaces.EmptyCallback;

@CapacitorPlugin(name = "PdfViewer")
public class PdfViewerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_CLOSED = "closed";
    public static final String EVENT_PAGE_CHANGE = "pageChange";
    public static final String TAG = "PdfViewerPlugin";

    private PdfViewer implementation;

    @PluginMethod
    public void close(PluginCall call) {
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
            implementation.close(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void load() {
        super.load();
        this.implementation = new PdfViewer(this);
    }

    public void notifyClosedListeners() {
        notifyListeners(EVENT_CLOSED, new JSObject());
    }

    public void notifyPageChangeListeners(@NonNull PageChangeEvent event) {
        notifyListeners(EVENT_PAGE_CHANGE, event.toJSObject());
    }

    @PluginMethod
    public void open(PluginCall call) {
        try {
            OpenOptions options = new OpenOptions(call);
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
            implementation.open(options, callback);
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
}
