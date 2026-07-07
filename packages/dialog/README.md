# Capacitor Dialog Plugin

Capacitor plugin for native alert, confirm, and prompt dialogs.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 💬 **Alert**: Display a native alert dialog with a single button.
- ❓ **Confirm**: Ask the user to confirm or cancel an action.
- ⌨️ **Prompt**: Request text input from the user.
- 🌐 **Cross-platform**: Works on Android, iOS, and the web.
- 🔒 **App Store safe**: Uses only official platform APIs.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-dialog` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-dialog
npx cap sync
```

### Android

No additional configuration is required for this plugin.

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Dialog } from '@capawesome/capacitor-dialog';

const alert = async () => {
  await Dialog.alert({
    title: 'Success',
    message: 'Your changes have been saved.',
  });
};

const confirm = async () => {
  const { value } = await Dialog.confirm({
    title: 'Confirm',
    message: 'Do you want to delete this item?',
  });
  console.log('Confirmed:', value);
};

const prompt = async () => {
  const { value, canceled } = await Dialog.prompt({
    title: 'Name',
    message: 'What is your name?',
    inputPlaceholder: 'Enter your name',
  });
  console.log('Value:', value, 'Canceled:', canceled);
};
```

## API

<docgen-index>

* [`alert(...)`](#alert)
* [`confirm(...)`](#confirm)
* [`prompt(...)`](#prompt)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### alert(...)

```typescript
alert(options: AlertOptions) => Promise<void>
```

Display an alert dialog with a single button.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#alertoptions">AlertOptions</a></code> |

**Since:** 0.1.0

--------------------


### confirm(...)

```typescript
confirm(options: ConfirmOptions) => Promise<ConfirmResult>
```

Display a confirmation dialog with two buttons.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#confirmoptions">ConfirmOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#confirmresult">ConfirmResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### prompt(...)

```typescript
prompt(options: PromptOptions) => Promise<PromptResult>
```

Display a prompt dialog with a text input, a confirm and a cancel button.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#promptoptions">PromptOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#promptresult">PromptResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### AlertOptions

| Prop              | Type                | Description                                                                                                         | Default           | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`buttonTitle`** | <code>string</code> | The title of the button that confirms the dialog. On the web, the button title cannot be customized and is ignored. | <code>'OK'</code> | 0.1.0 |
| **`message`**     | <code>string</code> | The message to display in the dialog.                                                                               |                   | 0.1.0 |
| **`title`**       | <code>string</code> | The title of the dialog. On the web, the title cannot be customized and is ignored.                                 |                   | 0.1.0 |


#### ConfirmResult

| Prop        | Type                 | Description                            | Since |
| ----------- | -------------------- | -------------------------------------- | ----- |
| **`value`** | <code>boolean</code> | Whether the user confirmed the dialog. | 0.1.0 |


#### ConfirmOptions

| Prop                    | Type                | Description                                                                                                         | Default               | Since |
| ----------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`cancelButtonTitle`** | <code>string</code> | The title of the button that cancels the dialog. On the web, the button title cannot be customized and is ignored.  | <code>'Cancel'</code> | 0.1.0 |
| **`message`**           | <code>string</code> | The message to display in the dialog.                                                                               |                       | 0.1.0 |
| **`okButtonTitle`**     | <code>string</code> | The title of the button that confirms the dialog. On the web, the button title cannot be customized and is ignored. | <code>'OK'</code>     | 0.1.0 |
| **`title`**             | <code>string</code> | The title of the dialog. On the web, the title cannot be customized and is ignored.                                 |                       | 0.1.0 |


#### PromptResult

| Prop           | Type                 | Description                           | Since |
| -------------- | -------------------- | ------------------------------------- | ----- |
| **`canceled`** | <code>boolean</code> | Whether the user canceled the dialog. | 0.1.0 |
| **`value`**    | <code>string</code>  | The value of the text input.          | 0.1.0 |


#### PromptOptions

| Prop                    | Type                | Description                                                                                                         | Default               | Since |
| ----------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`cancelButtonTitle`** | <code>string</code> | The title of the button that cancels the dialog. On the web, the button title cannot be customized and is ignored.  | <code>'Cancel'</code> | 0.1.0 |
| **`inputPlaceholder`**  | <code>string</code> | The placeholder of the text input. On the web, the placeholder cannot be customized and is ignored.                 |                       | 0.1.0 |
| **`inputText`**         | <code>string</code> | The initial value of the text input.                                                                                |                       | 0.1.0 |
| **`message`**           | <code>string</code> | The message to display in the dialog.                                                                               |                       | 0.1.0 |
| **`okButtonTitle`**     | <code>string</code> | The title of the button that confirms the dialog. On the web, the button title cannot be customized and is ignored. | <code>'OK'</code>     | 0.1.0 |
| **`title`**             | <code>string</code> | The title of the dialog. On the web, the title cannot be customized and is ignored.                                 |                       | 0.1.0 |

</docgen-api>

## Migrating from `@capacitor/dialog`

This plugin is API-compatible with the official [`@capacitor/dialog`](https://github.com/ionic-team/capacitor-plugins/tree/main/dialog) plugin, with a single difference: the `prompt(...)` result uses the property `canceled` (one `l`) instead of `cancelled` (two `l`s).

| `@capacitor/dialog`                | `@capawesome/capacitor-dialog`     |
| ---------------------------------- | ---------------------------------- |
| `alert({ title, message, buttonTitle })` | `alert({ title, message, buttonTitle })` |
| `confirm({ ... }) → { value }`     | `confirm({ ... }) → { value }`     |
| `prompt({ ... }) → { value, cancelled }` | `prompt({ ... }) → { value, canceled }` |

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/dialog/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/dialog/LICENSE).
