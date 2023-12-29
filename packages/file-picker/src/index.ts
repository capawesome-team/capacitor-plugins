import { registerPlugin } from '@capacitor/core';

import type { FilePickerPlugin } from './definitions';

const FilePicker = registerPlugin<FilePickerPlugin>('FilePicker', {
  web: () => import('./web').then(m => new m.FilePickerWeb()),
});

export * from './definitions';
export { FilePicker };
