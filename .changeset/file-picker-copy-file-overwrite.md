---
'@capawesome/capacitor-file-picker': patch
---

fix(android): reject `copyFile(...)` with `File already exists` only when the destination exists and `overwrite` is `false`
