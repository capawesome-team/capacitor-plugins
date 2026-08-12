import { WebPlugin } from '@capacitor/core';

import type {
  GetBrightnessResult,
  ScreenBrightnessPlugin,
} from './definitions';

export class ScreenBrightnessWeb
  extends WebPlugin
  implements ScreenBrightnessPlugin
{
  async getBrightness(): Promise<GetBrightnessResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async resetBrightness(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setBrightness(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
