package io.capawesome.capacitorjs.plugins.toast;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.toast.classes.options.ShowOptions;
import io.capawesome.capacitorjs.plugins.toast.interfaces.EmptyCallback;

public class Toast {

    @NonNull
    private final ToastPlugin plugin;

    public Toast(@NonNull ToastPlugin plugin) {
        this.plugin = plugin;
    }

    public void show(@NonNull ShowOptions options, @NonNull EmptyCallback callback) {
        int duration = getDurationForValue(options.getDuration());
        int gravity = getGravityForPosition(options.getPosition());
        getActivity()
            .runOnUiThread(() -> {
                android.widget.Toast toast = android.widget.Toast.makeText(getContext(), options.getText(), duration);
                if (gravity != Gravity.NO_GRAVITY) {
                    toast.setGravity(gravity, 0, 0);
                }
                toast.show();
                callback.success();
            });
    }

    private int getDurationForValue(@NonNull String value) {
        if (value.equals("LONG")) {
            return android.widget.Toast.LENGTH_LONG;
        }
        return android.widget.Toast.LENGTH_SHORT;
    }

    private int getGravityForPosition(@NonNull String position) {
        switch (position) {
            case "TOP":
                return Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            case "CENTER":
                return Gravity.CENTER;
            default:
                return Gravity.NO_GRAVITY;
        }
    }

    @NonNull
    private Activity getActivity() {
        return plugin.getActivity();
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }
}
