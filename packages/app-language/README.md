# @capawesome/capacitor-app-language

Capacitor plugin to manage the app's own language override, independent of the device language.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🌐 **Language override**: Set the app's language independently of the device language (Android).
- 🔎 **Read language**: Read the app's current language override.
- ♻️ **Reset**: Clear the override so the app follows the device language again (Android).
- ⚙️ **Settings**: Deep-link to the app's system settings page where the user can change the app language.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🔗 **Compatibility**: Works alongside the [Localization](https://capawesome.io/plugins/localization/) plugin, which reads the user's localization preferences.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-language` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-app-language
npx cap sync
```

This plugin only affects **natively rendered** strings (e.g. permission dialogs, notifications, or plugin-presented sheets). The language of your web UI should be handled by your app's i18n library.

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidxAppCompatVersion` version of `androidx.appcompat:appcompat` (default: `1.7.1`)

#### Locale Configuration

To integrate the app language into the system settings (Android 13+), declare the supported locales in a `res/xml/locales_config.xml` file and reference it from the `<application>` tag in your `AndroidManifest.xml`:

```xml
<!-- res/xml/locales_config.xml -->
<?xml version="1.0" encoding="utf-8"?>
<locale-config xmlns:android="http://schemas.android.com/apk/res/android">
    <locale android:name="en" />
    <locale android:name="de" />
</locale-config>
```

```xml
<application
    ...
    android:localeConfig="@xml/locales_config">
```

To persist the selected language across app restarts on Android 12 and below, add the following `<service>` inside the `<application>` tag of your `AndroidManifest.xml`:

```xml
<service
    android:name="androidx.appcompat.app.AppLocalesMetadataHolderService"
    android:enabled="false"
    android:exported="false">
    <meta-data
        android:name="autoStoreLocales"
        android:value="true" />
</service>
```

> [!NOTE]
> Setting the language recreates the current activity, which reloads the web view.

### iOS

On iOS, the app language can only be changed by the **user** in the system settings. The `setLanguage(...)` and `resetLanguage(...)` methods are therefore not available. Use `openSettings(...)` to deep-link the user to the app's settings page.

The per-app language row is only shown in the system settings if the app bundle provides **more than one** localization. Declare the supported localizations in the `Info.plist` file of your app:

```xml
<key>CFBundleLocalizations</key>
<array>
    <string>en</string>
    <string>de</string>
</array>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppLanguage } from '@capawesome/capacitor-app-language';

const getLanguage = async () => {
  const { languageTag } = await AppLanguage.getLanguage();
  return languageTag;
};

const setLanguage = async () => {
  await AppLanguage.setLanguage({ languageTag: 'de-DE' });
};

const resetLanguage = async () => {
  await AppLanguage.resetLanguage();
};

const openSettings = async () => {
  await AppLanguage.openSettings();
};
```

## API

<docgen-index>

* [`getLanguage()`](#getlanguage)
* [`openSettings()`](#opensettings)
* [`resetLanguage()`](#resetlanguage)
* [`setLanguage(...)`](#setlanguage)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getLanguage()

```typescript
getLanguage() => Promise<GetLanguageResult>
```

Get the app's current language override.

The language override is independent of the device language and only
affects natively rendered strings (e.g. permission dialogs).

**Returns:** <code>Promise&lt;<a href="#getlanguageresult">GetLanguageResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the app's settings page in the system settings.

On iOS, this is where the user can change the app's language (the language
row is only shown if the app bundle provides more than one localization).

**Since:** 0.1.0

--------------------


### resetLanguage()

```typescript
resetLanguage() => Promise<void>
```

Reset the app's language override so the app follows the device language
again.

Resetting the language recreates the current activity, which reloads the
web view.

Only available on Android.

**Since:** 0.1.0

--------------------


### setLanguage(...)

```typescript
setLanguage(options: SetLanguageOptions) => Promise<void>
```

Set the app's language override.

Setting the language recreates the current activity, which reloads the
web view.

On iOS, the language can only be changed by the user in the system
settings (see the `openSettings(...)` method).

Only available on Android.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setlanguageoptions">SetLanguageOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### GetLanguageResult

| Prop              | Type                        | Description                                                                                                                           | Since |
| ----------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`languageTag`** | <code>string \| null</code> | The BCP 47 language tag of the app's language override. Returns `null` if no override is set and the app follows the device language. | 0.1.0 |


#### SetLanguageOptions

| Prop              | Type                | Description                                                    | Since |
| ----------------- | ------------------- | -------------------------------------------------------------- | ----- |
| **`languageTag`** | <code>string</code> | The BCP 47 language tag to set as the app's language override. | 0.1.0 |

</docgen-api>

## Platform Behavior

Setting the app language programmatically is only supported on Android. On iOS, the language can only be changed by the user in the system settings.

| Method             | Android | iOS              | Web              |
| ------------------ | ------- | ---------------- | ---------------- |
| `getLanguage()`    | ✅      | ✅               | ❌ Unimplemented |
| `setLanguage(...)` | ✅      | ❌ Unimplemented | ❌ Unimplemented |
| `resetLanguage()`  | ✅      | ❌ Unimplemented | ❌ Unimplemented |
| `openSettings()`   | ✅      | ✅               | ❌ Unimplemented |

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-language/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-language/LICENSE).
