import { registerPlugin } from '@capacitor/core';

import type { AndroidIntentLauncherPlugin } from './definitions';

const AndroidIntentLauncher = registerPlugin<AndroidIntentLauncherPlugin>(
  'AndroidIntentLauncher',
  {
    web: () => import('./web').then(m => new m.AndroidIntentLauncherWeb()),
  },
);

export * from './definitions';
export { AndroidIntentLauncher };
