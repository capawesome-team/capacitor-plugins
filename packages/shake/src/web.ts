import { WebPlugin } from '@capacitor/core';

import type { ShakePlugin } from './definitions';

export class ShakeWeb extends WebPlugin implements ShakePlugin {
  async startWatching(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopWatching(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
