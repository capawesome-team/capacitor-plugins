import { registerPlugin } from '@capacitor/core';

import type { SystemWebViewPlugin } from './definitions';

const SystemWebView = registerPlugin<SystemWebViewPlugin>('SystemWebView', {
  web: () => import('./web').then(m => new m.SystemWebViewWeb()),
});

export * from './definitions';
export { SystemWebView };
