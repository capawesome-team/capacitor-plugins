import { registerPlugin } from '@capacitor/core';

import type { GooglePlayServicesPlugin } from './definitions';

const GooglePlayServices = registerPlugin<GooglePlayServicesPlugin>(
  'GooglePlayServices',
  {},
);

export * from './definitions';
export { GooglePlayServices };
