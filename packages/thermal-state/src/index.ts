import { registerPlugin } from '@capacitor/core';

import type { ThermalStatePlugin } from './definitions';

const ThermalState = registerPlugin<ThermalStatePlugin>('ThermalState', {
  web: () => import('./web').then(m => new m.ThermalStateWeb()),
});

export * from './definitions';
export { ThermalState };
