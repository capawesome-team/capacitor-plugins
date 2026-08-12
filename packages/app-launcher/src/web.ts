import { WebPlugin } from '@capacitor/core';

import type {
  AppLauncherPlugin,
  CanOpenUrlOptions,
  CanOpenUrlResult,
  OpenUrlOptions,
  OpenUrlResult,
} from './definitions';

export class AppLauncherWeb extends WebPlugin implements AppLauncherPlugin {
  async canOpenUrl(_options: CanOpenUrlOptions): Promise<CanOpenUrlResult> {
    return { value: true };
  }

  async openUrl(options: OpenUrlOptions): Promise<OpenUrlResult> {
    const target = window.open(options.url, '_blank');
    return { completed: target !== null };
  }
}
