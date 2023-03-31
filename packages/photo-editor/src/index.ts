import { registerPlugin } from '@capacitor/core';

import type { PhotoEditorPlugin } from './definitions';

const PhotoEditor = registerPlugin<PhotoEditorPlugin>('PhotoEditor', {
  web: () => import('./web').then(m => new m.PhotoEditorWeb()),
});

export * from './definitions';
export { PhotoEditor };
