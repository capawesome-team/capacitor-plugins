import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type { AppShortcutPlugin, GetResult } from './definitions';

export class AppShortcutWeb extends WebPlugin implements AppShortcutPlugin {
  public async get(): Promise<GetResult> {
    throw this.createUnimplementedError();
  }

  public async set(): Promise<void> {
    throw this.createUnimplementedError();
  }

  public async clear(): Promise<void> {
    throw this.createUnimplementedError();
  }

  private createUnimplementedError(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
