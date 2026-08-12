import { registerPlugin } from '@capacitor/core';

import type { FacebookSignInPlugin } from './definitions';

const FacebookSignIn = registerPlugin<FacebookSignInPlugin>('FacebookSignIn', {
  web: () => import('./web').then(m => new m.FacebookSignInWeb()),
});

export * from './definitions';
export { FacebookSignIn };
