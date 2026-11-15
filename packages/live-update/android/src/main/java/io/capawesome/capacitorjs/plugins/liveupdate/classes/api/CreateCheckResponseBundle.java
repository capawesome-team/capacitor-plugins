package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.FileUrlTemplate;
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
    private final FileUrlTemplate fileUrlTemplate;

    @NonNull
    private final String id;

    @Nullable
    private final String signature;

    private CreateCheckResponseBundle(@NonNull JSONObject json, @NonNull ArtifactType artifactType) {
        this.artifactType = artifactType;
        this.checksum = getNonEmptyString(json, "checksum");
        this.customProperties = json.optJSONObject("customProperties");
        this.fileUrlTemplate = new FileUrlTemplate(getNonEmptyString(json, "fileUrlTemplate"));
        this.id = getNonEmptyString(json, "id");
        this.signature = getNonEmptyString(json, "signature");
    }

    /**
     * The checksum is needed only if the template holds the checksum placeholder.
     *
     * @return The bundle, or `null` if a needed field is missing or the artifact type is unknown.
     */
    @Nullable
    public static CreateCheckResponseBundle parse(@NonNull JSONObject json) {
        ArtifactType artifactType = ArtifactType.fromValue(getNonEmptyString(json, "artifactType"));
        String fileUrlTemplate = getNonEmptyString(json, "fileUrlTemplate");
        if (artifactType == null || fileUrlTemplate == null || getNonEmptyString(json, "id") == null) {
            return null;
        }
        boolean needsChecksum = new FileUrlTemplate(fileUrlTemplate).hasChecksumPlaceholder();
        if (needsChecksum && getNonEmptyString(json, "checksum") == null) {
            return null;
        }
        return new CreateCheckResponseBundle(json, artifactType);
    }

    @NonNull
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    /**
     * @return The checksum of the first file, always set if the template holds the checksum placeholder.
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
    public FileUrlTemplate getFileUrlTemplate() {
        return fileUrlTemplate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }
}
