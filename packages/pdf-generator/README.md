# Capacitor PDF Generator Plugin

Capacitor plugin to generate paginated PDF files from HTML content or URLs.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📄 **HTML to PDF**: Generate PDF files from HTML strings, for example invoices, tickets, and reports.
- 🌐 **URL to PDF**: Generate PDF files from web pages.
- 📃 **Pagination**: Real page breaks with selectable page sizes (A3, A4, A5, Letter) and orientation.
- 🖋️ **Vector Output**: Text stays selectable and sharp at any zoom level.
- 📂 **File Output**: Results are written to a file so even large documents don't exhaust memory.
- ⏱️ **Timeout**: Configurable timeout so hung pages never hang your app.
- 🤝 **Compatibility**: Works alongside the [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/), [Printer](https://capawesome.io/docs/sdks/capacitor/printer/) and [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) plugins.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-pdf-generator` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-pdf-generator
npx cap sync
```

### Android

On Android, this plugin renders the HTML content in a hidden WebView and creates the PDF file with the [Android Print Framework](https://developer.android.com/training/printing). No additional configuration is required.

### iOS

On iOS, this plugin renders the HTML content in a hidden WKWebView and creates the PDF file with [UIKit](https://developer.apple.com/documentation/uikit). No additional configuration is required.

### Web

This plugin is not available on the web. Use `window.print()` with [print CSS](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_media_queries/Printing) to let the user save a web page as a PDF file.

## Configuration

No configuration required for this plugin.

## Usage

The generated PDF file is written to the cache directory and deleted on the next app launch. Move it to a permanent location if you want to keep it, for example with the `rename(...)` method of the [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin. You can also hand the path to the [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/), [Printer](https://capawesome.io/docs/sdks/capacitor/printer/), [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) or [Share](https://capacitorjs.com/docs/apis/share) plugin.

```typescript
import { PdfGenerator, Orientation, PageSize } from '@capawesome/capacitor-pdf-generator';

const generateFromHtml = async () => {
  const { path } = await PdfGenerator.generateFromHtml({
    html: '<h1>Invoice</h1><p>Thank you for your purchase!</p>',
    fileName: 'invoice.pdf',
    pageSize: PageSize.A4,
    orientation: Orientation.Portrait,
  });
  return path;
};

const generateFromUrl = async () => {
  const { path } = await PdfGenerator.generateFromUrl({
    url: 'https://capawesome.io/',
    pageSize: PageSize.Letter,
    orientation: Orientation.Landscape,
    timeout: 10000,
  });
  return path;
};
```

## API

<docgen-index>

* [`generateFromHtml(...)`](#generatefromhtml)
* [`generateFromUrl(...)`](#generatefromurl)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### generateFromHtml(...)

```typescript
generateFromHtml(options: GenerateFromHtmlOptions) => Promise<GenerateResult>
```

Generate a paginated PDF file from an HTML string.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#generatefromhtmloptions">GenerateFromHtmlOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#generateresult">GenerateResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### generateFromUrl(...)

```typescript
generateFromUrl(options: GenerateFromUrlOptions) => Promise<GenerateResult>
```

Generate a paginated PDF file from a URL.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#generatefromurloptions">GenerateFromUrlOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#generateresult">GenerateResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GenerateResult

| Prop       | Type                | Description                                                                                                                                                               | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the generated PDF file. The file is stored in the cache directory and deleted on the next app launch. Move it to a permanent location if you want to keep it. | 0.1.0 |


#### GenerateFromHtmlOptions

| Prop              | Type                                                | Description                                                                                                                            | Default                                      | Since |
| ----------------- | --------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- | ----- |
| **`baseUrl`**     | <code>string</code>                                 | The base URL to resolve relative resources (e.g. images, stylesheets) in the HTML content against.                                     |                                              | 0.1.0 |
| **`fileName`**    | <code>string</code>                                 | The name of the generated PDF file. If the name does not end with `.pdf`, the extension is appended.                                   | <code>A randomly generated file name.</code> | 0.1.0 |
| **`html`**        | <code>string</code>                                 | The HTML content to generate the PDF file from.                                                                                        |                                              | 0.1.0 |
| **`orientation`** | <code><a href="#orientation">Orientation</a></code> | The page orientation of the generated PDF file.                                                                                        | <code>Orientation.Portrait</code>            | 0.1.0 |
| **`pageSize`**    | <code><a href="#pagesize">PageSize</a></code>       | The page size of the generated PDF file.                                                                                               | <code>PageSize.A4</code>                     | 0.1.0 |
| **`timeout`**     | <code>number</code>                                 | The maximum time in milliseconds to wait for the PDF generation to complete before the call is rejected with the `TIMEOUT` error code. | <code>30000</code>                           | 0.1.0 |


#### GenerateFromUrlOptions

| Prop              | Type                                                | Description                                                                                                                            | Default                                      | Since |
| ----------------- | --------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- | ----- |
| **`fileName`**    | <code>string</code>                                 | The name of the generated PDF file. If the name does not end with `.pdf`, the extension is appended.                                   | <code>A randomly generated file name.</code> | 0.1.0 |
| **`orientation`** | <code><a href="#orientation">Orientation</a></code> | The page orientation of the generated PDF file.                                                                                        | <code>Orientation.Portrait</code>            | 0.1.0 |
| **`pageSize`**    | <code><a href="#pagesize">PageSize</a></code>       | The page size of the generated PDF file.                                                                                               | <code>PageSize.A4</code>                     | 0.1.0 |
| **`timeout`**     | <code>number</code>                                 | The maximum time in milliseconds to wait for the PDF generation to complete before the call is rejected with the `TIMEOUT` error code. | <code>30000</code>                           | 0.1.0 |
| **`url`**         | <code>string</code>                                 | The URL of the web page to generate the PDF file from.                                                                                 |                                              | 0.1.0 |


### Enums


#### Orientation

| Members         | Value                    | Description            | Since |
| --------------- | ------------------------ | ---------------------- | ----- |
| **`Landscape`** | <code>'LANDSCAPE'</code> | Landscape orientation. | 0.1.0 |
| **`Portrait`**  | <code>'PORTRAIT'</code>  | Portrait orientation.  | 0.1.0 |


#### PageSize

| Members      | Value                 | Description              | Since |
| ------------ | --------------------- | ------------------------ | ----- |
| **`A3`**     | <code>'A3'</code>     | ISO A3 (297 x 420 mm).   | 0.1.0 |
| **`A4`**     | <code>'A4'</code>     | ISO A4 (210 x 297 mm).   | 0.1.0 |
| **`A5`**     | <code>'A5'</code>     | ISO A5 (148 x 210 mm).   | 0.1.0 |
| **`Letter`** | <code>'LETTER'</code> | US Letter (8.5 x 11 in). | 0.1.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-generator/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-generator/LICENSE).
