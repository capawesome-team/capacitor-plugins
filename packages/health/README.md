# Capacitor Health Plugin

Capacitor plugin to read, write and aggregate health data via Apple HealthKit and Android Health Connect with a single, strictly typed API.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Health plugin connects your app to Apple Health (HealthKit) on iOS and Health Connect on Android. Here are some of the key features:

- 🏥 **Cross-Platform**: One API for Apple HealthKit and Android Health Connect.
- 📖 **~20 Data Types**: Steps, distance, calories, heart rate, sleep, blood pressure, blood glucose, workouts and more.
- 📊 **Aggregation-First**: Query sums, averages, minimums and maximums grouped by hour, day, week or month — with multi-source deduplication handled by the platform.
- ✍️ **Writing**: Log the record types apps commonly write, such as weight, hydration, blood pressure and workouts.
- 🔒 **Honest Permission Model**: Faithfully models what each platform actually reveals about permissions instead of pretending (see the FAQ).
- 🧭 **Availability Handling**: Explicitly models the Health Connect installation states and provides an install helper.
- 📋 **Policy Documentation**: Detailed guidance for the Google Play Health apps declaration and Apple's App Review Guideline 5.1.3.
- 🛡️ **Typed Errors**: Invalid queries reject with clear error codes instead of resolving with silently empty results.
- 🤝 **Compatibility**: Works hand in hand with the [Pedometer](https://capawesome.io/docs/sdks/capacitor/pedometer/), [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/) and [Local Notifications](https://capawesome.io/docs/sdks/capacitor/local-notifications/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Health plugin is typically used whenever an app works with health and fitness data, for example:

- **Fitness dashboards**: Show daily steps, distance and calories with hourly or daily breakdowns.
- **Workout tracking**: Read workouts from other apps or log your own workout sessions.
- **Health logging**: Let users log weight, hydration, blood pressure or blood glucose.
- **Sleep insights**: Analyze sleep sessions and sleep stages.
- **Coaching and habit apps**: Reward users based on their real activity data.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-health` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-health
npx cap sync
```

### Android

The plugin uses [Health Connect](https://health.google/health-connect-android/), Google's platform for health data on Android.
Health data is available on devices running Android 9 (API level 28) or higher.
On Android 9 to 13, the user may need to install the Health Connect app first (see `isAvailable()` and `installHealthConnect()`).
On Android 14 and higher, Health Connect is part of the operating system.

#### Minimum SDK Version

The Health Connect SDK requires a minimum SDK version of `26`.
Make sure that the `minSdkVersion` in your `android/variables.gradle` file is set to at least `26`:

```groovy
ext {
    minSdkVersion = 26
}
```

On devices below Android 9, `isAvailable()` resolves with the reason `not-supported` and all other methods reject as unavailable.

#### Permissions

Health Connect permissions are declared **in your app**, not by the plugin.
This is intentional: Google Play reviews every declared health permission, so your app must only declare the permissions for the data types it actually uses.

Add the permissions for the data types you use to your `AndroidManifest.xml` file before or after the `application` tag:

```xml
<!-- Add ONLY the permissions for the data types your app actually uses! -->
<uses-permission android:name="android.permission.health.READ_STEPS" />
<uses-permission android:name="android.permission.health.READ_HEART_RATE" />
<uses-permission android:name="android.permission.health.WRITE_WEIGHT" />
```

The following table lists the permission for each data type supported by this plugin:

| Data Type                | Read Permission                                             | Write Permission (only if you write)                                                                                                     |
| ------------------------ | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `ACTIVE_CALORIES`        | `android.permission.health.READ_ACTIVE_CALORIES_BURNED`     | -                                                                                                                                          |
| `BLOOD_GLUCOSE`          | `android.permission.health.READ_BLOOD_GLUCOSE`              | `android.permission.health.WRITE_BLOOD_GLUCOSE`                                                                                           |
| `BLOOD_PRESSURE`         | `android.permission.health.READ_BLOOD_PRESSURE`             | `android.permission.health.WRITE_BLOOD_PRESSURE`                                                                                          |
| `BODY_FAT`               | `android.permission.health.READ_BODY_FAT`                   | -                                                                                                                                          |
| `BODY_TEMPERATURE`       | `android.permission.health.READ_BODY_TEMPERATURE`           | -                                                                                                                                          |
| `DISTANCE`               | `android.permission.health.READ_DISTANCE`                   | -                                                                                                                                          |
| `FLOORS_CLIMBED`         | `android.permission.health.READ_FLOORS_CLIMBED`             | -                                                                                                                                          |
| `HEART_RATE`             | `android.permission.health.READ_HEART_RATE`                 | -                                                                                                                                          |
| `HEART_RATE_VARIABILITY` | `android.permission.health.READ_HEART_RATE_VARIABILITY`     | -                                                                                                                                          |
| `HEIGHT`                 | `android.permission.health.READ_HEIGHT`                     | `android.permission.health.WRITE_HEIGHT`                                                                                                  |
| `HYDRATION`              | `android.permission.health.READ_HYDRATION`                  | `android.permission.health.WRITE_HYDRATION`                                                                                               |
| `OXYGEN_SATURATION`      | `android.permission.health.READ_OXYGEN_SATURATION`          | -                                                                                                                                          |
| `RESPIRATORY_RATE`       | `android.permission.health.READ_RESPIRATORY_RATE`           | -                                                                                                                                          |
| `RESTING_HEART_RATE`     | `android.permission.health.READ_RESTING_HEART_RATE`         | -                                                                                                                                          |
| `SLEEP`                  | `android.permission.health.READ_SLEEP`                      | -                                                                                                                                          |
| `STEPS`                  | `android.permission.health.READ_STEPS`                      | `android.permission.health.WRITE_STEPS`                                                                                                   |
| `TOTAL_CALORIES`         | `android.permission.health.READ_TOTAL_CALORIES_BURNED`      | -                                                                                                                                          |
| `VO2_MAX`                | `android.permission.health.READ_VO2_MAX`                    | -                                                                                                                                          |
| `WEIGHT`                 | `android.permission.health.READ_WEIGHT`                     | `android.permission.health.WRITE_WEIGHT`                                                                                                  |
| `WORKOUT`                | `android.permission.health.READ_EXERCISE` (optionally `android.permission.health.READ_ACTIVE_CALORIES_BURNED` and `android.permission.health.READ_DISTANCE`) | `android.permission.health.WRITE_EXERCISE`, `android.permission.health.WRITE_DISTANCE`, `android.permission.health.WRITE_ACTIVE_CALORIES_BURNED` |

**Note**: Writing a workout requires three permissions because the plugin writes the optional workout totals (`totalDistance`, `totalCalories`) as separate distance and active calories records, which is how Health Connect models workout totals. For the same reason, `readWorkouts(...)` only returns the workout totals if the read permissions for active calories and distance are granted. Without them, the totals are `null`.

#### Privacy Policy

Health Connect **requires** every app to explain how it uses health data.
The plugin already ships the required activity that handles the privacy policy intents of Health Connect.
You only need to configure the URL of your privacy policy by adding the following `meta-data` element inside the `application` tag of your `AndroidManifest.xml` file:

```xml
<meta-data
    android:name="io.capawesome.capacitorjs.plugins.health.PRIVACY_POLICY_URL"
    android:value="https://example.com/privacy-policy" />
```

When the user taps the privacy policy link in the Health Connect permission dialog or in the Health Connect settings, this URL is opened in the browser.

#### Google Play Health Apps Declaration

Google Play requires **every** app that integrates with Health Connect to be approved for access.
Without this approval, your app **will be rejected** during Google Play review.
Follow these steps before you submit your app:

1. Open the [Google Play Console](https://play.google.com/console/) and select your app.
1. Go to **Monitor and improve** → **Policy and programs** → **App content**.
1. Complete the **Health apps** declaration form: declare that your app integrates with Health Connect, select the requested permission types and describe your use case.
1. Make sure your app provides a **privacy policy** that explains how health data is used (see above).
1. Wait for the approval before rolling out your release.

Health Connect data may only be used for the permitted health use cases described in the [Health Connect policy](https://support.google.com/googleplay/android-developer/answer/12991134).
Using health data for advertising or undisclosed data-mining purposes is prohibited and will get your app rejected or removed.

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default versions of the dependencies:

- `$healthConnectVersion` version of `androidx.health.connect:connect-client` (default: `1.1.0`)
- `$kotlinVersion` version of `org.jetbrains.kotlin:kotlin-gradle-plugin` (default: `2.1.20`)
- `$kotlinxCoroutinesVersion` version of `org.jetbrains.kotlinx:kotlinx-coroutines-android` (default: `1.10.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

The Health Connect SDK is a lightweight client library. The actual health data store is provided by the Health Connect app (Android 9 to 13) or the operating system (Android 14+) and is **not** bundled with your app.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

The plugin uses [HealthKit](https://developer.apple.com/documentation/healthkit), Apple's framework for health data on iOS.
Health data is not available on iPadOS versions before 17 and in visionOS; use `isAvailable()` to check at runtime.

#### Capability

Add the **HealthKit** capability to your app in Xcode: select your app target, open the **Signing & Capabilities** tab and add the **HealthKit** capability.
This adds the `com.apple.developer.healthkit` entitlement to your app.

#### Privacy Descriptions

Add the following keys to the `Info.plist` file of your app to explain why your app needs access to health data:

```xml
<key>NSHealthShareUsageDescription</key>
<string>The app needs access to your health data to show your activity and progress.</string>
<key>NSHealthUpdateUsageDescription</key>
<string>The app writes the health data you log back to Apple Health.</string>
```

`NSHealthShareUsageDescription` is required for reading and `NSHealthUpdateUsageDescription` is required for writing.
**Attention**: If a required key is missing, requesting authorization would crash your app.
The plugin therefore pre-checks the `Info.plist` file and rejects `requestPermissions(...)` with a clear error message instead.

#### App Review Guideline 5.1.3

Apple reviews health apps against [App Review Guideline 5.1.3 (Health and Health Research)](https://developer.apple.com/app-store/review/guidelines/#health-and-health-research).
The most important rules:

- Your app must provide a **genuine health or fitness feature** that justifies the HealthKit integration. Requesting health permissions without a visible feature is a common rejection reason.
- Health data may **not** be used for advertising, marketing or other use-based data mining. Serving ads based on health data will get your app rejected.
- Health data may **not** be shared with third parties without explicit user consent, and never for advertising purposes.
- Apps that write data to HealthKit must ensure the data is **accurate** and clearly attributed.
- Provide a **privacy policy** that explains how health data is collected and used.

## Configuration

No configuration required for this plugin.

## Platform Behavior

The two platforms model health data differently. The plugin exposes these differences honestly instead of hiding them:

| Behavior           | Android (Health Connect)                                                                                                       | iOS (HealthKit)                                                                                                                                             |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Availability       | Three states: available, Health Connect not installed (installable via `installHealthConnect()`) or unsupported device.        | Available on devices with health data support (`HKHealthStore.isHealthDataAvailable()`).                                                                      |
| Read permissions   | Reported as `granted` or `prompt` (`denied` after a rejected request). `checkPermissions(...)` never reports `denied` since Health Connect does not reveal it. | Reported as `prompt` before the first request and `unknown` afterwards. HealthKit deliberately hides read permission status (see FAQ). Never `granted`.       |
| Write permissions  | Reported as `granted` or `prompt` (`denied` after a rejected request).                                                          | Reported as `granted`, `denied` or `prompt`.                                                                                                                   |
| Historical reads   | Limited to 30 days before the permission was first granted.                                                                     | No time limit.                                                                                                                                                 |
| `SLEEP` records    | Sleep sessions containing all sleep stages.                                                                                     | Individual sleep analysis samples, each with exactly one stage (HealthKit has no session concept).                                                             |
| `TOTAL_CALORIES`   | Dedicated total calories record type.                                                                                           | Only available via `aggregate(...)`, computed as active + basal energy burned.                                                                                 |
| `HEART_RATE_VARIABILITY` | RMSSD metric.                                                                                                             | SDNN metric. **The values are not comparable across platforms!**                                                                                               |
| Workout totals     | Aggregated from the records the workout app wrote for the workout time range.                                                   | Read from the workout statistics (iOS 16+; `null` on earlier versions).                                                                                       |
| `sourceName`       | Not provided by Health Connect (always `null`). Use `sourceBundleId`.                                                           | The display name of the source (e.g. "Apple Watch").                                                                                                          |

## Usage

```typescript
import { DataType, Health } from '@capawesome-team/capacitor-health';

const checkAvailability = async () => {
  const { available, reason } = await Health.isAvailable();
  if (!available && reason === 'health-connect-not-installed') {
    // Prompt the user to install Health Connect (Android 9 to 13)
    await Health.installHealthConnect();
  }
  return available;
};

const requestPermissions = async () => {
  const { permissions } = await Health.requestPermissions({
    read: [DataType.Steps, DataType.HeartRate, DataType.Sleep],
    write: [DataType.Weight],
  });
  return permissions;
};

const readSteps = async () => {
  const { records } = await Health.readRecords({
    dataType: DataType.Steps,
    startDate: new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toISOString(),
    endDate: new Date().toISOString(),
  });
  return records;
};

const aggregateDailySteps = async () => {
  const { buckets } = await Health.aggregate({
    dataType: DataType.Steps,
    startDate: new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toISOString(),
    endDate: new Date().toISOString(),
    bucket: 'day',
    operations: ['sum'],
  });
  return buckets;
};

const readWorkouts = async () => {
  const { workouts } = await Health.readWorkouts({
    startDate: new Date(Date.now() - 30 * 24 * 60 * 60 * 1000).toISOString(),
    endDate: new Date().toISOString(),
    limit: 10,
  });
  return workouts;
};

const writeWeight = async () => {
  await Health.writeRecord({
    dataType: DataType.Weight,
    startDate: new Date().toISOString(),
    value: 71.5,
  });
};

const openSettings = async () => {
  await Health.openSettings();
};
```

## API

<docgen-index>

* [`aggregate(...)`](#aggregate)
* [`checkPermissions(...)`](#checkpermissions)
* [`installHealthConnect()`](#installhealthconnect)
* [`isAvailable()`](#isavailable)
* [`openSettings()`](#opensettings)
* [`readRecords(...)`](#readrecords)
* [`readWorkouts(...)`](#readworkouts)
* [`requestPermissions(...)`](#requestpermissions)
* [`writeRecord(...)`](#writerecord)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### aggregate(...)

```typescript
aggregate(options: AggregateOptions) => Promise<AggregateResult>
```

Aggregate health data over a time range, optionally grouped into buckets.

The aggregation is performed by the platform's health store, which
deduplicates overlapping data from multiple sources (e.g. a phone and a
smartwatch) automatically.

Only the following combinations of data type and operation are supported:

| Data Type            | `sum` | `average`, `maximum`, `minimum` |
| -------------------- | ----- | ------------------------------- |
| `ACTIVE_CALORIES`    | ✅    | ❌                              |
| `DISTANCE`           | ✅    | ❌                              |
| `FLOORS_CLIMBED`     | ✅    | ❌                              |
| `HEART_RATE`         | ❌    | ✅                              |
| `HEIGHT`             | ❌    | ✅                              |
| `HYDRATION`          | ✅    | ❌                              |
| `RESTING_HEART_RATE` | ❌    | ✅                              |
| `STEPS`              | ✅    | ❌                              |
| `TOTAL_CALORIES`     | ✅    | ❌                              |
| `WEIGHT`             | ❌    | ✅                              |

Any other combination rejects with the `INVALID_AGGREGATION` error code.
It never resolves with silently empty results.

On **iOS**, `TOTAL_CALORIES` is computed as the sum of active and basal
energy burned since HealthKit has no total energy type.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#aggregateoptions">AggregateOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#aggregateresult">AggregateResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### checkPermissions(...)

```typescript
checkPermissions(options: CheckPermissionsOptions) => Promise<PermissionStatus>
```

Check the permission status for the given data types.

On **Android**, Health Connect only reports whether a permission is
currently granted. A permission that has never been requested and a
permission that has been denied are both reported as `prompt`.

On **iOS**, HealthKit deliberately hides the read permission status to
prevent apps from inferring sensitive information (a denied read
permission is indistinguishable from the absence of data). Read
permissions are therefore reported as `prompt` before the first request
and as `unknown` afterwards. They are **never** reported as `granted`.
Design your app around the presence of data, not around the read
permission status.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#checkpermissionsoptions">CheckPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### installHealthConnect()

```typescript
installHealthConnect() => Promise<void>
```

Open the Play Store to install or update the Health Connect app.

On Android 9 to 13, Health Connect must be installed as a separate app.
On Android 14 and later, Health Connect is part of the operating system
but may still require an update.

Call this method if `isAvailable()` returns the reason
`health-connect-not-installed` or `health-connect-update-required`.

Only available on Android.

**Since:** 0.0.1

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether health data is available on the device.

On **Android**, this checks the Health Connect SDK status and reports one
of three states: available, not installed (but supported) or unsupported.
If Health Connect is not installed, you can prompt the user to install it
with `installHealthConnect()`.

On **iOS**, this checks `HKHealthStore.isHealthDataAvailable()`, which
returns `false` on devices without health data support (e.g. some iPads).

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the platform's health settings.

On **Android**, this opens the Health Connect settings where the user can
manage app permissions and data.

On **iOS**, this opens the Apple Health app. Apple does not provide a way
to open the privacy settings of your app directly. The user can manage
permissions in the Health app under `Profile → Privacy → Apps`.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### readRecords(...)

```typescript
readRecords(options: ReadRecordsOptions) => Promise<ReadRecordsResult>
```

Read individual health records of a single data type.

On **Android**, reads are limited to the last 30 days before the
permission was first granted.

On **iOS**, the `TOTAL_CALORIES` data type is not supported by this
method since HealthKit has no total energy type. Use `aggregate(...)`
instead.

To read workouts, use `readWorkouts(...)`.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#readrecordsoptions">ReadRecordsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readrecordsresult">ReadRecordsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### readWorkouts(...)

```typescript
readWorkouts(options: ReadWorkoutsOptions) => Promise<ReadWorkoutsResult>
```

Read workouts.

Requires the read permission for the `WORKOUT` data type.

On **Android**, the `totalCalories` and `totalDistance` values
additionally require the read permissions for the `ACTIVE_CALORIES` and
`DISTANCE` data types. Without them, both values are `null`.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#readworkoutsoptions">ReadWorkoutsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readworkoutsresult">ReadWorkoutsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options: RequestPermissionsOptions) => Promise<PermissionStatus>
```

Request permissions for the given data types.

On **Android**, the Health Connect permission screen is shown. If the
user denies the request twice, Health Connect permanently ignores further
requests and the permissions can only be granted via `openSettings()`.

On **iOS**, the HealthKit authorization sheet is shown once per data
type. After the request, read permissions are reported as `unknown` since
HealthKit hides the read permission status (see `checkPermissions(...)`).

On **iOS**, this method rejects with a clear error message if the
required usage descriptions are missing in the `Info.plist` file (see the
installation instructions).

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### writeRecord(...)

```typescript
writeRecord(options: WriteRecordOptions) => Promise<void>
```

Write a single health record.

Requires the write permission for the given data type.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#writerecordoptions">WriteRecordOptions</a></code> |

**Since:** 0.0.1

--------------------


### Interfaces


#### AggregateResult

| Prop          | Type                                 | Description                                    | Since |
| ------------- | ------------------------------------ | ---------------------------------------------- | ----- |
| **`buckets`** | <code>AggregateResultBucket[]</code> | The aggregated buckets in chronological order. | 0.0.1 |


#### AggregateResultBucket

| Prop            | Type                                | Description                                         | Since |
| --------------- | ----------------------------------- | --------------------------------------------------- | ----- |
| **`endDate`**   | <code>string</code>                 | The end of the bucket as ISO 8601 string.           | 0.0.1 |
| **`startDate`** | <code>string</code>                 | The start of the bucket as ISO 8601 string.         | 0.0.1 |
| **`values`**    | <code>AggregateResultValue[]</code> | The aggregated values for each requested operation. | 0.0.1 |


#### AggregateResultValue

| Prop            | Type                                                                  | Description                                                                                                                                                   | Since |
| --------------- | --------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`operation`** | <code><a href="#aggregationoperation">AggregationOperation</a></code> | The operation that produced this value.                                                                                                                       | 0.0.1 |
| **`value`**     | <code>number \| null</code>                                           | The aggregated value in the fixed unit of the data type (see <a href="#datatype">`DataType`</a>). If no data is available in the bucket, the value is `null`. | 0.0.1 |


#### AggregateOptions

| Prop             | Type                                                            | Description                                                                                                                                                                                                                                                                                    | Since |
| ---------------- | --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bucket`**     | <code><a href="#aggregationbucket">AggregationBucket</a></code> | The bucket size to group the results by. The buckets are aligned to `startDate`. The `day`, `week` and `month` buckets are calendar-aware (e.g. months have different lengths) and are based on the device's time zone. If `none`, a single bucket spanning the entire time range is returned. | 0.0.1 |
| **`dataType`**   | <code><a href="#datatype">DataType</a></code>                   | The data type to aggregate.                                                                                                                                                                                                                                                                    | 0.0.1 |
| **`endDate`**    | <code>string</code>                                             | The end of the time range (exclusive) as ISO 8601 string.                                                                                                                                                                                                                                      | 0.0.1 |
| **`operations`** | <code>AggregationOperation[]</code>                             | The operations to perform on the data in each bucket. See `aggregate(...)` for the supported combinations of data type and operation.                                                                                                                                                          | 0.0.1 |
| **`startDate`**  | <code>string</code>                                             | The start of the time range (inclusive) as ISO 8601 string.                                                                                                                                                                                                                                    | 0.0.1 |


#### PermissionStatus

| Prop              | Type                                  | Description                                         | Since |
| ----------------- | ------------------------------------- | --------------------------------------------------- | ----- |
| **`permissions`** | <code>HealthPermissionStatus[]</code> | The permission status for each requested data type. | 0.0.1 |


#### HealthPermissionStatus

| Prop           | Type                                                                    | Description                                                                                | Since |
| -------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`dataType`** | <code><a href="#datatype">DataType</a></code>                           | The data type this status belongs to.                                                      | 0.0.1 |
| **`read`**     | <code><a href="#healthpermissionstate">HealthPermissionState</a></code> | The read permission status. Only set if the data type was included in the `read` option.   | 0.0.1 |
| **`write`**    | <code><a href="#healthpermissionstate">HealthPermissionState</a></code> | The write permission status. Only set if the data type was included in the `write` option. | 0.0.1 |


#### CheckPermissionsOptions

| Prop        | Type                            | Description                                              | Since |
| ----------- | ------------------------------- | -------------------------------------------------------- | ----- |
| **`read`**  | <code>DataType[]</code>         | The data types to check the read permission status for.  | 0.0.1 |
| **`write`** | <code>WritableDataType[]</code> | The data types to check the write permission status for. | 0.0.1 |


#### IsAvailableResult

| Prop            | Type                                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                | Since |
| --------------- | ----------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code>                                                    | Whether or not health data is available on the device.                                                                                                                                                                                                                                                                                                                                                                     | 0.0.1 |
| **`reason`**    | <code><a href="#isavailablereason">IsAvailableReason</a> \| null</code> | The reason why health data is not available on the device. If health data is available, the value is `null`. On **Android**, the reason `health-connect-not-installed` means that the device is supported but the Health Connect app is not installed. Call `installHealthConnect()` to prompt the user to install it. The reason `health-connect-update-required` means that the installed Health Connect app is too old. | 0.0.1 |


#### ReadRecordsResult

| Prop          | Type                        | Description                 | Since |
| ------------- | --------------------------- | --------------------------- | ----- |
| **`records`** | <code>HealthRecord[]</code> | The records that were read. | 0.0.1 |


#### HealthRecord

A single health record.

| Prop                 | Type                                          | Description                                                                                                                                                                                                                                                                                                                   | Since |
| -------------------- | --------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`dataType`**       | <code><a href="#datatype">DataType</a></code> | The data type of the record.                                                                                                                                                                                                                                                                                                  | 0.0.1 |
| **`diastolic`**      | <code>number</code>                           | The diastolic blood pressure in millimeters of mercury. Only set for the `BLOOD_PRESSURE` data type.                                                                                                                                                                                                                          | 0.0.1 |
| **`endDate`**        | <code>string</code>                           | The end of the record as ISO 8601 string. For instantaneous records (e.g. weight), this is the same as `startDate`.                                                                                                                                                                                                           | 0.0.1 |
| **`id`**             | <code>string \| null</code>                   | The unique identifier of the record. If the platform does not provide an identifier, the value is `null`.                                                                                                                                                                                                                     | 0.0.1 |
| **`sourceBundleId`** | <code>string \| null</code>                   | The bundle identifier (iOS) or package name (Android) of the app that wrote the record.                                                                                                                                                                                                                                       | 0.0.1 |
| **`sourceName`**     | <code>string \| null</code>                   | The display name of the source that wrote the record. On **Android**, Health Connect does not provide a display name, so the value is always `null`.                                                                                                                                                                          | 0.0.1 |
| **`stages`**         | <code>SleepStageSample[]</code>               | The sleep stages of the record. Only set for the `SLEEP` data type. On **Android**, a record is a sleep session that contains all its stages. On **iOS**, HealthKit has no session concept. Each record is a single sleep analysis sample with exactly one stage.                                                             | 0.0.1 |
| **`startDate`**      | <code>string</code>                           | The start of the record as ISO 8601 string.                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`systolic`**       | <code>number</code>                           | The systolic blood pressure in millimeters of mercury. Only set for the `BLOOD_PRESSURE` data type.                                                                                                                                                                                                                           | 0.0.1 |
| **`unit`**           | <code>string</code>                           | The fixed unit of the value (see <a href="#datatype">`DataType`</a>).                                                                                                                                                                                                                                                         | 0.0.1 |
| **`value`**          | <code>number \| null</code>                   | The value of the record in the fixed unit of the data type (see `DataType`). For the `BLOOD_PRESSURE` data type, the value is `null` (see `systolic` and `diastolic`). For the `SLEEP` data type, the value is the duration in minutes. Non-finite values reported by third-party data sources are also normalized to `null`. | 0.0.1 |


#### SleepStageSample

A single sleep stage within a sleep record.

| Prop            | Type                                              | Description                                      | Since |
| --------------- | ------------------------------------------------- | ------------------------------------------------ | ----- |
| **`endDate`**   | <code>string</code>                               | The end of the sleep stage as ISO 8601 string.   | 0.0.1 |
| **`stage`**     | <code><a href="#sleepstage">SleepStage</a></code> | The sleep stage.                                 | 0.0.1 |
| **`startDate`** | <code>string</code>                               | The start of the sleep stage as ISO 8601 string. | 0.0.1 |


#### ReadRecordsOptions

| Prop              | Type                                          | Description                                                                                                                                                        | Default           | Since |
| ----------------- | --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----------------- | ----- |
| **`ascending`**   | <code>boolean</code>                          | Whether to return the records in ascending order by start date.                                                                                                    | <code>true</code> | 0.0.1 |
| **`dataOrigins`** | <code>string[]</code>                         | Filter the records by the apps that wrote them, identified by bundle identifier (iOS) or package name (Android). An empty array is treated like an omitted filter. |                   | 0.0.1 |
| **`dataType`**    | <code><a href="#datatype">DataType</a></code> | The data type to read. The `WORKOUT` data type is not supported by this method. Use `readWorkouts(...)` instead.                                                   |                   | 0.0.1 |
| **`endDate`**     | <code>string</code>                           | The end of the time range (exclusive) as ISO 8601 string.                                                                                                          |                   | 0.0.1 |
| **`limit`**       | <code>number</code>                           | The maximum number of records to return. If not set, all records in the time range are returned.                                                                   |                   | 0.0.1 |
| **`startDate`**   | <code>string</code>                           | The start of the time range (inclusive) as ISO 8601 string.                                                                                                        |                   | 0.0.1 |


#### ReadWorkoutsResult

| Prop           | Type                   | Description                  | Since |
| -------------- | ---------------------- | ---------------------------- | ----- |
| **`workouts`** | <code>Workout[]</code> | The workouts that were read. | 0.0.1 |


#### Workout

A single workout.

| Prop                 | Type                                                | Description                                                                                                                                                                                                                                          | Since |
| -------------------- | --------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`duration`**       | <code>number</code>                                 | The duration of the workout in seconds. On **Android**, this is the wall-clock time between `startDate` and `endDate`. On **iOS**, paused intervals are excluded.                                                                                    | 0.0.1 |
| **`endDate`**        | <code>string</code>                                 | The end of the workout as ISO 8601 string.                                                                                                                                                                                                           | 0.0.1 |
| **`id`**             | <code>string \| null</code>                         | The unique identifier of the workout. If the platform does not provide an identifier, the value is `null`.                                                                                                                                           | 0.0.1 |
| **`rawWorkoutType`** | <code>string \| null</code>                         | The platform-specific raw workout type. On **Android**, this is the numeric Health Connect exercise type. On **iOS**, this is the numeric `HKWorkoutActivityType` raw value. Use this value to distinguish workout types that are mapped to `OTHER`. | 0.0.1 |
| **`sourceBundleId`** | <code>string \| null</code>                         | The bundle identifier (iOS) or package name (Android) of the app that wrote the workout.                                                                                                                                                             | 0.0.1 |
| **`sourceName`**     | <code>string \| null</code>                         | The display name of the source that wrote the workout. On **Android**, Health Connect does not provide a display name, so the value is always `null`.                                                                                                | 0.0.1 |
| **`startDate`**      | <code>string</code>                                 | The start of the workout as ISO 8601 string.                                                                                                                                                                                                         | 0.0.1 |
| **`totalCalories`**  | <code>number \| null</code>                         | The total energy burned during the workout in kilocalories. If the platform does not provide a value, the value is `null`. On **iOS**, the value is only available on iOS 16 and later.                                                              | 0.0.1 |
| **`totalDistance`**  | <code>number \| null</code>                         | The total distance covered during the workout in meters. If the platform does not provide a value, the value is `null`. On **iOS**, the value is only available on iOS 16 and later.                                                                 | 0.0.1 |
| **`workoutType`**    | <code><a href="#workouttype">WorkoutType</a></code> | The type of the workout. If the platform-specific workout type has no equivalent in <a href="#workouttype">`WorkoutType`</a>, the value is `OTHER` and the platform-specific value is available in `rawWorkoutType`.                                 | 0.0.1 |


#### ReadWorkoutsOptions

| Prop              | Type                                                | Description                                                                                        | Since |
| ----------------- | --------------------------------------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`endDate`**     | <code>string</code>                                 | The end of the time range (exclusive) as ISO 8601 string.                                          | 0.0.1 |
| **`limit`**       | <code>number</code>                                 | The maximum number of workouts to return. If not set, all workouts in the time range are returned. | 0.0.1 |
| **`startDate`**   | <code>string</code>                                 | The start of the time range (inclusive) as ISO 8601 string.                                        | 0.0.1 |
| **`workoutType`** | <code><a href="#workouttype">WorkoutType</a></code> | Filter the workouts by workout type.                                                               | 0.0.1 |


#### RequestPermissionsOptions

| Prop        | Type                            | Description                                         | Since |
| ----------- | ------------------------------- | --------------------------------------------------- | ----- |
| **`read`**  | <code>DataType[]</code>         | The data types to request the read permission for.  | 0.0.1 |
| **`write`** | <code>WritableDataType[]</code> | The data types to request the write permission for. | 0.0.1 |


#### WriteRecordOptions

| Prop            | Type                                                              | Description                                                                                                                                                                                     | Since |
| --------------- | ----------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`dataType`**  | <code><a href="#writabledatatype">WritableDataType</a></code>     | The data type to write.                                                                                                                                                                         | 0.0.1 |
| **`diastolic`** | <code>number</code>                                               | The diastolic blood pressure in millimeters of mercury. Required for the `BLOOD_PRESSURE` data type.                                                                                            | 0.0.1 |
| **`endDate`**   | <code>string</code>                                               | The end of the record as ISO 8601 string. Required for the `HYDRATION`, `STEPS` and `WORKOUT` data types. Ignored for instantaneous data types (e.g. `WEIGHT`).                                 | 0.0.1 |
| **`startDate`** | <code>string</code>                                               | The start of the record as ISO 8601 string.                                                                                                                                                     | 0.0.1 |
| **`systolic`**  | <code>number</code>                                               | The systolic blood pressure in millimeters of mercury. Required for the `BLOOD_PRESSURE` data type.                                                                                             | 0.0.1 |
| **`value`**     | <code>number</code>                                               | The value of the record in the fixed unit of the data type (see `DataType`). Required for the `BLOOD_GLUCOSE`, `HEIGHT`, `HYDRATION`, `STEPS` and `WEIGHT` data types. Must be a finite number. | 0.0.1 |
| **`workout`**   | <code><a href="#writerecordworkout">WriteRecordWorkout</a></code> | The workout to write. Required for the `WORKOUT` data type.                                                                                                                                     | 0.0.1 |


#### WriteRecordWorkout

| Prop                | Type                                                | Description                                                                                                                                                                                                                         | Since |
| ------------------- | --------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`totalCalories`** | <code>number</code>                                 | The total energy burned during the workout in kilocalories. On **Android**, this additionally writes an active calories record for the workout time range, which requires the write permission for the `ACTIVE_CALORIES` data type. | 0.0.1 |
| **`totalDistance`** | <code>number</code>                                 | The total distance covered during the workout in meters. On **Android**, this additionally writes a distance record for the workout time range, which requires the write permission for the `DISTANCE` data type.                   | 0.0.1 |
| **`workoutType`**   | <code><a href="#workouttype">WorkoutType</a></code> | The type of the workout. If the platform has no equivalent for the given workout type, it is written as the platform's generic workout type.                                                                                        | 0.0.1 |


### Type Aliases


#### AggregationOperation

The operation to perform when aggregating data.

<code>'average' | 'maximum' | 'minimum' | 'sum'</code>


#### AggregationBucket

The bucket size to group aggregation results by.

<code>'day' | 'hour' | 'month' | 'none' | 'week'</code>


#### HealthPermissionState

The status of a single health permission.

The value `unknown` is used on iOS for read permissions after the first
request since HealthKit deliberately hides whether a read permission was
granted or denied.

<code>'denied' | 'granted' | 'prompt' | 'unknown'</code>


#### WritableDataType

The data types that can be written with `writeRecord(...)`.

<code><a href="#datatype">DataType.BloodGlucose</a> | <a href="#datatype">DataType.BloodPressure</a> | <a href="#datatype">DataType.Height</a> | <a href="#datatype">DataType.Hydration</a> | <a href="#datatype">DataType.Steps</a> | <a href="#datatype">DataType.Weight</a> | <a href="#datatype">DataType.Workout</a></code>


#### IsAvailableReason

The reason why health data is not available on the device.

<code>'health-connect-not-installed' | 'health-connect-update-required' | 'not-supported'</code>


### Enums


#### DataType

| Members                    | Value                                 | Description                                                                                                                                                                                                                                                                                                                               | Since |
| -------------------------- | ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ActiveCalories`**       | <code>'ACTIVE_CALORIES'</code>        | Active energy burned in kilocalories (`kcal`).                                                                                                                                                                                                                                                                                            | 0.0.1 |
| **`BloodGlucose`**         | <code>'BLOOD_GLUCOSE'</code>          | Blood glucose level in millimoles per liter (`mmol/L`).                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`BloodPressure`**        | <code>'BLOOD_PRESSURE'</code>         | Blood pressure in millimeters of mercury (`mmHg`). Records of this data type use the `systolic` and `diastolic` properties instead of `value`.                                                                                                                                                                                            | 0.0.1 |
| **`BodyFat`**              | <code>'BODY_FAT'</code>               | Body fat percentage (`percent`, 0 to 100).                                                                                                                                                                                                                                                                                                | 0.0.1 |
| **`BodyTemperature`**      | <code>'BODY_TEMPERATURE'</code>       | Body temperature in degrees Celsius (`celsius`).                                                                                                                                                                                                                                                                                          | 0.0.1 |
| **`Distance`**             | <code>'DISTANCE'</code>               | Distance covered in meters (`m`). On **iOS**, this maps to walking and running distance.                                                                                                                                                                                                                                                  | 0.0.1 |
| **`FloorsClimbed`**        | <code>'FLOORS_CLIMBED'</code>         | Floors climbed (`count`).                                                                                                                                                                                                                                                                                                                 | 0.0.1 |
| **`HeartRate`**            | <code>'HEART_RATE'</code>             | Heart rate in beats per minute (`bpm`).                                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`HeartRateVariability`** | <code>'HEART_RATE_VARIABILITY'</code> | Heart rate variability in milliseconds (`ms`). **Attention**: The platforms measure different metrics! Android (Health Connect) provides RMSSD values while iOS (HealthKit) provides SDNN values. The values are exposed as-is and are **not** comparable across platforms.                                                               | 0.0.1 |
| **`Height`**               | <code>'HEIGHT'</code>                 | Height in meters (`m`).                                                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`Hydration`**            | <code>'HYDRATION'</code>              | Water intake in liters (`L`).                                                                                                                                                                                                                                                                                                             | 0.0.1 |
| **`OxygenSaturation`**     | <code>'OXYGEN_SATURATION'</code>      | Blood oxygen saturation percentage (`percent`, 0 to 100).                                                                                                                                                                                                                                                                                 | 0.0.1 |
| **`RespiratoryRate`**      | <code>'RESPIRATORY_RATE'</code>       | Respiratory rate in breaths per minute (`breaths/min`).                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`RestingHeartRate`**     | <code>'RESTING_HEART_RATE'</code>     | Resting heart rate in beats per minute (`bpm`).                                                                                                                                                                                                                                                                                           | 0.0.1 |
| **`Sleep`**                | <code>'SLEEP'</code>                  | Sleep sessions with sleep stages. The record value is the duration in minutes (`min`).                                                                                                                                                                                                                                                    | 0.0.1 |
| **`Steps`**                | <code>'STEPS'</code>                  | Steps taken (`count`).                                                                                                                                                                                                                                                                                                                    | 0.0.1 |
| **`TotalCalories`**        | <code>'TOTAL_CALORIES'</code>         | Total energy burned in kilocalories (`kcal`). **Attention**: The platforms compose this value differently! Android (Health Connect) has a dedicated total calories record type while on iOS (HealthKit) the value is computed as the sum of active and basal energy burned. On iOS, this data type is only supported by `aggregate(...)`. | 0.0.1 |
| **`Vo2Max`**               | <code>'VO2_MAX'</code>                | Maximal oxygen consumption in milliliters per kilogram of body weight per minute (`mL/kg/min`).                                                                                                                                                                                                                                           | 0.0.1 |
| **`Weight`**               | <code>'WEIGHT'</code>                 | Body weight in kilograms (`kg`).                                                                                                                                                                                                                                                                                                          | 0.0.1 |
| **`Workout`**              | <code>'WORKOUT'</code>                | Workouts (exercise sessions). This data type is used for permission requests, `readWorkouts(...)` and `writeRecord(...)`. It is not supported by `readRecords(...)`.                                                                                                                                                                      | 0.0.1 |


#### SleepStage

| Members       | Value                  | Description                                      | Since |
| ------------- | ---------------------- | ------------------------------------------------ | ----- |
| **`Awake`**   | <code>'AWAKE'</code>   | The user is awake.                               | 0.0.1 |
| **`Deep`**    | <code>'DEEP'</code>    | The user is in deep sleep.                       | 0.0.1 |
| **`InBed`**   | <code>'IN_BED'</code>  | The user is in bed but not necessarily sleeping. | 0.0.1 |
| **`Light`**   | <code>'LIGHT'</code>   | The user is in light sleep.                      | 0.0.1 |
| **`Rem`**     | <code>'REM'</code>     | The user is in REM sleep.                        | 0.0.1 |
| **`Unknown`** | <code>'UNKNOWN'</code> | The sleep stage is unknown.                      | 0.0.1 |


#### WorkoutType

| Members                             | Value                                           | Description                                                                                                                                                                  | Since |
| ----------------------------------- | ----------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`AmericanFootball`**              | <code>'AMERICAN_FOOTBALL'</code>                |                                                                                                                                                                              | 0.0.1 |
| **`AustralianFootball`**            | <code>'AUSTRALIAN_FOOTBALL'</code>              |                                                                                                                                                                              | 0.0.1 |
| **`Badminton`**                     | <code>'BADMINTON'</code>                        |                                                                                                                                                                              | 0.0.1 |
| **`Baseball`**                      | <code>'BASEBALL'</code>                         |                                                                                                                                                                              | 0.0.1 |
| **`Basketball`**                    | <code>'BASKETBALL'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Biking`**                        | <code>'BIKING'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`Boxing`**                        | <code>'BOXING'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`Calisthenics`**                  | <code>'CALISTHENICS'</code>                     | On **iOS**, this is written as functional strength training and read as `STRENGTH_TRAINING`.                                                                                 | 0.0.1 |
| **`Cricket`**                       | <code>'CRICKET'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`Dancing`**                       | <code>'DANCING'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`Elliptical`**                    | <code>'ELLIPTICAL'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Fencing`**                       | <code>'FENCING'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`Golf`**                          | <code>'GOLF'</code>                             |                                                                                                                                                                              | 0.0.1 |
| **`Gymnastics`**                    | <code>'GYMNASTICS'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Handball`**                      | <code>'HANDBALL'</code>                         |                                                                                                                                                                              | 0.0.1 |
| **`HighIntensityIntervalTraining`** | <code>'HIGH_INTENSITY_INTERVAL_TRAINING'</code> |                                                                                                                                                                              | 0.0.1 |
| **`Hiking`**                        | <code>'HIKING'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`IceHockey`**                     | <code>'ICE_HOCKEY'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`IceSkating`**                    | <code>'ICE_SKATING'</code>                      |                                                                                                                                                                              | 0.0.1 |
| **`MartialArts`**                   | <code>'MARTIAL_ARTS'</code>                     |                                                                                                                                                                              | 0.0.1 |
| **`Other`**                         | <code>'OTHER'</code>                            |                                                                                                                                                                              | 0.0.1 |
| **`Paddling`**                      | <code>'PADDLING'</code>                         |                                                                                                                                                                              | 0.0.1 |
| **`Pilates`**                       | <code>'PILATES'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`Racquetball`**                   | <code>'RACQUETBALL'</code>                      |                                                                                                                                                                              | 0.0.1 |
| **`RockClimbing`**                  | <code>'ROCK_CLIMBING'</code>                    |                                                                                                                                                                              | 0.0.1 |
| **`Rowing`**                        | <code>'ROWING'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`RowingMachine`**                 | <code>'ROWING_MACHINE'</code>                   | On **iOS**, this is written and read as `ROWING`.                                                                                                                            | 0.0.1 |
| **`Rugby`**                         | <code>'RUGBY'</code>                            |                                                                                                                                                                              | 0.0.1 |
| **`Running`**                       | <code>'RUNNING'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`RunningTreadmill`**              | <code>'RUNNING_TREADMILL'</code>                | On **iOS**, this is written and read as `RUNNING`.                                                                                                                           | 0.0.1 |
| **`Sailing`**                       | <code>'SAILING'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`ScubaDiving`**                   | <code>'SCUBA_DIVING'</code>                     |                                                                                                                                                                              | 0.0.1 |
| **`Skiing`**                        | <code>'SKIING'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`Snowboarding`**                  | <code>'SNOWBOARDING'</code>                     |                                                                                                                                                                              | 0.0.1 |
| **`Soccer`**                        | <code>'SOCCER'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`Softball`**                      | <code>'SOFTBALL'</code>                         |                                                                                                                                                                              | 0.0.1 |
| **`Squash`**                        | <code>'SQUASH'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`StairClimbing`**                 | <code>'STAIR_CLIMBING'</code>                   |                                                                                                                                                                              | 0.0.1 |
| **`StrengthTraining`**              | <code>'STRENGTH_TRAINING'</code>                |                                                                                                                                                                              | 0.0.1 |
| **`Stretching`**                    | <code>'STRETCHING'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Surfing`**                       | <code>'SURFING'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`Swimming`**                      | <code>'SWIMMING'</code>                         | Generic swimming (iOS only). On **Android**, this is written as the generic workout type. Use `SWIMMING_POOL` or `SWIMMING_OPEN_WATER` for cross-platform swimming workouts. | 0.0.1 |
| **`SwimmingOpenWater`**             | <code>'SWIMMING_OPEN_WATER'</code>              | On **iOS**, this is written and read as `SWIMMING`.                                                                                                                          | 0.0.1 |
| **`SwimmingPool`**                  | <code>'SWIMMING_POOL'</code>                    | On **iOS**, this is written and read as `SWIMMING`.                                                                                                                          | 0.0.1 |
| **`TableTennis`**                   | <code>'TABLE_TENNIS'</code>                     |                                                                                                                                                                              | 0.0.1 |
| **`Tennis`**                        | <code>'TENNIS'</code>                           |                                                                                                                                                                              | 0.0.1 |
| **`Volleyball`**                    | <code>'VOLLEYBALL'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Walking`**                       | <code>'WALKING'</code>                          |                                                                                                                                                                              | 0.0.1 |
| **`WaterPolo`**                     | <code>'WATER_POLO'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Wheelchair`**                    | <code>'WHEELCHAIR'</code>                       |                                                                                                                                                                              | 0.0.1 |
| **`Yoga`**                          | <code>'YOGA'</code>                             |                                                                                                                                                                              | 0.0.1 |

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It is built for correctness and honesty with health data: it reports iOS permission states accurately, surfaces the real availability states on Android, and returns strongly-typed, aggregation-first results. It also ships the setup and policy documentation you need to pass App Review and the required Google Play health-data declaration — the steps that most often decide whether a health app ships — and it's backed by dedicated support.

### Why are read permissions reported as `unknown` on iOS?

HealthKit deliberately hides whether a read permission was granted or denied to prevent apps from inferring sensitive information (an app that knows it was denied access to, say, blood glucose data could conclude the user is likely diabetic).
A denied read permission behaves exactly like the absence of data.
Some plugins pretend read permissions are `granted` after a request — this plugin does not, because it would simply be wrong.
Design your app around the **presence of data**, not around the read permission status: request the permissions, query the data and show an appropriate empty state (e.g. "No data available. Check the Health app if you expected data here.") when nothing is returned.

### Why does my app get rejected by Google Play?

Every app that integrates with Health Connect must complete the **Health apps declaration** in the Google Play Console and be approved for the requested permission types (see the installation instructions above).
Also make sure your app only declares the health permissions it actually uses and provides a privacy policy.

### Why can't I read data older than 30 days on Android?

Health Connect only allows apps to read data from up to 30 days before the moment the permission was first granted.
If the permission is revoked and granted again, the 30-day window is calculated from the new grant.
Support for the `READ_HEALTH_DATA_HISTORY` permission is planned as a fast-follow feature.

### Is health data available on the web?

No. There is no web API for health data. All methods reject with an unimplemented error on the web.

### Why do steps from my phone and smartwatch not add up?

They do — that's the point of `aggregate(...)`. The platform deduplicates overlapping data from multiple sources automatically.
If you sum up the individual records from `readRecords(...)` yourself, you will count overlapping data twice. Use `aggregate(...)` for totals.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Pedometer](https://capawesome.io/docs/sdks/capacitor/pedometer/): Live step counting from the device's motion sensors.
- [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/): Track workout routes in the background.
- [Local Notifications](https://capawesome.io/docs/sdks/capacitor/local-notifications/): Remind users to log their health data.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/health/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/health/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/health/LICENSE).
