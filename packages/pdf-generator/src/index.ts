import { registerPlugin } from '@capacitor/core';

import type { PdfGeneratorPlugin } from './definitions';

const PdfGenerator = registerPlugin<PdfGeneratorPlugin>('PdfGenerator', {
  web: () => import('./web').then(m => new m.PdfGeneratorWeb()),
});

export * from './definitions';
export { PdfGenerator };
