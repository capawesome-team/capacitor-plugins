import { WebPlugin } from '@capacitor/core';

import type {
  GetInfoResult,
  IsUpdateRequiredResult,
  SystemWebViewPlugin,
} from './definitions';

export class SystemWebViewWeb extends WebPlugin implements SystemWebViewPlugin {
  async getInfo(): Promise<GetInfoResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isUpdateRequired(): Promise<IsUpdateRequiredResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async openAppStore(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
