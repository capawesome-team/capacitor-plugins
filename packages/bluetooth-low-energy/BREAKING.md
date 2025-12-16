# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Error code

On Web, all methods now return `UNIMPLEMENTED` instead of `UNAVAILABLE`.

### Error message for `startForegroundService`

On Android, the error message when starting a foreground service without the required permissions has changed to provide clearer guidance. The new error message explicitly mentions the need for the `FOREGROUND_SERVICE_CONNECTED_DEVICE` permission in the AndroidManifest.xml and that Bluetooth permissions must be granted. The original system error is now logged using the Capacitor Logger for debugging purposes.
