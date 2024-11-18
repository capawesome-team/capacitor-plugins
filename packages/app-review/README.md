# @capawesome/capacitor-app-review

Capacitor plugin that allows users to submit app store reviews and ratings.

## Installation

```bash
npm install @capawesome/capacitor-app-review
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppReview } from '@capawesome/capacitor-app-review';

const echo = async () => {
  await AppReview.echo();
};
```

## API

<docgen-index>

* [`openAppStore()`](#openappstore)
* [`requestReview()`](#requestreview)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### openAppStore()

```typescript
openAppStore() => Promise<void>
```

Open the App Store page for the current app and, if possible, open the dialog to leave a review.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### requestReview()

```typescript
requestReview() => Promise<void>
```

Request an in-app review.

Note: On iOS, review requests are limited to 3 requests per year.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-review/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-review/LICENSE).
