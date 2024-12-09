import { registerPlugin } from '@capacitor/core';

import type { AppShortcutPlugin } from './definitions';

const AppShortcut = registerPlugin<AppShortcutPlugin>('AppShortcut', {
  web: () => import('./web').then(m => new m.AppShortcutWeb()),
});

export * from './definitions';
export { AppShortcut };
