package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.getcapacitor.Logger;

public class EdgeToEdge {

    @NonNull
    private final EdgeToEdgeConfig config;

    @NonNull
    private final EdgeToEdgePlugin plugin;

    private View overlayView;

    public EdgeToEdge(@NonNull EdgeToEdgePlugin plugin, @NonNull EdgeToEdgeConfig config) {
        this.config = config;
        this.plugin = plugin;
        // Apply insets to disable the edge-to-edge feature
        setBackgroundColor(config.getBackgroundColor());
        applyInsets();
        setStatusBarBackgroundColor(config.getStatusBarBackgroundColor());
    }

    public void enable() {
        applyInsets();
    }

    public void disable() {
        removeInsets();
    }

    public ViewGroup.MarginLayoutParams getInsets() {
        View view = plugin.getBridge().getWebView();
        return (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    }

    public void setBackgroundColor(String color) {
        setBackgroundColor(Color.parseColor(color));
    }

    public void setStatusBarBackgroundColor(String color) {
        setStatusBarBackgroundColor(Color.parseColor(color));
    }

    private void applyInsets() {
        View view = plugin.getBridge().getWebView();
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();
        // Set insets
        WindowInsetsCompat currentInsets = ViewCompat.getRootWindowInsets(view);
        if (currentInsets != null) {
            Insets systemBarsInsets = currentInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()
            );
            Insets imeInsets = currentInsets.getInsets(WindowInsetsCompat.Type.ime());
            boolean keyboardVisible = currentInsets.isVisible(WindowInsetsCompat.Type.ime());

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            mlp.bottomMargin = keyboardVisible ? imeInsets.bottom : systemBarsInsets.bottom;
            mlp.topMargin = systemBarsInsets.top;
            mlp.leftMargin = systemBarsInsets.left;
            mlp.rightMargin = systemBarsInsets.right;

            view.setLayoutParams(mlp);
        }
        // Set listener
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            // Retrieve system bars insets (for status/navigation bars)
            Insets systemBarsInsets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()
            );
            // Retrieve keyboard (IME) insets
            Insets imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime());
            boolean keyboardVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime());

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            // Apply the appropriate bottom inset: use keyboard inset if visible, else system bars inset
            mlp.bottomMargin = keyboardVisible ? imeInsets.bottom : systemBarsInsets.bottom;

            // Set the other margins using system bars insets
            mlp.topMargin = systemBarsInsets.top;
            mlp.leftMargin = systemBarsInsets.left;
            mlp.rightMargin = systemBarsInsets.right;

            v.setLayoutParams(mlp);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void removeInsets() {
        View view = plugin.getBridge().getWebView();
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();
        // Reset insets
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        mlp.topMargin = 0;
        mlp.leftMargin = 0;
        mlp.rightMargin = 0;
        mlp.bottomMargin = 0;
        view.setLayoutParams(mlp);
        if (overlayView != null) {
            parent.getOverlay().remove(overlayView);
        }
        // Reset listener
        ViewCompat.setOnApplyWindowInsetsListener(view, null);
    }

    private void setBackgroundColor(int color) {
        View view = plugin.getBridge().getWebView();
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();
        // Set background color
        parent.setBackgroundColor(color);
    }

    private void setStatusBarBackgroundColor(int color) {
        View view = plugin.getBridge().getWebView();
        Activity activity = plugin.getBridge().getActivity();
        // Create new view
        View overlay = new View(activity);
        WindowInsetsCompat currentInsets = ViewCompat.getRootWindowInsets(view);
        if (currentInsets != null) {
            int statusBarHeight = currentInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()
            ).top;
            overlay.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarHeight
            ));
            overlay.setBackgroundColor(color);
            // Get root view bridge activity
            ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();

            // Remove previous overlay if exist
            if (overlayView != null) {
                rootView.removeView(overlayView);
            }
            // Set reference overlay view
            overlayView = view;
            // Add overlay view
            rootView.addView(overlay);
        }
    }
}
