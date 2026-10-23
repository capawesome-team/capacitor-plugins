package io.capawesome.capacitorjs.plugins.intune;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.microsoft.identity.client.AcquireTokenParameters;
import com.microsoft.identity.client.AcquireTokenSilentParameters;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.Prompt;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.SilentAuthenticationCallback;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalIntuneAppProtectionPolicyRequiredException;
import com.microsoft.identity.client.exception.MsalUserCancelException;
import com.microsoft.intune.mam.client.app.MAMComponents;
import com.microsoft.intune.mam.client.identity.MAMFileProtectionInfo;
import com.microsoft.intune.mam.client.identity.MAMFileProtectionManager;
import com.microsoft.intune.mam.client.identity.MAMPolicyManager;
import com.microsoft.intune.mam.client.notification.MAMNotificationReceiverRegistry;
import com.microsoft.intune.mam.policy.AppPolicy;
import com.microsoft.intune.mam.policy.MAMCAComplianceStatus;
import com.microsoft.intune.mam.policy.MAMComplianceManager;
import com.microsoft.intune.mam.policy.MAMEnrollmentManager;
import com.microsoft.intune.mam.policy.MAMServiceAuthenticationCallbackExtended;
import com.microsoft.intune.mam.policy.MAMUserInfo;
import com.microsoft.intune.mam.policy.SaveLocation;
import com.microsoft.intune.mam.policy.appconfig.MAMAppConfig;
import com.microsoft.intune.mam.policy.appconfig.MAMAppConfigManager;
import com.microsoft.intune.mam.policy.notification.MAMComplianceNotification;
import com.microsoft.intune.mam.policy.notification.MAMEnrollmentNotification;
import com.microsoft.intune.mam.policy.notification.MAMNotification;
import com.microsoft.intune.mam.policy.notification.MAMNotificationType;
import com.microsoft.intune.mam.policy.notification.MAMUserNotification;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomException;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.intune.classes.PublicClientAccount;
import io.capawesome.capacitorjs.plugins.intune.classes.events.AppConfigChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.EnrollmentChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.PolicyChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.WipeRequestedEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.options.AcquireTokenOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.AcquireTokenSilentOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.DecryptFileOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.GetAppConfigOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.GetPolicyOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.IsFileEncryptedOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.ProtectFileOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.RegisterAndEnrollAccountOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.RemediateComplianceOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.UnenrollAccountOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.results.AcquireTokenResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetAppConfigResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetEnrolledAccountResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetPolicyResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetSdkVersionResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.IsFileEncryptedResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.RemediateComplianceResult;
import io.capawesome.capacitorjs.plugins.intune.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.intune.interfaces.NonEmptyResultCallback;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class Intune {

    @Nullable
    private static Intune instance;

    @Nullable
    private static Context applicationContext;

    private static boolean initialized = false;

    private static final List<EnrollmentChangeEvent> pendingEnrollmentChangeEvents = Collections.synchronizedList(new ArrayList<>());

    private static final String PENDING_WIPE_ACCOUNT_IDS_KEY = "pendingWipeAccountIds";

    private static final String PUBLIC_CLIENT_APPLICATION_KEYS_KEY = "publicClientApplicationKeys";

    private static final String PUBLIC_CLIENT_APPLICATION_KEY_SEPARATOR = "|";

    private static final Map<String, IMultipleAccountPublicClientApplication> publicClientApplications = new HashMap<>();

    private static final String SHARED_PREFERENCES_NAME = "capawesome_capacitor_intune_events";

    private static final String UNMANAGED_ACCOUNT_ID = "";

    @NonNull
    private final IntunePlugin plugin;

    private final Map<String, List<NonEmptyResultCallback<RemediateComplianceResult>>> remediateComplianceCallbacks = new HashMap<>();

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
    }

    public void acquireToken(@NonNull AcquireTokenOptions options, @NonNull NonEmptyResultCallback<AcquireTokenResult> callback)
        throws Exception {
        IMultipleAccountPublicClientApplication application = getPublicClientApplication(options.getClientId(), options.getAuthority());
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

    public void acquireTokenSilent(@NonNull AcquireTokenSilentOptions options, @NonNull NonEmptyResultCallback<AcquireTokenResult> callback)
        throws Exception {
        IMultipleAccountPublicClientApplication application = getPublicClientApplication(options.getClientId(), options.getAuthority());
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

    public void decryptFile(@NonNull DecryptFileOptions options, @NonNull EmptyCallback callback) throws Exception {
        File file = new File(options.getPath());
        File destinationFile = options.getDestination() == null ? file : new File(options.getDestination());
        if (!destinationFile.getCanonicalFile().equals(file.getCanonicalFile())) {
            copyFile(file, destinationFile);
        }
        MAMFileProtectionManager.protectForOID(destinationFile, UNMANAGED_ACCOUNT_ID);
        callback.success();
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

    public void isFileEncrypted(@NonNull IsFileEncryptedOptions options, @NonNull NonEmptyResultCallback<IsFileEncryptedResult> callback)
        throws Exception {
        MAMFileProtectionInfo protectionInfo = MAMFileProtectionManager.getProtectionInfo(new File(options.getPath()));
        String accountId = protectionInfo == null ? null : protectionInfo.getIdentityOID();
        boolean encrypted =
            accountId != null &&
            !accountId.isEmpty() &&
            MAMPolicyManager.getPolicyForIdentityOID(accountId).diagnosticIsFileEncryptionInUse();
        callback.success(new IsFileEncryptedResult(encrypted));
    }

    public void protectFile(@NonNull ProtectFileOptions options, @NonNull EmptyCallback callback) throws Exception {
        MAMFileProtectionManager.protectForOID(new File(options.getPath()), options.getAccountId());
        callback.success();
    }

    public void registerAndEnrollAccount(@NonNull RegisterAndEnrollAccountOptions options, @NonNull EmptyCallback callback)
        throws Exception {
        IMultipleAccountPublicClientApplication application = getPublicClientApplication(options.getClientId(), options.getAuthority());
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

    public void remediateCompliance(
        @NonNull RemediateComplianceOptions options,
        @NonNull NonEmptyResultCallback<RemediateComplianceResult> callback
    ) throws Exception {
        // The callback must be added before the remediation starts, because the result may be reported immediately.
        addRemediateComplianceCallback(options.getAccountId(), callback);
        MAMComplianceManager complianceManager = MAMComponents.get(MAMComplianceManager.class);
        complianceManager.remediateCompliance(
            options.getUsername(),
            options.getAccountId(),
            options.getTenantId(),
            options.getAuthority(),
            !options.getSilent()
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
        PublicClientAccount publicClientAccount;
        try {
            publicClientAccount = findPublicClientAccount(options.getAccountId());
        } catch (Exception exception) {
            callback.error(new CustomException("UNENROLL_FAILED", getErrorMessage(exception)));
            return;
        }
        if (publicClientAccount == null) {
            callback.error(CustomExceptions.NOT_ENROLLED);
            return;
        }
        IAccount account = publicClientAccount.getAccount();
        enrollmentManager.unregisterAccountForMAM(account.getUsername(), account.getId());
        callback.success();
    }

    private void addRemediateComplianceCallback(
        @NonNull String accountId,
        @NonNull NonEmptyResultCallback<RemediateComplianceResult> callback
    ) {
        synchronized (remediateComplianceCallbacks) {
            remediateComplianceCallbacks.computeIfAbsent(accountId.toLowerCase(Locale.ROOT), key -> new ArrayList<>()).add(callback);
        }
    }

    private static void addToPersistedStringSet(@NonNull String key, @NonNull String value) {
        SharedPreferences preferences = getSharedPreferences();
        if (preferences == null) {
            return;
        }
        Set<String> values = new HashSet<>();
        Set<String> persistedValues = preferences.getStringSet(key, null);
        if (persistedValues != null) {
            values.addAll(persistedValues);
        }
        values.add(value);
        preferences.edit().putStringSet(key, values).apply();
    }

    private static void copyFile(@NonNull File source, @NonNull File destination) throws IOException {
        try (InputStream inputStream = new FileInputStream(source); OutputStream outputStream = new FileOutputStream(destination)) {
            copyStream(inputStream, outputStream);
        }
    }

    private static void copyStream(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
    }

    /**
     * Creates the MSAL application from `res/raw/auth_config.json`. If a client ID or authority is given,
     * it replaces the `client_id` or `authorities` entry of that file while all other settings are kept.
     */
    @NonNull
    private static IMultipleAccountPublicClientApplication createPublicClientApplication(
        @NonNull Context context,
        @Nullable String clientId,
        @Nullable String authority
    ) throws Exception {
        int resourceId = context.getResources().getIdentifier("auth_config", "raw", context.getPackageName());
        if (resourceId == 0) {
            throw CustomExceptions.MSAL_CONFIGURATION_MISSING;
        }
        if (clientId == null && authority == null) {
            return PublicClientApplication.createMultipleAccountPublicClientApplication(context, resourceId);
        }
        JSONObject configuration = new JSONObject(readRawResource(context, resourceId));
        if (clientId != null) {
            configuration.put("client_id", clientId);
        }
        if (authority != null) {
            JSONObject authorityConfiguration = new JSONObject().put("type", "AAD").put("authority_url", authority);
            configuration.put("authorities", new JSONArray().put(authorityConfiguration));
        }
        File configurationFile = File.createTempFile("auth_config", ".json", context.getCacheDir());
        try {
            try (OutputStream outputStream = new FileOutputStream(configurationFile)) {
                outputStream.write(configuration.toString().getBytes(StandardCharsets.UTF_8));
            }
            return PublicClientApplication.createMultipleAccountPublicClientApplication(context, configurationFile);
        } finally {
            configurationFile.delete();
        }
    }

    @NonNull
    private static String createPublicClientApplicationKey(@Nullable String clientId, @Nullable String authority) {
        return (clientId == null ? "" : clientId) + PUBLIC_CLIENT_APPLICATION_KEY_SEPARATOR + (authority == null ? "" : authority);
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
    private static CustomException createProtectionPolicyRequiredException(
        @NonNull MsalIntuneAppProtectionPolicyRequiredException exception
    ) {
        JSObject data = new JSObject();
        data.put("accountId", exception.getAccountUserId());
        data.put("authority", exception.getAuthorityUrl());
        data.put("tenantId", exception.getTenantId());
        data.put("username", exception.getAccountUpn());
        return new CustomException(
            "PROTECTION_POLICY_REQUIRED",
            "An Intune app protection policy is required to acquire a token for this account.",
            data
        );
    }

    @NonNull
    private static CustomException createTokenAcquisitionException(@NonNull MsalException exception) {
        if (exception instanceof MsalUserCancelException) {
            return CustomExceptions.INTERACTION_CANCELED;
        }
        if (exception instanceof MsalIntuneAppProtectionPolicyRequiredException) {
            return createProtectionPolicyRequiredException((MsalIntuneAppProtectionPolicyRequiredException) exception);
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

    /**
     * Searches the account in every MSAL application that has been used so far, because the token cache of MSAL is
     * kept per client ID.
     */
    @Nullable
    private static PublicClientAccount findPublicClientAccount(@NonNull String accountId) throws Exception {
        for (String key : getPublicClientApplicationKeys()) {
            String[] keyParts = key.split(Pattern.quote(PUBLIC_CLIENT_APPLICATION_KEY_SEPARATOR), -1);
            IMultipleAccountPublicClientApplication application = getPublicClientApplication(
                keyParts[0].isEmpty() ? null : keyParts[0],
                keyParts[1].isEmpty() ? null : keyParts[1]
            );
            IAccount account = application.getAccount(accountId);
            if (account != null) {
                return new PublicClientAccount(application, account);
            }
        }
        return null;
    }

    @NonNull
    private static String getErrorMessage(@NonNull Exception exception) {
        String message = exception.getMessage();
        return message == null ? IntunePlugin.ERROR_UNKNOWN_ERROR : message;
    }

    @NonNull
    private static synchronized IMultipleAccountPublicClientApplication getPublicClientApplication(
        @Nullable String clientId,
        @Nullable String authority
    ) throws Exception {
        Context context = applicationContext;
        if (context == null) {
            throw CustomExceptions.MSAL_NOT_INITIALIZED;
        }
        String key = createPublicClientApplicationKey(clientId, authority);
        IMultipleAccountPublicClientApplication application = publicClientApplications.get(key);
        if (application == null) {
            application = createPublicClientApplication(context, clientId, authority);
            publicClientApplications.put(key, application);
            addToPersistedStringSet(PUBLIC_CLIENT_APPLICATION_KEYS_KEY, key);
        }
        return application;
    }

    @NonNull
    private static Set<String> getPublicClientApplicationKeys() {
        Set<String> keys = new LinkedHashSet<>();
        keys.add(createPublicClientApplicationKey(null, null));
        SharedPreferences preferences = getSharedPreferences();
        if (preferences != null) {
            Set<String> persistedKeys = preferences.getStringSet(PUBLIC_CLIENT_APPLICATION_KEYS_KEY, null);
            if (persistedKeys != null) {
                keys.addAll(persistedKeys);
            }
        }
        return keys;
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

    private static void handleComplianceStatus(@NonNull MAMComplianceNotification notification) {
        Intune intune = instance;
        if (intune != null) {
            intune.resolveRemediateComplianceCallbacks(notification);
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
    private static String mapComplianceStatus(@Nullable MAMCAComplianceStatus status) {
        if (status == null) {
            return "unknown";
        }
        switch (status) {
            case CLIENT_ERROR:
                return "clientError";
            case COMPANY_PORTAL_REQUIRED:
                return "companyPortalRequired";
            case COMPLIANT:
                return "compliant";
            case NETWORK_FAILURE:
                return "networkFailure";
            case NOT_COMPLIANT:
                return "notCompliant";
            case PENDING:
                return "pending";
            case SERVICE_FAILURE:
                return "serviceFailure";
            default:
                return "unknown";
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
        addToPersistedStringSet(PENDING_WIPE_ACCOUNT_IDS_KEY, accountId == null ? "" : accountId);
    }

    @NonNull
    private static String readRawResource(@NonNull Context context, int resourceId) throws IOException {
        try (InputStream inputStream = context.getResources().openRawResource(resourceId)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            copyStream(inputStream, outputStream);
            return outputStream.toString(StandardCharsets.UTF_8.name());
        }
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
                        PublicClientAccount publicClientAccount = findPublicClientAccount(aadId);
                        if (publicClientAccount == null) {
                            return null;
                        }
                        AcquireTokenSilentParameters parameters = new AcquireTokenSilentParameters.Builder()
                            .forAccount(publicClientAccount.getAccount())
                            .fromAuthority(authority)
                            .withScopes(Collections.singletonList(resourceId + "/.default"))
                            .build();
                        IAuthenticationResult result = publicClientAccount.getApplication().acquireTokenSilent(parameters);
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
        registry.registerReceiver(notification -> {
            handleComplianceStatus((MAMComplianceNotification) notification);
            return true;
        }, MAMNotificationType.COMPLIANCE_STATUS);
        registry.registerReceiver(notification -> {
            MAMEnrollmentNotification enrollmentNotification = (MAMEnrollmentNotification) notification;
            handleEnrollmentChange(
                enrollmentNotification.getUserOid(),
                mapEnrollmentResultToStatus(enrollmentNotification.getEnrollmentResult())
            );
            return true;
        }, MAMNotificationType.MAM_ENROLLMENT_RESULT);
        registry.registerReceiver(notification -> {
            handleAppConfigChange(getAccountIdFromNotification(notification));
            return true;
        }, MAMNotificationType.REFRESH_APP_CONFIG);
        registry.registerReceiver(notification -> {
            handlePolicyChange(getAccountIdFromNotification(notification));
            return true;
        }, MAMNotificationType.REFRESH_POLICY);
        registry.registerReceiver(notification -> {
            handleWipeRequested(getAccountIdFromNotification(notification));
            return true;
        }, MAMNotificationType.WIPE_USER_DATA);
    }

    private void resolveRemediateComplianceCallbacks(@NonNull MAMComplianceNotification notification) {
        String accountId = notification.getUserOid();
        if (accountId == null) {
            return;
        }
        List<NonEmptyResultCallback<RemediateComplianceResult>> callbacks;
        synchronized (remediateComplianceCallbacks) {
            callbacks = remediateComplianceCallbacks.remove(accountId.toLowerCase(Locale.ROOT));
        }
        if (callbacks == null) {
            return;
        }
        RemediateComplianceResult result = new RemediateComplianceResult(
            notification.getComplianceErrorMessage(),
            notification.getComplianceErrorTitle(),
            mapComplianceStatus(notification.getComplianceStatus())
        );
        for (NonEmptyResultCallback<RemediateComplianceResult> callback : callbacks) {
            callback.success(result);
        }
    }

    @Nullable
    private static String getAccountIdFromNotification(@NonNull MAMNotification notification) {
        if (notification instanceof MAMUserNotification) {
            return ((MAMUserNotification) notification).getUserOid();
        }
        return null;
    }
}
