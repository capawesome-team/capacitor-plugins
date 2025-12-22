import { WebPlugin } from '@capacitor/core';

import type {
  AgeSignalsPlugin,
  CheckAgeSignalsOptions,
  CheckAgeSignalsResult,
  CheckEligibilityResult,
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
}
