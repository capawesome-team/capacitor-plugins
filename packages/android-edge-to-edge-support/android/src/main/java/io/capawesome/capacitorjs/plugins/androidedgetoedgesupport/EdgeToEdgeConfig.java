package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import androidx.annotation.Nullable;

public class EdgeToEdgeConfig {

    @Nullable
    private Integer backgroundColor;

    @Nullable
    private Integer navigationBarColor;

    @Nullable
    private Integer statusBarColor;

    @Nullable
    public Integer getBackgroundColor() {
        return this.backgroundColor;
    }

    @Nullable
    public Integer getNavigationBarColor() {
        return this.navigationBarColor;
    }

    @Nullable
    public Integer getStatusBarColor() {
        return this.statusBarColor;
    }

    public void setBackgroundColor(@Nullable Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setNavigationBarColor(@Nullable Integer navigationBarColor) {
        this.navigationBarColor = navigationBarColor;
    }

    public void setStatusBarColor(@Nullable Integer statusBarColor) {
        this.statusBarColor = statusBarColor;
    }
}
