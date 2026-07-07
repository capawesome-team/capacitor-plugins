package io.capawesome.capacitorjs.plugins.alarm.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.alarm.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateAlarmOptions {

    @Nullable
    private final ArrayList<Integer> days;

    private final int hour;

    @Nullable
    private final String label;

    private final int minute;

    private final boolean skipUi;

    @Nullable
    private final Boolean vibrate;

    public CreateAlarmOptions(@NonNull PluginCall call) throws Exception {
        JSObject androidOptions = call.getObject("android");
        this.days = CreateAlarmOptions.getDaysFromCall(call);
        this.hour = CreateAlarmOptions.getHourFromCall(call);
        this.label = call.getString("label");
        this.minute = CreateAlarmOptions.getMinuteFromCall(call);
        this.skipUi = androidOptions != null && androidOptions.optBoolean("skipUi", false);
        this.vibrate = androidOptions != null && androidOptions.has("vibrate") ? androidOptions.optBoolean("vibrate", false) : null;
    }

    @Nullable
    public ArrayList<Integer> getDays() {
        return days;
    }

    public int getHour() {
        return hour;
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    public int getMinute() {
        return minute;
    }

    public boolean getSkipUi() {
        return skipUi;
    }

    @Nullable
    public Boolean getVibrate() {
        return vibrate;
    }

    private static int getCalendarDayFromWeekday(@Nullable String weekday) throws Exception {
        if (weekday == null) {
            throw CustomExceptions.DAYS_INVALID;
        }
        switch (weekday) {
            case "FRIDAY":
                return Calendar.FRIDAY;
            case "MONDAY":
                return Calendar.MONDAY;
            case "SATURDAY":
                return Calendar.SATURDAY;
            case "SUNDAY":
                return Calendar.SUNDAY;
            case "THURSDAY":
                return Calendar.THURSDAY;
            case "TUESDAY":
                return Calendar.TUESDAY;
            case "WEDNESDAY":
                return Calendar.WEDNESDAY;
            default:
                throw CustomExceptions.DAYS_INVALID;
        }
    }

    @Nullable
    private static ArrayList<Integer> getDaysFromCall(@NonNull PluginCall call) throws Exception {
        JSArray days = call.getArray("days");
        if (days == null) {
            return null;
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int index = 0; index < days.length(); index++) {
            result.add(CreateAlarmOptions.getCalendarDayFromWeekday(days.optString(index, null)));
        }
        return result;
    }

    private static int getHourFromCall(@NonNull PluginCall call) throws Exception {
        Integer hour = call.getInt("hour");
        if (hour == null) {
            throw CustomExceptions.HOUR_MISSING;
        }
        if (hour < 0 || hour > 23) {
            throw CustomExceptions.HOUR_INVALID;
        }
        return hour;
    }

    private static int getMinuteFromCall(@NonNull PluginCall call) throws Exception {
        Integer minute = call.getInt("minute");
        if (minute == null) {
            throw CustomExceptions.MINUTE_MISSING;
        }
        if (minute < 0 || minute > 59) {
            throw CustomExceptions.MINUTE_INVALID;
        }
        return minute;
    }
}
