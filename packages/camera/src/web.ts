import { WebPlugin } from '@capacitor/core';

import type { CameraPlugin, MultiPhotoResult } from './definitions';

export class CameraWeb extends WebPlugin implements CameraPlugin {
  async takeMultiplePhotos(_options?: any): Promise<MultiPhotoResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
