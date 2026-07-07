# Capacitor Text Interaction Plugin

Capacitor plugin to enable and disable text interaction (selection, magnifier, callout menu) in the app's WebView.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ✋ **Text interaction**: Enable and disable text interaction at runtime.
- 🔍 **State**: Read whether text interaction is currently enabled.
- 🧩 **Zero-config**: No changes to your native project required.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-text-interaction` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-text-interaction
npx cap sync
```

This plugin is only available on **iOS**. On Android and Web, all methods reject as unimplemented. On Web, use the CSS `user-select` property to prevent text selection instead.

### iOS

No additional setup is required.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { TextInteraction } from '@capawesome/capacitor-text-interaction';

const enable = async () => {
  await TextInteraction.enable();
};

const disable = async () => {
  await TextInteraction.disable();
};

const isEnabled = async () => {
  const { enabled } = await TextInteraction.isEnabled();
  return enabled;
};
```

## API

<docgen-index>

* [`disable()`](#disable)
* [`enable()`](#enable)
* [`isEnabled()`](#isenabled)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### disable()

```typescript
disable() => Promise<void>
```

Disable text interaction in the app's WebView.

This turns off system text-interaction gestures such as text selection,
the selection magnifier and the callout (copy/paste) menu.

Only available on iOS.

**Since:** 0.1.0

--------------------


### enable()

```typescript
enable() => Promise<void>
```

Enable text interaction in the app's WebView.

Text interaction is enabled by default.

Only available on iOS.

**Since:** 0.1.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Check whether text interaction is currently enabled in the app's WebView.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### IsEnabledResult

| Prop          | Type                 | Description                                    | Since |
| ------------- | -------------------- | ---------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether text interaction is currently enabled. | 0.1.0 |

</docgen-api>

## Text Interaction Behavior

Text interaction covers the system gestures that let a user work with text in the WebView: text selection, the selection magnifier and the callout (copy/paste) menu.

- **Beyond CSS**: The CSS `user-select: none` property prevents text selection for specific elements and covers most cases. This plugin disables the system text-interaction gestures as a whole, which is useful for app-like UIs where accidental text selection breaks the experience.
- **Default state**: Text interaction is enabled by default.
- **Runtime toggling**: Changes apply to the WebView's configuration. If a change does not take effect immediately on already-rendered content, reload the WebView content to apply it.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/text-interaction/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/text-interaction/LICENSE).
