package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.stadiamaps.ferrostar.core.service.ForegroundNotificationBuilder;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options.AndroidNotification;
import uniffi.ferrostar.TripState;
import uniffi.ferrostar.VisualInstruction;

public class NotificationBuilder extends ForegroundNotificationBuilder {

    @NonNull
    private final String channelName;

    @Nullable
    private final Integer color;

    private final int icon;

    private boolean isNotificationChannelCreated = false;

    @Nullable
    private final String text;

    @NonNull
    private final String title;

    public NotificationBuilder(@NonNull Context context, @NonNull AndroidNotification androidNotification) {
        super(context);
        this.channelName = androidNotification.getChannelName();
        this.color = androidNotification.getColor();
        this.icon = NotificationBuilder.getIcon(context, androidNotification.getIcon());
        this.text = androidNotification.getText();
        this.title = NotificationBuilder.getTitle(context, androidNotification.getTitle());
    }

    @Override
    @NonNull
    public Notification build(@Nullable TripState tripState) {
        String channelId = getChannelId();
        if (channelId == null) {
            throw new IllegalStateException("channelId must be set before building the notification.");
        }
        createNotificationChannel(channelId);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text == null ? getInstructionText(tripState) : text)
            .setContentIntent(getOpenPendingIntent())
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        if (color != null) {
            builder.setColor(color);
        }
        return builder.build();
    }

    /**
     * The foreground service creates the channel with a fixed name right before it builds the first notification,
     * so the channel is created again here to apply the configured name.
     */
    private void createNotificationChannel(@NonNull String channelId) {
        if (isNotificationChannelCreated || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));
        isNotificationChannelCreated = true;
    }

    @SuppressLint("DiscouragedApi")
    private static int getIcon(@NonNull Context context, @Nullable String name) {
        int icon = name == null ? 0 : context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return icon == 0 ? context.getApplicationInfo().icon : icon;
    }

    @Nullable
    private static String getInstructionText(@Nullable TripState tripState) {
        if (!(tripState instanceof TripState.Navigating)) {
            return null;
        }
        VisualInstruction visualInstruction = ((TripState.Navigating) tripState).getVisualInstruction();
        return visualInstruction == null ? null : visualInstruction.getPrimaryContent().getText();
    }

    @NonNull
    private static String getTitle(@NonNull Context context, @Nullable String title) {
        if (title != null) {
            return title;
        }
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }
}
