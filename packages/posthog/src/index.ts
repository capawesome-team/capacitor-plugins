import { registerPlugin } from '@capacitor/core';

import type { PosthogPlugin } from './definitions';

const Posthog = registerPlugin<PosthogPlugin>('Posthog', {
  web: () => import('./web').then((m) => new m.PosthogWeb()),
});

export * from './definitions';
export { Posthog };
