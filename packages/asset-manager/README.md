# @capawesome/capacitor-asset-manager

Capacitor plugin to access native asset files.

## Installation

```bash
npm install @capawesome/capacitor-asset-manager
npx cap sync
```

## Usage

```typescript
import { AssetManager } from '@capawesome/capacitor-asset-manager';

const echo = async () => {
  await AssetManager.echo();
};
```

## API

<docgen-index>

* [`copy(...)`](#copy)
* [`list(...)`](#list)
* [`read(...)`](#read)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### copy(...)

```typescript
copy(options: CopyOptions) => Promise<void>
```

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#copyoptions">CopyOptions</a></code> |

--------------------


### list(...)

```typescript
list(options?: ListOptions | undefined) => Promise<ListResult>
```

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#listoptions">ListOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#listresult">ListResult</a>&gt;</code>

--------------------


### read(...)

```typescript
read(options: ReadOptions) => Promise<ReadResult>
```

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#readoptions">ReadOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readresult">ReadResult</a>&gt;</code>

--------------------


### Interfaces


#### CopyOptions

| Prop       | Type                | Description                      | Since |
| ---------- | ------------------- | -------------------------------- | ----- |
| **`from`** | <code>string</code> | The source path to copy from.    | 7.0.0 |
| **`to`**   | <code>string</code> | The destination path to copy to. | 7.0.0 |


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

| Prop           | Type                            | Description                                | Default               | Since |
| -------------- | ------------------------------- | ------------------------------------------ | --------------------- | ----- |
| **`encoding`** | <code>'base64' \| 'utf8'</code> | The encoding to use when reading the file. | <code>'base64'</code> | 7.0.0 |
| **`path`**     | <code>string</code>             | The path to read the file from.            |                       | 7.0.0 |

</docgen-api>
