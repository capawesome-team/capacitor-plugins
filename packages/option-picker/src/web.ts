import { WebPlugin } from '@capacitor/core';

import type {
  OptionPickerPlugin,
  PresentOptions,
  PresentResult,
} from './definitions';

export class OptionPickerWeb extends WebPlugin implements OptionPickerPlugin {
  async present(_options: PresentOptions): Promise<PresentResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
