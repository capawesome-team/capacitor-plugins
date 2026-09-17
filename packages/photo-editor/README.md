# Capacitor Photo Editor Plugin

Capacitor plugin that allows the user to edit a photo.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Photo Editor plugin is typically used whenever an app wants to hand a photo over to an installed photo editing app on Android, for example:

- **Photo touch-ups before upload**: Let users edit a photo (e.g. in Google Photos) before it is uploaded to your server.
- **Screenshot annotation**: Let users mark up a screenshot before attaching it to a bug report or support ticket.
- **Profile picture adjustments**: Let users fine-tune a selected photo before it is saved as their profile picture.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-photo-editor` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-photo-editor
npx cap sync
```

### Android

You need to specify the directories that contain the photos you want to edit.
To specify the directories, start by creating the file `file_paths.xml` in the `res/xml/` subdirectory of your project (see [Android docs](https://developer.android.com/training/secure-file-sharing/setup-sharing#DefineMetaData)).  
This is an example:

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

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                                                |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ |
| <img src="https://github.com/capawesome-team/capacitor-plugins/assets/13857929/062c0623-ebb2-4782-a464-b4bebd2aa58d" width="324" alt="Android Demo" /> |

## Usage

The following example shows how to open a photo in an installed photo editing app.

### Edit a photo

Open a photo at a given path in an installed photo editing app (e.g. Google Photos). The user should overwrite the image when saving so that the path to the image is not lost. Only available on Android:

```typescript
import { PhotoEditor } from '@capawesome/capacitor-photo-editor';

const editPhoto = async () => {
  await PhotoEditor.editPhoto({ path: 'data/image.png' });
};
```

## API

<docgen-index>

* [`editPhoto(...)`](#editphoto)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### editPhoto(...)

```typescript
editPhoto(options: EditPhotoOptions) => Promise<void>
```

Edit a photo at a given path.

**Attention**: A suitable photo editing app must be installed (e.g. Google Photos)
and the user should overwrite the image when saving
so that the path to the image is not lost.

Only available on Android.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#editphotooptions">EditPhotoOptions</a></code> |

--------------------


### Interfaces


#### EditPhotoOptions

| Prop       | Type                | Description                   |
| ---------- | ------------------- | ----------------------------- |
| **`path`** | <code>string</code> | The path of the file to edit. |

</docgen-api>

## FAQ

### Which platforms are supported?

The `editPhoto(...)` method is only available on Android. On iOS and the Web, there is no comparable way to hand a photo over to an installed editing app. For headless image transforms that work across platforms, take a look at the [Photo Manipulator](https://capawesome.io/docs/sdks/capacitor/photo-manipulator/) plugin.

### Why does nothing happen when I call the `editPhoto` method?

A suitable photo editing app (e.g. Google Photos) must be installed on the device. The plugin hands the photo over to that app instead of providing its own editing user interface.

### Why can I no longer find my photo after editing it?

The user should overwrite the image when saving in the editing app so that the path to the image is not lost. If the editing app saves the result as a new file, the original path no longer points to the edited version.

### Why do I need a `file_paths.xml` file on Android?

The plugin shares the photo with the editing app, so you need to specify the directories that contain the photos you want to edit. Create the file `file_paths.xml` in the `res/xml/` subdirectory of your project as shown in the [Installation](#installation) section and the [Android docs](https://developer.android.com/training/secure-file-sharing/setup-sharing#DefineMetaData).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Exif](https://capawesome.io/docs/sdks/capacitor/exif/): Read, write and remove EXIF metadata from image files.
- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select the photo to edit from the gallery or file system.
- [Photo Manipulator](https://capawesome.io/docs/sdks/capacitor/photo-manipulator/): Headless image transforms like crop, resize, rotate, flip and format conversion, including HEIC to JPEG.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/photo-editor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/photo-editor/LICENSE).

## Credits

This plugin is based on the [Capacitor Photo Editor](https://github.com/capawesome-team/capacitor-photo-editor) plugin.
Thanks to everyone who contributed to the project!
