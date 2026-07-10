import { registerPlugin } from '@capacitor/core';

import type { NodejsPlugin } from './definitions';

const Nodejs = registerPlugin<NodejsPlugin>('Nodejs', {
  web: () => import('./web').then(m => new m.NodejsWeb()),
});

export * from './definitions';
export { Nodejs };
