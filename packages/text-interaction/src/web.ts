import { WebPlugin } from '@capacitor/core';

import type { TextInteractionPlugin, IsEnabledResult } from './definitions';

export class TextInteractionWeb
  extends WebPlugin
  implements TextInteractionPlugin
{
  async disable(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async enable(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isEnabled(): Promise<IsEnabledResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
