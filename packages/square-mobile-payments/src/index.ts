import { registerPlugin } from '@capacitor/core';

import type { SquareMobilePaymentsPlugin } from './definitions';

const SquareMobilePayments = registerPlugin<SquareMobilePaymentsPlugin>(
  'SquareMobilePayments',
  {
    web: () => import('./web').then(m => new m.SquareMobilePaymentsWeb()),
  },
);

export * from './definitions';
export { SquareMobilePayments };
