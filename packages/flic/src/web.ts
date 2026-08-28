import { WebPlugin } from '@capacitor/core';

import type {
  ConnectButtonByIdOptions,
  DisconnectButtonByIdOptions,
  FlicPlugin,
  ForgetButtonByIdOptions,
  GetButtonsResult,
  InitializeOptions,
  PermissionStatus,
  StartScanResult,
} from './definitions';

export class FlicWeb extends WebPlugin implements FlicPlugin {
  async checkPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }

  async connectButtonById(_options: ConnectButtonByIdOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async disconnectButtonById(
    _options: DisconnectButtonByIdOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async forgetButtonById(_options: ForgetButtonByIdOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getButtons(): Promise<GetButtonsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async initialize(_options?: InitializeOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startScan(): Promise<StartScanResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopScan(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
