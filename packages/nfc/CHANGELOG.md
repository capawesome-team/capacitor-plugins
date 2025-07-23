# Changelog

## 7.2.0

### Minor Changes

- [`d8c8c4cdca6b177615563d770feded3df762083a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d8c8c4cdca6b177615563d770feded3df762083a) ([#201](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/201)): feat: add `isAvailable()` method

## 7.1.0

### Minor Changes

- [`4972c2f85d305836804eea23f2342e7bbd287aa5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/4972c2f85d305836804eea23f2342e7bbd287aa5) ([#194](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/194)): feat(android): automatically apply certain permission elements in app manifest

### Patch Changes

- [`e304408abd4dba89d111475794483bea2ab68c62`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/e304408abd4dba89d111475794483bea2ab68c62) ([#167](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/167)): fix(ios): reject plugin call if privacy descriptions are missing

## 7.0.4

### Patch Changes

- [`34f00cd7b4969c309778bf7093986fb4c7295d2f`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/34f00cd7b4969c309778bf7093986fb4c7295d2f) ([#97](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/97)): fix(ios): non-NDEF formatted tags could not be read

## 7.0.3

### Patch Changes

- [`9c4046836cac1019d89e0bcac3961e7de18ddfb7`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9c4046836cac1019d89e0bcac3961e7de18ddfb7) ([#78](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/78)): fix(ios): do not throw `NDEF tag does not contain any NDEF message` error

## 7.0.2

### Patch Changes

- [`b5eccc31fc96ce56011f219c66fbab4c531300a5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/b5eccc31fc96ce56011f219c66fbab4c531300a5) ([#58](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/58)): fix(ios): `scanSessionError` event was not emitted in certain cases

## 7.0.1

### Patch Changes

- [`9c89d10bccaf946a1a19e859e642c6c1d2eb09bf`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9c89d10bccaf946a1a19e859e642c6c1d2eb09bf) ([#56](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/56)): fix: set correct target paths for SPM support

## 7.0.0

### Major Changes

- [`556fe8c8c53b9ff386590702fe8f6f6ff04fe1c5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/556fe8c8c53b9ff386590702fe8f6f6ff04fe1c5) ([#51](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/51)): fix(ios): reverse nfc tag identifier array for ISO 15693 tags

- [`41f632a3dd08dd520724b0266971b63928f034f0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/41f632a3dd08dd520724b0266971b63928f034f0) ([#53](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/53)): feat: update to Capacitor 7

### Minor Changes

- [`071c0357b8e944aa6487b963eea7ab325db88b95`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/071c0357b8e944aa6487b963eea7ab325db88b95) ([#49](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/49)): feat: add SPM support

## 6.5.1

### Patch Changes

- [`0aa09fa`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0aa09fad995336673e5b5c01a29d976cbd29cf81) ([#47](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/47)): fix(ios): the `transceive(...)` method now returns the missing status bytes when using the `Iso7816` tech type

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

## [6.5.0](https://github.com/capawesome-team/sponsorware/compare/v6.4.0...v6.5.0) (2024-12-16)

### Features

- **ios:** 0x24 command support for the `transceive(...)` method ([#29](https://github.com/capawesome-team/sponsorware/issues/29)) ([2e718cb](https://github.com/capawesome-team/sponsorware/commit/2e718cb9c0d4f35012169986db17ff149ba7c962))
- **ios:** add `manufacturerCode` property ([#27](https://github.com/capawesome-team/sponsorware/issues/27)) ([f1a25eb](https://github.com/capawesome-team/sponsorware/commit/f1a25ebce5c6a457c9cb6b1a78c0fc27441049a8))
- support HCE ([#25](https://github.com/capawesome-team/sponsorware/issues/25)) ([ecf4837](https://github.com/capawesome-team/sponsorware/commit/ecf4837975023b985d2e5a9af217d8384bd2227f))

## [6.4.0](https://github.com/capawesome-team/sponsorware/compare/v6.3.0...v6.4.0) (2024-11-06)

### Features

- **ios:** add compatibility mode ([#28](https://github.com/capawesome-team/sponsorware/issues/28)) ([c0a9374](https://github.com/capawesome-team/sponsorware/commit/c0a937454be1fd1c960f3522e02f317deaf3c341))

## [6.3.0](https://github.com/capawesome-team/sponsorware/compare/v6.2.1...v6.3.0) (2024-10-31)

- update `LICENSE`

### [6.2.1](https://github.com/capawesome-team/sponsorware/compare/v6.2.0...v6.2.1) (2024-08-16)

### Bug Fixes

- **android:** skip `canMakeReadOnly` if it cannot be read ([bddaeec](https://github.com/capawesome-team/sponsorware/commit/bddaeec13a6085b6f36c2044c10132c2c7ca5baf))

## [6.2.0](https://github.com/capawesome-team/sponsorware/compare/v6.1.0...v6.2.0) (2024-06-01)

### Features

- **ios:** add `setAlertMessage(...)` method ([#24](https://github.com/capawesome-team/sponsorware/issues/24)) ([af380d9](https://github.com/capawesome-team/sponsorware/commit/af380d9e81ae01cc45d5b7c6cc664f83cb848579))

## [6.1.0](https://github.com/capawesome-team/sponsorware/compare/v6.0.0...v6.1.0) (2024-05-15)

### Features

- **android:** add `getAntennaInfo` method ([#23](https://github.com/capawesome-team/sponsorware/issues/23)) ([70deebf](https://github.com/capawesome-team/sponsorware/commit/70deebf6457612899695f67c46270f80f8f82c5f))

## [6.0.0](https://github.com/capawesome-team/sponsorware/compare/v5.1.0...v6.0.0) (2024-04-16)

### ⚠ BREAKING CHANGES

- This plugin now only supports Capacitor 6.

### Features

- **android:** add `connect(...)` and `close(...)` methods ([#21](https://github.com/capawesome-team/sponsorware/issues/21)) ([299fa9c](https://github.com/capawesome-team/sponsorware/commit/299fa9c282b696ea7a6045f064a75d1169c1cd94))
- **ios:** support iso15693 command code `0x23` ([#19](https://github.com/capawesome-team/sponsorware/issues/19)) ([923f46a](https://github.com/capawesome-team/sponsorware/commit/923f46a5beadeb0c5a3f966a1dbac3132ff5c118))
- update to Capacitor 6 ([#22](https://github.com/capawesome-team/sponsorware/issues/22)) ([0c7b849](https://github.com/capawesome-team/sponsorware/commit/0c7b849560f796e16925a46b07f0fc1eb7c6e1f2))

### Bug Fixes

- **android:** convert bytes to unsigned ([#20](https://github.com/capawesome-team/sponsorware/issues/20)) ([13779ba](https://github.com/capawesome-team/sponsorware/commit/13779baa627435b4e303c105d1b4f9a56ed1801d))

## [5.1.0](https://github.com/capawesome-team/sponsorware/compare/v5.0.2...v5.1.0) (2024-01-18)

### Features

- **ios:** add support for Iso7816 ([#17](https://github.com/capawesome-team/sponsorware/issues/17)) ([1cafc38](https://github.com/capawesome-team/sponsorware/commit/1cafc383ca95059bfd3bce1f064949895805a712))
- **ios:** support MIFARE DESFire in `transceive(...)` method ([#15](https://github.com/capawesome-team/sponsorware/issues/15)) ([60194a1](https://github.com/capawesome-team/sponsorware/commit/60194a179b9627d3ecf61333f6027d4ade1adf08))

### Bug Fixes

- **ios:** detect `NfcTagTechType.NfcV` correctly ([5570562](https://github.com/capawesome-team/sponsorware/commit/5570562449151d007d58d799bf72b9258a39823d))
- **ios:** support `MIFARE_PLUS` and `MIFARE_ULTRALIGHT` in `transceive(...)` method ([72ec3ae](https://github.com/capawesome-team/sponsorware/commit/72ec3ae7a2030c014ce866c9dcfde6553ae96364))
- retain `nfcTagScanned` event until consumed ([#16](https://github.com/capawesome-team/sponsorware/issues/16)) ([3ef3dee](https://github.com/capawesome-team/sponsorware/commit/3ef3deeb5b32f76105af53ada282d9abf83de28e))

### [5.0.2](https://github.com/capawesome-team/sponsorware/compare/v5.0.1...v5.0.2) (2023-06-13)

### Bug Fixes

- **android:** `ex.getMessage()` could be `null` ([#14](https://github.com/capawesome-team/sponsorware/issues/14)) ([84d5952](https://github.com/capawesome-team/sponsorware/commit/84d5952b4abd52f69a9642c05ee9c74b4bb1c421))

### [5.0.1](https://github.com/capawesome-team/sponsorware/compare/v5.0.0...v5.0.1) (2023-06-03)

### Bug Fixes

- **android:** `format()` method failed on some tags ([#13](https://github.com/capawesome-team/sponsorware/issues/13)) ([43f7290](https://github.com/capawesome-team/sponsorware/commit/43f7290d2bf4846aa7b037566a1f83e32383d269))

## [5.0.0](https://github.com/capawesome-team/sponsorware/compare/v0.3.2...v5.0.0) (2023-05-04)

### ⚠ BREAKING CHANGES

- This plugin now only supports Capacitor 5.

### Features

- update to Capacitor 5 ([#11](https://github.com/capawesome-team/sponsorware/issues/11)) ([4221923](https://github.com/capawesome-team/sponsorware/commit/4221923be3a40016b5dec50a1a5ede919cf227b7))

### Bug Fixes

- **android:** `Cannot invoke method toList() on null object` ([00f9e96](https://github.com/capawesome-team/sponsorware/commit/00f9e960cb9559a87d999e2b2169c066c1a102f2))

### [0.3.2](https://github.com/capawesome-team/sponsorware/compare/v0.3.1...v0.3.2) (2023-05-01)

### Bug Fixes

- **android:** `Attempt to get length of null array` ([#12](https://github.com/capawesome-team/sponsorware/issues/12)) ([f271da6](https://github.com/capawesome-team/sponsorware/commit/f271da68533fccbd15c66beb9026af9d97b250a9))

### [0.3.1](https://github.com/capawesome-team/sponsorware/compare/v0.3.0...v0.3.1) (2023-02-06)

### Features

- **utils:** add methods `convertBytesToHex` and `convertHexToBytes` ([#9](https://github.com/capawesome-team/sponsorware/issues/9)) ([1969c8b](https://github.com/capawesome-team/sponsorware/commit/1969c8bf8eaf252369b6e9a1e286ba15d907f850))

### Bug Fixes

- **ios:** restart polling after reading a tag ([#8](https://github.com/capawesome-team/sponsorware/issues/8)) ([50ff554](https://github.com/capawesome-team/sponsorware/commit/50ff554097c109f95e44b6742c84dd1164f817c3))
- **utils:** add missing `UriIdentifierCode` option ([#10](https://github.com/capawesome-team/sponsorware/issues/10)) ([b82600e](https://github.com/capawesome-team/sponsorware/commit/b82600e46ef6eaff9cf2b87668f4462e3db407ca))

## [0.3.0](https://github.com/capawesome-team/sponsorware/compare/v0.2.0...v0.3.0) (2022-11-11)

### ⚠ BREAKING CHANGES

- **android:** Error messages on Android are now no longer localized.

### Features

- add `erase` and `format` methods ([#6](https://github.com/capawesome-team/sponsorware/issues/6)) ([8c7c8a5](https://github.com/capawesome-team/sponsorware/commit/8c7c8a5c03697f605278c6849b224e6d3a887bd5))
- **android:** return non-localized error messages ([#7](https://github.com/capawesome-team/sponsorware/issues/7)) ([0d16442](https://github.com/capawesome-team/sponsorware/commit/0d164427026bef5394d21a5409788ea268347891))
- support NFC raw commands ([#5](https://github.com/capawesome-team/sponsorware/issues/5)) ([a00bfd9](https://github.com/capawesome-team/sponsorware/commit/a00bfd97a165ddc077fe59c9e3040027c12aa562))

### Bug Fixes

- **android:** add missing `makeReadOnly` implementation ([#4](https://github.com/capawesome-team/sponsorware/issues/4)) ([326d4a5](https://github.com/capawesome-team/sponsorware/commit/326d4a5505346a6b369e28c4ae07a62d937b6814))

## [0.2.0](https://github.com/capawesome-team/capacitor-nfc-sponsorware/compare/v0.1.1...v0.2.0) (2022-10-03)

### ⚠ BREAKING CHANGES

- FeliCa tags are no longer detected by default on iOS. The polling option must be configured using `pollingOptions`. (#3)

### Features

- **ios:** add `pollingOptions` option ([#3](https://github.com/capawesome-team/capacitor-nfc-sponsorware/issues/3)) ([8d809e8](https://github.com/capawesome-team/capacitor-nfc-sponsorware/commit/8d809e838f679b2a7ce873854218f05de951f49a))

### [0.1.1](https://github.com/capawesome-team/capacitor-nfc-sponsorware/compare/v0.1.0...v0.1.1) (2022-09-07)

### Bug Fixes

- **android:** catch all native errors ([1244330](https://github.com/capawesome-team/capacitor-nfc-sponsorware/commit/124433014bf882c11376e3886c781d59a6f6fb63))

## [0.1.0](https://github.com/capawesome-team/capacitor-nfc-sponsorware/compare/v0.0.1...v0.1.0) (2022-08-20)

### ⚠ BREAKING CHANGES

- update to Capacitor 4 (#1)

### Features

- update to Capacitor 4 ([#1](https://github.com/capawesome-team/capacitor-nfc-sponsorware/issues/1)) ([da113e5](https://github.com/capawesome-team/capacitor-nfc-sponsorware/commit/da113e5307a57f745deb19bc2eaf088a8e6a0372))

## 0.0.1 (2022-08-12)

Initial release 🎉
