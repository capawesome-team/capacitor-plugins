# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Error code

On Android, the `pauseRecording` and `resumeRecording` methods now return `UNAVAILABLE` instead of `UNIMPLEMENTED` when called on devices with API level below 24.
