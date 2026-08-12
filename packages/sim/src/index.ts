import { registerPlugin } from '@capacitor/core';

import type { SimPlugin } from './definitions';

const Sim = registerPlugin<SimPlugin>('Sim', {
  web: () => import('./web').then(m => new m.SimWeb()),
});

export * from './definitions';
export { Sim };
