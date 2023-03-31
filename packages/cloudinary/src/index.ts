import { registerPlugin } from '@capacitor/core';

import type { CloudinaryPlugin } from './definitions';

const Cloudinary = registerPlugin<CloudinaryPlugin>('Cloudinary', {
  web: () => import('./web').then(m => new m.CloudinaryWeb()),
});

export * from './definitions';
export * from './utils';
export { Cloudinary };
