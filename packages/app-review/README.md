# @capawesome/capacitor-app-review

Capacitor plugin that allows users to submit app store reviews and ratings.

## Installation

```bash
npm install @capawesome/capacitor-app-review
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidPlayReviewVersion` version of `com.google.android.play:review` (default: `2.0.2`)

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppReview } from '@capawesome/capacitor-app-review';

const openAppStore = async () => {
  await AppReview.openAppStore();
};

const requestReview = async () => {
  await AppReview.requestReview();
};
```

## API

<docgen-index>

* [`openAppStore(...)`](#openappstore)
* [`requestReview()`](#requestreview)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### openAppStore(...)

```typescript
openAppStore(options?: OpenAppStoreOptions | undefined) => Promise<void>
```

Open the App Store page for the current app and, if possible, open the dialog to leave a review.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#openappstoreoptions">OpenAppStoreOptions</a></code> |

**Since:** 6.0.0

--------------------


### requestReview()

```typescript
requestReview() => Promise<void>
```

Request an in-app review.

**Attention**: On iOS, review requests are limited to 3 requests per year.

Only available on Android and iOS (14+).

**Since:** 6.0.0

--------------------


### Interfaces


#### OpenAppStoreOptions

| Prop        | Type                | Description                                                                                                                             | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`appId`** | <code>string</code> | The app ID of the app to open in the App Store. On **iOS**, this is the Apple ID of your app (e.g. `123456789`). Only available on iOS. | 6.0.1 |

</docgen-api>

## Testing

In order to test the In-App Review functionality, you need to follow the instructions provided by the respective platform:

- [Android](https://developer.android.com/guide/playcore/in-app-review/test)
- [iOS](https://developer.apple.com/documentation/storekit/skstorereviewcontroller/3566727-requestreview#4278434)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-review/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-review/LICENSE).
