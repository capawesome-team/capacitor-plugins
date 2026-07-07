# Capacitor Volume Plugin

Capacitor plugin to control the volume and observe hardware volume button presses.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔊 **Volume control**: Get and set the volume level.
- 🎚️ **Audio streams**: Control individual audio streams (music, ring, alarm and more) on Android.
- 🔘 **Volume buttons**: Listen for hardware volume button presses.
- 🤫 **Suppression**: Keep the volume unchanged and hide the system volume indicator while watching.
- 👂 **Change events**: Listen for changes to the volume level.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-volume` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-volume
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On the Web, all methods reject as unimplemented.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Volume, VolumeStream } from '@capawesome/capacitor-volume';

const getVolume = async () => {
  const { volume } = await Volume.getVolume();
  return volume;
};

const getRingVolume = async () => {
  const { volume } = await Volume.getVolume({ stream: VolumeStream.Ring });
  return volume;
};

const setVolume = async () => {
  await Volume.setVolume({ volume: 0.5 });
};

const startWatching = async () => {
  await Volume.startWatching({ suppressVolumeChange: true });
};

const stopWatching = async () => {
  await Volume.stopWatching();
};

const isWatching = async () => {
  const { watching } = await Volume.isWatching();
  return watching;
};

const addVolumeButtonPressedListener = async () => {
  await Volume.addListener('volumeButtonPressed', event => {
    console.log('Volume button pressed:', event.direction);
  });
};

const addVolumeChangeListener = async () => {
  await Volume.addListener('volumeChange', event => {
    console.log('Volume changed:', event.volume);
  });
};

const removeAllListeners = async () => {
  await Volume.removeAllListeners();
};
```

## API

<docgen-index>

* [`getVolume(...)`](#getvolume)
* [`isWatching()`](#iswatching)
* [`setVolume(...)`](#setvolume)
* [`startWatching(...)`](#startwatching)
* [`stopWatching()`](#stopwatching)
* [`addListener('volumeButtonPressed', ...)`](#addlistenervolumebuttonpressed-)
* [`addListener('volumeChange', ...)`](#addlistenervolumechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getVolume(...)

```typescript
getVolume(options?: GetVolumeOptions | undefined) => Promise<GetVolumeResult>
```

Get the current volume level.

On iOS, this always returns the media volume.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#getvolumeoptions">GetVolumeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getvolumeresult">GetVolumeResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isWatching()

```typescript
isWatching() => Promise<IsWatchingResult>
```

Check whether the hardware volume buttons are currently being watched.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#iswatchingresult">IsWatchingResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### setVolume(...)

```typescript
setVolume(options: SetVolumeOptions) => Promise<void>
```

Set the volume level.

On iOS, this always sets the media volume.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setvolumeoptions">SetVolumeOptions</a></code> |

**Since:** 0.1.0

--------------------


### startWatching(...)

```typescript
startWatching(options?: StartWatchingOptions | undefined) => Promise<void>
```

Start watching the hardware volume buttons.

The `volumeButtonPressed` and `volumeChange` events are only
emitted while watching.

If the volume buttons are already being watched, this call has
no effect. Call `stopWatching()` first to change the options.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#startwatchingoptions">StartWatchingOptions</a></code> |

**Since:** 0.1.0

--------------------


### stopWatching()

```typescript
stopWatching() => Promise<void>
```

Stop watching the hardware volume buttons.

On iOS, this also restores the volume level that was set when
watching started if the `suppressVolumeChange` option was enabled.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### addListener('volumeButtonPressed', ...)

```typescript
addListener(eventName: 'volumeButtonPressed', listenerFunc: (event: VolumeButtonPressedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a hardware volume button is pressed while watching.

Only available on Android and iOS.

| Param              | Type                                                                                              |
| ------------------ | ------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'volumeButtonPressed'</code>                                                                |
| **`listenerFunc`** | <code>(event: <a href="#volumebuttonpressedevent">VolumeButtonPressedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('volumeChange', ...)

```typescript
addListener(eventName: 'volumeChange', listenerFunc: (event: VolumeChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the volume level changes while watching.

On Android, this is called for changes to the music stream.
On iOS, this is called for changes to the media volume and is
not called while the `suppressVolumeChange` option is enabled.

Only available on Android and iOS.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'volumeChange'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#volumechangeevent">VolumeChangeEvent</a>) =&gt; void</code> |

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


#### GetVolumeResult

| Prop         | Type                | Description                                              | Since |
| ------------ | ------------------- | -------------------------------------------------------- | ----- |
| **`volume`** | <code>number</code> | The current volume level as a value between `0` and `1`. | 0.1.0 |


#### GetVolumeOptions

| Prop         | Type                                                  | Description                                                        | Default                         | Since |
| ------------ | ----------------------------------------------------- | ------------------------------------------------------------------ | ------------------------------- | ----- |
| **`stream`** | <code><a href="#volumestream">VolumeStream</a></code> | The audio stream to get the volume for. Only available on Android. | <code>VolumeStream.Music</code> | 0.1.0 |


#### IsWatchingResult

| Prop           | Type                 | Description                                                      | Since |
| -------------- | -------------------- | ---------------------------------------------------------------- | ----- |
| **`watching`** | <code>boolean</code> | Whether the hardware volume buttons are currently being watched. | 0.1.0 |


#### SetVolumeOptions

| Prop         | Type                                                  | Description                                                        | Default                         | Since |
| ------------ | ----------------------------------------------------- | ------------------------------------------------------------------ | ------------------------------- | ----- |
| **`stream`** | <code><a href="#volumestream">VolumeStream</a></code> | The audio stream to set the volume for. Only available on Android. | <code>VolumeStream.Music</code> | 0.1.0 |
| **`volume`** | <code>number</code>                                   | The volume level to set as a value between `0` and `1`.            |                                 | 0.1.0 |


#### StartWatchingOptions

| Prop                       | Type                 | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Default            | Since |
| -------------------------- | -------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`suppressVolumeChange`** | <code>boolean</code> | Whether to keep the volume level unchanged when a hardware volume button is pressed while watching. On Android, the volume key events are consumed so that the system does not apply the volume change or display the volume panel. On iOS, the volume level is reset immediately after each button press and the system volume indicator is hidden. If the volume level is close to the minimum or maximum, it is nudged to a value from which both buttons can still be detected. The original volume level is restored when watching stops. | <code>false</code> | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### VolumeButtonPressedEvent

| Prop            | Type                                            | Description                                          | Since |
| --------------- | ----------------------------------------------- | ---------------------------------------------------- | ----- |
| **`direction`** | <code><a href="#direction">Direction</a></code> | The direction of the pressed hardware volume button. | 0.1.0 |


#### VolumeChangeEvent

| Prop         | Type                | Description                                          | Since |
| ------------ | ------------------- | ---------------------------------------------------- | ----- |
| **`volume`** | <code>number</code> | The new volume level as a value between `0` and `1`. | 0.1.0 |


### Type Aliases


#### Direction

The direction of a hardware volume button.

- `up`: The volume up button.
- `down`: The volume down button.

<code>'down' | 'up'</code>


### Enums


#### VolumeStream

| Members            | Value                       | Description                                    | Since |
| ------------------ | --------------------------- | ---------------------------------------------- | ----- |
| **`Alarm`**        | <code>'ALARM'</code>        | The audio stream for alarms.                   | 0.1.0 |
| **`Music`**        | <code>'MUSIC'</code>        | The audio stream for music and media playback. | 0.1.0 |
| **`Notification`** | <code>'NOTIFICATION'</code> | The audio stream for notifications.            | 0.1.0 |
| **`Ring`**         | <code>'RING'</code>         | The audio stream for the phone ring.           | 0.1.0 |
| **`System`**       | <code>'SYSTEM'</code>       | The audio stream for system sounds.            | 0.1.0 |
| **`VoiceCall`**    | <code>'VOICE_CALL'</code>   | The audio stream for phone calls.              | 0.1.0 |

</docgen-api>

## Volume Control

Keep the following platform differences in mind when getting and setting the volume:

- **Android**: The volume can be read and set per audio stream (see the `stream` option). If Do Not Disturb is active, changing the volume of the ring, notification or system stream requires Do Not Disturb access. Without this access, the call is rejected with the `DO_NOT_DISTURB_ACCESS_REQUIRED` error code. You can direct the user to the corresponding settings screen using the [`ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS`](https://developer.android.com/reference/android/provider/Settings#ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS) intent, for example with the [App Launcher](https://capacitorjs.com/docs/apis/app-launcher) plugin.
- **iOS**: There is no public API to set the system volume directly. The plugin therefore uses a hidden system volume view to change the media volume, which is the only volume that can be read and set. The `stream` option is ignored.

## Volume Button Watching

Keep the following platform differences in mind when watching the hardware volume buttons:

- **Android**: The volume key events are intercepted by the web view, so button presses are only detected while the app is in the foreground. With the `suppressVolumeChange` option enabled, the key events are consumed, which keeps the volume unchanged and prevents the system volume panel from appearing.
- **iOS**: Button presses are derived from changes to the media volume, so button presses are only detected while the app is in the foreground. Without the `suppressVolumeChange` option, presses can not be detected once the volume has reached its minimum or maximum. With the option enabled, the volume is reset immediately after each press (nudged away from the edges if needed), the system volume indicator is hidden, and the original volume is restored when watching stops. Note that volume changes from other sources (for example Control Center) are also reported while watching.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/volume/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/volume/LICENSE).
