import { WebPlugin } from '@capacitor/core';

import type { TabBarPlugin } from './definitions';

export class TabBarWeb extends WebPlugin implements TabBarPlugin {
  async hide(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async selectTabById(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setColors(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setTabs(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async show(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
