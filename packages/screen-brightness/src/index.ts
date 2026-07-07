import { registerPlugin } from '@capacitor/core';

import type { ScreenBrightnessPlugin } from './definitions';

const ScreenBrightness = registerPlugin<ScreenBrightnessPlugin>(
  'ScreenBrightness',
  {
    web: () => import('./web').then(m => new m.ScreenBrightnessWeb()),
  },
);

export * from './definitions';
export { ScreenBrightness };
