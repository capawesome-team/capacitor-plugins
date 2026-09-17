import { registerPlugin } from '@capacitor/core';

import type { OptionPickerPlugin } from './definitions';

const OptionPicker = registerPlugin<OptionPickerPlugin>('OptionPicker', {
  web: () => import('./web').then(m => new m.OptionPickerWeb()),
});

export * from './definitions';
export { OptionPicker };
