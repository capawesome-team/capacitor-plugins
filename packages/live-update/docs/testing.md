# Testing

The following test cases should be covered when testing the plugin:

- [ ] Verify that the live update fails when the wrong public key is used.
- [ ] Verify that the live update fails when the checksum of the downloaded file does not match the expected checksum.
- [ ] Verify that a rollback is performed when the `ready()` method is not called within the `readyTimeout` period.
- [ ] Verify that no rollback is performed when the `ready()` method is called within the `readyTimeout` period.
- [ ] Verify that the live update is still applied after restarting the app.
- [ ] Verify that no error is thrown on internal server errors/not found responses.
- [ ] Verify that the correct timeout error is thrown when the server does not respond within the `timeout` period.
- [ ] Verify that the `background` auto update strategy performs updates in the background on app start and resumes.
- [ ] Verify that roll backed bundles are blocked from being applied again if `autoBlockRollBackedBundles` is set to true.
- [ ] Verify that the live update is applied after calling `reload()`.
- [ ] Verify that a ZIP bundle whose `index.html` sits in a subfolder is unwrapped and applied.
- [ ] Verify that a manifest update reuses the unchanged files of the current bundle and the resulting bundle is complete.
- [ ] Verify that the temporary download files are deleted after the bundle has been added (ZIP and manifest).
- [ ] Verify that the temporary download files are deleted when a download fails (network error, malformed URL, checksum mismatch, signature verification failure).
- [ ] Verify that leftovers of interrupted downloads are deleted on plugin load.

## Provider mode

In provider mode, an Ionic Portals host constructs `LiveUpdateIonicManager` directly and calls `sync()` on it. There is no Capacitor bridge and no JavaScript API. The manager keeps its own state per app ID and channel, so these cases need their own run:

- [ ] Verify that `sync()` installs the bundle of the channel and returns its metadata.
- [ ] Verify that a second `sync()` without a new bundle returns no update and no error.
- [ ] Verify that the latest app directory is restored after a restart without a sync.
- [ ] Verify that a newer bundle replaces the current one and the old bundle is deleted when the manager is constructed again.
- [ ] Verify that a native app update (new version code) forgets the latest app directory and deletes the old bundle.
- [ ] Verify that two concurrent syncs for the same app ID and channel download the bundle once and do not fail.
- [ ] Verify that `sync()` fails with an error when the device is offline.
- [ ] Verify that `sync()` returns no update and no error for an unknown app ID.
- [ ] Verify that a signed bundle installs with the correct `publicKey`, and that an unsigned bundle or a wrong `publicKey` fails with a signature error and keeps the current bundle.

Two apps cover these cases:

- `example-ionic-provider` is a plain Capacitor app with a small test plugin. Its form takes an app ID and a channel and offers the buttons `Is Provider Available?`, `Get Latest App Directory` and `Sync Manager`. Use it for every case except code signing, because the form has no `publicKey` field.
- The [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) repository is a real Portals host. It syncs each portal on launch, so a bundle shows up on the second launch. Use it for code signing (set `publicKey` in the manager configuration) and for the shared bundle of portals with the same app ID and channel.

Check the result on each platform:

- iOS: launch with `xcrun simctl launch --console-pty <udid> <bundle id>` to see the sync result lines. Bundles live in `Library/NoCloud/ionic_built_snapshots` of the app container, the pointers in the app's user defaults under `CapawesomeLiveUpdateIonicProvider`.
- Android: filter logcat with the tag `LiveUpdate`. Bundles live in `files/_capacitor_live_update_bundles`, the pointers in `shared_prefs/CapawesomeLiveUpdateIonicProvider.xml` (read both with `adb shell run-as <package>`).
- Take screenshots with Maestro to confirm which bundle a portal shows.

The `test-live-update` skill in `.claude/skills/test-live-update` runs these cases with Maestro on the iOS simulator and the Android emulator. Provider mode is Part P of the skill.
