# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

## Version 6.x.x

### `multiple` property

The `multiple` property has been replaced by a new `limit` property in the `PickFilesOptions` and `PickMediaOptions` interfaces.

### `File` interface

The `File` interface has been replaced by the `PickedFile` interface.

### `skipTranscoding` property

The default value of the `skipTranscoding` property has been changed to `true`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `0.6.3`:

```
npm i @capawesome/capacitor-file-picker@0.6.3
```
