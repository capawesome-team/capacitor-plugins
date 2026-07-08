# Capacitor Toast Plugin

Capacitor plugin to show native toast notifications.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🍞 **Native toasts**: Show short, non-blocking text notifications on Android and iOS.
- 🌐 **Zero-dependency web**: A self-contained web implementation with no additional peer dependencies.
- 🧭 **Positioning**: Show toasts at the top, center or bottom of the screen.
- ⏱️ **Durations**: Choose between a short and a long display duration.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Toast plugin is typically used to give users brief, non-blocking feedback, for example:

- **Success confirmations**: Confirm that an action such as saving or sending was completed.
- **Error notices**: Inform users about a failed action without interrupting their flow.
- **Clipboard feedback**: Confirm that a value was copied to the clipboard.
- **Status updates**: Show short status messages such as a lost network connection.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-toast` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-toast
npx cap sync
```

### Android

No additional configuration is required for this plugin.

On Android 12 and newer, the operating system ignores the requested position and always shows toasts at the bottom of the screen. This is a system restriction and not a bug of this plugin.

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

The following example shows how to show a toast.

### Show a toast

Show a short, non-blocking text notification. Use the `duration` option to choose between a short (about 2000 ms) and a long (about 3500 ms) display duration, and the `position` option to show the toast at the top, center or bottom of the screen:

```typescript
import { Toast, ToastDuration, ToastPosition } from '@capawesome/capacitor-toast';

const show = async () => {
  await Toast.show({
    text: 'Hello World!',
    duration: ToastDuration.Short,
    position: ToastPosition.Bottom,
  });
};
```

## API

<docgen-index>

* [`show(...)`](#show)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### show(...)

```typescript
show(options: ShowOptions) => Promise<void>
```

Show a toast with a short text message.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#showoptions">ShowOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### ShowOptions

| Prop           | Type                                                    | Description                                                                                                                                                                 | Default                           | Since |
| -------------- | ------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------- | ----- |
| **`duration`** | <code><a href="#toastduration">ToastDuration</a></code> | The duration of the toast.                                                                                                                                                  | <code>ToastDuration.Short</code>  | 0.1.0 |
| **`position`** | <code><a href="#toastposition">ToastPosition</a></code> | The position of the toast on the screen. On Android 12 and newer, the position is ignored and the toast is always shown at the bottom of the screen (a system restriction). | <code>ToastPosition.Bottom</code> | 0.1.0 |
| **`text`**     | <code>string</code>                                     | The text to show in the toast.                                                                                                                                              |                                   | 0.1.0 |


### Enums


#### ToastDuration

| Members     | Value                | Description                                                  | Since |
| ----------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`Long`**  | <code>'LONG'</code>  | Show the toast for a longer period of time (about 3500 ms).  | 0.1.0 |
| **`Short`** | <code>'SHORT'</code> | Show the toast for a shorter period of time (about 2000 ms). | 0.1.0 |


#### ToastPosition

| Members      | Value                 | Description                                 | Since |
| ------------ | --------------------- | ------------------------------------------- | ----- |
| **`Bottom`** | <code>'BOTTOM'</code> | Show the toast at the bottom of the screen. | 0.1.0 |
| **`Center`** | <code>'CENTER'</code> | Show the toast in the center of the screen. | 0.1.0 |
| **`Top`**    | <code>'TOP'</code>    | Show the toast at the top of the screen.    | 0.1.0 |

</docgen-api>

## Migrating from `@capacitor/toast`

This plugin offers an API very similar to the official [`@capacitor/toast`](https://github.com/ionic-team/capacitor-plugins/tree/main/toast) plugin, so migrating is straightforward:

- Replace the dependency `@capacitor/toast` with `@capawesome/capacitor-toast`.
- The web implementation is self-contained, so the [`@ionic/pwa-elements`](https://github.com/ionic-team/pwa-elements) package is no longer required for toasts.
- The `duration` and `position` options are now typed enums with uppercase string values (e.g. `ToastDuration.Short` → `'SHORT'`, `ToastPosition.Bottom` → `'BOTTOM'`) instead of lowercase string literals.

## FAQ

### Why is my toast always shown at the bottom of the screen on Android?

On Android 12 and newer, the operating system ignores the requested position and always shows toasts at the bottom of the screen. This is a system restriction and not a bug of this plugin.

### How long is a toast displayed?

You can choose between two durations with the `duration` option: `ToastDuration.Short` shows the toast for about 2000 ms and `ToastDuration.Long` for about 3500 ms. If you don't set the option, the short duration is used by default.

### How is this plugin different from the official `@capacitor/toast` plugin?

The API is very similar, so migrating is straightforward. The main differences are that the web implementation is self-contained (so the `@ionic/pwa-elements` package is no longer required for toasts) and that the `duration` and `position` options are typed enums with uppercase string values. See [Migrating from `@capacitor/toast`](#migrating-from-capacitortoast) for details.

### Do I need to install `@ionic/pwa-elements` for the web?

No, the plugin ships with a self-contained web implementation that has no additional peer dependencies. Toasts work on the web out of the box after [installing](#installation) the plugin.

### Do I need any permissions or configuration to use this plugin?

No, the plugin does not require any permissions or configuration on Android, iOS, or Web. Simply [install](#installation) it and call the `show(...)` method.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/): Show native alert, confirm, and prompt dialogs.
- [Action Sheet](https://capawesome.io/docs/sdks/capacitor/action-sheet/): Show native action sheets.
- [Badge](https://capawesome.io/docs/sdks/capacitor/badge/): Access and update the badge number of the app icon.
- [Haptics](https://capawesome.io/docs/sdks/capacitor/haptics/): Provide haptic feedback such as impacts, notifications, and vibrations.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/toast/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/toast/LICENSE).
