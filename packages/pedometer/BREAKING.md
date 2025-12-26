# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Automatic Permission Requests

The plugin now automatically requests permissions when calling plugin methods instead of throwing an error. If you need to check permission status beforehand, use the `checkPermissions()` method before invoking the methods.
