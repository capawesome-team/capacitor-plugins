import { registerPlugin } from '@capacitor/core';

import type { PixlivePlugin } from './definitions';

const Pixlive = registerPlugin<PixlivePlugin>('Pixlive', {
  web: () => import('./web').then(m => new m.PixliveWeb()),
});

export * from './definitions';
export { Pixlive };
