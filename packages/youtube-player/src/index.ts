import { registerPlugin } from '@capacitor/core';

import type { YoutubePlayerPlugin } from './definitions';

const YoutubePlayer = registerPlugin<YoutubePlayerPlugin>('YoutubePlayer', {
  web: () => import('./web').then(m => new m.YoutubePlayerWeb()),
});

export * from './definitions';
export { YoutubePlayer };
