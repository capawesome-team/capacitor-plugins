# Capacitor SIM Plugin

Capacitor plugin for reading SIM card and carrier information.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📇 **SIM cards**: Read information about the SIM cards installed on the device.
- 🔀 **Multi-SIM**: Supports devices with multiple SIM slots.
- 🌐 **Carrier details**: Read carrier name, country code, MCC and MNC.
- 🔒 **Permissions**: Built-in handling of the required runtime permission.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The SIM plugin is typically used when an app needs to know about the device's SIM cards or carrier, for example:

- **Carrier-specific features**: Enable or disable functionality depending on the carrier name or the MCC and MNC of the SIM card.
- **Country detection**: Use the SIM card's ISO country code to preselect a country or region in your app.
- **Multi-SIM handling**: Show which SIM slots are in use on devices with multiple SIM slots.
- **eSIM detection**: Detect whether a SIM card is an embedded SIM (eSIM).

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-sim` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-sim
npx cap sync
```

This plugin is only available on **Android**. On iOS and Web, all methods reject as unimplemented (see [iOS](#ios) below).

### Android

The `READ_PHONE_STATE` permission is declared in the plugin's `AndroidManifest.xml` and is merged into your app automatically. You must request it at runtime via the `requestPermissions(...)` method before calling `getSimCards(...)`.

### iOS

Reading SIM card and carrier information is **not supported** on iOS. Apple deprecated the `CTCarrier` APIs of the Core Telephony framework with iOS 16, and they return placeholder values (e.g. `"--"` and `65535`) on iOS 16.4 and later. Because there is no reliable system API left, all methods reject as unimplemented on iOS.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check and request permissions and read the SIM cards installed on the device.

### Check and request permissions

Reading the SIM cards requires the `READ_PHONE_STATE` runtime permission on Android. Check the current permission state and request the permission before calling `getSimCards(...)`. Only available on Android:

```typescript
import { Sim } from '@capawesome/capacitor-sim';

const checkPermissions = async () => {
  const { readSimCards } = await Sim.checkPermissions();
  return readSimCards;
};

const requestPermissions = async () => {
  const { readSimCards } = await Sim.requestPermissions();
  return readSimCards;
};
```

### Read the SIM cards

Get information about the SIM cards installed on the device, such as the carrier name, country code, MCC and MNC. On devices with multiple SIM slots, all active SIM cards are returned. Only available on Android:

```typescript
import { Sim } from '@capawesome/capacitor-sim';

const getSimCards = async () => {
  const { simCards } = await Sim.getSimCards();
  return simCards;
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`getSimCards()`](#getsimcards)
* [`requestPermissions()`](#requestpermissions)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the permission to read the SIM cards.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getSimCards()

```typescript
getSimCards() => Promise<GetSimCardsResult>
```

Get information about the SIM cards installed on the device.

On devices with multiple SIM slots, all active SIM cards are returned.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getsimcardsresult">GetSimCardsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the permission to read the SIM cards.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### PermissionStatus

| Prop               | Type                                                        | Description                                    | Since |
| ------------------ | ----------------------------------------------------------- | ---------------------------------------------- | ----- |
| **`readSimCards`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state of reading the SIM cards. | 0.1.0 |


#### GetSimCardsResult

| Prop           | Type                   | Description                            | Since |
| -------------- | ---------------------- | -------------------------------------- | ----- |
| **`simCards`** | <code>SimCard[]</code> | The SIM cards installed on the device. | 0.1.0 |


#### SimCard

| Prop                    | Type                         | Description                                                                                                                                                                         | Since |
| ----------------------- | ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`carrierName`**       | <code>string \| null</code>  | The name of the carrier. Returns `null` if the carrier name is not available.                                                                                                       | 0.1.0 |
| **`displayName`**       | <code>string \| null</code>  | The user-editable display name of the SIM card. Returns `null` if the display name is not available.                                                                                | 0.1.0 |
| **`isEmbedded`**        | <code>boolean \| null</code> | Whether the SIM card is an embedded SIM (eSIM). Returns `null` if the information is not available.                                                                                 | 0.1.0 |
| **`isoCountryCode`**    | <code>string \| null</code>  | The two-letter ISO 3166-1 country code of the carrier. Returns `null` if the country code is not available.                                                                         | 0.1.0 |
| **`mobileCountryCode`** | <code>string \| null</code>  | The Mobile Country Code (MCC) of the carrier. Returns `null` if the mobile country code is not available.                                                                           | 0.1.0 |
| **`mobileNetworkCode`** | <code>string \| null</code>  | The Mobile Network Code (MNC) of the carrier. Returns `null` if the mobile network code is not available.                                                                           | 0.1.0 |
| **`phoneNumber`**       | <code>string \| null</code>  | The phone number associated with the SIM card. This value is often empty because carriers do not reliably store the phone number on the SIM card. In that case, `null` is returned. | 0.1.0 |
| **`slotIndex`**         | <code>number</code>          | The index of the SIM slot on the device.                                                                                                                                            | 0.1.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>

</docgen-api>

## FAQ

### Why is the SIM plugin not available on iOS?

Apple deprecated the `CTCarrier` APIs of the Core Telephony framework with iOS 16, and they return placeholder values (e.g. `"--"` and `65535`) on iOS 16.4 and later. Because there is no reliable system API left for reading SIM card and carrier information, all methods reject as unimplemented on iOS. See the [iOS installation notes](#ios) for details.

### Which permissions are required to read the SIM cards?

The plugin requires the `READ_PHONE_STATE` permission on Android. It is declared in the plugin's `AndroidManifest.xml` and merged into your app automatically, so no manual manifest changes are needed. However, you must request the permission at runtime via the `requestPermissions` method before calling `getSimCards`.

### Why is the phone number `null`?

The `phoneNumber` property is often empty because carriers do not reliably store the phone number on the SIM card. In that case, the plugin returns `null`. Other properties such as `carrierName` or `isoCountryCode` may also be `null` if the information is not available.

### Does the plugin support dual-SIM devices?

Yes, on devices with multiple SIM slots, the `getSimCards` method returns all active SIM cards. Each SIM card includes a `slotIndex` property that indicates the index of the SIM slot on the device.

### Can I detect whether a SIM card is an eSIM?

Yes, each SIM card includes an `isEmbedded` property that indicates whether it is an embedded SIM (eSIM). The property returns `null` if the information is not available.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/): Read device information, such as the model, manufacturer, and operating system.
- [Network](https://capawesome.io/docs/sdks/capacitor/network/): Access network information.
- [Phone Dialer](https://capawesome.io/docs/sdks/capacitor/phone-dialer/): Open the native phone dialer prefilled with a phone number.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sim/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sim/LICENSE).
