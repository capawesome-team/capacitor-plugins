# Capacitor System WebView Plugin

Capacitor plugin to detect an outdated Android System WebView and guide users to update it.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔍 **Detection**: Read the package name and version of the active Android System WebView provider.
- ⚠️ **Outdated check**: Compare the installed Chromium major version against the minimum your app requires.
- 🛒 **Update flow**: Send users straight to the Play Store entry of their active WebView provider.
- 🤝 **Compatibility**: Works alongside the [App Update](https://capawesome.io/docs/sdks/capacitor/app-update/), [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/) and [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/) plugins.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-system-webview` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-system-webview
npx cap sync
```

This plugin is only available on **Android**. On iOS and Web, all methods reject as unimplemented (see [iOS](#ios) below).

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidxWebkitVersion` version of `androidx.webkit:webkit` (default: `1.14.0`)

### iOS

This plugin has **no iOS implementation** and does not need one. On iOS, the `WKWebView` is part of the operating system and is updated together with iOS itself. It cannot be updated separately, so there is nothing for an app to detect or fix.

All methods reject as unimplemented on iOS.

## Configuration

No configuration required for this plugin.

## Usage

The recommended pattern is to check whether an update is required on app start, prompt the user with the [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/) plugin, and then open the Play Store:

```typescript
import { Dialog } from '@capacitor/dialog';
import { SystemWebView } from '@capawesome/capacitor-system-webview';

const promptForUpdateIfRequired = async () => {
  const { required } = await SystemWebView.isUpdateRequired({
    minMajorVersion: 105,
  });
  if (!required) {
    return;
  }
  const { value } = await Dialog.confirm({
    title: 'Update required',
    message:
      'Your Android System WebView is outdated. Please update it to continue using all features of this app.',
    okButtonTitle: 'Update',
  });
  if (value) {
    await SystemWebView.openAppStore();
  }
};

const getInfo = async () => {
  const info = await SystemWebView.getInfo();
  return info;
};
```

> [!NOTE]
> A WebView update only takes effect the next time the app process is started. The currently running app keeps using the old WebView until it is restarted.

## API

<docgen-index>

* [`getInfo()`](#getinfo)
* [`isUpdateRequired(...)`](#isupdaterequired)
* [`openAppStore()`](#openappstore)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getInfo()

```typescript
getInfo() => Promise<GetInfoResult>
```

Get information about the active Android System WebView provider.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getinforesult">GetInfoResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isUpdateRequired(...)

```typescript
isUpdateRequired(options: IsUpdateRequiredOptions) => Promise<IsUpdateRequiredResult>
```

Check whether the active Android System WebView is older than the minimum
Chromium major version required by your app.

Only available on Android.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isupdaterequiredoptions">IsUpdateRequiredOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isupdaterequiredresult">IsUpdateRequiredResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### openAppStore()

```typescript
openAppStore() => Promise<void>
```

Open the Play Store entry of the active Android System WebView provider so
the user can update it.

Only available on Android.

**Since:** 0.1.0

--------------------


### Interfaces


#### GetInfoResult

| Prop               | Type                        | Description                                                                                                                                                                                                                                                       | Since |
| ------------------ | --------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`majorVersion`** | <code>number \| null</code> | The Chromium major version of the active WebView provider. This is the integer before the first dot of the `versionName` (e.g. `126` for `126.0.6478.122`). `null` if the `versionName` is not a Chromium-style version, which can happen with OEM WebView forks. | 0.1.0 |
| **`packageName`**  | <code>string</code>         | The package name of the active WebView provider.                                                                                                                                                                                                                  | 0.1.0 |
| **`versionName`**  | <code>string</code>         | The version name of the active WebView provider.                                                                                                                                                                                                                  | 0.1.0 |


#### IsUpdateRequiredResult

| Prop           | Type                 | Description                                                                                 | Since |
| -------------- | -------------------- | ------------------------------------------------------------------------------------------- | ----- |
| **`required`** | <code>boolean</code> | `true` if the active WebView is older than the required minimum version, otherwise `false`. | 0.1.0 |


#### IsUpdateRequiredOptions

| Prop                  | Type                | Description                                                                                                                                     | Since |
| --------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`minMajorVersion`** | <code>number</code> | The minimum Chromium major version your app requires. The active WebView is considered outdated if its `majorVersion` is lower than this value. | 0.1.0 |

</docgen-api>

## Why Outdated System WebViews Matter

On Android, your Capacitor app runs inside the Android System WebView, which is updated independently of the operating system. Many devices run an ancient WebView because the user has disabled automatic updates, is offline, or uses a custom ROM. When that happens, modern web features your app relies on may simply be missing, causing hard-to-diagnose "the app is broken" support tickets.

Because "outdated" depends on which web features your app's bundle actually needs, this plugin never hardcodes a threshold. Instead, your app declares the minimum Chromium major version it requires via `isUpdateRequired(...)`.

The following table gives a rough idea of when some popular web features became available in Chromium (and therefore the System WebView):

| Chromium major | Rough web-feature baseline                    |
| -------------- | --------------------------------------------- |
| 97+            | Array `findLast` / `findLastIndex`            |
| 105+           | CSS `:has()` selector and container queries   |
| 112+           | Native CSS nesting                            |
| 114+           | CSS `text-wrap: balance`                      |

Pick the minimum version based on the features your app uses and pass it as `minMajorVersion`.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/system-webview/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/system-webview/LICENSE).
