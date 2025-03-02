package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;

import com.getcapacitor.PluginConfig;

public class EdgeToEdgeConfig {
  private int backgroundColor = Color.WHITE;

  public EdgeToEdgeConfig(PluginConfig config) {
    String backgroundColor = config.getString("backgroundColor");
    if (backgroundColor != null) {
      this.backgroundColor = Color.parseColor(backgroundColor);
    }
  }

  public int getBackgroundColor() {
    return this.backgroundColor;
  }
}
