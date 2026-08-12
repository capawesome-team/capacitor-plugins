package io.capawesome.capacitorjs.plugins.mailcomposer.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class ComposeMailOptions {

    @NonNull
    private final List<String> attachments;

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

    public ComposeMailOptions(@NonNull PluginCall call) throws JSONException {
        this.to = getStringListFromCall(call, "to");
        this.cc = getStringListFromCall(call, "cc");
        this.bcc = getStringListFromCall(call, "bcc");
        this.subject = call.getString("subject");
        this.body = call.getString("body");
        this.isHtml = call.getBoolean("isHtml", false);
        this.attachments = getStringListFromCall(call, "attachments");
    }

    @NonNull
    public List<String> getAttachments() {
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
    private static List<String> getStringListFromCall(@NonNull PluginCall call, @NonNull String key) throws JSONException {
        List<String> values = new ArrayList<>();
        JSArray array = call.getArray(key);
        if (array != null) {
            values.addAll(array.toList());
        }
        return values;
    }
}
