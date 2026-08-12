package io.capawesome.capacitorjs.plugins.applauncher;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.applauncher.classes.options.CanOpenUrlOptions;
import io.capawesome.capacitorjs.plugins.applauncher.classes.options.OpenUrlOptions;
import io.capawesome.capacitorjs.plugins.applauncher.classes.results.CanOpenUrlResult;
import io.capawesome.capacitorjs.plugins.applauncher.classes.results.OpenUrlResult;
import io.capawesome.capacitorjs.plugins.applauncher.interfaces.NonEmptyResultCallback;

public class AppLauncher {

    @NonNull
    private final AppLauncherPlugin plugin;

    public AppLauncher(@NonNull AppLauncherPlugin plugin) {
        this.plugin = plugin;
    }

    public void canOpenUrl(@NonNull CanOpenUrlOptions options, @NonNull NonEmptyResultCallback<CanOpenUrlResult> callback) {
        boolean value = canOpenUrl(options.getUrl());
        callback.success(new CanOpenUrlResult(value));
    }

    public void openUrl(@NonNull OpenUrlOptions options, @NonNull NonEmptyResultCallback<OpenUrlResult> callback) {
        boolean completed = openUrl(options.getUrl());
        callback.success(new OpenUrlResult(completed));
    }

    private boolean canOpenUrl(@NonNull String url) {
        if (isPackageInstalled(url)) {
            return true;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        ResolveInfo resolveInfo = getContext().getPackageManager().resolveActivity(intent, 0);
        return resolveInfo != null;
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }

    private boolean isPackageInstalled(@NonNull String packageName) {
        try {
            getContext().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException exception) {
            return false;
        }
    }

    private boolean openUrl(@NonNull String url) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getContext().startActivity(viewIntent);
            return true;
        } catch (ActivityNotFoundException exception) {
            // Fall back to launching the app by its package name.
        }
        Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage(url);
        if (launchIntent == null) {
            return false;
        }
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getContext().startActivity(launchIntent);
            return true;
        } catch (ActivityNotFoundException exception) {
            return false;
        }
    }
}
