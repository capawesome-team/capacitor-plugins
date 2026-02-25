package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class SetARViewTouchHoleOptions {

    private final int top;
    private final int bottom;
    private final int left;
    private final int right;

    public SetARViewTouchHoleOptions(@NonNull PluginCall call) throws Exception {
        this.top = SetARViewTouchHoleOptions.getTopFromCall(call);
        this.bottom = SetARViewTouchHoleOptions.getBottomFromCall(call);
        this.left = SetARViewTouchHoleOptions.getLeftFromCall(call);
        this.right = SetARViewTouchHoleOptions.getRightFromCall(call);
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    private static int getTopFromCall(@NonNull PluginCall call) throws Exception {
        Integer top = call.getInt("top");
        if (top == null) {
            throw CustomExceptions.TOP_MISSING;
        }
        return top;
    }

    private static int getBottomFromCall(@NonNull PluginCall call) throws Exception {
        Integer bottom = call.getInt("bottom");
        if (bottom == null) {
            throw CustomExceptions.BOTTOM_MISSING;
        }
        return bottom;
    }

    private static int getLeftFromCall(@NonNull PluginCall call) throws Exception {
        Integer left = call.getInt("left");
        if (left == null) {
            throw CustomExceptions.LEFT_MISSING;
        }
        return left;
    }

    private static int getRightFromCall(@NonNull PluginCall call) throws Exception {
        Integer right = call.getInt("right");
        if (right == null) {
            throw CustomExceptions.RIGHT_MISSING;
        }
        return right;
    }
}
