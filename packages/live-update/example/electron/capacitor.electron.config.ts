import { defineConfig } from '@capawesome/capacitor-electron/config';

export default defineConfig({
  window: {
    width: 1200,
    height: 800,
  },
  // A splash screen is shown automatically while the app boots when a splash
  // file exists (`assets/splash.html` or `assets/splash.png`). Uncomment to
  // customize it:
  // splashScreen: {
  //   path: 'assets/splash.html',
  //   width: 400,
  //   height: 300,
  //   backgroundColor: '#ffffff',
  //   minimumDurationMs: 0,
  // },
  // Per-plugin config overrides. Merged over the `plugins` section of the
  // Capacitor config (this section wins per key) — the Electron equivalent of
  // Android string resources / iOS Info.plist plugin settings. Being
  // TypeScript, values can be computed, e.g. a live-update channel derived
  // from the app version (`import packageJson from './package.json'`):
  // plugins: {
  //   LiveUpdate: {
  //     defaultChannel: `production-${packageJson.version}`,
  //   },
  // },
});
