const config = require('../../swiftlint.config.js');

// The DeclaredAgeRange framework uses long symbol names that this plugin mirrors.
module.exports = {
  ...config,
  identifier_name: {
    ...config.identifier_name,
    max_length: 50,
  },
  type_name: {
    ...config.type_name,
    max_length: 50,
  },
};
