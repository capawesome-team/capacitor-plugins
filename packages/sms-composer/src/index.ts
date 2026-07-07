import { registerPlugin } from '@capacitor/core';

import type { SmsComposerPlugin } from './definitions';

const SmsComposer = registerPlugin<SmsComposerPlugin>('SmsComposer', {
  web: () => import('./web').then(m => new m.SmsComposerWeb()),
});

export * from './definitions';
export { SmsComposer };
