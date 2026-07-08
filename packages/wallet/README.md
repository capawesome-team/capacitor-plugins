# Capacitor Wallet Plugin

Capacitor plugin for adding passes to [Apple Wallet](https://developer.apple.com/documentation/passkit) and [Google Wallet](https://developers.google.com/wallet).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🍎 **Apple Wallet**: Add passes such as tickets, loyalty cards, coupons and boarding passes to Apple Wallet.
- 🤖 **Google Wallet**: Save passes to Google Wallet using the official "Save to Wallet" flow.
- ✅ **Availability check**: Check whether passes can be added on the current device before showing the UI.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Wallet plugin is typically used to hand digital passes to the user's wallet app, for example:

- **Boarding passes**: Add flight or train boarding passes so travelers have them ready at the gate.
- **Event tickets**: Add tickets for concerts, sports events, or the cinema.
- **Loyalty cards**: Let customers store membership and loyalty cards in their wallet.
- **Coupons**: Distribute coupons and vouchers that users can redeem in store.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-wallet` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-wallet
npx cap sync
```

This plugin presents passes only. Passes must be **created and signed on your server**. See [Creating Passes](#creating-passes) below.

### iOS

Adding passes to Apple Wallet via `PKAddPassesViewController` does not require any special entitlement. The `com.apple.developer.pass-type-identifiers` entitlement is only needed if you want to read or manage passes that your app owns, which is out of scope for this plugin.

### Android

No additional permissions or Gradle variables are required. The plugin opens the Google Wallet "Save to Wallet" flow, which is handled by the Google Wallet app or the device's browser.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether passes can be added, add passes to Apple Wallet, and save a pass to Google Wallet.

### Check whether passes can be added

On some devices (e.g. certain iPads) or when adding passes is restricted, passes cannot be added to Apple Wallet. Use this method to gate the UI that triggers `addPasses(...)`. Only available on iOS:

```typescript
import { Wallet } from '@capawesome/capacitor-wallet';

const canAddPasses = async () => {
  const { canAdd } = await Wallet.canAddPasses();
  return canAdd;
};
```

### Add passes to Apple Wallet

Present the system add-pass sheet with one or more passes. Each pass must be a base64-encoded `.pkpass` file that was created and signed on your server (see [Creating Passes](#creating-passes)). Only available on iOS:

```typescript
import { Wallet } from '@capawesome/capacitor-wallet';

const addPasses = async (passes: string[]) => {
  // `passes` are base64-encoded `.pkpass` files that were signed on your server.
  await Wallet.addPasses({ passes });
};
```

### Save a pass to Google Wallet

Open the Google Wallet "Save to Wallet" flow with a signed JWT that was created on your server (see [Creating Passes](#creating-passes)). Only available on Android:

```typescript
import { Wallet } from '@capawesome/capacitor-wallet';

const saveToGoogleWallet = async (jwt: string) => {
  // `jwt` is a signed Google Wallet JWT that was created on your server.
  await Wallet.saveToGoogleWallet({ jwt });
};
```

## API

<docgen-index>

* [`addPasses(...)`](#addpasses)
* [`canAddPasses()`](#canaddpasses)
* [`saveToGoogleWallet(...)`](#savetogooglewallet)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addPasses(...)

```typescript
addPasses(options: AddPassesOptions) => Promise<void>
```

Add one or more passes to Apple Wallet.

The passes must be created and signed on your server. This method only
presents the system add-pass sheet with the provided passes.

Only available on iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#addpassesoptions">AddPassesOptions</a></code> |

**Since:** 0.1.0

--------------------


### canAddPasses()

```typescript
canAddPasses() => Promise<CanAddPassesResult>
```

Check whether passes can be added to Apple Wallet.

This may return `false` on some devices (e.g. certain iPads) or when adding
passes is restricted. Use this to gate the UI that triggers `addPasses`.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#canaddpassesresult">CanAddPassesResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### saveToGoogleWallet(...)

```typescript
saveToGoogleWallet(options: SaveToGoogleWalletOptions) => Promise<void>
```

Save a pass to Google Wallet.

This opens the Google Wallet "Save to Wallet" flow using the provided JWT.
The JWT must be created and signed on your server.

**Note**: With the URL-based flow there is no completion signal, so the
promise resolves as soon as the flow is launched, not when the pass is
actually saved.

Only available on Android.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#savetogooglewalletoptions">SaveToGoogleWalletOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### AddPassesOptions

| Prop         | Type                  | Description                                                                                                           | Since |
| ------------ | --------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`passes`** | <code>string[]</code> | The passes to add to Apple Wallet. Each entry must be a base64-encoded `.pkpass` file that was signed on your server. | 0.1.0 |


#### CanAddPassesResult

| Prop         | Type                 | Description                                  | Since |
| ------------ | -------------------- | -------------------------------------------- | ----- |
| **`canAdd`** | <code>boolean</code> | Whether passes can be added to Apple Wallet. | 0.1.0 |


#### SaveToGoogleWalletOptions

| Prop      | Type                | Description                                                   | Since |
| --------- | ------------------- | ------------------------------------------------------------- | ----- |
| **`jwt`** | <code>string</code> | The signed Google Wallet JWT that was created on your server. | 0.1.0 |

</docgen-api>

## Creating Passes

This plugin only **presents** passes to the user. Creating and signing passes must happen on your server, because it requires private keys and certificates that must never be shipped inside your app.

### Apple Wallet

A pass is a signed `.pkpass` bundle. Build and sign it on your server using your Pass Type ID certificate, then send the resulting file to your app as a base64-encoded string and pass it to `addPasses(...)`. See Apple's [Wallet documentation](https://developer.apple.com/documentation/walletpasses) for details on building and signing passes.

### Google Wallet

A pass is represented by a signed JWT. Create and sign the JWT on your server using your Google Wallet service account, then send it to your app and pass it to `saveToGoogleWallet(...)`. See Google's [Wallet API documentation](https://developers.google.com/wallet) for details on creating passes and JWTs.

**Note**: With the URL-based "Save to Wallet" flow used by this plugin, there is no completion signal, so `saveToGoogleWallet(...)` resolves as soon as the flow is launched, not when the pass is actually saved.

## FAQ

### Can this plugin create passes for me?

No, this plugin only presents passes to the user. Creating and signing passes must happen on your server, because it requires private keys and certificates that must never be shipped inside your app. See [Creating Passes](#creating-passes) for details and links to the official Apple and Google documentation.

### Which methods are available on which platform?

The `addPasses(...)` and `canAddPasses()` methods are only available on iOS, where they interact with Apple Wallet. The `saveToGoogleWallet(...)` method is only available on Android, where it opens the Google Wallet "Save to Wallet" flow.

### Why does `saveToGoogleWallet` resolve before the pass is saved?

The plugin uses the URL-based "Save to Wallet" flow, which does not provide a completion signal. The promise therefore resolves as soon as the flow is launched, not when the pass is actually saved.

### Do I need a special entitlement to add passes on iOS?

No, adding passes via the system add-pass sheet does not require any special entitlement. The `com.apple.developer.pass-type-identifiers` entitlement is only needed if you want to read or manage passes that your app owns, which is out of scope for this plugin.

### Why does `canAddPasses` return false?

The `canAddPasses()` method may return `false` on some devices (e.g. certain iPads) or when adding passes is restricted. Use it to hide or disable the UI that triggers `addPasses(...)` in these cases.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Purchases](https://capawesome.io/docs/sdks/capacitor/purchases/): Support in-app purchases in your Capacitor app.
- [Square Mobile Payments](https://capawesome.io/docs/sdks/capacitor/square-mobile-payments/): Accept payments with the Square Mobile Payments SDK.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/wallet/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/wallet/LICENSE).
