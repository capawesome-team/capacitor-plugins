package io.capawesome.capacitorjs.plugins.appupdate;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

@CapacitorPlugin(name = "AppUpdate", requestCodes = { AppUpdatePlugin.REQUEST_IMMEDIATE_UPDATE, AppUpdatePlugin.REQUEST_FLEXIBLE_UPDATE })
public class AppUpdatePlugin extends Plugin {

    public static final String TAG = "AppUpdate";
    public static final String ERROR_GET_APP_INFO_FAILED = "Unable to get app info.";
    /** Update result: update ok. */
    public static final int UPDATE_OK = 0;
    /** Update result: update canceled. */
    public static final int UPDATE_CANCELED = 1;
    /** Update result: update failed. */
    public static final int UPDATE_FAILED = 2;
    /** Update result: update not available. */
    public static final int UPDATE_NOT_AVAILABLE = 3;
    /** Update result: update not allowed. */
    public static final int UPDATE_NOT_ALLOWED = 4;
    /** Update result: update info missing. */
    public static final int UPDATE_INFO_MISSING = 5;
    /** Request code for immediate update */
    public static final int REQUEST_IMMEDIATE_UPDATE = 10;
    /** Request code for flexible update */
    public static final int REQUEST_FLEXIBLE_UPDATE = 11;
    public static final String ERROR_GOOGLE_PLAY_SERVICES_UNAVAILABLE = "GooglePlayServices are not available.";
    private AppUpdateManager appUpdateManager;
    private AppUpdateInfo appUpdateInfo;
    private InstallStateUpdatedListener listener;
    private PluginCall savedPluginCall;

    public void load() {
        this.appUpdateManager = AppUpdateManagerFactory.create(this.getContext());
    }

    @PluginMethod
    public void getAppUpdateInfo(PluginCall call) {
        try {
            boolean isGooglePlayServicesAvailable = this.isGooglePlayServicesAvailable();
            if (!isGooglePlayServicesAvailable) {
                call.reject(ERROR_GOOGLE_PLAY_SERVICES_UNAVAILABLE);
                return;
            }
            Task<AppUpdateInfo> appUpdateInfoTask = this.appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(
                appUpdateInfo -> {
                    this.appUpdateInfo = appUpdateInfo;
                    PackageInfo pInfo;
                    try {
                        pInfo = this.getPackageInfo();
                    } catch (PackageManager.NameNotFoundException e) {
                        call.reject(ERROR_GET_APP_INFO_FAILED);
                        return;
                    }
                    JSObject ret = new JSObject();
                    ret.put("currentVersionName", pInfo.versionName);
                    ret.put("currentVersionCode", String.valueOf(pInfo.versionCode));
                    ret.put("availableVersionCode", String.valueOf(appUpdateInfo.availableVersionCode()));
                    ret.put("updateAvailability", appUpdateInfo.updateAvailability());
                    ret.put("updatePriority", appUpdateInfo.updatePriority());
                    ret.put("immediateUpdateAllowed", appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE));
                    ret.put("flexibleUpdateAllowed", appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE));
                    Integer clientVersionStalenessDays = appUpdateInfo.clientVersionStalenessDays();
                    if (clientVersionStalenessDays != null) {
                        ret.put("clientVersionStalenessDays", clientVersionStalenessDays);
                    }
                    ret.put("installStatus", appUpdateInfo.installStatus());
                    call.resolve(ret);
                }
            );
            appUpdateInfoTask.addOnFailureListener(
                failure -> {
                    String message = failure.getMessage();
                    call.reject(message);
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void openAppStore(PluginCall call) {
        try {
            String packageName = this.getContext().getPackageName();
            Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            try {
                this.getBridge().getActivity().startActivity(launchIntent);
            } catch (ActivityNotFoundException ex) {
                launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                this.getBridge().getActivity().startActivity(launchIntent);
            }
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void performImmediateUpdate(PluginCall call) {
        try {
            boolean ready = this.readyForUpdate(call, AppUpdateType.IMMEDIATE);
            if (!ready) {
                return;
            }
            savedPluginCall = call;
            try {
                this.appUpdateManager.startUpdateFlowForResult(
                        this.appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        getActivity(),
                        AppUpdatePlugin.REQUEST_IMMEDIATE_UPDATE
                    );
            } catch (IntentSender.SendIntentException e) {
                call.reject(e.getMessage());
            }
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void startFlexibleUpdate(PluginCall call) {
        try {
            boolean ready = this.readyForUpdate(call, AppUpdateType.FLEXIBLE);
            if (!ready) {
                return;
            }
            savedPluginCall = call;
            this.listener =
                state -> {
                    int installStatus = state.installStatus();
                    JSObject ret = new JSObject();
                    ret.put("installStatus", installStatus);
                    if (installStatus == InstallStatus.DOWNLOADING) {
                        ret.put("bytesDownloaded", state.bytesDownloaded());
                        ret.put("totalBytesToDownload", state.totalBytesToDownload());
                    }
                    notifyListeners("onFlexibleUpdateStateChange", ret);
                };
            this.appUpdateManager.registerListener(this.listener);
            this.appUpdateManager.startUpdateFlowForResult(
                    this.appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    getActivity(),
                    AppUpdatePlugin.REQUEST_FLEXIBLE_UPDATE
                );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void completeFlexibleUpdate(PluginCall call) {
        try {
            this.unregisterListener();
            this.appUpdateManager.completeUpdate();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.handleOnActivityResult(requestCode, resultCode, data);
            if (resultCode != RESULT_OK && requestCode == REQUEST_FLEXIBLE_UPDATE) {
                this.unregisterListener();
            }
            this.appUpdateInfo = null;
            if (savedPluginCall == null) {
                return;
            }
            JSObject ret = new JSObject();
            if (resultCode == RESULT_OK) {
                ret.put("code", UPDATE_OK);
            } else if (resultCode == RESULT_CANCELED) {
                ret.put("code", UPDATE_CANCELED);
            } else if (resultCode == RESULT_IN_APP_UPDATE_FAILED) {
                ret.put("code", UPDATE_FAILED);
            }
            savedPluginCall.resolve(ret);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            if (savedPluginCall == null) {
                return;
            }
            savedPluginCall.reject(exception.getMessage());
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(bridge.getContext());
        return resultCode == ConnectionResult.SUCCESS;
    }

    private PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        String packageName = this.getContext().getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return this.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0));
        } else {
            return this.getContext().getPackageManager().getPackageInfo(packageName, 0);
        }
    }

    private boolean readyForUpdate(PluginCall call, int appUpdateType) {
        JSObject ret = new JSObject();
        if (this.appUpdateInfo == null) {
            ret.put("code", UPDATE_INFO_MISSING);
            call.resolve(ret);
            return false;
        }
        if (this.appUpdateInfo.updateAvailability() != UpdateAvailability.UPDATE_AVAILABLE) {
            ret.put("code", UPDATE_NOT_AVAILABLE);
            call.resolve(ret);
            return false;
        }
        if (!this.appUpdateInfo.isUpdateTypeAllowed(appUpdateType)) {
            ret.put("code", UPDATE_NOT_ALLOWED);
            call.resolve(ret);
            return false;
        }
        return true;
    }

    private void unregisterListener() {
        if (this.listener == null || this.appUpdateManager == null) {
            return;
        }
        this.appUpdateManager.unregisterListener(this.listener);
        this.listener = null;
    }
}
