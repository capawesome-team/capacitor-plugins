import { registerPlugin } from '@capacitor/core';

import type { PhoneDialerPlugin } from './definitions';

const PhoneDialer = registerPlugin<PhoneDialerPlugin>('PhoneDialer', {
  web: () => import('./web').then(m => new m.PhoneDialerWeb()),
});

export * from './definitions';
export { PhoneDialer };
