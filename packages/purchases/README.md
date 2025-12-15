# @capawesome-team/capacitor-purchases

Capacitor plugin to support in-app purchases.

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for in-app purchases. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🛍️ **Product Types**: Supports subscriptions, consumables, and non-consumable in-app products.
- 🔒 **Server Validation**: Provides verification tokens (iOS JWS, Android purchase tokens) for server-side validation.
- 📋 **Transaction Management**: Track current, unfinished, and historical transactions.
- 🔄 **Purchase Restoration**: Easily sync and restore purchases across devices.
- 🚀 **Modern APIs**: Uses StoreKit 2 and Google Play Billing Library 8.0.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll add it for you!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.3.x          | >=8.x.x           | Active support |

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
npm install @capawesome-team/capacitor-purchases
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$googlePlayBillingVersion` version of `com.android.billingclient:billing` (default: `8.2.0`)

### iOS

#### Capabilities

Ensure `In-App Purchase` capabilities have been enabled in your application in Xcode.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Purchases } from '@capawesome-team/capacitor-purchases';

const purchaseProduct = async (productId: string) => {
  const { transaction } = await Purchases.purchaseProduct({ productId });
  // Deliver the purchased content or enable the service here
  // ...
  // Finish the transaction
  await Purchases.finishTransaction({ transactionId: transaction.id });
};

const restorePurchases = async () => {
  await Purchases.syncTransactions();
  const { transactions } = await Purchases.getCurrentTransactions();
  for (const transaction of transactions) {
    // Deliver the purchased content or enable the service here
    // ...
  }
};
```

## API

<docgen-index>

* [`finishTransaction(...)`](#finishtransaction)
* [`getAllTransactions()`](#getalltransactions)
* [`getCurrentTransactions()`](#getcurrenttransactions)
* [`getUnfinishedTransactions()`](#getunfinishedtransactions)
* [`isAvailable()`](#isavailable)
* [`purchaseProduct(...)`](#purchaseproduct)
* [`syncTransactions()`](#synctransactions)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### finishTransaction(...)

```typescript
finishTransaction(options: FinishTransactionOptions) => Promise<void>
```

Finish a transaction.

Indicates to the App Store that the app delivered the purchased content
or enabled the service to finish the transaction.

Only available on Android and iOS (15.0+).

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#finishtransactionoptions">FinishTransactionOptions</a></code> |

**Since:** 0.1.0

--------------------


### getAllTransactions()

```typescript
getAllTransactions() => Promise<GetAllTransactionsResult>
```

Returns transaction details for all transactions made by the user
within your app.

Only available on iOS (15.0+).

**Returns:** <code>Promise&lt;<a href="#getalltransactionsresult">GetAllTransactionsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getCurrentTransactions()

```typescript
getCurrentTransactions() => Promise<GetCurrentTransactionsResult>
```

Returns transaction details for currently owned items bought within your app.

Only active subscriptions and non-consumed one-time purchases are returned.

Only available on Android and iOS (15.0+).

**Returns:** <code>Promise&lt;<a href="#getcurrenttransactionsresult">GetCurrentTransactionsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getUnfinishedTransactions()

```typescript
getUnfinishedTransactions() => Promise<GetUnfinishedTransactionsResult>
```

Returns transaction details for all transactions that are not yet finished.

Check for unfinished transactions at least once every app launch to ensure
that all transactions are processed correctly.

Only available on Android and iOS (15.0+).

**Returns:** <code>Promise&lt;<a href="#getunfinishedtransactionsresult">GetUnfinishedTransactionsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if in-app purchases are supported on the device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### purchaseProduct(...)

```typescript
purchaseProduct(options: PurchaseProductOptions) => Promise<PurchaseProductResult>
```

Purchase a product by its ID.

Make sure to call `finishTransaction(...)` after the purchase is complete
and the content has been delivered or the service has been enabled.

Only available on Android and iOS (15.0+).

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#purchaseproductoptions">PurchaseProductOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#purchaseproductresult">PurchaseProductResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### syncTransactions()

```typescript
syncTransactions() => Promise<void>
```

Sync transactions with the App Store.

This method is used to ensure that all transactions are up-to-date and
methods like `getCurrentTransactions` return the latest transactions.

On **iOS**, calling this method will display a system dialog to the user
asking them to authenticate with their App Store credentials. Call this
method only in response to an explicit user action.

On **Android**, this method silently queries and refreshes purchases from
Google Play without user interaction.

Only available on Android and iOS (15.0+).

**Since:** 0.1.0

--------------------


### Interfaces


#### FinishTransactionOptions

| Prop                | Type                | Description                          | Since |
| ------------------- | ------------------- | ------------------------------------ | ----- |
| **`transactionId`** | <code>string</code> | The ID of the transaction to finish. | 0.1.0 |


#### GetAllTransactionsResult

| Prop               | Type                       | Description                    | Since |
| ------------------ | -------------------------- | ------------------------------ | ----- |
| **`transactions`** | <code>Transaction[]</code> | The transactions for the user. | 0.1.0 |


#### Transaction

| Prop                     | Type                | Description                                                                                                                                                                                                                       | Since |
| ------------------------ | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**                 | <code>string</code> | The unique identifier for the transaction.                                                                                                                                                                                        | 0.1.0 |
| **`verificationResult`** | <code>string</code> | The JWS (JSON Web Signature) representation of the transaction verification result. Pass this to your server to validate the purchase. If the transaction could not be verified, this will not be present. Only available on iOS. | 0.1.0 |
| **`token`**              | <code>string</code> | A unique identifier that represents the user and the product ID for the in-app product they purchased. Pass this to your server to validate the purchase. Only available on Android.                                              | 0.2.1 |


#### GetCurrentTransactionsResult

| Prop               | Type                       | Description                            | Since |
| ------------------ | -------------------------- | -------------------------------------- | ----- |
| **`transactions`** | <code>Transaction[]</code> | The current transactions for the user. | 0.1.0 |


#### GetUnfinishedTransactionsResult

| Prop               | Type                       | Description                               | Since |
| ------------------ | -------------------------- | ----------------------------------------- | ----- |
| **`transactions`** | <code>Transaction[]</code> | The unfinished transactions for the user. | 0.1.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                     | Since |
| ----------------- | -------------------- | --------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Indicates whether in-app purchases are available on the device. | 0.1.0 |


#### PurchaseProductResult

| Prop              | Type                                                | Description                                                   | Since |
| ----------------- | --------------------------------------------------- | ------------------------------------------------------------- | ----- |
| **`transaction`** | <code><a href="#transaction">Transaction</a></code> | The transaction that was created as a result of the purchase. | 0.1.0 |


#### PurchaseProductOptions

| Prop            | Type                | Description                                                                                                                                                                              | Since |
| --------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`productId`** | <code>string</code> | The product ID of the product to purchase. On **Android**, this is the Product ID configured in Google Play Console. On **iOS**, this is the Product ID configured in App Store Connect. | 0.1.0 |

</docgen-api>

## Testing

### Android

To test in-app purchases on Android, you need to:

1. Upload your app to the Google Play Console (internal testing track is sufficient)
2. Add test accounts in Google Play Console under **Settings** → **License Testing**
3. Install the app from Google Play (not via direct APK installation)
4. Use test product IDs or enable license testing for your account

See [Test Google Play Billing](https://developer.android.com/google/play/billing/test) for more details.

### iOS

To test in-app purchases on iOS, you can use:

1. **Sandbox Testing**: Create sandbox test accounts in App Store Connect and test on physical devices or simulators
2. **StoreKit Testing in Xcode**: Configure a StoreKit configuration file for local testing without server connectivity (requires iOS 14+)

Sandbox accounts can be created in App Store Connect under **Users and Access** → **Sandbox**.

See [Testing In-App Purchases](https://developer.apple.com/documentation/storekit/in-app_purchase/testing_in-app_purchases) for more details.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/purchases/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/purchases/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/purchases/LICENSE).
