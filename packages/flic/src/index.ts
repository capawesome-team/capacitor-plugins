import { registerPlugin } from '@capacitor/core';

import type { FlicPlugin } from './definitions';

const Flic = registerPlugin<FlicPlugin>('Flic', {
  web: () => import('./web').then(m => new m.FlicWeb()),
});

export * from './definitions';
export { Flic };
