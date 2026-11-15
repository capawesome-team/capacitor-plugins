package io.capawesome.capacitorjs.plugins.liveupdate.interfaces;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

public interface ResponseBodyHandler {
    void handle(@NonNull ResponseBody body) throws Exception;
}
