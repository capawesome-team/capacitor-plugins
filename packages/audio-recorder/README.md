# @capawesome-team/capacitor-audio-recorder

Capacitor plugin for audio recording using the device's microphone.

## Features

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- ⏯️ **Full Control**: Start, pause, resume, cancel and stop recording.
- 🔑 **Permissions**: Check and request microphone permissions.
- 🔊 **Events**: Listen for events like `recordingError` or `recordingStopped`.
- 🎚️ **Audio Metrics**: Access the decibels full-scale (dBFS) in real-time.
- 🤝 **Compatibility**: Compatible with the [Speech Recognition](https://capawesome.io/plugins/speech-recognition/), [Speech Synthesis](https://capawesome.io/plugins/speech-synthesis/) and [Native Audio](https://github.com/capacitor-community/native-audio) plugin.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-audio-recorder
npx cap sync
```

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

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
import { AudioRecorder } from '@capawesome-team/capacitor-audio-recorder';
import { Capacitor } from '@capacitor/core';
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
  await AudioRecorder.startRecording();
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
* [`startRecording()`](#startrecording)
* [`stopRecording()`](#stoprecording)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('recordingError', ...)`](#addlistenerrecordingerror-)
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


### startRecording()

```typescript
startRecording() => Promise<void>
```

Start recording audio in AAC format.

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


#### StopRecordingResult

| Prop       | Type                | Description                                                       | Since |
| ---------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`blob`** | <code>Blob</code>   | The recorded audio as a Blob. Only available on Web.              | 7.0.0 |
| **`uri`**  | <code>string</code> | The URI of the recorded audio. Only available on Android and iOS. | 7.0.0 |


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

| Prop       | Type                | Description                                                       | Since |
| ---------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`blob`** | <code>Blob</code>   | The recorded audio as a Blob. Only available on Web.              | 7.0.0 |
| **`uri`**  | <code>string</code> | The URI of the recorded audio. Only available on Android and iOS. | 7.0.0 |


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

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-recorder/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-recorder/LICENSE).
