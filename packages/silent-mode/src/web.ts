import { WebPlugin } from '@capacitor/core';

import type {
  GetRingerModeResult,
  IsSilentResult,
  SilentModePlugin,
} from './definitions';

export class SilentModeWeb extends WebPlugin implements SilentModePlugin {
  async getRingerMode(): Promise<GetRingerModeResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isSilent(): Promise<IsSilentResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
