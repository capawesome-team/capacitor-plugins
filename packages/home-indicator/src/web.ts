import { WebPlugin } from '@capacitor/core';

import type { HomeIndicatorPlugin, IsHiddenResult } from './definitions';

export class HomeIndicatorWeb extends WebPlugin implements HomeIndicatorPlugin {
  async hide(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isHidden(): Promise<IsHiddenResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async show(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
