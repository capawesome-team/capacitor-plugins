# @capawesome/capacitor-screenshot

Capacitor plugin for taking screenshots.

## Installation

Install the plugin:

```bash
npm install @capawesome/capacitor-screenshot
npx cap sync
```

If you are using the Web platform, you must also install the `html2canvas` package:

```bash
npm i html2canvas
```

## Usage

```ts
import { Screenshot } from '@capawesome/capacitor-screenshot';

const take = async () => {
  const { uri } = await Screenshot.take();
  console.log('Screenshot saved at:', uri);
};
```

## API

<docgen-index>

* [`take()`](#take)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### take()

```typescript
take() => Promise<TakeResult>
```

Take a screenshot.

**Returns:** <code>Promise&lt;<a href="#takeresult">TakeResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### Interfaces


#### TakeResult

| Prop      | Type                | Description                                                          | Since |
| --------- | ------------------- | -------------------------------------------------------------------- | ----- |
| **`uri`** | <code>string</code> | The file path (Android and iOS) or data URI (Web) of the screenshot. | 6.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screenshot/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screenshot/LICENSE).
