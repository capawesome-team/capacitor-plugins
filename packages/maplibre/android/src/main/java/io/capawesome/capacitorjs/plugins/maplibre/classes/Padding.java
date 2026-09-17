package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

/**
 * The padding between the content of a map and its edges in CSS pixels.
 */
public class Padding {

    private final double bottom;
    private final double left;
    private final double right;
    private final double top;

    public Padding(@Nullable JSObject object) {
        this.bottom = object == null ? 0 : object.optDouble("bottom", 0);
        this.left = object == null ? 0 : object.optDouble("left", 0);
        this.right = object == null ? 0 : object.optDouble("right", 0);
        this.top = object == null ? 0 : object.optDouble("top", 0);
    }

    public double getBottom() {
        return bottom;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }
}
