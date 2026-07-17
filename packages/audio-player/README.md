# Capacitor Audio Player Plugin

Capacitor plugin to play audio with background support.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Audio Player plugin is one of the most complete audio playback solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌙 **Background Mode**: Play audio even when the app is in the background.
- 🎵 **Audio Focus Management**: Automatically manages audio focus on Android to pause other audio sources during playback.
- ⏯️ **Full Control**: Play, pause, resume, stop, seek, and adjust volume.
- 🔂 **Loop Support**: Loop audio playback for continuous sound.
- 🔊 **Volume Control**: Precise volume control from 0-100.
- ⏩ **Playback Speed**: Adjustable playback rate with pitch preservation.
- 🗂️ **Web Assets**: Support for web asset paths alongside file URIs and remote URLs.
- 🤝 **Compatibility**: Compatible with the [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/), [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/), [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/) and [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Audio Player plugin is typically used whenever an app needs to play audio, for example:

- **Music and podcast playback**: Play remote audio files and keep them playing while the app is in the background.
- **Voice message playback**: Play voice messages recorded with the [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/) plugin in chat or support apps.
- **Sound effects**: Play short sounds from your web assets with precise volume control and looping.
- **Audiobooks and learning apps**: Let users adjust the playback speed and seek to specific positions.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 0.2.x          | 7.x.x             | Deprecated     |

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-audio-player` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-audio-player
npx cap sync
```

### iOS

#### Capabilities

If you want to play audio in the background, ensure `Background Modes` capability is enabled with `Audio, AirPlay, and Picture in Picture` in your Xcode project.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

## Usage

The following examples show how to play audio from web assets, remote URLs, the file system, or a blob, and how to control, seek, adjust the volume of, and inspect the playback.

### Play an audio file from your web assets or a remote URL

Use the `src` option to play a web asset or a remote URL. Both are supported on all platforms:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const playFromWebAsset = async () => {
  await AudioPlayer.play({ 
    src: '/assets/audio.mp3', 
    loop: false, 
    volume: 100, 
    position: 0 
  });
};
```

### Play an audio file from the file system

Use the `uri` option to play a file from the device's file system, for example one retrieved with the Capacitor Filesystem plugin. This option is only available on Android and iOS:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';
import { Filesystem, FilesystemDirectory } from '@capacitor/filesystem';

const playFromNativeFile = async () => {
  const { uri } = await Filesystem.getUri({
    directory: FilesystemDirectory.Documents,
    path: 'audio.mp3',
  });
  await AudioPlayer.play({ uri, loop: false, volume: 100, position: 0 });
};
```

### Play an audio file from a blob

Use the `blob` option to play a `Blob` instance, for example one fetched from a server. This option is only available on Web:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const playFromBlob = async () => {
  const assetUrl = 'https://www.example.com/audio.mp3';
  const response = await fetch(assetUrl);
  const blob = await response.blob();
  await AudioPlayer.play({ blob, loop: false, volume: 100, position: 0 });
};
```

### Pause, resume and stop the playback

Pause the playback and resume it later, or stop it entirely:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const pause = async () => {
  await AudioPlayer.pause();
};

const resume = async () => {
  await AudioPlayer.resume();
};

const stop = async () => {
  await AudioPlayer.stop();
};
```

### Seek to a specific position

Jump to a specific position in the audio playback, given in milliseconds:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const seekTo = async () => {
  await AudioPlayer.seekTo({ position: 30_000 }); // Seek to 30 seconds
};
```

### Adjust the volume

Set the volume level of the current playback session to a value between 0 and 100:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const setVolume = async () => {
  await AudioPlayer.setVolume({ volume: 50 }); // Set volume to 50%
};
```

### Get the current playback state

Retrieve the current position and duration of the playback in milliseconds, and check whether the audio is currently playing:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const getCurrentPosition = async () => {
  const { position } = await AudioPlayer.getCurrentPosition();
  console.log('Current position:', position);
};

const getDuration = async () => {
  const { duration } = await AudioPlayer.getDuration();
  console.log('Duration:', duration);
};

const isPlaying = async () => {
  const { isPlaying } = await AudioPlayer.isPlaying();
  console.log('Is playing:', isPlaying);
};
```

## API

<docgen-index>

* [`getCurrentPosition()`](#getcurrentposition)
* [`getDuration()`](#getduration)
* [`isPlaying()`](#isplaying)
* [`pause()`](#pause)
* [`play(...)`](#play)
* [`resume()`](#resume)
* [`seekTo(...)`](#seekto)
* [`setRate(...)`](#setrate)
* [`setVolume(...)`](#setvolume)
* [`stop(...)`](#stop)
* [`addListener('stop', ...)`](#addlistenerstop-)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getCurrentPosition()

```typescript
getCurrentPosition() => Promise<GetCurrentPositionResult>
```

Get the current position of the audio playback in milliseconds.

**Returns:** <code>Promise&lt;<a href="#getcurrentpositionresult">GetCurrentPositionResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getDuration()

```typescript
getDuration() => Promise<GetDurationResult>
```

Get the duration of the audio playback in milliseconds.

**Returns:** <code>Promise&lt;<a href="#getdurationresult">GetDurationResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isPlaying()

```typescript
isPlaying() => Promise<IsPlayingResult>
```

Check whether the audio is currently playing.

**Returns:** <code>Promise&lt;<a href="#isplayingresult">IsPlayingResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### pause()

```typescript
pause() => Promise<void>
```

Pause the audio playback.

**Since:** 0.0.1

--------------------


### play(...)

```typescript
play(options: PlayOptions) => Promise<void>
```

Play the audio playback.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#playoptions">PlayOptions</a></code> |

**Since:** 0.0.1

--------------------


### resume()

```typescript
resume() => Promise<void>
```

Resume the audio playback.

**Since:** 0.0.1

--------------------


### seekTo(...)

```typescript
seekTo(options: SeekToOptions) => Promise<void>
```

Seek to a specific position in the audio playback.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#seektooptions">SeekToOptions</a></code> |

**Since:** 0.0.1

--------------------


### setRate(...)

```typescript
setRate(options: SetRateOptions) => Promise<void>
```

Set the playback rate for the audio playback.

This only affects the current playback session and is not persisted.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#setrateoptions">SetRateOptions</a></code> |

**Since:** 8.2.0

--------------------


### setVolume(...)

```typescript
setVolume(options: SetVolumeOptions) => Promise<void>
```

Set the volume level for the audio playback.

This only affects the current playback session and is not persisted.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setvolumeoptions">SetVolumeOptions</a></code> |

**Since:** 0.0.1

--------------------


### stop(...)

```typescript
stop(options?: StopOptions | undefined) => Promise<void>
```

Stop the audio playback.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#stopoptions">StopOptions</a></code> |

**Since:** 0.0.1

--------------------


### addListener('stop', ...)

```typescript
addListener(eventName: 'stop', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the audio has stopped playing.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'stop'</code>        |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.2.2

--------------------


### Interfaces


#### GetCurrentPositionResult

| Prop           | Type                | Description                                                 | Since |
| -------------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`position`** | <code>number</code> | The current position of the audio playback in milliseconds. | 0.0.1 |


#### GetDurationResult

| Prop           | Type                | Description                                         | Since |
| -------------- | ------------------- | --------------------------------------------------- | ----- |
| **`duration`** | <code>number</code> | The duration of the audio playback in milliseconds. | 0.0.1 |


#### IsPlayingResult

| Prop            | Type                 | Description                             | Since |
| --------------- | -------------------- | --------------------------------------- | ----- |
| **`isPlaying`** | <code>boolean</code> | Whether the audio is currently playing. | 0.0.1 |


#### PlayOptions

| Prop           | Type                 | Description                                                                                                                                                                                                                       | Default          | Since |
| -------------- | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`blob`**     | <code>Blob</code>    | The audio file to play. If both `blob` and `src` are provided, `blob` takes priority. Only available on Web.                                                                                                                      |                  | 0.0.1 |
| **`loop`**     | <code>boolean</code> | Whether to loop the audio playback.                                                                                                                                                                                               |                  | 0.0.1 |
| **`position`** | <code>number</code>  | The position to start playback from (in milliseconds).                                                                                                                                                                            |                  | 0.0.1 |
| **`rate`**     | <code>number</code>  | The playback rate to use. Values between 0.5 and 2.0 are recommended. Other values may not be supported on all devices. Only available on Android (SDK 23+), iOS and Web.                                                         | <code>1.0</code> | 8.2.0 |
| **`src`**      | <code>string</code>  | The path to the web asset file to play. If both `blob` and `src` are provided, `blob` takes priority. If both `uri` and `src` are provided, `uri` takes priority. Both web assets and remote URLs are supported on all platforms. |                  | 0.1.2 |
| **`uri`**      | <code>string</code>  | The URI or path of the audio file to play. If both `uri` and `src` are provided, `uri` takes priority. Only available on Android and iOS.                                                                                         |                  | 0.0.1 |
| **`volume`**   | <code>number</code>  | The volume level to set (0-100).                                                                                                                                                                                                  | <code>100</code> | 0.0.1 |


#### SeekToOptions

| Prop           | Type                | Description                                | Since |
| -------------- | ------------------- | ------------------------------------------ | ----- |
| **`position`** | <code>number</code> | The position to seek to (in milliseconds). | 0.0.1 |


#### SetRateOptions

| Prop       | Type                | Description                                                                                                             | Since |
| ---------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------- | ----- |
| **`rate`** | <code>number</code> | The playback rate to set. Values between 0.5 and 2.0 are recommended. Other values may not be supported on all devices. | 8.2.0 |


#### SetVolumeOptions

| Prop         | Type                | Description                      | Since |
| ------------ | ------------------- | -------------------------------- | ----- |
| **`volume`** | <code>number</code> | The volume level to set (0-100). | 0.0.1 |


#### StopOptions

| Prop                         | Type                 | Description                                                                                                                                                                                                                                                           | Default           | Since |
| ---------------------------- | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`deactivateAudioSession`** | <code>boolean</code> | Whether to deactivate the audio session when stopping playback. Set to `false` if you intend to call `play()` again shortly after stopping, to avoid `CoreMediaErrorDomain -16042` errors on iOS or audio focus issues on Android. Only available on Android and iOS. | <code>true</code> | 8.3.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>

## Troubleshooting

##### `CoreMediaErrorDomain -16042` error on iOS when calling `play()` after `stop()`

When `stop()` is called, the audio session is deactivated by default. If `play()` is called shortly after, `AVAudioSession.setActive(true)` can fail with `CoreMediaErrorDomain -16042`, breaking all subsequent playback. To avoid this, set `deactivateAudioSession` to `false` in the `stop()` options:

```typescript
await AudioPlayer.stop({ deactivateAudioSession: false });
```

## FAQ

### Can I play audio while the app is in the background?

Yes, the plugin supports background playback. On iOS, you need to enable the `Background Modes` capability with `Audio, AirPlay, and Picture in Picture` in your Xcode project, as described in the [Installation](#installation) section.

### Which audio sources can I play?

You can play web assets and remote URLs via the `src` option on all platforms. On Android and iOS, you can also play files from the device's file system via the `uri` option. On Web, you can play `Blob` instances via the `blob` option. See the [Usage](#usage) section for examples.

### How can I change the playback speed?

Use the `rate` option of the `play(...)` method or call `setRate(...)` during playback. Values between 0.5 and 2.0 are recommended, as other values may not be supported on all devices. The playback rate is adjusted with pitch preservation and is available on Android (SDK 23+), iOS and Web.

### Why does playback fail with a CoreMediaErrorDomain -16042 error on iOS?

This can happen when `play()` is called shortly after `stop()`, because the audio session is deactivated by default when stopping. Set the `deactivateAudioSession` option of the `stop(...)` method to `false` if you intend to play audio again shortly after stopping. See the [Troubleshooting](#troubleshooting) section for more details.

### Can I use this plugin together with other audio plugins?

Yes, the plugin is compatible with the [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/), [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/), [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/) and [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugins. For example, you can play back a recording created with the Audio Recorder plugin.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/): Record audio using the device's microphone.
- [Audio Session](https://capawesome.io/docs/sdks/capacitor/audio-session/): Configure and observe the iOS audio session.
- [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/): Interact with media controllers, volume keys and media buttons.
- [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/): Synthesize speech from text with voice selection, pitch, and rate control.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-player/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-player/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-player/LICENSE).
