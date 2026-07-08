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

## Use Cases

The PDF Viewer plugin is typically used whenever an app needs to present a PDF document to the user, for example:

- **Invoices and receipts**: Display invoices or receipts that your app has generated or downloaded.
- **Reports and manuals**: Let users read reports, product manuals, or other multi-page documents with paging and zoom.
- **Confidential documents**: Open password-protected PDF documents such as payslips or bank statements.
- **Reading progress**: Use the `pageChange` event to remember the last read page and reopen the document there via the `page` option.

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

Import the plugin and call its methods:

```typescript
import { PdfViewer } from '@capawesome/capacitor-pdf-viewer';
```

### Open a PDF document

Open a local PDF file in a fullscreen native viewer. You can set a custom toolbar title and the page to display initially. Only available on Android and iOS:

```typescript
const open = async () => {
  await PdfViewer.open({
    path: 'file:///path/to/document.pdf',
    title: 'Invoice',
    page: 1,
  });
};
```

### Open a password-protected PDF document

Use the `password` option to unlock a password-protected PDF file:

```typescript
const openWithPassword = async () => {
  await PdfViewer.open({
    path: 'file:///path/to/document.pdf',
    password: 'secret',
  });
};
```

### Close the viewer

Close the currently open viewer from code. If no viewer is open, this method does nothing:

```typescript
const close = async () => {
  await PdfViewer.close();
};
```

### Listen for page changes and the viewer being closed

Use the `pageChange` and `closed` events to react to the user scrolling through the document or closing the viewer:

```typescript
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

## FAQ

### Can I display a PDF from a remote URL?

No, the plugin only supports local files. Download the file first, for example with the `downloadFile(...)` method of the official [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin, and then pass the local file path to the `open(...)` method.

### Why is the plugin not available on the Web?

Browsers ship with a built-in PDF viewer, so a plugin is not needed there. On the Web, you can simply render a PDF document using an `<iframe>` or `<object>` element.

### How do I open a password-protected PDF document?

Pass the password using the `password` option of the `open(...)` method. The viewer then unlocks and displays the document. See [Open a password-protected PDF document](#open-a-password-protected-pdf-document) for an example.

### How much does the plugin add to my Android app size?

On Android, the plugin uses the [android-pdf-viewer](https://github.com/mhiew/AndroidPdfViewer) library, which bundles the Pdfium native libraries. These add about 10 to 16 MB (uncompressed, across all ABIs) to your app. If you publish your app as an Android App Bundle, each device only downloads the native libraries for its own ABI, which significantly reduces the download size.

### Can I select text in the viewer on Android?

No, the viewer does not support text selection on Android. On iOS, the plugin uses the PDFKit framework, which provides the native viewer experience of the platform.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select a PDF file from the device's file system.
- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open a file with the default application instead of an in-app viewer.
- [PDF Generator](https://capawesome.io/docs/sdks/capacitor/pdf-generator/): Generate paginated PDF files from HTML content or URLs.
- [Printer](https://capawesome.io/docs/sdks/capacitor/printer/): Print PDF documents on Android and iOS.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-viewer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-viewer/LICENSE).
