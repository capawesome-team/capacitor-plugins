package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class LiveUpdateHttpClient {

    @NonNull
    private final LiveUpdateConfig config;

    @Nullable
    public static String getChecksumFromResponse(Response response) {
        String checksum = response.header("X-Checksum");
        if (checksum == null) {
            return null;
        }
        return checksum;
    }

    @Nullable
    public static String getSignatureFromResponse(Response response) {
        String signature = response.header("X-Signature");
        if (signature == null) {
            return null;
        }
        return signature;
    }

    public LiveUpdateHttpClient(@NonNull LiveUpdateConfig config) {
        this.config = config;
    }

    public Response execute(String url) throws IOException {
        int httpTimeout = config.getHttpTimeout();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .build();
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request).execute();
    }

    public static void writeResponseBodyToFile(ResponseBody body, File file) throws IOException {
        long contentLength = body.contentLength();
        BufferedSource source = body.source();
        BufferedSink sink = Okio.buffer(Okio.sink(file));
        Buffer sinkBuffer = sink.getBuffer();
        long totalBytesRead = 0;
        int bufferSize = 8 * 1024;
        for (long bytesRead; (bytesRead = source.read(sinkBuffer, bufferSize)) != -1;) {
            sink.emit();
            totalBytesRead += bytesRead;
            int progress = (int) ((totalBytesRead * 100) / contentLength);
            Logger.debug(LiveUpdatePlugin.TAG, "Downloading progress: " + progress + "%");
        }
        sink.flush();
        sink.close();
        source.close();
    }
}
