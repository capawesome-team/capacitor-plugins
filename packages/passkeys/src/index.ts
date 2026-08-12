import { registerPlugin } from '@capacitor/core';

import type { PasskeysPlugin } from './definitions';

const Passkeys = registerPlugin<PasskeysPlugin>('Passkeys', {
  web: () => import('./web').then(m => new m.PasskeysWeb()),
});

export * from './definitions';
export { Passkeys };
