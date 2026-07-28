import { WebPlugin } from '@capacitor/core';

import type {
  AgeSignalsPlugin,
  GetAgeRangeResult,
  GetRegulatoryRequirementsResult,
  IsAvailableResult,
  RequestAgeRangeOptions,
  RequestAgeRangeResult,
  SetNextAgeSignalsAccessResultOptions,
  SetNextAgeSignalsExceptionOptions,
  SetNextAgeSignalsResultOptions,
  SetNextRequestAgeSignalsAccessExceptionOptions,
  SetUseFakeManagerOptions,
  ShowSignificantUpdateAcknowledgmentOptions,
} from './definitions';

export class AgeSignalsWeb extends WebPlugin implements AgeSignalsPlugin {
  async getAgeRange(): Promise<GetAgeRangeResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getRegulatoryRequirements(): Promise<GetRegulatoryRequirementsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    return { available: false };
  }

  async requestAgeRange(
    _options?: RequestAgeRangeOptions,
  ): Promise<RequestAgeRangeResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setNextAgeSignalsAccessResult(
    _options: SetNextAgeSignalsAccessResultOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setNextAgeSignalsException(
    _options: SetNextAgeSignalsExceptionOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setNextAgeSignalsResult(
    _options: SetNextAgeSignalsResultOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setNextRequestAgeSignalsAccessException(
    _options: SetNextRequestAgeSignalsAccessExceptionOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setUseFakeManager(_options: SetUseFakeManagerOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async showSignificantUpdateAcknowledgment(
    _options: ShowSignificantUpdateAcknowledgmentOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
