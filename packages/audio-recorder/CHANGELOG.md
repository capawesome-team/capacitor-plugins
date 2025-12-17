# Changelog

## 8.0.0

### Major Changes

- [`4438ee93102f40e80b8466e047c424239f066a3a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/4438ee93102f40e80b8466e047c424239f066a3a) ([#338](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/338)): refactor(ios)!: set default value of `audioSessionMode` in the `StartRecordingOptions` interface to `AudioSessionMode.Default`

- [`9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d) ([#353](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/353)): feat!: update to Capacitor 8 (see `BREAKING.md`)

- [`968064d3ca529d7094aecd28ed56ccfcd1e7ee85`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/968064d3ca529d7094aecd28ed56ccfcd1e7ee85): fix!: throw correct error code (see `BREAKING.md`)

### Patch Changes

- [`d402dd89338610c8c6decd72473ac09aa2a6010a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d402dd89338610c8c6decd72473ac09aa2a6010a) ([#339](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/339)): fix(android): stop recording on destroy

## 7.5.0

### Minor Changes

- [`0f7284f63e343cd5390216603d6fdd2968cd533d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0f7284f63e343cd5390216603d6fdd2968cd533d) ([#270](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/270)): feat(ios): add `audioSessionCategoryOptions` to `StartRecordingOptions`

## 7.4.0

### Minor Changes

- [`eeb5e5d13dbebb5b26d8964eb215b413b2eedb84`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/eeb5e5d13dbebb5b26d8964eb215b413b2eedb84) ([#243](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/243)): feat(ios): add `audioSessionMode` property

## 7.3.2

### Patch Changes

- [`cd337f11490c4463ed77a93a0abd7c92a0a776d7`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/cd337f11490c4463ed77a93a0abd7c92a0a776d7): fix: `options` parameter in `startRecording` method is now optional

## 7.3.1

### Patch Changes

- [`a91c9b18d1e4dbb54b79bc70de1ae652864442a0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/a91c9b18d1e4dbb54b79bc70de1ae652864442a0): fix: `options` parameter in `startRecording` method is now optional

## 7.3.0

### Minor Changes

- [`4972c2f85d305836804eea23f2342e7bbd287aa5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/4972c2f85d305836804eea23f2342e7bbd287aa5) ([#194](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/194)): feat(android): automatically apply certain permission elements in app manifest

### Patch Changes

- [`e304408abd4dba89d111475794483bea2ab68c62`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/e304408abd4dba89d111475794483bea2ab68c62) ([#167](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/167)): fix(ios): reject plugin call if privacy descriptions are missing

- [`a14dddad070e929c5700ccef3a3015c70d775d66`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/a14dddad070e929c5700ccef3a3015c70d775d66) ([#196](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/196)): fix(ios): gracefully handle audio session activation errors

## 7.2.0

### Minor Changes

- [`85e73689fa22d15e04c242dc512801b22a4465b0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/85e73689fa22d15e04c242dc512801b22a4465b0) ([#175](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/175)): feat: add `bitRate` option to `StartRecordingOptions`

## 7.1.0

### Minor Changes

- [`b89118918c27c291e6ffee8165c79c2ed702ed73`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/b89118918c27c291e6ffee8165c79c2ed702ed73) ([#140](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/140)): feat: add `duration` property to `StopRecordingResult` and `RecordingStoppedEvent`

- [`da40ee0a556db922e42835fc9c9b8a60f2abf477`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/da40ee0a556db922e42835fc9c9b8a60f2abf477) ([#138](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/138)): feat: add sample rate option

- [`0ec9dc296108ff523ebbb1129fa4adbc8ef789bb`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0ec9dc296108ff523ebbb1129fa4adbc8ef789bb) ([#142](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/142)): feat: add `recordingPaused` event listener

## 7.0.0

### Major Changes

- [`a668f8abb74257540cd4d20dd5680cab3c977df7`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/a668f8abb74257540cd4d20dd5680cab3c977df7) ([#104](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/104)): Initial release 🎉
