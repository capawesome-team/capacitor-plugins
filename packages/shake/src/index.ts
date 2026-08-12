import { registerPlugin } from '@capacitor/core';

import type { ShakePlugin } from './definitions';

const Shake = registerPlugin<ShakePlugin>('Shake', {
  web: () => import('./web').then(m => new m.ShakeWeb()),
});

export * from './definitions';
export { Shake };
