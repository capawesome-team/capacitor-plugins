import { registerPlugin } from '@capacitor/core';

import type { GyroscopePlugin } from './definitions';

const Gyroscope = registerPlugin<GyroscopePlugin>('Gyroscope', {
  web: () => import('./web').then(m => new m.GyroscopeWeb()),
});

export * from './definitions';
export { Gyroscope };
