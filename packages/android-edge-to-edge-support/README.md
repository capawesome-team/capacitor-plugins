# @capawesome/capacitor-android-edge-to-edge-support

Capacitor plugin to support [edge-to-edge](https://developer.android.com/develop/ui/views/layout/edge-to-edge) display on Android with advanced features like setting the background color of the status bar and navigation bar.

| Before                                                                                                      | After                                                                                                       | Before                                                                                                      | After                                                                                                       |
| ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- |
| <image src="https://github.com/user-attachments/assets/1c42aa63-1191-4b9b-860f-ffc47881d815" width="200" /> | <image src="https://github.com/user-attachments/assets/a4df4e58-0c21-45b5-aadd-ca197308016a" width="200" /> | <image src="https://github.com/user-attachments/assets/22c94f95-a0c4-4ace-8a3b-3a0feabf9191" width="200" /> | <image src="https://github.com/user-attachments/assets/21ece022-fb74-4067-889b-6922ecd0e2a5" width="200" /> |

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-android-edge-to-edge-support
npx cap sync
```

### Android

If you are using the [Capacitor Keyboard](https://capacitorjs.com/docs/apis/keyboard) plugin, make sure to set the `resizeOnFullScreen` property to `false` (default) in your Capacitor Configuration file:

```json
{
  "plugins": {
    "Keyboard": {
      "resizeOnFullScreen": false
    }
  }
}
```

Otherwise, the web view will be resized to fit the screen, which may cause issues with this plugin, see [this comment](https://github.com/capawesome-team/capacitor-plugins/issues/490#issuecomment-2826435796).

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                     | Type                | Description                                                                                | Since |
| ------------------------ | ------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`backgroundColor`**    | <code>string</code> | The hexadecimal color to set as the background color of the status bar and navigation bar. | 7.1.0 |
| **`statusBarColor`**     | <code>string</code> | The hexadecimal color to set as the background color of the status bar.                    | 7.3.0 |
| **`navigationBarColor`** | <code>string</code> | The hexadecimal color to set as the background color of the navigation bar.                | 7.3.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "EdgeToEdge": {
      "backgroundColor": "#ffffff",
      "statusBarColor": "#ffffff",
      "navigationBarColor": "#000000"
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
      statusBarColor: "#ffffff",
      navigationBarColor: "#000000",
    },
  },
};

export default config;
```

</docgen-config>

## Usage

The plugin **only needs to be installed**. It applies insets to the web view to support edge-to-edge display on Android. The plugin also provides methods to set the background color of the status bar and navigation bar separately or together. It's recommended to use these methods in combination with the [Status Bar](https://capacitorjs.com/docs/apis/status-bar) plugin.

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';
import { StatusBar, Style } from '@capacitor/status-bar';

const enable = async () => {
  await EdgeToEdge.enable();
};

const disable = async () => {
  await EdgeToEdge.disable();
};

const getInsets = async () => {
  const result = await EdgeToEdge.getInsets();
  console.log('Insets:', result);
};

// Set both status bar and navigation bar to the same color (deprecated, use setStatusBarColor and setNavigationBarColor instead)
const setBackgroundColor = async () => {
  await EdgeToEdge.setBackgroundColor({ color: '#ffffff' });
  await StatusBar.setStyle({ style: Style.Light });
};

// Set status bar and navigation bar colors separately
const setStatusBarColor = async () => {
  await EdgeToEdge.setStatusBarColor({ color: '#ffffff' });
  await StatusBar.setStyle({ style: Style.Light });
};

const setNavigationBarColor = async () => {
  await EdgeToEdge.setNavigationBarColor({ color: '#000000' });
};

// Example: Set different colors for status bar and navigation bar
const setDifferentColors = async () => {
  await EdgeToEdge.setStatusBarColor({ color: '#ffffff' });
  await EdgeToEdge.setNavigationBarColor({ color: '#000000' });
  await StatusBar.setStyle({ style: Style.Light });
};
```

## API

<docgen-index>

* [`enable()`](#enable)
* [`disable()`](#disable)
* [`getInsets()`](#getinsets)
* [`setBackgroundColor(...)`](#setbackgroundcolor)
* [`setStatusBarColor(...)`](#setstatusbarcolor)
* [`setNavigationBarColor(...)`](#setnavigationbarcolor)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### enable()

```typescript
enable() => Promise<void>
```

Enable the edge-to-edge mode.

Only available on Android.

**Since:** 7.2.0

--------------------


### disable()

```typescript
disable() => Promise<void>
```

Disable the edge-to-edge mode.

Only available on Android.

**Since:** 7.2.0

--------------------


### getInsets()

```typescript
getInsets() => Promise<GetInsetsResult>
```

Return the insets that are currently applied to the webview.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getinsetsresult">GetInsetsResult</a>&gt;</code>

**Since:** 7.2.0

--------------------


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


### setStatusBarColor(...)

```typescript
setStatusBarColor(options: SetStatusBarColorOptions) => Promise<void>
```

Set the background color of the status bar.

Only available on Android.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setstatusbarcoloroptions">SetStatusBarColorOptions</a></code> |

**Since:** 7.3.0

--------------------


### setNavigationBarColor(...)

```typescript
setNavigationBarColor(options: SetNavigationBarColorOptions) => Promise<void>
```

Set the background color of the navigation bar.

Only available on Android.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnavigationbarcoloroptions">SetNavigationBarColorOptions</a></code> |

**Since:** 7.3.0

--------------------


### Interfaces


#### GetInsetsResult

| Prop         | Type                | Description                                                                  | Since |
| ------------ | ------------------- | ---------------------------------------------------------------------------- | ----- |
| **`bottom`** | <code>number</code> | The bottom inset that was applied to the webview. Only available on Android. | 7.2.0 |
| **`left`**   | <code>number</code> | The left inset that was applied to the webview. Only available on Android.   | 7.2.0 |
| **`right`**  | <code>number</code> | The right inset that was applied to the webview. Only available on Android.  | 7.2.0 |
| **`top`**    | <code>number</code> | The top inset that was applied to the webview. Only available on Android.    | 7.2.0 |


#### SetBackgroundColorOptions

| Prop        | Type                | Description                                                                                | Since |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`color`** | <code>string</code> | The hexadecimal color to set as the background color of the status bar and navigation bar. | 7.0.0 |


#### SetStatusBarColorOptions

| Prop        | Type                | Description                                                             | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`color`** | <code>string</code> | The hexadecimal color to set as the background color of the status bar. | 7.3.0 |


#### SetNavigationBarColorOptions

| Prop        | Type                | Description                                                                 | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------- | ----- |
| **`color`** | <code>string</code> | The hexadecimal color to set as the background color of the navigation bar. | 7.3.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-edge-to-edge-support/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-edge-to-edge-support/LICENSE).
