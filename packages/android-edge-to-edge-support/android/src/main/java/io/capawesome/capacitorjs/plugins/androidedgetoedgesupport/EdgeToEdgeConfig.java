package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;

public class EdgeToEdgeConfig {

    private int backgroundColor = Color.WHITE;
    private int statusBarColor = Color.WHITE;
    private int navigationBarColor = Color.WHITE;

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getStatusBarColor() {
        return this.statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public int getNavigationBarColor() {
        return this.navigationBarColor;
    }

    public void setNavigationBarColor(int navigationBarColor) {
        this.navigationBarColor = navigationBarColor;
    }
}
