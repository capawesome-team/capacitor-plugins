package io.capawesome.capacitorjs.plugins.mailcomposer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.options.ComposeMailOptions;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.results.CanComposeMailResult;
import io.capawesome.capacitorjs.plugins.mailcomposer.interfaces.NonEmptyResultCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MailComposer {

    @NonNull
    private final MailComposerPlugin plugin;

    public MailComposer(@NonNull MailComposerPlugin plugin) {
        this.plugin = plugin;
    }

    public void canComposeMail(@NonNull NonEmptyResultCallback<CanComposeMailResult> callback) {
        boolean canCompose = canResolveIntent(createMailtoIntent());
        callback.success(new CanComposeMailResult(canCompose));
    }

    public boolean canResolveIntent(@NonNull Intent intent) {
        return intent.resolveActivity(getContext().getPackageManager()) != null;
    }

    @NonNull
    public Intent createComposeIntent(@NonNull ComposeMailOptions options) throws Exception {
        List<Uri> attachmentUris = createAttachmentUris(options.getAttachments());
        Intent intent;
        if (attachmentUris.isEmpty()) {
            intent = createMailtoIntent();
        } else if (attachmentUris.size() == 1) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_STREAM, attachmentUris.get(0));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("message/rfc822");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, new ArrayList<>(attachmentUris));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        applyRecipients(intent, options);
        applyContent(intent, options);
        return intent;
    }

    private void applyContent(@NonNull Intent intent, @NonNull ComposeMailOptions options) {
        if (options.getSubject() != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, options.getSubject());
        }
        String body = options.getBody();
        if (body != null) {
            if (options.isHtml()) {
                intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY).toString());
                intent.putExtra(Intent.EXTRA_HTML_TEXT, body);
            } else {
                intent.putExtra(Intent.EXTRA_TEXT, body);
            }
        }
    }

    private void applyRecipients(@NonNull Intent intent, @NonNull ComposeMailOptions options) {
        if (!options.getTo().isEmpty()) {
            intent.putExtra(Intent.EXTRA_EMAIL, options.getTo().toArray(new String[0]));
        }
        if (!options.getCc().isEmpty()) {
            intent.putExtra(Intent.EXTRA_CC, options.getCc().toArray(new String[0]));
        }
        if (!options.getBcc().isEmpty()) {
            intent.putExtra(Intent.EXTRA_BCC, options.getBcc().toArray(new String[0]));
        }
    }

    @NonNull
    private List<Uri> createAttachmentUris(@NonNull List<String> attachments) throws Exception {
        List<Uri> uris = new ArrayList<>();
        for (String attachment : attachments) {
            File file = createFile(attachment);
            if (!file.exists()) {
                throw CustomExceptions.ATTACHMENT_NOT_FOUND;
            }
            try {
                String authority = getContext().getPackageName() + ".fileprovider";
                uris.add(FileProvider.getUriForFile(getContext(), authority, file));
            } catch (IllegalArgumentException exception) {
                throw CustomExceptions.ATTACHMENT_NOT_FOUND;
            }
        }
        return uris;
    }

    @NonNull
    private File createFile(@NonNull String path) {
        if (path.startsWith("file://")) {
            String filePath = Uri.parse(path).getPath();
            return new File(filePath == null ? path : filePath);
        }
        return new File(path);
    }

    @NonNull
    private Intent createMailtoIntent() {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }
}
