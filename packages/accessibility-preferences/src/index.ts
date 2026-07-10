import { registerPlugin } from '@capacitor/core';

import type { AccessibilityPreferencesPlugin } from './definitions';

const AccessibilityPreferences = registerPlugin<AccessibilityPreferencesPlugin>(
  'AccessibilityPreferences',
  {
    web: () => import('./web').then(m => new m.AccessibilityPreferencesWeb()),
  },
);

export * from './definitions';
export { AccessibilityPreferences };
