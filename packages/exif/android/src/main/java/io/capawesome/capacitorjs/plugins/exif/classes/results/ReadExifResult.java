package io.capawesome.capacitorjs.plugins.exif.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.exif.interfaces.Result;

public class ReadExifResult implements Result {

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

    @Nullable
    private final Integer pixelHeight;

    @Nullable
    private final Integer pixelWidth;

    @Nullable
    private final String software;

    public ReadExifResult(@NonNull ExifInterface exifInterface, @Nullable Integer pixelWidth, @Nullable Integer pixelHeight) {
        double[] latLong = exifInterface.getLatLong();
        Integer flashValue = ReadExifResult.getIntAttribute(exifInterface, ExifInterface.TAG_FLASH);
        this.dateTimeOriginal = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);
        this.exposureTime = ReadExifResult.getDoubleAttribute(exifInterface, ExifInterface.TAG_EXPOSURE_TIME);
        this.fNumber = ReadExifResult.getDoubleAttribute(exifInterface, ExifInterface.TAG_F_NUMBER);
        this.flash = flashValue == null ? null : (flashValue & 1) == 1;
        this.focalLength = ReadExifResult.getDoubleAttribute(exifInterface, ExifInterface.TAG_FOCAL_LENGTH);
        this.gpsAltitude = exifInterface.hasAttribute(ExifInterface.TAG_GPS_ALTITUDE) ? exifInterface.getAltitude(0) : null;
        this.gpsLatitude = latLong == null ? null : latLong[0];
        this.gpsLongitude = latLong == null ? null : latLong[1];
        this.iso = ReadExifResult.getIntAttribute(exifInterface, ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY);
        this.lensModel = exifInterface.getAttribute(ExifInterface.TAG_LENS_MODEL);
        this.make = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
        this.model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
        this.orientation = ReadExifResult.getIntAttribute(exifInterface, ExifInterface.TAG_ORIENTATION);
        this.pixelHeight = pixelHeight;
        this.pixelWidth = pixelWidth;
        this.software = exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE);
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject tags = new JSObject();
        if (dateTimeOriginal != null) {
            tags.put("dateTimeOriginal", dateTimeOriginal);
        }
        if (exposureTime != null) {
            tags.put("exposureTime", (double) exposureTime);
        }
        if (fNumber != null) {
            tags.put("fNumber", (double) fNumber);
        }
        if (flash != null) {
            tags.put("flash", (boolean) flash);
        }
        if (focalLength != null) {
            tags.put("focalLength", (double) focalLength);
        }
        if (gpsAltitude != null) {
            tags.put("gpsAltitude", (double) gpsAltitude);
        }
        if (gpsLatitude != null) {
            tags.put("gpsLatitude", (double) gpsLatitude);
        }
        if (gpsLongitude != null) {
            tags.put("gpsLongitude", (double) gpsLongitude);
        }
        if (iso != null) {
            tags.put("iso", (int) iso);
        }
        if (lensModel != null) {
            tags.put("lensModel", lensModel);
        }
        if (make != null) {
            tags.put("make", make);
        }
        if (model != null) {
            tags.put("model", model);
        }
        if (orientation != null) {
            tags.put("orientation", (int) orientation);
        }
        if (pixelHeight != null) {
            tags.put("pixelHeight", (int) pixelHeight);
        }
        if (pixelWidth != null) {
            tags.put("pixelWidth", (int) pixelWidth);
        }
        if (software != null) {
            tags.put("software", software);
        }
        JSObject result = new JSObject();
        result.put("tags", tags);
        return result;
    }

    @Nullable
    private static Double getDoubleAttribute(@NonNull ExifInterface exifInterface, @NonNull String tag) {
        if (!exifInterface.hasAttribute(tag)) {
            return null;
        }
        return exifInterface.getAttributeDouble(tag, 0);
    }

    @Nullable
    private static Integer getIntAttribute(@NonNull ExifInterface exifInterface, @NonNull String tag) {
        if (!exifInterface.hasAttribute(tag)) {
            return null;
        }
        return exifInterface.getAttributeInt(tag, 0);
    }
}
