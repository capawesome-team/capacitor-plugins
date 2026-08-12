import { WebPlugin } from '@capacitor/core';

import type {
  AddPassesOptions,
  CanAddPassesResult,
  SaveToGoogleWalletOptions,
  WalletPlugin,
} from './definitions';

export class WalletWeb extends WebPlugin implements WalletPlugin {
  async addPasses(_options: AddPassesOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async canAddPasses(): Promise<CanAddPassesResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async saveToGoogleWallet(_options: SaveToGoogleWalletOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
