# Changelog

## 5.1.1

### Patch Changes

- [`0abee4e`](https://github.com/capawesome-team/capacitor-plugins/commit/0abee4e2807fbd9a7c5f56b7adbbe4890a58e399) ([#73](https://github.com/capawesome-team/capacitor-plugins/pull/73)): fix(ios): copy picked files to unique directories

## 5.1.0

### Minor Changes

- [`4b3195d`](https://github.com/capawesome-team/capacitor-plugins/commit/4b3195d27f76c0ad0cae3306c086f611ee07bc69) ([#42](https://github.com/capawesome-team/capacitor-plugins/pull/42)): feat(ios): add `skipTranscoding` option

## 5.0.0

### Major Changes

- [`3442479`](https://github.com/capawesome-team/capacitor-plugins/commit/3442479e9927c8a9641b0f27c04268d2bdb189a4) ([#20](https://github.com/capawesome-team/capacitor-plugins/pull/20)): feat!: update to Capacitor 5

### Minor Changes

- [`145a35b`](https://github.com/capawesome-team/capacitor-plugins/commit/145a35b941567a874313ca4f79117da4c8ae6fde) ([#32](https://github.com/capawesome-team/capacitor-plugins/pull/32)): feat(web): add support for `pickImages(...)`, `pickMedia(...)` and `pickVideos(...)`

## [0.6.3](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.6.2...v0.6.3) (2023-03-31)

### Features

- **ios:** add `pickerDismissed` listener ([#93](https://github.com/capawesome-team/capacitor-file-picker/issues/93)) ([85c1d5f](https://github.com/capawesome-team/capacitor-file-picker/commit/85c1d5fba0b7a0f4c86b5db37825862f48563ee3))

## [0.6.2](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.6.1...v0.6.2) (2023-03-01)

### Features

- **ios:** save temporary file to `Directory​.Cache` ([#95](https://github.com/capawesome-team/capacitor-file-picker/issues/95)) ([b8cd1a0](https://github.com/capawesome-team/capacitor-file-picker/commit/b8cd1a042d697339fb5f1a95f5c53613a7c7dbcd))

## [0.6.1](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.6.0...v0.6.1) (2023-02-12)

### Bug Fixes

- **ios:** `convertHeicToJpeg` deleted the source file ([da86d97](https://github.com/capawesome-team/capacitor-file-picker/commit/da86d97c7dca9782387a4bbdc3fe7aa7f79e03e3))
- **ios:** picked files were renamed ([b55817b](https://github.com/capawesome-team/capacitor-file-picker/commit/b55817b7c025c3b086bea9d049052a351954c80e))

## [0.6.0](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.10...v0.6.0) (2023-02-10)

### ⚠ BREAKING CHANGES

- **ios:** `duration` is now returned in seconds and no longer in milliseconds.

### Features

- **ios:** add method `convertHeicToJpeg` ([#89](https://github.com/capawesome-team/capacitor-file-picker/issues/89)) ([aa34d9a](https://github.com/capawesome-team/capacitor-file-picker/commit/aa34d9acad2f414c6eb5297b4f60202e69f97cad))

### Bug Fixes

- **ios:** invalid values for `duration` ([#88](https://github.com/capawesome-team/capacitor-file-picker/issues/88)) ([0467a1d](https://github.com/capawesome-team/capacitor-file-picker/commit/0467a1d9918d8e6a07df7deba651992dda064458))

## [0.5.10](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.9...v0.5.10) (2023-02-07)

### Bug Fixes

- **android:** catch null pointer exception ([#87](https://github.com/capawesome-team/capacitor-file-picker/issues/87)) ([a4f1f56](https://github.com/capawesome-team/capacitor-file-picker/commit/a4f1f565fd70e8f5ea806fe90badd784f45683b0))

## [0.5.9](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.8...v0.5.9) (2023-01-17)

### Features

- add `modifiedAt` property ([#79](https://github.com/capawesome-team/capacitor-file-picker/issues/79)) ([f9ac248](https://github.com/capawesome-team/capacitor-file-picker/commit/f9ac2482a63e2c29855d5c094ed9ff9a62c3cbe4))

## [0.5.8](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.7...v0.5.8) (2023-01-15)

### Bug Fixes

- **ios:** return the correct height and width according to the orientation ([#75](https://github.com/capawesome-team/capacitor-file-picker/issues/75)) ([6c184c2](https://github.com/capawesome-team/capacitor-file-picker/commit/6c184c21532702c9efb5b657858eaf7ec79965c5))

## [0.5.7](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.6...v0.5.7) (2023-01-10)

### Bug Fixes

- **android:** select files from external storage ([#67](https://github.com/capawesome-team/capacitor-file-picker/issues/67)) ([06a3030](https://github.com/capawesome-team/capacitor-file-picker/commit/06a303069a683bebe71bed2ead5d58cf87f3f148))
- **ios:** `pickMedia`, `pickImages` and `pickVideos` do not return multiple files ([#68](https://github.com/capawesome-team/capacitor-file-picker/issues/68)) ([36e8321](https://github.com/capawesome-team/capacitor-file-picker/commit/36e8321b3bd93fcbe57d3463e40af848da3e4547))

## [0.5.6](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.5...v0.5.6) (2023-01-09)

### Bug Fixes

- **android:** `unreported exception IOException` ([#65](https://github.com/capawesome-team/capacitor-file-picker/issues/65)) ([91e2714](https://github.com/capawesome-team/capacitor-file-picker/commit/91e2714bca2092dbbf795acc08a383887c8c1f1b))

## [0.5.5](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.4...v0.5.5) (2023-01-06)

### Bug Fixes

- **ios:** save files in a temporary directory ([ad3f8bb](https://github.com/capawesome-team/capacitor-file-picker/commit/ad3f8bbf3a314b331a62752de58dddd1978df06f))

## [0.5.4](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.3...v0.5.4) (2023-01-06)

### Bug Fixes

- wrong data type was returned for `duration`, `height` and `width` ([7a42c13](https://github.com/capawesome-team/capacitor-file-picker/commit/7a42c135275dd5a86b719e596fe083470b913552))

## [0.5.3](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.2...v0.5.3) (2023-01-06)

### Features

- add methods `pickImages`, `pickMedia` and `pickVideos` ([#62](https://github.com/capawesome-team/capacitor-file-picker/issues/62)) ([b7c1381](https://github.com/capawesome-team/capacitor-file-picker/commit/b7c13819053c60a02f3fbbb8a8f1dda14b23d2f7))
- return `duration`, `width` and `height` properties ([#63](https://github.com/capawesome-team/capacitor-file-picker/issues/63)) ([cb7e4ef](https://github.com/capawesome-team/capacitor-file-picker/commit/cb7e4ef8d060d3544ffdca4b342e981a6d21f69d))

## [0.5.2](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.1...v0.5.2) (2022-10-11)

### Bug Fixes

- **android:** files of type `text/csv` are not selectable ([#51](https://github.com/capawesome-team/capacitor-file-picker/issues/51)) ([25ed4d9](https://github.com/capawesome-team/capacitor-file-picker/commit/25ed4d9ee0df6451392f7ede125ec0677e38d75b))

## [0.5.1](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.5.0...v0.5.1) (2022-10-09)

### Bug Fixes

- **android:** base64 encoded data contains line breaks ([#49](https://github.com/capawesome-team/capacitor-file-picker/issues/49)) ([953d92a](https://github.com/capawesome-team/capacitor-file-picker/commit/953d92a956c2f13d05ba6f39f39277894544a54b))

## [0.5.0](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.4.0...v0.5.0) (2022-09-04)

### ⚠ BREAKING CHANGES

- set default value of `readData` to `false` (#43)

- set default value of `readData` to `false` ([#43](https://github.com/capawesome-team/capacitor-file-picker/issues/43)) ([6100f00](https://github.com/capawesome-team/capacitor-file-picker/commit/6100f0046328c6e395cf120d2ce4fca3ce586355))

## [0.4.0](https://github.com/capawesome-team/capacitor-file-picker/compare/v0.3.1...v0.4.0) (2022-08-04)

### ⚠ BREAKING CHANGES

- This plugin was renamed to `@capawesome/capacitor-file-picker`. Please install the new npm package and run `npx cap sync`.
- This plugin now only supports Capacitor 4.

### Features

- update to Capacitor 4 ([#39](https://github.com/capawesome-team/capacitor-file-picker/issues/39)) ([ca8caf5](https://github.com/capawesome-team/capacitor-file-picker/commit/ca8caf53fcac91fe74b6e25e2044eb0aaf40c42c))

- rename to `@capawesome/capacitor-file-picker` ([#40](https://github.com/capawesome-team/capacitor-file-picker/issues/40)) ([d7d0bbe](https://github.com/capawesome-team/capacitor-file-picker/commit/d7d0bbe515a54169c3ef07c3832febd4773e2204))

## [0.3.1](https://github.com/robingenz/capacitor-file-picker/compare/v0.3.0...v0.3.1) (2022-07-13)

### Features

- **web:** provide `Blob` instance ([#37](https://github.com/robingenz/capacitor-file-picker/issues/37)) ([e84bcf9](https://github.com/robingenz/capacitor-file-picker/commit/e84bcf9a3c9f6cb309d18c36e1fcf918f8ec822a))

### Bug Fixes

- **android:** map `text/csv` to `text/comma-separated-values` ([#36](https://github.com/robingenz/capacitor-file-picker/issues/36)) ([87b82f6](https://github.com/robingenz/capacitor-file-picker/commit/87b82f67518189041c7ea237eb6717f94c67d320))

## [0.3.0](https://github.com/robingenz/capacitor-file-picker/compare/v0.2.3...v0.3.0) (2022-02-23)

### ⚠ BREAKING CHANGES

- Property `data` of `File` is now optional

### Features

- add `readData` option ([#23](https://github.com/robingenz/capacitor-file-picker/issues/23)) ([ed29f2e](https://github.com/robingenz/capacitor-file-picker/commit/ed29f2e61b70a95a09e2d9e237b7005b97a2d38b))

## [0.2.3](https://github.com/robingenz/capacitor-file-picker/compare/v0.2.2...v0.2.3) (2022-01-26)

### Bug Fixes

- inline source code in esm map files ([41952b2](https://github.com/robingenz/capacitor-file-picker/commit/41952b26a6c82cca3f7061fe630e0413a781fff1))

## [0.2.2](https://github.com/robingenz/capacitor-file-picker/compare/v0.2.1...v0.2.2) (2021-11-22)

### Bug Fixes

- **android:** add fallback for file name ([#20](https://github.com/robingenz/capacitor-file-picker/issues/20)) ([96fc1e6](https://github.com/robingenz/capacitor-file-picker/commit/96fc1e679588b251e7b5151e924f800f182ca500))

## [0.2.1](https://github.com/robingenz/capacitor-file-picker/compare/v0.2.0...v0.2.1) (2021-10-31)

### Bug Fixes

- **android:** local files are not shown with empty `types` array ([#18](https://github.com/robingenz/capacitor-file-picker/issues/18)) ([fc2c6aa](https://github.com/robingenz/capacitor-file-picker/commit/fc2c6aac005aa8170346fb008f47fa769f515eb1))
- **web:** increase timeout to detect `cancel` ([#19](https://github.com/robingenz/capacitor-file-picker/issues/19)) ([d03c001](https://github.com/robingenz/capacitor-file-picker/commit/d03c001af8825f7a5aa50ba1d08324340d476abe))

## [0.2.0](https://github.com/robingenz/capacitor-file-picker/compare/v0.1.3...v0.2.0) (2021-10-02)

### ⚠ BREAKING CHANGES

- Method `pickFile` is replaced by `pickFiles`

### Features

- support for multiple file selection ([#14](https://github.com/robingenz/capacitor-file-picker/issues/14)) ([ea7f055](https://github.com/robingenz/capacitor-file-picker/commit/ea7f055d6a359629fc2476f50334f9d27431ffed))

## [0.1.3](https://github.com/robingenz/capacitor-file-picker/compare/v0.1.2...v0.1.3) (2021-10-01)

### Bug Fixes

- local files are not shown ([0115e2b](https://github.com/robingenz/capacitor-file-picker/commit/0115e2bd9da125bf3ad5ea67450b2d5d5b05b7e7))

## [0.1.2](https://github.com/robingenz/capacitor-file-picker/compare/v0.1.1...v0.1.2) (2021-08-29)

### Features

- implement `types` option ([#10](https://github.com/robingenz/capacitor-file-picker/issues/10)) ([176dced](https://github.com/robingenz/capacitor-file-picker/commit/176dcedb00e6008da4d6ac357b1c216194bc9217))

### Bug Fixes

- **ios:** document picker swipe down not handled ([#11](https://github.com/robingenz/capacitor-file-picker/issues/11)) ([ed13fee](https://github.com/robingenz/capacitor-file-picker/commit/ed13feeae0590d75c76e0e0ec979ea297733cfcf))

## [0.1.1](https://github.com/robingenz/capacitor-file-picker/compare/v0.1.0...v0.1.1) (2021-08-29)

### Features

- add `web` support ([#9](https://github.com/robingenz/capacitor-file-picker/issues/9)) ([23e5bf6](https://github.com/robingenz/capacitor-file-picker/commit/23e5bf6a065cb33dd1547a4f9b04666c32029c06))

## 0.1.0 (2021-08-15)

Initial release 🎉
