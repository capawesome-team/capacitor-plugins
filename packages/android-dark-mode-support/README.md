# Capacitor Android Dark Mode Support Plugin

Capacitor plugin for seamless Android dark mode support. Enhance user experience with `prefers-color-scheme` CSS media feature compatibility.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Android Dark Mode Support plugin is typically used whenever an app should respect the system-wide dark mode setting on Android, for example:

- **Automatic dark theme switching**: Apply dark styles with `prefers-color-scheme` CSS media queries that correctly reflect the Android system setting.
- **Consistent theming**: Ensure the web view of your Capacitor app matches the user's system-wide dark mode preference without a manual in-app toggle.
- **Framework theming**: Let UI frameworks whose dark themes rely on the `prefers-color-scheme` media feature switch themes correctly on Android.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-android-dark-mode-support` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-android-dark-mode-support
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxWebkitVersion` version of `androidx.webkit:webkit` (default: `1.9.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

The plugin only needs to be installed.

It enables the correct behavior of the [`prefers-color-scheme`](https://developer.mozilla.org/en-US/docs/Web/CSS/@media/prefers-color-scheme) CSS media feature.

## FAQ

### Why does `prefers-color-scheme` not work in my Capacitor app on Android?

By default, the `prefers-color-scheme` CSS media feature may not correctly reflect the system dark mode setting in the Android web view. This plugin enables the correct behavior of the media feature so that your CSS dark mode styles are applied when the user has enabled dark mode on their device.

### Do I need to call any methods to use this plugin?

No, the plugin only needs to be installed. Once installed, it enables the correct behavior of the `prefers-color-scheme` CSS media feature without any additional code. See the [Usage](#usage) section for more information.

### Does this plugin work on iOS or Web?

No, this plugin only provides an Android implementation, as the name suggests. It exists to fix the dark mode behavior of the Android web view.

### Can I change the version of the `androidx.webkit` dependency?

Yes, you can define the `$androidxWebkitVersion` project variable in your app's `variables.gradle` file to change the default version (`1.9.0`) of the `androidx.webkit:webkit` dependency. This can be useful if you encounter dependency conflicts with other plugins in your project.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accessibility Preferences](https://capawesome.io/docs/sdks/capacitor/accessibility-preferences/): Read the user's system accessibility preferences.
- [Android Edge-to-Edge Support](https://capawesome.io/docs/sdks/capacitor/android-edge-to-edge-support/): Support edge-to-edge display on Android and set the background color of the status bar and navigation bar.
- [Navigation Bar](https://capawesome.io/docs/sdks/capacitor/navigation-bar/): Set the background color and button style of the navigation bar.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-dark-mode-support/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-dark-mode-support/LICENSE).
