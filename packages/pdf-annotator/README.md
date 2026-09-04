# Capacitor PDF Annotator Plugin

Capacitor plugin to annotate PDF documents in a fullscreen native viewer.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ✍️ **Annotate**: Draw, highlight and erase on top of any PDF document with the native markup tools.
- ↩️ **Undo & Redo**: Undo and redo changes while annotating.
- 🖊️ **Apple Pencil**: Full Apple Pencil support on iPad.
- 💾 **Non-destructive**: The original file is never modified. Annotations are saved to a copy.
- ✅ **Availability Check**: Check whether annotation is supported on the current device.
- 🔒 **App Store safe**: Uses only official platform APIs.
- 🤝 **Compatibility**: Works alongside the [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/), [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/) and [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The PDF Annotator plugin is typically used whenever users need to mark up a PDF document, for example:

- **Sheet music**: Let musicians write fingerings, breath marks or cues on their scores and highlight passages.
- **Document review**: Let users highlight sections and add handwritten remarks to contracts, reports or proofs.
- **Field work**: Let technicians draw on plans and inspection reports directly on site.
- **Education**: Let students and teachers annotate worksheets, handouts and exercises.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-pdf-annotator` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-pdf-annotator
npx cap sync
```

### Android

On Android, this plugin uses the [Jetpack PDF](https://developer.android.com/jetpack/androidx/releases/pdf) library, which provides a pen, a highlighter, an eraser and undo/redo. The annotation features of this library are currently in beta and marked as experimental by Google.

#### SDK Extension Level

The Jetpack PDF library requires your app to be compiled against Android SDK 36 with SDK extension level 19 or higher. Add the `compileSdkExtension` property to the `android` block of your app's `android/app/build.gradle` file:

```groovy
android {
    compileSdk = rootProject.ext.compileSdkVersion
    compileSdkExtension = 19
    // ...
}
```

This requires the `Android SDK Platform 36-ext19` package. If it is not installed automatically during the build, install it via the SDK Manager of Android Studio (enable **Show Package Details** in the **SDK Platforms** tab) or by running the following command:

```bash
sdkmanager "platforms;android-36-ext19"
```

If your app is already compiled against Android SDK 37 or higher (requires Android Gradle Plugin 9.1 or higher), no extension level needs to be set.

#### Device Support

Annotating PDF documents requires Android 11 (API level 30) or higher and a PDF system module that supports annotations (SDK extension level 18 or higher). This module ships with Android 16 QPR2 and is delivered to older devices via Google Play system updates. Always check the availability with the `isAvailable()` method before calling `open(...)`.

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default version of the dependencies:

- `$androidxPdfVersion` version of `androidx.pdf:pdf-ink` and `androidx.pdf:pdf-viewer-fragment` (default: `1.0.0-beta01`)
- `$androidxMaterialVersion` version of `com.google.android.material:material` (default: `1.13.0`)

### iOS

On iOS, this plugin uses the [Quick Look](https://developer.apple.com/documentation/quicklook) framework, which provides the system markup tools (pen, highlighter, pencil, eraser, shapes, text, signature and undo/redo) including Apple Pencil support. No additional configuration is required.

Be aware that the markup tools are not available on all simulator versions. Test the plugin on a real device.

### Web

This plugin is not available on the web.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check the availability, open a PDF document for annotation and keep the annotated file.

The plugin only supports local files. Remote URLs must be downloaded first, for example with the `downloadFile(...)` method of the [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin.

### Check the availability

Check whether PDF annotation is supported on the current device before showing an annotation button to the user:

```typescript
import { PdfAnnotator } from '@capawesome/capacitor-pdf-annotator';

const isAvailable = async () => {
  const { available } = await PdfAnnotator.isAvailable();
  return available;
};
```

### Annotate a PDF document

Open a local PDF file in a fullscreen native viewer that lets the user annotate it. The promise resolves with the path of the annotated copy when the user saves the annotations and closes the viewer. If the user closes the viewer without saving, the promise is rejected with the `CANCELED` error code. Only available on Android and iOS:

```typescript
import { ErrorCode, PdfAnnotator } from '@capawesome/capacitor-pdf-annotator';

const open = async () => {
  try {
    const { path } = await PdfAnnotator.open({
      path: 'file:///path/to/document.pdf',
    });
    console.log('Annotated file: ', path);
  } catch (error) {
    if (error.code === ErrorCode.Canceled) {
      console.log('The user closed the viewer without saving.');
    }
  }
};
```

### Keep the annotated file

The annotated file is stored in the cache directory and deleted on the next app launch. Move it to a permanent location with the [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin if you want to keep it:

```typescript
import { Directory, Filesystem } from '@capacitor/filesystem';
import { PdfAnnotator } from '@capawesome/capacitor-pdf-annotator';

const annotateAndKeep = async () => {
  const { path } = await PdfAnnotator.open({
    path: 'file:///path/to/document.pdf',
  });
  await Filesystem.copy({
    from: path,
    to: 'annotated.pdf',
    toDirectory: Directory.Documents,
  });
};
```

## API

<docgen-index>

* [`isAvailable()`](#isavailable)
* [`open(...)`](#open)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether PDF annotation is available on this device.

On **Android**, this resolves to `true` if the device runs Android 11 (API level 30)
or higher and its PDF system module supports annotations (SDK extension level 18 or higher).
On **iOS**, this always resolves to `true`.
On **Web**, this always resolves to `false`.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### open(...)

```typescript
open(options: OpenOptions) => Promise<OpenResult>
```

Open a PDF file in a fullscreen native viewer that lets the user annotate it.

The promise resolves with the path of the annotated copy when the user
saves the annotations and closes the viewer. If the user closes the viewer
without saving, the promise is rejected with the `CANCELED` error code.
The original file is never modified.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#openoptions">OpenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#openresult">OpenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### IsAvailableResult

| Prop            | Type                 | Description                                                | Since |
| --------------- | -------------------- | ---------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not PDF annotation is available on this device. | 0.1.0 |


#### OpenResult

| Prop       | Type                | Description                                                                                                                                                                           | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path of the annotated copy of the PDF file. The file is stored in the cache directory and deleted on the next app launch. Move it to a permanent location if you want to keep it. | 0.1.0 |


#### OpenOptions

| Prop       | Type                | Description                                                                                                                                                                          | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`path`** | <code>string</code> | The path of the local PDF file to annotate. Remote URLs are not supported. Download the file first, for example to the cache directory, and pass the local file path to this method. | 0.1.0 |

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It lets users draw, highlight and erase on top of a PDF document with the native markup tools of each platform, backed by Quick Look on iOS and the Jetpack PDF library on Android for a genuinely native annotation experience with undo/redo and Apple Pencil support. The original file is never touched: the annotated document is handed back as a separate file through a fully typed API that uses only official platform APIs. Actively maintained against the latest Capacitor version, it also pairs naturally with the PDF Viewer, PDF Generator and File Picker plugins.

### How is this plugin different from the PDF Viewer plugin?

The [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/) plugin is a lightweight, read-only viewer with paging, zoom and password support. The PDF Annotator plugin adds the native markup tools of each platform and returns the annotated document. Use the PDF Viewer plugin when users only need to read a document, and the PDF Annotator plugin when they need to mark it up.

### Why is annotation not available on my Android device?

On Android, annotating PDF documents requires Android 11 (API level 30) or higher and a PDF system module that supports annotations (SDK extension level 18 or higher). This module ships with Android 16 QPR2 and is delivered to older devices via Google Play system updates. Use the `isAvailable()` method to check the availability and hide the annotation feature on unsupported devices.

### Why do I need to set the SDK extension level on Android?

The Jetpack PDF library used by this plugin declares that depending apps must be compiled against an SDK with extension level 19 or higher. This is enforced by the Android Gradle Plugin, so the build fails without it. Android SDK 36 provides this extension level via the `android-36-ext19` platform (see [SDK Extension Level](#sdk-extension-level)). Changing the compile SDK does not change the runtime behavior of your app, which is controlled by the target SDK version.

### Can I customize the toolbar?

No, the plugin uses the system markup user interface of each platform, which can not be customized.

### Can I annotate a PDF from a remote URL?

No, the plugin only supports local files. Download the file first, for example with the `downloadFile(...)` method of the official [Filesystem](https://capacitorjs.com/docs/apis/filesystem) plugin, and then pass the local file path to the `open(...)` method.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open a file with the default application instead of an in-app viewer.
- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select a PDF file from the device's file system.
- [PDF Generator](https://capawesome.io/docs/sdks/capacitor/pdf-generator/): Generate paginated PDF files from HTML content or URLs.
- [PDF Viewer](https://capawesome.io/docs/sdks/capacitor/pdf-viewer/): Display PDF documents in a fullscreen native viewer.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-annotator/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pdf-annotator/LICENSE).
