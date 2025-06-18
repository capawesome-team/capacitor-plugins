# @capawesome/capacitor-realtimekit

Unofficial Capacitor plugin for using the RealtimeKit SDK.

## Installation

```bash
npm install @capawesome/capacitor-realtimekit
npx cap sync
```

## Usage

```typescript
import { RealtimeKit } from '@capawesome/capacitor-realtimekit';

const echo = async () => {
  await RealtimeKit.echo();
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
