package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UrlTemplate;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import org.json.JSONObject;

public class CreateCheckResponseBundle {

    @NonNull
    private ArtifactType artifactType;

    @Nullable
    private String checksum;

    @Nullable
    private JSONObject customProperties;

    @NonNull
    private String id;

    @Nullable
    private String signature;

    @NonNull
    private String url;

    @Nullable
    private UrlTemplate urlTemplate;

    private CreateCheckResponseBundle() {}

    @NonNull
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    /**
     * @return The checksum of the first file, always set if the bundle has a URL template.
     */
    @Nullable
    public String getChecksum() {
        return checksum;
    }

    @Nullable
    public JSONObject getCustomProperties() {
        return customProperties;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    /**
     * @return The plain address of the bundle: `url`, or else `legacyUrl`.
     */
    @NonNull
    public String getUrl() {
        return url;
    }

    @Nullable
    public UrlTemplate getUrlTemplate() {
        return urlTemplate;
    }

    /**
     * The plain address is `url`, or else `legacyUrl`. A `urlTemplate` is optional, but it needs `checksum` and the placeholder.
     *
     * @return The bundle, or `null` if a needed field is missing, the URL template cannot be used or the artifact type is unknown.
     */
    @Nullable
    public static CreateCheckResponseBundle parse(@NonNull JSONObject json) {
        CreateCheckResponseBundle bundle = new CreateCheckResponseBundle();
        bundle.artifactType = ArtifactType.fromValue(getNonEmptyString(json, "artifactType"));
        bundle.checksum = getNonEmptyString(json, "checksum");
        bundle.customProperties = json.optJSONObject("customProperties");
        bundle.id = getNonEmptyString(json, "id");
        bundle.signature = getNonEmptyString(json, "signature");
        bundle.url = getNonEmptyString(json, "url");
        if (bundle.url == null) {
            bundle.url = getNonEmptyString(json, "legacyUrl");
        }
        String urlTemplateText = getNonEmptyString(json, "urlTemplate");
        bundle.urlTemplate = urlTemplateText == null ? null : new UrlTemplate(urlTemplateText);
        if (bundle.artifactType == null || bundle.id == null || bundle.url == null) {
            return null;
        }
        if (bundle.urlTemplate != null && (bundle.checksum == null || !bundle.urlTemplate.hasChecksumPlaceholder())) {
            return null;
        }
        return bundle;
    }
}
