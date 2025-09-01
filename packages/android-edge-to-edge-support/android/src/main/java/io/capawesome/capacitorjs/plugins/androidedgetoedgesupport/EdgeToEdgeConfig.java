package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;

public class EdgeToEdgeConfig {

    private int backgroundColor = Color.WHITE;
    private int statusBarBackgroundColor = Color.WHITE;

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getStatusBarBackgroundColor() {
        return this.statusBarBackgroundColor;
    }

    public void setStatusBarBackgroundColor(int statusBarBackgroundColor) {
        this.statusBarBackgroundColor = backgroundColor;
    }
}
