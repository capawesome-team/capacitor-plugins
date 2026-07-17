# Capacitor Speech Synthesis Plugin

Capacitor plugin for synthesizing speech from text (also known as text-to-speech) with advanced features like voice selection, pitch, and rate control.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Speech Synthesis plugin offers one of the most feature-rich text-to-speech solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌐 **Multiple Languages**: Supports many different languages.
- 🗣️ **Multiple Voices**: Supports multiple voices for each language.
- 🎚️ **Customization**: Customize the pitch, rate, volume and voice of the speech.
- 🎧 **Background Audio**: Synthesize speech from text while your application runs in the background.
- 📜 **Queue Strategy**: Add or flush the utterance to the queue.
- 🔊 **Events**: Listen for events like `boundary`, `end`, `error` and `start`.
- ⏸️ **Pause/Resume**: Pause and resume speech synthesis.
- ⚔️ **Battle-Tested**: Used in more than 100 projects.
- 🤝 **Compatibility**: Compatible with the [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/), [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/) and [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Speech Synthesis plugin is typically used whenever an app needs to read text aloud to the user, for example:

- **Read content aloud**: Read articles, messages, or notifications to users, for example while they are driving or exercising.
- **Voice feedback**: Give spoken confirmations or instructions in response to user actions, even while your app runs in the background.
- **Accessibility**: Make your app usable for people with visual impairments or reading difficulties by speaking on-screen content.
- **Language learning**: Demonstrate the pronunciation of words and sentences in many different languages and voices.
- **Audio file generation**: Synthesize speech to an audio file, for example to play it later or share it.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
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

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-speech-synthesis` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-speech-synthesis
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to speak and synthesize text, pause and cancel speech, check availability, list languages and voices, and handle synthesis events.

### Speak a text

Add an utterance to the utterance queue to be spoken. You can customize the language, pitch, rate, voice, and volume. The `end` event is emitted when the utterance has finished:

```typescript
import { SpeechSynthesis, QueueStrategy } from '@capawesome-team/capacitor-speech-synthesis';

const speak = async () => {
  // Add an utterance to the utterance queue to be spoken
  const { utteranceId } = await SpeechSynthesis.speak({
    language: 'en-US',
    pitch: 1.0,
    queueStrategy: QueueStrategy.Add,
    rate: 1.0,
    text: 'Hello, World!',
    voiceId: 'com.apple.ttsbundle.Samantha-compact',
    volume: 1.0,
  });
  // Wait for the utterance to finish
  await new Promise(resolve => {
    void SpeechSynthesis.addListener('end', event => {
      if (event.utteranceId === utteranceId) {
        resolve();
      }
    });
  });
};
```

### Synthesize speech to an audio file

Instead of speaking an utterance, you can synthesize it to an audio file, for example to play it later. The file is available as soon as the `end` event is emitted. Only available on Android and iOS:

```typescript
import { SpeechSynthesis, QueueStrategy } from '@capawesome-team/capacitor-speech-synthesis';

const synthesizeToFile = async () => {
  // Add an utterance to the utterance queue to be synthesized to a file
  const { path, utteranceId } = await SpeechSynthesis.synthesizeToFile({
    language: 'en-US',
    pitch: 1.0,
    queueStrategy: QueueStrategy.Add,
    rate: 1.0,
    text: 'Hello, World!',
    voiceId: 'com.apple.ttsbundle.Samantha-compact',
    volume: 1.0,
  });
  // Wait for the utterance to finish
  await new Promise(resolve => {
    void SpeechSynthesis.addListener('end', event => {
      if (event.utteranceId === utteranceId) {
        resolve();
      }
    });
  });
  // Return the path to the synthesized audio file
  return path;
};
```

### Pause, resume, and cancel speech

Pause the speech immediately and resume it later, or remove all utterances from the utterance queue:

```typescript
import { SpeechSynthesis } from '@capawesome-team/capacitor-speech-synthesis';

const pause = async () => {
  await SpeechSynthesis.pause();
};

const resume = async () => {
  await SpeechSynthesis.resume();
};

const cancel = async () => {
  await SpeechSynthesis.cancel();
};
```

### Check availability

Check whether speech synthesis is available on the current device and whether a specific language or voice is supported:

```typescript
import { SpeechSynthesis } from '@capawesome-team/capacitor-speech-synthesis';

const isAvailable = async () => {
  const result = await SpeechSynthesis.isAvailable();
  return result.isAvailable;
};

const isLanguageAvailable = async () => {
  const result = await SpeechSynthesis.isLanguageAvailable({ language: 'en-US' });
  return result.isAvailable;
};

const isVoiceAvailable = async () => {
  const result = await SpeechSynthesis.isVoiceAvailable({ voiceId: 'com.apple.ttsbundle.Samantha-compact' });
  return result.isAvailable;
};
```

### Get the available languages and voices

Retrieve the available languages as BCP-47 language tags and the available voices, for example to let the user pick a preferred voice:

```typescript
import { SpeechSynthesis } from '@capawesome-team/capacitor-speech-synthesis';

const getLanguages = async () => {
  const result = await SpeechSynthesis.getLanguages();
  return result.languages;
};

const getVoices = async () => {
  const result = await SpeechSynthesis.getVoices();
  return result.voices;
};
```

### Listen for speech synthesis events

React to the different stages of the speech synthesis, for example to highlight the word that is currently being spoken using the `boundary` event:

```typescript
import { SpeechSynthesis } from '@capawesome-team/capacitor-speech-synthesis';

const addListeners = () => {
  SpeechSynthesis.addListener('boundary', (event) => {
    console.log('boundary', event);
  });

  SpeechSynthesis.addListener('end', (event) => {
    console.log('end', event);
  });

  SpeechSynthesis.addListener('error', (event) => {
    console.log('error', event);
  });

  SpeechSynthesis.addListener('start', (event) => {
    console.log('start', event);
  });
};
```

### Remove all listeners

Remove all listeners for this plugin when they are no longer needed:

```typescript
import { SpeechSynthesis } from '@capawesome-team/capacitor-speech-synthesis';

const removeAllListeners = async () => {
  await SpeechSynthesis.removeAllListeners();
};
```

## API

<docgen-index>

* [`activateAudioSession(...)`](#activateaudiosession)
* [`cancel()`](#cancel)
* [`deactivateAudioSession()`](#deactivateaudiosession)
* [`getLanguages()`](#getlanguages)
* [`getVoices()`](#getvoices)
* [`initialize()`](#initialize)
* [`isAvailable()`](#isavailable)
* [`isSpeaking()`](#isspeaking)
* [`isLanguageAvailable(...)`](#islanguageavailable)
* [`isVoiceAvailable(...)`](#isvoiceavailable)
* [`pause()`](#pause)
* [`resume()`](#resume)
* [`speak(...)`](#speak)
* [`synthesizeToFile(...)`](#synthesizetofile)
* [`addListener('boundary', ...)`](#addlistenerboundary-)
* [`addListener('end', ...)`](#addlistenerend-)
* [`addListener('error', ...)`](#addlistenererror-)
* [`addListener('start', ...)`](#addlistenerstart-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### activateAudioSession(...)

```typescript
activateAudioSession(options: ActivateAudioSessionOptions) => Promise<void>
```

Activate the audio session. This method is not mandatory.
It can be used to set the audio session category before speaking.

Only available on iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#activateaudiosessionoptions">ActivateAudioSessionOptions</a></code> |

**Since:** 6.0.0

--------------------


### cancel()

```typescript
cancel() => Promise<void>
```

Remove all utterances from the utterance queue.

**Since:** 6.0.0

--------------------


### deactivateAudioSession()

```typescript
deactivateAudioSession() => Promise<void>
```

Deactivate the audio session.

Only available on iOS.

**Since:** 6.0.0

--------------------


### getLanguages()

```typescript
getLanguages() => Promise<GetLanguagesResult>
```

Get the available languages for speech synthesis.

**Returns:** <code>Promise&lt;<a href="#getlanguagesresult">GetLanguagesResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getVoices()

```typescript
getVoices() => Promise<GetVoicesResult>
```

Get the available voices for speech synthesis.

**Returns:** <code>Promise&lt;<a href="#getvoicesresult">GetVoicesResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### initialize()

```typescript
initialize() => Promise<void>
```

Initialize the plugin before any other method is called.

Use this method to warm up the speech synthesis engine.
If this method is not called, the plugin will be automatically
initialized on the first call to any other method.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if speech synthesis is available on the current device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isSpeaking()

```typescript
isSpeaking() => Promise<IsSpeakingResult>
```

Check if speech synthesis is currently speaking.

**Returns:** <code>Promise&lt;<a href="#isspeakingresult">IsSpeakingResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isLanguageAvailable(...)

```typescript
isLanguageAvailable(options: IsLanguageAvailableOption) => Promise<IsLanguageAvailableResult>
```

Check if a language is available for speech synthesis.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#islanguageavailableoption">IsLanguageAvailableOption</a></code> |

**Returns:** <code>Promise&lt;<a href="#islanguageavailableresult">IsLanguageAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isVoiceAvailable(...)

```typescript
isVoiceAvailable(options: IsVoiceAvailableOption) => Promise<IsVoiceAvailableResult>
```

Check if a voice is available for speech synthesis.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isvoiceavailableoption">IsVoiceAvailableOption</a></code> |

**Returns:** <code>Promise&lt;<a href="#isvoiceavailableresult">IsVoiceAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### pause()

```typescript
pause() => Promise<void>
```

Pause speech immediately.

**Since:** 7.2.0

--------------------


### resume()

```typescript
resume() => Promise<void>
```

Resume speech.

**Since:** 7.2.0

--------------------


### speak(...)

```typescript
speak(options: SpeakOptions) => Promise<SpeakResult>
```

Add an utterance to the utterance queue to be spoken.

The `end` event will be emitted when the utterance has finished.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#speakoptions">SpeakOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#speakresult">SpeakResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### synthesizeToFile(...)

```typescript
synthesizeToFile(options: SynthesizeToFileOptions) => Promise<SynthesizeToFileResult>
```

Add an utterance to the utterance queue to be synthesized to a file.

The `end` event will be emitted when the utterance has finished.

Only available on Android and iOS.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#speakoptions">SpeakOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#synthesizetofileresult">SynthesizeToFileResult</a>&gt;</code>

**Since:** 7.1.0

--------------------


### addListener('boundary', ...)

```typescript
addListener(eventName: 'boundary', listenerFunc: (event: BoundaryEvent) => void) => Promise<PluginListenerHandle>
```

Called hen the spoken utterance reaches a word boundary.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'boundary'</code>                                                     |
| **`listenerFunc`** | <code>(event: <a href="#boundaryevent">BoundaryEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('end', ...)

```typescript
addListener(eventName: 'end', listenerFunc: (event: EndEvent) => void) => Promise<PluginListenerHandle>
```

Called when the spoken utterance has finished.

| Param              | Type                                                              |
| ------------------ | ----------------------------------------------------------------- |
| **`eventName`**    | <code>'end'</code>                                                |
| **`listenerFunc`** | <code>(event: <a href="#endevent">EndEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('error', ...)

```typescript
addListener(eventName: 'error', listenerFunc: (event: ErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during speech synthesis.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'error'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#errorevent">ErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('start', ...)

```typescript
addListener(eventName: 'start', listenerFunc: (event: StartEvent) => void) => Promise<PluginListenerHandle>
```

Called when the spoken utterance has started.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'start'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#startevent">StartEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for the plugin.

**Since:** 6.0.0

--------------------


### Interfaces


#### ActivateAudioSessionOptions

| Prop           | Type                                                                  | Description                        | Since |
| -------------- | --------------------------------------------------------------------- | ---------------------------------- | ----- |
| **`category`** | <code><a href="#audiosessioncategory">AudioSessionCategory</a></code> | The audio session category to set. | 6.0.0 |


#### GetLanguagesResult

| Prop            | Type                  | Description                                     | Since |
| --------------- | --------------------- | ----------------------------------------------- | ----- |
| **`languages`** | <code>string[]</code> | The available languages as BC-47 language tags. | 6.0.0 |


#### GetVoicesResult

| Prop         | Type                 | Description           | Since |
| ------------ | -------------------- | --------------------- | ----- |
| **`voices`** | <code>Voice[]</code> | The available voices. | 6.0.0 |


#### Voice

| Prop                              | Type                            | Description                                                           | Since |
| --------------------------------- | ------------------------------- | --------------------------------------------------------------------- | ----- |
| **`default`**                     | <code>boolean</code>            | Whether or not the voice is the default voice. Only available on Web. | 6.0.0 |
| **`gender`**                      | <code>'female' \| 'male'</code> | The gender of the voice. Only available on iOS.                       | 6.0.0 |
| **`id`**                          | <code>string</code>             | The identifier of the voice.                                          | 6.0.0 |
| **`isNetworkConnectionRequired`** | <code>boolean</code>            | Whether or not the voice is available via a local or remote service.  | 6.0.0 |
| **`language`**                    | <code>string</code>             | The BC-47 language tag for the language of the voice.                 | 6.0.0 |
| **`name`**                        | <code>string</code>             | The name of the voice.                                                | 6.0.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                         | Since |
| ----------------- | -------------------- | ------------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether or not speech synthesis is available on the current device. | 6.0.0 |


#### IsSpeakingResult

| Prop             | Type                 | Description                                            | Since |
| ---------------- | -------------------- | ------------------------------------------------------ | ----- |
| **`isSpeaking`** | <code>boolean</code> | Whether or not an utterance is currently being spoken. | 6.0.0 |


#### IsLanguageAvailableResult

| Prop              | Type                 | Description                                                    | Since |
| ----------------- | -------------------- | -------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether or not the language is available for speech synthesis. | 6.0.0 |


#### IsLanguageAvailableOption

| Prop           | Type                | Description                                       | Since |
| -------------- | ------------------- | ------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The BC-47 language tag for the language to check. | 6.0.0 |


#### IsVoiceAvailableResult

| Prop              | Type                 | Description                                                 | Since |
| ----------------- | -------------------- | ----------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether or not the voice is available for speech synthesis. | 6.0.0 |


#### IsVoiceAvailableOption

| Prop          | Type                | Description                           | Since |
| ------------- | ------------------- | ------------------------------------- | ----- |
| **`voiceId`** | <code>string</code> | The identifier of the voice to check. | 6.0.0 |


#### SpeakResult

| Prop              | Type                | Description                                           | Since |
| ----------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`utteranceId`** | <code>string</code> | The identifier of the utterance that is being spoken. | 6.0.0 |


#### SpeakOptions

| Prop                       | Type                                                                  | Description                                                                                                                                                                                                                                          | Default                                       | Since |
| -------------------------- | --------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------- | ----- |
| **`audioSessionCategory`** | <code><a href="#audiosessioncategory">AudioSessionCategory</a></code> | The audio session category to use for speech synthesis. The audio session will be automatically activated with this category when speech starts and deactivated when speech ends, restoring the previous audio session state. Only available on iOS. | <code>AudioSessionCategory.SoloAmbient</code> | 8.0.0 |
| **`language`**             | <code>string</code>                                                   | The BC-47 language tag for the language to use for speech synthesis. On **iOS**, this option is only used when the `voiceId` option is not provided.                                                                                                 |                                               | 6.0.0 |
| **`pitch`**                | <code>number</code>                                                   | The pitch that the utterance will be spoken at.                                                                                                                                                                                                      | <code>1.0</code>                              | 6.0.0 |
| **`queueStrategy`**        | <code><a href="#queuestrategy">QueueStrategy</a></code>               | The queue strategy to use for the utterance.                                                                                                                                                                                                         | <code>QueueStrategy.Add</code>                | 6.0.0 |
| **`rate`**                 | <code>number</code>                                                   | The speed at which the utterance will be spoken at.                                                                                                                                                                                                  | <code>1.0</code>                              | 6.0.0 |
| **`text`**                 | <code>string</code>                                                   | The text that will be synthesized when the utterance is spoken.                                                                                                                                                                                      |                                               | 6.0.0 |
| **`voiceId`**              | <code>string</code>                                                   | The identifier of the voice to use for speech synthesis.                                                                                                                                                                                             |                                               | 6.0.0 |
| **`volume`**               | <code>number</code>                                                   | The volume that the utterance will be spoken at.                                                                                                                                                                                                     | <code>1.0</code>                              | 6.0.0 |


#### SynthesizeToFileResult

| Prop       | Type                | Description                                                                                                                                                 | Since |
| ---------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path to which the synthesized audio file will be saved. The file is available as soon as the `end` event is emitted. Only available on Android and iOS. | 7.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BoundaryEvent

| Prop              | Type                | Description                                           | Since |
| ----------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`endIndex`**    | <code>number</code> | The index of the last character in the word.          | 6.0.0 |
| **`startIndex`**  | <code>number</code> | The index of the first character in the word.         | 6.0.0 |
| **`utteranceId`** | <code>string</code> | The identifier of the utterance that is being spoken. | 6.0.0 |
| **`word`**        | <code>string</code> | The word that was spoken.                             | 6.0.0 |


#### EndEvent

| Prop              | Type                | Description                                        | Since |
| ----------------- | ------------------- | -------------------------------------------------- | ----- |
| **`utteranceId`** | <code>string</code> | The identifier of the utterance that has finished. | 6.0.0 |


#### ErrorEvent

| Prop              | Type                | Description                                            | Since |
| ----------------- | ------------------- | ------------------------------------------------------ | ----- |
| **`message`**     | <code>string</code> | The error message.                                     | 6.0.0 |
| **`utteranceId`** | <code>string</code> | The identifier of the utterance that caused the error. | 6.0.0 |


#### StartEvent

| Prop              | Type                | Description                                       | Since |
| ----------------- | ------------------- | ------------------------------------------------- | ----- |
| **`utteranceId`** | <code>string</code> | The identifier of the utterance that has started. | 6.0.0 |


### Type Aliases


#### SynthesizeToFileOptions

<code><a href="#speakoptions">SpeakOptions</a></code>


### Enums


#### AudioSessionCategory

| Members             | Value                          | Description                                                                                                                                         | Since |
| ------------------- | ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Ambient`**       | <code>'AMBIENT'</code>         | The audio session category for ambient sound. Audio from other apps mixes with your audio. Screen locking and the Silent switch silence your audio. | 6.0.0 |
| **`MultiRoute`**    | <code>'MULTI_ROUTE'</code>     | The audio session category for routing distinct streams to different outputs.                                                                       | 8.0.0 |
| **`PlayAndRecord`** | <code>'PLAY_AND_RECORD'</code> | The audio session category for recording or playback.                                                                                               | 8.0.0 |
| **`Playback`**      | <code>'PLAYBACK'</code>        | The audio session category for playback. App audio continues with the Silent switch set to silent or when the screen locks.                         | 6.0.0 |
| **`Record`**        | <code>'RECORD'</code>          | The audio session category for recording.                                                                                                           | 8.0.0 |
| **`SoloAmbient`**   | <code>'SOLO_AMBIENT'</code>    | The default audio session category. Audio from other apps is silenced. Screen locking and the Silent switch silence your audio.                     | 8.0.0 |


#### QueueStrategy

| Members     | Value          | Description                                                          | Since |
| ----------- | -------------- | -------------------------------------------------------------------- | ----- |
| **`Add`**   | <code>0</code> | Add the utterance to the end of the queue.                           | 6.0.0 |
| **`Flush`** | <code>1</code> | Flush the queue and add the utterance to the beginning of the queue. | 6.0.0 |

</docgen-api>

## FAQ

### How do I know when an utterance has finished?

The `speak(...)` and `synthesizeToFile(...)` methods resolve as soon as the utterance has been added to the utterance queue, not when it has finished. To be notified when the utterance has finished, add a listener for the `end` event and compare the `utteranceId` of the event with the one returned by the method, as shown in the [usage example](#speak-a-text) above.

### Why is no audio played when my iPhone is in silent mode?

By default, the plugin uses the `SoloAmbient` audio session category on iOS, which is silenced by the Silent switch and when the screen locks. If the speech should continue to play in silent mode or when the screen locks, set the `audioSessionCategory` option of the `speak(...)` method to `AudioSessionCategory.Playback`.

### Can I save the synthesized speech as an audio file?

Yes, use the `synthesizeToFile(...)` method to synthesize an utterance to an audio file instead of speaking it. The result contains the path to which the file is saved, and the file is available as soon as the `end` event is emitted. This method is only available on Android and iOS.

### How do I use a specific voice?

First, retrieve the available voices using the `getVoices()` method. Then pass the identifier of the desired voice as the `voiceId` option to the `speak(...)` method. Note that on iOS, the `language` option is only used when the `voiceId` option is not provided.

### What is the difference between the `Add` and `Flush` queue strategies?

The `QueueStrategy.Add` strategy adds the utterance to the end of the utterance queue, so it is spoken after all previously queued utterances. The `QueueStrategy.Flush` strategy flushes the queue and adds the utterance to the beginning of the queue, so it is spoken as soon as possible.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Audio Player](https://capawesome.io/docs/sdks/capacitor/audio-player/): Play audio with background support.
- [Audio Recorder](https://capawesome.io/docs/sdks/capacitor/audio-recorder/): Record audio using the device's microphone.
- [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/): Transcribe speech into text (also known as speech-to-text).

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-synthesis/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-synthesis/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/speech-synthesis/LICENSE).
