import { registerPlugin } from '@capacitor/core';

import type { PasswordAutofillPlugin } from './definitions';

const PasswordAutofill = registerPlugin<PasswordAutofillPlugin>(
  'PasswordAutofill',
  {
    web: () => import('./web').then(m => new m.PasswordAutofillWeb()),
  },
);

export * from './definitions';
export { PasswordAutofill };
