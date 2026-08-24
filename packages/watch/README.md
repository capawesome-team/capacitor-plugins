# Capacitor Watch Plugin

Capacitor plugin for communicating with Apple Watch and Wear OS apps. Send messages, sync state and queue transfers between your Capacitor app and your native watch app with a single, unified API.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Watch plugin is a communication bridge between your Capacitor app and your native watch app. You build your watch UI natively (SwiftUI on watchOS, whatever you prefer on Wear OS) and use this plugin to exchange data with it. Here are some of the key features:

- 💬 **Messaging**: Send live messages with optional request/reply semantics.
- 🔄 **State Sync**: Share the latest state between phone and watch, even across app restarts.
- 📦 **Queued Transfers**: Queue data for guaranteed delivery, even if the watch is currently not reachable.
- 📡 **Reachability**: Query the connection info and get notified when the reachability changes.
- 🌙 **Background Reception**: Data received while the app is closed is replayed as soon as your listeners are registered. On Android, the data is persisted and even survives an app restart.
- ⌚ **Watch SDKs**: Ships with native SDKs for watchOS (Swift Package) and Wear OS (Kotlin library) for the watch side.
- 🧩 **Zero Boilerplate**: No `AppDelegate` or `MainActivity` modifications required.
- 🤝 **Compatibility**: Works hand in hand with the [Health](https://capawesome.io/docs/sdks/capacitor/health/) and [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Watch plugin is typically used whenever an app wants to extend its experience to the wrist, for example:

- **Fitness and health**: Start and stop workouts from the watch and mirror live stats from the phone.
- **Remote control**: Control media playback, smart home devices or timers from the watch.
- **Glanceable data**: Keep the latest scores, tasks or account balances in sync with the watch app.
- **Quick actions**: Confirm orders, check in or trigger phone-side actions with a tap on the watch.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Guides

- [How to Build a Wear OS App for Your Capacitor App](https://capawesome.io/blog/how-to-build-a-wear-os-app-for-your-capacitor-app/): Add a native Kotlin watch module and exchange messages, state, and transfers with this plugin.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-watch` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-watch
npx cap sync
```

### Android

The plugin registers a `WearableListenerService` in its own manifest, so messages and data from the watch are received even while your app is closed. No manifest changes are required.

This plugin requires Google Play services. On devices without Google Play services, all plugin methods reject as unavailable.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$playServicesWearableVersion` version of `com.google.android.gms:play-services-wearable` (default: `20.0.1`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

No additional steps are required for the phone side. The plugin activates the `WCSession` automatically when it is loaded, so you do **not** need to modify your `AppDelegate`.

This plugin is the phone side of the bridge. To exchange data, you also need a native watch app. The plugin ships with watch-side SDKs for both platforms, so the watch side is just as simple.

### watchOS

The watchOS SDK is a Swift Package located at `node_modules/@capawesome-team/capacitor-watch/sdks/watchos`. It wires up the `WCSession` for you and exposes an `ObservableObject` that you can use directly in SwiftUI.

Follow these steps to add a watch app to your Capacitor project:

1. Open your app's Xcode project (`ios/App/App.xcodeproj`) — or the Xcode workspace (`ios/App/App.xcworkspace`) if your app uses CocoaPods.
2. Select **File > New > Target…**, choose the **watchOS** tab and select the **Watch App for Existing iOS App** template.
   - If no companion app is selectable, make sure you opened the dialog via **File > New > Target…** (not **File > New > Project…**) and that the **Project** dropdown at the bottom of the dialog points to your app's project.
3. Enter a product name (e.g. `watch`) and select **SwiftUI** as interface. The bundle identifier of the watch app must be prefixed with the bundle identifier of your iOS app (e.g. `com.example.app.watchkitapp`), which Xcode derives automatically.
4. Select **File > Add Package Dependencies… > Add Local…** and select the `node_modules/@capawesome-team/capacitor-watch/sdks/watchos` folder. Add the `CapawesomeWatchSDK` product to your **watch app target** (not the iOS app target).
5. Activate the session and start communicating:

```swift
import SwiftUI
import CapawesomeWatchSDK

struct ContentView: View {
    @ObservedObject private var watch = CapawesomeWatch.shared

    var body: some View {
        VStack {
            Text(watch.reachable ? "Reachable" : "Not reachable")
            Button("Send Message") {
                watch.sendMessage(["text": "Hello from the watch!"])
            }
        }
        .onAppear {
            watch.onMessageReceived = { data, reply in
                print("Message received:", data)
                reply?(["text": "Hello back!"])
            }
            watch.activate()
        }
    }
}
```

You can find a complete minimal watch app in the [`example/watchos`](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/watch/example/watchos) folder. When adding these files to your watch app target, enable **Copy items if needed**, select only the watch app as target and do not create a bridging header (the sources are pure Swift).

**Note**: The local package reference is resolved relative to your Xcode project. Make sure `node_modules` is installed before opening the project.

### Wear OS

The Wear OS SDK is a Kotlin library located at `node_modules/@capawesome-team/capacitor-watch/sdks/wearos`. It provides send helpers and a listener service base class for the watch side.

Wear OS communication has two hard requirements imposed by the Google Play services Data Layer:

- The Wear OS module must use the **same `applicationId`** as your phone app.
- Both apps must be signed with the **same signing certificate**.

Follow these steps to add a Wear OS app to your Capacitor project:

1. Create a new module folder `android/wear` for the watch app. You can copy the minimal example from the [`example/wearos`](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/watch/example/wearos) folder as a starting point (make sure to change the `applicationId` to your app's id).
2. Include the watch app module and the Wear OS SDK in your `android/settings.gradle` file:

```groovy
include ':wear'
include ':capawesome-watch-sdk'
project(':capawesome-watch-sdk').projectDir = new File('../node_modules/@capawesome-team/capacitor-watch/sdks/wearos')
```

3. Add the SDK as a dependency to your `android/wear/build.gradle` file:

```groovy
dependencies {
    implementation project(':capawesome-watch-sdk')
}
```

4. Declare the capability that the phone side uses to discover your watch app in `android/wear/src/main/res/values/wear.xml`:

```xml
<resources>
    <string-array name="android_wear_capabilities">
        <item>capawesome_watch</item>
    </string-array>
</resources>
```

5. Create a listener service by extending the `WatchListenerService` base class:

```kotlin
class MyWatchListenerService : WatchListenerService() {
    override fun onMessageReceived(data: JSONObject, reply: ((JSONObject) -> Unit)?) {
        reply?.invoke(JSONObject().put("text", "Hello back!"))
    }

    override fun onStateReceived(data: JSONObject) {}

    override fun onUserInfoReceived(data: JSONObject) {}
}
```

6. Register the service in the manifest of your watch app:

```xml
<service
    android:name=".MyWatchListenerService"
    android:exported="true">
    <intent-filter>
        <action android:name="com.google.android.gms.wearable.MESSAGE_RECEIVED" />
        <action android:name="com.google.android.gms.wearable.DATA_CHANGED" />
        <data
            android:scheme="wear"
            android:host="*"
            android:pathPrefix="/capawesome/watch" />
    </intent-filter>
</service>
```

7. Send data to the phone using the `CapawesomeWatch` class. All methods are `suspend` functions, so they must be called from a coroutine:

```kotlin
val watch = CapawesomeWatch(context)
val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

scope.launch {
    watch.sendMessage(JSONObject().put("text", "Hello from the watch!"))
    val reply = watch.sendMessageForReply(JSONObject().put("text", "Hello from the watch!"))
    watch.updateState(JSONObject().put("counter", 42))
    watch.transferUserInfo(JSONObject().put("sentAt", System.currentTimeMillis()))
}
```

## Configuration

On Android, the plugin discovers your watch app via the capability that the watch app declares (see the Wear OS setup above). You can configure the capability string in your `capacitor.config.ts` file:

```typescript
import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    Watch: {
      capability: 'capawesome_watch',
    },
  },
};

export default config;
```

- `capability` (string): The capability that the Wear OS watch app declares. Only available on Android. Default: `capawesome_watch`.

On iOS, no configuration is required.

## Usage

The plugin exposes three communication channels with different delivery guarantees — see [Communication Channels](#communication-channels) for how they map to the platform APIs.

```typescript
import { Watch } from '@capawesome-team/capacitor-watch';

const getConnectionInfo = async () => {
  const { reachable, paired, watchAppInstalled } = await Watch.getConnectionInfo();
  return { reachable, paired, watchAppInstalled };
};

const sendMessage = async () => {
  await Watch.sendMessage({ data: { text: 'Hello from the phone!' } });
};

const sendMessageWithReply = async () => {
  const { reply } = await Watch.sendMessage({
    data: { text: 'Hello from the phone!' },
    expectsReply: true,
  });
  return reply;
};

const updateState = async () => {
  await Watch.updateState({ data: { counter: 42 } });
};

const transferUserInfo = async () => {
  await Watch.transferUserInfo({ data: { sentAt: Date.now() } });
};

const addListeners = async () => {
  await Watch.addListener('messageReceived', async event => {
    if (event.messageId) {
      await Watch.replyToMessage({
        data: { text: 'Hello back!' },
        messageId: event.messageId,
      });
    }
  });
  await Watch.addListener('reachabilityChange', event => {
    console.log('Reachable:', event.reachable);
  });
  await Watch.addListener('stateReceived', event => {
    console.log('State received:', event.data);
  });
  await Watch.addListener('userInfoReceived', event => {
    console.log('User info received:', event.data);
  });
};
```

## API

<docgen-index>

* [`getConnectionInfo()`](#getconnectioninfo)
* [`getReceivedState()`](#getreceivedstate)
* [`replyToMessage(...)`](#replytomessage)
* [`sendMessage(...)`](#sendmessage)
* [`transferUserInfo(...)`](#transferuserinfo)
* [`updateState(...)`](#updatestate)
* [`addListener('messageReceived', ...)`](#addlistenermessagereceived-)
* [`addListener('reachabilityChange', ...)`](#addlistenerreachabilitychange-)
* [`addListener('stateReceived', ...)`](#addlistenerstatereceived-)
* [`addListener('userInfoReceived', ...)`](#addlisteneruserinforeceived-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getConnectionInfo()

```typescript
getConnectionInfo() => Promise<GetConnectionInfoResult>
```

Get information about the connection to the watch.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getconnectioninforesult">GetConnectionInfoResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getReceivedState()

```typescript
getReceivedState() => Promise<GetReceivedStateResult>
```

Get the last state that was received from the watch.

The state is persisted and can be read at any time,
including after an app restart.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getreceivedstateresult">GetReceivedStateResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### replyToMessage(...)

```typescript
replyToMessage(options: ReplyToMessageOptions) => Promise<void>
```

Reply to a message that was received from the watch.

This is only possible if the `messageReceived` event
provided a `messageId`.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#replytomessageoptions">ReplyToMessageOptions</a></code> |

**Since:** 0.0.1

--------------------


### sendMessage(...)

```typescript
sendMessage(options: SendMessageOptions) => Promise<SendMessageResult>
```

Send a message to the watch for immediate delivery.

The watch must be reachable, otherwise the call is rejected.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#sendmessageoptions">SendMessageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#sendmessageresult">SendMessageResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### transferUserInfo(...)

```typescript
transferUserInfo(options: TransferUserInfoOptions) => Promise<void>
```

Transfer data to the watch in a queue.

In contrast to `updateState(...)`, every transfer is delivered,
even if the watch is currently not reachable.

On iOS, the transfers are delivered in the order they were queued.
On Android, there is no ordering guarantee between transfers.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#transferuserinfooptions">TransferUserInfoOptions</a></code> |

**Since:** 0.0.1

--------------------


### updateState(...)

```typescript
updateState(options: UpdateStateOptions) => Promise<void>
```

Update the state that is delivered to the watch.

Only the latest state is delivered. If the watch is currently not
reachable, the state is delivered as soon as it is reachable again.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#updatestateoptions">UpdateStateOptions</a></code> |

**Since:** 0.0.1

--------------------


### addListener('messageReceived', ...)

```typescript
addListener(eventName: 'messageReceived', listenerFunc: (event: MessageReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a message from the watch is received.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'messageReceived'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#messagereceivedevent">MessageReceivedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('reachabilityChange', ...)

```typescript
addListener(eventName: 'reachabilityChange', listenerFunc: (event: ReachabilityChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the reachability of the watch changes.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'reachabilityChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#reachabilitychangeevent">ReachabilityChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('stateReceived', ...)

```typescript
addListener(eventName: 'stateReceived', listenerFunc: (event: StateReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a state update from the watch is received.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'stateReceived'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#statereceivedevent">StateReceivedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('userInfoReceived', ...)

```typescript
addListener(eventName: 'userInfoReceived', listenerFunc: (event: UserInfoReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a user info transfer from the watch is received.

Only available on Android and iOS.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'userInfoReceived'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#userinforeceivedevent">UserInfoReceivedEvent</a>) =&gt; void</code> |

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


#### GetConnectionInfoResult

| Prop                    | Type                         | Description                                                                                                                                                                                                         | Since |
| ----------------------- | ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`paired`**            | <code>boolean \| null</code> | Whether or not a watch is paired with the device. On Android, this value is always `null` since the platform does not provide this information.                                                                     | 0.0.1 |
| **`reachable`**         | <code>boolean</code>         | Whether or not the watch is currently reachable for immediate message delivery. On Android, this requires that the watch app is installed and has declared the configured capability (default: `capawesome_watch`). | 0.0.1 |
| **`watchAppInstalled`** | <code>boolean \| null</code> | Whether or not the watch app is installed on the watch. On Android, this is derived from the configured capability (default: `capawesome_watch`) that the watch app must declare.                                   | 0.0.1 |


#### GetReceivedStateResult

| Prop       | Type                                               | Description                                                                                  | Since |
| ---------- | -------------------------------------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt; \| null</code> | The last state that was received from the watch or `null` if no state has been received yet. | 0.0.1 |


#### ReplyToMessageOptions

| Prop            | Type                                       | Description                                                                            | Since |
| --------------- | ------------------------------------------ | -------------------------------------------------------------------------------------- | ----- |
| **`data`**      | <code>Record&lt;string, unknown&gt;</code> | The data to send as reply. Must be JSON-serializable. `null` values are not supported. | 0.0.1 |
| **`messageId`** | <code>string</code>                        | The identifier of the message to reply to as provided by the `messageReceived` event.  | 0.0.1 |


#### SendMessageResult

| Prop        | Type                                               | Description                                                                    | Since |
| ----------- | -------------------------------------------------- | ------------------------------------------------------------------------------ | ----- |
| **`reply`** | <code>Record&lt;string, unknown&gt; \| null</code> | The reply that was received from the watch or `null` if no reply was expected. | 0.0.1 |


#### SendMessageOptions

| Prop               | Type                                       | Description                                                                                                                  | Default            | Since |
| ------------------ | ------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`data`**         | <code>Record&lt;string, unknown&gt;</code> | The data to send. Must be JSON-serializable. `null` values are not supported.                                                |                    | 0.0.1 |
| **`expectsReply`** | <code>boolean</code>                       | Whether or not the watch is expected to reply to the message. If `true`, the call resolves as soon as the watch has replied. | <code>false</code> | 0.0.1 |


#### TransferUserInfoOptions

| Prop       | Type                                       | Description                                                                       | Since |
| ---------- | ------------------------------------------ | --------------------------------------------------------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data to transfer. Must be JSON-serializable. `null` values are not supported. | 0.0.1 |


#### UpdateStateOptions

| Prop       | Type                                       | Description                                                                                          | Since |
| ---------- | ------------------------------------------ | ---------------------------------------------------------------------------------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data to deliver as the latest state. Must be JSON-serializable. `null` values are not supported. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### MessageReceivedEvent

| Prop            | Type                                       | Description                                                                                                                        | Since |
| --------------- | ------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`data`**      | <code>Record&lt;string, unknown&gt;</code> | The data that was received.                                                                                                        | 0.0.1 |
| **`messageId`** | <code>string \| null</code>                | The identifier of the message if the watch expects a reply. In this case, you should reply using the `replyToMessage(...)` method. | 0.0.1 |


#### ReachabilityChangeEvent

| Prop            | Type                 | Description                                                                     | Since |
| --------------- | -------------------- | ------------------------------------------------------------------------------- | ----- |
| **`reachable`** | <code>boolean</code> | Whether or not the watch is currently reachable for immediate message delivery. | 0.0.1 |


#### StateReceivedEvent

| Prop       | Type                                       | Description                 | Since |
| ---------- | ------------------------------------------ | --------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data that was received. | 0.0.1 |


#### UserInfoReceivedEvent

| Prop       | Type                                       | Description                 | Since |
| ---------- | ------------------------------------------ | --------------------------- | ----- |
| **`data`** | <code>Record&lt;string, unknown&gt;</code> | The data that was received. | 0.0.1 |

</docgen-api>

## Communication Channels

The plugin exposes the three communication channels of the platforms with honest, platform-true semantics:

| Method                  | Semantics                        | iOS                        | Android                        | Guarantee                                       |
| ----------------------- | -------------------------------- | -------------------------- | ------------------------------ | ----------------------------------------------- |
| `sendMessage(...)`      | Live, interactive, optional reply | `WCSession.sendMessage`    | `MessageClient`                | Requires reachability, not queued               |
| `updateState(...)`      | Latest state wins                | `updateApplicationContext` | `DataItem` (fixed path)        | Replaces undelivered state, survives restarts   |
| `transferUserInfo(...)` | Queued, all delivered            | `transferUserInfo`         | `DataItem` (unique path)       | Queued until delivered, even across relaunches  |

The payload of all channels is limited to about 100 KB by the operating systems.

## Platform Behavior

The two platform stacks behave differently in some aspects. The plugin does not paper over these differences:

| Behavior                       | iOS                                                                            | Android                                                                      |
| ------------------------------ | ------------------------------------------------------------------------------ | ---------------------------------------------------------------------------- |
| Watch → phone `sendMessage`    | Wakes the phone app in the background if it is not running.                     | Starts the plugin's `WearableListenerService`; the event is queued for JS.    |
| Phone → watch `sendMessage`    | Does **not** launch the watch app. The watch app must be running.               | Does **not** launch the watch app. The watch app's listener service is started. |
| `paired` / `watchAppInstalled` | Provided by the system.                                                         | `paired` is always `null`; `watchAppInstalled` is derived from the capability. |
| State persistence              | The latest received state is persisted by the system.                           | The state is persisted and replicated by the Data Layer.                     |
| Identical state updates        | May not be redelivered if the data did not change.                              | May not be redelivered if the data did not change.                           |
| Queued transfers               | Delivered in the order they were queued.                                       | No ordering guarantee. The 100 most recent undelivered transfers are kept.   |
| Payload types                  | Must be property list compatible; `null` values are not supported.              | Any JSON-serializable data.                                                  |

**Note**: A Wear OS watch that is paired to an iPhone is **not** supported. The Google Play services Data Layer only works between an Android phone and a Wear OS watch.

## FAQ

### How is this plugin different from other similar plugins?

It is a single, unified bridge between your Capacitor app and native Apple Watch and Wear OS apps, exposing the three platform channels honestly — live messages with optional replies, latest-wins state sync, and queued transfers for guaranteed delivery — plus reachability info and background reception of data that arrives while your app is closed. It even ships native watch-side SDKs for watchOS and Wear OS and needs no `AppDelegate` or `MainActivity` changes, so you build your watch UI natively and wire up communication with minimal boilerplate. Everything is fully typed, actively maintained against the latest OS and Capacitor versions and backed by dedicated support.

### Do I need to modify my AppDelegate on iOS?

No. The plugin activates the `WCSession` when it is loaded and handles all session delegate callbacks internally, including background deliveries that launch the app.

### What happens to data that arrives while my app is closed?

On Android, the plugin's manifest-registered `WearableListenerService` receives the data even while your app is not running and persists it, so the events are replayed as soon as your listeners are registered, even after an app restart. On iOS, a message from the watch wakes your app in the background and the events are retained in memory until your listeners are registered. Events that have not been consumed are therefore lost on iOS if the app is terminated in the meantime.

### Can my Wear OS watch app communicate with an iPhone app?

No. The Google Play services Data Layer only works between an Android phone and a Wear OS watch. Likewise, an Apple Watch can only communicate with an iPhone. This is a platform limitation, not a plugin limitation.

### Why does `sendMessage(...)` reject with `WATCH_NOT_REACHABLE`?

The message channel is a live channel and requires the watch to be reachable. On Android, also make sure that your watch app declares the `capawesome_watch` capability (see the Wear OS setup). If you need guaranteed delivery, use `transferUserInfo(...)` instead.

### Does the watch app UI come from the plugin?

No, and that is deliberate. You build your watch UI natively (e.g. with SwiftUI or Jetpack Compose) with the full power of the platform, and use the shipped watch-side SDKs to communicate with your Capacitor app.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Health](https://capawesome.io/docs/sdks/capacitor/health/): Read health and fitness data that pairs well with a watch experience.
- [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/): Track the device location in the background.
- [Geofences](https://capawesome.io/docs/sdks/capacitor/geofences/): Monitor geographic regions, even when the app is closed.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/watch/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/watch/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/watch/LICENSE).
