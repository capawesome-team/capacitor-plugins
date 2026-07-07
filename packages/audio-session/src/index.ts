import { registerPlugin } from '@capacitor/core';

import type { AudioSessionPlugin } from './definitions';

const AudioSession = registerPlugin<AudioSessionPlugin>('AudioSession', {
  web: () => import('./web').then(m => new m.AudioSessionWeb()),
});

export * from './definitions';
export { AudioSession };
