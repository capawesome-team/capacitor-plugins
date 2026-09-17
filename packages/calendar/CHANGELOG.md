# Changelog

## 0.1.2

### Patch Changes

- [`ab1c7e6d773db266e49c445aa8f2d5443d5d395c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ab1c7e6d773db266e49c445aa8f2d5443d5d395c) ([#583](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/583)): fix(android): parse the event durations written by the calendar provider, such as `P10800S`

- [`f25c9924d36ffa27260a4c6bf70b2ff973265cd0`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f25c9924d36ffa27260a4c6bf70b2ff973265cd0) ([#644](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/644)): fix(android): accept `0` as `from` or `to` in `getEvents(...)`

- [`0f0fa589b32a1ecab5b1c1a44beb9e3b7e286adb`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0f0fa589b32a1ecab5b1c1a44beb9e3b7e286adb) ([#641](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/641)): fix(android): reject a non-numeric `date` in `openCalendar(...)` instead of opening the current date

- [`0f0fa589b32a1ecab5b1c1a44beb9e3b7e286adb`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0f0fa589b32a1ecab5b1c1a44beb9e3b7e286adb) ([#641](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/641)): fix(android): apply a recurring-series split to the calendar instances so `getEvents(...)` no longer returns the truncated occurrences

- [`ab1c7e6d773db266e49c445aa8f2d5443d5d395c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ab1c7e6d773db266e49c445aa8f2d5443d5d395c) ([#583](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/583)): fix(android): create the continuing event before truncating the series when updating this and all future occurrences

- [`ab1c7e6d773db266e49c445aa8f2d5443d5d395c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ab1c7e6d773db266e49c445aa8f2d5443d5d395c) ([#583](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/583)): fix(android): reject a non-numeric `startDate`, `endDate` or `until` instead of storing it as a date in 1970

- [`ab1c7e6d773db266e49c445aa8f2d5443d5d395c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ab1c7e6d773db266e49c445aa8f2d5443d5d395c) ([#583](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/583)): fix(ios): limit the time range queried by `getEvents(...)` to 100 years and reject an invalid `from` or `to`

- [`ab1c7e6d773db266e49c445aa8f2d5443d5d395c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ab1c7e6d773db266e49c445aa8f2d5443d5d395c) ([#583](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/583)): fix(ios): reject a non-numeric `startDate`, `endDate` or `until` instead of ignoring it

- [`0f0fa589b32a1ecab5b1c1a44beb9e3b7e286adb`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/0f0fa589b32a1ecab5b1c1a44beb9e3b7e286adb) ([#641](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/641)): fix(ios): reject non-finite timestamps in `createEvent(...)` and `updateEventById(...)` and a non-numeric `date` in `openCalendar(...)`

- [`ab1c7e6d773db266e49c445aa8f2d5443d5d395c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ab1c7e6d773db266e49c445aa8f2d5443d5d395c) ([#583](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/583)): fix(ios): reject an invalid `date` in `openCalendar(...)` instead of crashing

## 0.1.1

### Patch Changes

- [`d31e7c2b33eeabfaf0635b84b39d069c549bf50c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d31e7c2b33eeabfaf0635b84b39d069c549bf50c) ([#555](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/555)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.1.0

### Minor Changes

- [`d730357dccdf6363e58ec478e4ed98bcdbc2565a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d730357dccdf6363e58ec478e4ed98bcdbc2565a) ([#491](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/491)): Initial release 🎉
