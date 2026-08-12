import { WebPlugin } from '@capacitor/core';

import type {
  GetInfoResult,
  IsUpdateRequiredResult,
  SystemWebviewPlugin,
} from './definitions';

export class SystemWebviewWeb extends WebPlugin implements SystemWebviewPlugin {
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
