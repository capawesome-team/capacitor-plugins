# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 1.x.x](#version-1xx)

## Version 1.x.x

### Error codes

On **Android**, `signIn(...)` now rejects with `SIGN_IN_CANCELED` only if the user canceled the sign-in flow. Google Play services also reports failures such as `[16] Account reauth failed.` (e.g. caused by a wrong SHA-1 fingerprint) as a cancellation. These failures now reject without an error code and keep the original error message. The error message of a user cancellation is now always `The user canceled the sign-in flow.`, as on iOS.
