import { registerPlugin } from '@capacitor/core';

import type { SingularPlugin } from './definitions';

const Singular = registerPlugin<SingularPlugin>('Singular', {
  web: () => import('./web').then(m => new m.SingularWeb()),
});

export * from './definitions';
export { Singular };
