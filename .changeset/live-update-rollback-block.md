---
"@capawesome/capacitor-live-update": patch
---

fix(android,ios): block a rolled back bundle at the rollback, so that a `sync(...)` before the next `ready(...)` call does not apply it again
