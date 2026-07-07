# @capawesome/capacitor-privacy-screen

Capacitor plugin to hide sensitive app content in the app switcher, block screenshots, and detect when a screenshot is taken.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🖼️ **App switcher**: Hide sensitive app content in the app switcher.
- 📸 **Screenshot prevention**: Block screenshots and screen recordings.
- 🔔 **Screenshot detection**: Get notified when the user takes a screenshot.
- 🤝 **Compatibility**: Works alongside the [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/) and [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/) plugins.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-privacy-screen` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-privacy-screen
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On Web, all methods reject as unimplemented.

### Android

#### Permissions

The plugin declares the `android.permission.DETECT_SCREEN_CAPTURE` permission in its `AndroidManifest.xml`. This install-time permission is required to detect screenshots on Android 14 (API level 34) and newer. It is added automatically and requires no further configuration.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { PrivacyScreen } from '@capawesome/capacitor-privacy-screen';

const enable = async () => {
  await PrivacyScreen.enable({
    ios: {
      preventScreenshots: true,
    },
  });
};

const disable = async () => {
  await PrivacyScreen.disable();
};

const isEnabled = async () => {
  const { enabled } = await PrivacyScreen.isEnabled();
  return enabled;
};

const addListener = async () => {
  await PrivacyScreen.addListener('screenshotTaken', () => {
    console.log('The user took a screenshot.');
  });
};

const removeAllListeners = async () => {
  await PrivacyScreen.removeAllListeners();
};
```

## API

<docgen-index>

* [`disable()`](#disable)
* [`enable(...)`](#enable)
* [`isEnabled()`](#isenabled)
* [`addListener('screenshotTaken', ...)`](#addlistenerscreenshottaken-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### disable()

```typescript
disable() => Promise<void>
```

Disable the privacy screen.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### enable(...)

```typescript
enable(options?: EnableOptions | undefined) => Promise<void>
```

Enable the privacy screen.

On Android, this sets the `FLAG_SECURE` flag on the window, which hides the
app content in the app switcher and blocks screenshots and screen recordings.

On iOS, this installs an overlay that hides the app content in the app
switcher. Screenshots are only blocked if the `ios.preventScreenshots`
option is enabled.

Only available on Android and iOS.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#enableoptions">EnableOptions</a></code> |

**Since:** 0.1.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Check whether the privacy screen is currently enabled.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('screenshotTaken', ...)

```typescript
addListener(eventName: 'screenshotTaken', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Listen for when the user takes a screenshot of the app.

The event can only be observed after the screenshot has been taken and
therefore cannot be prevented.

On Android, this event is only emitted on Android 14 (API level 34) and
newer. On older versions, the listener is never called.

Only available on Android and iOS.

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>'screenshotTaken'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### EnableOptions

| Prop      | Type                                                          | Description                                                  | Since |
| --------- | ------------------------------------------------------------- | ------------------------------------------------------------ | ----- |
| **`ios`** | <code><a href="#enableiosoptions">EnableIosOptions</a></code> | Options that are only applied on iOS. Only available on iOS. | 0.1.0 |


#### EnableIosOptions

| Prop                     | Type                 | Description                                                                                                                                                                                                                   | Default            | Since |
| ------------------------ | -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`preventScreenshots`** | <code>boolean</code> | Whether to block screenshots while the privacy screen is enabled. There is no official iOS API to prevent screenshots. This uses an unofficial technique that may stop working in future iOS versions. Only available on iOS. | <code>false</code> | 0.1.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                                      | Since |
| ------------- | -------------------- | ------------------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether the privacy screen is currently enabled. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>

## Capabilities

The capabilities of this plugin differ between platforms. The following table gives an honest overview of what is supported:

| Capability                      | Android                | iOS                                    |
| ------------------------------- | ---------------------- | -------------------------------------- |
| Hide content in the app switcher | ✅ (via `enable()`)    | ✅ (via `enable()`)                    |
| Block screenshots                | ✅ (via `enable()`)    | ⚠️ (opt-in via `ios.preventScreenshots`) |
| Detect screenshots               | ✅ (Android 14+ only)  | ✅                                     |

**Android**: The `enable()` method sets the `FLAG_SECURE` window flag, which hides the app content in the app switcher and blocks both screenshots and screen recordings with a single flag. Screenshot detection is only available on Android 14 (API level 34) and newer; on older versions the `screenshotTaken` event is never emitted.

**iOS**: The `enable()` method installs an overlay that hides the app content in the app switcher. There is no official API to block screenshots. When `ios.preventScreenshots` is enabled, the plugin uses an unofficial secure text field technique that may stop working in future iOS versions. Screenshot detection observes `UIApplication.userDidTakeScreenshotNotification`, which only fires **after** the screenshot has been taken and therefore cannot be prevented.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/privacy-screen/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/privacy-screen/LICENSE).
