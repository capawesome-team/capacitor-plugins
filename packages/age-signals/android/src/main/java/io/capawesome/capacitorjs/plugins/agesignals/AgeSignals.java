package io.capawesome.capacitorjs.plugins.agesignals;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.play.agesignals.AgeSignalsManager;
import com.google.android.play.agesignals.AgeSignalsManagerFactory;
import com.google.android.play.agesignals.AgeSignalsRequest;
import com.google.android.play.agesignals.AgeSignalsResult;
import io.capawesome.capacitorjs.plugins.agesignals.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.results.CheckAgeSignalsResult;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.NonEmptyResultCallback;

public class AgeSignals {

    @NonNull
    private final AgeSignalsPlugin plugin;

    public AgeSignals(@NonNull AgeSignalsPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkAgeSignals(@NonNull NonEmptyResultCallback<CheckAgeSignalsResult> callback) {
        AgeSignalsManager manager = AgeSignalsManagerFactory.create(plugin.getActivity());
        AgeSignalsRequest request = AgeSignalsRequest.builder().build();

        Task<AgeSignalsResult> task = manager.checkAgeSignals(request);
        task.addOnSuccessListener(ageSignalsResult -> {
            try {
                CheckAgeSignalsResult result = new CheckAgeSignalsResult(ageSignalsResult);
                callback.success(result);
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
        task.addOnFailureListener(exception -> {
            Exception mappedException = mapErrorCodeToException(exception);
            callback.error(mappedException);
        });
    }

    @NonNull
    private Exception mapErrorCodeToException(@NonNull Exception exception) {
        if (!(exception instanceof com.google.android.gms.common.api.ApiException)) {
            return exception;
        }

        com.google.android.gms.common.api.ApiException apiException = (com.google.android.gms.common.api.ApiException) exception;
        int statusCode = apiException.getStatusCode();

        switch (statusCode) {
            case 25000:
                return CustomExceptions.API_NOT_AVAILABLE;
            case 25001:
                return CustomExceptions.PLAY_STORE_NOT_FOUND;
            case 25002:
                return CustomExceptions.NETWORK_ERROR;
            case 25003:
                return CustomExceptions.PLAY_SERVICES_NOT_FOUND;
            case 25004:
                return CustomExceptions.CANNOT_BIND_TO_SERVICE;
            case 25005:
                return CustomExceptions.PLAY_STORE_VERSION_OUTDATED;
            case 25006:
                return CustomExceptions.PLAY_SERVICES_VERSION_OUTDATED;
            case 25007:
                return CustomExceptions.CLIENT_TRANSIENT_ERROR;
            case 25008:
                return CustomExceptions.APP_NOT_OWNED;
            case 25009:
                return CustomExceptions.INTERNAL_ERROR;
            default:
                return exception;
        }
    }
}
