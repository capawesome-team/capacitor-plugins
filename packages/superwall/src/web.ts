import { WebPlugin } from '@capacitor/core';

import type { SuperwallPlugin } from './definitions';

export class SuperwallWeb extends WebPlugin implements SuperwallPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
