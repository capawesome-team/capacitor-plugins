import { WebPlugin } from '@capacitor/core';

import type { TiktokAppEventsPlugin } from './definitions';

export class TiktokAppEventsWeb
  extends WebPlugin
  implements TiktokAppEventsPlugin
{
  async flush(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async identify(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async initialize(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async logout(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async trackEvent(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
