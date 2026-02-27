# @capawesome/capacitor-live-update

Capacitor plugin that allows you to update your app remotely in real-time without requiring users to download a new version from the app store, known as Over-the-Air (OTA) updates.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for Over-the-Air (OTA) updates. Here are some of the key features:

- 🔋 Supports **Android and iOS**
- ⚡️ **Capacitor 6/7/8** support
- 📦 **Bundle Management**: Download, set, and delete bundles.
- ☁️ **Cloud Support**: Use the [Capawesome Cloud](https://cloud.capawesome.io/) to manage your app updates.
- 📺 **Channel Support**: Set a channel for the app to manage different versions.
- 🔄 **Auto Update**: Automatically download and set the latest bundle for the app with configurable background update strategy.
- 🛟 **Rollback**: Reset the app to the default bundle if an incompatible bundle has been set.
- 🚀 **Rollout**: Gradually roll out new bundles to gather valuable feedback.
- 🔁 **Delta Updates**: Make your updates faster by only downloading changed files.
- ⚙️ **Runtime Configuration**: Update plugin configuration at runtime without rebuilding the app.
- 📡 **Update Lifecycle Events**: Track download progress, react to bundle changes, and detect reloads with auto-blocking of rolled back bundles.
- 🏠 **Self-Hosted Bundles**: Download bundles from any URL, no Capawesome Cloud dependency required.
- 🏷️ **Custom Properties**: Associate custom key-value metadata with bundles via Capawesome Cloud.
- 🔒 **Security**: Verify the authenticity and integrity of the bundle using a public key.
- ⚔️ **Battle-Tested**: Used in more than 1,000 projects to update apps on more than 20,000,000 devices.
- 🌐 **Open Source**: Licensed under the MIT License.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Maintenance    |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Guides

- [Getting Started with Capawesome Cloud Live Updates](https://capawesome.io/cloud/live-updates/setup/)
- [Migrating from Ionic Appflow to Capawesome Cloud](https://capawesome.io/blog/migrating-from-ionic-appflow-to-capawesome-cloud/)
- [Migrating from App Center to Capawesome Cloud](https://capawesome.io/blog/migrating-from-app-center-to-capawesome-cloud/)

## Installation

```bash
npm install @capawesome/capacitor-live-update
npx cap sync
```

### Android

#### Channel

If you are using [Versioned Channels](https://capawesome.io/cloud/live-updates/guides/best-practices/#versioned-channels), you can set a default channel directly in your native project by adding a string resource.
This allows you to tie the channel to the version code at build time.

Add the following to your app's `build.gradle` file:

```groovy
android {
    defaultConfig {
        resValue "string", "capawesome_live_update_default_channel", "production-" + defaultConfig.versionCode
    }
}
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$okhttp3Version` version of `com.squareup.okhttp3:okhttp` (default: `5.3.2`)
- `$zip4jVersion` version of `net.lingala.zip4j:zip4j` (default: `2.11.5`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Channel

If you are using [Versioned Channels](https://capawesome.io/cloud/live-updates/guides/best-practices/#versioned-channels), you can set a default channel directly in your native project by adding a key to your `Info.plist` file.
This allows you to tie the channel to the build version at build time.

Add the following to your `Info.plist` file:

```xml
<key>CapawesomeLiveUpdateDefaultChannel</key>
<string>production-$(CURRENT_PROJECT_VERSION)</string>
```

#### Privacy manifest

Add the `NSPrivacyAccessedAPICategoryUserDefaults` dictionary key to your [Privacy Manifest](https://capacitorjs.com/docs/ios/privacy-manifest) (usually `ios/App/PrivacyInfo.xcprivacy`):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
  <dict>
    <key>NSPrivacyAccessedAPITypes</key>
    <array>
      <!-- Add this dict entry to the array if the file already exists. -->
      <dict>
        <key>NSPrivacyAccessedAPIType</key>
        <string>NSPrivacyAccessedAPICategoryUserDefaults</string>
        <key>NSPrivacyAccessedAPITypeReasons</key>
        <array>
          <string>CA92.1</string>
        </array>
      </dict>
    </array>
  </dict>
</plist>
```

We recommend to declare [`CA92.1`](https://developer.apple.com/documentation/bundleresources/privacy_manifest_files/describing_use_of_required_reason_api#4278401) as the reason for accessing the [`UserDefaults`](https://developer.apple.com/documentation/foundation/userdefaults) API.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                             | Type                                | Description                                                                                                                                                                                                                                                                                                                                                                                                                | Default                                | Since |
| -------------------------------- | ----------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------- | ----- |
| **`appId`**                      | <code>string</code>                 | The app ID is used to identify the app when using [Capawesome Cloud](https://capawesome.io/cloud/). This is **NOT** the same as the app identifier (e.g. `com.example.app`). This is a unique identifier generated by Capawesome Cloud (e.g. `6e351b4f-69a7-415e-a057-4567df7ffe94`).                                                                                                                                      |                                        | 5.0.0 |
| **`autoBlockRolledBackBundles`** | <code>boolean</code>                | Whether or not to automatically block bundles that have been rolled back. When enabled, the plugin will automatically block bundles that caused a rollback (up to 100 bundles). When the limit is reached, the oldest blocked bundle is unblocked. Blocked bundles will be skipped in future sync operations. **Attention**: This option has no effect if `readyTimeout` is set to `0`. Only available on Android and iOS. | <code>false</code>                     | 7.3.0 |
| **`autoDeleteBundles`**          | <code>boolean</code>                | Whether or not to automatically delete unused bundles. When enabled, the plugin will automatically delete unused bundles after calling `ready()`.                                                                                                                                                                                                                                                                          | <code>false</code>                     | 5.0.0 |
| **`autoUpdateStrategy`**         | <code>'none' \| 'background'</code> | The auto-update strategy for live updates. - `none`: Live updates will not be applied automatically. - `background`: Live updates will be automatically downloaded and applied in the background at app startup and when the app resumes (if the last check was more than 15 minutes ago). Only available on Android and iOS.                                                                                              | <code>'none'</code>                    | 7.3.0 |
| **`defaultChannel`**             | <code>string</code>                 | The default channel of the app. This can be overridden by `setChannel()`, the `channel` parameter of `sync()`, or the native channel configuration (`CapawesomeLiveUpdateDefaultChannel` in `Info.plist` on iOS or `capawesome_live_update_default_channel` in `strings.xml` on Android).                                                                                                                                  |                                        | 6.3.0 |
| **`httpTimeout`**                | <code>number</code>                 | The timeout in milliseconds for HTTP requests.                                                                                                                                                                                                                                                                                                                                                                             | <code>60000</code>                     | 6.4.0 |
| **`publicKey`**                  | <code>string</code>                 | The public key to verify the integrity of the bundle. The public key must be a PEM-encoded RSA public key.                                                                                                                                                                                                                                                                                                                 |                                        | 6.1.0 |
| **`readyTimeout`**               | <code>number</code>                 | The timeout in milliseconds to wait for the app to be ready before resetting to the default bundle. It is strongly **recommended** to configure this option (e.g. `10000` ms) so that the plugin can roll back to the default bundle in case of problems. If configured, the plugin will wait for the app to call the `ready()` method before resetting to the default bundle. Set to `0` to disable the timeout.          | <code>0</code>                         | 5.0.0 |
| **`serverDomain`**               | <code>string</code>                 | The API domain of the [Capawesome Cloud](https://cloud.capawesome.io) server **without** scheme or path.                                                                                                                                                                                                                                                                                                                   | <code>'api.cloud.capawesome.io'</code> | 7.0.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "LiveUpdate": {
      "appId": '6e351b4f-69a7-415e-a057-4567df7ffe94',
      "autoBlockRolledBackBundles": undefined,
      "autoDeleteBundles": undefined,
      "autoUpdateStrategy": 'background',
      "defaultChannel": 'production',
      "httpTimeout": undefined,
      "publicKey": '-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDodf1SD0OOn6hIlDuKBza0Ed0OqtwyVJwiyjmE9BJaZ7y8ZUfcF+SKmd0l2cDPM45XIg2tAFux5n29uoKyHwSt+6tCi5CJA5Z1/1eZruRRqABLonV77KS3HUtvOgqRLDnKSV89dYZkM++NwmzOPgIF422mvc+VukcVOBfc8/AHQIDAQAB-----END PUBLIC KEY-----',
      "readyTimeout": 10000,
      "serverDomain": 'api.cloud.capawesome.eu'
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-live-update" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    LiveUpdate: {
      appId: '6e351b4f-69a7-415e-a057-4567df7ffe94',
      autoBlockRolledBackBundles: undefined,
      autoDeleteBundles: undefined,
      autoUpdateStrategy: 'background',
      defaultChannel: 'production',
      httpTimeout: undefined,
      publicKey: '-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDodf1SD0OOn6hIlDuKBza0Ed0OqtwyVJwiyjmE9BJaZ7y8ZUfcF+SKmd0l2cDPM45XIg2tAFux5n29uoKyHwSt+6tCi5CJA5Z1/1eZruRRqABLonV77KS3HUtvOgqRLDnKSV89dYZkM++NwmzOPgIF422mvc+VukcVOBfc8/AHQIDAQAB-----END PUBLIC KEY-----',
      readyTimeout: 10000,
      serverDomain: 'api.cloud.capawesome.eu',
    },
  },
};

export default config;
```

</docgen-config>

## Usage

```typescript
import { LiveUpdate } from '@capawesome/capacitor-live-update';

const deleteBundle = async () => {
  await LiveUpdate.deleteBundle({ bundleId: 'my-bundle' });
};

const downloadBundle = async () => {
  await LiveUpdate.downloadBundle({ url: 'https://example.com/1.0.0.zip', bundleId: '1.0.0' });
};

const fetchLatestBundle = async () => {
  await LiveUpdate.fetchLatestBundle();
};

const getBundles = async () => {
  const result = await LiveUpdate.getBundles();
  return result.bundleIds;
};

const getChannel = async () => {
  const result = await LiveUpdate.getChannel();
  return result.channel;
};

const getCurrentBundle = async () => {
  const result = await LiveUpdate.getCurrentBundle();
  return result.bundleId;
};

const getCustomId = async () => {
  const result = await LiveUpdate.getCustomId();
  return result.customId;
};

const getDeviceId = async () => {
  const result = await LiveUpdate.getDeviceId();
  return result.deviceId;
};

const getNextBundle = async () => {
  const result = await LiveUpdate.getNextBundle();
  return result.bundleId;
};

const getVersionCode = async () => {
  const result = await LiveUpdate.getVersionCode();
  return result.versionCode;
};

const getVersionName = async () => {
  const result = await LiveUpdate.getVersionName();
  return result.versionName;
};

const ready = async () => {
  const result = await LiveUpdate.ready();
  if (result.currentBundleId) {
    console.log(`The app is now using the bundle with the identifier ${result.currentBundleId}.`);
  }
  if (result.previousBundleId) {
    console.log(`The app was using the bundle with the identifier ${result.previousBundleId}.`);
  }
  if (result.rollback) {
    console.log('The app was reset to the default bundle.');
  }
};

const reload = async () => {
  await LiveUpdate.reload();
};

const reset = async () => {
  await LiveUpdate.reset();
};

const setChannel = async () => {
  await LiveUpdate.setChannel({ channel: 'production-5' });
};

const setCustomId = async () => {
  await LiveUpdate.setCustomId({ customId: 'my-custom-id' });
};

const setNextBundle = async () => {
  await LiveUpdate.setNextBundle({ bundleId: '7f0b9bf2-dff6-4be2-bcac-b068cc5ea756' });
};

const sync = async () => {
  const result = await LiveUpdate.sync({
    channel: 'production-5',
  });
  return result.nextBundleId;
};

const isNewBundleAvailable = async () => {
  const { bundleId: latestBundleId } = await LiveUpdate.fetchLatestBundle({
    channel: 'production-5',
  });
  if (latestBundleId) {
    const { bundleId: currentBundleId } = await LiveUpdate.getCurrentBundle();
    return latestBundleId !== currentBundleId;
  } else {
    return false;
  }
};
```

## API

<docgen-index>

* [`clearBlockedBundles()`](#clearblockedbundles)
* [`deleteBundle(...)`](#deletebundle)
* [`downloadBundle(...)`](#downloadbundle)
* [`fetchLatestBundle(...)`](#fetchlatestbundle)
* [`getBlockedBundles()`](#getblockedbundles)
* [`getBundles()`](#getbundles)
* [`getChannel()`](#getchannel)
* [`getConfig()`](#getconfig)
* [`getDownloadedBundles()`](#getdownloadedbundles)
* [`getCurrentBundle()`](#getcurrentbundle)
* [`getCustomId()`](#getcustomid)
* [`getDefaultChannel()`](#getdefaultchannel)
* [`getDeviceId()`](#getdeviceid)
* [`isSyncing()`](#issyncing)
* [`getNextBundle()`](#getnextbundle)
* [`getVersionCode()`](#getversioncode)
* [`getVersionName()`](#getversionname)
* [`ready()`](#ready)
* [`reload()`](#reload)
* [`reset()`](#reset)
* [`resetConfig()`](#resetconfig)
* [`setChannel(...)`](#setchannel)
* [`setConfig(...)`](#setconfig)
* [`setCustomId(...)`](#setcustomid)
* [`setNextBundle(...)`](#setnextbundle)
* [`sync(...)`](#sync)
* [`addListener('downloadBundleProgress', ...)`](#addlistenerdownloadbundleprogress-)
* [`addListener('nextBundleSet', ...)`](#addlistenernextbundleset-)
* [`addListener('reloaded', ...)`](#addlistenerreloaded-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clearBlockedBundles()

```typescript
clearBlockedBundles() => Promise<void>
```

Clear all blocked bundles from the blocked list.

This removes all bundle identifiers that were automatically blocked
due to rollbacks when `autoBlockRolledBackBundles` is enabled.

Only available on Android and iOS.

**Since:** 7.4.0

--------------------


### deleteBundle(...)

```typescript
deleteBundle(options: DeleteBundleOptions) => Promise<void>
```

Delete a bundle from the app.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletebundleoptions">DeleteBundleOptions</a></code> |

**Since:** 5.0.0

--------------------


### downloadBundle(...)

```typescript
downloadBundle(options: DownloadBundleOptions) => Promise<void>
```

Download a bundle.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#downloadbundleoptions">DownloadBundleOptions</a></code> |

**Since:** 5.0.0

--------------------


### fetchLatestBundle(...)

```typescript
fetchLatestBundle(options?: FetchLatestBundleOptions | undefined) => Promise<FetchLatestBundleResult>
```

Fetch the latest bundle using the [Capawesome Cloud](https://capawesome.io/cloud/).

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#fetchlatestbundleoptions">FetchLatestBundleOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#fetchlatestbundleresult">FetchLatestBundleResult</a>&gt;</code>

**Since:** 6.6.0

--------------------


### getBlockedBundles()

```typescript
getBlockedBundles() => Promise<GetBlockedBundlesResult>
```

Get all blocked bundle identifiers.

Returns the list of bundle identifiers that were automatically blocked
due to rollbacks when `autoBlockRolledBackBundles` is enabled.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getblockedbundlesresult">GetBlockedBundlesResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### getBundles()

```typescript
getBundles() => Promise<GetBundlesResult>
```

Get all identifiers of bundles that have been downloaded.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getbundlesresult">GetBundlesResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getChannel()

```typescript
getChannel() => Promise<GetChannelResult>
```

Get the channel that is used for the update.

The channel is resolved in the following order (highest priority first):
1. `setChannel()` (SharedPreferences on Android / UserDefaults on iOS)
2. Native config (`CapawesomeLiveUpdateDefaultChannel` in `Info.plist` on iOS or
   `capawesome_live_update_default_channel` in `strings.xml` on Android)
3. Capacitor config `defaultChannel`

**Note**: The `channel` parameter of `sync()` takes the highest priority
but is not persisted and therefore not returned by this method.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getchannelresult">GetChannelResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getConfig()

```typescript
getConfig() => Promise<GetConfigResult>
```

Get the runtime configuration.

Returns the current plugin configuration including any runtime
overrides set via `setConfig()`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getconfigresult">GetConfigResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### getDownloadedBundles()

```typescript
getDownloadedBundles() => Promise<GetDownloadedBundlesResult>
```

Get all identifiers of bundles that have been downloaded.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdownloadedbundlesresult">GetDownloadedBundlesResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### getCurrentBundle()

```typescript
getCurrentBundle() => Promise<GetCurrentBundleResult>
```

Get the bundle identifier of the current bundle.
The current bundle is the bundle that is currently used by the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getcurrentbundleresult">GetCurrentBundleResult</a>&gt;</code>

**Since:** 6.7.0

--------------------


### getCustomId()

```typescript
getCustomId() => Promise<GetCustomIdResult>
```

Get the custom identifier of the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getcustomidresult">GetCustomIdResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getDefaultChannel()

```typescript
getDefaultChannel() => Promise<GetDefaultChannelResult>
```

Get the default channel of the app.

The default channel is resolved in the following order (highest priority first):
1. Native config (`CapawesomeLiveUpdateDefaultChannel` in `Info.plist` on iOS or
   `capawesome_live_update_default_channel` in `strings.xml` on Android)
2. Capacitor config `defaultChannel`

Unlike `getChannel()`, this method does **not** include
the channel set by `setChannel()`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdefaultchannelresult">GetDefaultChannelResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### getDeviceId()

```typescript
getDeviceId() => Promise<GetDeviceIdResult>
```

Get the unique device identifier.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdeviceidresult">GetDeviceIdResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### isSyncing()

```typescript
isSyncing() => Promise<IsSyncingResult>
```

Check whether a sync operation is currently in progress.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#issyncingresult">IsSyncingResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### getNextBundle()

```typescript
getNextBundle() => Promise<GetNextBundleResult>
```

Get the bundle identifier of the next bundle.
The next bundle is the bundle that will be used after calling `reload()`
or restarting the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getnextbundleresult">GetNextBundleResult</a>&gt;</code>

**Since:** 6.7.0

--------------------


### getVersionCode()

```typescript
getVersionCode() => Promise<GetVersionCodeResult>
```

Get the version code of the app.

On **Android**, this is the `versionCode` from the `android/app/build.gradle` file.
On **iOS**, this is the `CFBundleVersion` from the `Info.plist` file.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getversioncoderesult">GetVersionCodeResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getVersionName()

```typescript
getVersionName() => Promise<GetVersionNameResult>
```

Get the version name of the app.

On **Android**, this is the `versionName` from the `android/app/build.gradle` file.
On **iOS**, this is the `CFBundleShortVersionString` from the `Info.plist` file.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getversionnameresult">GetVersionNameResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### ready()

```typescript
ready() => Promise<ReadyResult>
```

Notify the plugin that the app is ready to use and no rollback is needed.

**Attention**: This method should be called as soon as the app is ready to use
to prevent the app from being reset to the default bundle.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#readyresult">ReadyResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### reload()

```typescript
reload() => Promise<void>
```

Reload the app to apply the new bundle.

Only available on Android and iOS.

**Since:** 5.0.0

--------------------


### reset()

```typescript
reset() => Promise<void>
```

Reset the app to the default bundle.

Call `reload()` or restart the app to apply the changes.

Only available on Android and iOS.

**Since:** 5.0.0

--------------------


### resetConfig()

```typescript
resetConfig() => Promise<void>
```

Reset the runtime configuration to the values from the Capacitor config file.

This clears any runtime configuration set via `setConfig()`.
The changes take effect immediately.

Only available on Android and iOS.

**Since:** 7.4.0

--------------------


### setChannel(...)

```typescript
setChannel(options: SetChannelOptions) => Promise<void>
```

Set the channel to use for the update.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setchanneloptions">SetChannelOptions</a></code> |

**Since:** 5.0.0

--------------------


### setConfig(...)

```typescript
setConfig(options: SetConfigOptions) => Promise<void>
```

Set the runtime configuration.

This allows updating plugin configuration options at runtime.
The changes are persisted across app restarts and take effect immediately.

**Important:** Runtime configuration is automatically reset to default values
whenever the native app is updated to a new version. This ensures that
configuration from previous versions doesn't persist after an app update.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setconfigoptions">SetConfigOptions</a></code> |

**Since:** 7.4.0

--------------------


### setCustomId(...)

```typescript
setCustomId(options: SetCustomIdOptions) => Promise<void>
```

Set the custom identifier of the device.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setcustomidoptions">SetCustomIdOptions</a></code> |

**Since:** 5.0.0

--------------------


### setNextBundle(...)

```typescript
setNextBundle(options: SetNextBundleOptions) => Promise<void>
```

Set the next bundle to use for the app.

Call `reload()` or restart the app to apply the changes.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnextbundleoptions">SetNextBundleOptions</a></code> |

**Since:** 6.7.0

--------------------


### sync(...)

```typescript
sync(options?: SyncOptions | undefined) => Promise<SyncResult>
```

Automatically download and set the latest bundle for the app using the [Capawesome Cloud](https://capawesome.io/cloud/).

Call `reload()` or restart the app to apply the changes.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#syncoptions">SyncOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#syncresult">SyncResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### addListener('downloadBundleProgress', ...)

```typescript
addListener(eventName: 'downloadBundleProgress', listenerFunc: DownloadBundleProgressListener) => Promise<PluginListenerHandle>
```

Listen for the download progress of a bundle.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'downloadBundleProgress'</code>                                                     |
| **`listenerFunc`** | <code><a href="#downloadbundleprogresslistener">DownloadBundleProgressListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.0.0

--------------------


### addListener('nextBundleSet', ...)

```typescript
addListener(eventName: 'nextBundleSet', listenerFunc: NextBundleSetListener) => Promise<PluginListenerHandle>
```

Listen for when a bundle is set as the next bundle.

This event is triggered whenever a bundle is set to be used on the next app restart,
either through automatic updates or manual calls to `setNextBundle()`.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'nextBundleSet'</code>                                            |
| **`listenerFunc`** | <code><a href="#nextbundlesetlistener">NextBundleSetListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.3.0

--------------------


### addListener('reloaded', ...)

```typescript
addListener(eventName: 'reloaded', listenerFunc: ReloadedListener) => Promise<PluginListenerHandle>
```

Listen for when the app is reloaded.

This event is triggered after the `reload()` method is called
and the app has been reloaded.

**Note:** To verify whether an update was successfully applied after a reload,
use the `ready()` method instead. The `ready()` method provides detailed information
about the current bundle, previous bundle, and whether a rollback occurred.

Only available on Android and iOS.

| Param              | Type                                                          |
| ------------------ | ------------------------------------------------------------- |
| **`eventName`**    | <code>'reloaded'</code>                                       |
| **`listenerFunc`** | <code><a href="#reloadedlistener">ReloadedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.4.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 7.2.0

--------------------


### Interfaces


#### DeleteBundleOptions

| Prop           | Type                | Description                                    | Since |
| -------------- | ------------------- | ---------------------------------------------- | ----- |
| **`bundleId`** | <code>string</code> | The unique identifier of the bundle to delete. | 5.0.0 |


#### DownloadBundleOptions

| Prop               | Type                             | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            | Default            | Since |
| ------------------ | -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------ | ----- |
| **`artifactType`** | <code>'manifest' \| 'zip'</code> | The artifact type of the bundle.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | <code>'zip'</code> | 6.6.0 |
| **`bundleId`**     | <code>string</code>              | The unique identifier of the bundle. **Attention**: The value `public` is reserved and cannot be used as a bundle identifier.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |                    | 5.0.0 |
| **`checksum`**     | <code>string</code>              | The checksum of the self-hosted bundle as a SHA-256 hash in hex format to verify the integrity of the bundle. **Attention**: Only supported for the `zip` artifact type.                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |                    | 7.1.0 |
| **`signature`**    | <code>string</code>              | The signature of the self-hosted bundle as a signed SHA-256 hash in base64 format to verify the integrity of the bundle. **Attention**: Only supported for the `zip` artifact type.                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |                    | 7.1.0 |
| **`url`**          | <code>string</code>              | The URL of the bundle to download. For the `zip` artifact type, the URL must point to a ZIP file. For the `manifest` artifact type, the URL serves as the base URL to download the individual files. For example, if the URL is `https://example.com/download`, the plugin will download the file with the href `index.html` from `https://example.com/download?href=index.html`. To **verify the integrity** of the file, the server should return a `X-Checksum` header with the SHA-256 hash in hex format. To **verify the signature** of the file, the server should return a `X-Signature` header with the signed SHA-256 hash in base64 format. |                    | 5.0.0 |


#### FetchLatestBundleResult

| Prop                   | Type                                    | Description                                                                                                                                                                                  | Since |
| ---------------------- | --------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`artifactType`**     | <code>'manifest' \| 'zip'</code>        | The artifact type of the bundle.                                                                                                                                                             | 6.7.0 |
| **`bundleId`**         | <code>string \| null</code>             | The unique identifier of the latest bundle. If `null`, no bundle is available.                                                                                                               | 6.6.0 |
| **`checksum`**         | <code>string</code>                     | The checksum of the latest bundle if the bundle is self-hosted. If the bundle is hosted on Capawesome Cloud, the checksum will be returned as response header when downloading the bundle.   | 7.1.0 |
| **`customProperties`** | <code>{ [key: string]: string; }</code> | Custom properties that are associated with the latest bundle.                                                                                                                                | 7.0.0 |
| **`downloadUrl`**      | <code>string</code>                     | The URL of the latest bundle to download. Pass this URL to the `downloadBundle(...)` method to download the bundle.                                                                          | 6.7.0 |
| **`signature`**        | <code>string</code>                     | The signature of the latest bundle if the bundle is self-hosted. If the bundle is hosted on Capawesome Cloud, the signature will be returned as response header when downloading the bundle. | 7.1.0 |


#### FetchLatestBundleOptions

| Prop          | Type                | Description                                                      | Since |
| ------------- | ------------------- | ---------------------------------------------------------------- | ----- |
| **`channel`** | <code>string</code> | The name of the channel where the latest bundle is fetched from. | 6.7.0 |


#### GetBlockedBundlesResult

| Prop            | Type                  | Description                                            | Since |
| --------------- | --------------------- | ------------------------------------------------------ | ----- |
| **`bundleIds`** | <code>string[]</code> | An array of unique identifiers of all blocked bundles. | 7.4.0 |


#### GetBundlesResult

| Prop            | Type                  | Description                                              | Since |
| --------------- | --------------------- | -------------------------------------------------------- | ----- |
| **`bundleIds`** | <code>string[]</code> | An array of unique identifiers of all available bundles. | 5.0.0 |


#### GetChannelResult

| Prop          | Type                        | Description                                                        | Since |
| ------------- | --------------------------- | ------------------------------------------------------------------ | ----- |
| **`channel`** | <code>string \| null</code> | The channel name. If `null`, the app is using the default channel. | 5.0.0 |


#### GetConfigResult

| Prop                     | Type                                | Description                                                              | Since |
| ------------------------ | ----------------------------------- | ------------------------------------------------------------------------ | ----- |
| **`appId`**              | <code>string \| null</code>         | The app ID used to identify the app. If `null`, no app ID is configured. | 7.4.0 |
| **`autoUpdateStrategy`** | <code>'none' \| 'background'</code> | The auto-update strategy for live updates.                               | 7.4.0 |


#### GetDownloadedBundlesResult

| Prop            | Type                  | Description                                               | Since |
| --------------- | --------------------- | --------------------------------------------------------- | ----- |
| **`bundleIds`** | <code>string[]</code> | An array of unique identifiers of all downloaded bundles. | 7.4.0 |


#### GetCurrentBundleResult

| Prop           | Type                        | Description                                                                               | Since |
| -------------- | --------------------------- | ----------------------------------------------------------------------------------------- | ----- |
| **`bundleId`** | <code>string \| null</code> | The unique identifier of the current bundle. If `null`, the default bundle is being used. | 6.7.0 |


#### GetCustomIdResult

| Prop           | Type                        | Description                                                                  | Since |
| -------------- | --------------------------- | ---------------------------------------------------------------------------- | ----- |
| **`customId`** | <code>string \| null</code> | The custom identifier of the device. If `null`, no custom identifier is set. | 5.0.0 |


#### GetDefaultChannelResult

| Prop          | Type                        | Description                                                            | Since |
| ------------- | --------------------------- | ---------------------------------------------------------------------- | ----- |
| **`channel`** | <code>string \| null</code> | The default channel name. If `null`, no default channel is configured. | 8.2.0 |


#### GetDeviceIdResult

| Prop           | Type                | Description                                                                                                                                                                                                                                                                    | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`deviceId`** | <code>string</code> | The unique identifier of the device. On iOS, [`identifierForVendor`](https://developer.apple.com/documentation/uikit/uidevice/1620059-identifierforvendor) is used. The value of this property is the same for apps that come from the same vendor running on the same device. | 5.0.0 |


#### IsSyncingResult

| Prop          | Type                 | Description                                        | Since |
| ------------- | -------------------- | -------------------------------------------------- | ----- |
| **`syncing`** | <code>boolean</code> | Whether a sync operation is currently in progress. | 7.4.0 |


#### GetNextBundleResult

| Prop           | Type                        | Description                                                                            | Since |
| -------------- | --------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`bundleId`** | <code>string \| null</code> | The unique identifier of the next bundle. If `null`, the default bundle is being used. | 6.7.0 |


#### GetVersionCodeResult

| Prop              | Type                | Description                                                                                                                                                                            | Since |
| ----------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`versionCode`** | <code>string</code> | The version code of the app. On **Android**, this is the `versionCode` from the `android/app/build.gradle` file. On **iOS**, this is the `CFBundleVersion` from the `Info.plist` file. | 5.0.0 |


#### GetVersionNameResult

| Prop              | Type                | Description                                                                                                                                                                                       | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`versionName`** | <code>string</code> | The version name of the app. On **Android**, this is the `versionName` from the `android/app/build.gradle` file. On **iOS**, this is the `CFBundleShortVersionString` from the `Info.plist` file. | 5.0.0 |


#### ReadyResult

| Prop                   | Type                        | Description                                                                             | Since |
| ---------------------- | --------------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`previousBundleId`** | <code>string \| null</code> | The identifier of the previous bundle used. If `null`, the default bundle was used.     | 7.0.0 |
| **`currentBundleId`**  | <code>string \| null</code> | The identifier of the current bundle used. If `null`, the default bundle is being used. | 7.0.0 |
| **`rollback`**         | <code>boolean</code>        | Whether or not the app was reset to the default bundle.                                 | 7.0.0 |


#### SetChannelOptions

| Prop          | Type                        | Description                                         | Since |
| ------------- | --------------------------- | --------------------------------------------------- | ----- |
| **`channel`** | <code>string \| null</code> | The channel name. Set `null` to remove the channel. | 5.0.0 |


#### SetConfigOptions

| Prop        | Type                        | Description                                                                                           | Since |
| ----------- | --------------------------- | ----------------------------------------------------------------------------------------------------- | ----- |
| **`appId`** | <code>string \| null</code> | The app ID used to identify the app. Set `null` to reset to the value from the Capacitor config file. | 7.4.0 |


#### SetCustomIdOptions

| Prop           | Type                        | Description                                                                      | Since |
| -------------- | --------------------------- | -------------------------------------------------------------------------------- | ----- |
| **`customId`** | <code>string \| null</code> | The custom identifier of the device. Set `null` to remove the custom identifier. | 5.0.0 |


#### SetNextBundleOptions

| Prop           | Type                        | Description                                                                                                   | Since |
| -------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------- | ----- |
| **`bundleId`** | <code>string \| null</code> | The unique identifier of the bundle to use. Set `null` to use the default bundle (same as calling `reset()`). | 6.7.0 |


#### SyncResult

| Prop               | Type                        | Description                                                                                                | Since |
| ------------------ | --------------------------- | ---------------------------------------------------------------------------------------------------------- | ----- |
| **`nextBundleId`** | <code>string \| null</code> | The identifier of the next bundle to use. If `null`, the app is up-to-date and no new bundle is available. | 5.0.0 |


#### SyncOptions

| Prop          | Type                | Description                                                      | Since |
| ------------- | ------------------- | ---------------------------------------------------------------- | ----- |
| **`channel`** | <code>string</code> | The name of the channel where the latest bundle is fetched from. | 6.7.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### DownloadBundleProgressEvent

Event that is triggered when the download progress of a bundle changes.

| Prop                  | Type                | Description                                                             | Since |
| --------------------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`bundleId`**        | <code>string</code> | The unique identifier of the bundle that is being downloaded.           | 7.0.0 |
| **`downloadedBytes`** | <code>number</code> | The number of bytes that have been downloaded.                          | 7.0.0 |
| **`progress`**        | <code>number</code> | The progress of the download in percent as a value between `0` and `1`. | 7.0.0 |
| **`totalBytes`**      | <code>number</code> | The total number of bytes to download.                                  | 7.0.0 |


#### NextBundleSetEvent

Event that is triggered when a bundle is set as the next bundle.

| Prop           | Type                        | Description                                                                                                     | Since |
| -------------- | --------------------------- | --------------------------------------------------------------------------------------------------------------- | ----- |
| **`bundleId`** | <code>string \| null</code> | The unique identifier of the bundle that is set as the next bundle. If `null`, the default bundle will be used. | 7.3.0 |


### Type Aliases


#### DownloadBundleProgressListener

Listener for the download progress of a bundle.

<code>(event: <a href="#downloadbundleprogressevent">DownloadBundleProgressEvent</a>): void</code>


#### NextBundleSetListener

Listener for when a bundle is set as the next bundle.

<code>(event: <a href="#nextbundlesetevent">NextBundleSetEvent</a>): void</code>


#### ReloadedListener

Listener for when the app is reloaded.

<code>(): void</code>

</docgen-api>

## Testing

When testing the plugin, you must make sure that you do not use the [Live Reload](https://ionicframework.com/docs/cli/livereload) option, as in this case a development server is used to load the bundle and not the local file system.

Therefore, simply start your app without the live reload option, for example with the following command:

```bash
npx ionic cap run android --open
```

If you want to **not** receive live updates to test other parts of your app, you can simply set a non-existent channel, for example:

```typescript
import { LiveUpdate } from '@capawesome/capacitor-live-update';

const sync = async () => {
  await LiveUpdate.sync({ channel: 'non-existent-channel' });
};
```

This way, the app will check for updates, but no updates will be found.

## Limitations

Live updates are only supported for [binary-compatible changes](https://capawesome.io/cloud/live-updates/faq/#what-are-binary-compatible-changes) (e.g. HTML, CSS, JavaScript).
If you change native code, such as adding a new plugin, you need to resubmit your app to the app stores.
For this reason, you must be careful to [restrict live updates to compatible native versions](https://capawesome.io/blog/how-to-restrict-capacitor-live-updates-to-native-versions/) of your app.

## FAQ

### Why can't I see my changes during development?

As soon as you have installed a live update, the app will use the live update bundle and no longer the default bundle. 
So if you make local changes to your app and execute `npx cap run`, for example, these changes will apply to the default bundle, which is not currently in use.
You then have three options to get back to the default bundle:

1. **Reset**: Call the [`reset()`](#reset) method to reset the app to the default bundle.
2. **Reinstall**: Reinstall the app to remove the live update bundle.
3. **Update**: Increase the native version code of your app so that Capacitor automatically resets to the default bundle.

However, this is only a problem during development. It is not a problem in production, as Capacitor automatically resets to the default bundle after a native update.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/live-update/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/live-update/LICENSE).
