import { WebPlugin } from '@capacitor/core';

import type { AppReviewPlugin } from './definitions';

export class AppReviewWeb extends WebPlugin implements AppReviewPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
