# Capacitor TikTok App Events Plugin

Unofficial Capacitor plugin for the [TikTok App Events SDK](https://ads.tiktok.com/help/article/how-to-integrate-tiktok-app-events-sdk).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor TikTok App Events plugin is a modern integration of the TikTok App Events SDK for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📊 **Event Tracking**: Track standard and custom events with properties such as currency, value and contents.
- 🤖 **Automatic Events**: Let the SDK log app installs, launches, retention and in-app purchases automatically.
- 👤 **Identification**: Improve attribution by identifying users with hashed personal data.
- 🐞 **Debug Mode**: Verify your integration in the TikTok Events Manager before you release.
- 🍎 **SKAdNetwork**: Control whether the SDK updates the SKAdNetwork conversion value on iOS.
- 🔒 **Limited Data Use**: Enable Limited Data Use mode to comply with privacy regulations.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The TikTok App Events plugin is typically used to measure and optimize TikTok ad campaigns, for example:

- **App install campaigns**: Attribute app installs to your TikTok ads.
- **Conversion tracking**: Report purchases, registrations and other in-app conversions to TikTok.
- **App event optimization**: Optimize your campaigns for the in-app events that matter to your business.
- **Retargeting**: Build custom audiences from in-app activity to re-engage existing users.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-tiktok-app-events` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-tiktok-app-events
npx cap sync
```

This plugin requires a [TikTok for Business](https://ads.tiktok.com/) account. Connect your app in the **TikTok Events Manager** and generate a **TikTok App ID** and an **Access Token** as described in the [TikTok guide](https://ads.tiktok.com/help/article/how-to-integrate-tiktok-app-events-sdk).

> [!IMPORTANT]
> Use of the TikTok App Events SDK is governed by the [TikTok for Business Commercial Terms of Service](https://ads.tiktok.com/i18n/official/policy/commercial-terms-of-service) and the [TikTok Business Products (Data) Terms](https://ads.tiktok.com/i18n/official/policy/business-products-terms), which prohibit sharing sensitive data with TikTok (see [Third-Party Notices](#third-party-notices)). This plugin declares the SDKs as dependencies and downloads them from JitPack, CocoaPods or Swift Package Manager at build time. It does not bundle or modify them.

### Android

The [TikTok App Events SDK for Android](https://github.com/tiktok/tiktok-business-android-sdk) is resolved from [JitPack](https://jitpack.io/). The plugin already declares the JitPack repository in its `build.gradle` file. If your project restricts repository declarations to the settings file (e.g. via `dependencyResolutionManagement` with `FAIL_ON_PROJECT_REPOS`), add the JitPack repository to your `settings.gradle` file:

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$tiktokBusinessSdkVersion` version of `com.github.tiktok:tiktok-business-android-sdk` (default: `1.7.1`)
- `$installReferrerVersion` version of `com.android.installreferrer:installreferrer` (default: `2.2`)

### iOS

The [TikTok App Events SDK for iOS](https://github.com/tiktok/tiktok-business-ios-sdk) can be integrated via Swift Package Manager (recommended) or CocoaPods.

#### App Tracking Transparency

The SDK only collects the advertising identifier (IDFA) if the user has granted tracking permission. You can request the permission with the [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/) plugin.

#### SKAdNetwork

To attribute installs via SKAdNetwork, add the TikTok SKAdNetwork identifiers to your `ios/App/App/Info.plist` file as described in the [TikTok App Events SDK documentation](https://business-api.tiktok.com/portal/docs?id=1739585432134657). If another SDK (e.g. a mobile measurement partner) already updates the SKAdNetwork conversion value, set `iosSkAdNetworkSupport` to `false` when calling `initialize(...)`.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Initialize the plugin

Call `initialize(...)` once before all other methods, for example on app start or after the user has given consent:

```typescript
import { TiktokAppEvents } from '@capawesome/capacitor-tiktok-app-events';

const initialize = async () => {
  await TiktokAppEvents.initialize({
    accessToken: 'YOUR_ACCESS_TOKEN',
    tiktokAppId: 'YOUR_TIKTOK_APP_ID',
    iosAppId: 'YOUR_APP_STORE_ID',
  });
};
```

### Track events

Track a standard event with properties or a custom event:

```typescript
import { TiktokAppEvents } from '@capawesome/capacitor-tiktok-app-events';

const trackPurchase = async () => {
  await TiktokAppEvents.trackEvent({
    name: 'Purchase',
    properties: {
      currency: 'USD',
      value: 9.99,
      contents: [
        {
          content_id: 'sku-123',
          content_type: 'product',
          quantity: 1,
          price: 9.99,
        },
      ],
    },
  });
};

const trackCustomEvent = async () => {
  await TiktokAppEvents.trackEvent({
    name: 'LevelCompleted',
    properties: { level: 3 },
  });
};
```

See [Supported In-App Events](https://ads.tiktok.com/help/article/all-supported-in-app-events) for the list of standard event names and properties.

### Identify the user

Identify the user after sign-in and log out when the user signs out:

```typescript
import { TiktokAppEvents } from '@capawesome/capacitor-tiktok-app-events';

const identify = async () => {
  await TiktokAppEvents.identify({
    externalId: 'user-123',
    email: 'jane.doe@example.com',
  });
};

const logout = async () => {
  await TiktokAppEvents.logout();
};
```

## API

<docgen-index>

* [`flush()`](#flush)
* [`identify(...)`](#identify)
* [`initialize(...)`](#initialize)
* [`logout()`](#logout)
* [`trackEvent(...)`](#trackevent)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### flush()

```typescript
flush() => Promise<void>
```

Flush all queued events to TikTok immediately.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### identify(...)

```typescript
identify(options: IdentifyOptions) => Promise<void>
```

Identify the current user to improve the attribution of events.

Personal data is hashed (SHA-256) by the TikTok SDK on the device
before it is sent to TikTok.

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#identifyoptions">IdentifyOptions</a></code> |

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the TikTok App Events SDK.

This method must be called before any other method.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### logout()

```typescript
logout() => Promise<void>
```

Log out the current user and clear the identification data.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### trackEvent(...)

```typescript
trackEvent(options: TrackEventOptions) => Promise<void>
```

Track an event.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#trackeventoptions">TrackEventOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### IdentifyOptions

| Prop                   | Type                | Description                                       | Since |
| ---------------------- | ------------------- | ------------------------------------------------- | ----- |
| **`email`**            | <code>string</code> | The email address of the user.                    | 0.1.0 |
| **`externalId`**       | <code>string</code> | The unique identifier of the user in your system. | 0.1.0 |
| **`externalUserName`** | <code>string</code> | The user name of the user.                        | 0.1.0 |
| **`phoneNumber`**      | <code>string</code> | The phone number of the user.                     | 0.1.0 |


#### InitializeOptions

| Prop                            | Type                 | Description                                                                                                                                                                                                       | Default            | Since |
| ------------------------------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`accessToken`**               | <code>string</code>  | The access token of your app. You can find it in the TikTok Events Manager.                                                                                                                                       |                    | 0.1.0 |
| **`automaticPurchaseTracking`** | <code>boolean</code> | Whether the SDK automatically tracks in-app purchases made through Google Play Billing (Android) or StoreKit (iOS).                                                                                               | <code>true</code>  | 0.1.0 |
| **`automaticTracking`**         | <code>boolean</code> | Whether the SDK automatically tracks app installs, app launches and second-day retention.                                                                                                                         | <code>true</code>  | 0.1.0 |
| **`debugMode`**                 | <code>boolean</code> | Whether debug mode is enabled. In debug mode, events are sent to the test events pipeline of the TikTok Events Manager and are not used for reporting. Make sure to disable debug mode before releasing your app. | <code>false</code> | 0.1.0 |
| **`iosAppId`**                  | <code>string</code>  | The App Store ID of your app. Required on iOS. On Android, the package name of your app is used instead. Only available on iOS.                                                                                   |                    | 0.1.0 |
| **`iosSkAdNetworkSupport`**     | <code>boolean</code> | Whether the SDK updates the SKAdNetwork conversion value. Disable this if another SDK (e.g. a mobile measurement partner) already updates the conversion value to avoid conflicts. Only available on iOS.         | <code>true</code>  | 0.1.0 |
| **`limitedDataUse`**            | <code>boolean</code> | Whether Limited Data Use (LDU) mode is enabled.                                                                                                                                                                   | <code>false</code> | 0.1.0 |
| **`tiktokAppId`**               | <code>string</code>  | The TikTok App ID of your app. You can find it in the TikTok Events Manager. Multiple IDs can be provided as a comma-separated list.                                                                              |                    | 0.1.0 |


#### TrackEventOptions

| Prop             | Type                                       | Description                                                                                                                | Since |
| ---------------- | ------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**         | <code>string</code>                        | A unique identifier of the event. Used by TikTok to deduplicate events that are also reported through the Events API.      | 0.1.0 |
| **`name`**       | <code>string</code>                        | The name of the event. Can be a standard event name (e.g. `Purchase`, `AddToCart`, `Registration`) or a custom event name. | 0.1.0 |
| **`properties`** | <code>Record&lt;string, unknown&gt;</code> | The properties of the event.                                                                                               | 0.1.0 |

</docgen-api>

## FAQ

### Do I need a TikTok for Business account to use this plugin?

Yes. This plugin wraps the official TikTok App Events SDKs, which require a [TikTok for Business](https://ads.tiktok.com/) account, a TikTok App ID and an access token.

### Where do I find the TikTok App ID and the access token?

Both are generated in the TikTok Events Manager when you connect your app with the TikTok SDK. See the [TikTok guide](https://ads.tiktok.com/help/article/how-to-integrate-tiktok-app-events-sdk) for step-by-step instructions.

### How can I verify that events are received by TikTok?

Enable `debugMode` when calling `initialize(...)`. Events are then sent to the test events pipeline and appear in the **Test Events** tab of the TikTok Events Manager. Call `flush()` to send queued events immediately. Make sure to disable debug mode before releasing your app.

### Is personal data hashed before it is sent to TikTok?

Yes. The TikTok SDK hashes the email address, phone number and external ID with SHA-256 on the device before they are sent to TikTok.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/): Request the tracking permission required to collect the IDFA on iOS.
- [Firebase Analytics](https://capawesome.io/docs/sdks/capacitor/firebase/analytics/): Unofficial Capacitor plugin for Firebase Analytics.
- [PostHog](https://capawesome.io/docs/sdks/capacitor/posthog/): Unofficial Capacitor plugin for the PostHog product analytics platform.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/tiktok-app-events/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/tiktok-app-events/LICENSE).

## Third-Party Notices

The TikTok App Events SDKs for Android and iOS are licensed under the MIT license and are distributed via JitPack, CocoaPods, and Swift Package Manager. Their use is additionally governed by the [TikTok for Business Commercial Terms of Service](https://ads.tiktok.com/i18n/official/policy/commercial-terms-of-service) and the [TikTok Business Products (Data) Terms](https://ads.tiktok.com/i18n/official/policy/business-products-terms), which include the obligation not to share sensitive data with TikTok. This plugin only declares these SDKs as dependencies and does not bundle or modify them. The MIT license of this plugin covers the wrapper code only, not the TikTok SDKs.

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by TikTok Inc. or any of its affiliates or subsidiaries. "TikTok" is a trademark of TikTok Inc. or its affiliates.
