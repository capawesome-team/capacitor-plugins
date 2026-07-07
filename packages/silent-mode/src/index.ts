import { registerPlugin } from '@capacitor/core';

import type { SilentModePlugin } from './definitions';

const SilentMode = registerPlugin<SilentModePlugin>('SilentMode', {
  web: () => import('./web').then(m => new m.SilentModeWeb()),
});

export * from './definitions';
export { SilentMode };
