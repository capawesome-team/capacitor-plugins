import { WebPlugin } from '@capacitor/core';

import type {
  GoogleSignInPlugin,
  InitializeOptions,
  SignInOptions,
  SignInResult,
} from './definitions';

const AUTHORIZATION_ENDPOINT =
  'https://accounts.google.com/o/oauth2/v2/auth';

const SESSION_STORAGE_KEY_NONCE = '__capawesome_google_sign_in_nonce';
const SESSION_STORAGE_KEY_STATE = '__capawesome_google_sign_in_state';

export class GoogleSignInWeb extends WebPlugin implements GoogleSignInPlugin {
  private clientId: string | undefined;
  private redirectUrl: string | undefined;
  private scopes: string[] | undefined;

  async handleRedirectCallback(): Promise<SignInResult> {
    const hash = window.location.hash.substring(1);
    const params = new URLSearchParams(hash);

    const error = params.get('error');
    if (error) {
      const errorDescription = params.get('error_description') || error;
      throw new Error(errorDescription);
    }

    const idToken = params.get('id_token');
    const accessToken = params.get('access_token');
    const state = params.get('state');

    if (!idToken) {
      throw new Error('No ID token found in the URL.');
    }

    const storedState = sessionStorage.getItem(SESSION_STORAGE_KEY_STATE);
    if (state !== storedState) {
      throw new Error('State mismatch. Possible CSRF attack.');
    }

    const storedNonce = sessionStorage.getItem(SESSION_STORAGE_KEY_NONCE);

    sessionStorage.removeItem(SESSION_STORAGE_KEY_NONCE);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_STATE);

    window.history.replaceState(
      {},
      document.title,
      window.location.pathname + window.location.search,
    );

    const payload = this.decodeJwtPayload(idToken);

    if (storedNonce && payload.nonce !== storedNonce) {
      throw new Error('Nonce mismatch. Possible replay attack.');
    }

    return {
      idToken,
      userId: payload.sub,
      email: payload.email ?? null,
      displayName: payload.name ?? null,
      givenName: payload.given_name ?? null,
      familyName: payload.family_name ?? null,
      imageUrl: payload.picture ?? null,
      accessToken: accessToken ?? null,
      serverAuthCode: null,
    };
  }

  async initialize(options?: InitializeOptions): Promise<void> {
    this.clientId = options?.clientId;
    this.redirectUrl = options?.redirectUrl;
    this.scopes = options?.scopes;
  }

  async signIn(options?: SignInOptions): Promise<SignInResult> {
    if (!this.clientId) {
      throw new Error('clientId must be provided.');
    }
    if (!this.redirectUrl) {
      throw new Error('redirectUrl must be provided.');
    }

    const state = this.generateRandomString(32);
    const nonce = options?.nonce ?? this.generateRandomString(32);

    sessionStorage.setItem(SESSION_STORAGE_KEY_STATE, state);
    sessionStorage.setItem(SESSION_STORAGE_KEY_NONCE, nonce);

    const defaultScopes = ['openid', 'email', 'profile'];
    const userScopes = this.scopes ?? [];
    const allScopes = [...new Set([...defaultScopes, ...userScopes])];

    const params = new URLSearchParams({
      response_type: 'id_token token',
      client_id: this.clientId,
      redirect_uri: this.redirectUrl,
      scope: allScopes.join(' '),
      prompt: 'select_account',
      state,
      nonce,
    });

    window.location.href = `${AUTHORIZATION_ENDPOINT}?${params.toString()}`;

    // This promise never resolves because the page redirects
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    return new Promise<SignInResult>(() => {});
  }

  async signOut(): Promise<void> {
    sessionStorage.removeItem(SESSION_STORAGE_KEY_NONCE);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_STATE);
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

  private generateRandomString(length: number): string {
    const array = new Uint8Array(length);
    crypto.getRandomValues(array);
    return btoa(String.fromCharCode(...array))
      .replace(/\+/g, '-')
      .replace(/\//g, '_')
      .replace(/=+$/, '')
      .substring(0, length);
  }
}

interface JwtPayload {
  sub: string;
  email?: string;
  name?: string;
  given_name?: string;
  family_name?: string;
  picture?: string;
  nonce?: string;
}
