import { WebPlugin } from '@capacitor/core';

import type {
  AndroidIntentLauncherPlugin,
  CanResolveActivityResult,
  StartActivityResult,
} from './definitions';

export class AndroidIntentLauncherWeb
  extends WebPlugin
  implements AndroidIntentLauncherPlugin
{
  async canResolveActivity(): Promise<CanResolveActivityResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startActivity(): Promise<StartActivityResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
