package io.capawesome.capacitorjs.plugins.optionpicker;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.options.PickerOption;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.options.PresentOptions;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.results.PresentResult;
import io.capawesome.capacitorjs.plugins.optionpicker.enums.Theme;
import io.capawesome.capacitorjs.plugins.optionpicker.interfaces.NonEmptyResultCallback;
import java.util.List;

public class OptionPicker {

    @NonNull
    private final OptionPickerPlugin plugin;

    @Nullable
    private AlertDialog activeDialog;

    public OptionPicker(@NonNull OptionPickerPlugin plugin) {
        this.plugin = plugin;
    }

    public void present(@NonNull PresentOptions options, @NonNull NonEmptyResultCallback<PresentResult> callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            if (activeDialog != null) {
                callback.error(CustomExceptions.PICKER_ALREADY_PRESENTED);
                return;
            }
            List<PickerOption> pickerOptions = options.getOptions();
            AlertDialog dialog = new MaterialAlertDialogBuilder(createThemedContext(activity, options.getTheme()))
                .setTitle(options.getTitle())
                .setSingleChoiceItems(getLabels(pickerOptions), getInitialIndex(pickerOptions, options.getValue()), null)
                .setPositiveButton(options.getDoneButtonText(), (dialogInterface, which) -> {
                    int selectedIndex = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                    callback.success(new PresentResult(pickerOptions.get(selectedIndex).getValue()));
                })
                .setNegativeButton(options.getCancelButtonText(), (dialogInterface, which) ->
                    callback.error(CustomExceptions.PICKER_CANCELED)
                )
                .setOnCancelListener(dialogInterface -> callback.error(CustomExceptions.PICKER_CANCELED))
                .setOnDismissListener(dialogInterface -> activeDialog = null)
                .create();
            activeDialog = dialog;
            dialog.show();
        });
    }

    @NonNull
    private Context createAppThemedContext(@NonNull Activity activity, @NonNull Theme theme) {
        switch (theme) {
            case LIGHT:
                return new ContextThemeWrapper(activity, com.google.android.material.R.style.ThemeOverlay_Material3_Light);
            case DARK:
                return new ContextThemeWrapper(activity, com.google.android.material.R.style.ThemeOverlay_Material3_Dark);
            default:
                return activity;
        }
    }

    @NonNull
    private Context createMaterialThemedContext(@NonNull Activity activity, @NonNull Theme theme) {
        Context context = new ContextThemeWrapper(activity, getMaterialTheme(theme));
        return DynamicColors.wrapContextIfAvailable(context, getDynamicColorsThemeOverlay(theme));
    }

    /**
     * MaterialAlertDialogBuilder requires a Material theme. Apps with an AppCompat theme (the Capacitor default)
     * would crash, so those get a Material 3 themed context with dynamic colors instead of inheriting the app theme.
     */
    @NonNull
    private Context createThemedContext(@NonNull Activity activity, @NonNull Theme theme) {
        if (hasMaterialTheme(activity)) {
            return createAppThemedContext(activity, theme);
        }
        return createMaterialThemedContext(activity, theme);
    }

    @StyleRes
    private int getDynamicColorsThemeOverlay(@NonNull Theme theme) {
        switch (theme) {
            case LIGHT:
                return com.google.android.material.R.style.ThemeOverlay_Material3_DynamicColors_Light;
            case DARK:
                return com.google.android.material.R.style.ThemeOverlay_Material3_DynamicColors_Dark;
            default:
                return com.google.android.material.R.style.ThemeOverlay_Material3_DynamicColors_DayNight;
        }
    }

    private int getInitialIndex(@NonNull List<PickerOption> options, @Nullable String value) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getValue().equals(value)) {
                return i;
            }
        }
        return 0;
    }

    @NonNull
    private CharSequence[] getLabels(@NonNull List<PickerOption> options) {
        CharSequence[] labels = new CharSequence[options.size()];
        for (int i = 0; i < options.size(); i++) {
            labels[i] = options.get(i).getLabel();
        }
        return labels;
    }

    @StyleRes
    private int getMaterialTheme(@NonNull Theme theme) {
        switch (theme) {
            case LIGHT:
                return com.google.android.material.R.style.Theme_Material3_Light;
            case DARK:
                return com.google.android.material.R.style.Theme_Material3_Dark;
            default:
                return com.google.android.material.R.style.Theme_Material3_DayNight;
        }
    }

    private boolean hasMaterialTheme(@NonNull Activity activity) {
        TypedValue typedValue = new TypedValue();
        return activity.getTheme().resolveAttribute(com.google.android.material.R.attr.materialAlertDialogTheme, typedValue, true);
    }
}
