import { WebPlugin } from '@capacitor/core';

import type { EditPhotoOptions, PhotoEditorPlugin } from './definitions';

export class PhotoEditorWeb extends WebPlugin implements PhotoEditorPlugin {
  async editPhoto(_options: EditPhotoOptions): Promise<void> {
    throw new Error('Not available on web.');
  }
}
