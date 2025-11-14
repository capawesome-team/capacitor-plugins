package io.capawesome.capacitorjs.plugins.datetimepicker;

import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.Date;
import java.util.Locale;

@CapacitorPlugin(name = "DatetimePicker")
public class DatetimePickerPlugin extends Plugin {

    public static final String TAG = "DatetimePickerPlugin";

    public static final String ERROR_MODE_INVALID = "The provided mode is invalid.";
    public static final String ERROR_PICKER_CANCELED = "The picker was canceled.";
    public static final String ERROR_PICKER_DISMISSED = "The picker was dismissed.";
    public static final String ERROR_CODE_CANCELED = "canceled";
    public static final String ERROR_CODE_DISMISSED = "dismissed";

    private DatetimePicker implementation;

    public void load() {
        DatetimePickerConfig config = getDatetimePickerConfig();
        implementation = new DatetimePicker(this, config);
    }

    @PluginMethod
    public void cancel(PluginCall call) {
        implementation.cancel();
        call.resolve();
    }

    @PluginMethod
    public void present(PluginCall call) {
        try {
            String format = call.getString("format", "yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            String localeString = call.getString("locale");
            String max = call.getString("max");
            String min = call.getString("min");
            String mode = call.getString("mode", "datetime");
            String theme = call.getString("theme");
            String value = call.getString("value");
            String cancelButtonText = call.getString("cancelButtonText", "Cancel");
            String doneButtonText = call.getString("doneButtonText", "Ok");
            String androidTimePickerModeText = call.getString("androidTimePickerMode", "clock");
            String androidDatePickerModeText = call.getString("androidDatePickerMode", "calendar");

            AndroidDatePickerMode androidDatePickerMode = null;
            if (androidDatePickerModeText != null) {
                androidDatePickerMode = DatetimePickerHelper.convertStringToDatePickerMode(androidDatePickerModeText);
            }
            AndroidTimePickerMode androidTimePickerMode = null;
            if (androidTimePickerModeText != null) {
                androidTimePickerMode = DatetimePickerHelper.convertStringToTimePickerMode(androidTimePickerModeText);
            }
            Locale locale = null;
            if (localeString != null) {
                locale = DatetimePickerHelper.convertStringToLocale(localeString);
            }
            Date date = new Date();
            if (value != null) {
                date = DatetimePickerHelper.convertStringToDate(format, value);
            }
            Date minDate = null;
            if (min != null) {
                minDate = DatetimePickerHelper.convertStringToDate(format, min);
            }
            Date maxDate = null;
            if (max != null) {
                maxDate = DatetimePickerHelper.convertStringToDate(format, max);
            }
            PresentResultCallback resultCallback = new PresentResultCallback();
            resultCallback.setSuccessListener(selectedDate -> {
                String dateAsString = DatetimePickerHelper.convertDateToString(format, selectedDate);
                JSObject result = new JSObject();
                result.put("value", dateAsString);
                call.resolve(result);
            });
            resultCallback.setCancelListener(() -> call.reject(ERROR_PICKER_CANCELED, ERROR_CODE_CANCELED));
            resultCallback.setDismissListener(() -> call.reject(ERROR_PICKER_DISMISSED, ERROR_CODE_DISMISSED));

            if (mode.equals("datetime")) {
                implementation.presentDateTimePicker(
                    date,
                    minDate,
                    maxDate,
                    locale,
                    cancelButtonText,
                    doneButtonText,
                    theme,
                    resultCallback,
                    androidDatePickerMode,
                    androidTimePickerMode
                );
            } else if (mode.equals("date")) {
                implementation.presentDatePicker(
                    date,
                    minDate,
                    maxDate,
                    locale,
                    cancelButtonText,
                    doneButtonText,
                    theme,
                    resultCallback,
                    androidDatePickerMode,
                    androidTimePickerMode
                );
            } else if (mode.equals("time")) {
                implementation.presentTimePicker(
                    date,
                    locale,
                    cancelButtonText,
                    doneButtonText,
                    theme,
                    resultCallback,
                    androidDatePickerMode,
                    androidTimePickerMode
                );
            } else {
                call.reject(ERROR_MODE_INVALID);
            }
        } catch (Exception ex) {
            String message = ex.getLocalizedMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    private DatetimePickerConfig getDatetimePickerConfig() {
        DatetimePickerConfig config = new DatetimePickerConfig();

        String theme = getConfig().getString("theme");
        config.setTheme(theme, config.getTheme());

        return config;
    }
}
