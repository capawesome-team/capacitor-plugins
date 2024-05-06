# @capawesome-team/capacitor-printer

Capacitor plugin for printing.

## Sponsorware

This project is available as **Sponsorware**.

> Sponsorware is a release strategy for open-source software that enables developers to be compensated for their open-source work with fewer downsides than traditional open-source funding models. ([Source](https://github.com/sponsorware/docs))

This means...

- The source code will be published as soon as the [funding goal](https://capawesome.io/sponsors/insiders/#funding) is reached.
- Any [Polar sponsor](https://polar.sh/capawesome-team/subscriptions) with a sponsorware tier gets **immediate access** to our sponsors-only repository and can start using the project right away.

## Terms

This project is licensed under the terms of the MIT license.  
However, we kindly ask you to respect our **fair use policy**:

- Please **don't distribute the source code** of the sponsors-only repository. You may freely use it for public, private or commercial projects, privately fork or mirror it, but please don't make the source code public, as it would counteract the sponsorware strategy.
- If you cancel your subscription, you're automatically removed as a collaborator and will miss out on all future updates. However, **you may use the latest version that's available to you as long as you like**.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                             | iOS                                                                                                                                 |
| ----------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/7de4bcb3-aa59-43bc-a882-2796964be539" width="324" /> | <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/d796d9f0-32c1-4d5c-b38f-ab46509a5eda" width="324" /> |

## Installation

See [Getting started with Insiders](https://capawesome.io/sponsors/insiders/getting-started/?plugin=capacitor-printer) and follow the instructions to install the plugin.

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.0.1`)

## Configuration

No configuration required for this plugin.

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

* [`printHtml(...)`](#printhtml)
* [`printPdf(...)`](#printpdf)
* [`printWebView(...)`](#printwebview)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### printHtml(...)

```typescript
printHtml(options: PrintHtmlOptions) => Promise<void>
```

Present the printing user interface to print a html document.

Only available for Android and iOS.

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

Only available for Android and iOS.

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

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#printoptions">PrintOptions</a></code> |

**Since:** 5.0.0

Only available for Android and iOS.

--------------------


### Interfaces


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
