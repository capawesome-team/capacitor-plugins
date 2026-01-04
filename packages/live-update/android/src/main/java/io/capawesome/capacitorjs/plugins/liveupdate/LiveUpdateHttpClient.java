package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.DownloadProgressCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Security;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
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
import org.conscrypt.Conscrypt;

public class LiveUpdateHttpClient {

    @NonNull
    private final LiveUpdateConfig config;

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

        // Install Conscrypt as the preferred security provider for better SSL/TLS support on older Android versions
        try {
            Security.insertProviderAt(Conscrypt.newProvider(), 1);
        } catch (Exception e) {
            Logger.warn("LiveUpdateHttpClient", "Failed to install Conscrypt security provider: " + e.getMessage());
        }

        // Increase max requests per host to allow multiple parallel downloads from the same host
        okhttp3.Dispatcher dispatcher = new okhttp3.Dispatcher();
        dispatcher.setMaxRequestsPerHost(30);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .connectTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(httpTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(httpTimeout, TimeUnit.MILLISECONDS);

        // Configure OkHttp to use Conscrypt for SSL/TLS
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS", Conscrypt.newProvider());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
            );
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException(
                    "Unexpected default trust managers:" + Arrays.toString(trustManagers)
                );
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            Logger.warn("LiveUpdateHttpClient", "Failed to configure Conscrypt SSL: " + e.getMessage());
        }

        this.okHttpClient = builder.build();
    }

    public Call enqueue(String url, NonEmptyCallback<Response> callback) {
        Request request = new Request.Builder().url(url).build();

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
