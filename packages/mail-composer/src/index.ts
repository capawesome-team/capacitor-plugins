import { registerPlugin } from '@capacitor/core';

import type { MailComposerPlugin } from './definitions';

const MailComposer = registerPlugin<MailComposerPlugin>('MailComposer', {
  web: () => import('./web').then(m => new m.MailComposerWeb()),
});

export * from './definitions';
export { MailComposer };
