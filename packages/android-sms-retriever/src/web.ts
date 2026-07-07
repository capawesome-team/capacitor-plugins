import { WebPlugin } from '@capacitor/core';

import type {
  AndroidSmsRetrieverPlugin,
  RequestPhoneNumberResult,
  RetrieveSmsResult,
} from './definitions';

export class AndroidSmsRetrieverWeb
  extends WebPlugin
  implements AndroidSmsRetrieverPlugin
{
  async requestPhoneNumber(): Promise<RequestPhoneNumberResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async retrieveSms(): Promise<RetrieveSmsResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
