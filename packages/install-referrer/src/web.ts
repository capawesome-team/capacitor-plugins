import { WebPlugin } from '@capacitor/core';

import type {
  GetAttributionTokenResult,
  GetInstallReferrerResult,
  InstallReferrerPlugin,
} from './definitions';

export class InstallReferrerWeb
  extends WebPlugin
  implements InstallReferrerPlugin
{
  async getAttributionToken(): Promise<GetAttributionTokenResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getInstallReferrer(): Promise<GetInstallReferrerResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
