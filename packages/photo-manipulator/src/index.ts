import { registerPlugin } from '@capacitor/core';

import type { PhotoManipulatorPlugin } from './definitions';

const PhotoManipulator = registerPlugin<PhotoManipulatorPlugin>(
  'PhotoManipulator',
  {
    web: () => import('./web').then(m => new m.PhotoManipulatorWeb()),
  },
);

export * from './definitions';
export { PhotoManipulator };
