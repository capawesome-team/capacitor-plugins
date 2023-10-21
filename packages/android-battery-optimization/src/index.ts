import { registerPlugin } from '@capacitor/core';

import type { BatteryOptimizationPlugin } from './definitions';

const BatteryOptimization = registerPlugin<BatteryOptimizationPlugin>(
  'BatteryOptimization',
);

export * from './definitions';
export { BatteryOptimization };
