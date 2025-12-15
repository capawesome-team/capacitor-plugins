# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 0.2.x](#version-7xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `androidxExifInterfaceVersion` variable has been updated to `1.4.2`.

## Version 0.2.x

### New `mimeType` and `name` properties for shared files

We have introduced a `SharedFile` interface that provides additional information about shared files. For this, you need to re-install the plugin. Make sure to follow the platform-specific instructions in the [README](./README.md) to update all relevant files. For example, on iOS, the [Share Extension](./README.md#share-extension) has to be updated. On Web, the [Service Worker](./README.md#service-worker) has to be updated.
