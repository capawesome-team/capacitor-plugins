import { registerPlugin } from '@capacitor/core';

import type { AgeSignalsPlugin } from './definitions';

const AgeSignals = registerPlugin<AgeSignalsPlugin>('AgeSignals', {
  web: () => import('./web').then(m => new m.AgeSignalsWeb()),
});

export * from './definitions';
export { AgeSignals };
