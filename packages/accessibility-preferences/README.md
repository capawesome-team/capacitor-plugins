# Capacitor Accessibility Preferences Plugin

Capacitor plugin for reading the user's system accessibility preferences.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ♿ **Accessibility preferences**: Read the user's system accessibility preferences.
- 🔤 **Font scale**: Adapt your typography to the user's preferred text size.
- 🎞️ **Reduce motion**: Disable animations when the user prefers reduced motion.
- 🎨 **Contrast & colors**: React to increased contrast and inverted colors.
- 🌐 **Cross-platform**: Works on Android, iOS, and the Web.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Accessibility Preferences plugin is typically used to adapt an app's user interface to the user's system accessibility settings, for example:

- **Dynamic typography**: Scale your app's font sizes based on the system font scale so text stays readable for users who prefer larger text.
- **Reduced animations**: Disable or simplify animations and transitions when the user prefers reduced motion.
- **High-contrast themes**: Switch to a higher-contrast color scheme when the user has enabled increased contrast.
- **Bold text support**: Render heavier font weights when the user prefers bold text.
- **Color-aware rendering**: Adjust images or charts when the user has enabled inverted colors.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-accessibility-preferences` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following command:

```bash
npm install @capawesome/capacitor-accessibility-preferences
npx cap sync
```

This plugin does not require any additional permissions or configuration.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { AccessibilityPreferences } from '@capawesome/capacitor-accessibility-preferences';
```

### Read the user's accessibility preferences

Call `getPreferences()` to read all system accessibility preferences at once, such as the font scale, reduce motion, bold text, and contrast settings. Fields that the current platform cannot provide are set to `null` (see [Platform Availability](#platform-availability)):

```typescript
const getPreferences = async () => {
  const preferences = await AccessibilityPreferences.getPreferences();
  return preferences;
};
```

## API

<docgen-index>

* [`getPreferences()`](#getpreferences)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getPreferences()

```typescript
getPreferences() => Promise<GetPreferencesResult>
```

Get the user's current system accessibility preferences.

Fields that the current platform cannot provide are set to `null`.

**Returns:** <code>Promise&lt;<a href="#getpreferencesresult">GetPreferencesResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetPreferencesResult

| Prop                              | Type                         | Description                                                                                                                                                                                                                                                                                                                                           | Since |
| --------------------------------- | ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`fontScale`**                   | <code>number</code>          | The system font scale factor. A value of `1.0` represents the default font size. Values greater than `1.0` indicate that the user prefers larger text, values less than `1.0` smaller text. On iOS, the value is derived from the preferred content size category. On Web, this is always `1.0` because the font scale is not exposed to web content. | 0.1.0 |
| **`isBoldTextEnabled`**           | <code>boolean \| null</code> | Whether the user prefers bold text. Returns `null` on platforms where the value cannot be read (Web and Android versions below 12).                                                                                                                                                                                                                   | 0.1.0 |
| **`isHighContrastEnabled`**       | <code>boolean \| null</code> | Whether the user prefers increased contrast. On iOS, this reflects the "Increase Contrast" setting (darker system colors). On Android, this reflects the high text contrast setting. Returns `null` on platforms where the value cannot be read.                                                                                                      | 0.1.0 |
| **`isInvertColorsEnabled`**       | <code>boolean \| null</code> | Whether the user has enabled inverted colors. Returns `null` on Web.                                                                                                                                                                                                                                                                                  | 0.1.0 |
| **`isReduceMotionEnabled`**       | <code>boolean</code>         | Whether the user prefers reduced motion (fewer animations).                                                                                                                                                                                                                                                                                           | 0.1.0 |
| **`isReduceTransparencyEnabled`** | <code>boolean \| null</code> | Whether the user prefers reduced transparency. Only available on iOS. Returns `null` on Android and Web.                                                                                                                                                                                                                                              | 0.1.0 |

</docgen-api>

## Platform Availability

Not every preference can be read on every platform. Fields that the current platform cannot provide are set to `null`.

| Field                         | Android      | iOS | Web  |
| ----------------------------- | ------------ | --- | ---- |
| `fontScale`                   | ✅           | ✅  | ⚠️\* |
| `isReduceMotionEnabled`       | ✅           | ✅  | ✅   |
| `isBoldTextEnabled`           | ✅ (API 31+) | ✅  | ❌   |
| `isInvertColorsEnabled`       | ✅           | ✅  | ❌   |
| `isReduceTransparencyEnabled` | ❌           | ✅  | ❌   |
| `isHighContrastEnabled`       | ✅           | ✅  | ✅   |

✅ = available · ❌ = always `null` · ⚠️\* = `fontScale` is always `1.0` on Web because it is not exposed to web content.

On iOS, `fontScale` is derived from the preferred content size category using the following mapping (relative to the default body text size of 17 pt):

| Content size category           | `fontScale` |
| ------------------------------- | ----------- |
| Extra Small                     | 0.82        |
| Small                           | 0.88        |
| Medium                          | 0.94        |
| Large (default)                 | 1.0         |
| Extra Large                     | 1.12        |
| Extra Extra Large               | 1.24        |
| Extra Extra Extra Large         | 1.35        |
| Accessibility Medium            | 1.65        |
| Accessibility Large             | 1.94        |
| Accessibility Extra Large       | 2.35        |
| Accessibility Extra Extra Large | 2.76        |
| Accessibility 3X Large          | 3.12        |

CSS media queries such as `prefers-reduced-motion` and `prefers-contrast` already cover reduce-motion and contrast inside the WebView. The value of this plugin is the fields CSS cannot see (`fontScale`, bold text, inverted colors) and having a single API for your app logic on all platforms.

The screen reader _state_ (VoiceOver/TalkBack) is intentionally out of scope. Use the official [`@capacitor/screen-reader`](https://capacitorjs.com/docs/apis/screen-reader) plugin for that.

## FAQ

### Which accessibility preferences can be read on which platforms?

Not every preference is exposed on every platform. The font scale, reduce motion, and high contrast settings are available on Android, iOS, and the Web, while bold text (Android 12+ and iOS), inverted colors (Android and iOS), and reduced transparency (iOS only) are more limited. Check the [Platform Availability](#platform-availability) section for the complete matrix.

### Why is the font scale always 1.0 on the Web?

The system font scale is not exposed to web content, so the plugin cannot read it in a browser. On Android, the plugin returns the system font scale factor, and on iOS the value is derived from the preferred content size category.

### Why are some fields null in the result?

Fields that the current platform cannot provide are set to `null` instead of a fabricated value. For example, `isBoldTextEnabled` is `null` on the Web and on Android versions below 12, and `isReduceTransparencyEnabled` is `null` on Android and Web because it is an iOS-only setting.

### Do I still need this plugin if I use CSS media queries like prefers-reduced-motion?

CSS media queries such as `prefers-reduced-motion` and `prefers-contrast` already cover reduce motion and contrast inside the WebView. The value of this plugin lies in the fields CSS cannot see, such as the font scale, bold text, and inverted colors, and in having a single API for your app logic on all platforms.

### Can this plugin detect whether a screen reader like VoiceOver or TalkBack is running?

No, the screen reader state is intentionally out of scope for this plugin. Use the official [`@capacitor/screen-reader`](https://capacitorjs.com/docs/apis/screen-reader) plugin for that.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Screen Reader](https://capawesome.io/docs/sdks/capacitor/screen-reader/): Interact with screen readers such as VoiceOver and TalkBack.
- [Text Zoom](https://capawesome.io/docs/sdks/capacitor/text-zoom/): Read and control the WebView text zoom.
- [Localization](https://capawesome.io/docs/sdks/capacitor/localization/): Read the user's localization preferences, such as preferred locales and time zone.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accessibility-preferences/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accessibility-preferences/LICENSE).
