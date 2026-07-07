import { registerPlugin } from '@capacitor/core';

import type { BatteryPlugin } from './definitions';

const Battery = registerPlugin<BatteryPlugin>('Battery', {
  web: () => import('./web').then(m => new m.BatteryWeb()),
});

export * from './definitions';
export { Battery };
