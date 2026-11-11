package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.CustomExceptions;

public class AndroidNotification {

    @NonNull
    private final String channelName;

    @Nullable
    private final Integer color;

    @Nullable
    private final String icon;

    @Nullable
    private final String text;

    @Nullable
    private final String title;

    public AndroidNotification(@Nullable JSObject androidNotification) throws Exception {
        JSObject object = androidNotification == null ? new JSObject() : androidNotification;
        this.channelName = object.getString("channelName", "Navigation");
        this.color = AndroidNotification.getColorFromObject(object);
        this.icon = object.getString("icon");
        this.text = object.getString("text");
        this.title = object.getString("title");
    }

    @NonNull
    public String getChannelName() {
        return channelName;
    }

    @Nullable
    public Integer getColor() {
        return color;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    private static Integer getColorFromObject(@NonNull JSObject object) throws Exception {
        String color = object.getString("color");
        if (color == null) {
            return null;
        }
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException exception) {
            throw CustomExceptions.COLOR_INVALID;
        }
    }
}
