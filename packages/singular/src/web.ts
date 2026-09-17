import { WebPlugin } from '@capacitor/core';

import type {
  CreateReferrerShortLinkResult,
  GetGlobalPropertiesResult,
  GetLimitDataSharingResult,
  IsAllTrackingStoppedResult,
  SingularPlugin,
  SkanGetConversionValueResult,
} from './definitions';

export class SingularWeb extends WebPlugin implements SingularPlugin {
  async clearGlobalProperties(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async createReferrerShortLink(): Promise<CreateReferrerShortLinkResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getGlobalProperties(): Promise<GetGlobalPropertiesResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getLimitDataSharing(): Promise<GetLimitDataSharingResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async initialize(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAllTrackingStopped(): Promise<IsAllTrackingStoppedResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async resumeAllTracking(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setCustomUserId(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setDeviceToken(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setGlobalProperty(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setLimitAdvertisingIdentifiers(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setLimitDataSharing(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async skanGetConversionValue(): Promise<SkanGetConversionValueResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async skanRegisterAppForAdNetworkAttribution(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async skanUpdateConversionValue(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopAllTracking(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async trackAdRevenue(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async trackEvent(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async trackRevenue(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async trackingOptIn(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async trackingUnder13(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async unsetCustomUserId(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async unsetGlobalProperty(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
