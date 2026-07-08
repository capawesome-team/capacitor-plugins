# Capacitor Screenshot Plugin

Capacitor plugin for taking screenshots.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Screenshot plugin is one of the most capable screen capture solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 📸 **Easy screenshots**: Simple one-method API for taking screenshots.
- 🌐 **Web support**: Uses html2canvas for web platform screenshot capture.
- 📱 **Native capture**: High-quality native screenshot capture on mobile.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Screenshot plugin is typically used whenever an app needs to capture its own screen, for example:

- **Bug reporting and feedback**: Attach a screenshot of the current screen to bug reports or feedback forms.
- **Sharing app content**: Capture achievements, results, or other screens so users can share them.
- **Support tickets**: Capture the current state of the app to help your support team reproduce issues.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-screenshot` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-screenshot
npx cap sync
```

If you are using the Web platform, you must also install the `html2canvas` package:

```bash
npm i html2canvas
```

## Usage

The following example shows how to take a screenshot of the current screen.

### Take a screenshot

Take a screenshot of the current screen. The result contains the file path of the screenshot on Android and iOS, or a data URI on the Web:

```ts
import { Screenshot } from '@capawesome/capacitor-screenshot';

const take = async () => {
  const { uri } = await Screenshot.take();
  console.log('Screenshot saved at:', uri);
};
```

## API

<docgen-index>

* [`take()`](#take)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### take()

```typescript
take() => Promise<TakeResult>
```

Take a screenshot.

**Returns:** <code>Promise&lt;<a href="#takeresult">TakeResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### Interfaces


#### TakeResult

| Prop      | Type                | Description                                                          | Since |
| --------- | ------------------- | -------------------------------------------------------------------- | ----- |
| **`uri`** | <code>string</code> | The file path (Android and iOS) or data URI (Web) of the screenshot. | 6.0.0 |

</docgen-api>

## FAQ

### What does the take method return on each platform?

On Android and iOS, the `take()` method returns the file path of the captured screenshot. On the Web, it returns a data URI instead, since the screenshot is rendered with `html2canvas` in the browser.

### Why do I need to install html2canvas?

The `html2canvas` package is only required if you use the plugin on the Web platform, where it is used to render the screenshot. On Android and iOS, the screenshot is captured natively and `html2canvas` is not needed. See the [Installation](#installation) section for details.

### Can I prevent screenshots of my app?

No, this plugin only takes screenshots. If you want to block screenshots, hide sensitive app content in the app switcher, or detect when a screenshot is taken, use the [Privacy Screen](https://capawesome.io/docs/sdks/capacitor/privacy-screen/) plugin instead.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Privacy Screen](https://capawesome.io/docs/sdks/capacitor/privacy-screen/): Hide sensitive app content in the app switcher, block screenshots, and detect when a screenshot is taken.
- [Photo Editor](https://capawesome.io/docs/sdks/capacitor/photo-editor/): Let the user edit a photo, for example a captured screenshot.
- [File Opener](https://capawesome.io/docs/sdks/capacitor/file-opener/): Open a file with the default application.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screenshot/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screenshot/LICENSE).
