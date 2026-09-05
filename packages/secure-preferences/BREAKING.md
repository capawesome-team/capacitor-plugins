# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.4.x](#version-04x)
- [Version 0.3.x](#version-03x)
- [Version 0.2.x](#version-02x)

## Version 0.4.x

### Error Codes

The `get(...)` method now rejects instead of resolving with `value: null` when a stored value cannot be read. On Android, it rejects with the error code `DECRYPTION_FAILED` if the value cannot be decrypted and with `KEY_INVALIDATED` if the encryption key can no longer be used. On iOS, it rejects if the Keychain read fails, for example because the device is locked. A `null` value now only means that the key does not exist. Handle these rejections in your code.

### Encryption Key Reset

On Android, the `clear()` method now also deletes the encryption key from the Android Keystore, so the next call to `set(...)` generates a new key. This is the documented recovery from a `KEY_INVALIDATED` error.

### AES-128 Fallback

On Android, the silent fallback from a 256-bit to a 128-bit encryption key has been removed. If a 256-bit key cannot be generated, the call now fails with an error instead of using weaker encryption. Values that were stored with a 128-bit key remain readable. Installations that already use a 128-bit key keep using it, also for new values; call `clear()` to switch the store to a 256-bit key.

## Version 0.3.x

### iOS Error Messages

Error messages for iOS keychain operations now include detailed failure reasons and error codes (e.g., `"Item not found. (code: -25300)"` instead of `"remove failed."`). Update any code that parses these error messages.

## Version 0.2.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.
