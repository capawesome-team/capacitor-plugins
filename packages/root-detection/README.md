# Capacitor Root Detection Plugin

Capacitor plugin for detecting rooted and jailbroken devices.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔒 **Root detection**: Detect rooted (Android) and jailbroken (iOS) devices.
- 🤖 **Emulator detection**: Detect whether the app is running on an emulator or simulator.
- 🛠️ **Developer mode**: Detect whether developer mode is enabled (Android).
- 🤝 **Compatibility**: Works alongside the [App Integrity](https://capawesome.io/docs/sdks/capacitor/app-integrity/) plugin for server-verifiable device attestation.
- 📦 **SPM**: Supports Swift Package Manager and CocoaPods for iOS.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-root-detection` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-root-detection
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$rootbeerVersion` version of `com.scottyab:rootbeer-lib` (default: `0.1.0`)

### iOS

#### Application Queries Schemes

The `isRooted()` method checks whether the `cydia://` URL scheme can be opened as part of its jailbreak heuristics. For this check to work, add the `cydia` scheme to the `LSApplicationQueriesSchemes` array in the `Info.plist` file of your app:

```xml
<key>LSApplicationQueriesSchemes</key>
<array>
  <string>cydia</string>
</array>
```

This step is optional. If the scheme is not added, the `isRooted()` method still works but skips the URL scheme check.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { RootDetection } from '@capawesome/capacitor-root-detection';

const isRooted = async () => {
  const { rooted } = await RootDetection.isRooted();
  return rooted;
};

const isEmulator = async () => {
  const { emulator } = await RootDetection.isEmulator();
  return emulator;
};

const isDeveloperModeEnabled = async () => {
  const { enabled } = await RootDetection.isDeveloperModeEnabled();
  return enabled;
};
```

## API

<docgen-index>

* [`isDeveloperModeEnabled()`](#isdevelopermodeenabled)
* [`isEmulator()`](#isemulator)
* [`isRooted()`](#isrooted)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isDeveloperModeEnabled()

```typescript
isDeveloperModeEnabled() => Promise<IsDeveloperModeEnabledResult>
```

Check whether developer mode is enabled on the device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isdevelopermodeenabledresult">IsDeveloperModeEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isEmulator()

```typescript
isEmulator() => Promise<IsEmulatorResult>
```

Check whether the app is running on an emulator (Android) or simulator (iOS).

**Returns:** <code>Promise&lt;<a href="#isemulatorresult">IsEmulatorResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isRooted()

```typescript
isRooted() => Promise<IsRootedResult>
```

Check whether the device is rooted (Android) or jailbroken (iOS).

**Attention**: This is a best-effort, client-side check that can be
bypassed by a determined attacker. Do not rely on it as the sole security
measure. Use the App Integrity plugin when server-verifiable trust is
required.

**Returns:** <code>Promise&lt;<a href="#isrootedresult">IsRootedResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### IsDeveloperModeEnabledResult

| Prop          | Type                 | Description                                      | Since |
| ------------- | -------------------- | ------------------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether developer mode is enabled on the device. | 0.1.0 |


#### IsEmulatorResult

| Prop           | Type                 | Description                                                             | Since |
| -------------- | -------------------- | ----------------------------------------------------------------------- | ----- |
| **`emulator`** | <code>boolean</code> | Whether the app is running on an emulator (Android) or simulator (iOS). | 0.1.0 |


#### IsRootedResult

| Prop         | Type                 | Description                                                 | Since |
| ------------ | -------------------- | ----------------------------------------------------------- | ----- |
| **`rooted`** | <code>boolean</code> | Whether the device is rooted (Android) or jailbroken (iOS). | 0.1.0 |

</docgen-api>

## Security Considerations

The checks provided by this plugin are performed entirely on the device and are **best-effort**. A determined attacker with full control over a rooted or jailbroken device can bypass or spoof any client-side detection. Therefore, you should **never** rely on this plugin as the sole security measure for protecting sensitive functionality.

For **server-verifiable** device and app integrity, use the [App Integrity](https://capawesome.io/docs/sdks/capacitor/app-integrity/) plugin, which leverages the Play Integrity API (Android) and App Attest (iOS) to produce attestations that can be validated on your backend.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/root-detection/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/root-detection/LICENSE).
