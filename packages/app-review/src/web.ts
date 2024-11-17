import { WebPlugin } from '@capacitor/core';

import type { AppReviewPlugin } from './definitions';

export class AppReviewWeb extends WebPlugin implements AppReviewPlugin {
  openAppStore(): Promise<void> {
    throw this.throwUnimplementedError();
  }

  requestReview(): Promise<void> {
    throw this.throwUnimplementedError();
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
