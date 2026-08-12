package io.capawesome.capacitorjs.plugins.datetimepicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

    public void presentMonthPicker(
        Date date,
        @Nullable Date minDate,
        @Nullable Date maxDate,
        @Nullable Locale locale,
        String cancelButtonText,
        String doneButtonText,
        @Nullable String theme,
        final PresentResultCallback resultCallback
    ) {
        if (locale != null) {
            this.updateLocaleConfiguration(locale);
        }

        Calendar selected = this.createCalendarFromDate(date);
        Locale effectiveLocale = locale != null ? locale : Locale.getDefault();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int rawMinYear = minDate != null ? getYearFromDate(minDate) : currentYear - 100;
        int rawMaxYear = maxDate != null ? getYearFromDate(maxDate) : currentYear + 100;
        final int minYear = rawMinYear > rawMaxYear ? rawMaxYear : rawMinYear;
        final int maxYear = rawMaxYear;

        int initialYear = clamp(selected.get(Calendar.YEAR), minYear, maxYear);
        int initialMonth = selected.get(Calendar.MONTH);

        int themeRes = getTheme(theme, null, null);
        Context themedContext = new ContextThemeWrapper(plugin.getContext(), themeRes);
        LayoutInflater inflater = LayoutInflater.from(themedContext);
        View dialogView = inflater.inflate(R.layout.dialog_month_picker, null);

        NumberPicker monthPicker = dialogView.findViewById(R.id.month_picker_month);
        NumberPicker yearPicker = dialogView.findViewById(R.id.month_picker_year);

        String[] monthNames = new DateFormatSymbols(effectiveLocale).getMonths();
        String[] displayedMonths = new String[12];
        System.arraycopy(monthNames, 0, displayedMonths, 0, 12);

        yearPicker.setMinValue(minYear);
        yearPicker.setMaxValue(maxYear);
        yearPicker.setValue(initialYear);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        applyMonthRange(monthPicker, displayedMonths, initialYear, minYear, maxYear, minDate, maxDate, initialMonth);

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) ->
            applyMonthRange(monthPicker, displayedMonths, newVal, minYear, maxYear, minDate, maxDate, monthPicker.getValue())
        );

        final AlertDialog dialog = new AlertDialog.Builder(themedContext)
            .setView(dialogView)
            .setPositiveButton(doneButtonText, (DialogInterface d, int which) -> {
                Calendar result = Calendar.getInstance();
                result.set(yearPicker.getValue(), monthPicker.getValue(), 1, 0, 0, 0);
                result.set(Calendar.MILLISECOND, 0);
                resultCallback.success(result.getTime());
            })
            .setNegativeButton(cancelButtonText, (DialogInterface d, int which) -> {
                resultCallback.cancel();
                d.dismiss();
            })
            .create();

        dialog.setOnDismissListener(_dialog -> {
            if (activeDialog == _dialog) {
                activeDialog = null;
            }
            resultCallback.dismiss();
        });

        activeDialog = dialog;
        dialog.show();
    }

    private void applyMonthRange(
        NumberPicker monthPicker,
        String[] monthNames,
        int year,
        int minYear,
        int maxYear,
        @Nullable Date minDate,
        @Nullable Date maxDate,
        int currentMonth
    ) {
        int minMonth = 0;
        int maxMonth = 11;
        if (minDate != null && year == minYear) {
            minMonth = getMonthFromDate(minDate);
        }
        if (maxDate != null && year == maxYear) {
            maxMonth = getMonthFromDate(maxDate);
        }
        if (minMonth > maxMonth) {
            minMonth = maxMonth;
        }

        int rangeSize = maxMonth - minMonth + 1;
        String[] displayed = new String[rangeSize];
        System.arraycopy(monthNames, minMonth, displayed, 0, rangeSize);

        monthPicker.setDisplayedValues(null);
        monthPicker.setMinValue(minMonth);
        monthPicker.setMaxValue(maxMonth);
        monthPicker.setDisplayedValues(displayed);
        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setValue(clamp(currentMonth, minMonth, maxMonth));
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private int getYearFromDate(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    private int getMonthFromDate(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
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
