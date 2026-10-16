---
'@capawesome/capacitor-app-update': patch
---

fix(android): reject `completeFlexibleUpdate()` when the installation fails and keep emitting `onFlexibleUpdateStateChange` until a final install status
