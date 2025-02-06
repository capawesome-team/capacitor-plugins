import { registerPlugin } from '@capacitor/core';

import type { EdgeToEdgePlugin } from './definitions';

const EdgeToEdge = registerPlugin<EdgeToEdgePlugin>('EdgeToEdge', {});

export * from './definitions';
export { EdgeToEdge };
