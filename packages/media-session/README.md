# Capacitor Media Session Plugin

Capacitor plugin to interact with media controllers, volume keys and media buttons.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Media Session plugin is one of the most complete media playback integration solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🎮 **Media Controls**: Handle hardware media keys, lock screen controls, and notification controls.
- 🎵 **Rich Metadata**: Display song title, artist, album, and artwork on lock screen and notifications.
- 🎨 **Customizable Icon**: Configure the notification icon on Android to match your app's branding.
- ▶️ **Action Handlers**: Support for play, pause, seek, next/previous track, and more.
- 📍 **Position State**: Track and display playback position, duration, and playback rate.
- 🔧 **Native APIs**: Uses MediaSession API on Android and MPNowPlayingInfoCenter on iOS for the best possible integration.
- 🤝 **Compatibility**: Compatible with the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/) plugin.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Media Session plugin is typically used in apps that play media and want to integrate with the operating system's media controls, for example:

- **Music streaming apps**: Display the current track's title, artist, album, and artwork on the lock screen and respond to play, pause, and track change actions.
- **Podcast and audiobook players**: Let users seek forward and backward with configurable seek offsets and display the current playback position and duration.
- **Video call apps**: Handle the microphone toggle, camera toggle, and hang up actions on the Web.
- **Presentation apps**: Respond to the previous and next slide actions on the Web.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 0.1.x          | 7.x.x             | Deprecated     |

## Demo

| Android                                                                                                                   | iOS                                                                                                               | Web                                                                                                               |
| ------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| <img src="https://capawesome.io/docs/assets/images/gifs/capacitor-media-session-android.gif" width="324" alt="Android Demo" /> | <img src="https://capawesome.io/docs/assets/images/gifs/capacitor-media-session-ios.gif" width="266" alt="iOS Demo" /> | <img src="https://capawesome.io/docs/assets/images/gifs/capacitor-media-session-web.gif" width="324" alt="Web Demo" /> |

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-media-session` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-media-session
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidMediaVersion` version of `androidx.media:media` (default: `1.7.1`)

## Configuration

These configuration options are available:

| Prop          | Type     | Description                                                                                                                                                                                                                                               | Default           | Since |
| ------------- | -------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **smallIcon** | `string` | The name of the drawable resource to use as the small icon in the media notification. Only available on Android. The resource name should not include the `R.drawable.` prefix or file extension. If the resource is not found, the default icon is used. | `"ic_media_play"` | 8.1.0 |

### Examples

In `capacitor.config.ts`:

```ts
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    MediaSession: {
      smallIcon: 'ic_notification',
    },
  },
};

export default config;
```

In `capacitor.config.json`:

```json
{
  "plugins": {
    "MediaSession": {
      "smallIcon": "ic_notification"
    }
  }
}
```

### Android Custom Icon Setup

To use a custom notification icon on Android:

1. Add your icon to `android/app/src/main/res/drawable/` (e.g., `ic_notification.png`)
   - Icon should be single-color white with transparent background for best display
   - Can use density-specific folders (`drawable-mdpi`, `drawable-hdpi`, etc.)

2. Configure the plugin in `capacitor.config.ts`:
   ```ts
   const config: CapacitorConfig = {
     plugins: {
       MediaSession: {
         smallIcon: 'ic_notification',  // Matches ic_notification.png
       },
     },
   };
   ```

3. Run: `npx cap sync`

## Usage

The following examples show how to display track metadata, register handlers for media actions, update the playback and position state, listen for media session actions, and unregister handlers and remove listeners, using the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/) plugin for the actual audio playback.

### Display metadata about the current track

Set the title, artist, album, and artwork that the operating system displays on the lock screen and in notifications. Note that the `artwork` option is only available on iOS and Web:

```typescript
import { MediaSession } from '@capawesome-team/capacitor-media-session';

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
```

### Register handlers for media actions

Register a handler for each media session action you want to support. The media session only responds to an action if a handler is registered for it:

```typescript
import { MediaSession, MediaSessionAction } from '@capawesome-team/capacitor-media-session';

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
```

### Update the playback state

Tell the operating system whether media is currently playing, paused, or stopped so that the media controls reflect the correct state:

```typescript
import { MediaSession, MediaSessionPlaybackState } from '@capawesome-team/capacitor-media-session';

const setPlaybackState = async () => {
  await MediaSession.setPlaybackState({
    playbackState: MediaSessionPlaybackState.Playing,
  });
};
```

### Update the position state

Report the duration, playback rate, and current position of the media in seconds so that the operating system can display a progress bar:

```typescript
import { MediaSession } from '@capawesome-team/capacitor-media-session';

const setPositionState = async () => {
  await MediaSession.setPositionState({
    duration: 180,
    playbackRate: 1.0,
    position: 30,
  });
};
```

### Listen for media session actions

Add an `action` event listener to react to the registered actions, for example when the user presses a hardware media key or a button on the lock screen:

```typescript
import { MediaSession, MediaSessionAction, MediaSessionPlaybackState } from '@capawesome-team/capacitor-media-session';
import { AudioPlayer } from '@capawesome-team/capacitor-audio-player';

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
```

### Unregister action handlers and remove listeners

Make sure to unregister action handlers when they are no longer needed and remove your event listeners:

```typescript
import { MediaSession, MediaSessionAction } from '@capawesome-team/capacitor-media-session';

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
* [`setSeekOffset(...)`](#setseekoffset)
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


### setSeekOffset(...)

```typescript
setSeekOffset(options: SetSeekOffsetOptions) => Promise<void>
```

Set the seek offset for the `SeekForward` and `SeekBackward` actions.

The offset controls both the value emitted in the `action` event's
`seekOffset` property and the offset displayed by the OS (e.g., the
number badge on the iOS lock-screen skip button).

Can be called at any time; the new offset is applied immediately.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#setseekoffsetoptions">SetSeekOffsetOptions</a></code> |

**Since:** 8.3.0

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


#### SetSeekOffsetOptions

| Prop                     | Type                | Description                                                                                                                | Default         | Since |
| ------------------------ | ------------------- | -------------------------------------------------------------------------------------------------------------------------- | --------------- | ----- |
| **`seekBackwardOffset`** | <code>number</code> | The offset in seconds for the `SeekBackward` action. Must be greater than `0`. If not provided, the current value is kept. | <code>10</code> | 8.3.0 |
| **`seekForwardOffset`**  | <code>number</code> | The offset in seconds for the `SeekForward` action. Must be greater than `0`. If not provided, the current value is kept.  | <code>10</code> | 8.3.0 |


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

## FAQ

### How do I show playback controls on the lock screen?

Set the metadata for the current track using `setMetadata`, register handlers for the actions you want to support using `registerActionHandler`, and update the playback state using `setPlaybackState`. The plugin uses the MediaSession API on Android and MPNowPlayingInfoCenter on iOS, so the operating system takes care of displaying the controls on the lock screen and in notifications. See the [usage examples](#usage) above for details.

### Why is the artwork not displayed on Android?

The `artwork` option of the `setMetadata` method is only available on iOS and Web. On Android, you can configure the notification icon instead using the `smallIcon` configuration option.

### Can I customize the notification icon on Android?

Yes, use the `smallIcon` configuration option to set the name of a drawable resource from your app's `res/drawable` directory. The icon should be single-color white with a transparent background for the best display. If the resource is not found, the default icon is used. See the [Configuration](#configuration) section for details.

### Can I use this plugin together with the Audio Player plugin?

Yes, the plugin is compatible with the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/) plugin. A common setup is to play audio with the Audio Player plugin and use the Media Session plugin to display metadata and respond to media actions, as shown in the [usage examples](#usage) above.

### Why does the media session not respond to certain actions?

The media session only responds to an action if a handler was registered for it using `registerActionHandler`. Also note that some actions such as `SkipAd`, `ToggleMicrophone`, `ToggleCamera`, `ToggleScreenShare`, `HangUp`, `PreviousSlide`, `NextSlide`, `EnterPictureInPicture` and `VoiceActivity` are only available on the Web.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/): Play audio with background support.
- [Audio Session](https://capawesome.io/docs/sdks/capacitor/audio-session/): Configure and observe the iOS audio session.
- [Volume](https://capawesome.io/docs/sdks/capacitor/volume/): Control the volume and observe hardware volume button presses.
- [YouTube Player](https://capawesome.io/docs/sdks/capacitor/youtube-player/): Embed and control YouTube players, with lock-screen media controls via this plugin.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/media-session/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/media-session/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/media-session/LICENSE).
