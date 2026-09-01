# Capacitor Zeroconf Plugin

Capacitor plugin to discover and advertise services on the local network using mDNS/DNS-SD (Zeroconf) on Android and iOS. Compatible with services published by Apple Bonjour and Avahi.[^1][^2]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Zeroconf plugin lets your app find other devices on the same network and make itself discoverable, without the user ever typing in an IP address. Here are some of the key features:

- 🔍 **Service Discovery**: Discover services of a given type on the local network, with multiple discoveries running at the same time.
- 📡 **Service Advertising**: Advertise your own service on the local network, including its TXT record.
- 🧭 **Modern OS APIs**: Built on `NsdManager` on Android and the system DNS-SD API on iOS, so there is no bundled mDNS stack and no raw socket handling.
- 🌐 **Addresses & Ports**: Resolve a service to its hostname, port, IPv4 and IPv6 addresses, instead of only its name.
- 🎯 **Explicit Resolution**: Resolve services automatically or on demand, so your app does not resolve every service it sees.
- 🔒 **Permissions**: First-class support for the local network permission, including the system service picker on Android that needs no permission at all.
- ⚠️ **Actionable Errors**: The iOS configuration is validated at runtime, so a missing `Info.plist` entry becomes a clear error instead of an empty result.
- 🤝 **Compatibility**: Works alongside the [Wifi](https://capawesome.io/docs/sdks/capacitor/wifi/) and [Bluetooth Low Energy](https://capawesome.io/docs/sdks/capacitor/bluetooth-low-energy/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Zeroconf plugin is typically used whenever an app needs to talk to another device on the same network, for example:

- **IoT and smart home**: Find cameras, sensors, or controllers on the network and connect to them by IP address and port.
- **Printing**: Discover network printers and read their capabilities from the TXT record before sending a print job.
- **Local servers**: Let the app find an on-premise or desktop server without asking the user to enter an IP address that may change.
- **Point of sale and medical devices**: Connect to local hardware in environments that have no internet connection at all.
- **Peer-to-peer features**: Advertise the app itself as a service so that other devices running your app can discover it.
- **Media**: Find media servers and streaming targets on the network.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Testing

Service discovery and advertising can only be tested on **real devices** that are connected to the **same network**:

- The **Android emulator** does not support multicast traffic, so mDNS does not work there at all. No services are found and advertised services are invisible to other devices.
- The **iOS Simulator** does not enforce local network privacy. A setup that works in the simulator can still fail silently on a device, so always verify the `Info.plist` configuration (see [iOS](#ios)) on a physical device.
- Many guest and enterprise networks block multicast traffic or isolate clients from each other. If nothing is found, cross-check with a desktop tool such as `dns-sd -B _http._tcp` on macOS or `avahi-browse -a` on Linux.

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-zeroconf` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-zeroconf
npx cap sync
```

### Android

#### Permissions

No permission is required today. The plugin already declares the permissions it needs in its manifest, so there is nothing to add to your `AndroidManifest.xml`.

Starting with **Android 17 (API level 37)**, access to the local network is permission-gated. The required `ACCESS_LOCAL_NETWORK` permission is declared by the plugin automatically, so you only have to request it at runtime (see [Check and request permissions](#check-and-request-permissions)) once your app targets SDK 37 or newer. The permission exists on Android 16 (API level 36) and newer. On older versions, `checkPermissions()` and `requestPermissions()` always report `granted`.

Alternatively, set `androidUsePicker` to `true` in `startDiscovery(...)`. The operating system then displays a service picker and delivers only the service that the user selected, **without requiring any permission at all**. This option requires Android 17 (API level 37) or newer and is ignored on iOS.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Privacy Descriptions

Add the `NSLocalNetworkUsageDescription` key **and** an `NSBonjourServices` array to the `ios/App/App/Info.plist` file. **Both are required.** `NSLocalNetworkUsageDescription` tells the user why your app needs access to the local network, and `NSBonjourServices` is a static allow list of the service types that your app is permitted to use:

```xml
<key>NSLocalNetworkUsageDescription</key>
<string>The app needs access to the local network to find nearby devices.</string>
<key>NSBonjourServices</key>
<array>
  <string>_http._tcp</string>
  <string>_ipp._tcp</string>
</array>
```

Add one `<string>` entry for **every** service type that your app uses, for discovery as well as for advertising. Wildcards are not supported, so the types must be known at build time.

If a key is missing or a service type is not listed, iOS fails **silently**: no services are discovered, advertised services never become visible on the network, and the operating system reports no error whatsoever. To prevent that, the plugin reads the `Info.plist` at runtime and rejects `startDiscovery(...)`, `startAdvertising(...)` and `requestPermissions(...)` with an error message that names the exact entry to add.

#### Entitlements

The `com.apple.developer.networking.multicast` entitlement is **not** required for normal use and should not be requested. It is only needed if your app has to browse for service types that are not known at build time and therefore cannot be listed in `NSBonjourServices`. The entitlement must be requested from Apple; approval is at Apple’s discretion, may take some time and is not guaranteed.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to discover, resolve and advertise services, and how to check and request permissions.

### Discover services

Start a discovery for a service type and stop it again once it is no longer needed. Multiple discoveries can run at the same time, each identified by the returned `id`. Only available on Android and iOS:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const startDiscovery = async () => {
  const { id } = await Zeroconf.startDiscovery({
    type: '_http._tcp',
  });
  return id;
};

const stopDiscovery = async (id: string) => {
  await Zeroconf.stopDiscoveryById({ id });
};

const stopAllDiscoveries = async () => {
  await Zeroconf.stopAllDiscoveries();
};
```

### Listen for discovered services

Discovered services are delivered via events. A service is first reported as found without its hostname, port and IP addresses, and again as resolved once those are known. Both events carry the same `id`, so the resolved service replaces the found one. Only available on Android and iOS:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const addListeners = async () => {
  await Zeroconf.addListener('serviceFound', (event) => {
    console.log('Service found:', event.service.name);
  });
  await Zeroconf.addListener('serviceResolved', (event) => {
    console.log(
      'Service resolved:',
      event.service.ipv4Addresses,
      event.service.port,
    );
  });
  await Zeroconf.addListener('serviceUpdated', (event) => {
    console.log('Service updated:', event.service.name);
  });
  await Zeroconf.addListener('serviceLost', (event) => {
    console.log('Service lost:', event.service.name);
  });
  await Zeroconf.addListener('discoveryFailed', (event) => {
    console.error('Discovery failed:', event.message);
  });
};
```

### Resolve a service

By default, every found service is resolved automatically. Set `autoResolve` to `false` if your app only needs the names of the available services and resolve a service explicitly once the user selects it. Only available on Android and iOS:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const startDiscoveryWithoutAutoResolve = async () => {
  const { id } = await Zeroconf.startDiscovery({
    autoResolve: false,
    type: '_http._tcp',
  });
  return id;
};

const resolveService = async (id: string) => {
  const { service } = await Zeroconf.resolveServiceById({ id });
  return service;
};
```

### Let the user select a service

On Android 17 and newer, the operating system can display a service picker and deliver only the service that the user selected. No local network permission is required in this case. The option is ignored on iOS. Only available on Android:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const startDiscoveryWithPicker = async () => {
  const { id } = await Zeroconf.startDiscovery({
    androidUsePicker: true,
    type: '_http._tcp',
  });
  return id;
};
```

### Advertise a service

Advertise your own service so that other devices can find it. If the name is already taken on the network, the service is renamed automatically, so always use the returned name instead of the requested one. Only available on Android and iOS:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const startAdvertising = async () => {
  const { id, name } = await Zeroconf.startAdvertising({
    name: 'My Service',
    port: 8080,
    txtRecord: { path: '/api' },
    type: '_http._tcp',
  });
  return { id, name };
};

const stopAdvertising = async (id: string) => {
  await Zeroconf.stopAdvertisingById({ id });
};

const stopAllAdvertising = async () => {
  await Zeroconf.stopAllAdvertising();
};
```

If the service is renamed after it has been registered, for example because another device claimed the name in the meantime, the `advertisingNameChange` event is emitted:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const addListener = async () => {
  await Zeroconf.addListener('advertisingNameChange', (event) => {
    console.log(`Renamed from ${event.previousName} to ${event.name}.`);
  });
};
```

### Check and request permissions

Discovery and advertising require the local network permission. On Android below version 16, the permission does not exist and is always reported as `granted`. On iOS, the operating system provides no API to read the permission and it cannot be requested programmatically, so `requestPermissions()` only triggers the one-time system prompt and the status is never `granted` (see the [FAQ](#faq)). Only available on Android and iOS:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const checkPermissions = async () => {
  return Zeroconf.checkPermissions();
};

const requestPermissions = async () => {
  return Zeroconf.requestPermissions();
};
```

If the permission was denied, send the user to the native app settings:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const openAppSettings = async () => {
  await Zeroconf.openAppSettings();
};
```

### Remove all listeners

Remove all listeners for this plugin when they are no longer needed:

```typescript
import { Zeroconf } from '@capawesome-team/capacitor-zeroconf';

const removeAllListeners = async () => {
  await Zeroconf.removeAllListeners();
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`openAppSettings()`](#openappsettings)
* [`requestPermissions()`](#requestpermissions)
* [`resolveServiceById(...)`](#resolveservicebyid)
* [`startAdvertising(...)`](#startadvertising)
* [`startDiscovery(...)`](#startdiscovery)
* [`stopAdvertisingById(...)`](#stopadvertisingbyid)
* [`stopAllAdvertising()`](#stopalladvertising)
* [`stopAllDiscoveries()`](#stopalldiscoveries)
* [`stopDiscoveryById(...)`](#stopdiscoverybyid)
* [`addListener('advertisingNameChange', ...)`](#addlisteneradvertisingnamechange-)
* [`addListener('discoveryFailed', ...)`](#addlistenerdiscoveryfailed-)
* [`addListener('serviceFound', ...)`](#addlistenerservicefound-)
* [`addListener('serviceLost', ...)`](#addlistenerservicelost-)
* [`addListener('serviceResolved', ...)`](#addlistenerserviceresolved-)
* [`addListener('serviceUpdated', ...)`](#addlistenerserviceupdated-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the status of the local network permission.

On **Android**, the local network permission only exists on Android 16+.
On older versions, this method always returns `granted`.

On **iOS**, the system provides no API to read the local network
permission. This method returns `prompt` until a denial has been
observed during the current app launch, then `denied`. It never
returns `granted`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openAppSettings()

```typescript
openAppSettings() => Promise<void>
```

Open the app settings so the user can grant or revoke the
local network permission.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the local network permission.

On **Android**, the local network permission only exists on Android 16+.
On older versions, this method always returns `granted`.

On **iOS**, the permission cannot be requested programmatically.
This method triggers the one-time system prompt by briefly browsing
for the first service type declared in `NSBonjourServices` and then
returns the current status (`prompt` or `denied`, see `checkPermissions()`).

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### resolveServiceById(...)

```typescript
resolveServiceById(options: ResolveServiceByIdOptions) => Promise<ResolveServiceByIdResult>
```

Resolve a discovered service to get its hostname, port and IP addresses.

This is only needed when `autoResolve` was set to `false` or to
refresh the data of an already resolved service. The first resolution
of a service also emits the `serviceResolved` event; a refresh that
changed the data of an already resolved service emits the
`serviceUpdated` event.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#resolveservicebyidoptions">ResolveServiceByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#resolveservicebyidresult">ResolveServiceByIdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### startAdvertising(...)

```typescript
startAdvertising(options: StartAdvertisingOptions) => Promise<StartAdvertisingResult>
```

Advertise a service on the local network.

If the service name is already taken, the service is automatically
renamed (e.g. `My <a href="#service">Service`</a> becomes `My <a href="#service">Service</a> (2)`). The returned
name is the actually registered name. If the service is renamed
later, the `advertisingNameChange` event is emitted.

Advertising is only active while the app is in the foreground.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startadvertisingoptions">StartAdvertisingOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#startadvertisingresult">StartAdvertisingResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### startDiscovery(...)

```typescript
startDiscovery(options: StartDiscoveryOptions) => Promise<StartDiscoveryResult>
```

Start discovering services of a given type on the local network.

Discovered services are delivered via the `serviceFound`,
`serviceResolved`, `serviceUpdated` and `serviceLost` events.

Discovery is only active while the app is in the foreground.
On **iOS**, the `serviceLost` event is emitted for all discovered
services when the app enters the background and the discovery is
automatically restarted when the app returns to the foreground.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#startdiscoveryoptions">StartDiscoveryOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#startdiscoveryresult">StartDiscoveryResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### stopAdvertisingById(...)

```typescript
stopAdvertisingById(options: StopAdvertisingByIdOptions) => Promise<void>
```

Stop advertising a service.

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#stopadvertisingbyidoptions">StopAdvertisingByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopAllAdvertising()

```typescript
stopAllAdvertising() => Promise<void>
```

Stop advertising all services.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### stopAllDiscoveries()

```typescript
stopAllDiscoveries() => Promise<void>
```

Stop all running discoveries.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### stopDiscoveryById(...)

```typescript
stopDiscoveryById(options: StopDiscoveryByIdOptions) => Promise<void>
```

Stop a running discovery.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#stopdiscoverybyidoptions">StopDiscoveryByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### addListener('advertisingNameChange', ...)

```typescript
addListener(eventName: 'advertisingNameChange', listenerFunc: (event: AdvertisingNameChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when an advertised service was renamed by the network
responder because of a name conflict.

Only available on Android and iOS.

| Param              | Type                                                                                                  |
| ------------------ | ----------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'advertisingNameChange'</code>                                                                  |
| **`listenerFunc`** | <code>(event: <a href="#advertisingnamechangeevent">AdvertisingNameChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('discoveryFailed', ...)

```typescript
addListener(eventName: 'discoveryFailed', listenerFunc: (event: DiscoveryFailedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a running discovery fails. The discovery is stopped
before this event is emitted.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'discoveryFailed'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#discoveryfailedevent">DiscoveryFailedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('serviceFound', ...)

```typescript
addListener(eventName: 'serviceFound', listenerFunc: (event: ServiceEvent) => void) => Promise<PluginListenerHandle>
```

Called when a service was found on the local network.

The service is not yet resolved at this point, so `hostname`,
`port` and the IP addresses are not yet available.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'serviceFound'</code>                                               |
| **`listenerFunc`** | <code>(event: <a href="#serviceevent">ServiceEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('serviceLost', ...)

```typescript
addListener(eventName: 'serviceLost', listenerFunc: (event: ServiceEvent) => void) => Promise<PluginListenerHandle>
```

Called when a service disappeared from the local network.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'serviceLost'</code>                                                |
| **`listenerFunc`** | <code>(event: <a href="#serviceevent">ServiceEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('serviceResolved', ...)

```typescript
addListener(eventName: 'serviceResolved', listenerFunc: (event: ServiceEvent) => void) => Promise<PluginListenerHandle>
```

Called when a service was resolved. The service now contains the
hostname, port and IP addresses. It replaces the service delivered
by the `serviceFound` event (same `id`).

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'serviceResolved'</code>                                            |
| **`listenerFunc`** | <code>(event: <a href="#serviceevent">ServiceEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('serviceUpdated', ...)

```typescript
addListener(eventName: 'serviceUpdated', listenerFunc: (event: ServiceEvent) => void) => Promise<PluginListenerHandle>
```

Called when the data of an already resolved service changed.

On **iOS**, this event is only emitted when the service is
re-resolved via `resolveServiceById(...)`.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'serviceUpdated'</code>                                             |
| **`listenerFunc`** | <code>(event: <a href="#serviceevent">ServiceEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### PermissionStatus

| Prop               | Type                                                        | Description                                           | Since |
| ------------------ | ----------------------------------------------------------- | ----------------------------------------------------- | ----- |
| **`localNetwork`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for accessing the local network. | 0.0.1 |


#### ResolveServiceByIdResult

| Prop          | Type                                        | Description           | Since |
| ------------- | ------------------------------------------- | --------------------- | ----- |
| **`service`** | <code><a href="#service">Service</a></code> | The resolved service. | 0.0.1 |


#### Service

| Prop                | Type                                      | Description                                                                                                                                                                                                                  | Since |
| ------------------- | ----------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`domain`**        | <code>string</code>                       | The domain of the service.                                                                                                                                                                                                   | 0.0.1 |
| **`hostname`**      | <code>string \| null</code>               | The hostname of the device providing the service. Is `null` until the service has been resolved.                                                                                                                             | 0.0.1 |
| **`id`**            | <code>string</code>                       | The unique identifier of the service. The identifier is generated by the plugin and stays stable for the lifetime of the discovery, across the `serviceFound`, `serviceResolved`, `serviceUpdated` and `serviceLost` events. | 0.0.1 |
| **`ipv4Addresses`** | <code>string[]</code>                     | The IPv4 addresses of the device providing the service. Is empty until the service has been resolved.                                                                                                                        | 0.0.1 |
| **`ipv6Addresses`** | <code>string[]</code>                     | The IPv6 addresses of the device providing the service. Link-local addresses (`fe80::/10`) are filtered out. Is empty until the service has been resolved.                                                                   | 0.0.1 |
| **`lastSeenAt`**    | <code>number</code>                       | The timestamp at which the service was last seen, in milliseconds since the Unix epoch.                                                                                                                                      | 0.0.1 |
| **`name`**          | <code>string</code>                       | The name of the service.                                                                                                                                                                                                     | 0.0.1 |
| **`port`**          | <code>number \| null</code>               | The port on which the service is available. Is `null` until the service has been resolved.                                                                                                                                   | 0.0.1 |
| **`txtRecord`**     | <code>Record&lt;string, string&gt;</code> | The TXT record of the service.                                                                                                                                                                                               | 0.0.1 |
| **`type`**          | <code>string</code>                       | The type of the service.                                                                                                                                                                                                     | 0.0.1 |


#### ResolveServiceByIdOptions

| Prop     | Type                | Description                               | Since |
| -------- | ------------------- | ----------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the service to resolve. | 0.0.1 |


#### StartAdvertisingResult

| Prop       | Type                | Description                                                                                                                                 | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**   | <code>string</code> | The identifier of the advertised service.                                                                                                   | 0.0.1 |
| **`name`** | <code>string</code> | The actually registered name of the service. This may differ from the requested name if the service was renamed because of a name conflict. | 0.0.1 |


#### StartAdvertisingOptions

| Prop            | Type                                      | Description                                                                                                                                                                 | Default               | Since |
| --------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`domain`**    | <code>string</code>                       | The domain in which to advertise the service.                                                                                                                               | <code>'local.'</code> | 0.0.1 |
| **`name`**      | <code>string</code>                       | The name of the service. If the name is already taken, the service is automatically renamed.                                                                                |                       | 0.0.1 |
| **`port`**      | <code>number</code>                       | The port on which the service is available.                                                                                                                                 |                       | 0.0.1 |
| **`txtRecord`** | <code>Record&lt;string, string&gt;</code> | The TXT record to advertise with the service. If not provided, a single default pair (`txtvers=1`) is advertised since some platforms cannot advertise an empty TXT record. |                       | 0.0.1 |
| **`type`**      | <code>string</code>                       | The type of the service.                                                                                                                                                    |                       | 0.0.1 |


#### StartDiscoveryResult

| Prop     | Type                | Description                      | Since |
| -------- | ------------------- | -------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the discovery. | 0.0.1 |


#### StartDiscoveryOptions

| Prop                   | Type                 | Description                                                                                                                                                                                                              | Default               | Since |
| ---------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------------- | ----- |
| **`androidUsePicker`** | <code>boolean</code> | Whether or not to let the user select the service via a system dialog. In this case, no local network permission is required and only the selected service is delivered to the app. Only available on Android (SDK 37+). | <code>false</code>    | 0.0.1 |
| **`autoResolve`**      | <code>boolean</code> | Whether or not found services are automatically resolved. If set to `false`, services must be resolved manually using `resolveServiceById(...)`.                                                                         | <code>true</code>     | 0.0.1 |
| **`domain`**           | <code>string</code>  | The domain in which to discover services.                                                                                                                                                                                | <code>'local.'</code> | 0.0.1 |
| **`type`**             | <code>string</code>  | The type of the services to discover.                                                                                                                                                                                    |                       | 0.0.1 |


#### StopAdvertisingByIdOptions

| Prop     | Type                | Description                                       | Since |
| -------- | ------------------- | ------------------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the advertised service to stop. | 0.0.1 |


#### StopDiscoveryByIdOptions

| Prop     | Type                | Description                              | Since |
| -------- | ------------------- | ---------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the discovery to stop. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### AdvertisingNameChangeEvent

| Prop               | Type                | Description                                  | Since |
| ------------------ | ------------------- | -------------------------------------------- | ----- |
| **`id`**           | <code>string</code> | The identifier of the advertised service.    | 0.0.1 |
| **`name`**         | <code>string</code> | The new name of the advertised service.      | 0.0.1 |
| **`previousName`** | <code>string</code> | The previous name of the advertised service. | 0.0.1 |


#### DiscoveryFailedEvent

| Prop          | Type                        | Description                                  | Since |
| ------------- | --------------------------- | -------------------------------------------- | ----- |
| **`code`**    | <code>string \| null</code> | The error code of the failure.               | 0.0.1 |
| **`id`**      | <code>string</code>         | The identifier of the discovery that failed. | 0.0.1 |
| **`message`** | <code>string</code>         | The error message of the failure.            | 0.0.1 |


#### ServiceEvent

| Prop              | Type                                        | Description                                               | Since |
| ----------------- | ------------------------------------------- | --------------------------------------------------------- | ----- |
| **`discoveryId`** | <code>string</code>                         | The identifier of the discovery that delivered the event. | 0.0.1 |
| **`service`**     | <code><a href="#service">Service</a></code> | The service that the event refers to.                     | 0.0.1 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>

</docgen-api>

## Service Types

A service type consists of a service name and a transport protocol, for example `_http._tcp` for a web server or `_ipp._tcp` for a network printer:

```
_<service>._tcp
_<service>._udp
```

The trailing dot is optional, so both `_http._tcp` and `_http._tcp.` are accepted. The plugin normalizes the type and always returns it **without** the trailing dot, so the `type` of a discovered service can be compared directly to the type you passed in. Domains are normalized the other way round and always carry the trailing dot, for example `local.`.

A malformed type is rejected immediately, instead of silently returning no results, with a message that shows the expected form:

```
type must be of the form _<service>._tcp or _<service>._udp (e.g. _http._tcp).
```

Registered service types are listed in the [IANA service name registry](https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml). If you advertise your own protocol, pick a name that is not registered yet.

Two variants are deliberately not supported and are rejected with an explanatory message:

- **Service type enumeration** (`_services._dns-sd._udp`), because neither platform implements it reliably. Discover a concrete service type instead.
- **Subtypes** (for example `_printer._sub._http._tcp`), which are not supported yet.

## Foreground Only

Discovery and advertising are only active while the app is in the **foreground** on both platforms. This is a restriction of the operating systems and not of the plugin: Android only holds the multicast lock for an app that is currently in the foreground, and iOS closes the connection to the mDNS daemon as soon as the app is suspended, which happens within seconds after it is backgrounded. Neither platform offers a background mode for this.

The two platforms behave differently while the app is not in the foreground:

- On **iOS**, the plugin emits a `serviceLost` event for every currently known service when the app enters the background and tears down its native handles. When the app returns to the foreground, all running discoveries and all advertised services are restored automatically. If a service is registered under a different name afterwards, an `advertisingNameChange` event is emitted.
- On **Android**, the requests stay registered with the operating system, but no events are delivered while the app is in the background. Discovery continues on its own once the app returns to the foreground.

## FAQ

### How is this plugin different from other similar plugins?

It is built on the service discovery APIs that the operating systems maintain themselves: `NsdManager` on Android and the low-level DNS-SD C API on iOS. That matters right now, because both platforms are restricting local network access at the same time. Android 17 permission-gates the raw sockets that a bundled mDNS stack needs, and iOS deprecates the classic `NetService` API — this plugin is already on the other side of both changes, and it additionally supports the Android system picker, which returns the service the user selected without requiring any permission at all. On iOS, the `Info.plist` configuration is validated at runtime, so the single most common cause of "nothing is found" becomes an error message naming the exact entry to add, instead of an empty result and no error at all. Discovery and resolution are separate steps, so your app does not have to resolve every service it sees, and a resolved service carries its hostname, port, IPv4 and IPv6 addresses separately rather than one ambiguous address. On top of that, permissions are handled first class on both platforms, every runtime failure rejects with a documented error code, and the plugin comes with priority support from the Capawesome Team.

### Is service discovery available on the web?

No. All methods are only available on Android and iOS. On the web, they reject with an unimplemented error. Browsers deliberately do not expose mDNS to web content, and there is no active proposal that would change this.

### Which devices can I discover?

Every device that publishes a service via mDNS/DNS-SD, which includes implementations such as Apple Bonjour and Avahi as well as printers, media servers, network attached storage, and other apps using this plugin. Only the service type has to match.

### Why are no services found?

The most common causes, in this order: the app runs on the Android emulator, which cannot do multicast at all; the service type is not declared in `NSBonjourServices` on iOS, which fails silently on the operating system side; the devices are on different networks or on a network that isolates clients from each other; or the service type does not match exactly. See [Testing](#testing) for how to verify the network itself.

### Why are `hostname` and `port` `null`?

The service has been found but not resolved yet. Discovery reports the name, type and domain of a service first, and its hostname, port and IP addresses only after it has been resolved. Wait for the `serviceResolved` event or resolve the service explicitly with `resolveServiceById(...)`.

### Why does `checkPermissions()` never return `granted` on iOS?

iOS provides no API to read the state of the local network permission ([TN3179](https://developer.apple.com/documentation/technotes/tn3179-understanding-local-network-privacy)). The plugin therefore reports `prompt` until a denial has actually been observed during the current app launch and `denied` afterwards, instead of pretending to know a state it cannot read.

### How many discoveries and advertisements can run at the same time?

On Android, the operating system limits the combined number of discoveries and advertisements per app to around ten. Once the limit is reached, `startDiscovery(...)` and `startAdvertising(...)` reject with the `MAX_REQUESTS_REACHED` error code. Stop the ones you no longer need instead of keeping them around.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Wifi](https://capawesome.io/docs/sdks/capacitor/wifi/): Manage Wi-Fi connectivity and read the network information of the device.
- [Bluetooth Low Energy](https://capawesome.io/docs/sdks/capacitor/bluetooth-low-energy/): Discover and communicate with nearby devices via Bluetooth.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zeroconf/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zeroconf/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/zeroconf/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Apple Inc. or any of their affiliates or subsidiaries.
[^2]: `Bonjour` is a registered trademark of Apple Inc.
