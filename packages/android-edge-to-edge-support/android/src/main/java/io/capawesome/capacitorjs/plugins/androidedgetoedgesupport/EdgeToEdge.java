package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.getcapacitor.Logger;
import java.lang.reflect.Constructor;

public class EdgeToEdge {

    @NonNull
    private final EdgeToEdgeConfig config;

    @NonNull
    private final EdgeToEdgePlugin plugin;

    @Nullable
    private View navigationBarOverlay;

    @Nullable
    private View statusBarOverlay;

    private int currentNavigationBarColor;

    private int currentStatusBarColor;

    public EdgeToEdge(@NonNull EdgeToEdgePlugin plugin, @NonNull EdgeToEdgeConfig config) {
        this.config = config;
        this.plugin = plugin;
        // Store initial colors from config
        this.currentStatusBarColor = config.getStatusBarColor();
        this.currentNavigationBarColor = config.getNavigationBarColor();
        // Enable edge-to-edge
        enable();
    }

    public void enable() {
        // Create color overlays if they don't exist
        createColorOverlays();
        // Restore previously set colors
        setStatusBarColor(currentStatusBarColor);
        setNavigationBarColor(currentNavigationBarColor);
        // Apply insets
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

    public void setNavigationBarColor(String color) {
        setNavigationBarColor(Color.parseColor(color));
    }

    public void setStatusBarColor(String color) {
        setStatusBarColor(Color.parseColor(color));
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
            // Only use IME insets if keyboard is visible AND larger than system bars (handles external keyboard case)
            boolean useImeInsets = keyboardVisible && imeInsets.bottom > systemBarsInsets.bottom;

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            mlp.bottomMargin = useImeInsets ? imeInsets.bottom : systemBarsInsets.bottom;
            mlp.topMargin = systemBarsInsets.top;
            mlp.leftMargin = systemBarsInsets.left;
            mlp.rightMargin = systemBarsInsets.right;

            view.setLayoutParams(mlp);

            // Update color overlays based on current insets
            updateColorOverlays(systemBarsInsets);
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
            // Only use IME insets if keyboard is visible AND larger than system bars (handles external keyboard case)
            boolean useImeInsets = keyboardVisible && imeInsets.bottom > systemBarsInsets.bottom;

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

            // Apply the appropriate bottom inset: use keyboard inset if visible, else system bars inset
            mlp.bottomMargin = useImeInsets ? imeInsets.bottom : systemBarsInsets.bottom;

            // Set the other margins using system bars insets
            mlp.topMargin = systemBarsInsets.top;
            mlp.leftMargin = systemBarsInsets.left;
            mlp.rightMargin = systemBarsInsets.right;

            v.setLayoutParams(mlp);

            // Update color overlays based on current insets
            updateColorOverlays(systemBarsInsets);

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
        // Set a no-op listener that consumes insets without applying them.
        // Using null would allow Android 15's default edge-to-edge handling to take over,
        // which can cause extra padding when re-enabling.
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> WindowInsetsCompat.CONSUMED);
        // Remove color overlays
        removeColorOverlays();
    }

    private void createColorOverlays() {
        View webView = plugin.getBridge().getWebView();
        ViewGroup parent = (ViewGroup) webView.getParent();

        if (statusBarOverlay == null) {
            statusBarOverlay = new View(parent.getContext());
            parent.addView(statusBarOverlay, 0); // Add behind webview
        }

        if (navigationBarOverlay == null) {
            navigationBarOverlay = new View(parent.getContext());
            parent.addView(navigationBarOverlay, 0); // Add behind webview
        }
    }

    private void removeColorOverlays() {
        View webView = plugin.getBridge().getWebView();
        ViewGroup parent = (ViewGroup) webView.getParent();

        if (statusBarOverlay != null) {
            parent.removeView(statusBarOverlay);
            statusBarOverlay = null;
        }

        if (navigationBarOverlay != null) {
            parent.removeView(navigationBarOverlay);
            navigationBarOverlay = null;
        }
    }

    private void setNavigationBarColor(int color) {
        this.currentNavigationBarColor = color;
        if (navigationBarOverlay != null) {
            navigationBarOverlay.setBackgroundColor(color);
        }
    }

    private void setStatusBarColor(int color) {
        this.currentStatusBarColor = color;
        if (statusBarOverlay != null) {
            statusBarOverlay.setBackgroundColor(color);
        }
    }

    private void updateColorOverlays(Insets systemBarsInsets) {
        View webView = plugin.getBridge().getWebView();
        ViewGroup parent = (ViewGroup) webView.getParent();

        if (statusBarOverlay != null) {
            // Position status bar overlay at top
            ViewGroup.LayoutParams statusParams = createLayoutParams(
                parent,
                ViewGroup.LayoutParams.MATCH_PARENT,
                systemBarsInsets.top,
                Gravity.TOP
            );
            statusBarOverlay.setLayoutParams(statusParams);
        }

        if (navigationBarOverlay != null) {
            // Position navigation bar overlay at bottom
            ViewGroup.LayoutParams navParams = createLayoutParams(
                parent,
                ViewGroup.LayoutParams.MATCH_PARENT,
                systemBarsInsets.bottom,
                Gravity.BOTTOM
            );
            navigationBarOverlay.setLayoutParams(navParams);
        }
    }

    private ViewGroup.LayoutParams createLayoutParams(ViewGroup parent, int width, int height, int gravity) {
        String parentClassName = parent.getClass().getName();

        // Handle CoordinatorLayout using reflection
        if (parentClassName.contains("CoordinatorLayout")) {
            try {
                Class<?> layoutParamsClass = Class.forName("androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams");
                Constructor<?> constructor = layoutParamsClass.getConstructor(int.class, int.class);
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) constructor.newInstance(width, height);
                // Set gravity using reflection
                layoutParamsClass.getField("gravity").setInt(params, gravity);
                return params;
            } catch (Exception e) {
                Logger.error("EdgeToEdge", "Failed to create CoordinatorLayout.LayoutParams", e);
            }
        }

        // Handle FrameLayout
        if (parent instanceof FrameLayout) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.gravity = gravity;
            return params;
        }

        // Fallback to MarginLayoutParams
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(width, height);
        return params;
    }
}
