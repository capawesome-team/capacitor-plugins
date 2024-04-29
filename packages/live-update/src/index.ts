import { registerPlugin } from '@capacitor/core';

import type { LiveUpdatePlugin } from './definitions';

const LiveUpdate = registerPlugin<LiveUpdatePlugin>('LiveUpdate', {
  web: () => import('./web').then((m) => new m.LiveUpdateWeb()),
});

export * from './definitions';
export { LiveUpdate };
