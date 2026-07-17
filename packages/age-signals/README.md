# Capacitor Age Signals Plugin

Capacitor plugin to use the [Play Age Signals API](https://developer.android.com/google/play/age-signals/overview) (Android) and [DeclaredAgeRange](https://developer.apple.com/documentation/declaredagerange/) (iOS) to request age signals about the user.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Age Signals plugin is one of the most complete age assurance solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🔍 **Age Verification**: Request user age signals using Play Age Signals API (Android) and DeclaredAgeRange (iOS).
- 👨‍👩‍👧‍👦 **Parental Controls**: Support for supervised accounts with parental approval status.
- 🧪 **Testing Support**: Built-in `FakeAgeSignalsManager` integration for testing different age verification scenarios (Android).
- 🌍 **Compliance Ready**: Built for US state age verification requirements (effective January 1, 2026).
- 🤝 **Compatibility**: Works alongside the [App Integrity](https://capawesome.io/docs/sdks/capacitor/app-integrity/) and [Root Detection](https://capawesome.io/docs/sdks/capacitor/root-detection/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Age Signals plugin is typically used to comply with age verification requirements and to tailor the app experience to the user's age, for example:

- **Age-gated content**: Restrict access to mature or sensitive content based on the user's verification status and age range.
- **Regulatory compliance**: Meet US state age verification requirements by requesting age signals from Google Play and Apple's DeclaredAgeRange API.
- **Parental supervision**: Detect supervised accounts and react to pending or denied parental approvals.
- **Regional eligibility checks**: Use `checkEligibility()` on iOS to determine whether the user is in a region with additional age-related obligations.
- **Automated testing**: Simulate different age verification scenarios in your Android tests using the built-in fake manager.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.3.x          | >=8.x.x           | Active support |
| 0.2.x          | 7.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-age-signals` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-age-signals
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$androidPlayAgeSignalsVersion` version of `com.google.android.play:age-signals` (default: `0.0.3`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

**Note**: The `FakeAgeSignalsManager` testing API is included in the main `age-signals` library, so no additional dependency is required for testing.

### iOS

#### Entitlements

To use the DeclaredAgeRange API, you must enable the `com.apple.developer.declared-age-range` entitlement in your app's entitlements file by adding the following key:

```xml
<key>com.apple.developer.declared-age-range</key>
<true/>
```

Check out the [Apple documentation](https://developer.apple.com/documentation/bundleresources/entitlements/com.apple.developer.contacts.notes) for more information.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to request the user's age signals and check eligibility for age-gated features.

### Request the user's age signals

Call `checkAgeSignals(...)` to request the user's age signals. The result contains the user's verification status and, for supervised users, the lower and upper bounds of their age range:

```typescript
import { AgeSignals } from '@capawesome/capacitor-age-signals';

const checkAgeSignals = async () => {
  const result = await AgeSignals.checkAgeSignals();
  console.log('User Status:', result.userStatus);
  console.log('Age Lower:', result.ageLower);
  console.log('Age Upper:', result.ageUpper);
};
```

### Check eligibility for age-gated features

Check whether the user is in an applicable region that requires additional age-related obligations. Only available on iOS:

```typescript
import { AgeSignals } from '@capawesome/capacitor-age-signals';

const checkEligibility = async () => {
  const result = await AgeSignals.checkEligibility();
  console.log('Is Eligible:', result.isEligible);
};
```

## Testing

The plugin includes support for the `FakeAgeSignalsManager` API on Android, which allows you to simulate different age signals scenarios in your tests without requiring live responses from Google Play.

### Android Testing

**Important**: Due to a known issue in versions 0.0.1 and 0.0.2 of the Age Signals API, you may encounter a `java.lang.VerifyError` when calling the builder method of `AgeSignalsResult` in unit tests. As a workaround, run your tests as Android instrumented tests within the `androidTest` source set.

#### Example: Testing a Verified Adult User

```typescript
import { AgeSignals, UserStatus } from '@capawesome/capacitor-age-signals';

// Enable the fake manager
await AgeSignals.setUseFakeManager({ useFake: true });

// Set up a verified adult user
await AgeSignals.setNextAgeSignalsResult({
  userStatus: UserStatus.Verified,
});

// Check age signals - will return the fake result
const result = await AgeSignals.checkAgeSignals();
console.log(result.userStatus); // 'VERIFIED'
```

#### Example: Testing a Supervised User (13-17 years old)

```typescript
import { AgeSignals, UserStatus } from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

await AgeSignals.setNextAgeSignalsResult({
  userStatus: UserStatus.Supervised,
  ageLower: 13,
  ageUpper: 17,
  installId: 'fake_install_id',
});

const result = await AgeSignals.checkAgeSignals();
console.log(result.userStatus); // 'SUPERVISED'
console.log(result.ageLower); // 13
console.log(result.ageUpper); // 17
console.log(result.installId); // 'fake_install_id'
```

#### Example: Testing Parental Approval Scenarios

```typescript
import { AgeSignals, UserStatus } from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

// Test pending approval
await AgeSignals.setNextAgeSignalsResult({
  userStatus: UserStatus.SupervisedApprovalPending,
  ageLower: 13,
  ageUpper: 17,
  mostRecentApprovalDate: '2025-02-01',
  installId: 'fake_install_id',
});

const result = await AgeSignals.checkAgeSignals();
console.log(result.userStatus); // 'SUPERVISED_APPROVAL_PENDING'
console.log(result.mostRecentApprovalDate); // '2025-02-01'
```

#### Example: Testing Error Scenarios

```typescript
import { AgeSignals, ErrorCode } from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

// Simulate a network error
await AgeSignals.setNextAgeSignalsException({
  errorCode: ErrorCode.NetworkError,
});

try {
  await AgeSignals.checkAgeSignals();
} catch (error) {
  console.log('Caught network error:', error);
}
```

#### Disabling the Fake Manager

```typescript
import { AgeSignals } from '@capawesome/capacitor-age-signals';

// Switch back to the production manager
await AgeSignals.setUseFakeManager({ useFake: false });

// This will now use the real Age Signals API
const result = await AgeSignals.checkAgeSignals();
```

## API

<docgen-index>

* [`checkAgeSignals(...)`](#checkagesignals)
* [`checkEligibility()`](#checkeligibility)
* [`setUseFakeManager(...)`](#setusefakemanager)
* [`setNextAgeSignalsResult(...)`](#setnextagesignalsresult)
* [`setNextAgeSignalsException(...)`](#setnextagesignalsexception)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkAgeSignals(...)

```typescript
checkAgeSignals(options?: CheckAgeSignalsOptions | undefined) => Promise<CheckAgeSignalsResult>
```

Request the user's age signals.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#checkagesignalsoptions">CheckAgeSignalsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#checkagesignalsresult">CheckAgeSignalsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### checkEligibility()

```typescript
checkEligibility() => Promise<CheckEligibilityResult>
```

Check if the user is eligible for age-gated features.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#checkeligibilityresult">CheckEligibilityResult</a>&gt;</code>

**Since:** 0.3.1

--------------------


### setUseFakeManager(...)

```typescript
setUseFakeManager(options: SetUseFakeManagerOptions) => Promise<void>
```

Enable or disable the fake age signals manager for testing.

Only available on Android.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setusefakemanageroptions">SetUseFakeManagerOptions</a></code> |

**Since:** 0.3.1

--------------------


### setNextAgeSignalsResult(...)

```typescript
setNextAgeSignalsResult(options: SetNextAgeSignalsResultOptions) => Promise<void>
```

Set the next age signals result to be returned by the fake manager.

Only available on Android.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextagesignalsresultoptions">SetNextAgeSignalsResultOptions</a></code> |

**Since:** 0.3.1

--------------------


### setNextAgeSignalsException(...)

```typescript
setNextAgeSignalsException(options: SetNextAgeSignalsExceptionOptions) => Promise<void>
```

Set the next exception to be thrown by the fake manager.

Only available on Android.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextagesignalsexceptionoptions">SetNextAgeSignalsExceptionOptions</a></code> |

**Since:** 0.3.1

--------------------


### Interfaces


#### CheckAgeSignalsResult

| Prop                         | Type                                                                | Description                                                                                                                                                                                                                                                                                                       | Since |
| ---------------------------- | ------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`userStatus`**             | <code><a href="#userstatus">UserStatus</a></code>                   | The user's verification status.                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`ageLower`**               | <code>number</code>                                                 | The (inclusive) lower bound of a supervised user's age range. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`.                                                                                                                                   | 0.0.1 |
| **`ageUpper`**               | <code>number</code>                                                 | The (inclusive) upper bound of a supervised user's age range. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED` and the user's age is under 18.                                                                                                    | 0.0.1 |
| **`mostRecentApprovalDate`** | <code>string</code>                                                 | The effective from date of the most recent significant change that was approved. When an app is installed, the date of the most recent significant change prior to install is used. Only available when `userStatus` is `SUPERVISED_APPROVAL_PENDING` or `SUPERVISED_APPROVAL_DENIED`. Only available on Android. | 0.0.1 |
| **`installId`**              | <code>string</code>                                                 | An ID assigned to supervised user installs by Google Play, used for the purposes of notifying you of revoked app approval. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`. Only available on Android.                                           | 0.0.1 |
| **`ageRangeDeclaration`**    | <code><a href="#agerangedeclaration">AgeRangeDeclaration</a></code> | The age range declaration type. Only available on iOS (26.2+).                                                                                                                                                                                                                                                    | 0.4.0 |


#### CheckAgeSignalsOptions

| Prop           | Type                  | Description                                                                                                                    | Default                   | Since |
| -------------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------------ | ------------------------- | ----- |
| **`ageGates`** | <code>number[]</code> | The age ranges that the user falls into. The provided array must contain at least 2 and at most 3 ages. Only available on iOS. | <code>[13, 15, 18]</code> | 0.0.2 |


#### CheckEligibilityResult

| Prop             | Type                 | Description                                                                                                                                                                                   | Since |
| ---------------- | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`isEligible`** | <code>boolean</code> | Whether the user is eligible for age-gated features. Returns `true` if the user is in an applicable region that requires additional age-related obligations. Always returns `false` on macOS. | 0.3.1 |


#### SetUseFakeManagerOptions

| Prop          | Type                 | Description                                              | Default            | Since |
| ------------- | -------------------- | -------------------------------------------------------- | ------------------ | ----- |
| **`useFake`** | <code>boolean</code> | Whether to use the fake age signals manager for testing. | <code>false</code> | 0.3.1 |


#### SetNextAgeSignalsResultOptions

| Prop                         | Type                                              | Description                                                                                                                                                                                                                                                                            | Since |
| ---------------------------- | ------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`userStatus`**             | <code><a href="#userstatus">UserStatus</a></code> | The user's verification status.                                                                                                                                                                                                                                                        | 0.3.1 |
| **`ageLower`**               | <code>number</code>                               | The (inclusive) lower bound of a supervised user's age range. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`.                                                                                                        | 0.3.1 |
| **`ageUpper`**               | <code>number</code>                               | The (inclusive) upper bound of a supervised user's age range. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED` and the user's age is under 18.                                                                         | 0.3.1 |
| **`mostRecentApprovalDate`** | <code>string</code>                               | The effective from date of the most recent significant change that was approved. When an app is installed, the date of the most recent significant change prior to install is used. Only available when `userStatus` is `SUPERVISED_APPROVAL_PENDING` or `SUPERVISED_APPROVAL_DENIED`. | 0.3.1 |
| **`installId`**              | <code>string</code>                               | An ID assigned to supervised user installs by Google Play, used for the purposes of notifying you of revoked app approval. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`.                                           | 0.3.1 |


#### SetNextAgeSignalsExceptionOptions

| Prop            | Type                                            | Description                                      | Since |
| --------------- | ----------------------------------------------- | ------------------------------------------------ | ----- |
| **`errorCode`** | <code><a href="#errorcode">ErrorCode</a></code> | The error code to be thrown by the fake manager. | 0.3.1 |


### Enums


#### UserStatus

| Members                         | Value                                      | Description                                                                                                                                                                                                                                                                                    | Since |
| ------------------------------- | ------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Verified`**                  | <code>'VERIFIED'</code>                    | The user is over 18. Google verified the user's age using a commercially reasonable method such as a government-issued ID, credit card, or facial age estimation.                                                                                                                              | 0.0.1 |
| **`Supervised`**                | <code>'SUPERVISED'</code>                  | The user has a supervised Google Account managed by a parent who sets their age. Use `ageLower` and `ageUpper` to determine the user's age range.                                                                                                                                              | 0.0.1 |
| **`SupervisedApprovalPending`** | <code>'SUPERVISED_APPROVAL_PENDING'</code> | The user has a supervised Google Account, and their supervising parent has not yet approved one or more pending significant changes. Use `ageLower` and `ageUpper` to determine the user's age range. Use `mostRecentApprovalDate` to determine the last significant change that was approved. | 0.0.1 |
| **`SupervisedApprovalDenied`**  | <code>'SUPERVISED_APPROVAL_DENIED'</code>  | The user has a supervised Google Account, and their supervising parent denied approval for one or more significant changes. Use `ageLower` and `ageUpper` to determine the user's age range. Use `mostRecentApprovalDate` to determine the last significant change that was approved.          | 0.0.1 |
| **`Unknown`**                   | <code>'UNKNOWN'</code>                     | The user's age is unknown and the user is in an applicable jurisdiction or region. To obtain an age signal from Google Play, ask the user to visit the Play Store to resolve their status.                                                                                                     | 0.0.1 |
| **`Declared`**                  | <code>'DECLARED'</code>                    | The user has self-declared or guardian-declared their age. Only available on Android.                                                                                                                                                                                                          | 0.4.0 |
| **`Empty`**                     | <code>'EMPTY'</code>                       | The user is either not in an applicable jurisdiction or region, or the user does not share their age with apps.                                                                                                                                                                                | 0.0.1 |


#### AgeRangeDeclaration

| Members                | Value                            | Description                                                                            | Since |
| ---------------------- | -------------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`SelfDeclared`**     | <code>'SELF_DECLARED'</code>     | The age range was self-declared by the user without external verification.             | 0.4.0 |
| **`GuardianDeclared`** | <code>'GUARDIAN_DECLARED'</code> | The age range was declared by a guardian without external verification.                | 0.4.0 |
| **`Confirmed`**        | <code>'CONFIRMED'</code>         | The age range was set using a scrutinized method, like a credit card or government ID. | 0.4.0 |


#### ErrorCode

| Members                           | Value                                         | Description                                                                                                                                                      | Since |
| --------------------------------- | --------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ApiNotAvailable`**             | <code>'API_NOT_AVAILABLE'</code>              | The Play Age Signals API is not available. The Play Store app version installed on the device might be old.                                                      | 0.0.1 |
| **`PlayStoreNotFound`**           | <code>'PLAY_STORE_NOT_FOUND'</code>           | No Play Store app is found on the device.                                                                                                                        | 0.0.1 |
| **`NetworkError`**                | <code>'NETWORK_ERROR'</code>                  | No available network is found.                                                                                                                                   | 0.0.1 |
| **`PlayServicesNotFound`**        | <code>'PLAY_SERVICES_NOT_FOUND'</code>        | Play Services is not available or its version is too old.                                                                                                        | 0.0.1 |
| **`CannotBindToService`**         | <code>'CANNOT_BIND_TO_SERVICE'</code>         | Binding to the service in the Play Store has failed. This can be due to having an old Play Store version installed on the device or device memory is overloaded. | 0.0.1 |
| **`PlayStoreVersionOutdated`**    | <code>'PLAY_STORE_VERSION_OUTDATED'</code>    | The Play Store app needs to be updated.                                                                                                                          | 0.0.1 |
| **`PlayServicesVersionOutdated`** | <code>'PLAY_SERVICES_VERSION_OUTDATED'</code> | Play Services needs to be updated.                                                                                                                               | 0.0.1 |
| **`ClientTransientError`**        | <code>'CLIENT_TRANSIENT_ERROR'</code>         | There was a transient error in the client device.                                                                                                                | 0.0.1 |
| **`AppNotOwned`**                 | <code>'APP_NOT_OWNED'</code>                  | The app was not installed by Google Play.                                                                                                                        | 0.0.1 |
| **`InternalError`**               | <code>'INTERNAL_ERROR'</code>                 | Unknown internal error.                                                                                                                                          | 0.0.1 |
| **`SdkVersionOutdated`**          | <code>'SDK_VERSION_OUTDATED'</code>           | The Age Signals SDK version is outdated. Only available on Android.                                                                                              | 0.4.0 |

</docgen-api>

## FAQ

### Which age verification APIs does this plugin use?

On Android, the plugin uses the [Play Age Signals API](https://developer.android.com/google/play/age-signals/overview) to request age signals from Google Play. On iOS, it uses Apple's [DeclaredAgeRange](https://developer.apple.com/documentation/declaredagerange/) framework, which requires the `com.apple.developer.declared-age-range` entitlement (see [Installation](#installation)).

### What do the different user statuses mean?

The `VERIFIED` status means Google verified that the user is over 18 using a method such as a government-issued ID, credit card, or facial age estimation. The `SUPERVISED` statuses indicate a supervised Google Account managed by a parent; use `ageLower` and `ageUpper` to determine the user's age range. The `UNKNOWN` status means the user is in an applicable region but their age could not be determined, and `EMPTY` means the user is either not in an applicable region or does not share their age with apps.

### How can I test different age verification scenarios?

On Android, the plugin integrates the `FakeAgeSignalsManager` API, which lets you simulate age signals without live responses from Google Play. Enable it with `setUseFakeManager(...)` and set the next result or exception with `setNextAgeSignalsResult(...)` and `setNextAgeSignalsException(...)`. See the [Testing](#testing) section for complete examples.

### Why do I get a VerifyError in my Android unit tests?

Due to a known issue in versions 0.0.1 and 0.0.2 of the Age Signals API, calling the builder method of `AgeSignalsResult` in unit tests may throw a `java.lang.VerifyError`. As a workaround, run your tests as Android instrumented tests within the `androidTest` source set.

### Why does the plugin reject with an error like API_NOT_AVAILABLE or APP_NOT_OWNED?

The Play Age Signals API requires an up-to-date Play Store app and Play Services on the device, so errors like `API_NOT_AVAILABLE`, `PLAY_STORE_VERSION_OUTDATED`, or `PLAY_SERVICES_NOT_FOUND` usually indicate outdated or missing Google Play components. The `APP_NOT_OWNED` error means the app was not installed by Google Play. Check the `ErrorCode` enum in the [API](#api) section for the full list of error codes.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Integrity](https://capawesome.io/docs/sdks/capacitor/app-integrity/): Verify app and device integrity using the Play Integrity API and App Attest.
- [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/): Request user authorization via Apple's App Tracking Transparency framework.
- [Root Detection](https://capawesome.io/docs/sdks/capacitor/root-detection/): Detect rooted and jailbroken devices.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/age-signals/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/age-signals/LICENSE).
