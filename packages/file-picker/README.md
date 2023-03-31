# @capawesome/capacitor-file-picker

Capacitor plugin that allows the user to select a file.

## Installation

```bash
npm install @capawesome/capacitor-file-picker
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const pickFiles = async () => {
  const result = await FilePicker.pickFiles({
    types: ['image/png'],
    multiple: true,
  });
};

const pickImages = async () => {
  const result = await FilePicker.pickImages({
    multiple: true,
  });
};

const pickMedia = async () => {
  const result = await FilePicker.pickMedia({
    multiple: true,
  });
};

const pickVideos = async () => {
  const result = await FilePicker.pickVideos({
    multiple: true,
  });
};

const appendFileToFormData = async () => {
  const result = await FilePicker.pickFiles();
  const file = result.files[0];

  const formData = new FormData();
  if (file.blob) {
    const rawFile = new File(file.blob, file.name, {
      type: file.mimeType,
    });
    formData.append('file', rawFile, file.name);
  }
};
```

## API

<docgen-index>

* [`convertHeicToJpeg(...)`](#convertheictojpeg)
* [`pickFiles(...)`](#pickfiles)
* [`pickImages(...)`](#pickimages)
* [`pickMedia(...)`](#pickmedia)
* [`pickVideos(...)`](#pickvideos)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### convertHeicToJpeg(...)

```typescript
convertHeicToJpeg(options: ConvertHeicToJpegOptions) => Promise<ConvertHeicToJpegResult>
```

Convert a HEIC image to JPEG.

Only available on iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#convertheictojpegoptions">ConvertHeicToJpegOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#convertheictojpegresult">ConvertHeicToJpegResult</a>&gt;</code>

**Since:** 0.6.0

--------------------


### pickFiles(...)

```typescript
pickFiles(options?: PickFilesOptions | undefined) => Promise<PickFilesResult>
```

Open the file picker that allows the user to select one or more files.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#pickfilesoptions">PickFilesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickfilesresult">PickFilesResult</a>&gt;</code>

--------------------


### pickImages(...)

```typescript
pickImages(options?: PickMediaOptions | undefined) => Promise<PickImagesResult>
```

Pick one or more images from the gallery.

On iOS 13 and older it only allows to pick one image.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#pickmediaoptions">PickMediaOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickfilesresult">PickFilesResult</a>&gt;</code>

**Since:** 0.5.3

--------------------


### pickMedia(...)

```typescript
pickMedia(options?: PickMediaOptions | undefined) => Promise<PickMediaResult>
```

Pick one or more images or videos from the gallery.

On iOS 13 and older it only allows to pick one image or video.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#pickmediaoptions">PickMediaOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickfilesresult">PickFilesResult</a>&gt;</code>

**Since:** 0.5.3

--------------------


### pickVideos(...)

```typescript
pickVideos(options?: PickMediaOptions | undefined) => Promise<PickVideosResult>
```

Pick one or more videos from the gallery.

On iOS 13 and older it only allows to pick one video.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#pickmediaoptions">PickMediaOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickfilesresult">PickFilesResult</a>&gt;</code>

**Since:** 0.5.3

--------------------


### Interfaces


#### ConvertHeicToJpegResult

| Prop       | Type                | Description                           | Since |
| ---------- | ------------------- | ------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the converted JPEG image. | 0.6.0 |


#### ConvertHeicToJpegOptions

| Prop       | Type                | Description                 | Since |
| ---------- | ------------------- | --------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the HEIC image. | 0.6.0 |


#### PickFilesResult

| Prop        | Type                |
| ----------- | ------------------- |
| **`files`** | <code>File[]</code> |


#### File

| Prop             | Type                | Description                                                                                                          | Since |
| ---------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------- | ----- |
| **`blob`**       | <code>Blob</code>   | The Blob instance of the file. Only available on Web.                                                                |       |
| **`data`**       | <code>string</code> | The Base64 string representation of the data contained in the file. Is only provided if `readData` is set to `true`. |       |
| **`duration`**   | <code>number</code> | The duration of the video in seconds. Only available on Android and iOS.                                             | 0.5.3 |
| **`height`**     | <code>number</code> | The height of the image or video in pixels. Only available on Android and iOS.                                       | 0.5.3 |
| **`mimeType`**   | <code>string</code> | The mime type of the file.                                                                                           |       |
| **`modifiedAt`** | <code>number</code> | The last modified timestamp of the file in milliseconds.                                                             | 0.5.9 |
| **`name`**       | <code>string</code> | The name of the file.                                                                                                |       |
| **`path`**       | <code>string</code> | The path of the file. Only available on Android and iOS.                                                             |       |
| **`size`**       | <code>number</code> | The size of the file in bytes.                                                                                       |       |
| **`width`**      | <code>number</code> | The width of the image or video in pixels. Only available on Android and iOS.                                        | 0.5.3 |


#### PickFilesOptions

| Prop           | Type                  | Description                                                                                                                                                                                                                       | Default            |
| -------------- | --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ |
| **`types`**    | <code>string[]</code> | List of accepted file types. Look at [IANA Media Types](https://www.iana.org/assignments/media-types/media-types.xhtml) for a complete list of standard media types. This option cannot be used with `multiple: true` on Android. |                    |
| **`multiple`** | <code>boolean</code>  | Whether multiple files may be selected.                                                                                                                                                                                           | <code>false</code> |
| **`readData`** | <code>boolean</code>  | Whether to read the file data.                                                                                                                                                                                                    | <code>false</code> |


#### PickMediaOptions

| Prop           | Type                 | Description                             | Default            |
| -------------- | -------------------- | --------------------------------------- | ------------------ |
| **`multiple`** | <code>boolean</code> | Whether multiple files may be selected. | <code>false</code> |
| **`readData`** | <code>boolean</code> | Whether to read the file data.          | <code>false</code> |


### Type Aliases


#### PickedFile

<code><a href="#file">File</a></code>


#### PickImagesOptions

<code><a href="#pickmediaoptions">PickMediaOptions</a></code>


#### PickImagesResult

<code><a href="#pickmediaresult">PickMediaResult</a></code>


#### PickMediaResult

<code><a href="#pickfilesresult">PickFilesResult</a></code>


#### PickVideosOptions

<code><a href="#pickmediaoptions">PickMediaOptions</a></code>


#### PickVideosResult

<code><a href="#pickmediaresult">PickMediaResult</a></code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-file-picker/blob/main/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-file-picker/blob/main/LICENSE).

## Credits

This plugin is based on the [Capacitor File Picker](https://github.com/capawesome-team/capacitor-file-picker) plugin.
Thanks to everyone who contributed to the project!
