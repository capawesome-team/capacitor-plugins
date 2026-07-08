# Capacitor Screen Reader Plugin

Capacitor plugin to interact with screen readers.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔎 **Detection**: Check whether a screen reader (VoiceOver/TalkBack) is currently enabled.
- 📣 **Announcements**: Post accessibility announcements that are read out by the active screen reader.
- 🔔 **State changes**: Listen for changes to the enabled state of the screen reader.
- 🖥️ **Cross-platform**: Supports Android, iOS and the web.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Accessibility Preferences](https://capawesome.io/docs/sdks/capacitor/accessibility-preferences/) and [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugins.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

> [!NOTE]
> This plugin does **not** perform text-to-speech. The `announce(...)` method posts an accessibility announcement that is only read out when a screen reader is active. If you are looking for real text-to-speech, use the [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugin instead.

## Use Cases

The Screen Reader plugin is typically used to make apps more accessible for users of VoiceOver and TalkBack, for example:

- **Accessible status updates**: Announce dynamic changes such as "The item was added to your cart." that would otherwise go unnoticed by screen reader users.
- **Adaptive user interfaces**: Check whether a screen reader is enabled and adapt your UI accordingly, for example by simplifying gestures or animations.
- **Reacting to state changes**: Listen for changes to the enabled state of the screen reader and adjust your app's behavior on the fly.
- **Localized announcements**: Provide the language of an announcement on Android so the screen reader pronounces it correctly.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-screen-reader` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-screen-reader
npx cap sync
```

### Android

No additional configuration is required for this plugin.

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { ScreenReader } from '@capawesome/capacitor-screen-reader';
```

### Post an accessibility announcement

Post an announcement that is read out by the active screen reader (VoiceOver/TalkBack). Note that this does not perform text-to-speech; the message is only read out if a screen reader is running:

```typescript
const announce = async () => {
  await ScreenReader.announce({
    value: 'The item was added to your cart.',
  });
};
```

### Check whether a screen reader is enabled

Check whether a screen reader is currently enabled. Only available on Android and iOS:

```typescript
const isEnabled = async () => {
  const { enabled } = await ScreenReader.isEnabled();
  return enabled;
};
```

### Listen for screen reader state changes

Get notified when the screen reader is enabled or disabled. Only available on Android and iOS:

```typescript
const addStateChangeListener = async () => {
  await ScreenReader.addListener('stateChange', event => {
    console.log('Screen reader enabled:', event.enabled);
  });
};

const removeAllListeners = async () => {
  await ScreenReader.removeAllListeners();
};
```

## API

<docgen-index>

* [`announce(...)`](#announce)
* [`isEnabled()`](#isenabled)
* [`addListener('stateChange', ...)`](#addlistenerstatechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### announce(...)

```typescript
announce(options: AnnounceOptions) => Promise<void>
```

Post an accessibility announcement to the active screen reader.

This does **not** perform text-to-speech. It posts an announcement that is
read out by the screen reader (VoiceOver/TalkBack) if one is active. For
real text-to-speech, use the [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/)
plugin instead.

On the web, the announcement is made through a visually hidden
`aria-live` region, so it is only read out if the user has a screen reader
running.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#announceoptions">AnnounceOptions</a></code> |

**Since:** 0.1.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Check whether a screen reader is currently enabled.

On Android, this refers to whether touch exploration (TalkBack) is enabled.
On iOS, this refers to whether VoiceOver is running.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('stateChange', ...)

```typescript
addListener(eventName: 'stateChange', listenerFunc: (event: StateChangeEvent) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the enabled state of the screen reader.

The device is only observed while at least one listener is attached.

Only available on Android and iOS.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'stateChange'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#statechangeevent">StateChangeEvent</a>) =&gt; void</code> |

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


#### AnnounceOptions

| Prop           | Type                | Description                                                                                                                                    | Since |
| -------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`value`**    | <code>string</code> | The message to announce.                                                                                                                       | 0.1.0 |
| **`language`** | <code>string</code> | The language of the message as a BCP 47 language tag. This helps the screen reader pronounce the message correctly. Only available on Android. | 0.1.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                                   | Since |
| ------------- | -------------------- | --------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether a screen reader is currently enabled. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### StateChangeEvent

| Prop          | Type                 | Description                                   | Since |
| ------------- | -------------------- | --------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether a screen reader is currently enabled. | 0.1.0 |

</docgen-api>

## Migrating from `@capacitor/screen-reader`

This plugin is a drop-in replacement for the official `@capacitor/screen-reader` plugin with a few naming changes:

| `@capacitor/screen-reader`        | Screen Reader                     |
| --------------------------------- | --------------------------------- |
| `speak({ value, language })`      | `announce({ value, language })`   |
| `isEnabled()`                     | `isEnabled()`                     |
| `addListener('stateChange', ...)` | `addListener('stateChange', ...)` |

The `speak(...)` method has been renamed to `announce(...)` to make it clear that it posts an accessibility announcement to the active screen reader and does **not** perform text-to-speech. For real text-to-speech, use the [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugin instead.

## FAQ

### Does the announce method perform text-to-speech?

No. The `announce(...)` method posts an accessibility announcement that is only read out by the screen reader (VoiceOver/TalkBack) if one is active. If you are looking for real text-to-speech that always speaks, use the [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/) plugin instead.

### Why is my announcement not read out?

Announcements are only read out when a screen reader is active. On Android and iOS, make sure TalkBack or VoiceOver is enabled. On the Web, the announcement is made through a visually hidden `aria-live` region, so it is only read out if the user has a screen reader running.

### Can I check whether a screen reader is enabled on the Web?

No. The `isEnabled()` method and the `stateChange` listener are only available on Android and iOS. Browsers do not expose whether a screen reader is running. The `announce(...)` method, however, also works on the Web via an `aria-live` region.

### How is this plugin different from the official @capacitor/screen-reader plugin?

This plugin is a drop-in replacement for the official `@capacitor/screen-reader` plugin with a few naming changes. Most notably, the `speak(...)` method has been renamed to `announce(...)` to make it clear that it posts an accessibility announcement and does not perform text-to-speech. See the [migration guide](#migrating-from-capacitorscreen-reader) above for a complete mapping.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accessibility Preferences](https://capawesome.io/docs/sdks/capacitor/accessibility-preferences/): Read the user's system accessibility preferences.
- [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/): Synthesize speech from text (text-to-speech) with voice selection, pitch, and rate control.
- [Text Zoom](https://capawesome.io/docs/sdks/capacitor/text-zoom/): Read and control the WebView text zoom.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-reader/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-reader/LICENSE).
