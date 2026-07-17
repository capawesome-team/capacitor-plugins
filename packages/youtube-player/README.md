# Capacitor YouTube Player Plugin

Unofficial Capacitor plugin to embed and control [YouTube](https://www.youtube.com/) players on Android, iOS, and Web.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor YouTube Player plugin embeds YouTube videos as inline, frame-positioned players in your Capacitor app. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 🎞️ **Inline Playback**: Embed players inline on all platforms, including iOS.
- 🧩 **Multi-Instance**: Create and control multiple players at the same time by ID.
- 📐 **Frame Positioning**: Position and resize players with CSS pixel frames and keep them in sync with your layout.
- 🎛️ **Playback Controls**: Load, cue, play, pause, seek, mute, volume, and playback rate.
- 📡 **Typed Events**: Listen for ready, state, time, rate, error, and fullscreen events with typed listeners.
- 📜 **Terms Compliant**: Built on the official YouTube IFrame Player API with TOS-compliant plumbing (see [YouTube Terms of Service](#youtube-terms-of-service)).
- 🤝 **Compatibility**: Works alongside the [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/) and [Screen Orientation](https://capawesome.io/docs/sdks/capacitor/screen-orientation/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The YouTube Player plugin is typically used wherever you want to embed YouTube videos in your app, for example:

- **Media apps**: Embed trailers, music videos, or episodes inline in your content.
- **Onboarding & help**: Show tutorial and how-to videos directly in your app.
- **News & blogs**: Render YouTube embeds in articles with native playback performance.
- **Education**: Build course screens with multiple video lessons on one page.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-youtube-player` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-youtube-player
npx cap sync
```

### Android

On Android, the plugin uses the [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player) library, an open-source wrapper around the official YouTube IFrame Player API.

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidYoutubePlayerVersion` version of `com.pierfrancescosoffritti.androidyoutubeplayer:core` (default: `13.0.0`)

#### Permissions

The plugin automatically adds the `android.permission.ACCESS_NETWORK_STATE` permission to your app's manifest. It is used to recover the player when the network connection is lost and restored. No action is required on your part.

### iOS

On iOS, the plugin uses the [YoutubePlayerView](https://github.com/mukeshydv/YoutubePlayerView) library, an open-source wrapper around the official YouTube IFrame Player API. It can be integrated via Swift Package Manager or CocoaPods. No additional configuration is required.

### Web

The web implementation loads the [YouTube IFrame Player API](https://developers.google.com/youtube/iframe_api_reference) from YouTube at runtime. This requires an active network connection. If your app enforces a [Content Security Policy (CSP)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP), make sure to allow the YouTube domains (e.g. `https://www.youtube.com` for `script-src` and `frame-src`).

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Create a player

Measure a placeholder element in your layout and create a player at its position:

```typescript
import { YoutubePlayer } from '@capawesome/capacitor-youtube-player';

const createPlayer = async () => {
  const rect = document
    .querySelector('#player-placeholder')
    .getBoundingClientRect();
  await YoutubePlayer.createPlayer({
    id: 'my-player',
    frame: {
      x: rect.x,
      y: rect.y,
      width: rect.width,
      height: rect.height,
    },
    videoId: 'dQw4w9WgXcQ',
    options: {
      mute: true,
    },
  });
};
```

### Control playback

```typescript
import { YoutubePlayer } from '@capawesome/capacitor-youtube-player';

const controlPlayback = async () => {
  await YoutubePlayer.play({ id: 'my-player' });
  await YoutubePlayer.seekTo({ id: 'my-player', seconds: 42 });
  await YoutubePlayer.setVolume({ id: 'my-player', volume: 50 });
  await YoutubePlayer.setPlaybackRate({ id: 'my-player', rate: 1.5 });
  await YoutubePlayer.pause({ id: 'my-player' });
};
```

### Listen for events

```typescript
import { YoutubePlayer } from '@capawesome/capacitor-youtube-player';

const addListeners = async () => {
  await YoutubePlayer.addListener('playerReady', event => {
    console.log('Player ready:', event.id);
  });
  await YoutubePlayer.addListener('playerStateChange', event => {
    console.log('Player state:', event.id, event.state);
  });
  await YoutubePlayer.addListener('playerError', event => {
    console.error('Player error:', event.id, event.code);
  });
};
```

### Remove a player

```typescript
import { YoutubePlayer } from '@capawesome/capacitor-youtube-player';

const removePlayer = async () => {
  await YoutubePlayer.removePlayer({ id: 'my-player' });
};
```

## API

<docgen-index>

* [`createPlayer(...)`](#createplayer)
* [`cueVideo(...)`](#cuevideo)
* [`getCurrentTime(...)`](#getcurrenttime)
* [`getDuration(...)`](#getduration)
* [`loadVideo(...)`](#loadvideo)
* [`mute(...)`](#mute)
* [`pause(...)`](#pause)
* [`play(...)`](#play)
* [`removePlayer(...)`](#removeplayer)
* [`seekTo(...)`](#seekto)
* [`setPlaybackRate(...)`](#setplaybackrate)
* [`setPlayerFrame(...)`](#setplayerframe)
* [`setVolume(...)`](#setvolume)
* [`unmute(...)`](#unmute)
* [`addListener('currentTimeChange', ...)`](#addlistenercurrenttimechange-)
* [`addListener('fullscreenChange', ...)`](#addlistenerfullscreenchange-)
* [`addListener('playbackRateChange', ...)`](#addlistenerplaybackratechange-)
* [`addListener('playerError', ...)`](#addlistenerplayererror-)
* [`addListener('playerReady', ...)`](#addlistenerplayerready-)
* [`addListener('playerStateChange', ...)`](#addlistenerplayerstatechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### createPlayer(...)

```typescript
createPlayer(options: CreatePlayerOptions) => Promise<CreatePlayerResult>
```

Create a new YouTube player.

On Android and iOS, the player is rendered as a native view that is
positioned above the web view at the given frame. The frame is not
scrolled with the web content. Use `setPlayerFrame(...)` to keep the
frame in sync with your layout (see the README for a recipe).

The player must be at least 200×200 CSS pixels, as required by the
[YouTube Terms of Service](https://developers.google.com/youtube/terms/required-minimum-functionality#embedded-player-size).

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#createplayeroptions">CreatePlayerOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createplayerresult">CreatePlayerResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### cueVideo(...)

```typescript
cueVideo(options: CueVideoOptions) => Promise<void>
```

Load a video into the player without starting playback.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#cuevideooptions">CueVideoOptions</a></code> |

**Since:** 0.1.0

--------------------


### getCurrentTime(...)

```typescript
getCurrentTime(options: GetCurrentTimeOptions) => Promise<GetCurrentTimeResult>
```

Get the current playback time of the player.

On Android, the value is answered from the most recent value pushed by
the player (updated multiple times per second during playback).

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcurrenttimeoptions">GetCurrentTimeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcurrenttimeresult">GetCurrentTimeResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getDuration(...)

```typescript
getDuration(options: GetDurationOptions) => Promise<GetDurationResult>
```

Get the duration of the currently loaded video.

On Android, the value is answered from the most recent value pushed by
the player.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getdurationoptions">GetDurationOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getdurationresult">GetDurationResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### loadVideo(...)

```typescript
loadVideo(options: LoadVideoOptions) => Promise<void>
```

Load a video into the player and start playback.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#loadvideooptions">LoadVideoOptions</a></code> |

**Since:** 0.1.0

--------------------


### mute(...)

```typescript
mute(options: MuteOptions) => Promise<void>
```

Mute the player.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#muteoptions">MuteOptions</a></code> |

**Since:** 0.1.0

--------------------


### pause(...)

```typescript
pause(options: PauseOptions) => Promise<void>
```

Pause playback.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#pauseoptions">PauseOptions</a></code> |

**Since:** 0.1.0

--------------------


### play(...)

```typescript
play(options: PlayOptions) => Promise<void>
```

Start or resume playback.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#playoptions">PlayOptions</a></code> |

**Since:** 0.1.0

--------------------


### removePlayer(...)

```typescript
removePlayer(options: RemovePlayerOptions) => Promise<void>
```

Remove the player and release all its resources.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#removeplayeroptions">RemovePlayerOptions</a></code> |

**Since:** 0.1.0

--------------------


### seekTo(...)

```typescript
seekTo(options: SeekToOptions) => Promise<void>
```

Seek to the given time.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#seektooptions">SeekToOptions</a></code> |

**Since:** 0.1.0

--------------------


### setPlaybackRate(...)

```typescript
setPlaybackRate(options: SetPlaybackRateOptions) => Promise<void>
```

Set the playback rate of the player.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setplaybackrateoptions">SetPlaybackRateOptions</a></code> |

**Since:** 0.1.0

--------------------


### setPlayerFrame(...)

```typescript
setPlayerFrame(options: SetPlayerFrameOptions) => Promise<void>
```

Update the frame of the player.

Call this method whenever the layout changes (e.g. on scroll, resize or
orientation change) to keep the player in sync with your layout.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#setplayerframeoptions">SetPlayerFrameOptions</a></code> |

**Since:** 0.1.0

--------------------


### setVolume(...)

```typescript
setVolume(options: SetVolumeOptions) => Promise<void>
```

Set the volume of the player.

On iOS, the operating system does not allow changing the volume
programmatically, so this method has no effect. Use `mute()` and
`unmute()` instead.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setvolumeoptions">SetVolumeOptions</a></code> |

**Since:** 0.1.0

--------------------


### unmute(...)

```typescript
unmute(options: UnmuteOptions) => Promise<void>
```

Unmute the player.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#unmuteoptions">UnmuteOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('currentTimeChange', ...)

```typescript
addListener(eventName: 'currentTimeChange', listenerFunc: (event: CurrentTimeChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the current playback time of a player changes.

The event is emitted multiple times per second during playback. The
exact frequency depends on the platform.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'currentTimeChange'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#currenttimechangeevent">CurrentTimeChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('fullscreenChange', ...)

```typescript
addListener(eventName: 'fullscreenChange', listenerFunc: (event: FullscreenChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when a player enters or exits fullscreen.

Only available on Android and Web.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'fullscreenChange'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#fullscreenchangeevent">FullscreenChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('playbackRateChange', ...)

```typescript
addListener(eventName: 'playbackRateChange', listenerFunc: (event: PlaybackRateChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the playback rate of a player changes.

On iOS, this event is only emitted for `setPlaybackRate(...)` calls.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'playbackRateChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#playbackratechangeevent">PlaybackRateChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('playerError', ...)

```typescript
addListener(eventName: 'playerError', listenerFunc: (event: PlayerErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs in a player.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'playerError'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#playererrorevent">PlayerErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('playerReady', ...)

```typescript
addListener(eventName: 'playerReady', listenerFunc: (event: PlayerReadyEvent) => void) => Promise<PluginListenerHandle>
```

Called when a player has finished loading and is ready to receive
commands.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'playerReady'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#playerreadyevent">PlayerReadyEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('playerStateChange', ...)

```typescript
addListener(eventName: 'playerStateChange', listenerFunc: (event: PlayerStateChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the state of a player changes.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'playerStateChange'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#playerstatechangeevent">PlayerStateChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### CreatePlayerResult

| Prop     | Type                | Description                                  | Since |
| -------- | ------------------- | -------------------------------------------- | ----- |
| **`id`** | <code>string</code> | The unique identifier of the created player. | 0.1.0 |


#### CreatePlayerOptions

| Prop          | Type                                                                      | Description                                                                                                                                                     | Since |
| ------------- | ------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`frame`**   | <code><a href="#playerframe">PlayerFrame</a></code>                       | The frame of the player in CSS pixels, relative to the viewport. Must be at least 200×200 CSS pixels.                                                           | 0.1.0 |
| **`id`**      | <code>string</code>                                                       | The unique identifier of the player. If not provided, a random identifier is generated.                                                                         | 0.1.0 |
| **`options`** | <code><a href="#playeroptions">PlayerOptions</a></code>                   | The player options.                                                                                                                                             | 0.1.0 |
| **`videoId`** | <code>string</code>                                                       | The ID of the YouTube video to load into the player. If not provided, the player is created without a video. Load one with `loadVideo(...)` or `cueVideo(...)`. | 0.1.0 |
| **`web`**     | <code><a href="#createplayerweboptions">CreatePlayerWebOptions</a></code> | Options that are only available on Web.                                                                                                                         | 0.1.0 |


#### PlayerFrame

The frame of a player in CSS pixels, relative to the viewport.

| Prop         | Type                | Description                                                     | Since |
| ------------ | ------------------- | --------------------------------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the player in CSS pixels. Must be at least `200`. | 0.1.0 |
| **`width`**  | <code>number</code> | The width of the player in CSS pixels. Must be at least `200`.  | 0.1.0 |
| **`x`**      | <code>number</code> | The x-coordinate of the player in CSS pixels.                   | 0.1.0 |
| **`y`**      | <code>number</code> | The y-coordinate of the player in CSS pixels.                   | 0.1.0 |


#### PlayerOptions

| Prop               | Type                 | Description                                                                                                                             | Default            | Since |
| ------------------ | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`autoplay`**     | <code>boolean</code> | Whether the video starts playing automatically when the player is created.                                                              | <code>false</code> | 0.1.0 |
| **`ccLoadPolicy`** | <code>boolean</code> | Whether closed captions are shown by default, even if the user has turned captions off.                                                 | <code>false</code> | 0.1.0 |
| **`controls`**     | <code>boolean</code> | Whether the player controls are displayed.                                                                                              | <code>true</code>  | 0.1.0 |
| **`end`**          | <code>number</code>  | The time in seconds at which the player should stop playing the video.                                                                  |                    | 0.1.0 |
| **`fullscreen`**   | <code>boolean</code> | Whether the fullscreen button is displayed. Only available on Android and Web.                                                          | <code>false</code> | 0.1.0 |
| **`ivLoadPolicy`** | <code>boolean</code> | Whether video annotations are shown by default.                                                                                         | <code>false</code> | 0.1.0 |
| **`mute`**         | <code>boolean</code> | Whether the player is muted when it is created.                                                                                         | <code>false</code> | 0.1.0 |
| **`rel`**          | <code>boolean</code> | Whether related videos from other channels are shown when playback ends. If `false`, related videos are limited to the video's channel. | <code>false</code> | 0.1.0 |
| **`start`**        | <code>number</code>  | The time in seconds from which the video should start playing.                                                                          |                    | 0.1.0 |


#### CreatePlayerWebOptions

| Prop            | Type                | Description                                                                                                                                                                                                            | Since |
| --------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`elementId`** | <code>string</code> | The ID of an existing DOM element to mount the player into. If provided, the player fills this element instead of being positioned at the given frame, and `setPlayerFrame(...)` has no effect. Only available on Web. | 0.1.0 |


#### CueVideoOptions

| Prop               | Type                | Description                                                                      | Default        | Since |
| ------------------ | ------------------- | -------------------------------------------------------------------------------- | -------------- | ----- |
| **`id`**           | <code>string</code> | The unique identifier of the player.                                             |                | 0.1.0 |
| **`startSeconds`** | <code>number</code> | The time in seconds from which the video should start playing once it is played. | <code>0</code> | 0.1.0 |
| **`videoId`**      | <code>string</code> | The ID of the YouTube video to cue.                                              |                | 0.1.0 |


#### GetCurrentTimeResult

| Prop              | Type                | Description                           | Since |
| ----------------- | ------------------- | ------------------------------------- | ----- |
| **`currentTime`** | <code>number</code> | The current playback time in seconds. | 0.1.0 |


#### GetCurrentTimeOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### GetDurationResult

| Prop           | Type                | Description                                                                               | Since |
| -------------- | ------------------- | ----------------------------------------------------------------------------------------- | ----- |
| **`duration`** | <code>number</code> | The duration of the currently loaded video in seconds. Returns `0` if no video is loaded. | 0.1.0 |


#### GetDurationOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### LoadVideoOptions

| Prop               | Type                | Description                                                    | Default        | Since |
| ------------------ | ------------------- | -------------------------------------------------------------- | -------------- | ----- |
| **`id`**           | <code>string</code> | The unique identifier of the player.                           |                | 0.1.0 |
| **`startSeconds`** | <code>number</code> | The time in seconds from which the video should start playing. | <code>0</code> | 0.1.0 |
| **`videoId`**      | <code>string</code> | The ID of the YouTube video to load.                           |                | 0.1.0 |


#### MuteOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### PauseOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### PlayOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### RemovePlayerOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### SeekToOptions

| Prop          | Type                | Description                          | Since |
| ------------- | ------------------- | ------------------------------------ | ----- |
| **`id`**      | <code>string</code> | The unique identifier of the player. | 0.1.0 |
| **`seconds`** | <code>number</code> | The time in seconds to seek to.      | 0.1.0 |


#### SetPlaybackRateOptions

| Prop       | Type                | Description                                                                                 | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------- | ----- |
| **`id`**   | <code>string</code> | The unique identifier of the player.                                                        | 0.1.0 |
| **`rate`** | <code>number</code> | The playback rate. Must be one of `0.25`, `0.5`, `0.75`, `1`, `1.25`, `1.5`, `1.75` or `2`. | 0.1.0 |


#### SetPlayerFrameOptions

| Prop        | Type                                                | Description                                                                                               | Since |
| ----------- | --------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`frame`** | <code><a href="#playerframe">PlayerFrame</a></code> | The new frame of the player in CSS pixels, relative to the viewport. Must be at least 200×200 CSS pixels. | 0.1.0 |
| **`id`**    | <code>string</code>                                 | The unique identifier of the player.                                                                      | 0.1.0 |


#### SetVolumeOptions

| Prop         | Type                | Description                                  | Since |
| ------------ | ------------------- | -------------------------------------------- | ----- |
| **`id`**     | <code>string</code> | The unique identifier of the player.         | 0.1.0 |
| **`volume`** | <code>number</code> | The volume as a value between `0` and `100`. | 0.1.0 |


#### UnmuteOptions

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### CurrentTimeChangeEvent

| Prop              | Type                | Description                           | Since |
| ----------------- | ------------------- | ------------------------------------- | ----- |
| **`currentTime`** | <code>number</code> | The current playback time in seconds. | 0.1.0 |
| **`id`**          | <code>string</code> | The unique identifier of the player.  | 0.1.0 |


#### FullscreenChangeEvent

| Prop             | Type                 | Description                          | Since |
| ---------------- | -------------------- | ------------------------------------ | ----- |
| **`fullscreen`** | <code>boolean</code> | Whether the player is in fullscreen. | 0.1.0 |
| **`id`**         | <code>string</code>  | The unique identifier of the player. | 0.1.0 |


#### PlaybackRateChangeEvent

| Prop       | Type                | Description                          | Since |
| ---------- | ------------------- | ------------------------------------ | ----- |
| **`id`**   | <code>string</code> | The unique identifier of the player. | 0.1.0 |
| **`rate`** | <code>number</code> | The new playback rate.               | 0.1.0 |


#### PlayerErrorEvent

| Prop       | Type                                                        | Description                          | Since |
| ---------- | ----------------------------------------------------------- | ------------------------------------ | ----- |
| **`code`** | <code><a href="#playererrorcode">PlayerErrorCode</a></code> | The error code.                      | 0.1.0 |
| **`id`**   | <code>string</code>                                         | The unique identifier of the player. | 0.1.0 |


#### PlayerReadyEvent

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the player. | 0.1.0 |


#### PlayerStateChangeEvent

| Prop        | Type                                                | Description                          | Since |
| ----------- | --------------------------------------------------- | ------------------------------------ | ----- |
| **`id`**    | <code>string</code>                                 | The unique identifier of the player. | 0.1.0 |
| **`state`** | <code><a href="#playerstate">PlayerState</a></code> | The new state of the player.         | 0.1.0 |


### Type Aliases


#### PlayerErrorCode

The error code of a player error.

<code>'html5-error' | 'invalid-parameter' | 'not-embeddable' | 'unknown' | 'video-not-found'</code>


#### PlayerState

The state of a player.

<code>'buffering' | 'cued' | 'ended' | 'paused' | 'playing' | 'unstarted'</code>

</docgen-api>

## Frame Synchronization

On Android and iOS, the player is a native view that is rendered **above** the web view. It is not part of the DOM and therefore does not scroll or resize with your web content. Instead, you position it with CSS pixel frames and keep it in sync with your layout by calling `setPlayerFrame(...)` whenever the layout changes:

```typescript
import { YoutubePlayer } from '@capawesome/capacitor-youtube-player';

const syncPlayerFrame = async () => {
  const rect = document
    .querySelector('#player-placeholder')
    .getBoundingClientRect();
  await YoutubePlayer.setPlayerFrame({
    id: 'my-player',
    frame: {
      x: rect.x,
      y: rect.y,
      width: rect.width,
      height: rect.height,
    },
  });
};

window.addEventListener('scroll', syncPlayerFrame);
window.addEventListener('resize', syncPlayerFrame);
```

If you use a framework with its own scroll container (e.g. `ion-content`), attach the listener to that container's scroll event instead. On the Web, the player is positioned with `position: fixed`, so the same synchronization code works on all platforms. Alternatively, on the Web you can mount the player into an existing DOM element using the `web.elementId` option, in which case no frame synchronization is needed.

Since the player is rendered above the web view, HTML content can never overlap the player. Keep this in mind when designing your layout (see also [YouTube Terms of Service](#youtube-terms-of-service)).

## YouTube Terms of Service

By using this plugin, you agree to the [YouTube Terms of Service](https://www.youtube.com/t/terms) and the [YouTube API Services Terms of Service](https://developers.google.com/youtube/terms/api-services-terms-of-service). The plugin is designed to make it easy to comply with the [required minimum functionality](https://developers.google.com/youtube/terms/required-minimum-functionality) for embedded players:

- **Minimum player size**: Embedded players must be at least 200×200 pixels. The plugin enforces this and rejects smaller frames with the `FRAME_INVALID` error code.
- **No background playback**: Videos must not play in the background. The plugin automatically pauses all players when the app is moved to the background.
- **No overlays**: The player (including its ads) must not be obscured by other content. Since the native player view is always rendered above the web view, HTML content cannot overlap it by design.
- **Correct origin**: The plugin sets the `origin` player parameter correctly on all platforms, which avoids embed errors (e.g. error 152/153) without any header workarounds.
- **No player modification**: The plugin embeds the official YouTube player unaltered. Your app must not modify, build upon, or block any portion or functionality of the player — including any ads it serves.
- **Branding**: If your app displays YouTube logos or other brand assets alongside the player, follow the [YouTube Branding Guidelines](https://developers.google.com/youtube/terms/branding-guidelines).

## Platform Support

Not every feature is available on all platforms. The following table lists the per-platform differences of the plugin's API:

| Feature                            | Android | iOS | Web |
| ---------------------------------- | :-----: | :-: | :-: |
| Playback controls                  |   ✅    | ✅  | ✅  |
| `fullscreen` option                |   ✅    | ❌  | ✅  |
| `fullscreenChange` event           |   ✅    | ❌  | ✅  |
| Native mute                        |   ✅    | 🔄  | ✅  |

Additional platform-specific behavior:

- **iOS mute emulation**: The iOS player library exposes no native mute API, so `mute()` is emulated by setting the volume to `0`. `unmute()` restores the last volume set via `setVolume(...)` (or `100`). If `setVolume(...)` is called while the player is muted, the volume is only applied on the next `unmute()` call.
- **iOS playback rate event**: On iOS, the `playbackRateChange` event is only emitted for `setPlaybackRate(...)` calls, since the iOS player library exposes no playback rate callback.
- **iOS error mapping**: On iOS, videos that are not allowed to be played in embedded players may be reported with the error code `unknown` instead of `not-embeddable`, due to the error mapping of the iOS player library.
- **Android getters**: On Android, `getCurrentTime(...)` and `getDuration(...)` are answered from the most recent values pushed by the player, which are updated multiple times per second during playback.
- **`currentTimeChange` frequency**: The event is emitted about 10 times per second on Android and about twice per second on iOS and Web.

## FAQ

### How is this plugin different from other similar plugins?

This plugin can render multiple native player instances at once, each positioned to an exact frame that you keep in sync with your layout. It exposes a full, strongly typed event set on Android, iOS, and Web, and embeds the official YouTube player unaltered. It is built to comply with the behaviors the YouTube Terms of Service require — a minimum player size, no background playback, nothing drawn over the player, and a correct origin — so you stay compliant by default. It is actively maintained and backed by dedicated support.

### Why is my frame rejected with `FRAME_INVALID`?

Embedded YouTube players must be at least 200×200 CSS pixels, as required by the YouTube Terms of Service. Make sure the `width` and `height` of your frame are at least `200`.

### Why does the player not scroll with my content?

On Android and iOS, the player is a native view rendered above the web view and is not part of the DOM. Use `setPlayerFrame(...)` to keep it in sync with your layout (see [Frame Synchronization](#frame-synchronization)).

### Can I display HTML content above the player?

No. The native player view is always rendered above the web view. This is also a requirement of the YouTube Terms of Service, which prohibit obscuring the player.

### Does playback continue in the background?

No. The YouTube Terms of Service prohibit background playback, so the plugin automatically pauses all players when the app is moved to the background.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/): Interact with media controllers, volume keys and media buttons.
- [Screen Orientation](https://capawesome.io/docs/sdks/capacitor/screen-orientation/): Lock and unlock the screen orientation, for example to allow landscape playback.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/youtube-player/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/youtube-player/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or YouTube LLC or any of their affiliates or subsidiaries. "YouTube" is a trademark of Google LLC.
