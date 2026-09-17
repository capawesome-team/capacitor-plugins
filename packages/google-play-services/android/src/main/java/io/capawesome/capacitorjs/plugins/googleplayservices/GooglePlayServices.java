package io.capawesome.capacitorjs.plugins.googleplayservices;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.results.GetStatusResult;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.results.GetVersionResult;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.NonEmptyResultCallback;

public class GooglePlayServices {

    @NonNull
    private final GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

    @NonNull
    private final GooglePlayServicesPlugin plugin;

    public GooglePlayServices(@NonNull GooglePlayServicesPlugin plugin) {
        this.plugin = plugin;
    }

    public void getStatus(@NonNull NonEmptyResultCallback<GetStatusResult> callback) throws Exception {
        String status = getStatusFromStatusCode(getStatusCode());
        callback.success(new GetStatusResult(status));
    }

    public void getVersion(@NonNull NonEmptyResultCallback<GetVersionResult> callback) {
        int version = googleApiAvailability.getApkVersion(plugin.getContext());
        callback.success(new GetVersionResult(version));
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        boolean available = getStatusCode() == ConnectionResult.SUCCESS;
        callback.success(new IsAvailableResult(available));
    }

    public void makeAvailable(@NonNull EmptyCallback callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() ->
            googleApiAvailability
                .makeGooglePlayServicesAvailable(activity)
                .addOnSuccessListener(unused -> callback.success())
                .addOnFailureListener(exception -> callback.error(isCanceledByUser(exception) ? CustomExceptions.CANCELED : exception))
        );
    }

    private int getStatusCode() {
        return googleApiAvailability.isGooglePlayServicesAvailable(plugin.getContext());
    }

    @NonNull
    private String getStatusFromStatusCode(int statusCode) throws Exception {
        switch (statusCode) {
            case ConnectionResult.SUCCESS:
                return "SUCCESS";
            case ConnectionResult.SERVICE_MISSING:
                return "SERVICE_MISSING";
            case ConnectionResult.SERVICE_UPDATING:
                return "SERVICE_UPDATING";
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case ConnectionResult.SERVICE_DISABLED:
                return "SERVICE_DISABLED";
            case ConnectionResult.SERVICE_INVALID:
                return "SERVICE_INVALID";
            default:
                throw new Exception("Unknown status code: " + statusCode);
        }
    }

    private boolean isCanceledByUser(@NonNull Exception exception) {
        return exception instanceof ApiException && ((ApiException) exception).getStatusCode() == ConnectionResult.CANCELED;
    }
}
