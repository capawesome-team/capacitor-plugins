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

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

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

```typescript
import { AccessibilityPreferences } from '@capawesome/capacitor-accessibility-preferences';

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accessibility-preferences/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accessibility-preferences/LICENSE).
