# Changelog

## 8.0.1

### Patch Changes

- [`ae03cf50eb66ad15f91d922443cfb4e9c071ed8f`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ae03cf50eb66ad15f91d922443cfb4e9c071ed8f) ([#406](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/406)): fix(android): AGP 9.0 no longer supports `proguard-android.txt`

## 8.0.0

### Major Changes

- [`9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9b4b5a5f34bc17e87543f3ee24ec38c1a6df344d) ([#353](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/353)): feat!: update to Capacitor 8 (see `BREAKING.md`)

- [`24f72ad12dcce983a1eaa3af3287bbf0d4c8c688`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/24f72ad12dcce983a1eaa3af3287bbf0d4c8c688) ([#323](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/323)): refactor!: set default value of `limit` in `GetContactsOptions` to 20

## 7.7.0

### Minor Changes

- [`73eb0bffde285e903b10f5f81b325071aaa5c688`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/73eb0bffde285e903b10f5f81b325071aaa5c688) ([#289](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/289)): feat: add `openSettings()` method

## 7.6.1

### Patch Changes

- [`e882655143d6eb07fb7b574622c448c0c51d2eda`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/e882655143d6eb07fb7b574622c448c0c51d2eda): fix(ios): do not allow editing when calling the `displayContactById` method

## 7.6.0

### Minor Changes

- [`d8c8c4cdca6b177615563d770feded3df762083a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d8c8c4cdca6b177615563d770feded3df762083a) ([#201](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/201)): feat: add `isAvailable()` method

### Patch Changes

- [`02c56dc5274498d028ab5e8340c4d990f4da026d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/02c56dc5274498d028ab5e8340c4d990f4da026d) ([#199](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/199)): fix(android): some contact information was ignored on various devices when using the `displayCreateContact(...)` method

## 7.5.0

### Minor Changes

- [`4972c2f85d305836804eea23f2342e7bbd287aa5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/4972c2f85d305836804eea23f2342e7bbd287aa5) ([#194](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/194)): feat(android): automatically apply certain permission elements in app manifest

- [`d936294b79e89cae942444794d96481a237db6c3`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d936294b79e89cae942444794d96481a237db6c3) ([#179](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/179)): feat: add `label` and `type` properties to `UrlAddress`

### Patch Changes

- [`e304408abd4dba89d111475794483bea2ab68c62`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/e304408abd4dba89d111475794483bea2ab68c62) ([#167](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/167)): fix(ios): reject plugin call if privacy descriptions are missing

## 7.4.0

### Minor Changes

- [`eafaaf003a35bb7e8f603849f318448943172c6a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/eafaaf003a35bb7e8f603849f318448943172c6a) ([#154](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/154)): feat: add `displayContactById` method

- [`9ccf024b344ad86309df69a8f144853de85bb7ca`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/9ccf024b344ad86309df69a8f144853de85bb7ca) ([#157](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/157)): feat: add `updateContactById` method

- [`66b1fec8b9ac85dfad1abfa0cbd98d77f5a089b5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/66b1fec8b9ac85dfad1abfa0cbd98d77f5a089b5) ([#153](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/153)): feat: return contact id in `displayCreateContact`

- [`97cdca6999b56ff155a3ecbb183590559d7076a9`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/97cdca6999b56ff155a3ecbb183590559d7076a9) ([#147](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/147)): feat: support `pickContacts(...)` method on Android and iOS

- [`0b85186e3e3eeef98bb9858234729b66db220690`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0b85186e3e3eeef98bb9858234729b66db220690) ([#146](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/146)): feat(android): add support for accounts

- [`c2c670bfe147087b3e61e03cae4be116023839c5`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/c2c670bfe147087b3e61e03cae4be116023839c5) ([#160](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/160)): feat: add `displayUpdateContactById` method

- [`628d4e7aeabf966f7d3fecfece7b878b0bebfb8d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/628d4e7aeabf966f7d3fecfece7b878b0bebfb8d) ([#149](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/149)): feat: implement `countContacts` method

- [`d6256d790c5a78c5da486557380cf8e67051ba1c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d6256d790c5a78c5da486557380cf8e67051ba1c) ([#145](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/145)): feat(ios): support groups

- [`d9ceac9f52181a7a2102d662916bb0ae7283c4b7`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d9ceac9f52181a7a2102d662916bb0ae7283c4b7) ([#152](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/152)): feat: add `limit` and `offset` options to `getContacts(...)`

## 7.3.0

### Minor Changes

- [`edfb205e04f57f5a76959e6ce46a515aa5ea8404`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/edfb205e04f57f5a76959e6ce46a515aa5ea8404) ([#121](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/121)): feat: add `birthday` property to `Contact`

## 7.2.1

### Patch Changes

- [`92f34d2b5db51571544f8c3a8b20169f98dfe90a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/92f34d2b5db51571544f8c3a8b20169f98dfe90a): fix: throw error if `displayCreateContact(...)` is canceled

## 7.2.0

### Minor Changes

- [`d03690c844b942225bf4e8472c9f774ae3e50a63`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d03690c844b942225bf4e8472c9f774ae3e50a63) ([#110](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/110)): feat: add `displayCreateContact(...)` method

## 7.1.0

### Minor Changes

- [`50c2176a1489736dd04776aa78ce57ec067d6398`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/50c2176a1489736dd04776aa78ce57ec067d6398) ([#99](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/99)): feat: add `fields` option

## 7.0.1

### Patch Changes

- [`d0f9edc89803f71635a113cfa306db99834b3410`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d0f9edc89803f71635a113cfa306db99834b3410): fix(android): calls to the `createContact(...)` method were never resolved if no permission was granted

## 7.0.0

### Major Changes

- [`6bf9649f63280bb1a842f73f3e8c82b694443393`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/6bf9649f63280bb1a842f73f3e8c82b694443393) ([#68](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/68)): Initial release 🎉
