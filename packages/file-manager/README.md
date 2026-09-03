# Capacitor File Manager Plugin

Capacitor plugin to manage files and directories on Android, iOS and Web. Persist access to user-picked folders, run copy, move and delete operations with progress and cancellation, calculate checksums, and inspect device and app storage.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor File Manager plugin works with the user's own storage, not just your app's sandbox. Here are some of the key features:

- 📂 **Persistent Directory Access**: Let the user pick a folder once with the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugin and keep read and write access to it across app launches — Play-policy-compliant on Android, sandbox-correct on iOS.
- 🔗 **URI-based API**: Every method works directly with file URIs. Construct them with `getUri(...)` from well-known directories or any parent URI.
- 📄 **Files**: Read, write, append, truncate, copy, move and delete files with constant memory usage, including random access reads and writes for large files.
- 🗂️ **Directories**: Create, read, copy, move, delete and clear directories, and calculate their total size — with rich metadata and paging.
- 📊 **Progress & Cancellation**: Directory operations report progress events and can be canceled at any time.
- 🔢 **Checksums**: Calculate MD5, SHA-1 and SHA-256 checksums of files of any size with constant memory usage.
- 🧲 **Blob Reads**: Read large files as a `Blob` without base64 overhead via `readFileAsBlob(...)`.
- 💾 **Storage Info**: Query the total and free storage space of the device, the storage usage of your app, and clear the app's cache.
- 🌐 **Web Support**: A real web implementation on top of the Origin Private File System with actual directories, streaming and random access — not base64 blobs in IndexedDB.
- ⚠️ **Error Codes**: Every runtime failure rejects with a documented error code, so you can branch on it instead of parsing messages.
- 🤝 **Compatibility**: Works hand in hand with the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/), [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) and [File Transfer](https://capawesome.io/docs/sdks/capacitor/file-transfer/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The File Manager plugin is typically used whenever an app needs durable access to user-picked folders or manager-grade file operations, for example:

- **Document management apps**: Let the user pick a folder once and organize, rename and sort documents in it across app launches.
- **Download and media managers**: Check the free storage space before a download, verify the result with a checksum, and move it into a user-visible folder.
- **Backup and export**: Export app data into a folder the user picked, with progress reporting and cancellation for large exports.
- **Large file processing**: Read file headers with random access, stream large files into your web code as a `Blob`, and truncate or append without rewriting the whole file.
- **Cache management**: Show the user how much space your app occupies and offer a one-tap cache cleanup.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-file-manager` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-file-manager
npx cap sync
```

### Android

This plugin requires no storage permissions. The app's sandbox directories are accessible without any permission, and user-visible folders are accessed through persisted directories, which are granted by the user in the system file picker.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### Web

On the web platform, files are stored in the [Origin Private File System](https://developer.mozilla.org/en-US/docs/Web/API/File_System_API/Origin_private_file_system). Persisted directories and checksums are not available on the web.

#### Data persistence

The Origin Private File System is subject to the browser's storage eviction rules. By default, storage is granted on a best-effort basis: Chromium-based browsers and Firefox only evict data under storage pressure, but Safari deletes all script-writable storage after seven days without user interaction if the app runs in a browser tab. Web apps added to the home screen are exempt from this rule.

To reduce the risk of eviction, request persistent storage with [`navigator.storage.persist()`](https://developer.mozilla.org/en-US/docs/Web/API/StorageManager/persist) at a moment of meaningful user engagement (for example, after sign-in or after the user has saved data for the first time). Note that persistence is granted at the browser's discretion and reduces the risk of eviction, but is not an absolute guarantee.

## Configuration

No configuration required for this plugin.

## Usage

### Write and read a file in the app's sandbox

```typescript
import { Directory, Encoding, FileManager } from '@capawesome-team/capacitor-file-manager';

const writeAndReadFile = async () => {
  const { uri } = await FileManager.getUri({
    path: 'notes/todo.txt',
    directory: Directory.Data,
  });
  await FileManager.writeFile({
    uri,
    data: 'Hello, World!',
    encoding: Encoding.Utf8,
    recursive: true,
  });
  const { data } = await FileManager.readFile({ uri, encoding: Encoding.Utf8 });
  return data;
};
```

### Persist access to a picked directory

This requires version `8.1.0` or later of the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugin, since `pickDirectory()` only returns a bookmark from that version on.

```typescript
import { FileManager } from '@capawesome-team/capacitor-file-manager';
import { FilePicker } from '@capawesome/capacitor-file-picker';

const persistDirectoryAccess = async () => {
  const result = await FilePicker.pickDirectory();
  const { directory } = await FileManager.persistDirectoryAccess({
    uri: result.path,
    bookmark: result.bookmark,
  });
  return directory;
};

const getPersistedDirectories = async () => {
  // Call this on app start and use the returned URIs
  // instead of storing them yourself.
  const { directories } = await FileManager.getPersistedDirectories();
  return directories;
};
```

### Work inside a persisted directory

```typescript
import { Directory, FileManager } from '@capawesome-team/capacitor-file-manager';

const exportIntoPickedDirectory = async (directoryUri: string) => {
  // Construct the URI of a file inside the picked directory.
  const { uri: targetUri } = await FileManager.getUri({
    path: 'exports/report.pdf',
    parentUri: directoryUri,
  });
  // Copy a file from the app's sandbox into the picked directory.
  const { uri: sourceUri } = await FileManager.getUri({
    path: 'report.pdf',
    directory: Directory.Cache,
  });
  const { uri } = await FileManager.copyFile({ uri: sourceUri, toUri: targetUri });
  return uri;
};

const listPickedDirectory = async (directoryUri: string) => {
  const { entries } = await FileManager.readDirectory({ uri: directoryUri });
  return entries;
};
```

### Copy a directory with progress and cancellation

```typescript
import { FileManager } from '@capawesome-team/capacitor-file-manager';

const copyDirectoryWithProgress = async (uri: string, toUri: string) => {
  await FileManager.addListener('operationProgress', (event) => {
    console.log(`Processed ${event.processedFiles} of ${event.totalFiles} files`);
  });
  await FileManager.copyDirectory({ uri, toUri, id: 'my-copy-operation' });
};

const cancelCopy = async () => {
  await FileManager.cancelOperationById({ id: 'my-copy-operation' });
};
```

### Read a large file as a Blob

```typescript
import { FileManager } from '@capawesome-team/capacitor-file-manager';

const readLargeFile = async (uri: string) => {
  // Streams the file without base64 overhead.
  const { blob } = await FileManager.readFileAsBlob({ uri });
  return blob;
};
```

### Verify a file with a checksum

```typescript
import { ChecksumAlgorithm, FileManager } from '@capawesome-team/capacitor-file-manager';

const verifyFile = async (uri: string, expectedChecksum: string) => {
  const { checksum } = await FileManager.getFileChecksum({
    uri,
    algorithm: ChecksumAlgorithm.Sha256,
  });
  return checksum === expectedChecksum;
};
```

### Check the storage space

```typescript
import { FileManager } from '@capawesome-team/capacitor-file-manager';

const checkStorage = async () => {
  const { freeBytes } = await FileManager.getDeviceStorageInfo();
  const { cacheBytes } = await FileManager.getAppStorageInfo();
  if (cacheBytes > 100_000_000) {
    await FileManager.clearCache();
  }
  return freeBytes;
};
```

## API

<docgen-index>

* [`appendFile(...)`](#appendfile)
* [`cancelOperationById(...)`](#canceloperationbyid)
* [`clearCache()`](#clearcache)
* [`clearDirectory(...)`](#cleardirectory)
* [`copyDirectory(...)`](#copydirectory)
* [`copyFile(...)`](#copyfile)
* [`createDirectory(...)`](#createdirectory)
* [`deleteDirectory(...)`](#deletedirectory)
* [`deleteFile(...)`](#deletefile)
* [`exists(...)`](#exists)
* [`getAppStorageInfo()`](#getappstorageinfo)
* [`getDeviceStorageInfo()`](#getdevicestorageinfo)
* [`getDirectorySize(...)`](#getdirectorysize)
* [`getFileChecksum(...)`](#getfilechecksum)
* [`getMetadata(...)`](#getmetadata)
* [`getPersistedDirectories()`](#getpersisteddirectories)
* [`getUri(...)`](#geturi)
* [`moveDirectory(...)`](#movedirectory)
* [`moveFile(...)`](#movefile)
* [`persistDirectoryAccess(...)`](#persistdirectoryaccess)
* [`readDirectory(...)`](#readdirectory)
* [`readFile(...)`](#readfile)
* [`readFileAsBlob(...)`](#readfileasblob)
* [`releaseDirectoryAccess(...)`](#releasedirectoryaccess)
* [`truncateFile(...)`](#truncatefile)
* [`writeFile(...)`](#writefile)
* [`addListener('operationProgress', ...)`](#addlisteneroperationprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### appendFile(...)

```typescript
appendFile(options: AppendFileOptions) => Promise<void>
```

Append data to a file.

If the file does not exist, it is created.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#appendfileoptions">AppendFileOptions</a></code> |

**Since:** 0.0.1

--------------------


### cancelOperationById(...)

```typescript
cancelOperationById(options: CancelOperationByIdOptions) => Promise<void>
```

Cancel a running directory operation.

The promise of the canceled operation is rejected with the
`OPERATION_CANCELED` error code. Files that were already processed
remain in place.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#canceloperationbyidoptions">CancelOperationByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### clearCache()

```typescript
clearCache() => Promise<void>
```

Delete the contents of the app's cache directory.

**Since:** 0.0.1

--------------------


### clearDirectory(...)

```typescript
clearDirectory(options: ClearDirectoryOptions) => Promise<void>
```

Delete the contents of a directory but keep the directory itself.

Unlike deleting and recreating the directory, this preserves
persisted access to the directory.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#cleardirectoryoptions">ClearDirectoryOptions</a></code> |

**Since:** 0.0.1

--------------------


### copyDirectory(...)

```typescript
copyDirectory(options: CopyDirectoryOptions) => Promise<CopyDirectoryResult>
```

Copy a directory recursively.

Reports progress via the `operationProgress` event and can be
canceled with `cancelOperationById(...)`.

On **Android**, the operation fails with the `WRITE_FAILED` error code if a
document provider returns an entry name that cannot be used as a file name
(e.g. a name containing `/`).

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#copydirectoryoptions">CopyDirectoryOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#copydirectoryresult">CopyDirectoryResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### copyFile(...)

```typescript
copyFile(options: CopyFileOptions) => Promise<CopyFileResult>
```

Copy a file.

The file is copied natively with constant memory usage.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#copyfileoptions">CopyFileOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#copyfileresult">CopyFileResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### createDirectory(...)

```typescript
createDirectory(options: CreateDirectoryOptions) => Promise<CreateDirectoryResult>
```

Create a new directory.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createdirectoryoptions">CreateDirectoryOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createdirectoryresult">CreateDirectoryResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### deleteDirectory(...)

```typescript
deleteDirectory(options: DeleteDirectoryOptions) => Promise<void>
```

Delete a directory.

Reports progress via the `operationProgress` event and can be
canceled with `cancelOperationById(...)`.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletedirectoryoptions">DeleteDirectoryOptions</a></code> |

**Since:** 0.0.1

--------------------


### deleteFile(...)

```typescript
deleteFile(options: DeleteFileOptions) => Promise<void>
```

Delete a file.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#deletefileoptions">DeleteFileOptions</a></code> |

**Since:** 0.0.1

--------------------


### exists(...)

```typescript
exists(options: ExistsOptions) => Promise<ExistsResult>
```

Check whether a file or directory exists.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#existsoptions">ExistsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#existsresult">ExistsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getAppStorageInfo()

```typescript
getAppStorageInfo() => Promise<GetAppStorageInfoResult>
```

Get the storage usage of the app itself.

**Returns:** <code>Promise&lt;<a href="#getappstorageinforesult">GetAppStorageInfoResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getDeviceStorageInfo()

```typescript
getDeviceStorageInfo() => Promise<GetDeviceStorageInfoResult>
```

Get the total and free storage space of the device.

**Returns:** <code>Promise&lt;<a href="#getdevicestorageinforesult">GetDeviceStorageInfoResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getDirectorySize(...)

```typescript
getDirectorySize(options: GetDirectorySizeOptions) => Promise<GetDirectorySizeResult>
```

Get the total size of a directory and all of its contents.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getdirectorysizeoptions">GetDirectorySizeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getdirectorysizeresult">GetDirectorySizeResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getFileChecksum(...)

```typescript
getFileChecksum(options: GetFileChecksumOptions) => Promise<GetFileChecksumResult>
```

Calculate the checksum of a file.

The file is processed in a streaming manner with constant memory
usage, so it works for files of any size.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getfilechecksumoptions">GetFileChecksumOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getfilechecksumresult">GetFileChecksumResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getMetadata(...)

```typescript
getMetadata(options: GetMetadataOptions) => Promise<GetMetadataResult>
```

Get the metadata of a file or directory.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getmetadataoptions">GetMetadataOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getmetadataresult">GetMetadataResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getPersistedDirectories()

```typescript
getPersistedDirectories() => Promise<GetPersistedDirectoriesResult>
```

Get all directories with persisted access.

Stale entries are refreshed and directories whose document no longer
exists are released automatically.
Call this method on app start and use the returned URIs instead of
storing them yourself, since they may change between app launches.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getpersisteddirectoriesresult">GetPersistedDirectoriesResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getUri(...)

```typescript
getUri(options: GetUriOptions) => Promise<GetUriResult>
```

Construct the URI of a file or directory.

Provide either `directory` to resolve the path in a well-known
sandbox directory or `parentUri` to resolve the path relative to
any directory URI. The file or directory does not need to exist.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#geturioptions">GetUriOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#geturiresult">GetUriResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### moveDirectory(...)

```typescript
moveDirectory(options: MoveDirectoryOptions) => Promise<MoveDirectoryResult>
```

Move a directory.

Reports progress via the `operationProgress` event and can be
canceled with `cancelOperationById(...)`.

On **Android**, the operation fails with the `WRITE_FAILED` error code if a
document provider returns an entry name that cannot be used as a file name
(e.g. a name containing `/`).

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#movedirectoryoptions">MoveDirectoryOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#movedirectoryresult">MoveDirectoryResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### moveFile(...)

```typescript
moveFile(options: MoveFileOptions) => Promise<MoveFileResult>
```

Move a file.

A move within the same directory is performed as a rename.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#movefileoptions">MoveFileOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#movefileresult">MoveFileResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### persistDirectoryAccess(...)

```typescript
persistDirectoryAccess(options: PersistDirectoryAccessOptions) => Promise<PersistDirectoryAccessResult>
```

Persist access to a directory across app launches.

Pass the values returned by `FilePicker.pickDirectory()` of the
Capawesome File Picker plugin. Afterwards, the URIs of the persisted
directory and its contents are accepted by every method of this plugin.

Requires version `8.1.0` or later of the Capawesome File Picker plugin.

Only available on Android and iOS.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#persistdirectoryaccessoptions">PersistDirectoryAccessOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#persistdirectoryaccessresult">PersistDirectoryAccessResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### readDirectory(...)

```typescript
readDirectory(options: ReadDirectoryOptions) => Promise<ReadDirectoryResult>
```

Read the entries of a directory.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#readdirectoryoptions">ReadDirectoryOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readdirectoryresult">ReadDirectoryResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### readFile(...)

```typescript
readFile(options: ReadFileOptions) => Promise<ReadFileResult>
```

Read data from a file.

Use `offset` and `length` to read a byte range of a large file.

**Attention**: To read a whole large file, use `readFileAsBlob(...)` instead,
since it streams the file and avoids out-of-memory (OOM) issues. The `readFile(...)`
method reads the whole file into memory and encodes it as base64.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#readfileoptions">ReadFileOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readfileresult">ReadFileResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### readFileAsBlob(...)

```typescript
readFileAsBlob(options: ReadFileAsBlobOptions) => Promise<ReadFileAsBlobResult>
```

Read a file as a `Blob`.

This is the recommended way to read large files. The file is
streamed without base64 encoding overhead.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#readfileasbloboptions">ReadFileAsBlobOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readfileasblobresult">ReadFileAsBlobResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### releaseDirectoryAccess(...)

```typescript
releaseDirectoryAccess(options: ReleaseDirectoryAccessOptions) => Promise<void>
```

Release persisted access to a directory.

Only available on Android and iOS.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#releasedirectoryaccessoptions">ReleaseDirectoryAccessOptions</a></code> |

**Since:** 0.0.1

--------------------


### truncateFile(...)

```typescript
truncateFile(options: TruncateFileOptions) => Promise<void>
```

Truncate a file to a given size.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#truncatefileoptions">TruncateFileOptions</a></code> |

**Since:** 0.0.1

--------------------


### writeFile(...)

```typescript
writeFile(options: WriteFileOptions) => Promise<WriteFileResult>
```

Write data to a file.

If the file does not exist, it is created. If no `position` is
provided, the existing content is overwritten.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#writefileoptions">WriteFileOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#writefileresult">WriteFileResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('operationProgress', ...)

```typescript
addListener(eventName: 'operationProgress', listenerFunc: (event: OperationProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called when a directory operation reports progress.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'operationProgress'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#operationprogressevent">OperationProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### AppendFileOptions

| Prop           | Type                                          | Description                        | Default                      | Since |
| -------------- | --------------------------------------------- | ---------------------------------- | ---------------------------- | ----- |
| **`data`**     | <code>string</code>                           | The data to append to the file.    |                              | 0.0.1 |
| **`encoding`** | <code><a href="#encoding">Encoding</a></code> | The encoding of the `data` option. | <code>Encoding.Base64</code> | 0.0.1 |
| **`uri`**      | <code>string</code>                           | The URI of the file.               |                              | 0.0.1 |


#### CancelOperationByIdOptions

| Prop     | Type                | Description                                | Since |
| -------- | ------------------- | ------------------------------------------ | ----- |
| **`id`** | <code>string</code> | The identifier of the operation to cancel. | 0.0.1 |


#### ClearDirectoryOptions

| Prop      | Type                | Description               | Since |
| --------- | ------------------- | ------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the directory. | 0.0.1 |


#### CopyDirectoryResult

| Prop      | Type                | Description                       | Since |
| --------- | ------------------- | --------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the created directory. | 0.0.1 |


#### CopyDirectoryOptions

| Prop        | Type                | Description                                                                                                                 | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**    | <code>string</code> | A unique identifier for this operation. It can be used to cancel the operation and to correlate `operationProgress` events. | 0.0.1 |
| **`toUri`** | <code>string</code> | The URI of the directory to copy to.                                                                                        | 0.0.1 |
| **`uri`**   | <code>string</code> | The URI of the directory to copy.                                                                                           | 0.0.1 |


#### CopyFileResult

| Prop      | Type                | Description                                                                                                                                            | Since |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`uri`** | <code>string</code> | The URI of the created file. This may differ from the requested URI, for example if the document provider had to rename the file to avoid a collision. | 0.0.1 |


#### CopyFileOptions

| Prop        | Type                | Description                     | Since |
| ----------- | ------------------- | ------------------------------- | ----- |
| **`toUri`** | <code>string</code> | The URI of the file to copy to. | 0.0.1 |
| **`uri`**   | <code>string</code> | The URI of the file to copy.    | 0.0.1 |


#### CreateDirectoryResult

| Prop      | Type                | Description                       | Since |
| --------- | ------------------- | --------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the created directory. | 0.0.1 |


#### CreateDirectoryOptions

| Prop            | Type                 | Description                                          | Default           | Since |
| --------------- | -------------------- | ---------------------------------------------------- | ----------------- | ----- |
| **`recursive`** | <code>boolean</code> | Whether or not to create missing parent directories. | <code>true</code> | 0.0.1 |
| **`uri`**       | <code>string</code>  | The URI of the directory to create.                  |                   | 0.0.1 |


#### DeleteDirectoryOptions

| Prop            | Type                 | Description                                                                                                                                             | Default            | Since |
| --------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`id`**        | <code>string</code>  | A unique identifier for this operation. It can be used to cancel the operation and to correlate `operationProgress` events.                             |                    | 0.0.1 |
| **`recursive`** | <code>boolean</code> | Whether or not to delete the contents of the directory. If `false`, the call is rejected with the `NOT_EMPTY` error code if the directory is not empty. | <code>false</code> | 0.0.1 |
| **`uri`**       | <code>string</code>  | The URI of the directory to delete.                                                                                                                     |                    | 0.0.1 |


#### DeleteFileOptions

| Prop      | Type                | Description                    | Since |
| --------- | ------------------- | ------------------------------ | ----- |
| **`uri`** | <code>string</code> | The URI of the file to delete. | 0.0.1 |


#### ExistsResult

| Prop         | Type                 | Description                                  | Since |
| ------------ | -------------------- | -------------------------------------------- | ----- |
| **`exists`** | <code>boolean</code> | Whether or not the file or directory exists. | 0.0.1 |


#### ExistsOptions

| Prop      | Type                | Description                       | Since |
| --------- | ------------------- | --------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the file or directory. | 0.0.1 |


#### GetAppStorageInfoResult

| Prop             | Type                        | Description                                                                       | Since |
| ---------------- | --------------------------- | --------------------------------------------------------------------------------- | ----- |
| **`cacheBytes`** | <code>number</code>         | The size of the app's cache in bytes.                                             | 0.0.1 |
| **`dataBytes`**  | <code>number \| null</code> | The size of the app's data in bytes. Is `null` if the platform cannot provide it. | 0.0.1 |


#### GetDeviceStorageInfoResult

| Prop                   | Type                        | Description                                                                                                                                                                    | Since |
| ---------------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`allocatableBytes`** | <code>number \| null</code> | The number of bytes the app is allowed to allocate, including space the system can free up for the app (e.g. by deleting caches). Is `null` if the platform cannot provide it. | 0.0.1 |
| **`freeBytes`**        | <code>number</code>         | The number of free bytes.                                                                                                                                                      | 0.0.1 |
| **`totalBytes`**       | <code>number</code>         | The number of total bytes.                                                                                                                                                     | 0.0.1 |


#### GetDirectorySizeResult

| Prop       | Type                | Description                                                       | Since |
| ---------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`size`** | <code>number</code> | The total size of the directory and all of its contents in bytes. | 0.0.1 |


#### GetDirectorySizeOptions

| Prop      | Type                | Description               | Since |
| --------- | ------------------- | ------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the directory. | 0.0.1 |


#### GetFileChecksumResult

| Prop           | Type                | Description                                                 | Since |
| -------------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`checksum`** | <code>string</code> | The checksum of the file as a lowercase hexadecimal string. | 0.0.1 |


#### GetFileChecksumOptions

| Prop            | Type                                                            | Description                                        | Since |
| --------------- | --------------------------------------------------------------- | -------------------------------------------------- | ----- |
| **`algorithm`** | <code><a href="#checksumalgorithm">ChecksumAlgorithm</a></code> | The algorithm to use for the checksum calculation. | 0.0.1 |
| **`uri`**       | <code>string</code>                                             | The URI of the file.                               | 0.0.1 |


#### GetMetadataResult

| Prop             | Type                                            | Description                                                                                                                                 | Since |
| ---------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`createdAt`**  | <code>number \| null</code>                     | The timestamp when the file or directory was created, in milliseconds since the epoch. Is `null` if the platform does not provide it.       | 0.0.1 |
| **`mimeType`**   | <code>string \| null</code>                     | The MIME type of the file. Is `null` for directories or if the type is unknown.                                                             | 0.0.1 |
| **`modifiedAt`** | <code>number \| null</code>                     | The timestamp when the file or directory was last modified, in milliseconds since the epoch. Is `null` if the platform does not provide it. | 0.0.1 |
| **`name`**       | <code>string</code>                             | The name of the file or directory.                                                                                                          | 0.0.1 |
| **`size`**       | <code>number</code>                             | The size of the file in bytes. Is always `0` for directories.                                                                               | 0.0.1 |
| **`type`**       | <code><a href="#entrytype">EntryType</a></code> | The type of the entry.                                                                                                                      | 0.0.1 |
| **`uri`**        | <code>string</code>                             | The canonical URI of the file or directory.                                                                                                 | 0.0.1 |


#### GetMetadataOptions

| Prop      | Type                | Description                       | Since |
| --------- | ------------------- | --------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the file or directory. | 0.0.1 |


#### GetPersistedDirectoriesResult

| Prop              | Type                              | Description                            | Since |
| ----------------- | --------------------------------- | -------------------------------------- | ----- |
| **`directories`** | <code>PersistedDirectory[]</code> | The directories with persisted access. | 0.0.1 |


#### PersistedDirectory

| Prop       | Type                | Description                                                                                                                                                                                                          | Since |
| ---------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`name`** | <code>string</code> | The name of the directory.                                                                                                                                                                                           | 0.0.1 |
| **`uri`**  | <code>string</code> | The URI of the directory. The URI identifies the persisted directory in all methods of this plugin. It may change between app launches, so do not store it yourself but retrieve it via `getPersistedDirectories()`. | 0.0.1 |


#### GetUriResult

| Prop      | Type                | Description                       | Since |
| --------- | ------------------- | --------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the file or directory. | 0.0.1 |


#### GetUriOptions

| Prop            | Type                                            | Description                                                                                                                                                                                                                                                                           | Since |
| --------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`directory`** | <code><a href="#directory">Directory</a></code> | The well-known sandbox directory to resolve the path in. Either this option or `parentUri` must be provided.                                                                                                                                                                          | 0.0.1 |
| **`parentUri`** | <code>string</code>                             | The URI of the directory to resolve the path in. Either this option or `directory` must be provided. On Android, constructing the URI of a non-existing entry inside a persisted directory is only supported for path-structured document providers such as the local device storage. | 0.0.1 |
| **`path`**      | <code>string</code>                             | The path to resolve.                                                                                                                                                                                                                                                                  | 0.0.1 |


#### MoveDirectoryResult

| Prop      | Type                | Description                     | Since |
| --------- | ------------------- | ------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the moved directory. | 0.0.1 |


#### MoveDirectoryOptions

| Prop        | Type                | Description                                                                                                                 | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**    | <code>string</code> | A unique identifier for this operation. It can be used to cancel the operation and to correlate `operationProgress` events. | 0.0.1 |
| **`toUri`** | <code>string</code> | The URI of the directory to move to.                                                                                        | 0.0.1 |
| **`uri`**   | <code>string</code> | The URI of the directory to move.                                                                                           | 0.0.1 |


#### MoveFileResult

| Prop      | Type                | Description                                                                                                                                          | Since |
| --------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the moved file. This may differ from the requested URI, for example if the document provider had to rename the file to avoid a collision. | 0.0.1 |


#### MoveFileOptions

| Prop        | Type                | Description                     | Since |
| ----------- | ------------------- | ------------------------------- | ----- |
| **`toUri`** | <code>string</code> | The URI of the file to move to. | 0.0.1 |
| **`uri`**   | <code>string</code> | The URI of the file to move.    | 0.0.1 |


#### PersistDirectoryAccessResult

| Prop            | Type                                                              | Description              | Since |
| --------------- | ----------------------------------------------------------------- | ------------------------ | ----- |
| **`directory`** | <code><a href="#persisteddirectory">PersistedDirectory</a></code> | The persisted directory. | 0.0.1 |


#### PersistDirectoryAccessOptions

| Prop           | Type                | Description                                                                                                                                                                                                      | Since |
| -------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bookmark`** | <code>string</code> | The base64-encoded security-scoped bookmark of the directory as returned by `FilePicker.pickDirectory()`. Only returned by version `8.1.0` or later of the Capawesome File Picker plugin. Only available on iOS. | 0.0.1 |
| **`uri`**      | <code>string</code> | The URI of the directory as returned by `FilePicker.pickDirectory()` (named `path` in its result).                                                                                                               | 0.0.1 |


#### ReadDirectoryResult

| Prop          | Type                          | Description                   | Since |
| ------------- | ----------------------------- | ----------------------------- | ----- |
| **`entries`** | <code>DirectoryEntry[]</code> | The entries of the directory. | 0.0.1 |


#### DirectoryEntry

| Prop             | Type                                            | Description                                                                                                                     | Since |
| ---------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`createdAt`**  | <code>number \| null</code>                     | The timestamp when the entry was created, in milliseconds since the epoch. Is `null` if the platform does not provide it.       | 0.0.1 |
| **`mimeType`**   | <code>string \| null</code>                     | The MIME type of the entry. Is `null` for directories or if the type is unknown.                                                | 0.0.1 |
| **`modifiedAt`** | <code>number \| null</code>                     | The timestamp when the entry was last modified, in milliseconds since the epoch. Is `null` if the platform does not provide it. | 0.0.1 |
| **`name`**       | <code>string</code>                             | The name of the entry.                                                                                                          | 0.0.1 |
| **`size`**       | <code>number</code>                             | The size of the entry in bytes. Is always `0` for directories.                                                                  | 0.0.1 |
| **`type`**       | <code><a href="#entrytype">EntryType</a></code> | The type of the entry.                                                                                                          | 0.0.1 |
| **`uri`**        | <code>string</code>                             | The URI of the entry.                                                                                                           | 0.0.1 |


#### ReadDirectoryOptions

| Prop         | Type                | Description                                                                    | Default        | Since |
| ------------ | ------------------- | ------------------------------------------------------------------------------ | -------------- | ----- |
| **`limit`**  | <code>number</code> | The maximum number of entries to return. By default, all entries are returned. |                | 0.0.1 |
| **`offset`** | <code>number</code> | The number of entries to skip.                                                 | <code>0</code> | 0.0.1 |
| **`uri`**    | <code>string</code> | The URI of the directory to read.                                              |                | 0.0.1 |


#### ReadFileResult

| Prop       | Type                | Description                                                                  | Since |
| ---------- | ------------------- | ---------------------------------------------------------------------------- | ----- |
| **`data`** | <code>string</code> | The data read from the file, encoded as requested via the `encoding` option. | 0.0.1 |


#### ReadFileOptions

| Prop           | Type                                          | Description                                                                   | Default                      | Since |
| -------------- | --------------------------------------------- | ----------------------------------------------------------------------------- | ---------------------------- | ----- |
| **`encoding`** | <code><a href="#encoding">Encoding</a></code> | The encoding of the returned data.                                            | <code>Encoding.Base64</code> | 0.0.1 |
| **`length`**   | <code>number</code>                           | The maximum number of bytes to read. By default, the file is read to the end. |                              | 0.0.1 |
| **`offset`**   | <code>number</code>                           | The zero-based byte offset at which to start reading.                         | <code>0</code>               | 0.0.1 |
| **`uri`**      | <code>string</code>                           | The URI of the file to read.                                                  |                              | 0.0.1 |


#### ReadFileAsBlobResult

| Prop       | Type              | Description                           | Since |
| ---------- | ----------------- | ------------------------------------- | ----- |
| **`blob`** | <code>Blob</code> | The contents of the file as a `Blob`. | 0.0.1 |


#### ReadFileAsBlobOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the file to read. | 0.0.1 |


#### ReleaseDirectoryAccessOptions

| Prop      | Type                | Description                         | Since |
| --------- | ------------------- | ----------------------------------- | ----- |
| **`uri`** | <code>string</code> | The URI of the persisted directory. | 0.0.1 |


#### TruncateFileOptions

| Prop       | Type                | Description                                | Default        | Since |
| ---------- | ------------------- | ------------------------------------------ | -------------- | ----- |
| **`size`** | <code>number</code> | The size in bytes to truncate the file to. | <code>0</code> | 0.0.1 |
| **`uri`**  | <code>string</code> | The URI of the file to truncate.           |                | 0.0.1 |


#### WriteFileResult

| Prop      | Type                | Description                                                                                                                                            | Since |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`uri`** | <code>string</code> | The URI of the written file. This may differ from the requested URI, for example if the document provider had to rename the file to avoid a collision. | 0.0.1 |


#### WriteFileOptions

| Prop            | Type                                          | Description                                                                                                                                                 | Default                      | Since |
| --------------- | --------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------- | ----- |
| **`data`**      | <code>string</code>                           | The data to write to the file.                                                                                                                              |                              | 0.0.1 |
| **`encoding`**  | <code><a href="#encoding">Encoding</a></code> | The encoding of the `data` option.                                                                                                                          | <code>Encoding.Base64</code> | 0.0.1 |
| **`position`**  | <code>number</code>                           | The zero-based byte offset at which to start writing. Existing content outside of the written range is preserved. If not provided, the file is overwritten. |                              | 0.0.1 |
| **`recursive`** | <code>boolean</code>                          | Whether or not to create missing parent directories.                                                                                                        | <code>false</code>           | 0.0.1 |
| **`uri`**       | <code>string</code>                           | The URI of the file to write.                                                                                                                               |                              | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### OperationProgressEvent

| Prop                 | Type                                                    | Description                                                                                                           | Since |
| -------------------- | ------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**             | <code>string \| null</code>                             | The identifier of the operation as provided in the options of the operation. Is `null` if no identifier was provided. | 0.0.1 |
| **`operationType`**  | <code><a href="#operationtype">OperationType</a></code> | The type of the operation.                                                                                            | 0.0.1 |
| **`processedBytes`** | <code>number</code>                                     | The number of bytes that have been processed so far.                                                                  | 0.0.1 |
| **`processedFiles`** | <code>number</code>                                     | The number of files that have been processed so far.                                                                  | 0.0.1 |
| **`totalBytes`**     | <code>number \| null</code>                             | The total number of bytes to process. Is `null` if the total is not yet known.                                        | 0.0.1 |
| **`totalFiles`**     | <code>number \| null</code>                             | The total number of files to process. Is `null` if the total is not yet known.                                        | 0.0.1 |


### Enums


#### Encoding

| Members      | Value                 | Description      | Since |
| ------------ | --------------------- | ---------------- | ----- |
| **`Base64`** | <code>'BASE64'</code> | Base64 encoding. | 0.0.1 |
| **`Utf8`**   | <code>'UTF8'</code>   | UTF-8 encoding.  | 0.0.1 |


#### ChecksumAlgorithm

| Members      | Value                 | Description | Since |
| ------------ | --------------------- | ----------- | ----- |
| **`Md5`**    | <code>'MD5'</code>    | MD5.        | 0.0.1 |
| **`Sha1`**   | <code>'SHA1'</code>   | SHA-1.      | 0.0.1 |
| **`Sha256`** | <code>'SHA256'</code> | SHA-256.    | 0.0.1 |


#### EntryType

| Members         | Value                    | Description  | Since |
| --------------- | ------------------------ | ------------ | ----- |
| **`Directory`** | <code>'DIRECTORY'</code> | A directory. | 0.0.1 |
| **`File`**      | <code>'FILE'</code>      | A file.      | 0.0.1 |


#### Directory

| Members              | Value                           | Description                                                                                                                                                                                                                                                                                                                              | Since |
| -------------------- | ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Cache`**          | <code>'CACHE'</code>            | The cache directory. The system may delete this directory to free up disk space. On Android, it maps to `getCacheDir()`. On iOS, it maps to `Library/Caches`. On Web, it maps to `opfs://cache`.                                                                                                                                         | 0.0.1 |
| **`Data`**           | <code>'DATA'</code>             | The directory holding application files. On Android, it maps to `getFilesDir()`. On iOS, it maps to `Documents`. On Web, it maps to `opfs://data`.                                                                                                                                                                                       | 0.0.1 |
| **`Documents`**      | <code>'DOCUMENTS'</code>        | The documents directory. On Android, it maps to the app-specific documents directory (`getExternalFilesDir(DIRECTORY_DOCUMENTS)`). Unlike `@capacitor/filesystem`, no storage permissions are required. Use persisted directories to access user-visible folders. On iOS, it maps to `Documents`. On Web, it maps to `opfs://documents`. | 0.0.1 |
| **`External`**       | <code>'EXTERNAL'</code>         | The external directory. On Android, it maps to `getExternalFilesDir(null)`. On iOS, it maps to `Documents`. On Web, it maps to `opfs://external`.                                                                                                                                                                                        | 0.0.1 |
| **`ExternalCache`**  | <code>'EXTERNAL_CACHE'</code>   | The external cache directory. On Android, it maps to `getExternalCacheDir()`. On iOS, it maps to `Library/Caches`. On Web, it maps to `opfs://external-cache`.                                                                                                                                                                           | 0.0.1 |
| **`Library`**        | <code>'LIBRARY'</code>          | The library directory. On Android, it maps to `getFilesDir()`. On iOS, it maps to `Library`. On Web, it maps to `opfs://library`.                                                                                                                                                                                                        | 0.0.1 |
| **`LibraryNoCloud`** | <code>'LIBRARY_NO_CLOUD'</code> | The library directory without cloud backup. On Android, it maps to `getFilesDir()`. On iOS, it maps to `Library/NoCloud`. On Web, it maps to `opfs://library-no-cloud`.                                                                                                                                                                  | 0.0.1 |
| **`Temporary`**      | <code>'TEMPORARY'</code>        | The temporary directory. On Android, it maps to `getCacheDir()`. On iOS, it maps to `tmp`. On Web, it maps to `opfs://temporary`.                                                                                                                                                                                                        | 0.0.1 |


#### OperationType

| Members      | Value                 | Description         | Since |
| ------------ | --------------------- | ------------------- | ----- |
| **`Copy`**   | <code>'COPY'</code>   | A copy operation.   | 0.0.1 |
| **`Delete`** | <code>'DELETE'</code> | A delete operation. | 0.0.1 |
| **`Move`**   | <code>'MOVE'</code>   | A move operation.   | 0.0.1 |

</docgen-api>

## Migration from `@capacitor/filesystem`

This plugin is a replacement for the sandbox operations of `@capacitor/filesystem`. Instead of passing `path` and `directory` to every method, construct the URI once with `getUri(...)` and pass it to any method:

```typescript
// Before (@capacitor/filesystem)
const result = await Filesystem.readFile({ path: 'text.txt', directory: Directory.Data });

// After (@capawesome-team/capacitor-file-manager)
const { uri } = await FileManager.getUri({ path: 'text.txt', directory: Directory.Data });
const result = await FileManager.readFile({ uri });
```

The `Directory` enum members have the same names and values as in `@capacitor/filesystem`.

| `@capacitor/filesystem`             | `@capawesome-team/capacitor-file-manager`                                                    |
| ----------------------------------- | -------------------------------------------------------------------------------------------- |
| `appendFile(...)`                   | `getUri(...)` + `appendFile(...)`                                                            |
| `checkPermissions()`                | Not needed. Persisted directories replace the legacy storage permissions.                    |
| `copy(...)`                         | `getUri(...)` + `copyFile(...)` or `copyDirectory(...)`                                      |
| `deleteFile(...)`                   | `getUri(...)` + `deleteFile(...)`                                                            |
| `downloadFile(...)`                 | [File Transfer](https://capawesome.io/docs/sdks/capacitor/file-transfer/) plugin             |
| `getUri(...)`                       | `getUri(...)`                                                                                |
| `mkdir(...)`                        | `getUri(...)` + `createDirectory(...)`                                                       |
| `readdir(...)`                      | `getUri(...)` + `readDirectory(...)`                                                         |
| `readFile(...)`                     | `getUri(...)` + `readFile(...)`                                                              |
| `readFileInChunks(...)`             | `readFileAsBlob(...)`                                                                        |
| `rename(...)`                       | `getUri(...)` + `moveFile(...)` or `moveDirectory(...)`                                      |
| `requestPermissions()`              | Not needed. Persisted directories replace the legacy storage permissions.                    |
| `rmdir(...)`                        | `getUri(...)` + `deleteDirectory(...)`                                                       |
| `stat(...)`                         | `getUri(...)` + `getMetadata(...)`                                                           |
| `writeFile(...)`                    | `getUri(...)` + `writeFile(...)`                                                             |
| `Directory.ExternalStorage`         | Persisted directories. Direct access to the shared storage is blocked on Android 11+ anyway. |
| `Encoding.ASCII` / `Encoding.UTF16` | Dropped. Use `Encoding.Utf8` or `Encoding.Base64`.                                           |

## Working with other plugins

- **Sandbox URIs** (`file://`): Fully compatible with all file-related Capawesome plugins (File Opener, File Compressor, Zip, ...) — pass the URI wherever a `path` is expected.
- **Persisted directory URIs** (`content://` on Android, security-scoped `file://` on iOS): Not accepted by plugins that only support plain file paths. To bridge, copy the file into a sandbox directory (for example `Directory.Cache`) with `copyFile(...)`, operate on it there, and optionally copy it back.
- **Web URIs** (`opfs://`): Internal to this plugin. Use `readFileAsBlob(...)` to hand the data to other web APIs.

## FAQ

### How is this plugin different from other similar plugins?

It works with the user's own storage, not just your app's sandbox: pick a folder once and keep read and write access to it across app launches on Android and iOS, then run manager-grade operations there — recursive copy, move and delete with progress, checksums, and directory sizing. It also reports device and app storage usage, can clear the cache, and streams large files into your web code without base64 overhead. On the web, files live in the Origin Private File System with real directories and random access instead of base64 blobs in IndexedDB. If you only need to read and write files inside your app's own sandbox, a simpler approach is enough; if you need durable access to user-picked folders and integrity-checked file operations, this plugin is built for it. Backed by dedicated support.

### How do I read a whole large file?

Use `readFileAsBlob(...)`, which streams the file into a `Blob` without base64 overhead. Do not read a large file by calling `readFile(...)` with an increasing `offset` in a loop — on some storage providers every call has to skip to the offset again, which makes the loop drastically slower with every iteration. The `offset` and `length` options are intended for targeted reads, like a file header or a byte range.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user pick the files and directories that this plugin operates on.
- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open files in the matching app on the device.
- [File Transfer](https://capawesome.io/docs/sdks/capacitor/file-transfer/): Download and upload files, even while the app is in the background.
- [Zip](https://capawesome.io/docs/sdks/capacitor/zip/): Compress and decompress files and directories.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-manager/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-manager/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-manager/LICENSE).
