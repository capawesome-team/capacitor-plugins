# Capacitor Crisp Plugin

Unofficial Capacitor plugin for [Crisp](https://crisp.chat/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Crisp plugin is a comprehensive integration of the Crisp chat SDKs for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 💬 **Chatbox**: Open the Crisp chatbox with a single method call.
- 👤 **User Identity**: Set the user's email, nickname, phone number, avatar, and company.
- 🔐 **Identity Verification**: Verify the user's identity with an HMAC-SHA256 email signature.
- 🧩 **Session Data**: Attach custom data, segments, and events to the session.
- 📚 **Helpdesk**: Open the helpdesk search or a specific helpdesk article.
- 🔔 **Push Notifications**: Interoperate with Firebase Cloud Messaging (Android) and APNs (iOS).
- 📡 **Typed Events**: Listen for chat, message, and session events with typed listeners.
- 🌐 **Typed Web SDK**: Uses the official `crisp-sdk-web` package on the web.
- 🤝 **Compatibility**: Looking for a different chat SDK? Check out the [Intercom](https://capawesome.io/docs/sdks/capacitor/intercom/) plugin.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Crisp plugin is typically used wherever you want to offer live chat and customer support inside your app, for example:

- **Customer support**: Let users chat with your support team directly from within the app.
- **User identification**: Attach the signed-in user's email, name, and company to every conversation.
- **Self-service**: Point users to helpdesk articles without leaving the app.
- **Contextual data**: Enrich conversations with session data, segments, and events based on user behavior.
- **Re-engagement**: Notify users about new operator replies via push notifications.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-crisp` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-crisp
npx cap sync
```

This plugin requires a [Crisp](https://crisp.chat/) account. You can find your **Website ID** in the Crisp dashboard under **Settings → Website Settings → Setup & Integrations**.

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$crispSdkVersion` version of `im.crisp:crisp-sdk` (default: `2.0.22`)
- `$firebaseMessagingVersion` version of `com.google.firebase:firebase-messaging` (default: `24.1.1`)

#### Minimum SDK Version

The Crisp Android SDK requires a minimum SDK version of `24`. Make sure the `minSdkVersion` in your app's `variables.gradle` file is set to `24` or higher.

#### Multidex

The Crisp Android SDK requires [multidex](https://developer.android.com/build/multidex) to be enabled. Enable it in your app's `android/app/build.gradle` file:

```groovy
android {
  defaultConfig {
    multiDexEnabled true
  }
}
```

#### FileProvider

The Crisp Android SDK ships its own `FileProvider` (used for chat attachments) with the authority `${applicationId}.im.crisp.client.uploadfileprovider`. This authority is merged automatically and does not conflict with your app's own `FileProvider`, so no additional configuration is required.

#### Push Notifications

Push notifications on Android are delivered through [Firebase Cloud Messaging (FCM)](https://firebase.google.com/docs/cloud-messaging). There are two ways to integrate them:

**Option A — Crisp's own notification service (zero-config)**

If your app does not use its own `FirebaseMessagingService`, add the following service to the `application` tag of your `android/app/src/main/AndroidManifest.xml` file:

```xml
<service
  android:name="im.crisp.client.external.notification.CrispNotificationService"
  android:exported="false">
  <intent-filter>
    <action android:name="com.google.firebase.MESSAGING_EVENT" />
  </intent-filter>
</service>
```

**Option B — Share an existing `FirebaseMessagingService`**

If your app already receives push notifications (e.g. via [`@capacitor-firebase/messaging`](https://github.com/capawesome-team/capacitor-firebase)), forward incoming messages to Crisp from your JavaScript code:

```typescript
import { FirebaseMessaging } from '@capacitor-firebase/messaging';
import { Crisp } from '@capawesome/capacitor-crisp';

FirebaseMessaging.addListener('notificationReceived', async ({ notification }) => {
  const data = notification.data ?? {};
  const { crisp } = await Crisp.isCrispPushNotification({ data });
  if (crisp) {
    await Crisp.handlePushNotification({ data });
  }
});
```

You can enable or disable Crisp push notifications at runtime with `setNotificationsEnabled(...)`.

### iOS

The Crisp iOS SDK can be integrated via Swift Package Manager (recommended) or CocoaPods.

> [!NOTE]
> Crisp will stop publishing new CocoaPods releases in September 2026. We recommend using Swift Package Manager. If your app still uses CocoaPods, plan to migrate to Swift Package Manager before then.

#### Info.plist

The Crisp chatbox lets users send image and file attachments. Add the following keys to your `ios/App/App/Info.plist` file so users can attach photos:

```xml
<key>NSCameraUsageDescription</key>
<string>The app needs access to your camera to send photos in the chat.</string>
<key>NSPhotoLibraryAddUsageDescription</key>
<string>The app needs access to your photo library to send photos in the chat.</string>
```

#### Push Notifications

Push notifications on iOS are delivered through [Apple Push Notification service (APNs)](https://developer.apple.com/documentation/usernotifications):

1. Enable the **Push Notifications** capability for your app target in Xcode.
2. Register for remote notifications (e.g. via [`@capacitor-firebase/messaging`](https://github.com/capawesome-team/capacitor-firebase) or `@capacitor/push-notifications`).

The plugin automatically forwards the APNs device token to Crisp once your app has registered for remote notifications, so no additional token handling is required.

Use `setShouldPromptForNotificationPermission(...)` to control whether the Crisp SDK prompts the user for the notification permission.

### Web

The web implementation loads the Crisp chatbox from Crisp's CDN at runtime. This requires an active network connection. If your app enforces a [Content Security Policy (CSP)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP), make sure to allow the Crisp domains (e.g. `client.crisp.chat`, `*.crisp.chat`, and `wss://*.relay.crisp.chat`).

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Configure the plugin

Call `configure(...)` once before all other methods:

```typescript
import { Crisp } from '@capawesome/capacitor-crisp';

const configure = async () => {
  await Crisp.configure({
    websiteId: 'YOUR_WEBSITE_ID',
  });
};
```

### Open the chat

Open the Crisp chatbox:

```typescript
import { Crisp } from '@capawesome/capacitor-crisp';

const openChat = async () => {
  await Crisp.openChat();
};
```

### Set the user

Set the information of the current user, optionally with identity verification:

```typescript
import { Crisp } from '@capawesome/capacitor-crisp';

const setUser = async () => {
  await Crisp.setUser({
    email: 'jane.doe@example.com',
    emailSignature: 'YOUR_HMAC_SIGNATURE',
    nickname: 'Jane Doe',
  });
};
```

### Attach session data

Attach custom data, segments, and events to the current session:

```typescript
import { Crisp, SessionEventColor } from '@capawesome/capacitor-crisp';

const attachSessionData = async () => {
  await Crisp.setSessionString({ key: 'plan', value: 'pro' });
  await Crisp.setSessionSegment({ segment: 'checkout' });
  await Crisp.pushSessionEvent({ name: 'purchase', color: SessionEventColor.Green });
};
```

### Listen for events

Listen for chat, message, and session events:

```typescript
import { Crisp } from '@capawesome/capacitor-crisp';

const addListeners = async () => {
  await Crisp.addListener('sessionLoaded', ({ sessionId }) => {
    console.log('Session loaded:', sessionId);
  });
  await Crisp.addListener('messageReceived', ({ content }) => {
    console.log('Message received:', content);
  });
};
```

## API

<docgen-index>

* [`configure(...)`](#configure)
* [`handlePushNotification(...)`](#handlepushnotification)
* [`isCrispPushNotification(...)`](#iscrisppushnotification)
* [`openChat()`](#openchat)
* [`openHelpdeskArticle(...)`](#openhelpdeskarticle)
* [`pushSessionEvent(...)`](#pushsessionevent)
* [`resetSession()`](#resetsession)
* [`searchHelpdesk()`](#searchhelpdesk)
* [`setCompany(...)`](#setcompany)
* [`setNotificationsEnabled(...)`](#setnotificationsenabled)
* [`setSessionBool(...)`](#setsessionbool)
* [`setSessionInt(...)`](#setsessionint)
* [`setSessionSegment(...)`](#setsessionsegment)
* [`setSessionString(...)`](#setsessionstring)
* [`setShouldPromptForNotificationPermission(...)`](#setshouldpromptfornotificationpermission)
* [`setTokenId(...)`](#settokenid)
* [`setUser(...)`](#setuser)
* [`addListener('chatClosed', ...)`](#addlistenerchatclosed-)
* [`addListener('chatOpened', ...)`](#addlistenerchatopened-)
* [`addListener('messageReceived', ...)`](#addlistenermessagereceived-)
* [`addListener('messageSent', ...)`](#addlistenermessagesent-)
* [`addListener('sessionLoaded', ...)`](#addlistenersessionloaded-)
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

Configure the plugin with your Crisp website ID.

This method must be called before any other method.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#configureoptions">ConfigureOptions</a></code> |

**Since:** 0.1.0

--------------------


### handlePushNotification(...)

```typescript
handlePushNotification(options: HandlePushNotificationOptions) => Promise<void>
```

Handle an incoming push notification that belongs to Crisp.

Use `isCrispPushNotification(...)` to check whether the notification
belongs to Crisp before calling this method.

Only available on Android.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#handlepushnotificationoptions">HandlePushNotificationOptions</a></code> |

**Since:** 0.1.0

--------------------


### isCrispPushNotification(...)

```typescript
isCrispPushNotification(options: IsCrispPushNotificationOptions) => Promise<IsCrispPushNotificationResult>
```

Check whether an incoming push notification belongs to Crisp.

Only available on Android.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#iscrisppushnotificationoptions">IsCrispPushNotificationOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#iscrisppushnotificationresult">IsCrispPushNotificationResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### openChat()

```typescript
openChat() => Promise<void>
```

Open the chat.

**Since:** 0.1.0

--------------------


### openHelpdeskArticle(...)

```typescript
openHelpdeskArticle(options: OpenHelpdeskArticleOptions) => Promise<void>
```

Open a specific helpdesk article.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#openhelpdeskarticleoptions">OpenHelpdeskArticleOptions</a></code> |

**Since:** 0.1.0

--------------------


### pushSessionEvent(...)

```typescript
pushSessionEvent(options: PushSessionEventOptions) => Promise<void>
```

Push a session event to the current session.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#pushsessioneventoptions">PushSessionEventOptions</a></code> |

**Since:** 0.1.0

--------------------


### resetSession()

```typescript
resetSession() => Promise<void>
```

Reset the current session.

This clears the current session and starts a new one on the next
interaction.

**Since:** 0.1.0

--------------------


### searchHelpdesk()

```typescript
searchHelpdesk() => Promise<void>
```

Open the helpdesk with the search view.

**Since:** 0.1.0

--------------------


### setCompany(...)

```typescript
setCompany(options: SetCompanyOptions) => Promise<void>
```

Set the company of the current user.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setcompanyoptions">SetCompanyOptions</a></code> |

**Since:** 0.1.0

--------------------


### setNotificationsEnabled(...)

```typescript
setNotificationsEnabled(options: SetNotificationsEnabledOptions) => Promise<void>
```

Enable or disable push notifications.

Only available on Android.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnotificationsenabledoptions">SetNotificationsEnabledOptions</a></code> |

**Since:** 0.1.0

--------------------


### setSessionBool(...)

```typescript
setSessionBool(options: SetSessionBoolOptions) => Promise<void>
```

Set a boolean value for the given key in the current session.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessionbooloptions">SetSessionBoolOptions</a></code> |

**Since:** 0.1.0

--------------------


### setSessionInt(...)

```typescript
setSessionInt(options: SetSessionIntOptions) => Promise<void>
```

Set an integer value for the given key in the current session.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessionintoptions">SetSessionIntOptions</a></code> |

**Since:** 0.1.0

--------------------


### setSessionSegment(...)

```typescript
setSessionSegment(options: SetSessionSegmentOptions) => Promise<void>
```

Set the segment of the current session.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessionsegmentoptions">SetSessionSegmentOptions</a></code> |

**Since:** 0.1.0

--------------------


### setSessionString(...)

```typescript
setSessionString(options: SetSessionStringOptions) => Promise<void>
```

Set a string value for the given key in the current session.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessionstringoptions">SetSessionStringOptions</a></code> |

**Since:** 0.1.0

--------------------


### setShouldPromptForNotificationPermission(...)

```typescript
setShouldPromptForNotificationPermission(options: SetShouldPromptForNotificationPermissionOptions) => Promise<void>
```

Set whether the user should be prompted for the notification permission.

Only available on iOS.

| Param         | Type                                                                                                                        |
| ------------- | --------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setshouldpromptfornotificationpermissionoptions">SetShouldPromptForNotificationPermissionOptions</a></code> |

**Since:** 0.1.0

--------------------


### setTokenId(...)

```typescript
setTokenId(options: SetTokenIdOptions) => Promise<void>
```

Set the token ID of the current user.

The token ID is used to restore a session across devices and logins.
You may also set it directly via `configure(...)`.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#settokenidoptions">SetTokenIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### setUser(...)

```typescript
setUser(options: SetUserOptions) => Promise<void>
```

Set the information of the current user.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#setuseroptions">SetUserOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('chatClosed', ...)

```typescript
addListener(eventName: 'chatClosed', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the chat is closed.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'chatClosed'</code>  |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('chatOpened', ...)

```typescript
addListener(eventName: 'chatOpened', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the chat is opened.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'chatOpened'</code>  |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('messageReceived', ...)

```typescript
addListener(eventName: 'messageReceived', listenerFunc: (event: MessageReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a message is received from the operator.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'messageReceived'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#messagereceivedevent">MessageReceivedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('messageSent', ...)

```typescript
addListener(eventName: 'messageSent', listenerFunc: (event: MessageSentEvent) => void) => Promise<PluginListenerHandle>
```

Called when a message is sent by the user.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'messageSent'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#messagesentevent">MessageSentEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('sessionLoaded', ...)

```typescript
addListener(eventName: 'sessionLoaded', listenerFunc: (event: SessionLoadedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the session is loaded.

This is also the recommended way to get the session ID.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'sessionLoaded'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#sessionloadedevent">SessionLoadedEvent</a>) =&gt; void</code> |

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


#### ConfigureOptions

| Prop            | Type                | Description                           | Since |
| --------------- | ------------------- | ------------------------------------- | ----- |
| **`tokenId`**   | <code>string</code> | The token ID of the current user.     | 0.1.0 |
| **`websiteId`** | <code>string</code> | The website ID of your Crisp website. | 0.1.0 |


#### HandlePushNotificationOptions

| Prop       | Type                                       | Description                        | Since |
| ---------- | ------------------------------------------ | ---------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data of the push notification. | 0.1.0 |


#### IsCrispPushNotificationResult

| Prop        | Type                 | Description                                     | Since |
| ----------- | -------------------- | ----------------------------------------------- | ----- |
| **`crisp`** | <code>boolean</code> | Whether the push notification belongs to Crisp. | 0.1.0 |


#### IsCrispPushNotificationOptions

| Prop       | Type                                       | Description                        | Since |
| ---------- | ------------------------------------------ | ---------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data of the push notification. | 0.1.0 |


#### OpenHelpdeskArticleOptions

| Prop           | Type                | Description                   | Since |
| -------------- | ------------------- | ----------------------------- | ----- |
| **`category`** | <code>string</code> | The category of the article.  | 0.1.0 |
| **`id`**       | <code>string</code> | The ID (slug) of the article. | 0.1.0 |
| **`locale`**   | <code>string</code> | The locale of the article.    | 0.1.0 |
| **`title`**    | <code>string</code> | The title of the article.     | 0.1.0 |


#### PushSessionEventOptions

| Prop        | Type                                                            | Description             | Default                             | Since |
| ----------- | --------------------------------------------------------------- | ----------------------- | ----------------------------------- | ----- |
| **`color`** | <code><a href="#sessioneventcolor">SessionEventColor</a></code> | The color of the event. | <code>SessionEventColor.Blue</code> | 0.1.0 |
| **`name`**  | <code>string</code>                                             | The name of the event.  |                                     | 0.1.0 |


#### SetCompanyOptions

| Prop              | Type                                                                    | Description                                            | Since |
| ----------------- | ----------------------------------------------------------------------- | ------------------------------------------------------ | ----- |
| **`description`** | <code>string</code>                                                     | The description of the company.                        | 0.1.0 |
| **`employment`**  | <code><a href="#setcompanyemployment">SetCompanyEmployment</a></code>   | The employment information of the user at the company. | 0.1.0 |
| **`geolocation`** | <code><a href="#setcompanygeolocation">SetCompanyGeolocation</a></code> | The geolocation of the company.                        | 0.1.0 |
| **`name`**        | <code>string</code>                                                     | The name of the company.                               | 0.1.0 |
| **`url`**         | <code>string</code>                                                     | The URL of the company.                                | 0.1.0 |


#### SetCompanyEmployment

| Prop        | Type                | Description                               | Since |
| ----------- | ------------------- | ----------------------------------------- | ----- |
| **`role`**  | <code>string</code> | The role of the user at the company.      | 0.1.0 |
| **`title`** | <code>string</code> | The job title of the user at the company. | 0.1.0 |


#### SetCompanyGeolocation

| Prop          | Type                | Description                 | Since |
| ------------- | ------------------- | --------------------------- | ----- |
| **`city`**    | <code>string</code> | The city of the company.    | 0.1.0 |
| **`country`** | <code>string</code> | The country of the company. | 0.1.0 |


#### SetNotificationsEnabledOptions

| Prop          | Type                 | Description                                   | Since |
| ------------- | -------------------- | --------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether push notifications should be enabled. | 0.1.0 |


#### SetSessionBoolOptions

| Prop        | Type                 | Description                    | Since |
| ----------- | -------------------- | ------------------------------ | ----- |
| **`key`**   | <code>string</code>  | The key of the session data.   | 0.1.0 |
| **`value`** | <code>boolean</code> | The value of the session data. | 0.1.0 |


#### SetSessionIntOptions

| Prop        | Type                | Description                    | Since |
| ----------- | ------------------- | ------------------------------ | ----- |
| **`key`**   | <code>string</code> | The key of the session data.   | 0.1.0 |
| **`value`** | <code>number</code> | The value of the session data. | 0.1.0 |


#### SetSessionSegmentOptions

| Prop          | Type                | Description                 | Since |
| ------------- | ------------------- | --------------------------- | ----- |
| **`segment`** | <code>string</code> | The segment of the session. | 0.1.0 |


#### SetSessionStringOptions

| Prop        | Type                | Description                    | Since |
| ----------- | ------------------- | ------------------------------ | ----- |
| **`key`**   | <code>string</code> | The key of the session data.   | 0.1.0 |
| **`value`** | <code>string</code> | The value of the session data. | 0.1.0 |


#### SetShouldPromptForNotificationPermissionOptions

| Prop          | Type                 | Description                                                          | Since |
| ------------- | -------------------- | -------------------------------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether the user should be prompted for the notification permission. | 0.1.0 |


#### SetTokenIdOptions

| Prop          | Type                | Description                       | Since |
| ------------- | ------------------- | --------------------------------- | ----- |
| **`tokenId`** | <code>string</code> | The token ID of the current user. | 0.1.0 |


#### SetUserOptions

| Prop                 | Type                | Description                                                                                                                                                                                            | Since |
| -------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`avatarUrl`**      | <code>string</code> | The URL of the user's avatar.                                                                                                                                                                          | 0.1.0 |
| **`email`**          | <code>string</code> | The email address of the user.                                                                                                                                                                         | 0.1.0 |
| **`emailSignature`** | <code>string</code> | The HMAC-SHA256 signature of the user's email address for identity verification. The signature must be generated on your backend using your Crisp secret key. Never expose the secret key in your app. | 0.1.0 |
| **`nickname`**       | <code>string</code> | The nickname of the user.                                                                                                                                                                              | 0.1.0 |
| **`phone`**          | <code>string</code> | The phone number of the user.                                                                                                                                                                          | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### MessageReceivedEvent

| Prop          | Type                        | Description                                                                                                        | Since |
| ------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------------ | ----- |
| **`content`** | <code>string \| null</code> | The text content of the message. This is `null` if the message has no text content (e.g. a file or audio message). | 0.1.0 |


#### MessageSentEvent

| Prop          | Type                        | Description                                                                                                        | Since |
| ------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------------ | ----- |
| **`content`** | <code>string \| null</code> | The text content of the message. This is `null` if the message has no text content (e.g. a file or audio message). | 0.1.0 |


#### SessionLoadedEvent

| Prop            | Type                | Description                   | Since |
| --------------- | ------------------- | ----------------------------- | ----- |
| **`sessionId`** | <code>string</code> | The ID of the loaded session. | 0.1.0 |


### Enums


#### SessionEventColor

| Members      | Value                 | Since |
| ------------ | --------------------- | ----- |
| **`Black`**  | <code>'BLACK'</code>  | 0.1.0 |
| **`Blue`**   | <code>'BLUE'</code>   | 0.1.0 |
| **`Brown`**  | <code>'BROWN'</code>  | 0.1.0 |
| **`Green`**  | <code>'GREEN'</code>  | 0.1.0 |
| **`Grey`**   | <code>'GREY'</code>   | 0.1.0 |
| **`Orange`** | <code>'ORANGE'</code> | 0.1.0 |
| **`Pink`**   | <code>'PINK'</code>   | 0.1.0 |
| **`Purple`** | <code>'PURPLE'</code> | 0.1.0 |
| **`Red`**    | <code>'RED'</code>    | 0.1.0 |
| **`Yellow`** | <code>'YELLOW'</code> | 0.1.0 |

</docgen-api>

## Platform Support

Not every Crisp SDK feature is available on all platforms. The following table lists the per-platform differences of the plugin's API:

| Method                                       | Android | iOS | Web |
| -------------------------------------------- | :-----: | :-: | :-: |
| `setSessionBool(...)`                        |   ✅    | ✅  | ✅  |
| `isCrispPushNotification(...)`               |   ✅    | ❌  | ❌  |
| `handlePushNotification(...)`                |   ✅    | ❌  | ❌  |
| `setNotificationsEnabled(...)`               |   ✅    | ❌  | ❌  |
| `setShouldPromptForNotificationPermission(...)` |   ❌    | ✅  | ❌  |

On iOS, the Crisp SDK detects and handles its own push notifications internally once the APNs device token has been forwarded, so the JavaScript `isCrispPushNotification(...)` and `handlePushNotification(...)` methods are not available there.

The following features of the Crisp SDKs are intentionally not part of this plugin's initial release: unread message count, bot scenarios, runtime locale override, and audio/video calls. [Open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) if you need any of them.

## Licensing

The Crisp Android and iOS SDKs are proprietary, closed-source binaries provided by Crisp IM SAS. This plugin only declares them as dependencies (via Maven, CocoaPods, and Swift Package Manager) and does not bundle or modify them. Using the plugin requires an active Crisp account and website ID. The MIT license of this plugin covers the wrapper code only, not the Crisp SDKs.

## FAQ

### Do I need a Crisp account to use this plugin?

Yes. This plugin wraps the official Crisp SDKs, which require an active [Crisp](https://crisp.chat/) account and a website ID.

### How is this plugin different from other similar plugins?

This plugin exposes the Crisp SDKs through a fully typed API, including a strongly typed listener for each event and dedicated helpdesk methods. Identity verification is built in through email-signature HMAC, and push notifications are handled entirely through Crisp's public API. It ships a typed web implementation alongside Android and iOS, supports Swift Package Manager ahead of the CocoaPods sunset, and stays unopinionated by never forcing an automatic show or hide of the chatbox. It is actively maintained and backed by dedicated support.

### How do I get the session ID?

Listen for the `sessionLoaded` event. The event payload contains the `sessionId`. There is no synchronous getter, because the session is loaded asynchronously.

### How does identity verification work?

Generate an HMAC-SHA256 signature of the user's email address on your backend using your Crisp secret key, then pass it as the `emailSignature` option of `setUser(...)`. Never expose your secret key in your app.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Formbricks](https://capawesome.io/docs/sdks/capacitor/formbricks/): Unofficial Capacitor plugin for Formbricks to run in-app surveys.
- [Intercom](https://capawesome.io/docs/sdks/capacitor/intercom/): Unofficial Capacitor plugin for the Intercom live chat and customer support platform.
- [PostHog](https://capawesome.io/docs/sdks/capacitor/posthog/): Unofficial Capacitor plugin for the PostHog product analytics platform.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/crisp/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/crisp/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Crisp IM SAS or any of its affiliates or subsidiaries. "Crisp" is a trademark of Crisp IM SAS.
