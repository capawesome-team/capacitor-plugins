import { registerPlugin } from '@capacitor/core';

import type { ForegroundServicePlugin } from './definitions';

const ForegroundService =
  registerPlugin<ForegroundServicePlugin>('ForegroundService');

export * from './definitions';
export { ForegroundService };
