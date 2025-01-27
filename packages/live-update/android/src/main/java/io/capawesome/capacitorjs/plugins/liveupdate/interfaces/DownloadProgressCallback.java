package io.capawesome.capacitorjs.plugins.liveupdate.interfaces;

public interface DownloadProgressCallback {
    void onProgress(long downloadedBytes, long totalBytes);
}
