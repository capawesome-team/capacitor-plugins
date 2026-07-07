# Capacitor Alarm Plugin

Capacitor plugin to create system alarms and timers.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ⏰ **Real system alarms**: Create alarms that break through Silent Mode and Focus — unlike local notifications.
- ⏲️ **Timers**: Create countdown timers in the system clock app on Android.
- 📋 **Alarm management**: List and cancel the alarms created by your app on iOS.
- 🍏 **AlarmKit**: One of the first plugins to support Apple's new AlarmKit framework on iOS 26.
- 🔐 **Permissions**: Check and request the alarm authorization with a single call.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-alarm` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-alarm
npx cap sync
```

### Android

The plugin already declares the `com.android.alarm.permission.SET_ALARM` permission in its manifest. This is a normal permission that is granted automatically, so no additional configuration is required.

### iOS

This plugin uses Apple's [AlarmKit](https://developer.apple.com/documentation/alarmkit) framework, which requires **iOS 26 or later**. On older versions, all methods except `isAvailable()` reject as unavailable.

Add the `NSAlarmKitUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why the app needs to schedule alarms:

```xml
<key>NSAlarmKitUsageDescription</key>
<string>The app uses alarms to notify you at the times you choose.</string>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Alarm, Weekday } from '@capawesome/capacitor-alarm';

const cancelAlarm = async (id: string) => {
  await Alarm.cancelAlarm({ id });
};

const checkPermissions = async () => {
  const { alarms } = await Alarm.checkPermissions();
  return alarms;
};

const createAlarm = async () => {
  const { id } = await Alarm.createAlarm({
    hour: 6,
    minute: 30,
    label: 'Wake up',
    days: [Weekday.Monday, Weekday.Friday],
  });
  return id;
};

const createTimer = async () => {
  await Alarm.createTimer({
    duration: 300,
    label: 'Tea',
  });
};

const getAlarms = async () => {
  const { alarms } = await Alarm.getAlarms();
  return alarms;
};

const isAvailable = async () => {
  const { available } = await Alarm.isAvailable();
  return available;
};

const openAlarms = async () => {
  await Alarm.openAlarms();
};

const requestPermissions = async () => {
  const { alarms } = await Alarm.requestPermissions();
  return alarms;
};
```

## API

<docgen-index>

* [`cancelAlarm(...)`](#cancelalarm)
* [`checkPermissions()`](#checkpermissions)
* [`createAlarm(...)`](#createalarm)
* [`createTimer(...)`](#createtimer)
* [`getAlarms()`](#getalarms)
* [`isAvailable()`](#isavailable)
* [`openAlarms()`](#openalarms)
* [`requestPermissions()`](#requestpermissions)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### cancelAlarm(...)

```typescript
cancelAlarm(options: CancelAlarmOptions) => Promise<void>
```

Cancel an alarm that was created by the app.

Only available on iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#cancelalarmoptions">CancelAlarmOptions</a></code> |

**Since:** 0.1.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permission to schedule alarms.

On Android, this method always returns `granted` since
alarms are created via the system clock app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### createAlarm(...)

```typescript
createAlarm(options: CreateAlarmOptions) => Promise<CreateAlarmResult>
```

Create a new alarm.

On Android, the alarm is created in the system clock app.
The app has no access to the created alarm afterwards.

On iOS, the alarm is owned by the app and can be listed
and canceled using the `getAlarms(...)` and `cancelAlarm(...)`
methods.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#createalarmoptions">CreateAlarmOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createalarmresult">CreateAlarmResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### createTimer(...)

```typescript
createTimer(options: CreateTimerOptions) => Promise<void>
```

Create a new countdown timer in the system clock app.

Only available on Android.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#createtimeroptions">CreateTimerOptions</a></code> |

**Since:** 0.1.0

--------------------


### getAlarms()

```typescript
getAlarms() => Promise<GetAlarmsResult>
```

Get all alarms that were created by the app.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#getalarmsresult">GetAlarmsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if alarms are available on this device.

On Android, this checks whether an app that can handle
alarms is installed. On iOS, this returns `true` if the
device runs iOS 26 or later.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### openAlarms()

```typescript
openAlarms() => Promise<void>
```

Open the list of alarms in the system clock app.

Only available on Android.

**Since:** 0.1.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to schedule alarms.

On Android, this method always returns `granted` since
alarms are created via the system clock app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CancelAlarmOptions

| Prop     | Type                | Description                                   | Since |
| -------- | ------------------- | --------------------------------------------- | ----- |
| **`id`** | <code>string</code> | The unique identifier of the alarm to cancel. | 0.1.0 |


#### PermissionStatus

| Prop         | Type                                                        | Description                            | Since |
| ------------ | ----------------------------------------------------------- | -------------------------------------- | ----- |
| **`alarms`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state of scheduling alarms. | 0.1.0 |


#### CreateAlarmResult

| Prop     | Type                        | Description                                                                                                                       | Since |
| -------- | --------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`** | <code>string \| null</code> | The unique identifier of the created alarm. On Android, this is always `null` since the alarm is created in the system clock app. | 0.1.0 |


#### CreateAlarmOptions

| Prop          | Type                                                                            | Description                                                                                                                      | Since |
| ------------- | ------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`android`** | <code><a href="#createalarmandroidoptions">CreateAlarmAndroidOptions</a></code> | Android-specific options. Only available on Android.                                                                             | 0.1.0 |
| **`days`**    | <code>Weekday[]</code>                                                          | The days of the week on which the alarm repeats. If not provided, the alarm fires once at the next occurrence of the given time. | 0.1.0 |
| **`hour`**    | <code>number</code>                                                             | The hour of the alarm (0-23).                                                                                                    | 0.1.0 |
| **`label`**   | <code>string</code>                                                             | The label of the alarm.                                                                                                          | 0.1.0 |
| **`minute`**  | <code>number</code>                                                             | The minute of the alarm (0-59).                                                                                                  | 0.1.0 |


#### CreateAlarmAndroidOptions

| Prop          | Type                 | Description                                                                                                                                | Default            | Since |
| ------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ------------------ | ----- |
| **`skipUi`**  | <code>boolean</code> | Whether or not to create the alarm without showing the user interface of the system clock app. Only available on Android.                  | <code>false</code> | 0.1.0 |
| **`vibrate`** | <code>boolean</code> | Whether or not the alarm should vibrate. If not provided, the default behavior of the system clock app is used. Only available on Android. |                    | 0.1.0 |


#### CreateTimerOptions

| Prop           | Type                                                                            | Description                                          | Since |
| -------------- | ------------------------------------------------------------------------------- | ---------------------------------------------------- | ----- |
| **`android`**  | <code><a href="#createtimerandroidoptions">CreateTimerAndroidOptions</a></code> | Android-specific options. Only available on Android. | 0.1.0 |
| **`duration`** | <code>number</code>                                                             | The length of the timer in seconds (1-86400).        | 0.1.0 |
| **`label`**    | <code>string</code>                                                             | The label of the timer.                              | 0.1.0 |


#### CreateTimerAndroidOptions

| Prop         | Type                 | Description                                                                                                               | Default            | Since |
| ------------ | -------------------- | ------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`skipUi`** | <code>boolean</code> | Whether or not to create the timer without showing the user interface of the system clock app. Only available on Android. | <code>false</code> | 0.1.0 |


#### GetAlarmsResult

| Prop         | Type                     | Description                              | Since |
| ------------ | ------------------------ | ---------------------------------------- | ----- |
| **`alarms`** | <code>AlarmInfo[]</code> | The alarms that were created by the app. | 0.1.0 |


#### AlarmInfo

| Prop          | Type                         | Description                         | Since |
| ------------- | ---------------------------- | ----------------------------------- | ----- |
| **`enabled`** | <code>boolean \| null</code> | Whether or not the alarm is active. | 0.1.0 |
| **`hour`**    | <code>number \| null</code>  | The hour of the alarm (0-23).       | 0.1.0 |
| **`id`**      | <code>string</code>          | The unique identifier of the alarm. | 0.1.0 |
| **`label`**   | <code>string \| null</code>  | The label of the alarm.             | 0.1.0 |
| **`minute`**  | <code>number \| null</code>  | The minute of the alarm (0-59).     | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                         | Since |
| --------------- | -------------------- | --------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not alarms are available on this device. | 0.1.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### Weekday

| Members         | Value                    | Since |
| --------------- | ------------------------ | ----- |
| **`Friday`**    | <code>'FRIDAY'</code>    | 0.1.0 |
| **`Monday`**    | <code>'MONDAY'</code>    | 0.1.0 |
| **`Saturday`**  | <code>'SATURDAY'</code>  | 0.1.0 |
| **`Sunday`**    | <code>'SUNDAY'</code>    | 0.1.0 |
| **`Thursday`**  | <code>'THURSDAY'</code>  | 0.1.0 |
| **`Tuesday`**   | <code>'TUESDAY'</code>   | 0.1.0 |
| **`Wednesday`** | <code>'WEDNESDAY'</code> | 0.1.0 |

</docgen-api>

## Alarms vs. Local Notifications

Alarms are not the same as local notifications. Use an alarm when the user must be interrupted at a specific time, and a local notification for everything else.

|                                | Alarm                       | Local Notification            |
| ------------------------------ | --------------------------- | ----------------------------- |
| Breaks through Silent Mode     | ✅                          | ❌                            |
| Breaks through Focus modes     | ✅                          | ❌                            |
| Plays sound until dismissed    | ✅                          | ❌                            |
| Custom content and actions     | ❌                          | ✅                            |

## Platform Support

The two platforms have fundamentally different alarm models, which this plugin exposes honestly instead of hiding behind a false abstraction:

- **Android**: Alarms and timers are created in the **system clock app** via the [`AlarmClock`](https://developer.android.com/reference/android/provider/AlarmClock) intent API. This is fire-and-forget: once created, the alarm belongs to the clock app and the OS offers no API to list or cancel it. The `createAlarm(...)` method therefore returns `null` as the alarm identifier, and `getAlarms()` and `cancelAlarm(...)` reject as unimplemented.
- **iOS (26+)**: Alarms are created with [AlarmKit](https://developer.apple.com/documentation/alarmkit) and are **owned by your app**: you can list them with `getAlarms()` and cancel them with `cancelAlarm(...)`. Timers created with AlarmKit require a Live Activity widget extension in the app, which is why `createTimer(...)` is currently not supported on iOS. The `openAlarms()` method is not supported on iOS because the system clock app cannot be opened via a public API.

| Method                  | Android | iOS (26+) |
| ----------------------- | ------- | --------- |
| `cancelAlarm(...)`      | ❌      | ✅        |
| `checkPermissions()`    | ✅      | ✅        |
| `createAlarm(...)`      | ✅      | ✅        |
| `createTimer(...)`      | ✅      | ❌        |
| `getAlarms()`           | ❌      | ✅        |
| `isAvailable()`         | ✅      | ✅        |
| `openAlarms()`          | ✅      | ❌        |
| `requestPermissions()`  | ✅      | ✅        |

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/alarm/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/alarm/LICENSE).
