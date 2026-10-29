package io.capawesome.capacitorjs.plugins.haptics.classes.options;

import android.os.Build;
import android.os.VibrationEffect;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.haptics.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@RequiresApi(api = Build.VERSION_CODES.S)
public class PlayAndroidCompositionOptions {

    public static class Primitive {

        private final int delayMs;
        private final float scale;
        private final int type;

        public Primitive(int delayMs, float scale, int type) {
            this.delayMs = delayMs;
            this.scale = scale;
            this.type = type;
        }

        public int getDelayMs() {
            return delayMs;
        }

        public float getScale() {
            return scale;
        }

        public int getType() {
            return type;
        }
    }

    @NonNull
    private final List<Primitive> primitives;

    public PlayAndroidCompositionOptions(@NonNull PluginCall call) throws Exception {
        this.primitives = PlayAndroidCompositionOptions.getPrimitivesFromCall(call);
    }

    @NonNull
    public List<Primitive> getPrimitives() {
        return primitives;
    }

    @NonNull
    private static List<Primitive> getPrimitivesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray primitivesArray = call.getArray("primitives");
        if (primitivesArray == null || primitivesArray.length() == 0) {
            throw CustomExceptions.PRIMITIVES_MISSING;
        }
        List<Primitive> primitives = new ArrayList<>();
        for (int index = 0; index < primitivesArray.length(); index++) {
            JSONObject primitiveObject = primitivesArray.getJSONObject(index);
            if (!primitiveObject.has("type")) {
                throw CustomExceptions.TYPE_MISSING;
            }
            int type = PlayAndroidCompositionOptions.getTypeForName(primitiveObject.getString("type"));
            double scale = primitiveObject.optDouble("scale", 1);
            if (scale < 0 || scale > 1) {
                throw CustomExceptions.SCALE_INVALID;
            }
            int delayMs = primitiveObject.optInt("delay", 0);
            if (delayMs < 0) {
                throw CustomExceptions.DELAY_INVALID;
            }
            primitives.add(new Primitive(delayMs, (float) scale, type));
        }
        return primitives;
    }

    private static int getTypeForName(@NonNull String name) throws Exception {
        switch (name) {
            case "CLICK":
                return VibrationEffect.Composition.PRIMITIVE_CLICK;
            case "LOW_TICK":
                return VibrationEffect.Composition.PRIMITIVE_LOW_TICK;
            case "QUICK_FALL":
                return VibrationEffect.Composition.PRIMITIVE_QUICK_FALL;
            case "QUICK_RISE":
                return VibrationEffect.Composition.PRIMITIVE_QUICK_RISE;
            case "SLOW_RISE":
                return VibrationEffect.Composition.PRIMITIVE_SLOW_RISE;
            case "SPIN":
                return VibrationEffect.Composition.PRIMITIVE_SPIN;
            case "THUD":
                return VibrationEffect.Composition.PRIMITIVE_THUD;
            case "TICK":
                return VibrationEffect.Composition.PRIMITIVE_TICK;
            default:
                throw CustomExceptions.TYPE_INVALID;
        }
    }
}
