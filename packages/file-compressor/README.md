# @capawesome-team/capacitor-file-compressor

Capacitor plugin for compressing files.

## Sponsorware

This project is available as **Sponsorware**.

> Sponsorware is a release strategy for open-source software that enables developers to be compensated for their open-source work with fewer downsides than traditional open-source funding models. ([Source](https://github.com/sponsorware/docs))

This means...

- The source code will be published as soon as [our GitHub Sponsors goal](https://github.com/sponsors/capawesome-team) is reached.
- Any GitHub sponsor with a [sponsorware tier](https://github.com/sponsors/capawesome-team?frequency=recurring) gets **immediate access** to our sponsors-only repository and can start using the project right away.

## Terms

This project is licensed under the terms of the MIT license.  
However, we kindly ask you to respect our **fair use policy**:

- Please **don't distribute the source code** of the sponsors-only repository. You may freely use it for public, private or commercial projects, privately fork or mirror it, but please don't make the source code public, as it would counteract the sponsorware strategy.
- If you cancel your subscription, you're automatically removed as a collaborator and will miss out on all future updates. However, **you may use the latest version that's available to you as long as you like**.

## Demo

A working example can be found here: [capawesome-team/capacitor-nfc-demo](https://github.com/capawesome-team/capacitor-nfc-demo)

| Android                                                                                                                         | iOS                                                                                                                             |
| ------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/184409092-7fdc3a77-67b1-4155-b1b1-0fd34a92a77b.gif" width="324" /> | <img src="https://user-images.githubusercontent.com/13857929/184409000-a81243a3-93e5-4d51-817a-e006c0a385cf.gif" width="266" /> |

## Installation

See [Getting started with Insiders](https://capawesome.io/insiders/getting-started/?plugin=capacitor-file-compressor) and follow the instructions to install the plugin.

After that, follow the platform-specific instructions in the sections [Android](#android) and [iOS](#ios).

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.0.1`)

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Nfc, NfcUtils, NfcTagTechType } from '@capawesome-team/capacitor-nfc';

const createNdefTextRecord = () => {
  const utils = new NfcUtils();
  const { record } = utils.createNdefTextRecord({ text: 'Capacitor NFC Plugin' });
  return record;
};
```

## API

<docgen-index>

* [`compressImage(...)`](#compressimage)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### compressImage(...)

```typescript
compressImage(options: CompressImageOptions) => Promise<CompressImageResult>
```

Compress an image.

The following image formats are supported:
- JPEG
- PNG
- WEBP

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#compressimageoptions">CompressImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#compressimageresult">CompressImageResult</a>&gt;</code>

**Since:** 5.0.0

--------------------


### Interfaces


#### CompressImageResult

| Prop       | Type                | Description                                                         | Since |
| ---------- | ------------------- | ------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the compressed file. Only available on Android and iOS. | 5.0.0 |
| **`blob`** | <code>Blob</code>   | The blob of the compressed file. Only available on Web.             | 5.0.0 |


#### CompressImageOptions

| Prop           | Type                | Description                                                                                        | Default          | Since |
| -------------- | ------------------- | -------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`blob`**     | <code>Blob</code>   | The blob of the file to compress. Only available on Web.                                           |                  | 5.0.0 |
| **`mimeType`** | <code>string</code> | The mime type of the compressed file. On Web, only `image/jpeg` and `image/webp` is supported.     |                  | 5.0.0 |
| **`path`**     | <code>string</code> | The path of the file to compress. The file will be overwritten. Only available on Android and iOS. |                  | 5.0.0 |
| **`quality`**  | <code>number</code> | The quality of the resulting image. This must be a number between `0` and `1`.                     | <code>0.8</code> | 5.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-compressor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-compressor/LICENSE).
