# @capawesome-team/capacitor-file-opener

Capacitor plugin to open a file with the default application.

## Installation

If you are an [Insider](https://capawesome.io/insiders/#what-is-insiders), see [Getting started with Insiders](https://capawesome.io/insiders/getting-started/?plugin=capacitor-file-opener) and follow the instructions to install the plugin.

If you are **not** an Insider, please install the package from the public npm registry:

```bash
npm install @capawesome-team/capacitor-file-opener
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android | iOS |
| ---------- | ---------- |
| <img src="https://user-images.githubusercontent.com/13857929/185747140-7e59ca70-96c9-4d67-a3b3-8fd9c7eb1546.gif" width="324" /> | <img src="https://user-images.githubusercontent.com/13857929/185747133-62a2b5e5-ff6f-4b30-871c-4c3609db7829.gif" width="266" /> |

## Usage

```typescript
import { FileOpener } from '@capawesome-team/capacitor-file-opener';

const open = async () => {
  await FileOpener.openFile({
    path: 'file:///var/mobile/Containers/Data/Application/22A433FD-D82D-4989-8BE6-9FC49DEA20BB/Images/test.png'
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

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#openfileoptions">OpenFileOptions</a></code> |

**Since:** 0.0.1

--------------------


### Interfaces


#### OpenFileOptions

| Prop           | Type                | Description                                                                    | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------ | ----- |
| **`path`**     | <code>string</code> | The path of the file.                                                          | 0.0.1 |
| **`mimeType`** | <code>string</code> | The mime type of the file. If not specified, the mime type will be determined. | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-opener/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/file-opener/LICENSE).
