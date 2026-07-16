package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

    @Nullable
    private Integer currentNavigationBarColor;

    @Nullable
    private Integer currentStatusBarColor;

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
        if (isEdgeToEdgeEnforced()) {
            // Create color overlays if they don't exist
            createColorOverlays();
            // Restore previously set colors
            applyStatusBarColor();
            applyNavigationBarColor();
            // Apply insets
            applyInsets();
        } else {
            // Pre-Android 15 (or Android 15 with opt-out): the platform still honors
            // Window.setStatusBarColor/setNavigationBarColor, so we use that directly
            // instead of overlay views that depend on the inset dispatch chain.
            applyStatusBarColor();
            applyNavigationBarColor();
        }
    }

    public void disable() {
        if (isEdgeToEdgeEnforced()) {
            removeInsets();
        }
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
        // Set insets
        WindowInsetsCompat currentInsets = ViewCompat.getRootWindowInsets(view);
        if (currentInsets != null) {
            applyInsetsInternal(view, currentInsets);
        }
        // Set listener
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            applyInsetsInternal(v, windowInsets);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void applyInsetsInternal(View view, WindowInsetsCompat currentInsets) {
        Insets systemBarsInsets = currentInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
        Insets imeInsets = currentInsets.getInsets(WindowInsetsCompat.Type.ime());
        boolean keyboardVisible = currentInsets.isVisible(WindowInsetsCompat.Type.ime());
        // When keyboard is visible, don't apply bottom margin to avoid double-counting
        // (the system already resizes the window for the keyboard)
        int bottomMargin = keyboardVisible ? 0 : Math.max(imeInsets.bottom, systemBarsInsets.bottom);

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        mlp.bottomMargin = bottomMargin;
        mlp.topMargin = systemBarsInsets.top;
        mlp.leftMargin = systemBarsInsets.left;
        mlp.rightMargin = systemBarsInsets.right;
        view.setLayoutParams(mlp);

        // Update color overlays based on current insets
        updateColorOverlays(systemBarsInsets);
    }

    private void applyNavigationBarColor() {
        if (currentNavigationBarColor == null) {
            return;
        }
        if (isEdgeToEdgeEnforced()) {
            if (navigationBarOverlay != null) {
                navigationBarOverlay.setBackgroundColor(currentNavigationBarColor);
            }
        } else {
            Window window = plugin.getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            setNavigationBarColorDeprecated(window, currentNavigationBarColor);
        }
    }

    private void applyStatusBarColor() {
        if (currentStatusBarColor == null) {
            return;
        }
        if (isEdgeToEdgeEnforced()) {
            if (statusBarOverlay != null) {
                statusBarOverlay.setBackgroundColor(currentStatusBarColor);
            }
        } else {
            Window window = plugin.getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            setStatusBarColorDeprecated(window, currentStatusBarColor);
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

    private boolean isEdgeToEdgeEnforced() {
        int deviceApi = Build.VERSION.SDK_INT;
        if (deviceApi < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            // Pre-Android 15: platform honors legacy Window.setStatusBarColor/setNavigationBarColor
            return false;
        }
        if (deviceApi == Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            // Android 15: edge-to-edge enforced unless the app opted out via
            // android:windowOptOutEdgeToEdgeEnforcement on its theme
            return !isEdgeToEdgeOptOutEnabled();
        }
        // Android 16+: opt-out is ignored, edge-to-edge is always enforced
        return true;
    }

    private boolean isEdgeToEdgeOptOutEnabled() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            return false;
        }
        TypedValue value = new TypedValue();
        plugin.getActivity().getTheme().resolveAttribute(android.R.attr.windowOptOutEdgeToEdgeEnforcement, value, true);
        return value.data != 0;
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

    private void removeInsets() {
        View view = plugin.getBridge().getWebView();
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

    private void setNavigationBarColor(int color) {
        this.currentNavigationBarColor = color;
        applyNavigationBarColor();
    }

    @SuppressWarnings("deprecation")
    private void setNavigationBarColorDeprecated(@NonNull Window window, int color) {
        window.setNavigationBarColor(color);
    }

    private void setStatusBarColor(int color) {
        this.currentStatusBarColor = color;
        applyStatusBarColor();
    }

    @SuppressWarnings("deprecation")
    private void setStatusBarColorDeprecated(@NonNull Window window, int color) {
        window.setStatusBarColor(color);
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
}
