import { WebPlugin } from '@capacitor/core';

import type {
  GetVolumeResult,
  IsWatchingResult,
  VolumePlugin,
} from './definitions';

export class VolumeWeb extends WebPlugin implements VolumePlugin {
  async getVolume(): Promise<GetVolumeResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isWatching(): Promise<IsWatchingResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setVolume(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startWatching(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopWatching(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
