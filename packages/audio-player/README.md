# @capawesome-team/capacitor-audio-player

Capacitor plugin to play audio with background support.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for audio playback. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌙 **Background Mode**: Play audio even when the app is in the background.
- ⏯️ **Full Control**: Play, pause, resume, stop, seek, and adjust volume.
- 🔂 **Loop Support**: Loop audio playback for continuous sound.
- 🔊 **Volume Control**: Precise volume control from 0-100.
- 🗂️ **Web Assets**: Support for web asset paths alongside file URIs and remote URLs.
- 🤝 **Compatibility**: Compatible with the [Audio Recorder](https://capawesome.io/plugins/audio-recorder/), [Media Session](https://capawesome.io/plugins/media-session/), [Speech Recognition](https://capawesome.io/plugins/speech-recognition/) and [Speech Synthesis](https://capawesome.io/plugins/speech-synthesis/) plugins.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

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
npm install @capawesome-team/capacitor-audio-player
npx cap sync
```

### iOS

#### Capabilities

If you want to play audio in the background, ensure `Background Modes` capability is enabled with `Audio, AirPlay, and Picture in Picture` in your Xcode project.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

## Usage

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';
import { Capacitor } from '@capacitor/core';
import { Filesystem } from '@capacitor/filesystem';

const playFromWebAsset = async () => {
  await AudioPlayer.play({ 
    src: '/assets/audio.mp3', 
    loop: false, 
    volume: 100, 
    position: 0 
  });
};

const playFromNativeFile = async () => {
  const { uri } = await Filesystem.getUri({
    directory: FilesystemDirectory.Documents,
    path: 'audio.mp3',
  });
  await AudioPlayer.play({ uri, loop: false, volume: 100, position: 0 });
};

const playFromBlob = async () => {
  const assetUrl = 'https://www.example.com/audio.mp3';
  const response = await fetch(assetUrl);
  const blob = await response.blob();
  await AudioPlayer.play({ blob, loop: false, volume: 100, position: 0 });
};

const pause = async () => {
  await AudioPlayer.pause();
};

const resume = async () => {
  await AudioPlayer.resume();
};

const stop = async () => {
  await AudioPlayer.stop();
};

const seekTo = async () => {
  await AudioPlayer.seekTo({ position: 30_000 }); // Seek to 30 seconds
};

const setVolume = async () => {
  await AudioPlayer.setVolume({ volume: 50 }); // Set volume to 50%
};

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
* [`setVolume(...)`](#setvolume)
* [`stop()`](#stop)
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


### setVolume(...)

```typescript
setVolume(options: SetVolumeOptions) => Promise<void>
```

Set the volume level for the audio playback.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setvolumeoptions">SetVolumeOptions</a></code> |

**Since:** 0.0.1

--------------------


### stop()

```typescript
stop() => Promise<void>
```

Stop the audio playback.

**Since:** 0.0.1

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

| Prop           | Type                 | Description                                                                                                                                                                                                                                                                 | Since |
| -------------- | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`blob`**     | <code>Blob</code>    | The audio file to play. If both `blob` and `src` are provided, `blob` takes priority. Only available on Web.                                                                                                                                                                | 0.0.1 |
| **`loop`**     | <code>boolean</code> | Whether to loop the audio playback.                                                                                                                                                                                                                                         | 0.0.1 |
| **`position`** | <code>number</code>  | The position to start playback from (in milliseconds).                                                                                                                                                                                                                      | 0.0.1 |
| **`src`**      | <code>string</code>  | The path to the web asset file to play. If both `blob` and `src` are provided, `blob` takes priority. If both `uri` and `src` are provided, `uri` takes priority. On Android, only web assets are supported. On iOS and Web, both web assets and remote URLs are supported. | 0.1.2 |
| **`uri`**      | <code>string</code>  | The URI or path of the audio file to play. If both `uri` and `src` are provided, `uri` takes priority. Only available on Android and iOS.                                                                                                                                   | 0.0.1 |
| **`volume`**   | <code>number</code>  | The volume level to set (0-100).                                                                                                                                                                                                                                            | 0.0.1 |


#### SeekToOptions

| Prop           | Type                | Description                                | Since |
| -------------- | ------------------- | ------------------------------------------ | ----- |
| **`position`** | <code>number</code> | The position to seek to (in milliseconds). | 0.0.1 |


#### SetVolumeOptions

| Prop         | Type                | Description                      | Since |
| ------------ | ------------------- | -------------------------------- | ----- |
| **`volume`** | <code>number</code> | The volume level to set (0-100). | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-player/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-player/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-player/LICENSE).
