import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type { AppReviewPlugin } from './definitions';

export class AppReviewWeb extends WebPlugin implements AppReviewPlugin {
  public async openAppStore(): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async requestReview(): Promise<void> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
