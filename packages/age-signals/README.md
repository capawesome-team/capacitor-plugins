# @capawesome/capacitor-age-signals

Capacitor plugin to use the [Play Age Signals API](https://developer.android.com/google/play/age-signals/overview) to retrieve age-related signals for users.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-age-signals
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$androidPlayAgeSignalsVersion` version of `com.google.android.play:age-signals` (default: `0.0.1-beta01`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

The following entitlement must be added to the app:

```xml
<key>com.apple.developer.declared-age-range</key>
<true/>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AgeSignals } from '@capawesome/capacitor-age-signals';

const checkAgeSignals = async () => {
  const result = await AgeSignals.checkAgeSignals();
  console.log('User Status:', result.userStatus);
  console.log('Age Lower:', result.ageLower);
  console.log('Age Upper:', result.ageUpper);
};
```

## API

<docgen-index>

* [`checkAgeSignals(...)`](#checkagesignals)
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


### Interfaces


#### CheckAgeSignalsResult

| Prop                         | Type                                              | Description                                                                                                                                                                                                                                                                                                       | Since |
| ---------------------------- | ------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`userStatus`**             | <code><a href="#userstatus">UserStatus</a></code> | The user's verification status.                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`ageLower`**               | <code>number</code>                               | The (inclusive) lower bound of a supervised user's age range. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`.                                                                                                                                   | 0.0.1 |
| **`ageUpper`**               | <code>number</code>                               | The (inclusive) upper bound of a supervised user's age range. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED` and the user's age is under 18.                                                                                                    | 0.0.1 |
| **`mostRecentApprovalDate`** | <code>string</code>                               | The effective from date of the most recent significant change that was approved. When an app is installed, the date of the most recent significant change prior to install is used. Only available when `userStatus` is `SUPERVISED_APPROVAL_PENDING` or `SUPERVISED_APPROVAL_DENIED`. Only available on Android. | 0.0.1 |
| **`installId`**              | <code>string</code>                               | An ID assigned to supervised user installs by Google Play, used for the purposes of notifying you of revoked app approval. Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`. Only available on Android.                                           | 0.0.1 |


#### CheckAgeSignalsOptions

| Prop           | Type                  | Description                                                                                                                    | Default                   | Since |
| -------------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------------ | ------------------------- | ----- |
| **`ageGates`** | <code>number[]</code> | The age ranges that the user falls into. The provided array must contain at least 2 and at most 3 ages. Only available on iOS. | <code>[13, 15, 18]</code> | 0.0.2 |


### Enums


#### UserStatus

| Members                         | Value                                      | Description                                                                                                                                                                                                                                                                                    | Since |
| ------------------------------- | ------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Verified`**                  | <code>'VERIFIED'</code>                    | The user is over 18. Google verified the user's age using a commercially reasonable method such as a government-issued ID, credit card, or facial age estimation.                                                                                                                              | 0.0.1 |
| **`Supervised`**                | <code>'SUPERVISED'</code>                  | The user has a supervised Google Account managed by a parent who sets their age. Use `ageLower` and `ageUpper` to determine the user's age range.                                                                                                                                              | 0.0.1 |
| **`SupervisedApprovalPending`** | <code>'SUPERVISED_APPROVAL_PENDING'</code> | The user has a supervised Google Account, and their supervising parent has not yet approved one or more pending significant changes. Use `ageLower` and `ageUpper` to determine the user's age range. Use `mostRecentApprovalDate` to determine the last significant change that was approved. | 0.0.1 |
| **`SupervisedApprovalDenied`**  | <code>'SUPERVISED_APPROVAL_DENIED'</code>  | The user has a supervised Google Account, and their supervising parent denied approval for one or more significant changes. Use `ageLower` and `ageUpper` to determine the user's age range. Use `mostRecentApprovalDate` to determine the last significant change that was approved.          | 0.0.1 |
| **`Unknown`**                   | <code>'UNKNOWN'</code>                     | The user is not verified or supervised in applicable jurisdictions and regions. These users could be over or under 18. To obtain an age signal from Google Play, ask the user to visit the Play Store to resolve their status.                                                                 | 0.0.1 |
| **`Empty`**                     | <code>'EMPTY'</code>                       | All other users return this value.                                                                                                                                                                                                                                                             | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/age-signals/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/age-signals/LICENSE).
