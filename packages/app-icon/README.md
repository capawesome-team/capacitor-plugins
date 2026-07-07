# @capawesome/capacitor-app-icon

Capacitor plugin to change the app icon at runtime.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🎨 **Alternate icons**: Switch the home-screen icon between the icons your app declares.
- 🔍 **Current icon**: Read the name of the icon that is currently in use.
- 🔄 **Reset**: Restore the default icon at any time.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-icon` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-app-icon
npx cap sync
```

This plugin cannot add icons dynamically. Every icon you want to switch to must be **declared by the app beforehand**. The following sections describe how to declare alternate icons on each platform.

### Android

On Android, each alternate icon is declared as an [`<activity-alias>`](https://developer.android.com/guide/topics/manifest/activity-alias-element) that points at the launcher activity. The plugin enables the requested alias and disables the others.

Open your `AndroidManifest.xml` and remove the launcher `<intent-filter>` from your `.MainActivity`. Then add one `<activity-alias>` for the default icon and one for each alternate icon. Exactly one alias must be `android:enabled="true"` (the default icon); all others must be `android:enabled="false"`.

```xml
<!-- Remove the <intent-filter> from the MainActivity. -->
<activity
    android:name=".MainActivity"
    android:exported="true"
    ... />

<!-- The default icon (enabled). -->
<activity-alias
    android:name=".AppIconDefault"
    android:enabled="true"
    android:exported="true"
    android:icon="@mipmap/ic_launcher"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:targetActivity=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity-alias>

<!-- An alternate icon (disabled). -->
<activity-alias
    android:name=".AppIconChristmas"
    android:enabled="false"
    android:exported="true"
    android:icon="@mipmap/ic_launcher_christmas"
    android:roundIcon="@mipmap/ic_launcher_christmas_round"
    android:targetActivity=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity-alias>
```

The icon name passed to `setIcon(...)` is the alias name **without the leading dot** (e.g. `AppIconChristmas`). Add the referenced icon resources (e.g. `@mipmap/ic_launcher_christmas`) to your `res/mipmap-*` folders.

> [!NOTE]
> The behavior after a change depends on the launcher. Some launchers apply the new icon only after the app's task is closed, and a few kill the app despite the plugin requesting otherwise. Shortcuts that were pinned to a now-disabled alias may stop working.

### iOS

On iOS, alternate icons are declared under the `CFBundleIcons` key in your app's `Info.plist`. The icon image files must be bundled with the app (e.g. `AppIconChristmas@2x.png` and `AppIconChristmas@3x.png`).

```xml
<key>CFBundleIcons</key>
<dict>
    <key>CFBundleAlternateIcons</key>
    <dict>
        <key>AppIconChristmas</key>
        <dict>
            <key>CFBundleIconFiles</key>
            <array>
                <string>AppIconChristmas</string>
            </array>
            <key>UIPrerenderedIcon</key>
            <false/>
        </dict>
    </dict>
</dict>
```

The icon name passed to `setIcon(...)` is the key inside `CFBundleAlternateIcons` (e.g. `AppIconChristmas`). The default icon (declared via `CFBundlePrimaryIcon` or the asset catalog) is restored with `resetIcon(...)`.

> [!NOTE]
> The system shows a user-visible alert every time the icon changes, and the icon cannot be changed while the app is in the background.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppIcon } from '@capawesome/capacitor-app-icon';

const isAvailable = async () => {
  const { available } = await AppIcon.isAvailable();
  return available;
};

const getCurrentIcon = async () => {
  const { icon } = await AppIcon.getCurrentIcon();
  return icon;
};

const setIcon = async () => {
  await AppIcon.setIcon({ icon: 'AppIconChristmas' });
};

const resetIcon = async () => {
  await AppIcon.resetIcon();
};
```

## API

<docgen-index>

* [`getCurrentIcon()`](#getcurrenticon)
* [`isAvailable()`](#isavailable)
* [`resetIcon()`](#reseticon)
* [`setIcon(...)`](#seticon)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getCurrentIcon()

```typescript
getCurrentIcon() => Promise<GetCurrentIconResult>
```

Get the name of the icon that is currently in use.

Returns `null` if the default icon is in use.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getcurrenticonresult">GetCurrentIconResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if changing the app icon is supported on the current device.

On Android, this always resolves to `true`.
On iOS, this resolves to the value of `supportsAlternateIcons`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### resetIcon()

```typescript
resetIcon() => Promise<void>
```

Restore the default app icon.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### setIcon(...)

```typescript
setIcon(options: SetIconOptions) => Promise<void>
```

Change the app icon to the alternate icon with the given name.

The icon must be declared by the app beforehand. See the setup instructions
for [Android](https://capawesome.io/docs/plugins/app-icon/#android) and
[iOS](https://capawesome.io/docs/plugins/app-icon/#ios) for more information.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#seticonoptions">SetIconOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### GetCurrentIconResult

| Prop       | Type                        | Description                                                                                  | Since |
| ---------- | --------------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`icon`** | <code>string \| null</code> | The name of the icon that is currently in use. Returns `null` if the default icon is in use. | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                              | Since |
| --------------- | -------------------- | ------------------------------------------------------------------------ | ----- |
| **`available`** | <code>boolean</code> | Whether or not changing the app icon is supported on the current device. | 0.1.0 |


#### SetIconOptions

| Prop       | Type                | Description                                                                                                                                                                                         | Since |
| ---------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`icon`** | <code>string</code> | The name of the alternate icon to use. On Android, this is the name of the `&lt;activity-alias&gt;` (without the leading dot). On iOS, this is the key of the icon inside `CFBundleAlternateIcons`. | 0.1.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-icon/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-icon/LICENSE).
