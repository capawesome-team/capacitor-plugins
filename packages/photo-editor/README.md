# @capawesome/capacitor-photo-editor

Capacitor plugin that allows the user to edit a photo.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Installation

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/photo-editor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/photo-editor/LICENSE).

## Credits

This plugin is based on the [Capacitor Photo Editor](https://github.com/capawesome-team/capacitor-photo-editor) plugin.
Thanks to everyone who contributed to the project!
