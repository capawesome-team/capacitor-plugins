import { WebPlugin } from '@capacitor/core';

import type {
  AppleSignInPlugin,
  InitializeOptions,
  SignInOptions,
  SignInResult,
} from './definitions';

export class AppleSignInWeb extends WebPlugin implements AppleSignInPlugin {
  private clientId: string | undefined;
  private scriptLoaded = false;

  async initialize(options: InitializeOptions): Promise<void> {
    this.clientId = options.clientId;
    await this.loadScript();
  }

  async signIn(options?: SignInOptions): Promise<SignInResult> {
    if (!this.clientId) {
      throw new Error('clientId must be provided. Call initialize() first.');
    }

    const scope = (options?.scopes ?? [])
      .map(s => (s === 'FULL_NAME' ? 'name' : 'email'))
      .join(' ');

    (window as any).AppleID.auth.init({
      clientId: this.clientId,
      scope: scope || undefined,
      redirectURI: options?.redirectUrl,
      state: options?.state,
      nonce: options?.nonce,
      usePopup: true,
    });

    const response = await (window as any).AppleID.auth.signIn();

    const authorization = response.authorization;
    const idToken: string = authorization.id_token;
    const authorizationCode: string = authorization.code;
    const state: string | undefined = authorization.state;

    const payload = this.decodeJwtPayload(idToken);
    const user: string = payload.sub;
    const email: string | null = payload.email ?? null;

    let givenName: string | null = null;
    let familyName: string | null = null;
    if (response.user) {
      givenName = response.user.name?.firstName ?? null;
      familyName = response.user.name?.lastName ?? null;
    }

    const result: SignInResult = {
      authorizationCode,
      idToken,
      user,
      email: response.user?.email ?? email,
      givenName,
      familyName,
    };
    if (state !== undefined) {
      result.state = state;
    }
    return result;
  }

  private decodeJwtPayload(jwt: string): any {
    const parts = jwt.split('.');
    const payload = parts[1];
    const decoded = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
    return JSON.parse(decoded);
  }

  private loadScript(): Promise<void> {
    if (this.scriptLoaded) {
      return Promise.resolve();
    }
    return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.src =
        'https://appleid.cdn-apple.com/appleauth/static/jsapi/appleid/1/en_US/appleid.auth.js';
      script.onload = () => {
        this.scriptLoaded = true;
        resolve();
      };
      script.onerror = () => {
        reject(new Error('Failed to load Apple Sign-In SDK.'));
      };
      document.head.appendChild(script);
    });
  }
}
