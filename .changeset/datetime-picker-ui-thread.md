---
'@capawesome-team/capacitor-datetime-picker': patch
---

fix(android): show the picker dialog on the UI thread to prevent crashes (`CalledFromWrongThreadException`/`WindowLeaked`) on configuration changes
