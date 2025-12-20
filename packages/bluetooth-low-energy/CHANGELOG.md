# Changelog

## 8.0.2

### Patch Changes

- [`188711410525c88de41b37c12000ae319e0c524a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/188711410525c88de41b37c12000ae319e0c524a) ([#364](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/364)): fix(android): improve error handling for BLE advertising

## 8.0.1

### Patch Changes

- [`e89a9356329ce131ac99c9e02639c322398d1123`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/e89a9356329ce131ac99c9e02639c322398d1123) ([#360](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/360)): fix(android): add scan response data to advertising

## 8.0.0

### Major Changes

- [`9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d) ([#353](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/353)): feat!: update to Capacitor 8 (see `BREAKING.md`)

- [`c29868d4e168de35773a0ce3186061879549eafd`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/c29868d4e168de35773a0ce3186061879549eafd) ([#359](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/359)): feat(android)!: handle foreground service start failure with detailed error message

- [`d1c3bd063a4a41ee9b8a3b236da4cae128215650`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d1c3bd063a4a41ee9b8a3b236da4cae128215650): fix!: throw correct error code (see `BREAKING.md`)

## 7.8.0

### Minor Changes

- [`b8a6aca6bb7865ca94febda252abb62564c0b563`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/b8a6aca6bb7865ca94febda252abb62564c0b563) ([#347](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/347)): feat(ios): support `isEnabled` method in peripheral mode

## 7.7.1

### Patch Changes

- [`a6497fd275daa215db8c16816678008958d15635`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/a6497fd275daa215db8c16816678008958d15635) ([#321](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/321)): fix(android): null pointer exception in `stopScan()` method

## 7.7.0

### Minor Changes

- [`6a09c180cc20d34b1d41c7d07816cb5c2869def7`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/6a09c180cc20d34b1d41c7d07816cb5c2869def7) ([#316](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/316)): feat(android): add `isLocationEnabled()` method

## 7.6.0

### Minor Changes

- [`5928660c975ada503e1db633378cf6da1ebd1e29`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/5928660c975ada503e1db633378cf6da1ebd1e29) ([#306](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/306)): feat: add default values for `StartForegroundServiceOptions`

- [`09ed4a9cd4dd0586e618df767c3729fd52994bfd`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/09ed4a9cd4dd0586e618df767c3729fd52994bfd) ([#302](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/302)): feat(android): add more headless task methods

- [`673f6c52d92aba22500fb034df4a2e73c3469c05`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/673f6c52d92aba22500fb034df4a2e73c3469c05) ([#304](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/304)): feat(ios): add `autoReconnect` option

## 7.5.0

### Minor Changes

- [`7b9593769844335769ccddcbf3205677facd33b0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/7b9593769844335769ccddcbf3205677facd33b0) ([#275](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/275)): feat: automatically initialize the plugin on first call

- [`1de0c493350fa179cf2e37cfc19a062d6aca852a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/1de0c493350fa179cf2e37cfc19a062d6aca852a) ([#256](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/256)): feat(android): add `manufacturerData` option

## 7.4.0

### Minor Changes

- [`ea415660204bc01eea67b502c1584886223bb948`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ea415660204bc01eea67b502c1584886223bb948) ([#171](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/171)): feat(android): add `onConnectionStateChange` method to headless task

## 7.3.0

### Minor Changes

- [`4972c2f85d305836804eea23f2342e7bbd287aa5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/4972c2f85d305836804eea23f2342e7bbd287aa5) ([#194](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/194)): feat(android): automatically apply certain permission elements in app manifest

- [`f65a481aeed8d8235f75cc381aea5ef65d5b8da3`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f65a481aeed8d8235f75cc381aea5ef65d5b8da3) ([#193](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/193)): feat: implement `isAvailable` method

### Patch Changes

- [`e304408abd4dba89d111475794483bea2ab68c62`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/e304408abd4dba89d111475794483bea2ab68c62) ([#167](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/167)): fix(ios): reject plugin call if privacy descriptions are missing

## 7.2.1

### Patch Changes

- [`d827b67678b042f551b39a820bfd4d785f8f49bb`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d827b67678b042f551b39a820bfd4d785f8f49bb) ([#155](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/155)): fix(android): `Context.startForegroundService() did not then call Service.startForeground()` error

- [`8ccb9d8429aded24b5df83cc1b9cc52c89106bab`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/8ccb9d8429aded24b5df83cc1b9cc52c89106bab) ([#162](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/162)): fix(android): create the notification channel if it does not exist

## 7.2.0

### Minor Changes

- [`ed5409bb636bd5fde9cd601546202a2c79385596`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ed5409bb636bd5fde9cd601546202a2c79385596) ([#109](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/109)): feat: support peripheral role

## 7.1.0

### Minor Changes

- [`cb22e50c95adfedbd5b5891b58c3ca40c03a0560`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/cb22e50c95adfedbd5b5891b58c3ca40c03a0560) ([#85](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/85)): feat(android): add `autoConnect` option

### Patch Changes

- [`cb22e50c95adfedbd5b5891b58c3ca40c03a0560`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/cb22e50c95adfedbd5b5891b58c3ca40c03a0560) ([#85](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/85)): fix: `deviceId` in `deviceDisconnected` event was undefined

## 7.0.6

### Patch Changes

- [`002e1a739e77ad166bf7b35e0a1d75d0bc470bd0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/002e1a739e77ad166bf7b35e0a1d75d0bc470bd0) ([#80](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/80)): fix(android): `startCharacteristicNotifications(...)` and `stopCharacteristicNotifications(...)` calls were resolved too early

- [`dde8eeae462ed32aa4577738b5919209ce873386`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/dde8eeae462ed32aa4577738b5919209ce873386): fix(android): add missing null check

- [`9992144e3596121b63467087261d5f973225e0c3`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9992144e3596121b63467087261d5f973225e0c3): fix(android): `writeCharacteristic(...)` and `writeDescriptor(...)` calls were not resolved

- [`b931617713c99a4b1e580ed7d551af6e998a1055`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/b931617713c99a4b1e580ed7d551af6e998a1055) ([#81](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/81)): fix(ios): handle invalid UUIDs

## 7.0.5

### Patch Changes

- [`a6a52740acfbf438e965429772386ace6ceda714`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/a6a52740acfbf438e965429772386ace6ceda714): fix(android): `type` in `WriteCharacteristicOptions` was ignored

## 7.0.4

### Patch Changes

- [`7f16adf59c9d34b57b63cd9d87fa646796bc7794`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/7f16adf59c9d34b57b63cd9d87fa646796bc7794) ([#72](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/72)): fix(android): support older Android devices

## 7.0.3

### Patch Changes

- [`937c8628923097ceee3808790142d1e5b255f3d0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/937c8628923097ceee3808790142d1e5b255f3d0): fix(android): remove error logs for non-configured headless task

- [`3bfb4cb48d98d7e07c8561cd9e591000df09830b`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/3bfb4cb48d98d7e07c8561cd9e591000df09830b): fix: request permissions options were ignored

## 7.0.2

### Patch Changes

- [`adc86aadec4661aef43a1a2dfed41690c6ad8d98`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/adc86aadec4661aef43a1a2dfed41690c6ad8d98): fix(android): create default notification channel if necessary

## 7.0.1

### Patch Changes

- [`9c89d10bccaf946a1a19e859e642c6c1d2eb09bf`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9c89d10bccaf946a1a19e859e642c6c1d2eb09bf) ([#56](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/56)): fix: set correct target paths for SPM support

## 7.0.0

### Major Changes

- [`41f632a3dd08dd520724b0266971b63928f034f0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/41f632a3dd08dd520724b0266971b63928f034f0) ([#53](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/53)): feat: update to Capacitor 7

### Minor Changes

- [`071c0357b8e944aa6487b963eea7ab325db88b95`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/071c0357b8e944aa6487b963eea7ab325db88b95) ([#49](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/49)): feat: add SPM support

## 6.2.0

### Minor Changes

- [`196eb92`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/196eb92e6a34cddc7b4d83f42a00f01d37c3a473): docs: update `LICENSE`

## 6.1.0

### Minor Changes

- [`d6d8397`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d6d83973e0c177a87fb21f11a95be1b2b5b77c68) ([#6](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/6)): feat: add `type` property to `WriteCharacteristicOptions`

## 6.0.1

### Patch Changes

- [`d0bec32`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d0bec32eb6f4df79b19937103aca7be2118cc657) ([#8](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/8)): fix(android): updat Gradle plugin to 8.2.1

* [`eb05515`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/eb055152cfe1d035bf4803092542dc6330bebb94) ([#7](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/7)): fix(android): update `compileSdkVersion` and `targetSdkVersion` to 34

All notable changes to this project will be documented in this file. See [commit-and-tag-version](https://github.com/absolute-version/commit-and-tag-version) for commit guidelines.
