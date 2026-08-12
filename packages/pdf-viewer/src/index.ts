import { registerPlugin } from '@capacitor/core';

import type { PdfViewerPlugin } from './definitions';

const PdfViewer = registerPlugin<PdfViewerPlugin>('PdfViewer', {
  web: () => import('./web').then(m => new m.PdfViewerWeb()),
});

export * from './definitions';
export { PdfViewer };
