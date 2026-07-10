import { registerPlugin } from '@capacitor/core';

import type { RootDetectionPlugin } from './definitions';

const RootDetection = registerPlugin<RootDetectionPlugin>('RootDetection', {
  web: () => import('./web').then(m => new m.RootDetectionWeb()),
});

export * from './definitions';
export { RootDetection };
