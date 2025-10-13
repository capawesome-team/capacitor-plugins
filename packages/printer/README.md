# @capawesome-team/capacitor-printer

Capacitor plugin for seamless printing on Android and iOS. Supports base64, files, HTML, PDFs, and web views.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for printing. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🖨️ **Base64 Printer**: Print base64 encoded files.
- 🖨️ **File Printer**: Print files from the device.
- 🖨️ **HTML Printer**: Print custom HTML content.
- 🖨️ **PDF Printer**: Print PDF files.
- 🖨️ **Web View Printer**: Print web view content.
- ⚔️ **Battle-Tested**: Used in more than 100 projects.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

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

Next, install the package:

```
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

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.0.1`)
- `$androidxPrintVersion` version of `androidx.print:print` (default: `1.0.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

const printBase64 = async () => {
  await Printer.printBase64({
    name: 'My Document',
    data: 'JVBERi0...',
  });
}

const printFile = async () => {
  await Printer.printFile({
    mimeType: 'application/pdf',
    path: 'content://com.android.providers.downloads.documents/document/msf%3A1000000485',
  });
};

const printHtml = async () => {
  await Printer.printHtml({
    name: 'My Document',
    html: '<h1>Hello World</h1>',
  });
};

const printPdf = async () => {
  await Printer.printPdf({
    name: 'My Document',
    path: 'content://com.android.providers.downloads.documents/document/msf%3A1000000485',
  });
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/LICENSE).
