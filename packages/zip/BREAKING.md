# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Minimum iOS Deployment Target

The minimum iOS deployment target has been updated to **16.0** to address security vulnerabilities in ZIP file handling on earlier iOS versions.

If you are using **Swift Package Manager**, update the minimum iOS version in your `Package.swift` file:

```diff
platforms: [
-    .iOS(.v15)
+    .iOS(.v16)
]
```

If you are using **CocoaPods**, update the minimum iOS version in your `ios/App/Podfile` file:

```diff
- platform :ios, '15.0'
+ platform :ios, '16.0'
```

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.
