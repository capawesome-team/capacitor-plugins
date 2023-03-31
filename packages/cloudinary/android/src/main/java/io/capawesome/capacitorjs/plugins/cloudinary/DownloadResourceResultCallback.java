package io.capawesome.capacitorjs.plugins.cloudinary;

public interface DownloadResourceResultCallback {
    void success(String path);
    void error(String message);
}
