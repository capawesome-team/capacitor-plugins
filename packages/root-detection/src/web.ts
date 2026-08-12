import { WebPlugin } from '@capacitor/core';

import type {
  IsDeveloperModeEnabledResult,
  IsEmulatorResult,
  IsRootedResult,
  RootDetectionPlugin,
} from './definitions';

export class RootDetectionWeb extends WebPlugin implements RootDetectionPlugin {
  async isDeveloperModeEnabled(): Promise<IsDeveloperModeEnabledResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isEmulator(): Promise<IsEmulatorResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isRooted(): Promise<IsRootedResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
