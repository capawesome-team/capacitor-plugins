import { registerPlugin } from '@capacitor/core';

import type { AppShortcutsPlugin } from './definitions';

const AppShortcuts = registerPlugin<AppShortcutsPlugin>('AppShortcuts', {
  web: () => import('./web').then(m => new m.AppShortcutsWeb()),
});

export * from './definitions';
export { AppShortcuts };
