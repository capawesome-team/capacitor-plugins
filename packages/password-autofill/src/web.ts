import { WebPlugin } from '@capacitor/core';

import type {
  PasswordAutofillPlugin,
  SavePasswordOptions,
} from './definitions';

export class PasswordAutofillWeb
  extends WebPlugin
  implements PasswordAutofillPlugin
{
  async savePassword(_options: SavePasswordOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
