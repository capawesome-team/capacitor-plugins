package io.capawesome.capacitorjs.plugins.grafanafaro.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class FaroSession {

    @NonNull
    private final String id;

    @NonNull
    private final Map<String, String> attributes = new LinkedHashMap<>();

    private final boolean sampled;

    public FaroSession(@NonNull String id, boolean sampled) {
        this.id = id;
        this.sampled = sampled;
    }

    @NonNull
    public static FaroSession create(double samplingRate) {
        return createWithId(null, samplingRate);
    }

    @NonNull
    public static FaroSession createWithId(@Nullable String id, double samplingRate) {
        String resolvedId = (id != null && !id.isEmpty()) ? id : UUID.randomUUID().toString();
        boolean sampled = new Random().nextDouble() < samplingRate;
        return new FaroSession(resolvedId, sampled);
    }

    @NonNull
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public boolean isSampled() {
        return sampled;
    }

    public void setAttributes(@Nullable Map<String, String> source) {
        attributes.clear();
        if (source != null) {
            attributes.putAll(source);
        }
    }
}
