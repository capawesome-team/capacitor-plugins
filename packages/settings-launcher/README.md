# Capacitor Settings Launcher Plugin

Capacitor plugin to open native settings screens.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ⚙️ **App settings**: Open the settings screen of your app to guide users to grant a denied permission.
- 🔔 **Notification settings**: Open the notification settings screen of your app.
- 🤖 **Android settings**: Open a large catalog of Android system settings screens.
- 🔒 **App Store safe**: Uses only official platform APIs — no private URL schemes.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [App Language](https://capawesome.io/docs/sdks/capacitor/app-language/) and [Android Battery Optimization](https://capawesome.io/docs/sdks/capacitor/android-battery-optimization/) plugins.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Settings Launcher plugin is typically used to guide users to the right system settings screen, for example:

- **Permission recovery**: Open your app's settings screen so users can grant a previously denied permission.
- **Notification opt-in**: Guide users to your app's notification settings when notifications are disabled.
- **Connectivity troubleshooting**: Open the Wi-Fi, Bluetooth, or airplane mode settings on Android when your app requires a connection.
- **Onboarding flows**: Send users to Android settings screens such as locale, location, or NFC to enable features your app depends on.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-settings-launcher` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-settings-launcher
npx cap sync
```

### Android

No additional configuration is required for this plugin.

### iOS

The `openNotificationSettings(...)` method requires iOS 16 or later. On older versions, the call rejects as unavailable.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { SettingsLauncher, AndroidSettingsPage } from '@capawesome/capacitor-settings-launcher';
```

### Open the settings screen of your app

Open your app's settings screen, for example to guide users to grant a previously denied permission. Only available on Android and iOS:

```typescript
const openAppSettings = async () => {
  await SettingsLauncher.openAppSettings();
};
```

### Open the notification settings of your app

Open your app's notification settings screen, for example when the user has disabled notifications. On iOS, this method requires iOS 16 or later and rejects on older versions. Only available on Android and iOS:

```typescript
const openNotificationSettings = async () => {
  await SettingsLauncher.openNotificationSettings();
};
```

### Open an Android system settings screen

Use the `AndroidSettingsPage` enum to open one of the many supported Android system settings screens, such as Wi-Fi, Bluetooth, or location. Only available on Android:

```typescript
const openAndroidSettings = async () => {
  await SettingsLauncher.openAndroidSettings({
    page: AndroidSettingsPage.Wifi,
  });
};
```

## API

<docgen-index>

* [`openAndroidSettings(...)`](#openandroidsettings)
* [`openAppSettings()`](#openappsettings)
* [`openNotificationSettings()`](#opennotificationsettings)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### openAndroidSettings(...)

```typescript
openAndroidSettings(options: OpenAndroidSettingsOptions) => Promise<void>
```

Open a specific Android system settings screen.

Only available on Android.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#openandroidsettingsoptions">OpenAndroidSettingsOptions</a></code> |

**Since:** 0.1.0

--------------------


### openAppSettings()

```typescript
openAppSettings() => Promise<void>
```

Open the settings screen of your app.

This is the recommended way to guide users to grant a previously denied
permission.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### openNotificationSettings()

```typescript
openNotificationSettings() => Promise<void>
```

Open the notification settings screen of your app.

On iOS, this method requires iOS 16 or later and rejects on older versions.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### Interfaces


#### OpenAndroidSettingsOptions

| Prop       | Type                                                                | Description                                 | Since |
| ---------- | ------------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`page`** | <code><a href="#androidsettingspage">AndroidSettingsPage</a></code> | The Android system settings screen to open. | 0.1.0 |


### Enums


#### AndroidSettingsPage

| Members                         | Value                                      | Description                                                                                               | Since |
| ------------------------------- | ------------------------------------------ | --------------------------------------------------------------------------------------------------------- | ----- |
| **`Accessibility`**             | <code>'ACCESSIBILITY'</code>               | Show settings for accessibility modules.                                                                  | 0.1.0 |
| **`AirplaneMode`**              | <code>'AIRPLANE_MODE'</code>               | Show settings to allow entering/exiting airplane mode.                                                    | 0.1.0 |
| **`Apn`**                       | <code>'APN'</code>                         | Show settings to allow configuration of APNs.                                                             | 0.1.0 |
| **`BatterySaver`**              | <code>'BATTERY_SAVER'</code>               | Show settings to allow configuration of battery saver mode.                                               | 0.1.0 |
| **`Bluetooth`**                 | <code>'BLUETOOTH'</code>                   | Show settings to allow configuration of Bluetooth.                                                        | 0.1.0 |
| **`Captioning`**                | <code>'CAPTIONING'</code>                  | Show settings for video captioning.                                                                       | 0.1.0 |
| **`CastSettings`**              | <code>'CAST_SETTINGS'</code>               | Show settings to allow configuration of cast endpoints.                                                   | 0.1.0 |
| **`DataRoaming`**               | <code>'DATA_ROAMING'</code>                | Show settings for selection of 2G/3G.                                                                     | 0.1.0 |
| **`Date`**                      | <code>'DATE'</code>                        | Show settings to allow configuration of date and time.                                                    | 0.1.0 |
| **`Development`**               | <code>'DEVELOPMENT'</code>                 | Show settings to allow configuration of application development settings.                                 | 0.1.0 |
| **`DeviceInfo`**                | <code>'DEVICE_INFO'</code>                 | Show general device information settings (serial number, software version, phone number, etc.).           | 0.1.0 |
| **`Display`**                   | <code>'DISPLAY'</code>                     | Show settings to allow configuration of display.                                                          | 0.1.0 |
| **`Dream`**                     | <code>'DREAM'</code>                       | Show Daydream settings.                                                                                   | 0.1.0 |
| **`Home`**                      | <code>'HOME'</code>                        | Show settings for selecting the default home app.                                                         | 0.1.0 |
| **`IgnoreBatteryOptimization`** | <code>'IGNORE_BATTERY_OPTIMIZATION'</code> | Show screen for controlling which apps can ignore battery optimizations.                                  | 0.1.0 |
| **`InputMethod`**               | <code>'INPUT_METHOD'</code>                | Show settings to configure input methods, in particular allowing the user to enable input methods.        | 0.1.0 |
| **`InternalStorage`**           | <code>'INTERNAL_STORAGE'</code>            | Show settings for internal storage.                                                                       | 0.1.0 |
| **`Locale`**                    | <code>'LOCALE'</code>                      | Show settings to allow configuration of locale.                                                           | 0.1.0 |
| **`Location`**                  | <code>'LOCATION'</code>                    | Show settings to allow configuration of current location sources.                                         | 0.1.0 |
| **`ManageAllApplications`**     | <code>'MANAGE_ALL_APPLICATIONS'</code>     | Show settings to manage all applications.                                                                 | 0.1.0 |
| **`ManageApplications`**        | <code>'MANAGE_APPLICATIONS'</code>         | Show settings to manage installed applications.                                                           | 0.1.0 |
| **`MemoryCard`**                | <code>'MEMORY_CARD'</code>                 | Show settings for memory card storage.                                                                    | 0.1.0 |
| **`Network`**                   | <code>'NETWORK'</code>                     | Show settings for selecting the network operator.                                                         | 0.1.0 |
| **`Nfc`**                       | <code>'NFC'</code>                         | Show settings to allow configuration of NFC.                                                              | 0.1.0 |
| **`NfcPayment`**                | <code>'NFC_PAYMENT'</code>                 | Show settings to allow configuration of tap and pay.                                                      | 0.1.0 |
| **`NotificationListener`**      | <code>'NOTIFICATION_LISTENER'</code>       | Show settings for managing which apps have access to notification listener services.                      | 0.1.0 |
| **`Printing`**                  | <code>'PRINTING'</code>                    | Show settings for printing.                                                                               | 0.1.0 |
| **`Privacy`**                   | <code>'PRIVACY'</code>                     | Show settings to allow configuration of privacy options.                                                  | 0.1.0 |
| **`Search`**                    | <code>'SEARCH'</code>                      | Show settings to allow configuration of search.                                                           | 0.1.0 |
| **`Security`**                  | <code>'SECURITY'</code>                    | Show settings to allow configuration of security and location privacy.                                    | 0.1.0 |
| **`Sound`**                     | <code>'SOUND'</code>                       | Show settings to allow configuration of sound and volume.                                                 | 0.1.0 |
| **`Sync`**                      | <code>'SYNC'</code>                        | Show settings to allow configuration of sync settings.                                                    | 0.1.0 |
| **`Usage`**                     | <code>'USAGE'</code>                       | Show screen for controlling which apps can access usage statistics.                                       | 0.1.0 |
| **`UserDictionary`**            | <code>'USER_DICTIONARY'</code>             | Show settings to manage the user input dictionary.                                                        | 0.1.0 |
| **`VoiceInput`**                | <code>'VOICE_INPUT'</code>                 | Show settings to configure input methods, in particular allowing the user to enable voice input services. | 0.1.0 |
| **`Vpn`**                       | <code>'VPN'</code>                         | Show settings to allow configuration of VPN.                                                              | 0.1.0 |
| **`Wifi`**                      | <code>'WIFI'</code>                        | Show settings to allow configuration of Wi-Fi.                                                            | 0.1.0 |
| **`Wireless`**                  | <code>'WIRELESS'</code>                    | Show settings to allow configuration of wireless controls such as Wi-Fi, Bluetooth and mobile networks.   | 0.1.0 |

</docgen-api>

## Migrating from `capacitor-native-settings`

This plugin replaces the single `open(...)` method of `capacitor-native-settings` with three explicit methods. Instead of passing an `optionAndroid` and `optionIOS` to a single method, call the method that matches your intent:

| `capacitor-native-settings`                                | Settings Launcher                        |
| ---------------------------------------------------------- | ---------------------------------------- |
| `open({ optionAndroid: 'application_details', optionIOS: 'App' })` | `openAppSettings()`                      |
| `open({ optionAndroid: 'app_notification', optionIOS: 'App' })`    | `openNotificationSettings()`             |
| `open({ optionAndroid: '<page>', optionIOS: '...' })`      | `openAndroidSettings({ page: '<page>' })` |

On iOS, `openAppSettings()` and `openNotificationSettings()` cover the officially supported destinations. The various `optionIOS` deep links into specific iOS settings sections are intentionally not supported (see below).

## iOS and the `App-Prefs:` URL scheme

This plugin deliberately does **not** provide deep links into specific iOS system settings sections (e.g. Wi-Fi, Bluetooth, Location). Those links rely on the private `App-Prefs:` URL scheme, which is not part of the public iOS API. Apps that use it risk rejection during App Store review, and Apple may break it without notice between iOS releases.

Instead, this plugin only uses official Apple APIs: `UIApplication.openSettingsURLString` (your app's settings) and `openNotificationSettingsURLString` (your app's notification settings, iOS 16+). This is a deliberate design decision to keep your app App Store safe.

## FAQ

### How can I guide users to grant a previously denied permission?

Call the `openAppSettings` method to open the settings screen of your app. This is the recommended way to let users grant a permission that they previously denied, since neither Android nor iOS shows the permission prompt again after a denial. See the [usage example](#open-the-settings-screen-of-your-app) above.

### Why can't I open specific iOS settings sections like Wi-Fi or Bluetooth?

Deep links into specific iOS system settings sections rely on the private `App-Prefs:` URL scheme, which is not part of the public iOS API. Apps that use it risk rejection during App Store review, and Apple may break it without notice between iOS releases. This plugin deliberately only uses official Apple APIs to keep your app App Store safe (see [iOS and the `App-Prefs:` URL scheme](#ios-and-the-app-prefs-url-scheme)).

### Why does `openNotificationSettings` reject on iOS?

The `openNotificationSettings(...)` method requires iOS 16 or later. On older iOS versions, the call rejects as unavailable. The `openAppSettings()` method works on all supported iOS versions and also gives users access to the notification settings of your app.

### Which Android settings screens can I open?

The `openAndroidSettings(...)` method supports a large catalog of Android system settings screens, including Wi-Fi, Bluetooth, location, locale, NFC, display, sound, and many more. See the `AndroidSettingsPage` enum in the [API reference](#api) for the complete list. This method is only available on Android.

### How do I migrate from `capacitor-native-settings`?

This plugin replaces the single `open(...)` method of `capacitor-native-settings` with three explicit methods: `openAppSettings()`, `openNotificationSettings()` and `openAndroidSettings(...)`. See the [migration guide](#migrating-from-capacitor-native-settings) above for a mapping table.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Language](https://capawesome.io/docs/sdks/capacitor/app-language/): Manage the app's own language override, independent of the device language.
- [Android Battery Optimization](https://capawesome.io/docs/sdks/capacitor/android-battery-optimization/): Manage battery optimization settings and request exemptions on Android.
- [App Launcher](https://capawesome.io/docs/sdks/capacitor/app-launcher/): Check if an app can be opened and open it.
- [Android Intent Launcher](https://capawesome.io/docs/sdks/capacitor/android-intent-launcher/): Launch arbitrary Android intents.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/settings-launcher/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/settings-launcher/LICENSE).
