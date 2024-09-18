import { registerPlugin } from '@capacitor/core';

import type { VapiPlugin } from './definitions';

const Vapi = registerPlugin<VapiPlugin>('Vapi', {
  web: () => import('./web').then(m => new m.VapiWeb()),
});

export * from './definitions';
export { Vapi };
