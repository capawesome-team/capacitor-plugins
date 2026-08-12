import { WebPlugin } from '@capacitor/core';
import formbricks from '@formbricks/js';

import type {
  FormbricksPlugin,
  SetAttributeOptions,
  SetAttributesOptions,
  SetLanguageOptions,
  SetUserIdOptions,
  SetupOptions,
  TrackOptions,
} from './definitions';

export class FormbricksWeb extends WebPlugin implements FormbricksPlugin {
  async logout(): Promise<void> {
    await formbricks.logout();
  }

  async setAttribute(options: SetAttributeOptions): Promise<void> {
    await formbricks.setAttribute(options.key, options.value);
  }

  async setAttributes(options: SetAttributesOptions): Promise<void> {
    await formbricks.setAttributes(options.attributes);
  }

  async setLanguage(options: SetLanguageOptions): Promise<void> {
    await formbricks.setLanguage(options.language);
  }

  async setUserId(options: SetUserIdOptions): Promise<void> {
    await formbricks.setUserId(options.userId);
  }

  async setup(options: SetupOptions): Promise<void> {
    await formbricks.setup({
      appUrl: options.appUrl,
      environmentId: options.environmentId,
    });
  }

  async track(options: TrackOptions): Promise<void> {
    await formbricks.track(options.action);
  }
}
