# Capacitor Cloudinary Plugin

Unofficial Capacitor plugin for [Cloudinary SDK](https://cloudinary.com/documentation/cloudinary_sdks).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

Capacitor Cloudinary allows you to use the native Cloudinary SDKs to upload files directly from the filesystem without going through the WebView.

- 🔋 Supports Android, iOS and the Web
- 🍕 Chunk upload of large files
- ❌ No more out-of-memory issues
- 📁 Works with the [Capacitor Filesystem](https://capacitorjs.com/docs/apis/filesystem) and [Capacitor File Picker](https://github.com/capawesome-team/capacitor-file-picker)

## Use Cases

The Cloudinary plugin is typically used whenever an app needs to exchange media files with Cloudinary, for example:

- **User-generated content**: Upload photos and videos that users select from their device directly to your Cloudinary account.
- **Large video uploads**: Upload large files in chunks via the native Cloudinary SDKs to avoid out-of-memory crashes in the WebView.
- **Profile pictures**: Upload an image with a unique public identifier so it can be referenced and replaced later.
- **Media downloads**: Download images, videos, or raw files from Cloudinary to the device for further processing.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-cloudinary` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-cloudinary
npx cap sync
```

### Android

This API requires the following permission be added to your `AndroidManifest.xml` **before** the `application` tag:

```xml
<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
```

You also need to add the following receiver **inside** the `application` tag in your `AndroidManifest.xml`:

```xml
<receiver android:name="io.capawesome.capacitorjs.plugins.cloudinary.DownloadBroadcastReceiver" android:exported="true">
  <intent-filter>
    <action android:name="android.intent.action.DOWNLOAD_COMPLETE"/>
  </intent-filter>
</receiver>
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$cloudinaryAndroidVersion` version of `com.cloudinary:cloudinary-android` (default: `3.1.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

Import the plugin and call its methods:

```typescript
import { Cloudinary, ResourceType } from '@capawesome/capacitor-cloudinary';
```

### Initialize the plugin

Initialize the plugin with the cloud name of your app, which you can find in the Cloudinary Management Console. This method must be called once before all other methods:

```typescript
const initialize = async () => {
  await Cloudinary.initialize({ cloudName: 'my_cloud_name' });
};
```

### Upload a file to Cloudinary

Upload an image, video, or raw file to Cloudinary. On Android and iOS, pass the path of the file to upload; on the Web, pass a `Blob` instead. Note that currently only unsigned uploads are supported, so you need an upload preset:

```typescript
const uploadResource = async () => {
  await Cloudinary.uploadResource({
    path: 'file:///var/mobile/Containers/Data/Application/22A433FD-D82D-4989-8BE6-9FC49DEA20BB/Images/test.png',
    publicId: 'my_public_id',
    resourceType: ResourceType.image,
    uploadPreset: 'my_preset',
  });
};
```

### Download a file from Cloudinary

Download a resource by its URL. On Android and iOS, the result contains the path where the file was stored on the device; on the Web, it contains a `Blob`:

```typescript
const downloadResource = async () => {
  const { path } = await Cloudinary.downloadResource({
    url: 'https://res.cloudinary.com/myCloudName/image/upload/v123/123.png',
  });
  return path;
};
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`uploadResource(...)`](#uploadresource)
* [`downloadResource(...)`](#downloadresource)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the plugin.

This method must be called once before all other methods.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.0.1

--------------------


### uploadResource(...)

```typescript
uploadResource(options: UploadResourceOptions) => Promise<UploadResourceResult>
```

Upload a file to Cloudinary.

**Note**: Currently, only unsigned uploads are supported.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#uploadresourceoptions">UploadResourceOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#uploadresourceresult">UploadResourceResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### downloadResource(...)

```typescript
downloadResource(options: DownloadResourceOptions) => Promise<DownloadResourceResult>
```

Download a file from Cloudinary.

On **Android**, the file will be downloaded to the `Downloads` directory.
On **iOS**, the file will be downloaded to the temporary directory.

It is recommended to copy the file to a permanent location for
further processing after downloading.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#downloadresourceoptions">DownloadResourceOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#downloadresourceresult">DownloadResourceResult</a>&gt;</code>

**Since:** 0.0.3

--------------------


### Interfaces


#### InitializeOptions

| Prop            | Type                | Description                                                                         | Since |
| --------------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`cloudName`** | <code>string</code> | The cloud name of your app which you can find in the Cloudinary Management Console. | 0.0.1 |


#### UploadResourceResult

| Prop                   | Type                                                  | Description                                                                              | Since |
| ---------------------- | ----------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`assetId`**          | <code>string</code>                                   | The unique asset identifier of the uploaded resource. Only available on Android and Web. | 0.0.1 |
| **`bytes`**            | <code>number</code>                                   | The number of bytes of the uploaded resource.                                            | 0.0.1 |
| **`createdAt`**        | <code>string</code>                                   | The timestamp at which the resource was uploaded.                                        | 0.0.1 |
| **`duration`**         | <code>number</code>                                   | The duration of the uploaded resource in seconds.                                        | 0.1.5 |
| **`format`**           | <code>string</code>                                   | The format of the uploaded resource.                                                     | 0.0.1 |
| **`height`**           | <code>number</code>                                   | The height of the uploaded resource.                                                     | 0.1.4 |
| **`originalFilename`** | <code>string</code>                                   | The original filename of the uploaded resource. Only available on Android and iOS.       | 0.0.1 |
| **`resourceType`**     | <code><a href="#resourcetype">ResourceType</a></code> | The resource type of the uploaded resource.                                              | 0.0.1 |
| **`publicId`**         | <code>string</code>                                   | The unique public identifier of the uploaded resource.                                   | 0.0.1 |
| **`url`**              | <code>string</code>                                   | The url of the uploaded resource.                                                        | 0.0.1 |
| **`secureUrl`**        | <code>string</code>                                   | The secure url of the uploaded resource.                                                 | 5.1.0 |
| **`width`**            | <code>number</code>                                   | The width of the uploaded resource.                                                      | 0.1.4 |


#### UploadResourceOptions

| Prop               | Type                                                  | Description                                                        | Since |
| ------------------ | ----------------------------------------------------- | ------------------------------------------------------------------ | ----- |
| **`resourceType`** | <code><a href="#resourcetype">ResourceType</a></code> | The resource type to upload.                                       | 0.0.1 |
| **`blob`**         | <code>Blob</code>                                     | The file to upload. Only available on Web.                         | 0.0.1 |
| **`uploadPreset`** | <code>string</code>                                   | The selected upload preset.                                        | 0.0.1 |
| **`path`**         | <code>string</code>                                   | The path of the file to upload. Only available on Android and iOS. | 0.0.1 |
| **`publicId`**     | <code>string</code>                                   | Assign a unique public identifier to the resource.                 | 0.0.1 |


#### DownloadResourceResult

| Prop       | Type                | Description                                                                                              | Since |
| ---------- | ------------------- | -------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the downloaded resource where it is stored on the device. Only available on Android and iOS. | 0.0.3 |
| **`blob`** | <code>Blob</code>   | The downloaded resource as a blob. Only available on Web.                                                | 0.0.1 |


#### DownloadResourceOptions

| Prop      | Type                | Description                          | Since |
| --------- | ------------------- | ------------------------------------ | ----- |
| **`url`** | <code>string</code> | The url of the resource to download. | 0.0.3 |


### Enums


#### ResourceType

| Members     | Value                | Since |
| ----------- | -------------------- | ----- |
| **`Image`** | <code>'image'</code> | 0.0.1 |
| **`Video`** | <code>'video'</code> | 0.0.1 |
| **`Raw`**   | <code>'raw'</code>   | 0.0.1 |

</docgen-api>

## Utils

See [docs/utils/README.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/cloudinary/docs/utils/README.md).

## FAQ

### Do I need to call `initialize` before using the plugin?

Yes, the `initialize(...)` method must be called once before all other methods. It only requires the cloud name of your app, which you can find in the Cloudinary Management Console. See the [usage example](#initialize-the-plugin) above.

### Are signed uploads supported?

No, currently only unsigned uploads are supported. This means you need to create an unsigned upload preset in your Cloudinary account and pass its name via the `uploadPreset` option when calling `uploadResource(...)`.

### Where are downloaded files stored?

On Android, the file is downloaded to the `Downloads` directory. On iOS, the file is downloaded to the temporary directory, so it is recommended to copy it to a permanent location for further processing. On the Web, the downloaded resource is returned as a `Blob` instead of a path.

### How do I upload a file on the Web?

On the Web, pass the file as a `Blob` via the `blob` option of `uploadResource(...)`. The `path` option is only available on Android and iOS, where it points to a file on the device's filesystem.

### What do I need to configure on Android?

You need to add the `DOWNLOAD_WITHOUT_NOTIFICATION` permission and the `DownloadBroadcastReceiver` receiver to your `AndroidManifest.xml`, as described in the [Installation](#installation) section. No additional configuration is required on iOS or the Web.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select a file, image, or video to upload to Cloudinary.
- [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/): Compress images before uploading them.
- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open a downloaded file with the default application.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/cloudinary/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/cloudinary/LICENSE).

## Credits

This plugin is based on the [Capacitor Cloudinary](https://github.com/capawesome-team/capacitor-cloudinary) plugin.
Thanks to everyone who contributed to the project!

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Cloudinary Ltd. or any of their affiliates or subsidiaries.
