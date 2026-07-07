# Capacitor Photo Manipulator Plugin

Capacitor plugin for headless image transforms like crop, resize, rotate, flip and format conversion, including HEIC to JPEG.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🖼️ **Headless Transforms**: Crop, resize, rotate and flip images from code, without any UI.
- 🔄 **HEIC to JPEG**: Convert HEIC and AVIF photos to JPEG, PNG or WebP with the native platform decoders. No WASM blobs, no WebView memory spikes.
- 📐 **Upright Output**: The EXIF orientation is applied during decoding so the output is always upright.
- 🧠 **Memory Efficient**: Bounds-aware downsampled decoding, so full-resolution bitmaps are never loaded when resizing.
- 🕵️ **Privacy Friendly**: All metadata (e.g. EXIF, GPS) is stripped from the output by re-encoding.
- 📂 **File Output**: Results are written to files, so even large images don't exhaust memory.
- ℹ️ **Image Info**: Read the dimensions and format of an image without decoding the pixel data.
- 🤝 **Compatibility**: Works alongside the [Photo Editor](https://capawesome.io/docs/sdks/capacitor/photo-editor/), [Exif](https://capawesome.io/docs/sdks/capacitor/exif/) and [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/) plugins.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-photo-manipulator` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-photo-manipulator
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidxExifInterfaceVersion` version of `androidx.exifinterface:exifinterface` (default: `1.4.1`)

### iOS

On iOS, this plugin uses the [Image I/O](https://developer.apple.com/documentation/imageio) and [Core Graphics](https://developer.apple.com/documentation/coregraphics) frameworks. No additional configuration is required.

### Web

This plugin provides a partial web implementation based on the [Canvas API](https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API). HEIC and AVIF images can not be decoded by most browsers, which is exactly why the native implementations exist (see [Format Support](#format-support)).

## Configuration

No configuration required for this plugin.

## Usage

The transformed image is written to a new file in the cache directory and deleted on the next app launch. Move it to a permanent location if you want to keep it, for example with the `rename(...)` method of the [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin.

```typescript
import { PhotoManipulator, ImageFormat } from '@capawesome/capacitor-photo-manipulator';

const convertHeicToJpeg = async () => {
  // Convert an HEIC photo (e.g. taken on an iPhone) to JPEG
  const { path } = await PhotoManipulator.transform({
    path: 'file:///var/mobile/.../photo.heic',
    format: ImageFormat.Jpeg,
    quality: 0.9,
  });
  return path;
};

const createThumbnail = async () => {
  // Crop, resize, rotate and flip in one call
  const { path, width, height } = await PhotoManipulator.transform({
    path: 'file:///var/mobile/.../photo.jpeg',
    crop: { x: 100, y: 100, width: 1080, height: 1080 },
    resize: { width: 256 },
    rotate: 90,
    flipHorizontal: true,
  });
  return { path, width, height };
};

const getInfo = async () => {
  const { width, height, format } = await PhotoManipulator.getInfo({
    path: 'file:///var/mobile/.../photo.heic',
  });
  return { width, height, format };
};
```

## API

<docgen-index>

* [`getInfo(...)`](#getinfo)
* [`transform(...)`](#transform)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getInfo(...)

```typescript
getInfo(options: GetInfoOptions) => Promise<GetInfoResult>
```

Get the dimensions and format of an image without decoding
the pixel data where possible.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#getinfooptions">GetInfoOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getinforesult">GetInfoResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### transform(...)

```typescript
transform(options: TransformOptions) => Promise<TransformResult>
```

Apply one or more transformations to an image and write the
result to a new file.

The operations are always applied in the following fixed order:
crop → resize → rotate → flip. Chain multiple calls if you need
a different order.

The EXIF orientation of the source image is applied during decoding
so that the output is always upright. All other metadata (e.g. EXIF,
GPS) is stripped by re-encoding.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#transformoptions">TransformOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#transformresult">TransformResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetInfoResult

| Prop         | Type                        | Description                                                                                                                                                                                                                                | Since |
| ------------ | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`format`** | <code>string \| null</code> | The format of the image or `null` if the format could not be determined. The value is provided by the platform decoder and may differ slightly between platforms for the same file (e.g. `heic` on iOS and the web but `heif` on Android). | 0.1.0 |
| **`height`** | <code>number</code>         | The height of the image in pixels after the EXIF orientation has been applied.                                                                                                                                                             | 0.1.0 |
| **`width`**  | <code>number</code>         | The width of the image in pixels after the EXIF orientation has been applied.                                                                                                                                                              | 0.1.0 |


#### GetInfoOptions

| Prop       | Type                | Description                                                                                                                                                                             | Since |
| ---------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the image file. On Android and iOS, only local file paths and `file://` URIs are supported. On the web, any fetchable URL (e.g. `https://`, `blob:`, `data:`) is supported. | 0.1.0 |


#### TransformResult

| Prop         | Type                | Description                                                                                                                                                                                                                              | Since |
| ------------ | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the transformed image in pixels.                                                                                                                                                                                           | 0.1.0 |
| **`path`**   | <code>string</code> | The path of the transformed image file. On Android and iOS, the file is stored in the cache directory and deleted on the next app launch. Move it to a permanent location if you want to keep it. On the web, the path is an object URL. | 0.1.0 |
| **`width`**  | <code>number</code> | The width of the transformed image in pixels.                                                                                                                                                                                            | 0.1.0 |


#### TransformOptions

| Prop                 | Type                                                    | Description                                                                                                                                                                                          | Default                       | Since |
| -------------------- | ------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- | ----- |
| **`crop`**           | <code><a href="#cropoptions">CropOptions</a></code>     | The region of the source image to crop to, in source pixels. The region must be within the bounds of the source image.                                                                               |                               | 0.1.0 |
| **`flipHorizontal`** | <code>boolean</code>                                    | Whether or not to flip the image horizontally.                                                                                                                                                       | <code>false</code>            | 0.1.0 |
| **`flipVertical`**   | <code>boolean</code>                                    | Whether or not to flip the image vertically.                                                                                                                                                         | <code>false</code>            | 0.1.0 |
| **`format`**         | <code><a href="#imageformat">ImageFormat</a></code>     | The format of the output file. On iOS, <a href="#imageformat">`ImageFormat.Webp`</a> is not supported and rejects with the `UNSUPPORTED_FORMAT` error code.                                          | <code>ImageFormat.Jpeg</code> | 0.1.0 |
| **`path`**           | <code>string</code>                                     | The path of the image file to transform. On Android and iOS, only local file paths and `file://` URIs are supported. On the web, any fetchable URL (e.g. `https://`, `blob:`, `data:`) is supported. |                               | 0.1.0 |
| **`quality`**        | <code>number</code>                                     | The quality of the output file between `0` and `1`. Only applied when `format` is <a href="#imageformat">`ImageFormat.Jpeg`</a> or <a href="#imageformat">`ImageFormat.Webp`</a>.                    | <code>0.9</code>              | 0.1.0 |
| **`resize`**         | <code><a href="#resizeoptions">ResizeOptions</a></code> | The target size to resize the image to, in pixels. If only one of `width` and `height` is provided, the aspect ratio is preserved. The resize is applied after the crop.                             |                               | 0.1.0 |
| **`rotate`**         | <code>number</code>                                     | The clockwise angle to rotate the image by, in degrees. Must be `90`, `180` or `270`.                                                                                                                | <code>0</code>                | 0.1.0 |


#### CropOptions

| Prop         | Type                | Description                                                                                          | Since |
| ------------ | ------------------- | ---------------------------------------------------------------------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the crop region in pixels.                                                             | 0.1.0 |
| **`width`**  | <code>number</code> | The width of the crop region in pixels.                                                              | 0.1.0 |
| **`x`**      | <code>number</code> | The horizontal offset of the crop region in pixels, measured from the left edge of the source image. | 0.1.0 |
| **`y`**      | <code>number</code> | The vertical offset of the crop region in pixels, measured from the top edge of the source image.    | 0.1.0 |


#### ResizeOptions

| Prop         | Type                | Description                                                                                                  | Since |
| ------------ | ------------------- | ------------------------------------------------------------------------------------------------------------ | ----- |
| **`height`** | <code>number</code> | The target height in pixels. If only one of `width` and `height` is provided, the aspect ratio is preserved. | 0.1.0 |
| **`width`**  | <code>number</code> | The target width in pixels. If only one of `width` and `height` is provided, the aspect ratio is preserved.  | 0.1.0 |


### Enums


#### ImageFormat

| Members    | Value               | Description                                             | Since |
| ---------- | ------------------- | ------------------------------------------------------- | ----- |
| **`Jpeg`** | <code>'JPEG'</code> | JPEG (`image/jpeg`).                                    | 0.1.0 |
| **`Png`**  | <code>'PNG'</code>  | PNG (`image/png`).                                      | 0.1.0 |
| **`Webp`** | <code>'WEBP'</code> | WebP (`image/webp`). Only available on Android and Web. | 0.1.0 |

</docgen-api>

## Format Support

The formats an image can be decoded from and encoded to depend on the platform:

### Input (Decode)

| Format    | Android          | iOS          | Web               |
| --------- | ---------------- | ------------ | ----------------- |
| JPEG      | ✅               | ✅           | ✅                |
| PNG       | ✅               | ✅           | ✅                |
| WebP      | ✅               | ✅           | ✅                |
| GIF       | ✅               | ✅           | ✅                |
| BMP       | ✅               | ✅           | ✅                |
| HEIC/HEIF | ✅ (Android 9+)  | ✅           | ❌                |
| AVIF      | ✅ (Android 12+) | ✅ (iOS 16+) | Browser-dependent |

### Output (Encode)

| Format | Android | iOS | Web               |
| ------ | ------- | --- | ----------------- |
| JPEG   | ✅      | ✅  | ✅                |
| PNG    | ✅      | ✅  | ✅                |
| WebP   | ✅      | ❌  | Browser-dependent |

If an image can not be decoded or encoded on the current platform, the call is rejected with the `UNSUPPORTED_FORMAT` error code so you can fall back gracefully.

## Memory

This plugin is designed to keep the memory footprint low, even for very large images:

- When a `resize` target is provided, the image is decoded downsampled (using `inSampleSize` on Android and `kCGImageSourceThumbnailMaxPixelSize` on iOS) so the full-resolution bitmap is never loaded into memory.
- Transforms are processed one at a time on a background queue.
- The result is written to a file instead of being returned as base64 data.

For the smallest possible memory footprint, always provide a `resize` target if you don't need the full resolution.

## Metadata

The EXIF orientation of the source image is applied during decoding so that the output is always upright. All other metadata (e.g. EXIF, GPS) is stripped by re-encoding. Use the [Exif](https://capawesome.io/docs/sdks/capacitor/exif/) plugin if you want to read the metadata of the source image and write it back to the transformed image.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/photo-manipulator/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/photo-manipulator/LICENSE).
