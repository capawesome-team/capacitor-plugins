import { registerPlugin } from '@capacitor/core';

import type { SettingsLauncherPlugin } from './definitions';

const SettingsLauncher = registerPlugin<SettingsLauncherPlugin>(
  'SettingsLauncher',
  {
    web: () => import('./web').then(m => new m.SettingsLauncherWeb()),
  },
);

export * from './definitions';
export { SettingsLauncher };
