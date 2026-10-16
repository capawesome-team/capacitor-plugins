---
'@capawesome-team/capacitor-file-opener': patch
---

fix(ios): reject `openFile(...)` while a file preview is already open or still being presented instead of stacking a second preview
