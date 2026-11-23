package io.capawesome.capacitorjs.plugins.liveupdate.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum ArtifactType {
    MANIFEST("manifest"),
    ZIP("zip");

    @NonNull
    private final String value;

    ArtifactType(@NonNull String value) {
        this.value = value;
    }

    @Nullable
    public static ArtifactType fromValue(@Nullable String value) {
        for (ArtifactType artifactType : values()) {
            if (artifactType.value.equals(value)) {
                return artifactType;
            }
        }
        return null;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
