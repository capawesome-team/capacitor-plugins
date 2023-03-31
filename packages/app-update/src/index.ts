import { registerPlugin } from '@capacitor/core';

import type { AppUpdatePlugin } from './definitions';

const AppUpdate = registerPlugin<AppUpdatePlugin>('AppUpdate', {
  web: () => import('./web').then(m => new m.AppUpdateWeb()),
});

export * from './definitions';
export { AppUpdate };
