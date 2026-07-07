import { registerPlugin } from '@capacitor/core';

import type { LocalizationPlugin } from './definitions';

const Localization = registerPlugin<LocalizationPlugin>('Localization', {
  web: () => import('./web').then(m => new m.LocalizationWeb()),
});

export * from './definitions';
export { Localization };
