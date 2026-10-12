import { WebPlugin } from '@capacitor/core';

import type {
  AcquireTokenResult,
  GetAppConfigResult,
  GetEnrolledAccountResult,
  GetPolicyResult,
  GetSdkVersionResult,
  IntunePlugin,
  IsFileEncryptedResult,
  RemediateComplianceResult,
} from './definitions';

export class IntuneWeb extends WebPlugin implements IntunePlugin {
  async acquireToken(): Promise<AcquireTokenResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async acquireTokenSilent(): Promise<AcquireTokenResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async decryptFile(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getAppConfig(): Promise<GetAppConfigResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getEnrolledAccount(): Promise<GetEnrolledAccountResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getPolicy(): Promise<GetPolicyResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getSdkVersion(): Promise<GetSdkVersionResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async isFileEncrypted(): Promise<IsFileEncryptedResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async loginAndEnrollAccount(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async protectFile(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async registerAndEnrollAccount(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async remediateCompliance(): Promise<RemediateComplianceResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async showDiagnosticConsole(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async unenrollAccount(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }
}
