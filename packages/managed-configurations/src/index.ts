import { registerPlugin } from '@capacitor/core';

import type { ManagedConfigurationsPlugin } from './definitions';

const ManagedConfigurations = registerPlugin<ManagedConfigurationsPlugin>(
  'ManagedConfigurations',
  {
    web: () => import('./web').then(m => new m.ManagedConfigurationsWeb()),
  },
);

export * from './definitions';
export { ManagedConfigurations };
