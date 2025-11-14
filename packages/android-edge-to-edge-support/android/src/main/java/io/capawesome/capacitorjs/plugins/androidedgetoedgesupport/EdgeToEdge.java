package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

    private int statusBarColor = Color.WHITE;
    private int navigationBarColor = Color.WHITE;

    public EdgeToEdge(@NonNull EdgeToEdgePlugin plugin, @NonNull EdgeToEdgeConfig config) {
        this.config = config;
        this.plugin = plugin;
        // Initialize colors from config
        this.statusBarColor = config.getStatusBarColor();
        this.navigationBarColor = config.getNavigationBarColor();
        // Apply insets and colors
        updateBackgroundColors();
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
        int parsedColor = Color.parseColor(color);
        setStatusBarColor(parsedColor);
        setNavigationBarColor(parsedColor);
    }

    public void setStatusBarColor(String color) {
        setStatusBarColor(Color.parseColor(color));
    }

    public void setNavigationBarColor(String color) {
        setNavigationBarColor(Color.parseColor(color));
    }

    private void setBackgroundColor(int color) {
        setStatusBarColor(color);
        setNavigationBarColor(color);
    }

    private void setStatusBarColor(int color) {
        this.statusBarColor = color;
        updateBackgroundColors();
    }

    private void setNavigationBarColor(int color) {
        this.navigationBarColor = color;
        updateBackgroundColors();
    }

    private void updateBackgroundColors() {
        View view = plugin.getBridge().getWebView();
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();

        // Get the root window insets to determine status bar and navigation bar sizes
        WindowInsetsCompat currentInsets = ViewCompat.getRootWindowInsets(view);
        if (currentInsets != null) {
            Insets systemBarsInsets = currentInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()
            );

            int statusBarHeight = systemBarsInsets.top;
            int navigationBarHeight = systemBarsInsets.bottom;

            // Create a LayerDrawable with separate colors for status bar and navigation bar areas
            ColorDrawable statusBarDrawable = new ColorDrawable(statusBarColor);
            ColorDrawable navigationBarDrawable = new ColorDrawable(navigationBarColor);
            ColorDrawable middleDrawable = new ColorDrawable(Color.TRANSPARENT);

            // Create layer drawable
            Drawable[] layers = new Drawable[3];
            layers[0] = statusBarDrawable;
            layers[1] = middleDrawable;
            layers[2] = navigationBarDrawable;

            LayerDrawable layerDrawable = new LayerDrawable(layers);

            // Set layer bounds
            int parentHeight = parent.getHeight();
            if (parentHeight > 0) {
                // Status bar at top
                layerDrawable.setLayerInset(0, 0, 0, 0, parentHeight - statusBarHeight);
                // Middle transparent area
                layerDrawable.setLayerInset(1, 0, statusBarHeight, 0, navigationBarHeight);
                // Navigation bar at bottom
                layerDrawable.setLayerInset(2, 0, parentHeight - navigationBarHeight, 0, 0);
            }

            parent.setBackground(layerDrawable);
        } else {
            // Fallback: if we can't determine insets, just set a uniform background
            parent.setBackgroundColor(statusBarColor);
        }
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

            // Update background colors when insets change
            updateBackgroundColors();

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
}
