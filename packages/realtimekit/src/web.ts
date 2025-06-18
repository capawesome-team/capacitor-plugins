import { WebPlugin } from '@capacitor/core';

import type { RealtimeKitPlugin } from './definitions';

export class RealtimeKitWeb extends WebPlugin implements RealtimeKitPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
