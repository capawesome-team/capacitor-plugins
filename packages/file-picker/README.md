# @capawesome/capacitor-file-picker

Capacitor plugin that allows the user to select a file, directory, image, or video from the device's file system or gallery.

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for file picking. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 📂 **Directory picking**: Allows users to select a directory to retrieve all files.
- 🖼️ **Image picking**: Lets users select one or more images from the gallery.
- 🎥 **Video picking**: Lets users select one or more videos from the gallery.
- 📄 **File picking**: Lets users select one or more miscellaneous files from the file system.
- 📸 **HEIC to JPEG conversion**: Converts HEIC images to JPEG format on iOS.
- 📜 **File metadata**: Retrieves metadata such as file size, name, mime type, and last modified timestamp.
- 📦 **SPM**: Supports Swift Package Manager for iOS. 
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about this plugin by subscribing to our [Capawesome Newsletter](https://capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-file-picker
npx cap sync
```

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Needed if you want to retrieve unredacted EXIF metadata from photos -->
<uses-permission android:name="android.permission.ACCESS_MEDIA_LOCATION" />
<!-- Needed if you want to read files from external storage -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

### iOS

#### Entitlements

To use this plugin with Mac Catalyst, your app must have the `com.apple.security.files.user-selected.read-only` entitlement enabled. This allows the app to read files selected by the user. Check out the [Apple documentation](https://developer.apple.com/documentation/bundleresources/entitlements/com.apple.security.files.user-selected.read-only) for more information.

```xml
<key>com.apple.security.files.user-selected.read-only</key>
<true/>
```

If you don't want to use the plugin with Mac Catalyst, you can skip this step.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

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

const checkPermissions = async () => {
  const result = await FilePicker.checkPermissions();
};

const copyFile = async () => {
  const result = await FilePicker.copyFile({
    from: 'path/to/file',
    to: 'path/to/destination',
  });
};

const pickFiles = async () => {
  const result = await FilePicker.pickFiles({
    types: ['image/png'],
  });
};

const pickDirectory = async () => {
  const result = await FilePicker.pickDirectory();
};

const pickImages = async () => {
  const result = await FilePicker.pickImages();
};

const pickMedia = async () => {
  const result = await FilePicker.pickMedia();
};

const pickVideos = async () => {
  const result = await FilePicker.pickVideos();
};

const requestPermissions = async () => {
  const result = await FilePicker.requestPermissions();
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`convertHeicToJpeg(...)`](#convertheictojpeg)
* [`copyFile(...)`](#copyfile)
* [`pickFiles(...)`](#pickfiles)
* [`pickDirectory()`](#pickdirectory)
* [`pickImages(...)`](#pickimages)
* [`pickMedia(...)`](#pickmedia)
* [`pickVideos(...)`](#pickvideos)
* [`requestPermissions(...)`](#requestpermissions)
* [`addListener('pickerDismissed', ...)`](#addlistenerpickerdismissed-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions to access files.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.1.0

--------------------


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


### copyFile(...)

```typescript
copyFile(options: CopyFileOptions) => Promise<void>
```

Copy a file to a new location.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#copyfileoptions">CopyFileOptions</a></code> |

**Since:** 7.1.0

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


### pickDirectory()

```typescript
pickDirectory() => Promise<PickDirectoryResult>
```

Open a picker dialog that allows the user to select a directory.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#pickdirectoryresult">PickDirectoryResult</a>&gt;</code>

**Since:** 6.2.0

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


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions to access files.

Only available on Android.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.1.0

--------------------


### addListener('pickerDismissed', ...)

```typescript
addListener(eventName: 'pickerDismissed', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the file picker is dismissed.

Only available on iOS.

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>'pickerDismissed'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.6.2

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.6.2

--------------------


### Interfaces


#### PermissionStatus

| Prop                      | Type                                                        | Description                                                                                                             | Since |
| ------------------------- | ----------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- | ----- |
| **`accessMediaLocation`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for accessing media location. On Android, this requests/checks the `ACCESS_MEDIA_LOCATION` permission. | 6.1.0 |
| **`readExternalStorage`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for reading external storage. On Android, this requests/checks the `READ_EXTERNAL_STORAGE` permission. | 6.1.0 |


#### ConvertHeicToJpegResult

| Prop       | Type                | Description                           | Since |
| ---------- | ------------------- | ------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the converted JPEG image. | 0.6.0 |


#### ConvertHeicToJpegOptions

| Prop       | Type                | Description                 | Since |
| ---------- | ------------------- | --------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the HEIC image. | 0.6.0 |


#### CopyFileOptions

| Prop            | Type                 | Description                                                     | Default           | Since |
| --------------- | -------------------- | --------------------------------------------------------------- | ----------------- | ----- |
| **`from`**      | <code>string</code>  | The path of the file to copy.                                   |                   | 7.1.0 |
| **`overwrite`** | <code>boolean</code> | Whether to overwrite if the file at destination already exists. | <code>true</code> | 7.2.0 |
| **`to`**        | <code>string</code>  | The path to copy the file to.                                   |                   | 7.1.0 |


#### PickFilesResult

| Prop        | Type                      |
| ----------- | ------------------------- |
| **`files`** | <code>PickedFile[]</code> |


#### PickedFile

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

| Prop           | Type                  | Description                                                                                                                                                                                                                                                                                                                                                                       | Default            | Since |
| -------------- | --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`types`**    | <code>string[]</code> | List of accepted file types. Look at [IANA Media Types](https://www.iana.org/assignments/media-types/media-types.xhtml) for a complete list of standard media types. This option is ignored if `limit` is set.                                                                                                                                                                    |                    |       |
| **`limit`**    | <code>number</code>   | The maximum number of files that the user can select. Setting this to `0` sets the selection limit to unlimited. Currently, only `0` and `1` are supported.                                                                                                                                                                                                                       | <code>0</code>     | 6.0.0 |
| **`readData`** | <code>boolean</code>  | Whether to read the file data. **Attention**: Reading large files can lead to app crashes. It's therefore not recommended to use this option. Instead, use the [fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch) to load the file as a blob, see [this example](https://capawesome.io/blog/the-file-handling-guide-for-capacitor/#read-a-file). | <code>false</code> |       |


#### PickDirectoryResult

| Prop       | Type                | Description                         | Since |
| ---------- | ------------------- | ----------------------------------- | ----- |
| **`path`** | <code>string</code> | The path to the selected directory. | 6.2.0 |


#### PickMediaOptions

| Prop                  | Type                 | Description                                                                                                                                                          | Default            | Since |
| --------------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`readData`**        | <code>boolean</code> | Whether to read the file data.                                                                                                                                       | <code>false</code> |       |
| **`skipTranscoding`** | <code>boolean</code> | Whether to avoid transcoding, if possible. On iOS, for example, HEIC images are automatically transcoded to JPEG. Only available on iOS.                             | <code>true</code>  |       |
| **`limit`**           | <code>number</code>  | The maximum number of files that the user can select. Setting this to `0` sets the selection limit to unlimited. On Android and Web, only `0` and `1` are supported. | <code>0</code>     | 5.2.0 |
| **`ordered`**         | <code>boolean</code> | Whether an ordered number is displayed instead of a check mark in the selection badge. Only available on iOS (15+).                                                  | <code>false</code> | 5.3.0 |


#### RequestPermissionsOptions

| Prop              | Type                          | Description                 | Default                                                     | Since |
| ----------------- | ----------------------------- | --------------------------- | ----------------------------------------------------------- | ----- |
| **`permissions`** | <code>PermissionType[]</code> | The permissions to request. | <code>["accessMediaLocation", "readExternalStorage"]</code> | 6.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


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


#### PermissionType

<code>'accessMediaLocation' | 'readExternalStorage'</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-picker/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-picker/LICENSE).

## Credits

This plugin is based on the [Capacitor File Picker](https://github.com/capawesome-team/capacitor-file-picker) plugin.
Thanks to everyone who contributed to the project!
