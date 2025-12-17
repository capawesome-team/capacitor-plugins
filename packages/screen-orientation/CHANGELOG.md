# Changelog

## 8.0.0

### Major Changes

- [`12a22f63df48338afc9ed1ef91f7db0ace660540`](https://github.com/capawesome-team/capacitor-plugins/commit/12a22f63df48338afc9ed1ef91f7db0ace660540) ([#705](https://github.com/capawesome-team/capacitor-plugins/pull/705)): feat!: update to Capacitor 8 (see `BREAKING.md`)

## 7.1.0

### Minor Changes

- [`4b99f3aa51a292081886c29fc782f6b74cae9556`](https://github.com/capawesome-team/capacitor-plugins/commit/4b99f3aa51a292081886c29fc782f6b74cae9556) ([#652](https://github.com/capawesome-team/capacitor-plugins/pull/652)): feat: lock to current orientation by default

## 7.0.2

### Patch Changes

- [`bb6d837e87dbcc92ec16802231662d4648c9f59b`](https://github.com/capawesome-team/capacitor-plugins/commit/bb6d837e87dbcc92ec16802231662d4648c9f59b) ([#599](https://github.com/capawesome-team/capacitor-plugins/pull/599)): fix(ios): assure correct orientation after orientation unlock

## 7.0.1

### Patch Changes

- [`1dc1fe3626a199a0f5dec2685443d74589cf43e5`](https://github.com/capawesome-team/capacitor-plugins/commit/1dc1fe3626a199a0f5dec2685443d74589cf43e5) ([#407](https://github.com/capawesome-team/capacitor-plugins/pull/407)): fix: set correct target paths for SPM support

## 7.0.0

### Major Changes

- [`a8a9e276492df2176b327c6eedae5325ea0442ea`](https://github.com/capawesome-team/capacitor-plugins/commit/a8a9e276492df2176b327c6eedae5325ea0442ea) ([#402](https://github.com/capawesome-team/capacitor-plugins/pull/402)): feat: update to Capacitor 7

### Minor Changes

- [`fdbcd817f6ab89525149685045f6ea7b78606b45`](https://github.com/capawesome-team/capacitor-plugins/commit/fdbcd817f6ab89525149685045f6ea7b78606b45) ([#382](https://github.com/capawesome-team/capacitor-plugins/pull/382)): feat: add SPM support

## 6.0.1

### Patch Changes

- [`165db48`](https://github.com/capawesome-team/capacitor-plugins/commit/165db488a5c4aea335fa71e535533cd103424a0b) ([#209](https://github.com/capawesome-team/capacitor-plugins/pull/209)): fix(ios): lock orientation type `LANDSCAPE` now supports `LANDSCAPE_PRIMARY` and `LANDSCAPE_SECONDARY`

## 6.0.0

### Major Changes

- [`7033a8a`](https://github.com/capawesome-team/capacitor-plugins/commit/7033a8a42984523902f125239c3623e1e872b489) ([#159](https://github.com/capawesome-team/capacitor-plugins/pull/159)): feat: update to Capacitor 6

## 5.0.1

### Patch Changes

- [`89ab014`](https://github.com/capawesome-team/capacitor-plugins/commit/89ab014dba722e4ded1822a4862b04a8c621df47) ([#101](https://github.com/capawesome-team/capacitor-plugins/pull/101)): fix(ios): `None of the requested orientations are supported by the view controller` error

## 5.0.0

### Major Changes

- [`3442479`](https://github.com/capawesome-team/capacitor-plugins/commit/3442479e9927c8a9641b0f27c04268d2bdb189a4) ([#20](https://github.com/capawesome-team/capacitor-plugins/pull/20)): feat!: update to Capacitor 5

## [2.0.2](https://github.com/capawesome-team/capacitor-screen-orientation/compare/v2.0.1...v2.0.2) (2023-01-21)

### Bug Fixes

- **ios:** `Setting UIDevice.orientation is not supported` error on iOS 16 ([#29](https://github.com/capawesome-team/capacitor-screen-orientation/issues/29)) ([879a3fd](https://github.com/capawesome-team/capacitor-screen-orientation/commit/879a3fdd50301371091ec8784a5906d9af0f5fee))

## [2.0.1](https://github.com/capawesome-team/capacitor-screen-orientation/compare/v2.0.0...v2.0.1) (2022-08-19)

### Bug Fixes

- **ios:** replace deprecated `statusBarOrientation` ([#24](https://github.com/capawesome-team/capacitor-screen-orientation/issues/24)) ([7405595](https://github.com/capawesome-team/capacitor-screen-orientation/commit/74055956d03707c087d411d039c247dbfc96daa0))

## [2.0.0](https://github.com/capawesome-team/capacitor-screen-orientation/compare/v1.1.7...v2.0.0) (2022-08-04)

### ⚠ BREAKING CHANGES

- This plugin was renamed to `@capawesome/capacitor-screen-orientation`. Please install the new npm package and run `npx cap sync`.
- This plugin now only supports Capacitor 4.

### Features

- update to Capacitor 4 ([#21](https://github.com/capawesome-team/capacitor-screen-orientation/issues/21)) ([adadb99](https://github.com/capawesome-team/capacitor-screen-orientation/commit/adadb9995827251466fbe98d8449dc29f603f983))

- rename to `@capawesome/capacitor-screen-orientation` ([#22](https://github.com/capawesome-team/capacitor-screen-orientation/issues/22)) ([f846c40](https://github.com/capawesome-team/capacitor-screen-orientation/commit/f846c4067d5b31721989887952b3bbec931ed3f3))

## [1.1.7](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.6...v1.1.7) (2022-04-21)

### Bug Fixes

- **ios:** return correct current orientation type while locked ([#19](https://github.com/robingenz/capacitor-screen-orientation/issues/19)) ([9b6053a](https://github.com/robingenz/capacitor-screen-orientation/commit/9b6053ae9402db591c58f1bcc19358a2ac69d90f))

## [1.1.6](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.5...v1.1.6) (2022-04-21)

### Bug Fixes

- **ios:** `removeAllListeners` method not found ([7c20eb8](https://github.com/robingenz/capacitor-screen-orientation/commit/7c20eb82f6b4ecd4600ea94d209a11650b925edb))

## [1.1.5](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.4...v1.1.5) (2022-04-13)

### Bug Fixes

- **ios:** orientation change events fired while locked ([#16](https://github.com/robingenz/capacitor-screen-orientation/issues/16)) ([ce876e6](https://github.com/robingenz/capacitor-screen-orientation/commit/ce876e6795d51b21e3a6218c31e7d45725ea6bd6))

## [1.1.4](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.3...v1.1.4) (2022-01-26)

### Bug Fixes

- inline source code in esm map files ([31692b7](https://github.com/robingenz/capacitor-screen-orientation/commit/31692b717bac9c54317e46046aefb605d4aeaafe))

## [1.1.3](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.2...v1.1.3) (2022-01-12)

### Bug Fixes

- **ios:** rotate back to current orientation after unlock ([#14](https://github.com/robingenz/capacitor-screen-orientation/issues/14)) ([7def624](https://github.com/robingenz/capacitor-screen-orientation/commit/7def624915826cb263c71e7e53b507c1eb8bf584))

## [1.1.2](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.1...v1.1.2) (2022-01-07)

### Bug Fixes

- **ios:** `UI API called on a background thread` ([#12](https://github.com/robingenz/capacitor-screen-orientation/issues/12)) ([e70232d](https://github.com/robingenz/capacitor-screen-orientation/commit/e70232d32bd786dd9459c4261bc9a81ad6961cfb))

## [1.1.1](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.1.0...v1.1.1) (2021-12-14)

### Bug Fixes

- **ios:** handle `faceUp` and `faceDown` orientation ([#10](https://github.com/robingenz/capacitor-screen-orientation/issues/10)) ([25d58a4](https://github.com/robingenz/capacitor-screen-orientation/commit/25d58a4da012c6faf8d19b1d7221cd9d4ac22f23))

## [1.1.0](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.0.2...v1.1.0) (2021-08-26)

### Features

- add `screenOrientationChange` listener ([#8](https://github.com/robingenz/capacitor-screen-orientation/issues/8)) ([9bb5c2e](https://github.com/robingenz/capacitor-screen-orientation/commit/9bb5c2ef89cabfcdebc33a2d6a4d8f606179fe51))

## [1.0.2](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.0.1...v1.0.2) (2021-07-16)

### Bug Fixes

- **ios:** rotate to locked orientation ([#7](https://github.com/robingenz/capacitor-screen-orientation/issues/7)) ([a3bac7b](https://github.com/robingenz/capacitor-screen-orientation/commit/a3bac7b522897893362199d44e63d0e1451b1fb5))

## [1.0.1](https://github.com/robingenz/capacitor-screen-orientation/compare/v1.0.0...v1.0.1) (2021-06-02)

### Bug Fixes

- **ios:** typo `potrait` ([9c3380f](https://github.com/robingenz/capacitor-screen-orientation/commit/9c3380f25e139624d4641bb805f779c5e61a5c97))

## 1.0.0 (2021-05-17)

Initial release 🎉
