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
- **Regional eligibility checks**: Use `getRegulatoryRequirements()` on iOS to determine whether the user is in a region with additional age-related obligations.
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

- `$androidPlayAgeSignalsVersion` version of `com.google.android.play:age-signals` (default: `0.0.4`)

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

The following examples show how to request the user's age range and how to react to the regulatory requirements that apply to the user.

### Request the user's age range

Call `requestAgeRange(...)` to ask the user to share their age range. The status tells you whether the age range was shared, and the `ageRange` property contains the bounds:

```typescript
import { AgeRangeStatus, AgeSignals } from '@capawesome/capacitor-age-signals';

const requestAgeRange = async () => {
  const result = await AgeSignals.requestAgeRange();
  if (result.status !== AgeRangeStatus.Shared) {
    console.log('The user has not shared their age range:', result.status);
    return;
  }
  console.log('Lower Bound:', result.ageRange?.lowerBound);
  console.log('Upper Bound:', result.ageRange?.upperBound);
};
```

On Android, a status of `VERIFICATION_REQUIRED` means the user must resolve their status in the Google Play Store before an age range can be shared.

### Read the age range without prompting

Call `getAgeRange()` to read the age range again without showing a prompt. This is useful to poll for a `significantChange` status that moves from `PENDING` to `APPROVED`. Only available on Android:

```typescript
import { AgeSignals } from '@capawesome/capacitor-age-signals';

const getAgeRange = async () => {
  const result = await AgeSignals.getAgeRange();
  console.log('Significant Change:', result.significantChange?.status);
};
```

### Check the regulatory requirements

Check whether the user is in a region and account state that requires age assurance, and which regulatory features your app must support. Only available on iOS:

```typescript
import {
  AgeSignals,
  RegulatoryFeature,
} from '@capawesome/capacitor-age-signals';

const getRegulatoryRequirements = async () => {
  const result = await AgeSignals.getRegulatoryRequirements();
  if (
    result.regulatoryFeatures.includes(
      RegulatoryFeature.SignificantAppChangeRequiresAdultNotification,
    )
  ) {
    await AgeSignals.showSignificantUpdateAcknowledgment({
      updateDescription: 'This app now supports direct messages between users.',
    });
  }
};
```

## API

<docgen-index>

* [`getAgeRange()`](#getagerange)
* [`getRegulatoryRequirements()`](#getregulatoryrequirements)
* [`isAvailable()`](#isavailable)
* [`requestAgeRange(...)`](#requestagerange)
* [`setNextAgeSignalsAccessResult(...)`](#setnextagesignalsaccessresult)
* [`setNextAgeSignalsException(...)`](#setnextagesignalsexception)
* [`setNextAgeSignalsResult(...)`](#setnextagesignalsresult)
* [`setNextRequestAgeSignalsAccessException(...)`](#setnextrequestagesignalsaccessexception)
* [`setUseFakeManager(...)`](#setusefakemanager)
* [`showSignificantUpdateAcknowledgment(...)`](#showsignificantupdateacknowledgment)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAgeRange()

```typescript
getAgeRange() => Promise<GetAgeRangeResult>
```

Get the age range that the user has already shared with the app.

This method never shows a system prompt. Call `requestAgeRange(...)` first
and only call this method if the returned status was `SHARED`.

Use this method to poll for changes (for example a `significantChange`
status that moves from `PENDING` to `APPROVED`) without prompting the user
again.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getagerangeresult">GetAgeRangeResult</a>&gt;</code>

**Since:** 0.5.0

--------------------


### getRegulatoryRequirements()

```typescript
getRegulatoryRequirements() => Promise<GetRegulatoryRequirementsResult>
```

Get the regulatory requirements that apply to the current user.

Call this method before starting any age assurance flow to find out
whether the user is in a region and account state where age assurance
applies at all.

Only available on iOS (26.2+).

**Returns:** <code>Promise&lt;<a href="#getregulatoryrequirementsresult">GetRegulatoryRequirementsResult</a>&gt;</code>

**Since:** 0.5.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether age signals are available on this device.

On **Android**, this checks whether the Google Play Store is installed.
On **iOS**, this checks whether the device runs iOS 26.0 or later.

A result of `true` does not guarantee that an age range can be retrieved.
Always handle the errors of the other methods as well.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.5.0

--------------------


### requestAgeRange(...)

```typescript
requestAgeRange(options?: RequestAgeRangeOptions | undefined) => Promise<RequestAgeRangeResult>
```

Ask the user to share their age range with the app.

This method may show a system prompt. The system caches the decision of
the user, so subsequent calls may resolve without any user interaction.

On **Android**, the prompt is only shown to unsupervised users who have
chosen to be asked before sharing. It is not shown in regions where age
verification is mandatory. In that case the status is
`VERIFICATION_REQUIRED` and the user must resolve their status in the
Google Play Store. Google Play also suppresses the prompt after the user
has dismissed or declined it a few times.
On **iOS**, the prompt is not shown in regions where age assurance is
mandatory. In that case the age range is shared without user interaction.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestagerangeoptions">RequestAgeRangeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#requestagerangeresult">RequestAgeRangeResult</a>&gt;</code>

**Since:** 0.5.0

--------------------


### setNextAgeSignalsAccessResult(...)

```typescript
setNextAgeSignalsAccessResult(options: SetNextAgeSignalsAccessResultOptions) => Promise<void>
```

Set the next access result to be returned by the fake manager.

Only available on Android.

| Param         | Type                                                                                                  |
| ------------- | ----------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextagesignalsaccessresultoptions">SetNextAgeSignalsAccessResultOptions</a></code> |

**Since:** 0.5.0

--------------------


### setNextAgeSignalsException(...)

```typescript
setNextAgeSignalsException(options: SetNextAgeSignalsExceptionOptions) => Promise<void>
```

Set the next error to be thrown by the fake manager when the age range is
read.

Only available on Android.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextagesignalsexceptionoptions">SetNextAgeSignalsExceptionOptions</a></code> |

**Since:** 0.3.1

--------------------


### setNextAgeSignalsResult(...)

```typescript
setNextAgeSignalsResult(options: SetNextAgeSignalsResultOptions) => Promise<void>
```

Set the next age range result to be returned by the fake manager.

Only available on Android.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextagesignalsresultoptions">SetNextAgeSignalsResultOptions</a></code> |

**Since:** 0.3.1

--------------------


### setNextRequestAgeSignalsAccessException(...)

```typescript
setNextRequestAgeSignalsAccessException(options: SetNextRequestAgeSignalsAccessExceptionOptions) => Promise<void>
```

Set the next error to be thrown by the fake manager when access to the age
range is requested.

Only available on Android.

| Param         | Type                                                                                                                      |
| ------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextrequestagesignalsaccessexceptionoptions">SetNextRequestAgeSignalsAccessExceptionOptions</a></code> |

**Since:** 0.5.0

--------------------


### setUseFakeManager(...)

```typescript
setUseFakeManager(options: SetUseFakeManagerOptions) => Promise<void>
```

Enable or disable the fake manager for testing.

The fake manager is only available in debuggable builds. In release builds
this method rejects with `FAKE_MANAGER_NOT_ALLOWED`, because it would
otherwise allow age signals to be forged from the web layer.

Only available on Android.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setusefakemanageroptions">SetUseFakeManagerOptions</a></code> |

**Since:** 0.3.1

--------------------


### showSignificantUpdateAcknowledgment(...)

```typescript
showSignificantUpdateAcknowledgment(options: ShowSignificantUpdateAcknowledgmentOptions) => Promise<void>
```

Show a system interface that lets the user acknowledge a significant
change to the app.

Only call this method if `getRegulatoryRequirements()` returned
`SIGNIFICANT_APP_CHANGE_REQUIRES_ADULT_NOTIFICATION`.

Only available on iOS (26.4+).

| Param         | Type                                                                                                              |
| ------------- | ----------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#showsignificantupdateacknowledgmentoptions">ShowSignificantUpdateAcknowledgmentOptions</a></code> |

**Since:** 0.5.0

--------------------


### Interfaces


#### GetAgeRangeResult

| Prop                    | Type                                                            | Description                                                                                                                                                                                                                                                                                                                                                                                                      | Since |
| ----------------------- | --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ageRange`**          | <code><a href="#agerange">AgeRange</a></code>                   | The age range that the user has shared with the app. `undefined` if the user has not shared their age range.                                                                                                                                                                                                                                                                                                     | 0.5.0 |
| **`installId`**         | <code>string</code>                                             | An ID assigned to supervised user installs by Google Play, used for the purposes of notifying you of revoked app approval. Only available on Android.                                                                                                                                                                                                                                                            | 0.5.0 |
| **`significantChange`** | <code><a href="#significantchange">SignificantChange</a></code> | The parental approval state for the significant changes of the app. Significant changes are declared in the Google Play Console. Google Play then asks the parent or guardian of a supervised user to approve them. `undefined` if the user is not supervised, if no significant change has been declared yet or if significant changes do not apply in the jurisdiction of the user. Only available on Android. | 0.5.0 |


#### AgeRange

| Prop                         | Type                                                                | Description                                                                                                                                                                     | Since |
| ---------------------------- | ------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`activeParentalControls`** | <code>ParentalControl[]</code>                                      | The parental controls that are active for the user. Only available on iOS.                                                                                                      | 0.5.0 |
| **`ageRangeDeclaration`**    | <code><a href="#agerangedeclaration">AgeRangeDeclaration</a></code> | How the age range was declared. The more granular values are only returned in some regions. `undefined` if the system does not provide this information. Only available on iOS. | 0.5.0 |
| **`ageRangeSource`**         | <code><a href="#agerangesource">AgeRangeSource</a></code>           | How the age range was established. `undefined` if the system does not provide this information. Only available on Android.                                                      | 0.5.0 |
| **`lowerBound`**             | <code>number</code>                                                 | The (inclusive) lower bound of the age range. `undefined` if the user is below the lowest requested age gate.                                                                   | 0.5.0 |
| **`upperBound`**             | <code>number</code>                                                 | The (inclusive) upper bound of the age range. `undefined` if the user meets or exceeds the highest requested age gate.                                                          | 0.5.0 |


#### SignificantChange

| Prop               | Type                                                                        | Description                                                                                                                                                                                                                       | Since |
| ------------------ | --------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`approvalDate`** | <code>string</code>                                                         | The effective date of the most recently approved significant change, in ISO 8601 format. All significant changes with an earlier effective date are approved as well. `undefined` if no significant change has been approved yet. | 0.5.0 |
| **`status`**       | <code><a href="#significantchangestatus">SignificantChangeStatus</a></code> | The parental approval state for the most recent significant change.                                                                                                                                                               | 0.5.0 |


#### GetRegulatoryRequirementsResult

| Prop                       | Type                             | Description                                                                                                          | Since |
| -------------------------- | -------------------------------- | -------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ageAssuranceRequired`** | <code>boolean</code>             | Whether the user must share their age range because of a law or regulation that applies to their region and account. | 0.5.0 |
| **`regulatoryFeatures`**   | <code>RegulatoryFeature[]</code> | The regulatory features that the app must support for this user. Empty on iOS versions below 26.4.                   | 0.5.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                       | Since |
| --------------- | -------------------- | ------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether age signals are available on this device. | 0.5.0 |


#### RequestAgeRangeResult

| Prop                    | Type                                                            | Description                                                                                                                                                                                                                                                                                                                                                                                                      | Since |
| ----------------------- | --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ageRange`**          | <code><a href="#agerange">AgeRange</a></code>                   | The age range that the user has shared with the app. `undefined` if the status is not `SHARED`.                                                                                                                                                                                                                                                                                                                  | 0.5.0 |
| **`installId`**         | <code>string</code>                                             | An ID assigned to supervised user installs by Google Play, used for the purposes of notifying you of revoked app approval. Only available on Android.                                                                                                                                                                                                                                                            | 0.5.0 |
| **`significantChange`** | <code><a href="#significantchange">SignificantChange</a></code> | The parental approval state for the significant changes of the app. Significant changes are declared in the Google Play Console. Google Play then asks the parent or guardian of a supervised user to approve them. `undefined` if the user is not supervised, if no significant change has been declared yet or if significant changes do not apply in the jurisdiction of the user. Only available on Android. | 0.5.0 |
| **`status`**            | <code><a href="#agerangestatus">AgeRangeStatus</a></code>       | Whether the user has shared their age range with the app.                                                                                                                                                                                                                                                                                                                                                        | 0.5.0 |


#### RequestAgeRangeOptions

| Prop           | Type                  | Description                                                                                                                                                                                                                                                                                                                                          | Default                   | Since |
| -------------- | --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------- | ----- |
| **`ageGates`** | <code>number[]</code> | The age thresholds that are relevant for the app. The provided array must contain at least 1 and at most 3 ages. The ages must be sorted in ascending order and must be at least two years apart from each other. The system may ignore these thresholds and return a different age range if a law or regulation requires it. Only available on iOS. | <code>[13, 15, 18]</code> | 0.5.0 |


#### SetNextAgeSignalsAccessResultOptions

| Prop         | Type                                                      | Description                                    | Since |
| ------------ | --------------------------------------------------------- | ---------------------------------------------- | ----- |
| **`status`** | <code><a href="#agerangestatus">AgeRangeStatus</a></code> | The status to be returned by the fake manager. | 0.5.0 |


#### SetNextAgeSignalsExceptionOptions

| Prop            | Type                                            | Description                                                                                                        | Since |
| --------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------ | ----- |
| **`errorCode`** | <code><a href="#errorcode">ErrorCode</a></code> | The error code to be thrown by the fake manager. Only the error codes that are available on Android are supported. | 0.3.1 |


#### SetNextAgeSignalsResultOptions

| Prop                                | Type                                                                        | Description                                                                              | Since |
| ----------------------------------- | --------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`ageLower`**                      | <code>number</code>                                                         | The (inclusive) lower bound of the age range.                                            | 0.3.1 |
| **`ageRangeSource`**                | <code><a href="#agerangesource">AgeRangeSource</a></code>                   | How the age range was established.                                                       | 0.5.0 |
| **`ageUpper`**                      | <code>number</code>                                                         | The (inclusive) upper bound of the age range.                                            | 0.3.1 |
| **`installId`**                     | <code>string</code>                                                         | An ID assigned to supervised user installs by Google Play.                               | 0.3.1 |
| **`significantChangeApprovalDate`** | <code>string</code>                                                         | The effective date of the most recently approved significant change, in ISO 8601 format. | 0.5.0 |
| **`significantChangeStatus`**       | <code><a href="#significantchangestatus">SignificantChangeStatus</a></code> | The parental approval state for the most recent significant change.                      | 0.5.0 |


#### SetNextRequestAgeSignalsAccessExceptionOptions

| Prop            | Type                                            | Description                                                                                                        | Since |
| --------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------ | ----- |
| **`errorCode`** | <code><a href="#errorcode">ErrorCode</a></code> | The error code to be thrown by the fake manager. Only the error codes that are available on Android are supported. | 0.5.0 |


#### SetUseFakeManagerOptions

| Prop          | Type                 | Description                                  | Default            | Since |
| ------------- | -------------------- | -------------------------------------------- | ------------------ | ----- |
| **`useFake`** | <code>boolean</code> | Whether to use the fake manager for testing. | <code>false</code> | 0.3.1 |


#### ShowSignificantUpdateAcknowledgmentOptions

| Prop                    | Type                | Description                                                                              | Since |
| ----------------------- | ------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`updateDescription`** | <code>string</code> | A short description of what has changed in the app and why the user must acknowledge it. | 0.5.0 |


### Enums


#### ParentalControl

| Members                   | Value                               | Description                                                | Since |
| ------------------------- | ----------------------------------- | ---------------------------------------------------------- | ----- |
| **`CommunicationLimits`** | <code>'COMMUNICATION_LIMITS'</code> | The system limits the communication features for the user. | 0.5.0 |


#### AgeRangeDeclaration

| Members                            | Value                                           | Description                                                                                                           | Since |
| ---------------------------------- | ----------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`CheckedByOtherMethod`**         | <code>'CHECKED_BY_OTHER_METHOD'</code>          | The user set their own age range using an unspecified method. Only available on iOS (26.2+).                          | 0.5.0 |
| **`Confirmed`**                    | <code>'CONFIRMED'</code>                        | The age range was set using a scrutinized method, like a credit card or government ID. Only available on iOS (26.5+). | 0.4.0 |
| **`GovernmentIdChecked`**          | <code>'GOVERNMENT_ID_CHECKED'</code>            | The user set their own age range using a government ID. Only available on iOS (26.2+).                                | 0.5.0 |
| **`GuardianCheckedByOtherMethod`** | <code>'GUARDIAN_CHECKED_BY_OTHER_METHOD'</code> | A parent or guardian set the age range using an unspecified method. Only available on iOS (26.2+).                    | 0.5.0 |
| **`GuardianDeclared`**             | <code>'GUARDIAN_DECLARED'</code>                | A parent or guardian declared the age range without external verification.                                            | 0.4.0 |
| **`GuardianGovernmentIdChecked`**  | <code>'GUARDIAN_GOVERNMENT_ID_CHECKED'</code>   | A parent or guardian set the age range using a government ID. Only available on iOS (26.2+).                          | 0.5.0 |
| **`GuardianPaymentChecked`**       | <code>'GUARDIAN_PAYMENT_CHECKED'</code>         | A parent or guardian set the age range using a payment method, like a credit card. Only available on iOS (26.2+).     | 0.5.0 |
| **`PaymentChecked`**               | <code>'PAYMENT_CHECKED'</code>                  | The user set their own age range using a payment method, like a credit card. Only available on iOS (26.2+).           | 0.5.0 |
| **`SelfDeclared`**                 | <code>'SELF_DECLARED'</code>                    | The user declared their own age range without external verification.                                                  | 0.4.0 |


#### AgeRangeSource

| Members     | Value                 | Description                                                                                                               | Since |
| ----------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`TierA`** | <code>'TIER_A'</code> | The user has self-declared their age.                                                                                     | 0.5.0 |
| **`TierB`** | <code>'TIER_B'</code> | The age of the user is managed by a parent or guardian.                                                                   | 0.5.0 |
| **`TierC`** | <code>'TIER_C'</code> | The age of the user was assessed using a credit card, an email address, a selfie assessment, a government ID or a tax ID. | 0.5.0 |
| **`TierD`** | <code>'TIER_D'</code> | The age of the user was checked using a combination of a government ID and a selfie assessment, or using a digital ID.    | 0.5.0 |


#### SignificantChangeStatus

| Members        | Value                   | Description                                                                                               | Since |
| -------------- | ----------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`Approved`** | <code>'APPROVED'</code> | The parent or guardian has approved the most recent significant change and all prior significant changes. | 0.5.0 |
| **`Declined`** | <code>'DECLINED'</code> | The parent or guardian has declined one or more significant changes.                                      | 0.5.0 |
| **`Pending`**  | <code>'PENDING'</code>  | The parent or guardian has not yet approved one or more significant changes.                              | 0.5.0 |


#### RegulatoryFeature

| Members                                             | Value                                                             | Description                                                                                                                                                                        | Since |
| --------------------------------------------------- | ----------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`DeclaredAgeRangeRequired`**                      | <code>'DECLARED_AGE_RANGE_REQUIRED'</code>                        | The user must share their age range with the app.                                                                                                                                  | 0.5.0 |
| **`SignificantAppChangeRequiresAdultNotification`** | <code>'SIGNIFICANT_APP_CHANGE_REQUIRES_ADULT_NOTIFICATION'</code> | An adult user must acknowledge a significant change of the app. Use `showSignificantUpdateAcknowledgment(...)` to show the system interface for the acknowledgment.                | 0.5.0 |
| **`SignificantAppChangeRequiresParentalConsent`**   | <code>'SIGNIFICANT_APP_CHANGE_REQUIRES_PARENTAL_CONSENT'</code>   | A parent or guardian must consent to a significant change of the app. This plugin does not implement the consent flow. Use Apple's PermissionKit framework to request the consent. | 0.5.0 |


#### AgeRangeStatus

| Members                    | Value                                | Description                                                                                                                                                                               | Since |
| -------------------------- | ------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`NotShared`**            | <code>'NOT_SHARED'</code>            | The user has not shared their age range with the app.                                                                                                                                     | 0.5.0 |
| **`Shared`**               | <code>'SHARED'</code>                | The user has shared their age range with the app.                                                                                                                                         | 0.5.0 |
| **`Unspecified`**          | <code>'UNSPECIFIED'</code>           | The system did not specify whether the user has shared their age range. Treat this value like `NOT_SHARED`. Google Play does not document when it is returned. Only available on Android. | 0.5.0 |
| **`VerificationRequired`** | <code>'VERIFICATION_REQUIRED'</code> | The user must verify their age in the Google Play Store before the age range can be shared. Only available on Android.                                                                    | 0.5.0 |


#### ErrorCode

| Members                              | Value                                           | Description                                                                                                                                                                                 | Since |
| ------------------------------------ | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ApiNotAvailable`**                | <code>'API_NOT_AVAILABLE'</code>                | The age signals API is not available. On **Android**, the Play Store app version installed on the device might be old. On **iOS**, the system was unable to share the age range.            | 0.0.1 |
| **`AppNotOwned`**                    | <code>'APP_NOT_OWNED'</code>                    | The app was not installed by Google Play. Only available on Android.                                                                                                                        | 0.0.1 |
| **`CannotBindToService`**            | <code>'CANNOT_BIND_TO_SERVICE'</code>           | Binding to the service in the Play Store has failed. This can be due to having an old Play Store version installed on the device or device memory is overloaded. Only available on Android. | 0.0.1 |
| **`ClientTransientError`**           | <code>'CLIENT_TRANSIENT_ERROR'</code>           | There was a transient error in the client device. Only available on Android.                                                                                                                | 0.0.1 |
| **`FakeManagerNotAllowed`**          | <code>'FAKE_MANAGER_NOT_ALLOWED'</code>         | The fake manager is not available, because the app is not debuggable. Only available on Android.                                                                                            | 0.5.0 |
| **`FakeManagerNotEnabled`**          | <code>'FAKE_MANAGER_NOT_ENABLED'</code>         | The fake manager is not enabled. Only available on Android.                                                                                                                                 | 0.5.0 |
| **`InternalError`**                  | <code>'INTERNAL_ERROR'</code>                   | Unknown internal error. Only available on Android.                                                                                                                                          | 0.0.1 |
| **`InvalidRequest`**                 | <code>'INVALID_REQUEST'</code>                  | The request contains invalid parameters. On **iOS**, this is also returned if the requested age gates are rejected by the system.                                                           | 0.5.0 |
| **`NetworkError`**                   | <code>'NETWORK_ERROR'</code>                    | No available network is found. Only available on Android.                                                                                                                                   | 0.0.1 |
| **`NotSupported`**                   | <code>'NOT_SUPPORTED'</code>                    | Age signals are not supported on this device.                                                                                                                                               | 0.5.0 |
| **`PlayServicesNotFound`**           | <code>'PLAY_SERVICES_NOT_FOUND'</code>          | Play Services is not available or its version is too old. Only available on Android.                                                                                                        | 0.0.1 |
| **`PlayServicesVersionOutdated`**    | <code>'PLAY_SERVICES_VERSION_OUTDATED'</code>   | Play Services needs to be updated. Only available on Android.                                                                                                                               | 0.0.1 |
| **`PlayStoreNotFound`**              | <code>'PLAY_STORE_NOT_FOUND'</code>             | No Play Store app is found on the device. Only available on Android.                                                                                                                        | 0.0.1 |
| **`PlayStoreVersionOutdated`**       | <code>'PLAY_STORE_VERSION_OUTDATED'</code>      | The Play Store app needs to be updated. Only available on Android.                                                                                                                          | 0.0.1 |
| **`PresentationContextUnavailable`** | <code>'PRESENTATION_CONTEXT_UNAVAILABLE'</code> | No view controller was found to present the system interface. Only available on iOS.                                                                                                        | 0.5.0 |
| **`SdkVersionOutdated`**             | <code>'SDK_VERSION_OUTDATED'</code>             | The Age Signals SDK version is outdated. Only available on Android.                                                                                                                         | 0.4.0 |

</docgen-api>

## Testing

The plugin includes support for the `FakeAgeSignalsManager` API on Android, which allows you to simulate different age signals scenarios in your tests without requiring live responses from Google Play.

### Android Testing

**Important**: The fake manager is only available if your app is debuggable. In release builds, `setUseFakeManager(...)` rejects with `FAKE_MANAGER_NOT_ALLOWED`, because it would otherwise allow age signals to be forged from the web layer.

**Important**: Due to a known issue in versions 0.0.1 and 0.0.2 of the Age Signals API, you may encounter a `java.lang.VerifyError` when calling the builder method of `AgeSignalsResult` in unit tests. As a workaround, run your tests as Android instrumented tests within the `androidTest` source set.

#### Example: Testing a Self-Declared Adult User

```typescript
import {
  AgeRangeSource,
  AgeRangeStatus,
  AgeSignals,
} from '@capawesome/capacitor-age-signals';

// Enable the fake manager
await AgeSignals.setUseFakeManager({ useFake: true });

// The user agrees to share their age range
await AgeSignals.setNextAgeSignalsAccessResult({
  status: AgeRangeStatus.Shared,
});

// Set up an adult user
await AgeSignals.setNextAgeSignalsResult({
  ageLower: 18,
  ageRangeSource: AgeRangeSource.TierA,
});

const result = await AgeSignals.requestAgeRange();
console.log(result.status); // 'SHARED'
console.log(result.ageRange?.lowerBound); // 18
```

#### Example: Testing a Supervised User (13-17 years old)

```typescript
import {
  AgeRangeSource,
  AgeRangeStatus,
  AgeSignals,
} from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

await AgeSignals.setNextAgeSignalsAccessResult({
  status: AgeRangeStatus.Shared,
});

await AgeSignals.setNextAgeSignalsResult({
  ageLower: 13,
  ageUpper: 17,
  ageRangeSource: AgeRangeSource.TierB,
  installId: 'fake_install_id',
});

const result = await AgeSignals.requestAgeRange();
console.log(result.ageRange?.lowerBound); // 13
console.log(result.ageRange?.upperBound); // 17
console.log(result.installId); // 'fake_install_id'
```

#### Example: Testing Parental Approval Scenarios

```typescript
import {
  AgeRangeSource,
  AgeRangeStatus,
  AgeSignals,
  SignificantChangeStatus,
} from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

await AgeSignals.setNextAgeSignalsAccessResult({
  status: AgeRangeStatus.Shared,
});

// Test pending approval
await AgeSignals.setNextAgeSignalsResult({
  ageLower: 13,
  ageUpper: 17,
  ageRangeSource: AgeRangeSource.TierB,
  significantChangeApprovalDate: '2026-02-01T00:00:00.000Z',
  significantChangeStatus: SignificantChangeStatus.Pending,
});

const result = await AgeSignals.requestAgeRange();
console.log(result.significantChange?.status); // 'PENDING'
console.log(result.significantChange?.approvalDate); // '2026-02-01T00:00:00.000Z'
```

#### Example: Testing a User Who Must Verify Their Age

```typescript
import { AgeRangeStatus, AgeSignals } from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

await AgeSignals.setNextAgeSignalsAccessResult({
  status: AgeRangeStatus.VerificationRequired,
});

const result = await AgeSignals.requestAgeRange();
console.log(result.status); // 'VERIFICATION_REQUIRED'
console.log(result.ageRange); // undefined
```

#### Example: Testing Error Scenarios

```typescript
import { AgeSignals, ErrorCode } from '@capawesome/capacitor-age-signals';

await AgeSignals.setUseFakeManager({ useFake: true });

// Simulate a network error while access is requested
await AgeSignals.setNextRequestAgeSignalsAccessException({
  errorCode: ErrorCode.NetworkError,
});

try {
  await AgeSignals.requestAgeRange();
} catch (error) {
  console.log('Caught network error:', error);
}

// Simulate a network error while the age range is read
await AgeSignals.setNextAgeSignalsException({
  errorCode: ErrorCode.NetworkError,
});

try {
  await AgeSignals.getAgeRange();
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
const result = await AgeSignals.requestAgeRange();
```

## FAQ

### Which age verification APIs does this plugin use?

On Android, the plugin uses the [Play Age Signals API](https://developer.android.com/google/play/age-signals/overview) to request age signals from Google Play. On iOS, it uses Apple's [DeclaredAgeRange](https://developer.apple.com/documentation/declaredagerange/) framework, which requires the `com.apple.developer.declared-age-range` entitlement (see [Installation](#installation)).

### What do the different statuses mean?

The `status` property tells you whether the user shared their age range. `SHARED` means an age range is available, `NOT_SHARED` means the user declined or is not in an applicable region, and `VERIFICATION_REQUIRED` (Android only) means the user must resolve their status in the Google Play Store first.

### How do the Android age range sources map to the iOS age range declarations?

Android reports how an age was established as a tier, while iOS reports a declaration method. They do not map one to one, so both values are exposed as separate properties. The following table shows the closest equivalents:

| Android   | iOS                                                                                                     |
| --------- | ------------------------------------------------------------------------------------------------------- |
| `TIER_A`  | `SELF_DECLARED`                                                                                          |
| `TIER_B`  | `GUARDIAN_DECLARED`                                                                                      |
| `TIER_C`  | `PAYMENT_CHECKED`, `CHECKED_BY_OTHER_METHOD`, `GUARDIAN_PAYMENT_CHECKED`, `GUARDIAN_CHECKED_BY_OTHER_METHOD` |
| `TIER_D`  | `GOVERNMENT_ID_CHECKED`, `GUARDIAN_GOVERNMENT_ID_CHECKED`                                                |

On iOS 26.5 and later, the more granular declarations may be replaced by `CONFIRMED`, which covers both `TIER_C` and `TIER_D`.

### How can I test different age verification scenarios?

On Android, the plugin integrates the `FakeAgeSignalsManager` API, which lets you simulate age signals without live responses from Google Play. Enable it with `setUseFakeManager(...)` and set the next results or exceptions with `setNextAgeSignalsAccessResult(...)`, `setNextAgeSignalsResult(...)`, `setNextRequestAgeSignalsAccessException(...)` and `setNextAgeSignalsException(...)`. The fake manager is only available if your app is debuggable. See the [Testing](#testing) section for complete examples.

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
