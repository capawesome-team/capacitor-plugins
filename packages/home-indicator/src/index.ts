import { registerPlugin } from '@capacitor/core';

import type { HomeIndicatorPlugin } from './definitions';

const HomeIndicator = registerPlugin<HomeIndicatorPlugin>('HomeIndicator', {
  web: () => import('./web').then(m => new m.HomeIndicatorWeb()),
});

export * from './definitions';
export { HomeIndicator };
