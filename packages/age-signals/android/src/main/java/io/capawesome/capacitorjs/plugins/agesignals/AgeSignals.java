package io.capawesome.capacitorjs.plugins.agesignals;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.android.play.agesignals.AgeSignalsAccessRequest;
import com.google.android.play.agesignals.AgeSignalsAccessResult;
import com.google.android.play.agesignals.AgeSignalsException;
import com.google.android.play.agesignals.AgeSignalsManager;
import com.google.android.play.agesignals.AgeSignalsManagerFactory;
import com.google.android.play.agesignals.AgeSignalsRequest;
import com.google.android.play.agesignals.AgeSignalsResult;
import com.google.android.play.agesignals.model.AgeSignalsErrorCode;
import com.google.android.play.agesignals.model.AgeSignalsStatus;
import com.google.android.play.agesignals.testing.FakeAgeSignalsManager;
import io.capawesome.capacitorjs.plugins.agesignals.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetNextAgeSignalsAccessResultOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetNextAgeSignalsExceptionOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetNextAgeSignalsResultOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetUseFakeManagerOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.results.GetAgeRangeResult;
import io.capawesome.capacitorjs.plugins.agesignals.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.agesignals.classes.results.RequestAgeRangeResult;
import io.capawesome.capacitorjs.plugins.agesignals.enums.AgeRangeStatus;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.NonEmptyResultCallback;

public class AgeSignals {

    private static final String PLAY_STORE_PACKAGE_NAME = "com.android.vending";

    @NonNull
    private final AgeSignalsPlugin plugin;

    @Nullable
    private FakeAgeSignalsManager fakeManager = null;

    public AgeSignals(@NonNull AgeSignalsPlugin plugin) {
        this.plugin = plugin;
    }

    public void getAgeRange(@NonNull NonEmptyResultCallback<GetAgeRangeResult> callback) {
        Task<AgeSignalsResult> task = getManager().checkAgeSignals(AgeSignalsRequest.builder().build());
        task.addOnSuccessListener(result -> callback.success(new GetAgeRangeResult(result)));
        task.addOnFailureListener(exception -> callback.error(mapException(exception)));
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        callback.success(new IsAvailableResult(isPlayStoreInstalled()));
    }

    public void requestAgeRange(@NonNull NonEmptyResultCallback<RequestAgeRangeResult> callback) {
        AgeSignalsManager manager = getManager();
        AgeSignalsAccessRequest request = AgeSignalsAccessRequest.builder().setActivity(plugin.getActivity()).build();

        Task<AgeSignalsAccessResult> task = manager.requestAgeSignalsAccess(request);
        task.addOnSuccessListener(accessResult -> {
            AgeRangeStatus status = mapAgeSignalsStatus(accessResult.ageSignalsStatus());
            if (status != AgeRangeStatus.SHARED) {
                callback.success(new RequestAgeRangeResult(status, null));
                return;
            }
            Task<AgeSignalsResult> checkTask = manager.checkAgeSignals(AgeSignalsRequest.builder().build());
            checkTask.addOnSuccessListener(result -> callback.success(new RequestAgeRangeResult(status, new GetAgeRangeResult(result))));
            checkTask.addOnFailureListener(exception -> callback.error(mapException(exception)));
        });
        task.addOnFailureListener(exception -> callback.error(mapException(exception)));
    }

    public void setNextAgeSignalsAccessResult(@NonNull SetNextAgeSignalsAccessResultOptions options, @NonNull EmptyCallback callback) {
        try {
            getFakeManager().setNextAgeSignalsAccessResult(options.buildAgeSignalsAccessResult());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setNextAgeSignalsException(@NonNull SetNextAgeSignalsExceptionOptions options, @NonNull EmptyCallback callback) {
        try {
            getFakeManager().setNextAgeSignalsException(options.buildAgeSignalsException());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setNextAgeSignalsResult(@NonNull SetNextAgeSignalsResultOptions options, @NonNull EmptyCallback callback) {
        try {
            getFakeManager().setNextAgeSignalsResult(options.buildAgeSignalsResult());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setNextRequestAgeSignalsAccessException(
        @NonNull SetNextAgeSignalsExceptionOptions options,
        @NonNull EmptyCallback callback
    ) {
        try {
            getFakeManager().setNextRequestAgeSignalsAccessException(options.buildAgeSignalsException());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setUseFakeManager(@NonNull SetUseFakeManagerOptions options, @NonNull EmptyCallback callback) {
        try {
            assertFakeManagerAllowed();
            this.fakeManager = options.getUseFake() ? new FakeAgeSignalsManager() : null;
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * The fake manager must never be reachable in a release build, because it allows
     * age signals to be forged from the web layer.
     */
    private void assertFakeManagerAllowed() throws Exception {
        ApplicationInfo applicationInfo = plugin.getContext().getApplicationInfo();
        if ((applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == 0) {
            throw CustomExceptions.FAKE_MANAGER_NOT_ALLOWED;
        }
    }

    @NonNull
    private FakeAgeSignalsManager getFakeManager() throws Exception {
        assertFakeManagerAllowed();
        if (fakeManager == null) {
            throw CustomExceptions.FAKE_MANAGER_NOT_ENABLED;
        }
        return fakeManager;
    }

    @NonNull
    private AgeSignalsManager getManager() {
        if (fakeManager != null) {
            return fakeManager;
        }
        return AgeSignalsManagerFactory.create(plugin.getContext());
    }

    private boolean isPlayStoreInstalled() {
        try {
            plugin.getContext().getPackageManager().getPackageInfo(PLAY_STORE_PACKAGE_NAME, 0);
            return true;
        } catch (PackageManager.NameNotFoundException exception) {
            return false;
        }
    }

    @NonNull
    private AgeRangeStatus mapAgeSignalsStatus(@Nullable Integer status) {
        if (status == null) {
            return AgeRangeStatus.UNSPECIFIED;
        }
        switch (status) {
            case AgeSignalsStatus.SHARED:
                return AgeRangeStatus.SHARED;
            case AgeSignalsStatus.NOT_SHARED:
                return AgeRangeStatus.NOT_SHARED;
            case AgeSignalsStatus.VERIFICATION_REQUIRED:
                return AgeRangeStatus.VERIFICATION_REQUIRED;
            default:
                return AgeRangeStatus.UNSPECIFIED;
        }
    }

    /**
     * Not every failure is an {@link AgeSignalsException}. The service also fails pending
     * tasks with a plain {@code RemoteException} when the binder dies, so unmapped
     * exceptions are passed through unchanged.
     */
    @NonNull
    private Exception mapException(@NonNull Exception exception) {
        if (!(exception instanceof AgeSignalsException)) {
            return exception;
        }
        switch (((AgeSignalsException) exception).getErrorCode()) {
            case AgeSignalsErrorCode.API_NOT_AVAILABLE:
                return CustomExceptions.API_NOT_AVAILABLE;
            case AgeSignalsErrorCode.APP_NOT_OWNED:
                return CustomExceptions.APP_NOT_OWNED;
            case AgeSignalsErrorCode.CANNOT_BIND_TO_SERVICE:
                return CustomExceptions.CANNOT_BIND_TO_SERVICE;
            case AgeSignalsErrorCode.CLIENT_TRANSIENT_ERROR:
                return CustomExceptions.CLIENT_TRANSIENT_ERROR;
            case AgeSignalsErrorCode.INTERNAL_ERROR:
                return CustomExceptions.INTERNAL_ERROR;
            case AgeSignalsErrorCode.NETWORK_ERROR:
                return CustomExceptions.NETWORK_ERROR;
            case AgeSignalsErrorCode.PLAY_SERVICES_NOT_FOUND:
                return CustomExceptions.PLAY_SERVICES_NOT_FOUND;
            case AgeSignalsErrorCode.PLAY_SERVICES_VERSION_OUTDATED:
                return CustomExceptions.PLAY_SERVICES_VERSION_OUTDATED;
            case AgeSignalsErrorCode.PLAY_STORE_NOT_FOUND:
                return CustomExceptions.PLAY_STORE_NOT_FOUND;
            case AgeSignalsErrorCode.PLAY_STORE_VERSION_OUTDATED:
                return CustomExceptions.PLAY_STORE_VERSION_OUTDATED;
            case AgeSignalsErrorCode.SDK_VERSION_OUTDATED:
                return CustomExceptions.SDK_VERSION_OUTDATED;
            default:
                return exception;
        }
    }
}
