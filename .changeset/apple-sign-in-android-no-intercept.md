---
'@capawesome/capacitor-apple-sign-in': patch
---

fix(android): no longer intercept requests to `appleid.apple.com`, which dropped request bodies and cookies and broke the sign-in
