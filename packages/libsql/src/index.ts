import { registerPlugin } from '@capacitor/core';

import type { LibsqlPlugin } from './definitions';

const Libsql = registerPlugin<LibsqlPlugin>('Libsql', {
  web: () => import('./web').then(m => new m.LibsqlWeb()),
});

export * from './definitions';
export { Libsql };
