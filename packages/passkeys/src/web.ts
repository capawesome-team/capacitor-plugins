import { CapacitorException, WebPlugin } from '@capacitor/core';

import { ErrorCode } from './definitions';
import type {
  CreatePasskeyOptions,
  CreatePasskeyResult,
  GetPasskeyOptions,
  GetPasskeyResult,
  IsAvailableResult,
  PasskeyAuthenticatorAttachment,
  PasskeyTransport,
  PasskeysPlugin,
} from './definitions';

export class PasskeysWeb extends WebPlugin implements PasskeysPlugin {
  private static readonly errorCreateFailed =
    'The passkey could not be created.';
  private static readonly errorGetFailed =
    'The passkey could not be retrieved.';
  private static readonly errorNotSupported =
    'Passkeys are not supported in this browser.';

  async createPasskey(
    options: CreatePasskeyOptions,
  ): Promise<CreatePasskeyResult> {
    if (!PasskeysWeb.isWebAuthnSupported()) {
      throw PasskeysWeb.createNotSupportedException();
    }
    let credential: Credential | null;
    try {
      credential = await navigator.credentials.create({
        publicKey: PasskeysWeb.parseCreationOptions(options),
      });
    } catch (error) {
      throw PasskeysWeb.createException(error, ErrorCode.CreateFailed);
    }
    if (!(credential instanceof PublicKeyCredential)) {
      throw new CapacitorException(PasskeysWeb.errorCreateFailed, undefined, {
        code: ErrorCode.CreateFailed,
      });
    }
    return PasskeysWeb.serializeCredential(credential) as CreatePasskeyResult;
  }

  async getPasskey(options: GetPasskeyOptions): Promise<GetPasskeyResult> {
    if (!PasskeysWeb.isWebAuthnSupported()) {
      throw PasskeysWeb.createNotSupportedException();
    }
    let credential: Credential | null;
    try {
      credential = await navigator.credentials.get({
        publicKey: PasskeysWeb.parseRequestOptions(options),
      });
    } catch (error) {
      throw PasskeysWeb.createException(error, ErrorCode.GetFailed);
    }
    if (!(credential instanceof PublicKeyCredential)) {
      throw new CapacitorException(PasskeysWeb.errorGetFailed, undefined, {
        code: ErrorCode.GetFailed,
      });
    }
    return PasskeysWeb.serializeCredential(credential) as GetPasskeyResult;
  }

  async isAvailable(): Promise<IsAvailableResult> {
    if (!PasskeysWeb.isWebAuthnSupported()) {
      return { available: false };
    }
    const available =
      await PublicKeyCredential.isUserVerifyingPlatformAuthenticatorAvailable();
    return { available };
  }

  private static arrayBufferToBase64Url(buffer: ArrayBuffer): string {
    const bytes = new Uint8Array(buffer);
    let binary = '';
    for (const byte of bytes) {
      binary += String.fromCharCode(byte);
    }
    return btoa(binary)
      .replace(/\+/g, '-')
      .replace(/\//g, '_')
      .replace(/=+$/, '');
  }

  private static base64UrlToArrayBuffer(value: string): ArrayBuffer {
    let base64 = value.replace(/-/g, '+').replace(/_/g, '/');
    while (base64.length % 4 > 0) {
      base64 += '=';
    }
    const binary = atob(base64);
    const bytes = new Uint8Array(binary.length);
    for (let index = 0; index < binary.length; index++) {
      bytes[index] = binary.charCodeAt(index);
    }
    return bytes.buffer;
  }

  private static createException(
    error: unknown,
    fallbackCode: ErrorCode,
  ): CapacitorException {
    let code = fallbackCode;
    if (error instanceof DOMException) {
      if (error.name === 'NotAllowedError') {
        code = ErrorCode.Canceled;
      } else if (error.name === 'SecurityError') {
        code = ErrorCode.DomainNotAssociated;
      } else if (error.name === 'NotSupportedError') {
        code = ErrorCode.NotSupported;
      }
    }
    const message =
      error instanceof Error ? error.message : 'An unknown error has occurred.';
    return new CapacitorException(message, undefined, { code });
  }

  private static createNotSupportedException(): CapacitorException {
    return new CapacitorException(PasskeysWeb.errorNotSupported, undefined, {
      code: ErrorCode.NotSupported,
    });
  }

  private static isWebAuthnSupported(): boolean {
    return (
      typeof window !== 'undefined' &&
      typeof window.PublicKeyCredential !== 'undefined'
    );
  }

  private static parseCreationOptions(
    options: CreatePasskeyOptions,
  ): PublicKeyCredentialCreationOptions {
    if (
      typeof PublicKeyCredential.parseCreationOptionsFromJSON === 'function'
    ) {
      return PublicKeyCredential.parseCreationOptionsFromJSON(options);
    }
    return {
      attestation: options.attestation,
      authenticatorSelection: options.authenticatorSelection,
      challenge: PasskeysWeb.base64UrlToArrayBuffer(options.challenge),
      excludeCredentials: options.excludeCredentials?.map(credential => ({
        id: PasskeysWeb.base64UrlToArrayBuffer(credential.id),
        transports: credential.transports as AuthenticatorTransport[],
        type: credential.type,
      })),
      pubKeyCredParams: options.pubKeyCredParams,
      rp: options.rp,
      timeout: options.timeout,
      user: {
        displayName: options.user.displayName,
        id: PasskeysWeb.base64UrlToArrayBuffer(options.user.id),
        name: options.user.name,
      },
    };
  }

  private static parseRequestOptions(
    options: GetPasskeyOptions,
  ): PublicKeyCredentialRequestOptions {
    if (typeof PublicKeyCredential.parseRequestOptionsFromJSON === 'function') {
      return PublicKeyCredential.parseRequestOptionsFromJSON(options);
    }
    return {
      allowCredentials: options.allowCredentials?.map(credential => ({
        id: PasskeysWeb.base64UrlToArrayBuffer(credential.id),
        transports: credential.transports as AuthenticatorTransport[],
        type: credential.type,
      })),
      challenge: PasskeysWeb.base64UrlToArrayBuffer(options.challenge),
      rpId: options.rpId,
      timeout: options.timeout,
      userVerification: options.userVerification,
    };
  }

  private static serializeCredential(
    credential: PublicKeyCredential,
  ): CreatePasskeyResult | GetPasskeyResult {
    if (typeof credential.toJSON === 'function') {
      return credential.toJSON() as unknown as
        | CreatePasskeyResult
        | GetPasskeyResult;
    }
    const authenticatorAttachment = (credential.authenticatorAttachment ??
      undefined) as PasskeyAuthenticatorAttachment | undefined;
    const id = credential.id;
    const rawId = PasskeysWeb.arrayBufferToBase64Url(credential.rawId);
    if (credential.response instanceof AuthenticatorAssertionResponse) {
      const response = credential.response;
      return {
        authenticatorAttachment,
        id,
        rawId,
        response: {
          authenticatorData: PasskeysWeb.arrayBufferToBase64Url(
            response.authenticatorData,
          ),
          clientDataJSON: PasskeysWeb.arrayBufferToBase64Url(
            response.clientDataJSON,
          ),
          signature: PasskeysWeb.arrayBufferToBase64Url(response.signature),
          userHandle: response.userHandle
            ? PasskeysWeb.arrayBufferToBase64Url(response.userHandle)
            : undefined,
        },
        type: 'public-key',
      };
    }
    const response = credential.response as AuthenticatorAttestationResponse;
    return {
      authenticatorAttachment,
      id,
      rawId,
      response: {
        attestationObject: PasskeysWeb.arrayBufferToBase64Url(
          response.attestationObject,
        ),
        clientDataJSON: PasskeysWeb.arrayBufferToBase64Url(
          response.clientDataJSON,
        ),
        transports:
          typeof response.getTransports === 'function'
            ? (response.getTransports() as PasskeyTransport[])
            : undefined,
      },
      type: 'public-key',
    };
  }
}
