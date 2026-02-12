# @capawesome/capacitor-apple-sign-in

Unofficial Capacitor plugin to sign-in with Apple.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Installation

```bash
npm install @capawesome/capacitor-apple-sign-in
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppleSignIn } from '@capawesome/capacitor-apple-sign-in';

const echo = async () => {
  await AppleSignIn.echo();
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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/apple-sign-in/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/apple-sign-in/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Apple Inc. or any of their affiliates or subsidiaries.
