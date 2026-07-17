# Capacitor File Opener Plugin

Capacitor plugin to open a file with the default application.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor File Opener plugin is one of the most complete file opening solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 📄 **Multiple file formats**: Open various file types with their default applications.
- 🌐 **Web support**: Open files using Blob objects on Web platform.
- 📱 **Native integration**: Uses system file associations on mobile platforms.
- 📁 **Flexible paths**: Support for file paths and content URIs on Android.
- 🔍 **MIME type detection**: Automatic MIME type detection or manual specification.
- 🤝 **Compatibility**: Works alongside the [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/), [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) and [Printer](https://capawesome.io/docs/sdks/capacitor/printer/) plugins.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The File Opener plugin is typically used whenever an app hands a file over to another application, for example:

- **Document viewing**: Open downloaded documents such as PDFs, invoices, or reports with the device's default viewer app.
- **Post-download handling**: Let users open a file right after your app has downloaded or generated it.
- **Media playback**: Open images, videos, or audio files with the app the user has associated with that file type.
- **Web downloads**: Open files from `Blob` objects on the Web without saving them first.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-file-opener` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-file-opener
npx cap sync
```

### Android

You need to specify the directories that contain the files you want to open. To do this, create a new file named `file_paths.xml` in the `res/xml` directory of your Android project (e.g. `android/app/src/main/res/xml/file_paths.xml`). Here is an example of the content of the file:

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <files-path name="files" path="." />
    <cache-path name="cache" path="." />
    <external-files-path name="external-files" path="." />
    <external-cache-path name="external-cache" path="." />
    <external-path name="external" path="." />
</paths>
```

More information can be found in the [Android documentation](https://developer.android.com/training/secure-file-sharing/setup-sharing#DefineMetaData).

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxDocumentFileVersion` version of `androidx.documentfile:documentfile` (default: `1.1.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                                            | iOS                                                                                                                                            |
| -------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/185747140-7e59ca70-96c9-4d67-a3b3-8fd9c7eb1546.gif" width="324" alt="Android Demo" /> | <img src="https://user-images.githubusercontent.com/13857929/185747133-62a2b5e5-ff6f-4b30-871c-4c3609db7829.gif" width="266" alt="iOS Demo" /> |

## Usage

The following example shows how to open a file with the default application.

### Open a file with the default application

On Android and iOS, pass the path of the file. On Android, both file paths and content URIs are supported. The mime type is determined automatically, but you can also specify it with the `mimeType` option. On Web, pass a `Blob` instance instead:

```typescript
import { FileOpener } from '@capawesome-team/capacitor-file-opener';

const open = async () => {
  await FileOpener.openFile({
    path: 'content://com.android.providers.downloads.documents/document/msf%3A1000000073',
  });
};
```

## API

<docgen-index>

* [`openFile(...)`](#openfile)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### openFile(...)

```typescript
openFile(options: OpenFileOptions) => Promise<void>
```

Open a file with the default application.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#openfileoptions">OpenFileOptions</a></code> |

**Since:** 0.0.1

--------------------


### Interfaces


#### OpenFileOptions

| Prop           | Type                | Description                                                                                                       | Since |
| -------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------- | ----- |
| **`blob`**     | <code>Blob</code>   | The blob instance of the file to open. Only available on Web.                                                     | 6.1.0 |
| **`path`**     | <code>string</code> | The path of the file. Only available on Android and iOS.                                                          | 0.0.1 |
| **`mimeType`** | <code>string</code> | The mime type of the file. If not specified, the mime type will be determined. Only available on Android and iOS. | 0.0.1 |

</docgen-api>

## FAQ

### Which file types can I open with this plugin?

The plugin can open various file types with their default applications. On Android and iOS, it uses the system file associations, so any file type for which the user has a suitable app installed can be opened. The mime type is determined automatically, but you can also specify it manually with the `mimeType` option.

### Why can't a file be opened on Android?

Make sure that the directory containing the file is declared in the `file_paths.xml` file of your Android project, as described in the [Installation](#installation) section. Without this configuration, the file can not be shared with the app that should open it.

### How do I open a file on the Web?

On the Web, pass a `Blob` instance to the `openFile(...)` method using the `blob` option. The `path` and `mimeType` options are only available on Android and iOS.

### Do I need to specify the mime type of the file?

No, if the `mimeType` option is not specified, the mime type will be determined automatically. You only need to set it if you want to override the detected type. This option is only available on Android and iOS.

### How is this plugin different from the File Picker plugin?

The [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugin lets the user select a file from the device and returns its path and metadata. The File Opener plugin does the opposite: it takes a file your app already has and opens it with the default application. Both plugins work well together, for example to pick a file and then open it.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Compressor](https://capawesome.io/docs/sdks/capacitor/file-compressor/): Compress files with support for image formats like PNG, JPEG, and WebP.
- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select a file, directory, image, or video from the device.
- [Printer](https://capawesome.io/docs/sdks/capacitor/printer/): Print files, HTML, PDFs, and web views on Android and iOS.
- [Share Target](https://capawesome.io/docs/sdks/capacitor/share-target/): Receive text, links, and files shared from other apps.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-opener/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-opener/LICENSE).
