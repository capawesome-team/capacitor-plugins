import { registerPlugin } from '@capacitor/core';

import type { TabBarPlugin } from './definitions';

const TabBar = registerPlugin<TabBarPlugin>('TabBar', {
  web: () => import('./web').then(m => new m.TabBarWeb()),
});

export * from './definitions';
export { TabBar };
