import { registerPlugin } from '@capacitor/core';

import type { MapsLauncherPlugin } from './definitions';

const MapsLauncher = registerPlugin<MapsLauncherPlugin>('MapsLauncher', {
  web: () => import('./web').then(m => new m.MapsLauncherWeb()),
});

export * from './definitions';
export { MapsLauncher };
