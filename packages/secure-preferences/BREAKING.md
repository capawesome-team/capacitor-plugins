# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.3.x](#version-03x)
- [Version 0.2.x](#version-02x)

## Version 0.3.x

### iOS Error Messages

Error messages for iOS keychain operations now include detailed failure reasons and error codes (e.g., `"Item not found. (code: -25300)"` instead of `"remove failed."`). Update any code that parses these error messages.

## Version 0.2.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.
