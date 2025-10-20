# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor Live Update plugin.

## Versions

- [Version 7.x.x](#version-7xx)

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
