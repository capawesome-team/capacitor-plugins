# @capawesome/capacitor-formbricks

Unofficial Capacitor plugin for Formbricks SDK.

## Installation

```bash
npm install @capawesome/capacitor-formbricks
npx cap sync
```

## Usage

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const echo = async () => {
  await Formbricks.echo();
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
