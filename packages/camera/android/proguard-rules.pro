# Add any plugin specific proguard rules here.
# For more information, see:
# https://developer.android.com/studio/build/shrink-code

# CameraX
-keep class androidx.camera.core.** { *; }
-keep class androidx.camera.camera2.** { *; }
-keep class androidx.camera.lifecycle.** { *; }
-keep class androidx.camera.view.** { *; }
