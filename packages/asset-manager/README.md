# @capawesome/capacitor-asset-manager

Capacitor plugin to access native asset files.

## Installation

```bash
npm install @capawesome/capacitor-asset-manager
npx cap sync
```

## Usage

```typescript
import { AssetManager } from '@capawesome/capacitor-asset-manager';

const echo = async () => {
  await AssetManager.echo();
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
