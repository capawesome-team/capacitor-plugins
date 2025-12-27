# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 5.x.x](#version-5xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Error Handling

On iOS 16 and later, the plugin no longer uses the deprecated `applicationIconBadgeNumber` API. Instead, it now utilizes the `UNUserNotificationCenter` to manage badge numbers. This change may affect how errors are handled when setting badge numbers.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `2.0.2`:

```
npm i @capawesome/capacitor-badge@2.0.2
```
