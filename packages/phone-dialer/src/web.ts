import { WebPlugin } from '@capacitor/core';

import type { CanDialResult, PhoneDialerPlugin } from './definitions';

export class PhoneDialerWeb extends WebPlugin implements PhoneDialerPlugin {
  async canDial(): Promise<CanDialResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async dial(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
