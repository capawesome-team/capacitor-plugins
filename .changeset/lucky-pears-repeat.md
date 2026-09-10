---
'@capawesome/capacitor-crisp': patch
'@capawesome/capacitor-intercom': patch
---

fix: use the correct global name for the IIFE bundle. Both plugins exposed the bundle as `capacitorFacebookSignIn`, which collided with each other and with the Facebook Sign-In plugin when loaded via a script tag. The installation instructions now also list the required web SDK.
