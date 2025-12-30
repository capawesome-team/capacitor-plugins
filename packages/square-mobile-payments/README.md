# @capawesome/capacitor-square-mobile-payments

Unofficial Capacitor plugin for Square Mobile Payments SDK.

## Installation

```bash
npm install @capawesome/capacitor-square-mobile-payments
npx cap sync
```

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
