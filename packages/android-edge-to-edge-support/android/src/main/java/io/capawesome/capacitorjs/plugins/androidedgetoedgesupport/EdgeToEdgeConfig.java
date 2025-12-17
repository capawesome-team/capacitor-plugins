package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;

public class EdgeToEdgeConfig {

    private int backgroundColor = Color.TRANSPARENT;
    private int navigationBarColor = Color.TRANSPARENT;
    private int statusBarColor = Color.TRANSPARENT;

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getNavigationBarColor() {
        return this.navigationBarColor;
    }

    public int getStatusBarColor() {
        return this.statusBarColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setNavigationBarColor(int navigationBarColor) {
        this.navigationBarColor = navigationBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }
}
