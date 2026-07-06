import { WebPlugin } from '@capacitor/core';

import type {
  AppTrackingTransparencyPlugin,
  GetAdvertisingIdentifierResult,
  GetStatusResult,
  RequestPermissionResult,
} from './definitions';

export class AppTrackingTransparencyWeb
  extends WebPlugin
  implements AppTrackingTransparencyPlugin
{
  async getStatus(): Promise<GetStatusResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async requestPermission(): Promise<RequestPermissionResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getAdvertisingIdentifier(): Promise<GetAdvertisingIdentifierResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
