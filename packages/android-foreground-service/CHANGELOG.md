# Changelog

## [5.0.0](https://github.com/capawesome-team/sponsorware/compare/v0.3.0...v5.0.0) (2023-05-04)


### ⚠ BREAKING CHANGES

* This plugin now only supports Capacitor 5. Also, on Android 13, you now have to call `requestPermissions()`.

### Features

* update to Capacitor 5 ([#4](https://github.com/capawesome-team/sponsorware/issues/4)) ([35c55d3](https://github.com/capawesome-team/sponsorware/commit/35c55d333b421f6ae77f89081b27afe549350742))

## [0.3.0](https://github.com/capawesome-team/sponsorware/compare/v0.2.0...v0.3.0) (2023-03-14)


### ⚠ BREAKING CHANGES

* The following permission must be added to your `AndroidManifest.xml` before or after the `application` tag: `<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />`

### Features

* add methods `moveToForeground`, `requestManageOverlayPermission` and `checkManageOverlayPermission` ([7ef8d91](https://github.com/capawesome-team/sponsorware/commit/7ef8d91bd7ad8cd704d34f5ad2925069f50549b0))

## [0.2.0](https://github.com/capawesome-team/sponsorware/compare/v0.1.0...v0.2.0) (2023-03-12)


### ⚠ BREAKING CHANGES

* The following receiver must be added to your `AndroidManifest.xml` **in** the `application` tag: `<receiver android:name="io.capawesome.capacitorjs.plugins.foregroundservice.NotificationActionBroadcastReceiver" />`

### Features

* support notification buttons ([#3](https://github.com/capawesome-team/sponsorware/issues/3)) ([46f448c](https://github.com/capawesome-team/sponsorware/commit/46f448ce737ab5b8355e5adc9483c355343f7040))

## [0.1.0](https://github.com/capawesome-team/sponsorware/compare/v0.0.2...v0.1.0) (2022-12-09)


### ⚠ BREAKING CHANGES

* The following permission must be added to your `AndroidManifest.xml` before the `application` tag: `<uses-permission android:name="android.permission.WAKE_LOCK" />`

### Features

* keep the CPU on ([#2](https://github.com/capawesome-team/sponsorware/issues/2)) ([f92453d](https://github.com/capawesome-team/sponsorware/commit/f92453dc9594ae622e2745731187ad8dd5fdf2ff))

### [0.0.2](https://github.com/capawesome-team/sponsorware/compare/v0.0.1...v0.0.2) (2022-09-25)


### Features

* open app on notification click ([#1](https://github.com/capawesome-team/sponsorware/issues/1)) ([5754816](https://github.com/capawesome-team/sponsorware/commit/57548161aced2ceac89021c74936c84977875977))

## 0.0.1 (2022-09-05)

Initial release 🎉
