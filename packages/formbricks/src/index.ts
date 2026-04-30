import { registerPlugin } from '@capacitor/core';

import type { FormbricksPlugin } from './definitions';

const Formbricks = registerPlugin<FormbricksPlugin>('Formbricks', {
  web: () => import('./web').then(m => new m.FormbricksWeb()),
});

export * from './definitions';
export { Formbricks };
