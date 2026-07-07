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

```typescript
import { Localization } from '@capawesome/capacitor-localization';

const getLocales = async () => {
  const { locales } = await Localization.getLocales();
  return locales;
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/localization/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/localization/LICENSE).
