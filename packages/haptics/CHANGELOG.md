# Changelog

## 0.2.0

### Minor Changes

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): feat!: use milliseconds for all times and durations of `playPattern(...)`

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): feat!: rename `performAndroidHaptic(...)` to `performAndroidHapticFeedback(...)` and `AndroidHapticType` to `AndroidHapticFeedbackType`

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): feat!: rename the `PATTERN_PLAYBACK_FAILED` error code to `PLAYBACK_FAILED`

### Patch Changes

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): fix(android): play `playPattern(...)` events at their requested times when played as a composition, which now requires Android 12+

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): feat(android): add the `playAndroidComposition(...)` method

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): fix(android): play haptic feedback with the touch vibration usage so that the system touch feedback setting is respected

- [`a60b200e78c2eb6c8fb03a42650618bc81a01a7b`](https://github.com/capawesome-team/capacitor-plugins/commit/a60b200e78c2eb6c8fb03a42650618bc81a01a7b) ([#1185](https://github.com/capawesome-team/capacitor-plugins/pull/1185)): feat(ios): add the `parameterCurves` option to the `PlayPatternOptions` interface

## 0.1.2

### Patch Changes

- [`f114a9e6390697418b0ffd11b8db1db2d307b2dd`](https://github.com/capawesome-team/capacitor-plugins/commit/f114a9e6390697418b0ffd11b8db1db2d307b2dd) ([#1023](https://github.com/capawesome-team/capacitor-plugins/pull/1023)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.1.1

### Patch Changes

- [`6b4f706e965d85274c97506336ab683fd449ff23`](https://github.com/capawesome-team/capacitor-plugins/commit/6b4f706e965d85274c97506336ab683fd449ff23) ([#924](https://github.com/capawesome-team/capacitor-plugins/pull/924)): fix: use `proguard-android-optimize.txt` for AGP 9.3.0 and R8 compatibility

## 0.1.0

### Minor Changes

- [`004f07eafa6de08bebe650fe4ff4d63cd3a06cf7`](https://github.com/capawesome-team/capacitor-plugins/commit/004f07eafa6de08bebe650fe4ff4d63cd3a06cf7) ([#913](https://github.com/capawesome-team/capacitor-plugins/pull/913)): Initial release 🎉
