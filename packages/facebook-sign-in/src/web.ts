import { CapacitorException, WebPlugin } from '@capacitor/core';

import type {
  AccessToken,
  FacebookSignInPlugin,
  GetCurrentAccessTokenResult,
  InitializeOptions,
  Profile,
  SignInOptions,
  SignInResult,
} from './definitions';
import { ErrorCode } from './definitions';

export class FacebookSignInWeb
  extends WebPlugin
  implements FacebookSignInPlugin
{
  private static readonly errorAppIdMissing = 'appId must be provided.';
  private static readonly errorNotInitialized =
    'Facebook Sign-In is not initialized. Call initialize() first.';
  private static readonly errorSdkLoadFailed =
    'Failed to load the Facebook SDK.';
  private static readonly errorSignInCanceled =
    'The user canceled the sign-in flow.';
  private static readonly graphApiVersion = 'v23.0';
  private static readonly profileFields =
    'id,name,email,picture.width(720).height(720)';
  private static readonly sdkUrl = 'https://connect.facebook.net/en_US/sdk.js';

  private sdk: FacebookSdk | undefined;

  async getCurrentAccessToken(): Promise<GetCurrentAccessTokenResult> {
    const sdk = this.getSdk();
    const response = await this.getLoginStatus(sdk);
    const authResponse = response.authResponse;
    if (response.status !== 'connected' || !authResponse) {
      return { accessToken: null };
    }
    const permissions = await this.getGrantedPermissions(sdk);
    return {
      accessToken: this.createAccessTokenFromAuthResponse(
        authResponse,
        permissions,
      ),
    };
  }

  async initialize(options?: InitializeOptions): Promise<void> {
    const appId = options?.appId;
    if (!appId) {
      throw new Error(FacebookSignInWeb.errorAppIdMissing);
    }
    const sdk = await this.loadSdk();
    sdk.init({
      appId,
      cookie: true,
      version: FacebookSignInWeb.graphApiVersion,
      xfbml: false,
    });
    this.sdk = sdk;
  }

  async signIn(options?: SignInOptions): Promise<SignInResult> {
    const sdk = this.getSdk();
    const permissions = options?.permissions ?? ['public_profile', 'email'];
    const response = await new Promise<FacebookLoginStatusResponse>(resolve =>
      sdk.login(resolve, {
        return_scopes: true,
        scope: permissions.join(','),
      }),
    );
    const authResponse = response.authResponse;
    if (!authResponse) {
      throw new CapacitorException(
        FacebookSignInWeb.errorSignInCanceled,
        undefined,
        {
          code: ErrorCode.SignInCanceled,
        },
      );
    }
    const grantedPermissions = authResponse.grantedScopes?.split(',') ?? [];
    const profile = await this.getProfile(sdk);
    return {
      accessToken: this.createAccessTokenFromAuthResponse(
        authResponse,
        grantedPermissions,
      ),
      authenticationToken: null,
      profile,
    };
  }

  async signOut(): Promise<void> {
    const sdk = this.getSdk();
    const response = await this.getLoginStatus(sdk);
    if (response.status !== 'connected') {
      return;
    }
    await new Promise<void>(resolve => sdk.logout(() => resolve()));
  }

  private createAccessTokenFromAuthResponse(
    authResponse: FacebookAuthResponse,
    permissions: string[],
  ): AccessToken {
    return {
      expiresAt: Date.now() + authResponse.expiresIn * 1000,
      permissions,
      token: authResponse.accessToken,
      userId: authResponse.userID,
    };
  }

  private getGrantedPermissions(sdk: FacebookSdk): Promise<string[]> {
    return new Promise(resolve => {
      sdk.api<FacebookPermissionsResponse>(
        '/me/permissions',
        { fields: 'permission,status' },
        response => {
          const data = response.data ?? [];
          resolve(
            data
              .filter(item => item.status === 'granted')
              .map(item => item.permission),
          );
        },
      );
    });
  }

  private getLoginStatus(
    sdk: FacebookSdk,
  ): Promise<FacebookLoginStatusResponse> {
    return new Promise(resolve => sdk.getLoginStatus(resolve));
  }

  private getProfile(sdk: FacebookSdk): Promise<Profile> {
    return new Promise((resolve, reject) => {
      sdk.api<FacebookProfileResponse>(
        '/me',
        { fields: FacebookSignInWeb.profileFields },
        response => {
          if (response.error) {
            reject(new Error(response.error.message));
            return;
          }
          resolve({
            email: response.email ?? null,
            id: response.id,
            imageUrl: response.picture?.data?.url ?? null,
            name: response.name ?? null,
          });
        },
      );
    });
  }

  private getSdk(): FacebookSdk {
    if (!this.sdk) {
      throw new Error(FacebookSignInWeb.errorNotInitialized);
    }
    return this.sdk;
  }

  private loadSdk(): Promise<FacebookSdk> {
    return new Promise((resolve, reject) => {
      const sdk = (window as WindowWithFacebookSdk).FB;
      if (sdk) {
        resolve(sdk);
        return;
      }
      const script = document.createElement('script');
      script.src = FacebookSignInWeb.sdkUrl;
      script.async = true;
      script.onload = () => {
        const loadedSdk = (window as WindowWithFacebookSdk).FB;
        if (loadedSdk) {
          resolve(loadedSdk);
        } else {
          reject(new Error(FacebookSignInWeb.errorSdkLoadFailed));
        }
      };
      script.onerror = () =>
        reject(new Error(FacebookSignInWeb.errorSdkLoadFailed));
      document.head.appendChild(script);
    });
  }
}

interface FacebookAuthResponse {
  accessToken: string;
  expiresIn: number;
  grantedScopes?: string;
  userID: string;
}

interface FacebookLoginStatusResponse {
  authResponse: FacebookAuthResponse | null;
  status: string;
}

interface FacebookPermissionsResponse {
  data?: {
    permission: string;
    status: string;
  }[];
}

interface FacebookProfileResponse {
  email?: string;
  error?: {
    message: string;
  };
  id: string;
  name?: string;
  picture?: {
    data?: {
      url?: string;
    };
  };
}

interface FacebookSdk {
  api<T>(
    path: string,
    params: { fields: string },
    callback: (response: T) => void,
  ): void;
  getLoginStatus(
    callback: (response: FacebookLoginStatusResponse) => void,
  ): void;
  init(options: {
    appId: string;
    cookie: boolean;
    version: string;
    xfbml: boolean;
  }): void;
  login(
    callback: (response: FacebookLoginStatusResponse) => void,
    options?: {
      return_scopes?: boolean;
      scope?: string;
    },
  ): void;
  logout(callback: () => void): void;
}

interface WindowWithFacebookSdk extends Window {
  FB?: FacebookSdk;
}
