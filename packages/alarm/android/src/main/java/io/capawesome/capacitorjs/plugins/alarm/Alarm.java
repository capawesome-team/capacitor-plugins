package io.capawesome.capacitorjs.plugins.alarm;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.alarm.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.alarm.classes.options.CreateAlarmOptions;
import io.capawesome.capacitorjs.plugins.alarm.classes.options.CreateTimerOptions;
import io.capawesome.capacitorjs.plugins.alarm.classes.results.CreateAlarmResult;
import io.capawesome.capacitorjs.plugins.alarm.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.alarm.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.alarm.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;

public class Alarm {

    @NonNull
    private final AlarmPlugin plugin;

    public Alarm(@NonNull AlarmPlugin plugin) {
        this.plugin = plugin;
    }

    public void createAlarm(@NonNull CreateAlarmOptions options, @NonNull NonEmptyResultCallback<CreateAlarmResult> callback)
        throws Exception {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, options.getHour());
        intent.putExtra(AlarmClock.EXTRA_MINUTES, options.getMinute());
        String label = options.getLabel();
        if (label != null) {
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, label);
        }
        ArrayList<Integer> days = options.getDays();
        if (days != null) {
            intent.putExtra(AlarmClock.EXTRA_DAYS, days);
        }
        Boolean vibrate = options.getVibrate();
        if (vibrate != null) {
            intent.putExtra(AlarmClock.EXTRA_VIBRATE, vibrate.booleanValue());
        }
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, options.getSkipUi());
        startIntent(intent);
        callback.success(new CreateAlarmResult(null));
    }

    public void createTimer(@NonNull CreateTimerOptions options, @NonNull EmptyCallback callback) throws Exception {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_LENGTH, options.getDuration());
        String label = options.getLabel();
        if (label != null) {
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, label);
        }
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, options.getSkipUi());
        startIntent(intent);
        callback.success();
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        boolean available = intent.resolveActivity(getContext().getPackageManager()) != null;
        callback.success(new IsAvailableResult(available));
    }

    public void openAlarms(@NonNull EmptyCallback callback) throws Exception {
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        startIntent(intent);
        callback.success();
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }

    private void startIntent(@NonNull Intent intent) throws Exception {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            throw CustomExceptions.NO_CLOCK_APP;
        }
    }
}
