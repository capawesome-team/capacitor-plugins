package io.capawesome.capacitorjs.plugins.intune;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.microsoft.identity.client.AcquireTokenParameters;
import com.microsoft.identity.client.AcquireTokenSilentParameters;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.Prompt;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.SilentAuthenticationCallback;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalUserCancelException;
import com.microsoft.intune.mam.client.app.MAMComponents;
import com.microsoft.intune.mam.client.identity.MAMPolicyManager;
import com.microsoft.intune.mam.client.notification.MAMNotificationReceiverRegistry;
import com.microsoft.intune.mam.policy.AppPolicy;
import com.microsoft.intune.mam.policy.MAMEnrollmentManager;
import com.microsoft.intune.mam.policy.MAMServiceAuthenticationCallbackExtended;
import com.microsoft.intune.mam.policy.MAMUserInfo;
import com.microsoft.intune.mam.policy.SaveLocation;
import com.microsoft.intune.mam.policy.appconfig.MAMAppConfig;
import com.microsoft.intune.mam.policy.appconfig.MAMAppConfigManager;
import com.microsoft.intune.mam.policy.notification.MAMEnrollmentNotification;
import com.microsoft.intune.mam.policy.notification.MAMNotification;
import com.microsoft.intune.mam.policy.notification.MAMNotificationType;
import com.microsoft.intune.mam.policy.notification.MAMUserNotification;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomException;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.intune.classes.events.AppConfigChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.EnrollmentChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.PolicyChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.WipeRequestedEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.options.AcquireTokenOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.AcquireTokenSilentOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.GetAppConfigOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.GetPolicyOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.RegisterAndEnrollAccountOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.UnenrollAccountOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.results.AcquireTokenResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetAppConfigResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetEnrolledAccountResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetPolicyResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetSdkVersionResult;
import io.capawesome.capacitorjs.plugins.intune.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.intune.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Intune {

    @Nullable
    private static Intune instance;

    @Nullable
    private static Context applicationContext;

    private static boolean initialized = false;

    private static final List<EnrollmentChangeEvent> pendingEnrollmentChangeEvents = Collections.synchronizedList(new ArrayList<>());

    private static final String PENDING_WIPE_ACCOUNT_IDS_KEY = "pendingWipeAccountIds";

    @Nullable
    private static IMultipleAccountPublicClientApplication publicClientApplication;

    private static final String SHARED_PREFERENCES_NAME = "capawesome_capacitor_intune_events";

    @NonNull
    private final IntunePlugin plugin;

    public Intune(@NonNull IntunePlugin plugin) {
        this.plugin = plugin;
        instance = this;
        deliverPendingEnrollmentChangeEvents();
        deliverPendingWipeRequestedEvents();
    }

    public static synchronized void initialize(@NonNull Context context) {
        if (initialized) {
            return;
        }
        initialized = true;
        applicationContext = context.getApplicationContext();
        registerAuthenticationCallback();
        registerNotificationReceivers();
        createPublicClientApplication(context.getApplicationContext());
    }

    public void acquireToken(@NonNull AcquireTokenOptions options, @NonNull NonEmptyResultCallback<AcquireTokenResult> callback)
        throws Exception {
        IMultipleAccountPublicClientApplication application = getPublicClientApplication();
        Activity activity = plugin.getActivity();
        AcquireTokenParameters.Builder builder = new AcquireTokenParameters.Builder()
            .startAuthorizationFromActivity(activity)
            .withScopes(options.getScopes())
            .withCallback(
                new AuthenticationCallback() {
                    @Override
                    public void onSuccess(IAuthenticationResult result) {
                        callback.success(createAcquireTokenResult(result));
                    }

                    @Override
                    public void onError(MsalException exception) {
                        callback.error(createTokenAcquisitionException(exception));
                    }

                    @Override
                    public void onCancel() {
                        callback.error(CustomExceptions.INTERACTION_CANCELED);
                    }
                }
            );
        if (options.getLoginHint() != null) {
            builder.withLoginHint(options.getLoginHint());
        }
        if (options.getForcePrompt()) {
            builder.withPrompt(Prompt.LOGIN);
        }
        application.acquireToken(builder.build());
    }

    public void acquireTokenSilent(
        @NonNull AcquireTokenSilentOptions options,
        @NonNull NonEmptyResultCallback<AcquireTokenResult> callback
    ) throws Exception {
        IMultipleAccountPublicClientApplication application = getPublicClientApplication();
        application.getAccount(
            options.getAccountId(),
            new IMultipleAccountPublicClientApplication.GetAccountCallback() {
                @Override
                public void onTaskCompleted(@Nullable IAccount account) {
                    if (account == null) {
                        callback.error(CustomExceptions.NOT_ENROLLED);
                        return;
                    }
                    AcquireTokenSilentParameters parameters = new AcquireTokenSilentParameters.Builder()
                        .forAccount(account)
                        .fromAuthority(account.getAuthority())
                        .withScopes(options.getScopes())
                        .forceRefresh(options.getForceRefresh())
                        .withCallback(
                            new SilentAuthenticationCallback() {
                                @Override
                                public void onSuccess(IAuthenticationResult result) {
                                    callback.success(createAcquireTokenResult(result));
                                }

                                @Override
                                public void onError(MsalException exception) {
                                    callback.error(createTokenAcquisitionException(exception));
                                }
                            }
                        )
                        .build();
                    application.acquireTokenSilentAsync(parameters);
                }

                @Override
                public void onError(@NonNull MsalException exception) {
                    callback.error(createTokenAcquisitionException(exception));
                }
            }
        );
    }

    public void getAppConfig(@NonNull GetAppConfigOptions options, @NonNull NonEmptyResultCallback<GetAppConfigResult> callback)
        throws Exception {
        MAMAppConfigManager appConfigManager = MAMComponents.get(MAMAppConfigManager.class);
        Map<String, List<String>> config = new LinkedHashMap<>();
        if (appConfigManager != null) {
            MAMAppConfig appConfig = appConfigManager.getAppConfigForOID(options.getAccountId());
            if (appConfig != null) {
                for (Map<String, String> data : appConfig.getFullData()) {
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        List<String> values = config.get(entry.getKey());
                        if (values == null) {
                            values = new ArrayList<>();
                            config.put(entry.getKey(), values);
                        }
                        if (!values.contains(entry.getValue())) {
                            values.add(entry.getValue());
                        }
                    }
                }
            }
        }
        callback.success(new GetAppConfigResult(config));
    }

    public void getEnrolledAccount(@NonNull NonEmptyResultCallback<GetEnrolledAccountResult> callback) throws Exception {
        MAMUserInfo userInfo = MAMComponents.get(MAMUserInfo.class);
        String accountId = userInfo == null ? null : userInfo.getPrimaryUserOID();
        String username = userInfo == null ? null : userInfo.getPrimaryUser();
        callback.success(new GetEnrolledAccountResult(accountId, username));
    }

    public void getPolicy(@NonNull GetPolicyOptions options, @NonNull NonEmptyResultCallback<GetPolicyResult> callback) throws Exception {
        AppPolicy policy = MAMPolicyManager.getPolicyForIdentityOID(options.getAccountId());
        GetPolicyResult result = new GetPolicyResult(
            policy.getIsContactSyncAllowed(),
            policy.diagnosticIsFileEncryptionInUse(),
            policy.getIsManagedBrowserRequired(),
            policy.getIsPinRequired(),
            policy.getIsSaveToLocationAllowedForOID(SaveLocation.LOCAL, options.getAccountId()),
            policy.getIsScreenCaptureAllowed()
        );
        callback.success(result);
    }

    public void getSdkVersion(@NonNull NonEmptyResultCallback<GetSdkVersionResult> callback) throws Exception {
        callback.success(new GetSdkVersionResult(BuildConfig.INTUNE_MAM_SDK_VERSION, PublicClientApplication.getSdkVersion()));
    }

    public void registerAndEnrollAccount(@NonNull RegisterAndEnrollAccountOptions options, @NonNull EmptyCallback callback)
        throws Exception {
        IMultipleAccountPublicClientApplication application = getPublicClientApplication();
        application.getAccount(
            options.getAccountId(),
            new IMultipleAccountPublicClientApplication.GetAccountCallback() {
                @Override
                public void onTaskCompleted(@Nullable IAccount account) {
                    if (account == null) {
                        callback.error(CustomExceptions.NOT_ENROLLED);
                        return;
                    }
                    MAMEnrollmentManager enrollmentManager = MAMComponents.get(MAMEnrollmentManager.class);
                    if (enrollmentManager == null) {
                        callback.error(createEnrollmentException(null));
                        return;
                    }
                    enrollmentManager.registerAccountForMAM(
                        account.getUsername(),
                        account.getId(),
                        account.getTenantId(),
                        account.getAuthority()
                    );
                    callback.success();
                }

                @Override
                public void onError(@NonNull MsalException exception) {
                    callback.error(createEnrollmentException(exception.getMessage()));
                }
            }
        );
    }

    public void showDiagnosticConsole(@NonNull EmptyCallback callback) throws Exception {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            MAMPolicyManager.showDiagnostics(activity);
            callback.success();
        });
    }

    public void unenrollAccount(@NonNull UnenrollAccountOptions options, @NonNull EmptyCallback callback) throws Exception {
        MAMEnrollmentManager enrollmentManager = MAMComponents.get(MAMEnrollmentManager.class);
        if (enrollmentManager == null) {
            callback.error(CustomExceptions.NOT_ENROLLED);
            return;
        }
        MAMUserInfo userInfo = MAMComponents.get(MAMUserInfo.class);
        if (userInfo != null && options.getAccountId().equalsIgnoreCase(userInfo.getPrimaryUserOID())) {
            String username = userInfo.getPrimaryUser();
            if (username != null) {
                enrollmentManager.unregisterAccountForMAM(username, options.getAccountId());
                callback.success();
                return;
            }
        }
        IMultipleAccountPublicClientApplication application = getPublicClientApplication();
        application.getAccount(
            options.getAccountId(),
            new IMultipleAccountPublicClientApplication.GetAccountCallback() {
                @Override
                public void onTaskCompleted(@Nullable IAccount account) {
                    if (account == null) {
                        callback.error(CustomExceptions.NOT_ENROLLED);
                        return;
                    }
                    enrollmentManager.unregisterAccountForMAM(account.getUsername(), account.getId());
                    callback.success();
                }

                @Override
                public void onError(@NonNull MsalException exception) {
                    callback.error(new CustomException("UNENROLL_FAILED", getErrorMessage(exception)));
                }
            }
        );
    }

    private static void createPublicClientApplication(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier("auth_config", "raw", context.getPackageName());
        if (resourceId == 0) {
            Logger.warn(IntunePlugin.TAG, "The res/raw/auth_config.json resource was not found. MSAL is not initialized.");
            return;
        }
        PublicClientApplication.createMultipleAccountPublicClientApplication(
            context,
            resourceId,
            new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                @Override
                public void onCreated(IMultipleAccountPublicClientApplication application) {
                    publicClientApplication = application;
                }

                @Override
                public void onError(MsalException exception) {
                    Logger.error(IntunePlugin.TAG, "MSAL could not be initialized.", exception);
                }
            }
        );
    }

    @NonNull
    private static AcquireTokenResult createAcquireTokenResult(@NonNull IAuthenticationResult result) {
        IAccount account = result.getAccount();
        return new AcquireTokenResult(
            result.getAccessToken(),
            account.getId(),
            account.getIdToken(),
            result.getTenantId(),
            account.getUsername()
        );
    }

    @NonNull
    private static CustomException createEnrollmentException(@Nullable String message) {
        return new CustomException("ENROLLMENT_FAILED", message == null ? "The enrollment of the account failed." : message);
    }

    @NonNull
    private static CustomException createTokenAcquisitionException(@NonNull MsalException exception) {
        if (exception instanceof MsalUserCancelException) {
            return CustomExceptions.INTERACTION_CANCELED;
        }
        return new CustomException("TOKEN_ACQUISITION_FAILED", getErrorMessage(exception));
    }

    private void deliverPendingEnrollmentChangeEvents() {
        synchronized (pendingEnrollmentChangeEvents) {
            for (EnrollmentChangeEvent event : pendingEnrollmentChangeEvents) {
                plugin.notifyEnrollmentChangeListeners(event);
            }
            pendingEnrollmentChangeEvents.clear();
        }
    }

    private void deliverPendingWipeRequestedEvents() {
        SharedPreferences preferences = getSharedPreferences();
        if (preferences == null) {
            return;
        }
        Set<String> accountIds = preferences.getStringSet(PENDING_WIPE_ACCOUNT_IDS_KEY, null);
        if (accountIds == null) {
            return;
        }
        preferences.edit().remove(PENDING_WIPE_ACCOUNT_IDS_KEY).apply();
        for (String accountId : accountIds) {
            plugin.notifyWipeRequestedListeners(new WipeRequestedEvent(accountId.isEmpty() ? null : accountId));
        }
    }

    @NonNull
    private static String getErrorMessage(@NonNull Exception exception) {
        String message = exception.getMessage();
        return message == null ? IntunePlugin.ERROR_UNKNOWN_ERROR : message;
    }

    @NonNull
    private static IMultipleAccountPublicClientApplication getPublicClientApplication() throws Exception {
        IMultipleAccountPublicClientApplication application = publicClientApplication;
        if (application == null) {
            throw CustomExceptions.MSAL_NOT_INITIALIZED;
        }
        return application;
    }

    @Nullable
    private static SharedPreferences getSharedPreferences() {
        Context context = applicationContext;
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static void handleAppConfigChange(@Nullable String accountId) {
        Intune intune = instance;
        if (intune != null) {
            intune.plugin.notifyAppConfigChangeListeners(new AppConfigChangeEvent(accountId));
        }
    }

    private static void handleEnrollmentChange(@Nullable String accountId, @NonNull String status) {
        EnrollmentChangeEvent event = new EnrollmentChangeEvent(accountId, status);
        Intune intune = instance;
        if (intune == null) {
            pendingEnrollmentChangeEvents.add(event);
        } else {
            intune.plugin.notifyEnrollmentChangeListeners(event);
        }
    }

    private static void handlePolicyChange(@Nullable String accountId) {
        Intune intune = instance;
        if (intune != null) {
            intune.plugin.notifyPolicyChangeListeners(new PolicyChangeEvent(accountId));
        }
    }

    private static void handleWipeRequested(@Nullable String accountId) {
        Intune intune = instance;
        if (intune != null && intune.plugin.hasWipeRequestedListeners()) {
            intune.plugin.notifyWipeRequestedListeners(new WipeRequestedEvent(accountId));
            return;
        }
        persistPendingWipeAccountId(accountId);
        if (intune != null) {
            intune.plugin.notifyWipeRequestedListeners(new WipeRequestedEvent(accountId));
        }
    }

    @NonNull
    private static String mapEnrollmentResultToStatus(@Nullable MAMEnrollmentManager.Result result) {
        if (result == null) {
            return "pending";
        }
        switch (result) {
            case ENROLLMENT_SUCCEEDED:
                return "enrolled";
            case UNENROLLMENT_SUCCEEDED:
                return "unenrolled";
            case PENDING:
                return "pending";
            default:
                return "failed";
        }
    }

    private static void persistPendingWipeAccountId(@Nullable String accountId) {
        SharedPreferences preferences = getSharedPreferences();
        if (preferences == null) {
            return;
        }
        Set<String> accountIds = new HashSet<>();
        Set<String> persistedAccountIds = preferences.getStringSet(PENDING_WIPE_ACCOUNT_IDS_KEY, null);
        if (persistedAccountIds != null) {
            accountIds.addAll(persistedAccountIds);
        }
        accountIds.add(accountId == null ? "" : accountId);
        preferences.edit().putStringSet(PENDING_WIPE_ACCOUNT_IDS_KEY, accountIds).apply();
    }

    private static void registerAuthenticationCallback() {
        MAMEnrollmentManager enrollmentManager = MAMComponents.get(MAMEnrollmentManager.class);
        if (enrollmentManager == null) {
            return;
        }
        enrollmentManager.registerAuthenticationCallback(
            new MAMServiceAuthenticationCallbackExtended() {
                @Nullable
                @Override
                public String acquireToken(
                    @NonNull String upn,
                    @NonNull String aadId,
                    @NonNull String tenantId,
                    @NonNull String authority,
                    @NonNull String resourceId
                ) {
                    try {
                        IMultipleAccountPublicClientApplication application = getPublicClientApplication();
                        IAccount account = application.getAccount(aadId);
                        if (account == null) {
                            return null;
                        }
                        AcquireTokenSilentParameters parameters = new AcquireTokenSilentParameters.Builder()
                            .forAccount(account)
                            .fromAuthority(authority)
                            .withScopes(Collections.singletonList(resourceId + "/.default"))
                            .build();
                        IAuthenticationResult result = application.acquireTokenSilent(parameters);
                        return result.getAccessToken();
                    } catch (Exception exception) {
                        Logger.error(IntunePlugin.TAG, "Failed to acquire a token for the MAM service.", exception);
                        return null;
                    }
                }
            }
        );
    }

    private static void registerNotificationReceivers() {
        MAMNotificationReceiverRegistry registry = MAMComponents.get(MAMNotificationReceiverRegistry.class);
        if (registry == null) {
            return;
        }
        registry.registerReceiver(
            notification -> {
                MAMEnrollmentNotification enrollmentNotification = (MAMEnrollmentNotification) notification;
                handleEnrollmentChange(
                    enrollmentNotification.getUserOid(),
                    mapEnrollmentResultToStatus(enrollmentNotification.getEnrollmentResult())
                );
                return true;
            },
            MAMNotificationType.MAM_ENROLLMENT_RESULT
        );
        registry.registerReceiver(
            notification -> {
                handleAppConfigChange(getAccountIdFromNotification(notification));
                return true;
            },
            MAMNotificationType.REFRESH_APP_CONFIG
        );
        registry.registerReceiver(
            notification -> {
                handlePolicyChange(getAccountIdFromNotification(notification));
                return true;
            },
            MAMNotificationType.REFRESH_POLICY
        );
        registry.registerReceiver(
            notification -> {
                handleWipeRequested(getAccountIdFromNotification(notification));
                return true;
            },
            MAMNotificationType.WIPE_USER_DATA
        );
    }

    @Nullable
    private static String getAccountIdFromNotification(@NonNull MAMNotification notification) {
        if (notification instanceof MAMUserNotification) {
            return ((MAMUserNotification) notification).getUserOid();
        }
        return null;
    }
}
