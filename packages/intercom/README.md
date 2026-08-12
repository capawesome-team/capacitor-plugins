# Capacitor Intercom Plugin

Unofficial Capacitor plugin for [Intercom](https://www.intercom.com/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Intercom plugin is a modern integration of the Intercom SDKs for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 💬 **Messenger**: Present the Intercom Messenger and its Home, Messages, Help Center, and Tickets spaces.
- 👤 **Identity**: Log in identified or unidentified users with the modern login APIs, wired to the native result callbacks.
- 🔐 **Identity Verification**: Verify the user's identity with a user hash (HMAC) or a JSON Web Token (JWT).
- 🧩 **User Attributes**: Update the user's name, email, phone, custom attributes, and companies.
- 📚 **Content**: Present articles, carousels, surveys, conversations, and help center collections.
- 🔢 **Unread Count**: Read the unread conversation count and listen for changes.
- 🔔 **Push Notifications**: Compose cleanly with Firebase Cloud Messaging (Android) and APNs (iOS).
- 🌐 **Typed Web SDK**: Uses the official `@intercom/messenger-js-sdk` package on the web.
- 🤝 **Compatibility**: Looking for a different chat SDK? Check out the [Crisp](https://capawesome.io/docs/sdks/capacitor/crisp/) plugin.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Intercom plugin is typically used wherever you want to offer live chat and customer support inside your app, for example:

- **Customer support**: Let users chat with your support team directly from within the app.
- **User identification**: Attach the signed-in user's identity and attributes to every conversation.
- **Self-service**: Point users to articles, surveys, and help center collections without leaving the app.
- **Engagement**: Track events and show the unread conversation count in your own UI.
- **Re-engagement**: Notify users about new replies via push notifications.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-intercom` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-intercom
npx cap sync
```

This plugin requires an [Intercom](https://www.intercom.com/) account. You can find your **App ID** and the platform-specific **API keys** in the Intercom dashboard under **Settings → Installation**.

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$intercomSdkVersion` version of `io.intercom.android:intercom-sdk-base` (default: `18.4.0`)

This plugin depends on `intercom-sdk-base` (not the full `intercom-sdk` artifact) on purpose. The full artifact ships its own `FirebaseMessagingService` and automatically integrates Firebase Cloud Messaging, which conflicts with apps that manage push notifications themselves (e.g. via [`@capacitor-firebase/messaging`](https://github.com/capawesome-team/capacitor-firebase)). The base artifact leaves push handling to you (see below).

#### Push Notifications

Push notifications on Android are delivered through [Firebase Cloud Messaging (FCM)](https://firebase.google.com/docs/cloud-messaging). Because this plugin uses the base SDK, you forward the push token and incoming messages to Intercom from your JavaScript code. This is what makes the plugin coexist cleanly with [`@capacitor-firebase/messaging`](https://github.com/capawesome-team/capacitor-firebase):

```typescript
import { FirebaseMessaging } from '@capacitor-firebase/messaging';
import { Intercom } from '@capawesome/capacitor-intercom';

// Forward the FCM token to Intercom.
FirebaseMessaging.addListener('tokenReceived', async ({ token }) => {
  await Intercom.sendPushTokenToIntercom({ token });
});

// Forward incoming messages to Intercom.
FirebaseMessaging.addListener('notificationReceived', async ({ notification }) => {
  const data = notification.data ?? {};
  const { intercom } = await Intercom.isIntercomPushNotification({ data });
  if (intercom) {
    await Intercom.handlePushNotification({ data });
  }
});
```

### iOS

The Intercom iOS SDK can be integrated via Swift Package Manager (recommended) or CocoaPods.

#### Info.plist

To manage push notifications yourself (and coexist with other push plugins), disable Intercom's automatic push integration by adding the following key to your `ios/App/App/Info.plist` file:

```xml
<key>IntercomAutoIntegratePushNotifications</key>
<false/>
```

#### Push Notifications

Push notifications on iOS are delivered through [Apple Push Notification service (APNs)](https://developer.apple.com/documentation/usernotifications):

1. Enable the **Push Notifications** capability for your app target in Xcode.
2. Register for remote notifications (e.g. via [`@capacitor-firebase/messaging`](https://github.com/capawesome-team/capacitor-firebase) or `@capacitor/push-notifications`).
3. Forward the APNs device token to Intercom as a hexadecimal string:

```typescript
import { PushNotifications } from '@capacitor/push-notifications';
import { Intercom } from '@capawesome/capacitor-intercom';

PushNotifications.addListener('registration', async ({ value }) => {
  // `value` is the hexadecimal APNs device token on iOS.
  await Intercom.sendPushTokenToIntercom({ token: value });
});
```

You can then check and handle incoming notifications the same way as on Android using `isIntercomPushNotification(...)` and `handlePushNotification(...)`.

### Web

The web implementation loads the Intercom Messenger from Intercom's CDN at runtime. This requires an active network connection. If your app enforces a [Content Security Policy (CSP)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP), make sure to allow the Intercom domains (e.g. `https://widget.intercom.io`, `https://js.intercomcdn.com`, and `wss://*.intercom.io`).

> [!NOTE]
> On the web, the Intercom launcher is visible by default after `initialize(...)`, whereas on Android and iOS it is hidden until you present the Messenger. Use `setLauncherVisible({ visible: false })` on the web if you want to match the mobile behavior.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Initialize the plugin

Call `initialize(...)` once before all other methods:

```typescript
import { Intercom } from '@capawesome/capacitor-intercom';

const initialize = async () => {
  await Intercom.initialize({
    appId: 'YOUR_APP_ID',
    androidApiKey: 'YOUR_ANDROID_API_KEY',
    iosApiKey: 'YOUR_IOS_API_KEY',
  });
};
```

### Log in a user

Log in an identified user, optionally with identity verification:

```typescript
import { Intercom } from '@capawesome/capacitor-intercom';

const loginUser = async () => {
  await Intercom.setUserHash({ userHash: 'YOUR_HMAC_HASH' });
  await Intercom.loginUser({
    userId: 'jane-doe',
    email: 'jane.doe@example.com',
  });
};
```

### Update the user

Update the attributes of the current user:

```typescript
import { Intercom } from '@capawesome/capacitor-intercom';

const updateUser = async () => {
  await Intercom.updateUser({
    name: 'Jane Doe',
    customAttributes: { plan: 'pro' },
    companies: [{ id: 'capawesome', name: 'Capawesome', plan: 'enterprise' }],
  });
};
```

### Present the Messenger

Present the Intercom Messenger or a specific piece of content:

```typescript
import { Intercom } from '@capawesome/capacitor-intercom';

const present = async () => {
  await Intercom.present({ space: 'home' });
};

const presentArticle = async () => {
  await Intercom.presentContent({ type: 'article', id: '123456' });
};
```

### Listen for events

Listen for the unread conversation count:

```typescript
import { Intercom } from '@capawesome/capacitor-intercom';

const addListeners = async () => {
  await Intercom.addListener('unreadConversationCountChange', ({ count }) => {
    console.log('Unread conversations:', count);
  });
};
```

## API

<docgen-index>

* [`getUnreadConversationCount()`](#getunreadconversationcount)
* [`handlePushNotification(...)`](#handlepushnotification)
* [`hide()`](#hide)
* [`initialize(...)`](#initialize)
* [`isIntercomPushNotification(...)`](#isintercompushnotification)
* [`logEvent(...)`](#logevent)
* [`loginUnidentifiedUser()`](#loginunidentifieduser)
* [`loginUser(...)`](#loginuser)
* [`logout()`](#logout)
* [`present(...)`](#present)
* [`presentContent(...)`](#presentcontent)
* [`presentMessageComposer(...)`](#presentmessagecomposer)
* [`sendPushTokenToIntercom(...)`](#sendpushtokentointercom)
* [`setBottomPadding(...)`](#setbottompadding)
* [`setInAppMessagesVisible(...)`](#setinappmessagesvisible)
* [`setLauncherVisible(...)`](#setlaunchervisible)
* [`setUserHash(...)`](#setuserhash)
* [`setUserJwt(...)`](#setuserjwt)
* [`updateUser(...)`](#updateuser)
* [`addListener('messengerHidden', ...)`](#addlistenermessengerhidden-)
* [`addListener('messengerShown', ...)`](#addlistenermessengershown-)
* [`addListener('unreadConversationCountChange', ...)`](#addlistenerunreadconversationcountchange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getUnreadConversationCount()

```typescript
getUnreadConversationCount() => Promise<GetUnreadConversationCountResult>
```

Get the number of unread conversations for the current user.

**Returns:** <code>Promise&lt;<a href="#getunreadconversationcountresult">GetUnreadConversationCountResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### handlePushNotification(...)

```typescript
handlePushNotification(options: HandlePushNotificationOptions) => Promise<void>
```

Handle an incoming push notification that belongs to Intercom.

Use `isIntercomPushNotification(...)` to check whether the notification
belongs to Intercom before calling this method.

Only available on Android and iOS.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#handlepushnotificationoptions">HandlePushNotificationOptions</a></code> |

**Since:** 0.1.0

--------------------


### hide()

```typescript
hide() => Promise<void>
```

Hide any currently presented Intercom UI (e.g. the Messenger).

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the Intercom SDK with your app ID and API key.

This method must be called before any other method.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### isIntercomPushNotification(...)

```typescript
isIntercomPushNotification(options: IsIntercomPushNotificationOptions) => Promise<IsIntercomPushNotificationResult>
```

Check whether an incoming push notification belongs to Intercom.

Only available on Android and iOS.

| Param         | Type                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isintercompushnotificationoptions">IsIntercomPushNotificationOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isintercompushnotificationresult">IsIntercomPushNotificationResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### logEvent(...)

```typescript
logEvent(options: LogEventOptions) => Promise<void>
```

Log an event with an optional set of metadata.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#logeventoptions">LogEventOptions</a></code> |

**Since:** 0.1.0

--------------------


### loginUnidentifiedUser()

```typescript
loginUnidentifiedUser() => Promise<void>
```

Log in an unidentified (anonymous) user.

**Since:** 0.1.0

--------------------


### loginUser(...)

```typescript
loginUser(options: LoginUserOptions) => Promise<void>
```

Log in an identified user with a user ID and/or an email address.

At least one of `userId` or `email` must be provided.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#loginuseroptions">LoginUserOptions</a></code> |

**Since:** 0.1.0

--------------------


### logout()

```typescript
logout() => Promise<void>
```

Log out the current user and clear the local Intercom data.

**Since:** 0.1.0

--------------------


### present(...)

```typescript
present(options?: PresentOptions | undefined) => Promise<void>
```

Present the Intercom Messenger with the given space.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#presentoptions">PresentOptions</a></code> |

**Since:** 0.1.0

--------------------


### presentContent(...)

```typescript
presentContent(options: PresentContentOptions) => Promise<void>
```

Present a specific piece of Intercom content (e.g. an article, carousel,
survey, or conversation).

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#presentcontentoptions">PresentContentOptions</a></code> |

**Since:** 0.1.0

--------------------


### presentMessageComposer(...)

```typescript
presentMessageComposer(options?: PresentMessageComposerOptions | undefined) => Promise<void>
```

Present the Intercom message composer, optionally pre-filled with an
initial message.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#presentmessagecomposeroptions">PresentMessageComposerOptions</a></code> |

**Since:** 0.1.0

--------------------


### sendPushTokenToIntercom(...)

```typescript
sendPushTokenToIntercom(options: SendPushTokenToIntercomOptions) => Promise<void>
```

Forward a push notification token to Intercom.

On Android, pass the Firebase Cloud Messaging (FCM) token. On iOS, pass
the hexadecimal APNs device token.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#sendpushtokentointercomoptions">SendPushTokenToIntercomOptions</a></code> |

**Since:** 0.1.0

--------------------


### setBottomPadding(...)

```typescript
setBottomPadding(options: SetBottomPaddingOptions) => Promise<void>
```

Set the bottom padding of the Intercom UI (in-app messages and launcher).

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setbottompaddingoptions">SetBottomPaddingOptions</a></code> |

**Since:** 0.1.0

--------------------


### setInAppMessagesVisible(...)

```typescript
setInAppMessagesVisible(options: SetInAppMessagesVisibleOptions) => Promise<void>
```

Set whether in-app messages are visible.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setinappmessagesvisibleoptions">SetInAppMessagesVisibleOptions</a></code> |

**Since:** 0.1.0

--------------------


### setLauncherVisible(...)

```typescript
setLauncherVisible(options: SetLauncherVisibleOptions) => Promise<void>
```

Set whether the Intercom launcher is visible.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setlaunchervisibleoptions">SetLauncherVisibleOptions</a></code> |

**Since:** 0.1.0

--------------------


### setUserHash(...)

```typescript
setUserHash(options: SetUserHashOptions) => Promise<void>
```

Set the user hash (HMAC) for identity verification.

This must be called before logging in the user.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setuserhashoptions">SetUserHashOptions</a></code> |

**Since:** 0.1.0

--------------------


### setUserJwt(...)

```typescript
setUserJwt(options: SetUserJwtOptions) => Promise<void>
```

Set the JSON Web Token (JWT) for identity verification.

This must be called before logging in the user.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setuserjwtoptions">SetUserJwtOptions</a></code> |

**Since:** 0.1.0

--------------------


### updateUser(...)

```typescript
updateUser(options: UpdateUserOptions) => Promise<void>
```

Update the attributes of the current user.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#updateuseroptions">UpdateUserOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('messengerHidden', ...)

```typescript
addListener(eventName: 'messengerHidden', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the Intercom Messenger is hidden.

Only available on iOS and Web.

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>'messengerHidden'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('messengerShown', ...)

```typescript
addListener(eventName: 'messengerShown', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the Intercom Messenger is shown.

Only available on iOS and Web.

| Param              | Type                          |
| ------------------ | ----------------------------- |
| **`eventName`**    | <code>'messengerShown'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>    |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('unreadConversationCountChange', ...)

```typescript
addListener(eventName: 'unreadConversationCountChange', listenerFunc: (event: UnreadConversationCountChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the number of unread conversations changes.

| Param              | Type                                                                                                                  |
| ------------------ | --------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'unreadConversationCountChange'</code>                                                                          |
| **`listenerFunc`** | <code>(event: <a href="#unreadconversationcountchangeevent">UnreadConversationCountChangeEvent</a>) =&gt; void</code> |

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


#### GetUnreadConversationCountResult

| Prop        | Type                | Description                         | Since |
| ----------- | ------------------- | ----------------------------------- | ----- |
| **`count`** | <code>number</code> | The number of unread conversations. | 0.1.0 |


#### HandlePushNotificationOptions

| Prop       | Type                                       | Description                        | Since |
| ---------- | ------------------------------------------ | ---------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data of the push notification. | 0.1.0 |


#### InitializeOptions

| Prop                | Type                | Description                                                                      | Since |
| ------------------- | ------------------- | -------------------------------------------------------------------------------- | ----- |
| **`androidApiKey`** | <code>string</code> | The Android API key of your Intercom app. Required to use the plugin on Android. | 0.1.0 |
| **`appId`**         | <code>string</code> | The app ID of your Intercom app.                                                 | 0.1.0 |
| **`iosApiKey`**     | <code>string</code> | The iOS API key of your Intercom app. Required to use the plugin on iOS.         | 0.1.0 |


#### IsIntercomPushNotificationResult

| Prop           | Type                 | Description                                        | Since |
| -------------- | -------------------- | -------------------------------------------------- | ----- |
| **`intercom`** | <code>boolean</code> | Whether the push notification belongs to Intercom. | 0.1.0 |


#### IsIntercomPushNotificationOptions

| Prop       | Type                                       | Description                        | Since |
| ---------- | ------------------------------------------ | ---------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data of the push notification. | 0.1.0 |


#### LogEventOptions

| Prop       | Type                                                           | Description                | Since |
| ---------- | -------------------------------------------------------------- | -------------------------- | ----- |
| **`data`** | <code>Record&lt;string, string \| number \| boolean&gt;</code> | The metadata of the event. | 0.1.0 |
| **`name`** | <code>string</code>                                            | The name of the event.     | 0.1.0 |


#### LoginUserOptions

| Prop         | Type                | Description                        | Since |
| ------------ | ------------------- | ---------------------------------- | ----- |
| **`email`**  | <code>string</code> | The email address of the user.     | 0.1.0 |
| **`userId`** | <code>string</code> | The unique identifier of the user. | 0.1.0 |


#### PresentOptions

| Prop        | Type                                                    | Description           | Default             | Since |
| ----------- | ------------------------------------------------------- | --------------------- | ------------------- | ----- |
| **`space`** | <code><a href="#intercomspace">IntercomSpace</a></code> | The space to present. | <code>'home'</code> | 0.1.0 |


#### PresentContentOptions

| Prop       | Type                                                                | Description                                                                                                                   | Since |
| ---------- | ------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**   | <code>string</code>                                                 | The identifier of the content to present. Required for the `article`, `carousel`, `survey`, and `conversation` content types. | 0.1.0 |
| **`ids`**  | <code>string[]</code>                                               | The identifiers of the content to present. Required for the `help-center-collections` content type.                           | 0.1.0 |
| **`type`** | <code><a href="#intercomcontenttype">IntercomContentType</a></code> | The type of content to present.                                                                                               | 0.1.0 |


#### PresentMessageComposerOptions

| Prop                 | Type                | Description                                | Since |
| -------------------- | ------------------- | ------------------------------------------ | ----- |
| **`initialMessage`** | <code>string</code> | The message to pre-fill the composer with. | 0.1.0 |


#### SendPushTokenToIntercomOptions

| Prop        | Type                | Description                                                                                                                                   | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`token`** | <code>string</code> | The push notification token. On Android, this is the Firebase Cloud Messaging (FCM) token. On iOS, this is the hexadecimal APNs device token. | 0.1.0 |


#### SetBottomPaddingOptions

| Prop          | Type                | Description                | Since |
| ------------- | ------------------- | -------------------------- | ----- |
| **`padding`** | <code>number</code> | The bottom padding to set. | 0.1.0 |


#### SetInAppMessagesVisibleOptions

| Prop          | Type                 | Description                                | Since |
| ------------- | -------------------- | ------------------------------------------ | ----- |
| **`visible`** | <code>boolean</code> | Whether in-app messages should be visible. | 0.1.0 |


#### SetLauncherVisibleOptions

| Prop          | Type                 | Description                             | Since |
| ------------- | -------------------- | --------------------------------------- | ----- |
| **`visible`** | <code>boolean</code> | Whether the launcher should be visible. | 0.1.0 |


#### SetUserHashOptions

| Prop           | Type                | Description                                                                                                                                                         | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`userHash`** | <code>string</code> | The user hash (HMAC) for identity verification. The hash must be generated on your backend using your Intercom secret key. Never expose the secret key in your app. | 0.1.0 |


#### SetUserJwtOptions

| Prop      | Type                | Description                                                                                                                                                              | Since |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`jwt`** | <code>string</code> | The JSON Web Token (JWT) for identity verification. The token must be generated on your backend using your Intercom secret key. Never expose the secret key in your app. | 0.1.0 |


#### UpdateUserOptions

| Prop                         | Type                                                           | Description                                                 | Since |
| ---------------------------- | -------------------------------------------------------------- | ----------------------------------------------------------- | ----- |
| **`companies`**              | <code>UpdateUserCompany[]</code>                               | The companies the user belongs to.                          | 0.1.0 |
| **`customAttributes`**       | <code>Record&lt;string, string \| number \| boolean&gt;</code> | The custom attributes of the user.                          | 0.1.0 |
| **`email`**                  | <code>string</code>                                            | The email address of the user.                              | 0.1.0 |
| **`languageOverride`**       | <code>string</code>                                            | The preferred language of the user as an ISO 639-1 code.    | 0.1.0 |
| **`name`**                   | <code>string</code>                                            | The name of the user.                                       | 0.1.0 |
| **`phone`**                  | <code>string</code>                                            | The phone number of the user.                               | 0.1.0 |
| **`signedUpAt`**             | <code>number</code>                                            | The date the user signed up as a Unix timestamp in seconds. | 0.1.0 |
| **`unsubscribedFromEmails`** | <code>boolean</code>                                           | Whether the user is unsubscribed from emails.               | 0.1.0 |
| **`userId`**                 | <code>string</code>                                            | The unique identifier of the user.                          | 0.1.0 |


#### UpdateUserCompany

| Prop                   | Type                                                           | Description                                                      | Since |
| ---------------------- | -------------------------------------------------------------- | ---------------------------------------------------------------- | ----- |
| **`createdAt`**        | <code>number</code>                                            | The date the company was created as a Unix timestamp in seconds. | 0.1.0 |
| **`customAttributes`** | <code>Record&lt;string, string \| number \| boolean&gt;</code> | The custom attributes of the company.                            | 0.1.0 |
| **`id`**               | <code>string</code>                                            | The unique identifier of the company.                            | 0.1.0 |
| **`monthlySpend`**     | <code>number</code>                                            | The monthly spend of the company.                                | 0.1.0 |
| **`name`**             | <code>string</code>                                            | The name of the company.                                         | 0.1.0 |
| **`plan`**             | <code>string</code>                                            | The plan of the company.                                         | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### UnreadConversationCountChangeEvent

| Prop        | Type                | Description                         | Since |
| ----------- | ------------------- | ----------------------------------- | ----- |
| **`count`** | <code>number</code> | The number of unread conversations. | 0.1.0 |


### Type Aliases


#### IntercomSpace

A space of the Intercom Messenger.

<code>'home' | 'messages' | 'help-center' | 'tickets'</code>


#### IntercomContentType

A type of Intercom content that can be presented.

<code>'article' | 'carousel' | 'conversation' | 'help-center-collections' | 'survey'</code>

</docgen-api>

## Migration

If you are migrating from [`@capacitor-community/intercom`](https://github.com/capacitor-community/intercom) or another Intercom plugin, note that this plugin uses the **modern** Intercom API names throughout. The deprecated `register*` login methods are intentionally not exposed. The following table maps common legacy method names to their modern equivalents:

| Legacy method              | This plugin                          |
| -------------------------- | ------------------------------------ |
| `registerIdentifiedUser`   | `loginUser`                          |
| `registerUnidentifiedUser` | `loginUnidentifiedUser`              |
| `logout`                   | `logout`                             |
| `displayMessenger`         | `present`                            |
| `displayMessageComposer`   | `presentMessageComposer`             |
| `displayArticle`           | `presentContent`                     |
| `displayCarousel`          | `presentContent`                     |
| `displayHelpCenter`        | `present({ space: 'help-center' })`  |
| `hideMessenger`            | `hide`                               |
| `unreadConversationCount`  | `getUnreadConversationCount`         |
| `setLauncherVisibility`    | `setLauncherVisible`                 |
| `setInAppMessageVisibility`| `setInAppMessagesVisible`            |
| `sendPushTokenToIntercom`  | `sendPushTokenToIntercom`            |

## Platform Support

Not every Intercom SDK feature is available on all platforms. The following table lists the notable per-platform differences of the plugin's API:

| Method / Event                                    | Android | iOS | Web |
| ------------------------------------------------- | :-----: | :-: | :-: |
| `handlePushNotification(...)`                      |   ✅    | ✅  | ❌  |
| `isIntercomPushNotification(...)`                  |   ✅    | ✅  | ❌  |
| `presentContent(...)` (`carousel`)                 |   ✅    | ✅  | ❌  |
| `presentContent(...)` (`help-center-collections`)  |   ✅    | ✅  | ❌  |
| `sendPushTokenToIntercom(...)`                     |   ✅    | ✅  | ❌  |
| `setBottomPadding(...)`                            |   ✅    | ✅  | ❌  |
| `messengerShown` / `messengerHidden` events        |   ❌    | ✅  | ✅  |

Additional notes:

- On the web, `getUnreadConversationCount(...)` returns the last value received from the change event, since the web SDK exposes the count only through a callback.
- The `messengerShown` and `messengerHidden` events are not available on Android because the Intercom Android SDK does not expose a window visibility hook.
- `setBottomPadding(...)` uses pixels on Android and points on iOS, matching the respective native SDK.

## Licensing

The Intercom Android and iOS SDKs are licensed under the Apache License 2.0 and are distributed via Maven Central, CocoaPods, and Swift Package Manager. The web SDK is licensed under the MIT license. This plugin only declares these SDKs as dependencies and does not bundle or modify them. Using the plugin requires an active Intercom account. The MIT license of this plugin covers the wrapper code only, not the Intercom SDKs.

## FAQ

### Do I need an Intercom account to use this plugin?

Yes. This plugin wraps the official Intercom SDKs, which require an active [Intercom](https://www.intercom.com/) account, an app ID, and platform-specific API keys.

### How is this plugin different from other similar plugins?

This plugin uses Intercom's modern login APIs (`loginUnidentifiedUser(...)` and `loginUser(...)`). On Android it depends on the base SDK, so it coexists cleanly with your own Firebase Cloud Messaging or APNs setup and lets you forward push notifications to Intercom yourself. It also ships a fully typed web implementation, cross-platform push notification helpers, and is backed by dedicated support.

### Why does this plugin use `intercom-sdk-base` instead of `intercom-sdk` on Android?

The full `intercom-sdk` artifact automatically integrates Firebase Cloud Messaging by registering its own `FirebaseMessagingService`. This conflicts with apps that manage push notifications themselves (e.g. via `@capacitor-firebase/messaging`). The base artifact avoids this conflict and lets you forward push notifications to Intercom yourself.

### How does identity verification work?

Generate a user hash (HMAC) or a JSON Web Token (JWT) on your backend using your Intercom secret key, then pass it via `setUserHash(...)` or `setUserJwt(...)` **before** logging in the user. Never expose your secret key in your app.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Crisp](https://capawesome.io/docs/sdks/capacitor/crisp/): Unofficial Capacitor plugin for the Crisp live chat and customer support platform.
- [Formbricks](https://capawesome.io/docs/sdks/capacitor/formbricks/): Unofficial Capacitor plugin for Formbricks to run in-app surveys.
- [PostHog](https://capawesome.io/docs/sdks/capacitor/posthog/): Unofficial Capacitor plugin for the PostHog product analytics platform.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/intercom/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/intercom/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Intercom Inc. or any of its affiliates or subsidiaries. "Intercom" is a trademark of Intercom Inc.
