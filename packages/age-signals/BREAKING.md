# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.5.x](#version-05x)
- [Version 0.4.x](#version-04x)
- [Version 0.3.x](#version-03x)

## Version 0.5.x

### Play Age Signals 0.0.4

On Android, this plugin now uses version `0.0.4` of the Play Age Signals library. Google has removed the `userStatus` field from the API and split it into two independent fields, and has introduced a separate step to request access to the age signals. The API of this plugin has been redesigned accordingly.

### Methods

- The `checkAgeSignals(...)` method has been renamed to `requestAgeRange(...)`. On Android, it now requests access to the age signals first and only reads the age range if the user has shared it.
- The `checkEligibility()` method has been replaced by the `getRegulatoryRequirements()` method. The `isEligible` property has been renamed to `ageAssuranceRequired` and the result contains the new `regulatoryFeatures` property.
- The new `getAgeRange()` method reads the age range without showing a prompt. Use it to poll for changes.
- The new `isAvailable()` method checks whether age signals are available on the device.
- The new `showSignificantUpdateAcknowledgment(...)` method shows the system interface to acknowledge a significant change of the app.
- The new `setNextAgeSignalsAccessResult(...)` and `setNextRequestAgeSignalsAccessException(...)` methods configure the fake manager for the access request.
- The `setUseFakeManager(...)` method now rejects with `FAKE_MANAGER_NOT_ALLOWED` if the app is not debuggable. The fake manager allows age signals to be forged, so it must not be reachable in a release build.

### Results

- The `ageLower` and `ageUpper` properties have been renamed to `lowerBound` and `upperBound` and have been moved into the new `ageRange` property.
- The `ageRangeDeclaration` property has been moved into the new `ageRange` property.
- The `mostRecentApprovalDate` property has been renamed to `approvalDate` and has been moved into the new `significantChange` property. The value is now formatted as an ISO 8601 string.
- The `userStatus` property has been removed. Use the new `status` property to determine whether the user has shared their age range, the new `ageRange.ageRangeSource` property to determine how the age range was established, and the new `significantChange.status` property to determine the parental approval state.

### Enums

- The `UserStatus` enum has been removed. Use the new `AgeRangeStatus`, `AgeRangeSource` and `SignificantChangeStatus` enums instead.
- The `AgeRangeDeclaration` enum has six new values. Make sure to handle them.
- The `ErrorCode` enum has the new values `FAKE_MANAGER_NOT_ALLOWED`, `FAKE_MANAGER_NOT_ENABLED`, `INVALID_REQUEST`, `NOT_SUPPORTED` and `PRESENTATION_CONTEXT_UNAVAILABLE`.

### Options

- The `ageGates` array may now contain 1 to 3 ages. It previously had to contain 2 to 3 ages.

### Error handling

On Android, the error codes returned by the Play Age Signals library were never mapped, so every error was rejected with the raw message of the library. This has been fixed. Make sure your error handling does not rely on the previous messages.

### Variables

- On Android, the `androidPlayAgeSignalsVersion` variable has been updated to `0.0.4`.

## Version 0.4.x

### Variables

- On Android, the `androidPlayAgeSignalsVersion` variable has been updated to `0.0.3`.

## Version 0.3.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `androidPlayAgeSignalsVersion` variable has been updated to `0.0.2`.
