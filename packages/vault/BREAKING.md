# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `initialize(...)` and `unlock(...)` reject with `KEY_INVALIDATED`

If the encryption key is gone while encrypted values are still stored, both methods now reject with the `KEY_INVALIDATED` error code instead of creating a new key over the existing data. A new key cannot decrypt those values, so the app must call `destroy()` and initialize the vault again.

### Error message of `KEY_INVALIDATED`

The message of the `KEY_INVALIDATED` error changed from `The encryption key was invalidated because the device's biometric set changed.` to `The encryption key is gone or can no longer be used.`, because a changed biometric set is only one of the reasons the key can become unusable. Match on the error code instead of the error message.

### `skippedCount` in `ExportDataResult`

`ExportDataResult` has a new required `skippedCount` property that reports how many stored values could not be decrypted and were therefore left out of the exported map. Custom implementations or mocks of the plugin interface must provide it.

### `clear(...)`, `destroy(...)`, `importData(...)` and `removeValue(...)` can reject on iOS

All four methods now report a failing Keychain deletion instead of always resolving, for example when the device is locked. Treat a rejection as "not deleted yet" and retry when the app is in the foreground and the device is unlocked; `importData()` rejects before writing anything, so the previous values stay intact. `destroy()` still attempts every deletion and always clears the in-memory state before it rejects.
