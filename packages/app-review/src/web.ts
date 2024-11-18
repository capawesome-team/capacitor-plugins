import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type { AppReviewPlugin } from './definitions';

export class AppReviewWeb extends WebPlugin implements AppReviewPlugin {
  openAppStore(): Promise<void> {
    throw this.createUnimplementedException();
  }

  requestReview(): Promise<void> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
