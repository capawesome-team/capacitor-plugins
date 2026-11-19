package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UrlTemplate;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import org.json.JSONObject;

public class CreateCheckResponseBundle {

    @NonNull
    private final ArtifactType artifactType;

    @Nullable
    private final String checksum;

    @Nullable
    private final JSONObject customProperties;

    @NonNull
    private final String id;

    @Nullable
    private final String signature;

    @NonNull
    private final String url;

    @Nullable
    private final UrlTemplate urlTemplate;

    private CreateCheckResponseBundle(@NonNull JSONObject json, @NonNull ArtifactType artifactType, @NonNull String url) {
        this.artifactType = artifactType;
        this.checksum = getNonEmptyString(json, "checksum");
        this.customProperties = json.optJSONObject("customProperties");
        this.id = getNonEmptyString(json, "id");
        this.signature = getNonEmptyString(json, "signature");
        this.url = url;
        String urlTemplateText = getNonEmptyString(json, "urlTemplate");
        this.urlTemplate = urlTemplateText == null ? null : new UrlTemplate(urlTemplateText);
    }

    /**
     * @return The bundle, or `null` if a needed field is missing, the address cannot be built or the artifact type is unknown.
     */
    @Nullable
    public static CreateCheckResponseBundle parse(@NonNull JSONObject json) {
        ArtifactType artifactType = ArtifactType.fromValue(getNonEmptyString(json, "artifactType"));
        String url = buildUrl(json);
        if (artifactType == null || getNonEmptyString(json, "id") == null || url == null) {
            return null;
        }
        return new CreateCheckResponseBundle(json, artifactType, url);
    }

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
     * @return The address of the first file.
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
     * A bundle has exactly one of `url` and `urlTemplate`. A `url` is used as it is. A template is filled with the checksum.
     *
     * @return The address of the first file, or `null` if the bundle has both or neither, or a template without a checksum or the placeholder.
     */
    @Nullable
    private static String buildUrl(@NonNull JSONObject json) {
        String url = getNonEmptyString(json, "url");
        String urlTemplateText = getNonEmptyString(json, "urlTemplate");
        if (urlTemplateText == null) {
            return url;
        }
        UrlTemplate urlTemplate = new UrlTemplate(urlTemplateText);
        String checksum = getNonEmptyString(json, "checksum");
        if (url != null || checksum == null || !urlTemplate.hasChecksumPlaceholder()) {
            return null;
        }
        return urlTemplate.fill(checksum);
    }
}
