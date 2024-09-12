import { WebPlugin } from '@capacitor/core';

import type { IsAvailableResult, IsEnabledResult, TorchPlugin } from './definitions';

export class TorchWeb extends WebPlugin implements TorchPlugin {
  async enable(): Promise<void> {
    this.throwUnimplementedError();
  }

  async disable(): Promise<void> {
    this.throwUnimplementedError();
  }

  async isAvailable(): Promise<IsAvailableResult> {
    this.throwUnimplementedError();
  }

  async isEnabled(): Promise<IsEnabledResult> {
    this.throwUnimplementedError();
  }

  async toggle(): Promise<void> {
    this.throwUnimplementedError();
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
