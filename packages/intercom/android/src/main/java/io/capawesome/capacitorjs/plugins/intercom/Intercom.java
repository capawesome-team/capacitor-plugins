package io.capawesome.capacitorjs.plugins.intercom;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomException;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.events.UnreadConversationCountChangeEvent;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.HandlePushNotificationOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.IsIntercomPushNotificationOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.LogEventOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.LoginUserOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.PresentContentOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.PresentMessageComposerOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.PresentOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SendPushTokenToIntercomOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetBottomPaddingOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetInAppMessagesVisibleOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetLauncherVisibleOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetUserHashOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetUserJwtOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.UpdateUserOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.results.GetUnreadConversationCountResult;
import io.capawesome.capacitorjs.plugins.intercom.classes.results.IsIntercomPushNotificationResult;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.NonEmptyResultCallback;
import io.intercom.android.sdk.Company;
import io.intercom.android.sdk.IntercomContent;
import io.intercom.android.sdk.IntercomError;
import io.intercom.android.sdk.IntercomSpace;
import io.intercom.android.sdk.IntercomStatusCallback;
import io.intercom.android.sdk.UnreadConversationCountListener;
import io.intercom.android.sdk.UserAttributes;
import io.intercom.android.sdk.identity.Registration;
import io.intercom.android.sdk.push.IntercomPushClient;
import java.util.List;
import java.util.Map;

public class Intercom {

    @NonNull
    private final IntercomPushClient pushClient = new IntercomPushClient();

    @NonNull
    private final IntercomPlugin plugin;

    private boolean initialized = false;

    @Nullable
    private UnreadConversationCountListener unreadConversationCountListener;

    public Intercom(@NonNull IntercomPlugin plugin) {
        this.plugin = plugin;
    }

    public void getUnreadConversationCount(@NonNull NonEmptyResultCallback<GetUnreadConversationCountResult> callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                int count = io.intercom.android.sdk.Intercom.client().getUnreadConversationCount();
                callback.success(new GetUnreadConversationCountResult(count));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void handlePushNotification(@NonNull HandlePushNotificationOptions options, @NonNull EmptyCallback callback) {
        try {
            Map<String, String> data = IntercomHelper.convertJSObjectToStringMap(options.getData());
            pushClient.handlePush(getApplication(), data);
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void hide(@NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                io.intercom.android.sdk.Intercom.client().hideIntercom();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                String apiKey = options.getAndroidApiKey();
                if (apiKey == null) {
                    throw CustomExceptions.ANDROID_API_KEY_MISSING;
                }
                io.intercom.android.sdk.Intercom.initialize(getApplication(), apiKey, options.getAppId());
                initialized = true;
                registerUnreadConversationCountListener();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void isIntercomPushNotification(
        @NonNull IsIntercomPushNotificationOptions options,
        @NonNull NonEmptyResultCallback<IsIntercomPushNotificationResult> callback
    ) {
        try {
            Map<String, String> data = IntercomHelper.convertJSObjectToStringMap(options.getData());
            boolean isIntercomPush = pushClient.isIntercomPush(data);
            callback.success(new IsIntercomPushNotificationResult(isIntercomPush));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void logEvent(@NonNull LogEventOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            JSObject data = options.getData();
            if (data == null) {
                io.intercom.android.sdk.Intercom.client().logEvent(options.getName());
            } else {
                io.intercom.android.sdk.Intercom.client().logEvent(options.getName(), IntercomHelper.convertJSObjectToObjectMap(data));
            }
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void loginUnidentifiedUser(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            io.intercom.android.sdk.Intercom.client().loginUnidentifiedUser(createStatusCallback(callback, "LOGIN_FAILED"));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void loginUser(@NonNull LoginUserOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            Registration registration = Registration.create();
            if (options.getUserId() != null) {
                registration = registration.withUserId(options.getUserId());
            }
            if (options.getEmail() != null) {
                registration = registration.withEmail(options.getEmail());
            }
            io.intercom.android.sdk.Intercom.client().loginIdentifiedUser(registration, createStatusCallback(callback, "LOGIN_FAILED"));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void logout(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            io.intercom.android.sdk.Intercom.client().logout();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void present(@NonNull PresentOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                io.intercom.android.sdk.Intercom.client().present(mapSpace(options.getSpace()));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void presentContent(@NonNull PresentContentOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                io.intercom.android.sdk.Intercom.client().presentContent(mapContent(options));
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void presentMessageComposer(@NonNull PresentMessageComposerOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                String initialMessage = options.getInitialMessage();
                if (initialMessage == null) {
                    io.intercom.android.sdk.Intercom.client().displayMessageComposer();
                } else {
                    io.intercom.android.sdk.Intercom.client().displayMessageComposer(initialMessage);
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void sendPushTokenToIntercom(@NonNull SendPushTokenToIntercomOptions options, @NonNull EmptyCallback callback) {
        try {
            pushClient.sendTokenToIntercom(getApplication(), options.getToken());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setBottomPadding(@NonNull SetBottomPaddingOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                io.intercom.android.sdk.Intercom.client().setBottomPadding(options.getPadding());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setInAppMessagesVisible(@NonNull SetInAppMessagesVisibleOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                io.intercom.android.sdk.Intercom.client()
                    .setInAppMessageVisibility(
                        options.getVisible()
                            ? io.intercom.android.sdk.Intercom.Visibility.VISIBLE
                            : io.intercom.android.sdk.Intercom.Visibility.GONE
                    );
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setLauncherVisible(@NonNull SetLauncherVisibleOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireInitialized();
                io.intercom.android.sdk.Intercom.client()
                    .setLauncherVisibility(
                        options.getVisible()
                            ? io.intercom.android.sdk.Intercom.Visibility.VISIBLE
                            : io.intercom.android.sdk.Intercom.Visibility.GONE
                    );
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setUserHash(@NonNull SetUserHashOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            io.intercom.android.sdk.Intercom.client().setUserHash(options.getUserHash());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setUserJwt(@NonNull SetUserJwtOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            io.intercom.android.sdk.Intercom.client().setUserJwt(options.getJwt());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void unregisterUnreadConversationCountListener() {
        if (unreadConversationCountListener != null) {
            io.intercom.android.sdk.Intercom.client().removeUnreadConversationCountListener(unreadConversationCountListener);
            unreadConversationCountListener = null;
        }
    }

    public void updateUser(@NonNull UpdateUserOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            UserAttributes attributes = buildUserAttributes(options);
            io.intercom.android.sdk.Intercom.client().updateUser(attributes, createStatusCallback(callback, "UPDATE_FAILED"));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @NonNull
    private Company buildCompany(@NonNull JSObject company) throws Exception {
        String id = company.getString("id");
        if (id == null) {
            throw CustomExceptions.COMPANY_ID_MISSING;
        }
        Company.Builder builder = new Company.Builder();
        builder.withCompanyId(id);
        if (company.getString("name") != null) {
            builder.withName(company.getString("name"));
        }
        if (company.getString("plan") != null) {
            builder.withPlan(company.getString("plan"));
        }
        if (company.has("monthlySpend")) {
            builder.withMonthlySpend(company.getInteger("monthlySpend"));
        }
        if (company.has("createdAt")) {
            builder.withCreatedAt(company.optLong("createdAt"));
        }
        JSObject customAttributes = company.getJSObject("customAttributes");
        if (customAttributes != null) {
            builder.withCustomAttributes(IntercomHelper.convertJSObjectToObjectMap(customAttributes));
        }
        return builder.build();
    }

    @NonNull
    private UserAttributes buildUserAttributes(@NonNull UpdateUserOptions options) throws Exception {
        UserAttributes.Builder builder = new UserAttributes.Builder();
        if (options.getName() != null) {
            builder.withName(options.getName());
        }
        if (options.getEmail() != null) {
            builder.withEmail(options.getEmail());
        }
        if (options.getUserId() != null) {
            builder.withUserId(options.getUserId());
        }
        if (options.getPhone() != null) {
            builder.withPhone(options.getPhone());
        }
        if (options.getLanguageOverride() != null) {
            builder.withLanguageOverride(options.getLanguageOverride());
        }
        if (options.getSignedUpAt() != null) {
            builder.withSignedUpAt(options.getSignedUpAt());
        }
        if (options.getUnsubscribedFromEmails() != null) {
            builder.withUnsubscribedFromEmails(options.getUnsubscribedFromEmails());
        }
        if (options.getCustomAttributes() != null) {
            builder.withCustomAttributes(IntercomHelper.convertJSObjectToObjectMap(options.getCustomAttributes()));
        }
        List<JSObject> companies = options.getCompanies();
        if (companies != null) {
            for (JSObject company : companies) {
                builder.withCompany(buildCompany(company));
            }
        }
        return builder.build();
    }

    @NonNull
    private IntercomStatusCallback createStatusCallback(@NonNull EmptyCallback callback, @NonNull String errorCode) {
        return new IntercomStatusCallback() {
            @Override
            public void onSuccess() {
                callback.success();
            }

            @Override
            public void onFailure(@NonNull IntercomError intercomError) {
                callback.error(new CustomException(errorCode, intercomError.getErrorMessage()));
            }
        };
    }

    @NonNull
    private Application getApplication() {
        return (Application) plugin.getContext().getApplicationContext();
    }

    @NonNull
    private IntercomContent mapContent(@NonNull PresentContentOptions options) throws Exception {
        switch (options.getType()) {
            case "article":
                return new IntercomContent.Article(options.getId());
            case "carousel":
                return new IntercomContent.Carousel(options.getId());
            case "survey":
                return new IntercomContent.Survey(options.getId());
            case "conversation":
                return new IntercomContent.Conversation(options.getId());
            case "help-center-collections":
                return new IntercomContent.HelpCenterCollections(options.getIds());
            default:
                throw CustomExceptions.TYPE_INVALID;
        }
    }

    @NonNull
    private IntercomSpace mapSpace(@NonNull String space) {
        switch (space) {
            case "messages":
                return IntercomSpace.Messages;
            case "help-center":
                return IntercomSpace.HelpCenter;
            case "tickets":
                return IntercomSpace.Tickets;
            default:
                return IntercomSpace.Home;
        }
    }

    private void registerUnreadConversationCountListener() {
        if (unreadConversationCountListener != null) {
            return;
        }
        unreadConversationCountListener = count -> {
            plugin.notifyUnreadConversationCountChangeListeners(new UnreadConversationCountChangeEvent(count));
        };
        io.intercom.android.sdk.Intercom.client().addUnreadConversationCountListener(unreadConversationCountListener);
    }

    private void requireInitialized() throws Exception {
        if (!initialized) {
            throw CustomExceptions.NOT_INITIALIZED;
        }
    }

    private void runOnMainThread(@NonNull Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
