import { WebPlugin } from '@capacitor/core';

import type {
  IsAvailableResult,
  OpenResult,
  PdfAnnotatorPlugin,
} from './definitions';

export class PdfAnnotatorWeb extends WebPlugin implements PdfAnnotatorPlugin {
  async isAvailable(): Promise<IsAvailableResult> {
    return { available: false };
  }

  async open(): Promise<OpenResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
