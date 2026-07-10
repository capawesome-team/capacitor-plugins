import { WebPlugin } from '@capacitor/core';

import type { PdfViewerPlugin } from './definitions';

export class PdfViewerWeb extends WebPlugin implements PdfViewerPlugin {
  async close(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async open(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
