import { WebPlugin } from '@capacitor/core';

import type {
  AudioSessionPlugin,
  ConfigureOptions,
  GetCurrentOutputsResult,
  OverrideOutputOptions,
  SetActiveOptions,
} from './definitions';

export class AudioSessionWeb extends WebPlugin implements AudioSessionPlugin {
  async configure(_options: ConfigureOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getCurrentOutputs(): Promise<GetCurrentOutputsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async overrideOutput(_options: OverrideOutputOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setActive(_options: SetActiveOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
