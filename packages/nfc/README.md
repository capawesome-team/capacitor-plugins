# @capawesome-team/capacitor-nfc

Capacitor plugin for NFC tag reading, writing, and emulation. Supports Android, iOS, and Web with advanced features like HCE and raw command handling.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for NFC. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🔄 **NDEF**: Read and write NFC Data Exchange Format (NDEF) messages.
- 📳 **HCE**: Emulate an NFC card that other devices can interact with.
- ⌘ **Raw Commands**: Send raw commands to an NFC tag and receive the response.
- 🛠️ **Utils**: Utility functions to make your life easier.
- ⚔️ **Battle-Tested**: Used in more than 100 projects.
- 📚 **Documentation**: Comprehensive documentation to help you get started.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Demo

A working example can be found [here](https://github.com/capawesome-team/capacitor-nfc-demo).

| Android                                                                                                                                            | iOS                                                                                                                                            |
| -------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/184409092-7fdc3a77-67b1-4155-b1b1-0fd34a92a77b.gif" width="324" alt="Android Demo" /> | <img src="https://user-images.githubusercontent.com/13857929/184409000-a81243a3-93e5-4d51-817a-e006c0a385cf.gif" width="266" alt="iOS Demo" /> |

## Guides

- [Announcing the Capacitor NFC Plugin](https://capawesome.io/blog/announcing-the-capacitor-nfc-plugin/)
- [Exploring the Capacitor NFC API](https://capawesome.io/blog/exploring-the-capacitor-nfc-api/)

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-nfc
npx cap sync
```

### Android

#### Features

Add the following element to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<uses-feature android:name="android.hardware.nfc" android:required="true" />
```

Set the `android:required` attribute to `true` if your app can't function, or isn't designed to function, when NFC is not available on the device. If your app can function without NFC, set the `android:required` attribute to `false`. This will allow your app to be installed on devices that do not support NFC.

#### Intent Filter

If you want to launch your app through an NFC tag, please take a look at the [Android documentation](https://developer.android.com/guide/topics/connectivity/nfc/nfc#dispatching).
There you will find which changes you have to apply to your `AndroidManifest.xml` ([example](https://developer.android.com/guide/topics/connectivity/nfc/nfc#ndef-disc)).

#### Services

To be able to use Host Card Emulation (HCE), you also need to add the following service **inside** the `application` tag in your `AndroidManifest.xml` (usually `android/app/src/main/AndroidManifest.xml`):

```xml
<service android:name=".MyHostApduService" android:exported="true"
         android:permission="android.permission.BIND_NFC_SERVICE">
    <intent-filter>
        <action android:name="android.nfc.cardemulation.action.HOST_APDU_SERVICE"/>
    </intent-filter>
    <meta-data android:name="android.nfc.cardemulation.host_apdu_service"
               android:resource="@xml/apduservice"/>
</service>
```

This meta-data tag points to an `apduservice.xml` file. The following is an example of such a file with a single AID group declaration containing two proprietary AIDs:

```xml
<host-apdu-service xmlns:android="http://schemas.android.com/apk/res/android"
           android:description="@string/servicedesc"
           android:requireDeviceUnlock="false">
    <aid-group android:description="@string/aiddescription"
               android:category="other">
        <aid-filter android:name="F0010203040506"/>
        <aid-filter android:name="F0394148148100"/>
    </aid-group>
</host-apdu-service>
```

You can find more information about this in the [Android documentation](https://developer.android.com/develop/connectivity/nfc/hce#manifest-declaration).

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Entitlements

Ensure `Near Field Communication Tag Reading` and `NFC Scan` capabilities have been enabled in your application in Xcode.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

This will also create or update the `ios/App/App/App.entitlements` file. Make sure it contains the following key to allow reading NFC tags:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
	<key>com.apple.developer.nfc.readersession.formats</key>
	<array>
		<string>NDEF</string>
		<string>TAG</string>
	</array>
</dict>
</plist>
```

#### Privacy Descriptions

Add the `NFCReaderUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why the app needs to use NFC:

```xml
<key>NFCReaderUsageDescription</key>
<string>The app enables the reading and writing of various NFC tags.</string>
```

#### Universal Links

If you want to launch your app through an NFC tag, please take a look at the [Core NFC documentation](https://developer.apple.com/documentation/corenfc/adding_support_for_background_tag_reading#3032598).
The NFC tag requires a [URI record](https://w3c.github.io/web-nfc/#uri-record) (see [`createNdefUriRecord(...)`](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/nfc/docs/utils#createndefurirecord)) that must contain either a universal link (see [Deep Linking with Universal and App Links](https://capacitorjs.com/docs/guides/deep-links)) or a [supported URL scheme](https://developer.apple.com/documentation/corenfc/adding_support_for_background_tag_reading#3032600).

## Configuration

No configuration required for this plugin.

## Guides

- [Announcing the Capacitor NFC Plugin](https://capawesome.io/blog/announcing-the-capacitor-nfc-plugin/)

## Usage

```typescript
import { Nfc, NfcUtils, NfcTagTechType } from '@capawesome-team/capacitor-nfc';
import { Capacitor } from '@capacitor/core';

const createNdefTextRecord = () => {
  const utils = new NfcUtils();
  const { record } = utils.createNdefTextRecord({ text: 'Capacitor NFC Plugin' });
  return record;
};

const write = async () => {
  return new Promise((resolve) => {
    const record = createNdefTextRecord();

    Nfc.addListener('nfcTagScanned', async (event) => {
      await Nfc.write({ message: { records: [record] } });
      await Nfc.stopScanSession();
      resolve();
    });

    Nfc.startScanSession();
  });
};

const read = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      await Nfc.stopScanSession();
      resolve(event.nfcTag);
    });

    Nfc.startScanSession();
  });
};

const makeReadOnly = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      await Nfc.makeReadOnly();
      await Nfc.stopScanSession();
      resolve();
    });

    Nfc.startScanSession();
  });
};

const readSignature = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      if (Capacitor.getPlatform() === 'android') {
        // 1. Connect to the tag.
        await Nfc.connect({ techType: NfcTagTechType.NfcA }); 
        // 2. Send one or more commands to the tag and receive the response.
        const result = await Nfc.transceive({ data: [60, 0] });
        // 3. Close the connection to the tag.
        await Nfc.close();
        await Nfc.stopScanSession();
        resolve(result);
      } else {
        // 1. Send one or more commands to the tag and receive the response.
        const result = await Nfc.transceive({ techType: NfcTagTechType.NfcA, data: [60, 0] });
        await Nfc.stopScanSession();
        resolve(result);
      }
    });

    Nfc.startScanSession();
  });
};

const erase = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      await Nfc.erase();
      await Nfc.stopScanSession();
      resolve();
    });

    Nfc.startScanSession();
  });
};

const format = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      await Nfc.format();
      await Nfc.stopScanSession();
      resolve();
    });

    Nfc.startScanSession();
  });
};

const isSupported = async () => {
  const { nfc } = await Nfc.isSupported();
  return nfc;
};

const isEnabled = async () => {
  const { isEnabled } = await Nfc.isEnabled();
  return isEnabled;
};

const openSettings = async () => {
  await Nfc.openSettings();
};

const checkPermissions = async () => {
  const { nfc } = await Nfc.checkPermissions();
  return nfc;
};

const requestPermissions = async () => {
  const { nfc } = await Nfc.requestPermissions();
  return nfc;
};

const removeAllListeners = async () => {
  await Nfc.removeAllListeners();
};
```

### Advanced

#### HCE

Host Card Emulation (HCE) allows your device to emulate an NFC card that other devices can interact with. The first example below demonstrates how to send APDU commands from an NFC reader using the `transceive(...)` method:

```typescript
import { Nfc, NfcTagTechType } from '@capawesome-team/capacitor-nfc';
import { Capacitor } from '@capacitor/core';

const sendApduCommands = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      // Define APDU commands
      const selectApdu = [0x00, 0xA4, 0x04, 0x00, 0x07, 0xF0, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x00];
      const customApdu = [0x00, 0x01, 0x00, 0x00, 0x00];

      // Connect to the tag (Android only)
      if (Capacitor.getPlatform() === 'android') {
        await Nfc.connect({ techType: NfcTagTechType.IsoDep });
      }

      // Send SELECT APDU command
      const selectResponse = await Nfc.transceive({
        techType: NfcTagTechType.Iso7816,
        data: selectApdu
      });

      // Send custom APDU command
      const customResponse = await Nfc.transceive({
        techType: NfcTagTechType.Iso7816,
        data: customApdu
      });

      // Close the connection (Android only)
      if (Capacitor.getPlatform() === 'android') {
        await Nfc.close();
      }

      // Stop the scan session
      await Nfc.stopScanSession();
      resolve();
    });
    // Start the scan session
    Nfc.startScanSession();
  });
};
```

The second example below demonstrates how to respond to APDU commands sent by an NFC reader using the `commandReceived` event listener and the `respond(...)` method:

```typescript
import { Nfc } from '@capawesome-team/capacitor-nfc';

const respondToApduCommands = async () => {
  // Listen for incoming APDU commands
  Nfc.addListener('commandReceived', async (event) => {
    // Parse the incoming command
    const command = event.data;

    // Check if it's a SELECT command
    if (command[0] === 0x00 && command[1] === 0xA4 && command[2] === 0x04 && command[3] === 0x00) {
      // Respond with success status (0x9000)
      const response = [0x90, 0x00];
      await Nfc.respond({ data: response });
    }
    // Check for custom command
    else if (command[0] === 0x00 && command[1] === 0x01) {
      // Respond with custom data
      const response = [0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x90, 0x00]; // "Hello" + success status
      await Nfc.respond({ data: response });
    }
    // Unknown command
    else {
      // Respond with error status (0x6D00 - Instruction not supported)
      const response = [0x6D, 0x00];
      await Nfc.respond({ data: response });
    }
  });
};
```

## API

<docgen-index>

* [`startScanSession(...)`](#startscansession)
* [`stopScanSession(...)`](#stopscansession)
* [`write(...)`](#write)
* [`respond(...)`](#respond)
* [`makeReadOnly()`](#makereadonly)
* [`erase()`](#erase)
* [`format()`](#format)
* [`transceive(...)`](#transceive)
* [`connect(...)`](#connect)
* [`close()`](#close)
* [`isAvailable()`](#isavailable)
* [`isSupported()`](#issupported)
* [`isEnabled()`](#isenabled)
* [`openSettings()`](#opensettings)
* [`getAntennaInfo()`](#getantennainfo)
* [`setAlertMessage(...)`](#setalertmessage)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('commandReceived', ...)`](#addlistenercommandreceived-)
* [`addListener('nfcLinkDeactivated', ...)`](#addlistenernfclinkdeactivated-)
* [`addListener('nfcTagScanned', ...)`](#addlistenernfctagscanned-)
* [`addListener('scanSessionCanceled', ...)`](#addlistenerscansessioncanceled-)
* [`addListener('scanSessionError', ...)`](#addlistenerscansessionerror-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startScanSession(...)

```typescript
startScanSession(options?: StartScanSessionOptions | undefined) => Promise<void>
```

Start a scan session.
Only one session can be active at a time.

Stop the session as soon as you are done using `stopScanSession(...)`.

On iOS, this will trigger the NFC Reader Session alert.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startscansessionoptions">StartScanSessionOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopScanSession(...)

```typescript
stopScanSession(options?: StopScanSessionOptions | undefined) => Promise<void>
```

Stop the active scan session.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#stopscansessionoptions">StopScanSessionOptions</a></code> |

**Since:** 0.0.1

--------------------


### write(...)

```typescript
write(options: WriteOptions) => Promise<void>
```

Write to a NFC tag.

This method must be called from within a `nfcTagScanned` handler.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#writeoptions">WriteOptions</a></code> |

**Since:** 0.0.1

--------------------


### respond(...)

```typescript
respond(options: RespondOptions) => Promise<void>
```

Send a response APDU back to the remote device.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#respondoptions">RespondOptions</a></code> |

**Since:** 6.3.0

--------------------


### makeReadOnly()

```typescript
makeReadOnly() => Promise<void>
```

Make a NFC tag readonly.

This method must be called from within a `nfcTagScanned` handler.

**Attention:** This is permanent and can not be undone.

**Since:** 0.0.1

--------------------


### erase()

```typescript
erase() => Promise<void>
```

Erase the NFC tag by writing an empty NDEF message.

This method must be called from within a `nfcTagScanned` handler.

**Since:** 0.3.0

--------------------


### format()

```typescript
format() => Promise<void>
```

Format the NFC tag as NDEF and write an empty NDEF message.

This method must be called from within a `nfcTagScanned` handler.

Only available on Android.

**Since:** 0.3.0

--------------------


### transceive(...)

```typescript
transceive(options: TransceiveOptions) => Promise<TransceiveResult>
```

Send raw command to the tag and receive the response.

This method must be called from within a `nfcTagScanned` handler.

On **Android**, the tag must be connected with `connect()` first.

⚠️ **Experimental:** This method could not be tested extensively yet.
Please let us know if you discover any issues!

⚠️ **Attention**: A bad command can damage the tag forever.
Please read the Android and iOS documentation linked below first.

More information on how to use this method on **Android**: https://bit.ly/3ywSkvT

More information on how to use this method on **iOS** with...
- ISO 15693-3: https://apple.co/3Lliaum
- FeliCa: https://apple.co/3LpvRs6

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#transceiveoptions">TransceiveOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#transceiveresult">TransceiveResult</a>&gt;</code>

**Since:** 0.3.0

--------------------


### connect(...)

```typescript
connect(options: ConnectOptions) => Promise<void>
```

Connect to the tag and enable I/O operations.

This method must be called from within a `nfcTagScanned` handler.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#connectoptions">ConnectOptions</a></code> |

**Since:** 6.0.0

--------------------


### close()

```typescript
close() => Promise<void>
```

Close the connection to the tag.

This method must be called from within a `nfcTagScanned` handler.

Only available on Android.

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Returns whether or not NFC is available.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 7.2.0

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Returns whether or not NFC is supported.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Returns whether or not NFC is enabled.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Opens the NFC device settings so that the user can enable or disable NFC.

Only available on Android.

**Since:** 0.0.1

--------------------


### getAntennaInfo()

```typescript
getAntennaInfo() => Promise<GetAntennaInfoResult>
```

Returns information regarding Nfc antennas on the device such as their relative positioning on the device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getantennainforesult">GetAntennaInfoResult</a>&gt;</code>

**Since:** 6.1.0

--------------------


### setAlertMessage(...)

```typescript
setAlertMessage(options: SetAlertMessageOptions) => Promise<void>
```

Set a custom message, which is displayed in the NFC Reader Session alert.

Only available on iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setalertmessageoptions">SetAlertMessageOptions</a></code> |

**Since:** 6.2.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionResult>
```

Check permission for reading and writing NFC tags.

This method is only needed on Web.
On Android and iOS, `granted` is always returned.

**Returns:** <code>Promise&lt;<a href="#permissionresult">PermissionResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionResult>
```

Request permission for reading and writing NFC tags.

This method is only needed on Web.
On Android and iOS, `granted` is always returned.

**Returns:** <code>Promise&lt;<a href="#permissionresult">PermissionResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('commandReceived', ...)

```typescript
addListener(eventName: 'commandReceived', listenerFunc: (event: CommandReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called whenever a NFC reader sends an Application Protocol Data Unit (APDU).

Only available on Android.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'commandReceived'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#commandreceivedevent">CommandReceivedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.3.0

--------------------


### addListener('nfcLinkDeactivated', ...)

```typescript
addListener(eventName: 'nfcLinkDeactivated', listenerFunc: (event: NfcLinkDeactivatedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the NFC link has been deactivated or lost.

Only available on Android.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'nfcLinkDeactivated'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#nfclinkdeactivatedevent">NfcLinkDeactivatedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.3.0

--------------------


### addListener('nfcTagScanned', ...)

```typescript
addListener(eventName: 'nfcTagScanned', listenerFunc: (event: NfcTagScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a new NFC tag is scanned.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'nfcTagScanned'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#nfctagscannedevent">NfcTagScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('scanSessionCanceled', ...)

```typescript
addListener(eventName: 'scanSessionCanceled', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the scan session was canceled.

Only available on iOS.

| Param              | Type                               |
| ------------------ | ---------------------------------- |
| **`eventName`**    | <code>'scanSessionCanceled'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>         |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('scanSessionError', ...)

```typescript
addListener(eventName: 'scanSessionError', listenerFunc: (event: ScanSessionErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during the scan session.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanSessionError'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#scansessionerrorevent">ScanSessionErrorEvent</a>) =&gt; void</code> |

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


#### StartScanSessionOptions

| Prop                    | Type                          | Description                                                                                                                                                                                                  | Default                                                       | Since |
| ----------------------- | ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------- | ----- |
| **`alertMessage`**      | <code>string</code>           | A custom message, which is displayed in the NFC Reader Session alert. Only available on iOS.                                                                                                                 |                                                               | 0.0.1 |
| **`compatibilityMode`** | <code>boolean</code>          | Whether or not to use the [`NFCNDEFReaderSession`](https://developer.apple.com/documentation/corenfc/nfcndefreadersession). **Attention:** This mode only supports reading NDEF tags. Only available on iOS. | <code>false</code>                                            | 6.4.0 |
| **`techTypes`**         | <code>NfcTagTechType[]</code> | The NFC tag technologies to filter on in this session. Only available on Android.                                                                                                                            |                                                               | 0.0.1 |
| **`mimeTypes`**         | <code>string[]</code>         | Mime types to filter on in this session. Only available on Android.                                                                                                                                          |                                                               | 0.0.1 |
| **`pollingOptions`**    | <code>PollingOption[]</code>  | Type of tags to detect during a polling sequence. Only available on iOS.                                                                                                                                     | <code>[PollingOption.iso14443, PollingOption.iso15693]</code> | 0.2.0 |


#### StopScanSessionOptions

| Prop               | Type                | Description                                                                                        | Since |
| ------------------ | ------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`errorMessage`** | <code>string</code> | A custom error message, which is displayed in the NFC Reader Session alert. Only available on iOS. | 0.0.1 |


#### WriteOptions

| Prop          | Type                                                | Description                | Since |
| ------------- | --------------------------------------------------- | -------------------------- | ----- |
| **`message`** | <code><a href="#ndefmessage">NdefMessage</a></code> | The NDEF message to write. | 0.0.1 |


#### NdefMessage

| Prop          | Type                      | Description                      | Since |
| ------------- | ------------------------- | -------------------------------- | ----- |
| **`records`** | <code>NdefRecord[]</code> | The records of the NDEF message. | 0.0.1 |


#### NdefRecord

| Prop          | Type                                                      | Description                                                                                                              | Since |
| ------------- | --------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`id`**      | <code>number[]</code>                                     | The record identifier as byte array.                                                                                     | 0.0.1 |
| **`payload`** | <code>number[]</code>                                     | The payload field data as byte array.                                                                                    | 0.0.1 |
| **`tnf`**     | <code><a href="#typenameformat">TypeNameFormat</a></code> | The record type name format which indicates the structure of the value of the `type` field.                              | 0.0.1 |
| **`type`**    | <code>number[]</code>                                     | The type of the record payload. This should be used in conjunction with the `tnf` field to determine the payload format. | 0.0.1 |


#### RespondOptions

| Prop       | Type                  | Description    | Since |
| ---------- | --------------------- | -------------- | ----- |
| **`data`** | <code>number[]</code> | Bytes to send. | 6.3.0 |


#### TransceiveResult

| Prop           | Type                  | Description                 | Since |
| -------------- | --------------------- | --------------------------- | ----- |
| **`response`** | <code>number[]</code> | Bytes received in response. | 0.3.0 |


#### TransceiveOptions

| Prop                       | Type                                                      | Description                                                                                                                                                                                                       | Since |
| -------------------------- | --------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`techType`**             | <code><a href="#nfctagtechtype">NfcTagTechType</a></code> | The NFC tag technology to connect with. Only available on iOS.                                                                                                                                                    | 0.3.0 |
| **`data`**                 | <code>number[]</code>                                     | Bytes to send.                                                                                                                                                                                                    | 0.3.0 |
| **`iso15693RequestFlags`** | <code>Iso15693RequestFlag[]</code>                        | The request flags for the NFC tag technology type `NfcV` (ISO 15693-3). Only available on iOS 14+                                                                                                                 | 0.3.0 |
| **`iso15693CommandCode`**  | <code>number</code>                                       | The custom command code defined by the IC manufacturer for the NFC tag technology type `NfcV` (ISO 15693-3). Valid range is 0xA0 to 0xDF inclusively, 0x23 and 0x24 are also supported. Only available on iOS 14+ | 0.3.0 |


#### ConnectOptions

| Prop           | Type                                                      | Description                                                        | Since |
| -------------- | --------------------------------------------------------- | ------------------------------------------------------------------ | ----- |
| **`techType`** | <code><a href="#nfctagtechtype">NfcTagTechType</a></code> | The NFC tag technology to connect with. Only available on Android. | 6.0.0 |


#### IsAvailableResult

| Prop      | Type                 | Description                                                          | Since |
| --------- | -------------------- | -------------------------------------------------------------------- | ----- |
| **`hce`** | <code>boolean</code> | Whether or not Host Card Emulation (HCE) is available on the device. | 7.2.0 |
| **`nfc`** | <code>boolean</code> | Whether or not NFC is available on the device.                       | 7.2.0 |


#### IsSupportedResult

| Prop              | Type                 | Description                                                          | Since |
| ----------------- | -------------------- | -------------------------------------------------------------------- | ----- |
| **`isSupported`** | <code>boolean</code> |                                                                      | 0.0.1 |
| **`nfc`**         | <code>boolean</code> | Whether or not NFC is supported on the device.                       | 6.3.0 |
| **`hce`**         | <code>boolean</code> | Whether or not Host Card Emulation (HCE) is supported on the device. | 6.3.0 |


#### IsEnabledResult

| Prop            | Type                 | Since |
| --------------- | -------------------- | ----- |
| **`isEnabled`** | <code>boolean</code> | 0.0.1 |


#### GetAntennaInfoResult

| Prop                    | Type                   | Description                               | Since |
| ----------------------- | ---------------------- | ----------------------------------------- | ----- |
| **`availableAntennas`** | <code>Antenna[]</code> | The available NFC antennas on the device. | 6.1.0 |
| **`deviceHeight`**      | <code>number</code>    | The height of the device in millimeters.  | 6.1.0 |
| **`deviceWidth`**       | <code>number</code>    | The width of the device in millimeters.   | 6.1.0 |
| **`isDeviceFoldable`**  | <code>boolean</code>   | Whether or not the device is foldable.    | 6.1.0 |


#### Antenna

| Prop            | Type                | Description                                                   | Since |
| --------------- | ------------------- | ------------------------------------------------------------- | ----- |
| **`locationX`** | <code>number</code> | The location of the NFC antenna on the X axis in millimeters. | 6.1.0 |
| **`locationY`** | <code>number</code> | The location of the NFC antenna on the Y axis in millimeters. | 6.1.0 |


#### SetAlertMessageOptions

| Prop          | Type                | Description                                                             | Since |
| ------------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`message`** | <code>string</code> | The custom message, which is displayed in the NFC Reader Session alert. | 6.2.0 |


#### PermissionResult

| Prop      | Type                                                        | Description                                        | Since |
| --------- | ----------------------------------------------------------- | -------------------------------------------------- | ----- |
| **`nfc`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for reading and writing NFC tags. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### CommandReceivedEvent

| Prop       | Type                  | Description                                  | Since |
| ---------- | --------------------- | -------------------------------------------- | ----- |
| **`data`** | <code>number[]</code> | The command received from the remote device. | 6.3.0 |


#### NfcLinkDeactivatedEvent

| Prop         | Type                                                              | Description                               | Since |
| ------------ | ----------------------------------------------------------------- | ----------------------------------------- | ----- |
| **`reason`** | <code><a href="#deactivationreason">DeactivationReason</a></code> | The reason why the deactivation occurred. | 6.3.0 |


#### NfcTagScannedEvent

| Prop         | Type                                      | Description          | Since |
| ------------ | ----------------------------------------- | -------------------- | ----- |
| **`nfcTag`** | <code><a href="#nfctag">NfcTag</a></code> | The scanned NFC tag. | 0.0.1 |


#### NfcTag

| Prop                   | Type                                                | Description                                                                                        | Since |
| ---------------------- | --------------------------------------------------- | -------------------------------------------------------------------------------------------------- | ----- |
| **`atqa`**             | <code>number[]</code>                               | The ATQA/SENS_RES bytes of an NFC A tag. Only available on Android.                                | 0.0.1 |
| **`applicationData`**  | <code>number[]</code>                               | The Application Data bytes from ATQB/SENSB_RES of an NFC B tag. Only available on Android and iOS. | 0.0.1 |
| **`barcode`**          | <code>number[]</code>                               | The barcode bytes of an NfcBarcode tag. Only available on Android.                                 | 0.0.1 |
| **`canMakeReadOnly`**  | <code>boolean</code>                                | Whether the NDEF tag can be made read-only or not. Only available on Android.                      | 0.0.1 |
| **`dsfId`**            | <code>number[]</code>                               | The DSF ID bytes of an NFC V tag. Only available on Android.                                       | 0.0.1 |
| **`hiLayerResponse`**  | <code>number[]</code>                               | The higher layer response bytes of an ISO-DEP tag. Only available on Android.                      | 0.0.1 |
| **`historicalBytes`**  | <code>number[]</code>                               | The historical bytes of an ISO-DEP tag. Only available on Android and iOS.                         | 0.0.1 |
| **`id`**               | <code>number[]</code>                               | The tag identifier (low level serial number) which is used for anti-collision and identification.  | 0.0.1 |
| **`isWritable`**       | <code>boolean</code>                                | Whether the NDEF tag is writable or not. Only available on Android and iOS.                        | 0.0.1 |
| **`manufacturer`**     | <code>number[]</code>                               | The manufacturer bytes of an NFC F tag. Only available on Android and iOS.                         | 0.0.1 |
| **`manufacturerCode`** | <code>number</code>                                 | The Integrated Circuit (IC) manufacturer code of an NFC V tag. Only available on iOS.              | 6.3.0 |
| **`maxSize`**          | <code>number</code>                                 | The maximum NDEF message size in bytes. Only available on Android and iOS.                         | 0.0.1 |
| **`message`**          | <code><a href="#ndefmessage">NdefMessage</a></code> | The NDEF-formatted message.                                                                        | 0.0.1 |
| **`protocolInfo`**     | <code>number[]</code>                               | The Protocol Info bytes from ATQB/SENSB_RES of an NFC B tag. Only available on Android.            | 0.0.1 |
| **`responseFlags`**    | <code>number[]</code>                               | The Response Flag bytes of an NFC V tag. Only available on Android.                                | 0.0.1 |
| **`sak`**              | <code>number[]</code>                               | The SAK/SEL_RES bytes of an NFC A tag. Only available on Android.                                  | 0.0.1 |
| **`systemCode`**       | <code>number[]</code>                               | The System Code bytes of an NFC F tag. Only available on Android and iOS.                          | 0.0.1 |
| **`techTypes`**        | <code>NfcTagTechType[]</code>                       | The technologies available in this tag. Only available on Android and iOS.                         | 0.0.1 |
| **`type`**             | <code><a href="#nfctagtype">NfcTagType</a></code>   | The NDEF tag type. Only available on Android and iOS.                                              | 0.0.1 |


#### ScanSessionErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


### Type Aliases


#### PermissionState

<code>"denied" | "granted" | "prompt"</code>


### Enums


#### NfcTagTechType

| Members                | Value                            | Description                                                                   | Since |
| ---------------------- | -------------------------------- | ----------------------------------------------------------------------------- | ----- |
| **`NfcA`**             | <code>'NFC_A'</code>             | The NFC-A (ISO 14443-3A) tag technology. Only available on Android.           | 0.0.1 |
| **`NfcB`**             | <code>'NFC_B'</code>             | The NFC-B (ISO 14443-3B) tag technology. Only available on Android.           | 0.0.1 |
| **`NfcF`**             | <code>'NFC_F'</code>             | The NFC-F (JIS 6319-4) tag technology. Only available on Android and iOS.     | 0.0.1 |
| **`NfcV`**             | <code>'NFC_V'</code>             | The NFC-V (ISO 15693) tag technology. Only available on Android and iOS.      | 0.0.1 |
| **`IsoDep`**           | <code>'ISO_DEP'</code>           | The ISO-DEP (ISO 14443-4) tag technology. Only available on Android.          | 0.0.1 |
| **`Iso7816`**          | <code>'ISO_7816'</code>          | The ISO 7816 tag technology. Only available on iOS.                           | 5.1.0 |
| **`Ndef`**             | <code>'NDEF'</code>              | The NDEF tag technology. Only available on Android.                           | 0.0.1 |
| **`MifareClassic`**    | <code>'MIFARE_CLASSIC'</code>    | The MIFARE Classic tag technology. Only available on Android.                 | 0.0.1 |
| **`MifareDesfire`**    | <code>'MIFARE_DESFIRE'</code>    | The MIFARE Desfire tag technology. Only available on iOS.                     | 5.1.0 |
| **`MifarePlus`**       | <code>'MIFARE_PLUS'</code>       | The MIFARE Plus tag technology. Only available on iOS.                        | 5.1.0 |
| **`MifareUltralight`** | <code>'MIFARE_ULTRALIGHT'</code> | The MIFARE Ultralight tag technology. Only available on Android and iOS.      | 0.0.1 |
| **`NfcBarcode`**       | <code>'NFC_BARCODE'</code>       | The technology of a tag containing just a barcode. Only available on Android. | 0.0.1 |
| **`NdefFormatable`**   | <code>'NDEF_FORMATABLE'</code>   | The NDEF formatable tag technology. Only available on Android.                | 0.0.1 |


#### PollingOption

| Members        | Value                   | Description                                                   | Since |
| -------------- | ----------------------- | ------------------------------------------------------------- | ----- |
| **`iso14443`** | <code>'iso14443'</code> | The option for detecting ISO 7816-compatible and MIFARE tags. | 0.2.0 |
| **`iso15693`** | <code>'iso15693'</code> | The option for detecting ISO 15693 tags.                      | 0.2.0 |
| **`iso18092`** | <code>'iso18092'</code> | The option for detecting FeliCa tags.                         | 0.2.0 |


#### TypeNameFormat

| Members           | Value          | Description                                                                                            | Since |
| ----------------- | -------------- | ------------------------------------------------------------------------------------------------------ | ----- |
| **`Empty`**       | <code>0</code> | An empty record with no type or payload.                                                               | 0.0.1 |
| **`WellKnown`**   | <code>1</code> | A predefined type defined in the RTD specification of the NFC Forum.                                   | 0.0.1 |
| **`MimeMedia`**   | <code>2</code> | An Internet media type as defined in RFC 2046.                                                         | 0.0.1 |
| **`AbsoluteUri`** | <code>3</code> | A URI as defined in RFC 3986.                                                                          | 0.0.1 |
| **`External`**    | <code>4</code> | A user-defined value that is based on the rules of the NFC Forum Record Type Definition specification. | 0.0.1 |
| **`Unknown`**     | <code>5</code> | Type is unknown.                                                                                       | 0.0.1 |
| **`Unchanged`**   | <code>6</code> | Indicates the payload is an intermediate or final chunk of a chunked NDEF Record.                      | 0.0.1 |


#### Iso15693RequestFlag

| Members                   | Value                              | Since |
| ------------------------- | ---------------------------------- | ----- |
| **`address`**             | <code>'address'</code>             | 0.3.0 |
| **`commandSpecificBit8`** | <code>'commandSpecificBit8'</code> | 0.3.0 |
| **`dualSubCarriers`**     | <code>'dualSubCarriers'</code>     | 0.3.0 |
| **`highDataRate`**        | <code>'highDataRate'</code>        | 0.3.0 |
| **`option`**              | <code>'option'</code>              | 0.3.0 |
| **`protocolExtension`**   | <code>'protocolExtension'</code>   | 0.3.0 |
| **`select`**              | <code>'select'</code>              | 0.3.0 |


#### DeactivationReason

| Members          | Value          | Description                                                       | Since |
| ---------------- | -------------- | ----------------------------------------------------------------- | ----- |
| **`LinkLoss`**   | <code>0</code> | Indicates deactivation was due to the NFC link being lost.        | 6.3.0 |
| **`Deselected`** | <code>1</code> | Indicates deactivation was due to a different AID being selected. | 6.3.0 |


#### NfcTagType

| Members                 | Value                              | Description                        | Since |
| ----------------------- | ---------------------------------- | ---------------------------------- | ----- |
| **`NfcForumType1`**     | <code>'NFC_FORUM_TYPE_1'</code>    | Only available on Android.         | 0.0.1 |
| **`NfcForumType2`**     | <code>'NFC_FORUM_TYPE_2'</code>    | Only available on Android.         | 0.0.1 |
| **`NfcForumType3`**     | <code>'NFC_FORUM_TYPE_3'</code>    | Only available on Android and iOS. | 0.0.1 |
| **`NfcForumType4`**     | <code>'NFC_FORUM_TYPE_4'</code>    | Only available on Android.         | 0.0.1 |
| **`MifareClassic`**     | <code>'MIFARE_CLASSIC'</code>      | Only available on Android.         | 0.0.1 |
| **`MifareDesfire`**     | <code>'MIFARE_DESFIRE'</code>      |                                    | 0.0.1 |
| **`MifarePlus`**        | <code>'MIFARE_PLUS'</code>         | Only available on Android.         | 0.0.1 |
| **`MifarePro`**         | <code>'MIFARE_PRO'</code>          | Only available on Android.         | 0.0.1 |
| **`MifareUltralight`**  | <code>'MIFARE_ULTRALIGHT'</code>   | Only available on Android.         | 0.0.1 |
| **`MifareUltralightC`** | <code>'MIFARE_ULTRALIGHT_C'</code> | Only available on Android.         | 0.0.1 |

</docgen-api>

## Utils

This plugin provides a utility class `NfcUtils` that can be used for various NFC-related operations, for example, creating NDEF records:

```ts
import { NfcUtils } from '@capawesome-team/capacitor-nfc';

const createNdefTextRecord = () => {
  const utils = new NfcUtils();
  const { record } = utils.createNdefTextRecord({ text: 'Capacitor NFC Plugin' });
  return record;
};
```

See [docs/utils/README.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/nfc/docs/utils/README.md) for more information.

## Troubleshooting

##### `The connection to service named com.apple.nfcd.service.corenfc was invalidated from this process`

This error occurs on iOS when the required NFC capability is not added to the app. To fix this, add the `Near Field Communication Tag Reading` capability in Xcode under the `Signing & Capabilities` tab. See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/nfc/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/nfc/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/nfc/LICENSE).
