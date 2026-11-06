import { WebPlugin } from '@capacitor/core';

import type {
  FerrostarNavigationPlugin,
  ReplaceRouteOptions,
  StartNavigationOptions,
} from './definitions';

export class FerrostarNavigationWeb
  extends WebPlugin
  implements FerrostarNavigationPlugin
{
  async startNavigation(options: StartNavigationOptions): Promise<void> {
    console.log('startNavigation', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async replaceRoute(options: ReplaceRouteOptions): Promise<void> {
    console.log('replaceRoute', options);
    throw this.unimplemented('Not implemented on web.');
  }

  async stopNavigation(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async muteVoice(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async unmuteVoice(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
