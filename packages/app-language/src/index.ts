import { registerPlugin } from '@capacitor/core';

import type { AppLanguagePlugin } from './definitions';

const AppLanguage = registerPlugin<AppLanguagePlugin>('AppLanguage', {
  web: () => import('./web').then(m => new m.AppLanguageWeb()),
});

export * from './definitions';
export { AppLanguage };
