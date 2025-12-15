# @capawesome/capacitor-realtimekit

Unofficial Capacitor plugin for using the [RealtimeKit SDK](https://docs.realtime.cloudflare.com/).[^1]

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.2.x          | >=8.x.x           | Active support |
| 0.1.x          | 7.x.x             | Deprecated     |

## Installation

```bash
npm install @capawesome/capacitor-realtimekit
npx cap sync
```

### Android

This plugin is supported on Android SDK +24.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$realtimekitUiVersion` version of `com.cloudflare.realtimekit:ui-android` (default: `0.3.1`)

### iOS

#### Privacy Descriptions

Add the following keys to the `ios/App/App/Info.plist` file:

```xml
<key>NSBluetoothPeripheralUsageDescription</key>
<string>We will use your Bluetooth to access your Bluetooth headphones.</string>
<key>NSBluetoothAlwaysUsageDescription</key>
<string>We will use your Bluetooth to access your Bluetooth headphones.</string>
<key>NSCameraUsageDescription</key>
<string>For people to see you during meetings, we need access to your camera.</string>
<key>NSMicrophoneUsageDescription</key>
<string>For people to hear you during meetings, we need access to your microphone.</string>
<key>NSPhotoLibraryUsageDescription</key>
<string>For people to share, we need access to your photos.</string>
<key>UIBackgroundModes</key>
    <array>
        <string>audio</string>
        <string>voip</string>
        <string>fetch</string>
        <string>remote-notification</string>
    </array>
```

## Usage

```typescript
import { RealtimeKit } from '@capawesome/capacitor-realtimekit';

const initialize = async () => {
  await RealtimeKit.initialize();
};

const startMeeting = async (options: StartMeetingOptions) => {
  await RealtimeKit.startMeeting(options);
};
```

## API

<docgen-index>

* [`initialize()`](#initialize)
* [`startMeeting(...)`](#startmeeting)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize()

```typescript
initialize() => Promise<void>
```

Initialize the RealtimeKit plugin.

This method must be called before using any other methods in the plugin.

**Since:** 0.0.0

--------------------


### startMeeting(...)

```typescript
startMeeting(options: StartMeetingOptions) => Promise<void>
```

Start a meeting using the built-in UI.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#startmeetingoptions">StartMeetingOptions</a></code> |

**Since:** 0.0.0

--------------------


### Interfaces


#### StartMeetingOptions

| Prop              | Type                 | Description                                     | Default           | Since |
| ----------------- | -------------------- | ----------------------------------------------- | ----------------- | ----- |
| **`authToken`**   | <code>string</code>  | The authentication token for the participant.   |                   | 0.0.0 |
| **`enableAudio`** | <code>boolean</code> | Whether to join the meeting with audio enabled. | <code>true</code> | 0.0.0 |
| **`enableVideo`** | <code>boolean</code> | Whether to join the meeting with video enabled. | <code>true</code> | 0.0.0 |

</docgen-api>

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Cloudflare, Inc. or any of their affiliates or subsidiaries.
