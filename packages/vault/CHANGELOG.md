# Changelog

## 0.2.4

### Patch Changes

- [`cbbb27cb34e4b83ad1b2c3f159453c8dccf62654`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/cbbb27cb34e4b83ad1b2c3f159453c8dccf62654) ([#711](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/711)): feat: reject with the new `AUTHENTICATOR_LOCKED_OUT` error code when the biometric authentication is locked after too many failed attempts

- [`cbbb27cb34e4b83ad1b2c3f159453c8dccf62654`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/cbbb27cb34e4b83ad1b2c3f159453c8dccf62654) ([#711](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/711)): fix(ios): let `initialize()` and `exists()` succeed while the biometric authentication is locked

- [`19f24150f0d44b6799a141e00623941021a1a939`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/19f24150f0d44b6799a141e00623941021a1a939) ([#713](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/713)): fix(ios): hide the fallback button of the authentication prompt for `BIOMETRIC` vaults

## 0.2.3

### Patch Changes

- [`a08bf65dd11e64480817503e16eb844e67177c92`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/a08bf65dd11e64480817503e16eb844e67177c92) ([#706](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/706)): fix(android): reject `unlock()` with `KEY_INVALIDATED` only when the stored key fails its integrity check

- [`550954ca0844b9ff11be1f69a6d9193902ffde7c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/550954ca0844b9ff11be1f69a6d9193902ffde7c) ([#708](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/708)): fix: reject a non-integer `lockAfterBackgrounded`, non-string `importData()` values and a non-string `vaultId` instead of ignoring them

- [`1fcc6a79c480fee82015f0e9fa946592752e4321`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/1fcc6a79c480fee82015f0e9fa946592752e4321) ([#709](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/709)): docs: describe what the plugin protects against

- [`c53bee4dfb1c4b4a869a5fb989f23436b9eca644`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/c53bee4dfb1c4b4a869a5fb989f23436b9eca644) ([#707](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/707)): fix: drop the key of an unlock that completes after `lock()`, `destroy()` or `initialize()`

## 0.2.2

### Patch Changes

- [`f3b34892cc023d0ee32fcd828eb3914157f013be`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f3b34892cc023d0ee32fcd828eb3914157f013be) ([#696](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/696)): feat: add the `CUSTOM_PASSCODE` vault type, whose key is protected by an app-provided passcode, with `setPasscode(...)` and the `PASSCODE_INVALID` and `PASSCODE_LIMIT_REACHED` error codes

- [`d7aea8dca8ea1038f64a03112cd52a8d4e87a035`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d7aea8dca8ea1038f64a03112cd52a8d4e87a035) ([#704](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/704)): refactor(ios): replace the deprecated `kSecUseAuthenticationUI` Keychain query key with an `LAContext`

- [`f3b34892cc023d0ee32fcd828eb3914157f013be`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f3b34892cc023d0ee32fcd828eb3914157f013be) ([#696](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/696)): fix(ios): reject `exists()`, `getKeys()`, `getValue()`, `isEmpty()` and `exportData()` when the Keychain cannot be read instead of reporting an empty vault

- [`f3b34892cc023d0ee32fcd828eb3914157f013be`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f3b34892cc023d0ee32fcd828eb3914157f013be) ([#696](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/696)): fix(ios): register the plugin class as `CapawesomeVaultPlugin` so that the app no longer crashes at launch when Ionic Identity Vault is installed as well

- [`ad155a2dedd4a2a6bfa2c7f7896cee149c1a0ea6`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ad155a2dedd4a2a6bfa2c7f7896cee149c1a0ea6) ([#705](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/705)): fix(ios): normalize `vaultId` to one Unicode form so that ids that differ only in composition address the same vault

## 0.2.1

### Patch Changes

- [`5b944aca9fa89cdb4284c1839b87bb243eebd12f`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/5b944aca9fa89cdb4284c1839b87bb243eebd12f) ([#664](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/664)): fix(android): persist `setValue(...)`, `removeValue(...)` and `importData(...)` synchronously so that the values survive the app process being killed right after the call resolves

## 0.2.0

### Minor Changes

- [`080bdca03c216fb2dd6dd38db29d9fc72052a365`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/080bdca03c216fb2dd6dd38db29d9fc72052a365) ([#570](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/570)): fix(ios)!: reject `clear(...)`, `destroy(...)`, `importData(...)` and `removeValue(...)` when the Keychain refuses to delete an item

- [`3803f402577ad74ad993ce0abc1ddee125e58328`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3803f402577ad74ad993ce0abc1ddee125e58328): fix!: reject `initialize(...)` and `unlock(...)` with `KEY_INVALIDATED` when the encryption key is missing or can no longer be used while values are still stored

### Patch Changes

- [`2dc2cdf276d4f9c608ecef54027ec73aa2032b79`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/2dc2cdf276d4f9c608ecef54027ec73aa2032b79) ([#572](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/572)): fix(android): report `UNLOCK_CANCELED` for biometric lockouts and prompt timeouts, and `AUTHENTICATOR_UNAVAILABLE` for unavailable biometric hardware, a missing device credential or a required security update

- [`05b0b86dafa609436d60cebedf1c6a2d8c4d6ff8`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/05b0b86dafa609436d60cebedf1c6a2d8c4d6ff8) ([#571](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/571)): fix: measure the `lockAfterBackgrounded` timeout with a monotonic clock

- [`3361fd91a5a70a83cb11e7a584b8b758a2a1061d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3361fd91a5a70a83cb11e7a584b8b758a2a1061d) ([#576](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/576)): feat: reject `getValue(...)` with `DECRYPTION_FAILED` and skip such values in `exportData(...)`, reporting their number in `skippedCount`

- [`3803f402577ad74ad993ce0abc1ddee125e58328`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3803f402577ad74ad993ce0abc1ddee125e58328): fix: delete the stored values before the encryption key in `destroy(...)`, persist the deletions synchronously on Android and attempt every deletion before rejecting

- [`3361fd91a5a70a83cb11e7a584b8b758a2a1061d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3361fd91a5a70a83cb11e7a584b8b758a2a1061d) ([#576](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/576)): fix: report an existing vault in `exists(...)` while values are still stored

- [`3361fd91a5a70a83cb11e7a584b8b758a2a1061d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3361fd91a5a70a83cb11e7a584b8b758a2a1061d) ([#576](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/576)): fix(ios): report `UNLOCK_CANCELED` when the authentication prompt cannot be shown

- [`2dc2cdf276d4f9c608ecef54027ec73aa2032b79`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/2dc2cdf276d4f9c608ecef54027ec73aa2032b79) ([#572](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/572)): fix(web): escape the vault id and keys in the localStorage key names

## 0.1.1

### Patch Changes

- [`d31e7c2b33eeabfaf0635b84b39d069c549bf50c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d31e7c2b33eeabfaf0635b84b39d069c549bf50c) ([#555](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/555)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.1.0

### Minor Changes

- [`4caa96d9cecb8b1b88a4c19d02c2b43d6abaab41`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/4caa96d9cecb8b1b88a4c19d02c2b43d6abaab41) ([#469](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/469)): Initial release 🎉
