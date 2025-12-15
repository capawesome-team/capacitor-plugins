# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Default Language

On iOS, the default value for the `language` option has changed from `Locale.current` to `Locale.preferredLanguages`. This change improves language detection based on user preferences. If your application relies on the previous behavior, you may need to explicitly set the `language` option when invoking speech recognition features.
