const { channel } = require('bridge');

channel.on('echo', message => {
  channel.post('echo', `Echo from Node.js ${process.version}: ${message}`);
});
