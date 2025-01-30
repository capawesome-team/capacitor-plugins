# Changelog

## 7.0.0

### Major Changes

- [`a8a9e276492df2176b327c6eedae5325ea0442ea`](https://github.com/capawesome-team/capacitor-plugins/commit/a8a9e276492df2176b327c6eedae5325ea0442ea) ([#402](https://github.com/capawesome-team/capacitor-plugins/pull/402)): feat: update to Capacitor 7

## 6.2.0

### Minor Changes

- [`b5088c7`](https://github.com/capawesome-team/capacitor-plugins/commit/b5088c73cef1e2be5d915d1ed7a0649865a3d3ca) ([#358](https://github.com/capawesome-team/capacitor-plugins/pull/358)): feat(android): support more Foreground service types

## 6.1.0

### Minor Changes

- [`6051ba5`](https://github.com/capawesome-team/capacitor-plugins/commit/6051ba5c06b8f49b7ec3c5a36f630358bf4d3dc3) ([#328](https://github.com/capawesome-team/capacitor-plugins/pull/328)): feat(android): add `updateForegroundService(...)` method

* [`965d322`](https://github.com/capawesome-team/capacitor-plugins/commit/965d322aa0d8f95dfa3a287df5233caa6a5aac78) ([#334](https://github.com/capawesome-team/capacitor-plugins/pull/334)): feat: add `createNotificationChannel` and `deleteNotificationChannel` methods

## 6.0.1

### Patch Changes

- [`a9652e8`](https://github.com/capawesome-team/capacitor-plugins/commit/a9652e89227cf72995d4fe3634d4c7aefc3d0f12) ([#164](https://github.com/capawesome-team/capacitor-plugins/pull/164)): fix(android): invalid property name returned

## 6.0.0

### Major Changes

- [`7033a8a`](https://github.com/capawesome-team/capacitor-plugins/commit/7033a8a42984523902f125239c3623e1e872b489) ([#159](https://github.com/capawesome-team/capacitor-plugins/pull/159)): feat: update to Capacitor 6

## [5.0.0](https://github.com/capawesome-team/sponsorware/compare/v0.3.0...v5.0.0) (2023-05-04)

### ⚠ BREAKING CHANGES

- This plugin now only supports Capacitor 5. Also, on Android 13, you now have to call `requestPermissions()`.

### Features

- update to Capacitor 5 ([#4](https://github.com/capawesome-team/sponsorware/issues/4)) ([35c55d3](https://github.com/capawesome-team/sponsorware/commit/35c55d333b421f6ae77f89081b27afe549350742))

## [0.3.0](https://github.com/capawesome-team/sponsorware/compare/v0.2.0...v0.3.0) (2023-03-14)

### ⚠ BREAKING CHANGES

- The following permission must be added to your `AndroidManifest.xml` before or after the `application` tag: `<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />`

### Features

- add methods `moveToForeground`, `requestManageOverlayPermission` and `checkManageOverlayPermission` ([7ef8d91](https://github.com/capawesome-team/sponsorware/commit/7ef8d91bd7ad8cd704d34f5ad2925069f50549b0))

## [0.2.0](https://github.com/capawesome-team/sponsorware/compare/v0.1.0...v0.2.0) (2023-03-12)

### ⚠ BREAKING CHANGES

- The following receiver must be added to your `AndroidManifest.xml` **in** the `application` tag: `<receiver android:name="io.capawesome.capacitorjs.plugins.foregroundservice.NotificationActionBroadcastReceiver" />`

### Features

- support notification buttons ([#3](https://github.com/capawesome-team/sponsorware/issues/3)) ([46f448c](https://github.com/capawesome-team/sponsorware/commit/46f448ce737ab5b8355e5adc9483c355343f7040))

## [0.1.0](https://github.com/capawesome-team/sponsorware/compare/v0.0.2...v0.1.0) (2022-12-09)

### ⚠ BREAKING CHANGES

- The following permission must be added to your `AndroidManifest.xml` before the `application` tag: `<uses-permission android:name="android.permission.WAKE_LOCK" />`

### Features

- keep the CPU on ([#2](https://github.com/capawesome-team/sponsorware/issues/2)) ([f92453d](https://github.com/capawesome-team/sponsorware/commit/f92453dc9594ae622e2745731187ad8dd5fdf2ff))

### [0.0.2](https://github.com/capawesome-team/sponsorware/compare/v0.0.1...v0.0.2) (2022-09-25)

### Features

- open app on notification click ([#1](https://github.com/capawesome-team/sponsorware/issues/1)) ([5754816](https://github.com/capawesome-team/sponsorware/commit/57548161aced2ceac89021c74936c84977875977))

## 0.0.1 (2022-09-05)

Initial release 🎉
