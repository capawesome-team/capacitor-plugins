package io.capawesome.capacitorjs.plugins.datetimepicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatetimePicker {

    private final DatetimePickerPlugin plugin;
    private final DatetimePickerConfig config;
    private Dialog activeDialog;

    public DatetimePicker(DatetimePickerPlugin plugin, DatetimePickerConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void presentDateTimePicker(
        Date date,
        @Nullable Date minDate,
        @Nullable Date maxDate,
        @Nullable Locale locale,
        String cancelButtonText,
        String doneButtonText,
        @Nullable String theme,
        final PresentResultCallback resultCallback,
        @Nullable AndroidDatePickerMode androidDatePickerMode,
        @Nullable AndroidTimePickerMode androidTimePickerMode
    ) {
        PresentResultCallback dateResultCallback = new PresentResultCallback();
        dateResultCallback.setSuccessListener(selectedDate -> {
            PresentResultCallback timeResultCallback = new PresentResultCallback();
            timeResultCallback.setSuccessListener((Date selectedDateAndTime) -> resultCallback.success(selectedDateAndTime));
            timeResultCallback.setCancelListener(() -> resultCallback.cancel());
            timeResultCallback.setDismissListener(() -> resultCallback.dismiss());
            presentTimePicker(
                selectedDate,
                locale,
                cancelButtonText,
                doneButtonText,
                theme,
                timeResultCallback,
                androidDatePickerMode,
                androidTimePickerMode
            );
        });
        dateResultCallback.setCancelListener(() -> resultCallback.cancel());
        dateResultCallback.setDismissListener(() -> resultCallback.dismiss());
        presentDatePicker(
            date,
            minDate,
            maxDate,
            locale,
            cancelButtonText,
            doneButtonText,
            theme,
            dateResultCallback,
            androidDatePickerMode,
            androidTimePickerMode
        );
    }

    public void presentDatePicker(
        Date date,
        @Nullable Date minDate,
        @Nullable Date maxDate,
        @Nullable Locale locale,
        String cancelButtonText,
        String doneButtonText,
        @Nullable String theme,
        final PresentResultCallback resultCallback,
        @Nullable AndroidDatePickerMode androidDatePickerMode,
        @Nullable AndroidTimePickerMode androidTimePickerMode
    ) {
        if (locale != null) {
            this.updateLocaleConfiguration(locale);
        }

        Calendar calendar = this.createCalendarFromDate(date);

        final DatePickerDialog dialog = new DatePickerDialog(
            plugin.getContext(),
            getTheme(theme, androidDatePickerMode, androidTimePickerMode),
            (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                resultCallback.success(calendar.getTime());
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.setOnDismissListener(_dialog -> {
            if (activeDialog == _dialog) {
                activeDialog = null;
            }
            resultCallback.dismiss();
        });
        dialog.create();
        activeDialog = dialog;

        Button doneButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        doneButton.setText(doneButtonText);

        Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        cancelButton.setText(cancelButtonText);
        cancelButton.setOnClickListener(view -> {
            resultCallback.cancel();
            dialog.dismiss();
        });

        DatePicker picker = dialog.getDatePicker();
        if (minDate != null) {
            picker.setMinDate(minDate.getTime());
        }
        if (maxDate != null) {
            picker.setMaxDate(maxDate.getTime());
        }

        dialog.show();
    }

    public void presentTimePicker(
        Date date,
        @Nullable Locale locale,
        String cancelButtonText,
        String doneButtonText,
        @Nullable String theme,
        final PresentResultCallback resultCallback,
        @Nullable AndroidDatePickerMode androidDatePickerMode,
        @Nullable AndroidTimePickerMode androidTimePickerMode
    ) {
        if (locale != null) {
            this.updateLocaleConfiguration(locale);
        }

        Calendar calendar = this.createCalendarFromDate(date);
        boolean is24HourView = DateFormat.is24HourFormat(plugin.getContext());
        if (locale != null) {
            is24HourView = DatetimePickerHelper.is24HourLocale(locale);
        }

        final TimePickerDialog dialog = new TimePickerDialog(
            plugin.getContext(),
            getTheme(theme, androidDatePickerMode, androidTimePickerMode),
            (view, hourOfDay, minute) -> {
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    hourOfDay,
                    minute
                );
                resultCallback.success(calendar.getTime());
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            is24HourView
        );
        dialog.setOnDismissListener(_dialog -> {
            if (activeDialog == _dialog) {
                activeDialog = null;
            }
            resultCallback.dismiss();
        });
        dialog.create();
        activeDialog = dialog;

        Button doneButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        doneButton.setText(doneButtonText);

        Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
        cancelButton.setText(cancelButtonText);
        cancelButton.setOnClickListener(view -> {
            resultCallback.cancel();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void presentMonthYearPicker(
        Date date,
        @Nullable Date minDate,
        @Nullable Date maxDate,
        @Nullable Locale locale,
        String cancelButtonText,
        String doneButtonText,
        @Nullable String theme,
        final PresentResultCallback resultCallback,
        @Nullable AndroidDatePickerMode androidDatePickerMode,
        @Nullable AndroidTimePickerMode androidTimePickerMode
    ) {
        if (locale != null) {
            this.updateLocaleConfiguration(locale);
        }

        Calendar calendar = this.createCalendarFromDate(date);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        int minYear = currentYear - 100;
        int minMonthValue = Calendar.JANUARY;
        if (minDate != null) {
            Calendar minCal = this.createCalendarFromDate(minDate);
            minYear = minCal.get(Calendar.YEAR);
            minMonthValue = minCal.get(Calendar.MONTH);
        }

        int maxYear = currentYear + 100;
        int maxMonthValue = Calendar.DECEMBER;
        if (maxDate != null) {
            Calendar maxCal = this.createCalendarFromDate(maxDate);
            maxYear = maxCal.get(Calendar.YEAR);
            maxMonthValue = maxCal.get(Calendar.MONTH);
        }

        Locale displayLocale = locale != null ? locale : Locale.getDefault();
        String[] monthNames = new DateFormatSymbols(displayLocale).getMonths();

        LinearLayout layout = new LinearLayout(plugin.getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        int padding = (int) (24 * plugin.getContext().getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);

        NumberPicker monthPicker = new NumberPicker(plugin.getContext());
        NumberPicker yearPicker = new NumberPicker(plugin.getContext());

        final int fMinYear = minYear;
        final int fMaxYear = maxYear;
        final int fMinMonth = minMonthValue;
        final int fMaxMonth = maxMonthValue;

        yearPicker.setMinValue(minYear);
        yearPicker.setMaxValue(maxYear);
        yearPicker.setValue(currentYear);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        updateMonthPicker(monthPicker, currentYear, fMinYear, fMaxYear, fMinMonth, fMaxMonth, monthNames);
        monthPicker.setValue(currentMonth);
        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int previousMonth = monthPicker.getValue();
            updateMonthPicker(monthPicker, newVal, fMinYear, fMaxYear, fMinMonth, fMaxMonth, monthNames);
            int newMin = monthPicker.getMinValue();
            int newMax = monthPicker.getMaxValue();
            int clampedMonth = Math.min(Math.max(previousMonth, newMin), newMax);
            monthPicker.setValue(clampedMonth);
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        layout.addView(monthPicker, params);
        layout.addView(yearPicker, params);

        AlertDialog.Builder builder = new AlertDialog.Builder(
            plugin.getContext(),
            getTheme(theme, null, null)
        );
        builder.setView(layout);
        builder.setPositiveButton(doneButtonText, (dialog, which) -> {
            calendar.set(Calendar.YEAR, yearPicker.getValue());
            calendar.set(Calendar.MONTH, monthPicker.getValue());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            resultCallback.success(calendar.getTime());
        });
        builder.setNegativeButton(cancelButtonText, (dialog, which) -> {
            resultCallback.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(_dialog -> resultCallback.dismiss());
        dialog.show();
    }

    private void updateMonthPicker(
        NumberPicker monthPicker,
        int year,
        int minYear,
        int maxYear,
        int minMonth,
        int maxMonth,
        String[] monthNames
    ) {
        int lower = (year == minYear) ? minMonth : Calendar.JANUARY;
        int upper = (year == maxYear) ? maxMonth : Calendar.DECEMBER;
        String[] displayed = new String[upper - lower + 1];
        for (int i = lower; i <= upper; i++) {
            displayed[i - lower] = monthNames[i];
        }
        monthPicker.setDisplayedValues(null);
        monthPicker.setMinValue(lower);
        monthPicker.setMaxValue(upper);
        monthPicker.setDisplayedValues(displayed);
    }

    private Calendar createCalendarFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private int getTheme(
        @Nullable String unconvertedTheme,
        @Nullable AndroidDatePickerMode androidDatePickerMode,
        @Nullable AndroidTimePickerMode androidTimePickerMode
    ) {
        Theme theme = config.getTheme();
        Theme overrideConfig = DatetimePickerHelper.convertStringToTheme(unconvertedTheme);
        if (overrideConfig != null) {
            theme = overrideConfig;
        }
        if (androidDatePickerMode != AndroidDatePickerMode.SPINNER && androidTimePickerMode != AndroidTimePickerMode.SPINNER) {
            switch (theme) {
                case LIGHT:
                    return R.style.MaterialLightTheme;
                case DARK:
                    return R.style.MaterialDarkTheme;
                case AUTO: {
                    int nightModeFlags = plugin.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (nightModeFlags) {
                        case Configuration.UI_MODE_NIGHT_YES:
                            return R.style.MaterialDarkTheme;
                        case Configuration.UI_MODE_NIGHT_NO:
                            return R.style.MaterialLightTheme;
                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                            return R.style.MaterialLightTheme;
                    }
                }
            }
        }
        if (androidDatePickerMode == AndroidDatePickerMode.SPINNER && androidTimePickerMode != AndroidTimePickerMode.SPINNER) {
            switch (theme) {
                case LIGHT:
                    return R.style.MaterialLightTheme_DatePickerStyleSpinner;
                case DARK:
                    return R.style.MaterialDarkTheme_DatePickerStyleSpinner;
                case AUTO: {
                    int nightModeFlags = plugin.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (nightModeFlags) {
                        case Configuration.UI_MODE_NIGHT_YES:
                            return R.style.MaterialDarkTheme_DatePickerStyleSpinner;
                        case Configuration.UI_MODE_NIGHT_NO:
                            return R.style.MaterialLightTheme_DatePickerStyleSpinner;
                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                            return R.style.MaterialLightTheme_DatePickerStyleSpinner;
                    }
                }
            }
        }
        if (androidDatePickerMode != AndroidDatePickerMode.SPINNER && androidTimePickerMode == AndroidTimePickerMode.SPINNER) {
            switch (theme) {
                case LIGHT:
                    return R.style.MaterialLightTheme_TimePickerStyleSpinner;
                case DARK:
                    return R.style.MaterialDarkTheme_TimePickerStyleSpinner;
                case AUTO: {
                    int nightModeFlags = plugin.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (nightModeFlags) {
                        case Configuration.UI_MODE_NIGHT_YES:
                            return R.style.MaterialDarkTheme_TimePickerStyleSpinner;
                        case Configuration.UI_MODE_NIGHT_NO:
                            return R.style.MaterialLightTheme_TimePickerStyleSpinner;
                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                            return R.style.MaterialLightTheme_TimePickerStyleSpinner;
                    }
                }
            }
        }
        if (androidDatePickerMode == AndroidDatePickerMode.SPINNER && androidTimePickerMode == AndroidTimePickerMode.SPINNER) {
            switch (theme) {
                case LIGHT:
                    return R.style.MaterialLightTheme_DateTimePickerStyleSpinner;
                case DARK:
                    return R.style.MaterialDarkTheme_DateTimePickerStyleSpinner;
                case AUTO: {
                    int nightModeFlags = plugin.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (nightModeFlags) {
                        case Configuration.UI_MODE_NIGHT_YES:
                            return R.style.MaterialDarkTheme_DateTimePickerStyleSpinner;
                        case Configuration.UI_MODE_NIGHT_NO:
                            return R.style.MaterialLightTheme_DateTimePickerStyleSpinner;
                        case Configuration.UI_MODE_NIGHT_UNDEFINED:
                            return R.style.MaterialLightTheme_DateTimePickerStyleSpinner;
                    }
                }
            }
        }
        return R.style.MaterialLightTheme;
    }

    private void updateLocaleConfiguration(@NonNull Locale locale) {
        Configuration config = new Configuration();
        config.locale = locale;
        plugin.getContext().getResources().updateConfiguration(config, plugin.getContext().getResources().getDisplayMetrics());
    }

    public void cancel() {
        if (activeDialog != null && activeDialog.isShowing()) {
            activeDialog.dismiss();
            activeDialog = null;
        }
    }
}
