package io.capawesome.capacitorjs.plugins.crisp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import im.crisp.client.external.data.message.Message;
import im.crisp.client.external.data.message.content.Content;
import im.crisp.client.external.data.message.content.TextContent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CrispHelper {

    @NonNull
    public static Map<String, String> convertDataToStringMap(@NonNull JSObject data) {
        Map<String, String> result = new HashMap<>();
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = data.opt(key);
            if (value != null) {
                result.put(key, value.toString());
            }
        }
        return result;
    }

    @Nullable
    public static String getMessageContent(@NonNull Message message) {
        Content content = message.getContent();
        if (content instanceof TextContent) {
            return ((TextContent) content).getText();
        }
        return null;
    }
}
