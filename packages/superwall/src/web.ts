import { WebPlugin } from '@capacitor/core';

import type {
  ConfigureOptions,
  GetIsLoggedInResult,
  GetPresentationResultOptions,
  GetPresentationResultResult,
  GetSubscriptionStatusResult,
  GetUserIdResult,
  HandleDeepLinkOptions,
  IdentifyOptions,
  RegisterOptions,
  RegisterResult,
  SetUserAttributesOptions,
  SuperwallPlugin,
} from './definitions';

export class SuperwallWeb extends WebPlugin implements SuperwallPlugin {
  async configure(_options: ConfigureOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async register(_options: RegisterOptions): Promise<RegisterResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getPresentationResult(
    _options: GetPresentationResultOptions,
  ): Promise<GetPresentationResultResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async identify(_options: IdentifyOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async reset(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getUserId(): Promise<GetUserIdResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getIsLoggedIn(): Promise<GetIsLoggedInResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async setUserAttributes(_options: SetUserAttributesOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async handleDeepLink(_options: HandleDeepLinkOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getSubscriptionStatus(): Promise<GetSubscriptionStatusResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
