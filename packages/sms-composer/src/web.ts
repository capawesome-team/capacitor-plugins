import { WebPlugin } from '@capacitor/core';

import type {
  CanComposeSmsResult,
  ComposeSmsResult,
  SmsComposerPlugin,
} from './definitions';

export class SmsComposerWeb extends WebPlugin implements SmsComposerPlugin {
  async canComposeSms(): Promise<CanComposeSmsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async composeSms(): Promise<ComposeSmsResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
