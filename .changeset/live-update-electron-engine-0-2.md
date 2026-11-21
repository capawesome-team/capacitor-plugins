---
'@capawesome/capacitor-live-update': minor
---

feat(electron): return `customProperties` from `sync(...)` and use the same error codes as on Android and iOS. A failed request now has the code `REQUEST_FAILED`. Errors other than `BUNDLE_ALREADY_EXISTS`, `BUNDLE_NOT_FOUND`, `REQUEST_FAILED` and `SYNC_IN_PROGRESS` no longer have a code.
