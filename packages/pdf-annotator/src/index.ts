import { registerPlugin } from '@capacitor/core';

import type { PdfAnnotatorPlugin } from './definitions';

const PdfAnnotator = registerPlugin<PdfAnnotatorPlugin>('PdfAnnotator', {
  web: () => import('./web').then(m => new m.PdfAnnotatorWeb()),
});

export * from './definitions';
export { PdfAnnotator };
