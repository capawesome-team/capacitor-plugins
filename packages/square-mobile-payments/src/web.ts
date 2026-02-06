import { WebPlugin } from '@capacitor/core';

import type {
  AuthorizeOptions,
  ForgetReaderOptions,
  GetAvailableCardInputMethodsResult,
  GetReadersResult,
  GetSettingsResult,
  InitializeOptions,
  IsAppleAccountLinkedResult,
  IsAuthorizedResult,
  IsDeviceCapableResult,
  IsPairingInProgressResult,
  PermissionStatus,
  RetryConnectionOptions,
  SquareMobilePaymentsPlugin,
  StartPaymentOptions,
} from './definitions';

export class SquareMobilePaymentsWeb
  extends WebPlugin
  implements SquareMobilePaymentsPlugin
{
  async initialize(_options: InitializeOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async authorize(_options: AuthorizeOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAuthorized(): Promise<IsAuthorizedResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async deauthorize(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async showSettings(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async showMockReader(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async hideMockReader(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getSettings(): Promise<GetSettingsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startPairing(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async stopPairing(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isPairingInProgress(): Promise<IsPairingInProgressResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getReaders(): Promise<GetReadersResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async forgetReader(_options: ForgetReaderOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async retryConnection(_options: RetryConnectionOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async startPayment(_options: StartPaymentOptions): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async cancelPayment(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getAvailableCardInputMethods(): Promise<GetAvailableCardInputMethodsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async linkAppleAccount(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async relinkAppleAccount(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isAppleAccountLinked(): Promise<IsAppleAccountLinkedResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isDeviceCapable(): Promise<IsDeviceCapableResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async checkPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }
}
