package io.capawesome.capacitorjs.plugins.photoeditor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import com.getcapacitor.Bridge;
import com.getcapacitor.Logger;
import java.io.File;
import java.net.URI;
import java.util.List;

public class PhotoEditor {

    private Bridge bridge;

    PhotoEditor(Bridge bridge) {
        this.bridge = bridge;
    }

    public Intent createEditPhotoIntent(String path) {
        try {
            File file = new File(new URI(path));
            String contextPackageName = bridge.getContext().getPackageName();
            Uri uri = FileProvider.getUriForFile(bridge.getActivity(), contextPackageName + ".fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
            intent.addFlags(flags);
            List<ResolveInfo> resolveInfoList = bridge
                .getContext()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resolveInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                bridge.getContext().grantUriPermission(packageName, uri, flags);
            }
            return intent;
        } catch (Exception exception) {
            Logger.error("createEditPhotoIntent failed.", exception);
            return null;
        }
    }
}
