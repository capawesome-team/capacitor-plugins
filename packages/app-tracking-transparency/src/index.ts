import { registerPlugin } from '@capacitor/core';

import type { AppTrackingTransparencyPlugin } from './definitions';

const AppTrackingTransparency = registerPlugin<AppTrackingTransparencyPlugin>(
  'AppTrackingTransparency',
  {
    web: () => import('./web').then(m => new m.AppTrackingTransparencyWeb()),
  },
);

export * from './definitions';
export { AppTrackingTransparency };
