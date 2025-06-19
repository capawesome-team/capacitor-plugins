import { registerPlugin } from '@capacitor/core';

import type { RealtimeKitPlugin } from './definitions';

const RealtimeKit = registerPlugin<RealtimeKitPlugin>('RealtimeKit', {
  web: () => import('./web').then(m => new m.RealtimeKitWeb()),
});

export * from './definitions';
export { RealtimeKit };
