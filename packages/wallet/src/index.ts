import { registerPlugin } from '@capacitor/core';

import type { WalletPlugin } from './definitions';

const Wallet = registerPlugin<WalletPlugin>('Wallet', {
  web: () => import('./web').then(m => new m.WalletWeb()),
});

export * from './definitions';
export { Wallet };
