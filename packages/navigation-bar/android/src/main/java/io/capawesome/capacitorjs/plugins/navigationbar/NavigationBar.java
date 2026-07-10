package io.capawesome.capacitorjs.plugins.navigationbar;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.getcapacitor.util.WebColor;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.options.SetColorOptions;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.options.SetStyleOptions;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.results.GetColorResult;
import io.capawesome.capacitorjs.plugins.navigationbar.classes.results.GetStyleResult;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.NonEmptyCallback;

public class NavigationBar {

    private static final String STYLE_DARK = "DARK";
    private static final String STYLE_DEFAULT = "DEFAULT";
    private static final String STYLE_LIGHT = "LIGHT";
    private static final String TRANSPARENT = "transparent";

    @NonNull
    private final NavigationBarPlugin plugin;

    public NavigationBar(@NonNull NavigationBarPlugin plugin) {
        this.plugin = plugin;
    }

    public void applyConfig(@NonNull NavigationBarConfig config) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                String color = config.getColor();
                String dividerColor = config.getDividerColor();
                if (color != null) {
                    applyColor(color, dividerColor);
                }
                String style = config.getStyle();
                if (style != null) {
                    applyStyle(style);
                }
            });
    }

    public void getColor(@NonNull NonEmptyCallback<GetColorResult> callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                Window window = plugin.getActivity().getWindow();
                int color = window.getNavigationBarColor();
                String hex = formatColor(color);
                callback.success(new GetColorResult(hex));
            });
    }

    public void getStyle(@NonNull NonEmptyCallback<GetStyleResult> callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                Window window = plugin.getActivity().getWindow();
                WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
                boolean isLight = controller.isAppearanceLightNavigationBars();
                callback.success(new GetStyleResult(isLight ? STYLE_LIGHT : STYLE_DARK));
            });
    }

    public void hide(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                Window window = plugin.getActivity().getWindow();
                WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
                controller.hide(WindowInsetsCompat.Type.navigationBars());
                callback.success();
            });
    }

    public void setColor(@NonNull SetColorOptions options, @NonNull EmptyCallback callback) {
        String color = options.getColor();
        String dividerColor = options.getDividerColor();
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                try {
                    applyColor(color, dividerColor);
                    callback.success();
                } catch (Exception exception) {
                    callback.error(exception);
                }
            });
    }

    public void setStyle(@NonNull SetStyleOptions options, @NonNull EmptyCallback callback) {
        String style = options.getStyle();
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                try {
                    applyStyle(style);
                    callback.success();
                } catch (Exception exception) {
                    callback.error(exception);
                }
            });
    }

    public void show(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                Window window = plugin.getActivity().getWindow();
                WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
                controller.show(WindowInsetsCompat.Type.navigationBars());
                callback.success();
            });
    }

    private void applyColor(@NonNull String color, @androidx.annotation.Nullable String dividerColor) {
        Window window = plugin.getActivity().getWindow();
        window.setNavigationBarColor(parseColor(color));
        if (dividerColor != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setNavigationBarDividerColor(parseColor(dividerColor));
        }
    }

    private void applyStyle(@NonNull String style) {
        String resolved = resolveStyle(style);
        Window window = plugin.getActivity().getWindow();
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightNavigationBars(STYLE_LIGHT.equals(resolved));
    }

    private String formatColor(int color) {
        if (Color.alpha(color) == 0) {
            return TRANSPARENT;
        }
        return String.format("#%06X", 0xFFFFFF & color);
    }

    private boolean isNightMode() {
        int nightModeFlags = plugin.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    private int parseColor(@NonNull String color) {
        if (TRANSPARENT.equalsIgnoreCase(color)) {
            return Color.TRANSPARENT;
        }
        return WebColor.parseColor(color);
    }

    private String resolveStyle(@NonNull String style) {
        if (STYLE_DEFAULT.equals(style)) {
            return isNightMode() ? STYLE_DARK : STYLE_LIGHT;
        }
        return style;
    }
}
