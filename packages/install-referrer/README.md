# Capacitor Install Referrer Plugin

Capacitor plugin for reading install attribution data from the [Play Install Referrer](https://developer.android.com/google/play/installreferrer) and [Apple Ad Services](https://developer.apple.com/documentation/adservices).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🤖 **Play Install Referrer**: Read the Play Store install referrer on Android.
- 🍏 **Apple Ad Services**: Read the Apple Ad Services attribution token on iOS.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Install Referrer plugin is typically used to attribute app installs to marketing campaigns, for example:

- **Campaign attribution**: Attribute Android installs to a campaign by reading the referrer URL of the installed package.
- **Apple Search Ads measurement**: Measure iOS campaign performance by exchanging the Apple Ad Services attribution token server-side.
- **Deferred deep linking**: Pass parameters through the Play Store referrer URL to route users to the right content after the first launch.
- **Google Play Instant detection**: Detect whether the app was installed via a Google Play Instant experience within the past 7 days.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-install-referrer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-install-referrer
npx cap sync
```

The `getInstallReferrer(...)` method is only available on **Android**, the `getAttributionToken(...)` method is only available on **iOS**. On all other platforms, the respective method rejects as unimplemented.

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$installReferrerVersion` version of `com.android.installreferrer:installreferrer` (default: `2.2`)

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to read the Play Store install referrer on Android and the Apple Ad Services attribution token on iOS.

### Read the Play Store install referrer

Get the Play Store install referrer information, including the referrer URL and the click and install timestamps. Fetch it once shortly after the first launch and persist it, since Google only guarantees its availability for a limited window after the install. Only available on Android:

```typescript
import { InstallReferrer } from '@capawesome/capacitor-install-referrer';

const getInstallReferrer = async () => {
  const result = await InstallReferrer.getInstallReferrer();
  return result;
};
```

### Read the Apple Ad Services attribution token

Get the Apple Ad Services attribution token. The token is opaque and must be exchanged server-side with Apple's attribution API (see [Attribution Token Exchange](#attribution-token-exchange-ios)). Only available on iOS:

```typescript
import { InstallReferrer } from '@capawesome/capacitor-install-referrer';

const getAttributionToken = async () => {
  const { token } = await InstallReferrer.getAttributionToken();
  return token;
};
```

## API

<docgen-index>

* [`getAttributionToken()`](#getattributiontoken)
* [`getInstallReferrer()`](#getinstallreferrer)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAttributionToken()

```typescript
getAttributionToken() => Promise<GetAttributionTokenResult>
```

Get the Apple Ad Services attribution token.

The token is opaque and only the client half of the attribution flow. It
must be exchanged server-side with Apple's Ad Services attribution API
(`https://api-adservices.apple.com/api/v1/`) and expires after 24 hours.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#getattributiontokenresult">GetAttributionTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getInstallReferrer()

```typescript
getInstallReferrer() => Promise<GetInstallReferrerResult>
```

Get the Play Store install referrer information.

The referrer should be fetched once shortly after the first launch and
persisted by your app. Google only guarantees availability for a limited
window after the install.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getinstallreferrerresult">GetInstallReferrerResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetAttributionTokenResult

| Prop        | Type                | Description                              | Since |
| ----------- | ------------------- | ---------------------------------------- | ----- |
| **`token`** | <code>string</code> | The Apple Ad Services attribution token. | 0.1.0 |


#### GetInstallReferrerResult

| Prop                               | Type                 | Description                                                                                | Since |
| ---------------------------------- | -------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`googlePlayInstantParam`**       | <code>boolean</code> | Whether the app was installed via a Google Play Instant experience within the past 7 days. | 0.1.0 |
| **`installBeginTimestampMillis`**  | <code>number</code>  | The client-side timestamp (in milliseconds) when the app installation began.               | 0.1.0 |
| **`referrerClickTimestampMillis`** | <code>number</code>  | The client-side timestamp (in milliseconds) when the referrer click happened.              | 0.1.0 |
| **`referrerUrl`**                  | <code>string</code>  | The referrer URL of the installed package.                                                 | 0.1.0 |

</docgen-api>

## Install Referrer Window (Android)

Google only guarantees that the Play Store install referrer is available for a limited window after the app is installed. You should fetch the referrer once shortly after the first launch and persist it in your app, so that it remains available later.

## Attribution Token Exchange (iOS)

The token returned by `getAttributionToken(...)` is opaque and only the client half of the attribution flow. To resolve the attribution data, you must exchange the token server-side against Apple's Ad Services attribution API:

```
POST https://api-adservices.apple.com/api/v1/
Content-Type: text/plain

<attribution-token>
```

The token expires after 24 hours, so it should be exchanged promptly. See the [Apple documentation](https://developer.apple.com/documentation/adservices/aaattribution/attributiontoken()) for more information.

## FAQ

### Which platforms does this plugin support?

The `getInstallReferrer(...)` method is only available on Android and the `getAttributionToken(...)` method is only available on iOS. On all other platforms, the respective method rejects as unimplemented.

### Why is the install referrer no longer available on Android?

Google only guarantees that the Play Store install referrer is available for a limited window after the app is installed. You should therefore fetch the referrer once shortly after the first launch and persist it in your app, so that it remains available later.

### How do I resolve the attribution data from the Apple attribution token?

The token returned by `getAttributionToken(...)` is opaque and only the client half of the attribution flow. You must exchange it server-side against Apple's Ad Services attribution API, as described in [Attribution Token Exchange](#attribution-token-exchange-ios). Note that the token expires after 24 hours, so it should be exchanged promptly.

### Do I need any permissions or special configuration?

No, the plugin does not require any runtime permissions or configuration. On Android, it uses the `com.android.installreferrer:installreferrer` library, whose version can be changed via the `$installReferrerVersion` project variable if needed (see [Installation](#installation)).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/): Request the user's permission to track them via the App Tracking Transparency framework on iOS.
- [PostHog](https://capawesome.io/docs/sdks/capacitor/posthog/): Capture analytics events with the PostHog platform.
- [Firebase Analytics](https://capawesome.io/docs/sdks/capacitor/firebase/analytics/): Measure user engagement with Firebase Analytics.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/install-referrer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/install-referrer/LICENSE).
