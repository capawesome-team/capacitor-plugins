import { WebPlugin } from '@capacitor/core';

import type {
  AppIntegrityPlugin,
  AttestKeyResult,
  GenerateAssertionResult,
  GenerateKeyResult,
  IsAvailableResult,
  RequestIntegrityTokenResult,
} from './definitions';

export class AppIntegrityWeb extends WebPlugin implements AppIntegrityPlugin {
  async attestKey(): Promise<AttestKeyResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async generateAssertion(): Promise<GenerateAssertionResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async generateKey(): Promise<GenerateKeyResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async prepareIntegrityToken(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async requestIntegrityToken(): Promise<RequestIntegrityTokenResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
