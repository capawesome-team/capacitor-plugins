import { registerPlugin } from '@capacitor/core';

import type { ScreenshotPlugin } from './definitions';

const Screenshot = registerPlugin<ScreenshotPlugin>('Screenshot', {
  web: () => import('./web').then(m => new m.ScreenshotWeb()),
});

export * from './definitions';
export { Screenshot };
