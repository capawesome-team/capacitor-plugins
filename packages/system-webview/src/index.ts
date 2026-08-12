import { registerPlugin } from '@capacitor/core';

import type { SystemWebviewPlugin } from './definitions';

const SystemWebview = registerPlugin<SystemWebviewPlugin>('SystemWebview', {
  web: () => import('./web').then(m => new m.SystemWebviewWeb()),
});

export * from './definitions';
export { SystemWebview };
