package io.capawesome.capacitorjs.plugins.screenreader;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LocaleSpan;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.screenreader.classes.events.StateChangeEvent;
import io.capawesome.capacitorjs.plugins.screenreader.classes.options.AnnounceOptions;
import io.capawesome.capacitorjs.plugins.screenreader.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.screenreader.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.screenreader.interfaces.NonEmptyResultCallback;
import java.util.Locale;

public class ScreenReader {

    @NonNull
    private final ScreenReaderPlugin plugin;

    @NonNull
    private final AccessibilityManager accessibilityManager;

    private final AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = enabled ->
        handleTouchExplorationStateChanged(enabled);

    private boolean isObserving = false;

    private boolean lastEnabled = false;

    public ScreenReader(@NonNull ScreenReaderPlugin plugin) {
        this.plugin = plugin;
        this.accessibilityManager = (AccessibilityManager) plugin.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public void announce(@NonNull AnnounceOptions options, @NonNull EmptyCallback callback) {
        View webView = plugin.getBridge().getWebView();
        if (webView == null) {
            callback.success();
            return;
        }
        CharSequence text = createAnnouncementText(options.getValue(), options.getLanguage());
        webView.post(() -> {
            webView.announceForAccessibility(text);
            callback.success();
        });
    }

    public void isEnabled(@NonNull NonEmptyResultCallback<IsEnabledResult> callback) {
        callback.success(new IsEnabledResult(isTouchExplorationEnabled()));
    }

    public void startObserving() {
        if (isObserving) {
            return;
        }
        lastEnabled = isTouchExplorationEnabled();
        accessibilityManager.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        isObserving = true;
    }

    public void stopObserving() {
        if (!isObserving) {
            return;
        }
        accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        isObserving = false;
    }

    @NonNull
    private CharSequence createAnnouncementText(@NonNull String value, @Nullable String language) {
        if (language == null) {
            return value;
        }
        SpannableString spannable = new SpannableString(value);
        spannable.setSpan(new LocaleSpan(Locale.forLanguageTag(language)), 0, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannable;
    }

    private void handleTouchExplorationStateChanged(boolean enabled) {
        if (enabled == lastEnabled) {
            return;
        }
        lastEnabled = enabled;
        plugin.notifyStateChangeListeners(new StateChangeEvent(enabled));
    }

    private boolean isTouchExplorationEnabled() {
        return accessibilityManager.isTouchExplorationEnabled();
    }
}
