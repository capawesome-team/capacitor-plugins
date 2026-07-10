import { WebPlugin } from '@capacitor/core';

import type {
  GetSimCardsResult,
  PermissionStatus,
  SimPlugin,
} from './definitions';

export class SimWeb extends WebPlugin implements SimPlugin {
  async checkPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getSimCards(): Promise<GetSimCardsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }
}
