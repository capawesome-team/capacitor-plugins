import { WebPlugin } from '@capacitor/core';

import type {
  AgeSignalsPlugin,
  CheckAgeSignalsOptions,
  CheckAgeSignalsResult,
  CheckEligibilityResult,
  SetNextAgeSignalsExceptionOptions,
  SetNextAgeSignalsResultOptions,
  SetUseFakeManagerOptions,
} from './definitions';

export class AgeSignalsWeb extends WebPlugin implements AgeSignalsPlugin {
  async checkAgeSignals(
    _options: CheckAgeSignalsOptions,
  ): Promise<CheckAgeSignalsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async checkEligibility(): Promise<CheckEligibilityResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setUseFakeManager(
    _options: SetUseFakeManagerOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setNextAgeSignalsResult(
    _options: SetNextAgeSignalsResultOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setNextAgeSignalsException(
    _options: SetNextAgeSignalsExceptionOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
