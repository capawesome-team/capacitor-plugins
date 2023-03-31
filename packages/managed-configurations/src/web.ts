import { WebPlugin } from '@capacitor/core';

import type {
  GetOptions,
  GetResult,
  ManagedConfigurationsPlugin,
} from './definitions';

export class ManagedConfigurationsWeb
  extends WebPlugin
  implements ManagedConfigurationsPlugin
{
  async getString(_options: GetOptions): Promise<GetResult<string>> {
    throw new Error('Not available on web.');
  }

  async getNumber(_options: GetOptions): Promise<GetResult<number>> {
    throw new Error('Not available on web.');
  }

  async getBoolean(_options: GetOptions): Promise<GetResult<boolean>> {
    throw new Error('Not available on web.');
  }
}
