import { WebPlugin } from '@capacitor/core';

import type {
  AppLanguagePlugin,
  GetLanguageResult,
  SetLanguageOptions,
} from './definitions';

export class AppLanguageWeb extends WebPlugin implements AppLanguagePlugin {
  async getLanguage(): Promise<GetLanguageResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openSettings(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async resetLanguage(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setLanguage(_options: SetLanguageOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
