# Capacitor Plugin ProGuard Rules
# These rules ensure the plugin works correctly when the consumer app uses minification

# Keep all plugin classes
-keep class io.capawesome.capacitorjs.plugins.androidedgetoedgesupport.** { *; }

# Keep Capacitor annotations (needed for reflection-based plugin loading)
-keep @com.getcapacitor.annotation.CapacitorPlugin class * { *; }
-keep @com.getcapacitor.PluginMethod class * { *; }
-keepclassmembers class * {
    @com.getcapacitor.PluginMethod *;
}

# Keep CoordinatorLayout.LayoutParams (used via reflection in EdgeToEdge.java)
-keep class androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams { *; }

# Keep AndroidX classes used by the plugin
-keep class androidx.core.view.ViewCompat { *; }
-keep class androidx.core.view.WindowInsetsCompat { *; }
-keep class androidx.core.view.WindowInsetsCompat$Type { *; }
-keep class androidx.core.graphics.Insets { *; }
