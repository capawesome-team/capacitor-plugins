package io.capawesome.capacitorjs.plugins.mailcomposer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Html;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.options.ComposeMailOptions;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.options.MailAttachment;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.results.CanComposeMailResult;
import io.capawesome.capacitorjs.plugins.mailcomposer.interfaces.NonEmptyResultCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MailComposer {

    private static final String ATTACHMENTS_DIRECTORY_NAME = "capawesome_capacitor_mail_composer_attachments";

    @NonNull
    private final MailComposerPlugin plugin;

    public MailComposer(@NonNull MailComposerPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean canComposeMail() {
        return createMailtoIntent().resolveActivity(getContext().getPackageManager()) != null;
    }

    public void canComposeMail(@NonNull NonEmptyResultCallback<CanComposeMailResult> callback) {
        callback.success(new CanComposeMailResult(canComposeMail()));
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
        if (attachmentUris.isEmpty()) {
            return intent;
        }
        return createMailAppChooserIntent(intent);
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
    private File createAttachmentFile(@NonNull MailAttachment attachment) throws IOException {
        File directory = getAttachmentsDirectory();
        directory.mkdirs();
        File file = new File(directory, attachment.getName());
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(attachment.getData());
        }
        return file;
    }

    @NonNull
    private List<Uri> createAttachmentUris(@NonNull List<MailAttachment> attachments) throws Exception {
        deleteAttachmentFiles();
        List<Uri> uris = new ArrayList<>();
        for (MailAttachment attachment : attachments) {
            String path = attachment.getPath();
            File file;
            if (path == null) {
                file = createAttachmentFile(attachment);
            } else {
                file = createFile(path);
                if (!file.exists()) {
                    throw CustomExceptions.ATTACHMENT_NOT_FOUND;
                }
            }
            uris.add(createUriForFile(file));
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

    // ACTION_SEND(_MULTIPLE) also matches generic share targets, so exclude every app that cannot handle mailto:
    @NonNull
    private Intent createMailAppChooserIntent(@NonNull Intent intent) {
        List<ComponentName> nonMailAppComponents = getNonMailAppComponents(intent);
        Intent chooserIntent = Intent.createChooser(intent, null);
        chooserIntent.putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, nonMailAppComponents.toArray(new ComponentName[0]));
        return chooserIntent;
    }

    @NonNull
    private Intent createMailtoIntent() {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
    }

    @NonNull
    private Uri createUriForFile(@NonNull File file) throws Exception {
        try {
            String authority = getContext().getPackageName() + ".fileprovider";
            return FileProvider.getUriForFile(getContext(), authority, file);
        } catch (IllegalArgumentException exception) {
            throw CustomExceptions.ATTACHMENT_NOT_FOUND;
        }
    }

    private void deleteAttachmentFiles() {
        File[] files = getAttachmentsDirectory().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            file.delete();
        }
    }

    @NonNull
    private File getAttachmentsDirectory() {
        return new File(getContext().getCacheDir(), ATTACHMENTS_DIRECTORY_NAME);
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }

    @NonNull
    private List<ComponentName> getNonMailAppComponents(@NonNull Intent intent) {
        PackageManager packageManager = getContext().getPackageManager();
        Set<String> mailAppPackageNames = new HashSet<>();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(createMailtoIntent(), 0)) {
            mailAppPackageNames.add(resolveInfo.activityInfo.packageName);
        }
        List<ComponentName> components = new ArrayList<>();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)) {
            if (!mailAppPackageNames.contains(resolveInfo.activityInfo.packageName)) {
                components.add(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            }
        }
        return components;
    }
}
