# @capawesome-team/capacitor-media-session

Capacitor plugin to interact with media controllers, volume keys and media buttons.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for media sessions. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🎮 **Media Controls**: Handle hardware media keys, lock screen controls, and notification controls.
- 🎵 **Rich Metadata**: Display song title, artist, album, and artwork on lock screen and notifications.
- ▶️ **Action Handlers**: Support for play, pause, seek, next/previous track, and more.
- 📍 **Position State**: Track and display playback position, duration, and playback rate.
- 🔧 **Native APIs**: Uses MediaSession API on Android and MPNowPlayingInfoCenter on iOS for the best possible integration.
- 🤝 **Compatibility**: Compatible with the [Audio Player](https://capawesome.io/plugins/audio-player/) plugin.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Demo

| Android                                                                                                                   | iOS                                                                                                               | Web                                                                                                               |
| ------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| <img src="https://capawesome.io/assets/images/gifs/capacitor-media-session-android.gif" width="324" alt="Android Demo" /> | <img src="https://capawesome.io/assets/images/gifs/capacitor-media-session-ios.gif" width="266" alt="iOS Demo" /> | <img src="https://capawesome.io/assets/images/gifs/capacitor-media-session-web.gif" width="324" alt="Web Demo" /> |

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
npm install @capawesome-team/capacitor-media-session
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidMediaVersion` version of `androidx.media:media` (default: `1.7.1`)

## Usage

```typescript
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';
import {
  MediaSession,
  MediaSessionAction,
  MediaSessionPlaybackState
} from '@capawesome-team/capacitor-media-session';

const setMetadata = async () => {
  await MediaSession.setMetadata({
    title: 'Test Song',
    artist: 'My Awesome Artist',
    album: 'My Awesome Album',
    artwork: [
      {
        src: 'https://example.com/cover-96x96.png',
        sizes: '96x96',
        type: 'image/png',
      },
      {
        src: 'https://example.com/cover-512x512.png',
        sizes: '512x512',
        type: 'image/png',
      },
    ],
  });
};

const registerActions = async () => {
  await MediaSession.registerActionHandler({
    action: MediaSessionAction.Play
  });
  await MediaSession.registerActionHandler({
    action: MediaSessionAction.Pause
  });
  await MediaSession.registerActionHandler({
    action: MediaSessionAction.SeekBackward
  });
  await MediaSession.registerActionHandler({
    action: MediaSessionAction.SeekForward
  });
  await MediaSession.registerActionHandler({
    action: MediaSessionAction.SeekTo
  });
  await MediaSession.registerActionHandler({
    action: MediaSessionAction.Stop
  });
};

const setPlaybackState = async () => {
  await MediaSession.setPlaybackState({
    playbackState: MediaSessionPlaybackState.Playing,
  });
};

const setPositionState = async () => {
  await MediaSession.setPositionState({
    duration: 180,
    playbackRate: 1.0,
    position: 30,
  });
};

const addActionListener = () => {
  MediaSession.addListener('action', async (event) => {
    switch (event.action) {
      case MediaSessionAction.Play:
        await AudioPlayer.resume();
        await MediaSession.setPlaybackState({
          playbackState: MediaSessionPlaybackState.Playing,
        });
        break;
      case MediaSessionAction.Pause:
        await AudioPlayer.pause();
        await MediaSession.setPlaybackState({
          playbackState: MediaSessionPlaybackState.Paused,
        });
        break;
      case MediaSessionAction.SeekBackward:
        const { position: currentPos } = await AudioPlayer.getCurrentPosition();
        const offsetMs = event.seekOffset ? event.seekOffset * 1000 : 10000;
        await AudioPlayer.seekTo({
          position: Math.max(0, currentPos - offsetMs)
        });
        break;
      case MediaSessionAction.SeekForward:
        const { position: pos } = await AudioPlayer.getCurrentPosition();
        const { duration } = await AudioPlayer.getDuration();
        const offset = event.seekOffset ? event.seekOffset * 1000 : 10000;
        await AudioPlayer.seekTo({
          position: Math.min(duration, pos + offset)
        });
        break;
      case MediaSessionAction.SeekTo:
        if (event.seekTime !== undefined) {
          await AudioPlayer.seekTo({
            position: event.seekTime * 1000
          });
        }
        break;
      case MediaSessionAction.Stop:
        await AudioPlayer.stop();
        await MediaSession.setPlaybackState({
          playbackState: MediaSessionPlaybackState.None,
        });
        break;
    }
  });
};

const removeActionListener = async () => {
  await MediaSession.unregisterActionHandler({
    action: MediaSessionAction.Play
  });
  await MediaSession.unregisterActionHandler({
    action: MediaSessionAction.Pause
  });
  await MediaSession.removeAllListeners();
};
```

## API

<docgen-index>

* [`registerActionHandler(...)`](#registeractionhandler)
* [`setCameraActive(...)`](#setcameraactive)
* [`setMetadata(...)`](#setmetadata)
* [`setMicrophoneActive(...)`](#setmicrophoneactive)
* [`setPlaybackState(...)`](#setplaybackstate)
* [`setPositionState(...)`](#setpositionstate)
* [`unregisterActionHandler(...)`](#unregisteractionhandler)
* [`addListener('action', ...)`](#addlisteneraction-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### registerActionHandler(...)

```typescript
registerActionHandler(options: RegisterActionHandlerOptions) => Promise<void>
```

Register a handler for a media session action.

If the action handler is registered, the media session will respond to the action
by calling the `action` event listener. If the action handler is not registered,
the media session will not respond to the action.

Make sure to unregister the action handler when it is no longer needed.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#registeractionhandleroptions">RegisterActionHandlerOptions</a></code> |

**Since:** 0.0.1

--------------------


### setCameraActive(...)

```typescript
setCameraActive(options: SetCameraActiveOptions) => Promise<void>
```

Set the camera active state.

Only available on Web.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setcameraactiveoptions">SetCameraActiveOptions</a></code> |

**Since:** 0.0.1

--------------------


### setMetadata(...)

```typescript
setMetadata(options: SetMetadataOptions) => Promise<void>
```

Set the metadata for the media session.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setmetadataoptions">SetMetadataOptions</a></code> |

**Since:** 0.0.1

--------------------


### setMicrophoneActive(...)

```typescript
setMicrophoneActive(options: SetMicrophoneActive) => Promise<void>
```

Set the microphone active state.

Only available on Web.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setmicrophoneactive">SetMicrophoneActive</a></code> |

**Since:** 0.0.1

--------------------


### setPlaybackState(...)

```typescript
setPlaybackState(options: SetPlaybackStateOptions) => Promise<void>
```

Set the playback state.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setplaybackstateoptions">SetPlaybackStateOptions</a></code> |

**Since:** 0.0.1

--------------------


### setPositionState(...)

```typescript
setPositionState(options: SetPositionStateOptions) => Promise<void>
```

Set the position state.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setpositionstateoptions">SetPositionStateOptions</a></code> |

**Since:** 0.0.1

--------------------


### unregisterActionHandler(...)

```typescript
unregisterActionHandler(options: UnregisterActionHandlerOptions) => Promise<void>
```

Unregister a handler for a media session action.

If the action handler is unregistered, the media session will no longer respond to the action
and the `action` event listener will no longer be called for the specific action.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#unregisteractionhandleroptions">UnregisterActionHandlerOptions</a></code> |

**Since:** 0.0.1

--------------------


### addListener('action', ...)

```typescript
addListener(eventName: 'action', listenerFunc: (event: ActionEvent) => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'action'</code>                                                   |
| **`listenerFunc`** | <code>(event: <a href="#actionevent">ActionEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### RegisterActionHandlerOptions

| Prop         | Type                                                              | Description           | Since |
| ------------ | ----------------------------------------------------------------- | --------------------- | ----- |
| **`action`** | <code><a href="#mediasessionaction">MediaSessionAction</a></code> | The action to handle. | 0.0.1 |


#### SetCameraActiveOptions

| Prop         | Type                 | Description                          | Since |
| ------------ | -------------------- | ------------------------------------ | ----- |
| **`active`** | <code>boolean</code> | Whether or not the camera is active. | 0.0.1 |


#### SetMetadataOptions

| Prop          | Type                                | Description                    | Since |
| ------------- | ----------------------------------- | ------------------------------ | ----- |
| **`album`**   | <code>string</code>                 |                                | 0.0.1 |
| **`artist`**  | <code>string</code>                 |                                | 0.0.1 |
| **`artwork`** | <code>MediaMetadataArtwork[]</code> | Only available on iOS and Web. | 0.0.1 |
| **`title`**   | <code>string</code>                 |                                | 0.0.1 |


#### MediaMetadataArtwork

| Prop        | Type                | Description                                                 | Since |
| ----------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`sizes`** | <code>string</code> | The size of the artwork.                                    | 0.0.1 |
| **`src`**   | <code>string</code> | The URL from which the user agent fetches the image's data. | 0.0.1 |
| **`type`**  | <code>string</code> | The MIME type hint for the user agent.                      | 0.0.1 |


#### SetMicrophoneActive

| Prop         | Type                 | Description                              | Since |
| ------------ | -------------------- | ---------------------------------------- | ----- |
| **`active`** | <code>boolean</code> | Whether or not the microphone is active. | 0.0.1 |


#### SetPlaybackStateOptions

| Prop                | Type                                                                            | Description                | Since |
| ------------------- | ------------------------------------------------------------------------------- | -------------------------- | ----- |
| **`playbackState`** | <code><a href="#mediasessionplaybackstate">MediaSessionPlaybackState</a></code> | The playback state to set. | 0.0.1 |


#### SetPositionStateOptions

| Prop               | Type                | Description                                   | Since |
| ------------------ | ------------------- | --------------------------------------------- | ----- |
| **`duration`**     | <code>number</code> | The duration of the media in seconds.         | 0.0.1 |
| **`playbackRate`** | <code>number</code> | The playback rate of the media.               | 0.0.1 |
| **`position`**     | <code>number</code> | The current position of the media in seconds. | 0.0.1 |


#### UnregisterActionHandlerOptions

| Prop         | Type                                                              | Description               | Since |
| ------------ | ----------------------------------------------------------------- | ------------------------- | ----- |
| **`action`** | <code><a href="#mediasessionaction">MediaSessionAction</a></code> | The action to unregister. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ActionEvent

| Prop             | Type                                                              | Description                                | Since |
| ---------------- | ----------------------------------------------------------------- | ------------------------------------------ | ----- |
| **`action`**     | <code><a href="#mediasessionaction">MediaSessionAction</a></code> | The action that was handled.               | 0.0.1 |
| **`fastSeek`**   | <code>boolean</code>                                              | Whether or not the action was a fast seek. | 0.0.1 |
| **`seekOffset`** | <code>number</code>                                               | The offset in seconds to seek to.          | 0.0.1 |
| **`seekTime`**   | <code>number</code>                                               | The time in seconds to seek to.            | 0.0.1 |


### Enums


#### MediaSessionAction

| Members                     | Value                                   | Description            | Since |
| --------------------------- | --------------------------------------- | ---------------------- | ----- |
| **`Play`**                  | <code>'PLAY'</code>                     |                        | 0.0.1 |
| **`Pause`**                 | <code>'PAUSE'</code>                    |                        | 0.0.1 |
| **`SeekBackward`**          | <code>'SEEK_BACKWARD'</code>            |                        | 0.0.1 |
| **`SeekForward`**           | <code>'SEEK_FORWARD'</code>             |                        | 0.0.1 |
| **`PreviousTrack`**         | <code>'PREVIOUS_TRACK'</code>           |                        | 0.0.1 |
| **`NextTrack`**             | <code>'NEXT_TRACK'</code>               |                        | 0.0.1 |
| **`SkipAd`**                | <code>'SKIP_AD'</code>                  | Only available on Web. | 0.0.1 |
| **`Stop`**                  | <code>'STOP'</code>                     |                        | 0.0.1 |
| **`SeekTo`**                | <code>'SEEK_TO'</code>                  |                        | 0.0.1 |
| **`ToggleMicrophone`**      | <code>'TOGGLE_MICROPHONE'</code>        | Only available on Web. | 0.0.1 |
| **`ToggleCamera`**          | <code>'TOGGLE_CAMERA'</code>            | Only available on Web. | 0.0.1 |
| **`ToggleScreenShare`**     | <code>'TOGGLE_SCREEN_SHARE'</code>      | Only available on Web. | 0.0.1 |
| **`HangUp`**                | <code>'HANG_UP'</code>                  | Only available on Web. | 0.0.1 |
| **`PreviousSlide`**         | <code>'PREVIOUS_SLIDE'</code>           | Only available on Web. | 0.0.1 |
| **`NextSlide`**             | <code>'NEXT_SLIDE'</code>               | Only available on Web. | 0.0.1 |
| **`EnterPictureInPicture`** | <code>'ENTER_PICTURE_IN_PICTURE'</code> | Only available on Web. | 0.0.1 |
| **`VoiceActivity`**         | <code>'VOICE_ACTIVITY'</code>           | Only available on Web. | 0.0.1 |


#### MediaSessionPlaybackState

| Members       | Value                  | Since |
| ------------- | ---------------------- | ----- |
| **`None`**    | <code>'NONE'</code>    | 0.0.1 |
| **`Paused`**  | <code>'PAUSED'</code>  | 0.0.1 |
| **`Playing`** | <code>'PLAYING'</code> | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/media-session/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/media-session/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/media-session/LICENSE).
