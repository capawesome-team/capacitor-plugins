package io.capawesome.capacitorjs.plugins.cloudinary;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.getcapacitor.Logger;

public class DownloadBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                CloudinaryPlugin.onDownloadCompleted(downloadId);
            }
        } catch (Exception exception) {
            Logger.error(CloudinaryPlugin.TAG, exception.getMessage(), exception);
        }
    }
}
