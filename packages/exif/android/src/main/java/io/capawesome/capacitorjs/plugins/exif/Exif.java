package io.capawesome.capacitorjs.plugins.exif;

import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import io.capawesome.capacitorjs.plugins.exif.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.exif.classes.options.ReadExifOptions;
import io.capawesome.capacitorjs.plugins.exif.classes.options.RemoveExifOptions;
import io.capawesome.capacitorjs.plugins.exif.classes.options.WriteExifOptions;
import io.capawesome.capacitorjs.plugins.exif.classes.results.ReadExifResult;
import io.capawesome.capacitorjs.plugins.exif.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.exif.interfaces.NonEmptyResultCallback;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Exif {

    private static final String[] REMOVABLE_TAGS = {
        ExifInterface.TAG_APERTURE_VALUE,
        ExifInterface.TAG_ARTIST,
        ExifInterface.TAG_BODY_SERIAL_NUMBER,
        ExifInterface.TAG_BRIGHTNESS_VALUE,
        ExifInterface.TAG_CAMERA_OWNER_NAME,
        ExifInterface.TAG_CFA_PATTERN,
        ExifInterface.TAG_COLOR_SPACE,
        ExifInterface.TAG_COMPONENTS_CONFIGURATION,
        ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL,
        ExifInterface.TAG_CONTRAST,
        ExifInterface.TAG_COPYRIGHT,
        ExifInterface.TAG_CUSTOM_RENDERED,
        ExifInterface.TAG_DATETIME,
        ExifInterface.TAG_DATETIME_DIGITIZED,
        ExifInterface.TAG_DATETIME_ORIGINAL,
        ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION,
        ExifInterface.TAG_DIGITAL_ZOOM_RATIO,
        ExifInterface.TAG_EXIF_VERSION,
        ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
        ExifInterface.TAG_EXPOSURE_INDEX,
        ExifInterface.TAG_EXPOSURE_MODE,
        ExifInterface.TAG_EXPOSURE_PROGRAM,
        ExifInterface.TAG_EXPOSURE_TIME,
        ExifInterface.TAG_FILE_SOURCE,
        ExifInterface.TAG_FLASH,
        ExifInterface.TAG_FLASHPIX_VERSION,
        ExifInterface.TAG_FLASH_ENERGY,
        ExifInterface.TAG_FOCAL_LENGTH,
        ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM,
        ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT,
        ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION,
        ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION,
        ExifInterface.TAG_F_NUMBER,
        ExifInterface.TAG_GAIN_CONTROL,
        ExifInterface.TAG_GAMMA,
        ExifInterface.TAG_GPS_ALTITUDE,
        ExifInterface.TAG_GPS_ALTITUDE_REF,
        ExifInterface.TAG_GPS_AREA_INFORMATION,
        ExifInterface.TAG_GPS_DATESTAMP,
        ExifInterface.TAG_GPS_DEST_BEARING,
        ExifInterface.TAG_GPS_DEST_BEARING_REF,
        ExifInterface.TAG_GPS_DEST_DISTANCE,
        ExifInterface.TAG_GPS_DEST_DISTANCE_REF,
        ExifInterface.TAG_GPS_DEST_LATITUDE,
        ExifInterface.TAG_GPS_DEST_LATITUDE_REF,
        ExifInterface.TAG_GPS_DEST_LONGITUDE,
        ExifInterface.TAG_GPS_DEST_LONGITUDE_REF,
        ExifInterface.TAG_GPS_DIFFERENTIAL,
        ExifInterface.TAG_GPS_DOP,
        ExifInterface.TAG_GPS_H_POSITIONING_ERROR,
        ExifInterface.TAG_GPS_IMG_DIRECTION,
        ExifInterface.TAG_GPS_IMG_DIRECTION_REF,
        ExifInterface.TAG_GPS_LATITUDE,
        ExifInterface.TAG_GPS_LATITUDE_REF,
        ExifInterface.TAG_GPS_LONGITUDE,
        ExifInterface.TAG_GPS_LONGITUDE_REF,
        ExifInterface.TAG_GPS_MAP_DATUM,
        ExifInterface.TAG_GPS_MEASURE_MODE,
        ExifInterface.TAG_GPS_PROCESSING_METHOD,
        ExifInterface.TAG_GPS_SATELLITES,
        ExifInterface.TAG_GPS_SPEED,
        ExifInterface.TAG_GPS_SPEED_REF,
        ExifInterface.TAG_GPS_STATUS,
        ExifInterface.TAG_GPS_TIMESTAMP,
        ExifInterface.TAG_GPS_TRACK,
        ExifInterface.TAG_GPS_TRACK_REF,
        ExifInterface.TAG_GPS_VERSION_ID,
        ExifInterface.TAG_IMAGE_DESCRIPTION,
        ExifInterface.TAG_IMAGE_UNIQUE_ID,
        ExifInterface.TAG_INTEROPERABILITY_INDEX,
        ExifInterface.TAG_ISO_SPEED,
        ExifInterface.TAG_ISO_SPEED_LATITUDE_YYY,
        ExifInterface.TAG_ISO_SPEED_LATITUDE_ZZZ,
        ExifInterface.TAG_LENS_MAKE,
        ExifInterface.TAG_LENS_MODEL,
        ExifInterface.TAG_LENS_SERIAL_NUMBER,
        ExifInterface.TAG_LENS_SPECIFICATION,
        ExifInterface.TAG_LIGHT_SOURCE,
        ExifInterface.TAG_MAKE,
        ExifInterface.TAG_MAKER_NOTE,
        ExifInterface.TAG_MAX_APERTURE_VALUE,
        ExifInterface.TAG_METERING_MODE,
        ExifInterface.TAG_MODEL,
        ExifInterface.TAG_OECF,
        ExifInterface.TAG_OFFSET_TIME,
        ExifInterface.TAG_OFFSET_TIME_DIGITIZED,
        ExifInterface.TAG_OFFSET_TIME_ORIGINAL,
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY,
        ExifInterface.TAG_PIXEL_X_DIMENSION,
        ExifInterface.TAG_PIXEL_Y_DIMENSION,
        ExifInterface.TAG_RECOMMENDED_EXPOSURE_INDEX,
        ExifInterface.TAG_RELATED_SOUND_FILE,
        ExifInterface.TAG_RESOLUTION_UNIT,
        ExifInterface.TAG_SATURATION,
        ExifInterface.TAG_SCENE_CAPTURE_TYPE,
        ExifInterface.TAG_SCENE_TYPE,
        ExifInterface.TAG_SENSING_METHOD,
        ExifInterface.TAG_SENSITIVITY_TYPE,
        ExifInterface.TAG_SHARPNESS,
        ExifInterface.TAG_SHUTTER_SPEED_VALUE,
        ExifInterface.TAG_SOFTWARE,
        ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE,
        ExifInterface.TAG_SPECTRAL_SENSITIVITY,
        ExifInterface.TAG_STANDARD_OUTPUT_SENSITIVITY,
        ExifInterface.TAG_SUBJECT_AREA,
        ExifInterface.TAG_SUBJECT_DISTANCE,
        ExifInterface.TAG_SUBJECT_DISTANCE_RANGE,
        ExifInterface.TAG_SUBJECT_LOCATION,
        ExifInterface.TAG_SUBSEC_TIME,
        ExifInterface.TAG_SUBSEC_TIME_DIGITIZED,
        ExifInterface.TAG_SUBSEC_TIME_ORIGINAL,
        ExifInterface.TAG_USER_COMMENT,
        ExifInterface.TAG_WHITE_BALANCE,
        ExifInterface.TAG_X_RESOLUTION,
        ExifInterface.TAG_Y_RESOLUTION
    };

    @NonNull
    private final ExifPlugin plugin;

    public Exif(@NonNull ExifPlugin plugin) {
        this.plugin = plugin;
    }

    public void readExif(@NonNull ReadExifOptions options, @NonNull NonEmptyResultCallback<ReadExifResult> callback) {
        File file = new File(options.getPath());
        if (!file.exists()) {
            callback.error(CustomExceptions.FILE_NOT_FOUND);
            return;
        }
        try {
            ExifInterface exifInterface = new ExifInterface(file);
            BitmapFactory.Options boundsOptions = new BitmapFactory.Options();
            boundsOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getPath(), boundsOptions);
            Integer pixelWidth = boundsOptions.outWidth > 0 ? boundsOptions.outWidth : null;
            Integer pixelHeight = boundsOptions.outHeight > 0 ? boundsOptions.outHeight : null;
            callback.success(new ReadExifResult(exifInterface, pixelWidth, pixelHeight));
        } catch (Exception exception) {
            callback.error(CustomExceptions.READ_FAILED);
        }
    }

    public void removeExif(@NonNull RemoveExifOptions options, @NonNull EmptyCallback callback) {
        File file = new File(options.getPath());
        if (!file.exists()) {
            callback.error(CustomExceptions.FILE_NOT_FOUND);
            return;
        }
        try {
            if (!isWritableFormat(file)) {
                callback.error(CustomExceptions.UNSUPPORTED_FORMAT);
                return;
            }
        } catch (Exception exception) {
            callback.error(CustomExceptions.READ_FAILED);
            return;
        }
        try {
            ExifInterface exifInterface = new ExifInterface(file);
            String orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            for (String tag : REMOVABLE_TAGS) {
                exifInterface.setAttribute(tag, null);
            }
            if (options.getKeepOrientation() && orientation != null) {
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, orientation);
            }
            exifInterface.saveAttributes();
            callback.success();
        } catch (Exception exception) {
            callback.error(CustomExceptions.WRITE_FAILED);
        }
    }

    public void writeExif(@NonNull WriteExifOptions options, @NonNull EmptyCallback callback) {
        File file = new File(options.getPath());
        if (!file.exists()) {
            callback.error(CustomExceptions.FILE_NOT_FOUND);
            return;
        }
        try {
            if (!isWritableFormat(file)) {
                callback.error(CustomExceptions.UNSUPPORTED_FORMAT);
                return;
            }
        } catch (Exception exception) {
            callback.error(CustomExceptions.READ_FAILED);
            return;
        }
        try {
            ExifInterface exifInterface = new ExifInterface(file);
            applyTags(exifInterface, options);
            exifInterface.saveAttributes();
            callback.success();
        } catch (Exception exception) {
            callback.error(CustomExceptions.WRITE_FAILED);
        }
    }

    private void applyTags(@NonNull ExifInterface exifInterface, @NonNull WriteExifOptions options) {
        if (options.getDateTimeOriginal() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_DATETIME_ORIGINAL, options.getDateTimeOriginal());
        }
        if (options.getExposureTime() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_EXPOSURE_TIME, String.valueOf(options.getExposureTime()));
        }
        if (options.getFNumber() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_F_NUMBER, String.valueOf(options.getFNumber()));
        }
        if (options.getFlash() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_FLASH, options.getFlash() ? "1" : "0");
        }
        if (options.getFocalLength() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_FOCAL_LENGTH, convertDoubleToRational(options.getFocalLength()));
        }
        if (options.getGpsAltitude() != null) {
            exifInterface.setAltitude(options.getGpsAltitude());
        }
        if (options.getGpsLatitude() != null && options.getGpsLongitude() != null) {
            exifInterface.setLatLong(options.getGpsLatitude(), options.getGpsLongitude());
        }
        if (options.getIso() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, String.valueOf(options.getIso()));
        }
        if (options.getLensModel() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_LENS_MODEL, options.getLensModel());
        }
        if (options.getMake() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_MAKE, options.getMake());
        }
        if (options.getModel() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_MODEL, options.getModel());
        }
        if (options.getOrientation() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(options.getOrientation()));
        }
        if (options.getSoftware() != null) {
            exifInterface.setAttribute(ExifInterface.TAG_SOFTWARE, options.getSoftware());
        }
    }

    @NonNull
    private String convertDoubleToRational(double value) {
        return Math.round(value * 1000) + "/1000";
    }

    private boolean isWritableFormat(@NonNull File file) throws Exception {
        byte[] header = new byte[12];
        try (InputStream inputStream = new FileInputStream(file)) {
            int bytesRead = inputStream.read(header);
            if (bytesRead < 12) {
                return false;
            }
        }
        boolean isJpeg = (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
        boolean isPng = (header[0] & 0xFF) == 0x89 && header[1] == 'P' && header[2] == 'N' && header[3] == 'G';
        boolean isWebp =
            header[0] == 'R' &&
            header[1] == 'I' &&
            header[2] == 'F' &&
            header[3] == 'F' &&
            header[8] == 'W' &&
            header[9] == 'E' &&
            header[10] == 'B' &&
            header[11] == 'P';
        return isJpeg || isPng || isWebp;
    }
}
