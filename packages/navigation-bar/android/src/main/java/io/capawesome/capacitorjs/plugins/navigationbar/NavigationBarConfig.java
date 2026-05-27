package io.capawesome.capacitorjs.plugins.navigationbar;

import androidx.annotation.Nullable;

public class NavigationBarConfig {

    @Nullable
    private String color;

    @Nullable
    private String dividerColor;

    @Nullable
    private String style;

    @Nullable
    public String getColor() {
        return color;
    }

    @Nullable
    public String getDividerColor() {
        return dividerColor;
    }

    @Nullable
    public String getStyle() {
        return style;
    }

    public void setColor(@Nullable String color) {
        this.color = color;
    }

    public void setDividerColor(@Nullable String dividerColor) {
        this.dividerColor = dividerColor;
    }

    public void setStyle(@Nullable String style) {
        this.style = style;
    }
}
