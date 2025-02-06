# @capawesome/capacitor-android-edge-to-edge

Capacitor plugin to support edge-to-edge display on Android.

## Installation

```bash
npm install @capawesome/capacitor-android-edge-to-edge
npx cap sync
```

## Usage

```typescript
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge';

const echo = async () => {
  await EdgeToEdge.echo();
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
