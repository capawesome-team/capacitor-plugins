import { WebPlugin } from '@capacitor/core';

import type { IsEnabledResult, PrivacyScreenPlugin } from './definitions';

export class PrivacyScreenWeb extends WebPlugin implements PrivacyScreenPlugin {
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
