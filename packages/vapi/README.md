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
* [`send(...)`](#send)
* [`setMuted(...)`](#setmuted)
* [`setup(...)`](#setup)
* [`start(...)`](#start)
* [`stop()`](#stop)
* [Interfaces](#interfaces)

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


### send(...)

```typescript
send(options: SendOptions) => Promise<void>
```

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#sendoptions">SendOptions</a></code> |

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


### Interfaces


#### IsMutedResult

| Prop        | Type                 |
| ----------- | -------------------- |
| **`muted`** | <code>boolean</code> |


#### SayOptions

| Prop                     | Type                 |
| ------------------------ | -------------------- |
| **`message`**            | <code>string</code>  |
| **`endCallAfterSpoken`** | <code>boolean</code> |


#### SendOptions

| Prop          | Type                                                                   |
| ------------- | ---------------------------------------------------------------------- |
| **`role`**    | <code>'function' \| 'user' \| 'system' \| 'assistant' \| 'tool'</code> |
| **`content`** | <code>string</code>                                                    |


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

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vapi/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vapi/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Superpowered Labs Inc. or any of their affiliates or subsidiaries.
