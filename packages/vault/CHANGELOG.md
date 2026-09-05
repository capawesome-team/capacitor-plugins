# Changelog

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
