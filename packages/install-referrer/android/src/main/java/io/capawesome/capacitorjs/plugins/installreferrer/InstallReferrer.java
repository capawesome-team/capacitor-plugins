package io.capawesome.capacitorjs.plugins.installreferrer;

import androidx.annotation.NonNull;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import io.capawesome.capacitorjs.plugins.installreferrer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.installreferrer.classes.results.GetInstallReferrerResult;
import io.capawesome.capacitorjs.plugins.installreferrer.interfaces.NonEmptyResultCallback;

public class InstallReferrer {

    @NonNull
    private final InstallReferrerPlugin plugin;

    public InstallReferrer(@NonNull InstallReferrerPlugin plugin) {
        this.plugin = plugin;
    }

    public void getInstallReferrer(@NonNull NonEmptyResultCallback<GetInstallReferrerResult> callback) {
        final InstallReferrerClient client = InstallReferrerClient.newBuilder(plugin.getContext()).build();
        client.startConnection(
            new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    handleSetupFinished(client, responseCode, callback);
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {}
            }
        );
    }

    private void handleSetupFinished(
        @NonNull InstallReferrerClient client,
        int responseCode,
        @NonNull NonEmptyResultCallback<GetInstallReferrerResult> callback
    ) {
        try {
            switch (responseCode) {
                case InstallReferrerClient.InstallReferrerResponse.OK:
                    ReferrerDetails details = client.getInstallReferrer();
                    GetInstallReferrerResult result = new GetInstallReferrerResult(
                        details.getGooglePlayInstantParam(),
                        details.getInstallBeginTimestampSeconds() * 1000,
                        details.getReferrerClickTimestampSeconds() * 1000,
                        details.getInstallReferrer()
                    );
                    callback.success(result);
                    break;
                case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                    callback.error(CustomExceptions.FEATURE_NOT_SUPPORTED);
                    break;
                case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR:
                    callback.error(CustomExceptions.DEVELOPER_ERROR);
                    break;
                default:
                    callback.error(CustomExceptions.SERVICE_UNAVAILABLE);
                    break;
            }
        } catch (Exception exception) {
            callback.error(exception);
        } finally {
            client.endConnection();
        }
    }
}
