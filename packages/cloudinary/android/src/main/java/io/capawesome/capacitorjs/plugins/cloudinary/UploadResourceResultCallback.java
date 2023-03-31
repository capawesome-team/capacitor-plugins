package io.capawesome.capacitorjs.plugins.cloudinary;

import java.util.Map;

public interface UploadResourceResultCallback {
    void success(Map resultData);
    void error(String message);
}
