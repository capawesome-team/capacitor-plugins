---
'@capawesome/capacitor-posthog': minor
---

feat: add consent management and cookieless tracking support

- Add `optOut` setup option to start with capturing disabled
- Add `cookielessMode` setup option for anonymous tracking (web only)
- Add `optIn()`, `optOut()`, and `isOptedOut()` methods
