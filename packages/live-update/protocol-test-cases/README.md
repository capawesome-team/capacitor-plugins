# Protocol test cases

This folder describes how a Live Update SDK talks to Capawesome Cloud, and it holds test cases for that protocol as JSON files. Every SDK runs the cases as unit tests, so all SDKs behave the same. The folder is not part of the published package.

## The check

The SDK asks for an update with `POST https://<serverDomain>/v1/live-updates/apps/<appId>/checks` and a JSON body.

| Field | Value |
|---|---|
| `appVersionCode`, `appVersionName` | The version of the native app |
| `bundleId` | The bundle that the device runs, if any |
| `channelName` | The channel of the device, if one is set |
| `customId` | The custom id of the device, if one is set |
| `deviceId` | The id of the device |
| `failure` | The last failed update that was not reported yet, as `bundleId` and `reason` |
| `osVersion` | The version of the operating system |
| `platform` | `android`, `ios` or `electron` |
| `pluginVersion` | The version of the SDK |
| `runtime` | `capacitor`, `cordova` or `electron` |

A field without a value is left out.

The `reason` of a `failure` is one of these values:

| `reason` | The update failed because |
|---|---|
| `app_crashed` | The app ended before it called `ready()`, and the SDK rolled back at the next start. It is sent on Electron only. |
| `bundle_invalid` | The bundle cannot be used, for example a zip or a manifest that cannot be read, or a bundle without `index.html` |
| `checksum_mismatched` | A file does not match its checksum |
| `download_failed` | A file could not be loaded |
| `readiness_timed_out` | The app did not call `ready()` in time, and the SDK rolled back |
| `signature_verification_failed` | The signature is missing or does not match, or the configured public key cannot be used |

A rollback has no report of its own. It is a failure with the reason `app_crashed` or `readiness_timed_out`.

A check that the server processed is answered with 200.

| `result` | `reason` | `bundle` |
|---|---|---|
| `available` | none | The bundle to load |
| `up_to_date` | none | The latest bundle of the channel, if there is one |
| `skipped` | Why no bundle is offered | none |

Only `available` offers an update. `fetchLatestBundle()` returns the bundle of an `up_to_date` answer too.

The SDK writes the reason to its log as the server sent it, for example `channel_paused`. It does not hand the reason to the app.

A bundle needs `id`, `artifactType` and an address in `url` or `legacyUrl`. `urlTemplate` is optional. A bundle with `urlTemplate` also needs `checksum`, and the template must hold `{checksum}`. `signature` and `customProperties` may be missing. No update is offered when a needed field is missing, when a bundle has neither `url` nor `legacyUrl`, when a template has no `{checksum}`, when the SDK does not know the `result` or the `artifactType`, or when the body is not a JSON object.

| Status | Meaning for the SDK |
|---|---|
| 200 | The answer of the check, as described above |
| 429 | No update. The SDK makes no further check for the number of seconds in `Retry-After`, which is a whole number that is not negative. Without such a number it waits 15 minutes. It never waits longer than 24 hours. The wait is kept in memory only, so a restart of the app always checks. |
| Every other status | The check fails with the error code `REQUEST_FAILED`. |

The failure that was sent with the check is removed after a 200 answer. After every other answer it stays and is sent with the next check.

## The headers

Every request of the SDK carries `User-Agent: capawesome-<sdk>/<version> (<platform>)`, for example `capawesome-capacitor-live-update/8.9.0 (ios)`.

| Part | Value |
|---|---|
| `<sdk>` | `<runtime>-live-update`, with `runtime` as in the check. So it is `capacitor-live-update`, `cordova-live-update` or `electron-live-update`, the name of the package without its scope. |
| `<version>` | The version of that package, as in `pluginVersion` of the check |
| `<platform>` | `android`, `ios` or `electron`, as in `platform` of the check |

So the Capacitor plugin on Electron sends `capawesome-capacitor-live-update/<version> (electron)`, and the Electron SDK on its own sends `capawesome-electron-live-update/<version> (electron)`.

A request of this protocol never carries the id of the device in a header. The check carries it in its body, and the download of a file from `urlTemplate` does not carry it at all. The download from the plain address of a bundle still carries it in a header, as before this protocol.

## The address of a bundle

A bundle of the answer carries its address in one of these ways:

| Bundle | Fields of `bundle` |
|---|---|
| Hosted by the app owner | `url` |
| Hosted by Capawesome Cloud, with a template | `legacyUrl` and `urlTemplate` |
| Hosted by Capawesome Cloud, without a template | `legacyUrl` |

The plain address of a bundle is `url`, or else `legacyUrl`. A bundle with neither is not a valid bundle. The plain address is never filled in, also when its text holds `{checksum}`.

With `urlTemplate`, the SDK loads every file from the template. The address of a file is the template with every `{checksum}` replaced by the SHA-256 checksum of that file. That holds for the zip file, the manifest and every file that the manifest lists. The replacement is done on the text. No other character of the template changes, also not in the query.

Without `urlTemplate`, the bundle is loaded from the plain address as before this protocol.

`checksum` and `signature` of the answer belong to the first file. That is the zip file, or the manifest of a bundle that is stored file by file.

The SDK sends `Accept-Encoding: identity` when it downloads the zip file of a bundle with `urlTemplate`. Otherwise the zip file may arrive compressed and without `Content-Length`, and the SDK could report the downloaded bytes but not the total size. Every other file is downloaded with the default encodings of the platform.

`fetchLatestBundle()` returns the plain address as `downloadUrl`. `downloadBundle()` loads a bundle from that address as before this protocol.

## The manifest

A bundle that is stored file by file has a manifest: a JSON list in which every item has `href`, `checksum` and `sizeInBytes`. An item without `sizeInBytes` counts as an item of size zero. A manifest cannot be used when it is not a JSON list or when an item has no `href` or no `checksum`. It also cannot be used when an `href` has a `..` part, because every `href` must name a file inside the bundle.

To install such a bundle, the SDK compares the new manifest with the manifest of the bundle that it runs. A file whose checksum is in the current manifest is copied from the path that holds this content there. If two paths hold it, the first one is used. Every other file is downloaded. If the current manifest is missing or cannot be used, every file is downloaded.

## Verification

With `urlTemplate`, the first file is verified against the checksum, and also against the signature if a public key is configured. Every downloaded file of a manifest is verified against its checksum in the manifest.

## The case files

| File | Cases |
|---|---|
| `check-request.json` | `input` holds the values, `expectedBody` the JSON body of the check |
| `check-response.json` | `statusCode`, `retryAfter` and `body` are the answer. `rawBody` replaces `body` when the text is not JSON. `expected` holds what the SDK reads from it. `expectedError` replaces `expected` when the check fails, and holds the error code. |
| `url-template.json` | `urlTemplate` and `checksum` give `expectedUrl` |
| `manifest.json` | `parse`: `text` gives `expectedItems`, or `null` for a manifest that cannot be used. `plan`: `currentItems` and `latestItems` give the files to `copy` and to `download`, in the order of the new manifest. |

Four fields of `expected` in `check-response.json` need a word:

- `result` is the `result` of a processed answer, and `null` for every other answer.
- `reason` is the `reason` of a skipped check as the server sent it, and `null` for every other answer.
- `url` of `bundle` is the plain address: `url`, or else `legacyUrl`.
- `retryAfterMs` is the wait after a 429.

A test compares JSON bodies as values, not as text.
