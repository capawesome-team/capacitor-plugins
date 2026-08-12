import { registerPlugin } from '@capacitor/core';

import type { TextInteractionPlugin } from './definitions';

const TextInteraction = registerPlugin<TextInteractionPlugin>(
  'TextInteraction',
  {
    web: () => import('./web').then(m => new m.TextInteractionWeb()),
  },
);

export * from './definitions';
export { TextInteraction };
