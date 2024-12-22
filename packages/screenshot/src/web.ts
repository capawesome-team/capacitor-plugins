import { WebPlugin } from '@capacitor/core';
import html2canvas from 'html2canvas';

import type { ScreenshotPlugin, TakeResult } from './definitions';

export class ScreenshotWeb extends WebPlugin implements ScreenshotPlugin {
  async take(): Promise<TakeResult> {
    const canvas = await html2canvas(document.body);
    return { uri: canvas.toDataURL() };
  }
}
