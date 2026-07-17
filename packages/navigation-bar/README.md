# Capacitor Navigation Bar Plugin

Capacitor plugin to set the background color and button style of the navigation bar.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Navigation Bar plugin offers one of the most complete solutions for customizing the Android navigation bar in Capacitor apps. Here are some of the key features:

- 🎨 **Background color**: Set the navigation bar background to any hex color or fully transparent.
- 🔘 **Button style**: Choose dark or light navigation bar buttons, or follow the system appearance.
- ➖ **Divider color**: Customize the navigation bar divider color on Android 9+.
- 👁️ **Visibility**: Hide or show the navigation bar on demand.
- ⚙️ **Configuration**: Apply defaults at app launch via the Capacitor configuration.
- 🤝 **Compatibility**: Works alongside the [Android Dark Mode Support](https://capawesome.io/docs/sdks/capacitor/android-dark-mode-support/), [Android Edge-to-Edge Support](https://capawesome.io/docs/sdks/capacitor/android-edge-to-edge-support/) and [Home Indicator](https://capawesome.io/docs/sdks/capacitor/home-indicator/) plugins.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Navigation Bar plugin is typically used to make the Android system navigation bar blend in with the app's design, for example:

- **Brand theming**: Set the navigation bar background to your brand color so it matches the rest of your app.
- **Dark mode support**: Switch the navigation bar color and button style when the user toggles between light and dark themes.
- **Immersive experiences**: Hide the navigation bar for fullscreen content such as videos or games and show it again afterwards.
- **Consistent app launch**: Apply a default color, divider color, and button style at app launch via the Capacitor configuration.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-navigation-bar` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-navigation-bar
npx cap sync
```

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop               | Type                                        | Description                                                                                                                                                        | Since |
| ------------------ | ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`color`**        | <code>string</code>                         | The hexadecimal color to set as the background color of the navigation bar. Use `'transparent'` to make the navigation bar transparent. Only available on Android. | 8.0.0 |
| **`dividerColor`** | <code>string</code>                         | The hexadecimal color to set as the color of the navigation bar divider. Use `'transparent'` to hide the divider. Only available on Android (API level 28+).       | 8.0.0 |
| **`style`**        | <code>'DARK' \| 'LIGHT' \| 'DEFAULT'</code> | The style of the navigation bar buttons. Only available on Android.                                                                                                | 8.0.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "NavigationBar": {
      "color": "#ffffff",
      "dividerColor": "#d9d9d9",
      "style": "LIGHT"
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-navigation-bar" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    NavigationBar: {
      color: "#ffffff",
      dividerColor: "#d9d9d9",
      style: "LIGHT",
    },
  },
};

export default config;
```

</docgen-config>

## Usage

The following examples show how to set the navigation bar color and button style, read the current color and style, and hide and show the navigation bar.

### Set the navigation bar color

Set the background color of the navigation bar to any hexadecimal color, or pass `'transparent'` to make it fully transparent. Only available on Android:

```typescript
import { NavigationBar } from '@capawesome/capacitor-navigation-bar';

const setColor = async () => {
  await NavigationBar.setColor({ color: '#ffffff' });
};
```

### Set the button style

Choose dark or light navigation bar buttons, or use `Style.Default` to follow the current device appearance. Only available on Android:

```typescript
import { NavigationBar, Style } from '@capawesome/capacitor-navigation-bar';

const setStyle = async () => {
  await NavigationBar.setStyle({ style: Style.Light });
};
```

### Read the current color and style

Get the current background color and button style of the navigation bar. Only available on Android:

```typescript
import { NavigationBar } from '@capawesome/capacitor-navigation-bar';

const getColor = async () => {
  const { color } = await NavigationBar.getColor();
  return color;
};

const getStyle = async () => {
  const { style } = await NavigationBar.getStyle();
  return style;
};
```

### Hide and show the navigation bar

Hide the navigation bar on demand, for example for fullscreen content, and show it again afterwards. Only available on Android:

```typescript
import { NavigationBar } from '@capawesome/capacitor-navigation-bar';

const hide = async () => {
  await NavigationBar.hide();
};

const show = async () => {
  await NavigationBar.show();
};
```

## API

<docgen-index>

* [`getColor()`](#getcolor)
* [`getStyle()`](#getstyle)
* [`hide()`](#hide)
* [`setColor(...)`](#setcolor)
* [`setStyle(...)`](#setstyle)
* [`show()`](#show)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getColor()

```typescript
getColor() => Promise<GetColorResult>
```

Get the current background color of the navigation bar.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getcolorresult">GetColorResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### getStyle()

```typescript
getStyle() => Promise<GetStyleResult>
```

Get the current style of the navigation bar buttons.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getstyleresult">GetStyleResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### hide()

```typescript
hide() => Promise<void>
```

Hide the navigation bar.

Only available on Android.

**Since:** 8.0.0

--------------------


### setColor(...)

```typescript
setColor(options: SetColorOptions) => Promise<void>
```

Set the background color of the navigation bar.

Only available on Android.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setcoloroptions">SetColorOptions</a></code> |

**Since:** 8.0.0

--------------------


### setStyle(...)

```typescript
setStyle(options: SetStyleOptions) => Promise<void>
```

Set the style of the navigation bar buttons.

Only available on Android.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setstyleoptions">SetStyleOptions</a></code> |

**Since:** 8.0.0

--------------------


### show()

```typescript
show() => Promise<void>
```

Show the navigation bar.

Only available on Android.

**Since:** 8.0.0

--------------------


### Interfaces


#### GetColorResult

| Prop        | Type                | Description                                                                                                 | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------------------------------------------- | ----- |
| **`color`** | <code>string</code> | The hexadecimal color of the navigation bar, or `'transparent'` if the navigation bar is fully transparent. | 8.0.0 |


#### GetStyleResult

| Prop        | Type                                    | Description                              | Since |
| ----------- | --------------------------------------- | ---------------------------------------- | ----- |
| **`style`** | <code><a href="#style">Style</a></code> | The style of the navigation bar buttons. | 8.0.0 |


#### SetColorOptions

| Prop               | Type                | Description                                                                                                                                                  | Since |
| ------------------ | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`color`**        | <code>string</code> | The hexadecimal color to set as the background color of the navigation bar. Use `'transparent'` to make the navigation bar transparent.                      | 8.0.0 |
| **`dividerColor`** | <code>string</code> | The hexadecimal color to set as the color of the navigation bar divider. Use `'transparent'` to hide the divider. Only available on Android (API level 28+). | 8.0.0 |


#### SetStyleOptions

| Prop        | Type                                    | Description                              | Since |
| ----------- | --------------------------------------- | ---------------------------------------- | ----- |
| **`style`** | <code><a href="#style">Style</a></code> | The style of the navigation bar buttons. | 8.0.0 |


### Enums


#### Style

| Members       | Value                  | Description                                  | Since |
| ------------- | ---------------------- | -------------------------------------------- | ----- |
| **`Dark`**    | <code>'DARK'</code>    | Light icons on a dark background.            | 8.0.0 |
| **`Default`** | <code>'DEFAULT'</code> | Resolved from the current device appearance. | 8.0.0 |
| **`Light`**   | <code>'LIGHT'</code>   | Dark icons on a light background.            | 8.0.0 |

</docgen-api>

## FAQ

### Why are the `setColor` and `setStyle` methods not working on Android 15+?

Starting with Android 15, the system enforces [edge-to-edge display](https://developer.android.com/develop/ui/views/layout/edge-to-edge) by default. In this mode the navigation bar is fully transparent and `Window.setNavigationBarColor` becomes a no-op, so setting the color through this plugin has no visible effect.
If your app targets Android 15+ and you want to color the navigation bar area, use the [`@capawesome/capacitor-android-edge-to-edge-support`](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/android-edge-to-edge-support) plugin, which paints an overlay behind the navigation bar.

### Why is this plugin not available on iOS?

iOS does not have a system navigation bar that can be customized in the same way as on Android. All methods of this plugin are therefore unimplemented on iOS and reject with an error.

### How do I make the navigation bar transparent?

Pass `'transparent'` as the color to the `setColor` method or use it as the `color` value in the [Capacitor configuration](#configuration). You can also pass `'transparent'` as the `dividerColor` to hide the navigation bar divider.

### Can I apply a color and style at app launch?

Yes, you can define the `color`, `dividerColor`, and `style` properties in the `NavigationBar` section of your Capacitor configuration file. The plugin applies these defaults when the app launches, so you don't have to call `setColor` or `setStyle` manually. See the [Configuration](#configuration) section for examples.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Android Dark Mode Support](https://capawesome.io/docs/sdks/capacitor/android-dark-mode-support/): Seamless dark mode support for Android with `prefers-color-scheme` compatibility.
- [Android Edge-to-Edge Support](https://capawesome.io/docs/sdks/capacitor/android-edge-to-edge-support/): Support edge-to-edge display on Android, including setting the background color of the status bar and navigation bar.
- [Home Indicator](https://capawesome.io/docs/sdks/capacitor/home-indicator/): Hide and show the home indicator on iOS.
- [Screen Orientation](https://capawesome.io/docs/sdks/capacitor/screen-orientation/): Lock and unlock the screen orientation.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/navigation-bar/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/navigation-bar/LICENSE).
