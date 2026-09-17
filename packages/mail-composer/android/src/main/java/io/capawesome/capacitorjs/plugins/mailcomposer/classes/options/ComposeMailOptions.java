package io.capawesome.capacitorjs.plugins.mailcomposer.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ComposeMailOptions {

    @NonNull
    private final List<MailAttachment> attachments;

    @NonNull
    private final List<String> bcc;

    @Nullable
    private final String body;

    @NonNull
    private final List<String> cc;

    private final boolean isHtml;

    @Nullable
    private final String subject;

    @NonNull
    private final List<String> to;

    public ComposeMailOptions(@NonNull PluginCall call) throws Exception {
        this.to = getStringListFromCall(call, "to");
        this.cc = getStringListFromCall(call, "cc");
        this.bcc = getStringListFromCall(call, "bcc");
        this.subject = call.getString("subject");
        this.body = call.getString("body");
        this.isHtml = call.getBoolean("isHtml", false);
        this.attachments = getAttachmentsFromCall(call);
    }

    @NonNull
    public List<MailAttachment> getAttachments() {
        return attachments;
    }

    @NonNull
    public List<String> getBcc() {
        return bcc;
    }

    @Nullable
    public String getBody() {
        return body;
    }

    @NonNull
    public List<String> getCc() {
        return cc;
    }

    @Nullable
    public String getSubject() {
        return subject;
    }

    @NonNull
    public List<String> getTo() {
        return to;
    }

    public boolean isHtml() {
        return isHtml;
    }

    @NonNull
    private static List<MailAttachment> getAttachmentsFromCall(@NonNull PluginCall call) throws Exception {
        List<MailAttachment> attachments = new ArrayList<>();
        JSArray array = call.getArray("attachments");
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                attachments.add(new MailAttachment(object));
            }
        }
        return attachments;
    }

    @NonNull
    private static List<String> getStringListFromCall(@NonNull PluginCall call, @NonNull String key) throws Exception {
        List<String> values = new ArrayList<>();
        JSArray array = call.getArray(key);
        if (array != null) {
            values.addAll(array.toList());
        }
        return values;
    }
}
