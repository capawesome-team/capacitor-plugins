---
"@capawesome/capacitor-live-update": patch
---

fix(android,ios): keep the bundles of provider managers in their own folder and delete old ones once per app start. A bundle that a manager downloaded with an earlier version is downloaded again once. `getBundles(...)`, `getDownloadedBundles(...)`, `deleteBundle(...)` and `autoDeleteBundles` no longer cover the bundles of a manager, and a download of a manager no longer sends the `downloadBundleProgress` event.
