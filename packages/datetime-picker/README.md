# Capacitor Datetime Picker Plugin

Capacitor plugin for seamless date and time selection with advanced features like localization, theming, and more. Available for Android and iOS.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Datetime Picker plugin is one of the most feature-rich date and time selection solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📅 **Multiple modes**: Date, time, datetime, and month picker modes.
- 🌍 **Localization**: Support for BCP 47 language tags.
- 🎨 **Theming**: Auto, light, and dark theme support.
- ⚡ **Custom formats**: Define your own date/time format strings.
- 🔒 **Min/Max constraints**: Set minimum and maximum selectable dates/times.
- 📱 **Native UI**: Uses platform-specific picker components.
- ⚙️ **Flexible configuration**: Customizable button texts and picker modes.
- 🤝 **Compatibility**: Works alongside the [Action Sheet](https://capawesome.io/docs/sdks/capacitor/action-sheet/), [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/) and [Localization](https://capawesome.io/docs/sdks/capacitor/localization/) plugins.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Datetime Picker plugin is typically used whenever an app needs the user to enter a date or time, for example:

- **Appointment booking**: Let users pick a date and time for a reservation and restrict the selection with minimum and maximum constraints.
- **Profile forms**: Ask for a date of birth with a native, localized date picker.
- **Reminders and alarms**: Let users choose a time of day using the native time picker, optionally with a custom minute interval on iOS.
- **Reports and statements**: Let users select a month and year only, for example to filter a monthly report, using the `month` mode.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-datetime-picker` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome-team/capacitor-datetime-picker
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                                            | iOS                                                                                                                                            |
| -------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/184545710-a837f45f-e335-4903-b3a9-e1f30b42163f.gif" width="324" alt="Android Demo" /> | <img src="https://user-images.githubusercontent.com/13857929/184545717-a10291e4-95fe-4453-91f7-c04246c61dec.gif" width="266" alt="iOS Demo" /> |

## Usage

The following examples show how to present a date, time, or datetime picker, and how to let the user pick a month and year only.

### Present a date, time, or datetime picker

Open the native picker with the `present(...)` method. Use the `mode` option to choose between a date, time, or datetime picker, and customize the theme, locale, and button texts as needed. Only available on Android and iOS:

```typescript
import { DatetimePicker } from '@capawesome-team/capacitor-datetime-picker';

const present = async () => {
  const date = new Date('1995-12-24T02:23:00');

  const { value } = await DatetimePicker.present({
    cancelButtonText: 'Cancel',
    doneButtonText: 'Ok',
    mode: 'time',
    value: date.toISOString(),
    theme: 'dark',
    locale: 'en-US',
  });

  return value;
};
```

### Let the user pick a month and year only

Use the `month` mode to let the user pick a month and year without a day or time, for example to filter a monthly report. The returned value is the first day of the selected month:

```typescript
import { DatetimePicker } from '@capawesome-team/capacitor-datetime-picker';

const presentMonth = async () => {
  const { value } = await DatetimePicker.present({
    cancelButtonText: 'Cancel',
    doneButtonText: 'Ok',
    mode: 'month',
    value: '2026-05',
    format: 'yyyy-MM',
    locale: 'en-US',
  });

  return value;
};
```

## API

<docgen-index>

* [`present(...)`](#present)
* [`cancel()`](#cancel)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### present(...)

```typescript
present(options?: PresentOptions | undefined) => Promise<PresentResult>
```

Open the datetime picker.

An error is thrown if the input is canceled or dismissed by the user.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#presentoptions">PresentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#presentresult">PresentResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### cancel()

```typescript
cancel() => Promise<void>
```

Cancel the currently active datetime picker.

If there is no active picker, this method does nothing.

Only available on Android and iOS.

**Since:** 7.2.0

--------------------


### Interfaces


#### PresentResult

| Prop        | Type                | Description                                                                                          | Since |
| ----------- | ------------------- | ---------------------------------------------------------------------------------------------------- | ----- |
| **`value`** | <code>string</code> | The value entered by the user. The format of this value matches the value of the `format` parameter. | 0.0.1 |


#### PresentOptions

| Prop                        | Type                                                   | Description                                                                                                                                                                                                                                                                                                                                                           | Default                                     | Since |
| --------------------------- | ------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`cancelButtonText`**      | <code>string</code>                                    | The cancel button text.                                                                                                                                                                                                                                                                                                                                               | <code>'Cancel'</code>                       | 0.0.1 |
| **`doneButtonText`**        | <code>string</code>                                    | The done button text.                                                                                                                                                                                                                                                                                                                                                 | <code>'Ok'</code>                           | 0.0.1 |
| **`format`**                | <code>string</code>                                    | The format in which values are received and returned.                                                                                                                                                                                                                                                                                                                 | <code>'yyyy-MM-dd'T'HH:mm:ss.sss'Z''</code> | 0.0.1 |
| **`locale`**                | <code>string</code>                                    | BCP 47 language tag to define the language of the UI.                                                                                                                                                                                                                                                                                                                 |                                             | 0.0.2 |
| **`max`**                   | <code>string</code>                                    | The latest date and time to accept. The format of this value must match the value of the `format` parameter. This value must specify a date string later than or equal to the one specified by the `min` attribute.                                                                                                                                                   |                                             | 0.0.1 |
| **`min`**                   | <code>string</code>                                    | The earliest date and time to accept. The format of this value must match the value of the `format` parameter. This value must specify a date string earlier than or equal to the one specified by the `max` attribute.                                                                                                                                               |                                             | 0.0.1 |
| **`mode`**                  | <code>'date' \| 'time' \| 'datetime' \| 'month'</code> | Whether you want a date, time, datetime or month picker. The `month` mode (since `8.1.0`) lets the user pick a month and year only. Unlike the other modes, it uses a custom (non-native) UI on both platforms, because neither Android nor iOS provide a native month-only picker. The returned value is the first day of the selected month at 00:00:00 local time. | <code>'datetime'</code>                     | 0.0.1 |
| **`theme`**                 | <code>'auto' \| 'light' \| 'dark'</code>               | Choose the theme that the datetime picker should have. With `auto` the system theme is used. This value overwrites the `theme` configuration value. Only available on Android and iOS. Spinner options only available on Android                                                                                                                                      |                                             | 0.0.1 |
| **`value`**                 | <code>string</code>                                    | The predefined value when opening the picker. The format of this value must match the value of the `format` parameter.                                                                                                                                                                                                                                                |                                             | 0.0.1 |
| **`androidTimePickerMode`** | <code>'clock' \| 'spinner'</code>                      | Whether to use the spinner or clock mode for the time picker on Android. This value overwrites the `androidTimePickerMode` configuration value. Only available on Android.                                                                                                                                                                                            |                                             | 5.1.0 |
| **`androidDatePickerMode`** | <code>'spinner' \| 'calendar'</code>                   | Whether to use the calendar or spinner mode for the date picker on Android. This value overwrites the `androidDatePickerMode` configuration value. Only available on Android.                                                                                                                                                                                         |                                             | 5.1.0 |
| **`minuteInterval`**        | <code>number</code>                                    | The minute interval of the time picker. This controls the granularity of the minute selector (e.g., 15 for 0, 15, 30, 45). The value must be evenly divisible into 60. Only available on iOS when using time or datetime modes. On Android, this parameter is ignored.                                                                                                | <code>1</code>                              | 7.1.0 |

</docgen-api>

## FAQ

### What happens when the user cancels the picker?

The `present(...)` method throws an error if the input is canceled or dismissed by the user. Make sure to catch this error in your code, for example to keep the previous value.

### How can I close the picker programmatically?

Use the `cancel()` method to cancel the currently active datetime picker, for example when your app is sent to the background. If there is no active picker, this method does nothing.

### How do I restrict which dates the user can select?

Use the `min` and `max` options of the `present(...)` method to define the earliest and latest date and time to accept. The format of both values must match the value of the `format` parameter.

### How do I let the user pick only a month and year?

Set the `mode` option to `month`. Unlike the other modes, the month picker uses a custom (non-native) UI on both platforms, because neither Android nor iOS provide a native month-only picker. The returned value is the first day of the selected month at 00:00:00 local time. See the [usage example](#let-the-user-pick-a-month-and-year-only) above.

### Can I change the language and theme of the picker?

Yes, use the `locale` option with a BCP 47 language tag to define the language of the UI, and the `theme` option to choose between `auto`, `light`, and `dark`. With `auto`, the system theme is used.

### Does this plugin work on the Web?

No, this plugin is only available on Android and iOS, where it uses platform-specific picker components. The `present(...)` and `cancel()` methods are not available on the Web.

## Related Plugins

- [Action Sheet](https://capawesome.io/docs/sdks/capacitor/action-sheet/): Show native action sheets.
- [Dialog](https://capawesome.io/docs/sdks/capacitor/dialog/): Display native alert, confirm, and prompt dialogs.
- [Localization](https://capawesome.io/docs/sdks/capacitor/localization/): Read the user's preferred locales to localize the picker.

## Credits

The iOS implementation of this plugin is based on [RPicker](https://github.com/rheyansh/RPicker) which is licensed under [MIT](https://github.com/rheyansh/RPicker/blob/master/LICENSE).

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/datetime-picker/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/datetime-picker/LICENSE).