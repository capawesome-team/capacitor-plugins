# Capacitor App Launcher Plugin

Capacitor plugin to check if an app can be opened and to open it.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔗 **Open apps**: Open another app by its URL scheme or package name.
- ✅ **Availability**: Check whether an app can be opened before trying to open it.
- 🤖 **Package names**: Check and open apps by their package name on Android.
- 🔒 **App Store safe**: Uses only official platform APIs.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Maps Launcher](https://capawesome.io/docs/sdks/capacitor/maps-launcher/) and [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) plugins.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-launcher` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-app-launcher
npx cap sync
```

### Android

Starting with Android 11 (API level 30), apps must declare the packages and URL schemes they want to check or open in the [`<queries>`](https://developer.android.com/training/package-visibility) element of their `AndroidManifest.xml`.

**If you do not add the corresponding `<queries>` entries, `canOpenUrl(...)` always resolves with `value: false` and `openUrl(...)` may resolve with `completed: false`.**

Add an entry for **every** package name and URL scheme you pass to the plugin. For example, to check and open the Gmail app (package name `com.google.android.gm`) and any `mailto:` URL, add the following to your `android/app/src/main/AndroidManifest.xml` before or after the `application` tag:

```xml
<queries>
    <!-- Required to check and open a specific package name. -->
    <package android:name="com.google.android.gm" />
    <!-- Required to check and open a specific URL scheme. -->
    <intent>
        <action android:name="android.intent.action.VIEW" />
        <data android:scheme="mailto" />
    </intent>
</queries>
```

### iOS

On iOS, every URL scheme you want to check with `canOpenUrl(...)` must be declared in the [`LSApplicationQueriesSchemes`](https://developer.apple.com/documentation/bundleresources/information_property_list/lsapplicationqueriesschemes) key of your app's `Info.plist`.

**If you do not add the corresponding entries, `canOpenUrl(...)` always resolves with `value: false`.**

Add an entry for **every** URL scheme you pass to the plugin. For example, to check any `mailto:` URL, add the following to your `ios/App/App/Info.plist`:

```xml
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>mailto</string>
</array>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppLauncher } from '@capawesome/capacitor-app-launcher';

const canOpenUrl = async () => {
  const { value } = await AppLauncher.canOpenUrl({ url: 'mailto:' });
  return value;
};

const openUrl = async () => {
  const { completed } = await AppLauncher.openUrl({ url: 'mailto:' });
  return completed;
};
```

## API

<docgen-index>

* [`canOpenUrl(...)`](#canopenurl)
* [`openUrl(...)`](#openurl)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### canOpenUrl(...)

```typescript
canOpenUrl(options: CanOpenUrlOptions) => Promise<CanOpenUrlResult>
```

Check if an app can be opened with the given URL.

On **iOS**, every URL scheme you want to check must be declared in the
`LSApplicationQueriesSchemes` key of your app's `Info.plist`. Otherwise
this method always resolves with `value: false`.

On **Android**, every package name or URL scheme you want to check must be
declared in the `&lt;queries&gt;` element of your app's `AndroidManifest.xml`.
Otherwise this method always resolves with `value: false`.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#canopenurloptions">CanOpenUrlOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#canopenurlresult">CanOpenUrlResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### openUrl(...)

```typescript
openUrl(options: OpenUrlOptions) => Promise<OpenUrlResult>
```

Open an app with the given URL.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#openurloptions">OpenUrlOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#openurlresult">OpenUrlResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CanOpenUrlResult

| Prop        | Type                 | Description                                                                                                                                                  | Since |
| ----------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`value`** | <code>boolean</code> | Whether or not the app can be opened with the given URL. On the web, this is always `true` because the browser cannot determine whether a URL can be opened. | 0.1.0 |


#### CanOpenUrlOptions

| Prop      | Type                | Description                                                                                                                                                                           | Since |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`url`** | <code>string</code> | The URL to check. On **iOS**, this must be a URL scheme (e.g. `mailto:`). On **Android**, this can be a URL scheme (e.g. `mailto:`) or a package name (e.g. `com.google.android.gm`). | 0.1.0 |


#### OpenUrlResult

| Prop            | Type                 | Description                                     | Since |
| --------------- | -------------------- | ----------------------------------------------- | ----- |
| **`completed`** | <code>boolean</code> | Whether or not the app was opened successfully. | 0.1.0 |


#### OpenUrlOptions

| Prop      | Type                | Description                                                                                                                                                                          | Since |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`url`** | <code>string</code> | The URL to open. On **iOS**, this must be a URL scheme (e.g. `mailto:`). On **Android**, this can be a URL scheme (e.g. `mailto:`) or a package name (e.g. `com.google.android.gm`). | 0.1.0 |

</docgen-api>

## Migrating from `@capacitor/app-launcher`

This plugin is a drop-in replacement for the official `@capacitor/app-launcher` plugin. The API is identical, so you only need to change the import and installation.

| `@capacitor/app-launcher`                    | App Launcher                                 |
| -------------------------------------------- | -------------------------------------------- |
| `AppLauncher.canOpenUrl({ url })`            | `AppLauncher.canOpenUrl({ url })`            |
| `AppLauncher.openUrl({ url })`               | `AppLauncher.openUrl({ url })`               |

On Android, the `url` may be a URL scheme or a package name for both `canOpenUrl(...)` and `openUrl(...)`.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-launcher/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-launcher/LICENSE).
