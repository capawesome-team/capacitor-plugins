import { registerPlugin } from '@capacitor/core';

import type { ExifPlugin } from './definitions';

const Exif = registerPlugin<ExifPlugin>('Exif', {
  web: () => import('./web').then(m => new m.ExifWeb()),
});

export * from './definitions';
export { Exif };
