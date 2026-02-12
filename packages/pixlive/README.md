# @capawesome/capacitor-pixlive

Unofficial Capacitor plugin for Pixlive SDK.

## Installation

```bash
npm install @capawesome/capacitor-pixlive
npx cap sync
```

## Usage

```typescript
import { Pixlive } from '@capawesome/capacitor-pixlive';

const echo = async () => {
  await Pixlive.echo();
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
