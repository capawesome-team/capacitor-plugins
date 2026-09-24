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
- 🎛️ **Media Session**: Control the playback from the system's media controls (e.g. notification and lock screen), even while the app is in the background.
- ⏪ **Seek Buttons**: Display seek backward and seek forward buttons with a configurable offset in the system's media controls.
- 🎨 **Customizable Icons**: Configure the media notification icons on Android to match your app's branding.
- 🔊 **Volume Control**: Precise volume control from 0-100.
- ⏩ **Playback Speed**: Adjustable playback rate with pitch preservation.
- 🗂️ **Web Assets**: Support for web asset paths alongside file URIs and remote URLs.
- 🤝 **Compatibility**: Compatible with the [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/), [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/) and [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugins.
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

## Guides

- [How to Play Audio in the Background in a Capacitor App](https://capawesome.io/blog/how-to-play-audio-in-the-background-in-capacitor/): Native playback, lock screen media controls, and playlists that keep running.

## Demo

A working example can be found here: [capawesome-team/capacitor-audio-player-demo](https://github.com/capawesome-team/capacitor-audio-player-demo)

| Android                                                                                                                                                                        | iOS                                                                                                                                                                        |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/audio-player/assets/audio-player-demo-android.mp4" width="324" controls></video> | <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/audio-player/assets/audio-player-demo-ios.mp4" width="266" controls></video> |

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

If needed, you can define the following project variables in your app's `variables.gradle` file to change the default versions of the dependencies:

- `$androidxMedia3ExoPlayerVersion` version of `androidx.media3:media3-exoplayer` (default: `1.6.1`)
- `$androidxMedia3SessionVersion` version of `androidx.media3:media3-session` (default: `1.6.1`)

#### Media Session

If you want to use the media session integration (see the `metadata` option of the `play(...)` method), add the following service to your `AndroidManifest.xml` inside the `application` tag:

```xml
<service
    android:name="io.capawesome.capacitorjs.plugins.audioplayer.AudioPlayerService"
    android:exported="false"
    android:foregroundServiceType="mediaPlayback">
    <intent-filter>
        <action android:name="androidx.media3.session.MediaSessionService" />
    </intent-filter>
</service>
```

Also, add the following permissions before or after the `application` tag:

```xml
<!-- Required to display the media controls in a notification. -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />
```

#### Notification Icons

If you want to use custom icons in the media notification (see [Configuration](#configuration)):

1. Add your icons to `android/app/src/main/res/drawable/` (e.g., `ic_notification.png`)
   - Icons should be single-color white with transparent background for best display
   - Can use density-specific folders (`drawable-mdpi`, `drawable-hdpi`, etc.)

2. Configure the plugin in `capacitor.config.ts`:
   ```ts
   const config: CapacitorConfig = {
     plugins: {
       AudioPlayer: {
         smallIcon: 'ic_notification', // Matches ic_notification.png
         seekBackwardIcon: 'ic_seek_backward_20', // Matches ic_seek_backward_20.png
         seekForwardIcon: 'ic_seek_forward_20', // Matches ic_seek_forward_20.png
       },
     },
   };
   ```

3. Run: `npx cap sync`

On iOS, the playback controls on the lock screen and in the Control Center are rendered by the operating system and cannot be customized.

### iOS

#### Capabilities

If you want to play audio in the background, ensure `Background Modes` capability is enabled with `Audio, AirPlay, and Picture in Picture` in your Xcode project.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                   | Type                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | Default                                                                                                                      | Since |
| ---------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`seekBackwardIcon`** | <code>string</code> | The name of the drawable resource to use as the icon for the seek backward button in the media notification. The button is only displayed if a seek offset has been provided (see the `seekBackwardOffset` and `seekForwardOffset` options of the `play(...)` method). The resource name should not include the `R.drawable.` prefix or file extension. For example, if you have `res/drawable/ic_seek_backward.png`, set this to `"ic_seek_backward"`. If the resource is not found, the default icon is used. **Attention:** The icon should be white pixels on a transparent background. Otherwise, a white square or circle may be displayed instead of the icon. Only available on Android. | <code>"media3_icon_skip_back" (Media3 built-in icon, with a number badge for an offset of 5, 10, 15 or 30 seconds)</code>    | 8.5.0 |
| **`seekForwardIcon`**  | <code>string</code> | The name of the drawable resource to use as the icon for the seek forward button in the media notification. The button is only displayed if a seek offset has been provided (see the `seekBackwardOffset` and `seekForwardOffset` options of the `play(...)` method). The resource name should not include the `R.drawable.` prefix or file extension. For example, if you have `res/drawable/ic_seek_forward.png`, set this to `"ic_seek_forward"`. If the resource is not found, the default icon is used. **Attention:** The icon should be white pixels on a transparent background. Otherwise, a white square or circle may be displayed instead of the icon. Only available on Android.    | <code>"media3_icon_skip_forward" (Media3 built-in icon, with a number badge for an offset of 5, 10, 15 or 30 seconds)</code> | 8.5.0 |
| **`smallIcon`**        | <code>string</code> | The name of the drawable resource to use as the small icon in the media notification. The resource name should not include the `R.drawable.` prefix or file extension. For example, if you have `res/drawable/ic_notification.png`, set this to `"ic_notification"`. If the resource is not found, the default icon is used. **Attention:** The icon should be white pixels on a transparent background. Otherwise, a white square or circle may be displayed instead of the icon. Only available on Android.                                                                                                                                                                                    | <code>"media3_notification_small_icon" (Media3 built-in icon)</code>                                                         | 8.5.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "AudioPlayer": {
      "seekBackwardIcon": "ic_seek_backward",
      "seekForwardIcon": "ic_seek_forward",
      "smallIcon": "ic_notification"
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome-team/capacitor-audio-player" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    AudioPlayer: {
      seekBackwardIcon: "ic_seek_backward",
      seekForwardIcon: "ic_seek_forward",
      smallIcon: "ic_notification",
    },
  },
};

export default config;
```

</docgen-config>

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

### Control the playback from the system's media controls

Provide the `metadata` option to display the playback in the system's media controls (e.g. notification and lock screen). The playback can then be controlled from there, even while the app is in the background. On Android, this requires additional manifest entries (see [Installation](#installation)):

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const playWithMediaSession = async () => {
  await AudioPlayer.play({
    tracks: [
      {
        src: 'https://example.com/track1.mp3',
        metadata: {
          album: 'The Dark Side of the Moon',
          artist: 'Pink Floyd',
          artworkSource: 'https://example.com/artwork.png',
          title: 'Time',
        },
      },
      {
        src: 'https://example.com/track2.mp3',
        metadata: {
          album: 'The Dark Side of the Moon',
          artist: 'Pink Floyd',
          artworkSource: 'https://example.com/artwork.png',
          title: 'Money',
        },
      },
    ],
  });
};

const listenForPlaybackStateChanges = async () => {
  await AudioPlayer.addListener('playbackStateChanged', (event) => {
    console.log('Playback state changed to:', event.state);
  });
};
```

Provide the `seekBackwardOffset` and `seekForwardOffset` options to display seek buttons instead of the previous and next track buttons, for example for podcasts or audiobooks:

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

const playWithSeekButtons = async () => {
  await AudioPlayer.play({
    src: 'https://example.com/episode.mp3',
    metadata: {
      artist: 'Capawesome',
      title: 'Episode 12',
    },
    seekBackwardOffset: 15000,
    seekForwardOffset: 30000,
  });
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
* [`addListener('playbackStateChanged', ...)`](#addlistenerplaybackstatechanged-)
* [`addListener('stop', ...)`](#addlistenerstop-)
* [`addListener('trackChange', ...)`](#addlistenertrackchange-)
* [Interfaces](#interfaces)
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


### addListener('playbackStateChanged', ...)

```typescript
addListener(eventName: 'playbackStateChanged', listenerFunc: (event: PlaybackStateChangedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the playback state changes, for example when the user
pauses the playback via the media controls on the lock screen.

| Param              | Type                                                                                                |
| ------------------ | --------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'playbackStateChanged'</code>                                                                 |
| **`listenerFunc`** | <code>(event: <a href="#playbackstatechangedevent">PlaybackStateChangedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.4.0

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

| Prop           | Type                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                         | Since |
| -------------- | ------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`blob`**     | <code>Blob</code>                                       | The audio file to play. Only available on Web.                                                                                                                                                                                                                                                                                                                                                                                                      | 8.4.0 |
| **`metadata`** | <code><a href="#trackmetadata">TrackMetadata</a></code> | The metadata of the track, displayed in the system's media controls (e.g. notification and lock screen). Providing metadata activates the media session integration so that the playback can be controlled from the system's media controls, even while the app is in the background. On Android, this requires additional manifest entries. See the [documentation](https://capawesome.io/docs/sdks/capacitor/audio-player/) for more information. | 8.4.0 |
| **`src`**      | <code>string</code>                                     | The path to the web asset file or a remote URL. Both web assets and remote URLs are supported on all platforms.                                                                                                                                                                                                                                                                                                                                     | 8.4.0 |
| **`uri`**      | <code>string</code>                                     | The URI or path of the audio file to play. Only available on Android and iOS.                                                                                                                                                                                                                                                                                                                                                                       | 8.4.0 |


#### TrackMetadata

| Prop                | Type                | Description                                                                                                                      | Since |
| ------------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`album`**         | <code>string</code> | The album of the track.                                                                                                          | 8.4.0 |
| **`artist`**        | <code>string</code> | The artist of the track.                                                                                                         | 8.4.0 |
| **`artworkSource`** | <code>string</code> | The source of the artwork of the track, displayed in the system's media controls. Both web assets and remote URLs are supported. | 8.4.0 |
| **`title`**         | <code>string</code> | The title of the track.                                                                                                          | 8.4.0 |


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

| Prop                     | Type                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | Default          | Since |
| ------------------------ | ------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`blob`**               | <code>Blob</code>                                       | The audio file to play. If both `blob` and `src` are provided, `blob` takes priority. Only available on Web.                                                                                                                                                                                                                                                                                                                                                                                          |                  | 0.0.1 |
| **`loop`**               | <code>boolean</code>                                    | Whether to loop the audio playback. This option is ignored when `tracks` is provided.                                                                                                                                                                                                                                                                                                                                                                                                                 |                  | 0.0.1 |
| **`metadata`**           | <code><a href="#trackmetadata">TrackMetadata</a></code> | The metadata of the track, displayed in the system's media controls (e.g. notification and lock screen). Providing metadata activates the media session integration so that the playback can be controlled from the system's media controls, even while the app is in the background. On Android, this requires additional manifest entries. See the [documentation](https://capawesome.io/docs/sdks/capacitor/audio-player/) for more information. This option is ignored when `tracks` is provided. |                  | 8.4.0 |
| **`position`**           | <code>number</code>                                     | The position to start playback from (in milliseconds).                                                                                                                                                                                                                                                                                                                                                                                                                                                |                  | 0.0.1 |
| **`rate`**               | <code>number</code>                                     | The playback rate to use. Values between 0.5 and 2.0 are recommended. Other values may not be supported on all devices. Must be greater than `0`. Only available on Android, iOS and Web.                                                                                                                                                                                                                                                                                                             | <code>1.0</code> | 8.2.0 |
| **`seekBackwardOffset`** | <code>number</code>                                     | The offset in milliseconds for the seek backward button in the system's media controls. If provided, the seek backward button is displayed instead of the previous track button, since the system's media controls only have one slot on each side of the play button. Must be greater than `0`. Only applies if the media session is active (see the `metadata` option).                                                                                                                             |                  | 8.5.0 |
| **`seekForwardOffset`**  | <code>number</code>                                     | The offset in milliseconds for the seek forward button in the system's media controls. If provided, the seek forward button is displayed instead of the next track button, since the system's media controls only have one slot on each side of the play button. Must be greater than `0`. Only applies if the media session is active (see the `metadata` option).                                                                                                                                   |                  | 8.5.0 |
| **`startIndex`**         | <code>number</code>                                     | The 0-based index of the track to start playback from. Only meaningful when `tracks` is provided.                                                                                                                                                                                                                                                                                                                                                                                                     | <code>0</code>   | 8.4.0 |
| **`tracks`**             | <code>AudioTrack[]</code>                               | A list of audio tracks to play sequentially. When provided, `blob`, `src`, and `uri` are ignored.                                                                                                                                                                                                                                                                                                                                                                                                     |                  | 8.4.0 |
| **`src`**                | <code>string</code>                                     | The path to the web asset file to play. If both `blob` and `src` are provided, `blob` takes priority. If both `uri` and `src` are provided, `uri` takes priority. Both web assets and remote URLs are supported on all platforms.                                                                                                                                                                                                                                                                     |                  | 0.1.2 |
| **`uri`**                | <code>string</code>                                     | The URI or path of the audio file to play. If both `uri` and `src` are provided, `uri` takes priority. Only available on Android and iOS.                                                                                                                                                                                                                                                                                                                                                             |                  | 0.0.1 |
| **`volume`**             | <code>number</code>                                     | The volume level to set (0-100).                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | <code>100</code> | 0.0.1 |


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

| Prop       | Type                | Description                                                                                                                                       | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`rate`** | <code>number</code> | The playback rate to set. Values between 0.5 and 2.0 are recommended. Other values may not be supported on all devices. Must be greater than `0`. | 8.2.0 |


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


#### PlaybackStateChangedEvent

| Prop        | Type                                                    | Description             | Since |
| ----------- | ------------------------------------------------------- | ----------------------- | ----- |
| **`state`** | <code><a href="#playbackstate">PlaybackState</a></code> | The new playback state. | 8.4.0 |


#### TrackChangeEvent

| Prop        | Type                | Description                                 | Since |
| ----------- | ------------------- | ------------------------------------------- | ----- |
| **`index`** | <code>number</code> | The 0-based index of the new current track. | 8.4.0 |


### Enums


#### RepeatMode

| Members    | Value               | Description                 | Since |
| ---------- | ------------------- | --------------------------- | ----- |
| **`All`**  | <code>'ALL'</code>  | Repeat the entire playlist. | 8.4.0 |
| **`None`** | <code>'NONE'</code> | Do not repeat.              | 8.4.0 |
| **`One`**  | <code>'ONE'</code>  | Repeat the current track.   | 8.4.0 |


#### PlaybackState

| Members       | Value                  | Description              | Since |
| ------------- | ---------------------- | ------------------------ | ----- |
| **`Paused`**  | <code>'PAUSED'</code>  | The playback is paused.  | 8.4.0 |
| **`Playing`** | <code>'PLAYING'</code> | The playback is playing. | 8.4.0 |
| **`Stopped`** | <code>'STOPPED'</code> | The playback is stopped. | 8.4.0 |

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

Yes, the plugin is compatible with the [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/), [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/) and [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugins. For example, you can play back a recording created with the Audio Recorder plugin. However, combining it with the [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/) plugin is not recommended. Use the built-in media session integration (see the `metadata` option of the `play(...)` method) instead, which handles the media controls natively, even when the web view is suspended while the app is in the background.

### How can I display seek buttons in the system's media controls?

Provide the `seekBackwardOffset` and `seekForwardOffset` options of the `play(...)` method with the desired offsets in milliseconds. The seek buttons are displayed instead of the previous and next track buttons, since the system's media controls only have one slot on each side of the play button. The options belong to the playback, so a playlist started without them displays the previous and next track buttons again. See the [Usage](#usage) section for an example.

### Can I customize the notification icons on Android?

Yes, use the `smallIcon`, `seekBackwardIcon` and `seekForwardIcon` configuration options to set the name of a drawable resource from your app's `res/drawable` directory. The icons should be single-color white with a transparent background for the best display. If a resource is not found, the default icon is used. The play, pause, previous track and next track buttons cannot be customized, as they are rendered by the operating system. See the [Configuration](#configuration) section for details.

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
