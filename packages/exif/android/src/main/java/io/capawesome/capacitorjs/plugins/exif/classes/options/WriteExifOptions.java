package io.capawesome.capacitorjs.plugins.exif.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.exif.classes.CustomExceptions;

public class WriteExifOptions {

    @Nullable
    private final String dateTimeOriginal;

    @Nullable
    private final Double exposureTime;

    @Nullable
    private final Double fNumber;

    @Nullable
    private final Boolean flash;

    @Nullable
    private final Double focalLength;

    @Nullable
    private final Double gpsAltitude;

    @Nullable
    private final Double gpsLatitude;

    @Nullable
    private final Double gpsLongitude;

    @Nullable
    private final Integer iso;

    @Nullable
    private final String lensModel;

    @Nullable
    private final String make;

    @Nullable
    private final String model;

    @Nullable
    private final Integer orientation;

    @NonNull
    private final String path;

    @Nullable
    private final String software;

    public WriteExifOptions(@NonNull PluginCall call) throws Exception {
        JSObject tags = WriteExifOptions.getTagsFromCall(call);
        this.dateTimeOriginal = tags.getString("dateTimeOriginal");
        this.exposureTime = WriteExifOptions.getDoubleFromTags(tags, "exposureTime");
        this.fNumber = WriteExifOptions.getDoubleFromTags(tags, "fNumber");
        this.flash = tags.getBool("flash");
        this.focalLength = WriteExifOptions.getDoubleFromTags(tags, "focalLength");
        this.gpsAltitude = WriteExifOptions.getDoubleFromTags(tags, "gpsAltitude");
        this.gpsLatitude = WriteExifOptions.getDoubleFromTags(tags, "gpsLatitude");
        this.gpsLongitude = WriteExifOptions.getDoubleFromTags(tags, "gpsLongitude");
        this.iso = tags.getInteger("iso");
        this.lensModel = tags.getString("lensModel");
        this.make = tags.getString("make");
        this.model = tags.getString("model");
        this.orientation = tags.getInteger("orientation");
        this.path = WriteExifOptions.getPathFromCall(call);
        this.software = tags.getString("software");
        if ((gpsLatitude == null) != (gpsLongitude == null)) {
            throw CustomExceptions.GPS_COORDINATES_INCOMPLETE;
        }
    }

    @Nullable
    public String getDateTimeOriginal() {
        return dateTimeOriginal;
    }

    @Nullable
    public Double getExposureTime() {
        return exposureTime;
    }

    @Nullable
    public Double getFNumber() {
        return fNumber;
    }

    @Nullable
    public Boolean getFlash() {
        return flash;
    }

    @Nullable
    public Double getFocalLength() {
        return focalLength;
    }

    @Nullable
    public Double getGpsAltitude() {
        return gpsAltitude;
    }

    @Nullable
    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    @Nullable
    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    @Nullable
    public Integer getIso() {
        return iso;
    }

    @Nullable
    public String getLensModel() {
        return lensModel;
    }

    @Nullable
    public String getMake() {
        return make;
    }

    @Nullable
    public String getModel() {
        return model;
    }

    @Nullable
    public Integer getOrientation() {
        return orientation;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @Nullable
    public String getSoftware() {
        return software;
    }

    @Nullable
    private static Double getDoubleFromTags(@NonNull JSObject tags, @NonNull String key) throws Exception {
        if (!tags.has(key)) {
            return null;
        }
        return tags.getDouble(key);
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null || path.isEmpty()) {
            throw CustomExceptions.PATH_MISSING;
        }
        if (path.startsWith("file://")) {
            String uriPath = Uri.parse(path).getPath();
            if (uriPath != null) {
                return uriPath;
            }
        }
        return path;
    }

    @NonNull
    private static JSObject getTagsFromCall(@NonNull PluginCall call) throws Exception {
        JSObject tags = call.getObject("tags");
        if (tags == null) {
            throw CustomExceptions.TAGS_MISSING;
        }
        return tags;
    }
}
