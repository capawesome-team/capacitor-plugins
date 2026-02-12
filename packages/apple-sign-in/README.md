# @capawesome/capacitor-apple-sign-in

Capacitor plugin to sign-in with Apple.

## Installation

```bash
npm install @capawesome/capacitor-apple-sign-in
npx cap sync
```

## Usage

```typescript
import { AppleSignIn } from '@capawesome/capacitor-apple-sign-in';

const echo = async () => {
  await AppleSignIn.echo();
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
