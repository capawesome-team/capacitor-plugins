import { WebPlugin } from '@capacitor/core';

import type {
  AppIconPlugin,
  GetCurrentIconResult,
  IsAvailableResult,
  SetIconOptions,
} from './definitions';

export class AppIconWeb extends WebPlugin implements AppIconPlugin {
  async getCurrentIcon(): Promise<GetCurrentIconResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async resetIcon(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setIcon(_options: SetIconOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
