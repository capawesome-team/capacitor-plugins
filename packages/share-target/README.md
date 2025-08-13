# @capawesome-team/capacitor-share-target

Capacitor plugin to receive content such as text, links, and files from other apps.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for receiving content from other apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📝 **Multi-content types**: Handle text, URLs, images, videos, and files.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Demo

A working example can be found [here](https://github.com/capawesome-team/capacitor-share-target-demo).

| Android                                                                                                                      | iOS                                                                                                                      |
| ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| <img src="https://github.com/user-attachments/assets/9a35add2-dd32-4b50-8c5f-18bd79df0bde" width="324" alt="Android Demo" /> | <img src="https://github.com/user-attachments/assets/9171f4ae-d4db-49d0-ab0e-9827fb3358b7" width="266" alt="iOS Demo" /> |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-share-target
npx cap sync
```

### Android

#### Intent Filters

To enable your app to receive shared content from other apps, you need to add intent filters to your main activity in `AndroidManifest.xml` (usually `android/app/src/main/AndroidManifest.xml`).

For example, to handle text, URLs, and other text-based content, add the following intent filter:

```xml
<intent-filter>
    <action android:name="android.intent.action.SEND" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="text/plain" />
</intent-filter>
```

To handle a single image, add the following intent filter:

```xml
<intent-filter>
    <action android:name="android.intent.action.SEND" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="image/*" />
</intent-filter>
```

To handle multiple images, add the following intent filter:

```xml
<intent-filter>
    <action android:name="android.intent.action.SEND_MULTIPLE" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="image/*" />
</intent-filter>
```

You can also add additional intent filters for other MIME types as needed, such as `application/pdf` for PDF files or `video/*` for videos.

**Attention**: Make sure to add these intent filters inside the `<activity>` tag of your main activity, typically `MainActivity`. Also, make sure the activity has the `android:exported="true"` attribute set, which allows other apps to send intents to it. The `android:launchMode="singleTask"` attribute is recommended to prevent multiple instances of your app from being created when receiving shared content.

Here's an example of how your `AndroidManifest.xml` might look like:

```xml
<activity
    android:name=".MainActivity"
    android:exported="true"
    android:launchMode="singleTask">
    <intent-filter>
        <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="text/plain" />
    </intent-filter>
    <intent-filter>
        <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="image/*" />
    </intent-filter>
    <intent-filter>
        <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="video/*" />
    </intent-filter>
</activity>
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

On iOS, it's not possible to receive shared content directly in the main app. Instead, you need to create a share extension that can handle the shared content and then communicate it back to your main app. This involves setting up a URL scheme and configuring the share extension to handle the shared content. The communication between the share extension and the main app is done using URL schemes, which allows the share extension to open the main app with the shared content as parameters in the URL.

#### URL Scheme

To enable your app to be opened by the share extension, you need to set up a URL scheme in your iOS app. This is done by adding a URL type in your app's `Info.plist` file. Add the following code to your `Info.plist` file:

```xml
<key>CFBundleURLTypes</key>
<array>
    <dict>
        <key>CFBundleURLSchemes</key>
        <array>
            <string>example</string>
        </array>
    </dict>
</array>
```

**Attention**: Replace `example` with your desired URL scheme. This will allow your app to be opened with URLs like `example://?text=Hello%20World`.

#### Share Extension

To enable your app to receive shared content from other apps on iOS, you need to create a share extension. First, you need to add a new target to your Xcode project:

1. Open your Xcode project.
2. Go to `File` > `New` > `Target...`.
3. Select `Share Extension` from the list of templates.
4. Name your extension `AppShare` and click `Finish`.

This will create a new target in your Xcode project with the necessary files for a share extension. There is one file called `MainInterface.storyboard` that you can delete, as we will not need it.

After this, you need to configure the share extension to handle the shared content. Open the `Info.plist` file of your share extension and replace its content with the following:

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
	<key>NSExtension</key>
	<dict>
		<key>NSExtensionAttributes</key>
		<dict>
			<key>NSExtensionActivationRule</key>
			<dict>
				<key>NSExtensionActivationDictionaryVersion</key>
				<integer>2</integer>
				<key>NSExtensionActivationSupportsWebURLWithMaxCount</key>
				<integer>1</integer>
				<key>NSExtensionActivationSupportsText</key>
				<true/>
				<key>NSExtensionActivationSupportsWebURLWithMaxCount</key>
				<integer>10</integer>
				<key>NSExtensionActivationSupportsFileWithMaxCount</key>
				<integer>10</integer>
				<key>NSExtensionActivationSupportsImageWithMaxCount</key>
				<integer>10</integer>
				<key>NSExtensionActivationSupportsMovieWithMaxCount</key>
				<integer>10</integer>
				<key>NSExtensionActivationSupportsWebPageWithMaxCount</key>
				<integer>1</integer>
			</dict>
		</dict>
		<key>NSExtensionPrincipalClass</key>
		<string>AppShare.ShareViewController</string>
		<key>NSExtensionPointIdentifier</key>
		<string>com.apple.share-services</string>
	</dict>
</dict>
</plist>
```

Feel free to adjust the `NSExtensionActivationRule` keys to match the types of content you want to support. The example above supports text, web URLs, files, images, and movies.

Next, you need to create a new Swift file in your share extension target. Name it `ShareViewController.swift` and replace its content with the following code:

```swift
import MobileCoreServices
import Social
import UIKit
import UniformTypeIdentifiers

class ShareViewController: UIViewController {

    private let appGroupIdentifier = "PLACEHOLDER_APP_GROUP_IDENTIFIER"
    private let urlScheme = "PLACEHOLDER_URL_SCHEME"

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.extensionContext!.completeRequest(returningItems: [], completionHandler: nil)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        Task {
            processSharedContent()
        }
    }

    private func openURL(_ url: URL) {
        var responder: UIResponder? = self
        while responder != nil {
            if let application = responder as? UIApplication {
                application.open(url, options: [:], completionHandler: nil)
                return
            }
            responder = responder?.next
        }
    }

    private func copyFileToSharedContainer(_ url: URL) -> String? {
        guard let containerURL = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier: appGroupIdentifier) else {
            return nil
        }

        let fileName = url.lastPathComponent
        let destinationURL = containerURL.appendingPathComponent(fileName)

        do {
            // Remove file if it already exists
            if FileManager.default.fileExists(atPath: destinationURL.path) {
                try FileManager.default.removeItem(at: destinationURL)
            }

            // Copy file to shared container
            try FileManager.default.copyItem(at: url, to: destinationURL)
            return destinationURL.absoluteString
        } catch {
            print("Error copying file to shared container: \(error)")
            return nil
        }
    }

    private func sendData(with textValues: [String], fileValues: [String], title: String) {
        var urlComps = URLComponents(string: "\(urlScheme)://?")!
        var queryItems: [URLQueryItem] = []

        if !title.isEmpty {
            queryItems.append(URLQueryItem(name: "title", value: title))
        }

        for text in textValues {
            if !text.isEmpty {
                queryItems.append(URLQueryItem(name: "text", value: text))
            }
        }

        for file in fileValues {
            if !file.isEmpty {
                queryItems.append(URLQueryItem(name: "file", value: file))
            }
        }

        urlComps.queryItems = queryItems.isEmpty ? nil : queryItems
        openURL(urlComps.url!)
    }

    private func processSharedContent() {
        guard let extensionContext = extensionContext else {
            self.extensionContext?.completeRequest(returningItems: [], completionHandler: nil)
            return
        }

        guard let item = extensionContext.inputItems.first as? NSExtensionItem else {
            self.extensionContext?.completeRequest(returningItems: [], completionHandler: nil)
            return
        }

        guard let attachments = item.attachments else {
            self.extensionContext?.completeRequest(returningItems: [], completionHandler: nil)
            return
        }

        var textValues: [String] = []
        var fileValues: [String] = []
        let title = item.attributedTitle?.string ?? item.attributedContentText?.string ?? ""
        let dispatchGroup = DispatchGroup()

        for attachment in attachments {
            // Handle images
            if attachment.hasItemConformingToTypeIdentifier(UTType.image.identifier) {
                dispatchGroup.enter()
                attachment.loadItem(forTypeIdentifier: UTType.image.identifier, options: nil) { [weak self] (item, _) in
                    defer { dispatchGroup.leave() }

                    if let url = item as? URL {
                        if let sharedPath = self?.copyFileToSharedContainer(url) {
                            fileValues.append(sharedPath)
                        }
                    } else if let image = item as? UIImage {
                        if let data = image.pngData() {
                            let base64String = data.base64EncodedString()
                            fileValues.append("data:image/png;base64,\(base64String)")
                        }
                    }
                }
            }
            // Handle movies
            if attachment.hasItemConformingToTypeIdentifier(UTType.movie.identifier) {
                dispatchGroup.enter()
                attachment.loadItem(forTypeIdentifier: UTType.movie.identifier, options: nil) { [weak self] (item, _) in
                    defer { dispatchGroup.leave() }

                    if let url = item as? URL {
                        if let sharedPath = self?.copyFileToSharedContainer(url) {
                            fileValues.append(sharedPath)
                        }
                    }
                }
            }
            // Handle plain text content
            if attachment.hasItemConformingToTypeIdentifier(UTType.plainText.identifier) {
                dispatchGroup.enter()
                attachment.loadItem(forTypeIdentifier: UTType.plainText.identifier, options: nil) { (item, _) in
                    if let text = item as? String {
                        textValues.append(text)
                    }
                    dispatchGroup.leave()
                }
            }
            // Handle URL content
            if attachment.hasItemConformingToTypeIdentifier(UTType.url.identifier) {
                dispatchGroup.enter()
                attachment.loadItem(forTypeIdentifier: UTType.url.identifier, options: nil) { (item, _) in
                    if let url = item as? URL {
                        textValues.append(url.absoluteString)
                    }
                    dispatchGroup.leave()
                }
            }
        }

        dispatchGroup.notify(queue: .main) { [weak self] in
            self?.sendData(with: textValues, fileValues: fileValues, title: title)
        }
    }
}
```

**Attention**: Replace `<YOUR_URL_SCHEME>` with the URL scheme you defined in your main app's `Info.plist` file.

Finally, you need to modify the `AppDelegate.swift` file of your main app target to handle the URLs opened by the share extension. Add the missing import and the following code to the `application(_:open:options:)` method:

```diff
+ import CapawesomeTeamCapacitorShareTarget

func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
+    // Handle share target URLs
+    let _ = ShareTargetPlugin.handleOpenUrl(url)
+
    // Called when the app was launched with a url. Feel free to add additional processing here,
    // but if you want the App API to support tracking app url opens, make sure to keep this call
    return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
}
```

#### Capabilities

If you want to receive files (e.g., images, videos) from other apps, you need to enable the "App Groups" capability for both your main app and the share extension. This allows both targets to share files in a common container. To do this, follow these steps:

1. Open your Xcode project.
2. Select your app target.
3. Go to the "Signing & Capabilities" tab.
4. Click the "+" button to add a new capability.
5. Select "App Groups" from the list.
6. Create a new app group (must begin with `group.`) and note its identifier (e.g., `group.com.example.app`).
7. Repeat the same steps for your share extension target, ensuring that both targets use the same app group identifier.

If you don't want to receive files, you can skip this step.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { ShareTarget } from '@capawesome-team/capacitor-share-target';

const addListener = async () => {
    await ShareTarget.addListener('shareReceived', (event) => {
        console.log('Share received:', event);
    });
};
```

## API

<docgen-index>

* [`addListener('shareReceived', ...)`](#addlistenersharereceived-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addListener('shareReceived', ...)

```typescript
addListener(eventName: 'shareReceived', listenerFunc: (event: ShareReceivedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a share is received.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'shareReceived'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#sharereceivedevent">ShareReceivedEvent</a>) =&gt; void</code> |

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


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ShareReceivedEvent

| Prop        | Type                  | Description                                                                                                                                                                                                               | Since |
| ----------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`title`** | <code>string</code>   | The title of the shared content.                                                                                                                                                                                          | 0.1.0 |
| **`texts`** | <code>string[]</code> | The text content that was shared.                                                                                                                                                                                         | 0.1.0 |
| **`files`** | <code>string[]</code> | The files that were shared. On **Android** and **iOS**, this will contain the file paths or base64 encoded data URLs of the shared files. On **Web**, this will contain the base64 encoded data URLs of the shared files. | 0.1.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/share-target/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/share-target/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/share-target/LICENSE).
