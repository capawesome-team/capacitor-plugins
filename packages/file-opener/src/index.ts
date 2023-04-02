import { registerPlugin } from '@capacitor/core';

import type { FileOpenerPlugin } from './definitions';

const FileOpener = registerPlugin<FileOpenerPlugin>('FileOpener', {
  web: () => import('./web').then(m => new m.FileOpenerWeb()),
});

export * from './definitions';
export { FileOpener };
