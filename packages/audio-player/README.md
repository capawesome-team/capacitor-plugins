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
- 📋 **Playlist Mode**: Play multiple tracks sequentially with native track advancement, even in the background.
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

### Android

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$androidxMedia3ExoPlayerVersion` version of `androidx.media3:media3-exoplayer` (default: `1.6.1`)

### iOS

#### Capabilities

If you want to play audio in the background, ensure `Background Modes` capability is enabled with `Audio, AirPlay, and Picture in Picture` in your Xcode project.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

## Usage

The following examples show how to play audio from web assets, remote URLs, the file system, or a blob, how to play playlists, and how to control, seek, adjust the volume of, and inspect the playback.

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

### Play a playlist

Use the `tracks` option to play multiple tracks sequentially. The next track starts automatically when the current one ends, even in the background. Use the `startIndex` option to start playback from a specific track:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const playPlaylist = async () => {
  await AudioPlayer.play({
    tracks: [
      { src: '/assets/track1.mp3' },
      { src: '/assets/track2.mp3' },
      { src: '/assets/track3.mp3' },
    ],
    startIndex: 0,
  });
};

const listenForTrackChanges = async () => {
  await AudioPlayer.addListener('trackChange', (event) => {
    console.log('Track changed to index:', event.index);
  });
};
```

### Navigate within a playlist

Skip to the next or previous track, jump to a specific track, or retrieve the index of the current track:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const skipToNextTrack = async () => {
  await AudioPlayer.skipToNextTrack();
};

const skipToPreviousTrack = async () => {
  await AudioPlayer.skipToPreviousTrack();
};

const jumpToTrack = async () => {
  await AudioPlayer.seekTo({ index: 2, position: 0 });
};

const getCurrentTrackIndex = async () => {
  const { index } = await AudioPlayer.getCurrentTrackIndex();
  console.log('Current track index:', index);
};
```

### Modify the playlist

Add tracks to the playlist or remove tracks from it while it is playing:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const addTracks = async () => {
  await AudioPlayer.addTracks({
    tracks: [{ src: '/assets/track4.mp3' }],
  });
};

const removeTrack = async () => {
  await AudioPlayer.removeTrack({ index: 0 });
};
```

### Set the repeat mode

Repeat the current track or the entire playlist:

```typescript
import { AudioPlayer, RepeatMode } from '@capawesome-team/capacitor-audio-player';

const setRepeatMode = async () => {
  await AudioPlayer.setRepeatMode({ mode: RepeatMode.All });
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

* [`addTracks(...)`](#addtracks)
* [`getCurrentPosition()`](#getcurrentposition)
* [`getCurrentTrackIndex()`](#getcurrenttrackindex)
* [`getDuration()`](#getduration)
* [`isPlaying()`](#isplaying)
* [`pause()`](#pause)
* [`play(...)`](#play)
* [`removeTrack(...)`](#removetrack)
* [`resume()`](#resume)
* [`seekTo(...)`](#seekto)
* [`setRate(...)`](#setrate)
* [`setRepeatMode(...)`](#setrepeatmode)
* [`setVolume(...)`](#setvolume)
* [`skipToNextTrack()`](#skiptonexttrack)
* [`skipToPreviousTrack()`](#skiptoprevioustrack)
* [`stop(...)`](#stop)
* [`addListener('stop', ...)`](#addlistenerstop-)
* [`addListener('trackChange', ...)`](#addlistenertrackchange-)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addTracks(...)

```typescript
addTracks(options: AddTracksOptions) => Promise<void>
```

Add tracks to the currently loaded playlist.

Only available if a playlist has been loaded via `play({ tracks })`.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#addtracksoptions">AddTracksOptions</a></code> |

**Since:** 8.4.0

--------------------


### getCurrentPosition()

```typescript
getCurrentPosition() => Promise<GetCurrentPositionResult>
```

Get the current position of the audio playback in milliseconds.

**Returns:** <code>Promise&lt;<a href="#getcurrentpositionresult">GetCurrentPositionResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getCurrentTrackIndex()

```typescript
getCurrentTrackIndex() => Promise<GetCurrentTrackIndexResult>
```

Get the index of the currently playing track within the loaded playlist.

The `index` is `undefined` if no playlist is loaded (e.g. single-track playback or nothing playing).

**Returns:** <code>Promise&lt;<a href="#getcurrenttrackindexresult">GetCurrentTrackIndexResult</a>&gt;</code>

**Since:** 8.4.0

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


### removeTrack(...)

```typescript
removeTrack(options: RemoveTrackOptions) => Promise<void>
```

Remove a track from the currently loaded playlist.

If the currently playing track is removed, playback continues with the
track that takes its place, or stops if it was the last track.

Only available if a playlist has been loaded via `play({ tracks })`.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#removetrackoptions">RemoveTrackOptions</a></code> |

**Since:** 8.4.0

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

Seek to a specific position and/or track within the current playback.

Provide `position` to seek within the current track, `index` to jump to a different
track in the currently loaded playlist, or both to do both at once. If neither is
provided, the call is a no-op.

Play state is preserved: if the player was paused, it stays paused at the new location;
call `resume()` afterwards to start playback.

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

Only available on Android, iOS and Web.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#setrateoptions">SetRateOptions</a></code> |

**Since:** 8.2.0

--------------------


### setRepeatMode(...)

```typescript
setRepeatMode(options: SetRepeatModeOptions) => Promise<void>
```

Set the repeat mode for the current playback.

This only affects the current playback session and is not persisted.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#setrepeatmodeoptions">SetRepeatModeOptions</a></code> |

**Since:** 8.4.0

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


### skipToNextTrack()

```typescript
skipToNextTrack() => Promise<void>
```

Skip to the next track in the currently loaded playlist.

If the repeat mode is `ALL`, skipping past the last track wraps around
to the first track.

Only available if a playlist has been loaded via `play({ tracks })`.

**Since:** 8.4.0

--------------------


### skipToPreviousTrack()

```typescript
skipToPreviousTrack() => Promise<void>
```

Skip to the previous track in the currently loaded playlist.

If the repeat mode is `ALL`, skipping before the first track wraps around
to the last track.

Only available if a playlist has been loaded via `play({ tracks })`.

**Since:** 8.4.0

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


### addListener('trackChange', ...)

```typescript
addListener(eventName: 'trackChange', listenerFunc: (event: TrackChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the current track changes during playlist playback.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'trackChange'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#trackchangeevent">TrackChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.4.0

--------------------


### Interfaces


#### AddTracksOptions

| Prop         | Type                      | Description                                                                                                           | Since |
| ------------ | ------------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`index`**  | <code>number</code>       | The 0-based index at which to insert the tracks. If not provided, the tracks are appended to the end of the playlist. | 8.4.0 |
| **`tracks`** | <code>AudioTrack[]</code> | The tracks to add to the playlist.                                                                                    | 8.4.0 |


#### AudioTrack

| Prop       | Type                                  | Description                                                                                                     | Since |
| ---------- | ------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ----- |
| **`blob`** | <code><a href="#blob">Blob</a></code> | The audio file to play. Only available on Web.                                                                  | 8.4.0 |
| **`src`**  | <code>string</code>                   | The path to the web asset file or a remote URL. Both web assets and remote URLs are supported on all platforms. | 8.4.0 |
| **`uri`**  | <code>string</code>                   | The URI or path of the audio file to play. Only available on Android and iOS.                                   | 8.4.0 |


#### Blob

A file-like object of immutable, raw data. Blobs represent data that isn't necessarily in a JavaScript-native format. The File interface is based on <a href="#blob">Blob</a>, inheriting blob functionality and expanding it to support files on the user's system.

| Prop       | Type                |
| ---------- | ------------------- |
| **`size`** | <code>number</code> |
| **`type`** | <code>string</code> |

| Method          | Signature                                                                                                                  |
| --------------- | -------------------------------------------------------------------------------------------------------------------------- |
| **arrayBuffer** | () =&gt; Promise&lt;<a href="#arraybuffer">ArrayBuffer</a>&gt;                                                             |
| **slice**       | (start?: number \| undefined, end?: number \| undefined, contentType?: string \| undefined) =&gt; <a href="#blob">Blob</a> |
| **stream**      | () =&gt; <a href="#readablestream">ReadableStream</a>                                                                      |
| **text**        | () =&gt; Promise&lt;string&gt;                                                                                             |


#### ArrayBuffer

Represents a raw buffer of binary data, which is used to store data for the
different typed arrays. ArrayBuffers cannot be read from or written to directly,
but can be passed to a typed array or DataView Object to interpret the raw
buffer as needed.

| Prop             | Type                | Description                                                                     |
| ---------------- | ------------------- | ------------------------------------------------------------------------------- |
| **`byteLength`** | <code>number</code> | Read-only. The length of the <a href="#arraybuffer">ArrayBuffer</a> (in bytes). |

| Method    | Signature                                                                               | Description                                                     |
| --------- | --------------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| **slice** | (begin: number, end?: number \| undefined) =&gt; <a href="#arraybuffer">ArrayBuffer</a> | Returns a section of an <a href="#arraybuffer">ArrayBuffer</a>. |


#### ReadableStream

This Streams API interface represents a readable stream of byte data. The Fetch API offers a concrete instance of a <a href="#readablestream">ReadableStream</a> through the body property of a Response object.

| Prop         | Type                 |
| ------------ | -------------------- |
| **`locked`** | <code>boolean</code> |

| Method          | Signature                                                                                                                                                                                                                         |
| --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **cancel**      | (reason?: any) =&gt; Promise&lt;void&gt;                                                                                                                                                                                          |
| **getReader**   | () =&gt; <a href="#readablestreamdefaultreader">ReadableStreamDefaultReader</a>&lt;R&gt;                                                                                                                                          |
| **pipeThrough** | &lt;T&gt;(transform: <a href="#readablewritablepair">ReadableWritablePair</a>&lt;T, R&gt;, options?: <a href="#streampipeoptions">StreamPipeOptions</a> \| undefined) =&gt; <a href="#readablestream">ReadableStream</a>&lt;T&gt; |
| **pipeTo**      | (dest: <a href="#writablestream">WritableStream</a>&lt;R&gt;, options?: <a href="#streampipeoptions">StreamPipeOptions</a> \| undefined) =&gt; Promise&lt;void&gt;                                                                |
| **tee**         | () =&gt; [ReadableStream&lt;R&gt;, <a href="#readablestream">ReadableStream</a>&lt;R&gt;]                                                                                                                                         |


#### ReadableStreamDefaultReader

| Method          | Signature                                                                                                       |
| --------------- | --------------------------------------------------------------------------------------------------------------- |
| **read**        | () =&gt; Promise&lt;<a href="#readablestreamdefaultreadresult">ReadableStreamDefaultReadResult</a>&lt;R&gt;&gt; |
| **releaseLock** | () =&gt; void                                                                                                   |


#### ReadableStreamDefaultReadValueResult

| Prop        | Type               |
| ----------- | ------------------ |
| **`done`**  | <code>false</code> |
| **`value`** | <code>T</code>     |


#### ReadableStreamDefaultReadDoneResult

| Prop        | Type              |
| ----------- | ----------------- |
| **`done`**  | <code>true</code> |
| **`value`** |                   |


#### ReadableWritablePair

| Prop           | Type                                                               | Description                                                                                                                                                                                                                                                                                                                                                                         |
| -------------- | ------------------------------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`readable`** | <code><a href="#readablestream">ReadableStream</a>&lt;R&gt;</code> |                                                                                                                                                                                                                                                                                                                                                                                     |
| **`writable`** | <code><a href="#writablestream">WritableStream</a>&lt;W&gt;</code> | Provides a convenient, chainable way of piping this readable stream through a transform stream (or any other { writable, readable } pair). It simply pipes the stream into the writable side of the supplied pair, and returns the readable side for further use. Piping a stream will lock it for the duration of the pipe, preventing any other consumer from acquiring a reader. |


#### WritableStream

This Streams API interface provides a standard abstraction for writing streaming data to a destination, known as a sink. This object comes with built-in backpressure and queuing.

| Prop         | Type                 |
| ------------ | -------------------- |
| **`locked`** | <code>boolean</code> |

| Method        | Signature                                                                                |
| ------------- | ---------------------------------------------------------------------------------------- |
| **abort**     | (reason?: any) =&gt; Promise&lt;void&gt;                                                 |
| **getWriter** | () =&gt; <a href="#writablestreamdefaultwriter">WritableStreamDefaultWriter</a>&lt;W&gt; |


#### WritableStreamDefaultWriter

This Streams API interface is the object returned by <a href="#writablestream">WritableStream.getWriter</a>() and once created locks the &lt; writer to the <a href="#writablestream">WritableStream</a> ensuring that no other streams can write to the underlying sink.

| Prop              | Type                                  |
| ----------------- | ------------------------------------- |
| **`closed`**      | <code>Promise&lt;undefined&gt;</code> |
| **`desiredSize`** | <code>number \| null</code>           |
| **`ready`**       | <code>Promise&lt;undefined&gt;</code> |

| Method          | Signature                                |
| --------------- | ---------------------------------------- |
| **abort**       | (reason?: any) =&gt; Promise&lt;void&gt; |
| **close**       | () =&gt; Promise&lt;void&gt;             |
| **releaseLock** | () =&gt; void                            |
| **write**       | (chunk: W) =&gt; Promise&lt;void&gt;     |


#### StreamPipeOptions

| Prop                | Type                                                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| ------------------- | --------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`preventAbort`**  | <code>boolean</code>                                |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| **`preventCancel`** | <code>boolean</code>                                |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| **`preventClose`**  | <code>boolean</code>                                | Pipes this readable stream to a given writable stream destination. The way in which the piping process behaves under various error conditions can be customized with a number of passed options. It returns a promise that fulfills when the piping process completes successfully, or rejects if any errors were encountered. Piping a stream will lock it for the duration of the pipe, preventing any other consumer from acquiring a reader. Errors and closures of the source and destination streams propagate as follows: An error in this source readable stream will abort destination, unless preventAbort is truthy. The returned promise will be rejected with the source's error, or with any error that occurs during aborting the destination. An error in destination will cancel this source readable stream, unless preventCancel is truthy. The returned promise will be rejected with the destination's error, or with any error that occurs during canceling the source. When this source readable stream closes, destination will be closed, unless preventClose is truthy. The returned promise will be fulfilled once this process completes, unless an error is encountered while closing the destination, in which case it will be rejected with that error. If destination starts out closed or closing, this source readable stream will be canceled, unless preventCancel is true. The returned promise will be rejected with an error indicating piping to a closed stream failed, or with any error that occurs during canceling the source. The signal option can be set to an <a href="#abortsignal">AbortSignal</a> to allow aborting an ongoing pipe operation via the corresponding AbortController. In this case, this source readable stream will be canceled, and destination aborted, unless the respective options preventCancel or preventAbort are set. |
| **`signal`**        | <code><a href="#abortsignal">AbortSignal</a></code> |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |


#### AbortSignal

A signal object that allows you to communicate with a DOM request (such as a Fetch) and abort it if required via an AbortController object.

| Prop          | Type                                                                                                            | Description                                                                                                               |
| ------------- | --------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **`aborted`** | <code>boolean</code>                                                                                            | Returns true if this <a href="#abortsignal">AbortSignal</a>'s AbortController has signaled to abort, and false otherwise. |
| **`onabort`** | <code>((this: <a href="#abortsignal">AbortSignal</a>, ev: <a href="#event">Event</a>) =&gt; any) \| null</code> |                                                                                                                           |

| Method                  | Signature                                                                                                                                                                                                                                       | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **addEventListener**    | &lt;K extends "abort"&gt;(type: K, listener: (this: <a href="#abortsignal">AbortSignal</a>, ev: AbortSignalEventMap[K]) =&gt; any, options?: boolean \| <a href="#addeventlisteneroptions">AddEventListenerOptions</a> \| undefined) =&gt; void | Appends an event listener for events whose type attribute value is type. The callback argument sets the callback that will be invoked when the event is dispatched. The options argument sets listener-specific options. For compatibility this can be a boolean, in which case the method behaves exactly as if the value was specified as options's capture. When set to true, options's capture prevents callback from being invoked when the event's eventPhase attribute value is BUBBLING_PHASE. When false (or not present), callback will not be invoked when event's eventPhase attribute value is CAPTURING_PHASE. Either way, callback will be invoked if event's eventPhase attribute value is AT_TARGET. When set to true, options's passive indicates that the callback will not cancel the event by invoking preventDefault(). This is used to enable performance optimizations described in § 2.8 Observing event listeners. When set to true, options's once indicates that the callback will only be invoked once after which the event listener will be removed. The event listener is appended to target's event listener list and is not appended if it has the same type, callback, and capture. |
| **addEventListener**    | (type: string, listener: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a>, options?: boolean \| <a href="#addeventlisteneroptions">AddEventListenerOptions</a> \| undefined) =&gt; void                     | Appends an event listener for events whose type attribute value is type. The callback argument sets the callback that will be invoked when the event is dispatched. The options argument sets listener-specific options. For compatibility this can be a boolean, in which case the method behaves exactly as if the value was specified as options's capture. When set to true, options's capture prevents callback from being invoked when the event's eventPhase attribute value is BUBBLING_PHASE. When false (or not present), callback will not be invoked when event's eventPhase attribute value is CAPTURING_PHASE. Either way, callback will be invoked if event's eventPhase attribute value is AT_TARGET. When set to true, options's passive indicates that the callback will not cancel the event by invoking preventDefault(). This is used to enable performance optimizations described in § 2.8 Observing event listeners. When set to true, options's once indicates that the callback will only be invoked once after which the event listener will be removed. The event listener is appended to target's event listener list and is not appended if it has the same type, callback, and capture. |
| **removeEventListener** | &lt;K extends "abort"&gt;(type: K, listener: (this: <a href="#abortsignal">AbortSignal</a>, ev: AbortSignalEventMap[K]) =&gt; any, options?: boolean \| <a href="#eventlisteneroptions">EventListenerOptions</a> \| undefined) =&gt; void       | Removes the event listener in target's event listener list with the same type, callback, and options.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **removeEventListener** | (type: string, listener: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a>, options?: boolean \| <a href="#eventlisteneroptions">EventListenerOptions</a> \| undefined) =&gt; void                           | Removes the event listener in target's event listener list with the same type, callback, and options.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |


#### AbortSignalEventMap

| Prop          | Type                                    |
| ------------- | --------------------------------------- |
| **`"abort"`** | <code><a href="#event">Event</a></code> |


#### Event

An event which takes place in the DOM.

| Prop                   | Type                                                        | Description                                                                                                                                                                                                                                                |
| ---------------------- | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`bubbles`**          | <code>boolean</code>                                        | Returns true or false depending on how event was initialized. True if event goes through its target's ancestors in reverse tree order, and false otherwise.                                                                                                |
| **`cancelBubble`**     | <code>boolean</code>                                        |                                                                                                                                                                                                                                                            |
| **`cancelable`**       | <code>boolean</code>                                        | Returns true or false depending on how event was initialized. Its return value does not always carry meaning, but true can indicate that part of the operation during which event was dispatched, can be canceled by invoking the preventDefault() method. |
| **`composed`**         | <code>boolean</code>                                        | Returns true or false depending on how event was initialized. True if event invokes listeners past a ShadowRoot node that is the root of its target, and false otherwise.                                                                                  |
| **`currentTarget`**    | <code><a href="#eventtarget">EventTarget</a> \| null</code> | Returns the object whose event listener's callback is currently being invoked.                                                                                                                                                                             |
| **`defaultPrevented`** | <code>boolean</code>                                        | Returns true if preventDefault() was invoked successfully to indicate cancelation, and false otherwise.                                                                                                                                                    |
| **`eventPhase`**       | <code>number</code>                                         | Returns the event's phase, which is one of NONE, CAPTURING_PHASE, AT_TARGET, and BUBBLING_PHASE.                                                                                                                                                           |
| **`isTrusted`**        | <code>boolean</code>                                        | Returns true if event was dispatched by the user agent, and false otherwise.                                                                                                                                                                               |
| **`returnValue`**      | <code>boolean</code>                                        |                                                                                                                                                                                                                                                            |
| **`srcElement`**       | <code><a href="#eventtarget">EventTarget</a> \| null</code> |                                                                                                                                                                                                                                                            |
| **`target`**           | <code><a href="#eventtarget">EventTarget</a> \| null</code> | Returns the object to which event is dispatched (its target).                                                                                                                                                                                              |
| **`timeStamp`**        | <code>number</code>                                         | Returns the event's timestamp as the number of milliseconds measured relative to the time origin.                                                                                                                                                          |
| **`type`**             | <code>string</code>                                         | Returns the type of event, e.g. "click", "hashchange", or "submit".                                                                                                                                                                                        |
| **`AT_TARGET`**        | <code>number</code>                                         |                                                                                                                                                                                                                                                            |
| **`BUBBLING_PHASE`**   | <code>number</code>                                         |                                                                                                                                                                                                                                                            |
| **`CAPTURING_PHASE`**  | <code>number</code>                                         |                                                                                                                                                                                                                                                            |
| **`NONE`**             | <code>number</code>                                         |                                                                                                                                                                                                                                                            |

| Method                       | Signature                                                                                    | Description                                                                                                                                                                                                                             |
| ---------------------------- | -------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **composedPath**             | () =&gt; EventTarget[]                                                                       | Returns the invocation target objects of event's path (objects on which listeners will be invoked), except for any nodes in shadow trees of which the shadow root's mode is "closed" that are not reachable from event's currentTarget. |
| **initEvent**                | (type: string, bubbles?: boolean \| undefined, cancelable?: boolean \| undefined) =&gt; void |                                                                                                                                                                                                                                         |
| **preventDefault**           | () =&gt; void                                                                                | If invoked when the cancelable attribute value is true, and while executing a listener for the event with passive set to false, signals to the operation that caused event to be dispatched that it needs to be canceled.               |
| **stopImmediatePropagation** | () =&gt; void                                                                                | Invoking this method prevents event from reaching any registered event listeners after the current one finishes running and, when dispatched in a tree, also prevents event from reaching any other objects.                            |
| **stopPropagation**          | () =&gt; void                                                                                | When dispatched in a tree, invoking this method prevents event from reaching any objects other than the current object.                                                                                                                 |


#### EventTarget

<a href="#eventtarget">EventTarget</a> is a DOM interface implemented by objects that can receive events and may have listeners for them.

| Method                  | Signature                                                                                                                                                                                                                           | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **addEventListener**    | (type: string, listener: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a> \| null, options?: boolean \| <a href="#addeventlisteneroptions">AddEventListenerOptions</a> \| undefined) =&gt; void | Appends an event listener for events whose type attribute value is type. The callback argument sets the callback that will be invoked when the event is dispatched. The options argument sets listener-specific options. For compatibility this can be a boolean, in which case the method behaves exactly as if the value was specified as options's capture. When set to true, options's capture prevents callback from being invoked when the event's eventPhase attribute value is BUBBLING_PHASE. When false (or not present), callback will not be invoked when event's eventPhase attribute value is CAPTURING_PHASE. Either way, callback will be invoked if event's eventPhase attribute value is AT_TARGET. When set to true, options's passive indicates that the callback will not cancel the event by invoking preventDefault(). This is used to enable performance optimizations described in § 2.8 Observing event listeners. When set to true, options's once indicates that the callback will only be invoked once after which the event listener will be removed. The event listener is appended to target's event listener list and is not appended if it has the same type, callback, and capture. |
| **dispatchEvent**       | (event: <a href="#event">Event</a>) =&gt; boolean                                                                                                                                                                                   | Dispatches a synthetic event event to target and returns true if either event's cancelable attribute value is false or its preventDefault() method was not invoked, and false otherwise.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **removeEventListener** | (type: string, callback: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a> \| null, options?: boolean \| <a href="#eventlisteneroptions">EventListenerOptions</a> \| undefined) =&gt; void       | Removes the event listener in target's event listener list with the same type, callback, and options.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |


#### EventListener


#### EventListenerObject

| Method          | Signature                                    |
| --------------- | -------------------------------------------- |
| **handleEvent** | (evt: <a href="#event">Event</a>) =&gt; void |


#### AddEventListenerOptions

| Prop          | Type                 |
| ------------- | -------------------- |
| **`once`**    | <code>boolean</code> |
| **`passive`** | <code>boolean</code> |


#### EventListenerOptions

| Prop          | Type                 |
| ------------- | -------------------- |
| **`capture`** | <code>boolean</code> |


#### GetCurrentPositionResult

| Prop           | Type                | Description                                                 | Since |
| -------------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`position`** | <code>number</code> | The current position of the audio playback in milliseconds. | 0.0.1 |


#### GetCurrentTrackIndexResult

| Prop        | Type                | Description                                                                                                        | Since |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------------------------------ | ----- |
| **`index`** | <code>number</code> | The 0-based index of the currently playing track within the loaded playlist. `undefined` if no playlist is loaded. | 8.4.0 |


#### GetDurationResult

| Prop           | Type                | Description                                         | Since |
| -------------- | ------------------- | --------------------------------------------------- | ----- |
| **`duration`** | <code>number</code> | The duration of the audio playback in milliseconds. | 0.0.1 |


#### IsPlayingResult

| Prop            | Type                 | Description                             | Since |
| --------------- | -------------------- | --------------------------------------- | ----- |
| **`isPlaying`** | <code>boolean</code> | Whether the audio is currently playing. | 0.0.1 |


#### PlayOptions

| Prop             | Type                                  | Description                                                                                                                                                                                                                       | Default          | Since |
| ---------------- | ------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`blob`**       | <code><a href="#blob">Blob</a></code> | The audio file to play. If both `blob` and `src` are provided, `blob` takes priority. Only available on Web.                                                                                                                      |                  | 0.0.1 |
| **`loop`**       | <code>boolean</code>                  | Whether to loop the audio playback. This option is ignored when `tracks` is provided.                                                                                                                                             |                  | 0.0.1 |
| **`position`**   | <code>number</code>                   | The position to start playback from (in milliseconds).                                                                                                                                                                            |                  | 0.0.1 |
| **`rate`**       | <code>number</code>                   | The playback rate to use. Values between 0.5 and 2.0 are recommended. Other values may not be supported on all devices. Only available on Android, iOS and Web.                                                                   | <code>1.0</code> | 8.2.0 |
| **`startIndex`** | <code>number</code>                   | The 0-based index of the track to start playback from. Only meaningful when `tracks` is provided.                                                                                                                                 | <code>0</code>   | 8.4.0 |
| **`tracks`**     | <code>AudioTrack[]</code>             | A list of audio tracks to play sequentially. When provided, `blob`, `src`, and `uri` are ignored.                                                                                                                                 |                  | 8.4.0 |
| **`src`**        | <code>string</code>                   | The path to the web asset file to play. If both `blob` and `src` are provided, `blob` takes priority. If both `uri` and `src` are provided, `uri` takes priority. Both web assets and remote URLs are supported on all platforms. |                  | 0.1.2 |
| **`uri`**        | <code>string</code>                   | The URI or path of the audio file to play. If both `uri` and `src` are provided, `uri` takes priority. Only available on Android and iOS.                                                                                         |                  | 0.0.1 |
| **`volume`**     | <code>number</code>                   | The volume level to set (0-100).                                                                                                                                                                                                  | <code>100</code> | 0.0.1 |


#### RemoveTrackOptions

| Prop        | Type                | Description                                                 | Since |
| ----------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`index`** | <code>number</code> | The 0-based index of the track to remove from the playlist. | 8.4.0 |


#### SeekToOptions

| Prop           | Type                | Description                                                                                                                                             | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`index`**    | <code>number</code> | The 0-based index of the track to jump to within the currently loaded playlist. Only meaningful when a playlist has been loaded via `play({ tracks })`. | 8.4.0 |
| **`position`** | <code>number</code> | The position to seek to (in milliseconds). When `index` is also provided, this is the position within the target track.                                 | 0.0.1 |


#### SetRateOptions

| Prop       | Type                | Description                                                                                                             | Since |
| ---------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------- | ----- |
| **`rate`** | <code>number</code> | The playback rate to set. Values between 0.5 and 2.0 are recommended. Other values may not be supported on all devices. | 8.2.0 |


#### SetRepeatModeOptions

| Prop       | Type                                              | Description             | Since |
| ---------- | ------------------------------------------------- | ----------------------- | ----- |
| **`mode`** | <code><a href="#repeatmode">RepeatMode</a></code> | The repeat mode to set. | 8.4.0 |


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


#### TrackChangeEvent

| Prop        | Type                | Description                                 | Since |
| ----------- | ------------------- | ------------------------------------------- | ----- |
| **`index`** | <code>number</code> | The 0-based index of the new current track. | 8.4.0 |


### Type Aliases


#### ReadableStreamDefaultReadResult

<code><a href="#readablestreamdefaultreadvalueresult">ReadableStreamDefaultReadValueResult</a>&lt;T&gt; | <a href="#readablestreamdefaultreaddoneresult">ReadableStreamDefaultReadDoneResult</a></code>


#### EventListenerOrEventListenerObject

<code><a href="#eventlistener">EventListener</a> | <a href="#eventlistenerobject">EventListenerObject</a></code>


#### AbortSignal

<code>unknown</code>


### Enums


#### RepeatMode

| Members    | Value               | Description                 | Since |
| ---------- | ------------------- | --------------------------- | ----- |
| **`All`**  | <code>'ALL'</code>  | Repeat the entire playlist. | 8.4.0 |
| **`None`** | <code>'NONE'</code> | Do not repeat.              | 8.4.0 |
| **`One`**  | <code>'ONE'</code>  | Repeat the current track.   | 8.4.0 |

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
