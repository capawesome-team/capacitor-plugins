# @capawesome/capacitor-square-mobile-payments

Unofficial Capacitor plugin for [Square Mobile Payments SDK](https://developer.squareup.com/docs/mobile-payments-sdk).[^1]

## Installation

```bash
npm install @capawesome/capacitor-square-mobile-payments
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$squareMobilePaymentsSdkVersion` version of `com.squareup.sdk:mobile-payments-sdk` (default: `2.3.4`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Usage

```typescript
import { SquareMobilePayments } from '@capawesome/capacitor-square-mobile-payments';

const echo = async () => {
  await SquareMobilePayments.echo();
};
```

## API

<docgen-index>

* [`echo(...)`](#echo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Square, Inc. or any of their affiliates or subsidiaries.