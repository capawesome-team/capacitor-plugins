# Capacitor Exif Plugin

Capacitor plugin to read, write and remove EXIF metadata from image files.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📖 **Read**: Read typed EXIF tags like GPS position, camera model, and capture date.
- ✍️ **Write**: Update EXIF tags in place without touching the rest of the file.
- 🕵️ **Privacy**: Strip GPS positions and other sensitive metadata before uploading images.
- 💎 **Lossless**: The pixel data is never re-encoded, so the image quality is not affected.
- 🖼️ **HEIC support**: Read and write HEIC metadata natively, which JavaScript libraries can not do.
- ⚡ **Native performance**: Files are processed natively and never loaded into the WebView memory.
- 🤝 **Compatibility**: Works alongside the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/), [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/) and [Photo Editor](https://capawesome.io/docs/sdks/capacitor/photo-editor/) plugins.
- 🔒 **App Store safe**: Uses only official platform APIs.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Exif plugin is typically used whenever an app needs to work with photo metadata, for example:

- **Privacy protection**: Strip GPS positions and other sensitive metadata from photos before uploading them to a server.
- **Photo organization**: Read the capture date and GPS position to sort and group photos.
- **Metadata correction**: Fix a wrong capture date or add a missing GPS position without re-encoding the image.
- **Camera details**: Display camera and lens information such as ISO, aperture, and exposure time in a photo detail view.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-exif` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-exif
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxExifInterfaceVersion` version of `androidx.exifinterface:exifinterface` (default: `1.4.2`)

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { Exif } from '@capawesome/capacitor-exif';
```

### Read the EXIF metadata of an image

Read typed EXIF tags such as the GPS position, camera model, and capture date from an image file. Only available on Android and iOS:

```typescript
const readExif = async () => {
  const { tags } = await Exif.readExif({
    path: 'file:///path/to/photo.jpg',
  });
  console.log('GPS position:', tags.gpsLatitude, tags.gpsLongitude);
};
```

### Write EXIF tags to an image

Update EXIF tags in place. Only the provided tags are updated and the pixel data is never re-encoded. Only available on Android and iOS:

```typescript
const writeExif = async () => {
  await Exif.writeExif({
    path: 'file:///path/to/photo.jpg',
    tags: {
      dateTimeOriginal: '2026:01:15 10:30:00',
      gpsLatitude: 51.503364,
      gpsLongitude: -0.127625,
    },
  });
};
```

### Remove the EXIF metadata from an image

Strip all EXIF metadata from an image file in place, for example to protect the privacy of your users before an upload. By default, the orientation tag is kept so that the image is not suddenly displayed rotated. Only available on Android and iOS:

```typescript
const removeExif = async () => {
  await Exif.removeExif({
    path: 'file:///path/to/photo.jpg',
  });
};
```

## API

<docgen-index>

* [`readExif(...)`](#readexif)
* [`removeExif(...)`](#removeexif)
* [`writeExif(...)`](#writeexif)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### readExif(...)

```typescript
readExif(options: ReadExifOptions) => Promise<ReadExifResult>
```

Read the EXIF metadata of an image file.

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#readexifoptions">ReadExifOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readexifresult">ReadExifResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeExif(...)

```typescript
removeExif(options: RemoveExifOptions) => Promise<void>
```

Remove the EXIF metadata from an image file in place.

The pixel data is never re-encoded, so the image quality
is not affected.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#removeexifoptions">RemoveExifOptions</a></code> |

**Since:** 0.1.0

--------------------


### writeExif(...)

```typescript
writeExif(options: WriteExifOptions) => Promise<void>
```

Write EXIF metadata to an image file in place.

Only the provided tags are updated. All other tags remain unchanged.
The pixel data is never re-encoded, so the image quality
is not affected.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#writeexifoptions">WriteExifOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### ReadExifResult

| Prop       | Type                                          | Description                                       | Since |
| ---------- | --------------------------------------------- | ------------------------------------------------- | ----- |
| **`tags`** | <code><a href="#exiftags">ExifTags</a></code> | The EXIF tags that were read from the image file. | 0.1.0 |


#### ExifTags

The EXIF tags of an image file.

Every property is optional and only present if the tag
exists in the file.

| Prop                   | Type                 | Description                                                                                                        | Since |
| ---------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------ | ----- |
| **`dateTimeOriginal`** | <code>string</code>  | The date and time when the image was originally captured in the EXIF date format `YYYY:MM:DD HH:MM:SS`.            | 0.1.0 |
| **`exposureTime`**     | <code>number</code>  | The exposure time in seconds.                                                                                      | 0.1.0 |
| **`fNumber`**          | <code>number</code>  | The F number (aperture) of the lens.                                                                               | 0.1.0 |
| **`flash`**            | <code>boolean</code> | Whether or not the flash fired when the image was captured.                                                        | 0.1.0 |
| **`focalLength`**      | <code>number</code>  | The focal length of the lens in millimeters.                                                                       | 0.1.0 |
| **`gpsAltitude`**      | <code>number</code>  | The altitude in meters above (positive) or below (negative) sea level.                                             | 0.1.0 |
| **`gpsLatitude`**      | <code>number</code>  | The latitude in signed decimal degrees. Positive values are north of the equator, negative values are south.       | 0.1.0 |
| **`gpsLongitude`**     | <code>number</code>  | The longitude in signed decimal degrees. Positive values are east of the prime meridian, negative values are west. | 0.1.0 |
| **`iso`**              | <code>number</code>  | The ISO speed rating (photographic sensitivity).                                                                   | 0.1.0 |
| **`lensModel`**        | <code>string</code>  | The model of the lens.                                                                                             | 0.1.0 |
| **`make`**             | <code>string</code>  | The manufacturer of the camera.                                                                                    | 0.1.0 |
| **`model`**            | <code>string</code>  | The model of the camera.                                                                                           | 0.1.0 |
| **`orientation`**      | <code>number</code>  | The orientation of the image as defined by the EXIF specification (1-8).                                           | 0.1.0 |
| **`pixelHeight`**      | <code>number</code>  | The height of the image in pixels.                                                                                 | 0.1.0 |
| **`pixelWidth`**       | <code>number</code>  | The width of the image in pixels.                                                                                  | 0.1.0 |
| **`software`**         | <code>string</code>  | The name of the software that was used to create or edit the image.                                                | 0.1.0 |


#### ReadExifOptions

| Prop       | Type                | Description                                                                                                   | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path to the image file to read the EXIF metadata from. Local file paths and `file://` URIs are supported. | 0.1.0 |


#### RemoveExifOptions

| Prop                  | Type                 | Description                                                                                                                                                                                   | Default           | Since |
| --------------------- | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`keepOrientation`** | <code>boolean</code> | Whether or not to keep the orientation tag when removing the EXIF metadata. This is enabled by default so that images are not suddenly displayed rotated after the metadata has been removed. | <code>true</code> | 0.1.0 |
| **`path`**            | <code>string</code>  | The path to the image file to remove the EXIF metadata from. Local file paths and `file://` URIs are supported.                                                                               |                   | 0.1.0 |


#### WriteExifOptions

| Prop       | Type                                                          | Description                                                                                                    | Since |
| ---------- | ------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code>                                           | The path to the image file to write the EXIF metadata to. Local file paths and `file://` URIs are supported.   | 0.1.0 |
| **`tags`** | <code><a href="#writableexiftags">WritableExifTags</a></code> | The EXIF tags to write to the image file. Only the provided tags are updated. All other tags remain unchanged. | 0.1.0 |


#### WritableExifTags

The writable subset of the EXIF tags.

| Prop                   | Type                 | Description                                                                                                                                                      | Since |
| ---------------------- | -------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`dateTimeOriginal`** | <code>string</code>  | The date and time when the image was originally captured in the EXIF date format `YYYY:MM:DD HH:MM:SS`.                                                          | 0.1.0 |
| **`exposureTime`**     | <code>number</code>  | The exposure time in seconds.                                                                                                                                    | 0.1.0 |
| **`fNumber`**          | <code>number</code>  | The F number (aperture) of the lens.                                                                                                                             | 0.1.0 |
| **`flash`**            | <code>boolean</code> | Whether or not the flash fired when the image was captured.                                                                                                      | 0.1.0 |
| **`focalLength`**      | <code>number</code>  | The focal length of the lens in millimeters.                                                                                                                     | 0.1.0 |
| **`gpsAltitude`**      | <code>number</code>  | The altitude in meters above (positive) or below (negative) sea level.                                                                                           | 0.1.0 |
| **`gpsLatitude`**      | <code>number</code>  | The latitude in signed decimal degrees. Positive values are north of the equator, negative values are south. Must be provided together with `gpsLongitude`.      | 0.1.0 |
| **`gpsLongitude`**     | <code>number</code>  | The longitude in signed decimal degrees. Positive values are east of the prime meridian, negative values are west. Must be provided together with `gpsLatitude`. | 0.1.0 |
| **`iso`**              | <code>number</code>  | The ISO speed rating (photographic sensitivity).                                                                                                                 | 0.1.0 |
| **`lensModel`**        | <code>string</code>  | The model of the lens.                                                                                                                                           | 0.1.0 |
| **`make`**             | <code>string</code>  | The manufacturer of the camera.                                                                                                                                  | 0.1.0 |
| **`model`**            | <code>string</code>  | The model of the camera.                                                                                                                                         | 0.1.0 |
| **`orientation`**      | <code>number</code>  | The orientation of the image as defined by the EXIF specification (1-8).                                                                                         | 0.1.0 |
| **`software`**         | <code>string</code>  | The name of the software that was used to create or edit the image.                                                                                              | 0.1.0 |

</docgen-api>

## Format Support

The plugin can read EXIF metadata from any image format that the platform supports.
Writing and removing EXIF metadata is a lossless in-place operation that is only supported for the following formats:

| Format    | Read (Android) | Read (iOS) | Write & Remove (Android) | Write & Remove (iOS) |
| --------- | -------------- | ---------- | ------------------------ | -------------------- |
| JPEG      | ✅             | ✅         | ✅                       | ✅                   |
| PNG       | ✅             | ✅         | ✅                       | ❌                   |
| WebP      | ✅             | ✅         | ✅                       | ❌                   |
| HEIC/HEIF | ✅             | ✅         | ❌                       | ✅                   |
| DNG/RAW   | ✅             | ✅         | ❌                       | ❌                   |

Calling `writeExif(...)` or `removeExif(...)` with an unsupported format rejects with the `UNSUPPORTED_FORMAT` error code.

## Lossless Metadata Writes

A metadata plugin that degrades the image quality is broken.
This plugin therefore guarantees that the pixel data is never decoded or re-encoded:

- On **Android**, the [`ExifInterface`](https://developer.android.com/reference/androidx/exifinterface/media/ExifInterface) library rewrites only the metadata segments of the file in place.
- On **iOS**, the [`CGImageDestinationCopyImageSource`](https://developer.apple.com/documentation/imageio/cgimagedestinationcopyimagesource(_:_:_:_:)) API copies the compressed image data unchanged and only replaces the metadata.

## Privacy: Strip Metadata Before Upload

Images taken with a smartphone usually contain the exact GPS position, the device model, and the capture date.
To protect the privacy of your users, remove this metadata before uploading images to a server:

```typescript
import { Exif } from '@capawesome/capacitor-exif';

const prepareUpload = async (path: string) => {
  // Remove the GPS position and all other EXIF metadata.
  // The orientation is kept so that the image is not displayed rotated.
  await Exif.removeExif({ path });
};
```

## Limitations

### Android

- Vendor-specific maker notes and other unknown tags may survive `removeExif(...)` since the `ExifInterface` library can only remove known tags.

### iOS

- HEIC files store the orientation at the container level, so the `orientation` tag can not be changed and is always kept, even with `keepOrientation` set to `false`.
- HEIC files may keep the `software` and `dateTimeOriginal` tags after `removeExif(...)`. GPS data and device identity tags (`make`, `model`, `lensModel`) are always removed.

## FAQ

### Which platforms are supported by this plugin?

The plugin supports Android and iOS. All methods (`readExif`, `writeExif` and `removeExif`) are only available on these two platforms, since the files are processed natively and never loaded into the WebView memory.

### Does writing or removing EXIF metadata affect the image quality?

No. Writing and removing EXIF metadata is a lossless in-place operation. The pixel data is never decoded or re-encoded, so the image quality is not affected. See [Lossless Metadata Writes](#lossless-metadata-writes) for details on how this is implemented on each platform.

### Which image formats are supported?

Reading EXIF metadata works with any image format that the platform supports, including JPEG, PNG, WebP, HEIC/HEIF, and DNG/RAW. Writing and removing metadata is only supported for certain formats, which differ between Android and iOS (see [Format Support](#format-support)). Calling `writeExif(...)` or `removeExif(...)` with an unsupported format rejects with the `UNSUPPORTED_FORMAT` error code.

### Why is the orientation tag still present after calling `removeExif`?

The `keepOrientation` option is enabled by default so that images are not suddenly displayed rotated after the metadata has been removed. You can set it to `false` to also remove the orientation tag. Note that HEIC files on iOS store the orientation at the container level, so it is always kept there.

### How do I remove the GPS position from a photo before uploading it?

Call `removeExif(...)` with the path to the image file. This strips the GPS position along with all other EXIF metadata in place, without affecting the image quality. See [Privacy: Strip Metadata Before Upload](#privacy-strip-metadata-before-upload) for an example.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Pick the image files whose metadata you want to read or edit.
- [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/): Compress images in formats like PNG, JPEG, and WebP.
- [Photo Editor](https://capawesome.io/docs/sdks/capacitor/photo-editor/): Let the user edit a photo.
- [Photo Manipulator](https://capawesome.io/docs/sdks/capacitor/photo-manipulator/): Headless image transforms like crop, resize, rotate, and format conversion.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/exif/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/exif/LICENSE).
