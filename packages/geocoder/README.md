# @capawesome-team/capacitor-geocoder

Capacitor plugin for handling geocoding and reverse geocoding.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for geocoding and reverse geocoding. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 📍 **Geocoding**: Convert addresses into geographic coordinates.
- 🗺️ **Reverse Geocoding**: Convert geographic coordinates into human-readable addresses.
- 🌐 **Multiple Providers**: Support for Google Maps and OpenStreetMap on Web.
- 🌍 **Localization**: Customize the locale for geocoding requests.
- 🔢 **Configurable Results**: Limit the number of addresses returned in reverse geocoding operations.
- 🛠️ **Native APIs**: Uses platform-native geocoding services on Android and iOS for reliable and accurate results.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Demo

| Android                                                                                                                      | iOS                                                                                                                      |
| ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| <img src="https://github.com/user-attachments/assets/1b49d200-e4de-4d71-9058-ef7730cdc7ee" width="324" alt="Android Demo" /> | <img src="https://github.com/user-attachments/assets/cb5e7f27-5394-4ae3-9657-b4a7a6bc9599" width="266" alt="iOS Demo" /> |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-geocoder universal-geocoder
npx cap sync
```

## Usage

```typescript
import { Geocoder } from '@capawesome-team/capacitor-geocoder';

const geocode = async () => {
  const result = await Geocoder.geocode({
    address: '1600 Amphitheatre Parkway, Mountain View, CA',
  });
  console.log('Geocode result:', result);
};

const geodecode = async () => {
  const result = await Geocoder.geodecode({
    latitude: 37.422,
    longitude: -122.084,
  });
  console.log('Geodecode result:', result);
};
```

## API

<docgen-index>

* [`geocode(...)`](#geocode)
* [`geodecode(...)`](#geodecode)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### geocode(...)

```typescript
geocode(options: GeocodeOptions) => Promise<GeocodeResult>
```

Translate an address into geographic coordinates.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#geocodeoptions">GeocodeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#geocoderesult">GeocodeResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### geodecode(...)

```typescript
geodecode(options: GeodecodeOptions) => Promise<GeodecodeResult>
```

Translate geographic coordinates into a human-readable address.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#geodecodeoptions">GeodecodeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#geodecoderesult">GeodecodeResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### Interfaces


#### GeocodeResult

| Prop            | Type                | Description                             | Since |
| --------------- | ------------------- | --------------------------------------- | ----- |
| **`latitude`**  | <code>number</code> | The latitude of the geocoded location.  | 0.0.1 |
| **`longitude`** | <code>number</code> | The longitude of the geocoded location. | 0.0.1 |


#### GeocodeOptions

| Prop               | Type                                          | Description                                                                                                 | Default                       | Since |
| ------------------ | --------------------------------------------- | ----------------------------------------------------------------------------------------------------------- | ----------------------------- | ----- |
| **`address`**      | <code>string</code>                           | The address to geocode.                                                                                     |                               | 0.0.1 |
| **`locale`**       | <code>string</code>                           | The locale (BCP 47 language tag) to use for the geocoding request. By default, the device's locale is used. |                               | 0.0.1 |
| **`webApiKey`**    | <code>string</code>                           | The API key to use for the geocoding service. Only available on Web.                                        |                               | 0.0.1 |
| **`webProvider`**  | <code>'googlemaps' \| 'openstreetmaps'</code> | The provider to use for the geocoding service. Only available on Web.                                       | <code>'openstreetmaps'</code> | 0.0.1 |
| **`webUserAgent`** | <code>string</code>                           | The User-Agent identifying your application. Only available on the web.                                     |                               | 0.0.1 |


#### GeodecodeResult

| Prop            | Type                   | Description                                                                                                                | Since |
| --------------- | ---------------------- | -------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`addresses`** | <code>Address[]</code> | The list of addresses that match the given coordinates. The number of addresses returned is limited by the `limit` option. | 0.0.1 |


#### Address

| Prop               | Type                  | Description                                                      | Since |
| ------------------ | --------------------- | ---------------------------------------------------------------- | ----- |
| **`adminArea`**    | <code>string</code>   | The administrative area (e.g. state or province) of the address. | 0.0.1 |
| **`addressLines`** | <code>string[]</code> | The lines of the address.                                        | 0.0.1 |
| **`countryCode`**  | <code>string</code>   | The country code of the address.                                 | 0.0.1 |
| **`countryName`**  | <code>string</code>   | The name of the country.                                         | 0.0.1 |
| **`phoneNumber`**  | <code>string</code>   | The phone number of the address.                                 | 0.0.1 |
| **`postalCode`**   | <code>string</code>   | The postal code of the address.                                  | 0.0.1 |
| **`subAdminArea`** | <code>string</code>   | The sub-administrative area (e.g. county) of the address.        | 0.0.1 |
| **`url`**          | <code>string</code>   | The URL of the address.                                          | 0.0.1 |


#### GeodecodeOptions

| Prop               | Type                                          | Description                                                                                                                                                                                                                                                                                              | Default                       | Since |
| ------------------ | --------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- | ----- |
| **`latitude`**     | <code>number</code>                           | The latitude of the location to reverse geocode.                                                                                                                                                                                                                                                         |                               | 0.0.1 |
| **`limit`**        | <code>number</code>                           | The maximum number of results to return.                                                                                                                                                                                                                                                                 | <code>5</code>                | 0.0.1 |
| **`longitude`**    | <code>number</code>                           | The longitude of the location to reverse geocode.                                                                                                                                                                                                                                                        |                               | 0.0.1 |
| **`webApiKey`**    | <code>string</code>                           | The API key to use for the geocoding service. Only available on Web.                                                                                                                                                                                                                                     |                               | 0.0.1 |
| **`webProvider`**  | <code>'googlemaps' \| 'openstreetmaps'</code> | The provider to use for the geocoding service. Only available on Web.                                                                                                                                                                                                                                    | <code>'openstreetmaps'</code> | 0.0.1 |
| **`webUserAgent`** | <code>string</code>                           | The User-Agent identifying your application. Only needed if `webProvider` is set to `openstreetmaps` which uses the Nominatim service (see https://operations.osmfoundation.org/policies/nominatim/). The goal is to be able to limit the number of requests per application. Only available on the web. |                               | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/geocoder/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/geocoder/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/geocoder/LICENSE).
