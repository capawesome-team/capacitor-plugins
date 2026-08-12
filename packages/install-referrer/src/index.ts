import { registerPlugin } from '@capacitor/core';

import type { InstallReferrerPlugin } from './definitions';

const InstallReferrer = registerPlugin<InstallReferrerPlugin>(
  'InstallReferrer',
  {
    web: () => import('./web').then(m => new m.InstallReferrerWeb()),
  },
);

export * from './definitions';
export { InstallReferrer };
