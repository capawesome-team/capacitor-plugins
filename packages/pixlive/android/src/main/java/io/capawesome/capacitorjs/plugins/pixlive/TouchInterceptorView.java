package io.capawesome.capacitorjs.plugins.pixlive;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.vidinoti.android.vdarsdk.VDARAnnotationView;

public class TouchInterceptorView extends FrameLayout {

    private boolean touchEnabled = false;

    private float holeTop = 0;
    private float holeBottom = 0;
    private float holeLeft = 0;
    private float holeRight = 0;

    @Nullable
    private VDARAnnotationView annotationView;

    public TouchInterceptorView(@NonNull Context context) {
        super(context);
    }

    public void setTouchEnabled(boolean enabled) {
        this.touchEnabled = enabled;
    }

    public void setTouchHole(float top, float bottom, float left, float right) {
        this.holeTop = top;
        this.holeBottom = bottom;
        this.holeLeft = left;
        this.holeRight = right;
    }

    public void setAnnotationView(@Nullable VDARAnnotationView view) {
        this.annotationView = view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (
            !touchEnabled || annotationView == null || annotationView.getVisibility() != View.VISIBLE || annotationView.getParent() == null
        ) {
            return super.onInterceptTouchEvent(event);
        }

        float x = event.getX();
        float y = event.getY();

        if (x >= holeLeft && x < holeRight && y >= holeTop && y < holeBottom) {
            return super.onInterceptTouchEvent(event);
        }

        float arViewX = annotationView.getLeft();
        float arViewY = annotationView.getTop();

        for (int i = 0; i < event.getPointerCount(); i++) {
            float xPos = event.getX(i);
            float yPos = event.getY(i);

            if (
                xPos >= arViewX &&
                xPos < arViewX + annotationView.getWidth() &&
                yPos >= arViewY &&
                yPos < arViewY + annotationView.getHeight()
            ) {
                return true;
            }
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (annotationView == null) {
            return false;
        }
        float arViewX = annotationView.getLeft();
        float arViewY = annotationView.getTop();

        event.offsetLocation(-arViewX, -arViewY);

        if (annotationView.dispatchTouchEvent(event)) {
            return true;
        }

        event.offsetLocation(arViewX, arViewY);
        return false;
    }
}
