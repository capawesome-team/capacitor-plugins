# @capawesome/capacitor-superwall

Unofficial Capacitor plugin for [Superwall SDK](https://superwall.com/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer a comprehensive Capacitor plugin for Superwall SDK integration. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 💰 **Paywall Presentation**: Show beautiful, remotely-configured paywalls to drive subscriptions.
- 🎯 **Feature Gating**: Control feature access based on subscription status.
- 📊 **Analytics Events**: Forward Superwall events to your analytics platform.
- 👤 **User Management**: Identify users, set custom attributes, and manage subscription status.
- 🧪 **A/B Testing**: Built-in support for paywall experiments and holdout groups.
- 🔗 **Deep Linking**: Handle deep links for paywall campaigns.
- 🎨 **Customization**: Configure paywall behavior, logging, and appearance.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Installation

```bash
npm install @capawesome/capacitor-superwall
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$superwallAndroidVersion` version of `com.superwall.sdk:superwall-android` (default: `2.6.6`)

#### Manifest

Add the required activities to your `AndroidManifest.xml` file:

```xml
<activity
  android:name="com.superwall.sdk.paywall.view.SuperwallPaywallActivity"
  android:theme="@style/Theme.MaterialComponents.DayNight.NoActionBar"
  android:configChanges="orientation|screenSize|keyboardHidden">
</activity>
<activity android:name="com.superwall.sdk.debug.DebugViewActivity" />
<activity android:name="com.superwall.sdk.debug.localizations.SWLocalizationActivity" />
<activity android:name="com.superwall.sdk.debug.SWConsoleActivity" />
```

Set your app's theme in the `android:theme` section of the `SuperwallPaywallActivity`.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Superwall } from '@capawesome/capacitor-superwall';

const configureSuperwall = async () => {
  await Superwall.configure({
    apiKey: 'pk_your_api_key_here',
    options: {
      paywalls: {
        shouldPreload: true,
        automaticallyDismiss: true,
      },
      logging: {
        level: 'WARN',
        scopes: ['ALL'],
      },
    },
  });
};

const showPaywall = async () => {
  const result = await Superwall.register({
    placement: 'premium_feature',
    params: {
      user_level: 5,
      source: 'settings',
    },
  });

  console.log('Paywall result:', result.result); // 'PURCHASED', 'CANCELLED', or 'RESTORED'
};

const checkPaywall = async () => {
  const result = await Superwall.getPresentationResult({
    placement: 'premium_feature',
  });

  if (result.type === 'PAYWALL') {
    console.log('Paywall would be shown');
  } else if (result.type === 'NO_AUDIENCE_MATCH') {
    console.log('User does not match audience, no paywall');
  }
};

const identifyUser = async (userId: string) => {
  await Superwall.identify({
    userId: userId,
    options: {
      restorePaywallAssignments: true,
    },
  });
};

const setAttributes = async () => {
  await Superwall.setUserAttributes({
    attributes: {
      username: 'john_doe',
      subscription_tier: 'free',
      total_purchases: 5,
    },
  });
};

const checkSubscription = async () => {
  const result = await Superwall.getSubscriptionStatus();
  console.log('Subscription status:', result.status); // 'ACTIVE', 'INACTIVE', or 'UNKNOWN'
};

const addListeners = async () => {
  // Forward analytics events to your platform
  await Superwall.addListener('superwallEvent', (event) => {
    console.log('Superwall event:', event.type, event.data);
    // Forward to your analytics: Analytics.track(event.type, event.data);
  });

  // Track subscription status changes
  await Superwall.addListener('subscriptionStatusDidChange', (event) => {
    console.log('Subscription status changed to:', event.status);
  });

  // Handle paywall lifecycle events
  await Superwall.addListener('paywallPresented', (event) => {
    console.log('Paywall presented:', event.paywallInfo.placement);
  });

  await Superwall.addListener('paywallDismissed', (event) => {
    console.log('Paywall dismissed:', event.paywallInfo.placement);
  });

  // Handle custom paywall actions
  await Superwall.addListener('customPaywallAction', (event) => {
    console.log('Custom action:', event.name);
    // Handle custom actions like 'help', 'contact', etc.
  });
};

const handleCampaignDeepLink = async (url: string) => {
  await Superwall.handleDeepLink({ url });
};

const logout = async () => {
  await Superwall.reset();
};
```

## API

<docgen-index>

* [`configure(...)`](#configure)
* [`register(...)`](#register)
* [`getPresentationResult(...)`](#getpresentationresult)
* [`identify(...)`](#identify)
* [`reset()`](#reset)
* [`getUserId()`](#getuserid)
* [`getIsLoggedIn()`](#getisloggedin)
* [`setUserAttributes(...)`](#setuserattributes)
* [`handleDeepLink(...)`](#handledeeplink)
* [`getSubscriptionStatus()`](#getsubscriptionstatus)
* [`addListener('superwallEvent', ...)`](#addlistenersuperwallevent-)
* [`addListener('subscriptionStatusDidChange', ...)`](#addlistenersubscriptionstatusdidchange-)
* [`addListener('paywallPresented', ...)`](#addlistenerpaywallpresented-)
* [`addListener('paywallWillDismiss', ...)`](#addlistenerpaywallwilldismiss-)
* [`addListener('paywallDismissed', ...)`](#addlistenerpaywalldismissed-)
* [`addListener('customPaywallAction', ...)`](#addlistenercustompaywallaction-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### configure(...)

```typescript
configure(options: ConfigureOptions) => Promise<void>
```

Configure the Superwall SDK.

This method must be called once before all other methods.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#configureoptions">ConfigureOptions</a></code> |

**Since:** 0.0.1

--------------------


### register(...)

```typescript
register(options: RegisterOptions) => Promise<RegisterResult>
```

Register a placement and present a paywall if the user doesn't have an active subscription.

This is the primary method for feature gating and paywall presentation.
The feature closure will execute based on the gating mode:
- Non-gated: Executes immediately
- Gated: Executes after subscription or if already subscribed

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#registeroptions">RegisterOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#registerresult">RegisterResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getPresentationResult(...)

```typescript
getPresentationResult(options: GetPresentationResultOptions) => Promise<GetPresentationResultResult>
```

Check if a paywall would be presented for a placement without actually presenting it.

Useful for determining whether to show a feature or paywall before the user interacts.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getpresentationresultoptions">GetPresentationResultOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getpresentationresultresult">GetPresentationResultResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### identify(...)

```typescript
identify(options: IdentifyOptions) => Promise<void>
```

Identify the current user with a unique ID.

This links the user ID to their anonymous alias for analytics and paywall assignments.

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#identifyoptions">IdentifyOptions</a></code> |

**Since:** 0.0.1

--------------------


### reset()

```typescript
reset() => Promise<void>
```

Reset the user identity.

This rotates the anonymous user ID, clears local paywall assignments,
and requires the SDK to re-download configuration.
Should only be called on explicit logout.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### getUserId()

```typescript
getUserId() => Promise<GetUserIdResult>
```

Get the current user ID.

Returns the identified user ID if set, otherwise returns the anonymous ID.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getuseridresult">GetUserIdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getIsLoggedIn()

```typescript
getIsLoggedIn() => Promise<GetIsLoggedInResult>
```

Check if the user is logged in (identified).

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getisloggedinresult">GetIsLoggedInResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### setUserAttributes(...)

```typescript
setUserAttributes(options: SetUserAttributesOptions) => Promise<void>
```

Set custom user attributes for personalization and audience filtering.

Attributes can be used in audience filters on the Superwall dashboard.
Keys starting with `$` are reserved for Superwall use.
Arrays and nested structures are not supported.
Set values to null to remove attributes.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setuserattributesoptions">SetUserAttributesOptions</a></code> |

**Since:** 0.0.1

--------------------


### handleDeepLink(...)

```typescript
handleDeepLink(options: HandleDeepLinkOptions) => Promise<void>
```

Handle a deep link URL for paywall campaigns.

This processes deep links associated with Superwall campaigns
configured on the dashboard.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#handledeeplinkoptions">HandleDeepLinkOptions</a></code> |

**Since:** 0.0.1

--------------------


### getSubscriptionStatus()

```typescript
getSubscriptionStatus() => Promise<GetSubscriptionStatusResult>
```

Get the current subscription status.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getsubscriptionstatusresult">GetSubscriptionStatusResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('superwallEvent', ...)

```typescript
addListener(eventName: 'superwallEvent', listenerFunc: (event: SuperwallEventInfo) => void) => Promise<PluginListenerHandle>
```

Add a listener for Superwall analytics events.

These events can be forwarded to your analytics platform.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'superwallEvent'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#superwalleventinfo">SuperwallEventInfo</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('subscriptionStatusDidChange', ...)

```typescript
addListener(eventName: 'subscriptionStatusDidChange', listenerFunc: (event: SubscriptionStatusDidChangeEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for subscription status changes.

Only available on Android and iOS.

| Param              | Type                                                                                                              |
| ------------------ | ----------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'subscriptionStatusDidChange'</code>                                                                        |
| **`listenerFunc`** | <code>(event: <a href="#subscriptionstatusdidchangeevent">SubscriptionStatusDidChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('paywallPresented', ...)

```typescript
addListener(eventName: 'paywallPresented', listenerFunc: (event: PaywallPresentedEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for when a paywall is presented.

Only available on Android and iOS.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'paywallPresented'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#paywallpresentedevent">PaywallPresentedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('paywallWillDismiss', ...)

```typescript
addListener(eventName: 'paywallWillDismiss', listenerFunc: (event: PaywallWillDismissEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for when a paywall will dismiss.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'paywallWillDismiss'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#paywallwilldismissevent">PaywallWillDismissEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('paywallDismissed', ...)

```typescript
addListener(eventName: 'paywallDismissed', listenerFunc: (event: PaywallDismissedEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for when a paywall is dismissed.

Only available on Android and iOS.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'paywallDismissed'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#paywalldismissedevent">PaywallDismissedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('customPaywallAction', ...)

```typescript
addListener(eventName: 'customPaywallAction', listenerFunc: (event: CustomPaywallActionEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for custom paywall actions.

Triggered when a user taps an element with the `data-pw-custom` attribute.

Only available on Android and iOS.

| Param              | Type                                                                                              |
| ------------------ | ------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'customPaywallAction'</code>                                                                |
| **`listenerFunc`** | <code>(event: <a href="#custompaywallactionevent">CustomPaywallActionEvent</a>) =&gt; void</code> |

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


#### ConfigureOptions

| Prop          | Type                                                          | Description                                 | Since |
| ------------- | ------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`apiKey`**  | <code>string</code>                                           | The Superwall API key from your dashboard.  | 0.0.1 |
| **`options`** | <code><a href="#superwalloptions">SuperwallOptions</a></code> | Optional configuration options for the SDK. | 0.0.1 |


#### SuperwallOptions

| Prop                                  | Type                                                              | Description                                                                          | Default                                 | Since |
| ------------------------------------- | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------ | --------------------------------------- | ----- |
| **`paywalls`**                        | <code><a href="#paywalloptions">PaywallOptions</a></code>         | Paywall presentation and behavior options.                                           |                                         | 0.0.1 |
| **`logging`**                         | <code><a href="#loggingoptions">LoggingOptions</a></code>         | Logging configuration.                                                               |                                         | 0.0.1 |
| **`networkEnvironment`**              | <code><a href="#networkenvironment">NetworkEnvironment</a></code> | Network environment for API requests.                                                | <code>NetworkEnvironment.Release</code> | 0.0.1 |
| **`localeIdentifier`**                | <code>string</code>                                               | Override the locale identifier for localization.                                     |                                         | 0.0.1 |
| **`shouldObservePurchases`**          | <code>boolean</code>                                              | Observe external Google Play purchases. Only available on Android.                   | <code>true</code>                       | 0.0.1 |
| **`isExternalDataCollectionEnabled`** | <code>boolean</code>                                              | Enable or disable external data collection for analytics. Only available on Android. | <code>true</code>                       | 0.0.1 |


#### PaywallOptions

| Prop                                 | Type                 | Description                                           | Default           | Since |
| ------------------------------------ | -------------------- | ----------------------------------------------------- | ----------------- | ----- |
| **`isHapticFeedbackEnabled`**        | <code>boolean</code> | Enable haptic feedback on paywall interactions.       | <code>true</code> | 0.0.1 |
| **`shouldShowPurchaseFailureAlert`** | <code>boolean</code> | Show an alert when purchase restoration fails.        | <code>true</code> | 0.0.1 |
| **`shouldPreload`**                  | <code>boolean</code> | Preload paywalls during SDK initialization.           | <code>true</code> | 0.0.1 |
| **`automaticallyDismiss`**           | <code>boolean</code> | Automatically dismiss paywall on purchase or restore. | <code>true</code> | 0.0.1 |


#### LoggingOptions

| Prop         | Type                                          | Description                 | Default                     | Since |
| ------------ | --------------------------------------------- | --------------------------- | --------------------------- | ----- |
| **`level`**  | <code><a href="#loglevel">LogLevel</a></code> | The log level for SDK logs. | <code>LogLevel.Warn</code>  | 0.0.1 |
| **`scopes`** | <code>LogScope[]</code>                       | The log scopes to enable.   | <code>[LogScope.All]</code> | 0.0.1 |


#### RegisterResult

| Prop         | Type                                                    | Description                             | Since |
| ------------ | ------------------------------------------------------- | --------------------------------------- | ----- |
| **`result`** | <code><a href="#paywallresult">PaywallResult</a></code> | The result of the paywall presentation. | 0.0.1 |


#### RegisterOptions

| Prop            | Type                                   | Description                                                                                        | Since |
| --------------- | -------------------------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`placement`** | <code>string</code>                    | The placement identifier configured in the Superwall dashboard.                                    | 0.0.1 |
| **`params`**    | <code>Record&lt;string, any&gt;</code> | Optional parameters for audience filtering. Keys starting with `$` are reserved for Superwall use. | 0.0.1 |


#### GetPresentationResultResult

| Prop             | Type                                                                      | Description                                                                             | Since |
| ---------------- | ------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`type`**       | <code><a href="#presentationresulttype">PresentationResultType</a></code> | The type of presentation result.                                                        | 0.0.1 |
| **`experiment`** | <code><a href="#experiment">Experiment</a></code>                         | <a href="#experiment">Experiment</a> information if the result is a paywall or holdout. | 0.0.1 |


#### Experiment

| Prop          | Type                | Description                               | Since |
| ------------- | ------------------- | ----------------------------------------- | ----- |
| **`id`**      | <code>string</code> | The unique identifier for the experiment. | 0.0.1 |
| **`variant`** | <code>string</code> | The variant assigned to the user.         | 0.0.1 |


#### GetPresentationResultOptions

| Prop            | Type                                   | Description                                 | Since |
| --------------- | -------------------------------------- | ------------------------------------------- | ----- |
| **`placement`** | <code>string</code>                    | The placement identifier to check.          | 0.0.1 |
| **`params`**    | <code>Record&lt;string, any&gt;</code> | Optional parameters for audience filtering. | 0.0.1 |


#### IdentifyOptions

| Prop          | Type                                                                    | Description                              | Since |
| ------------- | ----------------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`userId`**  | <code>string</code>                                                     | The unique user ID to identify the user. | 0.0.1 |
| **`options`** | <code><a href="#identifyoptionsconfig">IdentifyOptionsConfig</a></code> | Additional options for identification.   | 0.0.1 |


#### IdentifyOptionsConfig

| Prop                            | Type                 | Description                                                                                              | Default            | Since |
| ------------------------------- | -------------------- | -------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`restorePaywallAssignments`** | <code>boolean</code> | Restore paywall assignments from the server. Set to true when switching accounts to restore assignments. | <code>false</code> | 0.0.1 |


#### GetUserIdResult

| Prop         | Type                | Description                                    | Since |
| ------------ | ------------------- | ---------------------------------------------- | ----- |
| **`userId`** | <code>string</code> | The current user ID (identified or anonymous). | 0.0.1 |


#### GetIsLoggedInResult

| Prop             | Type                 | Description                                 | Since |
| ---------------- | -------------------- | ------------------------------------------- | ----- |
| **`isLoggedIn`** | <code>boolean</code> | Whether the user is logged in (identified). | 0.0.1 |


#### SetUserAttributesOptions

| Prop             | Type                                   | Description                                                                                                       | Since |
| ---------------- | -------------------------------------- | ----------------------------------------------------------------------------------------------------------------- | ----- |
| **`attributes`** | <code>Record&lt;string, any&gt;</code> | User attributes as key-value pairs. Keys starting with `$` are reserved. Set values to null to remove attributes. | 0.0.1 |


#### HandleDeepLinkOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`url`** | <code>string</code> | The deep link URL to handle. | 0.0.1 |


#### GetSubscriptionStatusResult

| Prop         | Type                                                              | Description                      | Since |
| ------------ | ----------------------------------------------------------------- | -------------------------------- | ----- |
| **`status`** | <code><a href="#subscriptionstatus">SubscriptionStatus</a></code> | The current subscription status. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### SuperwallEventInfo

| Prop       | Type                                                              | Description                               | Since |
| ---------- | ----------------------------------------------------------------- | ----------------------------------------- | ----- |
| **`type`** | <code><a href="#superwalleventtype">SuperwallEventType</a></code> | The type of Superwall event.              | 0.0.1 |
| **`data`** | <code>Record&lt;string, any&gt;</code>                            | Additional event data as key-value pairs. | 0.0.1 |


#### SubscriptionStatusDidChangeEvent

| Prop         | Type                                                              | Description                  | Since |
| ------------ | ----------------------------------------------------------------- | ---------------------------- | ----- |
| **`status`** | <code><a href="#subscriptionstatus">SubscriptionStatus</a></code> | The new subscription status. | 0.0.1 |


#### PaywallPresentedEvent

| Prop              | Type                                                | Description                              | Since |
| ----------------- | --------------------------------------------------- | ---------------------------------------- | ----- |
| **`paywallInfo`** | <code><a href="#paywallinfo">PaywallInfo</a></code> | Information about the presented paywall. | 0.0.1 |


#### PaywallInfo

| Prop             | Type                                              | Description                                                                             | Since |
| ---------------- | ------------------------------------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`id`**         | <code>string</code>                               | The unique identifier for the paywall.                                                  | 0.0.1 |
| **`placement`**  | <code>string</code>                               | The placement identifier.                                                               | 0.0.1 |
| **`experiment`** | <code><a href="#experiment">Experiment</a></code> | <a href="#experiment">Experiment</a> information if the paywall is part of an A/B test. | 0.0.1 |
| **`data`**       | <code>Record&lt;string, any&gt;</code>            | Additional paywall metadata.                                                            | 0.0.1 |


#### PaywallWillDismissEvent

| Prop              | Type                                                | Description                                    | Since |
| ----------------- | --------------------------------------------------- | ---------------------------------------------- | ----- |
| **`paywallInfo`** | <code><a href="#paywallinfo">PaywallInfo</a></code> | Information about the paywall being dismissed. | 0.0.1 |


#### PaywallDismissedEvent

| Prop              | Type                                                | Description                              | Since |
| ----------------- | --------------------------------------------------- | ---------------------------------------- | ----- |
| **`paywallInfo`** | <code><a href="#paywallinfo">PaywallInfo</a></code> | Information about the dismissed paywall. | 0.0.1 |


#### CustomPaywallActionEvent

| Prop       | Type                | Description                    | Since |
| ---------- | ------------------- | ------------------------------ | ----- |
| **`name`** | <code>string</code> | The name of the custom action. | 0.0.1 |


### Enums


#### LogLevel

| Members     | Value                | Description            | Since |
| ----------- | -------------------- | ---------------------- | ----- |
| **`Debug`** | <code>'DEBUG'</code> | Debug level logging.   | 0.0.1 |
| **`Info`**  | <code>'INFO'</code>  | Info level logging.    | 0.0.1 |
| **`Warn`**  | <code>'WARN'</code>  | Warning level logging. | 0.0.1 |
| **`Error`** | <code>'ERROR'</code> | Error level logging.   | 0.0.1 |


#### LogScope

| Members         | Value                    | Description                 | Since |
| --------------- | ------------------------ | --------------------------- | ----- |
| **`All`**       | <code>'ALL'</code>       | All log scopes.             | 0.0.1 |
| **`Analytics`** | <code>'ANALYTICS'</code> | Analytics related logs.     | 0.0.1 |
| **`Config`**    | <code>'CONFIG'</code>    | Configuration related logs. | 0.0.1 |
| **`Events`**    | <code>'EVENTS'</code>    | Event tracking logs.        | 0.0.1 |
| **`Paywalls`**  | <code>'PAYWALLS'</code>  | Paywall related logs.       | 0.0.1 |
| **`Purchases`** | <code>'PURCHASES'</code> | Purchase related logs.      | 0.0.1 |


#### NetworkEnvironment

| Members         | Value                    | Description                          | Since |
| --------------- | ------------------------ | ------------------------------------ | ----- |
| **`Release`**   | <code>'RELEASE'</code>   | Production environment.              | 0.0.1 |
| **`Developer`** | <code>'DEVELOPER'</code> | Development environment for testing. | 0.0.1 |


#### PaywallResult

| Members         | Value                    | Description                 | Since |
| --------------- | ------------------------ | --------------------------- | ----- |
| **`Purchased`** | <code>'PURCHASED'</code> | User completed a purchase.  | 0.0.1 |
| **`Cancelled`** | <code>'CANCELLED'</code> | User cancelled the paywall. | 0.0.1 |
| **`Restored`**  | <code>'RESTORED'</code>  | User restored purchases.    | 0.0.1 |


#### PresentationResultType

| Members                   | Value                                | Description                             | Since |
| ------------------------- | ------------------------------------ | --------------------------------------- | ----- |
| **`PlacementNotFound`**   | <code>'PLACEMENT_NOT_FOUND'</code>   | The placement was not found.            | 0.0.1 |
| **`NoAudienceMatch`**     | <code>'NO_AUDIENCE_MATCH'</code>     | No audience matched for this placement. | 0.0.1 |
| **`Paywall`**             | <code>'PAYWALL'</code>               | A paywall would be presented.           | 0.0.1 |
| **`Holdout`**             | <code>'HOLDOUT'</code>               | User is in a holdout group.             | 0.0.1 |
| **`PaywallNotAvailable`** | <code>'PAYWALL_NOT_AVAILABLE'</code> | Paywall is not available.               | 0.0.1 |


#### SubscriptionStatus

| Members        | Value                   | Description                                | Since |
| -------------- | ----------------------- | ------------------------------------------ | ----- |
| **`Unknown`**  | <code>'UNKNOWN'</code>  | Subscription status is unknown.            | 0.0.1 |
| **`Active`**   | <code>'ACTIVE'</code>   | User has an active subscription.           | 0.0.1 |
| **`Inactive`** | <code>'INACTIVE'</code> | User does not have an active subscription. | 0.0.1 |


#### SuperwallEventType

| Members                           | Value                                         | Description                  | Since |
| --------------------------------- | --------------------------------------------- | ---------------------------- | ----- |
| **`FirstSeen`**                   | <code>'FIRST_SEEN'</code>                     | First time user is seen.     | 0.0.1 |
| **`AppOpen`**                     | <code>'APP_OPEN'</code>                       | App was opened.              | 0.0.1 |
| **`AppLaunch`**                   | <code>'APP_LAUNCH'</code>                     | App was launched.            | 0.0.1 |
| **`AppClose`**                    | <code>'APP_CLOSE'</code>                      | App was closed.              | 0.0.1 |
| **`SessionStart`**                | <code>'SESSION_START'</code>                  | Session started.             | 0.0.1 |
| **`DeepLink`**                    | <code>'DEEP_LINK'</code>                      | Deep link was opened.        | 0.0.1 |
| **`TriggerFire`**                 | <code>'TRIGGER_FIRE'</code>                   | Trigger was fired.           | 0.0.1 |
| **`PaywallOpen`**                 | <code>'PAYWALL_OPEN'</code>                   | Paywall was opened.          | 0.0.1 |
| **`PaywallClose`**                | <code>'PAYWALL_CLOSE'</code>                  | Paywall was closed.          | 0.0.1 |
| **`PaywallDecline`**              | <code>'PAYWALL_DECLINE'</code>                | Paywall was declined.        | 0.0.1 |
| **`TransactionStart`**            | <code>'TRANSACTION_START'</code>              | Transaction started.         | 0.0.1 |
| **`TransactionComplete`**         | <code>'TRANSACTION_COMPLETE'</code>           | Transaction completed.       | 0.0.1 |
| **`TransactionFail`**             | <code>'TRANSACTION_FAIL'</code>               | Transaction failed.          | 0.0.1 |
| **`TransactionAbandon`**          | <code>'TRANSACTION_ABANDON'</code>            | Transaction was abandoned.   | 0.0.1 |
| **`TransactionRestore`**          | <code>'TRANSACTION_RESTORE'</code>            | Transaction restore.         | 0.0.1 |
| **`TransactionTimeout`**          | <code>'TRANSACTION_TIMEOUT'</code>            | Transaction timeout.         | 0.0.1 |
| **`SubscriptionStart`**           | <code>'SUBSCRIPTION_START'</code>             | Subscription started.        | 0.0.1 |
| **`FreeTrialStart`**              | <code>'FREE_TRIAL_START'</code>               | Free trial started.          | 0.0.1 |
| **`SubscriptionStatusDidChange`** | <code>'SUBSCRIPTION_STATUS_DID_CHANGE'</code> | Subscription status changed. | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/superwall/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/superwall/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Nest 22, Inc. or any of their affiliates or subsidiaries.
