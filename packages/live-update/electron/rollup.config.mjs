export default {
  input: 'electron/dist/esm/electron/src/index.js',
  output: {
    file: 'electron/dist/plugin.mjs',
    format: 'esm',
  },
  external: [
    'electron',
    '@capacitor/core',
    '@capawesome/electron-live-update/engine',
    /^node:/,
  ],
};
