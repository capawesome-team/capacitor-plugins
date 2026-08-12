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
- 🤝 **Compatibility**: Works alongside the [Background Task](https://capawesome.io/docs/sdks/capacitor/background-task/), [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/) and [Silent Mode](https://capawesome.io/docs/sdks/capacitor/silent-mode/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Alarm plugin is typically used when the user must be reliably interrupted at a specific time, for example:

- **Wake-up alarms**: Create alarms that break through Silent Mode and Focus, for example for morning routines in sleep or fitness apps.
- **Recurring reminders**: Repeat alarms on specific weekdays, such as a medication reminder every Monday and Friday.
- **Countdown timers**: Start timers in the system clock app on Android, for example for cooking or workout apps.
- **Alarm management**: List and cancel the alarms created by your app on iOS to build your own alarm overview.

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

The following examples show how to check availability, check and request permissions, create alarms and timers, list and cancel alarms, and open the system clock app.

### Check if alarms are available

Before creating an alarm, check if alarms are available on the device. On Android, this checks whether an app that can handle alarms is installed. On iOS, this returns `true` if the device runs iOS 26 or later:

```typescript
import { Alarm } from '@capawesome/capacitor-alarm';

const isAvailable = async () => {
  const { available } = await Alarm.isAvailable();
  return available;
};
```

### Check and request permissions

Check and request the permission to schedule alarms. On Android, both methods always return `granted` since alarms are created via the system clock app:

```typescript
import { Alarm } from '@capawesome/capacitor-alarm';

const checkPermissions = async () => {
  const { alarms } = await Alarm.checkPermissions();
  return alarms;
};

const requestPermissions = async () => {
  const { alarms } = await Alarm.requestPermissions();
  return alarms;
};
```

### Create an alarm

Create a one-time or repeating alarm by providing the hour, the minute, and optionally a label and the weekdays on which the alarm repeats. On Android, the alarm is created in the system clock app and the returned `id` is `null`. On iOS, the alarm is owned by your app and can be listed and canceled later:

```typescript
import { Alarm, Weekday } from '@capawesome/capacitor-alarm';

const createAlarm = async () => {
  const { id } = await Alarm.createAlarm({
    hour: 6,
    minute: 30,
    label: 'Wake up',
    days: [Weekday.Monday, Weekday.Friday],
  });
  return id;
};
```

### Create a timer

Create a countdown timer in the system clock app by providing the duration in seconds. Only available on Android:

```typescript
import { Alarm } from '@capawesome/capacitor-alarm';

const createTimer = async () => {
  await Alarm.createTimer({
    duration: 300,
    label: 'Tea',
  });
};
```

### List and cancel alarms

Get all alarms that were created by your app and cancel them by their identifier. Only available on iOS:

```typescript
import { Alarm } from '@capawesome/capacitor-alarm';

const getAlarms = async () => {
  const { alarms } = await Alarm.getAlarms();
  return alarms;
};

const cancelAlarm = async (id: string) => {
  await Alarm.cancelAlarm({ id });
};
```

### Open the alarms in the system clock app

Open the list of alarms in the system clock app, for example so the user can review or edit an alarm. Only available on Android:

```typescript
import { Alarm } from '@capawesome/capacitor-alarm';

const openAlarms = async () => {
  await Alarm.openAlarms();
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

## FAQ

### How is this plugin different from other similar plugins?

It creates real system alarms that break through Silent Mode and Focus and keep sounding until dismissed — one of the first plugins to adopt Apple's new AlarmKit on iOS 26 — with repeating weekday alarms, permission handling in a single call, timers via the system clock app on Android, and alarm listing and cancellation on iOS. It exposes each platform's fundamentally different alarm model honestly through a fully typed API and stays actively maintained against the latest Capacitor and OS versions. If a normal reminder is enough, a local notification is the simpler choice; if the user must be reliably interrupted at a set time, this plugin is built for that.

### Why does the plugin require iOS 26 or later?

The plugin uses Apple's [AlarmKit](https://developer.apple.com/documentation/alarmkit) framework, which was introduced with iOS 26. On older iOS versions, all methods except `isAvailable()` reject as unavailable, so you can use `isAvailable()` to check for support at runtime.

### Why can't I list or cancel alarms on Android?

On Android, alarms are created in the system clock app via the `AlarmClock` intent API, which is fire-and-forget: once created, the alarm belongs to the clock app and the OS offers no API to list or cancel it. That is why `createAlarm(...)` returns `null` as the alarm identifier on Android and `getAlarms()` and `cancelAlarm(...)` reject as unimplemented. See the [Platform Support](#platform-support) section for details.

### What is the difference between an alarm and a local notification?

An alarm breaks through Silent Mode and Focus modes and plays a sound until it is dismissed, while a local notification does not. On the other hand, local notifications support custom content and actions. Use an alarm when the user must be interrupted at a specific time, and a local notification for everything else (see [Alarms vs. Local Notifications](#alarms-vs-local-notifications)).

### Why is creating timers not supported on iOS?

Timers created with AlarmKit require a Live Activity widget extension in the app, which is why `createTimer(...)` is currently not supported on iOS. On Android, timers are created in the system clock app.

### Do I need any permissions to create alarms?

On Android, the plugin already declares the `com.android.alarm.permission.SET_ALARM` permission in its manifest, which is granted automatically. On iOS, you must add the `NSAlarmKitUsageDescription` key to your `Info.plist` file (see [Installation](#installation)) and request the alarm authorization using `requestPermissions()`.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Background Task](https://capawesome.io/docs/sdks/capacitor/background-task/): Run background tasks in your app.
- [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/): Let the user pick the alarm time with a native date and time picker.
- [Silent Mode](https://capawesome.io/docs/sdks/capacitor/silent-mode/): Detect whether the device is in silent mode.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/alarm/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/alarm/LICENSE).
