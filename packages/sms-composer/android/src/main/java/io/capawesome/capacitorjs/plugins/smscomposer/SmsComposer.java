package io.capawesome.capacitorjs.plugins.smscomposer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.options.ComposeSmsOptions;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.results.ComposeSmsResult;
import io.capawesome.capacitorjs.plugins.smscomposer.interfaces.NonEmptyResultCallback;
import java.util.List;

public class SmsComposer {

    public static final String STATUS_UNKNOWN = "unknown";

    private final SmsComposerPlugin plugin;

    public SmsComposer(@NonNull SmsComposerPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean canComposeSms() {
        Intent intent = createSendToIntent(null);
        return intent.resolveActivity(plugin.getContext().getPackageManager()) != null;
    }

    public void composeSms(@NonNull ComposeSmsOptions options, @NonNull NonEmptyResultCallback<ComposeSmsResult> callback) {
        Intent intent = createSendToIntent(options.getRecipients());
        String body = options.getBody();
        if (body != null) {
            intent.putExtra("sms_body", body);
        }
        try {
            plugin.getActivity().startActivity(intent);
            callback.success(new ComposeSmsResult(STATUS_UNKNOWN));
        } catch (ActivityNotFoundException exception) {
            callback.error(CustomExceptions.COMPOSE_FAILED);
        }
    }

    @NonNull
    private Intent createSendToIntent(@Nullable List<String> recipients) {
        String uri = "smsto:";
        if (recipients != null && !recipients.isEmpty()) {
            uri += TextUtils.join(";", recipients);
        }
        return new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
    }
}
