---
name: test-live-update
description: Manually test the live-update plugin end to end with the example app, a Capawesome Cloud test app and Maestro on the iOS simulator and the Android emulator. Use after changing the plugin's download, verification, bundle or startup logic, or before releasing.
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
4. Deploy: `npx cap run ios --target <udid>` and `npx cap run android --target emulator-5554` from `example`.

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
| Signature ok | `publicKey` = key A | `sync` on a channel signed with key A, `reload` | new title, clean |
| Wrong public key | `publicKey` = key B | `sync` on the channel signed with key A | "Signature verification failed.", no bundle, clean |
| Invalid signature | `publicKey` set | `download` of the nested ZIP with `SIGNATURE=AAAA`, `ERROR="Signature verification failed."` | toast, clean |
| Rollback without `ready()` | `readyTimeout: 10000`, `autoBlockRolledBackBundles: true` | `sync`, poll, `reload`, do not tap Ready, `expect-title` with the default title and `TIMEOUT=30000` | back on the default bundle |
| No rollback with `ready()` | `readyTimeout: 10000` | `sync`, poll, `reload`, `ready`, wait longer than the timeout, `expect-title` with the new title | still on the new bundle, `inspect` agrees |
| Rolled-back bundle blocked | same deploy as the rollback row | after the rollback row, `ready` on the default bundle (the plugin records the block there), then `sync-keep-state`, poll for 30 s | bundle not installed again, "Get Blocked Bundles" lists it |
| No error on 404 | `serverDomain: "example.com"` | `sync` | no error toast, no bundle |
| Timeout error | `serverDomain: "10.255.255.1"`, `httpTimeout: 1000` | `sync` | "Request timed out." |
| Background auto update | `autoUpdateStrategy: "background"` | `relaunch`, poll without tapping Sync; then send the app to the background and back | bundle appears on start and on resume |
| Startup sweep | | `scripts/device.sh <platform> plant-leftover`, `relaunch` | downloads folder gone |

A 500 response from the update server needs a server you control that answers over HTTPS; cover it when such a server is available.

## Pitfalls

- Maestro often asserts before the WebView's accessibility tree refreshes after a reload or cold start. Retry once and check `serverBasePath` from `inspect` before blaming the plugin.
- `clearState` gives the iOS app a new container. Re-resolve the path after any flow that clears state; `scripts/device.sh` does.
- The example sends empty strings for blank inputs and the plugin verifies an empty checksum. Manual downloads that should succeed need the real checksum.
- Maestro logs env placeholders unsubstituted, for example `Assert that "${TITLE}" is visible... COMPLETED`. Filter on `COMPLETED` and `FAILED`, never on the expected value.
- Release branches may default `readyTimeout` to 10 seconds. There, run `ready` right after `reload` or the bundle rolls back before you look.

---

## Cleanup

Revert `capacitor.config.ts`, stop the local server, `adb reverse --remove tcp:8000`, and delete the Cloud app with `npx @capawesome/cli apps:delete --app-id <id> -y` unless the user wants to keep it.
