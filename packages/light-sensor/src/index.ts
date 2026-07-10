import { registerPlugin } from '@capacitor/core';

import type { LightSensorPlugin } from './definitions';

const LightSensor = registerPlugin<LightSensorPlugin>('LightSensor', {
  web: () => import('./web').then(m => new m.LightSensorWeb()),
});

export * from './definitions';
export { LightSensor };
