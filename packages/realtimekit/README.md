# Capacitor RealtimeKit Plugin

Unofficial Capacitor plugin for using the [RealtimeKit SDK](https://docs.realtime.cloudflare.com/).[^1]

## Use Cases

The RealtimeKit plugin is typically used to add real-time meetings to an app, for example:

- **Video meetings**: Start a meeting with the built-in meeting UI without building your own interface.
- **Audio-only calls**: Join meetings with video disabled for voice-only communication.
- **Telehealth and remote support**: Let patients or customers join video sessions authenticated with a participant token.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.2.x          | >=8.x.x           | Active support |
| 0.1.x          | 7.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-realtimekit` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

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

The following examples show how to initialize the plugin and start a meeting.

### Initialize the plugin

Call `initialize()` before using any other method of the plugin:

```typescript
import { RealtimeKit } from '@capawesome/capacitor-realtimekit';

const initialize = async () => {
  await RealtimeKit.initialize();
};
```

### Start a meeting

Start a meeting using the built-in UI. The options let you pass the participant's authentication token and control whether the participant joins with audio and video enabled. Only available on Android and iOS:

```typescript
import { RealtimeKit, StartMeetingOptions } from '@capawesome/capacitor-realtimekit';

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

## FAQ

### Do I have to call the `initialize` method before starting a meeting?

Yes, the `initialize()` method must be called before using any other method of the plugin, including `startMeeting(...)`.

### Can participants join a meeting without camera or microphone?

Yes, set the `enableAudio` or `enableVideo` option of the `startMeeting(...)` method to `false` to join the meeting with the microphone or camera disabled. Both options default to `true`.

### Does this plugin work on the Web?

No, the `startMeeting(...)` method is only available on Android and iOS, since it relies on the native RealtimeKit SDK and its built-in meeting UI.

### What do I need to configure on iOS?

You must add privacy descriptions for Bluetooth, camera, microphone, and photo library access as well as the required background modes to your `Info.plist` file. See the [Installation](#installation) section for the exact keys.

### Which Android versions are supported?

The plugin is supported on Android SDK 24 and newer. It also uses the `$realtimekitUiVersion` project variable to control the version of the `com.cloudflare.realtimekit:ui-android` dependency.

### Is this an official Cloudflare plugin?

No, this is an unofficial plugin. The project is not affiliated with, endorsed by, sponsored by, or approved by Cloudflare, Inc. or any of their affiliates or subsidiaries.

## Related Plugins

- [Audio Session](https://capawesome.io/docs/sdks/capacitor/audio-session/): Configure and observe the iOS audio session.
- [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/): Transcribe speech into text with advanced features like silence detection.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Cloudflare, Inc. or any of their affiliates or subsidiaries.
