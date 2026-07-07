import { registerPlugin } from '@capacitor/core';

import type { KeepAwakePlugin } from './definitions';

const KeepAwake = registerPlugin<KeepAwakePlugin>('KeepAwake', {
  web: () => import('./web').then(m => new m.KeepAwakeWeb()),
});

export * from './definitions';
export { KeepAwake };
