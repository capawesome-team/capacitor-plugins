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

### `location` configuration option

The `location` configuration option has been removed. 
