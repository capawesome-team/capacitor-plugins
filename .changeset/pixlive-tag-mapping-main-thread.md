---
"@capawesome/capacitor-pixlive": patch
---

fix: wait for the context DB to be loaded before `synchronize(...)`, `synchronizeWithToursAndContexts(...)`, `updateTagMapping(...)` and `enableContextsWithTags(...)`, and run them on the main thread on iOS (and `enableContextsWithTags(...)` on the UI thread on Android), so `updateTagMapping(...)` resolves on iOS and language filtering works
