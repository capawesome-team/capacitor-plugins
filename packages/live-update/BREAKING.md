# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### iOS Deployment Target

The minimum iOS deployment target has been updated to **16.0** to address several security vulnerabilities related to ZIP file handling in earlier iOS versions.

If you are using **Swift Package Manager**, update your iOS deployment target in your Xcode project settings (usually in `ios/App/App.xcodeproj`):

```diff
-IPHONEOS_DEPLOYMENT_TARGET = 15.0
+IPHONEOS_DEPLOYMENT_TARGET = 16.0
```

If you are using **CocoaPods**, update your `Podfile`:

```diff
-platform :ios, '15.0'
+platform :ios, '16.0'
```

### Variables

- On Android, the `okhttp3Version` variable has been updated to `5.3.2`.

## Version 7.x.x

### `getBundle()` method

The `getBundle()` method has been replaced by the `getCurrentBundle()` method.

### `setBundle()` method

The `setBundle()` method has been replaced by the `setNextBundle()` method.

### `DownloadBundleOptions` interface

The `checksum` property has been removed from the `DownloadBundleOptions` interface. The server should now return a `X-Checksum` header instead.

### `enabled` configuration option

The `enabled` configuration option has been removed. The plugin is now always enabled.

### `location` configuration option

The `location` configuration option has been replaced by the `serverDomain` configuration option.

### `readyTimeout` configuration option

The default value of the `readyTimeout` configuration option has been changed from `10000` to `0` to disable the timeout by default.
However, it is strongly **recommended** to configure this option so that the plugin can roll back to the default bundle in case of problems.

### `resetOnUpdate` configuration option

The `resetOnUpdate` configuration option has been removed. Capacitor always resets the app to the default bundle during a native update.
