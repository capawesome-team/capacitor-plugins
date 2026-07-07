# Capacitor Clipboard Plugin

Capacitor plugin to read from and write to the system clipboard.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📋 **Read & Write**: Read from and write to the system clipboard.
- 🖼️ **Images**: Copy and paste real images, not just data URLs as plain text.
- 📝 **Rich Text**: Write HTML content with a plain-text fallback.
- 🔗 **URLs**: Copy URLs as native URL clipboard items.
- 🌐 **Cross-platform**: Works on Android, iOS, and the web.
- 🔒 **App Store safe**: Uses only official platform APIs.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-clipboard` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-clipboard
npx cap sync
```

### Android

No additional configuration is required for this plugin.

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Clipboard } from '@capawesome/capacitor-clipboard';

const writeText = async () => {
  await Clipboard.write({ text: 'Hello World' });
};

const writeHtml = async () => {
  await Clipboard.write({
    html: '<b>Hello World</b>',
    text: 'Hello World',
  });
};

const writeImage = async () => {
  await Clipboard.write({
    image: 'data:image/png;base64,iVBORw0KGgo...',
  });
};

const writeUrl = async () => {
  await Clipboard.write({ url: 'https://capawesome.io' });
};

const read = async () => {
  const { type, value } = await Clipboard.read();
  console.log('Type:', type, 'Value:', value);
};
```

## API

<docgen-index>

* [`read()`](#read)
* [`write(...)`](#write)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### read()

```typescript
read() => Promise<ReadResult>
```

Read the current content of the system clipboard.

On Android, reading the clipboard is only possible while the app is in the
foreground.

On iOS, reading the clipboard displays a system paste notification. This is
expected behavior and cannot be suppressed.

**Returns:** <code>Promise&lt;<a href="#readresult">ReadResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### write(...)

```typescript
write(options: WriteOptions) => Promise<void>
```

Write content to the system clipboard.

Exactly one of `text`, `html`, `image` or `url` must be provided. The
`html` property may additionally be combined with `text` to provide a
plain-text fallback.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#writeoptions">WriteOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### ReadResult

| Prop        | Type                                                                  | Description                                                                                     | Since |
| ----------- | --------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- | ----- |
| **`type`**  | <code><a href="#clipboardcontenttype">ClipboardContentType</a></code> | The type of the content that was read from the clipboard.                                       | 0.1.0 |
| **`value`** | <code>string</code>                                                   | The content that was read from the clipboard. Images are returned as a Base64-encoded data URL. | 0.1.0 |


#### WriteOptions

| Prop        | Type                | Description                                                                                                                                     | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`html`**  | <code>string</code> | The HTML content to write to the clipboard. Combine this with `text` to provide a plain-text fallback for apps that cannot handle HTML content. | 0.1.0 |
| **`image`** | <code>string</code> | The image to write to the clipboard as a Base64-encoded data URL.                                                                               | 0.1.0 |
| **`label`** | <code>string</code> | The label used to describe the clipboard content. Only available on Android.                                                                    | 0.1.0 |
| **`text`**  | <code>string</code> | The plain text to write to the clipboard.                                                                                                       | 0.1.0 |
| **`url`**   | <code>string</code> | The URL to write to the clipboard.                                                                                                              | 0.1.0 |


### Enums


#### ClipboardContentType

| Members     | Value                | Description                                                     | Since |
| ----------- | -------------------- | --------------------------------------------------------------- | ----- |
| **`Html`**  | <code>'HTML'</code>  | The content is HTML.                                            | 0.1.0 |
| **`Image`** | <code>'IMAGE'</code> | The content is an image, returned as a Base64-encoded data URL. | 0.1.0 |
| **`Text`**  | <code>'TEXT'</code>  | The content is plain text.                                      | 0.1.0 |
| **`Url`**   | <code>'URL'</code>   | The content is a URL.                                           | 0.1.0 |

</docgen-api>

## Platform Behavior

### Android

- On Android 10 (API level 29) and later, apps can only read the clipboard while they are in the foreground and have input focus. The `read(...)` method rejects otherwise.
- On Android 12 (API level 31) and later, the system shows a toast message when an app reads the clipboard content that was written by another app. This is expected behavior and cannot be suppressed.

### iOS

- On iOS 14 and later, the system shows a paste notification banner every time the clipboard is read. This is expected behavior and cannot be suppressed.

## Migrating from `@capacitor/clipboard`

This plugin is a drop-in alternative to the official [`@capacitor/clipboard`](https://github.com/ionic-team/capacitor-plugins/tree/main/clipboard) plugin with real image support on Android and HTML support on all platforms. The following differences apply:

| `@capacitor/clipboard`                     | `@capawesome/capacitor-clipboard`                      |
| ------------------------------------------ | ------------------------------------------------------ |
| `write({ string: 'Hello' })`               | `write({ text: 'Hello' })`                             |
| `write({ image: dataUrl })` (text-only on Android) | `write({ image: dataUrl })` (real image on all platforms) |
| —                                          | `write({ html: '<b>Hello</b>', text: 'Hello' })`       |
| `read() → { value, type: 'text/plain' }`   | `read() → { value, type: 'TEXT' }`                     |

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/clipboard/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/clipboard/LICENSE).
