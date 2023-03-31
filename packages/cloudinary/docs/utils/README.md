# Utils

## API

<docgen-index>

* [`uploadResourceAsBlob(...)`](#uploadresourceasblob)
* [`downloadResourceAsBlob(...)`](#downloadresourceasblob)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### uploadResourceAsBlob(...)

```typescript
uploadResourceAsBlob(options: UploadResourceAsBlobOptions) => Promise<UploadResourceResult>
```

Upload a file to Cloudinary as a blob.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#uploadresourceasbloboptions">UploadResourceAsBlobOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#uploadresourceresult">UploadResourceResult</a>&gt;</code>

**Since:** 0.1.1

--------------------


### downloadResourceAsBlob(...)

```typescript
downloadResourceAsBlob(options: DownloadResourceAsBlobOptions) => Promise<DownloadResourceAsBlobResult>
```

Download a file from Cloudinary as a blob.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#downloadresourceoptions">DownloadResourceOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#downloadresourceasblobresult">DownloadResourceAsBlobResult</a>&gt;</code>

**Since:** 0.1.1

--------------------


### Interfaces


#### UploadResourceResult

| Prop                   | Type                                                  | Description                                                                              | Since |
| ---------------------- | ----------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`assetId`**          | <code>string</code>                                   | The unique asset identifier of the uploaded resource. Only available on Android and Web. | 0.0.1 |
| **`bytes`**            | <code>number</code>                                   | The number of bytes of the uploaded resource. Only available on Android and Web.         | 0.0.1 |
| **`createdAt`**        | <code>string</code>                                   | The timestamp at which the resource was uploaded.                                        | 0.0.1 |
| **`duration`**         | <code>number</code>                                   | The duration of the uploaded resource in seconds.                                        | 0.1.5 |
| **`format`**           | <code>string</code>                                   | The format of the uploaded resource.                                                     | 0.0.1 |
| **`height`**           | <code>number</code>                                   | The height of the uploaded resource.                                                     | 0.1.4 |
| **`originalFilename`** | <code>string</code>                                   | The original filename of the uploaded resource. Only available on Android and iOS.       | 0.0.1 |
| **`resourceType`**     | <code><a href="#resourcetype">ResourceType</a></code> | The resource type of the uploaded resource.                                              | 0.0.1 |
| **`publicId`**         | <code>string</code>                                   | The unique public identifier of the uploaded resource.                                   | 0.0.1 |
| **`url`**              | <code>string</code>                                   | The url of the uploaded resource.                                                        | 0.0.1 |
| **`width`**            | <code>number</code>                                   | The width of the uploaded resource.                                                      | 0.1.4 |


#### UploadResourceAsBlobOptions

| Prop               | Type                                                  | Description                                                                         | Since |
| ------------------ | ----------------------------------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`cloudName`**    | <code>string</code>                                   | The cloud name of your app which you can find in the Cloudinary Management Console. | 0.1.1 |
| **`resourceType`** | <code><a href="#resourcetype">ResourceType</a></code> | The resource type to upload.                                                        | 0.1.1 |
| **`blob`**         | <code>Blob</code>                                     | The file to upload.                                                                 | 0.1.1 |
| **`uploadPreset`** | <code>string</code>                                   | The selected upload preset.                                                         | 0.1.1 |
| **`publicId`**     | <code>string</code>                                   | Assign a unique public identifier to the resource.                                  | 0.1.1 |


#### DownloadResourceAsBlobResult

| Prop       | Type              | Description                        | Since |
| ---------- | ----------------- | ---------------------------------- | ----- |
| **`blob`** | <code>Blob</code> | The downloaded resource as a blob. | 0.1.1 |


#### DownloadResourceOptions

| Prop      | Type                | Description                          | Since |
| --------- | ------------------- | ------------------------------------ | ----- |
| **`url`** | <code>string</code> | The url of the resource to download. | 0.0.3 |


### Type Aliases


#### DownloadResourceAsBlobOptions

<code><a href="#downloadresourceoptions">DownloadResourceOptions</a></code>


### Enums


#### ResourceType

| Members     | Value                | Since |
| ----------- | -------------------- | ----- |
| **`Image`** | <code>'image'</code> | 0.0.1 |
| **`Video`** | <code>'video'</code> | 0.0.1 |
| **`Raw`**   | <code>'raw'</code>   | 0.0.1 |

</docgen-api>
