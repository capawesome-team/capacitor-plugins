import { WebPlugin } from '@capacitor/core';

import type {
  CompassPlugin,
  GetHeadingResult,
  IsAvailableResult,
} from './definitions';

export class CompassWeb extends WebPlugin implements CompassPlugin {
  async getHeading(): Promise<GetHeadingResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startHeadingUpdates(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopHeadingUpdates(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
