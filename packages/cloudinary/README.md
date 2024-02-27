# @capawesome/capacitor-cloudinary

Unofficial Capacitor plugin for [Cloudinary SDK](https://cloudinary.com/documentation/cloudinary_sdks).[^1]

## Features

Capacitor Cloudinary allows you to use the native Cloudinary SDKs to upload files directly from the filesystem without going through the WebView.

- 🔋 Supports Android, iOS and the Web
- 🍕 Chunk upload of large files
- ❌ No more out-of-memory issues
- 📁 Works with the [Capacitor Filesystem](https://capacitorjs.com/docs/apis/filesystem) and [Capacitor File Picker](https://github.com/capawesome-team/capacitor-file-picker)

## Installation

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

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$cloudinaryAndroidVersion` version of `com.cloudinary:cloudinary-android` (default: `2.3.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Cloudinary, ResourceType } from '@capawesome/capacitor-cloudinary';

const initialize = async () => {
  await Cloudinary.initialize({ cloudName: 'my_cloud_name' });
};

const uploadResource = async () => {
  await Cloudinary.uploadResource({
    path: 'file:///var/mobile/Containers/Data/Application/22A433FD-D82D-4989-8BE6-9FC49DEA20BB/Images/test.png',
    publicId: 'my_public_id',
    resourceType: ResourceType.image,
    uploadPreset: 'my_preset',
  });
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/cloudinary/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/cloudinary/LICENSE).

## Credits

This plugin is based on the [Capacitor Cloudinary](https://github.com/capawesome-team/capacitor-cloudinary) plugin.
Thanks to everyone who contributed to the project!

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Cloudinary Ltd. or any of their affiliates or subsidiaries.
