import { registerPlugin } from '@capacitor/core';

import type { NavigationBarPlugin } from './definitions';

const NavigationBar = registerPlugin<NavigationBarPlugin>('NavigationBar', {
  web: () => import('./web').then(m => new m.NavigationBarWeb()),
});

export * from './definitions';
export { NavigationBar };
