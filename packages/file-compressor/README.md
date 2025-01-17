# @capawesome-team/capacitor-file-compressor

Capacitor plugin for compressing files.

## Features

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌅 **Compress Images**: Compress png, jpeg, and webp images.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

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
npm install @capawesome-team/capacitor-file-compressor
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.0.1`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [capawesome-team/capacitor-plugin-demo](https://github.com/capawesome-team/capacitor-plugin-demo)

| Android                                                                                                                             |
| ----------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/24fa080e-327f-4a7e-afed-f2e7da82d5a7" width="324" /> |

## Usage

```typescript
import { FileCompressor } from '@capawesome-team/capacitor-file-compressor';

const compressImage = async () => {
  const { path } = await FileCompressor.compressImage({
    mimeType: 'image/jpeg',
    path: 'content://com.android.providers.downloads.documents/document/msf%3A1000000485',
    quality: 0.7,
  });
  return path;
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

Only png, jpeg, and webp images are supported.

**Attention**: The exif data of the image is lost during compression.

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

| Prop           | Type                | Description                                                                                                                                                                                                                    | Default                   | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------- | ----- |
| **`blob`**     | <code>Blob</code>   | The blob of the file to compress. Only available on Web.                                                                                                                                                                       |                           | 5.0.0 |
| **`mimeType`** | <code>string</code> | The mime type of the compressed file. On Android, only `image/jpeg` and `image/webp` are supported. On iOS, only `image/jpeg` is supported. On Web, only `image/jpeg` and `image/webp` are supported.                          | <code>'image/jpeg'</code> | 5.0.0 |
| **`path`**     | <code>string</code> | The path of the file to compress. Only available on Android and iOS.                                                                                                                                                           |                           | 5.0.0 |
| **`quality`**  | <code>number</code> | The quality of the resulting image, expressed as a value from `0.0` to `1.0`. The value `0.0` represents the maximum compression (or lowest quality) while the value `1.0` represents the least compression (or best quality). | <code>0.6</code>          | 5.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-compressor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-compressor/LICENSE).
