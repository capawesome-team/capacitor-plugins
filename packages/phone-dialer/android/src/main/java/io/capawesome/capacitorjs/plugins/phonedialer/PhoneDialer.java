package io.capawesome.capacitorjs.plugins.phonedialer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.phonedialer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.phonedialer.classes.options.DialOptions;
import io.capawesome.capacitorjs.plugins.phonedialer.interfaces.EmptyCallback;

public class PhoneDialer {

    @NonNull
    private final PhoneDialerPlugin plugin;

    public PhoneDialer(@NonNull PhoneDialerPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean canDial() {
        PackageManager packageManager = plugin.getContext().getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            return false;
        }
        Intent intent = createDialIntent(null);
        return intent.resolveActivity(packageManager) != null;
    }

    public void dial(@NonNull DialOptions options, @NonNull EmptyCallback callback) {
        Intent intent = createDialIntent(options.getNumber());
        try {
            plugin.getActivity().startActivity(intent);
            callback.success();
        } catch (ActivityNotFoundException exception) {
            callback.error(CustomExceptions.DIAL_FAILED);
        }
    }

    @NonNull
    private Intent createDialIntent(@Nullable String number) {
        String uri = "tel:" + (number == null ? "" : Uri.encode(number));
        return new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
    }
}
