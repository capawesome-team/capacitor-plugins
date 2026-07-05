import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type { IsReadyResult, NodejsPlugin } from './definitions';

export class NodejsWeb extends WebPlugin implements NodejsPlugin {
  async isReady(): Promise<IsReadyResult> {
    throw this.createUnimplementedException();
  }

  async send(): Promise<void> {
    throw this.createUnimplementedException();
  }

  async start(): Promise<void> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
