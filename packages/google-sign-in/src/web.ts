import { WebPlugin } from '@capacitor/core';

import type {
  GoogleSignInPlugin,
  InitializeOptions,
  SignInOptions,
  SignInResult,
} from './definitions';

export class GoogleSignInWeb extends WebPlugin implements GoogleSignInPlugin {
  private static readonly GIS_SCRIPT_URL =
    'https://accounts.google.com/gsi/client';

  private clientId: string | undefined;
  private scopes: string[] | undefined;
  private scriptLoaded = false;

  async initialize(options?: InitializeOptions): Promise<void> {
    this.clientId = options?.clientId;
    this.scopes = options?.scopes;
    await this.loadScript();
  }

  async signIn(options?: SignInOptions): Promise<SignInResult> {
    const google = this.getGoogleApi();
    if (!this.clientId) {
      throw new Error('clientId must be provided.');
    }
    const credential = await this.promptOneTap(
      google,
      this.clientId,
      options?.nonce,
    );
    const payload = this.decodeJwtPayload(credential);
    const idToken = credential;
    const userId = payload.sub;
    const email: string | null = payload.email ?? null;
    const displayName: string | null = payload.name ?? null;
    const givenName: string | null = payload.given_name ?? null;
    const familyName: string | null = payload.family_name ?? null;
    const imageUrl: string | null = payload.picture ?? null;

    let accessToken: string | null = null;
    if (this.scopes && this.scopes.length > 0) {
      accessToken = await this.requestAccessToken(
        google,
        this.clientId,
        this.scopes,
      );
    }

    return {
      idToken,
      userId,
      email,
      displayName,
      givenName,
      familyName,
      imageUrl,
      accessToken,
      serverAuthCode: null,
    };
  }

  async signOut(): Promise<void> {
    const google = this.getGoogleApi();
    google.accounts.id.disableAutoSelect();
  }

  private getGoogleApi(): GoogleApi {
    const google = (window as WindowWithGoogle).google;
    if (!google) {
      throw new Error('Google Identity Services library is not loaded.');
    }
    return google;
  }

  private loadScript(): Promise<void> {
    if (this.scriptLoaded || (window as WindowWithGoogle).google) {
      this.scriptLoaded = true;
      return Promise.resolve();
    }
    return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.src = GoogleSignInWeb.GIS_SCRIPT_URL;
      script.onload = () => {
        this.scriptLoaded = true;
        resolve();
      };
      script.onerror = () => {
        reject(new Error('Failed to load Google Identity Services SDK.'));
      };
      document.head.appendChild(script);
    });
  }

  private promptOneTap(
    google: GoogleApi,
    clientId: string,
    nonce?: string,
  ): Promise<string> {
    return new Promise((resolve, reject) => {
      google.accounts.id.initialize({
        client_id: clientId,
        callback: (response: CredentialResponse) => {
          resolve(response.credential);
        },
        nonce: nonce,
      });
      google.accounts.id.prompt((notification: PromptNotification) => {
        if (notification.isNotDisplayed()) {
          reject(
            new Error(
              'One Tap is not displayed: ' +
                notification.getNotDisplayedReason(),
            ),
          );
        } else if (notification.isSkippedMoment()) {
          reject(
            new Error(
              'One Tap was skipped: ' + notification.getSkippedReason(),
            ),
          );
        }
      });
    });
  }

  private requestAccessToken(
    google: GoogleApi,
    clientId: string,
    scopes: string[],
  ): Promise<string> {
    return new Promise((resolve, reject) => {
      const tokenClient = google.accounts.oauth2.initTokenClient({
        client_id: clientId,
        scope: scopes.join(' '),
        callback: (response: TokenResponse) => {
          if (response.error) {
            reject(new Error(response.error));
            return;
          }
          resolve(response.access_token);
        },
        error_callback: (error: TokenClientError) => {
          reject(new Error(error.message));
        },
      });
      tokenClient.requestAccessToken();
    });
  }

  private decodeJwtPayload(token: string): JwtPayload {
    const parts = token.split('.');
    if (parts.length !== 3) {
      throw new Error('Invalid JWT token.');
    }
    const base64Url = parts[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join(''),
    );
    return JSON.parse(jsonPayload);
  }
}

interface WindowWithGoogle extends Window {
  google?: GoogleApi;
}

interface GoogleApi {
  accounts: {
    id: {
      initialize(config: OneTapConfig): void;
      prompt(callback: (notification: PromptNotification) => void): void;
      disableAutoSelect(): void;
    };
    oauth2: {
      initTokenClient(config: TokenClientConfig): TokenClient;
    };
  };
}

interface OneTapConfig {
  client_id: string;
  callback: (response: CredentialResponse) => void;
  nonce?: string;
}

interface CredentialResponse {
  credential: string;
}

interface PromptNotification {
  isNotDisplayed(): boolean;
  isSkippedMoment(): boolean;
  isDismissedMoment(): boolean;
  getNotDisplayedReason(): string;
  getSkippedReason(): string;
  getDismissedReason(): string;
}

interface TokenClientConfig {
  client_id: string;
  scope: string;
  callback: (response: TokenResponse) => void;
  error_callback: (error: TokenClientError) => void;
}

interface TokenResponse {
  access_token: string;
  error?: string;
}

interface TokenClient {
  requestAccessToken(): void;
}

interface TokenClientError {
  message: string;
}

interface JwtPayload {
  sub: string;
  email?: string;
  name?: string;
  given_name?: string;
  family_name?: string;
  picture?: string;
}
