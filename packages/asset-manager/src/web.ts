import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  AssetManagerPlugin,
  CopyOptions,
  ListOptions,
  ListResult,
  ReadOptions,
  ReadResult,
} from './definitions';

export class AssetManagerWeb extends WebPlugin implements AssetManagerPlugin {
  async copy(_options: CopyOptions): Promise<void> {
    throw this.createUnimplementedError();
  }

  async list(_options?: ListOptions): Promise<ListResult> {
    throw this.createUnimplementedError();
  }

  async read(_options: ReadOptions): Promise<ReadResult> {
    throw this.createUnimplementedError();
  }

  private createUnimplementedError(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
