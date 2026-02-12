# @capawesome/capacitor-google-sign-in

Capacitor plugin to sign-in with Google.

## Installation

```bash
npm install @capawesome/capacitor-google-sign-in
npx cap sync
```

## Usage

```typescript
import { GoogleSignIn } from '@capawesome/capacitor-google-sign-in';

const echo = async () => {
  await GoogleSignIn.echo();
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
