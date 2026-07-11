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

## Use Cases

The Dialog plugin is typically used whenever an app needs a quick native interaction with the user, for example:

- **Confirmations**: Ask the user to confirm a destructive action, such as deleting an item, before executing it.
- **Notices**: Inform the user about the result of an action, such as saved changes or an error, with a simple alert.
- **Quick text input**: Ask the user for a short text value, such as a name, without building a custom form.
- **Native look and feel**: Replace the browser's `window.alert`, `window.confirm`, and `window.prompt` with native dialogs on Android and iOS.

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

The following examples show how to display an alert dialog, ask the user for confirmation, and request text input from the user.

### Display an alert dialog

Show a message with a single button, for example to inform the user about the result of an action:

```typescript
import { Dialog } from '@capawesome/capacitor-dialog';

const alert = async () => {
  await Dialog.alert({
    title: 'Success',
    message: 'Your changes have been saved.',
  });
};
```

### Ask the user for confirmation

Show a confirmation dialog with two buttons. The result tells you whether the user confirmed the dialog:

```typescript
import { Dialog } from '@capawesome/capacitor-dialog';

const confirm = async () => {
  const { value } = await Dialog.confirm({
    title: 'Confirm',
    message: 'Do you want to delete this item?',
  });
  console.log('Confirmed:', value);
};
```

### Request text input from the user

Show a prompt dialog with a text input, a confirm and a cancel button. The result contains the entered value and whether the user canceled the dialog:

```typescript
import { Dialog } from '@capawesome/capacitor-dialog';

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

## FAQ

### How is this plugin different from other similar plugins?

It brings native alert, confirm, and prompt dialogs to Android, iOS, and the web through a single, fully typed API, using only official platform APIs so it stays safe for App Store and Google Play submissions. It supports both CocoaPods and Swift Package Manager on iOS and is actively maintained against the latest Capacitor and OS versions, with customizable button titles on Android and iOS. If you only need a quick native message, it is refreshingly simple to drop in; if you need consistent alert, confirm, and prompt behavior across every platform, it is built for exactly that.

### How is this plugin different from the official `@capacitor/dialog` plugin?

This plugin is API-compatible with the official `@capacitor/dialog` plugin, with a single difference: the `prompt(...)` result uses the property `canceled` (one `l`) instead of `cancelled` (two `l`s). See the [migration table](#migrating-from-capacitordialog) above for the complete method mapping.

### Can I customize the dialog buttons?

Yes, on Android and iOS you can customize the button titles using the `buttonTitle`, `okButtonTitle`, and `cancelButtonTitle` options. On the web, the button titles cannot be customized and are ignored, because the browser's built-in dialogs are used.

### How do I know whether the user canceled a prompt?

The result of the `prompt(...)` method contains a `canceled` property that is `true` if the user canceled the dialog, in addition to the `value` property with the text input. For confirmation dialogs, the `value` property of the `confirm(...)` result tells you whether the user confirmed the dialog.

### Why is the dialog title not displayed on the web?

On the web, the title and button titles cannot be customized and are ignored, because the plugin uses the browser's built-in dialogs. Only the message is displayed. On Android and iOS, the title is fully supported.

### Is this plugin safe to use for App Store submissions?

Yes, the plugin uses only official platform APIs to display the dialogs, so it is safe to use in apps submitted to the Apple App Store and Google Play Store.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Action Sheet](https://capawesome.io/docs/sdks/capacitor/action-sheet/): Show native action sheets.
- [Toast](https://capawesome.io/docs/sdks/capacitor/toast/): Show native toast notifications.
- [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/): Let the user pick a date and time with a native picker.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/dialog/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/dialog/LICENSE).
