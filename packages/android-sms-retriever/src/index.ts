import { registerPlugin } from '@capacitor/core';

import type { AndroidSmsRetrieverPlugin } from './definitions';

const AndroidSmsRetriever = registerPlugin<AndroidSmsRetrieverPlugin>(
  'AndroidSmsRetriever',
  {
    web: () => import('./web').then(m => new m.AndroidSmsRetrieverWeb()),
  },
);

export * from './definitions';
export { AndroidSmsRetriever };
