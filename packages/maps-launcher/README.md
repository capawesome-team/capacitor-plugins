# Capacitor Maps Launcher Plugin

Capacitor plugin to launch navigation apps with turn-by-turn directions.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🧭 **Navigate**: Launch a navigation app with turn-by-turn directions to a destination.
- 🗺️ **Curated apps**: Support for Google Maps, Apple Maps (iOS) and Waze.
- 📍 **Coordinates & addresses**: Navigate to coordinates or a plain address.
- 🚗 **Travel modes**: Choose between driving, walking, bicycling and transit (best-effort per app).
- ✅ **Availability**: Check which navigation apps are installed and can be launched.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Maps Launcher plugin is typically used whenever an app wants to hand the user over to a navigation app, for example:

- **Store locators**: Add a "Get directions" button that starts turn-by-turn navigation to a store or office.
- **Delivery and field service**: Launch navigation to the next stop using its coordinates.
- **Event apps**: Navigate attendees to a venue by its plain address, without needing coordinates.
- **User choice**: Detect which navigation apps are installed with `getAvailableApps` and let users pick their favorite one.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-maps-launcher` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-maps-launcher
npx cap sync
```

### Android

The plugin declares the required [package visibility](https://developer.android.com/training/package-visibility) `<queries>` for Google Maps and Waze in its own `AndroidManifest.xml`. No additional configuration is required.

### iOS

#### Application Queries Schemes

To detect and launch Google Maps and Waze, the following URL schemes must be added to the `LSApplicationQueriesSchemes` array in the `Info.plist` file of your app. Without them, `getAvailableApps(...)` reports those apps as unavailable and `navigate(...)` rejects with the `APP_NOT_AVAILABLE` error code.

```xml
<key>LSApplicationQueriesSchemes</key>
<array>
  <string>comgooglemaps</string>
  <string>waze</string>
</array>
```

Apple Maps is always available and does not require any configuration.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to get the available and default navigation apps and how to navigate to coordinates or an address.

### Get the available navigation apps

Check which navigation apps are installed and can be launched. On iOS, Apple Maps is always included, while Google Maps and Waze are only included if the corresponding URL schemes are declared in your `Info.plist` file (see [Installation](#installation)). Only available on Android and iOS:

```typescript
import { MapsLauncher } from '@capawesome/capacitor-maps-launcher';

const getAvailableApps = async () => {
  const { apps } = await MapsLauncher.getAvailableApps();
  return apps;
};
```

### Get the default navigation app

Find out which navigation app is configured as the default handler for navigation intents. Returns `null` if the default app is not part of the curated list of supported apps or if no default app is set. Only available on Android:

```typescript
import { MapsLauncher } from '@capawesome/capacitor-maps-launcher';

const getDefaultApp = async () => {
  const { app } = await MapsLauncher.getDefaultApp();
  return app;
};
```

### Navigate to coordinates

Launch a navigation app with turn-by-turn directions to a destination defined by its latitude and longitude. You can optionally specify the app to launch and the travel mode:

```typescript
import { MapsLauncher, NavigationApp } from '@capawesome/capacitor-maps-launcher';

const navigate = async () => {
  await MapsLauncher.navigate({
    destination: {
      latitude: 37.3349,
      longitude: -122.009,
    },
    app: NavigationApp.GoogleMaps,
    travelMode: 'driving',
  });
};
```

### Navigate to an address

Instead of coordinates, you can also pass a plain address as the destination. If no `app` is provided, the system default behavior is used (a chooser on Android, Apple Maps on iOS):

```typescript
import { MapsLauncher } from '@capawesome/capacitor-maps-launcher';

const navigateToAddress = async () => {
  await MapsLauncher.navigate({
    destination: {
      address: 'Apple Park, Cupertino, CA',
    },
  });
};
```

## API

<docgen-index>

* [`getAvailableApps()`](#getavailableapps)
* [`getDefaultApp()`](#getdefaultapp)
* [`navigate(...)`](#navigate)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAvailableApps()

```typescript
getAvailableApps() => Promise<GetAvailableAppsResult>
```

Get the navigation apps that are installed and can be launched.

On iOS, Apple Maps is always included. Google Maps and Waze are only
included if the corresponding `LSApplicationQueriesSchemes` entries are
added to the `Info.plist` file of your app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getavailableappsresult">GetAvailableAppsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getDefaultApp()

```typescript
getDefaultApp() => Promise<GetDefaultAppResult>
```

Get the navigation app that is configured as the default handler for
navigation intents.

Returns `null` if the default app is not part of the curated list of
supported apps or if no default app is set (i.e. the system shows a
chooser).

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getdefaultappresult">GetDefaultAppResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### navigate(...)

```typescript
navigate(options: NavigateOptions) => Promise<void>
```

Launch a navigation app with turn-by-turn directions to a destination.

If no `app` is provided, the system default behavior is used (a chooser on
Android, Apple Maps on iOS).

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#navigateoptions">NavigateOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### GetAvailableAppsResult

| Prop       | Type                         | Description                                                 | Since |
| ---------- | ---------------------------- | ----------------------------------------------------------- | ----- |
| **`apps`** | <code>NavigationApp[]</code> | The navigation apps that are installed and can be launched. | 0.1.0 |


#### GetDefaultAppResult

| Prop      | Type                                                            | Description                                                                                                                                                                    | Since |
| --------- | --------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`app`** | <code><a href="#navigationapp">NavigationApp</a> \| null</code> | The navigation app that is configured as the default handler. Returns `null` if the default app is not part of the curated list of supported apps or if no default app is set. | 0.1.0 |


#### NavigateOptions

| Prop              | Type                                                    | Description                                                                                                                                                                                                                                                                               | Default                | Since |
| ----------------- | ------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------- | ----- |
| **`app`**         | <code><a href="#navigationapp">NavigationApp</a></code> | The navigation app to launch. If not provided, the system default behavior is used (a chooser on Android, Apple Maps on iOS).                                                                                                                                                             |                        | 0.1.0 |
| **`destination`** | <code><a href="#destination">Destination</a></code>     | The destination to navigate to.                                                                                                                                                                                                                                                           |                        | 0.1.0 |
| **`start`**       | <code><a href="#destination">Destination</a></code>     | The start location of the route. If not provided, the current location of the device is used. **Note**: The support depends on the selected app. Apple Maps supports it fully, Google Maps opens the directions preview instead of starting turn-by-turn navigation, and Waze ignores it. |                        | 0.1.0 |
| **`travelMode`**  | <code><a href="#travelmode">TravelMode</a></code>       | The travel mode to use for the directions. **Note**: The support depends on the selected app and is best-effort. Waze only supports driving and ignores this option.                                                                                                                      | <code>'driving'</code> | 0.1.0 |


#### Destination

A destination is either defined by its coordinates or by its address, but
not both.

| Prop            | Type                | Description                                                                                        | Since |
| --------------- | ------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`address`**   | <code>string</code> | The address of the destination. Must be provided without `latitude` and `longitude`.               | 0.1.0 |
| **`latitude`**  | <code>number</code> | The latitude of the destination. Must be provided together with `longitude` and without `address`. | 0.1.0 |
| **`longitude`** | <code>number</code> | The longitude of the destination. Must be provided together with `latitude` and without `address`. | 0.1.0 |


### Type Aliases


#### TravelMode

The travel mode to use for the directions.

- `driving`: Driving directions.
- `walking`: Walking directions.
- `bicycling`: Bicycling directions.
- `transit`: Public transit directions.

<code>'driving' | 'walking' | 'bicycling' | 'transit'</code>


### Enums


#### NavigationApp

| Members          | Value                      | Description                        | Since |
| ---------------- | -------------------------- | ---------------------------------- | ----- |
| **`AppleMaps`**  | <code>'APPLE_MAPS'</code>  | Apple Maps. Only available on iOS. | 0.1.0 |
| **`GoogleMaps`** | <code>'GOOGLE_MAPS'</code> | Google Maps.                       | 0.1.0 |
| **`Waze`**       | <code>'WAZE'</code>        | Waze.                              | 0.1.0 |

</docgen-api>

## Platform Support

The behavior of the `navigate(...)` method depends on the selected app and platform. Keep the following in mind:

### Start location

| App         | `start` support                                                                                                    |
| ----------- | ------------------------------------------------------------------------------------------------------------------ |
| Apple Maps  | Full support.                                                                                                       |
| Google Maps | Supported, but opens the directions preview instead of starting turn-by-turn navigation directly.                  |
| Waze        | Not supported. The option is ignored.                                                                              |

If `start` is not provided, the current location of the device is used.

### Travel mode

| App         | `driving` | `walking` | `bicycling` | `transit` |
| ----------- | --------- | --------- | ----------- | --------- |
| Apple Maps  | ✅        | ✅        | ❌          | ✅        |
| Google Maps | ✅        | ✅        | ✅          | ✅        |
| Waze        | ✅        | ❌        | ❌          | ❌        |

Unsupported travel modes fall back to the default behavior of the respective app. Waze only supports driving and ignores the `travelMode` option.

## FAQ

### Why are Google Maps and Waze reported as unavailable on iOS?

To detect and launch Google Maps and Waze on iOS, the `comgooglemaps` and `waze` URL schemes must be added to the `LSApplicationQueriesSchemes` array in the `Info.plist` file of your app. Without them, `getAvailableApps` reports those apps as unavailable and `navigate` rejects with the `APP_NOT_AVAILABLE` error code. See the [Installation](#installation) section for details.

### What happens if I do not specify a navigation app?

If no `app` is provided, the system default behavior is used: Android shows a chooser and iOS opens Apple Maps. On Android, you can use `getDefaultApp` to find out which supported navigation app is configured as the default handler.

### Which travel modes are supported?

The plugin supports the `driving`, `walking`, `bicycling`, and `transit` travel modes, but the support depends on the selected app and is best-effort. Google Maps supports all four modes, Apple Maps does not support bicycling, and Waze only supports driving and ignores the option. Unsupported travel modes fall back to the default behavior of the respective app.

### Can I set a custom start location for the route?

Yes, use the `start` option of the `navigate` method. However, the support depends on the selected app: Apple Maps supports it fully, Google Maps opens the directions preview instead of starting turn-by-turn navigation, and Waze ignores it. If no start location is provided, the current location of the device is used.

### Does the plugin work on the web?

No, the `getAvailableApps` and `navigate` methods are only available on Android and iOS, and `getDefaultApp` is only available on Android. Launching installed navigation apps is a native capability that is not available in the browser.

## Related Plugins

- [App Launcher](https://capawesome.io/docs/sdks/capacitor/app-launcher/): Check if an app can be opened and open it.
- [Geocoder](https://capawesome.io/docs/sdks/capacitor/geocoder/): Convert addresses into coordinates and vice versa.
- [Android Intent Launcher](https://capawesome.io/docs/sdks/capacitor/android-intent-launcher/): Launch arbitrary Android intents.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/maps-launcher/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/maps-launcher/LICENSE).
