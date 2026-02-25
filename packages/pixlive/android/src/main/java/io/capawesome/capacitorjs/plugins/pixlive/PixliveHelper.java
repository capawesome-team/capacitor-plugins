package io.capawesome.capacitorjs.plugins.pixlive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.vidinoti.android.vdarsdk.VDARCodeType;
import com.vidinoti.android.vdarsdk.VDARContext;
import com.vidinoti.android.vdarsdk.VDARContextPrior;
import com.vidinoti.android.vdarsdk.VDARIntersectionPrior;
import com.vidinoti.android.vdarsdk.VDARPrior;
import com.vidinoti.android.vdarsdk.VDARTagPrior;
import com.vidinoti.android.vdarsdk.VDARTourPrior;
import com.vidinoti.android.vdarsdk.geopoint.VDARGPSPoint;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PixliveHelper {

    @NonNull
    public static List<VDARPrior> buildTagPriors(@NonNull JSONArray tags) throws Exception {
        List<VDARPrior> priors = new ArrayList<>();
        for (int i = 0; i < tags.length(); i++) {
            JSONArray tagGroup = tags.getJSONArray(i);

            ArrayList<VDARPrior> innerPriors = new ArrayList<>();
            for (int j = 0; j < tagGroup.length(); j++) {
                innerPriors.add(new VDARTagPrior(tagGroup.getString(j)));
            }
            priors.add(new VDARIntersectionPrior(innerPriors));
        }
        return priors;
    }

    @NonNull
    public static List<VDARPrior> buildFullPriors(@NonNull JSONArray tags, @NonNull JSONArray tourIds, @NonNull JSONArray contextIds)
        throws Exception {
        List<VDARPrior> priors = buildTagPriors(tags);
        for (int i = 0; i < tourIds.length(); i++) {
            priors.add(new VDARTourPrior(tourIds.getLong(i)));
        }
        for (int i = 0; i < contextIds.length(); i++) {
            priors.add(new VDARContextPrior(contextIds.getString(i)));
        }
        return priors;
    }

    @NonNull
    public static JSObject contextToJSObject(@NonNull VDARContext context) {
        JSObject obj = new JSObject();
        obj.put("contextId", context.getRemoteID());
        obj.put("name", context.getName() != null ? context.getName() : "");
        obj.put("description", context.getDescription() != null ? context.getDescription() : JSONObject.NULL);
        obj.put("lastUpdate", context.getLastModifiedDate() != null ? context.getLastModifiedDate().toString() : "");
        obj.put("imageThumbnailURL", context.getImageThumbnailURL() != null ? context.getImageThumbnailURL() : JSONObject.NULL);
        obj.put("imageHiResURL", context.getImageHiResURL() != null ? context.getImageHiResURL() : JSONObject.NULL);
        obj.put("notificationTitle", context.getNotificationTitle() != null ? context.getNotificationTitle() : JSONObject.NULL);
        obj.put("notificationMessage", context.getNotificationMessage() != null ? context.getNotificationMessage() : JSONObject.NULL);
        obj.put("tags", new JSArray());
        return obj;
    }

    @NonNull
    public static JSObject gpsPointToJSObject(@NonNull VDARGPSPoint point) {
        JSObject obj = new JSObject();
        obj.put("contextId", point.getContextID() != null ? point.getContextID() : "");
        obj.put("category", point.getCategory() != null ? point.getCategory() : "");
        obj.put("label", point.getLabel() != null ? point.getLabel() : "");
        obj.put("latitude", point.getLat());
        obj.put("longitude", point.getLon());
        float detectionRadius = point.getDetectionRadius();
        obj.put("detectionRadius", detectionRadius > 0 ? detectionRadius : JSONObject.NULL);
        obj.put("distanceFromCurrentPosition", point.getDistanceFromCurrentPos());
        return obj;
    }

    @NonNull
    public static String codeTypeToString(@NonNull VDARCodeType codeType) {
        switch (codeType) {
            case VDAR_CODE_TYPE_NONE:
                return "none";
            case VDAR_CODE_TYPE_EAN2:
                return "ean2";
            case VDAR_CODE_TYPE_EAN5:
                return "ean5";
            case VDAR_CODE_TYPE_EAN8:
                return "ean8";
            case VDAR_CODE_TYPE_UPCE:
                return "upce";
            case VDAR_CODE_TYPE_ISBN10:
                return "isbn10";
            case VDAR_CODE_TYPE_UPCA:
                return "upca";
            case VDAR_CODE_TYPE_EAN13:
                return "ean13";
            case VDAR_CODE_TYPE_ISBN13:
                return "isbn13";
            case VDAR_CODE_TYPE_COMPOSITE:
                return "composite";
            case VDAR_CODE_TYPE_I25:
                return "i25";
            case VDAR_CODE_TYPE_CODE39:
                return "code39";
            case VDAR_CODE_TYPE_QRCODE:
                return "qrcode";
            default:
                return "unknown";
        }
    }
}
