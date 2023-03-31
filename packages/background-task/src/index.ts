import { registerPlugin } from '@capacitor/core';

import type { BackgroundTaskPlugin } from './definitions';

const BackgroundTask = registerPlugin<BackgroundTaskPlugin>('BackgroundTask', {
  web: () => import('./web').then(m => new m.BackgroundTaskWeb()),
});

export * from './definitions';
export { BackgroundTask };
