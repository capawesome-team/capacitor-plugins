import { WebPlugin } from '@capacitor/core';

import type { PosthogPlugin } from './definitions';

export class PosthogWeb extends WebPlugin implements PosthogPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
