# Capacitor Printer Plugin

Capacitor plugin for seamless printing on Android and iOS. Supports base64, files, HTML, PDFs, and web views.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Printer plugin is one of the most complete printing solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🖨️ **Base64 Printer**: Print base64 encoded files.
- 🖨️ **File Printer**: Print files from the device.
- 🖨️ **HTML Printer**: Print custom HTML content.
- 🖨️ **PDF Printer**: Print PDF files.
- 🖨️ **Web View Printer**: Print web view content.
- ⚔️ **Battle-Tested**: Used in more than 100 projects.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Printer plugin is typically used whenever an app needs to send content to a printer, for example:

- **Receipts and invoices**: Print HTML-formatted receipts or invoices directly from your app.
- **Documents**: Print PDF documents or other files stored on the device.
- **Server-generated content**: Print documents received from an API as base64 encoded data.
- **Reports and summaries**: Print the current web view content, customized with a print style sheet.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |

## Demo

A working example can be found [here](https://github.com/robingenz/capacitor-plugin-demo).

| Android                                                                                                                                                | iOS                                                                                                                                                |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/7de4bcb3-aa59-43bc-a882-2796964be539" width="324" alt="Android Demo" /> | <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/d796d9f0-32c1-4d5c-b38f-ab46509a5eda" width="324" alt="iOS Demo" /> |

## Guides

- [Exploring the Capacitor Printer API](https://capawesome.io/blog/exploring-the-capacitor-printer-api/)

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-printer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-printer
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.1.0`)
- `$androidxPrintVersion` version of `androidx.print:print` (default: `1.1.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to print a file from the device, a base64 encoded file, custom HTML content, a PDF document, and the web view content.

### Print a file from the device

Present the printing user interface to print a file stored on the device, for example a file the user previously downloaded. Only available on Android and iOS:

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

const printFile = async () => {
  await Printer.printFile({
    mimeType: 'application/pdf',
    path: 'content://com.android.providers.downloads.documents/document/msf%3A1000000485',
  });
};
```

### Print a base64 encoded file

Print a file that is only available as a base64 encoded string, for example a document received from an API. Note that large files can lead to app crashes, so it's recommended to use the `printFile(...)` method instead whenever possible. Only available on Android and iOS:

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

const printBase64 = async () => {
  await Printer.printBase64({
    name: 'My Document',
    data: 'JVBERi0...',
  });
};
```

### Print custom HTML content

Print custom HTML content, for example a receipt or invoice rendered by your app. Only available on Android and iOS:

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

const printHtml = async () => {
  await Printer.printHtml({
    name: 'My Document',
    html: '<h1>Hello World</h1>',
  });
};
```

### Print a PDF document

Print a PDF document stored on the device. Only available on Android and iOS:

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

const printPdf = async () => {
  await Printer.printPdf({
    name: 'My Document',
    path: 'content://com.android.providers.downloads.documents/document/msf%3A1000000485',
  });
};
```

### Print the web view content

Print the current content of the web view. You can use a [print style sheet](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_media_queries/Printing) to customize the print output:

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

const printWebView = async () => {
  await Printer.printWebView({
    name: 'My Document',
  });
};
```

## API

<docgen-index>

* [`printBase64(...)`](#printbase64)
* [`printFile(...)`](#printfile)
* [`printHtml(...)`](#printhtml)
* [`printPdf(...)`](#printpdf)
* [`printWebView(...)`](#printwebview)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### printBase64(...)

```typescript
printBase64(options: PrintBase64Options) => Promise<void>
```

Present the printing user interface to print files encoded as base64 strings.

**Attention**: Large files can lead to app crashes. It's therefore recommended to use the `printFile` method instead.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#printbase64options">PrintBase64Options</a></code> |

**Since:** 7.1.0

--------------------


### printFile(...)

```typescript
printFile(options: PrintFileOptions) => Promise<void>
```

Present the printing user interface to print files.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#printfileoptions">PrintFileOptions</a></code> |

**Since:** 7.1.0

--------------------


### printHtml(...)

```typescript
printHtml(options: PrintHtmlOptions) => Promise<void>
```

Present the printing user interface to print a html document.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#printhtmloptions">PrintHtmlOptions</a></code> |

**Since:** 5.0.0

--------------------


### printPdf(...)

```typescript
printPdf(options: PrintPdfOptions) => Promise<void>
```

Present the printing user interface to print a pdf document.

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#printpdfoptions">PrintPdfOptions</a></code> |

**Since:** 5.1.0

--------------------


### printWebView(...)

```typescript
printWebView(options?: PrintOptions | undefined) => Promise<void>
```

Present the printing user interface to print the web view content.

You can use a print style sheet to customize the print
output (see https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_media_queries/Printing).

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#printoptions">PrintOptions</a></code> |

**Since:** 5.0.0

--------------------


### Interfaces


#### PrintBase64Options

| Prop           | Type                | Description                                                                                                                                               | Since |
| -------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`data`**     | <code>string</code> | A valid base 64 encoded string.                                                                                                                           | 7.1.0 |
| **`mimeType`** | <code>string</code> | The mime type of the data. The following mime types are supported: `application/pdf`, `image/gif`, `image/heic`, `image/heif`, `image/jpeg`, `image/png`. | 7.1.0 |


#### PrintFileOptions

| Prop           | Type                | Description                                           | Since |
| -------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`mimeType`** | <code>string</code> | The mime type of the file. Only available on Android. | 7.1.0 |
| **`path`**     | <code>string</code> | The path to the file.                                 | 7.1.0 |


#### PrintHtmlOptions

| Prop       | Type                | Description                | Since |
| ---------- | ------------------- | -------------------------- | ----- |
| **`html`** | <code>string</code> | The HTML content to print. | 5.0.0 |


#### PrintPdfOptions

| Prop       | Type                | Description                                 | Since |
| ---------- | ------------------- | ------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path to the pdf document on the device. | 5.1.0 |


#### PrintOptions

| Prop       | Type                | Description                | Default                 | Since |
| ---------- | ------------------- | -------------------------- | ----------------------- | ----- |
| **`name`** | <code>string</code> | The name of the print job. | <code>'Document'</code> | 5.0.0 |


### Type Aliases


#### PrintWebViewOptions

<code><a href="#printoptions">PrintOptions</a></code>

</docgen-api>

## FAQ

### Can I print on the web?

On the web, only the `printWebView(...)` method is available, which prints the current web view content. The `printBase64(...)`, `printFile(...)`, `printHtml(...)` and `printPdf(...)` methods are only available on Android and iOS.

### Why does my app crash when printing large base64 files?

The `printBase64(...)` method loads the entire file into memory, which can lead to app crashes for large files. It's therefore recommended to use the `printFile(...)` method instead, which prints the file directly from its path on the device.

### Which file types can I print?

The `printBase64(...)` method supports the mime types `application/pdf`, `image/gif`, `image/heic`, `image/heif`, `image/jpeg`, and `image/png`. In addition, you can print arbitrary files from the device with `printFile(...)`, PDF documents with `printPdf(...)`, custom HTML content with `printHtml(...)`, and the current web view content with `printWebView(...)`.

### How can I customize the print output of the web view?

You can use a print style sheet to customize how the web view content is printed, for example to hide navigation elements or adjust the layout. Check out the [MDN documentation on printing](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_media_queries/Printing) for more information.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [PDF Generator](https://capawesome.io/docs/sdks/capacitor/pdf-generator/): Generate paginated PDF files from HTML content or URLs.
- [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/): Display PDF documents in a fullscreen native viewer.
- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select a file to print from the device's file system.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/LICENSE).
