# @capawesome-team/capacitor-speech-recognition

Capacitor plugin to transcribe speech into text.

## Features

- 🔋 Supports Android and iOS.
- ⚡️ Capacitor 6 support.
- 🌐 **Multiple languages**: Supports many different languages.
- 🛠 **Permissions**: Check and request permissions for recording audio.
- 🎧 **Listening**: Check if the speech recognizer is available and currently listening.
- 🎙 **Events**: Listen for events like `beginningOfSpeech`, `endOfSpeech`, `error`, `partialResults`, `readyForSpeech`, and `results`.

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
npm install @capawesome-team/capacitor-speech-recognition
npx cap sync
```

### iOS

#### Privacy Descriptions

Add the `NSSpeechRecognitionUsageDescription` and `NSMicrophoneUsageDescription` keys to the `ios/App/App/Info.plist` file, which tells the user why your app is requesting location information:

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
    maxResults: 5,
    shouldReturnPartialResults: true,
  });
};

const stopListening = async () => {
  await SpeechRecognition.stopListening();
};

const checkPermissions = async () => {
  const { recordAudio } = await SpeechRecognition.checkPermissions();
  return recordAudio;
};

const requestPermissions = async () => {
  const { recordAudio } = await SpeechRecognition.requestPermissions();
  return recordAudio;
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
  SpeechRecognition.addListener('beginningOfSpeech', () => {
    console.log('User has started to speak');
  });

  SpeechRecognition.addListener('endOfSpeech', () => {
    console.log('User has stopped speaking');
  });

  SpeechRecognition.addListener('error', (event) => {
    console.error(event.message);
  });

  SpeechRecognition.addListener('partialResults', (event) => {
    console.log('Partial results:', event.results);
  });

  SpeechRecognition.addListener('readyForSpeech', () => {
    console.log('Speech recognizer is listening');
  });

  SpeechRecognition.addListener('results', (event) => {
    console.log('Final results:', event.results);
  });
};

const removeAllListeners = async () => {
  await SpeechRecognition.removeAllListeners();
};
```

## API

<docgen-index>

* [`getSupportedLanguages()`](#getsupportedlanguages)
* [`isAvailable()`](#isavailable)
* [`isListening()`](#islistening)
* [`startListening(...)`](#startlistening)
* [`stopListening()`](#stoplistening)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('beginningOfSpeech', ...)`](#addlistenerbeginningofspeech-)
* [`addListener('endOfSpeech', ...)`](#addlistenerendofspeech-)
* [`addListener('error', ...)`](#addlistenererror-)
* [`addListener('listeningState', ...)`](#addlistenerlisteningstate-)
* [`addListener('partialResults', ...)`](#addlistenerpartialresults-)
* [`addListener('readyForSpeech', ...)`](#addlistenerreadyforspeech-)
* [`addListener('results', ...)`](#addlistenerresults-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getSupportedLanguages()

```typescript
getSupportedLanguages() => Promise<GetSupportedLanguagesResult>
```

Get the supported languages for speech recognition.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getsupportedlanguagesresult">GetSupportedLanguagesResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the speech recognizer is available on the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isListening()

```typescript
isListening() => Promise<IsListeningResult>
```

Check if the speech recognizer is currently listening.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#islisteningresult">IsListeningResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### startListening(...)

```typescript
startListening(options?: StartListeningOptions | undefined) => Promise<void>
```

Start listening for speech.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#startlisteningoptions">StartListeningOptions</a></code> |

**Since:** 6.0.0

--------------------


### stopListening()

```typescript
stopListening() => Promise<void>
```

Stop listening for speech.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions for the plugin.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permissions for the plugin.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('beginningOfSpeech', ...)

```typescript
addListener(eventName: 'beginningOfSpeech', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the user has started to speak.

Only available on Android.

| Param              | Type                             |
| ------------------ | -------------------------------- |
| **`eventName`**    | <code>'beginningOfSpeech'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>       |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('endOfSpeech', ...)

```typescript
addListener(eventName: 'endOfSpeech', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the user has stopped speaking.

Only available on Android.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'endOfSpeech'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('error', ...)

```typescript
addListener(eventName: 'error', listenerFunc: (event: ErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs.

Only available on Android and iOS.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'error'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#errorevent">ErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('listeningState', ...)

```typescript
addListener(eventName: 'listeningState', listenerFunc: (event: ListeningStateEvent) => void) => Promise<PluginListenerHandle>
```

Called when the listening state changes.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'listeningState'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#listeningstateevent">ListeningStateEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('partialResults', ...)

```typescript
addListener(eventName: 'partialResults', listenerFunc: (event: PartialResultsEvent) => void) => Promise<PluginListenerHandle>
```

Called when a partial result is available.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'partialResults'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#partialresultsevent">PartialResultsEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('readyForSpeech', ...)

```typescript
addListener(eventName: 'readyForSpeech', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the speech recognizer is listening for speech.

Only available on Android and iOS.

| Param              | Type                          |
| ------------------ | ----------------------------- |
| **`eventName`**    | <code>'readyForSpeech'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>    |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('results', ...)

```typescript
addListener(eventName: 'results', listenerFunc: (event: ResultsEvent) => void) => Promise<PluginListenerHandle>
```

Called when the final results are available.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'results'</code>                                                    |
| **`listenerFunc`** | <code>(event: <a href="#resultsevent">ResultsEvent</a>) =&gt; void</code> |

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


#### GetSupportedLanguagesResult

| Prop            | Type                  | Description                                                             | Since |
| --------------- | --------------------- | ----------------------------------------------------------------------- | ----- |
| **`languages`** | <code>string[]</code> | The supported languages for speech recognition as BCP-47 language tags. | 6.0.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                      | Since |
| --------------- | -------------------- | ---------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the speech recognizer is available on the device. | 6.0.0 |


#### IsListeningResult

| Prop            | Type                 | Description                                                  | Since |
| --------------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`listening`** | <code>boolean</code> | Whether or not the speech recognizer is currently listening. | 6.0.0 |


#### StartListeningOptions

| Prop                             | Type                 | Description                                 | Default           | Since |
| -------------------------------- | -------------------- | ------------------------------------------- | ----------------- | ----- |
| **`language`**                   | <code>string</code>  | The language to use for speech recognition. |                   | 6.0.0 |
| **`maxResults`**                 | <code>number</code>  | The maximum number of results to return.    | <code>5</code>    | 6.0.0 |
| **`shouldReturnPartialResults`** | <code>boolean</code> | Whether or not to receive partial results.  | <code>true</code> | 6.0.0 |


#### PermissionStatus

| Prop              | Type                                                        | Description                           | Since |
| ----------------- | ----------------------------------------------------------- | ------------------------------------- | ----- |
| **`recordAudio`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for recording audio. | 6.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 6.0.0 |


#### ListeningStateEvent

| Prop            | Type                 | Description                                        | Since |
| --------------- | -------------------- | -------------------------------------------------- | ----- |
| **`listening`** | <code>boolean</code> | Whether or not the speech recognizer is listening. | 6.0.0 |


#### PartialResultsEvent

| Prop          | Type                  | Description                                    | Since |
| ------------- | --------------------- | ---------------------------------------------- | ----- |
| **`results`** | <code>string[]</code> | The partial results of the speech recognition. | 6.0.0 |


#### ResultsEvent

| Prop          | Type                  | Description                                  | Since |
| ------------- | --------------------- | -------------------------------------------- | ----- |
| **`results`** | <code>string[]</code> | The final results of the speech recognition. | 6.0.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-recognition/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-recognition/LICENSE).
