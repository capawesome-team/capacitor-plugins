# Capacitor Document Scanner Plugin

Capacitor plugin for scanning documents with the native, full-screen scanner on Android and iOS. Returns perspective-corrected images and an optional combined PDF.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Document Scanner plugin brings the platform's own document scanning experience to your Capacitor app. Here are some of the key features:

- 📱 **Native UI**: Uses the platform's built-in scanner (Google's ML Kit flow on Android, VisionKit on iOS).
- ✂️ **Perspective Correction**: Automatically detects edges and corrects the perspective of captured pages.
- 📄 **Multi-Page**: Capture multiple pages in a single scan.
- 🖼️ **JPEG Output**: Returns the scanned pages as JPEG files in the cache directory.
- 📑 **PDF Output**: Optionally generates a combined PDF document from the scanned pages.
- 🔒 **Public APIs Only**: Built exclusively on public platform APIs, so it is safe for App Review and resilient to OS updates.
- 🤝 **Compatibility**: Works hand in hand with the [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/), [Printer](https://capawesome.io/docs/sdks/capacitor/printer/) and [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Document Scanner plugin is typically used whenever an app needs to digitize a physical document, for example:

- **Receipts and invoices**: Let users scan receipts for expense tracking.
- **Contracts and forms**: Capture signed documents as multi-page PDFs.
- **ID and cards**: Scan identity documents or business cards.
- **Notes and whiteboards**: Digitize handwritten notes or whiteboard content.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Demo

| Android                                                                                                                                                                                | iOS                                                                                                                                                                                |
| --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/document-scanner/assets/document-scanner-demo-android.mp4" width="324" controls></video> | <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/document-scanner/assets/document-scanner-demo-ios.mp4" width="266" controls></video> |

## Guides

- [Announcing the Capacitor Document Scanner Plugin](https://capawesome.io/blog/announcing-the-capacitor-document-scanner-plugin/): Scan documents with the native scanner UIs and get perspective-corrected pages and a combined PDF in two API calls.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-document-scanner` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-document-scanner
npx cap sync
```

### Android

The Google-provided scanner activity requests the camera permission itself, so no manifest changes are required.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$playServicesMlkitDocumentScannerVersion` version of `com.google.android.gms:play-services-mlkit-document-scanner` (default: `16.0.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

The scanner is delivered as an on-demand Google Play services module. It is **not** bundled with your app and is downloaded automatically the first time `scanDocument(...)` is called.

### iOS

Add the `NSCameraUsageDescription` key to the `Info.plist` file of your app to explain why your app needs access to the camera:

```xml
<key>NSCameraUsageDescription</key>
<string>The app needs access to the camera to scan documents.</string>
```

If the key is missing, `scanDocument(...)` rejects with a clear error message.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether document scanning is available, scan a document, and generate a PDF document from the scanned pages.

### Check the availability

Check whether document scanning is available on the device before starting a scan. Only available on Android and iOS:

```typescript
import { DocumentScanner } from '@capawesome-team/capacitor-document-scanner';

const isAvailable = async () => {
  const { available } = await DocumentScanner.isAvailable();
  return available;
};
```

### Scan a document

Open the native scanner user interface to capture one or more pages. On both platforms, the scanner detects the edges of the document, corrects the perspective, and offers cropping, rotation and a review step before the pages are returned as JPEG files in the cache directory. On Android, you can change the editing capabilities with the `androidScannerMode` option and allow the user to import an existing image with the `androidGalleryImportAllowed` option. On iOS, image cleaning is applied automatically by the system. The promise rejects with the `SCAN_CANCELED` error code if the user cancels the scan. Only available on Android and iOS:

```typescript
import { DocumentScanner } from '@capawesome-team/capacitor-document-scanner';

const scanDocument = async () => {
  const { scannedImages } = await DocumentScanner.scanDocument({
    imageQuality: 80,
    pageLimit: 5,
  });
  return scannedImages;
};
```

### Generate a PDF document

Set the `generatePdf` option to `true` to receive a combined PDF document of all scanned pages in the `pdf` property of the result. On Android, the PDF is generated by ML Kit. On iOS, it is composed from the scanned page images. Only available on Android and iOS:

```typescript
import { DocumentScanner } from '@capawesome-team/capacitor-document-scanner';

const scanDocumentAsPdf = async () => {
  const { pdf } = await DocumentScanner.scanDocument({
    generatePdf: true,
  });
  return pdf;
};
```

You can pass the resulting `pdf` path to the [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/) plugin to display it, to the [Printer](https://capawesome.io/docs/sdks/capacitor/printer/) plugin to print it, or to the [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) plugin to open it in another app.

## API

<docgen-index>

* [`isAvailable()`](#isavailable)
* [`scanDocument(...)`](#scandocument)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether document scanning is available on the device.

On **Android**, this resolves to `true` if Google Play services is
available. The scanner module is downloaded on demand the first time
`scanDocument(...)` is called.

On **iOS**, this resolves to `true` if the device supports document
scanning (i.e. it has a camera and runs a supported iOS version).

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### scanDocument(...)

```typescript
scanDocument(options?: ScanDocumentOptions | undefined) => Promise<ScanDocumentResult>
```

Open the native document scanner user interface.

The scanner captures one or more pages, applies perspective correction,
and returns the file paths of the scanned images and, optionally, a
combined PDF document.

The promise rejects with the `SCAN_CANCELED` error code if the user
cancels the scan.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#scandocumentoptions">ScanDocumentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#scandocumentresult">ScanDocumentResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### Interfaces


#### IsAvailableResult

| Prop            | Type                 | Description                                                  | Since |
| --------------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`available`** | <code>boolean</code> | Whether or not document scanning is available on the device. | 0.0.1 |


#### ScanDocumentResult

| Prop                | Type                        | Description                                                                                                       | Since |
| ------------------- | --------------------------- | ----------------------------------------------------------------------------------------------------------------- | ----- |
| **`pdf`**           | <code>string \| null</code> | The path of the generated PDF document. Only set if the `generatePdf` option is set to `true`. Otherwise, `null`. | 0.0.1 |
| **`scannedImages`** | <code>string[]</code>       | The paths of the scanned images as JPEG files in the cache directory.                                             | 0.0.1 |


#### ScanDocumentOptions

| Prop                              | Type                                                | Description                                                                                                                                                                                                                             | Default                       | Since |
| --------------------------------- | --------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- | ----- |
| **`androidGalleryImportAllowed`** | <code>boolean</code>                                | Whether or not the user is allowed to import an existing image from the gallery instead of capturing a new one with the camera. Only available on Android.                                                                              | <code>false</code>            | 0.0.1 |
| **`androidScannerMode`**          | <code><a href="#scannermode">ScannerMode</a></code> | The mode of the scanner user interface. Only available on Android.                                                                                                                                                                      | <code>ScannerMode.Full</code> | 0.0.1 |
| **`generatePdf`**                 | <code>boolean</code>                                | Whether or not to generate a combined PDF document from the scanned pages. The path of the generated PDF is returned in the `pdf` property of the result.                                                                               | <code>false</code>            | 0.0.1 |
| **`imageQuality`**                | <code>number</code>                                 | The JPEG quality of the scanned images. Must be a value between `0` (lowest quality) and `100` (highest quality).                                                                                                                       | <code>100</code>              | 0.0.1 |
| **`pageLimit`**                   | <code>number</code>                                 | The maximum number of pages that can be scanned. Must be greater than or equal to `1`. On **iOS**, the scanner cannot be limited to a specific number of pages. Instead, the returned pages are truncated to this value after scanning. | <code>10</code>               | 0.0.1 |


### Enums


#### ScannerMode

| Members              | Value                           | Description                                                                                                           | Since |
| -------------------- | ------------------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Base`**           | <code>'BASE'</code>             | Basic editing capabilities (crop and rotate).                                                                         | 0.0.1 |
| **`BaseWithFilter`** | <code>'BASE_WITH_FILTER'</code> | Adds image filters (grayscale and automatic image enhancement) on top of the `Base` mode.                             | 0.0.1 |
| **`Full`**           | <code>'FULL'</code>             | Adds ML-based image cleaning capabilities (removes stains, fingers, and shadows) on top of the `BaseWithFilter` mode. | 0.0.1 |

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It brings the platform's native scanning experience to both Android (ML Kit) and iOS (VisionKit) through a single, unified API, with automatic edge detection and perspective correction, multi-page capture, JPEG output and an optional combined PDF. Everything is fully typed, built exclusively on public platform APIs so it stays safe for App Review, actively maintained against the latest OS and Capacitor versions, and backed by dedicated support. If you only need to scan on a single platform, a simpler setup can be enough; if you want consistent cross-platform scanning with PDF output, this plugin is designed for that.

### Is document scanning available on the web?

No. The `isAvailable(...)` and `scanDocument(...)` methods are only available on Android and iOS. On the web, both methods reject with an unimplemented error.

### Why can't I limit the number of pages on iOS?

Apple's VisionKit does not expose a public API to stop the scanner after a specific number of pages. To avoid using private APIs (which would put your app at risk during App Review), the plugin truncates the returned pages to `pageLimit` after scanning instead.

### Where are the scanned files stored?

The scanned images and the generated PDF are stored in the app's cache directory. Stale files created by the plugin are cleaned up automatically when the plugin is loaded. Copy the files to a persistent location if you need to keep them.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open the scanned files in another app.
- [ML Kit Document Scanner](https://www.npmjs.com/package/@capacitor-mlkit/document-scanner): Scan documents with ML Kit Document Scanning on Android.
- [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/): Display PDF documents in a fullscreen native viewer.
- [Photo Manipulator](https://capawesome.io/docs/sdks/capacitor/photo-manipulator/): Apply additional edits such as brightness or contrast adjustments.
- [Printer](https://capawesome.io/docs/sdks/capacitor/printer/): Print the scanned images or the generated PDF.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/document-scanner/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/document-scanner/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/document-scanner/LICENSE).
