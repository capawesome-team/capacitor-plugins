import { registerPlugin } from '@capacitor/core';

import type { FilePickerPlugin } from './definitions';
// See https://github.com/capawesome-team/capacitor-plugins/issues/14
// See https://github.com/capawesome-team/capacitor-plugins/issues/14
import * as web from './web';

const FilePicker = registerPlugin<FilePickerPlugin>('FilePicker', {
  web: () => new web.FilePickerWeb(),
});

export * from './definitions';
export { FilePicker };
