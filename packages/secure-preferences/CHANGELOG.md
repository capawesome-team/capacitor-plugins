# Changelog

## 0.4.0

### Minor Changes

- [`3803f402577ad74ad993ce0abc1ddee125e58328`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3803f402577ad74ad993ce0abc1ddee125e58328): fix(android)!: reject `get(...)` with `DECRYPTION_FAILED` or `KEY_INVALIDATED` instead of resolving `null` when a value cannot be read

- [`3803f402577ad74ad993ce0abc1ddee125e58328`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3803f402577ad74ad993ce0abc1ddee125e58328): fix(android)!: remove the AES-128 key-size fallback and reset the encryption key in `clear()`

- [`6c0cf50339918c7c2d6bbd1e770afb1700e2cd8f`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/6c0cf50339918c7c2d6bbd1e770afb1700e2cd8f) ([#569](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/569)): fix(ios)!: reject `get(...)` when the Keychain read fails

### Patch Changes

- [`3361fd91a5a70a83cb11e7a584b8b758a2a1061d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3361fd91a5a70a83cb11e7a584b8b758a2a1061d) ([#576](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/576)): fix(android): look up the encryption key with a single Keystore call

- [`3361fd91a5a70a83cb11e7a584b8b758a2a1061d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3361fd91a5a70a83cb11e7a584b8b758a2a1061d) ([#576](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/576)): fix(ios): only delete the plugin's own Keychain items in `clear()` and resolve `remove(...)` when the key does not exist

## 0.3.3

### Patch Changes

- [`d31e7c2b33eeabfaf0635b84b39d069c549bf50c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d31e7c2b33eeabfaf0635b84b39d069c549bf50c) ([#555](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/555)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.3.2

### Patch Changes

- [`ae6d88433783c1fdabce08a841078bd4420ea906`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ae6d88433783c1fdabce08a841078bd4420ea906) ([#434](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/434)): chore: declare package license

- [`cc425a124a9ae2967f9e7a564f1fbfd450dd6464`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/cc425a124a9ae2967f9e7a564f1fbfd450dd6464) ([#455](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/455)): chore: replace LICENSE with the new End User License Agreement (EULA). See https://capawesome.io/legal/eula/ for the current version.

## 0.3.1

### Patch Changes

- [`ae03cf50eb66ad15f91d922443cfb4e9c071ed8f`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ae03cf50eb66ad15f91d922443cfb4e9c071ed8f) ([#406](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/406)): fix(android): AGP 9.0 no longer supports `proguard-android.txt`

## 0.3.0

### Minor Changes

- [`ae8313ec7c6f82de92876fd8bc08b880bf3aa8e9`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ae8313ec7c6f82de92876fd8bc08b880bf3aa8e9) ([#361](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/361)): feat(ios)!: enhance error reporting with OSStatus codes (see `BREAKING.md`)

## 0.2.0

### Minor Changes

- [`9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d) ([#353](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/353)): feat!: update to Capacitor 8 (see `BREAKING.md`)

## 0.1.0

### Minor Changes

- [`d7df5dd5ccd3835ab466b6a374357e092c11a7a1`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d7df5dd5ccd3835ab466b6a374357e092c11a7a1) ([#128](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/128)): Initial release 🎉
