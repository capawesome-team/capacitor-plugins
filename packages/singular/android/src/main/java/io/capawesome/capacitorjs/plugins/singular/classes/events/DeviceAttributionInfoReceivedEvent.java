package io.capawesome.capacitorjs.plugins.singular.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;
import java.util.Map;

public class DeviceAttributionInfoReceivedEvent implements Result {

    @NonNull
    private final Map<String, Object> attributionInfo;

    public DeviceAttributionInfoReceivedEvent(@NonNull Map<String, Object> attributionInfo) {
        this.attributionInfo = attributionInfo;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject event = new JSObject();
        putStringIfPresent(event, "campaignId", "campaign_id");
        putStringIfPresent(event, "campaignName", "campaign_name");
        putClickTimestampIfPresent(event);
        putStringIfPresent(event, "creativeId", "creative_id");
        putStringIfPresent(event, "creativeName", "creative_name");
        putStringIfPresent(event, "matchType", "match_type");
        putStringIfPresent(event, "network", "network");
        putStringIfPresent(event, "passthrough", "passthrough");
        putStringIfPresent(event, "subcampaignId", "subcampaign_id");
        putStringIfPresent(event, "subcampaignName", "subcampaign_name");
        return event;
    }

    private void putClickTimestampIfPresent(@NonNull JSObject event) {
        Object value = attributionInfo.get("click_timestamp");
        if (value instanceof Number) {
            long timestampInMicroseconds = ((Number) value).longValue();
            event.put("clickTimestamp", timestampInMicroseconds / 1000);
        }
    }

    private void putStringIfPresent(@NonNull JSObject event, @NonNull String key, @NonNull String attributionInfoKey) {
        Object value = attributionInfo.get(attributionInfoKey);
        if (value instanceof String) {
            event.put(key, (String) value);
        }
    }
}
