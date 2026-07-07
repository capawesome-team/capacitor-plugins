import { registerPlugin } from '@capacitor/core';

import type { DeviceInfoPlugin } from './definitions';

const DeviceInfo = registerPlugin<DeviceInfoPlugin>('DeviceInfo', {
  web: () => import('./web').then(m => new m.DeviceInfoWeb()),
});

export * from './definitions';
export { DeviceInfo };
