package io.capawesome.capacitorjs.plugins.smscomposer.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import java.util.List;
import org.json.JSONException;

public class ComposeSmsOptions {

    @Nullable
    private final String body;

    @Nullable
    private final List<String> recipients;

    public ComposeSmsOptions(@NonNull PluginCall call) {
        this.recipients = getRecipientsFromCall(call);
        this.body = call.getString("body");
    }

    @Nullable
    public String getBody() {
        return body;
    }

    @Nullable
    public List<String> getRecipients() {
        return recipients;
    }

    @Nullable
    private static List<String> getRecipientsFromCall(@NonNull PluginCall call) {
        JSArray array = call.getArray("recipients");
        if (array == null) {
            return null;
        }
        try {
            return array.toList();
        } catch (JSONException exception) {
            return null;
        }
    }
}
