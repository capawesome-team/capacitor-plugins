# @capawesome/capacitor-vapi

Unofficial Capacitor plugin for [Vapi](https://vapi.ai/).[^1]

## Installation

```bash
npm install @capawesome/capacitor-vapi @vapi-ai/web
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxCoreKtxVersion` version of `androidx.core:core-ktx` (default: `1.13.1`)

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Vapi } from '@capawesome/capacitor-vapi';

const echo = async () => {
  await Vapi.echo();
};
```

## API

<docgen-index>

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vapi/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vapi/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Superpowered Labs Inc. or any of their affiliates or subsidiaries.
