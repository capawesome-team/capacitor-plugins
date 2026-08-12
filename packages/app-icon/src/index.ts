import { registerPlugin } from '@capacitor/core';

import type { AppIconPlugin } from './definitions';

const AppIcon = registerPlugin<AppIconPlugin>('AppIcon', {
  web: () => import('./web').then(m => new m.AppIconWeb()),
});

export * from './definitions';
export { AppIcon };
