---
'@capawesome/capacitor-file-picker': patch
---

fix(android): use the Android Photo Picker for `pickMedia(...)`, `pickImages(...)` and `pickVideos(...)` to avoid unreadable `file://` URIs on OEM devices
