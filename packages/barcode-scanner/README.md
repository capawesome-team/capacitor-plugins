# Capacitor Barcode Scanner Plugin

Capacitor plugin for scanning barcodes and QR codes with a themeable fullscreen scanner or an embedded camera view on Android, iOS, and Web.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Barcode Scanner plugin provides a premium barcode scanning experience with two presentation modes. Here are some of the key features:

- 🖥️ **Ready-Made UI**: Fullscreen scanner with viewfinder, torch and flip camera buttons.
- 🎨 **Themeable**: Customize the accent color, title, instructions, and buttons of the scanner UI.
- 📦 **Batch Scanning**: Collect multiple barcodes in one session before returning them all at once.
- 🖼️ **Embedded View**: Render the camera natively inside any frame of your app layout.
- ⏯️ **Pause & Resume**: Pause and resume the barcode detection without stopping the camera.
- 🔦 **Torch & Zoom**: Control the flashlight and the camera zoom during a scan session.
- 🎯 **Detection Area**: Restrict the barcode detection to a region of interest.
- 🔁 **Deduplication**: Configurable timeout before the same barcode is emitted again.
- 🏷️ **13 Barcode Formats**: QR code, Aztec, Codabar, Code 39, Code 93, Code 128, Data Matrix, EAN-8, EAN-13, ITF, PDF417, UPC-A, and UPC-E.
- 📷 **Image Reading**: Read barcodes from image files.
- 🍎 **Zero Dependencies on iOS**: Built on AVFoundation and Vision only, so there are no third-party pods and no simulator issues.
- 🤖 **Offline on Android**: Uses the bundled ML Kit model, so scanning works offline and without Google Play services.
- 🌐 **Web Support**: Embedded scanning and image reading via the `BarcodeDetector` API.
- 🤝 **Compatibility**: Works hand in hand with the [Document Scanner](https://capawesome.io/docs/sdks/capacitor/document-scanner/), [Torch](https://capawesome.io/docs/sdks/capacitor/torch/) and [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Barcode Scanner plugin is typically used whenever an app needs to capture barcode or QR code data, for example:

- **Retail and inventory**: Scan product barcodes for stock management or price lookup.
- **Ticketing and events**: Validate QR codes on tickets, also in batch mode at the entrance.
- **Logistics**: Scan parcel labels (Code 128, ITF, PDF417) in a continuous embedded scanner.
- **Payments and links**: Let users scan QR codes to open links or initiate payments.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Demo

| Android                                                                                                                                                                              | iOS                                                                                                                                                                              |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/barcode-scanner/assets/barcode-scanner-demo-android.mp4" width="324" controls></video> | <video src="https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/barcode-scanner/assets/barcode-scanner-demo-ios.mp4" width="266" controls></video> |

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-barcode-scanner` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-barcode-scanner
npx cap sync
```

### Android

The plugin declares the camera permission in its own manifest, so no manifest changes are required.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default version of the dependencies:

- `$androidxCameraVersion` version of the `androidx.camera` dependencies (default: `1.6.1`)
- `$mlkitBarcodeScanningVersion` version of `com.google.mlkit:barcode-scanning` (default: `17.3.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

The plugin uses the **bundled** ML Kit barcode scanning model. Scanning works offline and does not require Google Play services, but the model increases your app size by a few megabytes. The default dependency versions are compatible with the 16 KB page size requirement of Google Play.

### iOS

Add the `NSCameraUsageDescription` key to the `Info.plist` file of your app to explain why your app needs access to the camera:

```xml
<key>NSCameraUsageDescription</key>
<string>The app needs access to the camera to scan barcodes.</string>
```

If the key is missing, `scan(...)` and `startScan(...)` reject with a clear error message.

### Web

The web implementation is based on the [BarcodeDetector](https://developer.mozilla.org/en-US/docs/Web/API/BarcodeDetector) API, which is not available in all browsers. For browsers without built-in support, we recommend the [barcode-detector](https://www.npmjs.com/package/barcode-detector) polyfill:

```typescript
import 'barcode-detector/side-effects';
```

The polyfill is intentionally **not** bundled with this plugin, so you stay in control of your bundle size.

## Configuration

No configuration required for this plugin.

## Usage

The plugin offers two presentation modes: a ready-made, themeable native fullscreen scanner (`scan(...)`) and an embedded camera view that is rendered natively inside a frame of your app layout (`startScan(...)`). The following examples show how to check the availability and permissions, scan barcodes with both modes, and read barcodes from image files.

### Check the availability and permissions

Check whether barcode scanning is available on the device and request the camera permission before you start a scan session:

```typescript
import { BarcodeScanner } from '@capawesome-team/capacitor-barcode-scanner';

const isAvailable = async () => {
  const { available } = await BarcodeScanner.isAvailable();
  return available;
};

const requestPermissions = async () => {
  const { camera } = await BarcodeScanner.requestPermissions();
  return camera;
};
```

Get the cameras available on the device, for example to hide or disable a camera flip button if the device has only one camera:

```typescript
import {
  BarcodeScanner,
  LensFacing,
} from '@capawesome-team/capacitor-barcode-scanner';

const canFlipCamera = async () => {
  const { lensFacings } = await BarcodeScanner.getAvailableCameras();
  return (
    lensFacings.includes(LensFacing.Back) &&
    lensFacings.includes(LensFacing.Front)
  );
};
```

If the user has denied the camera permission, open the native app settings page so that the user can grant it:

```typescript
import { BarcodeScanner } from '@capawesome-team/capacitor-barcode-scanner';

const openSettings = async () => {
  await BarcodeScanner.openSettings();
};
```

### Scan a barcode with the fullscreen scanner

Open the ready-made fullscreen scanner and customize its user interface. Only barcodes that are fully inside the viewfinder are detected. The promise resolves with the first detected barcode:

```typescript
import {
  BarcodeFormat,
  BarcodeScanner,
} from '@capawesome-team/capacitor-barcode-scanner';

const scanSingleBarcode = async () => {
  const { barcodes } = await BarcodeScanner.scan({
    formats: [BarcodeFormat.QrCode, BarcodeFormat.Ean13],
    ui: {
      accentColor: '#59C7F9',
      instructions: 'Point your camera at a barcode.',
      title: 'Scan Barcode',
    },
  });
  return barcodes[0];
};
```

### Scan multiple barcodes in one session

Enable the batch mode to let the user collect multiple barcodes. The promise resolves with all of them when the user taps the done button:

```typescript
import { BarcodeScanner } from '@capawesome-team/capacitor-barcode-scanner';

const scanMultipleBarcodes = async () => {
  const { barcodes } = await BarcodeScanner.scan({
    batch: true,
  });
  return barcodes;
};
```

### Scan barcodes with the embedded camera view

Start an embedded scan session to render the camera preview inside a frame of your app layout. Detected barcodes are emitted continuously via the `barcodesScanned` event until you call `stopScan()`.

The camera preview is a **native view**. By default, it is rendered **above** the web view, which means that HTML elements cannot overlap the camera preview. Use a placeholder element to reserve the space in your layout and to measure the frame:

```typescript
import {
  BarcodeScanner,
  LensFacing,
} from '@capawesome-team/capacitor-barcode-scanner';

const getScanFrame = () => {
  const rect = document.querySelector('#scanner').getBoundingClientRect();
  return { x: rect.x, y: rect.y, width: rect.width, height: rect.height };
};

const startEmbeddedScan = async () => {
  await BarcodeScanner.addListener('barcodesScanned', (event) => {
    console.log('Scanned barcodes:', event.barcodes);
  });
  await BarcodeScanner.startScan({
    frame: getScanFrame(),
    lensFacing: LensFacing.Back,
  });
};

const stopEmbeddedScan = async () => {
  await BarcodeScanner.stopScan();
  await BarcodeScanner.removeAllListeners();
};
```

### Overlay HTML elements over the embedded camera view

Set `placement` to `PreviewPlacement.Behind` to render the camera preview **behind** the web view. This way, HTML elements can overlap the camera preview, for example to draw a viewfinder or detection markers.

The camera preview is only visible where your app is transparent. The placeholder element used to measure the frame, all its ancestors and the `body` must have a transparent background over the frame area:

```css
body,
#scanner {
  background: transparent;
}
```

With Ionic Framework UI components, also set `--background: transparent` on the surrounding `ion-content` element.

```typescript
import {
  BarcodeScanner,
  PreviewPlacement,
} from '@capawesome-team/capacitor-barcode-scanner';

const startEmbeddedScanBehindWebView = async () => {
  await BarcodeScanner.startScan({
    frame: getScanFrame(),
    placement: PreviewPlacement.Behind,
  });
};
```

### Update the frame of the embedded camera view

When the layout of your app changes (for example after an orientation change), update the frame of the active scan session:

```typescript
import { BarcodeScanner } from '@capawesome-team/capacitor-barcode-scanner';

window.addEventListener('resize', async () => {
  await BarcodeScanner.setScanFrame({ frame: getScanFrame() });
});
```

### Read barcodes from an image

Read barcodes from an image file, for example from an image the user picked with the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/) plugin:

```typescript
import { BarcodeScanner } from '@capawesome-team/capacitor-barcode-scanner';

const readBarcodesFromImage = async (path: string) => {
  const { barcodes } = await BarcodeScanner.readBarcodesFromImage({
    path,
  });
  return barcodes;
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`getAvailableCameras()`](#getavailablecameras)
* [`getZoomRatioRange()`](#getzoomratiorange)
* [`isAvailable()`](#isavailable)
* [`openSettings()`](#opensettings)
* [`pauseScan()`](#pausescan)
* [`readBarcodesFromImage(...)`](#readbarcodesfromimage)
* [`requestPermissions()`](#requestpermissions)
* [`resumeScan()`](#resumescan)
* [`scan(...)`](#scan)
* [`setScanFrame(...)`](#setscanframe)
* [`setTorchEnabled(...)`](#settorchenabled)
* [`setZoomRatio(...)`](#setzoomratio)
* [`startScan(...)`](#startscan)
* [`stopScan()`](#stopscan)
* [`addListener('barcodesScanned', ...)`](#addlistenerbarcodesscanned-)
* [`addListener('scanError', ...)`](#addlistenerscanerror-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the current camera permission status.

On **Web**, the permission status is queried on a best-effort basis.
Some browsers do not support the camera permission query and always
return `prompt`.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getAvailableCameras()

```typescript
getAvailableCameras() => Promise<GetAvailableCamerasResult>
```

Get the cameras available on the device.

Use this, for example, to hide or disable a camera flip button
if the device has only one camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getavailablecamerasresult">GetAvailableCamerasResult</a>&gt;</code>

**Since:** 0.1.1

--------------------


### getZoomRatioRange()

```typescript
getZoomRatioRange() => Promise<GetZoomRatioRangeResult>
```

Get the minimum and maximum zoom ratio supported by the camera.

The promise rejects with the `NOT_SCANNING` error code if no scan
session started with `startScan(...)` is active.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getzoomratiorangeresult">GetZoomRatioRangeResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether barcode scanning is available on the device.

On **Android** and **iOS**, this resolves to `true` if the device has
a camera.

On **Web**, this resolves to `true` if the browser supports camera
access and the `BarcodeDetector` API is available (natively or via a
polyfill).

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the native app settings page so that the user can grant the
camera permission.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### pauseScan()

```typescript
pauseScan() => Promise<void>
```

Pause the barcode detection of the active scan session.

The camera preview stays visible but no more `barcodesScanned` events
are emitted until `resumeScan()` is called.

The promise rejects with the `NOT_SCANNING` error code if no scan
session is active.

**Since:** 0.0.1

--------------------


### readBarcodesFromImage(...)

```typescript
readBarcodesFromImage(options: ReadBarcodesFromImageOptions) => Promise<ReadBarcodesFromImageResult>
```

Read barcodes from an image file.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#readbarcodesfromimageoptions">ReadBarcodesFromImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readbarcodesfromimageresult">ReadBarcodesFromImageResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the camera permission.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### resumeScan()

```typescript
resumeScan() => Promise<void>
```

Resume the barcode detection of the active scan session after it has
been paused with `pauseScan()`.

The promise rejects with the `NOT_SCANNING` error code if no scan
session is active.

**Since:** 0.0.1

--------------------


### scan(...)

```typescript
scan(options?: ScanOptions | undefined) => Promise<ScanResult>
```

Open the ready-made fullscreen scanner user interface.

In single-shot mode (default), the promise resolves with the first
detected barcode. In batch mode, the user collects multiple barcodes
and the promise resolves with all of them when the user taps the done
button.

Only barcodes that are fully inside the viewfinder are detected.

The promise rejects with the `SCAN_CANCELED` error code if the user
closes the scanner without a result.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#scanoptions">ScanOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#scanresult">ScanResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### setScanFrame(...)

```typescript
setScanFrame(options: SetScanFrameOptions) => Promise<void>
```

Update the frame of the active scan session, for example after an
orientation change or when the layout of your app changes.

The promise rejects with the `NOT_SCANNING` error code if no scan
session is active.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setscanframeoptions">SetScanFrameOptions</a></code> |

**Since:** 0.0.1

--------------------


### setTorchEnabled(...)

```typescript
setTorchEnabled(options: SetTorchEnabledOptions) => Promise<void>
```

Enable or disable the torch (flashlight) during an active scan session.

The promise rejects with the `NOT_SCANNING` error code if no scan
session started with `startScan(...)` is active.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#settorchenabledoptions">SetTorchEnabledOptions</a></code> |

**Since:** 0.0.1

--------------------


### setZoomRatio(...)

```typescript
setZoomRatio(options: SetZoomRatioOptions) => Promise<void>
```

Set the zoom ratio of the camera during an active scan session.

The promise rejects with the `NOT_SCANNING` error code if no scan
session started with `startScan(...)` is active.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setzoomratiooptions">SetZoomRatioOptions</a></code> |

**Since:** 0.0.1

--------------------


### startScan(...)

```typescript
startScan(options: StartScanOptions) => Promise<void>
```

Start an embedded scan session.

The camera preview is rendered natively in the given frame. Use a
placeholder element to measure the frame (e.g. with
`getBoundingClientRect()`) and reserve the space in your layout.

By default, the camera preview is rendered **above** the web view so
that HTML elements cannot overlap it. Set `placement` to
`PreviewPlacement.Behind` to render the camera preview **behind** the
web view so that HTML elements can overlap it (see the `placement`
property for the requirements).

Detected barcodes are emitted via the `barcodesScanned` event until
`stopScan()` is called.

The promise rejects with the `ALREADY_SCANNING` error code if a scan
session is already active.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#startscanoptions">StartScanOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

Stop the active scan session and remove the camera preview.

The promise rejects with the `NOT_SCANNING` error code if no scan
session is active.

**Since:** 0.0.1

--------------------


### addListener('barcodesScanned', ...)

```typescript
addListener(eventName: 'barcodesScanned', listenerFunc: (event: BarcodesScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when barcodes are detected during an active scan session.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'barcodesScanned'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#barcodesscannedevent">BarcodesScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('scanError', ...)

```typescript
addListener(eventName: 'scanError', listenerFunc: (event: ScanErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during an active scan session.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanError'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#scanerrorevent">ScanErrorEvent</a>) =&gt; void</code> |

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

| Prop         | Type                                                        | Description                  | Since |
| ------------ | ----------------------------------------------------------- | ---------------------------- | ----- |
| **`camera`** | <code><a href="#permissionstate">PermissionState</a></code> | The camera permission state. | 0.0.1 |


#### GetAvailableCamerasResult

| Prop              | Type                      | Description                                              | Since |
| ----------------- | ------------------------- | -------------------------------------------------------- | ----- |
| **`lensFacings`** | <code>LensFacing[]</code> | The lens facings of the cameras available on the device. | 0.1.1 |


#### GetZoomRatioRangeResult

| Prop      | Type                | Description             | Since |
| --------- | ------------------- | ----------------------- | ----- |
| **`max`** | <code>number</code> | The maximum zoom ratio. | 0.0.1 |
| **`min`** | <code>number</code> | The minimum zoom ratio. | 0.0.1 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                 | Since |
| --------------- | -------------------- | ----------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not barcode scanning is available on the device. | 0.0.1 |


#### ReadBarcodesFromImageResult

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### Barcode

| Prop               | Type                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | Since |
| ------------------ | ------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bytes`**        | <code>number[] \| null</code>                           | The raw bytes of the barcode. Only available on Android. On iOS and Web, this is always `null`.                                                                                                                                                                                                                                                                                                                                                                                                                              | 0.0.1 |
| **`cornerPoints`** | <code>[number, number][] \| null</code>                 | The four corner points of the barcode in clockwise order starting at the top-left corner. During an embedded scan session, the corner points are in CSS pixels relative to the top-left corner of the scan frame. During a fullscreen scan session, the corner points are in CSS pixels relative to the top-left corner of the screen. For `readBarcodesFromImage(...)`, the corner points are in pixels relative to the top-left corner of the image. This value might be `null` if the corner points cannot be determined. | 0.0.1 |
| **`displayValue`** | <code>string</code>                                     | The barcode value in a human-readable format.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                | 0.0.1 |
| **`format`**       | <code><a href="#barcodeformat">BarcodeFormat</a></code> | The format of the barcode.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   | 0.0.1 |
| **`rawValue`**     | <code>string</code>                                     | The barcode value as it was encoded in the barcode.                                                                                                                                                                                                                                                                                                                                                                                                                                                                          | 0.0.1 |


#### ReadBarcodesFromImageOptions

| Prop          | Type                         | Description                                                                                                                                  | Since |
| ------------- | ---------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`formats`** | <code>BarcodeFormat[]</code> | The barcode formats to detect. If not set, all supported formats are detected. Improve the performance by only setting the formats you need. | 0.0.1 |
| **`path`**    | <code>string</code>          | The path of the image file to read the barcodes from. On **Web**, this must be a URL that can be fetched by the browser.                     | 0.0.1 |


#### ScanResult

| Prop           | Type                   | Description                                                                   | Since |
| -------------- | ---------------------- | ----------------------------------------------------------------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The scanned barcodes. In single-shot mode, this contains exactly one element. | 0.0.1 |


#### ScanOptions

| Prop             | Type                                                    | Description                                                                                                                                                                                                                       | Default                      | Since |
| ---------------- | ------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------- | ----- |
| **`batch`**      | <code>boolean</code>                                    | Whether or not to scan multiple barcodes in one session. If `true`, the scanner collects the detected barcodes and resolves when the user taps the done button. If `false`, the scanner resolves with the first detected barcode. | <code>false</code>           | 0.0.1 |
| **`formats`**    | <code>BarcodeFormat[]</code>                            | The barcode formats to detect. If not set, all supported formats are detected. Improve the performance by only setting the formats you need.                                                                                      |                              | 0.0.1 |
| **`lensFacing`** | <code><a href="#lensfacing">LensFacing</a></code>       | The camera lens to use.                                                                                                                                                                                                           | <code>LensFacing.Back</code> | 0.0.1 |
| **`ui`**         | <code><a href="#scanuioptions">ScanUiOptions</a></code> | Options to customize the scanner user interface.                                                                                                                                                                                  |                              | 0.0.1 |


#### ScanUiOptions

| Prop                       | Type                 | Description                                                                                                                                                     | Default            | Since |
| -------------------------- | -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`accentColor`**          | <code>string</code>  | The accent color of the scanner user interface as a hex color code (e.g. `#59C7F9`). The accent color is applied to the viewfinder corners and the done button. |                    | 0.0.1 |
| **`beep`**                 | <code>boolean</code> | Whether or not to play a beep sound when a barcode is detected.                                                                                                 | <code>false</code> | 0.0.1 |
| **`hapticFeedback`**       | <code>boolean</code> | Whether or not to trigger a haptic feedback when a barcode is detected.                                                                                         | <code>true</code>  | 0.0.1 |
| **`instructions`**         | <code>string</code>  | The instructions text displayed below the title.                                                                                                                |                    | 0.0.1 |
| **`showFlipCameraButton`** | <code>boolean</code> | Whether or not to show the flip camera button.                                                                                                                  | <code>false</code> | 0.0.1 |
| **`showTorchButton`**      | <code>boolean</code> | Whether or not to show the torch button.                                                                                                                        | <code>true</code>  | 0.0.1 |
| **`title`**                | <code>string</code>  | The title displayed at the top of the scanner user interface.                                                                                                   |                    | 0.0.1 |


#### SetScanFrameOptions

| Prop        | Type                                            | Description                        | Since |
| ----------- | ----------------------------------------------- | ---------------------------------- | ----- |
| **`frame`** | <code><a href="#scanframe">ScanFrame</a></code> | The new frame of the scan session. | 0.0.1 |


#### ScanFrame

| Prop         | Type                | Description                                                                                  | Since |
| ------------ | ------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the frame in CSS pixels.                                                       | 0.0.1 |
| **`width`**  | <code>number</code> | The width of the frame in CSS pixels.                                                        | 0.0.1 |
| **`x`**      | <code>number</code> | The x coordinate of the frame in CSS pixels relative to the top-left corner of the viewport. | 0.0.1 |
| **`y`**      | <code>number</code> | The y coordinate of the frame in CSS pixels relative to the top-left corner of the viewport. | 0.0.1 |


#### SetTorchEnabledOptions

| Prop          | Type                 | Description                         | Since |
| ------------- | -------------------- | ----------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not to enable the torch. | 0.0.1 |


#### SetZoomRatioOptions

| Prop        | Type                | Description                                                                                | Since |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`ratio`** | <code>number</code> | The zoom ratio to set. Must be a value within the range returned by `getZoomRatioRange()`. | 0.0.1 |


#### StartScanOptions

| Prop                   | Type                                                          | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Default                             | Since |
| ---------------------- | ------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------- | ----- |
| **`detectionArea`**    | <code><a href="#detectionarea">DetectionArea</a></code>       | The area within the scan frame in which barcodes are detected. Barcodes that are detected outside of this area are ignored. If not set, barcodes are detected in the entire scan frame.                                                                                                                                                                                                                                                                                                                                                                           |                                     | 0.0.1 |
| **`duplicateTimeout`** | <code>number</code>                                           | The time in milliseconds after which the same barcode is emitted again.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | <code>1500</code>                   | 0.0.1 |
| **`formats`**          | <code>BarcodeFormat[]</code>                                  | The barcode formats to detect. If not set, all supported formats are detected. Improve the performance by only setting the formats you need.                                                                                                                                                                                                                                                                                                                                                                                                                      |                                     | 0.0.1 |
| **`frame`**            | <code><a href="#scanframe">ScanFrame</a></code>               | The frame in which the camera preview is rendered. The coordinates are in CSS pixels relative to the top-left corner of the viewport.                                                                                                                                                                                                                                                                                                                                                                                                                             |                                     | 0.0.1 |
| **`lensFacing`**       | <code><a href="#lensfacing">LensFacing</a></code>             | The camera lens to use.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | <code>LensFacing.Back</code>        | 0.0.1 |
| **`placement`**        | <code><a href="#previewplacement">PreviewPlacement</a></code> | Where the camera preview is rendered relative to the web view. With <a href="#previewplacement">`PreviewPlacement.Above`</a>, HTML elements cannot overlap the camera preview. With <a href="#previewplacement">`PreviewPlacement.Behind`</a>, HTML elements can overlap the camera preview, for example to draw a viewfinder or detection markers. The camera preview is only visible where your app is transparent. The placeholder element used to measure the frame, all its ancestors and the `body` must have a transparent background over the frame area. | <code>PreviewPlacement.Above</code> | 0.0.1 |


#### DetectionArea

| Prop         | Type                | Description                                                                                             | Since |
| ------------ | ------------------- | ------------------------------------------------------------------------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the detection area in CSS pixels.                                                         | 0.0.1 |
| **`width`**  | <code>number</code> | The width of the detection area in CSS pixels.                                                          | 0.0.1 |
| **`x`**      | <code>number</code> | The x coordinate of the detection area in CSS pixels relative to the top-left corner of the scan frame. | 0.0.1 |
| **`y`**      | <code>number</code> | The y coordinate of the detection area in CSS pixels relative to the top-left corner of the scan frame. | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BarcodesScannedEvent

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### ScanErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### LensFacing

| Members     | Value                | Description       | Since |
| ----------- | -------------------- | ----------------- | ----- |
| **`Back`**  | <code>'BACK'</code>  | The back camera.  | 0.0.1 |
| **`Front`** | <code>'FRONT'</code> | The front camera. | 0.0.1 |


#### BarcodeFormat

| Members          | Value                      | Description                                                                           | Since |
| ---------------- | -------------------------- | ------------------------------------------------------------------------------------- | ----- |
| **`Aztec`**      | <code>'AZTEC'</code>       | Aztec barcode.                                                                        | 0.0.1 |
| **`Codabar`**    | <code>'CODABAR'</code>     | Codabar barcode. On **iOS**, this format is only detected by the camera on iOS 15.4+. | 0.0.1 |
| **`Code128`**    | <code>'CODE_128'</code>    | Code 128 barcode.                                                                     | 0.0.1 |
| **`Code39`**     | <code>'CODE_39'</code>     | Code 39 barcode.                                                                      | 0.0.1 |
| **`Code93`**     | <code>'CODE_93'</code>     | Code 93 barcode.                                                                      | 0.0.1 |
| **`DataMatrix`** | <code>'DATA_MATRIX'</code> | Data Matrix barcode.                                                                  | 0.0.1 |
| **`Ean13`**      | <code>'EAN_13'</code>      | EAN-13 barcode.                                                                       | 0.0.1 |
| **`Ean8`**       | <code>'EAN_8'</code>       | EAN-8 barcode.                                                                        | 0.0.1 |
| **`Itf`**        | <code>'ITF'</code>         | ITF (Interleaved 2 of 5) barcode.                                                     | 0.0.1 |
| **`Pdf417`**     | <code>'PDF_417'</code>     | PDF417 barcode.                                                                       | 0.0.1 |
| **`QrCode`**     | <code>'QR_CODE'</code>     | QR code.                                                                              | 0.0.1 |
| **`UpcA`**       | <code>'UPC_A'</code>       | UPC-A barcode.                                                                        | 0.0.1 |
| **`UpcE`**       | <code>'UPC_E'</code>       | UPC-E barcode.                                                                        | 0.0.1 |


#### PreviewPlacement

| Members      | Value                 | Description                                         | Since |
| ------------ | --------------------- | --------------------------------------------------- | ----- |
| **`Above`**  | <code>'ABOVE'</code>  | The camera preview is rendered above the web view.  | 0.0.1 |
| **`Behind`** | <code>'BEHIND'</code> | The camera preview is rendered behind the web view. | 0.0.1 |

</docgen-api>

## Supported Barcode Formats

| Format        | Android | iOS | Web ¹ |
| ------------- | ------- | --- | ----- |
| `AZTEC`       | ✅       | ✅   | ✅     |
| `CODABAR`     | ✅       | ✅ ² | ✅     |
| `CODE_39`     | ✅       | ✅   | ✅     |
| `CODE_93`     | ✅       | ✅   | ✅     |
| `CODE_128`    | ✅       | ✅   | ✅     |
| `DATA_MATRIX` | ✅       | ✅   | ✅     |
| `EAN_8`       | ✅       | ✅   | ✅     |
| `EAN_13`      | ✅       | ✅   | ✅     |
| `ITF`         | ✅       | ✅   | ✅     |
| `PDF_417`     | ✅       | ✅   | ✅     |
| `QR_CODE`     | ✅       | ✅   | ✅     |
| `UPC_A`       | ✅       | ✅ ³ | ✅     |
| `UPC_E`       | ✅       | ✅   | ✅     |

¹ On the web, the supported formats depend on the browser (or the polyfill).

² Camera scanning of Codabar barcodes requires iOS 15.4+. Reading Codabar barcodes from images works on all supported iOS versions.

³ Apple's frameworks report UPC-A barcodes as EAN-13 barcodes with a leading `0`. The plugin normalizes them to `UPC_A` (unless you explicitly request only `EAN_13`), so the behavior is consistent across platforms.

## Migration from ML Kit Barcode Scanning

If you are migrating from the [ML Kit Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/) plugin, the following table maps the most important APIs:

| `@capacitor-mlkit/barcode-scanning`       | `@capawesome-team/capacitor-barcode-scanner`       |
| ----------------------------------------- | -------------------------------------------------- |
| `scan()`                                  | `scan()` (fullscreen UI, now also with batch mode) |
| `startScan()` (transparent web view)      | `startScan()` (embedded native view with `frame`)  |
| `stopScan()`                              | `stopScan()`                                       |
| —                                         | `pauseScan()` / `resumeScan()`                     |
| `readBarcodesFromImage()`                 | `readBarcodesFromImage()`                          |
| `isSupported()`                           | `isAvailable()`                                    |
| `enableTorch()` / `disableTorch()`        | `setTorchEnabled({ enabled })`                     |
| `setZoomRatio()` / `getZoomRatio()`       | `setZoomRatio()` / `getZoomRatioRange()`           |
| `getMinZoomRatio()` / `getMaxZoomRatio()` | `getZoomRatioRange()`                              |
| `checkPermissions()`                      | `checkPermissions()`                               |
| `requestPermissions()`                    | `requestPermissions()`                             |
| `openSettings()`                          | `openSettings()`                                   |
| `isGoogleBarcodeScannerModuleAvailable()` | Not needed (the scanner UI is part of the plugin)  |
| `addListener('barcodesScanned')`          | `addListener('barcodesScanned')`                   |
| `addListener('scanError')`                | `addListener('scanError')`                         |

**Key differences**:

- The camera preview is rendered inside a frame you provide, by default **above** the web view. You no longer need to make your app transparent (`hideBackground()`) during a scan, and HTML can no longer accidentally cover the camera. If you want to overlay HTML elements, opt in to rendering the camera preview behind the web view with the `placement` option.
- The `barcode.cornerPoints` are relative to the scan frame (embedded mode) or the image (`readBarcodesFromImage(...)`), not to the screen.
- Structured payload parsing (e.g. WiFi credentials, vCard contacts, driver licenses) is not yet supported. The `rawValue` is always returned, so you can parse the payload yourself. This feature is planned as a fast-follow.

## FAQ

### How is this plugin different from other similar plugins?

It offers two presentation modes — a themeable, ready-made fullscreen scanner and a true embedded native camera view — plus batch scanning, pause/resume, torch and zoom control, a configurable detection area and reading barcodes from image files across 13 formats. On iOS it is built purely on AVFoundation and Vision, so there are no third-party pods, no Swift Package Manager limitations and no arm64 simulator issues. On Android it uses the bundled ML Kit model so scanning works offline, and on the web it uses the `BarcodeDetector` API — all through one fully typed, actively maintained API with dedicated support. If you only need a quick one-off scan, a minimal setup can be enough; if you want a polished UI, an embedded view and consistent behavior on Android, iOS and the Web, this plugin is designed for that.

### Why does the embedded camera view cover my HTML elements?

By default, the embedded camera preview is a native view that is rendered above the web view. This is a deliberate design decision: it avoids the "camera not showing" issue class of transparent web view approaches. If you need buttons or overlays, place them outside the scan frame, use the fullscreen `scan(...)` mode which provides a themeable native UI, or set the `placement` option to `PreviewPlacement.Behind` to render the camera preview behind the web view.

### Is the fullscreen scanner available on the web?

No. The `scan(...)` method as well as torch and zoom control are not available on the web. However, you can build your own web scanner UI on top of the embedded mode (`startScan(...)`), which is fully supported on the web via the `BarcodeDetector` API.

### Why is the `bytes` property always `null` on iOS and Web?

Apple's AVFoundation and the web `BarcodeDetector` API only expose the string value of a barcode. The raw bytes are only available on Android.

### Does scanning work offline?

Yes. On Android, the plugin uses the bundled ML Kit model. On iOS, it uses the built-in AVFoundation framework. No network connection or Google Play services module download is required.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Document Scanner](https://capawesome.io/docs/sdks/capacitor/document-scanner/): Scan documents with the native fullscreen scanner.
- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Pick images to read barcodes from.
- [ML Kit Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/): Scan barcodes and QR codes with ML Kit Barcode Scanning.
- [Torch](https://capawesome.io/docs/sdks/capacitor/torch/): Control the flashlight outside of a scan session.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barcode-scanner/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barcode-scanner/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barcode-scanner/LICENSE).
