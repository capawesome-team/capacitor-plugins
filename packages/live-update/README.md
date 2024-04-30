# @capawesome/capacitor-live-update

Capacitor plugin to update your app remotely in real-time.

## Features

- 🔋 Supports **Android and iOS**
- ⚡️ **Capacitor 6** support
- 📦 **Bundle Management**: Download, set, and delete bundles.
- ☁️ **Cloud Support**: Use the [Capawesome Cloud](https://capawesome.io/cloud) to manage your app updates.
- 📺 **Channel Support**: Set a channel for the app to manage different versions.
- 🔄 **Auto Update**: Automatically download and set the latest bundle for the app.
- 🛟 **Rollback**: Reset the app to the default bundle if an incompatible bundle has been set.

## Installation

```bash
npm install @capawesome/capacitor-live-update
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$okhttp3Version` version of `com.squareup.okhttp3:okhttp` (default: `22.3.1`)
- `$zip4jVersion` version of `net.lingala.zip4j:zip4j` (default: `2.11.5`)

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                    | Type                 | Description                                                                                                                                       | Default            | Since |
| ----------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`appId`**             | <code>string</code>  | The app ID is used to identify the app when using [Capawesome Cloud](https://capawesome.io/cloud).                                                |                    | 5.0.0 |
| **`autoDeleteBundles`** | <code>boolean</code> | Whether or not to automatically delete unused bundles. When enabled, the plugin will automatically delete unused bundles after calling `ready()`. | <code>false</code> | 5.0.0 |
| **`enabled`**           | <code>boolean</code> | Whether or not the plugin is enabled.                                                                                                             | <code>true</code>  | 5.0.0 |
| **`readyTimeout`**      | <code>number</code>  | The timeout in milliseconds to wait for the app to be ready before resetting to the default bundle.                                               | <code>10000</code> | 5.0.0 |
| **`resetOnUpdate`**     | <code>boolean</code> | Whether or not the app should be reset to the default bundle during an update.                                                                    | <code>true</code>  | 5.0.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "LiveUpdate": {
      "appId": '4100e356-e851-47fe-9d3c-b411eb325a99',
      "autoDeleteBundles": undefined,
      "enabled": undefined,
      "readyTimeout": undefined,
      "resetOnUpdate": undefined
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capacitor/live-update" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    LiveUpdate: {
      appId: '4100e356-e851-47fe-9d3c-b411eb325a99',
      autoDeleteBundles: undefined,
      enabled: undefined,
      readyTimeout: undefined,
      resetOnUpdate: undefined,
    },
  },
};

export default config;
```

</docgen-config>

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Guides

- [Announcing the Capacitor Live Update Plugin](https://capawesome.io/blog/announcing-the-capacitor-live-update-plugin/)

## Usage

```typescript
import { LiveUpdate } from '@capawesome/capacitor-live-update';

const downloadBundle = async () => {
    // Download a bundle from a URL
    await LiveUpdate.downloadBundle({
        url: 'https://example.com/1.0.0.zip',
        bundleId: '1.0.0',
    });
    // Set the downloaded bundle as the next bundle to use
    await LiveUpdate.setBundle({ bundleId: '1.0.0' });
    // Reload the app to apply the new bundle
    await LiveUpdate.reload();
};

const sync = async () => {
    // Automatically download and set the latest bundle for the app
    const result = await LiveUpdate.sync();
    if (result.nextBundleId) {
        // Reload the app to apply the new bundle
        await LiveUpdate.reload();
    }
};
```

## API

<docgen-index>

* [`deleteBundle(...)`](#deletebundle)
* [`downloadBundle(...)`](#downloadbundle)
* [`getBundle()`](#getbundle)
* [`getBundles()`](#getbundles)
* [`getChannel()`](#getchannel)
* [`getCustomId()`](#getcustomid)
* [`getDeviceId()`](#getdeviceid)
* [`getVersionCode()`](#getversioncode)
* [`getVersionName()`](#getversionname)
* [`ready()`](#ready)
* [`reload()`](#reload)
* [`reset()`](#reset)
* [`setBundle(...)`](#setbundle)
* [`setChannel(...)`](#setchannel)
* [`setCustomId(...)`](#setcustomid)
* [`sync()`](#sync)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

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


### getBundle()

```typescript
getBundle() => Promise<GetBundleResult>
```

Get the active bundle identifier.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getbundleresult">GetBundleResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getBundles()

```typescript
getBundles() => Promise<GetBundlesResult>
```

Get all available bundle identifiers.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getbundlesresult">GetBundlesResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getChannel()

```typescript
getChannel() => Promise<GetChannelResult>
```

Get the channel of the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getchannelresult">GetChannelResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getCustomId()

```typescript
getCustomId() => Promise<GetCustomIdResult>
```

Get the custom identifier of the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getcustomidresult">GetCustomIdResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getDeviceId()

```typescript
getDeviceId() => Promise<GetDeviceIdResult>
```

Get the device identifier of the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdeviceidresult">GetDeviceIdResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getVersionCode()

```typescript
getVersionCode() => Promise<GetVersionCodeResult>
```

Get the version code of the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getversioncoderesult">GetVersionCodeResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### getVersionName()

```typescript
getVersionName() => Promise<GetVersionNameResult>
```

Get the version name of the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getversionnameresult">GetVersionNameResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### ready()

```typescript
ready() => Promise<void>
```

Notify the plugin that the app is ready to use and no rollback is needed.

Only available on Android and iOS.

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


### setBundle(...)

```typescript
setBundle(options: SetBundleOptions) => Promise<void>
```

Set the next bundle to use for the app.

Call `reload()` or restart the app to apply the changes.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setbundleoptions">SetBundleOptions</a></code> |

**Since:** 5.0.0

--------------------


### setChannel(...)

```typescript
setChannel(options: SetChannelOptions) => Promise<void>
```

Set the channel of the app.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setchanneloptions">SetChannelOptions</a></code> |

**Since:** 5.0.0

--------------------


### setCustomId(...)

```typescript
setCustomId(options: SetCustomIdOptions) => Promise<void>
```

Set the custom identifier of the app.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setcustomidoptions">SetCustomIdOptions</a></code> |

**Since:** 5.0.0

--------------------


### sync()

```typescript
sync() => Promise<SyncResult>
```

Automatically download and set the latest bundle for the app using the [Capawesome Cloud](https://capawesome.io/cloud/).

Call `reload()` or restart the app to apply the changes.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#syncresult">SyncResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### Interfaces


#### DeleteBundleOptions

| Prop           | Type                | Description                                    | Since |
| -------------- | ------------------- | ---------------------------------------------- | ----- |
| **`bundleId`** | <code>string</code> | The unique identifier of the bundle to delete. | 5.0.0 |


#### DownloadBundleOptions

| Prop           | Type                | Description                                                                                               | Since |
| -------------- | ------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`url`**      | <code>string</code> | The URL of the bundle to download. The bundle must be a ZIP file containing at least a `index.html` file. | 5.0.0 |
| **`bundleId`** | <code>string</code> | The unique identifier of the bundle.                                                                      | 5.0.0 |


#### GetBundleResult

| Prop           | Type                        | Description                                                                              | Since |
| -------------- | --------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`bundleId`** | <code>string \| null</code> | The unique identifier of the active bundle. If `null`, the default bundle is being used. | 5.0.0 |


#### GetBundlesResult

| Prop            | Type                  | Description                                              | Since |
| --------------- | --------------------- | -------------------------------------------------------- | ----- |
| **`bundleIds`** | <code>string[]</code> | An array of unique identifiers of all available bundles. | 5.0.0 |


#### GetChannelResult

| Prop          | Type                        | Description                                                              | Since |
| ------------- | --------------------------- | ------------------------------------------------------------------------ | ----- |
| **`channel`** | <code>string \| null</code> | The channel of the app. If `null`, the app is using the default channel. | 5.0.0 |


#### GetCustomIdResult

| Prop           | Type                        | Description                                                               | Since |
| -------------- | --------------------------- | ------------------------------------------------------------------------- | ----- |
| **`customId`** | <code>string \| null</code> | The custom identifier of the app. If `null`, no custom identifier is set. | 5.0.0 |


#### GetDeviceIdResult

| Prop           | Type                | Description                                                                                                                                                                                                                                                                    | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`deviceId`** | <code>string</code> | The unique identifier of the device. On iOS, [`identifierForVendor`](https://developer.apple.com/documentation/uikit/uidevice/1620059-identifierforvendor) is used. The value of this property is the same for apps that come from the same vendor running on the same device. | 5.0.0 |


#### GetVersionCodeResult

| Prop              | Type                | Description                                                                                                                                                                            | Since |
| ----------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`versionCode`** | <code>string</code> | The version code of the app. On **Android**, this is the `versionCode` from the `android/app/build.gradle` file. On **iOS**, this is the `CFBundleVersion` from the `Info.plist` file. | 5.0.0 |


#### GetVersionNameResult

| Prop              | Type                | Description                                                                                                                                                                                       | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`versionName`** | <code>string</code> | The version name of the app. On **Android**, this is the `versionName` from the `android/app/build.gradle` file. On **iOS**, this is the `CFBundleShortVersionString` from the `Info.plist` file. | 5.0.0 |


#### SetBundleOptions

| Prop           | Type                | Description                                 | Since |
| -------------- | ------------------- | ------------------------------------------- | ----- |
| **`bundleId`** | <code>string</code> | The unique identifier of the bundle to use. | 5.0.0 |


#### SetChannelOptions

| Prop          | Type                        | Description                                               | Since |
| ------------- | --------------------------- | --------------------------------------------------------- | ----- |
| **`channel`** | <code>string \| null</code> | The channel of the app. Set `null` to remove the channel. | 5.0.0 |


#### SetCustomIdOptions

| Prop           | Type                        | Description                                                                   | Since |
| -------------- | --------------------------- | ----------------------------------------------------------------------------- | ----- |
| **`customId`** | <code>string \| null</code> | The custom identifier of the app. Set `null` to remove the custom identifier. | 5.0.0 |


#### SyncResult

| Prop               | Type                        | Description                                                                                                | Since |
| ------------------ | --------------------------- | ---------------------------------------------------------------------------------------------------------- | ----- |
| **`nextBundleId`** | <code>string \| null</code> | The identifier of the next bundle to use. If `null`, the app is up-to-date and no new bundle is available. | 5.0.0 |

</docgen-api>

## Testing

When testing the plugin, you must make sure that you do not use the [Live Reload](https://ionicframework.com/docs/cli/livereload) option, as in this case a development server is used to load the bundle.

Therefore, simply start your app without the live reload option, for example with the following command:

```bash
npx ionic cap run android --open
```

If you want to disable the plugin to test other parts of your app, you can set the [`enabled`](#configuration) configuration option to `false`.

## Limitations

Live updates are only supported for [binary-compatible changes](https://capawesome.io/cloud/live-updates/faq/#what-are-binary-compatible-changes) (e.g. HTML, CSS, JavaScript).
If you change native code, such as adding a new plugin, you need to resubmit your app to the app stores.
For this reason, you must be careful to limit live updates to compatible native versions of your app.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/live-update/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/live-update/LICENSE).
