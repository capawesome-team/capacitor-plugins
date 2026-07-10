import { registerPlugin } from '@capacitor/core';

import type { AppIntegrityPlugin } from './definitions';

const AppIntegrity = registerPlugin<AppIntegrityPlugin>('AppIntegrity', {
  web: () => import('./web').then(m => new m.AppIntegrityWeb()),
});

export * from './definitions';
export { AppIntegrity };
