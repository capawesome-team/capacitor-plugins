import { WebPlugin } from '@capacitor/core';

import type {
  GetMeasurementResult,
  IsAvailableResult,
  LightSensorPlugin,
} from './definitions';

export class LightSensorWeb extends WebPlugin implements LightSensorPlugin {
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
