import { WebPlugin } from '@capacitor/core';

import type { ScreenshotPlugin } from './definitions';

export class ScreenshotWeb extends WebPlugin implements ScreenshotPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
