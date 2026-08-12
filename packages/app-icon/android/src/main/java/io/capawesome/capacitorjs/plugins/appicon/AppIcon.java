package io.capawesome.capacitorjs.plugins.appicon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.appicon.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.appicon.classes.options.SetIconOptions;
import io.capawesome.capacitorjs.plugins.appicon.classes.results.GetCurrentIconResult;
import io.capawesome.capacitorjs.plugins.appicon.classes.results.IsAvailableResult;
import java.util.List;

public class AppIcon {

    @NonNull
    private final AppIconPlugin plugin;

    public AppIcon(@NonNull AppIconPlugin plugin) {
        this.plugin = plugin;
    }

    @NonNull
    public GetCurrentIconResult getCurrentIcon() {
        List<ResolveInfo> launcherComponents = getLauncherComponents();
        String defaultIconName = getDefaultIconName(launcherComponents);
        String activeIconName = getActiveIconName(launcherComponents);
        boolean isDefault = activeIconName == null || activeIconName.equals(defaultIconName);
        return new GetCurrentIconResult(isDefault ? null : activeIconName);
    }

    @NonNull
    public IsAvailableResult isAvailable() {
        return new IsAvailableResult(true);
    }

    public void resetIcon() throws Exception {
        List<ResolveInfo> launcherComponents = getLauncherComponents();
        String defaultIconName = getDefaultIconName(launcherComponents);
        if (defaultIconName == null) {
            throw CustomExceptions.CHANGE_FAILED;
        }
        setActiveIcon(launcherComponents, defaultIconName);
    }

    public void setIcon(@NonNull SetIconOptions options) throws Exception {
        String icon = options.getIcon();
        List<ResolveInfo> launcherComponents = getLauncherComponents();
        if (!hasIcon(launcherComponents, icon)) {
            throw CustomExceptions.ICON_NOT_FOUND;
        }
        setActiveIcon(launcherComponents, icon);
    }

    @Nullable
    private String getActiveIconName(@NonNull List<ResolveInfo> launcherComponents) {
        PackageManager packageManager = plugin.getContext().getPackageManager();
        for (ResolveInfo info : launcherComponents) {
            ComponentName componentName = new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
            int state = packageManager.getComponentEnabledSetting(componentName);
            boolean isEnabled =
                state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                (state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT && info.activityInfo.enabled);
            if (isEnabled) {
                return getIconName(info);
            }
        }
        return null;
    }

    @Nullable
    private String getDefaultIconName(@NonNull List<ResolveInfo> launcherComponents) {
        for (ResolveInfo info : launcherComponents) {
            if (info.activityInfo.enabled) {
                return getIconName(info);
            }
        }
        return null;
    }

    @NonNull
    private String getIconName(@NonNull ResolveInfo info) {
        String name = info.activityInfo.name;
        int lastDotIndex = name.lastIndexOf('.');
        return lastDotIndex == -1 ? name : name.substring(lastDotIndex + 1);
    }

    @NonNull
    private List<ResolveInfo> getLauncherComponents() {
        Context context = plugin.getContext();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(context.getPackageName());
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DISABLED_COMPONENTS);
    }

    private boolean hasIcon(@NonNull List<ResolveInfo> launcherComponents, @NonNull String icon) {
        for (ResolveInfo info : launcherComponents) {
            if (getIconName(info).equals(icon)) {
                return true;
            }
        }
        return false;
    }

    private void setActiveIcon(@NonNull List<ResolveInfo> launcherComponents, @NonNull String icon) {
        PackageManager packageManager = plugin.getContext().getPackageManager();
        for (ResolveInfo info : launcherComponents) {
            ComponentName componentName = new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
            int newState = getIconName(info).equals(icon)
                ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            packageManager.setComponentEnabledSetting(componentName, newState, PackageManager.DONT_KILL_APP);
        }
    }
}
