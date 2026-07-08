# Capacitor App Review Plugin

Capacitor plugin that allows users to submit app store reviews and ratings.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The App Review plugin is typically used to collect more app store ratings and reviews, for example:

- **In-app review prompts**: Ask for a rating at a positive moment, such as after a completed task, without the user leaving the app.
- **Rate this app button**: Open the app store page directly from a button in your settings or about screen.
- **Feedback flows**: Route satisfied users from your own feedback dialog to the native review dialog or the app store review page.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-review` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-app-review
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidPlayReviewVersion` version of `com.google.android.play:review` (default: `2.0.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { AppReview } from '@capawesome/capacitor-app-review';
```

### Request an in-app review

Show the native in-app review dialog so the user can rate your app without leaving it. Note that on iOS, review requests are limited to 3 requests per year. Only available on Android and iOS (14+):

```typescript
const requestReview = async () => {
  await AppReview.requestReview();
};
```

### Open the app store page

Open the App Store page for the current app and, if possible, open the dialog to leave a review. On iOS, you can pass the Apple ID of your app via the `appId` option. Only available on Android and iOS:

```typescript
const openAppStore = async () => {
  await AppReview.openAppStore();
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

| Prop        | Type                | Description                                                                                                                                                                                                                                     | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`appId`** | <code>string</code> | The app ID of the app to open in the App Store. On **iOS**, this is the Apple ID of your app (e.g. `123456789`). You can find the ID in the URL of your app store entry (e.g. `https://apps.apple.com/app/id123456789`). Only available on iOS. | 6.0.1 |

</docgen-api>

## Testing

In order to test the In-App Review functionality, you need to follow the instructions provided by the respective platform:

- [Android](https://developer.android.com/guide/playcore/in-app-review/test)
- [iOS](https://developer.apple.com/documentation/storekit/skstorereviewcontroller/3566727-requestreview#4278434)

## FAQ

### Why is the in-app review dialog not showing up?

The operating system decides whether the review dialog is actually displayed. On iOS, review requests are limited to 3 requests per year, so the dialog may not appear every time you call `requestReview()`. Both platforms also have special requirements for testing, see the [Testing](#testing) section for the official instructions.

### What is the difference between `requestReview` and `openAppStore`?

The `requestReview()` method shows the native in-app review dialog, so the user can rate your app without leaving it. The `openAppStore(...)` method opens the App Store page for the current app and, if possible, opens the dialog to leave a review there. Use `requestReview()` for gentle prompts and `openAppStore(...)` for an explicit "Rate this app" button.

### Do I need to provide an `appId`?

Only on iOS, where the `appId` option of `openAppStore(...)` is the Apple ID of your app (e.g. `123456789`). You can find the ID in the URL of your app store entry (e.g. `https://apps.apple.com/app/id123456789`).

### How can I test the in-app review functionality?

You need to follow the instructions provided by the respective platform, since in-app reviews behave differently in development and production environments. The official testing guides for Android and iOS are linked in the [Testing](#testing) section.

### Which platforms are supported?

The plugin supports Android and iOS. The `requestReview()` method requires iOS 14 or later, and the `openAppStore(...)` method is available on Android and iOS.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Update](https://capawesome.io/docs/sdks/capacitor/app-update/): Assist your users with native app updates.
- [Badge](https://capawesome.io/docs/sdks/capacitor/badge/): Access and update the badge number of the app icon.
- [Install Referrer](https://capawesome.io/docs/sdks/capacitor/install-referrer/): Read install attribution data from the Play Install Referrer and Apple Ad Services.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-review/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-review/LICENSE).
