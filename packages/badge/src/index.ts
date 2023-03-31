import { registerPlugin } from '@capacitor/core';

import type { BadgePlugin } from './definitions';

const Badge = registerPlugin<BadgePlugin>('Badge', {
  web: () => import('./web').then(m => new m.BadgeWeb()),
});

export * from './definitions';
export { Badge };
