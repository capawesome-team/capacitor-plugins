import { WebPlugin } from '@capacitor/core';

import type {
  GenerateFromHtmlOptions,
  GenerateFromUrlOptions,
  GenerateResult,
  PdfGeneratorPlugin,
} from './definitions';

export class PdfGeneratorWeb extends WebPlugin implements PdfGeneratorPlugin {
  async generateFromHtml(
    _options: GenerateFromHtmlOptions,
  ): Promise<GenerateResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async generateFromUrl(
    _options: GenerateFromUrlOptions,
  ): Promise<GenerateResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
