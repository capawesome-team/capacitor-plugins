# Capacitor Asset Manager Plugin

Capacitor plugin to access native asset files.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Asset Manager plugin is one of the most complete native asset access solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📂 **Asset copying**: Copy files or directories from app bundle to data directory.
- 📄 **File listing**: List all files in asset directories.
- 📖 **File reading**: Read asset files with Base64 or UTF-8 encoding.
- 🔄 **Bundle access**: Direct access to native app bundle assets.
- 🗂️ **Directory operations**: Handle both individual files and entire directories.
- 📦 **Multiple encodings**: Support for Base64 and UTF-8 file reading.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Asset Manager plugin is typically used whenever an app ships files inside its native bundle that need to be accessed at runtime, for example:

- **Prepopulated data**: Copy bundled files such as databases or sample data from the app bundle to the app's data directory on first launch.
- **Configuration files**: Read bundled configuration files like `capacitor.config.json` using UTF-8 encoding.
- **Asset discovery**: List the files in an asset directory to process them dynamically.
- **Binary assets**: Read bundled binary files as Base64 encoded strings.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-asset-manager` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-asset-manager
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to copy an asset to the data directory, list asset files, and read an asset file.

### Copy an asset to the data directory

Copy a file or directory from the app bundle to the app's data directory. You can generate the destination path using the `getUri(...)` method of the Capacitor Filesystem plugin. Only available on Android and iOS:

```typescript
import { AssetManager } from '@capawesome/capacitor-asset-manager';
import { Directory, Filesystem } from '@capacitor/filesystem';

const copy = async () => {
  const { uri } = await Filesystem.getUri({
    directory: Directory.Cache,
    path: 'index.html'
  });
  await AssetManager.copy({
    from: 'public/index.html',
    to: uri
  });
};
```

### List asset files

List the files in an asset directory. If no path is specified, the root path is used. Only available on Android and iOS:

```typescript
import { AssetManager } from '@capawesome/capacitor-asset-manager';

const list = async () => {
  await AssetManager.list({
    path: 'public'
  });
};
```

### Read an asset file

Read a file from the app bundle using Base64 (default) or UTF-8 encoding. Avoid reading large files this way, as it can cause out of memory issues; instead, copy them to the app's data directory using the `copy(...)` method and read them from there using the [Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch). Only available on Android and iOS:

```typescript
import { AssetManager, Encoding } from '@capawesome/capacitor-asset-manager';

const read = async () => {
  const { data } = await AssetManager.read({
    encoding: Encoding.Utf8,
    path: 'capacitor.config.json'
  });
  return JSON.parse(data);
};
```

## API

<docgen-index>

* [`copy(...)`](#copy)
* [`list(...)`](#list)
* [`read(...)`](#read)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### copy(...)

```typescript
copy(options: CopyOptions) => Promise<void>
```

Copy a file or directory from the app bundle to the app's data directory.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#copyoptions">CopyOptions</a></code> |

**Since:** 7.0.0

--------------------


### list(...)

```typescript
list(options?: ListOptions | undefined) => Promise<ListResult>
```

List files in a directory.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#listoptions">ListOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#listresult">ListResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### read(...)

```typescript
read(options: ReadOptions) => Promise<ReadResult>
```

Read a file from the app bundle.

**Attention**: Reading large files can cause out of memory (OOM) issues.
It is therefore recommended to copy files to the app's data directory
using the `copy` method and read them from there using the [fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch).

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#readoptions">ReadOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readresult">ReadResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### Interfaces


#### CopyOptions

| Prop       | Type                | Description                                                                                                                                                                            | Since |
| ---------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`from`** | <code>string</code> | The source path to copy from.                                                                                                                                                          | 7.0.0 |
| **`to`**   | <code>string</code> | The destination path to copy to. **Tip**: Generate this path using the [`getUri(...)`](https://capacitorjs.com/docs/apis/filesystem#geturi) method of the Capacitor Filesystem plugin. | 7.0.0 |


#### ListResult

| Prop        | Type                  | Description                         | Since |
| ----------- | --------------------- | ----------------------------------- | ----- |
| **`files`** | <code>string[]</code> | The list of files in the directory. | 7.0.0 |


#### ListOptions

| Prop       | Type                | Description                                                                | Since |
| ---------- | ------------------- | -------------------------------------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The path to list files from. If not specified, the root path will be used. | 7.0.0 |


#### ReadResult

| Prop       | Type                | Description              | Since |
| ---------- | ------------------- | ------------------------ | ----- |
| **`data`** | <code>string</code> | The content of the file. | 7.0.0 |


#### ReadOptions

| Prop           | Type                                          | Description                                | Default               | Since |
| -------------- | --------------------------------------------- | ------------------------------------------ | --------------------- | ----- |
| **`encoding`** | <code><a href="#encoding">Encoding</a></code> | The encoding to use when reading the file. | <code>'base64'</code> | 7.0.0 |
| **`path`**     | <code>string</code>                           | The path to read the file from.            |                       | 7.0.0 |


### Enums


#### Encoding

| Members      | Value                 | Since |
| ------------ | --------------------- | ----- |
| **`Base64`** | <code>'base64'</code> | 7.0.0 |
| **`Utf8`**   | <code>'utf8'</code>   | 7.0.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin provides access to files in the native app bundle and is therefore only available on Android and iOS. All plugin methods are marked as only available on Android and iOS in the [API](#api) section.

### Why does my app crash when reading large asset files?

Reading large files with the `read(...)` method can cause out of memory (OOM) issues because the entire file content is returned as a string. Instead, copy the file to the app's data directory using the `copy(...)` method and read it from there using the Fetch API, as described in the [usage example](#read-an-asset-file) above.

### How is this plugin different from the Capacitor Filesystem plugin?

The Capacitor Filesystem plugin reads and writes files in the app's filesystem directories, but it cannot access the files bundled inside the native app. The Asset Manager plugin fills this gap by copying, listing, and reading native asset files. The two plugins work well together: for example, you can generate the destination path for the `copy(...)` method using the `getUri(...)` method of the Filesystem plugin.

### Which encodings are supported when reading a file?

The `read(...)` method supports Base64 and UTF-8 encoding. If no encoding is specified, Base64 is used by default.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Select a file, directory, image, or video from the device's file system or gallery.
- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open a file with the default application.
- [Zip](https://capawesome.io/docs/sdks/capacitor/zip/): Zip and unzip files and directories with support for encryption.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/asset-manager/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/asset-manager/LICENSE).
