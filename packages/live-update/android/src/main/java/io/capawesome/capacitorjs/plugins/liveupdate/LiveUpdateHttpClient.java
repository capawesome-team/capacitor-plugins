package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.DownloadProgressCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
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
    private String deviceId;

    @NonNull
    private final OkHttpClient okHttpClient;

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
        int httpTimeout = config.getHttpTimeout();

        // Increase max requests per host to allow multiple parallel downloads from the same host
        okhttp3.Dispatcher dispatcher = new okhttp3.Dispatcher();
        dispatcher.setMaxRequestsPerHost(30);

        this.okHttpClient = new OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .connectTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .build();
    }

    public void setDeviceId(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    public Call enqueue(String url, NonEmptyCallback<Response> callback) {
        Request.Builder builder = new Request.Builder().url(url);
        if (deviceId != null) {
            builder.addHeader("X-Capawesome-Device-Id", deviceId);
        }
        Request request = builder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(
            new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    callback.success(response);
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.error(new Exception(e));
                }
            }
        );
        return call;
    }

    public static void writeResponseBodyToFile(ResponseBody body, File file, @Nullable DownloadProgressCallback callback)
        throws IOException {
        long contentLength = body.contentLength();
        BufferedSource source = body.source();
        BufferedSink sink = Okio.buffer(Okio.sink(file));
        Buffer sinkBuffer = sink.getBuffer();
        long totalBytesRead = 0;
        int bufferSize = 8 * 1024;
        for (long bytesRead; (bytesRead = source.read(sinkBuffer, bufferSize)) != -1;) {
            sink.emit();
            totalBytesRead += bytesRead;
            if (callback != null) {
                callback.onProgress(totalBytesRead, contentLength);
            }
        }
        sink.flush();
        sink.close();
        source.close();
    }
}
