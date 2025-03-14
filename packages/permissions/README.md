# @capawesome/capacitor-permissions

Capacitor plugin to request and check the app permissions.

## Installation

```bash
npm install @capawesome/capacitor-permissions
npx cap sync
```

## Usage

```typescript
import { Permissions } from '@capawesome/capacitor-permissions';

const echo = async () => {
  await Permissions.echo();
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
