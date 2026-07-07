import { registerPlugin } from '@capacitor/core';

import type { VolumePlugin } from './definitions';

const Volume = registerPlugin<VolumePlugin>('Volume', {
  web: () => import('./web').then(m => new m.VolumeWeb()),
});

export * from './definitions';
export { Volume };
