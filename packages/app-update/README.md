# @capawesome/capacitor-app-update

Capacitor plugin that assists with app updates.
It supports retrieving app update information on **Android** and **iOS** and supports [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) on **Android**.

## Installation

```bash
npm install @capawesome/capacitor-app-update
npx cap sync
```

### Android Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidPlayCore` version of `com.google.android.play:core` (default: `1.9.0`)
- `$androidPlayServicesBaseVersion` version of `com.google.android.gms:play-services-base` (default: `18.0.1`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { AppUpdate } from '@capawesome/capacitor-app-update';

const getCurrentAppVersion = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  return result.currentVersion;
};

const getAvailableAppVersion = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  return result.availableVersion;
};

const openAppStore = async () => {
  await AppUpdate.openAppStore();
};

const performImmediateUpdate = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  if (result.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (result.immediateUpdateAllowed) {
    await AppUpdate.performImmediateUpdate();
  }
};

const startFlexibleUpdate = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  if (result.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (result.flexibleUpdateAllowed) {
    await AppUpdate.startFlexibleUpdate();
  }
};

const completeFlexibleUpdate = async () => {
  await AppUpdate.completeFlexibleUpdate();
};
```

## API

<docgen-index>

* [`getAppUpdateInfo(...)`](#getappupdateinfo)
* [`openAppStore(...)`](#openappstore)
* [`performImmediateUpdate()`](#performimmediateupdate)
* [`startFlexibleUpdate()`](#startflexibleupdate)
* [`completeFlexibleUpdate()`](#completeflexibleupdate)
* [`addListener('onFlexibleUpdateStateChange', ...)`](#addlisteneronflexibleupdatestatechange)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAppUpdateInfo(...)

```typescript
getAppUpdateInfo(options?: GetAppUpdateInfoOptions | undefined) => Promise<AppUpdateInfo>
```

Returns app update informations.

Only available for Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getappupdateinfooptions">GetAppUpdateInfoOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#appupdateinfo">AppUpdateInfo</a>&gt;</code>

--------------------


### openAppStore(...)

```typescript
openAppStore(options?: OpenAppStoreOptions | undefined) => Promise<void>
```

Opens the app store entry of the app in the Play Store (Android) or App Store (iOS).

Only available for Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#openappstoreoptions">OpenAppStoreOptions</a></code> |

--------------------


### performImmediateUpdate()

```typescript
performImmediateUpdate() => Promise<AppUpdateResult>
```

Performs an immediate in-app update.

Only available for Android.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

--------------------


### startFlexibleUpdate()

```typescript
startFlexibleUpdate() => Promise<AppUpdateResult>
```

Starts a flexible in-app update.

Only available for Android.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

--------------------


### completeFlexibleUpdate()

```typescript
completeFlexibleUpdate() => Promise<void>
```

Completes a flexible in-app update by restarting the app.

Only available for Android.

--------------------


### addListener('onFlexibleUpdateStateChange', ...)

```typescript
addListener(eventName: 'onFlexibleUpdateStateChange', listenerFunc: (state: FlexibleUpdateState) => void) => PluginListenerHandle
```

Adds a flexbile in-app update state change listener.

Only available for Android.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onFlexibleUpdateStateChange'</code>                                              |
| **`listenerFunc`** | <code>(state: <a href="#flexibleupdatestate">FlexibleUpdateState</a>) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

--------------------


### Interfaces


#### AppUpdateInfo

| Prop                              | Type                                                                                | Description                                                                                                                                                                    |
| --------------------------------- | ----------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **`currentVersion`**              | <code>string</code>                                                                 | Version code (Android) or CFBundleShortVersionString (iOS) of the currently installed app version. Only available for Android and iOS.                                         |
| **`availableVersion`**            | <code>string</code>                                                                 | Version code (Android) or CFBundleShortVersionString (iOS) of the update. Only available for Android and iOS.                                                                  |
| **`availableVersionReleaseDate`** | <code>string</code>                                                                 | Release date of the update in ISO 8601 (UTC) format. Only available for iOS.                                                                                                   |
| **`updateAvailability`**          | <code><a href="#appupdateavailability">AppUpdateAvailability</a></code>             | The app update availability. Only available for Android and iOS.                                                                                                               |
| **`updatePriority`**              | <code>number</code>                                                                 | In-app update priority for this update, as defined by the developer in the Google Play Developer API. Only available for Android.                                              |
| **`immediateUpdateAllowed`**      | <code>boolean</code>                                                                | `true` if an immediate update is allowed, otherwise `false`. Only available for Android.                                                                                       |
| **`flexibleUpdateAllowed`**       | <code>boolean</code>                                                                | `true` if a flexible update is allowed, otherwise `false`. Only available for Android.                                                                                         |
| **`clientVersionStalenessDays`**  | <code>number</code>                                                                 | Number of days since the Google Play Store app on the user's device has learnt about an available update if an update is available or in progress. Only available for Android. |
| **`installStatus`**               | <code><a href="#flexibleupdateinstallstatus">FlexibleUpdateInstallStatus</a></code> | Flexible in-app update install status. Only available for Android.                                                                                                             |
| **`minimumOsVersion`**            | <code>string</code>                                                                 | The minimum version of the operating system required for the app to run in iOS. Only available for iOS.                                                                        |


#### GetAppUpdateInfoOptions

| Prop          | Type                | Description                                                                                                                                                                |
| ------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`country`** | <code>string</code> | The two-letter country code for the store you want to search. See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes. Only available for iOS. |


#### OpenAppStoreOptions

| Prop          | Type                | Description                                                                                                                                                                |
| ------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`country`** | <code>string</code> | The two-letter country code for the store you want to search. See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes. Only available for iOS. |


#### AppUpdateResult

| Prop       | Type                                                                |
| ---------- | ------------------------------------------------------------------- |
| **`code`** | <code><a href="#appupdateresultcode">AppUpdateResultCode</a></code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### FlexibleUpdateState

| Prop                       | Type                                                                                | Description                                                                                                                        |
| -------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **`installStatus`**        | <code><a href="#flexibleupdateinstallstatus">FlexibleUpdateInstallStatus</a></code> | Flexible in-app update install status.                                                                                             |
| **`bytesDownloaded`**      | <code>number</code>                                                                 | Returns the number of bytes downloaded so far. `undefined` if the install status is other than `DOWNLOADING`.                      |
| **`totalBytesToDownload`** | <code>number</code>                                                                 | Returns the total number of bytes to be downloaded for this update. `undefined` if the install status is other than `DOWNLOADING`. |


### Enums


#### AppUpdateAvailability

| Members                    | Value          |
| -------------------------- | -------------- |
| **`UNKNOWN`**              | <code>0</code> |
| **`UPDATE_NOT_AVAILABLE`** | <code>1</code> |
| **`UPDATE_AVAILABLE`**     | <code>2</code> |
| **`UPDATE_IN_PROGRESS`**   | <code>3</code> |


#### FlexibleUpdateInstallStatus

| Members           | Value           |
| ----------------- | --------------- |
| **`UNKNOWN`**     | <code>0</code>  |
| **`PENDING`**     | <code>1</code>  |
| **`DOWNLOADING`** | <code>2</code>  |
| **`INSTALLING`**  | <code>3</code>  |
| **`INSTALLED`**   | <code>4</code>  |
| **`FAILED`**      | <code>5</code>  |
| **`CANCELED`**    | <code>6</code>  |
| **`DOWNLOADED`**  | <code>11</code> |


#### AppUpdateResultCode

| Members             | Value          | Description                                                                                 |
| ------------------- | -------------- | ------------------------------------------------------------------------------------------- |
| **`OK`**            | <code>0</code> | The user has accepted the update.                                                           |
| **`CANCELED`**      | <code>1</code> | The user has denied or cancelled the update.                                                |
| **`FAILED`**        | <code>2</code> | Some other error prevented either the user from providing consent or the update to proceed. |
| **`NOT_AVAILABLE`** | <code>3</code> | No update available.                                                                        |
| **`NOT_ALLOWED`**   | <code>4</code> | Update type not allowed.                                                                    |
| **`INFO_MISSING`**  | <code>5</code> | App update info missing. You must call `getAppUpdateInfo()` before requesting an update.    |

</docgen-api>

## Test with internal app-sharing

The Android Developers documentation describes how to test [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) using [internal app sharing](https://support.google.com/googleplay/android-developer/answer/9303479): https://developer.android.com/guide/playcore/in-app-updates/test

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/packages/app-update/blob/main/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/packages/app-update/blob/main/LICENSE).

## Credits

This plugin is based on the [Capacitor App Update](https://github.com/capawesome-team/capacitor-app-update) plugin.
Thanks to everyone who contributed to the project!
