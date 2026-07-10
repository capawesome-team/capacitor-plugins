import { WebPlugin } from '@capacitor/core';

import type { ExifPlugin, ReadExifResult } from './definitions';

export class ExifWeb extends WebPlugin implements ExifPlugin {
  async readExif(): Promise<ReadExifResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async removeExif(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async writeExif(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
