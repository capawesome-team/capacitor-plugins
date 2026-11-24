---
"@capawesome/capacitor-live-update": patch
---

fix(android,ios): resolve `ready(...)` with `rollback: false` and the stored `previousBundleId` when the ready timeout ran out while the built-in bundle was in use
