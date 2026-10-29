# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### Milliseconds

All times and durations are now in milliseconds. `HapticEvent.time`, `HapticEvent.duration` and `HapticParameterCurveControlPoint.time` were in seconds before. Multiply existing values by `1000`.

### `performAndroidHapticFeedback(...)`

`performAndroidHaptic(...)` has been renamed to `performAndroidHapticFeedback(...)`, `PerformAndroidHapticOptions` to `PerformAndroidHapticFeedbackOptions` and `AndroidHapticType` to `AndroidHapticFeedbackType`.

### Error codes

The `PATTERN_PLAYBACK_FAILED` error code has been renamed to `PLAYBACK_FAILED`.
