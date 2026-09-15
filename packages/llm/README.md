# Capacitor LLM Plugin

Capacitor plugin for running on-device large language models (LLMs) on Android and iOS. Uses the system-provided models Apple Intelligence (Foundation Models) and Gemini Nano (AICore) with chat sessions, token streaming and cancellation.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor LLM plugin brings the platform's own on-device AI models to your Capacitor app. Here are some of the key features:

- 🧠 **System Models**: Uses the models that ship with the operating system (Apple Intelligence on iOS, Gemini Nano on Android). No model files to bundle, no API keys, no cloud calls.
- 🔒 **Private by Design**: All inference runs on the device. Prompts and responses never leave the user's phone.
- 💬 **Chat Sessions**: Create chats with instructions (system prompt) that keep the conversation context across multiple generations.
- 🌊 **Token Streaming**: Stream the response chunk by chunk via events for a responsive UI.
- ✋ **Cancellation**: Cancel an in-flight generation at any time.
- 🚦 **Typed Availability**: Check the model availability with typed status values and get notified about changes.
- ⤵️ **Model Download**: Trigger the Gemini Nano model download on Android with progress events.
- 🔓 **Public APIs Only**: Built exclusively on public platform APIs, so it is safe for App Review and resilient to OS updates.
- 🤝 **Compatibility**: Works hand in hand with the [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/) and [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The LLM plugin is typically used whenever an app needs AI text generation without sending data to a server, for example:

- **Summarization**: Summarize notes, articles or messages on the device.
- **Smart Replies**: Suggest replies for chats and emails.
- **Rewriting**: Rephrase, shorten or proofread user-written text.
- **Offline Assistants**: Build chat assistants that work without an internet connection.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Demo

| Android                                                                                                                                                          | iOS                                                                                                                                                          |
| ---------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/llm/assets/llm-demo-android.mp4" width="324" controls></video> | <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/llm/assets/llm-demo-ios.mp4" width="266" controls></video> |

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-llm` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-llm
npx cap sync
```

### Android

The plugin uses the [ML Kit GenAI Prompt API](https://developers.google.com/ml-kit/genai/prompt/android) to run Gemini Nano via AICore. The SDK is declared as a regular Gradle dependency and fetched from Google's Maven repository when your app is built. The model itself is managed by the system (AICore) and is **not** bundled with your app.

**Attention**:

- Gemini Nano is only available on [Gemini Nano-capable devices](https://developers.google.com/ml-kit/genai#supported_devices) (for example the Google Pixel 9 series or Samsung Galaxy S25 series) with AICore. The list of supported devices is still short, so always check `getAvailability()` at runtime and design a fallback.
- The ML Kit GenAI Prompt SDK is still in **beta**, so breaking changes in the underlying SDK are possible.
- Apps using Gemini Nano are subject to Google's [Generative AI Prohibited Use Policy](https://policies.google.com/terms/generative-ai/use-policy).

#### Minimum SDK Version

The ML Kit GenAI Prompt SDK requires a minimum SDK version of `26`.
Make sure that the `minSdkVersion` in your `android/variables.gradle` file is set to at least `26`:

```groovy
ext {
    minSdkVersion = 26
}
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default versions of the dependencies:

- `$mlkitGenaiPromptVersion` version of `com.google.mlkit:genai-prompt` (default: `1.0.0-beta2`)
- `$kotlinVersion` version of `org.jetbrains.kotlin:kotlin-gradle-plugin` (default: `2.1.20`)
- `$kotlinxCoroutinesVersion` version of `org.jetbrains.kotlinx:kotlinx-coroutines-android` (default: `1.10.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

The plugin uses the [Foundation Models](https://developer.apple.com/documentation/foundationmodels) framework, which is part of the operating system. No additional dependencies or configuration are required.

**Attention**:

- The Foundation Models framework requires **iOS 26 or later** on an [Apple Intelligence-enabled device](https://www.apple.com/apple-intelligence/) (iPhone 15 Pro or later) with Apple Intelligence turned on. On older iOS versions, all methods except `getAvailability()` reject as unavailable.
- Building the plugin requires **Xcode 26 or later**.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check the model availability, download the model, create and delete chats, generate text, stream the response, cancel a generation, and tune the generation parameters.

### Check the model availability

The plugin uses the model that ships with the operating system, so its availability depends on the device and OS version. Always call `getAvailability()` before you generate text and handle every status. Listen for the `availabilityChange` event to be notified when the status changes:

| Status                | Meaning                                             | What you can do                                                             |
| --------------------- | --------------------------------------------------- | --------------------------------------------------------------------------- |
| `available`           | The model is ready to use.                          | Start generating.                                                           |
| `device-not-eligible` | The device hardware does not support the model.     | Offer a fallback (for example a cloud-based or custom model).               |
| `downloadable`        | The model can be downloaded.                        | Call `downloadModel()` to start the download.                               |
| `downloading`         | The model is currently being downloaded.            | Wait and listen for `availabilityChange` events.                            |
| `not-enabled`         | The model is disabled on the device.                | Ask the user to enable Apple Intelligence in the system settings.           |
| `not-ready`           | The model is not ready yet.                         | Try again later.                                                            |
| `unavailable`         | No system model exists on this platform/OS version. | Offer a fallback or hide the feature.                                       |

On Android, only `available`, `downloadable`, `downloading` and `unavailable` are reported. On iOS, only `available`, `device-not-eligible`, `not-enabled`, `not-ready` and `unavailable` are reported:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const getAvailability = async () => {
  const { status } = await Llm.getAvailability();
  return status;
};

const addAvailabilityChangeListener = async () => {
  await Llm.addListener('availabilityChange', event => {
    console.log(`Availability changed: ${event.status}`);
  });
};
```

### Download the model

If the availability status is `downloadable`, the model must be downloaded before it can be used. On Android, trigger the download explicitly and follow its progress via the `downloadProgress` event. On iOS, the download is managed by the system and cannot be triggered by the app. Only available on Android:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const downloadModel = async () => {
  const { status } = await Llm.getAvailability();
  if (status !== 'downloadable') {
    return;
  }
  await Llm.addListener('downloadProgress', event => {
    console.log(`Download progress: ${event.progress * 100}%`);
  });
  await Llm.downloadModel();
};
```

### Create and delete a chat

A chat keeps the conversation context across multiple generations and can be given instructions (system prompt) that guide the model's responses. Chats live in memory until they are deleted with `deleteChat(...)`, so delete the chats you no longer need to free the associated native resources. Only available on Android and iOS:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const createChat = async () => {
  const { id } = await Llm.createChat({
    instructions: 'You are a helpful assistant that answers briefly.',
  });
  return id;
};

const deleteChat = async (chatId: string) => {
  await Llm.deleteChat({ id: chatId });
};
```

### Generate text

Generate a response for a prompt and wait for the complete text. Only one generation can be in flight per chat at a time. Only available on Android and iOS:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const generateText = async (chatId: string) => {
  const { text } = await Llm.generateText({
    chatId,
    prompt: 'Why is the sky blue?',
  });
  return text;
};
```

### Stream the response

Stream the response chunk by chunk via the `textChunk` event for a more responsive UI. The returned promise resolves with the complete response text. Only available on Android and iOS:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const streamText = async (chatId: string) => {
  await Llm.addListener('textChunk', event => {
    if (event.chatId === chatId) {
      console.log(event.text); // Append the chunks to your UI
    }
  });
  const { text } = await Llm.streamText({
    chatId,
    prompt: 'Tell me a short story about a magical dog.',
  });
  return text; // The complete response text
};
```

### Cancel a generation

Cancel the in-flight generation of a chat. The pending promise rejects with the `GENERATION_CANCELED` error code. On iOS, the generation is canceled immediately. On Android, cancellation is best-effort, so a few more chunks may be emitted. Only available on Android and iOS:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const cancelGeneration = async (chatId: string) => {
  await Llm.cancelGeneration({ chatId });
};
```

### Tune the generation parameters

`maxOutputTokens` and `temperature` can be set per chat with `createChat(...)` and overridden per request with `generateText(...)` or `streamText(...)`. Only available on Android and iOS:

```typescript
import { Llm } from '@capawesome-team/capacitor-llm';

const createChatWithParameters = async () => {
  const { id } = await Llm.createChat({
    maxOutputTokens: 256,
    temperature: 0.7,
  });
  const { text } = await Llm.generateText({
    chatId: id,
    prompt: 'Write a haiku about the sea.',
    temperature: 0.2, // Overrides the chat's default value
  });
  return text;
};
```

The platforms enforce different limits on the generation parameters:

| Parameter         | Android (Gemini Nano)                  | iOS (Apple Intelligence)                     |
| ----------------- | -------------------------------------- | -------------------------------------------- |
| `maxOutputTokens` | Limited to a maximum of `4096` tokens. | No documented hard limit.                    |
| `temperature`     | Must be between `0.0` and `1.0`.       | Values greater than `1.0` are allowed.       |
| Context size      | Input must be under ~4,000 tokens.     | Context window of ~4,096 tokens per session. |

If the context limit of a chat is exceeded, the generation rejects with the `GENERATION_FAILED` error code. In that case, create a new chat.

## API

<docgen-index>

* [`cancelGeneration(...)`](#cancelgeneration)
* [`createChat(...)`](#createchat)
* [`deleteChat(...)`](#deletechat)
* [`downloadModel()`](#downloadmodel)
* [`generateText(...)`](#generatetext)
* [`getAvailability()`](#getavailability)
* [`streamText(...)`](#streamtext)
* [`addListener('availabilityChange', ...)`](#addlisteneravailabilitychange-)
* [`addListener('downloadProgress', ...)`](#addlistenerdownloadprogress-)
* [`addListener('textChunk', ...)`](#addlistenertextchunk-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### cancelGeneration(...)

```typescript
cancelGeneration(options: CancelGenerationOptions) => Promise<void>
```

Cancel the in-flight text generation of a chat.

The pending `generateText(...)` or `streamText(...)` promise rejects
with the `GENERATION_CANCELED` error code.

On **Android**, cancellation is best-effort. The generation is
interrupted as soon as possible but a few more chunks may be emitted.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#cancelgenerationoptions">CancelGenerationOptions</a></code> |

**Since:** 0.0.1

--------------------


### createChat(...)

```typescript
createChat(options?: CreateChatOptions | undefined) => Promise<CreateChatResult>
```

Create a new chat session.

A chat keeps the conversation context across multiple generations
until it is deleted with `deleteChat(...)`.

If a chat with the provided identifier already exists, the promise
rejects with the `CHAT_ALREADY_EXISTS` error code.

On **iOS**, each chat is backed by a native language model session
that maintains the conversation context.

On **Android**, the conversation history is kept in memory by the
plugin and included in each prompt since the system API does not
provide native multi-turn sessions.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#createchatoptions">CreateChatOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createchatresult">CreateChatResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### deleteChat(...)

```typescript
deleteChat(options: DeleteChatOptions) => Promise<void>
```

Delete a chat session and free the associated native resources.

An in-flight generation of the chat is canceled.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#deletechatoptions">DeleteChatOptions</a></code> |

**Since:** 0.0.1

--------------------


### downloadModel()

```typescript
downloadModel() => Promise<void>
```

Download the on-device model.

Call this method if `getAvailability()` returns the `downloadable`
status. The returned promise resolves when the download is complete.
The download progress is emitted via the `downloadProgress` event.

Only available on Android.

**Since:** 0.0.1

--------------------


### generateText(...)

```typescript
generateText(options: GenerateTextOptions) => Promise<GenerateTextResult>
```

Generate text for a prompt and resolve with the complete response.

Only one generation can be in flight per chat at a time. Starting
another one rejects with the `GENERATION_IN_PROGRESS` error code.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#generatetextoptions">GenerateTextOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#generatetextresult">GenerateTextResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getAvailability()

```typescript
getAvailability() => Promise<GetAvailabilityResult>
```

Get the availability status of the on-device model.

This method never rejects. On platforms or OS versions without a
system model it resolves with the `unavailable` status.

**Returns:** <code>Promise&lt;<a href="#getavailabilityresult">GetAvailabilityResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### streamText(...)

```typescript
streamText(options: StreamTextOptions) => Promise<StreamTextResult>
```

Generate text for a prompt and stream the response.

Incremental chunks are emitted via the `textChunk` event while the
generation is running. The returned promise resolves with the complete
response text when the generation is finished.

Only one generation can be in flight per chat at a time. Starting
another one rejects with the `GENERATION_IN_PROGRESS` error code.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#streamtextoptions">StreamTextOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#streamtextresult">StreamTextResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('availabilityChange', ...)

```typescript
addListener(eventName: 'availabilityChange', listenerFunc: (event: AvailabilityChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the availability status of the on-device model changes.

The plugin watches the availability status only while at least one
listener for this event is attached.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'availabilityChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#availabilitychangeevent">AvailabilityChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('downloadProgress', ...)

```typescript
addListener(eventName: 'downloadProgress', listenerFunc: (event: DownloadProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called while the on-device model is being downloaded.

Only available on Android.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'downloadProgress'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#downloadprogressevent">DownloadProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('textChunk', ...)

```typescript
addListener(eventName: 'textChunk', listenerFunc: (event: TextChunkEvent) => void) => Promise<PluginListenerHandle>
```

Called when a new text chunk is generated during a streaming generation
started with `streamText(...)`.

Only available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'textChunk'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#textchunkevent">TextChunkEvent</a>) =&gt; void</code> |

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


#### CancelGenerationOptions

| Prop         | Type                | Description                                                               | Since |
| ------------ | ------------------- | ------------------------------------------------------------------------- | ----- |
| **`chatId`** | <code>string</code> | The identifier of the chat whose in-flight generation should be canceled. | 0.0.1 |


#### CreateChatResult

| Prop     | Type                | Description                                | Since |
| -------- | ------------------- | ------------------------------------------ | ----- |
| **`id`** | <code>string</code> | The unique identifier of the created chat. | 0.0.1 |


#### CreateChatOptions

| Prop                  | Type                | Description                                                                                                                                                                                                                                          | Since |
| --------------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**              | <code>string</code> | The unique identifier of the chat. If not provided, a random UUID is generated.                                                                                                                                                                      | 0.0.1 |
| **`instructions`**    | <code>string</code> | The instructions (system prompt) that guide the model's responses in this chat.                                                                                                                                                                      | 0.0.1 |
| **`maxOutputTokens`** | <code>number</code> | The default maximum number of tokens the model may generate per response in this chat. Can be overridden per request. On **Android**, the value is limited to a maximum of `4096` tokens.                                                            | 0.0.1 |
| **`temperature`**     | <code>number</code> | The default sampling temperature for responses in this chat. Higher values produce more creative results, lower values produce more deterministic results. Can be overridden per request. On **Android**, the value must be between `0.0` and `1.0`. | 0.0.1 |


#### DeleteChatOptions

| Prop     | Type                | Description                           | Since |
| -------- | ------------------- | ------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the chat to delete. | 0.0.1 |


#### GenerateTextResult

| Prop       | Type                | Description         | Since |
| ---------- | ------------------- | ------------------- | ----- |
| **`text`** | <code>string</code> | The generated text. | 0.0.1 |


#### GenerateTextOptions

| Prop                  | Type                | Description                                                                                                                                                                   | Since |
| --------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`chatId`**          | <code>string</code> | The identifier of the chat to generate the text in.                                                                                                                           | 0.0.1 |
| **`maxOutputTokens`** | <code>number</code> | The maximum number of tokens the model may generate for this request. Overrides the chat's default value. On **Android**, the value is limited to a maximum of `4096` tokens. | 0.0.1 |
| **`prompt`**          | <code>string</code> | The prompt to generate a response for.                                                                                                                                        | 0.0.1 |
| **`temperature`**     | <code>number</code> | The sampling temperature for this request. Overrides the chat's default value. On **Android**, the value must be between `0.0` and `1.0`.                                     | 0.0.1 |


#### GetAvailabilityResult

| Prop         | Type                                                              | Description                                     | Since |
| ------------ | ----------------------------------------------------------------- | ----------------------------------------------- | ----- |
| **`status`** | <code><a href="#availabilitystatus">AvailabilityStatus</a></code> | The availability status of the on-device model. | 0.0.1 |


#### StreamTextResult

| Prop       | Type                | Description                  | Since |
| ---------- | ------------------- | ---------------------------- | ----- |
| **`text`** | <code>string</code> | The complete generated text. | 0.0.1 |


#### StreamTextOptions

| Prop                  | Type                | Description                                                                                                                                                                   | Since |
| --------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`chatId`**          | <code>string</code> | The identifier of the chat to generate the text in.                                                                                                                           | 0.0.1 |
| **`maxOutputTokens`** | <code>number</code> | The maximum number of tokens the model may generate for this request. Overrides the chat's default value. On **Android**, the value is limited to a maximum of `4096` tokens. | 0.0.1 |
| **`prompt`**          | <code>string</code> | The prompt to generate a response for.                                                                                                                                        | 0.0.1 |
| **`temperature`**     | <code>number</code> | The sampling temperature for this request. Overrides the chat's default value. On **Android**, the value must be between `0.0` and `1.0`.                                     | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### AvailabilityChangeEvent

| Prop         | Type                                                              | Description                                         | Since |
| ------------ | ----------------------------------------------------------------- | --------------------------------------------------- | ----- |
| **`status`** | <code><a href="#availabilitystatus">AvailabilityStatus</a></code> | The new availability status of the on-device model. | 0.0.1 |


#### DownloadProgressEvent

| Prop           | Type                | Description                                           | Since |
| -------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`progress`** | <code>number</code> | The download progress as a value between `0` and `1`. | 0.0.1 |


#### TextChunkEvent

| Prop         | Type                | Description                                                                                                            | Since |
| ------------ | ------------------- | ---------------------------------------------------------------------------------------------------------------------- | ----- |
| **`chatId`** | <code>string</code> | The identifier of the chat the chunk belongs to.                                                                       | 0.0.1 |
| **`text`**   | <code>string</code> | The newly generated text chunk. Append the chunks in the order they are received to reconstruct the complete response. | 0.0.1 |


### Type Aliases


#### AvailabilityStatus

The availability status of the on-device model.

- `available`: The model is downloaded and ready to use.
- `device-not-eligible`: The device does not support the on-device model.
- `downloadable`: The model can be downloaded. On Android, call
`downloadModel()` to trigger the download.
- `downloading`: The model is currently being downloaded.
- `not-enabled`: The on-device model is disabled. On iOS, the user must
enable Apple Intelligence in the system settings.
- `not-ready`: The model is not ready yet, for example because the system
is still preparing it. Try again later.
- `unavailable`: The model is not available on this platform or OS version.

<code>'available' | 'device-not-eligible' | 'downloadable' | 'downloading' | 'not-enabled' | 'not-ready' | 'unavailable'</code>

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It runs entirely on the platform's own on-device models — Apple Intelligence on iOS and Gemini Nano on Android — so prompts and responses never leave the device and there are no model files to bundle, API keys or cloud calls. You get chat sessions that keep context, token streaming, cancellation and typed availability checks through one fully typed API that is actively maintained against the latest OS and Capacitor versions, and it's backed by dedicated support. If you just need occasional cloud completions, a plain HTTP call may be enough; if you want private, offline text generation with a complete on-device workflow, this plugin is built for exactly that.

### Is text generation available on the web?

No. Browsers do not provide a system language model, so all methods except `getAvailability()` reject with an unimplemented error on the web. `getAvailability()` resolves with the `unavailable` status.

### Which devices support on-device text generation?

On iOS, all Apple Intelligence-enabled devices (iPhone 15 Pro or later) running iOS 26 or later. On Android, only [Gemini Nano-capable devices](https://developers.google.com/ml-kit/genai#supported_devices) with AICore, for example the Google Pixel 9 series or Samsung Galaxy S25 series. Always check `getAvailability()` at runtime.

### Can I use my own model files?

No. The plugin deliberately supports only the system-provided models. This keeps your app small and inference fast. Support for custom model runtimes may be added in the future.

### Why does a generation reject with `GENERATION_FAILED`?

The most common reasons are an exceeded context window (create a new chat in that case), content that is blocked by the platform's safety guardrails, or an exceeded AICore quota on Android. The error message contains the platform-specific reason.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/): Store chat data securely on the device.
- [Speech Recognition](https://capawesome.io/docs/sdks/capacitor/speech-recognition/): Turn the user's voice into prompts.
- [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/): Read the generated responses aloud.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/llm/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/llm/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/llm/LICENSE).
