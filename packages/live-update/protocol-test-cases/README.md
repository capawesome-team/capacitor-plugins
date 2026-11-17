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
| `up_to_date` | none | none |
| `skipped` | Why no bundle is offered | none |

The SDK hands a reason to the app in upper case, for example `CHANNEL_PAUSED`. The upper case does not depend on the language of the device. A reason that the SDK does not know is passed on in the same way.

A bundle needs `id`, `artifactType` and `fileUrlTemplate`. It also needs `checksum` when the template holds `{checksum}`. `signature` and `customProperties` may be missing. No update is offered when a needed field is missing, when the SDK does not know the `result` or the `artifactType`, when the body is not a JSON object, or when the status is not 200.

| Status | Meaning for the SDK |
|---|---|
| 429 | No update, with the reason `RATE_LIMITED`. The SDK makes no further check for the number of seconds in `Retry-After`, which is a whole number that is not negative. Without such a number it waits 15 minutes. It never waits longer than 24 hours. The wait is kept in memory only, so a restart of the app always checks. |
| Other 4xx | No update |
| Every other status | No update |

The failure that was sent with the check is cleared by a 200 answer whose body is a JSON object, and by every 4xx answer except 429. After every other answer it stays and is sent with the next check.

## The headers

Every request of the SDK carries `User-Agent: capawesome-<sdk>/<version> (<platform>)`, for example `capawesome-capacitor-live-update/8.9.0 (ios)`.

| Part | Value |
|---|---|
| `<sdk>` | `<runtime>-live-update`, with `runtime` as in the check. So it is `capacitor-live-update`, `cordova-live-update` or `electron-live-update`, the name of the package without its scope. |
| `<version>` | The version of that package, as in `pluginVersion` of the check |
| `<platform>` | `android`, `ios` or `electron`, as in `platform` of the check |

So the Capacitor plugin on Electron sends `capawesome-capacitor-live-update/<version> (electron)`, and the Electron SDK on its own sends `capawesome-electron-live-update/<version> (electron)`.

A request of this protocol never carries the id of the device in a header. The check carries it in its body, and the download of a file does not carry it at all.

## The address of a file

A bundle of the answer carries `fileUrlTemplate`. The address of a file is the template with every `{checksum}` replaced by the SHA-256 checksum of that file. That holds for the zip file, the manifest and every file that the manifest lists. The replacement is done on the text. No other character of the template changes. A template without `{checksum}` is used as it is.

`checksum` and `signature` of the answer belong to the first file. That is the zip file, or the manifest of a bundle that is stored file by file.

## The `#` part of `downloadUrl`

`fetchLatestBundle()` returns `downloadUrl`, and the app may pass it to `downloadBundle()`. So that the download can be verified, the address carries the fields of the bundle after a `#`:

```
<address of the first file>#capawesome-live-update&artifactType=…&checksum=…&fileUrlTemplate=…&signature=…
```

- The marker `capawesome-live-update` comes first. The fields follow in alphabetical order.
- `signature` is left out when the bundle has none.
- In a value, every character except letters, digits, `-`, `.`, `_` and `~` is percent-encoded.
- An address whose template has no `{checksum}` gets no `#` part.
- The SDK cuts the `#` part off before it makes a request. It is never sent to a server.
- An address with the marker must carry a checksum. If not, the download fails with "Bundle does not contain a checksum."
- A field with an empty value counts as a missing field.
- A missing `fileUrlTemplate` means that the address itself is the template.
- A missing `artifactType` leaves the choice to the `artifactType` option of `downloadBundle()`.
- An address without the marker is loaded as before this protocol.

## The manifest

A bundle that is stored file by file has a manifest: a JSON list in which every item has `href`, `checksum` and `sizeInBytes`. An item without `sizeInBytes` counts as an item of size zero. A manifest cannot be used when it is not a JSON list or when an item has no `href` or no `checksum`.

To install such a bundle, the SDK compares the new manifest with the manifest of the bundle that it runs. A file whose checksum is in the current manifest is copied from the path that holds this content there. If two paths hold it, the first one is used. Every other file is downloaded. If the current manifest is missing or cannot be used, every file is downloaded.

## Verification

With the marker, the first file is verified against the checksum, and also against the signature if a public key is configured. Every downloaded file of a manifest is verified against its checksum in the manifest.

## The case files

| File | Cases |
|---|---|
| `check-request.json` | `input` holds the values, `expectedBody` the JSON body of the check |
| `check-response.json` | `statusCode`, `retryAfter` and `body` are the answer. `rawBody` replaces `body` when the text is not JSON. `expected` holds what the SDK reads from it. |
| `file-url.json` | `fileUrlTemplate` and `checksum` give `expectedUrl` |
| `download-url.json` | `build`: a `bundle` gives `expectedDownloadUrl`. `parse`: a `downloadUrl` gives `expected`, or fails with `expectedError`. |
| `manifest.json` | `parse`: `text` gives `expectedItems`, or `null` for a manifest that cannot be used. `plan`: `currentItems` and `latestItems` give the files to `copy` and to `download`, in the order of the new manifest. |

Three fields of `expected` in `check-response.json` need a word:

- `outcome` is the `result` of a processed answer, `skipped` after a 429, and `null` for every other answer.
- `retryAfterMs` is the wait after a 429.
- `clearsPendingFailure` says whether the failure that was sent is cleared.

A test compares JSON bodies as values, not as text.
