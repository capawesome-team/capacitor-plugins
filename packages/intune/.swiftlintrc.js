const config = require('../../swiftlint.config.js');

module.exports = {
  ...config,
  excluded: [...config.excluded, `${process.cwd()}/ios/IntuneAppSDK`],
};
