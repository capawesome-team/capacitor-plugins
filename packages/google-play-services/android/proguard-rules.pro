# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
