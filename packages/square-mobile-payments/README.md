# @capawesome/capacitor-square-mobile-payments

Unofficial Capacitor plugin for [Square Mobile Payments SDK](https://developer.squareup.com/docs/mobile-payments-sdk).[^1]

## Features

This plugin provides a comprehensive integration with Square's Mobile Payments SDK for in-person payment processing. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 💳 **Payment Processing**: Accept in-person payments with Square card readers.
- 📱 **Reader Management**: Pair, manage, and monitor Square card readers.
- 🔐 **Secure Authorization**: OAuth-based authorization with Square access tokens.
- 🎨 **Flexible UI**: Choose between Square's default payment UI or build your own custom interface.
- 📊 **Real-time Updates**: Listen to reader status changes, pairing events, and payment completion.
- 💰 **Multiple Payment Methods**: Support for contactless (tap), chip (dip), swipe, and manual entry.
- 🌐 **Online & Offline**: Process payments online or offline with automatic sync.
- 🧾 **Receipt Data**: Access card details, authorization codes, and EMV data for compliant receipts.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Installation

```bash
npm install @capawesome/capacitor-square-mobile-payments
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$squareMobilePaymentsSdkVersion` version of `com.squareup.sdk:mobile-payments-sdk` (default: `2.3.4`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### SDK Initialization

The Square Mobile Payments SDK must be initialized in your `AppDelegate.swift` file before using the plugin. Add the following code to your `AppDelegate.swift`:

1. Import the `SquareMobilePaymentsSDK` at the top of the file:

```swift
import SquareMobilePaymentsSDK
```

2. Add the Square Application ID to your `Info.plist` file:

```xml
<key>SquareApplicationID</key>
<string>YOUR_SQUARE_APPLICATION_ID_HERE</string>
```

**Note**: Replace `YOUR_SQUARE_APPLICATION_ID_HERE` with your actual Square Application ID. Do not use the placeholder value in production.

3. Initialize the SDK in the `application(_:didFinishLaunchingWithOptions:)` method:

```swift
func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
    // Initialize Square Mobile Payments SDK
    // Get the Square Application ID from info.plist
    if let squareAppID = Bundle.main.object(forInfoDictionaryKey: "SquareApplicationID") as? String,
       !squareAppID.isEmpty && squareAppID != "YOUR_SQUARE_APPLICATION_ID_HERE" {
        MobilePaymentsSDK.initialize(
            applicationLaunchOptions: launchOptions,
            squareApplicationID: squareAppID
        )
    }

    // Override point for customization after application launch.
    return true
}
```

#### Build Phases

Add a new "Run Script Phase" in Xcode to run the Square SDK setup script by following these steps:

1. On the **Build Phases** tab for your application target in Xcode, choose the + button (at the top left of the pane).
2. Choose **New Run Script Phase**. The new run script phase should be the last build phase.
3. Expand the new Run Script phase and enter the following script:

```
FRAMEWORKS="${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}"
"${FRAMEWORKS}/SquareMobilePaymentsSDK.framework/setup"
```

More details can be found in the [Square Mobile Payments SDK iOS setup guide](https://developer.squareup.com/docs/mobile-payments-sdk/ios).

#### Privacy Descriptions

Add the `NSLocationWhenInUseUsageDescription`, `NSBluetoothAlwaysUsageDescription`, and `NSMicrophoneUsageDescription` keys to the `ios/App/App/Info.plist` file, which tells the user why your app is requesting location, Bluetooth, and microphone permissions:

```xml
<key>NSBluetoothAlwaysUsageDescription</key>
<string>We need Bluetooth access to connect to Square card readers.</string>
<key>NSLocationWhenInUseUsageDescription</key>
<string>We need your location to confirm payments are occurring in a supported Square location.</string>
<key>NSMicrophoneUsageDescription</key>
<string>We need microphone access to receive data from magstripe card readers.</string>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { SquareMobilePayments, CardInputMethod } from '@capawesome/capacitor-square-mobile-payments';

const initializeSDK = async () => {
  await SquareMobilePayments.initialize({
    locationId: 'YOUR_LOCATION_ID',
  });

  await SquareMobilePayments.authorize({
    accessToken: 'YOUR_ACCESS_TOKEN',
  });
};

const checkAuthorization = async () => {
  const { authorized } = await SquareMobilePayments.isAuthorized();
  console.log('Authorized:', authorized);
};

const pairReader = async () => {
  await SquareMobilePayments.startPairing();
};

const getReaders = async () => {
  const { readers } = await SquareMobilePayments.getReaders();
  for (const reader of readers) {
    console.log('Reader:', reader.serialNumber, reader.model, reader.status);
  }
};

const processPayment = async () => {
  await SquareMobilePayments.startPayment({
    paymentParameters: {
      amountMoney: {
        amount: 100,
        currency: 'USD',
      },
      paymentAttemptId: crypto.randomUUID(),
    },
    promptParameters: {
      mode: 'DEFAULT',
      additionalMethods: ['KEYED'],
    },
  });
};

const listenToPaymentEvents = () => {
  SquareMobilePayments.addListener('paymentDidFinish', (event) => {
    console.log('Payment completed:', event.payment.id);
    console.log('Amount:', event.payment.amountMoney.amount);
    console.log('Card:', event.payment.cardDetails?.card.lastFourDigits);
  });

  SquareMobilePayments.addListener('paymentDidFail', (event) => {
    console.error('Payment failed:', event.message);
  });

  SquareMobilePayments.addListener('paymentDidCancel', (event) => {
    console.log('Payment cancelled');
  });
};

const listenToReaderEvents = () => {
  SquareMobilePayments.addListener('readerWasAdded', (event) => {
    console.log('Reader added:', event.reader.serialNumber);
  });

  SquareMobilePayments.addListener('readerDidChange', (event) => {
    console.log('Reader changed:', event.reader.serialNumber, event.change);
  });

  SquareMobilePayments.addListener('availableCardInputMethodsDidChange', (event) => {
    console.log('Available methods:', event.cardInputMethods);
  });
};

const getAvailableMethods = async () => {
  const { cardInputMethods } = await SquareMobilePayments.getAvailableCardInputMethods();
  console.log('Available card input methods:', cardInputMethods);
};
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`authorize(...)`](#authorize)
* [`isAuthorized()`](#isauthorized)
* [`deauthorize()`](#deauthorize)
* [`showSettings()`](#showsettings)
* [`showMockReader()`](#showmockreader)
* [`hideMockReader()`](#hidemockreader)
* [`getSettings()`](#getsettings)
* [`startPairing()`](#startpairing)
* [`stopPairing()`](#stoppairing)
* [`isPairingInProgress()`](#ispairinginprogress)
* [`getReaders()`](#getreaders)
* [`forgetReader(...)`](#forgetreader)
* [`retryConnection(...)`](#retryconnection)
* [`startPayment(...)`](#startpayment)
* [`cancelPayment()`](#cancelpayment)
* [`getAvailableCardInputMethods()`](#getavailablecardinputmethods)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('readerPairingDidBegin', ...)`](#addlistenerreaderpairingdidbegin-)
* [`addListener('readerPairingDidSucceed', ...)`](#addlistenerreaderpairingdidsucceed-)
* [`addListener('readerPairingDidFail', ...)`](#addlistenerreaderpairingdidfail-)
* [`addListener('readerWasAdded', ...)`](#addlistenerreaderwasadded-)
* [`addListener('readerWasRemoved', ...)`](#addlistenerreaderwasremoved-)
* [`addListener('readerDidChange', ...)`](#addlistenerreaderdidchange-)
* [`addListener('availableCardInputMethodsDidChange', ...)`](#addlisteneravailablecardinputmethodsdidchange-)
* [`addListener('paymentDidFinish', ...)`](#addlistenerpaymentdidfinish-)
* [`addListener('paymentDidFail', ...)`](#addlistenerpaymentdidfail-)
* [`addListener('paymentDidCancel', ...)`](#addlistenerpaymentdidcancel-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the Square Mobile Payments SDK.

This method must be called before any other method.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.0.1

--------------------


### authorize(...)

```typescript
authorize(options: AuthorizeOptions) => Promise<void>
```

Authorize the SDK with a Square access token.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#authorizeoptions">AuthorizeOptions</a></code> |

**Since:** 0.0.1

--------------------


### isAuthorized()

```typescript
isAuthorized() => Promise<IsAuthorizedResult>
```

Check if the SDK is currently authorized.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isauthorizedresult">IsAuthorizedResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### deauthorize()

```typescript
deauthorize() => Promise<void>
```

Deauthorize the SDK and clear the current authorization.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### showSettings()

```typescript
showSettings() => Promise<void>
```

Show the Square settings screen.

This displays a pre-built settings UI where merchants can view and manage
their paired readers.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### showMockReader()

```typescript
showMockReader() => Promise<void>
```

Show the Mock Reader UI for testing.

This displays a mock reader interface for testing payment flows without
physical hardware. This is only for development and testing purposes and
is therefore only available in debug builds.

Only available on Android and iOS.

**Since:** 0.1.1

--------------------


### hideMockReader()

```typescript
hideMockReader() => Promise<void>
```

Hide the Mock Reader UI.

Only available on Android and iOS.

**Since:** 0.1.1

--------------------


### getSettings()

```typescript
getSettings() => Promise<GetSettingsResult>
```

Get the current SDK settings.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getsettingsresult">GetSettingsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### startPairing()

```typescript
startPairing() => Promise<void>
```

Start pairing with a Square reader.

This initiates the reader pairing process. The SDK will search for nearby
readers and pair with the first one found.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### stopPairing()

```typescript
stopPairing() => Promise<void>
```

Stop the reader pairing process.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### isPairingInProgress()

```typescript
isPairingInProgress() => Promise<IsPairingInProgressResult>
```

Check if a pairing process is currently in progress.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#ispairinginprogressresult">IsPairingInProgressResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getReaders()

```typescript
getReaders() => Promise<GetReadersResult>
```

Get a list of all paired readers.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getreadersresult">GetReadersResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### forgetReader(...)

```typescript
forgetReader(options: ForgetReaderOptions) => Promise<void>
```

Forget a paired reader.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#forgetreaderoptions">ForgetReaderOptions</a></code> |

**Since:** 0.0.1

--------------------


### retryConnection(...)

```typescript
retryConnection(options: RetryConnectionOptions) => Promise<void>
```

Retry connecting to a reader.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#retryconnectionoptions">RetryConnectionOptions</a></code> |

**Since:** 0.0.1

--------------------


### startPayment(...)

```typescript
startPayment(options: StartPaymentOptions) => Promise<void>
```

Start a payment flow.

This presents the payment UI and processes the payment using the specified
parameters. Only one payment can be active at a time.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#startpaymentoptions">StartPaymentOptions</a></code> |

**Since:** 0.0.1

--------------------


### cancelPayment()

```typescript
cancelPayment() => Promise<void>
```

Cancel an ongoing payment.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### getAvailableCardInputMethods()

```typescript
getAvailableCardInputMethods() => Promise<GetAvailableCardInputMethodsResult>
```

Get the currently available card input methods.

This returns the card entry methods that are available based on the
connected readers (e.g., TAP, DIP, SWIPE, KEYED).

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getavailablecardinputmethodsresult">GetAvailableCardInputMethodsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the current permission status.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the required permissions.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('readerPairingDidBegin', ...)

```typescript
addListener(eventName: 'readerPairingDidBegin', listenerFunc: ReaderPairingDidBeginListener) => Promise<PluginListenerHandle>
```

Listen for reader pairing events.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'readerPairingDidBegin'</code>                                                    |
| **`listenerFunc`** | <code><a href="#readerpairingdidbeginlistener">ReaderPairingDidBeginListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('readerPairingDidSucceed', ...)

```typescript
addListener(eventName: 'readerPairingDidSucceed', listenerFunc: ReaderPairingDidSucceedListener) => Promise<PluginListenerHandle>
```

Listen for successful reader pairing events.

Only available on Android and iOS.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'readerPairingDidSucceed'</code>                                                      |
| **`listenerFunc`** | <code><a href="#readerpairingdidsucceedlistener">ReaderPairingDidSucceedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('readerPairingDidFail', ...)

```typescript
addListener(eventName: 'readerPairingDidFail', listenerFunc: ReaderPairingDidFailListener) => Promise<PluginListenerHandle>
```

Listen for failed reader pairing events.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'readerPairingDidFail'</code>                                                   |
| **`listenerFunc`** | <code><a href="#readerpairingdidfaillistener">ReaderPairingDidFailListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('readerWasAdded', ...)

```typescript
addListener(eventName: 'readerWasAdded', listenerFunc: ReaderWasAddedListener) => Promise<PluginListenerHandle>
```

Listen for reader added events.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'readerWasAdded'</code>                                             |
| **`listenerFunc`** | <code><a href="#readerwasaddedlistener">ReaderWasAddedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('readerWasRemoved', ...)

```typescript
addListener(eventName: 'readerWasRemoved', listenerFunc: ReaderWasRemovedListener) => Promise<PluginListenerHandle>
```

Listen for reader removed events.

Only available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'readerWasRemoved'</code>                                               |
| **`listenerFunc`** | <code><a href="#readerwasremovedlistener">ReaderWasRemovedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('readerDidChange', ...)

```typescript
addListener(eventName: 'readerDidChange', listenerFunc: ReaderDidChangeListener) => Promise<PluginListenerHandle>
```

Listen for reader status and property changes.

Only available on Android and iOS.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'readerDidChange'</code>                                              |
| **`listenerFunc`** | <code><a href="#readerdidchangelistener">ReaderDidChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('availableCardInputMethodsDidChange', ...)

```typescript
addListener(eventName: 'availableCardInputMethodsDidChange', listenerFunc: AvailableCardInputMethodsDidChangeListener) => Promise<PluginListenerHandle>
```

Listen for available card input method changes.

Only available on Android and iOS.

| Param              | Type                                                                                                              |
| ------------------ | ----------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'availableCardInputMethodsDidChange'</code>                                                                 |
| **`listenerFunc`** | <code><a href="#availablecardinputmethodsdidchangelistener">AvailableCardInputMethodsDidChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('paymentDidFinish', ...)

```typescript
addListener(eventName: 'paymentDidFinish', listenerFunc: PaymentDidFinishListener) => Promise<PluginListenerHandle>
```

Listen for successful payment completion events.

Only available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'paymentDidFinish'</code>                                               |
| **`listenerFunc`** | <code><a href="#paymentdidfinishlistener">PaymentDidFinishListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('paymentDidFail', ...)

```typescript
addListener(eventName: 'paymentDidFail', listenerFunc: PaymentDidFailListener) => Promise<PluginListenerHandle>
```

Listen for failed payment events.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'paymentDidFail'</code>                                             |
| **`listenerFunc`** | <code><a href="#paymentdidfaillistener">PaymentDidFailListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('paymentDidCancel', ...)

```typescript
addListener(eventName: 'paymentDidCancel', listenerFunc: PaymentDidCancelListener) => Promise<PluginListenerHandle>
```

Listen for cancelled payment events.

Only available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'paymentDidCancel'</code>                                               |
| **`listenerFunc`** | <code><a href="#paymentdidcancellistener">PaymentDidCancelListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### Interfaces


#### InitializeOptions

| Prop             | Type                | Description             | Since |
| ---------------- | ------------------- | ----------------------- | ----- |
| **`locationId`** | <code>string</code> | The Square location ID. | 0.0.1 |


#### AuthorizeOptions

| Prop              | Type                | Description              | Since |
| ----------------- | ------------------- | ------------------------ | ----- |
| **`accessToken`** | <code>string</code> | The Square access token. | 0.0.1 |


#### IsAuthorizedResult

| Prop             | Type                 | Description                              | Since |
| ---------------- | -------------------- | ---------------------------------------- | ----- |
| **`authorized`** | <code>boolean</code> | Whether the SDK is currently authorized. | 0.0.1 |


#### GetSettingsResult

| Prop              | Type                                                | Description              | Since |
| ----------------- | --------------------------------------------------- | ------------------------ | ----- |
| **`version`**     | <code>string</code>                                 | The SDK version.         | 0.0.1 |
| **`environment`** | <code><a href="#environment">Environment</a></code> | The current environment. | 0.0.1 |


#### IsPairingInProgressResult

| Prop             | Type                 | Description                                         | Since |
| ---------------- | -------------------- | --------------------------------------------------- | ----- |
| **`inProgress`** | <code>boolean</code> | Whether a pairing process is currently in progress. | 0.0.1 |


#### GetReadersResult

| Prop          | Type                      | Description                 | Since |
| ------------- | ------------------------- | --------------------------- | ----- |
| **`readers`** | <code>ReaderInfo[]</code> | The list of paired readers. | 0.0.1 |


#### ReaderInfo

| Prop                            | Type                                                                    | Description                                                                                                | Since |
| ------------------------------- | ----------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- | ----- |
| **`serialNumber`**              | <code>string</code>                                                     | The reader's serial number.                                                                                | 0.0.1 |
| **`model`**                     | <code><a href="#readermodel">ReaderModel</a></code>                     | The reader's model.                                                                                        | 0.0.1 |
| **`status`**                    | <code><a href="#readerstatus">ReaderStatus</a></code>                   | The reader's current status.                                                                               | 0.0.1 |
| **`firmwareVersion`**           | <code>string</code>                                                     | The reader's firmware version.                                                                             | 0.0.1 |
| **`batteryLevel`**              | <code>number</code>                                                     | The reader's battery level (0-100).                                                                        | 0.0.1 |
| **`isCharging`**                | <code>boolean</code>                                                    | Whether the reader is currently charging.                                                                  | 0.0.1 |
| **`supportedCardInputMethods`** | <code>CardInputMethod[]</code>                                          | The card input methods supported by this reader.                                                           | 0.0.1 |
| **`unavailableReasonInfo`**     | <code><a href="#unavailablereasoninfo">UnavailableReasonInfo</a></code> | Additional information about why the reader is unavailable. Only present when status is ReaderUnavailable. | 0.0.1 |


#### UnavailableReasonInfo

| Prop          | Type                                                            | Description                                                        | Since |
| ------------- | --------------------------------------------------------------- | ------------------------------------------------------------------ | ----- |
| **`reason`**  | <code><a href="#unavailablereason">UnavailableReason</a></code> | The reason code why the reader is unavailable.                     | 0.0.1 |
| **`message`** | <code>string</code>                                             | A human-readable message describing why the reader is unavailable. | 0.0.1 |


#### ForgetReaderOptions

| Prop               | Type                | Description                                | Since |
| ------------------ | ------------------- | ------------------------------------------ | ----- |
| **`serialNumber`** | <code>string</code> | The serial number of the reader to forget. | 0.0.1 |


#### RetryConnectionOptions

| Prop               | Type                | Description                                          | Since |
| ------------------ | ------------------- | ---------------------------------------------------- | ----- |
| **`serialNumber`** | <code>string</code> | The serial number of the reader to retry connection. | 0.0.1 |


#### StartPaymentOptions

| Prop                    | Type                                                            | Description             | Since |
| ----------------------- | --------------------------------------------------------------- | ----------------------- | ----- |
| **`paymentParameters`** | <code><a href="#paymentparameters">PaymentParameters</a></code> | The payment parameters. | 0.0.1 |
| **`promptParameters`**  | <code><a href="#promptparameters">PromptParameters</a></code>   | The prompt parameters.  | 0.0.1 |


#### PaymentParameters

| Prop                   | Type                                                      | Description                                                                                                                                                 | Default                                | Since |
| ---------------------- | --------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------- | ----- |
| **`amountMoney`**      | <code><a href="#money">Money</a></code>                   | The amount of money to charge.                                                                                                                              |                                        | 0.0.1 |
| **`paymentAttemptId`** | <code>string</code>                                       | A unique identifier for this payment attempt. This is used for idempotent payment requests.                                                                 |                                        | 0.0.1 |
| **`processingMode`**   | <code><a href="#processingmode">ProcessingMode</a></code> | The processing mode for this payment.                                                                                                                       | <code>ProcessingMode.AutoDetect</code> | 0.0.1 |
| **`referenceId`**      | <code>string</code>                                       | A user-defined reference ID to associate with the payment.                                                                                                  |                                        | 0.0.1 |
| **`note`**             | <code>string</code>                                       | An optional note to add to the payment.                                                                                                                     |                                        | 0.0.1 |
| **`orderId`**          | <code>string</code>                                       | The Square order ID to associate with this payment.                                                                                                         |                                        | 0.0.1 |
| **`tipMoney`**         | <code><a href="#money">Money</a></code>                   | The tip amount.                                                                                                                                             |                                        | 0.0.1 |
| **`applicationFee`**   | <code><a href="#money">Money</a></code>                   | The application fee.                                                                                                                                        |                                        | 0.0.1 |
| **`autocomplete`**     | <code>boolean</code>                                      | Whether to automatically complete the payment. If false, the payment will be authorized but not captured, requiring manual completion via the Payments API. | <code>true</code>                      | 0.0.1 |
| **`delayDuration`**    | <code>string</code>                                       | The duration to delay before automatically completing or canceling the payment. Only applicable when autocomplete is false.                                 |                                        | 0.0.1 |
| **`delayAction`**      | <code><a href="#delayaction">DelayAction</a></code>       | The action to take when the delay duration expires. Only applicable when autocomplete is false.                                                             |                                        | 0.0.1 |


#### Money

| Prop           | Type                                                  | Description                                                     | Since |
| -------------- | ----------------------------------------------------- | --------------------------------------------------------------- | ----- |
| **`amount`**   | <code>number</code>                                   | The amount in the smallest currency unit (e.g., cents for USD). | 0.0.1 |
| **`currency`** | <code><a href="#currencycode">CurrencyCode</a></code> | The ISO 4217 currency code.                                     | 0.0.1 |


#### PromptParameters

| Prop                    | Type                                              | Description                          | Default                         | Since |
| ----------------------- | ------------------------------------------------- | ------------------------------------ | ------------------------------- | ----- |
| **`mode`**              | <code><a href="#promptmode">PromptMode</a></code> | The prompt mode.                     | <code>PromptMode.Default</code> | 0.0.1 |
| **`additionalMethods`** | <code>AdditionalPaymentMethod[]</code>            | Additional payment methods to allow. | <code>[]</code>                 | 0.0.1 |


#### GetAvailableCardInputMethodsResult

| Prop                   | Type                           | Description                       | Since |
| ---------------------- | ------------------------------ | --------------------------------- | ----- |
| **`cardInputMethods`** | <code>CardInputMethod[]</code> | The available card input methods. | 0.0.1 |


#### PermissionStatus

| Prop                   | Type                                                        | Description                                                                                                                                 | Since |
| ---------------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`location`**         | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for accessing location. Required to confirm that payments are occurring in a supported Square location.                    | 0.0.1 |
| **`recordAudio`**      | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for recording audio. Required to receive data from magstripe readers.                                                      | 0.0.1 |
| **`bluetoothConnect`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for Bluetooth connect. Required to receive data from contactless and chip readers. Only available on Android.              | 0.0.1 |
| **`bluetoothScan`**    | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for Bluetooth scan. Required to store information during checkout. Only available on Android.                              | 0.0.1 |
| **`readPhoneState`**   | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for reading phone state. Required to identify the device sending information to Square servers. Only available on Android. | 0.0.1 |
| **`bluetooth`**        | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for using Bluetooth. Only available on iOS.                                                                                | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ReaderPairingDidFailEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`code`**    | <code>string</code> | The error code.    | 0.0.1 |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


#### ReaderWasAddedEvent

| Prop         | Type                                              | Description                | Since |
| ------------ | ------------------------------------------------- | -------------------------- | ----- |
| **`reader`** | <code><a href="#readerinfo">ReaderInfo</a></code> | The reader that was added. | 0.0.1 |


#### ReaderWasRemovedEvent

| Prop         | Type                                              | Description                  | Since |
| ------------ | ------------------------------------------------- | ---------------------------- | ----- |
| **`reader`** | <code><a href="#readerinfo">ReaderInfo</a></code> | The reader that was removed. | 0.0.1 |


#### ReaderDidChangeEvent

| Prop         | Type                                                  | Description                       | Since |
| ------------ | ----------------------------------------------------- | --------------------------------- | ----- |
| **`reader`** | <code><a href="#readerinfo">ReaderInfo</a></code>     | The reader that changed.          | 0.0.1 |
| **`change`** | <code><a href="#readerchange">ReaderChange</a></code> | The type of change that occurred. | 0.0.1 |


#### AvailableCardInputMethodsDidChangeEvent

| Prop                   | Type                           | Description                       | Since |
| ---------------------- | ------------------------------ | --------------------------------- | ----- |
| **`cardInputMethods`** | <code>CardInputMethod[]</code> | The available card input methods. | 0.0.1 |


#### PaymentDidFinishEvent

| Prop          | Type                                        | Description            | Since |
| ------------- | ------------------------------------------- | ---------------------- | ----- |
| **`payment`** | <code><a href="#payment">Payment</a></code> | The completed payment. | 0.0.1 |


#### Payment

| Prop                 | Type                                                              | Description                                                                                  | Since |
| -------------------- | ----------------------------------------------------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`id`**             | <code>string \| null</code>                                       | The unique identifier for this payment. For offline payments, this may be null until synced. | 0.0.1 |
| **`type`**           | <code><a href="#paymenttype">PaymentType</a></code>               | The payment type.                                                                            | 0.0.1 |
| **`status`**         | <code><a href="#paymentstatus">PaymentStatus</a></code>           | The payment status.                                                                          | 0.0.1 |
| **`amountMoney`**    | <code><a href="#money">Money</a></code>                           | The amount paid.                                                                             | 0.0.1 |
| **`tipMoney`**       | <code><a href="#money">Money</a></code>                           | The tip amount.                                                                              | 0.0.1 |
| **`applicationFee`** | <code><a href="#money">Money</a></code>                           | The application fee.                                                                         | 0.0.1 |
| **`referenceId`**    | <code>string</code>                                               | The reference ID provided in the payment parameters.                                         | 0.0.1 |
| **`orderId`**        | <code>string</code>                                               | The order ID associated with this payment.                                                   | 0.0.1 |
| **`cardDetails`**    | <code><a href="#cardpaymentdetails">CardPaymentDetails</a></code> | <a href="#card">Card</a> payment details. Only present for card payments.                    | 0.0.1 |
| **`createdAt`**      | <code>string</code>                                               | The time the payment was created (ISO 8601 format).                                          | 0.0.1 |
| **`updatedAt`**      | <code>string</code>                                               | The time the payment was updated (ISO 8601 format).                                          | 0.0.1 |


#### CardPaymentDetails

| Prop                    | Type                                                        | Description                     | Since |
| ----------------------- | ----------------------------------------------------------- | ------------------------------- | ----- |
| **`card`**              | <code><a href="#card">Card</a></code>                       | The card information.           | 0.0.1 |
| **`entryMethod`**       | <code><a href="#cardinputmethod">CardInputMethod</a></code> | The card entry method used.     | 0.0.1 |
| **`authorizationCode`** | <code>string</code>                                         | The authorization code.         | 0.0.1 |
| **`applicationName`**   | <code>string</code>                                         | The EMV application name.       | 0.0.1 |
| **`applicationId`**     | <code>string</code>                                         | The EMV application identifier. | 0.0.1 |


#### Card

| Prop                  | Type                                            | Description                              | Since |
| --------------------- | ----------------------------------------------- | ---------------------------------------- | ----- |
| **`brand`**           | <code><a href="#cardbrand">CardBrand</a></code> | The card brand.                          | 0.0.1 |
| **`lastFourDigits`**  | <code>string</code>                             | The last four digits of the card number. | 0.0.1 |
| **`cardholderName`**  | <code>string</code>                             | The cardholder name.                     | 0.0.1 |
| **`expirationMonth`** | <code>number</code>                             | The card expiration month (1-12).        | 0.0.1 |
| **`expirationYear`**  | <code>number</code>                             | The card expiration year.                | 0.0.1 |


#### PaymentDidFailEvent

| Prop          | Type                                        | Description         | Since |
| ------------- | ------------------------------------------- | ------------------- | ----- |
| **`payment`** | <code><a href="#payment">Payment</a></code> | The failed payment. | 0.0.1 |
| **`code`**    | <code>string</code>                         | The error code.     | 0.0.1 |
| **`message`** | <code>string</code>                         | The error message.  | 0.0.1 |


#### PaymentDidCancelEvent

| Prop          | Type                                        | Description            | Since |
| ------------- | ------------------------------------------- | ---------------------- | ----- |
| **`payment`** | <code><a href="#payment">Payment</a></code> | The cancelled payment. | 0.0.1 |


### Type Aliases


#### CurrencyCode

<code>string</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### ReaderPairingDidBeginListener

Callback to receive reader pairing start notifications.

<code>(): void</code>


#### ReaderPairingDidSucceedListener

Callback to receive reader pairing success notifications.

<code>(): void</code>


#### ReaderPairingDidFailListener

Callback to receive reader pairing failure notifications.

<code>(event: <a href="#readerpairingdidfailevent">ReaderPairingDidFailEvent</a>): void</code>


#### ReaderWasAddedListener

Callback to receive reader added notifications.

<code>(event: <a href="#readerwasaddedevent">ReaderWasAddedEvent</a>): void</code>


#### ReaderWasRemovedListener

Callback to receive reader removed notifications.

<code>(event: <a href="#readerwasremovedevent">ReaderWasRemovedEvent</a>): void</code>


#### ReaderDidChangeListener

Callback to receive reader change notifications.

<code>(event: <a href="#readerdidchangeevent">ReaderDidChangeEvent</a>): void</code>


#### AvailableCardInputMethodsDidChangeListener

Callback to receive available card input method change notifications.

<code>(event: <a href="#availablecardinputmethodsdidchangeevent">AvailableCardInputMethodsDidChangeEvent</a>): void</code>


#### PaymentDidFinishListener

Callback to receive payment completion notifications.

<code>(event: <a href="#paymentdidfinishevent">PaymentDidFinishEvent</a>): void</code>


#### PaymentDidFailListener

Callback to receive payment failure notifications.

<code>(event: <a href="#paymentdidfailevent">PaymentDidFailEvent</a>): void</code>


#### PaymentDidCancelListener

Callback to receive payment cancellation notifications.

<code>(event: <a href="#paymentdidcancelevent">PaymentDidCancelEvent</a>): void</code>


### Enums


#### Environment

| Members          | Value                     | Description                      | Since |
| ---------------- | ------------------------- | -------------------------------- | ----- |
| **`Production`** | <code>'production'</code> | Production environment.          | 0.0.1 |
| **`Sandbox`**    | <code>'sandbox'</code>    | Sandbox environment for testing. | 0.0.1 |


#### ReaderModel

| Members                  | Value                               | Description                             | Since |
| ------------------------ | ----------------------------------- | --------------------------------------- | ----- |
| **`ContactlessAndChip`** | <code>'CONTACTLESS_AND_CHIP'</code> | Square Reader for contactless and chip. | 0.0.1 |
| **`Magstripe`**          | <code>'MAGSTRIPE'</code>            | Square Reader for magstripe.            | 0.0.1 |
| **`Stand`**              | <code>'STAND'</code>                | Square Stand.                           | 0.0.1 |
| **`Unknown`**            | <code>'UNKNOWN'</code>              | Unknown reader model.                   | 0.0.1 |


#### ReaderStatus

| Members                  | Value                               | Description                                                          | Since |
| ------------------------ | ----------------------------------- | -------------------------------------------------------------------- | ----- |
| **`Ready`**              | <code>'READY'</code>                | Reader is paired, connected, and ready to accept payments.           | 0.0.1 |
| **`ConnectingToSquare`** | <code>'CONNECTING_TO_SQUARE'</code> | Reader is connecting to Square servers.                              | 0.0.1 |
| **`ConnectingToDevice`** | <code>'CONNECTING_TO_DEVICE'</code> | Reader is connecting to the mobile device.                           | 0.0.1 |
| **`Faulty`**             | <code>'FAULTY'</code>               | Reader has a hardware or connection error in an unrecoverable state. | 0.0.1 |
| **`ReaderUnavailable`**  | <code>'READER_UNAVAILABLE'</code>   | Reader is connected but unable to process payments.                  | 0.0.1 |


#### CardInputMethod

| Members     | Value                | Description                 | Since |
| ----------- | -------------------- | --------------------------- | ----- |
| **`Tap`**   | <code>'TAP'</code>   | Contactless card tap (NFC). | 0.0.1 |
| **`Dip`**   | <code>'DIP'</code>   | EMV chip card insertion.    | 0.0.1 |
| **`Swipe`** | <code>'SWIPE'</code> | Magnetic stripe swipe.      | 0.0.1 |
| **`Keyed`** | <code>'KEYED'</code> | Manually keyed card entry.  | 0.0.1 |


#### UnavailableReason

| Members                        | Value                                     | Description                                               | Since |
| ------------------------------ | ----------------------------------------- | --------------------------------------------------------- | ----- |
| **`BluetoothError`**           | <code>'BLUETOOTH_ERROR'</code>            | Bluetooth connection issue. Only available on iOS.        | 0.0.1 |
| **`BluetoothFailure`**         | <code>'BLUETOOTH_FAILURE'</code>          | Bluetooth failure. Only available on Android.             | 0.0.1 |
| **`BluetoothDisabled`**        | <code>'BLUETOOTH_DISABLED'</code>         | Bluetooth is disabled. Only available on Android.         | 0.0.1 |
| **`FirmwareUpdate`**           | <code>'FIRMWARE_UPDATE'</code>            | Reader firmware is updating. Only available on iOS.       | 0.0.1 |
| **`BlockingUpdate`**           | <code>'BLOCKING_UPDATE'</code>            | Blocking firmware update. Only available on Android.      | 0.0.1 |
| **`ReaderUnavailableOffline`** | <code>'READER_UNAVAILABLE_OFFLINE'</code> | Reader is unavailable offline. Only available on Android. | 0.0.1 |
| **`DeviceDeveloperMode`**      | <code>'DEVICE_DEVELOPER_MODE'</code>      | Device is in developer mode. Only available on Android.   | 0.0.1 |
| **`Unknown`**                  | <code>'UNKNOWN'</code>                    | Unknown reason.                                           | 0.0.1 |


#### ProcessingMode

| Members           | Value                       | Description                                                        | Since |
| ----------------- | --------------------------- | ------------------------------------------------------------------ | ----- |
| **`AutoDetect`**  | <code>'AUTO_DETECT'</code>  | Automatically detect the best processing mode (online or offline). | 0.0.1 |
| **`OnlineOnly`**  | <code>'ONLINE_ONLY'</code>  | Process the payment online only.                                   | 0.0.1 |
| **`OfflineOnly`** | <code>'OFFLINE_ONLY'</code> | Allow offline payment processing.                                  | 0.0.1 |


#### DelayAction

| Members        | Value                   | Description                                                | Since |
| -------------- | ----------------------- | ---------------------------------------------------------- | ----- |
| **`Complete`** | <code>'COMPLETE'</code> | Automatically complete the payment when the delay expires. | 0.0.1 |
| **`Cancel`**   | <code>'CANCEL'</code>   | Automatically cancel the payment when the delay expires.   | 0.0.1 |


#### PromptMode

| Members       | Value                  | Description                        | Since |
| ------------- | ---------------------- | ---------------------------------- | ----- |
| **`Default`** | <code>'DEFAULT'</code> | Use the default Square payment UI. | 0.0.1 |
| **`Custom`**  | <code>'CUSTOM'</code>  | Use a custom payment UI.           | 0.0.1 |


#### AdditionalPaymentMethod

| Members     | Value                | Description                      | Since |
| ----------- | -------------------- | -------------------------------- | ----- |
| **`Keyed`** | <code>'KEYED'</code> | Allow manually keyed card entry. | 0.0.1 |
| **`Cash`**  | <code>'CASH'</code>  | Allow cash payments.             | 0.0.1 |


#### ReaderChange

| Members                       | Value                                     | Description                                                        | Since |
| ----------------------------- | ----------------------------------------- | ------------------------------------------------------------------ | ----- |
| **`BatteryDidBeginCharging`** | <code>'BATTERY_DID_BEGIN_CHARGING'</code> | Reader battery started charging. Only available on iOS.            | 0.0.1 |
| **`BatteryDidEndCharging`**   | <code>'BATTERY_DID_END_CHARGING'</code>   | Reader battery stopped charging. Only available on iOS.            | 0.0.1 |
| **`BatteryLevelDidChange`**   | <code>'BATTERY_LEVEL_DID_CHANGE'</code>   | Reader battery level changed. Only available on iOS.               | 0.0.1 |
| **`BatteryCharging`**         | <code>'BATTERY_CHARGING'</code>           | Reader battery charging status changed. Only available on Android. | 0.0.1 |
| **`Added`**                   | <code>'ADDED'</code>                      | Reader was added. Only available on Android.                       | 0.0.1 |
| **`Removed`**                 | <code>'REMOVED'</code>                    | Reader was removed. Only available on Android.                     | 0.0.1 |
| **`StatusDidChange`**         | <code>'STATUS_DID_CHANGE'</code>          | Reader status changed.                                             | 0.0.1 |


#### PaymentType

| Members       | Value                  | Description                                       | Since |
| ------------- | ---------------------- | ------------------------------------------------- | ----- |
| **`Online`**  | <code>'ONLINE'</code>  | <a href="#payment">Payment</a> processed online.  | 0.0.1 |
| **`Offline`** | <code>'OFFLINE'</code> | <a href="#payment">Payment</a> processed offline. | 0.0.1 |


#### PaymentStatus

| Members         | Value                    | Description                                                        | Since |
| --------------- | ------------------------ | ------------------------------------------------------------------ | ----- |
| **`Completed`** | <code>'COMPLETED'</code> | <a href="#payment">Payment</a> was completed successfully.         | 0.0.1 |
| **`Approved`**  | <code>'APPROVED'</code>  | <a href="#payment">Payment</a> was approved but not yet completed. | 0.0.1 |
| **`Canceled`**  | <code>'CANCELED'</code>  | <a href="#payment">Payment</a> was canceled.                       | 0.0.1 |
| **`Failed`**    | <code>'FAILED'</code>    | <a href="#payment">Payment</a> failed.                             | 0.0.1 |
| **`Pending`**   | <code>'PENDING'</code>   | <a href="#payment">Payment</a> is pending.                         | 0.0.1 |


#### CardBrand

| Members               | Value                           | Description                  | Since |
| --------------------- | ------------------------------- | ---------------------------- | ----- |
| **`Visa`**            | <code>'VISA'</code>             | Visa card.                   | 0.0.1 |
| **`Mastercard`**      | <code>'MASTERCARD'</code>       | Mastercard.                  | 0.0.1 |
| **`AmericanExpress`** | <code>'AMERICAN_EXPRESS'</code> | American Express card.       | 0.0.1 |
| **`Discover`**        | <code>'DISCOVER'</code>         | Discover card.               | 0.0.1 |
| **`DiscoverDiners`**  | <code>'DISCOVER_DINERS'</code>  | Discover Diners card.        | 0.0.1 |
| **`Jcb`**             | <code>'JCB'</code>              | JCB card.                    | 0.0.1 |
| **`UnionPay`**        | <code>'UNION_PAY'</code>        | UnionPay card.               | 0.0.1 |
| **`Interac`**         | <code>'INTERAC'</code>          | Interac card.                | 0.0.1 |
| **`Eftpos`**          | <code>'EFTPOS'</code>           | Eftpos card.                 | 0.0.1 |
| **`Other`**           | <code>'OTHER'</code>            | Other or unknown card brand. | 0.0.1 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/square-mobile-payments/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/square-mobile-payments/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Square, Inc. or any of their affiliates or subsidiaries.