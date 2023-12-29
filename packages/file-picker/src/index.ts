import { registerPlugin } from '@capacitor/core';

import type { FilePickerPlugin } from './definitions';
import * as web from './web';

const FilePicker = registerPlugin<FilePickerPlugin>('FilePicker', {
    web:  () => new web.FilePickerWeb()
});

export * from './definitions';
export { FilePicker };
