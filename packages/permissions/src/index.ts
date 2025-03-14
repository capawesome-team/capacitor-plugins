import { registerPlugin } from '@capacitor/core';

import type { PermissionsPlugin } from './definitions';

const Permissions = registerPlugin<PermissionsPlugin>('Permissions', {
  web: () => import('./web').then(m => new m.PermissionsWeb()),
});

export * from './definitions';
export { Permissions };
