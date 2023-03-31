package io.capawesome.capacitorjs.plugins.cloudinary;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.UploadRequest;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Cloudinary {

    class ActiveDownload {

        public String path;
        public DownloadResourceResultCallback callback;

        ActiveDownload(String path, DownloadResourceResultCallback callback) {
            this.path = path;
            this.callback = callback;
        }
    }

    private HashMap<Long, ActiveDownload> activeDownloads = new HashMap<Long, ActiveDownload>();

    private CloudinaryPlugin plugin;

    public Cloudinary(CloudinaryPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(String cloudName) {
        HashMap config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("secure", true);
        MediaManager.init(plugin.getContext(), config);
    }

    public void uploadResource(
        String resourceType,
        String path,
        String uploadPreset,
        @Nullable String publicId,
        UploadResourceResultCallback callback
    ) {
        UploadRequest request = MediaManager.get().upload(Uri.parse(path)).unsigned(uploadPreset).option("resource_type", resourceType);
        if (publicId != null) {
            request.option("public_id", publicId);
        }
        request
            .callback(
                new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {}

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        callback.success(resultData);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        callback.error(error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {}
                }
            )
            .dispatch();
    }

    public void downloadResource(String url, DownloadResourceResultCallback callback) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true);
        DownloadManager downloadManager = (DownloadManager) plugin.getContext().getSystemService(DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);
        String path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName).getAbsolutePath();
        activeDownloads.put(downloadId, new ActiveDownload(path, callback));
    }

    public void handleDownloadCompleted(long downloadId) {
        ActiveDownload download = activeDownloads.get(downloadId);
        if (download == null) {
            return;
        }
        download.callback.success(download.path);
    }
}
