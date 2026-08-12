import { WebPlugin } from '@capacitor/core';

import type {
  GetAvailableAppsResult,
  GetDefaultAppResult,
  MapsLauncherPlugin,
  NavigateOptions,
} from './definitions';

export class MapsLauncherWeb extends WebPlugin implements MapsLauncherPlugin {
  async getAvailableApps(): Promise<GetAvailableAppsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getDefaultApp(): Promise<GetDefaultAppResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async navigate(_options: NavigateOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
