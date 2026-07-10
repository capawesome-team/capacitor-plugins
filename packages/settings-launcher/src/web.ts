import { WebPlugin } from '@capacitor/core';

import type {
  OpenAndroidSettingsOptions,
  SettingsLauncherPlugin,
} from './definitions';

export class SettingsLauncherWeb
  extends WebPlugin
  implements SettingsLauncherPlugin
{
  async openAndroidSettings(
    _options: OpenAndroidSettingsOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openAppSettings(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openNotificationSettings(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
