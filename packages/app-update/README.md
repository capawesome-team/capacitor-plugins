# @capawesome/capacitor-app-update

Capacitor plugin that assists with native app updates.
It supports retrieving app update information on **Android** and **iOS** and supports [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) on **Android**.

> Check out the [Capacitor Live Update](https://capawesome.io/plugins/live-update/) plugin to update your app remotely in real-time without submitting a new version to the app store. 🚀

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-app-update
npx cap sync
```

### Android Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidPlayAppUpdateVersion` version of `com.google.android.play:app-update` (default: `2.1.0`)
- `$androidPlayServicesBaseVersion` version of `com.google.android.gms:play-services-base` (default: `18.0.1`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Capacitor } from '@capacitor/core';
import { AppUpdate } from '@capawesome/capacitor-app-update';

const getCurrentAppVersion = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  if (Capacitor.getPlatform() === 'android') {
    return result.currentVersionCode;
  } else {
    return result.currentVersionName;
  }
};

const getAvailableAppVersion = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  if (Capacitor.getPlatform() === 'android') {
    return result.availableVersionCode;
  } else {
    return result.availableVersionName;
  }
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
* [`addListener('onFlexibleUpdateStateChange', ...)`](#addlisteneronflexibleupdatestatechange-)
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

Only available on Android and iOS.

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

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#openappstoreoptions">OpenAppStoreOptions</a></code> |

--------------------


### performImmediateUpdate()

```typescript
performImmediateUpdate() => Promise<AppUpdateResult>
```

Performs an immediate in-app update.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

--------------------


### startFlexibleUpdate()

```typescript
startFlexibleUpdate() => Promise<AppUpdateResult>
```

Starts a flexible in-app update.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

--------------------


### completeFlexibleUpdate()

```typescript
completeFlexibleUpdate() => Promise<void>
```

Completes a flexible in-app update by restarting the app.

Only available on Android.

--------------------


### addListener('onFlexibleUpdateStateChange', ...)

```typescript
addListener(eventName: 'onFlexibleUpdateStateChange', listenerFunc: (state: FlexibleUpdateState) => void) => Promise<PluginListenerHandle>
```

Adds a flexbile in-app update state change listener.

Only available on Android.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onFlexibleUpdateStateChange'</code>                                              |
| **`listenerFunc`** | <code>(state: <a href="#flexibleupdatestate">FlexibleUpdateState</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

--------------------


### Interfaces


#### AppUpdateInfo

| Prop                              | Type                                                                                | Description                                                                                                                                                                                                                                  | Since |
| --------------------------------- | ----------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`currentVersionName`**          | <code>string</code>                                                                 | The current version name of the app. On **Android**, this is the `versionName` from the `android/app/build.gradle` file. On **iOS**, this is the `CFBundleShortVersionString` from the `Info.plist` file. Only available on Android and iOS. | 5.1.0 |
| **`availableVersionName`**        | <code>string</code>                                                                 | The available version name of the update. On **iOS**, this is the `CFBundleShortVersionString` from the `Info.plist` file. Only available on iOS.                                                                                            | 5.1.0 |
| **`currentVersionCode`**          | <code>string</code>                                                                 | The current version code of the app. On **Android**, this is the `versionCode` from the `android/app/build.gradle` file. On **iOS**, this is the `CFBundleVersion` from the `Info.plist` file. Only available on Android and iOS.            | 5.1.0 |
| **`availableVersionCode`**        | <code>string</code>                                                                 | The available version code of the update. On **Android**, this is the `versionCode` from the `android/app/build.gradle` file. Only available on Android.                                                                                     | 5.1.0 |
| **`availableVersionReleaseDate`** | <code>string</code>                                                                 | Release date of the update in ISO 8601 (UTC) format. Only available on iOS.                                                                                                                                                                  |       |
| **`updateAvailability`**          | <code><a href="#appupdateavailability">AppUpdateAvailability</a></code>             | The app update availability. Only available on Android and iOS.                                                                                                                                                                              |       |
| **`updatePriority`**              | <code>number</code>                                                                 | In-app update priority for this update, as defined by the developer in the Google Play Developer API. Only available on Android.                                                                                                             |       |
| **`immediateUpdateAllowed`**      | <code>boolean</code>                                                                | `true` if an immediate update is allowed, otherwise `false`. Only available on Android.                                                                                                                                                      |       |
| **`flexibleUpdateAllowed`**       | <code>boolean</code>                                                                | `true` if a flexible update is allowed, otherwise `false`. Only available on Android.                                                                                                                                                        |       |
| **`clientVersionStalenessDays`**  | <code>number</code>                                                                 | Number of days since the Google Play Store app on the user's device has learnt about an available update if an update is available or in progress. Only available on Android.                                                                |       |
| **`installStatus`**               | <code><a href="#flexibleupdateinstallstatus">FlexibleUpdateInstallStatus</a></code> | Flexible in-app update install status. Only available on Android.                                                                                                                                                                            |       |
| **`minimumOsVersion`**            | <code>string</code>                                                                 | The minimum version of the operating system required for the app to run in iOS. Only available on iOS.                                                                                                                                       |       |


#### GetAppUpdateInfoOptions

| Prop          | Type                | Description                                                                                                                                                               |
| ------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`country`** | <code>string</code> | The two-letter country code for the store you want to search. See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes. Only available on iOS. |


#### OpenAppStoreOptions

| Prop        | Type                | Description                                                                                                                                                                                                                                     | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`appId`** | <code>string</code> | The app ID of the app to open in the App Store. On **iOS**, this is the Apple ID of your app (e.g. `123456789`). You can find the ID in the URL of your app store entry (e.g. `https://apps.apple.com/app/id123456789`). Only available on iOS. | 6.1.0 |


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

The Android Developers documentation describes how to test [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) using [internal app sharing](https://developer.android.com/guide/playcore/in-app-updates/test).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-update/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-update/LICENSE).

## Credits

This plugin is based on the [Capacitor App Update](https://github.com/capawesome-team/capacitor-app-update) plugin.
Thanks to everyone who contributed to the project!
