---
name: test-live-update
description: Manually test the live-update plugin end to end with the example app, a Capawesome Cloud test app and Maestro on the iOS simulator and the Android emulator. Part E covers Electron with the example's Electron app. Part P covers provider mode (Ionic Portals) with example-ionic-provider and the Portals demo. Use after changing the plugin's check, download, verification, bundle, startup or provider logic, or before releasing.
argument-hint: [paths to cover, e.g. "zip manifest signature"]
allowed-tools: Read, Glob, Grep, Bash, AskUserQuestion
---

# Test Live Update Skill

Drive the example app on both platforms through the plugin's download paths and verify the result on disk, not only on screen.

---

## Prerequisites

- `maestro`, `adb`, `xcrun` and `npx @capawesome/cli` (logged in) available.
- A booted iOS simulator. Ask the user to start the Android emulator from Android Studio; launching it from this session crashes.
- A Capawesome Cloud organization the user names. Never hard-code ids in this repo.

---

## Setup

1. Create a throwaway Cloud app: `cloud_create_app` (MCP) or `npx @capawesome/cli apps:create`. Note the app id. Channels are created on demand with `cloud_create_app_channel` or `apps:channels:create`.
2. In `packages/live-update`: `npm run build`. In `example`: set `plugins.LiveUpdate.appId` in `capacitor.config.ts` to the test app id. Every `npx cap run` bakes the config into the app, so set it before each deploy and revert it only right before committing.
3. Build marker bundles from the example: change the `<ion-title>` in `src/index.html` to `Capacitor Live Update vN`, `npm run build`, upload, then `git checkout src/index.html` and rebuild so the deployed app stays on v1.
   - ZIP: `npx @capawesome/cli apps:liveupdates:upload --app-id <id> --path dist --channel <channel> --artifact-type zip -y`
   - Manifest: `npx @capawesome/cli apps:liveupdates:generatemanifest --path dist` first, then upload with `--artifact-type manifest`.
   - Signed: generate a throwaway RSA key pair outside the repo, put the public key as a single-line PEM into `plugins.LiveUpdate.publicKey`, upload with `--private-key <path>`.
   - Nested `index.html`: the CLI rejects it. Zip a `www/` wrapper yourself, serve it with `python3 -m http.server 8000 --bind 127.0.0.1` from a directory that holds nothing else, and run `adb reverse tcp:8000 tcp:8000` so both devices reach `http://localhost:8000/<file>.zip`.
   - Custom properties: add `--custom-property key=value` to the upload, once per property.
   - Moved file: copy one asset of a manifest bundle to a new path inside the next bundle, so both paths hold the same content, then generate the manifest and upload.
   - Self-hosted: serve a ZIP from the local server and register its address with `npx @capawesome/cli apps:liveupdates:register --app-id <id> --url <url> --channel <channel>`.
   - Without a URL template: the server offers some bundles with a plain address only. Ask the user for an app and a channel that serves one, and confirm with a check that its answer has `legacyUrl` and no `urlTemplate`.
   - Manifest from a plain address: the plugin asks for the manifest and for each file as `<url>?href=<path>`, so serve such a bundle with a small server of your own that answers by the `href` value. The cases in `protocol-test-cases/manifest.json` list the manifests that must be refused.
   - Last release: check out the tag of the last release in a second worktree and build the example there, so both builds can be installed over each other.
4. Deploy: `npx cap run ios --target <udid>` and `npx cap run android --target emulator-5554` from `example`. If `cap run ios` builds but fails to open the simulator (`Simulator.app does not exist`), install the build yourself: `xcrun simctl install <udid> ios/DerivedData/<udid>/Build/Products/Debug-iphonesimulator/App.app`.

---

## Flows

Flows live in `flows/` next to this file and take parameters via `-e`. `URL` is required for `download`; the other parameters fall back to sensible defaults inside the flows. Never add `env:` defaults to a flow: Maestro lets them override the `-e` values.

```
maestro --device <id> test -e CHANNEL=default flows/sync.yaml
maestro --device <id> test -e TITLE="Capacitor Live Update v2" flows/reload.yaml
maestro --device <id> test -e TITLE="Capacitor Live Update v2" flows/kill-restart.yaml
maestro --device <id> test -e BUNDLE_ID=x -e URL=... -e CHECKSUM=... -e ERROR="Checksum mismatch." flows/download.yaml   # omit ERROR for a download that should succeed
maestro --device <id> test flows/relaunch.yaml
```

Sync completes without a visible signal: poll the bundles directory with `scripts/device.sh <ios|android> bundles` until the bundle id appears, then continue.

The flows above launch the app. A second set does not, so a log capture that is attached to the running app survives:

```
maestro --device <id> test -e CHANNEL=<channel> flows/enter-channel.yaml
maestro --device <id> test -e BUTTON="Sync" flows/tap.yaml                              # scrolls down to the button
maestro --device <id> test -e BUTTON="Fetch Latest Bundle" flows/tap-up.yaml            # scrolls up to the button
maestro --device <id> test -e BUTTON="Fetch Latest Bundle" -e TIMES=15 flows/tap-repeat.yaml
maestro --device <id> test -e BUNDLE_ID=x -e URL=... flows/download-manifest.yaml       # a manifest bundle from a plain address
```

After `Fetch Latest Bundle` the form holds the result, so `Download Bundle`, `Set Next Bundle` and `reload` finish the manual flow.

On Android, call the plugin through the WebView DevTools socket to get the exact result or error of a call:

```
node scripts/webview-eval.mjs 'Capacitor.Plugins.LiveUpdate.sync({ channel: "<channel>" })'
node scripts/webview-eval.mjs 'Capacitor.Plugins.LiveUpdate.fetchLatestBundle({ channel: "<channel>" })'
```

It prints `{"ok":true,"value":...}` or `{"ok":false,"code":...,"message":...}`. Set `ANDROID_SERIAL` for another device and `APP` for another app.

Logs: on Android `adb logcat -s LiveUpdate`. On iOS the plugin writes to stdout only, so start the app with `xcrun simctl launch --console-pty <udid> com.example.plugin > <file> 2>&1` in the background and use the flows that do not launch the app.

---

## Matrix

The checklist in `packages/live-update/docs/testing.md` is the source of truth for behavior; the rows below add the storage checks. Run each on both platforms and inspect with `scripts/device.sh <platform> inspect` after every step; `scripts/device.sh <platform> files` lists every installed bundle's files and title for the rows that assert bundle contents. "Clean" means the plugin-owned downloads folder is empty or absent and no stray files sit in the cache root.

Rows marked "config" need a change to `plugins.LiveUpdate` in the example config and a redeploy of both apps; group them to save deploys.

| Path | Config | Steps | Expect |
|------|--------|-------|--------|
| ZIP sync | | `sync` on a ZIP channel, poll, `reload` | bundle installed, clean, new title |
| Restart applies the update | | `sync`, poll, `force-stop`, `kill-restart` | new title on cold start, clean |
| Network failure | | `download` with an unreachable URL, `ERROR="Bundle could not be downloaded."` | toast, clean |
| Malformed URL (Android) | | `download` with `URL="not a valid url"`, `ERROR=".*scheme.*"` | toast, clean |
| Checksum mismatch | | `download` with a wrong `CHECKSUM` | "Checksum mismatch.", clean |
| Nested ZIP | | `download` with the local nested ZIP and its real `CHECKSUM` and no `ERROR`, poll, `set-next-bundle`, `reload` | `files` shows `index.html` at the bundle root, clean |
| Manifest, fresh | | `sync` on a manifest channel, poll, `reload` | `files` shows the manifest file next to `index.html`, clean |
| Manifest, incremental | | second manifest bundle on the same channel, `sync-keep-state`, poll, `reload` | `files` shows every asset of the new bundle, clean |
| Manifest, moved file | | a manifest bundle that holds one file of the current bundle under a new path, `sync-keep-state`, poll, `reload` | `files` shows the file under both paths with the same content, clean |
| Manual flow, ZIP | | `Fetch Latest Bundle` on a ZIP channel, then `Download Bundle`, `Set Next Bundle`, `reload` | `downloadUrl` is the download address of the API, new title, clean |
| Manual flow, manifest | | the same on a manifest channel | `files` shows every asset, clean |
| Up to date | | bundle with custom properties: `sync`, `reload`, then `Fetch Latest Bundle` and `sync` on the same channel | the result holds the bundle with its custom properties, `sync` returns no next bundle and downloads nothing, the log says "The app is up to date." |
| Without a URL template | | `sync` on a channel whose check answer has no `urlTemplate`, poll, `reload` | bundle installed, clean |
| Self-hosted bundle | | `sync` on a channel with a registered bundle address, poll, `reload` | bundle installed from that address, clean |
| Manifest refused | | `download-manifest` with the address of a manifest that must be refused | "The bundle manifest could not be read.", no bundle, clean |
| Bundle already exists | | `download` of the same bundle twice | second call rejects with the code `BUNDLE_ALREADY_EXISTS` |
| Bundle not found | | `Set Next Bundle` with an id that is not on the device | rejects with the code `BUNDLE_NOT_FOUND` |
| Sync in progress | | two `sync` calls at the same time | one rejects with the code `SYNC_IN_PROGRESS`, one bundle installed |
| Rate limit | | `tap-repeat` on `Fetch Latest Bundle` until the log shows an answer with status 429, then `sync` | no error, the log then says "No check was made because the device is rate limited." |
| Signature ok | `publicKey` = key A | `sync` on a channel signed with key A, `reload` | new title, clean |
| Wrong public key | `publicKey` = key B | `sync` on the channel signed with key A | "Signature verification failed.", no bundle, clean |
| Invalid signature | `publicKey` set | `download` of the nested ZIP with `SIGNATURE=AAAA`, `ERROR="Signature verification failed."` | toast, clean |
| Rollback without `ready()` | `readyTimeout: 10000`, `autoBlockRolledBackBundles: true` | `sync`, poll, `reload`, do not tap Ready, `expect-title` with the default title and `TIMEOUT=30000` | back on the default bundle |
| No rollback with `ready()` | `readyTimeout: 10000` | `sync`, poll, `reload`, `ready`, wait longer than the timeout, `expect-title` with the new title | still on the new bundle, `inspect` agrees |
| Failure reported | same deploy as the rollback row | after the rollback, one more `sync` on the same channel | the log shows a check whose body holds `failure` with the bundle id and the reason `readiness_timed_out`, and the answer has status 200 |
| Rolled-back bundle blocked | same deploy as the rollback row | after the rollback row, `ready` on the default bundle (the plugin records the block there), then `sync-keep-state`, poll for 30 s | bundle not installed again, "Get Blocked Bundles" lists it |
| Server error | `serverDomain: "example.com"` | `sync` | "The check failed with status 405.", no bundle |
| Timeout error | `serverDomain: "10.255.255.1"`, `httpTimeout: 1000` | `sync` | "Request timed out." |
| Background auto update | `autoUpdateStrategy: "background"` | `relaunch`, poll without tapping Sync; then send the app to the background and back | bundle appears on start and on resume |
| Startup sweep | | `scripts/device.sh <platform> plant-leftover`, `relaunch` | downloads folder gone |
| Update in place | | install the build of the last release, `sync`, `reload`, `ready`, set a channel and a custom id, note the device id. Then install the new build over it without clearing the data (`adb install -r`, `xcrun simctl install`) and start it | same device id, same title and current bundle, channel and custom id kept, a `sync` on a channel with a newer bundle installs it |

A 500 response from the update server needs a server you control that answers over HTTPS; cover it when such a server is available.

## Pitfalls

- Maestro often asserts before the WebView's accessibility tree refreshes after a reload or cold start. Retry once and check `serverBasePath` from `inspect` before blaming the plugin.
- The Android WebView exposes a form input by its HTML id but not its label, iOS the other way around. Tap inputs through `flows/_tap-input.yaml` with `INPUT_ID` and `INPUT_LABEL`, never with `tapOn: "<label>"`.
- `clearState` gives the iOS app a new container. Re-resolve the path after any flow that clears state; `scripts/device.sh` does.
- The example sends empty strings for blank inputs and the plugin verifies an empty checksum. Manual downloads that should succeed need the real checksum.
- Maestro logs env placeholders unsubstituted, for example `Assert that "${TITLE}" is visible... COMPLETED`. Filter on `COMPLETED` and `FAILED`, never on the expected value.
- Release branches may default `readyTimeout` to 10 seconds. There, run `ready` right after `reload` or the bundle rolls back before you look.
- Maestro needs 10–15 s to start a flow, longer than a 10 s `readyTimeout`. Put `reload` and `ready` into one flow; a separate `ready` flow arrives after the rollback.
- Maestro matches text partially, so `expect-title` with `Capacitor Live Update` also matches `Capacitor Live Update v2`. To prove a rollback, wait until the new title is gone (`extendedWaitUntil` with `notVisible`) instead of asserting the default title.
- The background auto update row needs `defaultChannel` set to your channel, because the plugin otherwise syncs the Cloud `default` channel. The plugin skips auto update for 15 minutes after the last check, so upload the next bundle, keep the app in the background for 15 minutes, and only then test the resume.
- Run the rate limit row last. After an answer with status 429 the plugin makes no check for the time the server asks for. That wait lives in memory, so a restart of the app ends it.
- A fresh install gives the app a new device id. Use it to start a row without the limits and the state of the row before.
- On iOS the log line with the result of a call is cut off after about 250 characters. Read long results from the form fields with `maestro --device <id> hierarchy`, or add a temporary print and remove it again.
---

## Part E: Electron

The Electron platform runs on the engine `@capawesome/electron-live-update`. It supports ZIP bundles only, and it has its own storage and its own rollback at the next start. So it needs its own run.

### Setup

1. Check that the engine on disk is the version that `packages/live-update/package.json` asks for: `node -p "require('@capawesome/electron-live-update/package.json').version"` in `packages/live-update`. If it is older, run `npm install` in the repo root first, or the run tests the wrong engine.
2. In `packages/live-update`: `npm run build`. In `example`: set `plugins.LiveUpdate.appId` in `capacitor.config.ts`, then `npm run build`, `npx cap sync @capawesome/capacitor-electron` and `npx tsc -p electron/tsconfig.json`. Options that only Electron should get go into `electron/capacitor.electron.config.ts`.
3. Start the app by hand with `npx electron electron` from `example`. The plugin and the engine log to the terminal with the prefix `[LiveUpdate]`.
4. To call plugin methods from a script, reuse the helpers of the end-to-end tests in `example/test/electron/support/harness.mjs`: `launch(userDataDir)` starts the app with its own data folder, `callPlugin(page, method, options)` calls a method in the renderer and returns its result, and `writeLiveUpdateConfig({ appId, ... })` sets the options without a new sync. Write such a script outside the repo.
5. The data lives in `capawesome-live-update` inside the user data folder of the app: `bundles/<bundleId>/`, `staging/` for running downloads and `state.json` for the current bundle, the next bundle, the blocked bundles, the device id and the channel.

### Matrix

| Case | Config | Steps | Expect |
|------|--------|-------|--------|
| ZIP sync | | `sync` on a ZIP channel, `reload` | new title, `bundles` holds the bundle, `staging` is empty |
| Restart applies the update | | `sync`, close the app, start it again | new title after the start |
| Custom properties | | `sync` on a channel whose bundle has custom properties | the result holds them |
| Rollback without `ready()` | `readyTimeout: 10000`, `autoBlockRolledBackBundles: true` | `sync`, `reload`, do not call `ready` | back on the default bundle after the timeout, the next `ready` returns `rollback: true` |
| Rollback at the next start | `readyTimeout: 10000` | `sync`, `reload`, close the app before the timeout, start it again | the default bundle is shown |
| No rollback with `ready()` | `readyTimeout: 10000` | `sync`, `reload`, `ready`, wait longer than the timeout | still on the new bundle |
| Rolled-back bundle blocked | same run as the first rollback row | `sync` again | bundle not installed again, `getBlockedBundles` lists it |
| Signature ok | `publicKey` = key A | `sync` on a channel signed with key A, `reload` | new title |
| Wrong public key | `publicKey` = key B | `sync` on the channel signed with key A | "Signature verification failed.", no bundle |
| Manifest refused | | `downloadBundle` with `artifactType: 'manifest'` | "The manifest artifact type is not supported." |
| Error codes | | `downloadBundle` of the same bundle twice, `setNextBundle` with an unknown id, two `sync` calls at once | `BUNDLE_ALREADY_EXISTS`, `BUNDLE_NOT_FOUND`, `SYNC_IN_PROGRESS` |
| Timeout error | `serverDomain: "10.255.255.1"`, `httpTimeout: 1000` | `sync` | "Request timed out." with the code `REQUEST_FAILED` |
| Update in place | | start the build of the last release with a data folder, `sync`, `reload`, `ready`, close it. Start the new build with the same data folder | same device id, same title and current bundle, a `sync` on a channel with a newer bundle installs it |

Run `npm run e2e:electron` in `example` as well. It covers the first sync and both rollback rows against a local mock server.

---

## Part P: Provider mode

Provider mode constructs `LiveUpdateIonicManager` directly, without a Capacitor bridge. `packages/live-update/docs/testing.md` lists the cases. Two apps cover them:

- `packages/live-update/example-ionic-provider`: a plain Capacitor app (bundle id `com.example.plugin`) whose form takes `appId` and `channel` and offers the buttons `Is Provider Available?`, `Get Latest App Directory` and `Sync Manager`. Every case except code signing runs here.
- `../ionic-portals-ecommerce-demo`: a real Portals host (iOS `io.ionic.portals.ecommerce`, Android `io.ionic.demo.ecommerce`). The managers sit in `ios/Portals Ecommerce/Portals Ecommerce/AppDelegate.swift` and `android/PortalsEcommerce/app/src/main/java/io/ionic/demo/ecommerce/EcommerceApp.java`. Each portal syncs on launch, so a new bundle shows on the second launch. Code signing and the shared bundle of portals with the same app ID and channel run here.

### Rules

- One chain per device. Part P edits shared web and native files, so two device agents must not run it at the same time: create a lock file in the scratchpad with your platform name before Part P, wait while the other platform holds it, remove it when done.
- Back up every file you change with `cp -p` to a folder outside the repos, restore it the same way at the end and verify with `cmp`. Never `git checkout` these files: they may carry uncommitted changes. Files to expect: `src/index.html` and `capacitor.config.ts` of the provider example, the demo's `AppDelegate.swift`, `EcommerceApp.java`, `app/build.gradle` and `project.pbxproj`, and the demo's `web/src` marker pages.
- Use your own Cloud channels, for example `provider-<platform>-<case>`. Never upload to `default`: the auto-mode permission check blocks it as a production deploy.
- The `example` and `example-ionic-provider` apps share the bundle id `com.example.plugin`. Uninstall one before installing the other.
- `pod install` after every plugin change in `example-ionic-provider/ios/App` and in the demo; the plugin's iOS sources come in as a pod, and new files are not seen otherwise. Delete `ios/App/build` before `npx cap sync ios`.
- The demo hosts need JDK 21: `export JAVA_HOME=/opt/homebrew/opt/openjdk@21`.

### Setup

1. Provider example: `npm run build && npx cap sync`, then iOS `cd ios/App && pod install` and `xcodebuild -workspace App.xcworkspace -scheme App -destination 'platform=iOS Simulator,id=<udid>' -derivedDataPath build CODE_SIGNING_ALLOWED=NO build` followed by `xcrun simctl install`; Android `npx cap run android --target emulator-5554` or gradle plus `adb install -r`.
2. Marker bundles: change the `<ion-title>` in `example-ionic-provider/src/index.html` to `Provider vN`, `npm run build`, upload `dist` with `--artifact-type zip` to your channel, restore `src/index.html`, rebuild. The bundle id in the sync result equals the Cloud build id.
3. Config variants (`serverDomain`, `httpTimeout`, `publicKey`) are not in the form. Add them under `plugins.LiveUpdate` in `example-ionic-provider/capacitor.config.ts`, `npx cap copy <platform>`, rebuild, restore.
4. Demo: `./scripts/seed-portals.sh` from the repo root seeds the web builds into both hosts. Point the managers at the test app and your channel, then iOS `pod install` and `xcodebuild -workspace "Portals Ecommerce.xcworkspace" -scheme "Portals Ecommerce" -configuration Debug -destination 'platform=iOS Simulator,id=<udid>' -derivedDataPath build CODE_SIGNING_ALLOWED=NO build` (the Podfile hook sets the deployment target), Android `./gradlew :app:assembleDebug`. For code signing add `"publicKey"` next to `"channel"` in both configuration maps (single-line PEM) and rebuild. Demo markers live in `web/src/pages/CheckoutPage/CheckoutPage.tsx` (`Checkout v2`), `web/src/pages/HelpPage/HelpPage.tsx` (`Get Assistance`) and `featured-component/src/components/NewFeatured.tsx` (iOS only).
5. Native update reset: bump `versionCode` in `android/app/build.gradle` or `CURRENT_PROJECT_VERSION` in `project.pbxproj`, reinstall without clearing data (`adb install -r`, `xcrun simctl install`), then restore the file.

### Flows

```
maestro --device <id> test -e BUTTON="Sync Manager" -e APPID=<app id> -e CHANNEL=<channel> -e RELAUNCH=yes -e TWICE=no flows/provider-action.yaml
scripts/example-log.sh <id>                                   # prints the entries of the example's log output
scripts/device.sh <ios|android> prefs                        # provider pointers, after force-stop on iOS
maestro --device <id> test -e OUT=<name> flows/demo-markers-<ios|android>.yaml   # help and checkout screenshots of the demo
APP=io.ionic.portals.ecommerce scripts/device.sh ios prefs   # same for the demo (Android: APP=io.ionic.demo.ecommerce)
```

`BUTTON` takes the visible button label. `TWICE=yes` taps it twice for the concurrent case. Leave `APPID` or `CHANNEL` empty to keep the form value. Launch the demo with `xcrun simctl launch --console-pty <udid> io.ionic.portals.ecommerce` (the sync result lines go to stdout, not to the unified log) or `adb logcat -s EcommerceApp LiveUpdate`. Screenshots land in the newest folder under `~/.maestro/tests/`.

Preference keys are `CapawesomeLiveUpdateIonicProvider.lastSyncedBundleId.<key>`, `...lastVersionCode.<key>` and `...supersededBundleIds.<key>` with `<key>` = `<appId>` or `<appId>.<channel>`. Bundles live in the same directories as in standalone mode, so `scripts/device.sh <platform> bundles` works for both apps.

### Matrix

Run each row on both platforms. "Log" is `scripts/example-log.sh`, "prefs" is `scripts/device.sh <platform> prefs`.

| Case | App | Steps | Expect |
|------|-----|-------|--------|
| Provider available | example | `Is Provider Available?` | log `available: true` |
| First sync | example | `Sync Manager` with your channel, then `Get Latest App Directory` | log shows the bundle id and a directory, `bundles` lists it, prefs hold `lastSyncedBundleId` and `lastVersionCode` |
| Sync unchanged | example | `Sync Manager` again | log `null`, no error |
| State restore | example | `force-stop`, `Get Latest App Directory` with `RELAUNCH=yes` | same directory, no sync |
| New bundle | example | upload `Provider v3`, `Sync Manager`, prefs, `force-stop`, `Get Latest App Directory` | new id; before the relaunch `supersededBundleIds` holds the old id, after it the old directory and the key are gone |
| Native update reset | example | bump the version code, reinstall, `Get Latest App Directory` | log `null`, prefs without `lastSyncedBundleId`, old bundle deleted |
| Concurrent sync | example | fresh channel bundle, `Sync Manager` with `TWICE=yes` | no error, one bundle directory, one download in the log |
| Offline | example | `serverDomain: "10.255.255.1"` and `httpTimeout: 1000` via config variant, `Sync Manager` | log shows an error, not `null` |
| Unknown app ID | example | `Sync Manager` with a random UUID as `APPID` | log shows "The check failed with status 404." |
| Without a URL template | example | `Sync Manager` with an app and a channel whose check answer has no `urlTemplate` | log shows the bundle id and a directory |
| Shared bundle | demo | fresh install, launch twice, `demo-markers` | help and checkout show the channel marker, one bundle directory per app ID |
| Signed, correct key | demo | channel with a bundle uploaded with `--private-key`, `publicKey` set, launch twice | marker of the signed bundle |
| Unsigned with key | demo | upload an unsigned bundle to the same channel, launch twice | log `Signature verification failed.`, marker unchanged |
| Wrong key | demo | rebuild with another public key, launch twice | same as the row above |

### Pitfalls

- iOS writes user defaults when the app terminates. Run `scripts/device.sh ios force-stop` before `prefs`, or the pointer looks stale.
- The Android plugin logs under the tag `LiveUpdate`, in provider mode and in the example app.
- The demo does not reload a portal after its sync. Count launches: launch one downloads, launch two shows the bundle. The iOS featured portal is created on the Shop tab at startup and needs the second launch as well.
- The demo's cart tab has no accessibility id on Android; `demo-markers-android.yaml` taps it by position.

---

## Cleanup

Revert `capacitor.config.ts`, stop the local server, `adb reverse --remove tcp:8000`, and delete the Cloud app with `npx @capawesome/cli apps:delete --app-id <id> -y` unless the user wants to keep it. After Part E, revert `electron/capacitor.electron.config.ts` and delete the data folders you created. After Part P, restore the backed-up files, remove the lock file and uninstall the provider example and the demo from both devices.
