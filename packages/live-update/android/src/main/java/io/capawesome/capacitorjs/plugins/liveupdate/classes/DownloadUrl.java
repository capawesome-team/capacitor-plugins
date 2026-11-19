package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponseBundle;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * The address of the first file of a bundle.
 * With the marker, the fields of the bundle travel after a `#` so that the download can be verified.
 * The `#` part is cut off with string functions before any URL class sees the address.
 */
public class DownloadUrl {

    private static final String MARKER = "capawesome-live-update";

    @Nullable
    private ArtifactType artifactType;

    @Nullable
    private String checksum;

    @Nullable
    private String signature;

    @NonNull
    private final String url;

    @Nullable
    private UrlTemplate urlTemplate;

    private DownloadUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public static DownloadUrl fromBundle(@NonNull CreateCheckResponseBundle bundle) {
        DownloadUrl downloadUrl = new DownloadUrl(bundle.getUrl());
        downloadUrl.urlTemplate = bundle.getUrlTemplate();
        downloadUrl.checksum = bundle.getChecksum();
        downloadUrl.signature = bundle.getSignature();
        downloadUrl.artifactType = bundle.getArtifactType();
        return downloadUrl;
    }

    /**
     * With the marker, the fields of the `#` part win over the options.
     * Without the marker, the options are used as before this protocol.
     */
    @NonNull
    public static DownloadUrl fromOptions(@NonNull DownloadBundleOptions options) throws CustomException {
        DownloadUrl downloadUrl = parse(options.getUrl());
        if (!downloadUrl.hasMarker()) {
            downloadUrl.checksum = options.getChecksum();
            downloadUrl.signature = options.getSignature();
        }
        if (downloadUrl.artifactType == null) {
            downloadUrl.artifactType = options.getArtifactType();
        }
        return downloadUrl;
    }

    @NonNull
    public static DownloadUrl parse(@NonNull String text) throws CustomException {
        int fragmentStart = text.indexOf('#');
        if (fragmentStart == -1) {
            return new DownloadUrl(text);
        }
        String[] fragmentParts = text.substring(fragmentStart + 1).split("&", -1);
        if (!fragmentParts[0].equals(MARKER)) {
            return new DownloadUrl(text);
        }
        Map<String, String> fields = parseFields(fragmentParts);
        String checksum = fields.get("checksum");
        if (checksum == null) {
            throw CustomExceptions.CHECKSUM_MISSING;
        }
        DownloadUrl downloadUrl = new DownloadUrl(text.substring(0, fragmentStart));
        String urlTemplateText = fields.get("urlTemplate");
        downloadUrl.urlTemplate = new UrlTemplate(urlTemplateText == null ? downloadUrl.url : urlTemplateText);
        downloadUrl.checksum = checksum;
        downloadUrl.signature = fields.get("signature");
        downloadUrl.artifactType = ArtifactType.fromValue(fields.get("artifactType"));
        return downloadUrl;
    }

    /**
     * @return The artifact type, or `null` if the `#` part does not name one.
     */
    @Nullable
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Nullable
    public String getChecksum() {
        return checksum;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    /**
     * @return The address to request, without the `#` part of the marker.
     */
    @NonNull
    public String getUrl() {
        return url;
    }

    /**
     * @return The template for the files of the bundle, only set with the marker.
     */
    @Nullable
    public UrlTemplate getUrlTemplate() {
        return urlTemplate;
    }

    public boolean hasMarker() {
        return urlTemplate != null;
    }

    /**
     * @return The address with the `#` part, as `fetchLatestBundle()` returns it.
     */
    @NonNull
    @Override
    public String toString() {
        if (urlTemplate == null) {
            return url;
        }
        StringBuilder text = new StringBuilder(url).append('#').append(MARKER);
        appendField(text, "artifactType", artifactType == null ? null : artifactType.getValue());
        appendField(text, "checksum", checksum);
        appendField(text, "signature", signature);
        appendField(text, "urlTemplate", urlTemplate.getValue());
        return text.toString();
    }

    private static void appendField(@NonNull StringBuilder text, @NonNull String name, @Nullable String value) {
        if (value == null) {
            return;
        }
        text.append('&').append(name).append('=').append(encode(value));
    }

    @NonNull
    private static String decode(@NonNull String value) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            int high = i + 2 < value.length() ? Character.digit(value.charAt(i + 1), 16) : -1;
            int low = i + 2 < value.length() ? Character.digit(value.charAt(i + 2), 16) : -1;
            if (character == '%' && high != -1 && low != -1) {
                bytes.write((high << 4) + low);
                i += 2;
            } else {
                byte[] characterBytes = String.valueOf(character).getBytes(StandardCharsets.UTF_8);
                bytes.write(characterBytes, 0, characterBytes.length);
            }
        }
        return new String(bytes.toByteArray(), StandardCharsets.UTF_8);
    }

    @NonNull
    private static String encode(@NonNull String value) {
        StringBuilder encoded = new StringBuilder();
        for (byte valueByte : value.getBytes(StandardCharsets.UTF_8)) {
            char character = (char) (valueByte & 0xff);
            if (isUnreserved(character)) {
                encoded.append(character);
            } else {
                encoded.append(String.format("%%%02X", valueByte & 0xff));
            }
        }
        return encoded.toString();
    }

    private static boolean isUnreserved(char character) {
        boolean isLetter = (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');
        boolean isDigit = character >= '0' && character <= '9';
        return isLetter || isDigit || character == '-' || character == '.' || character == '_' || character == '~';
    }

    /**
     * A field with an empty value counts as a missing field.
     */
    @NonNull
    private static Map<String, String> parseFields(@NonNull String[] fragmentParts) {
        Map<String, String> fields = new HashMap<>();
        for (int i = 1; i < fragmentParts.length; i++) {
            int separator = fragmentParts[i].indexOf('=');
            if (separator == -1) {
                continue;
            }
            String value = decode(fragmentParts[i].substring(separator + 1));
            if (!value.isEmpty()) {
                fields.put(fragmentParts[i].substring(0, separator), value);
            }
        }
        return fields;
    }
}
