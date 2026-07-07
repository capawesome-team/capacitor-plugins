# Capacitor Action Sheet Plugin

Capacitor plugin for native action sheets.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📋 **Action Sheet**: Present a native action sheet with a list of buttons.
- 🎨 **Button Styles**: Highlight destructive actions and pin a cancel button.
- 📝 **Title & Message**: Show a title and message on both Android and iOS.
- 📱 **iPad Ready**: Automatically anchored as a popover on iPad.
- 🔒 **App Store safe**: Uses only official platform APIs.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/) plugin.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-action-sheet` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-action-sheet
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidxMaterialVersion` version of `com.google.android.material:material` (default: `1.12.0`)

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { ActionSheet, ActionSheetButtonStyle } from '@capawesome/capacitor-action-sheet';

const showActions = async () => {
  const { index, canceled } = await ActionSheet.showActions({
    title: 'Photo Options',
    message: 'Select an option to perform.',
    options: [
      { title: 'Upload' },
      { title: 'Share' },
      { title: 'Delete', style: ActionSheetButtonStyle.Destructive },
      { title: 'Cancel', style: ActionSheetButtonStyle.Cancel },
    ],
  });
  console.log('Index:', index, 'Canceled:', canceled);
};
```

## API

<docgen-index>

* [`showActions(...)`](#showactions)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### showActions(...)

```typescript
showActions(options: ShowActionsOptions) => Promise<ShowActionsResult>
```

Show an action sheet with a list of buttons.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#showactionsoptions">ShowActionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#showactionsresult">ShowActionsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### ShowActionsResult

| Prop           | Type                 | Description                                                                                                                                        | Since |
| -------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`canceled`** | <code>boolean</code> | Whether the action sheet was canceled by selecting a cancel button or by dismissing the action sheet.                                              | 0.1.0 |
| **`index`**    | <code>number</code>  | The index of the selected button in the `options` array (zero-based). If the action sheet was canceled without a cancel button, the index is `-1`. | 0.1.0 |


#### ShowActionsOptions

| Prop             | Type                             | Description                                                                                                                                                                                              | Default           | Since |
| ---------------- | -------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`cancelable`** | <code>boolean</code>             | Whether the action sheet can be dismissed by tapping outside of it or pressing the back button (Android). On iOS, the action sheet can always be dismissed by tapping outside of it (a system behavior). | <code>true</code> | 0.1.0 |
| **`message`**    | <code>string</code>              | The message to display below the title.                                                                                                                                                                  |                   | 0.1.0 |
| **`options`**    | <code>ActionSheetButton[]</code> | The buttons to display in the action sheet.                                                                                                                                                              |                   | 0.1.0 |
| **`title`**      | <code>string</code>              | The title of the action sheet.                                                                                                                                                                           |                   | 0.1.0 |


#### ActionSheetButton

| Prop        | Type                                                                      | Description              | Default                                     | Since |
| ----------- | ------------------------------------------------------------------------- | ------------------------ | ------------------------------------------- | ----- |
| **`style`** | <code><a href="#actionsheetbuttonstyle">ActionSheetButtonStyle</a></code> | The style of the button. | <code>ActionSheetButtonStyle.Default</code> | 0.1.0 |
| **`title`** | <code>string</code>                                                       | The title of the button. |                                             | 0.1.0 |


### Enums


#### ActionSheetButtonStyle

| Members           | Value                      | Description                                                                                  | Since |
| ----------------- | -------------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`Cancel`**      | <code>'CANCEL'</code>      | A button that cancels the action sheet. It should be the last button in the `options` array. | 0.1.0 |
| **`Default`**     | <code>'DEFAULT'</code>     | A button with the default style.                                                             | 0.1.0 |
| **`Destructive`** | <code>'DESTRUCTIVE'</code> | A button that indicates a destructive action.                                                | 0.1.0 |

</docgen-api>

## Migrating from `@capacitor/action-sheet`

This plugin is API-compatible with the official [`@capacitor/action-sheet`](https://github.com/ionic-team/capacitor-plugins/tree/main/action-sheet) plugin. The `ActionSheetButtonStyle` enum uses the same values (`DEFAULT`, `DESTRUCTIVE`, `CANCEL`), so no enum changes are required. The following differences apply:

| `@capacitor/action-sheet`                       | `@capawesome/capacitor-action-sheet`                   |
| ----------------------------------------------- | ------------------------------------------------------ |
| `showActions({ title, message, options })`      | `showActions({ title, message, options, cancelable })` |
| `showActions(...) → { index }`                  | `showActions(...) → { index, canceled }`               |
| `message` and button styles ignored on Android  | `message` and button styles rendered on Android        |
| `cancelable` not available                      | `cancelable` (default `true`)                          |

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/action-sheet/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/action-sheet/LICENSE).
