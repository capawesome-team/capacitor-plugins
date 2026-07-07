# Capacitor PDF Viewer Plugin

Capacitor plugin to display PDF documents in a fullscreen native viewer.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📄 **Fullscreen Viewer**: Display PDF documents in a fullscreen native viewer with a toolbar.
- 📖 **Paging**: Scroll through pages and jump to an initial page.
- 🔍 **Zoom**: Pinch to zoom in and out.
- 🔑 **Password Protection**: Open password-protected PDF documents.
- 🔔 **Events**: Listen for page changes and the closing of the viewer.
- 🤝 **Compatibility**: Works alongside the [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) and [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugins.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-pdf-viewer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-pdf-viewer
npx cap sync
```

### Android

On Android, this plugin uses the [android-pdf-viewer](https://github.com/mhiew/AndroidPdfViewer) library, which renders PDF documents with [Pdfium](https://pdfium.googlesource.com/pdfium/). Be aware that the library bundles the Pdfium native libraries, which add about 10 to 16 MB (uncompressed, across all ABIs) to your app. If you publish your app as an [Android App Bundle](https://developer.android.com/guide/app-bundle), each device only downloads the native libraries for its own ABI, which significantly reduces the download size. Also note that the viewer does not support text selection on Android.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidPdfViewerVersion` version of `com.github.mhiew:android-pdf-viewer` (default: `3.2.0-beta.3`)

### iOS

On iOS, this plugin uses the [PDFKit](https://developer.apple.com/documentation/pdfkit) framework. No additional configuration is required.

### Web

This plugin is not available on the web. Browsers ship with a built-in PDF viewer, so you can simply render a PDF document using an `<iframe>` or `<object>` element.

## Configuration

No configuration required for this plugin.

## Usage

The plugin only supports local files. Remote URLs must be downloaded first, for example with the `downloadFile(...)` method of the [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin.

```typescript
import { PdfViewer } from '@capawesome/capacitor-pdf-viewer';

const open = async () => {
  await PdfViewer.open({
    path: 'file:///path/to/document.pdf',
    title: 'Invoice',
    page: 1,
  });
};

const openWithPassword = async () => {
  await PdfViewer.open({
    path: 'file:///path/to/document.pdf',
    password: 'secret',
  });
};

const close = async () => {
  await PdfViewer.close();
};

const addListeners = async () => {
  await PdfViewer.addListener('closed', () => {
    console.log('Viewer closed');
  });
  await PdfViewer.addListener('pageChange', event => {
    console.log('Current page:', event.page);
  });
};
```

## API

<docgen-index>

* [`close()`](#close)
* [`open(...)`](#open)
* [`addListener('closed', ...)`](#addlistenerclosed-)
* [`addListener('pageChange', ...)`](#addlistenerpagechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### close()

```typescript
close() => Promise<void>
```

Close the currently open viewer.

If no viewer is open, this method does nothing.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### open(...)

```typescript
open(options: OpenOptions) => Promise<void>
```

Open a PDF file in a fullscreen native viewer.

If a viewer is already open, it is closed before the new one is
presented.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#openoptions">OpenOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('closed', ...)

```typescript
addListener(eventName: 'closed', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the viewer is closed.

Only available on Android and iOS.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'closed'</code>      |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('pageChange', ...)

```typescript
addListener(eventName: 'pageChange', listenerFunc: (event: PageChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the current page of the viewer changes.

Only available on Android and iOS.

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'pageChange'</code>                                                       |
| **`listenerFunc`** | <code>(event: <a href="#pagechangeevent">PageChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### OpenOptions

| Prop           | Type                | Description                                                                                                                                                                         | Default                                     | Since |
| -------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`page`**     | <code>number</code> | The page (1-based) to display initially.                                                                                                                                            | <code>1</code>                              | 0.1.0 |
| **`password`** | <code>string</code> | The password to unlock the PDF file if it is password-protected.                                                                                                                    |                                             | 0.1.0 |
| **`path`**     | <code>string</code> | The path of the local PDF file to display. Remote URLs are not supported. Download the file first, for example to the cache directory, and pass the local file path to this method. |                                             | 0.1.0 |
| **`title`**    | <code>string</code> | The title to display in the toolbar of the viewer.                                                                                                                                  | <code>The file name of the PDF file.</code> | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### PageChangeEvent

| Prop       | Type                | Description                                     | Since |
| ---------- | ------------------- | ----------------------------------------------- | ----- |
| **`page`** | <code>number</code> | The page (1-based) that is currently displayed. | 0.1.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-viewer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-viewer/LICENSE).
