import { registerPlugin } from '@capacitor/core';

import type { TiktokAppEventsPlugin } from './definitions';

const TiktokAppEvents = registerPlugin<TiktokAppEventsPlugin>(
  'TiktokAppEvents',
  {
    web: () => import('./web').then(m => new m.TiktokAppEventsWeb()),
  },
);

export * from './definitions';
export { TiktokAppEvents };
