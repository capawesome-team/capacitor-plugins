# Capacitor File Picker Plugin

Capacitor plugin that allows the user to select a file, directory, image, or video from the device's file system or gallery.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor File Picker plugin is one of the most complete file selection solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 📂 **Directory picking**: Allows users to select a directory to retrieve all files.
- 🖼️ **Image picking**: Lets users select one or more images from the gallery.
- 🎥 **Video picking**: Lets users select one or more videos from the gallery.
- 📄 **File picking**: Lets users select one or more miscellaneous files from the file system.
- 📸 **HEIC to JPEG conversion**: Converts HEIC images to JPEG format on iOS.
- 📜 **File metadata**: Retrieves metadata such as file size, name, mime type, and last modified timestamp.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS. 
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The File Picker plugin is typically used whenever an app needs the user to hand over a file, for example:

- **File uploads**: Let users attach documents such as PDFs or spreadsheets to a form and upload them to a server.
- **Profile and cover pictures**: Let users choose an existing photo from their gallery.
- **Media attachments**: Add images and videos to chat messages, posts, or support tickets.
- **Data imports**: Import CSV, JSON, or backup files into your app.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Guides

- [The File Handling Guide for Capacitor](https://capawesome.io/blog/capacitor-file-handling-guide/)

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-file-picker` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-file-picker
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxActivityVersion` version of `androidx.activity:activity` (default: `1.13.0`)

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

The following examples show how to pick files, images, videos, and directories, and how to process the selected files.

### Pick one or more files

Open the system file picker and let the user select one or more files of any type. The result contains the metadata (name, size, mime type, last modified timestamp) and, on Android and iOS, the path of each selected file:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const pickFiles = async () => {
  const result = await FilePicker.pickFiles();
  const file = result.files[0];
};
```

### Restrict the picker to specific file types

Use the `types` option to only accept certain [IANA media types](https://www.iana.org/assignments/media-types/media-types.xhtml), for example PDF documents:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const pickPdfFiles = async () => {
  const result = await FilePicker.pickFiles({
    types: ['application/pdf'],
  });
};
```

### Pick images or videos from the gallery

Use `pickImages(...)`, `pickVideos(...)` or `pickMedia(...)` to open the photo gallery instead of the file picker. These methods are only available on Android and iOS:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const pickImages = async () => {
  const result = await FilePicker.pickImages();
};

const pickVideos = async () => {
  const result = await FilePicker.pickVideos();
};

const pickMedia = async () => {
  // Pick both images and videos
  const result = await FilePicker.pickMedia({ limit: 3 });
};
```

### Pick a directory

Let the user select a directory, for example to import all files it contains. Only available on Android and iOS:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const pickDirectory = async () => {
  const { path } = await FilePicker.pickDirectory();
};
```

### Upload a picked file to a server

On the Web, the picked file contains a `Blob` instance. On Android and iOS, load the file as a blob using the [Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch) and the file's path. You can then append the blob to a `FormData` object and upload it:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';
import { Capacitor } from '@capacitor/core';

const uploadFile = async () => {
  const result = await FilePicker.pickFiles({ limit: 1 });
  const file = result.files[0];

  let blob: Blob;
  if (file.blob) {
    // Web
    blob = file.blob;
  } else {
    // Android and iOS
    const response = await fetch(Capacitor.convertFileSrc(file.path!));
    blob = await response.blob();
  }

  const formData = new FormData();
  formData.append('file', blob, file.name);
  await fetch('https://example.com/upload', {
    method: 'POST',
    body: formData,
  });
};
```

**Attention**: Avoid the `readData` option for large files. It loads the entire file into memory as a Base64 string, which can crash your app. The fetch-based approach above streams the file instead.

### Convert a HEIC image to JPEG

On iOS, photos are often stored in the HEIC format, which many servers and browsers cannot display. Use `convertHeicToJpeg(...)` to convert them. Only available on iOS:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const convertHeicToJpeg = async () => {
  const { path } = await FilePicker.convertHeicToJpeg({
    path: 'path/to/image.heic',
  });
};
```

### Check and request permissions

Picking files does not require any permissions since the operating system presents the picker. However, if you need the `ACCESS_MEDIA_LOCATION` or `READ_EXTERNAL_STORAGE` permission on Android (see [Installation](#installation)), you can check and request them:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const checkPermissions = async () => {
  const result = await FilePicker.checkPermissions();
};

const requestPermissions = async () => {
  const result = await FilePicker.requestPermissions();
};
```

### Listen for the picker being dismissed

On iOS, you can be notified when the user closes the picker without selecting anything:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const addPickerDismissedListener = async () => {
  await FilePicker.addListener('pickerDismissed', () => {
    console.log('Picker was dismissed');
  });
};
```

### Copy a file

Copy a picked file to a new location, for example into your app's data directory:

```typescript
import { FilePicker } from '@capawesome/capacitor-file-picker';

const copyFile = async () => {
  await FilePicker.copyFile({
    from: 'path/to/file',
    to: 'path/to/destination',
  });
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
| **`readData`** | <code>boolean</code>  | Whether to read the file data. **Attention**: Reading large files can lead to app crashes. It's therefore not recommended to use this option. Instead, use the [fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch) to load the file as a blob, see [this example](https://capawesome.io/blog/capacitor-file-handling-guide/#read-a-file). | <code>false</code> |       |


#### PickDirectoryResult

| Prop           | Type                | Description                                                                                                                                                         | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bookmark`** | <code>string</code> | The base64-encoded security-scoped bookmark of the selected directory. It can be used to retain access to the directory across app launches. Only available on iOS. | 8.1.0 |
| **`path`**     | <code>string</code> | The path to the selected directory.                                                                                                                                 | 6.2.0 |


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

## FAQ

### How do I upload a picked file to a server?

On the Web, the picked file already contains a `Blob` instance that you can append to a `FormData` object. On Android and iOS, load the file as a blob using the Fetch API and the file's path, then upload it the same way. See the [usage example](#upload-a-picked-file-to-a-server) above and [The File Handling Guide for Capacitor](https://capawesome.io/blog/capacitor-file-handling-guide/) for a complete walkthrough.

### Why does my app crash when picking large files?

This usually happens when the `readData` option is enabled. It reads the entire file into memory as a Base64 string, which can exceed the available memory for large files. Keep `readData` disabled (the default) and load the file as a blob using the Fetch API instead, as shown in the [usage example](#upload-a-picked-file-to-a-server) above.

### What is the difference between `pickFiles`, `pickImages`, `pickMedia` and `pickVideos`?

The `pickFiles(...)` method opens the system file picker and supports any file type on Android, iOS and Web. The `pickImages(...)`, `pickVideos(...)` and `pickMedia(...)` methods open the photo gallery instead, which provides a more familiar experience for selecting photos and videos, and are only available on Android and iOS.

### Do I need any runtime permissions to pick files?

No, picking files itself does not require any runtime permissions because the operating system presents the picker on behalf of your app. On Android, the `ACCESS_MEDIA_LOCATION` permission is only needed to retrieve unredacted EXIF metadata from photos, and `READ_EXTERNAL_STORAGE` is only needed to read files from external storage. On iOS, no privacy descriptions are required.

### How is this plugin different from the Capacitor Camera and Filesystem plugins?

The Capacitor Camera plugin takes a photo with the camera or picks images from the gallery, but it does not support other file types. The Capacitor Filesystem plugin reads and writes files at known paths, but it does not provide any user interface for selecting them. The File Picker plugin fills this gap: it lets the user select any file, directory, image, or video and returns its path and metadata, which you can then process with other plugins.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open a picked file with the default application.
- [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/): Compress images before uploading them.
- [Zip](https://capawesome.io/docs/sdks/capacitor/zip/): Zip and unzip files and directories.
- [Share Target](https://capawesome.io/docs/sdks/capacitor/share-target/): Receive files shared from other apps.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-picker/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-picker/LICENSE).

## Credits

This plugin is based on the [Capacitor File Picker](https://github.com/capawesome-team/capacitor-file-picker) plugin.
Thanks to everyone who contributed to the project!
