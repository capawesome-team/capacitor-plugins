# @capawesome/capacitor-realtimekit

Unofficial Capacitor plugin for using the RealtimeKit SDK.

## Installation

```bash
npm install @capawesome/capacitor-realtimekit
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidRealtimekitUiVersion` version of `com.cloudflare.realtimekit:ui-android` (default: `0.1.1`)

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

const echo = async () => {
  await RealtimeKit.echo();
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
