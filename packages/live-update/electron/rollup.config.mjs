import typescript from '@rollup/plugin-typescript';

export default {
  input: 'electron/src/index.ts',
  output: {
    file: 'electron/dist/plugin.mjs',
    format: 'esm',
  },
  plugins: [typescript({ tsconfig: 'electron/tsconfig.json' })],
  external: [
    'electron',
    '@capacitor/core',
    '@capawesome/capacitor-electron/plugin',
    '@capawesome/electron-live-update/engine',
    /^node:/,
  ],
};
