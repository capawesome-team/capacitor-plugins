# Capacitor Singular Plugin

Unofficial Capacitor plugin for the [Singular Mobile SDK](https://support.singular.net/hc/en-us/articles/360037640172-Integrating-a-Singular-SDK-Planning-and-Prerequisites).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Singular plugin is a modern integration of the Singular Mobile SDK for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📈 **Attribution**: Attribute app installs and re-engagements to your ad campaigns.
- 🎯 **Events & Revenue**: Track standard and custom events as well as in-app revenue.
- 💰 **Ad Revenue**: Report ad revenue from your mediation platform to measure ROAS.
- 🔗 **Singular Links**: Handle deep links and deferred deep links after an install.
- 🍎 **SKAdNetwork**: Let the SDK manage the conversion value or update it yourself.
- 🔒 **Privacy**: Comply with GDPR and CCPA using the built-in consent and opt-out controls.
- 🗑️ **Uninstall Tracking**: Measure uninstalls using push notification device tokens.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

Singular is a Mobile Measurement Partner (MMP). The plugin is typically used to measure and optimize paid user acquisition, for example:

- **Install attribution**: Attribute app installs to the network, campaign and creative that drove them.
- **Campaign optimization**: Report in-app events, purchase revenue and ad revenue to measure ROI and ROAS.
- **Deep linking**: Route users to the right screen with Singular Links, including deferred deep links after an install.
- **Referral programs**: Generate short links that attribute installs to a referring user.
- **Privacy compliance**: Collect consent and honor opt-outs under GDPR and CCPA.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-singular` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-singular
npx cap sync
```

This plugin requires a [Singular](https://www.singular.net/) account. You can find the **SDK Key** and the **SDK Secret** of your app in the Singular dashboard under **Developer Tools → SDK Integration → SDK Keys**. Do not use the Singular Reporting API key, otherwise no SDK data is received.

> [!IMPORTANT]
> Use of the Singular SDKs is governed by the [Singular Terms & Conditions of Service](https://www.singular.net/terms/), which limit the use of the service to your own advertising and promotions and prohibit sending Protected Health Information (as defined under HIPAA) to Singular (see [Third-Party Notices](#third-party-notices)). This plugin declares the SDKs as dependencies and downloads them from Singular's Maven repository, CocoaPods or Swift Package Manager at build time. It does not bundle or modify them.

### Android

The [Singular SDK for Android](https://support.singular.net/hc/en-us/articles/360037581952-Android-SDK-Basic-Integration) is not published on Maven Central. It is resolved from Singular's own Maven repository, which the plugin already declares in its `build.gradle` file. If your project declares its repositories centrally in the `settings.gradle` file (via `dependencyResolutionManagement` with `RepositoriesMode.PREFER_SETTINGS`), the repositories declared by plugins are ignored and you must add the repository yourself:

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://maven.singular.net/' }
    }
}
```

#### Permissions

This plugin already declares the `INTERNET`, `ACCESS_NETWORK_STATE` and `com.google.android.gms.permission.AD_ID` permissions in its `AndroidManifest.xml` file, so no manual configuration is required.

Apps that participate in the [Google Play Families program](https://support.google.com/googleplay/android-developer/answer/9893335) must not request the advertising ID. Add the following element to your `AndroidManifest.xml` file before or after the `application` tag to remove the permission:

```xml
<!-- Required for apps in the Google Play Families program. -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID" tools:node="remove" />
```

Make sure that the `tools` namespace is declared on the `manifest` element (`xmlns:tools="http://schemas.android.com/tools"`).

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class com.singular.sdk.** { *; }
-keep public class com.android.installreferrer.** { *; }
```

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$singularSdkVersion` version of `com.singular.sdk:singular_sdk` (default: `12.16.0`)

#### Singular Links

To open [Singular Links](https://support.singular.net/hc/en-us/articles/35356520601755-Android-SDK-Supporting-Deep-Links) in your app, add an App Links intent filter to the `MainActivity` in your `AndroidManifest.xml` file:

```xml
<intent-filter android:autoVerify="true">
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="https" android:host="YOUR_SUBDOMAIN.sng.link" android:pathPrefix="/A" />
    <data android:scheme="https" android:host="YOUR_SUBDOMAIN.sng.link" android:pathPrefix="/B" />
    <data android:scheme="https" android:host="YOUR_SUBDOMAIN.sng.link" android:pathPrefix="/E" />
    <data android:scheme="https" android:host="YOUR_SUBDOMAIN.sng.link" android:pathPrefix="/F" />
</intent-filter>
```

Replace `YOUR_SUBDOMAIN` with the subdomain of your Singular Links domain. Singular hosts the required `assetlinks.json` file for you, but you must enter the SHA256 fingerprints of your signing keys in the Singular dashboard under **Settings → Apps** so that Android can verify the App Links.

### iOS

The [Singular SDK for iOS](https://github.com/singular-labs/Singular-iOS-SDK) can be integrated via Swift Package Manager (recommended) or CocoaPods. This plugin requires iOS 15 or later.

#### App Tracking Transparency

The SDK only collects the advertising identifier (IDFA) if the user has granted tracking permission. You can request the permission with the [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/) plugin. In that case, add the `NSUserTrackingUsageDescription` key to your `ios/App/App/Info.plist` file:

```xml
<key>NSUserTrackingUsageDescription</key>
<string>The advertising identifier is used to measure the performance of our advertising campaigns.</string>
```

Set `iosWaitForTrackingAuthorizationTimeout` when calling `initialize(...)` so that the SDK waits for the user's decision before it sends the first session.

#### Singular Links

To open Singular Links in your app, add the **Associated Domains** capability to your app in Xcode and add an entry for each Singular Links domain in the format `applinks:YOUR_SUBDOMAIN.sng.link`.

#### SKAdNetwork

[SKAdNetwork](https://support.singular.net/hc/en-us/articles/360047448611-Introduction-to-Singular-s-SKAdNetwork-Solution) support is enabled by default and the SDK manages the conversion value for you. Set `iosManualSkanConversionManagement` to `true` when calling `initialize(...)` if your app manages the conversion value itself. In that case, call `skanRegisterAppForAdNetworkAttribution()` and `skanUpdateConversionValue(...)` yourself.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Initialize the plugin

Add your listeners before calling `initialize(...)` so that no deferred deep link or attribution event is missed:

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const initialize = async () => {
  await Singular.addListener('singularLinkResolved', event => {
    console.log('Singular Link resolved', event.deepLink, event.isDeferred);
  });
  await Singular.addListener('deviceAttributionInfoReceived', event => {
    console.log('Attributed to network', event.network);
  });

  await Singular.initialize({
    apiKey: 'YOUR_SDK_KEY',
    secret: 'YOUR_SDK_SECRET',
  });
};
```

### Track events

Track a standard event or a custom event with attributes:

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const trackLogin = async () => {
  await Singular.trackEvent({
    name: 'sng_login',
  });
};

const trackLevelCompleted = async () => {
  await Singular.trackEvent({
    name: 'level_completed',
    attributes: { level: 3, character: 'warrior' },
  });
};
```

See [Singular Standard Events](https://support.singular.net/hc/en-us/articles/7648172966299-Singular-Standard-Events-Full-List-and-Recommended-Events-by-Vertical) for the list of standard event names and attributes.

### Track revenue

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const trackRevenue = async () => {
  await Singular.trackRevenue({
    amount: 9.99,
    currency: 'USD',
    eventName: 'subscription_purchase',
  });
};
```

### Track ad revenue

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const trackAdRevenue = async () => {
  await Singular.trackAdRevenue({
    adPlatform: 'AdMob',
    adType: 'Rewarded',
    currency: 'USD',
    revenue: 0.05,
  });
};
```

### Set the custom user ID

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const login = async () => {
  await Singular.setCustomUserId({ customUserId: 'user-123' });
};

const logout = async () => {
  await Singular.unsetCustomUserId();
};
```

### Global properties

Global properties are attached to all events. At most 5 global properties can be set:

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const setGlobalProperty = async () => {
  await Singular.setGlobalProperty({ key: 'plan', value: 'premium' });
};

const getGlobalProperties = async () => {
  const { properties } = await Singular.getGlobalProperties();
  return properties;
};

const clearGlobalProperties = async () => {
  await Singular.clearGlobalProperties();
};
```

### Privacy

Notify the SDK about the consent of the user and stop tracking if the user opts out:

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const optIn = async () => {
  await Singular.trackingOptIn();
};

const optOut = async () => {
  await Singular.stopAllTracking();
};

const limitDataSharing = async () => {
  await Singular.setLimitDataSharing({ limit: true });
};
```

Call `resumeAllTracking()` to resume tracking and `trackingUnder13()` if the user is under 13 years old.

### Singular Links

Handle deep links and deferred deep links and create a short link for a referring user:

```typescript
import { Singular } from '@capawesome/capacitor-singular';

const addSingularLinkResolvedListener = async () => {
  await Singular.addListener('singularLinkResolved', event => {
    console.log('Deep link', event.deepLink);
    console.log('Deferred', event.isDeferred);
    console.log('URL parameters', event.urlParameters);
  });
};

const createReferrerShortLink = async () => {
  const { link } = await Singular.createReferrerShortLink({
    baseLink: 'https://myapp.sng.link/A1b2c/d3e4',
    passthroughParameters: { campaign: 'friend-invite' },
    referrerId: 'user-123',
    referrerName: 'Jane Doe',
  });
  return link;
};
```

### SKAdNetwork

Only required if `iosManualSkanConversionManagement` is enabled:

```typescript
import {
  Singular,
  SkanCoarseConversionValue,
} from '@capawesome/capacitor-singular';

const registerAppForAdNetworkAttribution = async () => {
  await Singular.skanRegisterAppForAdNetworkAttribution();
};

const updateConversionValue = async () => {
  await Singular.skanUpdateConversionValue({
    coarseValue: SkanCoarseConversionValue.Medium,
    value: 10,
  });
};

const getConversionValue = async () => {
  const { value } = await Singular.skanGetConversionValue();
  return value;
};
```

### Uninstall tracking

Pass the push notification device token to the SDK. On Android, this is the FCM registration token. On iOS, this is the hex-encoded APNs device token:

```typescript
import { PushNotifications } from '@capacitor/push-notifications';
import { Singular } from '@capawesome/capacitor-singular';

const enableUninstallTracking = async () => {
  await PushNotifications.addListener('registration', async token => {
    await Singular.setDeviceToken({ token: token.value });
  });
  await PushNotifications.register();
};
```

## API

<docgen-index>

* [`clearGlobalProperties()`](#clearglobalproperties)
* [`createReferrerShortLink(...)`](#createreferrershortlink)
* [`getGlobalProperties()`](#getglobalproperties)
* [`getLimitDataSharing()`](#getlimitdatasharing)
* [`initialize(...)`](#initialize)
* [`isAllTrackingStopped()`](#isalltrackingstopped)
* [`resumeAllTracking()`](#resumealltracking)
* [`setCustomUserId(...)`](#setcustomuserid)
* [`setDeviceToken(...)`](#setdevicetoken)
* [`setGlobalProperty(...)`](#setglobalproperty)
* [`setLimitAdvertisingIdentifiers(...)`](#setlimitadvertisingidentifiers)
* [`setLimitDataSharing(...)`](#setlimitdatasharing)
* [`skanGetConversionValue()`](#skangetconversionvalue)
* [`skanRegisterAppForAdNetworkAttribution()`](#skanregisterappforadnetworkattribution)
* [`skanUpdateConversionValue(...)`](#skanupdateconversionvalue)
* [`stopAllTracking()`](#stopalltracking)
* [`trackAdRevenue(...)`](#trackadrevenue)
* [`trackEvent(...)`](#trackevent)
* [`trackRevenue(...)`](#trackrevenue)
* [`trackingOptIn()`](#trackingoptin)
* [`trackingUnder13()`](#trackingunder13)
* [`unsetCustomUserId()`](#unsetcustomuserid)
* [`unsetGlobalProperty(...)`](#unsetglobalproperty)
* [`addListener('deviceAttributionInfoReceived', ...)`](#addlistenerdeviceattributioninforeceived-)
* [`addListener('singularLinkResolved', ...)`](#addlistenersingularlinkresolved-)
* [`addListener('skanConversionValueUpdated', ...)`](#addlistenerskanconversionvalueupdated-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clearGlobalProperties()

```typescript
clearGlobalProperties() => Promise<void>
```

Remove all global properties.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### createReferrerShortLink(...)

```typescript
createReferrerShortLink(options: CreateReferrerShortLinkOptions) => Promise<CreateReferrerShortLinkResult>
```

Create a short link that attributes installs to a referring user.

The short link expires after 30 days.

Only available on Android and iOS.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createreferrershortlinkoptions">CreateReferrerShortLinkOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createreferrershortlinkresult">CreateReferrerShortLinkResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getGlobalProperties()

```typescript
getGlobalProperties() => Promise<GetGlobalPropertiesResult>
```

Get all global properties.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getglobalpropertiesresult">GetGlobalPropertiesResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getLimitDataSharing()

```typescript
getLimitDataSharing() => Promise<GetLimitDataSharingResult>
```

Get whether data sharing is limited.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getlimitdatasharingresult">GetLimitDataSharingResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the Singular SDK and start the first session.

This method must be called before any other method.
Add your listeners before calling this method so that no
deferred deep link or attribution event is missed.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### isAllTrackingStopped()

```typescript
isAllTrackingStopped() => Promise<IsAllTrackingStoppedResult>
```

Get whether all tracking has been stopped via `stopAllTracking()`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isalltrackingstoppedresult">IsAllTrackingStoppedResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### resumeAllTracking()

```typescript
resumeAllTracking() => Promise<void>
```

Resume all tracking after it was stopped via `stopAllTracking()`.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### setCustomUserId(...)

```typescript
setCustomUserId(options: SetCustomUserIdOptions) => Promise<void>
```

Set the custom user ID that is attached to all sessions and events.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setcustomuseridoptions">SetCustomUserIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### setDeviceToken(...)

```typescript
setDeviceToken(options: SetDeviceTokenOptions) => Promise<void>
```

Set the push notification device token used for uninstall tracking.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#setdevicetokenoptions">SetDeviceTokenOptions</a></code> |

**Since:** 0.1.0

--------------------


### setGlobalProperty(...)

```typescript
setGlobalProperty(options: SetGlobalPropertyOptions) => Promise<void>
```

Set a global property that is attached to all events.

At most 5 global properties can be set.
The call is rejected if the property could not be set.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setglobalpropertyoptions">SetGlobalPropertyOptions</a></code> |

**Since:** 0.1.0

--------------------


### setLimitAdvertisingIdentifiers(...)

```typescript
setLimitAdvertisingIdentifiers(options: SetLimitAdvertisingIdentifiersOptions) => Promise<void>
```

Set whether the SDK is allowed to collect advertising identifiers
(e.g. the Google Advertising ID or the IDFA).

Only available on Android and iOS.

| Param         | Type                                                                                                    |
| ------------- | ------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setlimitadvertisingidentifiersoptions">SetLimitAdvertisingIdentifiersOptions</a></code> |

**Since:** 0.1.0

--------------------


### setLimitDataSharing(...)

```typescript
setLimitDataSharing(options: SetLimitDataSharingOptions) => Promise<void>
```

Set whether data sharing with third parties is limited
(e.g. after the user opted out under CCPA).

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setlimitdatasharingoptions">SetLimitDataSharingOptions</a></code> |

**Since:** 0.1.0

--------------------


### skanGetConversionValue()

```typescript
skanGetConversionValue() => Promise<SkanGetConversionValueResult>
```

Get the current SKAdNetwork conversion value.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#skangetconversionvalueresult">SkanGetConversionValueResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### skanRegisterAppForAdNetworkAttribution()

```typescript
skanRegisterAppForAdNetworkAttribution() => Promise<void>
```

Register the app for SKAdNetwork attribution.

Only required if `iosManualSkanConversionManagement` is enabled.

Only available on iOS.

**Since:** 0.1.0

--------------------


### skanUpdateConversionValue(...)

```typescript
skanUpdateConversionValue(options: SkanUpdateConversionValueOptions) => Promise<void>
```

Update the SKAdNetwork conversion value.

Only required if `iosManualSkanConversionManagement` is enabled.
The call is rejected if the conversion value could not be updated.

Only available on iOS.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#skanupdateconversionvalueoptions">SkanUpdateConversionValueOptions</a></code> |

**Since:** 0.1.0

--------------------


### stopAllTracking()

```typescript
stopAllTracking() => Promise<void>
```

Stop all tracking.

This setting persists across app restarts until `resumeAllTracking()` is called.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### trackAdRevenue(...)

```typescript
trackAdRevenue(options: TrackAdRevenueOptions) => Promise<void>
```

Track ad revenue.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#trackadrevenueoptions">TrackAdRevenueOptions</a></code> |

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


### trackRevenue(...)

```typescript
trackRevenue(options: TrackRevenueOptions) => Promise<void>
```

Track a revenue event.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#trackrevenueoptions">TrackRevenueOptions</a></code> |

**Since:** 0.1.0

--------------------


### trackingOptIn()

```typescript
trackingOptIn() => Promise<void>
```

Notify the SDK that the user has opted in to tracking (e.g. under GDPR).

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### trackingUnder13()

```typescript
trackingUnder13() => Promise<void>
```

Notify the SDK that the user is under 13 years old
so that the SDK does not collect advertising identifiers.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### unsetCustomUserId()

```typescript
unsetCustomUserId() => Promise<void>
```

Remove the custom user ID.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### unsetGlobalProperty(...)

```typescript
unsetGlobalProperty(options: UnsetGlobalPropertyOptions) => Promise<void>
```

Remove a global property.

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#unsetglobalpropertyoptions">UnsetGlobalPropertyOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('deviceAttributionInfoReceived', ...)

```typescript
addListener(eventName: 'deviceAttributionInfoReceived', listenerFunc: (event: DeviceAttributionInfoReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for when the device attribution information is received.

The event is emitted once after the first session, and only if
device attribution is enabled for your Singular account.

Only available on Android and iOS.

| Param              | Type                                                                                                                  |
| ------------------ | --------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'deviceAttributionInfoReceived'</code>                                                                          |
| **`listenerFunc`** | <code>(event: <a href="#deviceattributioninforeceivedevent">DeviceAttributionInfoReceivedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('singularLinkResolved', ...)

```typescript
addListener(eventName: 'singularLinkResolved', listenerFunc: (event: SingularLinkResolvedEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for when a Singular Link is resolved.

This includes deferred deep links after an install.

Only available on Android and iOS.

| Param              | Type                                                                                                |
| ------------------ | --------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'singularLinkResolved'</code>                                                                 |
| **`listenerFunc`** | <code>(event: <a href="#singularlinkresolvedevent">SingularLinkResolvedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('skanConversionValueUpdated', ...)

```typescript
addListener(eventName: 'skanConversionValueUpdated', listenerFunc: (event: SkanConversionValueUpdatedEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for when the SDK updates the SKAdNetwork conversion value.

Only available on iOS.

| Param              | Type                                                                                                            |
| ------------------ | --------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'skanConversionValueUpdated'</code>                                                                       |
| **`listenerFunc`** | <code>(event: <a href="#skanconversionvalueupdatedevent">SkanConversionValueUpdatedEvent</a>) =&gt; void</code> |

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


#### CreateReferrerShortLinkResult

| Prop       | Type                | Description               | Since |
| ---------- | ------------------- | ------------------------- | ----- |
| **`link`** | <code>string</code> | The generated short link. | 0.1.0 |


#### CreateReferrerShortLinkOptions

| Prop                        | Type                                      | Description                                                         | Since |
| --------------------------- | ----------------------------------------- | ------------------------------------------------------------------- | ----- |
| **`baseLink`**              | <code>string</code>                       | The Singular Link to shorten.                                       | 0.1.0 |
| **`passthroughParameters`** | <code>Record&lt;string, string&gt;</code> | Additional parameters that are passed through to the installed app. | 0.1.0 |
| **`referrerId`**            | <code>string</code>                       | The unique identifier of the referring user.                        | 0.1.0 |
| **`referrerName`**          | <code>string</code>                       | The name of the referring user.                                     | 0.1.0 |


#### GetGlobalPropertiesResult

| Prop             | Type                                      | Description            | Since |
| ---------------- | ----------------------------------------- | ---------------------- | ----- |
| **`properties`** | <code>Record&lt;string, string&gt;</code> | The global properties. | 0.1.0 |


#### GetLimitDataSharingResult

| Prop        | Type                 | Description                      | Since |
| ----------- | -------------------- | -------------------------------- | ----- |
| **`limit`** | <code>boolean</code> | Whether data sharing is limited. | 0.1.0 |


#### InitializeOptions

| Prop                                         | Type                                      | Description                                                                                                                                                                                                                                             | Default            | Since |
| -------------------------------------------- | ----------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`androidFacebookAppId`**                   | <code>string</code>                       | The Facebook App ID used for Meta Install Referrer attribution. Only available on Android.                                                                                                                                                              |                    | 0.1.0 |
| **`apiKey`**                                 | <code>string</code>                       | The SDK key of your Singular account.                                                                                                                                                                                                                   |                    | 0.1.0 |
| **`brandedDomains`**                         | <code>string[]</code>                     | The branded domains that should be resolved as Singular Links.                                                                                                                                                                                          |                    | 0.1.0 |
| **`customUserId`**                           | <code>string</code>                       | The custom user ID that is attached to all sessions and events.                                                                                                                                                                                         |                    | 0.1.0 |
| **`espDomains`**                             | <code>string[]</code>                     | The email service provider domains that should be resolved as Singular Links.                                                                                                                                                                           |                    | 0.1.0 |
| **`globalProperties`**                       | <code>Record&lt;string, string&gt;</code> | Global properties that are attached to all events. Existing global properties with the same key are overridden. At most 5 global properties can be set.                                                                                                 |                    | 0.1.0 |
| **`iosManualSkanConversionManagement`**      | <code>boolean</code>                      | Whether the SKAdNetwork conversion value is managed by your app instead of by the SDK. Only available on iOS.                                                                                                                                           | <code>false</code> | 0.1.0 |
| **`iosSkAdNetworkEnabled`**                  | <code>boolean</code>                      | Whether SKAdNetwork support is enabled. Only available on iOS.                                                                                                                                                                                          | <code>true</code>  | 0.1.0 |
| **`iosWaitForTrackingAuthorizationTimeout`** | <code>number</code>                       | The number of seconds the SDK waits for the App Tracking Transparency authorization before sending the first session. Set this to a value greater than `0` if you request the tracking authorization right after the app launch. Only available on iOS. | <code>0</code>     | 0.1.0 |
| **`limitAdvertisingIdentifiers`**            | <code>boolean</code>                      | Whether the SDK is not allowed to collect advertising identifiers (e.g. the Google Advertising ID or the IDFA).                                                                                                                                         | <code>false</code> | 0.1.0 |
| **`limitDataSharing`**                       | <code>boolean</code>                      | Whether data sharing with third parties is limited.                                                                                                                                                                                                     | <code>false</code> | 0.1.0 |
| **`loggingEnabled`**                         | <code>boolean</code>                      | Whether debug logging is enabled.                                                                                                                                                                                                                       | <code>false</code> | 0.1.0 |
| **`secret`**                                 | <code>string</code>                       | The SDK secret of your Singular account.                                                                                                                                                                                                                |                    | 0.1.0 |
| **`sessionTimeout`**                         | <code>number</code>                       | The number of seconds the app can stay in the background before a new session is started.                                                                                                                                                               | <code>60</code>    | 0.1.0 |
| **`shortLinkResolveTimeout`**                | <code>number</code>                       | The number of seconds the SDK waits for a short link to be resolved.                                                                                                                                                                                    | <code>10</code>    | 0.1.0 |


#### IsAllTrackingStoppedResult

| Prop          | Type                 | Description                            | Since |
| ------------- | -------------------- | -------------------------------------- | ----- |
| **`stopped`** | <code>boolean</code> | Whether all tracking has been stopped. | 0.1.0 |


#### SetCustomUserIdOptions

| Prop               | Type                | Description         | Since |
| ------------------ | ------------------- | ------------------- | ----- |
| **`customUserId`** | <code>string</code> | The custom user ID. | 0.1.0 |


#### SetDeviceTokenOptions

| Prop        | Type                | Description                                                                                                                            | Since |
| ----------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`token`** | <code>string</code> | The push notification device token. On Android, this is the FCM registration token. On iOS, this is the hex-encoded APNs device token. | 0.1.0 |


#### SetGlobalPropertyOptions

| Prop                   | Type                 | Description                                                          | Default           | Since |
| ---------------------- | -------------------- | -------------------------------------------------------------------- | ----------------- | ----- |
| **`key`**              | <code>string</code>  | The key of the global property.                                      |                   | 0.1.0 |
| **`overrideExisting`** | <code>boolean</code> | Whether an existing global property with the same key is overridden. | <code>true</code> | 0.1.0 |
| **`value`**            | <code>string</code>  | The value of the global property.                                    |                   | 0.1.0 |


#### SetLimitAdvertisingIdentifiersOptions

| Prop        | Type                 | Description                                                        | Since |
| ----------- | -------------------- | ------------------------------------------------------------------ | ----- |
| **`limit`** | <code>boolean</code> | Whether the SDK is not allowed to collect advertising identifiers. | 0.1.0 |


#### SetLimitDataSharingOptions

| Prop        | Type                 | Description                                         | Since |
| ----------- | -------------------- | --------------------------------------------------- | ----- |
| **`limit`** | <code>boolean</code> | Whether data sharing with third parties is limited. | 0.1.0 |


#### SkanGetConversionValueResult

| Prop        | Type                        | Description                                                                            | Since |
| ----------- | --------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`value`** | <code>number \| null</code> | The fine-grained conversion value (`0` - `63`) or `null` if no value has been set yet. | 0.1.0 |


#### SkanUpdateConversionValueOptions

| Prop              | Type                                                                            | Description                                                                  | Default            | Since |
| ----------------- | ------------------------------------------------------------------------------- | ---------------------------------------------------------------------------- | ------------------ | ----- |
| **`coarseValue`** | <code><a href="#skancoarseconversionvalue">SkanCoarseConversionValue</a></code> | The coarse conversion value. Only available on iOS 16.1+.                    |                    | 0.1.0 |
| **`lockWindow`**  | <code>boolean</code>                                                            | Whether the conversion window should be locked. Only available on iOS 16.1+. | <code>false</code> | 0.1.0 |
| **`value`**       | <code>number</code>                                                             | The fine-grained conversion value (`0` - `63`).                              |                    | 0.1.0 |


#### TrackAdRevenueOptions

| Prop                  | Type                | Description                                          | Since |
| --------------------- | ------------------- | ---------------------------------------------------- | ----- |
| **`adGroupId`**       | <code>string</code> | The identifier of the ad group.                      | 0.1.0 |
| **`adGroupName`**     | <code>string</code> | The name of the ad group.                            | 0.1.0 |
| **`adGroupPriority`** | <code>string</code> | The priority of the ad group.                        | 0.1.0 |
| **`adGroupType`**     | <code>string</code> | The type of the ad group.                            | 0.1.0 |
| **`adPlacementName`** | <code>string</code> | The name of the ad placement.                        | 0.1.0 |
| **`adPlatform`**      | <code>string</code> | The ad mediation platform that reported the revenue. | 0.1.0 |
| **`adType`**          | <code>string</code> | The type of the ad.                                  | 0.1.0 |
| **`adUnitId`**        | <code>string</code> | The identifier of the ad unit.                       | 0.1.0 |
| **`adUnitName`**      | <code>string</code> | The name of the ad unit.                             | 0.1.0 |
| **`currency`**        | <code>string</code> | The currency of the revenue as ISO 4217 code.        | 0.1.0 |
| **`impressionId`**    | <code>string</code> | The identifier of the ad impression.                 | 0.1.0 |
| **`networkName`**     | <code>string</code> | The name of the ad network that served the ad.       | 0.1.0 |
| **`placementId`**     | <code>string</code> | The identifier of the placement.                     | 0.1.0 |
| **`precision`**       | <code>string</code> | The precision of the reported revenue.               | 0.1.0 |
| **`revenue`**         | <code>number</code> | The revenue amount.                                  | 0.1.0 |


#### TrackEventOptions

| Prop             | Type                                                           | Description                                                                                                                                       | Since |
| ---------------- | -------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`attributes`** | <code>Record&lt;string, string \| number \| boolean&gt;</code> | The attributes of the event. Attribute keys and values are limited to 500 characters.                                                             | 0.1.0 |
| **`name`**       | <code>string</code>                                            | The name of the event. Can be a standard event name (e.g. `sng_login`, `sng_tutorial_complete`) or a custom event name. Limited to 32 characters. | 0.1.0 |


#### TrackRevenueOptions

| Prop             | Type                                                           | Description                                                                                                                | Since |
| ---------------- | -------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`amount`**     | <code>number</code>                                            | The revenue amount.                                                                                                        | 0.1.0 |
| **`attributes`** | <code>Record&lt;string, string \| number \| boolean&gt;</code> | The attributes of the event.                                                                                               | 0.1.0 |
| **`currency`**   | <code>string</code>                                            | The currency of the revenue as ISO 4217 code.                                                                              | 0.1.0 |
| **`eventName`**  | <code>string</code>                                            | The name of the event. If provided, a custom revenue event with this name is tracked instead of the default revenue event. | 0.1.0 |


#### UnsetGlobalPropertyOptions

| Prop      | Type                | Description                     | Since |
| --------- | ------------------- | ------------------------------- | ----- |
| **`key`** | <code>string</code> | The key of the global property. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### DeviceAttributionInfoReceivedEvent

| Prop                  | Type                | Description                                                                 | Since |
| --------------------- | ------------------- | --------------------------------------------------------------------------- | ----- |
| **`campaignId`**      | <code>string</code> | The identifier of the campaign.                                             | 0.1.0 |
| **`campaignName`**    | <code>string</code> | The name of the campaign.                                                   | 0.1.0 |
| **`clickTimestamp`**  | <code>number</code> | The timestamp of the attributed click in milliseconds since the Unix epoch. | 0.1.0 |
| **`creativeId`**      | <code>string</code> | The identifier of the creative.                                             | 0.1.0 |
| **`creativeName`**    | <code>string</code> | The name of the creative.                                                   | 0.1.0 |
| **`matchType`**       | <code>string</code> | The type of the attribution match.                                          | 0.1.0 |
| **`network`**         | <code>string</code> | The name of the attributed network.                                         | 0.1.0 |
| **`passthrough`**     | <code>string</code> | The passthrough parameters of the attributed link.                          | 0.1.0 |
| **`subcampaignId`**   | <code>string</code> | The identifier of the sub campaign.                                         | 0.1.0 |
| **`subcampaignName`** | <code>string</code> | The name of the sub campaign.                                               | 0.1.0 |


#### SingularLinkResolvedEvent

| Prop                | Type                                      | Description                                                                | Since |
| ------------------- | ----------------------------------------- | -------------------------------------------------------------------------- | ----- |
| **`deepLink`**      | <code>string \| null</code>               | The deep link value of the Singular Link.                                  | 0.1.0 |
| **`isDeferred`**    | <code>boolean</code>                      | Whether the link is a deferred deep link (i.e. resolved after an install). | 0.1.0 |
| **`passthrough`**   | <code>string \| null</code>               | The passthrough value of the Singular Link.                                | 0.1.0 |
| **`urlParameters`** | <code>Record&lt;string, string&gt;</code> | The query parameters of the Singular Link.                                 | 0.1.0 |


#### SkanConversionValueUpdatedEvent

| Prop              | Type                                                                                    | Description                                                           | Since |
| ----------------- | --------------------------------------------------------------------------------------- | --------------------------------------------------------------------- | ----- |
| **`coarseValue`** | <code><a href="#skancoarseconversionvalue">SkanCoarseConversionValue</a> \| null</code> | The coarse conversion value. Only available on iOS 16.1+.             | 0.1.0 |
| **`lockWindow`**  | <code>boolean</code>                                                                    | Whether the conversion window is locked. Only available on iOS 16.1+. | 0.1.0 |
| **`value`**       | <code>number \| null</code>                                                             | The fine-grained conversion value (`0` - `63`).                       | 0.1.0 |


### Enums


#### SkanCoarseConversionValue

| Members      | Value                 | Since |
| ------------ | --------------------- | ----- |
| **`High`**   | <code>'HIGH'</code>   | 0.1.0 |
| **`Low`**    | <code>'LOW'</code>    | 0.1.0 |
| **`Medium`** | <code>'MEDIUM'</code> | 0.1.0 |

</docgen-api>

## FAQ

### Do I need a Singular account to use this plugin?

Yes. This plugin wraps the official Singular SDKs, which require a [Singular](https://www.singular.net/) account, an SDK key and an SDK secret.

### Where do I find the SDK key and the SDK secret?

Both are available in the Singular dashboard under **Developer Tools → SDK Integration → SDK Keys**. Do not use the Singular Reporting API key, otherwise no SDK data is received.

### Do I have to call `initialize(...)` before all other methods?

Yes. All other methods reject with the error code `NOT_INITIALIZED` until `initialize(...)` has been called.

### Why should I add my listeners before calling `initialize(...)`?

The SDK resolves deferred deep links and reports the device attribution information during the first session. If you add the listeners after calling `initialize(...)`, these events may be emitted before your listeners are attached and are therefore missed.

### Does `stopAllTracking()` persist across app restarts?

Yes. Tracking stays stopped until you call `resumeAllTracking()`, even if the app is restarted in between. You can check the current state with `isAllTrackingStopped()`.

### Are there any limits for event names, attributes and global properties?

Yes. Event names are limited to 32 characters, attribute keys and values to 500 characters, and at most 5 global properties can be set. Currencies must be passed as upper case ISO 4217 codes (e.g. `USD`).

### Do I have to handle deep links that open the app while it is already running?

No. The plugin handles warm starts for you by re-initializing the SDK with the new link so that the `singularLinkResolved` event is emitted. On Android, this requires the default `singleTask` launch mode of Capacitor's `MainActivity`.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/): Request the tracking permission required to collect the IDFA on iOS.
- [Install Referrer](https://capawesome.io/docs/sdks/capacitor/install-referrer/): Read install attribution data from the Play Install Referrer and Apple Ad Services.
- [PostHog](https://capawesome.io/docs/sdks/capacitor/posthog/): Unofficial Capacitor plugin for the PostHog product analytics platform.
- [TikTok App Events](https://capawesome.io/docs/sdks/capacitor/tiktok-app-events/): Unofficial Capacitor plugin for the TikTok App Events SDK.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/singular/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/singular/LICENSE).

## Third-Party Notices

The Singular SDK for iOS is licensed under the [MIT license](https://github.com/singular-labs/Singular-iOS-SDK/blob/master/LICENSE) and is distributed via CocoaPods and Swift Package Manager. The Singular SDK for Android is distributed as a binary artifact from Singular's Maven repository and does not declare a license. Use of both SDKs is additionally governed by the [Singular Terms & Conditions of Service](https://www.singular.net/terms/), which limit the use of the service to your own advertising and promotions and prohibit sending Protected Health Information (as defined under HIPAA) to Singular. This plugin only declares these SDKs as dependencies and does not bundle or modify them. The MIT license of this plugin covers the wrapper code only, not the Singular SDKs.

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Singular Labs, Inc. or any of their affiliates or subsidiaries.
