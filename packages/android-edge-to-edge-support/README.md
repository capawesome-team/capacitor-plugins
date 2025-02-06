# @capawesome/capacitor-android-edge-to-edge-support

Capacitor plugin to support edge-to-edge display on Android.

## Installation

```bash
npm install @capawesome/capacitor-android-edge-to-edge-support
npx cap sync
```

## Usage

The plugin only needs to be installed. It applies insets to the web view to support edge-to-edge display on Android. The plugin also provides a method to set the background color of the status bar and navigation bar.

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';

const setBackgroundColor = async () => {
  await EdgeToEdge.setBackgroundColor({ color: '#ffffff' });
};
```

## API

<docgen-index>

* [`setBackgroundColor(...)`](#setbackgroundcolor)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setBackgroundColor(...)

```typescript
setBackgroundColor(options: SetBackgroundColorOptions) => Promise<void>
```

Set the background color of the status bar and navigation bar.

Only available on Android.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setbackgroundcoloroptions">SetBackgroundColorOptions</a></code> |

**Since:** 7.0.0

--------------------


### Interfaces


#### SetBackgroundColorOptions

| Prop        | Type                | Description                                                                                | Since |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`color`** | <code>string</code> | The hexadecimal color to set as the background color of the status bar and navigation bar. | 7.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-edge-to-edge-support/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-edge-to-edge-support/LICENSE).
