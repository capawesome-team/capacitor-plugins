package io.capawesome.capacitorjs.plugins.crisp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import im.crisp.client.external.ChatActivity;
import im.crisp.client.external.EventsCallback;
import im.crisp.client.external.data.Company;
import im.crisp.client.external.data.Employment;
import im.crisp.client.external.data.Geolocation;
import im.crisp.client.external.data.SessionEvent;
import im.crisp.client.external.data.message.Message;
import im.crisp.client.external.notification.CrispNotificationClient;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.events.MessageReceivedEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.events.MessageSentEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.events.SessionLoadedEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.ConfigureOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.HandlePushNotificationOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.IsCrispPushNotificationOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.OpenHelpdeskArticleOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.PushSessionEventOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetCompanyOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetNotificationsEnabledOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionBoolOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionIntOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionSegmentOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionStringOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetTokenIdOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetUserOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.results.IsCrispPushNotificationResult;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.NonEmptyResultCallback;
import java.net.URL;
import java.util.Map;

public class Crisp {

    @NonNull
    private final CrispPlugin plugin;

    private boolean configured = false;

    @androidx.annotation.Nullable
    private EventsCallback eventsCallback;

    public Crisp(@NonNull CrispPlugin plugin) {
        this.plugin = plugin;
    }

    public void configure(@NonNull ConfigureOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Context context = getContext();
                String tokenId = options.getTokenId();
                if (tokenId == null) {
                    im.crisp.client.external.Crisp.configure(context, options.getWebsiteId());
                } else {
                    im.crisp.client.external.Crisp.configure(context, options.getWebsiteId(), tokenId);
                }
                registerCallback();
                configured = true;
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void handlePushNotification(@NonNull HandlePushNotificationOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                Map<String, String> data = CrispHelper.convertDataToStringMap(options.getData());
                CrispNotificationClient.handleNotification(getContext(), data);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void isCrispPushNotification(
        @NonNull IsCrispPushNotificationOptions options,
        @NonNull NonEmptyResultCallback<IsCrispPushNotificationResult> callback
    ) {
        try {
            Map<String, String> data = CrispHelper.convertDataToStringMap(options.getData());
            boolean isCrisp = CrispNotificationClient.isCrispNotification(data);
            callback.success(new IsCrispPushNotificationResult(isCrisp));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void openChat(@NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                Activity activity = plugin.getActivity();
                Intent intent = new Intent(activity, ChatActivity.class);
                activity.startActivity(intent);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void openHelpdeskArticle(@NonNull OpenHelpdeskArticleOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.openHelpdeskArticle(
                    getContext(),
                    options.getId(),
                    options.getLocale(),
                    options.getTitle(),
                    options.getCategory()
                );
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void pushSessionEvent(@NonNull PushSessionEventOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                SessionEvent event = new SessionEvent(options.getName(), options.getColor());
                im.crisp.client.external.Crisp.pushSessionEvent(event);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void resetSession(@NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.resetChatSession(getContext());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void searchHelpdesk(@NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.searchHelpdesk(getContext());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setCompany(@NonNull SetCompanyOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                URL url = options.getUrl() == null ? null : new URL(options.getUrl());
                Employment employment = null;
                if (options.getEmploymentTitle() != null || options.getEmploymentRole() != null) {
                    employment = new Employment(options.getEmploymentTitle(), options.getEmploymentRole());
                }
                Geolocation geolocation = null;
                if (options.getGeolocationCity() != null || options.getGeolocationCountry() != null) {
                    geolocation = new Geolocation(options.getGeolocationCity(), options.getGeolocationCountry());
                }
                Company company = new Company(options.getName(), url, options.getDescription(), employment, geolocation);
                im.crisp.client.external.Crisp.setUserCompany(company);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setNotificationsEnabled(@NonNull SetNotificationsEnabledOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.enableNotifications(getContext(), options.getEnabled());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setSessionBool(@NonNull SetSessionBoolOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.setSessionBool(options.getKey(), options.getValue());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setSessionInt(@NonNull SetSessionIntOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.setSessionInt(options.getKey(), options.getValue());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setSessionSegment(@NonNull SetSessionSegmentOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.setSessionSegment(options.getSegment());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setSessionString(@NonNull SetSessionStringOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                im.crisp.client.external.Crisp.setSessionString(options.getKey(), options.getValue());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setTokenId(@NonNull SetTokenIdOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                im.crisp.client.external.Crisp.setTokenID(getContext(), options.getTokenId());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setUser(@NonNull SetUserOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                requireConfigured();
                String email = options.getEmail();
                if (email != null) {
                    String signature = options.getEmailSignature();
                    if (signature == null) {
                        im.crisp.client.external.Crisp.setUserEmail(email);
                    } else {
                        im.crisp.client.external.Crisp.setUserEmail(email, signature);
                    }
                }
                if (options.getNickname() != null) {
                    im.crisp.client.external.Crisp.setUserNickname(options.getNickname());
                }
                if (options.getPhone() != null) {
                    im.crisp.client.external.Crisp.setUserPhone(options.getPhone());
                }
                if (options.getAvatarUrl() != null) {
                    im.crisp.client.external.Crisp.setUserAvatar(options.getAvatarUrl());
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void unregisterCallback() {
        if (eventsCallback != null) {
            im.crisp.client.external.Crisp.removeCallback(eventsCallback);
            eventsCallback = null;
        }
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }

    private void registerCallback() {
        if (eventsCallback != null) {
            return;
        }
        eventsCallback = new EventsCallback() {
            @Override
            public void onSessionLoaded(@NonNull String sessionId) {
                plugin.notifySessionLoadedListeners(new SessionLoadedEvent(sessionId));
            }

            @Override
            public void onChatOpened() {
                plugin.notifyChatOpenedListeners();
            }

            @Override
            public void onChatClosed() {
                plugin.notifyChatClosedListeners();
            }

            @Override
            public void onMessageSent(@NonNull Message message) {
                plugin.notifyMessageSentListeners(new MessageSentEvent(CrispHelper.getMessageContent(message)));
            }

            @Override
            public void onMessageReceived(@NonNull Message message) {
                plugin.notifyMessageReceivedListeners(new MessageReceivedEvent(CrispHelper.getMessageContent(message)));
            }

            @Override
            public void onNotificationReceived(@NonNull Map<String, String> data) {
                // No-op: push notifications are handled via the dedicated push methods.
            }
        };
        im.crisp.client.external.Crisp.addCallback(eventsCallback);
    }

    private void requireConfigured() throws Exception {
        if (!configured) {
            throw CustomExceptions.NOT_CONFIGURED;
        }
    }

    private void runOnMainThread(@NonNull Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
