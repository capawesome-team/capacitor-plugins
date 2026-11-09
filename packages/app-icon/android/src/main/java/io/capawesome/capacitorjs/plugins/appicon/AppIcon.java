package io.capawesome.capacitorjs.plugins.appicon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.appicon.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.appicon.classes.options.ResetIconOptions;
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
        ResolveInfo defaultComponent = getDefaultLauncherComponent(launcherComponents);
        String defaultIconName = defaultComponent == null ? null : getIconName(defaultComponent);
        String activeIconName = getActiveIconName(launcherComponents);
        boolean isDefault = activeIconName == null || activeIconName.equals(defaultIconName);
        return new GetCurrentIconResult(isDefault ? null : activeIconName);
    }

    @NonNull
    public IsAvailableResult isAvailable() {
        return new IsAvailableResult(true);
    }

    public void resetIcon(@NonNull ResetIconOptions options) throws Exception {
        List<ResolveInfo> launcherComponents = getLauncherComponents();
        ResolveInfo defaultComponent = getDefaultLauncherComponent(launcherComponents);
        if (defaultComponent == null) {
            throw CustomExceptions.CHANGE_FAILED;
        }
        if (options.getRelaunch()) {
            relaunchWithIcon(launcherComponents, defaultComponent);
        } else {
            setActiveIcon(launcherComponents, defaultComponent);
        }
    }

    public void setIcon(@NonNull SetIconOptions options) throws Exception {
        List<ResolveInfo> launcherComponents = getLauncherComponents();
        ResolveInfo newComponent = findLauncherComponent(launcherComponents, options.getIcon());
        if (newComponent == null) {
            throw CustomExceptions.ICON_NOT_FOUND;
        }
        if (options.getRelaunch()) {
            relaunchWithIcon(launcherComponents, newComponent);
        } else {
            setActiveIcon(launcherComponents, newComponent);
        }
    }

    private void disableOtherIcons(@NonNull List<ResolveInfo> launcherComponents, @NonNull ResolveInfo newComponent) {
        String icon = getIconName(newComponent);
        for (ResolveInfo info : launcherComponents) {
            if (!getIconName(info).equals(icon)) {
                setComponentEnabledSetting(info, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
            }
        }
    }

    @Nullable
    private ResolveInfo findLauncherComponent(@NonNull List<ResolveInfo> launcherComponents, @NonNull String icon) {
        for (ResolveInfo info : launcherComponents) {
            if (getIconName(info).equals(icon)) {
                return info;
            }
        }
        return null;
    }

    @Nullable
    private String getActiveIconName(@NonNull List<ResolveInfo> launcherComponents) {
        PackageManager packageManager = plugin.getContext().getPackageManager();
        for (ResolveInfo info : launcherComponents) {
            int state = packageManager.getComponentEnabledSetting(getComponentName(info));
            boolean isEnabled =
                state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                (state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT && info.activityInfo.enabled);
            if (isEnabled) {
                return getIconName(info);
            }
        }
        return null;
    }

    @NonNull
    private ComponentName getComponentName(@NonNull ResolveInfo info) {
        return new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
    }

    @Nullable
    private ResolveInfo getDefaultLauncherComponent(@NonNull List<ResolveInfo> launcherComponents) {
        for (ResolveInfo info : launcherComponents) {
            if (info.activityInfo.enabled) {
                return info;
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

    /**
     * The system closes every task that was started from a launcher component once that
     * component is disabled. A `singleTask` activity would reuse the task that was started
     * from the old component, so that task is removed first and the new component is started
     * in a fresh task before the other components are disabled.
     */
    private void relaunchWithIcon(@NonNull List<ResolveInfo> launcherComponents, @NonNull ResolveInfo newComponent) {
        Activity activity = plugin.getActivity();
        setComponentEnabledSetting(newComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        activity.finishAndRemoveTask();
        startNewTask(newComponent);
        disableOtherIcons(launcherComponents, newComponent);
    }

    private void setActiveIcon(@NonNull List<ResolveInfo> launcherComponents, @NonNull ResolveInfo newComponent) {
        setComponentEnabledSetting(newComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        disableOtherIcons(launcherComponents, newComponent);
    }

    private void setComponentEnabledSetting(@NonNull ResolveInfo info, int newState) {
        PackageManager packageManager = plugin.getContext().getPackageManager();
        packageManager.setComponentEnabledSetting(getComponentName(info), newState, PackageManager.DONT_KILL_APP);
    }

    private void startNewTask(@NonNull ResolveInfo component) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(getComponentName(component));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        plugin.getContext().getApplicationContext().startActivity(intent);
    }
}
