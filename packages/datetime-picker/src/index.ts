import { registerPlugin } from '@capacitor/core';

import type { DatetimePickerPlugin } from './definitions';

const DatetimePicker = registerPlugin<DatetimePickerPlugin>('DatetimePicker', {
  web: () => import('./web').then(m => new m.DatetimePickerWeb()),
});

export * from './definitions';
export { DatetimePicker };
