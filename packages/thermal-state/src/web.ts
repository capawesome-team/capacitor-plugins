import { WebPlugin } from '@capacitor/core';

import type { GetThermalStateResult, ThermalStatePlugin } from './definitions';

export class ThermalStateWeb extends WebPlugin implements ThermalStatePlugin {
  async getThermalState(): Promise<GetThermalStateResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
