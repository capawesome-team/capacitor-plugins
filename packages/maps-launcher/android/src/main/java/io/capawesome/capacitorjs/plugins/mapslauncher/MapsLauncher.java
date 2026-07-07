package io.capawesome.capacitorjs.plugins.mapslauncher;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.options.Destination;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.options.NavigateOptions;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.results.GetAvailableAppsResult;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.results.GetDefaultAppResult;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;

public class MapsLauncher {

    public static final String APP_GOOGLE_MAPS = "googleMaps";
    public static final String APP_WAZE = "waze";
    public static final String PACKAGE_GOOGLE_MAPS = "com.google.android.apps.maps";
    public static final String PACKAGE_WAZE = "com.waze";

    @NonNull
    private final MapsLauncherPlugin plugin;

    public MapsLauncher(@NonNull MapsLauncherPlugin plugin) {
        this.plugin = plugin;
    }

    public void getAvailableApps(@NonNull NonEmptyResultCallback<GetAvailableAppsResult> callback) {
        List<String> apps = new ArrayList<>();
        if (isPackageInstalled(PACKAGE_GOOGLE_MAPS)) {
            apps.add(APP_GOOGLE_MAPS);
        }
        if (isPackageInstalled(PACKAGE_WAZE)) {
            apps.add(APP_WAZE);
        }
        callback.success(new GetAvailableAppsResult(apps));
    }

    public void getDefaultApp(@NonNull NonEmptyResultCallback<GetDefaultAppResult> callback) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=0,0"));
        ResolveInfo resolveInfo = getContext().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String app = null;
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            app = mapPackageToApp(resolveInfo.activityInfo.packageName);
        }
        callback.success(new GetDefaultAppResult(app));
    }

    public void navigate(@NonNull NavigateOptions options, @NonNull EmptyCallback callback) throws Exception {
        String app = options.getApp();
        if (app != null) {
            String packageName = getPackageForApp(app);
            if (packageName == null || !isPackageInstalled(packageName)) {
                throw CustomExceptions.APP_NOT_AVAILABLE;
            }
        }
        Intent intent = createIntent(options);
        try {
            plugin.getActivity().startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            throw CustomExceptions.LAUNCH_FAILED;
        }
        callback.success();
    }

    @NonNull
    private Intent createDefaultIntent(@NonNull Destination destination) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(formatDestination(destination))));
    }

    @NonNull
    private Intent createGoogleMapsIntent(@NonNull Destination destination, @Nullable Destination start, @Nullable String travelMode) {
        Uri uri;
        if (start == null) {
            String value =
                "google.navigation:q=" + Uri.encode(formatDestination(destination)) + "&mode=" + mapTravelModeToGoogleMode(travelMode);
            uri = Uri.parse(value);
        } else {
            String value =
                "https://www.google.com/maps/dir/?api=1" +
                "&origin=" +
                Uri.encode(formatDestination(start)) +
                "&destination=" +
                Uri.encode(formatDestination(destination)) +
                "&travelmode=" +
                mapTravelModeToGoogleUrlMode(travelMode);
            uri = Uri.parse(value);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(PACKAGE_GOOGLE_MAPS);
        return intent;
    }

    @NonNull
    private Intent createIntent(@NonNull NavigateOptions options) {
        String app = options.getApp();
        if (APP_GOOGLE_MAPS.equals(app)) {
            return createGoogleMapsIntent(options.getDestination(), options.getStart(), options.getTravelMode());
        } else if (APP_WAZE.equals(app)) {
            return createWazeIntent(options.getDestination());
        } else {
            return createDefaultIntent(options.getDestination());
        }
    }

    @NonNull
    private Intent createWazeIntent(@NonNull Destination destination) {
        String query = destination.hasCoordinates() ? "ll=" : "q=";
        String value = "https://waze.com/ul?" + query + Uri.encode(formatDestination(destination)) + "&navigate=yes";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
        intent.setPackage(PACKAGE_WAZE);
        return intent;
    }

    @NonNull
    private String formatDestination(@NonNull Destination destination) {
        if (destination.hasCoordinates()) {
            return destination.getLatitude() + "," + destination.getLongitude();
        }
        String address = destination.getAddress();
        return address == null ? "" : address;
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }

    @Nullable
    private String getPackageForApp(@NonNull String app) {
        switch (app) {
            case APP_GOOGLE_MAPS:
                return PACKAGE_GOOGLE_MAPS;
            case APP_WAZE:
                return PACKAGE_WAZE;
            default:
                return null;
        }
    }

    private boolean isPackageInstalled(@NonNull String packageName) {
        try {
            getContext().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException exception) {
            return false;
        }
    }

    @Nullable
    private String mapPackageToApp(@NonNull String packageName) {
        switch (packageName) {
            case PACKAGE_GOOGLE_MAPS:
                return APP_GOOGLE_MAPS;
            case PACKAGE_WAZE:
                return APP_WAZE;
            default:
                return null;
        }
    }

    @NonNull
    private String mapTravelModeToGoogleMode(@Nullable String travelMode) {
        if (travelMode == null) {
            return "d";
        }
        switch (travelMode) {
            case "walking":
                return "w";
            case "bicycling":
                return "b";
            case "transit":
                return "l";
            default:
                return "d";
        }
    }

    @NonNull
    private String mapTravelModeToGoogleUrlMode(@Nullable String travelMode) {
        if (travelMode == null) {
            return "driving";
        }
        switch (travelMode) {
            case "walking":
            case "bicycling":
            case "transit":
                return travelMode;
            default:
                return "driving";
        }
    }
}
