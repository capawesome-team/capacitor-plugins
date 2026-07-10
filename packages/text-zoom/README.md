# Capacitor Text Zoom Plugin

Capacitor plugin for reading and controlling the WebView text zoom.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔍 **Read zoom**: Read the current text zoom of the WebView.
- 🔠 **Set zoom**: Set the text zoom, e.g. to offer your own text-size setting.
- ♿ **Preferred zoom**: Read the device's preferred text zoom to respect the operating system's font size settings.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Accessibility Preferences](https://capawesome.io/docs/sdks/capacitor/accessibility-preferences/) plugin, whose font scale is a natural input for the text zoom.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Text Zoom plugin is typically used to make the text size of an app adjustable, for example:

- **Custom text-size settings**: Offer an in-app setting that lets users increase or decrease the text size with `setZoom(...)`.
- **Accessibility**: Read the device's preferred text zoom with `getPreferredZoom()` and apply it to respect the operating system's font size settings.
- **Reading-focused apps**: Let users of news, book, or documentation apps adjust the text size to their comfort.
- **Restoring the zoom on app launch**: Read the persisted value from a preferences plugin and re-apply it with `setZoom(...)` on startup.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-text-zoom` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-text-zoom
npx cap sync
```

This plugin is available on **Android** and **iOS**. On Web, all methods reject as unimplemented.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to read the current text zoom, read the device's preferred text zoom, and set the text zoom of the WebView.

### Read the current text zoom

Get the current text zoom of the WebView, where `1.0` equals `100%` (no zoom). Only available on Android and iOS:

```typescript
import { TextZoom } from '@capawesome/capacitor-text-zoom';

const getZoom = async () => {
  const { zoom } = await TextZoom.getZoom();
  return zoom;
};
```

### Read the preferred text zoom of the device

Get the preferred text zoom based on the operating system's font size settings, for example to use it as an input for `setZoom(...)`. Only available on Android and iOS:

```typescript
import { TextZoom } from '@capawesome/capacitor-text-zoom';

const getPreferredZoom = async () => {
  const { zoom } = await TextZoom.getPreferredZoom();
  return zoom;
};
```

### Set the text zoom

Set the text zoom of the WebView. The value must be greater than `0` and is not persisted across app restarts. Only available on Android and iOS:

```typescript
import { TextZoom } from '@capawesome/capacitor-text-zoom';

const setZoom = async () => {
  await TextZoom.setZoom({ zoom: 1.5 });
};
```

## Migration from `@capacitor/text-zoom`

This plugin is a drop-in alternative to the official [`@capacitor/text-zoom`](https://www.npmjs.com/package/@capacitor/text-zoom) plugin with a slightly more explicit API:

| `@capacitor/text-zoom`       | `@capawesome/capacitor-text-zoom`      |
| ---------------------------- | -------------------------------------- |
| `get()` → `{ value }`        | `getZoom()` → `{ zoom }`               |
| `getPreferred()` → `{ value }` | `getPreferredZoom()` → `{ zoom }`    |
| `set({ value })`             | `setZoom({ zoom })`                    |

## API

<docgen-index>

* [`getPreferredZoom()`](#getpreferredzoom)
* [`getZoom()`](#getzoom)
* [`setZoom(...)`](#setzoom)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getPreferredZoom()

```typescript
getPreferredZoom() => Promise<GetPreferredZoomResult>
```

Get the preferred text zoom of the device based on the operating system's
font size settings.

This value can be used as an input for `setZoom(...)` to respect the
user's system font size preference.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getpreferredzoomresult">GetPreferredZoomResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getZoom()

```typescript
getZoom() => Promise<GetZoomResult>
```

Get the current text zoom of the WebView.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getzoomresult">GetZoomResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### setZoom(...)

```typescript
setZoom(options: SetZoomOptions) => Promise<void>
```

Set the text zoom of the WebView.

The zoom is not persisted across app restarts. Persist the value with a
preferences plugin and re-apply it on app launch if needed.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#setzoomoptions">SetZoomOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### GetPreferredZoomResult

| Prop       | Type                | Description                                                  | Since |
| ---------- | ------------------- | ------------------------------------------------------------ | ----- |
| **`zoom`** | <code>number</code> | The preferred text zoom where `1.0` equals `100%` (no zoom). | 0.1.0 |


#### GetZoomResult

| Prop       | Type                | Description                                                | Since |
| ---------- | ------------------- | ---------------------------------------------------------- | ----- |
| **`zoom`** | <code>number</code> | The current text zoom where `1.0` equals `100%` (no zoom). | 0.1.0 |


#### SetZoomOptions

| Prop       | Type                | Description                                                                         | Since |
| ---------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`zoom`** | <code>number</code> | The text zoom to set where `1.0` equals `100%` (no zoom). Must be greater than `0`. | 0.1.0 |

</docgen-api>

## Platform Notes

### iOS

The text zoom is implemented via the `-webkit-text-size-adjust` CSS property, which only takes effect when the WebView runs in **mobile content mode**. On iPad, the WebView defaults to desktop content mode, so `setZoom(...)` has no visible effect unless you set the following in your `capacitor.config`:

```json
{
  "ios": {
    "preferredContentMode": "mobile"
  }
}
```

### Persistence

The text zoom is **not** persisted across app restarts. Persist the value with a preferences plugin (e.g. [`@capacitor/preferences`](https://www.npmjs.com/package/@capacitor/preferences)) and re-apply it on app launch if needed.

## FAQ

### How is this plugin different from the official `@capacitor/text-zoom` plugin?

This plugin is a drop-in alternative to the official `@capacitor/text-zoom` plugin with a slightly more explicit API: `get()` becomes `getZoom()`, `getPreferred()` becomes `getPreferredZoom()`, and `set({ value })` becomes `setZoom({ zoom })`. See the [migration table](#migration-from-capacitortext-zoom) above for a side-by-side comparison.

### Why does `setZoom` have no effect on iPad?

On iOS, the text zoom is implemented via the `-webkit-text-size-adjust` CSS property, which only takes effect when the WebView runs in mobile content mode. On iPad, the WebView defaults to desktop content mode, so you need to set `preferredContentMode` to `mobile` in the `ios` section of your `capacitor.config` (see [Platform Notes](#platform-notes)).

### Is the text zoom persisted across app restarts?

No, the text zoom is not persisted. Persist the value with a preferences plugin such as `@capacitor/preferences` and re-apply it with `setZoom(...)` on app launch if needed.

### How do I respect the user's system font size?

Call `getPreferredZoom()` to read the preferred text zoom of the device based on the operating system's font size settings, and pass the returned value to `setZoom(...)`. This way, the text size of your app follows the user's system preference.

### Which platforms are supported?

The plugin is available on Android and iOS. On Web, all methods reject as unimplemented.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accessibility Preferences](https://capawesome.io/docs/sdks/capacitor/accessibility-preferences/): Read the user's system accessibility preferences, such as the font scale.
- [Text Interaction](https://capawesome.io/docs/sdks/capacitor/text-interaction/): Enable and disable text interaction (selection, magnifier, callout menu) in the app's WebView.
- [Screen Reader](https://capawesome.io/docs/sdks/capacitor/screen-reader/): Interact with screen readers.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/text-zoom/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/text-zoom/LICENSE).
