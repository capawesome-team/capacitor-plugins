# Capacitor File Transfer Plugin

Capacitor plugin for reliable background file uploads and downloads that survive the app being backgrounded. Task-based transfers with pause/resume, progress events, retries, and a persisted task store.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor File Transfer plugin brings robust, task-based file transfers to your Capacitor app. Here are some of the key features:

- ⬆️ **Uploads**: Binary (raw body, e.g. S3 presigned URLs) and `multipart/form-data` uploads.
- ⬇️ **Downloads**: Streamed downloads directly to a file on the device.
- 🔋 **Background Continuation**: Transfers keep running while the app is in the background — a background `URLSession` on iOS and a `dataSync` foreground service driven by our own OkHttp engine on Android.
- ⏯️ **Pause & Resume**: Downloads can be paused and resumed, even after the process was killed (via resume data on iOS and HTTP `Range` requests on Android).
- 📊 **Progress Events**: Throttled progress events with transferred and total bytes.
- 💾 **Persisted Tasks**: Transfers are persisted and can be queried after an app restart.
- 🔁 **Retries**: Automatic retries with backoff on network errors.
- 🔒 **Honest Semantics**: No fake pause and no hidden private APIs — documented behavior across foreground, background, and force-quit.
- 🤝 **Compatibility**: Works hand in hand with the [File Manager](https://capawesome.io/docs/sdks/capacitor/file-manager/), [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) and [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The File Transfer plugin is typically used whenever an app needs to move large files between the device and a server, for example:

- **Offline-first content**: Download large assets such as videos, maps, or course material for offline use and let the transfer continue while the user leaves the app.
- **Media uploads**: Upload photos and videos captured in your app to your own backend or directly to an S3 presigned URL, without blocking the user interface.
- **Resumable downloads**: Let users pause and resume large downloads, even after the app process was killed, and retry automatically after network errors.
- **Transfer manager UI**: Build a download or upload manager with live progress bars, using the throttled progress events and the persisted task store.
- **Wi-Fi-only transfers**: Restrict large transfers to unmetered networks so that users don't consume their mobile data plan.
- **Document synchronization**: Keep documents in sync with your backend in the background, then open or compress them with the [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) and [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/) plugins.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Demo

| Android                                                                                                                                                                          | iOS                                                                                                                                                                          |
| ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/file-transfer/assets/file-transfer-demo-android.mp4" width="324" controls></video> | <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/file-transfer/assets/file-transfer-demo-ios.mp4" width="266" controls></video> |

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-file-transfer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-file-transfer
npx cap sync
```

### Android

The plugin declares the `INTERNET`, `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_DATA_SYNC` and `POST_NOTIFICATIONS` permissions and the `dataSync` foreground service in its own manifest, so no manifest changes are required.

On Android 13 (API level 33) and higher, the progress notification is only shown if the user has granted the `POST_NOTIFICATIONS` runtime permission. Transfers still run without it — only the notification is hidden. Call `requestPermissions()` before starting a transfer if you want the notification to appear:

```typescript
const { notifications } = await FileTransfer.requestPermissions();
if (notifications !== 'granted') {
  // The transfer will run, but no progress notification is shown.
}
```

The foreground service notification itself is always shown while a transfer runs — Android requires a foreground service to have one. The per-transfer progress notification is opt-in and off by default:

```typescript
await FileTransfer.startDownload({
  url,
  path,
  androidNotification: { title: 'Downloading', progress: true },
});
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$okhttpVersion` version of `com.squareup.okhttp3:okhttp` (default: `4.12.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

The plugin uses a background `URLSession` so that transfers continue while your app is suspended. When a transfer finishes while the app is not running, iOS relaunches the app in the background and needs the transfer's completion handler to be forwarded to the plugin.

Add the following method to your app's `AppDelegate.swift` to forward the handler:

```swift
import Foundation

extension AppDelegate {
    func application(
        _ application: UIApplication,
        handleEventsForBackgroundURLSession identifier: String,
        completionHandler: @escaping () -> Void
    ) {
        NotificationCenter.default.post(
            name: Notification.Name("io.capawesome.capacitorjs.plugins.filetransfer.handleEventsForBackgroundURLSession"),
            object: completionHandler
        )
    }
}
```

Without this hook, background transfers still complete, but iOS may not be able to wake your app to deliver the final events promptly.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to download and upload files, listen for transfer events, pause, resume, and cancel transfers, and retrieve known transfers.

### Download a file

Start a background download with `startDownload(...)`. The method resolves immediately with the identifier of the transfer, while the download itself continues in the background:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const startDownload = async () => {
  const { id } = await FileTransfer.startDownload({
    url: 'https://example.com/file.zip',
    path: '/path/to/destination/file.zip',
    headers: {
      Authorization: 'Bearer <token>',
    },
    network: 'unmetered',
    maxRetries: 3,
    androidNotification: {
      title: 'Downloading file',
      text: 'The file is being downloaded.',
    },
  });
  return id;
};
```

### Upload a file

Upload a file from the device with `startUpload(...)`. By default, the file is sent as a `multipart/form-data` request, where `fileField` defines the name of the form field that contains the file and `formFields` adds further fields to the request:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const startUpload = async () => {
  const { id } = await FileTransfer.startUpload({
    url: 'https://example.com/upload',
    path: '/path/to/source/file.jpg',
    uploadType: 'multipart',
    fileField: 'file',
    mimeType: 'image/jpeg',
    formFields: {
      albumId: '42',
    },
  });
  return id;
};
```

### Upload to an S3 presigned URL

Presigned URLs expect the raw file as the request body. Use `uploadType: 'binary'` and the `PUT` method:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const uploadToPresignedUrl = async () => {
  const { id } = await FileTransfer.startUpload({
    url: 'https://example.com/presigned-url',
    path: '/path/to/source/file.jpg',
    method: 'PUT',
    uploadType: 'binary',
    mimeType: 'image/jpeg',
  });
  return id;
};
```

### Listen for transfer events

Transfers report their state through events: `transferProgress` is emitted repeatedly while a transfer is running, `transferCompleted` when it succeeds, and `transferFailed` when it fails. Completed and failed events that occur while no listener is registered are retained and delivered as soon as a listener is added:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const addTransferListeners = async () => {
  await FileTransfer.addListener('transferProgress', event => {
    console.log(`Transfer ${event.id}: ${event.bytes}/${event.totalBytes}`);
  });
  await FileTransfer.addListener('transferCompleted', event => {
    console.log(`Transfer ${event.id} completed: `, event.path);
  });
  await FileTransfer.addListener('transferFailed', event => {
    console.error(`Transfer ${event.id} failed: `, event.errorCode, event.message);
  });
};
```

### Pause and resume a transfer

Pause a running download with `pauseTransferById(...)` and continue it later with `resumeTransferById(...)`, using the identifier returned by `startDownload(...)`. Paused downloads survive process death and can therefore also be resumed after an app restart. Uploads cannot be paused in this version:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const pauseTransferById = async (id: string) => {
  await FileTransfer.pauseTransferById({ id });
};

const resumeTransferById = async (id: string) => {
  await FileTransfer.resumeTransferById({ id });
};
```

### Cancel a transfer

Cancel a running or paused transfer with `cancelTransferById(...)`. Any partially transferred data is deleted, so the transfer cannot be resumed afterwards:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const cancelTransferById = async (id: string) => {
  await FileTransfer.cancelTransferById({ id });
};
```

### Retrieve transfers

Get a single transfer with `getTransferById(...)` or all known transfers with `getTransfers()`. Transfers are persisted, so both methods also return transfers that were restored after an app restart, for example to rebuild a transfer list on app start:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const getTransferById = async (id: string) => {
  const { transfer } = await FileTransfer.getTransferById({ id });
  return transfer;
};

const getRunningTransfers = async () => {
  const { transfers } = await FileTransfer.getTransfers();
  return transfers.filter(transfer => transfer.state === 'running');
};
```

### Remove all listeners

Remove all listeners that your app has registered, for example when a view is destroyed:

```typescript
import { FileTransfer } from '@capawesome-team/capacitor-file-transfer';

const removeAllListeners = async () => {
  await FileTransfer.removeAllListeners();
};
```

## API

<docgen-index>

* [`cancelTransferById(...)`](#canceltransferbyid)
* [`checkPermissions()`](#checkpermissions)
* [`getTransferById(...)`](#gettransferbyid)
* [`getTransfers()`](#gettransfers)
* [`pauseTransferById(...)`](#pausetransferbyid)
* [`requestPermissions()`](#requestpermissions)
* [`resumeTransferById(...)`](#resumetransferbyid)
* [`startDownload(...)`](#startdownload)
* [`startUpload(...)`](#startupload)
* [`addListener('transferCompleted', ...)`](#addlistenertransfercompleted-)
* [`addListener('transferFailed', ...)`](#addlistenertransferfailed-)
* [`addListener('transferProgress', ...)`](#addlistenertransferprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### cancelTransferById(...)

```typescript
cancelTransferById(options: CancelTransferByIdOptions) => Promise<void>
```

Cancel a running or paused transfer and delete any partially transferred data.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#canceltransferbyidoptions">CancelTransferByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the current permission status.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getTransferById(...)

```typescript
getTransferById(options: GetTransferByIdOptions) => Promise<GetTransferByIdResult>
```

Get a single transfer by its identifier.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#gettransferbyidoptions">GetTransferByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#gettransferbyidresult">GetTransferByIdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getTransfers()

```typescript
getTransfers() => Promise<GetTransfersResult>
```

Get all known transfers, including transfers that were restored after an app restart.

**Returns:** <code>Promise&lt;<a href="#gettransfersresult">GetTransfersResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### pauseTransferById(...)

```typescript
pauseTransferById(options: PauseTransferByIdOptions) => Promise<void>
```

Pause a running transfer.

Downloads are paused so that they survive process death and can be resumed later.
Uploads and downloads that are not resumable cannot be paused and are rejected
with the `TRANSFER_NOT_PAUSABLE` error code.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#pausetransferbyidoptions">PauseTransferByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to post the progress notification.

On **Android 12 and older**, on **iOS** and on the **web**, this resolves
without prompting since no notification permission is required.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### resumeTransferById(...)

```typescript
resumeTransferById(options: ResumeTransferByIdOptions) => Promise<void>
```

Resume a paused transfer.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#resumetransferbyidoptions">ResumeTransferByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### startDownload(...)

```typescript
startDownload(options: StartDownloadOptions) => Promise<StartDownloadResult>
```

Start a background download and immediately return its identifier.

The download continues while the app is in the background.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#startdownloadoptions">StartDownloadOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#startdownloadresult">StartDownloadResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### startUpload(...)

```typescript
startUpload(options: StartUploadOptions) => Promise<StartUploadResult>
```

Start a background upload and immediately return its identifier.

The upload continues while the app is in the background.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#startuploadoptions">StartUploadOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#startuploadresult">StartUploadResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('transferCompleted', ...)

```typescript
addListener(eventName: 'transferCompleted', listenerFunc: (event: TransferCompletedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a transfer completes successfully.

Completed events that occur while no listener is registered are retained
and delivered once a listener is added.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'transferCompleted'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#transfercompletedevent">TransferCompletedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('transferFailed', ...)

```typescript
addListener(eventName: 'transferFailed', listenerFunc: (event: TransferFailedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a transfer fails.

Failed events that occur while no listener is registered are retained
and delivered once a listener is added.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'transferFailed'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#transferfailedevent">TransferFailedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('transferProgress', ...)

```typescript
addListener(eventName: 'transferProgress', listenerFunc: (event: TransferProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called repeatedly while a transfer is running to report its progress.

The event is throttled to roughly one emission every 100 milliseconds per transfer.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'transferProgress'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#transferprogressevent">TransferProgressEvent</a>) =&gt; void</code> |

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


#### CancelTransferByIdOptions

| Prop     | Type                | Description                               | Since |
| -------- | ------------------- | ----------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the transfer to cancel. | 0.0.1 |


#### PermissionStatus

| Prop                | Type                                                        | Description                                                                                                                                                                                                                                                                  | Since |
| ------------------- | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`notifications`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for posting notifications. The progress notification is only shown if this is `granted`. Transfers still run without it. On **Android 12 and older**, on **iOS** and on the **web**, this is always `granted` since no notification permission is required. | 0.0.1 |


#### GetTransferByIdResult

| Prop           | Type                                          | Description                             | Since |
| -------------- | --------------------------------------------- | --------------------------------------- | ----- |
| **`transfer`** | <code><a href="#transfer">Transfer</a></code> | The transfer with the given identifier. | 0.0.1 |


#### Transfer

| Prop             | Type                                                    | Description                                                  | Since |
| ---------------- | ------------------------------------------------------- | ------------------------------------------------------------ | ----- |
| **`id`**         | <code>string</code>                                     | The identifier of the transfer.                              | 0.0.1 |
| **`type`**       | <code><a href="#transfertype">TransferType</a></code>   | The direction of the transfer.                               | 0.0.1 |
| **`state`**      | <code><a href="#transferstate">TransferState</a></code> | The current state of the transfer.                           | 0.0.1 |
| **`url`**        | <code>string</code>                                     | The URL of the transfer.                                     | 0.0.1 |
| **`path`**       | <code>string</code>                                     | The path on the device of the transfer.                      | 0.0.1 |
| **`bytes`**      | <code>number</code>                                     | The number of bytes that have been transferred so far.       | 0.0.1 |
| **`totalBytes`** | <code>number \| null</code>                             | The total number of bytes to transfer, or `null` if unknown. | 0.0.1 |


#### GetTransferByIdOptions

| Prop     | Type                | Description                            | Since |
| -------- | ------------------- | -------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the transfer to get. | 0.0.1 |


#### GetTransfersResult

| Prop            | Type                    | Description                      | Since |
| --------------- | ----------------------- | -------------------------------- | ----- |
| **`transfers`** | <code>Transfer[]</code> | The list of all known transfers. | 0.0.1 |


#### PauseTransferByIdOptions

| Prop     | Type                | Description                              | Since |
| -------- | ------------------- | ---------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the transfer to pause. | 0.0.1 |


#### ResumeTransferByIdOptions

| Prop     | Type                | Description                               | Since |
| -------- | ------------------- | ----------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the transfer to resume. | 0.0.1 |


#### StartDownloadResult

| Prop     | Type                | Description                             | Since |
| -------- | ------------------- | --------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the started transfer. | 0.0.1 |


#### StartDownloadOptions

| Prop                      | Type                                                                                | Description                                                                                                                                                                                                                                                    | Default            | Since |
| ------------------------- | ----------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`androidNotification`** | <code><a href="#transfernotificationoptions">TransferNotificationOptions</a></code> | The configuration of the foreground service notification. Only available on Android.                                                                                                                                                                           |                    | 0.0.1 |
| **`url`**                 | <code>string</code>                                                                 | The URL to download the file from.                                                                                                                                                                                                                             |                    | 0.0.1 |
| **`path`**                | <code>string</code>                                                                 | The path on the device where the downloaded file should be stored.                                                                                                                                                                                             |                    | 0.0.1 |
| **`headers`**             | <code>Record&lt;string, string&gt;</code>                                           | The HTTP headers to send with the request.                                                                                                                                                                                                                     |                    | 0.0.1 |
| **`method`**              | <code>'GET' \| 'POST'</code>                                                        | The HTTP method to use for the request.                                                                                                                                                                                                                        | <code>'GET'</code> | 0.0.1 |
| **`network`**             | <code><a href="#transfernetwork">TransferNetwork</a></code>                         | The network type that is required for the transfer to run. Use `'unmetered'` to only run the transfer on unmetered networks (e.g. Wi-Fi). The transfer waits until such a network is available. On **Android**, it stays in the `pending` state while waiting. | <code>'any'</code> | 0.0.1 |
| **`resumable`**           | <code>boolean</code>                                                                | Whether the download may be resumed after a pause or an interruption. Requires the server to support the HTTP `Range` header.                                                                                                                                  | <code>true</code>  | 0.0.1 |
| **`maxRetries`**          | <code>number</code>                                                                 | The maximum number of times the transfer is retried after a network error.                                                                                                                                                                                     | <code>0</code>     | 0.0.1 |


#### TransferNotificationOptions

The configuration of the Android foreground service notification.

Only available on Android.

| Prop              | Type                 | Description                                                                                                                                                                                                                                          | Default                      | Since |
| ----------------- | -------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------- | ----- |
| **`title`**       | <code>string</code>  | The title of the notification.                                                                                                                                                                                                                       |                              | 0.0.1 |
| **`progress`**    | <code>boolean</code> | Whether to show a separate notification with a progress bar for this transfer. The foreground service notification is always shown while a transfer runs, because Android requires it. This option only adds the per-transfer progress notification. | <code>false</code>           | 0.0.1 |
| **`text`**        | <code>string</code>  | The text of the notification.                                                                                                                                                                                                                        |                              | 0.0.1 |
| **`channelName`** | <code>string</code>  | The name of the notification channel.                                                                                                                                                                                                                | <code>'File Transfer'</code> | 0.0.1 |


#### StartUploadResult

| Prop     | Type                | Description                             | Since |
| -------- | ------------------- | --------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the started transfer. | 0.0.1 |


#### StartUploadOptions

| Prop                      | Type                                                                                | Description                                                                                                                                                                                                                                                    | Default                  | Since |
| ------------------------- | ----------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ | ----- |
| **`androidNotification`** | <code><a href="#transfernotificationoptions">TransferNotificationOptions</a></code> | The configuration of the foreground service notification. Only available on Android.                                                                                                                                                                           |                          | 0.0.1 |
| **`url`**                 | <code>string</code>                                                                 | The URL to upload the file to.                                                                                                                                                                                                                                 |                          | 0.0.1 |
| **`path`**                | <code>string</code>                                                                 | The path on the device of the file to upload.                                                                                                                                                                                                                  |                          | 0.0.1 |
| **`method`**              | <code>'POST' \| 'PUT'</code>                                                        | The HTTP method to use for the request.                                                                                                                                                                                                                        | <code>'POST'</code>      | 0.0.1 |
| **`uploadType`**          | <code><a href="#transferuploadtype">TransferUploadType</a></code>                   | The type of upload to perform. Use `'binary'` to send the raw file as the request body (e.g. for S3 presigned URLs) or `'multipart'` to send a `multipart/form-data` request.                                                                                  | <code>'multipart'</code> | 0.0.1 |
| **`fileField`**           | <code>string</code>                                                                 | The name of the form field that contains the file. Only used when `uploadType` is `'multipart'`.                                                                                                                                                               | <code>'file'</code>      | 0.0.1 |
| **`mimeType`**            | <code>string</code>                                                                 | The MIME type of the file to upload.                                                                                                                                                                                                                           |                          | 0.0.1 |
| **`formFields`**          | <code>Record&lt;string, string&gt;</code>                                           | The additional form fields to send with the request. Only used when `uploadType` is `'multipart'`.                                                                                                                                                             |                          | 0.0.1 |
| **`headers`**             | <code>Record&lt;string, string&gt;</code>                                           | The HTTP headers to send with the request.                                                                                                                                                                                                                     |                          | 0.0.1 |
| **`network`**             | <code><a href="#transfernetwork">TransferNetwork</a></code>                         | The network type that is required for the transfer to run. Use `'unmetered'` to only run the transfer on unmetered networks (e.g. Wi-Fi). The transfer waits until such a network is available. On **Android**, it stays in the `pending` state while waiting. | <code>'any'</code>       | 0.0.1 |
| **`maxRetries`**          | <code>number</code>                                                                 | The maximum number of times the transfer is retried after a network error.                                                                                                                                                                                     | <code>0</code>           | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### TransferCompletedEvent

| Prop               | Type                        | Description                                                                       | Since |
| ------------------ | --------------------------- | --------------------------------------------------------------------------------- | ----- |
| **`id`**           | <code>string</code>         | The identifier of the transfer.                                                   | 0.0.1 |
| **`path`**         | <code>string \| null</code> | The path on the device of the transferred file, or `null` for uploads.            | 0.0.1 |
| **`responseCode`** | <code>number \| null</code> | The HTTP status code of the response, or `null` if not available.                 | 0.0.1 |
| **`responseBody`** | <code>string \| null</code> | The body of the response, or `null` if not available. Only available for uploads. | 0.0.1 |


#### TransferFailedEvent

| Prop               | Type                        | Description                                                       | Since |
| ------------------ | --------------------------- | ----------------------------------------------------------------- | ----- |
| **`id`**           | <code>string</code>         | The identifier of the transfer.                                   | 0.0.1 |
| **`errorCode`**    | <code>string</code>         | The error code of the failure.                                    | 0.0.1 |
| **`message`**      | <code>string</code>         | The error message of the failure.                                 | 0.0.1 |
| **`responseCode`** | <code>number \| null</code> | The HTTP status code of the response, or `null` if not available. | 0.0.1 |


#### TransferProgressEvent

| Prop             | Type                        | Description                                                                        | Since |
| ---------------- | --------------------------- | ---------------------------------------------------------------------------------- | ----- |
| **`id`**         | <code>string</code>         | The identifier of the transfer.                                                    | 0.0.1 |
| **`bytes`**      | <code>number</code>         | The number of bytes that have been transferred so far.                             | 0.0.1 |
| **`totalBytes`** | <code>number \| null</code> | The total number of bytes to transfer, or `null` if unknown.                       | 0.0.1 |
| **`progress`**   | <code>number \| null</code> | The progress of the transfer as a value between `0` and `1`, or `null` if unknown. | 0.0.1 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### TransferType

The direction of a transfer.

<code>'download' | 'upload'</code>


#### TransferState

The current state of a transfer.

<code>'pending' | 'running' | 'paused' | 'completed' | 'failed' | 'canceled'</code>


#### TransferNetwork

The network type that is required for a transfer to run.

<code>'any' | 'unmetered'</code>


#### TransferUploadType

The type of upload to perform.

<code>'binary' | 'multipart'</code>

</docgen-api>

## Migration from `@capacitor/file-transfer`

| `@capacitor/file-transfer`     | `@capawesome-team/capacitor-file-transfer`                                         |
| ------------------------------ | ---------------------------------------------------------------------------------- |
| `downloadFile({ url, path })`  | `startDownload({ url, path })` → returns `{ id }` and continues in the background  |
| `uploadFile({ url, path })`    | `startUpload({ url, path })` → returns `{ id }` and continues in the background    |
| `addListener('progress', ...)` | `addListener('transferProgress', ...)`                                             |
| _No equivalent_                | `pauseTransferById`, `resumeTransferById`, `cancelTransferById`, `getTransferById`, `getTransfers` |
| _No equivalent_                | `transferCompleted` and `transferFailed` events                                    |

Transfers are asynchronous: `startDownload(...)` and `startUpload(...)` resolve immediately with a transfer `id`, and you observe completion through the `transferCompleted` and `transferFailed` events.

## FAQ

### Do transfers continue when the app is in the background?

Yes. On Android, transfers keep running in a `dataSync` foreground service, and on iOS, in a background `URLSession`. The following table summarizes what happens to a transfer in each app state:

| App State                     | Android                                                 | iOS                                            |
| ----------------------------- | ------------------------------------------------------- | ---------------------------------------------- |
| Foreground                    | Runs.                                                   | Runs.                                          |
| Backgrounded                  | Runs (via the `dataSync` foreground service).           | Runs (via the background `URLSession`).        |
| Killed by the OS (low memory) | Interrupted; restored as `failed`, downloads resumable. | Continued by the OS and delivered on relaunch. |
| Force-quit by the user        | Interrupted; restored as `failed`, downloads resumable. | Canceled by the OS (documented OS behavior).   |

Resuming an interrupted download requires the server to support the HTTP `Range` header.

### Can I pause an upload?

Not in this version. Plain HTTP uploads have no standard resume mechanism, so `pauseTransferById(...)` rejects for uploads instead of faking it with a suspend. Downloads, on the other hand, can be paused and resumed at any time, even after the app process was killed.

### Is this available on the web?

No. Transfers require a native background API, so `startDownload(...)` and `startUpload(...)` reject as unavailable on the web, as do `pauseTransferById(...)` and `resumeTransferById(...)`. The remaining methods are implemented and simply report that no transfer exists.

### How is this plugin different from the `@capacitor/file-transfer` plugin?

This plugin offers advanced features such as background continuation on Android and iOS, pause and resume that survives process death, a task-based API with a persisted task store, throttled progress events, automatic retries, and network constraints, and comes with priority support from the Capawesome Team. See the [Migration from `@capacitor/file-transfer`](#migration-from-capacitorfile-transfer) section for a side-by-side overview of both APIs.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/): Compress files before uploading them.
- [File Manager](https://capawesome.io/docs/sdks/capacitor/file-manager/): Access and manage the user's storage natively.
- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open the transferred files in another app.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-transfer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-transfer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-transfer/LICENSE).
