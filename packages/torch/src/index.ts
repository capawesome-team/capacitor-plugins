import { registerPlugin } from '@capacitor/core';

import type { TorchPlugin } from './definitions';

const Torch = registerPlugin<TorchPlugin>('Torch', {
  web: () => import('./web').then(m => new m.TorchWeb()),
});

export * from './definitions';
export { Torch };
