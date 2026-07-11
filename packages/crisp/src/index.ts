import { registerPlugin } from '@capacitor/core';

import type { CrispPlugin } from './definitions';

const Crisp = registerPlugin<CrispPlugin>('Crisp', {
  web: () => import('./web').then(m => new m.CrispWeb()),
});

export * from './definitions';
export { Crisp };
