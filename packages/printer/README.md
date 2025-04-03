# @capawesome-team/capacitor-printer

Capacitor plugin for printing.

## Features

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🖨️ **PDF Printer**: Print PDF files.
- 🖨️ **HTML Printer**: Print custom HTML content.
- 🖨️ **Web View Printer**: Print web view content.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 5.x.x          | 5.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 7.x.x          | >=7.x.x           | Active support |

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

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.0.1`)
- `$androidxPrintVersion` version of `androidx.print:print` (default: `1.0.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                             | iOS                                                                                                                                 |
| ----------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/7de4bcb3-aa59-43bc-a882-2796964be539" width="324" /> | <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/d796d9f0-32c1-4d5c-b38f-ab46509a5eda" width="324" /> |

## Usage

```typescript
import { Printer } from '@capawesome-team/capacitor-printer';

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

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#printbase64options">PrintBase64Options</a></code> |

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

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/printer/LICENSE).
