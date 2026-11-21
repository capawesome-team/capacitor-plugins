---
'@capawesome/capacitor-live-update': minor
---

feat(electron): add the error code `REQUEST_FAILED` and return `customProperties` from `sync(...)`. The codes `DOWNLOAD_FAILED` and `HTTP_TIMEOUT` are now `REQUEST_FAILED`. All other codes except `BUNDLE_ALREADY_EXISTS`, `BUNDLE_NOT_FOUND` and `SYNC_IN_PROGRESS` are removed.
