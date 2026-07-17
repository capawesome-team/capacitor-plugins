# Capacitor Localization Plugin

Capacitor plugin to read the user's localization preferences, such as preferred locales, time zone, and regional formatting settings.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🌍 **Preferred locales**: Read the user's preferred locales, ordered by preference.
- 💱 **Regional formatting**: Read currency, decimal, and grouping separators per locale.
- ↔️ **Text direction**: Detect whether a locale is written left-to-right or right-to-left.
- 📏 **Measurement system**: Detect the metric, US, or UK measurement system.
- 🕐 **Settings**: Read the time zone, clock format, and first day of the week.
- 🖥️ **Cross-platform**: Full support for Android, iOS, and Web.
- 🤝 **Compatibility**: Works alongside the [App Language](https://capawesome.io/docs/sdks/capacitor/app-language/), [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/) and [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Localization plugin is typically used to adapt an app to the user's language and regional preferences, for example:

- **Localized formatting**: Format numbers and prices with the correct decimal separator, grouping separator, and currency symbol for the user's locale.
- **Right-to-left layouts**: Switch the layout direction of your app based on the text direction of the user's preferred language.
- **Unit display**: Show distances and weights in metric or imperial units depending on the user's measurement system.
- **Date and time display**: Render times in the user's time zone and clock format, and start calendar views on the correct first day of the week.
- **Language selection**: Preselect the best matching app language from the user's preferred locales.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-localization` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-localization
npx cap sync
```

No additional permissions or configuration are required on any platform.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to read the user's preferred locales and their regional formatting settings.

### Get the user's preferred locales

Read the user's preferred locales, ordered by preference. The first entry is the most preferred locale. Each locale contains the language tag, region code, currency, number separators, text direction, and measurement system:

```typescript
import { Localization } from '@capawesome/capacitor-localization';

const getLocales = async () => {
  const { locales } = await Localization.getLocales();
  return locales;
};
```

### Read the user's regional settings

Read the user's regional formatting settings, such as the time zone, whether a 24-hour clock is preferred, and the first day of the week:

```typescript
import { Localization } from '@capawesome/capacitor-localization';

const getSettings = async () => {
  const settings = await Localization.getSettings();
  return settings;
};
```

## API

<docgen-index>

* [`getLocales()`](#getlocales)
* [`getSettings()`](#getsettings)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getLocales()

```typescript
getLocales() => Promise<GetLocalesResult>
```

Get the user's preferred locales, ordered by preference.

The first entry is the most preferred locale.

**Returns:** <code>Promise&lt;<a href="#getlocalesresult">GetLocalesResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getSettings()

```typescript
getSettings() => Promise<GetSettingsResult>
```

Get the user's regional formatting settings.

**Returns:** <code>Promise&lt;<a href="#getsettingsresult">GetSettingsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetLocalesResult

| Prop          | Type                  | Description                                                                                        | Since |
| ------------- | --------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`locales`** | <code>Locale[]</code> | The user's preferred locales, ordered by preference. The first entry is the most preferred locale. | 0.1.0 |


#### Locale

| Prop                    | Type                                                                    | Description                                                                                                                                 | Since |
| ----------------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`currencyCode`**      | <code>string \| null</code>                                             | The ISO 4217 currency code of the locale. Returns `null` if the currency cannot be determined.                                              | 0.1.0 |
| **`currencySymbol`**    | <code>string \| null</code>                                             | The currency symbol of the locale. Returns `null` if the currency symbol cannot be determined.                                              | 0.1.0 |
| **`decimalSeparator`**  | <code>string \| null</code>                                             | The character used to separate the integer part from the fractional part of a number. Returns `null` if the separator cannot be determined. | 0.1.0 |
| **`groupingSeparator`** | <code>string \| null</code>                                             | The character used to group digits of the integer part of a number. Returns `null` if the separator cannot be determined.                   | 0.1.0 |
| **`languageCode`**      | <code>string</code>                                                     | The ISO 639 language code of the locale.                                                                                                    | 0.1.0 |
| **`languageTag`**       | <code>string</code>                                                     | The BCP 47 language tag of the locale.                                                                                                      | 0.1.0 |
| **`measurementSystem`** | <code><a href="#measurementsystem">MeasurementSystem</a> \| null</code> | The measurement system used by the locale. Returns `null` if the measurement system cannot be determined.                                   | 0.1.0 |
| **`regionCode`**        | <code>string \| null</code>                                             | The ISO 3166 region code of the locale. Returns `null` if the region cannot be determined.                                                  | 0.1.0 |
| **`textDirection`**     | <code><a href="#textdirection">TextDirection</a></code>                 | The writing direction of the locale's language.                                                                                             | 0.1.0 |


#### GetSettingsResult

| Prop                  | Type                 | Description                                                                                                                           | Since |
| --------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`firstDayOfWeek`**  | <code>number</code>  | The first day of the week as configured by the user. The value follows the ISO 8601 convention where `1` is Monday and `7` is Sunday. | 0.1.0 |
| **`timeZone`**        | <code>string</code>  | The identifier of the user's current time zone.                                                                                       | 0.1.0 |
| **`uses24HourClock`** | <code>boolean</code> | Whether the user prefers a 24-hour clock over a 12-hour clock.                                                                        | 0.1.0 |


### Type Aliases


#### MeasurementSystem

The measurement system used by a locale.

- `metric`: The metric system (e.g. meters, kilograms).
- `us`: The United States customary system (e.g. feet, pounds).
- `uk`: The United Kingdom imperial system.

<code>'metric' | 'us' | 'uk'</code>


#### TextDirection

The writing direction of a language.

- `ltr`: Left-to-right (e.g. English, German).
- `rtl`: Right-to-left (e.g. Arabic, Hebrew).

<code>'ltr' | 'rtl'</code>

</docgen-api>

## Platform Support

The plugin returns `null` for any field that a platform cannot determine. On the **Web** platform, `currencyCode`, `currencySymbol`, and `measurementSystem` are always `null`, because the browser does not expose this information.

## FAQ

### How is this plugin different from other similar plugins?

It reads the user's complete localization picture in one fully typed API — ordered preferred locales, currency and number separators, text direction, measurement system, time zone, clock format, and first day of the week — across Android, iOS, and the Web. It's honest about platform limits, returning `null` for fields a platform can't determine. If you only need the current language, a minimal lookup is enough; if you want to format numbers, currencies, dates, and layouts to match each user's region, this plugin is designed for exactly that.

### Why are some locale fields `null` on the Web?

The plugin returns `null` for any field that a platform cannot determine. On the Web, `currencyCode`, `currencySymbol`, and `measurementSystem` are always `null` because the browser does not expose this information. On Android and iOS, the plugin has full access to the system's localization settings.

### Do I need any permissions to use this plugin?

No, reading the user's localization preferences does not require any permissions. No additional configuration is required on any platform either, so the plugin works right after [installation](#installation).

### How do I detect right-to-left languages?

Every locale returned by `getLocales()` contains a `textDirection` property that is either `ltr` (left-to-right, e.g. English or German) or `rtl` (right-to-left, e.g. Arabic or Hebrew). You can use this value to switch the layout direction of your app.

### How is this plugin different from the App Language plugin?

The Localization plugin reads the user's system-level preferences, such as preferred locales, time zone, and regional formatting settings. The [App Language](https://capawesome.io/docs/sdks/capacitor/app-language/) plugin, on the other hand, manages the app's own language override, independent of the device language.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Language](https://capawesome.io/docs/sdks/capacitor/app-language/): Manage the app's own language override, independent of the device language.
- [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/): Native date and time selection with localization and theming support.
- [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/): Read device information, such as the model, manufacturer, and operating system.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/localization/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/localization/LICENSE).
