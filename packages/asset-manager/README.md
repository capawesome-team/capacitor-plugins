# @capawesome/capacitor-asset-manager

Capacitor plugin to access native asset files.

## Installation

```bash
npm install @capawesome/capacitor-asset-manager
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Directory, Filesystem } from '@capacitor/filesystem';
import { AssetManager, Encoding } from '@capawesome/capacitor-asset-manager';

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

const list = async () => {
  await AssetManager.list({
    path: 'public'
  });
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/asset-manager/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/asset-manager/LICENSE).
