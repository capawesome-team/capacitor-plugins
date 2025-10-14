# @capawesome/capacitor-age-signals

Capacitor plugin to use the Play Age Signals API to retrieve age-related signals for users.

## Installation

```bash
npm install @capawesome/capacitor-age-signals
npx cap sync
```

## Usage

```typescript
import { AgeSignals } from '@capawesome/capacitor-age-signals';

const echo = async () => {
  await AgeSignals.echo();
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
