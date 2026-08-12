package io.capawesome.capacitorjs.plugins.androidsmsretriever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.options.RetrieveSmsOptions;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.results.RequestPhoneNumberResult;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.results.RetrieveSmsResult;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.interfaces.NonEmptyResultCallback;

public class AndroidSmsRetriever {

    @NonNull
    private final AndroidSmsRetrieverPlugin plugin;

    @Nullable
    private NonEmptyResultCallback<RequestPhoneNumberResult> pendingPhoneNumberCallback;

    @Nullable
    private NonEmptyResultCallback<RetrieveSmsResult> pendingRetrieveSmsCallback;

    @Nullable
    private BroadcastReceiver smsBroadcastReceiver;

    public AndroidSmsRetriever(@NonNull AndroidSmsRetrieverPlugin plugin) {
        this.plugin = plugin;
    }

    public void handleOnDestroy() {
        unregisterSmsBroadcastReceiver();
        pendingPhoneNumberCallback = null;
        pendingRetrieveSmsCallback = null;
    }

    public void handlePhoneNumberCanceled() {
        if (pendingPhoneNumberCallback == null) {
            return;
        }
        NonEmptyResultCallback<RequestPhoneNumberResult> callback = pendingPhoneNumberCallback;
        pendingPhoneNumberCallback = null;
        callback.error(CustomExceptions.CANCELED);
    }

    public void handlePhoneNumberResult(@Nullable Intent data) {
        if (pendingPhoneNumberCallback == null) {
            return;
        }
        NonEmptyResultCallback<RequestPhoneNumberResult> callback = pendingPhoneNumberCallback;
        pendingPhoneNumberCallback = null;
        try {
            String phoneNumber = Identity.getSignInClient(plugin.getActivity()).getPhoneNumberFromIntent(data);
            callback.success(new RequestPhoneNumberResult(phoneNumber));
        } catch (Exception exception) {
            callback.error(CustomExceptions.PHONE_NUMBER_HINT_FAILED);
        }
    }

    public void handleSmsConsentCanceled() {
        rejectPendingRetrieveSms(CustomExceptions.USER_DENIED);
    }

    public void handleSmsConsentResult(@Nullable Intent data) {
        if (pendingRetrieveSmsCallback == null) {
            return;
        }
        NonEmptyResultCallback<RetrieveSmsResult> callback = pendingRetrieveSmsCallback;
        pendingRetrieveSmsCallback = null;
        String message = data == null ? null : data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
        if (message == null) {
            callback.error(CustomExceptions.RETRIEVE_FAILED);
        } else {
            callback.success(new RetrieveSmsResult(message));
        }
    }

    public boolean isGooglePlayServicesAvailable() {
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(plugin.getContext());
        return result == ConnectionResult.SUCCESS;
    }

    public void requestPhoneNumber(@NonNull NonEmptyResultCallback<RequestPhoneNumberResult> callback) {
        if (pendingPhoneNumberCallback != null) {
            callback.error(CustomExceptions.OPERATION_IN_PROGRESS);
            return;
        }
        GetPhoneNumberHintIntentRequest request = GetPhoneNumberHintIntentRequest.builder().build();
        Identity.getSignInClient(plugin.getActivity())
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener(pendingIntent -> {
                pendingPhoneNumberCallback = callback;
                plugin.launchPhoneNumberHintIntent(pendingIntent);
            })
            .addOnFailureListener(exception -> callback.error(CustomExceptions.PHONE_NUMBER_HINT_FAILED));
    }

    public void retrieveSms(@NonNull RetrieveSmsOptions options, @NonNull NonEmptyResultCallback<RetrieveSmsResult> callback) {
        if (pendingRetrieveSmsCallback != null) {
            callback.error(CustomExceptions.OPERATION_IN_PROGRESS);
            return;
        }
        pendingRetrieveSmsCallback = callback;
        registerSmsBroadcastReceiver();
        SmsRetriever.getClient(plugin.getContext())
            .startSmsUserConsent(options.getSenderPhoneNumber())
            .addOnFailureListener(exception -> rejectPendingRetrieveSms(CustomExceptions.RETRIEVE_FAILED));
    }

    private void handleSmsBroadcast(@NonNull Intent intent) {
        if (!SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            rejectPendingRetrieveSms(CustomExceptions.RETRIEVE_FAILED);
            return;
        }
        Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
        if (status == null) {
            rejectPendingRetrieveSms(CustomExceptions.RETRIEVE_FAILED);
            return;
        }
        switch (status.getStatusCode()) {
            case CommonStatusCodes.SUCCESS:
                Intent consentIntent = (Intent) extras.get(SmsRetriever.EXTRA_CONSENT_INTENT);
                unregisterSmsBroadcastReceiver();
                if (consentIntent == null) {
                    rejectPendingRetrieveSms(CustomExceptions.RETRIEVE_FAILED);
                } else {
                    plugin.launchSmsConsentIntent(consentIntent);
                }
                break;
            case CommonStatusCodes.TIMEOUT:
                rejectPendingRetrieveSms(CustomExceptions.TIMEOUT);
                break;
            default:
                rejectPendingRetrieveSms(CustomExceptions.RETRIEVE_FAILED);
                break;
        }
    }

    private void registerSmsBroadcastReceiver() {
        unregisterSmsBroadcastReceiver();
        smsBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleSmsBroadcast(intent);
            }
        };
        IntentFilter filter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        ContextCompat.registerReceiver(
            plugin.getContext(),
            smsBroadcastReceiver,
            filter,
            SmsRetriever.SEND_PERMISSION,
            null,
            ContextCompat.RECEIVER_EXPORTED
        );
    }

    private void rejectPendingRetrieveSms(@NonNull Exception exception) {
        unregisterSmsBroadcastReceiver();
        if (pendingRetrieveSmsCallback == null) {
            return;
        }
        NonEmptyResultCallback<RetrieveSmsResult> callback = pendingRetrieveSmsCallback;
        pendingRetrieveSmsCallback = null;
        callback.error(exception);
    }

    private void unregisterSmsBroadcastReceiver() {
        if (smsBroadcastReceiver == null) {
            return;
        }
        try {
            plugin.getContext().unregisterReceiver(smsBroadcastReceiver);
        } catch (IllegalArgumentException exception) {
            // The receiver was not registered.
        }
        smsBroadcastReceiver = null;
    }
}
