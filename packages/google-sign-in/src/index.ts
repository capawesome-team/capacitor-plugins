import { registerPlugin } from '@capacitor/core';

import type { GoogleSignInPlugin } from './definitions';

const GoogleSignIn = registerPlugin<GoogleSignInPlugin>('GoogleSignIn', {
  web: () => import('./web').then(m => new m.GoogleSignInWeb()),
});

export * from './definitions';
export { GoogleSignIn };
