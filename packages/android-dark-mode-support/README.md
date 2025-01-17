# @capawesome/capacitor-android-dark-mode-support

Capacitor plugin to support dark mode on Android.

## Installation

```bash
npm install @capawesome/capacitor-android-dark-mode-support
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):
- `$androidxWebkitVersion` version of `androidx.webkit:webkit` (default: `1.9.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

The plugin only needs to be installed.

It enables the correct behavior of the [`prefers-color-scheme`](https://developer.mozilla.org/en-US/docs/Web/CSS/@media/prefers-color-scheme) CSS media feature.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-dark-mode-support/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-dark-mode-support/LICENSE).
