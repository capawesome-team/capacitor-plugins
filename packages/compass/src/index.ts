import { registerPlugin } from '@capacitor/core';

import type { CompassPlugin } from './definitions';

const Compass = registerPlugin<CompassPlugin>('Compass', {
  web: () => import('./web').then(m => new m.CompassWeb()),
});

export * from './definitions';
export { Compass };
