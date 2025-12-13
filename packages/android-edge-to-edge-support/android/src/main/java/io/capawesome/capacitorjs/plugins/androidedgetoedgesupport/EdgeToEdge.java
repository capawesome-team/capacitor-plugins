package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
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

    public EdgeToEdge(@NonNull EdgeToEdgePlugin plugin, @NonNull EdgeToEdgeConfig config) {
        this.config = config;
        this.plugin = plugin;
        // Apply insets to disable the edge-to-edge feature
        setBackgroundColor(config.getBackgroundColor());
        applyInsets();
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

    private void applyInsets() {
        View view = plugin.getBridge().getWebView();
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();
        // Set insets
        WindowInsetsCompat currentInsets = ViewCompat.getRootWindowInsets(view);
        if (currentInsets != null) {
            Insets systemBarsInsets = currentInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets displayCutoutInsets = currentInsets.getInsets(WindowInsetsCompat.Type.displayCutout());
            Insets statusBarsInsets = currentInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            Insets imeInsets = currentInsets.getInsets(WindowInsetsCompat.Type.ime());
            boolean keyboardVisible = currentInsets.isVisible(WindowInsetsCompat.Type.ime());
            boolean statusBarsVisible = currentInsets.isVisible(WindowInsetsCompat.Type.statusBars());

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            // Apply the appropriate bottom inset: use keyboard inset if visible, else system bars inset
            mlp.bottomMargin = keyboardVisible ? imeInsets.bottom : systemBarsInsets.bottom;

            // For the top inset, apply insets only while the status bar is visible.
            // If the status bar is hidden, we allow the content to extend fully to the top.
            int topInset = 0;
            if (statusBarsVisible) {
                topInset = Math.max(displayCutoutInsets.top, statusBarsInsets.top);
            }
            mlp.topMargin = topInset;

            mlp.leftMargin = systemBarsInsets.left;
            mlp.rightMargin = systemBarsInsets.right;

            view.setLayoutParams(mlp);
        }
        // Set listener
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            // Retrieve system bars insets (for navigation bars etc.)
            Insets systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets displayCutoutInsets = windowInsets.getInsets(WindowInsetsCompat.Type.displayCutout());
            Insets statusBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            // Retrieve keyboard (IME) insets
            Insets imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime());
            boolean keyboardVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime());
            boolean statusBarsVisible = windowInsets.isVisible(WindowInsetsCompat.Type.statusBars());

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            // Apply the appropriate bottom inset: use keyboard inset if visible, else system bars inset
            mlp.bottomMargin = keyboardVisible ? imeInsets.bottom : systemBarsInsets.bottom;

            // For the top inset, apply insets only while the status bar is visible.
            // If the status bar is hidden, we allow the content to extend fully to the top.
            int topInset = 0;
            if (statusBarsVisible) {
                topInset = Math.max(displayCutoutInsets.top, statusBarsInsets.top);
            }
            mlp.topMargin = topInset;

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
}
