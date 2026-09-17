# Capacitor Option Picker Plugin

Capacitor plugin that lets the user pick an option from a list using a native picker.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📋 **Native Picker**: Present the platform's native single-choice picker: a wheel picker on iOS and a Material 3 dialog on Android.
- ✅ **Preselection**: Open the picker with the current value already selected.
- 📝 **Title & Buttons**: Customize the title and the texts of the done and cancel buttons.
- 📚 **Long Lists**: Scroll smoothly through long lists of options on both platforms.
- 🎨 **Themed**: Follows the system appearance or a forced light or dark theme, and adopts your app's Material theme on Android.
- 🔒 **App Store safe**: Uses only official platform APIs.
- 🤝 **Compatibility**: Works alongside the [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/) plugin.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Option Picker plugin is typically used as a native replacement for HTML `<select>` elements, for example:

- **Form inputs**: Let the user pick a country, language, or category in a form.
- **Settings screens**: Let the user choose a value such as a unit, a theme, or a refresh interval.
- **Filters and sorting**: Let the user pick a sort order or a filter category in a list view.
- **Guided flows**: Ask the user to pick a value before continuing to the next step.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-option-picker` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-option-picker
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

The following examples show how to present a native option picker and read the selected value.

### Present a picker

Present a native picker with a title and a list of options. Use the `value` option to preselect the current value. The result contains the value of the selected option. Only available on Android and iOS:

```typescript
import { OptionPicker } from '@capawesome/capacitor-option-picker';

const presentPicker = async () => {
  const { value } = await OptionPicker.present({
    title: 'Select a country',
    options: [
      { label: 'Germany', value: 'de' },
      { label: 'France', value: 'fr' },
      { label: 'Spain', value: 'es' },
    ],
    value: 'fr',
  });
  console.log('Selected value:', value);
};
```

### Handle cancellation

If the user cancels or dismisses the picker, the returned promise is rejected with the `ErrorCode.Canceled` error code:

```typescript
import { ErrorCode, OptionPicker } from '@capawesome/capacitor-option-picker';

const presentPicker = async () => {
  try {
    const { value } = await OptionPicker.present({
      options: [
        { label: 'Small', value: 's' },
        { label: 'Medium', value: 'm' },
        { label: 'Large', value: 'l' },
      ],
    });
    console.log('Selected value:', value);
  } catch (error) {
    if (error.code === ErrorCode.Canceled) {
      console.log('The user canceled the picker.');
    }
  }
};
```

## API

<docgen-index>

* [`present(...)`](#present)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### present(...)

```typescript
present(options: PresentOptions) => Promise<PresentResult>
```

Present a native picker that lets the user select one option from a list.

The returned promise is rejected with the `ErrorCode.Canceled` error code
if the user cancels or dismisses the picker.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#presentoptions">PresentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#presentresult">PresentResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### PresentResult

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`value`** | <code>string</code> | The value of the selected option. | 0.1.0 |


#### PresentOptions

| Prop                   | Type                                     | Description                                                                                                                | Default               | Since |
| ---------------------- | ---------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`cancelButtonText`** | <code>string</code>                      | The text of the cancel button.                                                                                             | <code>'Cancel'</code> | 0.1.0 |
| **`doneButtonText`**   | <code>string</code>                      | The text of the done button.                                                                                               | <code>'Ok'</code>     | 0.1.0 |
| **`options`**          | <code>PickerOption[]</code>              | The options the user can choose from. Must contain at least one option.                                                    |                       | 0.1.0 |
| **`theme`**            | <code>'auto' \| 'light' \| 'dark'</code> | The theme of the picker. `auto` follows the system appearance.                                                             | <code>'auto'</code>   | 0.1.0 |
| **`title`**            | <code>string</code>                      | The title of the picker.                                                                                                   |                       | 0.1.0 |
| **`value`**            | <code>string</code>                      | The value of the option that is selected when the picker opens. If no option has this value, the first option is selected. |                       | 0.1.0 |


#### PickerOption

| Prop        | Type                | Description                                     | Since |
| ----------- | ------------------- | ----------------------------------------------- | ----- |
| **`label`** | <code>string</code> | The text displayed for the option.              | 0.1.0 |
| **`value`** | <code>string</code> | The value returned when the option is selected. | 0.1.0 |

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It presents the platform's own single-choice picker on Android and iOS through a fully typed API: a wheel picker with a toolbar on iOS and a Material 3 dialog on Android, the same controls the operating systems use for `<select>` elements. The current value can be preselected, the title and button texts are customizable, and cancellation is reported with a dedicated error code, all using only official platform APIs. Actively maintained against the latest Capacitor version, it lets a single dependency replace non-native dropdown components.

### Which native UI is used on each platform?

On iOS, the plugin presents a `UIPickerView` in a bottom sheet with a toolbar that contains the cancel button, the title, and the done button. On Android, the plugin presents a Material 3 dialog with a scrollable single-choice list and a done and cancel button. Both follow the system appearance, including dark mode.

### Does the Android dialog use my app's theme?

Yes, if your app uses a Material Components or Material 3 theme, the dialog inherits its colors and shapes. If your app uses an AppCompat theme (the Capacitor default), the dialog uses a Material 3 theme with the system's dynamic colors on Android 12 and later, and the Material 3 baseline colors on older versions. To use your own brand colors in that case, switch your app theme to a Material 3 theme such as `Theme.Material3.DayNight.NoActionBar`.

### Can I force a light or dark theme?

Yes, set the `theme` option to `light` or `dark`. By default (`auto`), the picker follows the system appearance.

### What happens when the user cancels the picker?

The returned promise is rejected with the `ErrorCode.Canceled` error code. This happens when the user taps the cancel button, taps outside of the picker, or presses the back button on Android. See the [Handle cancellation](#handle-cancellation) example.

### Which option is selected when the picker opens?

The option whose value matches the `value` option is selected. If no `value` is provided or no option matches, the first option is selected.

### When should I use the Option Picker instead of the Action Sheet plugin?

Use the Option Picker when the user has to choose a value, for example in a form or a settings screen. It is designed for long lists and highlights the current value. Use the [Action Sheet](https://capawesome.io/docs/sdks/capacitor/action-sheet/) plugin when the user has to choose between a few actions to perform.

### Does this plugin work on the Web?

No, the `present(...)` method is only available on Android and iOS. On the Web, use a regular HTML `<select>` element instead.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Action Sheet](https://capawesome.io/docs/sdks/capacitor/action-sheet/): Show native action sheets.
- [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/): Let the user pick a date and time with a native picker.
- [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/): Display native alert, confirm, and prompt dialogs.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/option-picker/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/option-picker/LICENSE).
