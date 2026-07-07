# Capacitor Home Indicator Plugin

Capacitor plugin to hide and show the iOS [home indicator](https://developer.apple.com/documentation/uikit/uiviewcontroller/prefershomeindicatorautohidden).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🫥 **Hide & show**: Hide and show the home indicator at runtime.
- 🔍 **State**: Read the current plugin-managed state.
- 🧩 **Zero-config**: No changes to your native project required.
- 🤝 **Compatibility**: Need to control the Android navigation bar? Use the [Navigation Bar](https://capawesome.io/docs/sdks/capacitor/navigation-bar/) plugin.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-home-indicator` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-home-indicator
npx cap sync
```

This plugin is only available on **iOS**. On Android and Web, all methods reject as unimplemented. To control the navigation bar on Android, use the [Navigation Bar](https://capawesome.io/docs/sdks/capacitor/navigation-bar/) plugin instead.

### iOS

No additional setup is required. The plugin injects the home indicator override into the Capacitor bridge view controller automatically, so you do not need to subclass `CAPBridgeViewController` or edit your `AppDelegate`.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { HomeIndicator } from '@capawesome/capacitor-home-indicator';

const hide = async () => {
  await HomeIndicator.hide();
};

const show = async () => {
  await HomeIndicator.show();
};

const isHidden = async () => {
  const { hidden } = await HomeIndicator.isHidden();
  return hidden;
};
```

## API

<docgen-index>

* [`hide()`](#hide)
* [`isHidden()`](#ishidden)
* [`show()`](#show)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### hide()

```typescript
hide() => Promise<void>
```

Hide the home indicator.

The home indicator auto-hides while the screen is idle and reappears
when the user touches the screen. The system does not allow permanently
removing the home indicator.

Only available on iOS.

**Since:** 0.1.0

--------------------


### isHidden()

```typescript
isHidden() => Promise<IsHiddenResult>
```

Check whether the home indicator is currently set to be hidden.

This reflects the plugin-managed state, not the actual visibility of the
home indicator on screen (which the system controls). The state is kept
in memory only and resets when the app is restarted.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#ishiddenresult">IsHiddenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### show()

```typescript
show() => Promise<void>
```

Show the home indicator.

Only available on iOS.

**Since:** 0.1.0

--------------------


### Interfaces


#### IsHiddenResult

| Prop         | Type                 | Description                                               | Since |
| ------------ | -------------------- | --------------------------------------------------------- | ----- |
| **`hidden`** | <code>boolean</code> | Whether the home indicator is currently set to be hidden. | 0.1.0 |

</docgen-api>

## Home Indicator Behavior

It is important to understand how the iOS home indicator behaves, as the operating system does not allow it to be permanently removed:

- **Auto-hide only**: Calling `hide()` sets the home indicator to auto-hide. The indicator only fades out while the screen is idle.
- **Reappears on touch**: As soon as the user touches the screen, the home indicator reappears. It fades out again once the screen becomes idle. This is a system behavior enforced by iOS and cannot be changed.
- **In-memory state**: The state managed by this plugin is kept in memory only. It resets to shown when the app is restarted, so call `hide()` again after your app relaunches if needed.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/home-indicator/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/home-indicator/LICENSE).
