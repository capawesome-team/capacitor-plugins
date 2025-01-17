# @capawesome-team/capacitor-zip

Capacitor plugin to zip and unzip files.

## Features

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📁 **File Compression**: Zip and unzip single or multiple files.
- 🔑 **Encryption**: Encrypt and decrypt files.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
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
    password: 'secret',
  });
};

const zip = async () => {
  await Zip.zip({
    source: 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398',
    destination: 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398.zip',
    password: 'secret',
  });
};
```

## API

<docgen-index>

* [`unzip(...)`](#unzip)
* [`zip(...)`](#zip)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

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


### Interfaces


#### UnzipOptions

| Prop              | Type                | Description                           | Since |
| ----------------- | ------------------- | ------------------------------------- | ----- |
| **`destination`** | <code>string</code> | The destination directory.            | 6.0.0 |
| **`password`**    | <code>string</code> | The password to decrypt the zip file. | 6.1.0 |
| **`source`**      | <code>string</code> | The source file to unzip.             | 6.0.0 |


#### ZipOptions

| Prop              | Type                | Description                           | Since |
| ----------------- | ------------------- | ------------------------------------- | ----- |
| **`destination`** | <code>string</code> | The destination file.                 | 6.0.0 |
| **`password`**    | <code>string</code> | The password to encrypt the zip file. | 6.1.0 |
| **`source`**      | <code>string</code> | The source file or directory to zip.  | 6.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zip/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zip/LICENSE).
