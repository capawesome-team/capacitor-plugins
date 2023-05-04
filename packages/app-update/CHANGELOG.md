# Changelog

## 5.0.0

### Major Changes

- [`be60559`](https://github.com/capawesome-team/capacitor-plugins/commit/be6055988799282a56ef696d09d34e3eaa9c6e2d) ([#22](https://github.com/capawesome-team/capacitor-plugins/pull/22)): refactor(android)!: rename project variable `androidPlayCore` to `androidPlayCoreVersion`

* [`3442479`](https://github.com/capawesome-team/capacitor-plugins/commit/3442479e9927c8a9641b0f27c04268d2bdb189a4) ([#20](https://github.com/capawesome-team/capacitor-plugins/pull/20)): feat!: update to Capacitor 5

## [2.1.1](https://github.com/capawesome-team/capacitor-app-update/compare/v2.1.0...v2.1.1) (2023-03-30)

### Bug Fixes

- **android:** catch native errors ([beb8070](https://github.com/capawesome-team/capacitor-app-update/commit/beb8070581a67df2a360a8a694c0ecf8b1d97b36))
- **android:** check if Google Play Services are available ([#64](https://github.com/capawesome-team/capacitor-app-update/issues/64)) ([e336da9](https://github.com/capawesome-team/capacitor-app-update/commit/e336da9b2256fcbba96391ea765974d8c7da51cb))
- **android:** replace deprecated `NativePlugin` ([5330f2c](https://github.com/capawesome-team/capacitor-app-update/commit/5330f2c7c5deb8a7c9878267a22e6ea7f5d7c1bb))

## [2.1.0](https://github.com/capawesome-team/capacitor-app-update/compare/v2.0.1...v2.1.0) (2023-01-21)

### Features

- add `removeAllListeners` method ([#62](https://github.com/capawesome-team/capacitor-app-update/issues/62)) ([bd4e948](https://github.com/capawesome-team/capacitor-app-update/commit/bd4e948533717b7ff373a1409edaf297c0daefde))

## [2.0.1](https://github.com/capawesome-team/capacitor-app-update/compare/v2.0.0...v2.0.1) (2022-11-03)

### Bug Fixes

- **ios:** prevent caching of lookup requests ([#56](https://github.com/capawesome-team/capacitor-app-update/issues/56)) ([4033edd](https://github.com/capawesome-team/capacitor-app-update/commit/4033eddfe709c627f99ba5efcfbcc75441299921))

## [2.0.0](https://github.com/capawesome-team/capacitor-app-update/compare/v1.3.1...v2.0.0) (2022-08-04)

### ⚠ BREAKING CHANGES

- rename to `@capawesome/capacitor-app-update` (#49)
- This plugin now only supports Capacitor 4.

### Features

- update to Capacitor 4 ([#48](https://github.com/capawesome-team/capacitor-app-update/issues/48)) ([ab47fa5](https://github.com/capawesome-team/capacitor-app-update/commit/ab47fa50ffc8972296b6f392666db47a172dc31a))

- rename to `@capawesome/capacitor-app-update` ([#49](https://github.com/capawesome-team/capacitor-app-update/issues/49)) ([66c16ba](https://github.com/capawesome-team/capacitor-app-update/commit/66c16bada83bf1f7f5de62bebc3679a8241f2a94))

## [1.3.1](https://github.com/robingenz/capacitor-app-update/compare/v1.3.0...v1.3.1) (2022-01-26)

### Bug Fixes

- **android:** `NullPointerException` ([#36](https://github.com/robingenz/capacitor-app-update/issues/36)) ([dfa918e](https://github.com/robingenz/capacitor-app-update/commit/dfa918e1e2826f970907138f804fefc077ec417c))
- inline source code in esm map files ([895fe1c](https://github.com/robingenz/capacitor-app-update/commit/895fe1cdb215f183d82f23d9b1376ed4cf9fc86f))

## [1.3.0](https://github.com/robingenz/capacitor-app-update/compare/v1.2.0...v1.3.0) (2021-12-15)

### Features

- **ios:** expose `minimumOsVersion` ([#31](https://github.com/robingenz/capacitor-app-update/issues/31)) ([1b8fe82](https://github.com/robingenz/capacitor-app-update/commit/1b8fe82036978dd59870e9914fd45634426cf239))

## [1.2.0](https://github.com/robingenz/capacitor-app-update/compare/v1.1.0...v1.2.0) (2021-12-09)

### Features

- **android:** expose `clientVersionStalenessDays` ([#29](https://github.com/robingenz/capacitor-app-update/issues/29)) ([0ae0dd6](https://github.com/robingenz/capacitor-app-update/commit/0ae0dd617241d5c563957c6f7992e4f9a57a933c))
- **android:** expose `installStatus` ([#30](https://github.com/robingenz/capacitor-app-update/issues/30)) ([20701e8](https://github.com/robingenz/capacitor-app-update/commit/20701e81e6a4c1749aee2ade8ad7cda11ae18878))

## [1.1.0](https://github.com/robingenz/capacitor-app-update/compare/v1.0.0...v1.1.0) (2021-10-30)

### Features

- **ios:** add `country` property ([#26](https://github.com/robingenz/capacitor-app-update/issues/26)) ([101c7dc](https://github.com/robingenz/capacitor-app-update/commit/101c7dcbdc7a171a39df3aad9ec9b270b2ef954f))

## [1.0.0](https://github.com/robingenz/capacitor-app-update/compare/v1.0.0-alpha.3...v1.0.0) (2021-08-23)

### Bug Fixes

- **android:** unregister listener on update complete ([#21](https://github.com/robingenz/capacitor-app-update/issues/21)) ([659103a](https://github.com/robingenz/capacitor-app-update/commit/659103af743b338a0c1f82a04431952152620d95))

## [1.0.0-alpha.3](https://github.com/robingenz/capacitor-app-update/compare/v1.0.0-alpha.2...v1.0.0-alpha.3) (2021-07-23)

### ⚠ BREAKING CHANGES

- Update to Capacitor 3

### Features

- add Capacitor 3 support ([#13](https://github.com/robingenz/capacitor-app-update/issues/13)) ([b9f0ae5](https://github.com/robingenz/capacitor-app-update/commit/b9f0ae5281ff87f880e903da806d64a02658a4c5))

## [1.0.0-alpha.2](https://github.com/robingenz/capacitor-app-update/compare/v1.0.0-alpha.1...v1.0.0-alpha.2) (2021-05-07)

### Bug Fixes

- **android:** various typos ([18be5a5](https://github.com/robingenz/capacitor-app-update/commit/18be5a5bebb21060994c0c1e9eb3fad28720c680))

## [1.0.0-alpha.1](https://github.com/robingenz/capacitor-app-update/compare/v1.0.0-alpha.0...v1.0.0-alpha.1) (2021-04-06)

### Bug Fixes

- **android:** set correct request codes ([#16](https://github.com/robingenz/capacitor-app-update/issues/16)) ([7fec630](https://github.com/robingenz/capacitor-app-update/commit/7fec63040cc26580249ab938c57819afe41d85f2))
- update peer dependency version ([#11](https://github.com/robingenz/capacitor-app-update/issues/11)) ([2ad63e3](https://github.com/robingenz/capacitor-app-update/commit/2ad63e3f884508e02e2f2b5d6577567abcaaa05b))

## 1.0.0-alpha.0 (2021-02-21)

Initial release 🎉
