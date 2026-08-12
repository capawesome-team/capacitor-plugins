import { registerPlugin } from '@capacitor/core';

import type { PrivacyScreenPlugin } from './definitions';

const PrivacyScreen = registerPlugin<PrivacyScreenPlugin>('PrivacyScreen', {
  web: () => import('./web').then(m => new m.PrivacyScreenWeb()),
});

export * from './definitions';
export { PrivacyScreen };
