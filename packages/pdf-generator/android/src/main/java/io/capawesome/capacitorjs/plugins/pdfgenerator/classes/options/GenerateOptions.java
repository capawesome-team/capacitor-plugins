package io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options;

import android.print.PrintAttributes;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.CustomExceptions;
import java.util.UUID;

public class GenerateOptions {

    @NonNull
    private final String fileName;

    @NonNull
    private final PrintAttributes.MediaSize mediaSize;

    private final long timeout;

    public GenerateOptions(@NonNull PluginCall call) throws Exception {
        this.fileName = GenerateOptions.getFileNameFromCall(call);
        this.mediaSize = GenerateOptions.getMediaSizeFromCall(call);
        this.timeout = GenerateOptions.getTimeoutFromCall(call);
    }

    @NonNull
    public String getFileName() {
        return fileName;
    }

    @NonNull
    public PrintAttributes.MediaSize getMediaSize() {
        return mediaSize;
    }

    public long getTimeout() {
        return timeout;
    }

    @NonNull
    private static String getFileNameFromCall(@NonNull PluginCall call) {
        String fileName = call.getString("fileName");
        if (fileName == null) {
            fileName = UUID.randomUUID() + ".pdf";
        }
        return fileName.endsWith(".pdf") ? fileName : fileName + ".pdf";
    }

    @NonNull
    private static PrintAttributes.MediaSize getMediaSizeFromCall(@NonNull PluginCall call) throws Exception {
        PrintAttributes.MediaSize mediaSize;
        switch (call.getString("pageSize", "A4")) {
            case "A3":
                mediaSize = PrintAttributes.MediaSize.ISO_A3;
                break;
            case "A4":
                mediaSize = PrintAttributes.MediaSize.ISO_A4;
                break;
            case "A5":
                mediaSize = PrintAttributes.MediaSize.ISO_A5;
                break;
            case "LETTER":
                mediaSize = PrintAttributes.MediaSize.NA_LETTER;
                break;
            default:
                throw CustomExceptions.PAGE_SIZE_INVALID;
        }
        switch (call.getString("orientation", "PORTRAIT")) {
            case "LANDSCAPE":
                return mediaSize.asLandscape();
            case "PORTRAIT":
                return mediaSize.asPortrait();
            default:
                throw CustomExceptions.ORIENTATION_INVALID;
        }
    }

    private static long getTimeoutFromCall(@NonNull PluginCall call) {
        return call.getInt("timeout", 30000);
    }
}
