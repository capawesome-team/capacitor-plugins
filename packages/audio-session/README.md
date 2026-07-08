# Capacitor Audio Session Plugin

Capacitor plugin to configure and observe the iOS [audio session](https://developer.apple.com/documentation/avfaudio/avaudiosession).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Audio Session plugin is one of the most complete audio session management solutions for Capacitor apps. Here are some of the key features:

- 🎛️ **Configuration**: Set the audio session category, mode and options.
- 🔌 **Activation**: Activate and deactivate the audio session.
- 🔊 **Output routing**: Read the current audio outputs and override the output port.
- 📡 **Events**: Observe interruption and route change events.
- 🤝 **Compatibility**: Works alongside the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/) and [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Audio Session plugin is typically used whenever an app needs fine-grained control over how audio behaves on iOS, for example:

- **Media playback apps**: Configure the `playback` category so audio keeps playing while other apps are silenced.
- **Voice and video chat**: Switch between the receiver and the built-in speaker using the output override.
- **Mixing with other apps**: Let your audio play alongside (or duck) audio from other apps using the category options.
- **Handling phone calls**: Pause playback when the session is interrupted and resume it when the interruption ends.
- **Headphone awareness**: React when headphones or Bluetooth devices are plugged in or out by observing route changes.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-audio-session` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-audio-session
npx cap sync
```

This plugin is only available on **iOS**. On Android and Web, all methods reject as unimplemented.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { AudioSession } from '@capawesome/capacitor-audio-session';
```

### Configure the audio session

Set the audio session category, mode and options, for example for movie playback that interrupts audio from other apps. All methods of this plugin are only available on iOS:

```typescript
const configure = async () => {
  await AudioSession.configure({
    category: 'playback',
    mode: 'moviePlayback',
    options: {
      mixWithOthers: false,
    },
  });
};
```

### Activate or deactivate the audio session

Activate the audio session before playing or recording audio, and deactivate it when you are done so that other apps can resume their playback:

```typescript
const setActive = async () => {
  await AudioSession.setActive({ active: true });
};
```

### Read the current audio outputs

Get the audio outputs of the current audio route, for example to check whether headphones are connected:

```typescript
const getCurrentOutputs = async () => {
  const { outputs } = await AudioSession.getCurrentOutputs();
  return outputs;
};
```

### Route playback to the built-in speaker

Override the audio output port that is used for playback, for example to switch from the receiver to the loudspeaker during a call:

```typescript
const overrideOutput = async () => {
  await AudioSession.overrideOutput({ type: 'speaker' });
};
```

### Listen for interruptions

Get notified when the audio session is interrupted, e.g. by an incoming phone call. The `shouldResume` flag tells you whether playback should resume after the interruption ended:

```typescript
const addInterruptionListener = async () => {
  await AudioSession.addListener('interruption', event => {
    console.log('Interruption:', event.type, event.shouldResume);
  });
};
```

### Listen for route changes

Get notified when the audio route changes, e.g. when headphones are plugged in or out:

```typescript
const addRouteChangeListener = async () => {
  await AudioSession.addListener('routeChange', event => {
    console.log('Route change:', event.reason, event.outputs);
  });
};
```

### Remove all listeners

Remove all listeners that were registered for this plugin:

```typescript
const removeAllListeners = async () => {
  await AudioSession.removeAllListeners();
};
```

## API

<docgen-index>

* [`addListener('interruption', ...)`](#addlistenerinterruption-)
* [`addListener('routeChange', ...)`](#addlistenerroutechange-)
* [`configure(...)`](#configure)
* [`getCurrentOutputs()`](#getcurrentoutputs)
* [`overrideOutput(...)`](#overrideoutput)
* [`removeAllListeners()`](#removealllisteners)
* [`setActive(...)`](#setactive)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addListener('interruption', ...)

```typescript
addListener(eventName: 'interruption', listenerFunc: (event: InterruptionEvent) => void) => Promise<PluginListenerHandle>
```

Called when the audio session is interrupted, e.g. by an incoming phone call.

Only available on iOS.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'interruption'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#interruptionevent">InterruptionEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('routeChange', ...)

```typescript
addListener(eventName: 'routeChange', listenerFunc: (event: RouteChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the audio route changes, e.g. when headphones are plugged in or out.

Only available on iOS.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'routeChange'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#routechangeevent">RouteChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### configure(...)

```typescript
configure(options: ConfigureOptions) => Promise<void>
```

Configure the audio session category, mode and options.

Only available on iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#configureoptions">ConfigureOptions</a></code> |

**Since:** 0.1.0

--------------------


### getCurrentOutputs()

```typescript
getCurrentOutputs() => Promise<GetCurrentOutputsResult>
```

Get the audio outputs of the current audio route.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#getcurrentoutputsresult">GetCurrentOutputsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### overrideOutput(...)

```typescript
overrideOutput(options: OverrideOutputOptions) => Promise<void>
```

Override the audio output port that is used for playback.

Only available on iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#overrideoutputoptions">OverrideOutputOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

Only available on iOS.

**Since:** 0.1.0

--------------------


### setActive(...)

```typescript
setActive(options: SetActiveOptions) => Promise<void>
```

Activate or deactivate the audio session.

Only available on iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setactiveoptions">SetActiveOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### InterruptionEvent

| Prop               | Type                                                          | Description                                                                                    | Since |
| ------------------ | ------------------------------------------------------------- | ---------------------------------------------------------------------------------------------- | ----- |
| **`shouldResume`** | <code>boolean</code>                                          | Whether playback should resume after the interruption ended. Only `true` if `type` is `ended`. | 0.1.0 |
| **`type`**         | <code><a href="#interruptiontype">InterruptionType</a></code> | The type of the interruption.                                                                  | 0.1.0 |


#### RouteChangeEvent

| Prop          | Type                                                            | Description                                            | Since |
| ------------- | --------------------------------------------------------------- | ------------------------------------------------------ | ----- |
| **`outputs`** | <code>AudioSessionOutput[]</code>                               | The audio outputs of the audio route after the change. | 0.1.0 |
| **`reason`**  | <code><a href="#routechangereason">RouteChangeReason</a></code> | The reason why the audio route changed.                | 0.1.0 |


#### AudioSessionOutput

| Prop           | Type                | Description                                       | Since |
| -------------- | ------------------- | ------------------------------------------------- | ----- |
| **`portName`** | <code>string</code> | The human-readable name of the audio output port. | 0.1.0 |
| **`portType`** | <code>string</code> | The type of the audio output port.                | 0.1.0 |


#### ConfigureOptions

| Prop           | Type                                                                                | Description                         | Default                | Since |
| -------------- | ----------------------------------------------------------------------------------- | ----------------------------------- | ---------------------- | ----- |
| **`category`** | <code><a href="#audiosessioncategory">AudioSessionCategory</a></code>               | The audio session category.         |                        | 0.1.0 |
| **`mode`**     | <code><a href="#audiosessionmode">AudioSessionMode</a></code>                       | The audio session mode.             | <code>'default'</code> | 0.1.0 |
| **`options`**  | <code><a href="#audiosessioncategoryoptions">AudioSessionCategoryOptions</a></code> | The audio session category options. |                        | 0.1.0 |


#### AudioSessionCategoryOptions

| Prop                                       | Type                 | Description                                                                                                                                  | Default            | Since |
| ------------------------------------------ | -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`allowAirPlay`**                         | <code>boolean</code> | Whether AirPlay devices can be used for output.                                                                                              | <code>false</code> | 0.1.0 |
| **`allowBluetooth`**                       | <code>boolean</code> | Whether Bluetooth hands-free devices can be used for input and output.                                                                       | <code>false</code> | 0.1.0 |
| **`allowBluetoothA2DP`**                   | <code>boolean</code> | Whether Bluetooth A2DP devices can be used for output.                                                                                       | <code>false</code> | 0.1.0 |
| **`defaultToSpeaker`**                     | <code>boolean</code> | Whether audio is routed to the built-in speaker instead of the receiver when no other audio route is connected.                              | <code>false</code> | 0.1.0 |
| **`duckOthers`**                           | <code>boolean</code> | Whether audio from other sessions is reduced in volume (ducked) while audio from this session plays.                                         | <code>false</code> | 0.1.0 |
| **`interruptSpokenAudioAndMixWithOthers`** | <code>boolean</code> | Whether audio from other sessions using the `spokenAudio` mode is interrupted and audio from this session is mixed with the remaining audio. | <code>false</code> | 0.1.0 |
| **`mixWithOthers`**                        | <code>boolean</code> | Whether audio from this session mixes with audio from other active sessions instead of interrupting them.                                    | <code>false</code> | 0.1.0 |


#### GetCurrentOutputsResult

| Prop          | Type                              | Description                                   | Since |
| ------------- | --------------------------------- | --------------------------------------------- | ----- |
| **`outputs`** | <code>AudioSessionOutput[]</code> | The audio outputs of the current audio route. | 0.1.0 |


#### OverrideOutputOptions

| Prop       | Type                                                              | Description                                 | Since |
| ---------- | ----------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`type`** | <code><a href="#overrideoutputtype">OverrideOutputType</a></code> | The audio output port to route playback to. | 0.1.0 |


#### SetActiveOptions

| Prop                             | Type                 | Description                                                                                              | Default           | Since |
| -------------------------------- | -------------------- | -------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`active`**                     | <code>boolean</code> | Whether the audio session should be activated (`true`) or deactivated (`false`).                         |                   | 0.1.0 |
| **`notifyOthersOnDeactivation`** | <code>boolean</code> | Whether other audio sessions are notified when this session is deactivated, so they can resume playback. | <code>true</code> | 0.1.0 |


### Type Aliases


#### InterruptionType

The type of an audio session interruption.

<code>'began' | 'ended'</code>


#### RouteChangeReason

The reason why the audio route changed.

<code>'categoryChange' | 'newDeviceAvailable' | 'noSuitableRouteForCategory' | 'oldDeviceUnavailable' | 'override' | 'routeConfigurationChange' | 'unknown' | 'wakeFromSleep'</code>


#### AudioSessionCategory

The audio session category.

<code>'ambient' | 'multiRoute' | 'playAndRecord' | 'playback' | 'record' | 'soloAmbient'</code>


#### AudioSessionMode

The audio session mode.

<code>'default' | 'gameChat' | 'measurement' | 'moviePlayback' | 'spokenAudio' | 'videoChat' | 'videoRecording' | 'voiceChat' | 'voicePrompt'</code>


#### OverrideOutputType

The audio output port to route playback to.

<code>'default' | 'speaker'</code>

</docgen-api>

## Session Ownership

The audio session returned by [`AVAudioSession.sharedInstance()`](https://developer.apple.com/documentation/avfaudio/avaudiosession) is a **single, app-wide** object. This plugin gives you **manual** control over it, but so do other audio-related plugins (such as the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/), [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/) and Speech Recognition plugins) and the underlying platform APIs they use.

Because the session is shared, the **last write wins**: calling `configure(...)` may override the category, mode or options that another plugin has set, and vice versa. This plugin cannot prevent that. If you combine this plugin with other audio plugins, make sure to reconfigure the session whenever you switch between playback, recording and other audio scenarios.

## FAQ

### Is this plugin available on Android or the Web?

No, this plugin is only available on iOS since it controls the iOS-specific [audio session](https://developer.apple.com/documentation/avfaudio/avaudiosession). On Android and Web, all methods reject as unimplemented, so you can safely include the plugin in a cross-platform app.

### Why are my audio session settings overridden by other plugins?

The audio session is a single, app-wide object that is shared with other audio-related plugins (such as the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/) and [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/) plugins) and the underlying platform APIs they use. The last write wins, so another plugin may override the category, mode or options you have set. Reconfigure the session whenever you switch between playback, recording and other audio scenarios. See the [Session Ownership](#session-ownership) section for more details.

### How can my app play audio without interrupting other apps?

Use the `mixWithOthers` category option when calling `configure(...)` to mix your audio with audio from other active sessions instead of interrupting them. Alternatively, use the `duckOthers` option to reduce the volume of other sessions while your audio plays.

### How do I resume playback after a phone call interrupted my app?

Add a listener for the `interruption` event. When the event's `type` is `ended` and `shouldResume` is `true`, playback should be resumed. See [Listen for interruptions](#listen-for-interruptions) for an example.

### How do I route audio to the loudspeaker?

Call `overrideOutput(...)` with the type `speaker` to route playback to the built-in speaker, and with `default` to restore the default route. If you want audio to be routed to the speaker by default when no other route is connected, use the `defaultToSpeaker` category option when calling `configure(...)`.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/): Play audio with background support.
- [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/): Record audio using the device's microphone.
- [Media Session](https://capawesome.io/docs/sdks/capacitor/media-session/): Interact with media controllers, volume keys and media buttons.
- [Volume](https://capawesome.io/docs/sdks/capacitor/volume/): Control the volume and observe hardware volume button presses.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-session/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/audio-session/LICENSE).
