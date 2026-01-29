# @capawesome-team/capacitor-purchases

Capacitor plugin to support in-app purchases.

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for in-app purchases. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🛍️ **Product Types**: Supports subscriptions, consumables, and non-consumable in-app products.
- 🗂️ **Product Retrieval**: Fetch multiple products in a single call for efficient loading.
- 🎁 **Intro Offer Eligibility**: Check if introductory offers are available for subscription products.
- 🔒 **Server Validation**: Provides verification tokens (iOS JWS, Android purchase tokens) for server-side validation.
- 🔗 **Third-Party Validation**: Includes transaction properties for third-party validation.
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
* [`getProductById(...)`](#getproductbyid)
* [`getProductsByIds(...)`](#getproductsbyids)
* [`getUnfinishedTransactions()`](#getunfinishedtransactions)
* [`isAvailable()`](#isavailable)
* [`isIntroOfferAvailableForProduct(...)`](#isintroofferavailableforproduct)
* [`purchaseProduct(...)`](#purchaseproduct)
* [`syncTransactions()`](#synctransactions)
* [Interfaces](#interfaces)
* [Enums](#enums)

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


### getProductById(...)

```typescript
getProductById(options: GetProductByIdOptions) => Promise<GetProductByIdResult>
```

Get product details by product ID.

Only available on Android and iOS (15.0+).

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getproductbyidoptions">GetProductByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getproductbyidresult">GetProductByIdResult</a>&gt;</code>

**Since:** 0.3.1

--------------------


### getProductsByIds(...)

```typescript
getProductsByIds(options: GetProductsByIdsOptions) => Promise<GetProductsByIdsResult>
```

Get product details for multiple products by their IDs.

Only available on Android and iOS (15.0+).

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getproductsbyidsoptions">GetProductsByIdsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getproductsbyidsresult">GetProductsByIdsResult</a>&gt;</code>

**Since:** 0.3.3

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


### isIntroOfferAvailableForProduct(...)

```typescript
isIntroOfferAvailableForProduct(options: IsIntroOfferAvailableForProductOptions) => Promise<IsIntroOfferAvailableForProductResult>
```

Check if an introductory offer is available for a product.

On **iOS**, this uses StoreKit 2's `isEligibleForIntroOffer` to check if the customer
has already used an introductory offer for any subscription within the same subscription group.

On **Android**, this checks if the product has any introductory pricing phases available.
Google Play automatically filters offers based on eligibility when querying products,
so if an intro offer is present, the user can purchase it.

Only available on Android and iOS.

| Param         | Type                                                                                                      |
| ------------- | --------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isintroofferavailableforproductoptions">IsIntroOfferAvailableForProductOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isintroofferavailableforproductresult">IsIntroOfferAvailableForProductResult</a>&gt;</code>

**Since:** 0.3.2

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
| **`productId`**          | <code>string</code> | The product identifier associated with the transaction.                                                                                                                                                                           | 0.3.2 |
| **`originalJson`**       | <code>string</code> | The original JSON purchase data. Pass this to your server to validate the purchase. Only available on Android.                                                                                                                    | 0.3.2 |
| **`signature`**          | <code>string</code> | The RSA signature for purchase verification. Pass this to your server to validate the purchase. Only available on Android.                                                                                                        | 0.3.2 |


#### GetCurrentTransactionsResult

| Prop               | Type                       | Description                            | Since |
| ------------------ | -------------------------- | -------------------------------------- | ----- |
| **`transactions`** | <code>Transaction[]</code> | The current transactions for the user. | 0.1.0 |


#### GetProductByIdResult

| Prop          | Type                                        | Description          | Since |
| ------------- | ------------------------------------------- | -------------------- | ----- |
| **`product`** | <code><a href="#product">Product</a></code> | The product details. | 0.3.1 |


#### Product

Represents an in-app product available for purchase.

| Prop               | Type                                                | Description                                                                                                                                                            | Since |
| ------------------ | --------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**           | <code>string</code>                                 | The unique product identifier.                                                                                                                                         | 0.3.1 |
| **`displayName`**  | <code>string</code>                                 | The localized display name of the product. On Android, this uses ProductDetails.getName(). On iOS, this uses <a href="#product">Product.displayName</a>.               | 0.3.1 |
| **`description`**  | <code>string</code>                                 | The localized description of the product.                                                                                                                              | 0.3.1 |
| **`displayPrice`** | <code>string</code>                                 | The localized price string, formatted for display.                                                                                                                     | 0.3.1 |
| **`price`**        | <code>number</code>                                 | The price as a decimal number. On Android, this is calculated from priceAmountMicros / 1,000,000. On iOS, this uses <a href="#product">Product.price</a> as a decimal. | 0.3.1 |
| **`currencyCode`** | <code>string</code>                                 | The ISO 4217 currency code. On Android, this uses priceCurrencyCode. On iOS, this uses priceFormatStyle.currencyCode.                                                  | 0.3.1 |
| **`type`**         | <code><a href="#producttype">ProductType</a></code> | The type of product.                                                                                                                                                   | 0.3.1 |


#### GetProductByIdOptions

| Prop                  | Type                                                        | Description                                                                                                                                                                                                                    | Since |
| --------------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`productCategory`** | <code><a href="#productcategory">ProductCategory</a></code> | The category of product to fetch. If not specified, the plugin will query both categories. Only available on Android.                                                                                                          | 0.3.3 |
| **`productId`**       | <code>string</code>                                         | The product ID of the product to retrieve. On Android, this is the <a href="#product">Product</a> ID configured in Google Play Console. On iOS, this is the <a href="#product">Product</a> ID configured in App Store Connect. | 0.3.1 |


#### GetProductsByIdsResult

| Prop           | Type                   | Description                                                                                                                                                                        | Since |
| -------------- | ---------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`products`** | <code>Product[]</code> | The product details for the requested products. Note: If some products are not found, they will not be included in the array. The array may contain fewer products than requested. | 0.3.3 |


#### GetProductsByIdsOptions

| Prop                  | Type                                                        | Description                                                                                                                                                                                                                            | Since |
| --------------------- | ----------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`productCategory`** | <code><a href="#productcategory">ProductCategory</a></code> | The category of products to fetch. If not specified, the plugin will query both categories. Only available on Android.                                                                                                                 | 0.3.3 |
| **`productIds`**      | <code>string[]</code>                                       | The product IDs of the products to retrieve. On Android, these are the <a href="#product">Product</a> IDs configured in Google Play Console. On iOS, these are the <a href="#product">Product</a> IDs configured in App Store Connect. | 0.3.3 |


#### GetUnfinishedTransactionsResult

| Prop               | Type                       | Description                               | Since |
| ------------------ | -------------------------- | ----------------------------------------- | ----- |
| **`transactions`** | <code>Transaction[]</code> | The unfinished transactions for the user. | 0.1.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                     | Since |
| ----------------- | -------------------- | --------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Indicates whether in-app purchases are available on the device. | 0.1.0 |


#### IsIntroOfferAvailableForProductResult

| Prop                        | Type                 | Description                                                 | Since |
| --------------------------- | -------------------- | ----------------------------------------------------------- | ----- |
| **`isIntroOfferAvailable`** | <code>boolean</code> | Whether an introductory offer is available for the product. | 0.3.2 |


#### IsIntroOfferAvailableForProductOptions

| Prop            | Type                | Description                                  | Since |
| --------------- | ------------------- | -------------------------------------------- | ----- |
| **`productId`** | <code>string</code> | The product ID of the subscription to check. | 0.3.2 |


#### PurchaseProductResult

| Prop              | Type                                                | Description                                                   | Since |
| ----------------- | --------------------------------------------------- | ------------------------------------------------------------- | ----- |
| **`transaction`** | <code><a href="#transaction">Transaction</a></code> | The transaction that was created as a result of the purchase. | 0.1.0 |


#### PurchaseProductOptions

| Prop            | Type                | Description                                                                                                                                                                                                                            | Since |
| --------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`productId`** | <code>string</code> | The product ID of the product to purchase. On **Android**, this is the <a href="#product">Product</a> ID configured in Google Play Console. On **iOS**, this is the <a href="#product">Product</a> ID configured in App Store Connect. | 0.1.0 |


### Enums


#### ProductType

| Members                         | Value                                      | Description                                                                                                                                                                                           | Since |
| ------------------------------- | ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Consumable`**                | <code>'CONSUMABLE'</code>                  | A consumable in-app product. On Android, this is <a href="#producttype">ProductType</a>.INAPP. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.consumable.      | 0.3.1 |
| **`NonConsumable`**             | <code>'NON_CONSUMABLE'</code>              | A non-consumable in-app product. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.nonConsumable. Only available on iOS.                                          | 0.3.1 |
| **`AutoRenewableSubscription`** | <code>'AUTO_RENEWABLE_SUBSCRIPTION'</code> | An auto-renewable subscription. On Android, this is <a href="#producttype">ProductType</a>.SUBS. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.autoRenewable. | 0.3.1 |
| **`NonRenewableSubscription`**  | <code>'NON_RENEWABLE_SUBSCRIPTION'</code>  | A non-renewing subscription. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.nonRenewable. Only available on iOS.                                               | 0.3.1 |


#### ProductCategory

| Members            | Value                       | Description                                                                                                                               | Since |
| ------------------ | --------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`InApp`**        | <code>'IN_APP'</code>       | In-app products (consumables and non-consumables). On Android, this maps to `BillingClient.<a href="#producttype">ProductType</a>.INAPP`. | 0.3.3 |
| **`Subscription`** | <code>'SUBSCRIPTION'</code> | Subscription products. On Android, this maps to `BillingClient.<a href="#producttype">ProductType</a>.SUBS`.                              | 0.3.3 |

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
