# Capacitor In-App Browser Plugin

Capacitor plugin to open URLs in the external browser, the system browser or an embedded web view.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for in-app browsing. Here are some of the key features:

- 🌐 **Three browser modes**: Open URLs in the external browser, the system browser (Custom Tabs on Android, `SFSafariViewController` on iOS) or an embedded web view.
- 🧭 **Navigation events**: Get notified when the browser is closed, a page has been loaded or a navigation has been completed.
- 💉 **JavaScript execution**: Execute any JavaScript code in the embedded web view.
- 💬 **Messaging**: Exchange messages between your app and the web page in both directions.
- 🎨 **Toolbar theming**: Customize the toolbar color, title, close button and navigation buttons.
- 🍪 **Session control**: Clear the cache and session data, or use an isolated data store on iOS.
- 🎥 **Media permissions**: Camera and microphone permission requests from web pages are forwarded to the app.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-in-app-browser` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-in-app-browser
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxBrowserVersion` version of `androidx.browser:browser` (default: `1.9.0`)

#### Permissions

If web pages opened in the embedded web view should be able to access the camera or microphone, the following elements must be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Required if web pages should be able to access the camera. -->
<uses-permission android:name="android.permission.CAMERA" />
<!-- Required if web pages should be able to access the microphone. -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

The permissions must also be granted before a web page requests access to the camera or microphone.

### iOS

#### Privacy Descriptions

If web pages opened in the embedded web view should be able to access the camera or microphone, the `NSCameraUsageDescription` and `NSMicrophoneUsageDescription` keys must be added to the `Info.plist` file of your app:

```xml
<key>NSCameraUsageDescription</key>
<string>The camera is used by websites opened in the in-app browser.</string>
<key>NSMicrophoneUsageDescription</key>
<string>The microphone is used by websites opened in the in-app browser.</string>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { InAppBrowser } from '@capawesome/capacitor-in-app-browser';

const openInExternalBrowser = async () => {
  await InAppBrowser.openInExternalBrowser({
    url: 'https://capawesome.io',
  });
};

const openInSystemBrowser = async () => {
  await InAppBrowser.openInSystemBrowser({
    url: 'https://capawesome.io',
    android: {
      showTitle: true,
      toolbarColor: '#008080',
    },
    ios: {
      dismissButtonStyle: 'close',
      toolbarColor: '#008080',
    },
  });
};

const openInWebView = async () => {
  await InAppBrowser.openInWebView({
    url: 'https://capawesome.io',
    toolbar: {
      backgroundColor: '#008080',
      color: '#FFFFFF',
      showNavigationButtons: true,
    },
  });
};

const close = async () => {
  await InAppBrowser.close();
};

const executeScript = async () => {
  const { result } = await InAppBrowser.executeScript({
    script: 'document.title',
  });
  return result;
};

const postMessage = async () => {
  await InAppBrowser.postMessage({
    data: { name: 'Capawesome' },
  });
};

const clearCache = async () => {
  await InAppBrowser.clearCache();
};

const clearSessionData = async () => {
  await InAppBrowser.clearSessionData();
};

const addListeners = async () => {
  await InAppBrowser.addListener('browserClosed', () => {
    console.log('Browser closed');
  });
  await InAppBrowser.addListener('browserPageLoaded', () => {
    console.log('Browser page loaded');
  });
  await InAppBrowser.addListener('browserPageNavigationCompleted', event => {
    console.log('Navigation completed', event.url);
  });
  await InAppBrowser.addListener('messageReceived', event => {
    console.log('Message received', event.data);
  });
};
```

## API

<docgen-index>

* [`clearCache()`](#clearcache)
* [`clearSessionData()`](#clearsessiondata)
* [`close()`](#close)
* [`executeScript(...)`](#executescript)
* [`getCookies(...)`](#getcookies)
* [`openInExternalBrowser(...)`](#openinexternalbrowser)
* [`openInSystemBrowser(...)`](#openinsystembrowser)
* [`openInWebView(...)`](#openinwebview)
* [`postMessage(...)`](#postmessage)
* [`show()`](#show)
* [`addListener('browserClosed', ...)`](#addlistenerbrowserclosed-)
* [`addListener('browserPageLoaded', ...)`](#addlistenerbrowserpageloaded-)
* [`addListener('browserPageNavigationCompleted', ...)`](#addlistenerbrowserpagenavigationcompleted-)
* [`addListener('messageReceived', ...)`](#addlistenermessagereceived-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clearCache()

```typescript
clearCache() => Promise<void>
```

Clear the cache of the web view.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### clearSessionData()

```typescript
clearSessionData() => Promise<void>
```

Clear the session data (cookies and web storage) of the web view.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### close()

```typescript
close() => Promise<void>
```

Close the currently open browser.

This closes browsers opened with `openInWebView(...)` or
`openInSystemBrowser(...)`.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### executeScript(...)

```typescript
executeScript(options: ExecuteScriptOptions) => Promise<ExecuteScriptResult>
```

Execute a JavaScript script in the currently open web view.

This method is only available for browsers opened with
`openInWebView(...)`.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#executescriptoptions">ExecuteScriptOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#executescriptresult">ExecuteScriptResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getCookies(...)

```typescript
getCookies(options: GetCookiesOptions) => Promise<GetCookiesResult>
```

Get the cookies for a specific URL.

On iOS, only cookies from the shared data store are returned. Cookies from
a web view opened with `dataStore: 'isolated'` are not included.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#getcookiesoptions">GetCookiesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcookiesresult">GetCookiesResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### openInExternalBrowser(...)

```typescript
openInExternalBrowser(options: OpenInExternalBrowserOptions) => Promise<void>
```

Open a URL in the default browser app of the device.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#openinexternalbrowseroptions">OpenInExternalBrowserOptions</a></code> |

**Since:** 0.1.0

--------------------


### openInSystemBrowser(...)

```typescript
openInSystemBrowser(options: OpenInSystemBrowserOptions) => Promise<void>
```

Open a URL in the system browser (Custom Tabs on Android,
`SFSafariViewController` on iOS).

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#openinsystembrowseroptions">OpenInSystemBrowserOptions</a></code> |

**Since:** 0.1.0

--------------------


### openInWebView(...)

```typescript
openInWebView(options: OpenInWebViewOptions) => Promise<void>
```

Open a URL in an embedded web view with a native toolbar.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#openinwebviewoptions">OpenInWebViewOptions</a></code> |

**Since:** 0.1.0

--------------------


### postMessage(...)

```typescript
postMessage(options: PostMessageOptions) => Promise<void>
```

Post a message to the currently open web view.

The web page receives the message by listening for the
`capacitorInAppBrowserMessage` window event. The message data is
available in the `detail` property of the event.

This method is only available for browsers opened with
`openInWebView(...)`.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#postmessageoptions">PostMessageOptions</a></code> |

**Since:** 0.1.0

--------------------


### show()

```typescript
show() => Promise<void>
```

Show the web view if it was opened with `visible: false`.

This method is only available for browsers opened with
`openInWebView(...)`.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### addListener('browserClosed', ...)

```typescript
addListener(eventName: 'browserClosed', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the browser is closed.

Only available on Android and iOS.

| Param              | Type                         |
| ------------------ | ---------------------------- |
| **`eventName`**    | <code>'browserClosed'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>   |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('browserPageLoaded', ...)

```typescript
addListener(eventName: 'browserPageLoaded', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the initial page of the browser has finished loading.

On Android, this event is only emitted for browsers opened with
`openInWebView(...)`.

Only available on Android and iOS.

| Param              | Type                             |
| ------------------ | -------------------------------- |
| **`eventName`**    | <code>'browserPageLoaded'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>       |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('browserPageNavigationCompleted', ...)

```typescript
addListener(eventName: 'browserPageNavigationCompleted', listenerFunc: (event: BrowserPageNavigationCompletedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a page navigation has been completed in the web view.

This event is only emitted for browsers opened with `openInWebView(...)`.

Only available on Android and iOS.

| Param              | Type                                                                                                                    |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'browserPageNavigationCompleted'</code>                                                                           |
| **`listenerFunc`** | <code>(event: <a href="#browserpagenavigationcompletedevent">BrowserPageNavigationCompletedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('messageReceived', ...)

```typescript
addListener(eventName: 'messageReceived', listenerFunc: (event: MessageReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the web page posts a message to the app using the injected
`window.CapacitorInAppBrowser.postMessage(...)` function.

This event is only emitted for browsers opened with `openInWebView(...)`.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'messageReceived'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#messagereceivedevent">MessageReceivedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### ExecuteScriptResult

| Prop         | Type                        | Description                                                                                                                                  | Since |
| ------------ | --------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`result`** | <code>string \| null</code> | The result of the script execution serialized as a JSON string. If the script does not return a JSON-serializable value, `null` is returned. | 0.1.0 |


#### ExecuteScriptOptions

| Prop         | Type                | Description                                     | Since |
| ------------ | ------------------- | ----------------------------------------------- | ----- |
| **`script`** | <code>string</code> | The JavaScript code to execute in the web view. | 0.1.0 |


#### GetCookiesResult

| Prop          | Type                                    | Description                                                 | Since |
| ------------- | --------------------------------------- | ----------------------------------------------------------- | ----- |
| **`cookies`** | <code>{ [key: string]: string; }</code> | The cookies for the URL as a map of cookie names to values. | 0.1.0 |


#### GetCookiesOptions

| Prop      | Type                | Description                     | Since |
| --------- | ------------------- | ------------------------------- | ----- |
| **`url`** | <code>string</code> | The URL to get the cookies for. | 0.1.0 |


#### OpenInExternalBrowserOptions

| Prop      | Type                | Description                              | Since |
| --------- | ------------------- | ---------------------------------------- | ----- |
| **`url`** | <code>string</code> | The URL to open in the external browser. | 0.1.0 |


#### OpenInSystemBrowserOptions

| Prop          | Type                                                                                            | Description                                                          | Since |
| ------------- | ----------------------------------------------------------------------------------------------- | -------------------------------------------------------------------- | ----- |
| **`android`** | <code><a href="#openinsystembrowserandroidoptions">OpenInSystemBrowserAndroidOptions</a></code> | Options that are only applied on Android. Only available on Android. | 0.1.0 |
| **`ios`**     | <code><a href="#openinsystembrowseriosoptions">OpenInSystemBrowserIosOptions</a></code>         | Options that are only applied on iOS. Only available on iOS.         | 0.1.0 |
| **`url`**     | <code>string</code>                                                                             | The URL to open in the system browser.                               | 0.1.0 |


#### OpenInSystemBrowserAndroidOptions

| Prop                      | Type                 | Description                                                                                                | Default            | Since |
| ------------------------- | -------------------- | ---------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`hideToolbarOnScroll`** | <code>boolean</code> | Whether or not the toolbar should be hidden when the user scrolls down and shown when the user scrolls up. | <code>false</code> | 0.1.0 |
| **`showTitle`**           | <code>boolean</code> | Whether or not the title of the web page should be shown in the toolbar.                                   | <code>false</code> | 0.1.0 |
| **`toolbarColor`**        | <code>string</code>  | The background color of the toolbar as a hex color code.                                                   |                    | 0.1.0 |


#### OpenInSystemBrowserIosOptions

| Prop                     | Type                                                              | Description                                                                           | Default             | Since |
| ------------------------ | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------- | ------------------- | ----- |
| **`barCollapsing`**      | <code>boolean</code>                                              | Whether or not the toolbar should collapse when the user scrolls down.                | <code>true</code>   | 0.1.0 |
| **`dismissButtonStyle`** | <code><a href="#dismissbuttonstyle">DismissButtonStyle</a></code> | The style of the dismiss button in the toolbar.                                       | <code>'done'</code> | 0.1.0 |
| **`readerMode`**         | <code>boolean</code>                                              | Whether or not the reader mode should be entered if it is available for the web page. | <code>false</code>  | 0.1.0 |
| **`toolbarColor`**       | <code>string</code>                                               | The background color of the toolbar as a hex color code.                              |                     | 0.1.0 |


#### OpenInWebViewOptions

| Prop                                  | Type                                                                                | Description                                                                                                                                                                                                                                                        | Default               | Since |
| ------------------------------------- | ----------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------------- | ----- |
| **`android`**                         | <code><a href="#openinwebviewandroidoptions">OpenInWebViewAndroidOptions</a></code> | Options that are only applied on Android. Only available on Android.                                                                                                                                                                                               |                       | 0.1.0 |
| **`dataStore`**                       | <code><a href="#webviewdatastore">WebViewDataStore</a></code>                       | The data store to use for the web view. On Android, this option is ignored. The web view always uses the app-global (`shared`) data store. Only available on iOS.                                                                                                  | <code>'shared'</code> | 0.1.0 |
| **`headers`**                         | <code>{ [key: string]: string; }</code>                                             | Additional HTTP headers to send with the initial request.                                                                                                                                                                                                          |                       | 0.1.0 |
| **`ios`**                             | <code><a href="#openinwebviewiosoptions">OpenInWebViewIosOptions</a></code>         | Options that are only applied on iOS. Only available on iOS.                                                                                                                                                                                                       |                       | 0.1.0 |
| **`mediaPlaybackRequiresUserAction`** | <code>boolean</code>                                                                | Whether or not media playback requires user action.                                                                                                                                                                                                                | <code>false</code>    | 0.1.0 |
| **`toolbar`**                         | <code><a href="#webviewtoolbaroptions">WebViewToolbarOptions</a></code>             | Options for the toolbar of the web view.                                                                                                                                                                                                                           |                       | 0.1.0 |
| **`url`**                             | <code>string</code>                                                                 | The URL to open in the web view.                                                                                                                                                                                                                                   |                       | 0.1.0 |
| **`userAgent`**                       | <code>string</code>                                                                 | The custom user agent to use for the web view.                                                                                                                                                                                                                     |                       | 0.1.0 |
| **`visible`**                         | <code>boolean</code>                                                                | Whether or not the web view is presented when opened. If `false`, the web view loads the URL in the background and stays hidden until `show()` is called. The `browserPageLoaded` event is still emitted and `close()` can be called while the web view is hidden. | <code>true</code>     | 0.1.0 |


#### OpenInWebViewAndroidOptions

| Prop                       | Type                 | Description                                                                                                                                                                         | Default            | Since |
| -------------------------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`allowZoom`**            | <code>boolean</code> | Whether or not the user can zoom the web page.                                                                                                                                      | <code>false</code> | 0.1.0 |
| **`hardwareBackButton`**   | <code>boolean</code> | Whether or not the hardware back button navigates back in the web view's history before closing the web view. If `false`, the hardware back button closes the web view immediately. | <code>true</code>  | 0.1.0 |
| **`pauseMediaWhenHidden`** | <code>boolean</code> | Whether or not media playback is paused when the app is hidden.                                                                                                                     | <code>true</code>  | 0.1.0 |


#### OpenInWebViewIosOptions

| Prop                                      | Type                 | Description                                                                                   | Default            | Since |
| ----------------------------------------- | -------------------- | --------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`allowsBackForwardNavigationGestures`** | <code>boolean</code> | Whether or not horizontal swipe gestures navigate back and forward in the web view's history. | <code>false</code> | 0.1.0 |
| **`overscroll`**                          | <code>boolean</code> | Whether or not the web view bounces when scrolled past the edge of the content.               | <code>true</code>  | 0.1.0 |


#### WebViewToolbarOptions

| Prop                        | Type                 | Description                                                                                            | Default              | Since |
| --------------------------- | -------------------- | ------------------------------------------------------------------------------------------------------ | -------------------- | ----- |
| **`backgroundColor`**       | <code>string</code>  | The background color of the toolbar as a hex color code.                                               |                      | 0.1.0 |
| **`closeButtonText`**       | <code>string</code>  | The text of the close button in the toolbar.                                                           | <code>'Close'</code> | 0.1.0 |
| **`color`**                 | <code>string</code>  | The text color of the toolbar as a hex color code.                                                     |                      | 0.1.0 |
| **`showNavigationButtons`** | <code>boolean</code> | Whether or not the back and forward navigation buttons should be shown in the toolbar.                 | <code>false</code>   | 0.1.0 |
| **`showUrl`**               | <code>boolean</code> | Whether or not the current URL should be displayed in the toolbar instead of the title.                | <code>false</code>   | 0.1.0 |
| **`title`**                 | <code>string</code>  | The fixed title to display in the toolbar. If not set, the title of the current web page is displayed. |                      | 0.1.0 |
| **`visible`**               | <code>boolean</code> | Whether or not the toolbar should be visible.                                                          | <code>true</code>    | 0.1.0 |


#### PostMessageOptions

| Prop       | Type                                     | Description                                                                   | Since |
| ---------- | ---------------------------------------- | ----------------------------------------------------------------------------- | ----- |
| **`data`** | <code>{ [key: string]: unknown; }</code> | The message data to post to the web view. Must be a JSON-serializable object. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BrowserPageNavigationCompletedEvent

| Prop      | Type                | Description                                | Since |
| --------- | ------------------- | ------------------------------------------ | ----- |
| **`url`** | <code>string</code> | The URL of the page that was navigated to. | 0.1.0 |


#### MessageReceivedEvent

| Prop       | Type                 | Description                              | Since |
| ---------- | -------------------- | ---------------------------------------- | ----- |
| **`data`** | <code>unknown</code> | The message data posted by the web page. | 0.1.0 |


### Type Aliases


#### DismissButtonStyle

The style of the dismiss button in the toolbar of the system browser.

- `cancel`: A button with the text "Cancel".
- `close`: A button with the text "Close".
- `done`: A button with the text "Done".

<code>'cancel' | 'close' | 'done'</code>


#### WebViewDataStore

The data store to use for the web view.

- `isolated`: The web view uses a non-persistent data store. Cookies and
  web storage are discarded when the web view is closed.
- `shared`: The web view uses the app-global data store which is shared
  with other web views.

<code>'isolated' | 'shared'</code>

</docgen-api>

## Messaging

The embedded web view injects a small message bridge into every web page. This allows you to exchange messages between your app and the web page in both directions.

### From the web page to the app

The web page can post a message to the app using the injected `window.CapacitorInAppBrowser.postMessage(...)` function:

```javascript
window.CapacitorInAppBrowser.postMessage({ name: 'Capawesome' });
```

The app receives the message via the `messageReceived` event.

### From the app to the web page

The app can post a message to the web page using the `postMessage(...)` method. The web page receives the message by listening for the `capacitorInAppBrowserMessage` window event:

```javascript
window.addEventListener('capacitorInAppBrowserMessage', event => {
  console.log('Message received', event.detail);
});
```

## Platform Behavior

The three browser modes behave differently on each platform. Keep the following differences in mind:

- **System browser**: Tracking the visited URLs is not possible by design. If you need the `browserPageNavigationCompleted` event, use the `openInWebView(...)` method instead. On Android, the `browserPageLoaded` event is not emitted for the system browser and the `browserClosed` event is emitted when the user returns to the app.
- **Embedded web view**: The web view always uses the app-global (`shared`) data store on Android. The `dataStore` option is only supported on iOS. On iOS, hiding the toolbar removes the close button, so the browser can then only be closed using the `close(...)` method.
- **External browser**: The browser is opened in a separate app. For this reason, no events are emitted and the `close(...)` method has no effect.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/in-app-browser/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/in-app-browser/LICENSE).
