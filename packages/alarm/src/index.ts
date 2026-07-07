import { registerPlugin } from '@capacitor/core';

import type { AlarmPlugin } from './definitions';

const Alarm = registerPlugin<AlarmPlugin>('Alarm', {
  web: () => import('./web').then(m => new m.AlarmWeb()),
});

export * from './definitions';
export { Alarm };
