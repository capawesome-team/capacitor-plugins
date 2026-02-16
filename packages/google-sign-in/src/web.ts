import { WebPlugin } from '@capacitor/core';

import type {
  GoogleSignInPlugin,
  InitializeOptions,
  SignInOptions,
  SignInResult,
} from './definitions';

const AUTHORIZATION_ENDPOINT =
  'https://accounts.google.com/o/oauth2/v2/auth';
const TOKEN_ENDPOINT = 'https://oauth2.googleapis.com/token';

const SESSION_STORAGE_KEY_CODE_VERIFIER =
  '__capawesome_google_sign_in_code_verifier';
const SESSION_STORAGE_KEY_OPTIONS =
  '__capawesome_google_sign_in_options';
const SESSION_STORAGE_KEY_NONCE = '__capawesome_google_sign_in_nonce';
const SESSION_STORAGE_KEY_STATE = '__capawesome_google_sign_in_state';

export class GoogleSignInWeb extends WebPlugin implements GoogleSignInPlugin {
  private clientId: string | undefined;
  private clientSecret: string | undefined;
  private redirectUrl: string | undefined;
  private scopes: string[] | undefined;

  async handleRedirectCallback(): Promise<SignInResult> {
    const url = new URL(window.location.href);
    const code = url.searchParams.get('code');
    const state = url.searchParams.get('state');
    const error = url.searchParams.get('error');

    if (error) {
      const errorDescription =
        url.searchParams.get('error_description') || error;
      throw new Error(errorDescription);
    }

    if (!code) {
      throw new Error('No authorization code found in the URL.');
    }

    const storedState = sessionStorage.getItem(SESSION_STORAGE_KEY_STATE);
    if (state !== storedState) {
      throw new Error('State mismatch. Possible CSRF attack.');
    }

    const codeVerifier = sessionStorage.getItem(
      SESSION_STORAGE_KEY_CODE_VERIFIER,
    );
    const optionsJson = sessionStorage.getItem(SESSION_STORAGE_KEY_OPTIONS);
    if (!codeVerifier || !optionsJson) {
      throw new Error('No stored sign-in state found. Call signIn() first.');
    }

    const options: {
      clientId: string;
      clientSecret: string;
      redirectUrl: string;
    } = JSON.parse(optionsJson);

    const body = new URLSearchParams({
      grant_type: 'authorization_code',
      code,
      redirect_uri: options.redirectUrl,
      client_id: options.clientId,
      client_secret: options.clientSecret,
      code_verifier: codeVerifier,
    });

    const response = await fetch(TOKEN_ENDPOINT, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: body.toString(),
    });

    if (!response.ok) {
      const errorBody = await response.text();
      throw new Error(`Token exchange failed: ${errorBody}`);
    }

    const tokenResponse = await response.json();

    const storedNonce = sessionStorage.getItem(SESSION_STORAGE_KEY_NONCE);

    sessionStorage.removeItem(SESSION_STORAGE_KEY_CODE_VERIFIER);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_NONCE);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_OPTIONS);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_STATE);

    window.history.replaceState({}, document.title, window.location.pathname);

    const idToken: string = tokenResponse['id_token'];
    const accessToken: string = tokenResponse['access_token'];
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
      accessToken,
      serverAuthCode: null,
    };
  }

  async initialize(options?: InitializeOptions): Promise<void> {
    this.clientId = options?.clientId;
    this.clientSecret = options?.clientSecret;
    this.redirectUrl = options?.redirectUrl;
    this.scopes = options?.scopes;
  }

  async signIn(options?: SignInOptions): Promise<SignInResult> {
    if (!this.clientId) {
      throw new Error('clientId must be provided.');
    }
    if (!this.clientSecret) {
      throw new Error('clientSecret must be provided.');
    }
    if (!this.redirectUrl) {
      throw new Error('redirectUrl must be provided.');
    }

    const codeVerifier = this.generateCodeVerifier();
    const codeChallenge = await this.generateCodeChallenge(codeVerifier);
    const state = this.generateRandomString(32);

    sessionStorage.setItem(SESSION_STORAGE_KEY_CODE_VERIFIER, codeVerifier);
    sessionStorage.setItem(
      SESSION_STORAGE_KEY_OPTIONS,
      JSON.stringify({
        clientId: this.clientId,
        clientSecret: this.clientSecret,
        redirectUrl: this.redirectUrl,
      }),
    );
    sessionStorage.setItem(SESSION_STORAGE_KEY_STATE, state);

    const defaultScopes = ['openid', 'email', 'profile'];
    const userScopes = this.scopes ?? [];
    const allScopes = [...new Set([...defaultScopes, ...userScopes])];

    const params = new URLSearchParams({
      response_type: 'code',
      client_id: this.clientId,
      redirect_uri: this.redirectUrl,
      scope: allScopes.join(' '),
      code_challenge: codeChallenge,
      code_challenge_method: 'S256',
      state,
    });
    if (options?.nonce) {
      params.set('nonce', options.nonce);
      sessionStorage.setItem(SESSION_STORAGE_KEY_NONCE, options.nonce);
    }

    window.location.href = `${AUTHORIZATION_ENDPOINT}?${params.toString()}`;

    // This promise never resolves because the page redirects
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    return new Promise<SignInResult>(() => {});
  }

  async signOut(): Promise<void> {
    sessionStorage.removeItem(SESSION_STORAGE_KEY_CODE_VERIFIER);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_NONCE);
    sessionStorage.removeItem(SESSION_STORAGE_KEY_OPTIONS);
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

  private generateCodeVerifier(): string {
    return this.generateRandomString(64);
  }

  private async generateCodeChallenge(verifier: string): Promise<string> {
    const encoder = new TextEncoder();
    const data = encoder.encode(verifier);
    const digest = await crypto.subtle.digest('SHA-256', data);
    return btoa(String.fromCharCode(...new Uint8Array(digest)))
      .replace(/\+/g, '-')
      .replace(/\//g, '_')
      .replace(/=+$/, '');
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
