# @capawesome/capacitor-libsql

Capacitor plugin for libSQL databases.

## Installation

```bash
npm install @capawesome/capacitor-libsql
npx cap sync
```

## Usage

```typescript
import { Libsql } from '@capawesome/capacitor-libsql';

const echo = async () => {
  await Libsql.echo();
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
