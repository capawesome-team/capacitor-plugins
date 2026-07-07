package io.capawesome.capacitorjs.plugins.androidsmsretriever;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.CustomException;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.options.RetrieveSmsOptions;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.results.RequestPhoneNumberResult;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.results.RetrieveSmsResult;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.interfaces.Result;

@CapacitorPlugin(name = "AndroidSmsRetriever")
public class AndroidSmsRetrieverPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String TAG = "AndroidSmsRetrieverPlugin";

    private AndroidSmsRetriever implementation;
    private ActivityResultLauncher<IntentSenderRequest> phoneNumberHintLauncher;
    private ActivityResultLauncher<Intent> smsConsentLauncher;

    @Override
    public void load() {
        implementation = new AndroidSmsRetriever(this);
        phoneNumberHintLauncher = getActivity()
            .registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), this::handlePhoneNumberHintResult);
        smsConsentLauncher = getActivity()
            .registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleSmsConsentResult);
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        if (implementation != null) {
            implementation.handleOnDestroy();
        }
    }

    public void launchPhoneNumberHintIntent(@NonNull PendingIntent pendingIntent) {
        IntentSenderRequest request = new IntentSenderRequest.Builder(pendingIntent).build();
        phoneNumberHintLauncher.launch(request);
    }

    public void launchSmsConsentIntent(@NonNull Intent intent) {
        smsConsentLauncher.launch(intent);
    }

    @PluginMethod
    public void requestPhoneNumber(PluginCall call) {
        try {
            if (!implementation.isGooglePlayServicesAvailable()) {
                rejectCallAsUnavailable(call);
                return;
            }

            NonEmptyResultCallback<RequestPhoneNumberResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull RequestPhoneNumberResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.requestPhoneNumber(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void retrieveSms(PluginCall call) {
        try {
            if (!implementation.isGooglePlayServicesAvailable()) {
                rejectCallAsUnavailable(call);
                return;
            }

            RetrieveSmsOptions options = new RetrieveSmsOptions(call);
            NonEmptyResultCallback<RetrieveSmsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull RetrieveSmsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.retrieveSms(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void handlePhoneNumberHintResult(@NonNull ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            implementation.handlePhoneNumberResult(result.getData());
        } else {
            implementation.handlePhoneNumberCanceled();
        }
    }

    private void handleSmsConsentResult(@NonNull ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            implementation.handleSmsConsentResult(result.getData());
        } else {
            implementation.handleSmsConsentCanceled();
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("Google Play services is not available on this device.");
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
