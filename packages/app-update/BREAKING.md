# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor App Update plugin.

## Versions

- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)

## Version 7.x.x

### `OpenAppStoreOptions` interface

The `country` property has been replaced by the `appId` property in the `OpenAppStoreOptions` interface.

**Attention**: The `appId` is required on **iOS**.

## Version 6.x.x

### `currentVersion` property

The `currentVersion` property has been replaced by the `currentVersionCode` and `currentVersionName` properties in the `AppUpdateInfo` interface.

### `availableVersion` property

The `availableVersion` property has been replaced by the `availableVersionCode` and `availableVersionName` properties in the `AppUpdateInfo` interface.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `2.1.1`:

```
npm i @capawesome/capacitor-app-update@2.1.1
```

### Android Variables

The project variable `androidPlayCore` (defined in your app’s `variables.gradle` file) was renamed to `androidPlayCoreVersion`.
