# @capawesome/capacitor-screenshot

Capacitor plugin for taking screenshots.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for capturing screenshots. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 📸 **Easy screenshots**: Simple one-method API for taking screenshots.
- 🌐 **Web support**: Uses html2canvas for web platform screenshot capture.
- 📱 **Native capture**: High-quality native screenshot capture on mobile.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the Capawesome Skills to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills
```

Then use the following prompt:

> Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-screenshot` plugin in my project.

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screenshot/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screenshot/LICENSE).
