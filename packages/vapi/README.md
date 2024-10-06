# @capawesome/capacitor-vapi

Unofficial Capacitor plugin for [Vapi](https://vapi.ai/).[^1]

## Installation

```bash
npm install @capawesome/capacitor-vapi @vapi-ai/web openai
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxCoreKtxVersion` version of `androidx.core:core-ktx` (default: `1.13.1`)

### iOS

Add the `NSMicrophoneUsageDescription`, `NSCameraUsageDescription` and `UIBackgroundModes` keys to the `Info.plist` file (usually `ios/App/App/Info.plist`), which tells the user why your app needs access to the microphone and camera, and that it needs to run in the background:

```xml
<key>NSMicrophoneUsageDescription</key>
<string>The app needs access to the microphone to record audio.</string>
<key>NSCameraUsageDescription</key>
<string>The app needs access to the camera to record video.</string>
<key>UIBackgroundModes</key>
<array>
  <string>voip</string>
  <string>audio</string>
</array>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Vapi } from '@capawesome/capacitor-vapi';

const echo = async () => {
  await Vapi.echo();
};
```

## API

<docgen-index>

* [`isMuted()`](#ismuted)
* [`say(...)`](#say)
* [`setMuted(...)`](#setmuted)
* [`setup(...)`](#setup)
* [`start(...)`](#start)
* [`stop()`](#stop)
* [`addListener('callEnd', ...)`](#addlistenercallend)
* [`addListener('callStart', ...)`](#addlistenercallstart)
* [`addListener('conversationUpdate', ...)`](#addlistenerconversationupdate)
* [`addListener('error', ...)`](#addlistenererror)
* [`addListener('speechUpdate', ...)`](#addlistenerspeechupdate)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isMuted()

```typescript
isMuted() => Promise<IsMutedResult>
```

**Returns:** <code>Promise&lt;<a href="#ismutedresult">IsMutedResult</a>&gt;</code>

--------------------


### say(...)

```typescript
say(options: SayOptions) => Promise<void>
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#sayoptions">SayOptions</a></code> |

--------------------


### setMuted(...)

```typescript
setMuted(options: SetMutedOptions) => Promise<void>
```

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setmutedoptions">SetMutedOptions</a></code> |

--------------------


### setup(...)

```typescript
setup(options: SetupOptions) => Promise<void>
```

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#setupoptions">SetupOptions</a></code> |

--------------------


### start(...)

```typescript
start(options: StartOptions) => Promise<void>
```

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#startoptions">StartOptions</a></code> |

--------------------


### stop()

```typescript
stop() => Promise<void>
```

--------------------


### addListener('callEnd', ...)

```typescript
addListener(eventName: 'callEnd', listenerFunc: CallEndEventListener) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'callEnd'</code>                                                |
| **`listenerFunc`** | <code><a href="#callendeventlistener">CallEndEventListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener('callStart', ...)

```typescript
addListener(eventName: 'callStart', listenerFunc: CallStartEventListener) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'callStart'</code>                                                  |
| **`listenerFunc`** | <code><a href="#callstarteventlistener">CallStartEventListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener('conversationUpdate', ...)

```typescript
addListener(eventName: 'conversationUpdate', listenerFunc: ConversationUpdateEventListener) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'conversationUpdate'</code>                                                           |
| **`listenerFunc`** | <code><a href="#conversationupdateeventlistener">ConversationUpdateEventListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener('error', ...)

```typescript
addListener(eventName: 'error', listenerFunc: ErrorEventListener) => Promise<PluginListenerHandle>
```

| Param              | Type                                                              |
| ------------------ | ----------------------------------------------------------------- |
| **`eventName`**    | <code>'error'</code>                                              |
| **`listenerFunc`** | <code><a href="#erroreventlistener">ErrorEventListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener('speechUpdate', ...)

```typescript
addListener(eventName: 'speechUpdate', listenerFunc: SpeechUpdateEventListener) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'speechUpdate'</code>                                                     |
| **`listenerFunc`** | <code><a href="#speechupdateeventlistener">SpeechUpdateEventListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

--------------------


### Interfaces


#### IsMutedResult

| Prop        | Type                 |
| ----------- | -------------------- |
| **`muted`** | <code>boolean</code> |


#### SayOptions

| Prop                     | Type                 | Description            |
| ------------------------ | -------------------- | ---------------------- |
| **`message`**            | <code>string</code>  |                        |
| **`endCallAfterSpoken`** | <code>boolean</code> | Only available on Web. |


#### SetMutedOptions

| Prop        | Type                 |
| ----------- | -------------------- |
| **`muted`** | <code>boolean</code> |


#### SetupOptions

| Prop         | Type                |
| ------------ | ------------------- |
| **`apiKey`** | <code>string</code> |


#### StartOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`assistantId`** | <code>string</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ConversationUpdateEvent

| Prop           | Type                                                                         |
| -------------- | ---------------------------------------------------------------------------- |
| **`messages`** | <code>{ content: string; role: 'user' \| 'assistant' \| 'system'; }[]</code> |


#### ErrorEvent

Events providing information related to errors in scripts or in files.

| Prop           | Type                |
| -------------- | ------------------- |
| **`colno`**    | <code>number</code> |
| **`error`**    | <code>any</code>    |
| **`filename`** | <code>string</code> |
| **`lineno`**   | <code>number</code> |
| **`message`**  | <code>string</code> |


#### SpeechUpdateEvent

| Prop         | Type                                | Description                        |
| ------------ | ----------------------------------- | ---------------------------------- |
| **`role`**   | <code>'user' \| 'assistant'</code>  | Only available on Android and iOS. |
| **`status`** | <code>'stopped' \| 'started'</code> |                                    |


### Type Aliases


#### CallEndEventListener

<code>(): void</code>


#### CallStartEventListener

<code>(): void</code>


#### ConversationUpdateEventListener

<code>(event: <a href="#conversationupdateevent">ConversationUpdateEvent</a>): void</code>


#### ErrorEventListener

<code>(error: <a href="#errorevent">ErrorEvent</a>): void</code>


#### SpeechUpdateEventListener

<code>(event: <a href="#speechupdateevent">SpeechUpdateEvent</a>): void</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vapi/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vapi/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Superpowered Labs Inc. or any of their affiliates or subsidiaries.
