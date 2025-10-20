# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-7xx)

## Version 0.2.x

### New `mimeType` and `name` properties for shared files

We have introduced a `SharedFile` interface that provides additional information about shared files. For this, you need to re-install the plugin. Make sure to follow the platform-specific instructions in the [README](./README.md) to update all relevant files. For example, on iOS, the [Share Extension](./README.md#share-extension) has to be updated. On Web, the [Service Worker](./README.md#service-worker) has to be updated.
