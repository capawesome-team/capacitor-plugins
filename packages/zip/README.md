# @capawesome-team/capacitor-zip

Capacitor plugin to zip and unzip files.

## Installation

```bash
npm install @capawesome-team/capacitor-zip
npx cap sync
```

### Android Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$zip4jVersion` version of `net.lingala.zip4j:zip4j` (default: `2.11.5`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Zip } from '@capawesome-team/capacitor-zip';

const unzip = async () => {
  await Zip.unzip({
    source: 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398.zip',
    destination: 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398',
  });
};

const zip = async () => {
  await Zip.zip({
    source: 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398',
    destination: 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398.zip',
  });
};
```

## API

<docgen-index>

* [`zip(...)`](#zip)
* [`unzip(...)`](#unzip)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### zip(...)

```typescript
zip(options: ZipOptions) => Promise<void>
```

Zip a file or directory.

Only available on Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#zipoptions">ZipOptions</a></code> |

**Since:** 6.0.0

--------------------


### unzip(...)

```typescript
unzip(options: UnzipOptions) => Promise<void>
```

Unzip a file.

Only available on Android and iOS.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#unzipoptions">UnzipOptions</a></code> |

**Since:** 6.0.0

--------------------


### Interfaces


#### ZipOptions

| Prop              | Type                | Description                          | Since |
| ----------------- | ------------------- | ------------------------------------ | ----- |
| **`source`**      | <code>string</code> | The source file or directory to zip. | 6.0.0 |
| **`destination`** | <code>string</code> | The destination file.                | 6.0.0 |


#### UnzipOptions

| Prop              | Type                | Description                | Since |
| ----------------- | ------------------- | -------------------------- | ----- |
| **`source`**      | <code>string</code> | The source file to unzip.  | 6.0.0 |
| **`destination`** | <code>string</code> | The destination directory. | 6.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zip/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zip/LICENSE).
