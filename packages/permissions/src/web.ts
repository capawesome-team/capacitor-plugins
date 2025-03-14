import { WebPlugin } from '@capacitor/core';

import type { PermissionsPlugin } from './definitions';

export class PermissionsWeb extends WebPlugin implements PermissionsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
