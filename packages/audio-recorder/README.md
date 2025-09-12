# @capawesome-team/capacitor-audio-recorder

Capacitor plugin for seamless audio recording using the device's microphone. Supports Android, iOS, and Web with advanced features and high performance.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for audio recording. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- ⏯️ **Full Control**: Start, pause, resume, cancel and stop recording.
- 🚀 **Performance**: Record long audio sessions without any performance issues.
- 🔑 **Permissions**: Check and request microphone permissions.
- 🔊 **Events**: Listen for events like `recordingError`, `recordingPaused` or `recordingStopped`.
- 🌙 **Background Mode**: Record audio even when the app is in the background.
- 🤝 **Compatibility**: Compatible with the [Audio Player](https://capawesome.io/plugins/audio-player/), [Speech Recognition](https://capawesome.io/plugins/speech-recognition/) and [Speech Synthesis](https://capawesome.io/plugins/speech-synthesis/) plugins.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Guides

- [Announcing the Capacitor Audio Recorder Plugin](https://capawesome.io/blog/announcing-the-capacitor-audio-recorder-plugin/)
- [Exploring the Capacitor Audio Recorder API](https://capawesome.io/blog/exploring-the-capacitor-audio-recorder-api/)

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-audio-recorder
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Capabilities

If you want to record audio in the background, ensure `Background Modes` capability is enabled with `Audio, AirPlay, and Picture in Picture` in your Xcode project. See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

#### Privacy Descriptions

Add the `NSMicrophoneUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to the user's contacts:

```xml
<key>NSMicrophoneUsageDescription</key>
<string>We need access to your microphone to record audio.</string>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AudioRecorder, AudioSessionCategoryOption, AudioSessionMode } from '@capawesome-team/capacitor-audio-recorder';
import { NativeAudio } from '@capacitor-community/native-audio';

const cancelRecording = async () => {
  await AudioRecorder.cancelRecording();
};

const getRecordingStatus = async () => {
  const { status } = await AudioRecorder.getRecordingStatus();
  console.log('Recording status:', status);
};

const pauseRecording = async () => {
  await AudioRecorder.pauseRecording();
};

const resumeRecording = async () => {
  await AudioRecorder.resumeRecording();
};

const startRecording = async () => {
  await AudioRecorder.startRecording({
    audioSessionCategoryOptions: [AudioSessionCategoryOption.DuckOthers],
    audioSessionMode: AudioSessionMode.Measurement,
    bitRate: 192000,
    sampleRate: 44100
  });
};

const stopRecording = async () => {
  // Stop recording and get the audio blob or URI
  const { blob, uri } = await AudioRecorder.stopRecording();
  // Play the audio
  if (blob) {
    // Only available on Web
    const audio = new Audio();
    audio.src = URL.createObjectURL(blob);
    audio.play();
  } else if (uri) {
    // Only available on Android and iOS
    await NativeAudio.preload({
      assetId: 'recording',
      assetPath: uri,
      isUrl: true,
    });
    await NativeAudio.play({ assetId: 'recording' });
  }
};

const checkPermissions = async () => {
  const { recordAudio } = await AudioRecorder.checkPermissions();
  console.log('Record audio permission:', recordAudio);
};

const requestPermissions = async () => {
  const { recordAudio } = await AudioRecorder.requestPermissions();
  console.log('Record audio permission:', recordAudio);
};

const addRecordingErrorListener = async () => {
  await AudioRecorder.addListener('recordingError', (event) => {
    console.error('Recording error:', event.message);
  });
};

const addRecordingPausedListener = async () => {
  await AudioRecorder.addListener('recordingPaused', () => {
    console.log('Recording paused');
  });
};

const addRecordingStoppedListener = async () => {
  await AudioRecorder.addListener('recordingStopped', (event) => {
    console.log('Recording stopped:', event.uri);
  });
};
```

## API

<docgen-index>

* [`cancelRecording()`](#cancelrecording)
* [`getRecordingStatus()`](#getrecordingstatus)
* [`pauseRecording()`](#pauserecording)
* [`resumeRecording()`](#resumerecording)
* [`startRecording(...)`](#startrecording)
* [`stopRecording()`](#stoprecording)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('recordingError', ...)`](#addlistenerrecordingerror-)
* [`addListener('recordingPaused', ...)`](#addlistenerrecordingpaused-)
* [`addListener('recordingStopped', ...)`](#addlistenerrecordingstopped-)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### cancelRecording()

```typescript
cancelRecording() => Promise<void>
```

Cancel the recording.

**Since:** 7.0.0

--------------------


### getRecordingStatus()

```typescript
getRecordingStatus() => Promise<GetRecordingStatusResult>
```

Check if the device supports audio recording.

**Returns:** <code>Promise&lt;<a href="#getrecordingstatusresult">GetRecordingStatusResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### pauseRecording()

```typescript
pauseRecording() => Promise<void>
```

Pause the recording.

This method is only available on Android (SDK 24+), iOS and Web.

**Since:** 7.0.0

--------------------


### resumeRecording()

```typescript
resumeRecording() => Promise<void>
```

Resume the recording.

This method is only available on Android (SDK 24+), iOS and Web.

**Since:** 7.0.0

--------------------


### startRecording(...)

```typescript
startRecording(options?: StartRecordingOptions | undefined) => Promise<void>
```

Start recording audio in AAC format.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#startrecordingoptions">StartRecordingOptions</a></code> |

**Since:** 7.0.0

--------------------


### stopRecording()

```typescript
stopRecording() => Promise<StopRecordingResult>
```

Stop recording audio.

**Returns:** <code>Promise&lt;<a href="#stoprecordingresult">StopRecordingResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions for audio recording.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permissions for audio recording.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### addListener('recordingError', ...)

```typescript
addListener(eventName: 'recordingError', listenerFunc: (event: RecordingErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during recording. The recording will be cancelled.

Only available on iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'recordingError'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#recordingerrorevent">RecordingErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.0.0

--------------------


### addListener('recordingPaused', ...)

```typescript
addListener(eventName: 'recordingPaused', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the recording is paused (e.g. when the recording is interrupted by a phone call).

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>'recordingPaused'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.1.0

--------------------


### addListener('recordingStopped', ...)

```typescript
addListener(eventName: 'recordingStopped', listenerFunc: (event: RecordingStoppedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the recording is stopped.

**Note**: This will not be called if the recording is cancelled
or paused or if an error occurs.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'recordingStopped'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#recordingstoppedevent">RecordingStoppedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### Interfaces


#### GetRecordingStatusResult

| Prop         | Type                                                        | Description                   | Default                               | Since |
| ------------ | ----------------------------------------------------------- | ----------------------------- | ------------------------------------- | ----- |
| **`status`** | <code><a href="#recordingstatus">RecordingStatus</a></code> | The current recording status. | <code>RecordingStatus.Inactive</code> | 7.0.0 |


#### StartRecordingOptions

| Prop                              | Type                                                          | Description                                                                              | Default                                   | Since |
| --------------------------------- | ------------------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----------------------------------------- | ----- |
| **`audioSessionCategoryOptions`** | <code>AudioSessionCategoryOption[]</code>                     | The audio session category options for recording. Only available on iOS.                 | <code>['duckOthers']</code>               | 7.5.0 |
| **`audioSessionMode`**            | <code><a href="#audiosessionmode">AudioSessionMode</a></code> | The audio session mode for recording. Only available on iOS.                             | <code>AudioSessionMode.Measurement</code> | 7.4.0 |
| **`bitRate`**                     | <code>number</code>                                           | The audio bitrate in bytes per second. This option is only available on Android and iOS. | <code>192000</code>                       | 7.2.0 |
| **`sampleRate`**                  | <code>number</code>                                           | The audio sample rate in Hz. This option is only available on Android and iOS.           | <code>44100</code>                        | 7.1.0 |


#### StopRecordingResult

| Prop           | Type                | Description                                                       | Since |
| -------------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`blob`**     | <code>Blob</code>   | The recorded audio as a Blob. Only available on Web.              | 7.0.0 |
| **`duration`** | <code>number</code> | The duration of the recorded audio in milliseconds.               | 7.1.0 |
| **`uri`**      | <code>string</code> | The URI of the recorded audio. Only available on Android and iOS. | 7.0.0 |


#### PermissionStatus

| Prop              | Type                                                        | Description                               | Since |
| ----------------- | ----------------------------------------------------------- | ----------------------------------------- | ----- |
| **`recordAudio`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for audio recording. | 7.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### RecordingErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 7.0.0 |


#### RecordingStoppedEvent

| Prop           | Type                | Description                                                       | Since |
| -------------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`blob`**     | <code>Blob</code>   | The recorded audio as a Blob. Only available on Web.              | 7.0.0 |
| **`duration`** | <code>number</code> | The duration of the recorded audio in milliseconds.               | 7.1.0 |
| **`uri`**      | <code>string</code> | The URI of the recorded audio. Only available on Android and iOS. | 7.0.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### RecordingStatus

| Members         | Value                    | Description                | Since |
| --------------- | ------------------------ | -------------------------- | ----- |
| **`Inactive`**  | <code>'INACTIVE'</code>  | The recording is inactive. | 7.0.0 |
| **`Recording`** | <code>'RECORDING'</code> | The recording is active.   | 7.0.0 |
| **`Paused`**    | <code>'PAUSED'</code>    | The recording is paused.   | 7.0.0 |


#### AudioSessionCategoryOption

| Members                                    | Value                                                     | Description                                                                                                                | Since |
| ------------------------------------------ | --------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`AllowAirPlay`**                         | <code>'ALLOW_AIR_PLAY'</code>                             | Option to stream audio from this session to AirPlay devices.                                                               | 7.5.0 |
| **`AllowBluetooth`**                       | <code>'ALLOW_BLUETOOTH'</code>                            | Option to make Bluetooth hands-free devices appears as available input routes.                                             | 7.5.0 |
| **`AllowBluetoothA2DP`**                   | <code>'ALLOW_BLUETOOTH_A2DP'</code>                       | Option to stream audio from this session to Bluetooth devices that support the Advanced Audio Distribution Profile (A2DP). | 7.5.0 |
| **`DefaultToSpeaker`**                     | <code>'DEFAULT_TO_SPEAKER'</code>                         | Option to make audio from this session to default to the built-in speaker instead of the receiver.                         | 7.5.0 |
| **`DuckOthers`**                           | <code>'DUCK_OTHERS'</code>                                | Option to reduce the audio volume of other active sessions when audio from this session is in play.                        | 7.5.0 |
| **`InterruptSpokenAudioAndMixWithOthers`** | <code>'INTERRUPT_SPOKEN_AUDIO_AND_MIX_WITH_OTHERS'</code> | Option to pause spoken audio of other sessions when audio from this session is in play.                                    | 7.5.0 |
| **`MixWithOthers`**                        | <code>'MIX_WITH_OTHERS'</code>                            | Option to mix audio with audio from other active sessions in other apps.                                                   | 7.5.0 |
| **`overrideMutedMicrophoneInterruption`**  | <code>'OVERRIDE_MUTED_MICROPHONE_INTERRUPTION'</code>     | Option that indicates if the system interrupts the audio session when it mutes the built-in microphone.                    | 7.5.0 |


#### AudioSessionMode

| Members              | Value                          | Description                                                                   | Since |
| -------------------- | ------------------------------ | ----------------------------------------------------------------------------- | ----- |
| **`Default`**        | <code>'DEFAULT'</code>         | Default mode that doesn't enable additional audio session features.           | 7.4.0 |
| **`GameChat`**       | <code>'GAME_CHAT'</code>       | Mode for chat communication over VoIP or internet, optimized for low latency. | 7.4.0 |
| **`Measurement`**    | <code>'MEASUREMENT'</code>     | Mode for high-quality measurement recordings with maximum dynamic range.      | 7.4.0 |
| **`SpokenAudio`**    | <code>'SPOKEN_AUDIO'</code>    | Mode for speech recording and transcription with optimized voice processing.  | 7.4.0 |
| **`VideoChat`**      | <code>'VIDEO_CHAT'</code>      | Mode for two-way video chat communications.                                   | 7.4.0 |
| **`VideoRecording`** | <code>'VIDEO_RECORDING'</code> | Mode for recording video content with high-quality audio.                     | 7.4.0 |
| **`VoiceChat`**      | <code>'VOICE_CHAT'</code>      | Mode for voice chat communications.                                           | 7.4.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-recorder/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-recorder/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-recorder/LICENSE).
