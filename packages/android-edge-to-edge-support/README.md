# Capacitor Android Edge-to-Edge Support Plugin

Capacitor plugin to support [edge-to-edge](https://developer.android.com/develop/ui/views/layout/edge-to-edge) display on Android with advanced features like setting the background color of the status bar and navigation bar.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

| Before                                                                                                      | After                                                                                                       | Before                                                                                                      | After                                                                                                       |
| ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- |
| <image src="https://github.com/user-attachments/assets/1c42aa63-1191-4b9b-860f-ffc47881d815" width="200" /> | <image src="https://github.com/user-attachments/assets/a4df4e58-0c21-45b5-aadd-ca197308016a" width="200" /> | <image src="https://github.com/user-attachments/assets/22c94f95-a0c4-4ace-8a3b-3a0feabf9191" width="200" /> | <image src="https://github.com/user-attachments/assets/21ece022-fb74-4067-889b-6922ecd0e2a5" width="200" /> |

**Attention:** Despite its name, this plugin doesn't enable edge-to-edge mode by default. Instead, it preserves the traditional app behavior by applying proper insets to the webview, preventing Android's edge-to-edge changes from affecting apps that haven't been designed to support it.

## Use Cases

The Android Edge-to-Edge Support plugin is typically used whenever an app needs to deal with Android's edge-to-edge display behavior, for example:

- **Preserving traditional layouts**: Prevent Android's edge-to-edge changes from affecting apps that haven't been designed to support it by applying proper insets to the web view.
- **Styling the system bars**: Set the background color of the status bar and navigation bar so they match your app's design.
- **Switching between light and dark themes**: Update the system bar background colors together with the [SystemBars](https://capacitorjs.com/docs/apis/system-bars) plugin when the user changes the theme.
- **Custom inset handling**: Read the insets that are currently applied to the web view to fine-tune your own layout.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-android-edge-to-edge-support` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-android-edge-to-edge-support
npx cap sync
```

### Android

#### Capacitor SystemBars Plugin

Make sure to disable the built-in insets handling of the [Capacitor SystemBars](https://capacitorjs.com/docs/apis/system-bars) plugin in your [Capacitor Configuration](https://capacitorjs.com/docs/config) file:

```json
{
  "plugins": {
    "SystemBars": {
      "insetsHandling": "disable"
    }
  }
}
```

Please note that this plugin is part of the Capacitor core and the insets handling is **always enabled by default**.

#### Capacitor Keyboard Plugin

If you are using the [Capacitor Keyboard](https://capacitorjs.com/docs/apis/keyboard) plugin, make sure to set the `resizeOnFullScreen` property to `false` (default) in your [Capacitor Configuration](https://capacitorjs.com/docs/config) file:

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

| Prop                     | Type                | Description                                                                                                 | Since |
| ------------------------ | ------------------- | ----------------------------------------------------------------------------------------------------------- | ----- |
| **`backgroundColor`**    | <code>string</code> | The hexadecimal color to set as the background color of the status bar and navigation bar.                  | 7.1.0 |
| **`navigationBarColor`** | <code>string</code> | The hexadecimal color to set as the background color of the navigation bar area. Only available on Android. | 8.0.0 |
| **`statusBarColor`**     | <code>string</code> | The hexadecimal color to set as the background color of the status bar area. Only available on Android.     | 8.0.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "EdgeToEdge": {
      "backgroundColor": "#ffffff",
      "navigationBarColor": "#000000",
      "statusBarColor": "#ffffff"
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
      navigationBarColor: "#000000",
      statusBarColor: "#ffffff",
    },
  },
};

export default config;
```

</docgen-config>

## Usage

The plugin applies insets to the web view automatically once installed; the following examples show how to enable or disable the edge-to-edge mode, read the applied insets, and set the system bar colors for light and dark themes.

### Enable or disable the edge-to-edge mode

Enable or disable the edge-to-edge mode at runtime. Only available on Android:

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';

const enable = async () => {
  await EdgeToEdge.enable();
};

const disable = async () => {
  await EdgeToEdge.disable();
};
```

### Get the applied insets

Read the insets that are currently applied to the web view, for example to fine-tune your own layout. Only available on Android:

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';

const getInsets = async () => {
  const result = await EdgeToEdge.getInsets();
  console.log('Insets:', result);
};
```

### Set the system bar colors for light and dark themes

Set the background color of the status bar and navigation bar, for example when the user switches between a light and a dark theme. It's recommended to combine this with the `setStyle(...)` method of the [SystemBars](https://capacitorjs.com/docs/apis/system-bars) plugin so the system bar icons remain readable:

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';
import { SystemBars, SystemBarsStyle } from '@capacitor/core';

const setDarkStyle = async () => {
  await SystemBars.setStyle({ style: SystemBarsStyle.Dark });
  await EdgeToEdge.setBackgroundColor({ color: '#000000' });
};

const setLightStyle = async () => {
  await SystemBars.setStyle({ style: SystemBarsStyle.Light });
  await EdgeToEdge.setBackgroundColor({ color: '#FFFFFF' });
};
```

## API

<docgen-index>

* [`disable()`](#disable)
* [`enable()`](#enable)
* [`getInsets()`](#getinsets)
* [`setBackgroundColor(...)`](#setbackgroundcolor)
* [`setNavigationBarColor(...)`](#setnavigationbarcolor)
* [`setStatusBarColor(...)`](#setstatusbarcolor)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### disable()

```typescript
disable() => Promise<void>
```

Disable the edge-to-edge mode.

Only available on Android.

**Since:** 7.2.0

--------------------


### enable()

```typescript
enable() => Promise<void>
```

Enable the edge-to-edge mode.

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


### setNavigationBarColor(...)

```typescript
setNavigationBarColor(options: SetNavigationBarColorOptions) => Promise<void>
```

Set the background color of the navigation bar area.

Only available on Android.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setnavigationbarcoloroptions">SetNavigationBarColorOptions</a></code> |

**Since:** 8.0.0

--------------------


### setStatusBarColor(...)

```typescript
setStatusBarColor(options: SetStatusBarColorOptions) => Promise<void>
```

Set the background color of the status bar area.

Only available on Android.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setstatusbarcoloroptions">SetStatusBarColorOptions</a></code> |

**Since:** 8.0.0

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


#### SetNavigationBarColorOptions

| Prop        | Type                | Description                                                                      | Since |
| ----------- | ------------------- | -------------------------------------------------------------------------------- | ----- |
| **`color`** | <code>string</code> | The hexadecimal color to set as the background color of the navigation bar area. | 8.0.0 |


#### SetStatusBarColorOptions

| Prop        | Type                | Description                                                                  | Since |
| ----------- | ------------------- | ---------------------------------------------------------------------------- | ----- |
| **`color`** | <code>string</code> | The hexadecimal color to set as the background color of the status bar area. | 8.0.0 |

</docgen-api>

## FAQ

### What about Capacitor 8's built-in edge-to-edge support?

Capacitor 8 introduced native edge-to-edge functionality through the internal `SystemBars` plugin. While this covers many common scenarios, this plugin addresses additional edge cases that aren't yet fully resolved in the Capacitor core implementation. We plan to deprecate this plugin once all edge cases are properly handled in Capacitor core.

### Is this plugin compatible with Capacitor's SystemBars API?

Yes, this plugin is partially compatible with the new [SystemBars API](https://capacitorjs.com/docs/apis/system-bars) introduced in Capacitor 8. For example, methods like `setStyle()` from the SystemBars API are supported and can be used alongside this plugin without conflicts.

### Does this plugin enable edge-to-edge mode by default?

No, despite its name, this plugin doesn't enable edge-to-edge mode by default. Instead, it preserves the traditional app behavior by applying proper insets to the web view, preventing Android's edge-to-edge changes from affecting apps that haven't been designed to support it. You can still enable or disable the edge-to-edge mode at runtime using the `enable()` and `disable()` methods.

### Why does my layout break when the keyboard is displayed?

If you are using the [Capacitor Keyboard](https://capacitorjs.com/docs/apis/keyboard) plugin, make sure the `resizeOnFullScreen` property is set to `false` (the default) in your Capacitor configuration file. Otherwise, the web view will be resized to fit the screen, which may cause issues with this plugin. See the [Installation](#installation) section for more information.

### Does this plugin work on iOS or Web?

No, this plugin only provides an Android implementation, as the name suggests. All methods are only available on Android since it addresses Android's edge-to-edge display behavior.

## Related Plugins

- [Android Dark Mode Support](https://capawesome.io/docs/sdks/capacitor/android-dark-mode-support/): Support dark mode on Android via the `prefers-color-scheme` CSS media feature.
- [Navigation Bar](https://capawesome.io/docs/sdks/capacitor/navigation-bar/): Set the background color and button style of the navigation bar.
- [Home Indicator](https://capawesome.io/docs/sdks/capacitor/home-indicator/): Hide and show the iOS home indicator.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-edge-to-edge-support/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-edge-to-edge-support/LICENSE).
