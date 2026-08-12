# Capacitor Purchases Plugin

Capacitor plugin to support in-app purchases.

## Features

The Capacitor Purchases plugin is one of the most complete in-app purchase solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🛍️ **Product Types**: Supports subscriptions, consumables, and non-consumable in-app products.
- 🗂️ **Product Retrieval**: Fetch multiple products in a single call for efficient loading.
- 🎁 **Intro Offer Eligibility**: Check if introductory offers are available for subscription products.
- 📅 **Billing Plans**: Supports monthly-with-12-month-commitment subscriptions on iOS (26.4+).
- 🔒 **Server Validation**: Provides verification tokens (iOS JWS, Android purchase tokens) for server-side validation.
- 🔗 **Third-Party Validation**: Includes transaction properties for third-party validation.
- 📋 **Transaction Management**: Track current, unfinished, and historical transactions.
- 🔄 **Purchase Restoration**: Easily sync and restore purchases across devices.
- 🚀 **Modern APIs**: Uses StoreKit 2 and Google Play Billing Library 8.0.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- 🤝 **Compatibility**: Works alongside the [App Review](https://capawesome.io/docs/sdks/capacitor/app-review/) and [Superwall](https://capawesome.io/docs/sdks/capacitor/superwall/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll add it for you!

## Use Cases

The Purchases plugin is typically used whenever an app wants to monetize with in-app purchases, for example:

- **Premium subscriptions**: Sell auto-renewable subscriptions and check whether an introductory offer is available for a product.
- **One-time unlocks**: Sell consumable and non-consumable in-app products, such as feature unlocks or in-app currency.
- **Server-side validation**: Pass the verification tokens (JWS on iOS, purchase tokens on Android) to your backend to validate purchases.
- **Purchase restoration**: Sync and restore purchases so users keep their content across devices.
- **Reliable transaction handling**: Track current, unfinished, and historical transactions to make sure every purchase is delivered.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.3.x          | >=8.x.x           | Active support |

## Prerequisites

### iOS

Xcode 26.5 or later is required to build the plugin.

## Guides

- [Tips for Setting Up In-App Purchases with Capacitor](https://capawesome.io/blog/tips-for-setting-up-in-app-purchases-with-capacitor/)

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-purchases` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
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

The following examples show how to purchase a product and restore previous purchases.

### Purchase a product

Purchase a product by its ID and finish the transaction after the content has been delivered or the service has been enabled. On Android, this is the product ID configured in the Google Play Console. On iOS, this is the product ID configured in App Store Connect. Only available on Android, and on iOS 15.0 and later:

```typescript
import { Purchases } from '@capawesome-team/capacitor-purchases';

const purchaseProduct = async (productId: string) => {
  const { transaction } = await Purchases.purchaseProduct({ productId });
  // Deliver the purchased content or enable the service here
  // ...
  // Finish the transaction
  await Purchases.finishTransaction({ transactionId: transaction.id });
};
```

### Restore previous purchases

Sync the transactions with the store and retrieve the currently owned items to restore purchases across devices. On iOS, `syncTransactions()` displays a system dialog asking the user to authenticate, so call it only in response to an explicit user action. Only available on Android, and on iOS 15.0 and later:

```typescript
import { Purchases } from '@capawesome-team/capacitor-purchases';

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

| Prop                     | Type                                                                            | Description                                                                                                                                                                                                                       | Since |
| ------------------------ | ------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**                 | <code>string</code>                                                             | The unique identifier for the transaction.                                                                                                                                                                                        | 0.1.0 |
| **`verificationResult`** | <code>string</code>                                                             | The JWS (JSON Web Signature) representation of the transaction verification result. Pass this to your server to validate the purchase. If the transaction could not be verified, this will not be present. Only available on iOS. | 0.1.0 |
| **`token`**              | <code>string</code>                                                             | A unique identifier that represents the user and the product ID for the in-app product they purchased. Pass this to your server to validate the purchase. Only available on Android.                                              | 0.2.1 |
| **`productId`**          | <code>string</code>                                                             | The product identifier associated with the transaction.                                                                                                                                                                           | 0.3.2 |
| **`originalJson`**       | <code>string</code>                                                             | The original JSON purchase data. Pass this to your server to validate the purchase. Only available on Android.                                                                                                                    | 0.3.2 |
| **`signature`**          | <code>string</code>                                                             | The RSA signature for purchase verification. Pass this to your server to validate the purchase. Only available on Android.                                                                                                        | 0.3.2 |
| **`planType`**           | <code><a href="#billingplantype">BillingPlanType</a></code>                     | The billing plan type the user committed to when this transaction was made. Only available on iOS (26.4+).                                                                                                                        | 0.3.8 |
| **`commitmentInfo`**     | <code><a href="#transactioncommitmentinfo">TransactionCommitmentInfo</a></code> | The commitment details for transactions made under a billing plan that includes a commitment (e.g. monthly with 12-month commitment). Only available on iOS (26.4+).                                                              | 0.3.8 |


#### TransactionCommitmentInfo

The commitment details for a transaction.

| Prop                      | Type                | Description                                                             | Since |
| ------------------------- | ------------------- | ----------------------------------------------------------------------- | ----- |
| **`billingPeriodNumber`** | <code>number</code> | The 1-based number of the current billing period within the commitment. | 0.3.8 |
| **`totalBillingPeriods`** | <code>number</code> | The total number of billing periods in the commitment.                  | 0.3.8 |
| **`expirationDate`**      | <code>string</code> | The date the commitment expires, as an ISO 8601 string.                 | 0.3.8 |
| **`price`**               | <code>number</code> | The price charged for this billing period, as a decimal number.         | 0.3.8 |


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

| Prop                      | Type                                                | Description                                                                                                                                                                                                                                                              | Since  |
| ------------------------- | --------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------ |
| **`id`**                  | <code>string</code>                                 | The unique product identifier.                                                                                                                                                                                                                                           | 0.3.1  |
| **`displayName`**         | <code>string</code>                                 | The localized display name of the product. On Android, this uses ProductDetails.getName(). On iOS, this uses <a href="#product">Product.displayName</a>.                                                                                                                 | 0.3.1  |
| **`description`**         | <code>string</code>                                 | The localized description of the product.                                                                                                                                                                                                                                | 0.3.1  |
| **`displayPrice`**        | <code>string</code>                                 | The localized price string, formatted for display.                                                                                                                                                                                                                       | 0.3.1  |
| **`price`**               | <code>number</code>                                 | The price as a decimal number. On Android, this is calculated from priceAmountMicros / 1,000,000. On iOS, this uses <a href="#product">Product.price</a> as a decimal.                                                                                                   | 0.3.1  |
| **`currencyCode`**        | <code>string</code>                                 | The ISO 4217 currency code. On Android, this uses priceCurrencyCode. On iOS, this uses priceFormatStyle.currencyCode.                                                                                                                                                    | 0.3.1  |
| **`type`**                | <code><a href="#producttype">ProductType</a></code> | The type of product.                                                                                                                                                                                                                                                     | 0.3.1  |
| **`pricingTerms`**        | <code>PricingTerms[]</code>                         | The pricing terms available for the product, one entry per billing plan. Only present for auto-renewable subscriptions that are configured with multiple billing plans (e.g. monthly with 12-month commitment). Only available on iOS (26.4+).                           | 0.3.8  |
| **`subscriptionGroupId`** | <code>string</code>                                 | The identifier of the subscription group the product belongs to. Only present for auto-renewable subscriptions. Only available on iOS.                                                                                                                                   | 0.3.10 |
| **`subscriptionOffers`**  | <code>SubscriptionOffer[]</code>                    | The subscription offers available for the product. Only present for subscriptions. On **Android**, this contains one entry per base plan and offer combination. On **iOS**, this always contains exactly one entry, because every plan is configured as its own product. | 0.3.10 |


#### PricingTerms

The pricing terms for a billing plan of an auto-renewable subscription.

| Prop                 | Type                                                                              | Description                                                                  | Since |
| -------------------- | --------------------------------------------------------------------------------- | ---------------------------------------------------------------------------- | ----- |
| **`planType`**       | <code><a href="#billingplantype">BillingPlanType</a></code>                       | The plan type the pricing terms apply to.                                    | 0.3.8 |
| **`price`**          | <code>number</code>                                                               | The price billed per billing period, as a decimal number.                    | 0.3.8 |
| **`displayPrice`**   | <code>string</code>                                                               | The localized price string billed per billing period, formatted for display. | 0.3.8 |
| **`period`**         | <code><a href="#subscriptionperiod">SubscriptionPeriod</a></code>                 | The duration of one billing period.                                          | 0.3.8 |
| **`commitmentInfo`** | <code><a href="#pricingtermscommitmentinfo">PricingTermsCommitmentInfo</a></code> | The total commitment details that apply to this billing plan.                | 0.3.8 |


#### SubscriptionPeriod

A length of time for a subscription period.

| Prop        | Type                                                                      | Description                        | Since |
| ----------- | ------------------------------------------------------------------------- | ---------------------------------- | ----- |
| **`value`** | <code>number</code>                                                       | The number of units in the period. | 0.3.8 |
| **`unit`**  | <code><a href="#subscriptionperiodunit">SubscriptionPeriodUnit</a></code> | The unit of time for the period.   | 0.3.8 |


#### PricingTermsCommitmentInfo

The total commitment details of a billing plan.

| Prop               | Type                                                              | Description                                                        | Since |
| ------------------ | ----------------------------------------------------------------- | ------------------------------------------------------------------ | ----- |
| **`price`**        | <code>number</code>                                               | The total committed price, as a decimal number.                    | 0.3.8 |
| **`displayPrice`** | <code>string</code>                                               | The localized total committed price string, formatted for display. | 0.3.8 |
| **`period`**       | <code><a href="#subscriptionperiod">SubscriptionPeriod</a></code> | The total commitment duration.                                     | 0.3.8 |


#### SubscriptionOffer

An offer to subscribe to a product.

| Prop               | Type                                                              | Description                                                                                                                                                                                            | Since  |
| ------------------ | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------ |
| **`basePlanId`**   | <code>string</code>                                               | The ID of the base plan the offer belongs to. Only available on Android.                                                                                                                               | 0.3.10 |
| **`offerId`**      | <code>string</code>                                               | The ID of the offer. Not present if the offer is the base plan itself. Only available on Android.                                                                                                      | 0.3.10 |
| **`tags`**         | <code>string[]</code>                                             | The tags associated with the offer. Empty on iOS.                                                                                                                                                      | 0.3.10 |
| **`displayPrice`** | <code>string</code>                                               | The localized recurring price string, formatted for display. This is the price the user pays after all introductory pricing phases have ended.                                                         | 0.3.10 |
| **`price`**        | <code>number</code>                                               | The recurring price as a decimal number. This is the price the user pays after all introductory pricing phases have ended.                                                                             | 0.3.10 |
| **`currencyCode`** | <code>string</code>                                               | The ISO 4217 currency code.                                                                                                                                                                            | 0.3.10 |
| **`period`**       | <code><a href="#subscriptionperiod">SubscriptionPeriod</a></code> | The duration of one recurring billing period.                                                                                                                                                          | 0.3.10 |
| **`phases`**       | <code>SubscriptionPricingPhase[]</code>                           | The pricing phases of the offer, in the order they are applied. The last phase is always the recurring phase. Any preceding phase is an introductory phase such as a free trial or a discounted price. | 0.3.10 |


#### SubscriptionPricingPhase

A pricing phase of a subscription offer.

| Prop                 | Type                                                                              | Description                                                                                            | Since  |
| -------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ | ------ |
| **`displayPrice`**   | <code>string</code>                                                               | The localized price string of the phase, formatted for display.                                        | 0.3.10 |
| **`price`**          | <code>number</code>                                                               | The price of the phase as a decimal number. This is `0` for a free trial.                              | 0.3.10 |
| **`currencyCode`**   | <code>string</code>                                                               | The ISO 4217 currency code.                                                                            | 0.3.10 |
| **`period`**         | <code><a href="#subscriptionperiod">SubscriptionPeriod</a></code>                 | The duration of one billing period of the phase.                                                       | 0.3.10 |
| **`cycleCount`**     | <code>number</code>                                                               | The number of billing periods the phase is applied for. This is `0` if the phase repeats indefinitely. | 0.3.10 |
| **`recurrenceMode`** | <code><a href="#pricingphaserecurrencemode">PricingPhaseRecurrenceMode</a></code> | How the phase is repeated.                                                                             | 0.3.10 |


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

| Prop             | Type                                                        | Description                                                                                                                                                                                                                            | Since |
| ---------------- | ----------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`basePlanId`** | <code>string</code>                                         | The base plan ID of the subscription to purchase. Only available on Android.                                                                                                                                                           | 0.3.5 |
| **`offerId`**    | <code>string</code>                                         | The offer ID of the subscription offer to purchase. Only available on Android.                                                                                                                                                         | 0.3.5 |
| **`planType`**   | <code><a href="#billingplantype">BillingPlanType</a></code> | The billing plan type to purchase for a monthly-with-12-month-commitment auto-renewable subscription. Only available on iOS (26.4+).                                                                                                   | 0.3.8 |
| **`productId`**  | <code>string</code>                                         | The product ID of the product to purchase. On **Android**, this is the <a href="#product">Product</a> ID configured in Google Play Console. On **iOS**, this is the <a href="#product">Product</a> ID configured in App Store Connect. | 0.1.0 |


### Enums


#### BillingPlanType

| Members       | Value                   | Description                                                                     | Since |
| ------------- | ----------------------- | ------------------------------------------------------------------------------- | ----- |
| **`Monthly`** | <code>'MONTHLY'</code>  | The subscription is billed once per renewal period.                             | 0.3.8 |
| **`UpFront`** | <code>'UP_FRONT'</code> | The subscription is paid in full upfront at the start of the commitment period. | 0.3.8 |


#### ProductType

| Members                         | Value                                      | Description                                                                                                                                                                                           | Since |
| ------------------------------- | ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Consumable`**                | <code>'CONSUMABLE'</code>                  | A consumable in-app product. On Android, this is <a href="#producttype">ProductType</a>.INAPP. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.consumable.      | 0.3.1 |
| **`NonConsumable`**             | <code>'NON_CONSUMABLE'</code>              | A non-consumable in-app product. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.nonConsumable. Only available on iOS.                                          | 0.3.1 |
| **`AutoRenewableSubscription`** | <code>'AUTO_RENEWABLE_SUBSCRIPTION'</code> | An auto-renewable subscription. On Android, this is <a href="#producttype">ProductType</a>.SUBS. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.autoRenewable. | 0.3.1 |
| **`NonRenewableSubscription`**  | <code>'NON_RENEWABLE_SUBSCRIPTION'</code>  | A non-renewing subscription. On iOS, this is <a href="#product">Product</a>.<a href="#producttype">ProductType</a>.nonRenewable. Only available on iOS.                                               | 0.3.1 |


#### SubscriptionPeriodUnit

| Members       | Value                  | Description                                                | Since |
| ------------- | ---------------------- | ---------------------------------------------------------- | ----- |
| **`Day`**     | <code>'DAY'</code>     |                                                            | 0.3.8 |
| **`Week`**    | <code>'WEEK'</code>    |                                                            | 0.3.8 |
| **`Month`**   | <code>'MONTH'</code>   |                                                            | 0.3.8 |
| **`Year`**    | <code>'YEAR'</code>    |                                                            | 0.3.8 |
| **`Unknown`** | <code>'UNKNOWN'</code> | The unit of time is not recognized by this plugin version. | 0.3.8 |


#### PricingPhaseRecurrenceMode

| Members                 | Value                             | Description                                                        | Since  |
| ----------------------- | --------------------------------- | ------------------------------------------------------------------ | ------ |
| **`InfiniteRecurring`** | <code>'INFINITE_RECURRING'</code> | The phase repeats indefinitely until the subscription is canceled. | 0.3.10 |
| **`FiniteRecurring`**   | <code>'FINITE_RECURRING'</code>   | The phase repeats for a fixed number of billing periods.           | 0.3.10 |
| **`NonRecurring`**      | <code>'NON_RECURRING'</code>      | The phase is billed only once.                                     | 0.3.10 |


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

## FAQ

### Do I need a third-party service to use this plugin?

No, the plugin uses the native store APIs directly, namely StoreKit 2 on iOS and the Google Play Billing Library on Android. For server-side validation, the plugin provides verification tokens (JWS on iOS, purchase tokens on Android) that you can pass to your own backend, and it includes transaction properties for third-party validation services if you prefer to use one.

### Why do I need to call `finishTransaction` after a purchase?

Calling `finishTransaction(...)` indicates to the store that your app has delivered the purchased content or enabled the service. Make sure to call it after every purchase, and check for unfinished transactions with `getUnfinishedTransactions()` at least once every app launch to ensure that all transactions are processed correctly.

### How do I validate purchases on my server?

Every transaction contains properties that you can pass to your server for validation. On iOS, use the `verificationResult` property, which contains the JWS representation of the transaction verification result. On Android, use the `token`, `originalJson`, and `signature` properties.

### Why does `syncTransactions` show a system dialog on iOS?

On iOS, calling `syncTransactions()` displays a system dialog asking the user to authenticate with their App Store credentials. You should therefore call this method only in response to an explicit user action, for example a "Restore Purchases" button. On Android, this method silently queries and refreshes purchases from Google Play without user interaction.

### Which iOS versions are supported?

Most methods require iOS 15.0 or later because the plugin uses StoreKit 2. Billing plan features, such as monthly-with-12-month-commitment subscriptions, require iOS 26.4 or later. Additionally, Xcode 26.5 or later is required to build the plugin, as mentioned in the [Prerequisites](#prerequisites) section.

### How do I test in-app purchases?

On Android, upload your app to the Google Play Console, add test accounts under License Testing, and install the app from Google Play. On iOS, use sandbox test accounts created in App Store Connect or a StoreKit configuration file in Xcode. See the [Testing](#testing) section for detailed instructions.

## Related Plugins

- [App Review](https://capawesome.io/docs/sdks/capacitor/app-review/): Allow users to submit app store reviews and ratings.
- [Square Mobile Payments](https://capawesome.io/docs/sdks/capacitor/square-mobile-payments/): Unofficial Capacitor plugin for the Square Mobile Payments SDK.
- [Superwall](https://capawesome.io/docs/sdks/capacitor/superwall/): Unofficial Capacitor plugin for the Superwall SDK to present remotely configured paywalls.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/purchases/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/purchases/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/purchases/LICENSE).

## Third-Party Notices

This plugin depends on the **Google Play Billing Library**
(`com.android.billingclient:billing`), governed by the [Android Software
Development Kit License Agreement](https://developer.android.com/studio/terms).
The library is not bundled in this plugin; it is fetched at build time from
Google's Maven repository by your application's build system. By building
an Android application that uses this plugin, you accept the Android SDK
License Agreement and the [Google Play Developer Program Policies](https://play.google.com/about/developer-content-policy/),
and you are responsible for ensuring your app complies with both. This
plugin is intended for use in applications distributed via the Google Play
Store on compatible Android devices. "Google Play", "Google Play Billing",
and "Google Play Store" are used as descriptive identifiers only — no
affiliation with or endorsement by Google is claimed or implied.
