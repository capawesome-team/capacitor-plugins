package io.capawesome.capacitorjs.plugins.liveupdate.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class DownloadBundleProgressEvent implements Result {

    @NonNull
    private final String bundleId;

    private final long downloadedBytes;
    private final long totalBytes;
    private final double progress;

    public DownloadBundleProgressEvent(@NonNull String bundleId, long downloadedBytes, long totalBytes) {
        this.bundleId = bundleId;
        this.downloadedBytes = downloadedBytes;
        this.totalBytes = totalBytes;
        this.progress = (double) downloadedBytes / (double) totalBytes;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bundleId", bundleId);
        result.put("downloadedBytes", downloadedBytes);
        result.put("progress", progress);
        result.put("totalBytes", totalBytes);
        return result;
    }
}
