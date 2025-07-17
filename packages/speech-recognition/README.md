# @capawesome-team/capacitor-speech-recognition

Capacitor plugin to transcribe speech into text (also known as speech-to-text) with advanced features like silence detection, contextual strings, and more.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for speech recognition. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌐 **Multiple Languages**: Supports many different languages.
- 🔑 **Permissions**: Check and request permissions for recording audio.
- 🔊 **Events**: Listen for events like `start`, `end`, `speechStart`, `speechEnd`, `error`, `partialResults`, and `results`.
- 🔇 **Silence Detection**: Automatically detects silence to stop the recording.
- 📊 **Silence Threshold**: Define what's considered "silence" for your recordings.
- 💬 **Contextual Strings**: Provide an array of phrases that should be recognized, even if they are not in the system vocabulary.
- 🤝 **Compatibility**: Compatible with the [Audio Recorder](https://capawesome.io/plugins/audio-recorder/), [Speech Synthesis](https://capawesome.io/plugins/speech-synthesis/) and [Native Audio](https://github.com/capacitor-community/native-audio) plugin.
- ⚔️ **Battle-Tested**: Used in more than 50 projects.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |
| 6.x.x          | 6.x.x             | Deprecated     |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-speech-recognition
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Privacy Descriptions

Add the `NSSpeechRecognitionUsageDescription` and `NSMicrophoneUsageDescription` keys to the `ios/App/App/Info.plist` file, which tells the user why the app needs access to speech recognition and the microphone:

```xml
<key>NSSpeechRecognitionUsageDescription</key>
<string>Speech recognition is used to transcribe speech into text.</string>
<key>NSMicrophoneUsageDescription</key>
<string>Microphone is used to record audio for speech recognition.</string>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { SpeechRecognition } from '@capawesome-team/capacitor-speech-recognition';

const startListening = async () => {
  await SpeechRecognition.startListening({
    language: 'en-US',
    silenceThreshold: 2000,
  });
};

const stopListening = async () => {
  await SpeechRecognition.stopListening();
};

const checkPermissions = async () => {
  const { audioRecording, speechRecognition } = await SpeechRecognition.checkPermissions();
};

const requestPermissions = async () => {
  const { audioRecording, speechRecognition } = await SpeechRecognition.requestPermissions({
    permissions: ['audioRecording', 'speechRecognition'],
  });
};

const isAvailable = async () => {
  const { available } = await SpeechRecognition.isAvailable();
  return available;
};

const isListening = async () => {
  const { listening } = await SpeechRecognition.isListening();
  return listening;
};

const getSupportedLanguages = async () => {
  const { languages } = await SpeechRecognition.getSupportedLanguages();
  return languages;
};

const addListeners = () => {
  SpeechRecognition.addListener('start', () => {
    console.log('Speech recognition started');
  });
  SpeechRecognition.addListener('end', () => {
    console.log('Speech recognition ended');
  });
  SpeechRecognition.addListener('error', (event) => {
    console.error('Speech recognition error:', event.message);
  });
  SpeechRecognition.addListener('partialResult', (event) => {
    console.log('Partial result:', event.result);
  });
  SpeechRecognition.addListener('result', (event) => {
    console.log('Final result:', event.result);
  });
  SpeechRecognition.addListener('speechStart', () => {
    console.log('User started speaking');
  });
  SpeechRecognition.addListener('speechEnd', () => {
    console.log('User stopped speaking');
  });
};

const removeAllListeners = async () => {
  await SpeechRecognition.removeAllListeners();
};
```

## API

<docgen-index>

* [`getLanguages()`](#getlanguages)
* [`isAvailable()`](#isavailable)
* [`isListening()`](#islistening)
* [`startListening(...)`](#startlistening)
* [`stopListening(...)`](#stoplistening)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`addListener('end', ...)`](#addlistenerend-)
* [`addListener('error', ...)`](#addlistenererror-)
* [`addListener('partialResult', ...)`](#addlistenerpartialresult-)
* [`addListener('result', ...)`](#addlistenerresult-)
* [`addListener('speechEnd', ...)`](#addlistenerspeechend-)
* [`addListener('speechStart', ...)`](#addlistenerspeechstart-)
* [`addListener('start', ...)`](#addlistenerstart-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getLanguages()

```typescript
getLanguages() => Promise<GetLanguagesResult>
```

Get the available languages for speech recognition.

**Attention**: On Android, this method is unfortunately not supported
by all devices. If the method is not supported, the promise will never resolve.
It's recommended to set a timeout for the promise.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getlanguagesresult">GetLanguagesResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the speech recognizer is available on the device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isListening()

```typescript
isListening() => Promise<IsListeningResult>
```

Check if the speech recognizer is currently listening.

**Returns:** <code>Promise&lt;<a href="#islisteningresult">IsListeningResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### startListening(...)

```typescript
startListening(options?: StartListeningOptions | undefined) => Promise<void>
```

Start listening for speech.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#startlisteningoptions">StartListeningOptions</a></code> |

**Since:** 6.0.0

--------------------


### stopListening(...)

```typescript
stopListening(options?: StopListeningOptions | undefined) => Promise<void>
```

Stop listening for speech.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#stoplisteningoptions">StopListeningOptions</a></code> |

**Since:** 6.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions for the plugin.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(permissions?: SpeechRecognitionPluginPermission | undefined) => Promise<PermissionStatus>
```

Request permissions for the plugin.

| Param             | Type                                                                                            |
| ----------------- | ----------------------------------------------------------------------------------------------- |
| **`permissions`** | <code><a href="#speechrecognitionpluginpermission">SpeechRecognitionPluginPermission</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('end', ...)

```typescript
addListener(eventName: 'end', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the speech recognizer has stopped listening.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'end'</code>         |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('error', ...)

```typescript
addListener(eventName: 'error', listenerFunc: (event: ErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'error'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#errorevent">ErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('partialResult', ...)

```typescript
addListener(eventName: 'partialResult', listenerFunc: (event: PartialResultEvent) => void) => Promise<PluginListenerHandle>
```

Called when a partial result is available.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'partialResult'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#partialresultevent">PartialResultEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('result', ...)

```typescript
addListener(eventName: 'result', listenerFunc: (event: ResultEvent) => void) => Promise<PluginListenerHandle>
```

Called when the final results are available.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'result'</code>                                                   |
| **`listenerFunc`** | <code>(event: <a href="#resultevent">ResultEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('speechEnd', ...)

```typescript
addListener(eventName: 'speechEnd', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the user has stopped speaking.

Only available on Android and Web.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'speechEnd'</code>   |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('speechStart', ...)

```typescript
addListener(eventName: 'speechStart', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the user has started to speak.

Only available on Android and Web.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'speechStart'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('start', ...)

```typescript
addListener(eventName: 'start', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the speech recognizer has started listening.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'start'</code>       |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 6.0.0

--------------------


### Interfaces


#### GetLanguagesResult

| Prop            | Type                  | Description                                                             | Since |
| --------------- | --------------------- | ----------------------------------------------------------------------- | ----- |
| **`languages`** | <code>string[]</code> | The supported languages for speech recognition as BCP-47 language tags. | 6.0.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                      | Since |
| ----------------- | -------------------- | ---------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether or not the speech recognizer is available on the device. | 6.0.0 |


#### IsListeningResult

| Prop              | Type                 | Description                                                  | Since |
| ----------------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`isListening`** | <code>boolean</code> | Whether or not the speech recognizer is currently listening. | 6.0.0 |


#### StartListeningOptions

| Prop                               | Type                                                                  | Description                                                                                                                                                                                                                | Default                                  | Since |
| ---------------------------------- | --------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`audioSessionCategory`**         | <code><a href="#audiosessioncategory">AudioSessionCategory</a></code> | The audio session category to use for speech recognition. Only available on iOS.                                                                                                                                           | <code>AudioSessionCategory.Record</code> | 7.2.0 |
| **`contextualStrings`**            | <code>string[]</code>                                                 | An array of phrases that should be recognized, even if they are not in the system vocabulary. Only available on Android (SDK 33+) and iOS.                                                                                 |                                          | 7.3.0 |
| **`deactivateAudioSessionOnStop`** | <code>boolean</code>                                                  | Whether or not to deactivate your app's audio session on stop. Only available on iOS.                                                                                                                                      | <code>true</code>                        | 7.2.0 |
| **`enableFormatting`**             | <code>boolean</code>                                                  | Whether to add punctuation to speech recognition results. **Note**: On Android, this option does not work reliably as it varies depending on the device and TTS engine. Only available on Android (SDK 33+) and iOS (+16). | <code>false</code>                       | 7.4.0 |
| **`language`**                     | <code>string</code>                                                   | The BC-47 language tag for the language to use for speech recognition.                                                                                                                                                     |                                          | 6.0.0 |
| **`silenceThreshold`**             | <code>number</code>                                                   | The number of milliseconds of silence before the speech recognition ends. Only available on Android (SDK 33+) and iOS.                                                                                                     | <code>2000</code>                        | 6.0.0 |


#### StopListeningOptions

| Prop                         | Type                 | Description                                                                   | Default           | Since |
| ---------------------------- | -------------------- | ----------------------------------------------------------------------------- | ----------------- | ----- |
| **`deactivateAudioSession`** | <code>boolean</code> | Whether or not to deactivate your app's audio session. Only available on iOS. | <code>true</code> | 7.2.0 |


#### PermissionStatus

| Prop                    | Type                                                        | Description                                                     | Since |
| ----------------------- | ----------------------------------------------------------- | --------------------------------------------------------------- | ----- |
| **`audioRecording`**    | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for recording audio.                           | 7.1.0 |
| **`recordAudio`**       | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for speech recognition.                        | 6.0.0 |
| **`speechRecognition`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for speech recognition. Only available on iOS. | 7.1.0 |


#### SpeechRecognitionPluginPermission

| Prop              | Type                                           |
| ----------------- | ---------------------------------------------- |
| **`permissions`** | <code>SpeechRecognitionPermissionType[]</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 6.0.0 |


#### PartialResultEvent

| Prop         | Type                | Description                                   | Since |
| ------------ | ------------------- | --------------------------------------------- | ----- |
| **`result`** | <code>string</code> | The partial result of the speech recognition. | 6.0.0 |


#### ResultEvent

| Prop         | Type                | Description                                 | Since |
| ------------ | ------------------- | ------------------------------------------- | ----- |
| **`result`** | <code>string</code> | The final result of the speech recognition. | 6.0.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### SpeechRecognitionPermissionType

<code>'audioRecording' | 'speechRecognition'</code>


### Enums


#### AudioSessionCategory

| Members             | Value                          | Description                                                           | Since |
| ------------------- | ------------------------------ | --------------------------------------------------------------------- | ----- |
| **`Record`**        | <code>'RECORD'</code>          | The category for recording audio while also silencing playback audio. | 7.2.0 |
| **`PlayAndRecord`** | <code>'PLAY_AND_RECORD'</code> | The category for recording (input) and playback (output) of audio.    | 7.2.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-recognition/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-recognition/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-recognition/LICENSE).
