import { WebPlugin } from '@capacitor/core';

import type {
  GetMeasurementResult,
  IsAvailableResult,
  ProximitySensorPlugin,
} from './definitions';

export class ProximitySensorWeb
  extends WebPlugin
  implements ProximitySensorPlugin
{
  async getMeasurement(): Promise<GetMeasurementResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startMeasurementUpdates(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopMeasurementUpdates(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
