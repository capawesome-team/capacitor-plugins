# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `libsqlVersion` variable has been updated to `0.1.2`.

### Error message

On Android, the `sync` method now returns the error message `Not implemented on this platform.` instead of `Not available on this platform.`.
