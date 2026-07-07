package io.capawesome.capacitorjs.plugins.applanguage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import io.capawesome.capacitorjs.plugins.applanguage.classes.options.SetLanguageOptions;
import io.capawesome.capacitorjs.plugins.applanguage.classes.results.GetLanguageResult;
import io.capawesome.capacitorjs.plugins.applanguage.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.applanguage.interfaces.NonEmptyResultCallback;

public class AppLanguage {

    @NonNull
    private final AppLanguagePlugin plugin;

    public AppLanguage(@NonNull AppLanguagePlugin plugin) {
        this.plugin = plugin;
    }

    public void getLanguage(@NonNull NonEmptyResultCallback<GetLanguageResult> callback) {
        LocaleListCompat locales = AppCompatDelegate.getApplicationLocales();
        String languageTag = locales.isEmpty() ? null : locales.get(0).toLanguageTag();
        callback.success(new GetLanguageResult(languageTag));
    }

    public void openSettings(@NonNull EmptyCallback callback) {
        Context context = plugin.getContext();
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent = new Intent(Settings.ACTION_APP_LOCALE_SETTINGS);
        } else {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        }
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        callback.success();
    }

    public void resetLanguage(@NonNull EmptyCallback callback) {
        setApplicationLocales(LocaleListCompat.getEmptyLocaleList());
        callback.success();
    }

    public void setLanguage(@NonNull SetLanguageOptions options, @NonNull EmptyCallback callback) {
        setApplicationLocales(LocaleListCompat.forLanguageTags(options.getLanguageTag()));
        callback.success();
    }

    private void setApplicationLocales(@NonNull LocaleListCompat locales) {
        Activity activity = plugin.getActivity();
        if (activity == null) {
            AppCompatDelegate.setApplicationLocales(locales);
        } else {
            activity.runOnUiThread(() -> AppCompatDelegate.setApplicationLocales(locales));
        }
    }
}
