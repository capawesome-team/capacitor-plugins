# @capawesome/capacitor-android-edge-to-edge-support

Capacitor plugin to support [edge-to-edge](https://developer.android.com/develop/ui/views/layout/edge-to-edge) display on Android.

| Before                                                                                                      | After                                                                                                       | Before                                                                                                      | After                                                                                                       |
| ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- |
| <image src="https://github.com/user-attachments/assets/1c42aa63-1191-4b9b-860f-ffc47881d815" width="200" /> | <image src="https://github.com/user-attachments/assets/a4df4e58-0c21-45b5-aadd-ca197308016a" width="200" /> | <image src="https://github.com/user-attachments/assets/22c94f95-a0c4-4ace-8a3b-3a0feabf9191" width="200" /> | <image src="https://github.com/user-attachments/assets/21ece022-fb74-4067-889b-6922ecd0e2a5" width="200" /> |

## Installation

```bash
npm install @capawesome/capacitor-android-edge-to-edge-support
npx cap sync
```

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                  | Type                | Description                                                                                | Since |
| --------------------- | ------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`backgroundColor`** | <code>string</code> | The hexadecimal color to set as the background color of the status bar and navigation bar. | 7.1.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "EdgeToEdge": {
      "backgroundColor": "#ffffff"
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-android-edge-to-edge-support" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    EdgeToEdge: {
      backgroundColor: "#ffffff",
    },
  },
};

export default config;
```

</docgen-config>

## Usage

The plugin **only needs to be installed**. It applies insets to the web view to support edge-to-edge display on Android. The plugin also provides a method to set the background color of the status bar and navigation bar. It's recommended to use this method in combination with the [Status Bar](https://capacitorjs.com/docs/apis/status-bar) plugin.

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';
import { StatusBar, Style } from '@capacitor/status-bar';

const setBackgroundColor = async () => {
  await EdgeToEdge.setBackgroundColor({ color: '#ffffff' });
  await StatusBar.setStyle({ style: Style.Light });
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
