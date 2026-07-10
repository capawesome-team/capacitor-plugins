import { registerPlugin } from '@capacitor/core';

import type { ProximitySensorPlugin } from './definitions';

const ProximitySensor = registerPlugin<ProximitySensorPlugin>(
  'ProximitySensor',
  {
    web: () => import('./web').then(m => new m.ProximitySensorWeb()),
  },
);

export * from './definitions';
export { ProximitySensor };
