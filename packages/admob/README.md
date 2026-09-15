# Capacitor AdMob Plugin

Capacitor plugin for monetizing your app with Google AdMob ads on Android and iOS. Built on the Android Next-Gen Google Mobile Ads SDK with banner, interstitial, rewarded, rewarded interstitial and app open ads, ad revenue events, and the canonical User Messaging Platform (UMP) consent flow.

⚠️ **Experimental:** This plugin could not be tested extensively yet. Use it with caution in production and [report any issues](https://github.com/capawesome-team/capacitor-plugins/issues) you encounter.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor AdMob plugin is the most complete way to serve Google AdMob ads in a Capacitor app. Here are some of the key features:

- 🚀 **Next-Gen SDK**: Built on the Android Next-Gen Google Mobile Ads SDK and the latest iOS SDK from day one.
- 🖼️ **Banner Ads**: Anchored adaptive, inline adaptive, fixed sizes, and collapsible banners.
- 📐 **Banner Layout Done Right**: Overlay and resize modes with correct edge-to-edge insets, plus inline placement at a frame you measure in CSS pixels.
- 🎬 **Full-Screen Ads**: Interstitial, rewarded, rewarded interstitial, and app open ads with a uniform load/show API.
- 🔁 **App Open Automation**: Automatically load and show app open ads when the app returns to the foreground, with a frequency cap.
- 💰 **Revenue Events**: Receive the paid event of every ad format for your LTV and analytics pipelines.
- 🛡️ **Consent Management**: Canonical User Messaging Platform (UMP) flow with a single method call, privacy options form, and debug geography testing.
- 🔢 **Multiple Instances**: Load and show multiple typed ad instances at the same time, each with its own identifier.
- 🚨 **Typed Errors**: Every rejection carries a typed error code plus the underlying Google Mobile Ads SDK error message.
- 🧪 **Test Device Support**: Register test devices for ads and consent forms.
- 🤝 **Compatibility**: Works hand in hand with the [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/) and [Purchases](https://capawesome.io/docs/sdks/capacitor/purchases/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The AdMob plugin is typically used to monetize free apps, for example:

- **Banner ads**: Display a persistent banner at the top or bottom of your app, or inline within scrolling content.
- **Interstitial ads**: Show a full-screen ad at natural transition points, such as between levels of a game.
- **Rewarded ads**: Grant users in-app rewards, such as coins or extra lives, for watching an ad.
- **App open ads**: Show an ad when users bring your app back to the foreground.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-admob` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-admob
npx cap sync
```

### Android

#### AdMob App ID

Add your [AdMob app ID](https://support.google.com/admob/answer/7356431) as a `meta-data` element to the `application` element of your `AndroidManifest.xml` file:

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-3940256099942544~3347511713" />
```

**Attention**: The value above is Google's official public **test** app ID. Replace it with your own AdMob app ID before releasing your app. If the `meta-data` element is missing, `initialize(...)` rejects with a clear error message.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default version of the dependencies:

- `$adsMobileSdkVersion` version of `com.google.android.libraries.ads.mobile.sdk:ads-mobile-sdk` (default: `1.2.1`)
- `$userMessagingPlatformVersion` version of `com.google.android.ump:user-messaging-platform` (default: `3.2.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

The plugin uses the **Next-Gen** Google Mobile Ads SDK for Android, which is fetched from Google's Maven repository by your app — it is not bundled with the plugin.

### iOS

#### AdMob App ID

Add the `GADApplicationIdentifier` key with your [AdMob app ID](https://support.google.com/admob/answer/7356431) as value to the `Info.plist` file of your app:

```xml
<key>GADApplicationIdentifier</key>
<string>ca-app-pub-3940256099942544~1458002511</string>
```

**Attention**: The value above is Google's official public **test** app ID. Replace it with your own AdMob app ID before releasing your app. If the key is missing, `initialize(...)` rejects with a clear error message.

#### SKAdNetwork

Add the [SKAdNetwork identifiers](https://developers.google.com/admob/ios/quick-start#update_your_infoplist) recommended by Google to the `Info.plist` file of your app so that ad attribution works correctly:

```xml
<key>SKAdNetworkItems</key>
<array>
  <dict>
    <key>SKAdNetworkIdentifier</key>
    <string>cstr6suwn9.skadnetwork</string>
  </dict>
</array>
```

**Attention**: The list above only contains Google's own identifier. Google recommends adding the complete list of identifiers from the [official documentation](https://developers.google.com/admob/ios/quick-start#update_your_infoplist), which also covers third-party buyers.

#### App Tracking Transparency

If you want to serve personalized ads, you must request the user's permission to track them across apps and websites. Use the [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/) plugin for this and follow Google's recommended order:

1. Call `requestConsent(...)` (see [Usage](#gather-consent-and-initialize-the-sdk)). The UMP consent form includes the App Tracking Transparency context on iOS.
2. Request the tracking permission with the App Tracking Transparency plugin **before** initializing the Google Mobile Ads SDK.
3. Call `initialize(...)`.

This order is important to avoid App Store rejections.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to gather the user's consent, initialize the SDK, show banner, interstitial, rewarded and app open ads, track ad revenue, and test your integration.

### Gather consent and initialize the SDK

Google requires that you gather the user's consent with the User Messaging Platform (UMP) before requesting ads if the user is located in the European Economic Area (EEA), the UK, or a regulated US state. Set up your consent message in the [AdMob console](https://support.google.com/admob/answer/10113207) under **Privacy & messaging** first.

The plugin implements the canonical UMP flow in a single method call. Call `requestConsent(...)` on **every app launch** before initializing the Google Mobile Ads SDK. It requests the latest consent information and shows the consent form if consent is required:

```typescript
import { Admob } from '@capawesome-team/capacitor-admob';

const setupAds = async () => {
  // 1. Gather consent (shows the consent form only if required)
  const { canRequestAds, privacyOptionsRequired } = await Admob.requestConsent();
  // 2. Initialize the Google Mobile Ads SDK
  if (canRequestAds) {
    await Admob.initialize();
  }
  return privacyOptionsRequired;
};
```

If `privacyOptionsRequired` is `true`, you must offer the user a way to change their consent settings, for example from a privacy settings page:

```typescript
const showPrivacyOptions = async () => {
  await Admob.showPrivacyOptionsForm();
};
```

**Note**: The load methods of the plugin reject with the `CONSENT_NOT_GATHERED` error code if ads cannot be requested yet. This prevents ad requests that violate Google's EU User Consent Policy.

### Show a banner ad

The plugin supports three ways to place a banner ad. Choose the one that fits your layout:

| Mode                             | Description                                                                                              | When to use                                                                    |
| -------------------------------- | -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| `mode: 'overlay'` (default)      | The banner ad is displayed on top of the web view, anchored to the top or bottom edge (safe-area aware). | Your layout already reserves space for the banner (e.g. with a fixed footer). |
| `mode: 'resize'`                 | The web view is resized so that the banner ad never overlaps your web content.                            | You want the banner to never cover any content without changing your CSS.     |
| `frame: { x, y, width, height }` | The banner ad is placed at a frame you measure in CSS pixels, e.g. with `getBoundingClientRect()`.        | Inline placement within your content, e.g. between list items.                |

Load and show a banner ad with `showBanner(...)`. Use the returned identifier to hide, resume, or remove the banner ad later:

```typescript
import { Admob, BannerSize } from '@capawesome-team/capacitor-admob';

const showBanner = async () => {
  const { id } = await Admob.showBanner({
    adUnitId: 'ca-app-pub-3940256099942544/6300978111', // Test ad unit ID
    size: BannerSize.AdaptiveBanner,
    position: 'bottom',
  });
  return id;
};
```

For inline placement, measure the anchor element and update the frame on layout changes:

```typescript
import { Admob, BannerSize } from '@capawesome-team/capacitor-admob';

const showInlineBanner = async () => {
  const rect = document.querySelector('#banner-anchor').getBoundingClientRect();
  const { id } = await Admob.showBanner({
    adUnitId: 'ca-app-pub-3940256099942544/6300978111', // Test ad unit ID
    size: BannerSize.InlineAdaptiveBanner,
    frame: { x: rect.x, y: rect.y, width: rect.width, height: rect.height },
  });
  // Update the frame when the layout changes (e.g. on scroll or resize)
  window.addEventListener('resize', async () => {
    const newRect = document.querySelector('#banner-anchor').getBoundingClientRect();
    await Admob.setBannerFrame({
      id,
      frame: { x: newRect.x, y: newRect.y, width: newRect.width, height: newRect.height },
    });
  });
};
```

### Show an interstitial ad

Load an interstitial ad in advance and show it at a natural transition point, for example between two levels of a game:

```typescript
import { Admob } from '@capawesome-team/capacitor-admob';

const showInterstitialAd = async () => {
  const { id } = await Admob.loadInterstitialAd({
    adUnitId: 'ca-app-pub-3940256099942544/1033173712', // Test ad unit ID
  });
  await Admob.showInterstitialAd({ id });
};
```

### Show a rewarded ad

Listen for the `rewardEarned` event to grant the reward, then load and show the rewarded ad. Rewarded interstitial ads work the same way with `loadRewardedInterstitialAd(...)` and `showRewardedInterstitialAd(...)`:

```typescript
import { Admob } from '@capawesome-team/capacitor-admob';

const showRewardedAd = async () => {
  await Admob.addListener('rewardEarned', event => {
    console.log(`User earned ${event.amount} ${event.type}`);
  });
  const { id } = await Admob.loadRewardedAd({
    adUnitId: 'ca-app-pub-3940256099942544/5224354917', // Test ad unit ID
  });
  await Admob.showRewardedAd({ id });
};
```

### Show an app open ad

App open ads are shown when users bring your app back to the foreground. You can load and show them manually with `loadAppOpenAd(...)` and `showAppOpenAd(...)`, or let the plugin handle the entire lifecycle:

```typescript
import { Admob } from '@capawesome-team/capacitor-admob';

const enableAppOpenAds = async () => {
  await Admob.enableAppOpenAutoShow({
    adUnitId: 'ca-app-pub-3940256099942544/9257395921', // Test ad unit ID
    minInterval: 14400, // Show at most one ad every 4 hours
  });
};
```

The plugin automatically loads and shows an app open ad every time the app returns to the foreground, honoring the frequency cap. The ad is never shown while a consent form or another full-screen ad is visible.

### Track ad revenue

Listen for the `adRevenuePaid` event to receive the revenue of every ad format, for example for your lifetime value (LTV) and analytics pipelines:

```typescript
import { Admob } from '@capawesome-team/capacitor-admob';

const trackAdRevenue = async () => {
  await Admob.addListener('adRevenuePaid', event => {
    console.log(`Ad revenue: ${event.value} ${event.currencyCode} (${event.precision})`);
  });
};
```

### Test your integration

Always use Google's official public test ad units during development. Using your own ad units during development can lead to your AdMob account being suspended.

| Format                | Android                                  | iOS                                      |
| --------------------- | ---------------------------------------- | ---------------------------------------- |
| Banner                | `ca-app-pub-3940256099942544/6300978111` | `ca-app-pub-3940256099942544/2934735716` |
| Interstitial          | `ca-app-pub-3940256099942544/1033173712` | `ca-app-pub-3940256099942544/4411468910` |
| Rewarded              | `ca-app-pub-3940256099942544/5224354917` | `ca-app-pub-3940256099942544/1712485313` |
| Rewarded Interstitial | `ca-app-pub-3940256099942544/5354046379` | `ca-app-pub-3940256099942544/6978759866` |
| App Open              | `ca-app-pub-3940256099942544/9257395921` | `ca-app-pub-3940256099942544/5575463023` |

You can also register your own devices as test devices with the `testDeviceIds` option of `initialize(...)`.

To test the consent flow, use the `debugGeography` and `testDeviceIds` options of `requestConsent(...)` and reset the consent state with `resetConsent()`:

```typescript
import { Admob, DebugGeography } from '@capawesome-team/capacitor-admob';

const testConsent = async () => {
  await Admob.resetConsent();
  await Admob.requestConsent({
    debugGeography: DebugGeography.Eea,
    testDeviceIds: ['YOUR_TEST_DEVICE_ID'],
  });
};
```

### Handle errors

Every rejection that the plugin can recover from carries a typed error code in `error.code`. Use the `ErrorCode` enum to branch on it:

```typescript
import { Admob, ErrorCode } from '@capawesome-team/capacitor-admob';

const loadInterstitialAd = async () => {
  try {
    return await Admob.loadInterstitialAd({
      adUnitId: 'ca-app-pub-3940256099942544/1033173712', // Test ad unit ID
    });
  } catch (error) {
    if (error.code === ErrorCode.ConsentNotGathered) {
      console.log('Gather the consent with requestConsent() first.');
    } else if (error.code === ErrorCode.NotInitialized) {
      console.log('Initialize the SDK with initialize() first.');
    } else if (error.code === ErrorCode.LoadFailed) {
      console.log('No ad was available:', error.message);
    }
    return undefined;
  }
};
```

The plugin uses the following error codes:

| Code                       | Description                                                             |
| -------------------------- | ----------------------------------------------------------------------- |
| `AD_ALREADY_SHOWING`       | The ad is already showing.                                              |
| `AD_NOT_LOADED`            | No loaded ad was found for the given identifier.                        |
| `APPLICATION_ID_MISSING`   | The AdMob application ID is missing in the native project configuration. |
| `CONSENT_FORM_UNAVAILABLE` | The consent form is not available.                                      |
| `CONSENT_NOT_GATHERED`     | Ads cannot be requested because the consent has not been gathered yet.  |
| `CONSENT_REQUEST_FAILED`   | The consent information could not be requested.                         |
| `LOAD_FAILED`              | The ad could not be loaded.                                             |
| `NOT_INITIALIZED`          | The Google Mobile Ads SDK has not been initialized.                     |

**Note**: The `errorCode` property of the `adFailedToLoad` and `adFailedToShow` events is not an `ErrorCode`. It is the numeric error code reported by the Google Mobile Ads SDK (see [Android](https://developers.google.com/admob/android/reference/com/google/android/gms/ads/AdRequest#constant-summary) and [iOS](https://developers.google.com/admob/ios/reference/enum/GADErrorCode)).

## API

<docgen-index>

* [`disableAppOpenAutoShow()`](#disableappopenautoshow)
* [`enableAppOpenAutoShow(...)`](#enableappopenautoshow)
* [`hideBanner(...)`](#hidebanner)
* [`initialize(...)`](#initialize)
* [`loadAppOpenAd(...)`](#loadappopenad)
* [`loadInterstitialAd(...)`](#loadinterstitialad)
* [`loadRewardedAd(...)`](#loadrewardedad)
* [`loadRewardedInterstitialAd(...)`](#loadrewardedinterstitialad)
* [`removeBanner(...)`](#removebanner)
* [`requestConsent(...)`](#requestconsent)
* [`resetConsent()`](#resetconsent)
* [`resumeBanner(...)`](#resumebanner)
* [`setApplicationMuted(...)`](#setapplicationmuted)
* [`setApplicationVolume(...)`](#setapplicationvolume)
* [`setBannerFrame(...)`](#setbannerframe)
* [`showAppOpenAd(...)`](#showappopenad)
* [`showBanner(...)`](#showbanner)
* [`showInterstitialAd(...)`](#showinterstitialad)
* [`showPrivacyOptionsForm()`](#showprivacyoptionsform)
* [`showRewardedAd(...)`](#showrewardedad)
* [`showRewardedInterstitialAd(...)`](#showrewardedinterstitialad)
* [`addListener('adClicked', ...)`](#addlisteneradclicked-)
* [`addListener('adDismissed', ...)`](#addlisteneraddismissed-)
* [`addListener('adFailedToLoad', ...)`](#addlisteneradfailedtoload-)
* [`addListener('adFailedToShow', ...)`](#addlisteneradfailedtoshow-)
* [`addListener('adImpressionRecorded', ...)`](#addlisteneradimpressionrecorded-)
* [`addListener('adLoaded', ...)`](#addlisteneradloaded-)
* [`addListener('adRevenuePaid', ...)`](#addlisteneradrevenuepaid-)
* [`addListener('adShowed', ...)`](#addlisteneradshowed-)
* [`addListener('bannerSizeChanged', ...)`](#addlistenerbannersizechanged-)
* [`addListener('rewardEarned', ...)`](#addlistenerrewardearned-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### disableAppOpenAutoShow()

```typescript
disableAppOpenAutoShow() => Promise<void>
```

Disable the automatic loading and showing of app open ads.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### enableAppOpenAutoShow(...)

```typescript
enableAppOpenAutoShow(options: EnableAppOpenAutoShowOptions) => Promise<void>
```

Enable the automatic loading and showing of app open ads when the app
is brought back to the foreground.

The ad is never shown while a consent form is visible.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#enableappopenautoshowoptions">EnableAppOpenAutoShowOptions</a></code> |

**Since:** 0.0.1

--------------------


### hideBanner(...)

```typescript
hideBanner(options: HideBannerOptions) => Promise<void>
```

Hide a banner ad without destroying it.

Use `resumeBanner(...)` to show it again.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#hidebanneroptions">HideBannerOptions</a></code> |

**Since:** 0.0.1

--------------------


### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Initialize the Google Mobile Ads SDK.

Must be called once before loading any ads, after the consent
has been gathered with `requestConsent(...)`.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.0.1

--------------------


### loadAppOpenAd(...)

```typescript
loadAppOpenAd(options: LoadAppOpenAdOptions) => Promise<LoadAppOpenAdResult>
```

Load an app open ad.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#loadappopenadoptions">LoadAppOpenAdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#loadappopenadresult">LoadAppOpenAdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### loadInterstitialAd(...)

```typescript
loadInterstitialAd(options: LoadInterstitialAdOptions) => Promise<LoadInterstitialAdResult>
```

Load an interstitial ad.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#loadinterstitialadoptions">LoadInterstitialAdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#loadinterstitialadresult">LoadInterstitialAdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### loadRewardedAd(...)

```typescript
loadRewardedAd(options: LoadRewardedAdOptions) => Promise<LoadRewardedAdResult>
```

Load a rewarded ad.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#loadrewardedadoptions">LoadRewardedAdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#loadrewardedadresult">LoadRewardedAdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### loadRewardedInterstitialAd(...)

```typescript
loadRewardedInterstitialAd(options: LoadRewardedInterstitialAdOptions) => Promise<LoadRewardedInterstitialAdResult>
```

Load a rewarded interstitial ad.

Only available on Android and iOS.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#loadrewardedinterstitialadoptions">LoadRewardedInterstitialAdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#loadrewardedinterstitialadresult">LoadRewardedInterstitialAdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeBanner(...)

```typescript
removeBanner(options: RemoveBannerOptions) => Promise<void>
```

Remove and destroy a banner ad.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#removebanneroptions">RemoveBannerOptions</a></code> |

**Since:** 0.0.1

--------------------


### requestConsent(...)

```typescript
requestConsent(options?: RequestConsentOptions | undefined) => Promise<RequestConsentResult>
```

Request the latest consent information from the User Messaging Platform (UMP)
and show the consent form if consent is required.

Call this method on every app launch before initializing
the Google Mobile Ads SDK.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestconsentoptions">RequestConsentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#requestconsentresult">RequestConsentResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### resetConsent()

```typescript
resetConsent() => Promise<void>
```

Reset the consent state of the User Messaging Platform (UMP).

This method should only be used for testing purposes.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### resumeBanner(...)

```typescript
resumeBanner(options: ResumeBannerOptions) => Promise<void>
```

Show a hidden banner ad again.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#resumebanneroptions">ResumeBannerOptions</a></code> |

**Since:** 0.0.1

--------------------


### setApplicationMuted(...)

```typescript
setApplicationMuted(options: SetApplicationMutedOptions) => Promise<void>
```

Set whether the app is muted for ad playback.

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setapplicationmutedoptions">SetApplicationMutedOptions</a></code> |

**Since:** 0.0.1

--------------------


### setApplicationVolume(...)

```typescript
setApplicationVolume(options: SetApplicationVolumeOptions) => Promise<void>
```

Set the app volume for ad playback.

Only available on Android and iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setapplicationvolumeoptions">SetApplicationVolumeOptions</a></code> |

**Since:** 0.0.1

--------------------


### setBannerFrame(...)

```typescript
setBannerFrame(options: SetBannerFrameOptions) => Promise<void>
```

Update the frame of a banner ad that was shown with a frame,
for example after a layout change.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#setbannerframeoptions">SetBannerFrameOptions</a></code> |

**Since:** 0.0.1

--------------------


### showAppOpenAd(...)

```typescript
showAppOpenAd(options: ShowAppOpenAdOptions) => Promise<void>
```

Show a loaded app open ad.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#showappopenadoptions">ShowAppOpenAdOptions</a></code> |

**Since:** 0.0.1

--------------------


### showBanner(...)

```typescript
showBanner(options: ShowBannerOptions) => Promise<ShowBannerResult>
```

Load and show a banner ad.

Multiple banner ads can be shown at the same time
by using different identifiers.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#showbanneroptions">ShowBannerOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#showbannerresult">ShowBannerResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### showInterstitialAd(...)

```typescript
showInterstitialAd(options: ShowInterstitialAdOptions) => Promise<void>
```

Show a loaded interstitial ad.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#showinterstitialadoptions">ShowInterstitialAdOptions</a></code> |

**Since:** 0.0.1

--------------------


### showPrivacyOptionsForm()

```typescript
showPrivacyOptionsForm() => Promise<void>
```

Show the privacy options form of the User Messaging Platform (UMP).

Call this method when the user wants to change the consent settings,
for example from a privacy settings page. Only required if
`privacyOptionsRequired` is `true` in the result of `requestConsent(...)`.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### showRewardedAd(...)

```typescript
showRewardedAd(options: ShowRewardedAdOptions) => Promise<void>
```

Show a loaded rewarded ad.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#showrewardedadoptions">ShowRewardedAdOptions</a></code> |

**Since:** 0.0.1

--------------------


### showRewardedInterstitialAd(...)

```typescript
showRewardedInterstitialAd(options: ShowRewardedInterstitialAdOptions) => Promise<void>
```

Show a loaded rewarded interstitial ad.

Only available on Android and iOS.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#showrewardedinterstitialadoptions">ShowRewardedInterstitialAdOptions</a></code> |

**Since:** 0.0.1

--------------------


### addListener('adClicked', ...)

```typescript
addListener(eventName: 'adClicked', listenerFunc: (event: AdClickedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has been clicked.

Only available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adClicked'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#adclickedevent">AdClickedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adDismissed', ...)

```typescript
addListener(eventName: 'adDismissed', listenerFunc: (event: AdDismissedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a full-screen ad has been dismissed.

Only available on Android and iOS.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adDismissed'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#addismissedevent">AdDismissedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adFailedToLoad', ...)

```typescript
addListener(eventName: 'adFailedToLoad', listenerFunc: (event: AdFailedToLoadEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has failed to load.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adFailedToLoad'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#adfailedtoloadevent">AdFailedToLoadEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adFailedToShow', ...)

```typescript
addListener(eventName: 'adFailedToShow', listenerFunc: (event: AdFailedToShowEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has failed to show.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adFailedToShow'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#adfailedtoshowevent">AdFailedToShowEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adImpressionRecorded', ...)

```typescript
addListener(eventName: 'adImpressionRecorded', listenerFunc: (event: AdImpressionRecordedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has recorded an impression.

Only available on Android and iOS.

| Param              | Type                                                                                                |
| ------------------ | --------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adImpressionRecorded'</code>                                                                 |
| **`listenerFunc`** | <code>(event: <a href="#adimpressionrecordedevent">AdImpressionRecordedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adLoaded', ...)

```typescript
addListener(eventName: 'adLoaded', listenerFunc: (event: AdLoadedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has been loaded.

Only available on Android and iOS.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adLoaded'</code>                                                     |
| **`listenerFunc`** | <code>(event: <a href="#adloadedevent">AdLoadedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adRevenuePaid', ...)

```typescript
addListener(eventName: 'adRevenuePaid', listenerFunc: (event: AdRevenuePaidEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has earned revenue.

Use this event to track the ad revenue of your app,
for example for lifetime value (LTV) pipelines.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adRevenuePaid'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#adrevenuepaidevent">AdRevenuePaidEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('adShowed', ...)

```typescript
addListener(eventName: 'adShowed', listenerFunc: (event: AdShowedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an ad has been shown.

Only available on Android and iOS.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'adShowed'</code>                                                     |
| **`listenerFunc`** | <code>(event: <a href="#adshowedevent">AdShowedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('bannerSizeChanged', ...)

```typescript
addListener(eventName: 'bannerSizeChanged', listenerFunc: (event: BannerSizeChangedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the size of a banner ad has changed, for example after
a collapsible banner has been expanded or collapsed.

Only available on Android and iOS.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'bannerSizeChanged'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#bannersizechangedevent">BannerSizeChangedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('rewardEarned', ...)

```typescript
addListener(eventName: 'rewardEarned', listenerFunc: (event: RewardEarnedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the user has earned a reward from a rewarded ad
or a rewarded interstitial ad.

Only available on Android and iOS.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'rewardEarned'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#rewardearnedevent">RewardEarnedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### EnableAppOpenAutoShowOptions

| Prop              | Type                | Description                                                                   | Default            | Since |
| ----------------- | ------------------- | ----------------------------------------------------------------------------- | ------------------ | ----- |
| **`adUnitId`**    | <code>string</code> | The ad unit ID of the app open ad.                                            |                    | 0.0.1 |
| **`minInterval`** | <code>number</code> | The minimum interval in seconds between two automatically shown app open ads. | <code>14400</code> | 0.0.1 |


#### HideBannerOptions

| Prop     | Type                | Description                              | Since |
| -------- | ------------------- | ---------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the banner ad to hide. | 0.0.1 |


#### InitializeOptions

| Prop                               | Type                                                              | Description                                                                                                               | Since |
| ---------------------------------- | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`maxAdContentRating`**           | <code><a href="#maxadcontentrating">MaxAdContentRating</a></code> | The maximum ad content rating for all ad requests.                                                                        | 0.0.1 |
| **`tagForChildDirectedTreatment`** | <code>boolean</code>                                              | Whether the app should be treated as child-directed for purposes of the Children's Online Privacy Protection Act (COPPA). | 0.0.1 |
| **`tagForUnderAgeOfConsent`**      | <code>boolean</code>                                              | Whether the ad requests should be handled in a manner suitable for users under the age of consent.                        | 0.0.1 |
| **`testDeviceIds`**                | <code>string[]</code>                                             | The identifiers of the devices that should receive test ads.                                                              | 0.0.1 |


#### LoadAppOpenAdResult

| Prop     | Type                | Description               | Since |
| -------- | ------------------- | ------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad. | 0.0.1 |


#### LoadAppOpenAdOptions

| Prop                 | Type                                                          | Description                                                                  | Since |
| -------------------- | ------------------------------------------------------------- | ---------------------------------------------------------------------------- | ----- |
| **`adUnitId`**       | <code>string</code>                                           | The ad unit ID of the app open ad.                                           | 0.0.1 |
| **`id`**             | <code>string</code>                                           | The identifier of the ad. If not provided, a random identifier is generated. | 0.0.1 |
| **`requestOptions`** | <code><a href="#adrequestoptions">AdRequestOptions</a></code> | The additional options for the ad request.                                   | 0.0.1 |


#### AdRequestOptions

| Prop             | Type                  | Description                                                                                                                                           | Since |
| ---------------- | --------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`contentUrl`** | <code>string</code>   | The URL string for a web page whose content matches the app's primary content. This web page content is used for targeting and brand safety purposes. | 0.0.1 |
| **`keywords`**   | <code>string[]</code> | The keywords describing the app's content for targeting purposes.                                                                                     | 0.0.1 |


#### LoadInterstitialAdResult

| Prop     | Type                | Description               | Since |
| -------- | ------------------- | ------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad. | 0.0.1 |


#### LoadInterstitialAdOptions

| Prop                 | Type                                                          | Description                                                                  | Since |
| -------------------- | ------------------------------------------------------------- | ---------------------------------------------------------------------------- | ----- |
| **`adUnitId`**       | <code>string</code>                                           | The ad unit ID of the interstitial ad.                                       | 0.0.1 |
| **`id`**             | <code>string</code>                                           | The identifier of the ad. If not provided, a random identifier is generated. | 0.0.1 |
| **`requestOptions`** | <code><a href="#adrequestoptions">AdRequestOptions</a></code> | The additional options for the ad request.                                   | 0.0.1 |


#### LoadRewardedAdResult

| Prop     | Type                | Description               | Since |
| -------- | ------------------- | ------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad. | 0.0.1 |


#### LoadRewardedAdOptions

| Prop                         | Type                                                                                    | Description                                                                  | Since |
| ---------------------------- | --------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------- | ----- |
| **`adUnitId`**               | <code>string</code>                                                                     | The ad unit ID of the rewarded ad.                                           | 0.0.1 |
| **`id`**                     | <code>string</code>                                                                     | The identifier of the ad. If not provided, a random identifier is generated. | 0.0.1 |
| **`requestOptions`**         | <code><a href="#adrequestoptions">AdRequestOptions</a></code>                           | The additional options for the ad request.                                   | 0.0.1 |
| **`serverSideVerification`** | <code><a href="#serversideverificationoptions">ServerSideVerificationOptions</a></code> | The options for the server-side verification (SSV) of reward grants.         | 0.0.1 |


#### ServerSideVerificationOptions

| Prop             | Type                | Description                                                              | Since |
| ---------------- | ------------------- | ------------------------------------------------------------------------ | ----- |
| **`customData`** | <code>string</code> | The custom data to include in the server-side verification callback.     | 0.0.1 |
| **`userId`**     | <code>string</code> | The user identifier to include in the server-side verification callback. | 0.0.1 |


#### LoadRewardedInterstitialAdResult

| Prop     | Type                | Description               | Since |
| -------- | ------------------- | ------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad. | 0.0.1 |


#### LoadRewardedInterstitialAdOptions

| Prop                         | Type                                                                                    | Description                                                                  | Since |
| ---------------------------- | --------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------- | ----- |
| **`adUnitId`**               | <code>string</code>                                                                     | The ad unit ID of the rewarded interstitial ad.                              | 0.0.1 |
| **`id`**                     | <code>string</code>                                                                     | The identifier of the ad. If not provided, a random identifier is generated. | 0.0.1 |
| **`requestOptions`**         | <code><a href="#adrequestoptions">AdRequestOptions</a></code>                           | The additional options for the ad request.                                   | 0.0.1 |
| **`serverSideVerification`** | <code><a href="#serversideverificationoptions">ServerSideVerificationOptions</a></code> | The options for the server-side verification (SSV) of reward grants.         | 0.0.1 |


#### RemoveBannerOptions

| Prop     | Type                | Description                                | Since |
| -------- | ------------------- | ------------------------------------------ | ----- |
| **`id`** | <code>string</code> | The identifier of the banner ad to remove. | 0.0.1 |


#### RequestConsentResult

| Prop                         | Type                                                    | Description                                                                                             | Since |
| ---------------------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ----- |
| **`canRequestAds`**          | <code>boolean</code>                                    | Whether ads can be requested.                                                                           | 0.0.1 |
| **`privacyOptionsRequired`** | <code>boolean</code>                                    | Whether a privacy options form should be offered to the user, for example from a privacy settings page. | 0.0.1 |
| **`status`**                 | <code><a href="#consentstatus">ConsentStatus</a></code> | The consent status.                                                                                     | 0.0.1 |


#### RequestConsentOptions

| Prop                 | Type                                                      | Description                                                         | Since |
| -------------------- | --------------------------------------------------------- | ------------------------------------------------------------------- | ----- |
| **`debugGeography`** | <code><a href="#debuggeography">DebugGeography</a></code> | The debug geography for testing purposes.                           | 0.0.1 |
| **`testDeviceIds`**  | <code>string[]</code>                                     | The identifiers of the devices that should use the debug geography. | 0.0.1 |


#### ResumeBannerOptions

| Prop     | Type                | Description                                | Since |
| -------- | ------------------- | ------------------------------------------ | ----- |
| **`id`** | <code>string</code> | The identifier of the banner ad to resume. | 0.0.1 |


#### SetApplicationMutedOptions

| Prop        | Type                 | Description                               | Since |
| ----------- | -------------------- | ----------------------------------------- | ----- |
| **`muted`** | <code>boolean</code> | Whether the app is muted for ad playback. | 0.0.1 |


#### SetApplicationVolumeOptions

| Prop         | Type                | Description                                                                                     | Since |
| ------------ | ------------------- | ----------------------------------------------------------------------------------------------- | ----- |
| **`volume`** | <code>number</code> | The app volume for ad playback as a value between `0` (silent) and `1` (current device volume). | 0.0.1 |


#### SetBannerFrameOptions

| Prop        | Type                                                | Description                      | Since |
| ----------- | --------------------------------------------------- | -------------------------------- | ----- |
| **`frame`** | <code><a href="#bannerframe">BannerFrame</a></code> | The new frame of the banner ad.  | 0.0.1 |
| **`id`**    | <code>string</code>                                 | The identifier of the banner ad. | 0.0.1 |


#### BannerFrame

The frame of a banner ad in CSS pixels, relative to the top-left corner
of the web view.

| Prop         | Type                | Description                                                                                                                                                                                                | Since |
| ------------ | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the frame in CSS pixels. Used as the maximum height for the inline adaptive banner size. Ignored for the adaptive banner size because its height is determined by the Google Mobile Ads SDK. | 0.0.1 |
| **`width`**  | <code>number</code> | The width of the frame in CSS pixels.                                                                                                                                                                      | 0.0.1 |
| **`x`**      | <code>number</code> | The x-coordinate of the frame in CSS pixels.                                                                                                                                                               | 0.0.1 |
| **`y`**      | <code>number</code> | The y-coordinate of the frame in CSS pixels.                                                                                                                                                               | 0.0.1 |


#### ShowAppOpenAdOptions

| Prop     | Type                | Description                       | Since |
| -------- | ------------------- | --------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad to show. | 0.0.1 |


#### ShowBannerResult

| Prop     | Type                | Description                      | Since |
| -------- | ------------------- | -------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the banner ad. | 0.0.1 |


#### ShowBannerOptions

| Prop                 | Type                                                          | Description                                                                                                                                                                                                                                 | Default                                | Since |
| -------------------- | ------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------- | ----- |
| **`adUnitId`**       | <code>string</code>                                           | The ad unit ID of the banner ad.                                                                                                                                                                                                            |                                        | 0.0.1 |
| **`collapsible`**    | <code>boolean</code>                                          | Whether the banner ad is collapsible.                                                                                                                                                                                                       | <code>false</code>                     | 0.0.1 |
| **`frame`**          | <code><a href="#bannerframe">BannerFrame</a></code>           | The frame of the banner ad in CSS pixels for inline placement, for example measured with `getBoundingClientRect()`. If provided, the banner ad is placed at the given frame instead of the given position.                                  |                                        | 0.0.1 |
| **`id`**             | <code>string</code>                                           | The identifier of the banner ad. If not provided, a random identifier is generated.                                                                                                                                                         |                                        | 0.0.1 |
| **`mode`**           | <code><a href="#bannermode">BannerMode</a></code>             | The layout mode of the banner ad. In `'overlay'` mode, the banner ad is displayed on top of the web view. In `'resize'` mode, the web view is resized so that the banner ad never overlaps the web content. Ignored if a frame is provided. | <code>'overlay'</code>                 | 0.0.1 |
| **`position`**       | <code><a href="#bannerposition">BannerPosition</a></code>     | The position of the banner ad. Ignored if a frame is provided.                                                                                                                                                                              | <code>'bottom'</code>                  | 0.0.1 |
| **`requestOptions`** | <code><a href="#adrequestoptions">AdRequestOptions</a></code> | The additional options for the ad request.                                                                                                                                                                                                  |                                        | 0.0.1 |
| **`size`**           | <code><a href="#bannersize">BannerSize</a></code>             | The size of the banner ad.                                                                                                                                                                                                                  | <code>BannerSize.AdaptiveBanner</code> | 0.0.1 |


#### ShowInterstitialAdOptions

| Prop     | Type                | Description                       | Since |
| -------- | ------------------- | --------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad to show. | 0.0.1 |


#### ShowRewardedAdOptions

| Prop     | Type                | Description                       | Since |
| -------- | ------------------- | --------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad to show. | 0.0.1 |


#### ShowRewardedInterstitialAdOptions

| Prop     | Type                | Description                       | Since |
| -------- | ------------------- | --------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the ad to show. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### AdClickedEvent

| Prop         | Type                                          | Description               | Since |
| ------------ | --------------------------------------------- | ------------------------- | ----- |
| **`format`** | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.     | 0.0.1 |
| **`id`**     | <code>string</code>                           | The identifier of the ad. | 0.0.1 |


#### AdDismissedEvent

| Prop         | Type                                          | Description               | Since |
| ------------ | --------------------------------------------- | ------------------------- | ----- |
| **`format`** | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.     | 0.0.1 |
| **`id`**     | <code>string</code>                           | The identifier of the ad. | 0.0.1 |


#### AdFailedToLoadEvent

| Prop               | Type                                          | Description                                              | Since |
| ------------------ | --------------------------------------------- | -------------------------------------------------------- | ----- |
| **`errorCode`**    | <code>string</code>                           | The error code reported by the Google Mobile Ads SDK.    | 0.0.1 |
| **`errorMessage`** | <code>string</code>                           | The error message reported by the Google Mobile Ads SDK. | 0.0.1 |
| **`format`**       | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.                                    | 0.0.1 |
| **`id`**           | <code>string</code>                           | The identifier of the ad.                                | 0.0.1 |


#### AdFailedToShowEvent

| Prop               | Type                                          | Description                                              | Since |
| ------------------ | --------------------------------------------- | -------------------------------------------------------- | ----- |
| **`errorCode`**    | <code>string</code>                           | The error code reported by the Google Mobile Ads SDK.    | 0.0.1 |
| **`errorMessage`** | <code>string</code>                           | The error message reported by the Google Mobile Ads SDK. | 0.0.1 |
| **`format`**       | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.                                    | 0.0.1 |
| **`id`**           | <code>string</code>                           | The identifier of the ad.                                | 0.0.1 |


#### AdImpressionRecordedEvent

| Prop         | Type                                          | Description               | Since |
| ------------ | --------------------------------------------- | ------------------------- | ----- |
| **`format`** | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.     | 0.0.1 |
| **`id`**     | <code>string</code>                           | The identifier of the ad. | 0.0.1 |


#### AdLoadedEvent

| Prop         | Type                                          | Description               | Since |
| ------------ | --------------------------------------------- | ------------------------- | ----- |
| **`format`** | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.     | 0.0.1 |
| **`id`**     | <code>string</code>                           | The identifier of the ad. | 0.0.1 |


#### AdRevenuePaidEvent

| Prop               | Type                                                          | Description                                                          | Since |
| ------------------ | ------------------------------------------------------------- | -------------------------------------------------------------------- | ----- |
| **`currencyCode`** | <code>string</code>                                           | The ISO 4217 currency code of the value.                             | 0.0.1 |
| **`format`**       | <code><a href="#adformat">AdFormat</a></code>                 | The format of the ad.                                                | 0.0.1 |
| **`id`**           | <code>string</code>                                           | The identifier of the ad.                                            | 0.0.1 |
| **`precision`**    | <code><a href="#revenueprecision">RevenuePrecision</a></code> | The precision of the reported value.                                 | 0.0.1 |
| **`value`**        | <code>number</code>                                           | The monetary value earned by the ad in the currency's standard unit. | 0.0.1 |


#### AdShowedEvent

| Prop         | Type                                          | Description               | Since |
| ------------ | --------------------------------------------- | ------------------------- | ----- |
| **`format`** | <code><a href="#adformat">AdFormat</a></code> | The format of the ad.     | 0.0.1 |
| **`id`**     | <code>string</code>                           | The identifier of the ad. | 0.0.1 |


#### BannerSizeChangedEvent

| Prop         | Type                | Description                                | Since |
| ------------ | ------------------- | ------------------------------------------ | ----- |
| **`height`** | <code>number</code> | The height of the banner ad in CSS pixels. | 0.0.1 |
| **`id`**     | <code>string</code> | The identifier of the banner ad.           | 0.0.1 |
| **`width`**  | <code>number</code> | The width of the banner ad in CSS pixels.  | 0.0.1 |


#### RewardEarnedEvent

| Prop         | Type                | Description               | Since |
| ------------ | ------------------- | ------------------------- | ----- |
| **`amount`** | <code>number</code> | The amount of the reward. | 0.0.1 |
| **`id`**     | <code>string</code> | The identifier of the ad. | 0.0.1 |
| **`type`**   | <code>string</code> | The type of the reward.   | 0.0.1 |


### Type Aliases


#### ConsentStatus

The consent status of the User Messaging Platform (UMP).

<code>'not-required' | 'obtained' | 'required' | 'unknown'</code>


#### BannerMode

The layout mode of a banner ad.

<code>'overlay' | 'resize'</code>


#### BannerPosition

The position of a banner ad.

<code>'bottom' | 'top'</code>


### Enums


#### MaxAdContentRating

| Members  | Value             | Description                                                 | Since |
| -------- | ----------------- | ----------------------------------------------------------- | ----- |
| **`G`**  | <code>'G'</code>  | Content suitable for general audiences.                     | 0.0.1 |
| **`Ma`** | <code>'MA'</code> | Content suitable only for mature audiences.                 | 0.0.1 |
| **`Pg`** | <code>'PG'</code> | Content suitable for most audiences with parental guidance. | 0.0.1 |
| **`T`**  | <code>'T'</code>  | Content suitable for teen and older audiences.              | 0.0.1 |


#### DebugGeography

| Members                | Value                             | Description                                                            | Since |
| ---------------------- | --------------------------------- | ---------------------------------------------------------------------- | ----- |
| **`Disabled`**         | <code>'DISABLED'</code>           | The debug geography is disabled.                                       | 0.0.1 |
| **`Eea`**              | <code>'EEA'</code>                | The device appears as located in the European Economic Area (EEA).     | 0.0.1 |
| **`Other`**            | <code>'OTHER'</code>              | The device appears as located in a region with no regulation in force. | 0.0.1 |
| **`RegulatedUsState`** | <code>'REGULATED_US_STATE'</code> | The device appears as located in a regulated US state.                 | 0.0.1 |


#### BannerSize

| Members                    | Value                                 | Description                                                                                                                                                             | Since |
| -------------------------- | ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`AdaptiveBanner`**       | <code>'ADAPTIVE_BANNER'</code>        | An adaptive banner with a width that matches the screen width (or the frame width if a frame is provided) and a height that is determined by the Google Mobile Ads SDK. | 0.0.1 |
| **`Banner`**               | <code>'BANNER'</code>                 | A standard banner with a size of 320x50.                                                                                                                                | 0.0.1 |
| **`FullBanner`**           | <code>'FULL_BANNER'</code>            | A full-size banner with a size of 468x60 (tablets only).                                                                                                                | 0.0.1 |
| **`InlineAdaptiveBanner`** | <code>'INLINE_ADAPTIVE_BANNER'</code> | An adaptive banner for inline placement in scrolling content with a height that is limited by the height of the provided frame.                                         | 0.0.1 |
| **`LargeBanner`**          | <code>'LARGE_BANNER'</code>           | A large banner with a size of 320x100.                                                                                                                                  | 0.0.1 |
| **`Leaderboard`**          | <code>'LEADERBOARD'</code>            | A leaderboard banner with a size of 728x90 (tablets only).                                                                                                              | 0.0.1 |
| **`MediumRectangle`**      | <code>'MEDIUM_RECTANGLE'</code>       | A medium rectangle banner with a size of 300x250.                                                                                                                       | 0.0.1 |


#### AdFormat

| Members                    | Value                                | Description                 | Since |
| -------------------------- | ------------------------------------ | --------------------------- | ----- |
| **`AppOpen`**              | <code>'APP_OPEN'</code>              | An app open ad.             | 0.0.1 |
| **`Banner`**               | <code>'BANNER'</code>                | A banner ad.                | 0.0.1 |
| **`Interstitial`**         | <code>'INTERSTITIAL'</code>          | An interstitial ad.         | 0.0.1 |
| **`Rewarded`**             | <code>'REWARDED'</code>              | A rewarded ad.              | 0.0.1 |
| **`RewardedInterstitial`** | <code>'REWARDED_INTERSTITIAL'</code> | A rewarded interstitial ad. | 0.0.1 |


#### RevenuePrecision

| Members                 | Value                             | Description                                     | Since |
| ----------------------- | --------------------------------- | ----------------------------------------------- | ----- |
| **`Estimated`**         | <code>'ESTIMATED'</code>          | The value is estimated.                         | 0.0.1 |
| **`Precise`**           | <code>'PRECISE'</code>            | The value is the precise value paid for the ad. | 0.0.1 |
| **`PublisherProvided`** | <code>'PUBLISHER_PROVIDED'</code> | The value is provided by the publisher.         | 0.0.1 |
| **`Unknown`**           | <code>'UNKNOWN'</code>            | The value is unknown.                           | 0.0.1 |

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It covers the full AdMob surface on Android and iOS — banner, interstitial, rewarded, rewarded interstitial and app open ads — built on the Next-Gen Google Mobile Ads SDK, with correct edge-to-edge banner layout, ad revenue events for your LTV pipelines, and the canonical User Messaging Platform (UMP) consent flow in a single method call. Every ad format shares a uniform load/show API, rejections carry typed error codes, and you can run multiple typed ad instances at once — all fully typed, actively maintained against the latest SDK and Capacitor versions, and backed by dedicated support. If you only need a single banner, a minimal integration may be enough; if you want the complete monetization and consent story in one dependency, this plugin is built for it.

### Are ads available on the web?

No. AdMob is a mobile advertising product, so all methods are only available on Android and iOS. On the web, all methods reject with an unimplemented error. For web advertising, take a look at [Google AdSense](https://adsense.google.com/).

### Why do my ad requests fail with `CONSENT_NOT_GATHERED`?

The plugin only requests ads if the consent requirements are met. Call `requestConsent(...)` on every app launch before loading any ads. See [Usage](#gather-consent-and-initialize-the-sdk) for the canonical flow.

### Why does my app crash on Android without this plugin's error message?

The Google Mobile Ads SDK crashes the app if the AdMob app ID is missing or invalid. The plugin detects a missing app ID and rejects `initialize(...)` with a clear error message instead. However, it cannot detect an *invalid* app ID, so double-check the value in your `AndroidManifest.xml` and `Info.plist` files.

### Do I have to comply with any ad policies?

Yes. Apps that show AdMob ads must comply with [Google's ad policies](https://support.google.com/admob/answer/6128543) and, in the EEA and the UK, with Google's [EU User Consent Policy](https://www.google.com/about/company/user-consent-policy/). This plugin ships the User Messaging Platform (UMP) consent flow to help you with that (see [Usage](#gather-consent-and-initialize-the-sdk)).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Review](https://capawesome.io/docs/sdks/capacitor/app-review/): Ask happy users for a review at the right moment.
- [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/): Request the tracking permission on iOS for personalized ads.
- [Purchases](https://capawesome.io/docs/sdks/capacitor/purchases/): Offer an ad-free experience with in-app purchases.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/admob/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/admob/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/admob/LICENSE).
