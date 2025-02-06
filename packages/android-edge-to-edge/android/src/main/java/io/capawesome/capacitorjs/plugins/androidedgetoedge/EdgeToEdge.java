package io.capawesome.capacitorjs.plugins.androidedgetoedge;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EdgeToEdge {

    @NonNull
    private final EdgeToEdgePlugin plugin;

    public EdgeToEdge(@NonNull EdgeToEdgePlugin plugin) {
        this.plugin = plugin;
        // Apply insets to disable the edge-to-edge feature
        // applyInsets();
    }

    public void setBackgroundColor(String color) {
        View view = plugin.getBridge().getWebView();
        if (view == null) {
            return;
        }
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();
        // Set background color to black
        parent.setBackgroundColor(Color.parseColor(color));
    }

    private void applyInsets() {
        View view = plugin.getBridge().getWebView();
        if (view == null) {
            return;
        }
        // Get parent view
        ViewGroup parent = (ViewGroup) view.getParent();
        // Set background color to black
        parent.setBackgroundColor(Color.WHITE);
        // Apply insets to disable the edge-to-edge feature
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.bottomMargin = insets.bottom;
            mlp.leftMargin = insets.left;
            mlp.rightMargin = insets.right;
            mlp.topMargin = insets.top;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
