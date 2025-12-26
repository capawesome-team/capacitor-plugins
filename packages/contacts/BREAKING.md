# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### `GetContactsOptions` interface

The `limit` property is now by default set to `20`.

### `UpdateContactByIdOptions` interface

The `updateContactById` method now supports **partial updates**. Missing properties are preserved, while properties set to `null` or `[]` are deleted.

**Migration:** If you relied on missing properties being deleted, explicitly set them to `null` instead.
