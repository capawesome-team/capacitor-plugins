---
'@capawesome/capacitor-file-picker': patch
---

fix(ios): `pickFiles` did not allow selecting any file when `types` contained wildcard (e.g. `image/*`) or unknown MIME types
