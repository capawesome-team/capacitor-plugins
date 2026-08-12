import { WebPlugin } from '@capacitor/core';

import type {
  GetPreferredZoomResult,
  GetZoomResult,
  TextZoomPlugin,
} from './definitions';

export class TextZoomWeb extends WebPlugin implements TextZoomPlugin {
  async getPreferredZoom(): Promise<GetPreferredZoomResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getZoom(): Promise<GetZoomResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setZoom(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
