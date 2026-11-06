import { registerPlugin } from '@capacitor/core';

import type { FerrostarNavigationPlugin } from './definitions';

const FerrostarNavigation = registerPlugin<FerrostarNavigationPlugin>(
  'FerrostarNavigation',
  {
    web: () => import('./web').then(m => new m.FerrostarNavigationWeb()),
  },
);

export * from './definitions';
export { FerrostarNavigation };
