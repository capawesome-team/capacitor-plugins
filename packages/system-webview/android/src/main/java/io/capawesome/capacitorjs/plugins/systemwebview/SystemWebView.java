package io.capawesome.capacitorjs.plugins.systemwebview;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.webkit.WebViewCompat;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.options.IsUpdateRequiredOptions;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.results.IsUpdateRequiredResult;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.NonEmptyResultCallback;

public class SystemWebView {

    @NonNull
    private final SystemWebViewPlugin plugin;

    public SystemWebView(@NonNull SystemWebViewPlugin plugin) {
        this.plugin = plugin;
    }

    public void getInfo(@NonNull NonEmptyResultCallback<GetInfoResult> callback) throws Exception {
        PackageInfo packageInfo = getCurrentWebViewPackage();
        String versionName = packageInfo.versionName;
        if (versionName == null) {
            throw CustomExceptions.WEB_VIEW_PACKAGE_UNAVAILABLE;
        }
        Integer majorVersion = parseMajorVersion(versionName);
        callback.success(new GetInfoResult(packageInfo.packageName, versionName, majorVersion));
    }

    public void isUpdateRequired(
        @NonNull IsUpdateRequiredOptions options,
        @NonNull NonEmptyResultCallback<IsUpdateRequiredResult> callback
    ) throws Exception {
        PackageInfo packageInfo = getCurrentWebViewPackage();
        Integer majorVersion = parseMajorVersion(packageInfo.versionName);
        if (majorVersion == null) {
            throw CustomExceptions.VERSION_UNPARSEABLE;
        }
        boolean required = majorVersion < options.getMinMajorVersion();
        callback.success(new IsUpdateRequiredResult(required));
    }

    public void openAppStore(@NonNull EmptyCallback callback) throws Exception {
        PackageInfo packageInfo = getCurrentWebViewPackage();
        String packageName = packageInfo.packageName;
        try {
            startAppStoreIntent("market://details?id=" + packageName);
        } catch (ActivityNotFoundException exception) {
            try {
                startAppStoreIntent("https://play.google.com/store/apps/details?id=" + packageName);
            } catch (ActivityNotFoundException fallbackException) {
                throw CustomExceptions.OPEN_FAILED;
            }
        }
        callback.success();
    }

    @NonNull
    private PackageInfo getCurrentWebViewPackage() throws Exception {
        Context context = plugin.getContext();
        PackageInfo packageInfo = WebViewCompat.getCurrentWebViewPackage(context);
        if (packageInfo == null) {
            throw CustomExceptions.WEB_VIEW_PACKAGE_UNAVAILABLE;
        }
        return packageInfo;
    }

    @Nullable
    private Integer parseMajorVersion(@Nullable String versionName) {
        if (versionName == null) {
            return null;
        }
        int separatorIndex = versionName.indexOf('.');
        String majorPart = separatorIndex >= 0 ? versionName.substring(0, separatorIndex) : versionName;
        try {
            return Integer.parseInt(majorPart.trim());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private void startAppStoreIntent(@NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        plugin.getContext().startActivity(intent);
    }
}
